package com.tw2.runningmancallme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mReference;
    private SharedPreferences sharedPreferences;
    private boolean isInstall;
    private SharedPreferences.Editor edit;
    private int nbInstall;
    private List<Contact> list;
    private CircleImageView imgYooJaeSuk, imgKimJongKook, imgKwangSoo, imgSongJihyo, imgHaHa, imgJiSukJin,imgGary, imgSeChan, imgSomin;
    private List<CircleImageView> listCircle;
    private AdView banner;
    private AVLoadingIndicatorView loadingIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorStatusBar_1));
        }
        mReference = FirebaseDatabase.getInstance().getReference();
        sharedPreferences = getSharedPreferences("my_data", MODE_PRIVATE);
        edit = sharedPreferences.edit();

        initView();
        initAds();
        initNbInstall();
        initData();
    }

    private void initAds() {
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-2328589623882503~5227800474");
        banner = (AdView) findViewById(R.id.banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        banner.loadAd(adRequest);
    }

    private void initData() {
        list = new ArrayList<>();
        list.clear();

        mReference.child("runningman").child("data").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Contact contact = dataSnapshot.getValue(Contact.class);
                list.add(contact);
                if (list.size()==9){
                    loadingIndicatorView.hide();
                    for (int i=0;i<list.size();i++){
                        Picasso.get().load(list.get(i).getImage()).into(listCircle.get(i));
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                initData();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initView() {
        listCircle = new ArrayList<>();
        imgYooJaeSuk = (CircleImageView) findViewById(R.id.btn_1);
        imgKimJongKook = (CircleImageView) findViewById(R.id.btn_2);
        imgKwangSoo = (CircleImageView) findViewById(R.id.btn_3);
        imgSongJihyo = (CircleImageView) findViewById(R.id.btn_4);
        imgHaHa = (CircleImageView) findViewById(R.id.btn_5);
        imgJiSukJin = (CircleImageView) findViewById(R.id.btn_6);
        imgGary = (CircleImageView) findViewById(R.id.btn_7);
        imgSeChan = (CircleImageView) findViewById(R.id.btn_8);
        imgSomin = (CircleImageView) findViewById(R.id.btn_9);
        loadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.loading);
        loadingIndicatorView.show();

        listCircle.add(imgYooJaeSuk);
        listCircle.add(imgKimJongKook);
        listCircle.add(imgKwangSoo);
        listCircle.add(imgSongJihyo);
        listCircle.add(imgHaHa);
        listCircle.add(imgJiSukJin);
        listCircle.add(imgGary);
        listCircle.add(imgSeChan);
        listCircle.add(imgSomin);

        for (int i=0;i<listCircle.size();i++){
            final int finalI = i;
            listCircle.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list!=null && list.size()>0) {
                        Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                        intent.putExtra("IMAGE", list.get(finalI).getImage());
                        intent.putExtra("NAME", list.get(finalI).getName());
                        intent.putExtra("CALL", list.get(finalI).getCall());
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private void initNbInstall() {
        isInstall = sharedPreferences.getBoolean("isIntall", false);
        if (!isInstall) {
            mReference.child("runningman").child("install").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String nb = dataSnapshot.getValue(String.class);
                    nbInstall = Integer.parseInt(nb) + 1;
                    edit.putBoolean("isIntall", true);
                    edit.commit();
                    mReference.child("runningman").child("install").child("number").setValue(nbInstall + "");
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
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
