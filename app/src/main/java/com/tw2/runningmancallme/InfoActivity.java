package com.tw2.runningmancallme;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {
    private String name;
    private String image;
    private CircleImageView imageView;
    private TextView textView, tvCountdown;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Intent intent = getIntent();
        image = intent.getStringExtra("IMAGE");
        name = intent.getStringExtra("NAME");
        initView();
    }

    private void initView() {
        imageView = (CircleImageView) findViewById(R.id.img_avatar);
        textView = (TextView) findViewById(R.id.tv_name);
        tvCountdown = (TextView) findViewById(R.id.tv_countdown);

        textView.setText(name);
        Picasso.get().load(image).into(imageView);

        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_call_out).setOnClickListener(this);
        findViewById(R.id.btn_call_in).setOnClickListener(this);
        findViewById(R.id.btn_setting).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_setting:
                Intent intent3 = new Intent(InfoActivity.this, SettingActivity.class);
                startActivity(intent3);
                break;

            case R.id.btn_call_out:
                Intent intent = new Intent(InfoActivity.this, CallOutActivity.class);
                intent.putExtra("NAME", name);
                startActivity(intent);
                break;
            case R.id.btn_call_in:
                new CountDownTimer(5000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        tvCountdown.setText(""+millisUntilFinished/1000);
                    }

                    public void onFinish() {
                        tvCountdown.setText("");
                        Intent intent1 = new Intent(InfoActivity.this, CallInActivity.class);
                        intent1.putExtra("NAME", name);
                        startActivity(intent1);
                        cancel();
                    }

                }.start();
                break;

        }
    }
}
