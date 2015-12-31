package com.dazli;

import android.graphics.Bitmap;

public class Clase_Visualizador {

	protected Bitmap foto;
	protected long id;
	
	public Clase_Visualizador(Bitmap foto) {
		super();
		this.foto = foto;
	}
	
	public Bitmap getFoto() {
		return foto;
	}

	public void setFoto(Bitmap foto) {
		this.foto = foto;
	}

}
