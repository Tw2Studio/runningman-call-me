package com.tw2.runningmancallme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class CallInActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_in);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_decline).setOnClickListener(this);
        findViewById(R.id.btn_accept).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_decline:
                onBackPressed();
                break;
            case R.id.btn_accept:
                Intent intent = new Intent(CallInActivity.this, VideoCallActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }
}
