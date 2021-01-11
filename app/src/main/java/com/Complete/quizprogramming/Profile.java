package com.Complete.quizprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    TextView username;
    TextView correctAns;
    TextView incorrectAns;
    TextView total_Quiz;
    TextView total_score;
    TextView c_level;
    TextView c_plus_level;
    TextView java_level;
    ImageButton back;
    String id;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // For check internet connection
        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
            Toast.makeText(this, "Please check internet connection!", Toast.LENGTH_LONG).show();
        }

        // init view
        username = findViewById(R.id.txtUsername);
        back = findViewById(R.id.back_profile);
        correctAns = findViewById(R.id.txt_correctAn);
        incorrectAns = findViewById(R.id.txt_incorrectAns);
        total_Quiz = findViewById(R.id.txt_total_question);
        total_score = findViewById(R.id.txt_total_Score);
        c_level = findViewById(R.id.cProgram_level);
        c_plus_level = findViewById(R.id.c_plusProgram_level);
        java_level = findViewById(R.id.java_level);

        // For get username save in cache
        SignUp signUp = new SignUp();
        SharedPreferences sharedPreferences = getSharedPreferences(signUp.USER_INFO,MODE_PRIVATE);
        username.setText(sharedPreferences.getString(signUp.USERNAME,null));
        id = "id_"+ username.getText().toString()  ;


        // For query data from database
        DatabaseReference ref = database.getReference("user").child(id);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    // Set text to view
                    correctAns.setText(dataSnapshot.child("score").child("correctAns").getValue(String.class));
                    incorrectAns.setText(dataSnapshot.child("score").child("incorrectAns").getValue(String.class));
                    total_Quiz.setText(dataSnapshot.child("score").child("totalQuiz").getValue(String.class));
                    total_score.setText(dataSnapshot.child("score").child("totalScore").getValue(String.class));
                    c_level.setText(dataSnapshot.child("level").child("c_program").getValue(String.class));
                    c_plus_level.setText(dataSnapshot.child("level").child("c_plus_program").getValue(String.class));
                    java_level.setText(dataSnapshot.child("level").child("java_program").getValue(String.class));

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

                  }
            });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}