package com.nguyen.wifibruteforce;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.WIFI_SERVICE;

public class ConnectivityReceiver extends BroadcastReceiver {
    public static int group_handshake = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Networrk",Toast.LENGTH_LONG).show();
//        ConnectivityManager cm = (ConnectivityManager) context
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        boolean isConnected = activeNetwork != null
//                && activeNetwork.isConnectedOrConnecting();
//        Log.d("running", intent.getAction().toString());
//
//        if (connectivityReceiverListener != null) {
//            connectivityReceiverListener.onNetworkConnectionChanged(isConnected);
//        }
        WifiManager wifiMgr = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        assert wifiMgr != null;
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        Log.d("running", "supplicant0: "+String.valueOf(wifiInfo.getIpAddress()));
        Log.d("running", "supplicant1: "+String.valueOf(wifiInfo.getSupplicantState()));

        if (String.valueOf(wifiInfo.getSupplicantState()).equals(String.valueOf(SupplicantState.GROUP_HANDSHAKE))) {
            group_handshake = 1;
        }
    }

//    public static boolean isConnected(Context context) {
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        return activeNetwork != null
//                && activeNetwork.isConnectedOrConnecting();
//    }
//
//
//    public interface ConnectivityReceiverListener {
//        void onNetworkConnectionChanged(boolean isConnected);
//    }
}
