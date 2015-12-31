package com.dazli;

import java.util.Timer;
import java.util.TimerTask;

import com.dazli.fin.*;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

public class Clase_Ventana_Splash extends Activity {
private final int DURACION = 2000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ParseInstallation.getCurrentInstallation().saveInBackground();
		ParseAnalytics.trackAppOpened(getIntent());
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ventana_splash);
		
		SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
		SharedPreferences.Editor manejo_acceso = prefs.edit();
		manejo_acceso.putString("Categoria", "Todos");
		manejo_acceso.commit();
		
		TimerTask task =new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//Intent mainIntent= new Intent().setClass(Clase_Ventana_Splash.this, Clase_Prueba_Conect.class);
				Intent mainIntent= new Intent().setClass(Clase_Ventana_Splash.this, Clase_Ventana_Login.class);
				startActivity(mainIntent);
			finish();
			}
		};
		
		 Timer timer = new Timer();
	        timer.schedule(task, DURACION);
    }
}