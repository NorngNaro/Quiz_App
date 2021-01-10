package com.Complete.quizprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    public final String USER_INFO = "user_info";
    public final String USERNAME = "username";
    public final String PASSWORD = "password";
    public final String IN_OUT = "in_out";


    private EditText txt_username;
    private EditText txt_password;
    private EditText txt_re_password;
    private Button login;
    private ImageButton hide1;
    private ImageButton hide2;
    private Boolean isPressed = false;
    private Boolean isPressed_repass = false;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

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
                sign();

            }
        });





    }




    // Method for save data user to share preference

    public void saveData() {

        SharedPreferences sharedPreferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString( USERNAME, txt_username.getText().toString());
        editor.putString( PASSWORD, txt_password.getText().toString());
        editor.putBoolean(IN_OUT,true);
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

                saveData();

                DatabaseReference ref = database.getReference("user");
                String randomID =ref.push().getKey();
                ref.child(randomID).child("username").setValue(txt_username.getText().toString());
                ref.child(randomID).child("password").setValue(txt_password.getText().toString());

                Intent i = new Intent(SignUp.this, Quiz.class);
                startActivity(i);

                Toast.makeText(this, "Log in", Toast.LENGTH_SHORT).show();

                finish();
            }
        }
    }


    // Method for hide text type password

    private void hide_password(){
        hide1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isPressed == true) {
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


                if (isPressed_repass == true) {
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