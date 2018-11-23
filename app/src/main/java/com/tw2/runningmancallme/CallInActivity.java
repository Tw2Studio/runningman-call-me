package com.tw2.runningmancallme;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class CallInActivity extends AppCompatActivity implements View.OnClickListener {
    private String name;
    private TextView textView;
    private Handler handler;
    private int delay = 2000;
    private Ringtone defaultRingtone;
    private Timer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_in);
        Intent intent = getIntent();
        name = intent.getStringExtra("NAME");
        initView();
    }

    private void initView() {
        textView = (TextView) findViewById(R.id.tv_name);
        textView.setText(name);
        findViewById(R.id.btn_decline).setOnClickListener(this);
        findViewById(R.id.btn_accept).setOnClickListener(this);

        onVibrate();
        playRingtone();
    }

    private void playRingtone(){
        defaultRingtone = RingtoneManager.getRingtone(CallInActivity.this,
                Settings.System.DEFAULT_RINGTONE_URI);

        defaultRingtone.play();
    }

    private void onVibrate(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            v.vibrate(VibrationEffect.createOneShot(800, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            //deprecated in API 26
                            v.vibrate(800);
                        }

                    }
                });
            }
        }, 2000, 2000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_decline:
                onBackPressed();
                break;
            case R.id.btn_accept:
                if (timer!=null){
                    timer.cancel();
                }

                if (defaultRingtone!=null){
                    defaultRingtone.stop();
                }
                Intent intent = new Intent(CallInActivity.this, VideoCallActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (timer!=null){
            timer.cancel();
        }

        if (defaultRingtone!=null){
            defaultRingtone.stop();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //handler.removeCallbacks(runnable);
    }
}
