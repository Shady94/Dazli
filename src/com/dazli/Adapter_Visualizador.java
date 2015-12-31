package com.dazli;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.dazli.fin.R;

@SuppressLint("InflateParams")
public class Adapter_Visualizador extends BaseAdapter{

	protected Activity activity;
	protected ArrayList<Clase_Visualizador> items;

	public Adapter_Visualizador(Activity activity, ArrayList<Clase_Visualizador> items) {
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
		Clase_Visualizador dir = items.get(position);
		
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.imagen_visualizador, parent, false);
		holder = new ViewHolder();
		holder.image = (ImageView)convertView.findViewById(R.id.foto);
		
		convertView.setTag(holder);
		}
		
		else holder = (ViewHolder) convertView.getTag();

		holder.image.setImageBitmap(dir.getFoto());
		
		return convertView;

	}	
	
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	static class ViewHolder {

		public ImageView image;

		}
	
}