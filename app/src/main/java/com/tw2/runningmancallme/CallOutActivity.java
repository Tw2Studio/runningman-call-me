package com.tw2.runningmancallme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Timer;
import java.util.TimerTask;

import static com.tw2.runningmancallme.SettingActivity.TIME_OUT;

public class CallOutActivity extends AppCompatActivity implements View.OnClickListener {
    private String name, call;
    private TextView textView;
    private AdView banner;
    private SharedPreferences sharedPreferences;
    private int timOut;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_out);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorStatusBar_1));
        }
        Intent intent = getIntent();
        name = intent.getStringExtra("NAME");
        call = intent.getStringExtra("CALL");
        sharedPreferences = getSharedPreferences("my_data", MODE_PRIVATE);
        timOut = sharedPreferences.getInt(TIME_OUT, 5);
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
        textView = (TextView) findViewById(R.id.tv_name);
        textView.setText(name);

        findViewById(R.id.btn_decline).setOnClickListener(this);

        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(CallOutActivity.this, VideoCallActivity.class);
                        intent.putExtra("CALL", call);
                        startActivity(intent);
                        finish();
                        timer.cancel();

                    }
                });
            }
        }, timOut*1000, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_decline:
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
}
