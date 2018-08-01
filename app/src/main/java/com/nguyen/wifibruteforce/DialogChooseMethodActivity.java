package com.nguyen.wifibruteforce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codekidlabs.storagechooser.StorageChooser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogChooseMethodActivity extends Activity {

    @BindView(R.id.txtMethodBF)
    TextView txtMethodBF;
    @BindView(R.id.txtMethodDic)
    TextView txtMethodDic;
    @BindView(R.id.btnMethodCancel)
    Button btnMethodCancel;

    String ssid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog_method);
        ButterKnife.bind(this);
        this.setFinishOnTouchOutside(false);

        Intent intent = getIntent();
        ssid = intent.getStringExtra("SSID");
    }

    @OnClick(R.id.txtMethodBF)
    public void onTxtMethodBFClicked() {
        Toast.makeText(getApplicationContext(), "Brute force", Toast.LENGTH_LONG).show();
        Intent i = new Intent(DialogChooseMethodActivity.this, DialogTaskActivity.class);
        i.putExtra("SSID", ssid);
        startActivity(i);
        finish();
    }

    @OnClick(R.id.txtMethodDic)
    public void onTxtMethodDicClicked() {
        Toast.makeText(getApplicationContext(), "Choose a dictionary (.txt)", Toast.LENGTH_LONG).show();

        Utils.requestStoragePermission(DialogChooseMethodActivity.this);
        ArrayList<String> t = new ArrayList<>();    //custom file type
        t.add("txt");
        //choose file from storage (use library)
        StorageChooser chooser = new StorageChooser.Builder()
                .withActivity(DialogChooseMethodActivity.this)
                .withMemoryBar(true)
                .withFragmentManager(getFragmentManager())
                .allowCustomPath(true)
                .customFilter(t)
                .setType(StorageChooser.FILE_PICKER)
                .build();
        chooser.show();
        chooser.setOnSelectListener(new StorageChooser.OnSelectListener() {
            @Override
            public void onSelect(String s) {
                Log.e("SELECTED_PATH", s);
                Intent i = new Intent(DialogChooseMethodActivity.this, DialogTaskActivity.class);
                i.putExtra("SSID", ssid);
                i.putExtra("PATH", s);
                startActivity(i);
                finish();
            }
        });

    }

    @OnClick(R.id.btnMethodCancel)
    public void onBtnMethodCancelClicked() {
//        Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_LONG).show();
        finish();
    }


}
