package com.nguyen.wifibruteforce;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import static android.content.Context.WIFI_SERVICE;

public class ConnectivityReceiver extends BroadcastReceiver {
    public static int group_handshake = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        WifiManager wifiMgr = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        assert wifiMgr != null;
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        Log.d("running", "IP: "+String.valueOf(wifiInfo.getIpAddress()));
        Log.d("running", "ssid: "+String.valueOf(wifiInfo.getSSID()));
        Log.d("running", "supplicant_state: "+String.valueOf(wifiInfo.getSupplicantState()));

        if (String.valueOf(wifiInfo.getSupplicantState()).equals(String.valueOf(SupplicantState.GROUP_HANDSHAKE))) {
            group_handshake = 1;
        } else
            group_handshake = 0;

        Log.d("running", "group_handshake = " + group_handshake);
    }

}
