package com.Complete.quizprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private ProgressBar process;
    int counter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        process = findViewById(R.id.loadingbar);


        new CountDownTimer(1000, 5){
            public void onTick(long millisUntilFinished){
                process.setProgress(counter);
                counter++;
            }
            public  void onFinish(){
                process.setProgress(100);
                SharePrefer sharePrefer = new SharePrefer();
                SharedPreferences sharedPreferences = getSharedPreferences(sharePrefer.USER_INFO,MODE_PRIVATE);
                boolean log =sharedPreferences.getBoolean(sharePrefer.IN_OUT,true);
                if( log == false){
                    Intent intent = new Intent(MainActivity.this, SignIn.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    startActivity(intent);
                    finish();
                }

            }
        }.start();
    }
}