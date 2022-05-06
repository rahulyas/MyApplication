package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Fragment.HomeFragment;
import com.example.myapplication.Fragment.ProfileFragment;
import com.example.myapplication.Fragment.SettingFragment;
import com.example.myapplication.PhoneAuthentication.Profile;
import com.example.myapplication.databinding.ActivityHome2Binding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class
Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHome2Binding binding;

    FirebaseAuth mAuth;
    FirebaseUser currentUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHome2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarHome.toolbar);


        // ini

        mAuth =FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        NavigationView navigationView1 =(NavigationView) findViewById(R.id.nav_view);
        navigationView1.setNavigationItemSelectedListener(this);

        updateNavHeader();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if (id == R.id.home) {
            //handle the home action
            getSupportActionBar().setTitle("Home");
            getSupportFragmentManager().beginTransaction().replace(androidx.navigation.ui.R.id.container,new HomeFragment()).commit ();

            return true;

        }else if (id == R.id.profile){

            Intent profile=new Intent(Home.this, Profile.class);
            startActivity(profile);
            finish();
            return true;

        }else if (id == R.id.setting){

            getSupportActionBar().setTitle("Setting");
            getSupportFragmentManager().beginTransaction().replace(androidx.navigation.ui.R.id.container,new SettingFragment()).commit ();
            return true;

        }
        else if (id == R.id.signout){



            //FirebaseAuth.getInstance().signOut();
            Intent signout=new Intent(Home.this, LoginActivity.class);
            startActivity(signout);
            finish();
            return true;

        }
        DrawerLayout drawer= (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //Handle action bar item clicks here. The action bar will
        //automaticaly handle clicks on the Hoe/up button so long
        // as you specify a parent activity in Androidmanifest.xml
        int id = item.getItemId();

        
        if (id ==R.id.action_settings){
            return true;
        }

        //noinspection SimlifiableStatment
        return super.onOptionsItemSelected(item);
    }

   // @Override
    public void OnBackPressed(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    public void updateNavHeader(){
        NavigationView navigationView1 =(NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView1.getHeaderView(0);
        TextView navusername = headerView.findViewById(R.id.nav_user_name);
        TextView navusermail = headerView.findViewById(R.id.nav_user_email);
        ImageView navuserphoto = headerView.findViewById(R.id.nav_user_photo);

        // now we will use glide to load user image for that we need to import library

        Glide.with(this).load(currentUser.getPhotoUrl()).into(navuserphoto);

        navusername.setText(currentUser.getDisplayName());
        navusermail.setText(currentUser.getEmail());

    }
}