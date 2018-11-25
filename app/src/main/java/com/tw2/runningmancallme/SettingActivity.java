package com.tw2.runningmancallme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private AdView banner;
    private RadioGroup radioGroup1, radioGroup2, radioGroup3;
    private int amBao, timeIn, timOut;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;
    public static final String AM_BAO = "AM_BAO";
    public static final String TIME_OUT = "TIME_OUT";
    public static final String TIME_IN = "TIME_IN";
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorStatusBar_2));
        }
        sharedPreferences = getSharedPreferences("my_data", MODE_PRIVATE);
        edit = sharedPreferences.edit();
        amBao = sharedPreferences.getInt(AM_BAO, 1);
        timOut = sharedPreferences.getInt(TIME_OUT, 5);
        timeIn = sharedPreferences.getInt(TIME_IN, 5);
        intent = getIntent();
        initAds();
        initView();
    }

    private void initAds() {
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-2328589623882503~5227800474");
        banner = (AdView) findViewById(R.id.banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        banner.loadAd(adRequest);
    }

    private void initView() {
        findViewById(R.id.btn_back).setOnClickListener(this);
        radioGroup1 = (RadioGroup) findViewById(R.id.radio_group_1);
        radioGroup2 = (RadioGroup) findViewById(R.id.radio_group_2);
        radioGroup3 = (RadioGroup) findViewById(R.id.radio_group_3);

        radioGroup1.setOnCheckedChangeListener(this);
        radioGroup2.setOnCheckedChangeListener(this);
        radioGroup3.setOnCheckedChangeListener(this);

        if (amBao==1){
            radioGroup1.check(R.id.radio_button_1);
        } else if (amBao==2){
            radioGroup1.check(R.id.radio_button_2);
        } else {
            radioGroup1.check(R.id.radio_button_3);
        }

        if (timOut==3){
            radioGroup2.check(R.id.radio_button_4);
        } else if (timOut==5){
            radioGroup2.check(R.id.radio_button_5);
        } else {
            radioGroup2.check(R.id.radio_button_6);
        }

        if (timeIn==3){
            radioGroup3.check(R.id.radio_button_7);
        } else if (timeIn==5){
            radioGroup3.check(R.id.radio_button_8);
        } else {
            radioGroup3.check(R.id.radio_button_9);
        }



        findViewById(R.id.btn_save).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_save:
                edit.putInt(AM_BAO, amBao);
                edit.putInt(TIME_OUT, timOut);
                edit.putInt(TIME_IN, timeIn);
                edit.commit();
                setResult(RESULT_OK, intent);
                onBackPressed();
                break;

        }
    }

    @Override
    public void onPause() {
        if (banner != null) {
            banner.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (banner != null) {
            banner.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (banner != null) {
            banner.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int id = group.getCheckedRadioButtonId();
        switch (id) {
            case R.id.radio_button_1:
                amBao = 1;
                break;
            case R.id.radio_button_2:
                amBao = 2;
                break;
            case R.id.radio_button_3:
                amBao = 3;
                break;
            case R.id.radio_button_4:
                timOut = 3;
                break;
            case R.id.radio_button_5:
                timOut = 5;
                break;
            case R.id.radio_button_6:
                timOut = 10;
                break;
            case R.id.radio_button_7:
                timeIn = 3;
                break;
            case R.id.radio_button_8:
                timeIn = 5;
                break;
            case R.id.radio_button_9:
                timeIn = 10;
                break;

        }
    }
}
