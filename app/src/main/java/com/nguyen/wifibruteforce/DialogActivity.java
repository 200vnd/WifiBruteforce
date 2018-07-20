package com.nguyen.wifibruteforce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogActivity extends Activity {

    @BindView(R.id.btnCancel)
    Button btnStop;
    BruteForceTask hackingTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
        this.setFinishOnTouchOutside(false);

        Intent intent = getIntent();
        String ssid1 = intent.getStringExtra("SSID");
        String ssid2 = intent.getStringExtra("SSID");

        if (getIntent().hasExtra("PATH")) {
            Toast.makeText(getApplicationContext(), "dic", Toast.LENGTH_LONG).show();
            Log.d("z", intent.getStringExtra("PATH"));

        } else
            Toast.makeText(getApplicationContext(), "bf", Toast.LENGTH_LONG).show();
//        doStart(ssid);
    }


    void doStart(String ssid) {
        hackingTask = new BruteForceTask(DialogActivity.this);
        hackingTask.execute(ssid);

    }

    void doStop() {
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
