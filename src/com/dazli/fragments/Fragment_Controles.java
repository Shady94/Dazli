package com.dazli.fragments;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.dazli.Adapter_Visualizador;
import com.dazli.Clase_Conectar;
import com.dazli.Clase_Ventana_Info;
import com.dazli.Clase_Ventana_Mapa;
import com.dazli.Clase_Ventana_Visualizador;
import com.dazli.Clase_Visualizador;
import com.dazli.fin.R;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

public class Fragment_Controles extends Fragment implements OnClickListener {
	
	private ProgressDialog progress;
	int id_prenda = 0;
	int size = 0;
	int id_boutique = 0;
	
	String[][] tabla;
	
	static boolean flagg;
	
	public Fragment_Controles(){
	}
	
	ImageView btnLoc, btnInf, btnLike, btnDis;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View RootView = inflater.inflate(R.layout.fragment_controles, container,
				false);
		inicializarcomponentes(RootView);
		return RootView;
		
		
	}

	private void inicializarcomponentes(View view) {
		// TODO Auto-generated method stub
		btnLoc=(ImageView) view.findViewById(R.id.boton_ubic);
		btnLoc.setOnClickListener(this);
		btnLike=(ImageView) view.findViewById(R.id.boton_like);
		btnLike.setOnClickListener(this);
		btnDis=(ImageView) view.findViewById(R.id.boton_dislike);
		btnDis.setOnClickListener(this);
		btnInf=(ImageView) view.findViewById(R.id.boton_info);
		btnInf.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		
		case R.id.boton_ubic:
			EasyTracker easyTracker4 = EasyTracker.getInstance(getActivity());
			easyTracker4.send(MapBuilder.createEvent("Conteo Botones", // Event
					// category
					// (required)
					"Conteo de prenda: " + getId_Prenda(), // Event action (required)
					"Boton mas", // Event label
					(long) 1) // Event value
					.build());
			
			Intent mapaIntent = new Intent(getActivity(),
					Clase_Ventana_Mapa.class);
			mapaIntent.putExtra("Columna", getBoutique());
			mapaIntent.putExtra("Filas", getSize());
			startActivity(mapaIntent);
			break;
			
			
		case R.id.boton_like:
			Toast.makeText(getActivity(),
					"Prenda añadida al closet", Toast.LENGTH_SHORT).show();
			
			EasyTracker easyTracker = EasyTracker.getInstance(getActivity());
			easyTracker.send(MapBuilder.createEvent("Conteo Botones", // Event
					// category
					// (required)
					"Conteo de prenda: " + getId_Prenda(), // Event action (required)
					"Boton mas", // Event label
					(long) 1) // Event value
					.build());
			break;
			
		case R.id.boton_dislike:
			Toast.makeText(getActivity(),
					"Prenda ignorada", Toast.LENGTH_SHORT).show();
			
			EasyTracker easyTracker2 = EasyTracker.getInstance(getActivity());
			easyTracker2.send(MapBuilder.createEvent("Conteo Botones", // Event
					// category
					// (required)
					"Conteo de prenda: " + getId_Prenda(), // Event action (required)
					"Boton menos", // Event label
					(long) 1) // Event value
					.build());
			
			
			break;
			
		
		case R.id.boton_info:
			EasyTracker easyTracker3 = EasyTracker.getInstance(getActivity());
			easyTracker3.send(MapBuilder.createEvent("Conteo Botones", // Event
					// category
					// (required)
					"Conteo de prenda: " + getId_Prenda(), // Event action (required)
					"Boton mas", // Event label
					(long) 1) // Event value
					.build());
			
			Intent infoIntent = new Intent(getActivity(),
					Clase_Ventana_Info.class);
			
			infoIntent.putExtra("Columna", getId_Prenda());
			infoIntent.putExtra("Filas", getSize());
			startActivity(infoIntent);
			getActivity().finish();
			break;
		
		}
		
	}
	
	
	public int getId_Prenda() {
		return id_prenda;
	}

	public void setId_Prenda(int Prueba) {
		this.id_prenda = Prueba;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public int getBoutique() {
		return id_boutique;
	}

	public void setBoutique(int id_boutique) {
		this.id_boutique = id_boutique;
	}
	
	public void setArray(String[][] tabla){
		this.tabla = tabla;
	}
	
	public String[][] getArray(){
		return tabla;
	}
	///////////////////////////////////////
	
	public void desactivar() {
		btnLoc.setEnabled(false);
		btnLike.setEnabled(false);
		btnDis.setEnabled(false);
		btnInf.setEnabled(false);
	}
	
	public void activar() {
		btnLoc.setEnabled(true);
		btnLike.setEnabled(true);
		btnDis.setEnabled(true);
		btnInf.setEnabled(true);
	}
	
	void circle() {
		progress = new ProgressDialog(getActivity());
		progress.setMessage("Cargando imagen");
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.setIndeterminate(true);
		progress.show();
	}
	
}