package com.dazli;

import java.util.ArrayList;

import com.dazli.Adapter_Prendas.ViewHolder;
import com.dazli.fin.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class Adapter_Categorias extends BaseAdapter{

	protected Activity activity;
	protected ArrayList<Clase_Categorias> items;

	public Adapter_Categorias(Activity activity, ArrayList<Clase_Categorias> items) {
	    this.activity = activity;
	    this.items = items;
	  }
	
	public int getCount() {
		return items.size();
	}

	public Object getItem(int arg0) {
		return items.get(arg0);
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		Clase_Categorias dir = items.get(position);
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.filas_categorias, parent, false);
		holder = new ViewHolder();
		holder.image = (ImageView)convertView.findViewById(R.id.columna_izquierda);
		holder.image2 = (ImageView)convertView.findViewById(R.id.columna_derecha);
		
		convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();

		holder.image.setImageBitmap(dir.getFoto_Izquierda());
		holder.image2.setImageBitmap(dir.getFoto_Derecha());

		return convertView;

	}	
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	static class ViewHolder {

		public ImageView image;
		public ImageView image2;

		}
	
}
