package com.dazli;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dazli.fin.*;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class Clase_Ventana_Closeth extends Activity implements OnClickListener{
	
	String imageHttpAddress;  
	Bitmap loadedImage;
	ImageView imageView;
	
    int i=0;
	int x=0;
	int j=0;
	int k=0;
	int pos;
	String[][] tabla;
	int[][] tabla_i;
	String prenda,boutique,usuario;
	int lim_inf=0;
	int lim_sup=0;
	Button load;
	private ProgressDialog progress;
	
	ArrayList<Clase_Prendas> arraydir = new ArrayList<Clase_Prendas>();
	Clase_Prendas dir;
	Adapter_Prendas adapter = new Adapter_Prendas(this, arraydir);
	
	ImageView btnHome;
    ImageView btnCategorias;
	
	protected void onCreate(Bundle savedInstanceState){
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.closeth);
		progress = new ProgressDialog(this);
		circle();
		new closeth().execute();
		load = (Button) findViewById(R.id.btn_Actualizar);
		load.setOnClickListener(this);
		//final ListView lista = (ListView) findViewById(R.id.listadirectivos);
		
		final ListView lista_izq = (ListView) findViewById(R.id.ListaIzq);
		final ListView lista_der = (ListView) findViewById(R.id.listaDer);
		
		Bundle extras = this.getIntent().getExtras();
	    k = extras.getInt("Filas");//Tamanio
		SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
		usuario = prefs.getString("Usuario", "Error");
		/*
		SwipeListViewTouchListener touchListener =new SwipeListViewTouchListener(lista,new SwipeListViewTouchListener.OnSwipeCallback() {
			@Override
			public void onSwipeLeft(ListView listView, int [] reverseSortedPositions) {
				arraydir.remove(reverseSortedPositions[0]);
				adapter.notifyDataSetChanged();
				new delete().execute();
				
			}

			@Override
			public void onSwipeRight(ListView listView, int [] reverseSortedPositions) {
				arraydir.remove(reverseSortedPositions[0]);
				adapter.notifyDataSetChanged();
				new delete().execute();
			}
		},true, false);
		*/
		//lista.setOnTouchListener(touchListener);
		
		btnHome=(ImageView)findViewById(R.id.btn_home);
		btnHome.setOnClickListener(this);
		btnCategorias = (ImageView)findViewById(R.id.boton_categorias);
		btnCategorias.setOnClickListener(this);
	        
   	 	}
	
	void closeth_usuario(){
		
		tabla_i = new int[k][1];
		int j=0;
		
		Clase_Conectar cc = new Clase_Conectar();
	    Connection cn = cc.conexion();
		try{
			String sql = "SELECT * FROM Closet WHERE nombre_usuario = '"+usuario+"'";
	        Statement st = cn.createStatement();
	        ResultSet rs = st.executeQuery(sql);
	        while (rs.next()){
	            x+=1;
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(Clase_Ventana_Visualizador.class.getName()).log(Level.SEVERE, null, ex);

	    }
		lim_sup=x;
		tabla = new String[x][7];
		tabla_i = new int[x][1];
		j=0;
		
	    try{
	    	String sql = "SELECT nombre_prenda, url_prenda,tallas_prenda FROM Prendas p JOIN Closet c ON p.id_prenda = c.id_prenda WHERE c.nombre_usuario = '"+usuario+"'";//+
	        Statement st = cn.createStatement();
	        ResultSet rs = st.executeQuery(sql);
	        while (rs.next()){
	            tabla[j][1] = rs.getString(1);
	            tabla[j][2] = rs.getString(2);
	            tabla[j][3] = rs.getString(3);
	            //tabla_i[j][0] = 1;
	            j += 1;
	        }
	    } catch (SQLException ex) {
	    	Toast.makeText(getApplicationContext(), "Error consulta usuario: "+ex, Toast.LENGTH_SHORT).show();
	    }
	    
	    j=0;
	    
	    try{
	    	String sql = "SELECT id_prenda FROM Closet WHERE nombre_usuario = '"+usuario+"'";
	        Statement st = cn.createStatement();
	        ResultSet rs = st.executeQuery(sql);
	        while (rs.next()){
	            tabla_i[j][0] = rs.getInt(1);
	            j += 1;
	        }
	    } catch (SQLException ex) {
	    	Toast.makeText(getApplicationContext(), "Error consulta usuario: "+ex, Toast.LENGTH_SHORT).show();
	    }
	    
	    carga_lista();
		
	}
	
	private class closeth extends AsyncTask<Void,Void,Void> {
       	
       	@Override
           protected Void doInBackground(Void... args) {
       		closeth_usuario();
       		return null;
       	}
       	
       	@Override
       	protected void onPostExecute(Void json) {
       		adapter();
       		lista_clic();
       		progress.hide();
       	 }
       	
     }

	void imagen(){
		URL imageUrl = null;
		try{
			imageUrl = new URL(imageHttpAddress);
			HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
			conn.connect();
			loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
		} catch (IOException e) {
		Toast.makeText(getApplicationContext(), "Error cargando la imagen: "+e.getMessage(), Toast.LENGTH_LONG).show();
        e.printStackTrace();
		}
	}

	private class delete extends AsyncTask<Void,Void,Void> {
       	
       	@Override
           protected Void doInBackground(Void... args) {
       		borrar_closeth();
       		return null;
       	}
       	
     }
	
	void datos_lista(){
		imageHttpAddress = tabla[i][2];
		prenda = tabla[i][1];
		boutique = tabla[i][3];
		imagen();
		dir = new Clase_Prendas(loadedImage, prenda, boutique);
		arraydir.add(dir);
	}
	
	void adapter(){
		final ListView lista = (ListView) findViewById(R.id.listadirectivos);
		Adapter_Prendas adapter = new Adapter_Prendas(this, arraydir);
		lista.setAdapter(adapter);
	}
	
	void lista_clic(){
		final ListView lista = (ListView) findViewById(R.id.listadirectivos);
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
				lista.getAdapter().getItem(position);
				
					Intent clos = new Intent ("com.dazli.fin.Clase_Ventana_Info");
					clos.putExtra( "Columna" , tabla_i[position][0]);
					clos.putExtra( "Filas" , k );
					startActivity(clos);
			}
        });
	}
	
	void borrar_closeth(){
		Clase_Conectar cc = new Clase_Conectar();
		Connection cn = cc.conexion();
		String sql = "DELETE FROM Closet WHERE id_prenda="+tabla_i[j][0]+" AND nombre_usuario = '"+usuario+"'";
		try {
			PreparedStatement pst = cn.prepareStatement(sql);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void extra(){
		i++;
		datos_lista();
	}
	
	private class closeth2 extends AsyncTask<Void,Void,Void> {
       	
       	@Override
           protected Void doInBackground(Void... args) {
       		carga_lista();
       		return null;
       	}
       	
       	@Override
       	protected void onPostExecute(Void json) {
       		adapter();
       		lista_clic();
       		progress.hide();
       		Toast.makeText(getApplicationContext(), "Mostrando "+lim_inf+" de "+lim_sup+" Prendas", Toast.LENGTH_SHORT).show();
       	 }
       	
     }
	
	void carga_lista(){
		int lim=0;
		if((lim_inf+3)<lim_sup)
			lim=(lim_inf+3);
		else
			lim=lim_sup;
		
		if(lim!=0){
		for(i=lim_inf;i<lim;i++){
		    datos_lista();
	   	}
		lim_inf = i;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
		case R.id.btn_Actualizar:
			circle();
			new closeth2().execute();
			break;
			
		case R.id.boton_categorias:
			Intent cate = new Intent ("com.dazli.fin.Clase_Ventana_Categorias");
			startActivity(cate);
			break;
			
		case R.id.btn_home:
			Intent home = new Intent ("com.dazli.fin.Clase_Ventana_Categorias");
			startActivity(home);
			break;
		}
		// TODO Auto-generated method stub
		
	}
	
	void circle(){
		  progress.setMessage("Cargando closet");
	      progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	      progress.setIndeterminate(true);
	      progress.show();
	    }
	
	
	
}
