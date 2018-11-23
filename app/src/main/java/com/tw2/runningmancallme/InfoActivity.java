package com.tw2.runningmancallme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_call_out).setOnClickListener(this);
        findViewById(R.id.btn_call_in).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_call_out:
                Intent intent = new Intent(InfoActivity.this, CallOutActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_call_in:
                Intent intent1 = new Intent(InfoActivity.this, CallInActivity.class);
                startActivity(intent1);
                break;

        }
    }
}
