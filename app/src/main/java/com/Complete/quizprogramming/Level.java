package com.Complete.quizprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.Complete.quizprogramming.databinding.ActivityLevelBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Level extends AppCompatActivity {

    private String program_click;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ActivityLevelBinding binding;
    public String click;
    int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLevelBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // init btn color
        color = getColor(R.color.colorPrimaryDark);


        // Visible loading
        binding.progressBar.setVisibility(View.VISIBLE);

        // For check internet connection
        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
            Toast.makeText(this, "Please check internet connection!", Toast.LENGTH_LONG).show();
        }

        block_level();

        // Action for back button
        binding.backLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // btn play level 1 is click
        binding.btnLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Level.this , Quiz.class);
                intent.putExtra("level_click", "level1" );
                intent.putExtra("program", program_click);
                startActivity(intent);

            }
        });

        // btn play level 2 is click
        binding.btnLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Level.this , Quiz.class);
                intent.putExtra("level_click", "level2" );
                intent.putExtra("program", program_click);
                startActivity(intent);

            }
        });

        // btn play level 3 is click
        binding.btnLevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Level.this , Quiz.class);
                intent.putExtra("level_click", "level3" );
                intent.putExtra("program", program_click);
                startActivity(intent);

            }
        });

        // btn play level 4 is click
        binding.btnLevel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Level.this , Quiz.class);
                intent.putExtra("level_click", "level4" );
                intent.putExtra("program", program_click);
                startActivity(intent);

            }
        });

        // btn play level 5 is click
        binding.btnLevel5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Level.this , Quiz.class);
                intent.putExtra("level_click", "level5" );
                intent.putExtra("program", program_click);
                startActivity(intent);

            }
        });

        // btn play level 6 is click
        binding.btnLevel6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Level.this , Quiz.class);
                intent.putExtra("level_click", "level6" );
                intent.putExtra("program", program_click);
                startActivity(intent);

            }
        });

        // btn play level 7 is click
        binding.btnLevel7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Level.this , Quiz.class);
                intent.putExtra("level_click", "level7" );
                intent.putExtra("program", program_click);
                startActivity(intent);

            }
        });

        // btn play level 8 is click
        binding.btnLevel8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Level.this , Quiz.class);
                intent.putExtra("level_click", "level8" );
                intent.putExtra("program", program_click);
                startActivity(intent);

            }
        });

        // btn play level 9 is click
        binding.btnLevel9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Level.this , Quiz.class);
                intent.putExtra("level_click", "level9" );
                intent.putExtra("program", program_click);
                startActivity(intent);

            }
        });

        // btn play level 10 is click
        binding.btnLevel10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Level.this , Quiz.class);
                intent.putExtra("level_click", "level10" );
                intent.putExtra("program", program_click);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() {
        // Visible loading
        binding.progressBar.setVisibility(View.VISIBLE);

        block_level();
        super.onResume();
    }

    private void block_level(){

        // Get username from cache to find id
        SignUp signUp = new SignUp();
        SharedPreferences sharedPreferences = getSharedPreferences(signUp.USER_INFO,MODE_PRIVATE);
        String id = "id_"+ sharedPreferences.getString(signUp.USERNAME,null) ;

        // Query to database
        DatabaseReference ref = database.getReference("user").child(id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                binding.progressBar.setVisibility(View.GONE);

                // for check what is user click
                program_click =  getIntent().getSerializableExtra("click").toString();

                // Get value of level that have completed
                int block_level = Integer.parseInt(dataSnapshot.child("program").child(program_click).child("complete_level").getValue(String.class));
                // Loop for lock level
                for( int i = 1 ; i <= block_level + 1 ; i++ ){

                    int progress = Integer.parseInt(dataSnapshot.child("program").child(program_click).child("level"+i).child("completeQuiz").getValue(String.class));

                    if(i==1){
                        binding.imageLevel1.setImageResource(R.drawable.ic_baseline_lock_open_24);
                        binding.btnLevel1.setEnabled(true);
                        binding.btnLevel1.setBackgroundColor(color);
                        if(progress==0){
                            binding.txtLock1.setText(R.string.unlocked);
                        }else if(progress == 10){
                            binding.txtLock1.setText("Result: "+result(dataSnapshot , i , progress));
                            binding.btnLevel1.setText("Retry");
                        } else{
                            binding.txtLock1.setText("Completed: "+progress +"/10");
                            binding.btnLevel1.setText(R.string.txt_continue);
                        }

                    }else if(i==2){
                        binding.imageLevel2.setImageResource(R.drawable.ic_baseline_lock_open_24);
                        binding.btnLevel2.setEnabled(true);
                        binding.btnLevel2.setBackgroundColor(color);
                        if(progress==0){
                            binding.txtLock2.setText(R.string.unlocked);
                        }else if(progress == 10){
                            binding.txtLock1.setText("Result: "+result(dataSnapshot , i , progress));
                            binding.btnLevel1.setText("Retry");
                        } else{
                            binding.txtLock2.setText("Completed: "+progress +"/10");
                            binding.btnLevel2.setText(R.string.txt_continue);
                        }
                    }else if(i==3){
                        binding.imageLevel3.setImageResource(R.drawable.ic_baseline_lock_open_24);
                        binding.btnLevel3.setEnabled(true);
                        binding.btnLevel3.setBackgroundColor(color);
                        if(progress==0){
                            binding.txtLock3.setText(R.string.unlocked);
                        }else if(progress == 10){
                            binding.txtLock1.setText("Result: "+result(dataSnapshot , i , progress));
                            binding.btnLevel1.setText("Retry");
                        } else{
                            binding.txtLock3.setText("Completed: "+progress +"/10");
                            binding.btnLevel3.setText(R.string.txt_continue);
                        }
                    }else if(i==4){
                        binding.imageLevel4.setImageResource(R.drawable.ic_baseline_lock_open_24);
                        binding.btnLevel4.setEnabled(true);
                        binding.btnLevel4.setBackgroundColor(color);
                        if(progress==0){
                            binding.txtLock4.setText(R.string.unlocked);
                        }else if(progress == 10){
                            binding.txtLock1.setText("Result: "+result(dataSnapshot , i , progress));
                            binding.btnLevel1.setText("Retry");
                        } else{
                            binding.txtLock4.setText("Completed: "+progress +"/10");
                            binding.btnLevel4.setText(R.string.txt_continue);
                        }
                    }else if(i==5){
                        binding.imageLevel5.setImageResource(R.drawable.ic_baseline_lock_open_24);
                        binding.btnLevel5.setEnabled(true);
                        binding.btnLevel5.setBackgroundColor(color);
                        if(progress==0){
                            binding.txtLock5.setText(R.string.unlocked);
                        }else if(progress == 10){
                            binding.txtLock1.setText("Result: "+result(dataSnapshot , i , progress));
                            binding.btnLevel1.setText("Retry");
                        } else{
                            binding.txtLock5.setText("Completed: "+progress +"/10");
                            binding.btnLevel5.setText(R.string.txt_continue);
                        }
                    }else if(i==6){
                        binding.imageLevel6.setImageResource(R.drawable.ic_baseline_lock_open_24);
                        binding.btnLevel6.setEnabled(true);
                        binding.btnLevel6.setBackgroundColor(color);
                        if(progress==0){
                            binding.txtLock6.setText(R.string.unlocked);
                        }else if(progress == 10){
                            binding.txtLock1.setText("Result: "+result(dataSnapshot , i , progress));
                            binding.btnLevel1.setText("Retry");
                        } else{
                            binding.txtLock6.setText("Completed: "+progress +"/10");
                            binding.btnLevel6.setText(R.string.txt_continue);
                        }
                    }else if(i==7){
                        binding.imageLevel7.setImageResource(R.drawable.ic_baseline_lock_open_24);
                        binding.btnLevel7.setEnabled(true);
                        binding.btnLevel7.setBackgroundColor(color);
                        if(progress==0){
                            binding.txtLock7.setText(R.string.unlocked);
                        }else if(progress == 10){
                            binding.txtLock1.setText("Result: "+result(dataSnapshot , i , progress));
                            binding.btnLevel1.setText("Retry");
                        } else{
                            binding.txtLock7.setText("Completed: "+progress +"/10");
                            binding.btnLevel7.setText(R.string.txt_continue);
                        }
                    }else if(i==8){
                        binding.imageLevel8.setImageResource(R.drawable.ic_baseline_lock_open_24);
                        binding.btnLevel8.setEnabled(true);
                        binding.btnLevel8.setBackgroundColor(color);
                        if(progress==0){
                            binding.txtLock8.setText(R.string.unlocked);
                        }else if(progress == 10){
                            binding.txtLock1.setText("Result: "+result(dataSnapshot , i , progress));
                            binding.btnLevel1.setText("Retry");
                        } else{
                            binding.txtLock8.setText("Completed: "+progress +"/10");
                            binding.btnLevel8.setText(R.string.txt_continue);
                        }
                    }else if(i==9){
                        binding.imageLevel9.setImageResource(R.drawable.ic_baseline_lock_open_24);
                        binding.btnLevel9.setEnabled(true);
                        binding.btnLevel9.setBackgroundColor(color);
                        if(progress==0){
                            binding.txtLock9.setText(R.string.unlocked);
                        }else if(progress == 10){
                            binding.txtLock1.setText("Result: "+result(dataSnapshot , i , progress));
                            binding.btnLevel1.setText("Retry");
                        } else{
                            binding.txtLock9.setText("Completed: "+progress +"/10");
                            binding.btnLevel9.setText(R.string.txt_continue);
                        }
                    }else if(i==10){
                        binding.imageLevel10.setImageResource(R.drawable.ic_baseline_lock_open_24);
                        binding.btnLevel10.setEnabled(true);
                        binding.btnLevel10.setBackgroundColor(color);
                        if(progress==0){
                            binding.txtLock10.setText(R.string.unlocked);
                        }else if(progress == 10){
                            binding.txtLock1.setText("Result: "+result(dataSnapshot , i , progress));
                            binding.btnLevel1.setText("Retry");
                        } else{
                            binding.txtLock10.setText("Completed: "+progress +"/10");
                            binding.btnLevel10.setText(R.string.txt_continue);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Level.this, "Have something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int result(DataSnapshot dataSnapshot , int i , int progress){
        int result = Integer.parseInt(dataSnapshot.child("program").child(program_click).child("level"+i).child("correctQuiz").getValue(String.class))/
                progress;
        return result;
    }


    }
