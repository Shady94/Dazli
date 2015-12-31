package com.dazli;

import com.dazli.fin.R;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Clase_Temporal extends Activity implements OnClickListener{
	
	Button btn_ingreso;
	EditText nombre;
	int Access = 0;
	String layout;
	int w,h;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		 setContentView(R.layout.sin_face);
		 super.onCreate(savedInstanceState);
		 
		 ParseAnalytics.trackAppOpened(getIntent());
		 ParseInstallation.getCurrentInstallation().saveInBackground();
		 nombre = (EditText)findViewById(R.id.bxNombre);
		 btn_ingreso = (Button)findViewById(R.id.btnIngresar);
		 btn_ingreso.setOnClickListener(this);
		 
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.btnIngresar:
				
				SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
				SharedPreferences.Editor manejo_acceso = prefs.edit();
				String usr = nombre.getText().toString();
				if(usr.length() >0){
					manejo_acceso.putString("Usuario", usr);
					manejo_acceso.commit();
					acceso_ok();
		    		Intent intent=new Intent ("com.dazli.fin.Clase_Ventana_Visualizador");
					startActivity(intent);
				}
				else
					Toast.makeText(getApplicationContext(), "Porfavor ingrese su email", Toast.LENGTH_SHORT).show();
				break;
		}
	}
	
	void acceso_ok(){
		SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
		SharedPreferences.Editor manejo_acceso = prefs.edit();
		manejo_acceso.putInt("Acceso", 1);
		manejo_acceso.commit();
	}
	
	int getAccess(){
		SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
		int acc = prefs.getInt("Acceso", 0);
		return acc;
	}
	
	void acceso_close(){
		SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
		SharedPreferences.Editor manejo_acceso = prefs.edit();
		manejo_acceso.putInt("Acceso", 0);
		manejo_acceso.commit();
	}
	
}
