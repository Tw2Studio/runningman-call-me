package com.tw2.runningmancallme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class CallOutActivity extends AppCompatActivity implements View.OnClickListener {
    private String name;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_out);
        Intent intent = getIntent();
        name = intent.getStringExtra("NAME");
        initView();
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
                        startActivity(intent);
                        finish();
                        timer.cancel();

                    }
                });
            }
        }, 4000, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_decline:
                onBackPressed();
                break;
        }
    }
}
