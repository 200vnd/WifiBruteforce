package com.nguyen.wifibruteforce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;

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
        String ssid = intent.getStringExtra("SSID");

        doStart(ssid);
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
