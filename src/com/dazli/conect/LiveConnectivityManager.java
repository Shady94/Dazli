
package com.dazli.conect;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

public class LiveConnectivityManager {

    private static List<ConnectivityObserver> mObservers;

    private boolean mConnected;

    private final Context mContext;

    private static LiveConnectivityManager mManager;

    public static LiveConnectivityManager singleton(Context context) {

        if (mManager == null) {
            mManager = new LiveConnectivityManager(context);
        }

        return mManager;
    }
    
    private LiveConnectivityManager(Context context) {
        mObservers = new ArrayList<ConnectivityObserver>();
        mContext = context;
        mConnected = isConnectionEnabled();

    }
    
    boolean isConnectionEnabled() {

        try {
            ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        } catch (Exception e) {
            return false;
        }
    }

    public void addObserver(ConnectivityObserver observer) {
        mObservers.add(observer);
        observer.manageNotification(mConnected);
    }
    
    public void removeObserver(ConnectivityObserver observer) {
        mObservers.remove(observer);
    }

    void notifyConnectionChange() {

        mConnected = isConnectionEnabled();

        for (ConnectivityObserver observer : mObservers) {
            observer.manageNotification(mConnected);
        }
    }
}
