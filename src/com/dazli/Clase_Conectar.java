package com.dazli;

import java.sql.*;

public class Clase_Conectar {
	
Connection conect = null;
   public Connection conexion()
    {
      try {
           Class.forName("org.gjt.mm.mysql.Driver");
           conect = DriverManager.getConnection("jdbc:mysql://192.185.118.105/usrstore_prueba_dazli","usrstore_shady","498051");
        } catch (Exception e) {
        	//Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }
        return conect;
    }
    

}



