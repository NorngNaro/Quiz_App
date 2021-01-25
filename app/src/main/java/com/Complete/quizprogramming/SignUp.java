package com.Complete.quizprogramming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {


    private EditText txt_username;
    private EditText txt_password;
    private EditText txt_re_password;
    private Button login;
    private ImageButton hide1;
    private ImageButton hide2;
    private Boolean isPressed = false;
    private Boolean isPressed_repass = false;
    SharePrefer sharePrefer = new SharePrefer();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("user");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // init view

        txt_username = (EditText) findViewById(R.id.username_login);
        txt_password = (EditText) findViewById(R.id.password_login);
        txt_re_password = (EditText) findViewById(R.id.re_password_login);
        login = (Button) findViewById(R.id.btn_signup);
        hide1 = (ImageButton) findViewById(R.id.hide1);
        hide2 = (ImageButton) findViewById((R.id.hide2));


        // Call method

        hide_password();
        hide_repassword();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // For check internet connection
                ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
                if (netInfo == null){
                    Toast.makeText(SignUp.this, "Please check internet connection!", Toast.LENGTH_LONG).show();
                }

                // call sign in method
                sign();

            }
        });

    }

    // Method for save data user to share preference

    public void saveData() {

        SharedPreferences sharedPreferences = getSharedPreferences(sharePrefer.USER_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString( sharePrefer.USERNAME, txt_username.getText().toString());
        editor.putString( sharePrefer.PASSWORD, txt_password.getText().toString());
        editor.putBoolean(sharePrefer.IN_OUT,true);

        editor.apply();

        Toast.makeText(this, "Log in", Toast.LENGTH_SHORT).show();
    }

    // Method for sign up

    public void sign(){
        if(txt_username.getText().toString().length()==0){
            txt_username.requestFocus();
            txt_password.setError("Enter Username");
        }else if(txt_password.getText().toString().length() != txt_re_password.getText().toString().length()){
            txt_password.requestFocus();
            txt_re_password.requestFocus();
            txt_password.setError("Password not match");
            txt_re_password.setError("Password not match");
            Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show();
        }else if (txt_password.getText().toString().length() < 4){
            txt_password.requestFocus();
            Toast.makeText(this, "Password 4 Digits", Toast.LENGTH_SHORT).show();
        }
        else if(txt_username.getText().toString().length() != 0) {
            if (txt_password.getText().toString().equals(txt_re_password.getText().toString())) {


                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {

                            String user_name = zoneSnapshot.child("username").getValue(String.class);

                            if (txt_username.getText().toString().equals(user_name)) {
                                Log.e("test", "onDataChange: have something wrong " );
                                Toast.makeText(SignUp.this, "This username already used!", Toast.LENGTH_SHORT).show();
                                break;
                            }else{

                                String ID = "id_" + txt_username.getText().toString();

                                ref.child(ID).child("username").setValue(txt_username.getText().toString());
                                ref.child(ID).child("password").setValue(txt_password.getText().toString());
                                ref.child(ID).child("correctAns").setValue("0");
                                ref.child(ID).child("incorrectAns").setValue("0");
                                ref.child(ID).child("totalQuiz").setValue("0");
                                ref.child(ID).child("totalScore").setValue("0");

                                // For check quiz progress
                                ref.child(ID).child("program").child("c_program").child("level1").child("completeQuiz").setValue("0");
                                ref.child(ID).child("program").child("c_program").child("level1").child("correctQuiz").setValue("0");
                                ref.child(ID).child("program").child("c_program").child("level1").child("levelComplete").setValue("false");
                                ref.child(ID).child("program").child("c_plus_program").child("level1").child("completeQuiz").setValue("0");
                                ref.child(ID).child("program").child("c_plus_program").child("level1").child("correctQuiz").setValue("0");
                                ref.child(ID).child("program").child("c_plus_program").child("level1").child("levelComplete").setValue("false");
                                ref.child(ID).child("program").child("java_program").child("level1").child("completeQuiz").setValue("0");
                                ref.child(ID).child("program").child("java_program").child("level1").child("correctQuiz").setValue("0");
                                ref.child(ID).child("program").child("java_program").child("level1").child("levelComplete").setValue("false");

                                // For check complete level
                                ref.child(ID).child("program").child("c_program").child("complete_level").setValue("0");
                                ref.child(ID).child("program").child("c_plus_program").child("complete_level").setValue("0");
                                ref.child(ID).child("program").child("java_program").child("complete_level").setValue("0");

                                saveData();
                                Intent i = new Intent(SignUp.this, Home.class);
                                startActivity(i);

                                Toast.makeText(SignUp.this, "Log in", Toast.LENGTH_SHORT).show();

                                finish();
                            }

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(SignUp.this, "Have something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        }
    }


    // Method for hide text type password

    private void hide_password(){
        hide1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isPressed) {
                    hide1.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    txt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    hide1.setImageResource(R.drawable.ic_visibility_black_24dp);
                    txt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                isPressed = !isPressed;
            }

        });
    }

    // Method for hide text retype password

    private void hide_repassword(){
        hide2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isPressed_repass) {
                    hide2.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    txt_re_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    hide2.setImageResource(R.drawable.ic_visibility_black_24dp);
                    txt_re_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                isPressed_repass = !isPressed_repass;
            }

        });
    }


}