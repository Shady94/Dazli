package com.dazli;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class Application extends android.app.Application {

	  public Application() {
	  }

	  @Override
	  public void onCreate() {
	    super.onCreate();

		// Initialize the Parse SDK.
	    Parse.initialize(this, "hXZExNhh1D688kyVgTr3cg60yQFnFBBY1EsqUewZ", "eXLm8IZeManvb5OsR8WfUf6mNPjR48JwwlEtmuvo");

		// Specify an Activity to handle all pushes by default.
	PushService.setDefaultPushCallback(this, Clase_Ventana_Splash.class);
	
	FacebookSdk.sdkInitialize(getApplicationContext());
  }
}