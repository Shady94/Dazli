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

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.LocalSocketAddress;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.method.BaseKeyListener;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dazli.conect.ConnectivityObserver;
import com.dazli.conect.LiveConnectivityManager;
import com.dazli.fin.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

public class Clase_Ventana_Info extends FragmentActivity implements
	OnClickListener, ConnectivityObserver{
		Boolean conect;
		Bitmap loadedImage;
		ImageView img_prenda;
		ImageView botonhome, boton_share;
		TextView prenda, talla, descuento, precio, descripcion;
		EditText Mostrar;
		Button btnMasDescuento;
		String[][] tabla;
		String imageHttpAddress ="";
		String sql;
		ImageView btnDescuento;
		int i = 0, x = 0;
		private ProgressDialog progress;
		ImageView img_ubicacion;

		ImageView btnHome;
		ImageView btnCategorias;
		private static String ID_FB = "250628488394501";
		
		private SharedPreferences mPrefs;
		
		private CallbackManager fb_callbackManager;

		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			super.onCreate(savedInstanceState);
			setContentView(R.layout.info);
			LiveConnectivityManager.singleton(this).addObserver(this);
			//progress = new ProgressDialog(this);
			
			fb_callbackManager = CallbackManager.Factory.create();

			btnDescuento = (ImageView) findViewById(R.id.btnDescuento);
			btnDescuento.setOnClickListener(this);
			boton_share=(ImageView)findViewById(R.id.boton_share);
			boton_share.setOnClickListener(this);
			img_prenda = (ImageView) findViewById(R.id.img_Prenda);

			img_ubicacion = (ImageView) findViewById(R.id.img_Ubicacion);
			img_ubicacion.setOnClickListener(this);

			btnHome = (ImageView) findViewById(R.id.btn_home);
			btnHome.setOnClickListener(this);
			btnCategorias = (ImageView) findViewById(R.id.boton_categorias);
			btnCategorias.setOnClickListener(this);

			prenda = (TextView) findViewById(R.id.txtPrenda);
			talla = (TextView) findViewById(R.id.txtTalla);
			descuento = (TextView) findViewById(R.id.txtDescuento);
			precio = (TextView) findViewById(R.id.txtPrecio);

			descripcion=(TextView)findViewById(R.id.txtDescripcion);
			Bundle extras = this.getIntent().getExtras();
			i = extras.getInt("Columna");// Id_Prenda
			x = extras.getInt("Filas");// Tamanio
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

		public void onBackPressed() {
			Intent regresointent = new Intent(Clase_Ventana_Info.this,
					Clase_Ventana_Visualizador.class);
			startActivity(regresointent);
			finish();
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}

		void cargarbd() {
			tabla = new String[x][6];
			Clase_Conectar cc = new Clase_Conectar();
			Connection cn = cc.conexion();
			try {
				sql = "SELECT url_prenda, nombre_prenda, tallas_prenda, precio_prenda, descuento_prenda, descripcion_prenda FROM Prendas WHERE id_prenda="
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
		
					// i = rs.getInt(6);
				}

				try {
					sql = "SELECT id_boutique FROM Prendas WHERE id_prenda=" + i
							+ "";
					st = cn.createStatement();
					rs = st.executeQuery(sql);
					while (rs.next()) {
						i = rs.getInt(1);
					}
				} catch (SQLException ex) {
					Logger.getLogger(Clase_Ventana_Visualizador.class.getName())
					.log(Level.SEVERE, null, ex);
				}

			} catch (SQLException ex) {
				Logger.getLogger(Clase_Ventana_Visualizador.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.img_Ubicacion:
				// carga_boutique cb = new carga_boutique();
				// cb.execute();
				Intent inte = new Intent(this, Clase_Ventana_Mapa.class);
				inte.putExtra("Columna", i);
				inte.putExtra("Filas", x);
				startActivity(inte);
	
				break;

			case R.id.boton_categorias:
				Intent cate = new Intent("com.dazli.fin.Clase_Ventana_Categorias");
				startActivity(cate);
				this.finish();
				break;

			case R.id.btn_home:
				Intent home = new Intent("com.dazli.fin.Clase_Ventana_Visualizador");
				startActivity(home);
				this.finish();
				break;
	
			case R.id.boton_share:
				//Toast.makeText(getApplicationContext(),
					//	"Opcion disponible en versiones proximas", Toast.LENGTH_SHORT).show();
				share();
				break;
			case R.id.btnDescuento:
				share();
				break;
/*
 * case R.id.btnMasDescuento: FragmentManager fragmentManagerInv =
 * getSupportFragmentManager(); FragmentTransaction
 * fragmentTransactionInv = fragmentManagerInv .beginTransaction();
 * 
 * Fragment_Compartir fragment_invisible = new Fragment_Compartir();
 * fragmentTransactionInv .replace(android.R.id.content,
 * fragment_invisible).commit(); publicarMuro();
 * 
 * 
 * break;
 */

			}

		}

		private class bd extends AsyncTask<String, String, String> {

			@Override
			protected String doInBackground(String... args) {
				String json = "";
				cargarbd();
				imagen();
				return json;
			}

			@Override
			protected void onPostExecute(String json) {
				prenda.setText(tabla[0][1]);
				talla.setText("Talla: " + tabla[0][2]);
				// direccion.setText(tabla[0][8]);
				// telefono.setText(tabla[0][9]);
				descuento.setText("Descuento: " + tabla[0][4] + "%");
				// horario.setText(tabla[0][10]);
				precio.setText("Precio: " + tabla[0][3]);
				descripcion.setText("Descripci�n: \n"+tabla[0][5]);

				img_prenda.setImageBitmap(loadedImage);
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

		void circle() {
			progress = new ProgressDialog(this);
			progress.setMessage("Cargando informacion");
			progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progress.setIndeterminate(true);
			progress.show();
		}

		private class carga_boutique extends AsyncTask<String, String, String> {

			@Override
			protected String doInBackground(String... args) {
				String json = "";
				id_boutique();
				return json;
			}

			@Override
			protected void onPostExecute(String json) {
				
			}
		}

		void id_boutique() {
			Clase_Conectar cc = new Clase_Conectar();
			Connection cn = cc.conexion();
			try {
				sql = "SELECT id_boutique FROM Prendas WHERE id_prenda=" + i + "";
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery(sql);
				i = rs.getInt(1);
			} catch (SQLException ex) {
				Logger.getLogger(Clase_Ventana_Visualizador.class.getName()).log(
						Level.SEVERE, null, ex);
			}
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
				/*
    			prenda.setText("Verifique su conexion a internet");
    			talla.setText("Verifique su conexion a internet");
    			precio.setText("Verifique su conexion a internet");
				 */
			}
		}
		
		protected void share(){
	        String shareText = "A traves de dazli";
	        ShareDialog shareDialog = new ShareDialog(this);
	        if (ShareDialog.canShow(SharePhotoContent.class)) {
	            shareDialog.registerCallback(fb_callbackManager, new FacebookCallback<Sharer.Result>() {
	                public void onSuccess(Sharer.Result result) {
	                    Toast.makeText(Clase_Ventana_Info.this, "Share Success", Toast.LENGTH_SHORT).show();
	                    Log.d("DEBUG", "SHARE SUCCESS");
	                    
	                    Display display = getWindowManager().getDefaultDisplay();
	    				int ancho = display.getWidth();
	    				int  alto= display.getHeight();
	    				
	    				
	    				btnDescuento.getLayoutParams().height=60;
	    				
	    				
	    				descuento.setVisibility(descuento.VISIBLE);
	    				btnDescuento.setVisibility(btnDescuento.INVISIBLE);
	    				SharedPreferences prefsdesc = getSharedPreferences(
	    						"MisPreferencias", getApplicationContext().MODE_PRIVATE);
	    				SharedPreferences.Editor editor = prefsdesc.edit();
	    				//editor.putString("usuario", "logeados");
	    				editor.putString("descuento", "activado");
	    				editor.commit();
	                }

	                public void onCancel() {
	                    Toast.makeText(Clase_Ventana_Info.this, "Share Cancelled", Toast.LENGTH_SHORT).show();
	                    Log.d("DEBUG", "SHARE CACELLED");
	                }

	                public void onError(FacebookException exception) {
	                    Toast.makeText(Clase_Ventana_Info.this, exception.getMessage(), Toast.LENGTH_LONG).show();
	                    Log.e("DEBUG", "Share: " + exception.getMessage());
	                    exception.printStackTrace();
	                }
	            });

	            SharePhoto photo = new SharePhoto.Builder()
	                    .setBitmap(loadedImage)
	                    .build();
	            SharePhotoContent content = new SharePhotoContent.Builder()
	            .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.dazli.fin"))
	                    .addPhoto(photo)
	                    .build();
	            ShareLinkContent content2 = new ShareLinkContent.Builder()
	            .setContentTitle("Dazli")
	            .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.dazli.fin"))
	            .setImageUrl(Uri.parse(tabla[0][0]))
	            .build();

	            shareDialog.show(content2);
	        }

	    }
		

	}	