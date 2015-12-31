package com.dazli;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dazli.fin.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class Clase_Ventana_Login extends Activity implements FacebookCallback<LoginResult> {

	private static String APP_ID = "250628488394501";
	List<String> permissionNeeds=Arrays.asList("user_photos","friends_photos", "email", "user_birthday", "user_friends");

	private CallbackManager mCallbackManager;
    private LoginButton mFbLoginButton;
	// Instance of Facebook Class
	private SharedPreferences mPrefs;
	private ProgressDialog progress;

	// Buttons
	// Button btnFbLogin;
	ImageView btnFbLogin;
	ProgressBar prog;
	Button btn_login_sin_face;
	int w, h;
	String layout;
	String name, email_usr;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Verificar();
		
		//init facebook sdk and 
        FacebookSdk.sdkInitialize(getApplicationContext());

        //instantiate callbacks manager
        mCallbackManager = CallbackManager.Factory.create();


        mFbLoginButton=(LoginButton)findViewById(R.id.login_button);

        //set permissions mFbLoginButton.setReadPermissions(ApplicationContext.facebookPermissions);
        // register callback
        //means hey facebook after login call onActivityResult of **this**  
        mFbLoginButton.registerCallback(mCallbackManager, this);

	}

	void online() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			// Toast.makeText(getApplicationContext(), "Conectado",
			// Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(getApplicationContext(),
					"Porfavor compruebe su conexion a internet",
					Toast.LENGTH_LONG).show();
		}

	}

	void circle() {
		progress.setMessage("Cargando Login");
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.setIndeterminate(true);
		progress.show();
	}

	void datos_usuario() {
		Clase_Conectar cc = new Clase_Conectar();
		Connection cn = cc.conexion();

		String sql = "";
		sql = "INSERT INTO Usuarios (nombre_usuario,email_usuario) VALUES (?, ?)";
		try {
			PreparedStatement pst = cn.prepareStatement(sql);
			pst.setString(1, name);
			pst.setString(2, email_usr);
			pst.executeUpdate();
			System.out.println("Subida OK");
		} catch (SQLException ex) {
			System.out.println("ERROR"+ex);
		}

	}

	private class datos extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... args) {
			String json = "";
			datos_usuario();
			return json;
		}

		@Override
		protected void onPostExecute(String json) {
		}
	}
	
	public void Verificar() {
		SharedPreferences prefs2 = getSharedPreferences("MisPreferencias",
				getApplicationContext().MODE_PRIVATE);
		String tipo = prefs2.getString("tipo", "");
		if (tipo.equals("logeado")) {
			Intent mainIntent3 = new Intent().setClass(
					Clase_Ventana_Login.this,
					Clase_Ventana_Visualizador.class);
			startActivity(mainIntent3);
			finish();
		}
		return;
	}

	@Override
	public void onSuccess(LoginResult loginResult) {
		// TODO Auto-generated method stub
		//login ok  get access token 
        AccessToken.getCurrentAccessToken(); 
		
        GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
					@Override
					public void onCompleted(JSONObject json,
							GraphResponse response) {
						// TODO Auto-generated method stub
						if (response.getError() != null) {
                            // handle error
                            System.out.println("ERROR");
                        } else {
                            try {
                                email_usr = json.getString("email");
                                name = json.getString("name");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
						datos d = new datos();
						d.execute();
						
						SharedPreferences prefs = getSharedPreferences(
								"MisPreferencias", Context.MODE_PRIVATE);
						SharedPreferences.Editor manejo_acceso = prefs
								.edit();
						manejo_acceso.putString("Usuario", name);
						manejo_acceso.putString("tipo", "logeado");
						manejo_acceso.commit();
						/*
						Intent intent = new Intent(
								"com.dazli.fin.Clase_Ventana_Visualizador");
						startActivity(intent);
						*/
					}
					
                }).executeAsync();
        
		Intent intent = new Intent(
				"com.dazli.fin.Clase_Ventana_Visualizador");
		startActivity(intent);
		
	}

	@Override
	public void onCancel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(FacebookException error) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //manage login result
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

}