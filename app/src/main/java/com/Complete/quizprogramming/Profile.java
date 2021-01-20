package com.Complete.quizprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Complete.quizprogramming.databinding.ActivityLevelBinding;
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
        SignUp signUp = new SignUp();
        SharedPreferences sharedPreferences = getSharedPreferences(signUp.USER_INFO,MODE_PRIVATE);
        binding.txtUsername.setText(sharedPreferences.getString(signUp.USERNAME,null));

        id = "id_"+ binding.txtUsername.getText().toString()  ;


        // For query data from database
        DatabaseReference ref = database.getReference("user").child(id);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                binding.progressBar.setVisibility(View.GONE);

                    // Set text to view
                    binding.txtCorrectAn.setText(dataSnapshot.child("correctAns").getValue(String.class));
                    binding.txtIncorrectAns.setText(dataSnapshot.child("incorrectAns").getValue(String.class));
                    binding.txtTotalQuestion.setText(dataSnapshot.child("totalQuiz").getValue(String.class));
                    binding.txtTotalScore.setText(dataSnapshot.child("totalScore").getValue(String.class));
                    binding.cProgramLevel.setText(dataSnapshot.child("program").child("c_program").child("complete_level").getValue(String.class));
                    binding.cPlusProgramLevel.setText(dataSnapshot.child("program").child("c_plus_program").child("complete_level").getValue(String.class));
                    binding.javaLevel.setText(dataSnapshot.child("program").child("java_program").child("complete_level").getValue(String.class));

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        binding.backProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}