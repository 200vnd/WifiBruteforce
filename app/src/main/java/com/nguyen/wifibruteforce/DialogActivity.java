package com.nguyen.wifibruteforce;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogActivity extends Activity {

    @BindView(R.id.btnCancel)
    Button btnStop;
    DictionaryTask hackingTask;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
        this.setFinishOnTouchOutside(false);

        Intent intent = getIntent();
        String ssid = intent.getStringExtra("SSID");


        if (getIntent().hasExtra("PATH")) {             //dictionary method
            Toast.makeText(getApplicationContext(), "dic", Toast.LENGTH_LONG).show();
            Log.d("running", intent.getStringExtra("PATH"));

            String path = intent.getStringExtra("PATH");
            ArrayList<String> ListPass = read(path);

            flag = 0;
            doStart(ssid, ListPass, flag);

        } else {                //brute force method
            flag = 1;
            doStart(ssid, null, flag);
            Toast.makeText(getApplicationContext(), "bf", Toast.LENGTH_LONG).show();
        }
    }

    //2 types of reading file to arraylist
    private ArrayList read(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ArrayList arrPass = new ArrayList(Arrays.asList(br.lines().toArray()));
                br.close();
                return arrPass;
            } else {
                String line;
                ArrayList arrPass = new ArrayList();
                while ((line = br.readLine()) != null) {
                    arrPass.add(line);
                }
                br.close();
                return arrPass;
            }
        } catch (FileNotFoundException e) {
            Log.e("readFile", "not found");
            Toast.makeText(getApplicationContext(), getString(R.string.file_not_found), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("readFile", "IOEx");
            Toast.makeText(getApplicationContext(), getString(R.string.IOException), Toast.LENGTH_LONG).show();
        }
        return null;
    }

    private void doStart(String ssid, ArrayList<String> listPass, int flag) {
        if (flag == 0) {
            hackingTask = new DictionaryTask(DialogActivity.this);
            hackingTask.setArrPass(listPass);
            hackingTask.execute(ssid);
        } else if (flag == 1) {
            Log.d("running", "method bf " + ssid);

        } else {
            Log.e("running", "some thing wrong");

        }
    }

    private void doStop(int flag) {
        if (flag == 0) {
            if (hackingTask != null && hackingTask.getStatus() != AsyncTask.Status.FINISHED)
                hackingTask.cancel(true);
            if (hackingTask.isCancelled()) {
//            Log.d("running", "task cancel");
                finish();
            }
        } else {
            Log.d("running", "doStop flag !=0");
            finish();
        }
    }


    @OnClick(R.id.btnCancel)
    public void onViewClicked() {
        doStop(flag);
    }
}
