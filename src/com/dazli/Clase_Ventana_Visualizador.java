package com.dazli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.dazli.conect.ConnectivityObserver;
import com.dazli.conect.LiveConnectivityManager;
import com.dazli.fin.R;
import com.dazli.fragments.Fragment_Controles;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class Clase_Ventana_Visualizador extends FragmentActivity implements
		OnClickListener, ConnectivityObserver {

	String imageHttpAddress = "";
	Bitmap loadedImage;
	ImageView imageView;
	String label;
	LinearLayout layFondo;
	int temp;

	Boolean conect;

	ImageView botonlike;
	ImageView botondislike;
	ImageView botoninfo;
	ImageView botoncloseth;
	ImageView botoncompartir;
	ImageView botonhome;
	ImageView botoncategorias;
	ImageView botonmapa;
	ImageView botonshare;

	static int i = 0;
	static int x = 0;
	static int r = 0;
	static String[][] tabla;
	int[][] tabla_i;
	String usuario, categoria;
	static String mensaje = "Sin error";
	String url = "http://dazli.advisorproject.com/shady/getPrendas2.php";
	String result;
	static ArrayList<Integer> lista;
	private ProgressDialog progress;

	GoogleAnalytics analyticsInstance;

	public static GoogleAnalyticsTracker tracker;
	ArrayList<Clase_Visualizador> arraydir = new ArrayList<Clase_Visualizador>();
	Clase_Visualizador dir;
	Adapter_Visualizador adapter = new Adapter_Visualizador(this, arraydir); //Corregido
	static Fragment_Controles fragment_invisible = new Fragment_Controles();
	ListView lis;
	
	//static int[] prendas_vistas;
	static int cpv; //Contador de Prendas Vistas
	static ArrayList<Integer> prendas_nuevas = new ArrayList<Integer>();
	static ArrayList<Integer> prendas_vistas = new ArrayList<Integer>();
	static ArrayList<Integer> prendas_ignoradas = new ArrayList<Integer>();
	static int[] array_nuevas;
	
	static Context ctx;
	int[] prueba;
	
	static int repetidas;
	static boolean flagg;
	
	int pr =0;
	
	Activity activ;
	ListView ls;
	
	static int lim_sup;
	
	private CallbackManager fb_callbackManager;
	
	View desc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		LiveConnectivityManager.singleton(this).addObserver(this);
		ctx = getApplicationContext();
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.visualizador);
		//progress = new ProgressDialog(this);
		i=0;

		lis = (ListView) findViewById(R.id.lst_Imagen);

		FragmentManager fragmentManagerInv = getSupportFragmentManager();
		
		FragmentTransaction fragmentTransactionInv = fragmentManagerInv
				.beginTransaction();

		fragment_invisible = new Fragment_Controles();
		fragmentTransactionInv
				.replace(android.R.id.content, fragment_invisible).commit();
		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession("UA-57832079-2", 10, this);
		layFondo = (LinearLayout) findViewById(R.id.layFondo);

		botoncloseth = (ImageView) findViewById(R.id.boton_closeth);
		botoncloseth.setOnClickListener(this);
		
		botoncategorias = (ImageView) findViewById(R.id.boton_categorias);
		botoncategorias.setOnClickListener(this);
		
		botonshare = (ImageView) findViewById(R.id.boton_share);
		botonshare.setOnClickListener(this);

		lista = new ArrayList<Integer>();

		SharedPreferences prefs = getSharedPreferences("MisPreferencias",
				Context.MODE_PRIVATE);
		categoria = prefs.getString("Categoria", "Todos");
		usuario = prefs.getString("Usuario", "Error");

		string_categoria();
		
		if (conect.equals(false)){
			Toast.makeText(getApplicationContext(),
    				"Compruebe su conexion a internet", Toast.LENGTH_SHORT).show();
			
		}
		
		else{
			size a = new size();
			a.execute();
		}
		
		////////
		//prueba_shared();
		////////

		SwipeListViewTouchListener touchListener = new SwipeListViewTouchListener(
				lis, new SwipeListViewTouchListener.OnSwipeCallback() {
					@Override
					public void onSwipeLeft(ListView listView,
							int[] reverseSortedPositions) {
						//loadArray("Prendas_Nuevas");
						//arraydir.remove(i);
						arraydir.clear();
						adapter.notifyDataSetChanged();
						if (conect.equals(false)){
							Toast.makeText(getApplicationContext(),
				    				"Compruebe su conexion a internet", Toast.LENGTH_SHORT).show();
							
						}
						else{
							i -= 1;
							if (i < 0) {
								i = x - 1;
							}
							temp = Integer.parseInt(tabla[lista.get(i)][5]);
						
							fragment_invisible.setId_Prenda(temp);
							if (conect.equals(false)){
								Toast.makeText(getApplicationContext(),
										"Compruebe su conexion a internet", Toast.LENGTH_SHORT).show();
							}
							else{
								new carga_imagen().execute();
							}
						}
						
					}

					@Override
					public void onSwipeRight(ListView listView,
							int[] reverseSortedPositions) {
						arraydir.remove(reverseSortedPositions[0]);
						adapter.notifyDataSetChanged();
						if (conect.equals(false)){
							Toast.makeText(getApplicationContext(),
				    				"Compruebe su conexion a internet", Toast.LENGTH_SHORT).show();
							
						}
						else{
							i += 1;
							if (i > (x - 1)) {
								i = 0;
							}
							temp = Integer.parseInt(tabla[lista.get(i)][5]);
							fragment_invisible.setId_Prenda(temp);
						
							if (conect.equals(false)){
								Toast.makeText(getApplicationContext(),
				    				"Compruebe su conexion a internet", Toast.LENGTH_SHORT).show();
							}
							else{
								new carga_imagen().execute();
							}
						}
					}
				}, true, false);

		lis.setOnTouchListener(touchListener);
	}

	@Override
    protected void onStart() {
        super.onStart();
        LiveConnectivityManager.singleton(this).addObserver(this);
    }
	
	void downloadFile(String imageHttpAddress) {
		URL imageUrl = null;
		try {
			imageUrl = new URL(imageHttpAddress);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.connect();
			loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(),
					"Error cargando la imagen: " + e.getMessage(),
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.boton_info:
			temp = Integer.parseInt(tabla[lista.get(i)][5]);
			Intent in = new Intent(this, Clase_Ventana_Info.class);

			in.putExtra("Columna", temp);
			in.putExtra("Filas", r);
			

			EasyTracker easyTracker3 = EasyTracker.getInstance(this);

			easyTracker3.send(MapBuilder.createEvent("Conteo Botones", // Event
																		// category
																		// (required)
					"Conteo de prenda: " + temp, // Event action (required)
					"Boton info", // Event label
					(long) 1) // Event value
					.build());
			startActivity(in);
			this.finish();
			break;

		case R.id.boton_ubic:
			Intent map = new Intent(this, Clase_Ventana_Mapa.class);
			carga_boutique cb = new carga_boutique();
			cb.execute();
			break;

		case R.id.boton_like:
			temp = Integer.parseInt(tabla[lista.get(i)][5]);
			// lista.remove(i);

			EasyTracker easyTracker = EasyTracker.getInstance(this);

			easyTracker.send(MapBuilder.createEvent("Conteo Botones", // Event
																		// category
																		// (required)
					"Conteo de prenda: " + temp, // Event action (required)
					"Boton mas", // Event label
					(long) 1) // Event value
					.build());
			new closeth().execute();

			if (i > (x - 1)) {
				i = 0;
			}
			break;

		case R.id.boton_dislike:
			temp = Integer.parseInt(tabla[lista.get(i)][5]);

			EasyTracker easyTracker2 = EasyTracker.getInstance(this);
			easyTracker2.send(MapBuilder.createEvent("Conteo Botones", // Event
																		// category
																		// (required)
					"Conteo de prenda: " + temp, // Event action (required)
					"Boton Menos", // Event label
					(long) 1) // Event value
					.build());

			i += 1;
			if (i > (x - 1)) {
				i = 0;
			}
			new carga_imagen().execute();
			break;
		case R.id.boton_closeth:
			Toast.makeText(getApplicationContext(),
    				"Opcion disponible en versiones proximas", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.boton_share:
			share();
			break;

		case R.id.boton_categorias:
			//circle();
			Intent cate = new Intent("com.dazli.fin.Clase_Ventana_Categorias");
			startActivity(cate);
			this.finish();
			break;
		}

	}

	void imagen() {
		imageHttpAddress = tabla[lista.get(i)][2];
		
		
		
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

	void agregar_closeth() {
		Clase_Conectar cc = new Clase_Conectar();
		Connection cn = cc.conexion();
		String sql = "";
		int id = Integer.parseInt(tabla[lista.get(i)][5]);
		sql = "INSERT INTO Closet (nombre_usuario,id_prenda) VALUES (?, ?)";
		try {
			PreparedStatement pst = cn.prepareStatement(sql);
			pst.setString(1, usuario);
			pst.setInt(2, id);
			int n = pst.executeUpdate();
			if (n >= 0) {
				// Toast.makeText(getApplicationContext(),
				// "Prenda añadida al closeth", Toast.LENGTH_SHORT).show();
			}

		} catch (SQLException ex) {
			// Toast.makeText(getApplicationContext(),
			// "Error al añadir al closeth: "+ex, Toast.LENGTH_SHORT).show();
		}
		i += 1;
	}

	int consulta_closeth() {
		return 0;
	}

	private static void showJSON(String json) {
		try {
			JSONArray array = new JSONArray(json);
			r = array.length();
			tabla = new String[r][6];
			array_nuevas = new int[r];
			
			for (int i = 0; i < r; i++) {
					JSONObject row = (JSONObject) array.get(i);
					String nombre_prenda = row.get("nombre_prenda").toString();
					String imagen = row.get("url_prenda").toString();
					String descripcion = row.get("descripcion_prenda").toString();
					String id_prenda = row.get("id_prenda").toString();
				
					tabla[i][0] = nombre_prenda;
					tabla[i][2] = imagen;
					tabla[i][3] = descripcion;
					tabla[i][5] = id_prenda;
					lista.add(i);
					x = r;
					fragment_invisible.setSize(x);
					/*
					if(tabla[i][5].equals(prendas_nuevas.get(i))){
						repetidas = repetidas + 1;
					}
					*/
					int comp = Integer.parseInt(tabla[i][5]);
					array_nuevas[i] = Integer.parseInt(id_prenda);
					if(flagg == true){
						if( comp == prendas_nuevas.get(i) ){
							repetidas += 1;
						}
					}
			}
			
			if(flagg == false){
				//saveArray("Prendas_Nuevas", array_nuevas, ctx);
				//saveSize("Prendas_Nuevas", ctx);
			}
			
			//saveArray("Prendas_Vistas", guardar, ctx);
			datos_visualizador datos = new datos_visualizador();
			datos.setDatos(tabla);
			fragment_invisible.setArray(tabla);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			r = 10;
			//mensaje = e.getMessage();
			//Toast.makeText(null, "Error", Toast.LENGTH_SHORT).show();
		}
		Collections.shuffle(lista);

	}

	public String GET(String url) {
		InputStream inputStream = null;
		String result = "";
		try {
			// create HttpClient
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse httpResponse = null;
			HttpPost httppost = new HttpPost(url);

			try {
				httpResponse = httpclient.execute(httppost);

			} catch (ClientProtocolException e) {
			} catch (IOException e) {
			}

			inputStream = httpResponse.getEntity().getContent();

			if (inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";

		} catch (Exception e) {
			//Log.d("InputStream", e.getLocalizedMessage());
		}

		return result;
	}

	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}

	private class size extends AsyncTask<String, String, String> {
		
		protected void onPreExecute(){
			circle();
		}
		@Override
		protected String doInBackground(String... args) {
			String json = GET(url);
			showJSON(json);
			datos_lista();
			id_boutique();
			temp = Integer.parseInt(tabla[lista.get(i)][5]);
			fragment_invisible.setId_Prenda(temp);
			return json;
		}

		@Override
		protected void onPostExecute(String json) {
			adapter();
			progress.dismiss();
		}

	}

	private class carga_imagen extends AsyncTask<String, String, String> {
		
		protected void onPreExecute(){
			circle();
		}
		
		@Override
		protected String doInBackground(String... args) {
			String json = "";
			datos_lista();
			id_boutique();
			return json;
		}

		@Override
		protected void onPostExecute(String json) {
			adapter();
			progress.dismiss();
			/*
			if(1 <= lista.size())
				lista.remove(i);
				*/
		}
	}

	public String StreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	@Override
	protected void onDestroy() {
		tracker.dispatch();
		super.onDestroy();
		tracker.stopSession();
	}

	private class closeth extends AsyncTask<String, String, String> {
		
		@Override
		protected void onPreExecute(){
			circle();
		}

		@Override
		protected String doInBackground(String... args) {
			String json = "";
			agregar_closeth();
			datos_lista();
			
			return json;
		}

		@Override
		protected void onPostExecute(String json) {
			// imageView.setImageBitmap(loadedImage);
			adapter();
			Toast.makeText(getApplicationContext(),
					"Prenda añadida al closeth", Toast.LENGTH_SHORT).show();
			progress.dismiss();
		}
	}

	void datos_lista() {
		arraydir.clear();
		imageHttpAddress = tabla[i][2];
		if(imageHttpAddress.equals(null)){
			imageHttpAddress = "";
		}
		imagen();
		dir = new Clase_Visualizador(loadedImage);
		arraydir.add(dir);
	}

	void adapter() {
		lis.setAdapter(adapter);
	}
	
	public void onBackPressed() {
		this.finish();

	}

	void circle() {
		progress = new ProgressDialog(this);
		progress.setMessage("Cargando imagen");
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.setIndeterminate(true);
		progress.show();
	}

	void string_categoria() {
		if (categoria.equals("Arriba"))
			url = "http://dazli.advisorproject.com/shady/getPrendas_Arriba.php";
		else if (categoria.equals("Abajo"))
			url = "http://dazli.advisorproject.com/shady/getPrendas_Abajo.php";
		else if (categoria.equals("Accesorios"))
			url = "http://dazli.advisorproject.com/shady/getPrendas_Acces.php";
		else if (categoria.equals("Zapatos"))
			url = "http://dazli.advisorproject.com/shady/getPrendas_Zapatos.php";
		else
			url = "http://dazli.advisorproject.com/shady/getPrendas2.php";
	}

	void id_boutique() {
		temp = Integer.parseInt(tabla[lista.get(i)][5]);
		int boutique = 0;

		Clase_Conectar cc = new Clase_Conectar();
		Connection cn = cc.conexion();
		String sql;
		try {
			sql = "SELECT id_boutique FROM Prendas WHERE id_prenda=" + temp
					+ "";
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				boutique = rs.getInt(1);
			}
		} catch (SQLException ex) {
			Logger.getLogger(Clase_Ventana_Visualizador.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		fragment_invisible.setBoutique(boutique);
	}

	private class carga_boutique extends AsyncTask<String, String, String> {
		
		@Override
		protected void onPreExecute(){
			circle();
		}

		@Override
		protected String doInBackground(String... args) {
			String json = "";
			id_boutique();
			return json;
		}

		@Override
		protected void onPostExecute(String json) {
			progress.dismiss();
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
        }
	}
	/*
	public static void saveArray(String tipo_arreglo,int[] prendas_nuevas, Context ctx) { 
		  SharedPreferences prefs = ctx.getSharedPreferences(tipo_arreglo, 0);
		  SharedPreferences.Editor editor = prefs.edit();
		  editor.putInt("Size_Arreglo", prendas_nuevas.length);
		  for(int i=0;i<prendas_nuevas.length;i++)
		    editor.putInt(tipo_arreglo+"_" + i, prendas_nuevas[i]);
		  editor.commit();
		  flagg = true;
		}
	
	public static void saveSize(String tipo_arreglo, Context ctx) { 
		  SharedPreferences prefs = ctx.getSharedPreferences(tipo_arreglo, 0);
		  SharedPreferences.Editor editor = prefs.edit();
		  editor.putInt("Limite_Superior", repetidas);
		}
	
	public void loadArray(String tipo_arreglo) {
		  SharedPreferences prefs = getSharedPreferences(tipo_arreglo, Context.MODE_PRIVATE);
		  int size = prefs.getInt("Size_Arreglo", -1);
		  lim_sup = prefs.getInt("Limite_Superior", -1);
		  pr = size; //Prueba
		  
		  if(size == -1){//Si no tiene guardado ningun arreglo
			  prendas_nuevas = null;
			  flagg = false; //Prueba
		  }
		  else{
			  int prenda;
			  for(int i=0;i<size;i++){
		      prenda = prefs.getInt(tipo_arreglo+"_" + i, 0);
		      prendas_nuevas.add(prenda);
		      
		      flagg = true; //Prueba
		  }
			  //prendas_nuevas.add(size);
		  cpv = size;
		  }
		}
	
	public static void overwriteArray(String tipo_arreglo,int[] prendas_nuevas, Context ctx) { 
		  SharedPreferences prefs = ctx.getSharedPreferences(tipo_arreglo, 0);
		  SharedPreferences.Editor editor = prefs.edit();
		  int size = prefs.getInt("Size_Arreglo", -1);
		  int nuevo_tam = size + prendas_nuevas.length;
		  //editor.putInt("Size_Arreglo", prendas_nuevas.length);
		  for(int i=size;i<nuevo_tam;i++)
		    editor.putInt(tipo_arreglo+"_" + i, prendas_nuevas[i]);
		  
		  editor.putInt("Size_Arreglo", nuevo_tam);
		  editor.commit();
		}
	
	public void vistos(){
		temp = Integer.parseInt(tabla[lista.get(i)][5]);
		arraydir.clear();
		adapter.notifyDataSetChanged();
		i -= 1;
		if (i < 0) {
			i = x - 1;
		}
		fragment_invisible.setId_Prenda(temp);
	}
	
	*/
	public void prueba_shared(){
		///*
		//loadArray("Prendas_Vistas");
		
		Toast.makeText(getApplicationContext(),
				"Prueba: "+prendas_nuevas.get(23),
				Toast.LENGTH_LONG).show();
			//*/
	}
	
	protected void share(){
		fb_callbackManager = CallbackManager.Factory.create();
        String shareText = "A traves de dazli";
        ShareDialog shareDialog = new ShareDialog(this);
        if (ShareDialog.canShow(SharePhotoContent.class)) {
            shareDialog.registerCallback(fb_callbackManager, new FacebookCallback<Sharer.Result>() {
                public void onSuccess(Sharer.Result result) {
                    Toast.makeText(Clase_Ventana_Visualizador.this, "Share Success", Toast.LENGTH_SHORT).show();
                    Log.d("DEBUG", "SHARE SUCCESS");
                }

                public void onCancel() {
                    Toast.makeText(Clase_Ventana_Visualizador.this, "Share Cancelled", Toast.LENGTH_SHORT).show();
                    Log.d("DEBUG", "SHARE CACELLED");
                }

                public void onError(FacebookException exception) {
                    Toast.makeText(Clase_Ventana_Visualizador.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("DEBUG", "Share: " + exception.getMessage());
                    exception.printStackTrace();
                }
            });

            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(loadedImage)
                    .build();
            SharePhotoContent content = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .build();

            shareDialog.show(content);
        }

    }

	public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) 
            bgDrawable.draw(canvas);
        else 
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }
}
