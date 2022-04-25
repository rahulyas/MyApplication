package com.example.myapplication.PhoneAuthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.R;

public class Profile extends AppCompatActivity {
    //String phoneNumber,user_email,user_name,user_Gender,user_address,user_City;
    TextView mobileNumber,useremail,username,userGender,useraddress,userCity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //hooks

        mobileNumber =findViewById(R.id.user_phone);
        useremail = findViewById(R.id.user_email);
        username = findViewById(R.id.user_name);
        useremail = findViewById(R.id.user_email);
        useraddress =findViewById(R.id.user_address);
        userCity = findViewById(R.id.user_City);

        //show user data

        showAllUserData();

    }

    private void showAllUserData() {
        Intent intent = getIntent();
        String username1= intent.getStringExtra("name");
        String useremail1 = intent.getStringExtra("email");
        String useraddress1 = intent.getStringExtra("Address");
        String userCity1= intent.getStringExtra("country");
        String mobileNumber1 = intent.getStringExtra("phonetxt");



    }
}