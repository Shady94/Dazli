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
import android.widget.TextView;

import com.dazli.fin.*;

@SuppressLint("InflateParams")
public class Adapter_Prendas extends BaseAdapter{

	protected Activity activity;
	protected ArrayList<Clase_Prendas> items;

	public Adapter_Prendas(Activity activity, ArrayList<Clase_Prendas> items) {
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
		Clase_Prendas dir = items.get(position);
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.prenda_closeth, parent, false);
		holder = new ViewHolder();
		holder.image = (ImageView)convertView.findViewById(R.id.foto);
		holder.text = (TextView)convertView.findViewById(R.id.nombre);
		convertView.setTag(holder);
		}
		else holder = (ViewHolder) convertView.getTag();

		holder.image.setImageBitmap(dir.getFoto());
		holder.text.setText(dir.getNombre());

		return convertView;

	}	
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	static class ViewHolder {

		public ImageView image;
		public TextView text;

		}
	
}