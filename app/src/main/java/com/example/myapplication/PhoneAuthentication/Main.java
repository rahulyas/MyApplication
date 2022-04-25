package com.example.myapplication.PhoneAuthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Main extends AppCompatActivity {
    TextInputEditText editTextCountryCode, editTextPhone;
    AppCompatButton buttonContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //editTextCountryCode = findViewById(R.id.editTextCountryCode);
       final EditText editTextPhone = findViewById(R.id.editTextPhone);
       final AppCompatButton buttonContinue = findViewById(R.id.buttonContinue);




        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String code = editTextCountryCode.getText().toString().trim();

                if(editTextPhone.getText().toString().trim().isEmpty()){
                    Toast.makeText(Main.this,"Enter the Number",Toast.LENGTH_SHORT).show();
                    return;
                }
                //phone number

                /*if (number.isEmpty() || number.length() <10) {
                    editTextPhone.setError("Valid number is required");
                    editTextPhone.requestFocus();
                    return;
                }*/

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + editTextPhone.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        Main.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(Main.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }


                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                Intent intent = new Intent(Main.this, VerifyPhone.class);

                                intent.putExtra("phoneNumber",editTextPhone.getText().toString() );

                                intent.putExtra("verification",verificationId);

                                startActivity(intent);
                                finish();


                            }

                        });





            }
        });

    }
}