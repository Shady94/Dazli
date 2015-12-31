package com.dazli;

import android.graphics.Bitmap;

public class Clase_Prendas {
	protected Bitmap foto ;
	protected String nombre_prenda;
	protected String boutique;
	protected long id;
	
	public Clase_Prendas(Bitmap foto, String nombre, String boutique) {
		super();
		this.foto = foto;
		this.nombre_prenda = nombre;
		this.boutique = boutique;
	}
	
	public Bitmap getFoto() {
		return foto;
	}

	public void setFoto(Bitmap foto) {
		this.foto = foto;
	}

	public String getNombre() {
		return nombre_prenda;
	}

	public void setNombre(String nombre) {
		this.nombre_prenda = nombre;
	}

	public String getCargo() {
		return boutique;
	}

	public void setCargo(String boutique) {
		this.boutique = boutique;
	}

}
