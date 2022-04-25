package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class RegisterActivity extends AppCompatActivity {

    private EditText username,u_email,password,c_password,address,Country;
    RadioGroup radioGroup;
    RadioButton male;
    RadioButton female;
    private Button signup;
    ImageView userphoto;
    static int PRegCode =1;
    static int REQUESCODE =1;
    Uri pickedImaUri;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=findViewById(R.id.editTextName);
        u_email=findViewById(R.id.editTextEmail);
        password=findViewById(R.id.editTextPassword);
        c_password=findViewById(R.id.editTextPassword1);
        address = findViewById(R.id.address);
        Country = findViewById(R.id.Country);

       // radioGroup = findViewById(R.id.radiogroup);
       // radioGroup.clearCheck();

       // male=findViewById(R.id.male);
        //female = findViewById(R.id.female);



        final EditText phone=findViewById(R.id.phone);
        //////////////
        //this is for go back to login page
        final TextView loginNowBtn=findViewById(R.id.loginbtn1);
        loginNowBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                finish();
            }
        });

       firebaseAuth=FirebaseAuth.getInstance();
        signup=findViewById(R.id.button);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get data from EditTexts into String variables

                signup.setVisibility(View.VISIBLE);
                final String name=username.getText().toString();
                final String email=u_email.getText().toString();
                final String phonetxt=phone.getText().toString();
                final String Password=password.getText().toString();
                final String Password2=c_password.getText().toString();
                final  String Address=address.getText().toString();
                final String country=Country.getText().toString();
                //final String textGender = radioGroup.getText().toString();
               // int selectgenderid = radioGroup.getCheckedRadioButtonId();
                //radioGroup = findViewById(selectgenderid);



                if (name.isEmpty() || email.isEmpty() || phonetxt.isEmpty() || Password.isEmpty() || Address.isEmpty() || country.isEmpty()){

                    ///something goes wrong: all field must be filled
                    ///we need to display a error message
                    showMessage("Please fill all fields");
                    signup.setVisibility(View.VISIBLE);
                    
                }
                else if(!Password.equals(Password2)){
                    showMessage("Password are not matching");
                }
                else{

                    //everry thing is ok and all field are filled now we can created user account
                    //create user account method if user email is valid

                    CreateUserAccount(email,name,Password);
                    
                }

            }
        });

        userphoto=findViewById(R.id.userphoto);
        userphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT>= 22){

                    checkAndRequestedForPermission();
                }
                else
                {
                    openGallery();
                }



            }
        });


    }

    private void CreateUserAccount(String email, final String name, String password) {
        //this method create user account with specific email password
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            //user account create succesful
                            showMessage("Account created");
                            //after creating account now we update user information like name picture
                            updateUserInfo(name ,pickedImaUri,firebaseAuth.getCurrentUser());

                        }
                        else
                        {
                            //account creation failed
                            showMessage("Account creation failed" + task.getException());
                            signup.setVisibility(View.VISIBLE);

                        }

                    }
                });

    }

    /// update user photo and name

    private void updateUserInfo(final String username, Uri pickedImaUri,final FirebaseUser currentUser) {

        //first we need to upload user photo to firebase storage and get url

        StorageReference mstorage = FirebaseStorage.getInstance().getReference().child("user_photos");
        StorageReference imgFilePath = mstorage.child(pickedImaUri.getLastPathSegment());
        imgFilePath.putFile(pickedImaUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //image uploaded successfully
                // now get over image url

                imgFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //uri contain image url

                        UserProfileChangeRequest profleupdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                .setPhotoUri(uri)
                                .build();

                        currentUser.updateProfile(profleupdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    //user information update successfully
                                   showMessage("Register Complete");
                                    upadedUI();
                                }
                            }
                        });
                    }
                });
            }
        });



    }

    private void upadedUI() {
        Intent home= new Intent(getApplicationContext(),Home.class);
        startActivity(home);
        finish();
    }


    // Simple message to toast message

    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

    }

    private void openGallery() {
        //ToDo: open the gallery to pick the image

        Intent galleryIntent =new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);

    }

    private void checkAndRequestedForPermission() {

        if(ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){

                Toast.makeText(RegisterActivity.this,"Please accept forreuired permission",Toast.LENGTH_SHORT).show();

            }
            else
            {
                ActivityCompat.requestPermissions(RegisterActivity.this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE },
                        PRegCode);
            }
        }
        else
        {
            openGallery();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null) {

            //the user has successfully picked an image
            //we need to save its reference to a Uri variable

            pickedImaUri =data.getData();
            userphoto.setImageURI(pickedImaUri);
        }
    }

}