package com.nguyen.wifibruteforce;

import android.app.Activity;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
        this.setFinishOnTouchOutside(false);

        Intent intent = getIntent();
        String ssid = intent.getStringExtra("SSID");

        if (getIntent().hasExtra("PATH")) {
            Toast.makeText(getApplicationContext(), "dic", Toast.LENGTH_LONG).show();
            Log.d("pz", intent.getStringExtra("PATH"));

            String path = intent.getStringExtra("PATH");
            ArrayList<String> ListPass = read(path);
            Log.d("pz", "a: "+ListPass.get(4));

            doStart(ssid,ListPass);

//            String[] sss = a.toArray(new String[0]);
        } else
            Toast.makeText(getApplicationContext(), "bf", Toast.LENGTH_LONG).show();
//        doStart(ssid);
    }

    private ArrayList read(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ArrayList arrPass = new ArrayList(Arrays.asList(br.lines().toArray()));
                Log.d("pz", "loaded[5]: " + arrPass.get(5));
                br.close();
                return arrPass;
            }else {
                String line;
                ArrayList arrPass = new ArrayList();
                while ((line = br.readLine()) != null) {
                    arrPass.add(line);
                }
                Log.d("pz", "loaded[0]: " + arrPass.get(5));
                br.close();
                return arrPass;
            }
        } catch (FileNotFoundException e) {
            Log.e("read_file","not found");
            Toast.makeText(getApplicationContext(), R.string.file_not_found, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("read_file","IOEx");
            Toast.makeText(getApplicationContext(), R.string.IOException, Toast.LENGTH_LONG).show();
        }
        return null;
    }

    private void doStart(String ssid, ArrayList<String> listPass) {
        hackingTask = new DictionaryTask(DialogActivity.this);
        hackingTask.setArrPass(listPass);
        hackingTask.execute(ssid);

    }

    private void doStop() {
        hackingTask.cancel(true);
        if (hackingTask.isCancelled()) {
//            Log.d("pz", "task cancel");
            finish();
        }
    }


    @OnClick(R.id.btnCancel)
    public void onViewClicked() {
        doStop();
    }
}
