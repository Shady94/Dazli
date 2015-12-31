package com.dazli;

import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;

public class Progress_Circle {
	
	private ProgressDialog progress;
	
	public void open(View view){
	      progress.setMessage("Downloading Music :) ");
	      progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	      progress.setIndeterminate(true);
	      progress.show();

	   final int totalProgressTime = 100;

	   final Thread t = new Thread(){

	   @Override
	   public void run(){
	 
	      int jumpTime = 0;
	      while(jumpTime < totalProgressTime){
	         try {
	            sleep(200);
	            jumpTime += 5;
	            progress.setProgress(jumpTime);
	         } catch (InterruptedException e) {
	           // TODO Auto-generated catch block
	           e.printStackTrace();
	         }

	      }

	   }
	   };
	   t.start();

	   }
	   public boolean onCreateOptionsMenu(Menu menu) {
	      // Inflate the menu; this adds items to the action bar if it is present.
	      //getMenuInflater().inflate(R.menu.main, menu);
	      return true;
	   }
  
		 
}
