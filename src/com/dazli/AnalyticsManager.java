package com.dazli;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import android.content.Context;

public class AnalyticsManager {
	
	private GoogleAnalyticsTracker tracker;
	 private String UA_Code;
	 private int frecuencyUpdateGoogle = 60;
	 private Context context;
	 
	 private GoogleAnalyticsTracker getTracker(){
	  if(tracker == null){
	   tracker = GoogleAnalyticsTracker.getInstance();
	   tracker.startNewSession(UA_Code, frecuencyUpdateGoogle ,this.context);
	  }
	  return tracker;
	 }
	 
	 public AnalyticsManager(Context context,String UACode){
	  this.context = context;  
	  this.UA_Code = UACode;
	 }
	 
	 public void registerPage(String page, String parameter){
	  this.getTracker().trackPageView("/" + page+ "/" + parameter);
	 }
	 
	 public void registerPage(String page){  
	  this.getTracker().trackPageView("/" + page);
	 }
	 
	 public void registerAction(String Category, String Action, String Label, int Value){
	  this.getTracker().trackEvent(Category, Action, Label, Value);
	 }
	 
	 public void stopTracker(){
	  if(this.getTracker() != null)
	   this.getTracker().stopSession();
	 }
	 
	 
}
