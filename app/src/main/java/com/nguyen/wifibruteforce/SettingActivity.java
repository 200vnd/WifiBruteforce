package com.nguyen.wifibruteforce;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.switchDefaultDic)
    Switch switchDefaultDic;

    public static boolean flagSwitchDefaultDic = false;
    @BindView(R.id.btnDone)
    Button btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        Intent intent = getIntent();

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean tgpref = preferences.getBoolean("tgpref", true);  //default is true

        switchDefaultDic.setChecked(tgpref);
//        switchDefaultDic.setChecked(true);
        Toast.makeText(getApplicationContext(), "Oop" + flagSwitchDefaultDic, Toast.LENGTH_LONG).show();
//        file:///android_asset/pass_dictionary_test.txt

//        if (switchDefaultDic.isChecked()) {
//            flagSwitchDefaultDic = true;
//        }
    }

    @OnCheckedChanged(R.id.switchDefaultDic)
    public void onSwitchDefaultDicChecked(Switch switchDefaultDic, boolean isChecked) {
        if (isChecked) {
            flagSwitchDefaultDic = true;
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putBoolean("tgpref", switchDefaultDic.isChecked()); // value to store
//            editor.commit();
            Toast.makeText(getApplicationContext(), "On" + flagSwitchDefaultDic, Toast.LENGTH_LONG).show();
        } else {
            flagSwitchDefaultDic = false;
            Toast.makeText(getApplicationContext(), "Off" + flagSwitchDefaultDic, Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.btnDone)
    public void onViewClicked() {
        finish();
    }
}
