package com.Complete.quizprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.Complete.quizprogramming.databinding.ActivityProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    String id;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    ActivityProfileBinding binding;
    SharePrefer sharePrefer = new SharePrefer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        // For check internet connection
        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
            Toast.makeText(this, "Please check internet connection!", Toast.LENGTH_LONG).show();
        }

        // For get username save in cache
        SharePrefer sharePrefer = new SharePrefer();
        SharedPreferences sharedPreferences = getSharedPreferences(sharePrefer.USER_INFO,MODE_PRIVATE);
        binding.txtUsername.setText(sharedPreferences.getString(sharePrefer.USERNAME,null));

        id = "id_"+ binding.txtUsername.getText().toString()  ;


        // For query data from database
        DatabaseReference ref = database.getReference("user").child(id);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                binding.progressBar.setVisibility(View.GONE);


                    // Set text to view
                    binding.txtCorrectAn.setText(String.valueOf(dataSnapshot.child("correctAns").getValue(Long.class)));
                    binding.txtIncorrectAns.setText(String.valueOf(dataSnapshot.child("incorrectAns").getValue(Long.class)));
                    binding.txtTotalQuestion.setText(String.valueOf(dataSnapshot.child("totalQuiz").getValue(Long.class)));
                    binding.txtTotalScore.setText(String.valueOf(dataSnapshot.child("totalScore").getValue(Long.class)));
                    binding.cProgramLevel.setText(String.valueOf(dataSnapshot.child("program").child("c_program").child("complete_level").getValue(Long.class)));
                    binding.cPlusProgramLevel.setText(String.valueOf(dataSnapshot.child("program").child("c_plus_program").child("complete_level").getValue(Long.class)));
                    binding.javaLevel.setText(String.valueOf(dataSnapshot.child("program").child("java_program").child("complete_level").getValue(Long.class)));

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        binding.backProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 200);
            }
        });

    }

    private void btn(){
        SharedPreferences sharedPreferences = getSharedPreferences(sharePrefer.USER_INFO, MODE_PRIVATE);
        boolean hear =sharedPreferences.getBoolean(sharePrefer.SOUND,true);
        if(hear){
            MediaPlayer ring= MediaPlayer.create(this,R.raw.clickbtn);
            ring.start();
        }
    }
}