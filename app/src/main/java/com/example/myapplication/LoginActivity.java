package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.PhoneAuthentication.Main;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
private FirebaseAuth mAuth;
private TextView loginbtn;
private Intent MainActivity;
private Intent Home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final EditText usermail = findViewById(R.id.phone);
        final EditText password = findViewById(R.id.password);
        final TextView loginbtn = findViewById(R.id.loginbtn);
        final TextView registerNowBtn = findViewById(R.id.registerbtn);

        registerNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainactivity= new Intent(getApplicationContext(), Main.class);
                startActivity(mainactivity);
            }
        });
        MainActivity =new Intent(this, com.example.myapplication.MainActivity.class);
       // Dashboard =new Intent(this, com.example.myapplication.Dashboard.class);

        mAuth = FirebaseAuth.getInstance();
        Home =new Intent(this,com.example.myapplication.Home.class);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //loginbtn.setVisibility(View.INVISIBLE);

                final String mail= usermail.getText().toString();
                final String Password= password.getText().toString();

                if(mail.isEmpty() || Password.isEmpty()){
                    showMessage("Please Verify All Field");
                }
                else
                {
                    signIn(mail,Password);
                }
            }
        });


    }

    private void showMessage(String text) {
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
    }

    private void signIn(String mail, String Password) {
        mAuth.signInWithEmailAndPassword(mail,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                  //  if(1==1) {
                    //    loginbtn.setVisibility(View.VISIBLE);
                    //};
                    Intent intent=new Intent(LoginActivity.this, com.example.myapplication.Home.class);
                    startActivity(intent);
                    finish();
                    //updateUI();

                }
                else
                {
                    showMessage(task.getException().getMessage());
                }

            }
        });
    }

  /*  private void updateUI() {
     //   System.out.print("hello");


        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user= mAuth.getCurrentUser();

        if(user !=null){
            //user is already connected so we need to redirect him to home page
            updateUI();
        }
    }*/
}