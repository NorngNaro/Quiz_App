package com.Complete.quizprogramming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.Complete.quizprogramming.databinding.ActivityBestPlayerBinding;
import com.Complete.quizprogramming.databinding.ActivityLevelBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BestPlayer extends AppCompatActivity {

    private ActivityBestPlayerBinding binding;
    private int i = 0;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBestPlayerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.progressBar.setVisibility(View.VISIBLE);

        binding.backPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // For query data from database
        Query ref = database.getReference("user").orderByChild("totalScore").limitToLast(4);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                binding.progressBar.setVisibility(View.GONE);

                for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {
                    String username = zoneSnapshot.child("username").getValue(String.class);
                    String score = zoneSnapshot.child("totalScore").getValue(String.class);
                    Log.e("score","" +i+zoneSnapshot.child("username").getValue(String.class) );

                    if(i==0){
                        binding.txtScoreUser4.setText(score);
                        binding.txtUser4.setText(username);
                    }else if(i==1){
                        binding.txtScoreUser3.setText(score);
                        binding.txtUser3.setText(username);
                    }else if(i==2){
                        binding.txtScoreUser2.setText(score);
                        binding.txtUser2.setText(username);
                    }else if(i==3) {
                        binding.txtScoreUser1.setText(score);
                        binding.txtUser1.setText(username);
                    }
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}