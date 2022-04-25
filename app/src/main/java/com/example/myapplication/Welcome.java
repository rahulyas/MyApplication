package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.myapplication.PhoneAuthentication.Main;

public class Welcome extends AppCompatActivity {

    private static int SPLASH_TIME_OUT=1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Welcome.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT );
    }
}