package com.Complete.quizprogramming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.Complete.quizprogramming.databinding.ActivityQuizBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Quiz extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    ActivityQuizBinding binding;
    private int btn_color,correct,score,incorrect,quiz_range,totalQuiz,correctQuiz,com_level ;
    private String right_ans,level, program , id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        // For check internet connection
        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
            Toast.makeText(this, "Please check internet connection!", Toast.LENGTH_LONG).show();
        }


        binding.progressBar.setVisibility(View.VISIBLE);

        // Get username from cache to find id
        SignUp signUp = new SignUp();
        SharedPreferences sharedPreferences = getSharedPreferences(signUp.USER_INFO, MODE_PRIVATE);
        id = "id_" + sharedPreferences.getString(signUp.USERNAME, null);

        // set color
        btn_color = getColor(R.color.BasicColor);
        set_color();


        // receive data from level screen by put String
        Intent intent = getIntent();
        program = intent.getStringExtra("program");
        level = intent.getStringExtra("level_click");


        quiz_level();
        quiz();


        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnNext_click();
            }
        });

        binding.backQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                block_btnAns();
                if (binding.txtAns1.getText().equals(right_ans)) {
                    binding.answer1.setCardBackgroundColor(getColor(R.color.true_color));
                    sum_Score();
                } else {
                    binding.answer1.setCardBackgroundColor(getColor(R.color.wrong_color));
                    sum_incorrect();
                }
                check_ans();
            }
        });

        binding.answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                block_btnAns();
                if (binding.txtAns2.getText().equals(right_ans)) {
                    binding.answer2.setCardBackgroundColor(getColor(R.color.true_color));
                    sum_Score();
                } else {
                    binding.answer2.setCardBackgroundColor(getColor(R.color.wrong_color));
                    sum_incorrect();
                }
                check_ans();
            }
        });

        binding.answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                block_btnAns();
                if (binding.txtAns3.getText().equals(right_ans)) {
                    binding.answer3.setCardBackgroundColor(getColor(R.color.true_color));
                    sum_Score();
                } else {
                    binding.answer3.setCardBackgroundColor(getColor(R.color.wrong_color));
                    sum_incorrect();
                }
                check_ans();
            }
        });

        binding.answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                block_btnAns();
                if (binding.txtAns4.getText().equals(right_ans)) {
                    binding.answer4.setCardBackgroundColor(getColor(R.color.true_color));
                    sum_Score();
                } else {
                    binding.answer4.setCardBackgroundColor(getColor(R.color.wrong_color));
                    sum_incorrect();
                }
                check_ans();
            }
        });


    }

    // set color to btnAns
    private void set_color(){
        binding.answer1.setCardBackgroundColor(btn_color);
        binding.answer2.setCardBackgroundColor(btn_color);
        binding.answer3.setCardBackgroundColor(btn_color);
        binding.answer4.setCardBackgroundColor(btn_color);
    }

    // for block btn Answers
    private void block_btnAns(){
        sum_quiz();
        binding.btnNext.setVisibility(View.VISIBLE);
        binding.answer1.setEnabled(false);
        binding.answer2.setEnabled(false);
        binding.answer3.setEnabled(false);
        binding.answer4.setEnabled(false);
    }
    // for block btn Answers
    private void unblock_btnAns(){
        binding.btnNext.setVisibility(View.GONE);
        binding.answer1.setEnabled(true);
        binding.answer2.setEnabled(true);
        binding.answer3.setEnabled(true);
        binding.answer4.setEnabled(true);
    }

    // show btn ans
    private void show_btnAns(){
        binding.answer1.setVisibility(View.VISIBLE);
        binding.answer2.setVisibility(View.VISIBLE);
        binding.answer3.setVisibility(View.VISIBLE);
        binding.answer4.setVisibility(View.VISIBLE);
    }

    // btn next click
    private void btnNext_click() {
        // if quiz range = 10 level complete
        if(quiz_range == 10){
            // Show dialog next level
            final Dialog dialog = new Dialog(Quiz.this);
            dialog.setContentView(R.layout.complete_level);
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            CardView btnNext_level = dialog.findViewById(R.id.btn_nextLevel);
            btnNext_level.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sum_quiz();
                    next_level();
                    dialog.dismiss();
                    // Delay time for back screen query data
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           finish();
                        }
                    }, 300);
                }
            });

        }else {
            binding.progressBar.setVisibility(View.VISIBLE);
            sum_total_quiz();
            quiz_level();
            quiz();
            unblock_btnAns();
            show_btnAns();
            set_color();
            binding.btnNext.setVisibility(View.GONE);
        }

    }


    // method for btn next level click
    private void next_level(){

        final int num_level = 1 + Integer.parseInt(String.valueOf(level.charAt(level.length()-1)));
        final String complete_level = String.valueOf(com_level + 1);

        final DatabaseReference ref = database.getReference("user").child(id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ref.child("program").child(program).child("level"+num_level).child("completeQuiz").setValue("0");
                ref.child("program").child(program).child("level"+num_level).child("correctQuiz").setValue("0");
                ref.child("program").child(program).child("complete_level").setValue(complete_level);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Quiz.this, "Have something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });


    }


    // For checking answer that user have click
    private void check_ans() {
        if (binding.txtAns1.getText().equals(right_ans)) {
            binding.answer1.setCardBackgroundColor(getColor(R.color.true_color));
            block_btn();

        } else if (binding.txtAns2.getText().equals(right_ans)) {
            binding.answer2.setCardBackgroundColor(getColor(R.color.true_color));
            block_btn();

        } else if (binding.txtAns3.getText().equals(right_ans)) {
            binding.answer3.setCardBackgroundColor(getColor(R.color.true_color));
            block_btn();

        } else if (binding.txtAns4.getText().equals(right_ans)) {
            binding.answer4.setCardBackgroundColor(getColor(R.color.true_color));
            block_btn();

        }

    }

    // For invisible btn
    private void block_btn() {

        if (binding.answer1.getCardBackgroundColor().getDefaultColor() == getColor(R.color.BasicColor)) {
            binding.answer1.setVisibility(View.GONE);
        }
        if (binding.answer2.getCardBackgroundColor().getDefaultColor() == getColor(R.color.BasicColor)) {
            binding.answer2.setVisibility(View.GONE);
        }
        if (binding.answer3.getCardBackgroundColor().getDefaultColor() == getColor(R.color.BasicColor)) {
            binding.answer3.setVisibility(View.GONE);
        }
        if (binding.answer4.getCardBackgroundColor().getDefaultColor() == getColor(R.color.BasicColor)) {
            binding.answer4.setVisibility(View.GONE);
        }
    }


    // get complete quiz from database
    private void quiz_level() {

        Query ref = database.getReference("user").child(id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                quiz_range = Integer.parseInt(dataSnapshot.child("program").child(program).child(level).child("completeQuiz").getValue(String.class)) + 1;
                correctQuiz = Integer.parseInt(dataSnapshot.child("program").child(program).child(level).child("correctQuiz").getValue(String.class));
                binding.txtCountQuiz.setText(quiz_range + "/10");
                score = Integer.parseInt(dataSnapshot.child("totalScore").getValue(String.class));
                binding.txtTime.setText("Score: " + score);
                incorrect = Integer.parseInt(dataSnapshot.child("incorrectAns").getValue(String.class));
                correct = Integer.parseInt(dataSnapshot.child("correctAns").getValue(String.class));
                totalQuiz = Integer.parseInt(dataSnapshot.child("totalQuiz").getValue(String.class));
                com_level = Integer.parseInt(dataSnapshot.child("program").child(program).child("complete_level").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Quiz.this, "Have something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // For sum Score
    private void sum_Score() {
        score ++;
        sum_correct();
        binding.txtTime.setText("Score: "+score);
        String addScore = String.valueOf(score);
        DatabaseReference ref = database.getReference("user").child(id);
        ref.child("totalScore").setValue(addScore);
    }

    // For sum quiz
    private void sum_quiz() {
        String addQuiz = String.valueOf(quiz_range);
        binding.txtCountQuiz.setText(quiz_range+"/10");
        DatabaseReference ref = database.getReference("user").child(id).child("program").child(program);
        ref.child(level).child("completeQuiz").setValue(addQuiz);
    }

    // For sum incorrect
    private void sum_incorrect() {
        sum_correctLevel();
        incorrect++;
        String add_incorrect = String.valueOf(incorrect);
        DatabaseReference ref = database.getReference("user").child(id);
        ref.child("incorrectAns").setValue(add_incorrect);
    }

    // For sum correct
    private void sum_correct() {
        correct++;
        String add_correct = String.valueOf(correct);
        DatabaseReference ref = database.getReference("user").child(id);
        ref.child("correctAns").setValue(add_correct);
    }

    // For sum total Quiz
    private void sum_total_quiz() {
        totalQuiz++;
        String add_totalQuiz = String.valueOf(totalQuiz);
        DatabaseReference ref = database.getReference("user").child(id);
        ref.child("totalQuiz").setValue(add_totalQuiz);
    }

    // For sum correct in a level
    private void sum_correctLevel() {
        correctQuiz++;
        String add_correctLevel = String.valueOf(correctQuiz);
        DatabaseReference ref = database.getReference("user").child(id).child("program").child(program);
        ref.child(level).child("correctQuiz").setValue(add_correctLevel);
    }

    // Get data from database and set to view
    private void quiz(){
        Query ref = database.getReference("all_quiz").child(program).child(level);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                binding.progressBar.setVisibility(View.GONE);
                right_ans = dataSnapshot.child("quiz"+quiz_range).child("right_ans").getValue(String.class);
                binding.txtQuiz.setText(dataSnapshot.child("quiz"+quiz_range).child("quiz").getValue(String.class));
                binding.txtAns1.setText(dataSnapshot.child("quiz"+quiz_range).child("wrong_ans1").getValue(String.class));
                binding.txtAns2.setText(dataSnapshot.child("quiz"+quiz_range).child("wrong_ans2").getValue(String.class));
                binding.txtAns3.setText(dataSnapshot.child("quiz"+quiz_range).child("wrong_ans3").getValue(String.class));
                binding.txtAns4.setText(dataSnapshot.child("quiz"+quiz_range).child("wrong_ans4").getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Quiz.this, "Have something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
}