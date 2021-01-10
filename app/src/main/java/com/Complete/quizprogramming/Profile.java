package com.Complete.quizprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    TextView username;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // init view

        username = findViewById(R.id.txtUsername);
        back = findViewById(R.id.back_profile);

        SignUp signUp = new SignUp();
        SharedPreferences sharedPreferences = getSharedPreferences(signUp.USER_INFO,MODE_PRIVATE);
       username.setText(sharedPreferences.getString(signUp.USERNAME,null));


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }
}