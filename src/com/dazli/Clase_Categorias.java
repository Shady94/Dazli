package com.dazli;

import android.graphics.Bitmap;

public class Clase_Categorias{
	
	protected Bitmap foto_izquierda;
	protected Bitmap foto_derecha;
	protected long id;
	
	public Clase_Categorias(Bitmap derecha, Bitmap izquierda) {
		super();
		this.foto_derecha = derecha;
		this.foto_izquierda = izquierda;
	}
	
	public Bitmap getFoto_Derecha() {
		return foto_derecha;
	}

	public void setFoto_Derecha(Bitmap foto_derecha) {
		this.foto_derecha = foto_derecha;
	}
	
	public Bitmap getFoto_Izquierda() {
		return foto_izquierda;
	}

	public void setFoto_Izquierda(Bitmap foto_izquierda) {
		this.foto_izquierda = foto_izquierda;
	}

}
