package com.Complete.quizprogramming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private CardView c_Program;
    private CardView c_plus_program;
    private CardView java_Program;
    private CardView other_program;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        c_Program = findViewById(R.id.btn_C_program);
        other_program = findViewById(R.id.btn_Other_program);
        c_plus_program = findViewById(R.id.btn_C_plus_program);
        java_Program = findViewById(R.id.btn_Java_program);




        other_program.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this, "Can't use it now! Wait until next Version", Toast.LENGTH_LONG).show();
            }
        });
        c_Program.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn();
                Intent intent = new Intent(Home.this, Level.class);
                intent.putExtra("click", "c_program");
                startActivity(intent);


            }
        });
        c_plus_program.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn();
                Intent intent = new Intent(Home.this, Level.class);
                intent.putExtra("click", "c_plus_program" );
                startActivity(intent);
            }
        });
        java_Program.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn();
                Intent intent = new Intent(Home.this, Level.class);
                intent.putExtra("click",  "java_program" );
                startActivity(intent);
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.log_out) {
            drawerLayout.closeDrawers();
            // for insert login to false
            SharePrefer sharePrefer = new SharePrefer();
            SharedPreferences sharedPreferences = getSharedPreferences(sharePrefer.USER_INFO, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("in_out",false);
            editor.apply();
            Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show();
            // Open screen sign in
            Intent intent = new Intent(this,SignIn.class);
            startActivity(intent);
            finish();
        }else if(id==R.id.setting){
            btn();
            drawerLayout.closeDrawers();
            Intent intent = new Intent(this,Setting.class);
            startActivity(intent);
        }else if(id==R.id.profile){
            btn();
            drawerLayout.closeDrawers();
            Intent intent = new Intent(Home.this,Profile.class);
            startActivity(intent);
        }else if(id==R.id.about){
            btn();
            drawerLayout.closeDrawers();
            Intent intent = new Intent(Home.this,About.class);
            startActivity(intent);
        }else if(id==R.id.best_player){
            btn();
            drawerLayout.closeDrawers();
            Intent intent = new Intent(Home.this,BestPlayer.class);
            startActivity(intent);
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        exit();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


    private void exit(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit")
                .setMessage("Do you want to exit?")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // TODO
                    }
                });
                 AlertDialog alertDialog = builder.create();
                 alertDialog.show();
    }

    private void btn(){
        SharePrefer sharePrefer = new SharePrefer();
        SharedPreferences sharedPreferences = getSharedPreferences(sharePrefer.USER_INFO, MODE_PRIVATE);
        boolean hear =sharedPreferences.getBoolean(sharePrefer.SOUND,false);
        if(hear){
            MediaPlayer ring= MediaPlayer.create(this,R.raw.clickbtn);
            ring.start();
        }
    }
}

