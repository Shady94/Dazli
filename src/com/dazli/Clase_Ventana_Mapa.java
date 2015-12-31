package com.dazli;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dazli.conect.ConnectivityObserver;
import com.dazli.conect.LiveConnectivityManager;
import com.dazli.fin.*;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Clase_Ventana_Mapa extends Activity implements OnClickListener, ConnectivityObserver {

	Boolean conect;
	Bitmap loadedImage, logoimage;
	ImageView img_prenda;
	ImageView btnShare;
	ImageView img_map, img_log;
	ImageView botonhome;
	TextView txtMostrar, horario, direccion, telefono;
	EditText Mostrar;
	String[][] tabla;
	String imageHttpAddress, ubicacionurl;
	WebView webmap;
	int i = 0, x = 0;
	String a;
	private ProgressDialog progress;

	ImageView btnHome;
	ImageView btnCategorias;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa);
		LiveConnectivityManager.singleton(this).addObserver(this);
		//progress = new ProgressDialog(this);

		btnHome = (ImageView) findViewById(R.id.btn_home);
		btnHome.setOnClickListener(this);
		btnCategorias = (ImageView) findViewById(R.id.boton_categorias);
		btnCategorias.setOnClickListener(this);
		btnShare = (ImageView) findViewById(R.id.boton_share);
		btnShare.setOnClickListener(this);
		

		img_map = (ImageView) findViewById(R.id.img_mapa);
		img_map.setOnClickListener(this);
		img_log = (ImageView) findViewById(R.id.img_logo);
		direccion = (TextView) findViewById(R.id.txtDireccion);
		telefono = (TextView) findViewById(R.id.txtTelefono);
		horario = (TextView) findViewById(R.id.txtHorario);
		
		Bundle extras = this.getIntent().getExtras();
		i = extras.getInt("Columna");// Id_Prenda
		x = extras.getInt("Filas");
		/*
		circle();
		new bd().execute();
		*/
		
		if (conect.equals(false)){
			Toast.makeText(getApplicationContext(),
					"Compruebe su conexion a internet", Toast.LENGTH_SHORT).show();
		}
		else{
			circle();
			new bd().execute();
		}
		
	}

	/*
	 * public void onBackPressed() { Intent regresointent = new
	 * Intent(Clase_Ventana_Mapa.this, Clase_Ventana_Info.class);
	 * startActivity(regresointent); finish();
	 * 
	 * }
	 */
	void cargarbd() {
		tabla = new String[x][7];
		Clase_Conectar cc = new Clase_Conectar();
		Connection cn = cc.conexion();
		try {
			String sql = "SELECT url_local,url_logotipo, direccion_boutique, telefono_boutique, horario_boutique, descripcion_boutique, ubicacion FROM Boutique WHERE id_boutique="
					+ i + "";
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				tabla[0][0] = rs.getString(1);
				tabla[0][1] = rs.getString(2);
				tabla[0][2] = rs.getString(3);
				tabla[0][3] = rs.getString(4);
				tabla[0][4] = rs.getString(5);
				tabla[0][5] = rs.getString(6);
				tabla[0][6] = rs.getString(7);
			}
		} catch (SQLException ex) {
			Logger.getLogger(Clase_Ventana_Mapa.class.getName()).log(
					Level.SEVERE, null, ex);
		}

		try {
			String sql = "SELECT * FROM Boutiques";
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				tabla[0][3] = rs.getString(3);
			}
		} catch (SQLException ex) {
			Logger.getLogger(Clase_Ventana_Mapa.class.getName()).log(
					Level.SEVERE, null, ex);
		}

	}

	private class bd extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... args) {
			String json = "";
			cargarbd();
			imagen();
			logo();
			return json;
		}

		@Override
		protected void onPostExecute(String json) {
			img_map.setImageBitmap(loadedImage);
			direccion.setText("Direccion: " + tabla[0][2]);
			telefono.setText("Telefono: " + tabla[0][3]);
			horario.setText("Horario: " + tabla[0][4]);
			ubicacionurl = tabla[0][6];
			if (tabla[0][1].toString().equals("")) {

			} else {
				img_log.setImageBitmap(logoimage);
			}
			progress.hide();
		}
	}

	void imagen() {
		imageHttpAddress = tabla[0][0];
		URL imageUrl = null;
		try {
			imageUrl = new URL(imageHttpAddress);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.connect();
			loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void logo() {
		imageHttpAddress = tabla[0][1];
		URL imageUrl = null;
		try {
			imageUrl = new URL(imageHttpAddress);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.connect();
			logoimage = BitmapFactory.decodeStream(conn.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		case R.id.img_mapa:
			
			String url =(ubicacionurl);
			webmap=(WebView)findViewById(R.id.webmap);
			webmap.loadUrl(url);
			
			break;
		case R.id.boton_categorias:
			Intent cate = new Intent("com.dazli.fin.Clase_Ventana_Categorias");
			startActivity(cate);
			break;

		case R.id.btn_home:
			Intent home = new Intent("com.dazli.fin.Clase_Ventana_Visualizador");
			startActivity(home);
			break;
		
		case R.id.boton_share:
			Toast.makeText(getApplicationContext(),
    				"Opcion disponible en versiones proximas", Toast.LENGTH_SHORT).show();
			break;
		}
	}

	void circle() {
		progress = new ProgressDialog(this);
		progress.setMessage("Cargando informacion");
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.setIndeterminate(true);
		progress.show();
	}

	@Override
	public void manageNotification(boolean connectionEnabled) {
		// TODO Auto-generated method stub
		if (connectionEnabled) {
			conect = true;
	    } else {
	    	conect = false;
	    	Toast.makeText(getApplicationContext(),
					"Compruebe su conexion a internet", Toast.LENGTH_SHORT).show();
	    }
	}

}
