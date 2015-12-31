package com.dazli;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.dazli.conect.*;
import com.dazli.fin.R;

public class Clase_Prueba_Conect extends Activity implements ConnectivityObserver{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.prueba_conect);

        LiveConnectivityManager.singleton(this).addObserver(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LiveConnectivityManager.singleton(this).addObserver(this);
    }


	@Override
	public void manageNotification(boolean connectionEnabled) {
		// TODO Auto-generated method stub
		if (connectionEnabled) {
			Toast.makeText(getApplicationContext(),
					"Conexion habilitada", Toast.LENGTH_SHORT).show();
        } else {
        	Toast.makeText(getApplicationContext(),
    				"Sin conexion", Toast.LENGTH_SHORT).show();
        }
	}

}
