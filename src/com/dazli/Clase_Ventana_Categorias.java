package com.dazli;
	
import com.dazli.fin.R;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


	public class Clase_Ventana_Categorias extends Activity implements OnClickListener {
		ImageView btnShare;
		ImageView btnHome;
		LinearLayout btnVestido, btnPantalon, btnLentes, btnChamarra, btnCortos, btnInterior;
		TextView tv1, tv2, tv3, tv4;
		
		int w,h;
		String layout;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			super.onCreate(savedInstanceState);
			setContentView(R.layout.ventana_categorias);
			
			btnVestido=(LinearLayout)findViewById(R.id.btnArriba);
			btnChamarra=(LinearLayout)findViewById(R.id.btnAbajo);
			btnLentes=(LinearLayout)findViewById(R.id.btnAccesorios);
			btnPantalon=(LinearLayout)findViewById(R.id.btnZapatos);
			btnShare=(ImageView)findViewById(R.id.boton_share);
			btnShare.setOnClickListener(this);
			btnVestido.setOnClickListener(this);
			btnPantalon.setOnClickListener(this);
			btnChamarra.setOnClickListener(this);
			btnLentes.setOnClickListener(this);
			
			
			btnHome=(ImageView)findViewById(R.id.btn_home);
			btnHome.setOnClickListener(this);
		}
		@Override
		public void onClick(View v) {
			
			Intent mainIntent= new Intent ("com.dazli.fin.Clase_Ventana_Visualizador");
			SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
			SharedPreferences.Editor manejo_acceso = prefs.edit();
			switch(v.getId()){
			
			case R.id.btnArriba:
				manejo_acceso.putString("Categoria", "Arriba");
				manejo_acceso.commit();
				startActivity(mainIntent);
				this.finish();
				break;
				
			case R.id.btnAbajo:	
				manejo_acceso.putString("Categoria", "Abajo");
				manejo_acceso.commit();
				startActivity(mainIntent);
				this.finish();
				break;
				
			case R.id.btnAccesorios:	
				manejo_acceso.putString("Categoria", "Accesorios");
				manejo_acceso.commit();
				startActivity(mainIntent);
				this.finish();
				break;
				
			case R.id.btnZapatos:	
				manejo_acceso.putString("Categoria", "Zapatos");
				manejo_acceso.commit();
				startActivity(mainIntent);
				this.finish();
				break;
			case R.id.btn_home:
				Intent home = new Intent ("com.dazli.fin.Clase_Ventana_Visualizador");
				startActivity(home);
				this.finish();
				break;
				
			case R.id.boton_share:
				Toast.makeText(getApplicationContext(),
	    				"Opcion disponible en versiones proximas", Toast.LENGTH_SHORT).show();
				break;
			
			}
			
		}
		public void onBackPressed() {
			Intent regresointent = new Intent(Clase_Ventana_Categorias.this,
					Clase_Ventana_Visualizador.class);
			startActivity(regresointent);
			this.finish();

		}
	}
