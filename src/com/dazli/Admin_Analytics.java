package com.dazli;

import com.google.analytics.tracking.android.EasyTracker;


import android.app.Activity;
import android.os.Bundle;

public class Admin_Analytics extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	  }

	  public void onStart() {
	    super.onStart();
	    EasyTracker.getInstance(this).activityStart(this);  // Add this method.
	  }

	  public void onStop() {
	    super.onStop();
	    EasyTracker.getInstance(this).activityStop(this);  // Add this method.
	  }
	}
