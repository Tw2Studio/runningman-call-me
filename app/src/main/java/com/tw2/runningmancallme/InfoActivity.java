package com.tw2.runningmancallme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tw2.runningmancallme.SettingActivity.TIME_IN;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {
    private String name;
    private String image;
    private String call;
    private CircleImageView imageView;
    private TextView textView, tvCountdown;
    private AdView banner;
    public static final int REQUES_CODE = 1011;
    private InterstitialAd mInterstitialAd;
    private SharedPreferences sharedPreferences;
    private int timeIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorStatusBar_2));
        }
        Intent intent = getIntent();
        image = intent.getStringExtra("IMAGE");
        name = intent.getStringExtra("NAME");
        call = intent.getStringExtra("CALL");
        sharedPreferences = getSharedPreferences("my_data", MODE_PRIVATE);
        timeIn = sharedPreferences.getInt(TIME_IN, 5);
        initAdsFull();
        initAds();
        initView();
    }

    private void initAds() {
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-2328589623882503~5227800474");
        banner = (AdView) findViewById(R.id.banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        banner.loadAd(adRequest);
    }

    public void initAdsFull(){
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-2328589623882503~5227800474");
        mInterstitialAd = new InterstitialAd(InfoActivity.this);
        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.banner_full));
        final AdRequest adRequest = new AdRequest.Builder()
                .build();

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(adRequest);
            }
        });

        mInterstitialAd.loadAd(adRequest);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUES_CODE && resultCode==RESULT_OK){
            timeIn = sharedPreferences.getInt(TIME_IN, 5);
            Random random = new Random();
            int show = random.nextInt(2);
            if (show==1 && mInterstitialAd.isLoaded()){
                mInterstitialAd.show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_setting:
                Intent intent3 = new Intent(InfoActivity.this, SettingActivity.class);
                startActivityForResult(intent3, REQUES_CODE);
                break;

            case R.id.btn_call_out:
                Intent intent = new Intent(InfoActivity.this, CallOutActivity.class);
                intent.putExtra("NAME", name);
                intent.putExtra("CALL", call);
                startActivity(intent);
                break;
            case R.id.btn_call_in:
                new CountDownTimer(timeIn*1000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        tvCountdown.setText(""+millisUntilFinished/1000);
                    }

                    public void onFinish() {
                        tvCountdown.setText("");
                        Intent intent1 = new Intent(InfoActivity.this, CallInActivity.class);
                        intent1.putExtra("NAME", name);
                        intent1.putExtra("CALL", call);
                        startActivity(intent1);
                        cancel();
                    }

                }.start();
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
