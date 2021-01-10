package com.Complete.quizprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private ProgressBar process;
    int counter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        process = findViewById(R.id.loadingbar);





        new CountDownTimer(1380, 10){
            public void onTick(long millisUntilFinished){
                process.setProgress(counter);
                counter++;
            }
            public  void onFinish(){
                SignUp signUp = new SignUp();
                SharedPreferences sharedPreferences = getSharedPreferences(signUp.USER_INFO,MODE_PRIVATE);
                boolean log =sharedPreferences.getBoolean(signUp.IN_OUT,false);
                if( log == false){
                    Intent intent = new Intent(MainActivity.this, SignIn.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(MainActivity.this, Quiz.class);
                    startActivity(intent);
                    finish();
                }

            }
        }.start();
    }
}