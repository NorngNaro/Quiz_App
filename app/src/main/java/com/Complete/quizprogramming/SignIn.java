package com.Complete.quizprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    private Button btnlogin;
    private EditText username;
    private EditText password;
    private TextView create_account;
    private ImageButton hidepassword;
    private Boolean isPressed = false;
    private String pass_word="";
    private String user_name="";
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // init view
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btnlogin = (Button) findViewById(R.id.btn_login);
        hidepassword = (ImageButton) findViewById(R.id.hide);
        create_account = (TextView) findViewById(R.id.create);


        // call method
        hide_password();
        sigup();


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                internet();

                // For query data from database
                DatabaseReference ref = database.getReference("user");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // For check login or logout using share Preference
                        SignUp signUp = new SignUp();
                        SharedPreferences sharedPreferences = getSharedPreferences(signUp.USER_INFO,MODE_PRIVATE);
                        if(password.getText().toString().length()==0){
                            if(username.getText().toString().length()==0){
                                username.setError("Enter Username");
                                username.requestFocus();
                            }
                            password.setError("Enter Password");
                            password.requestFocus();
                        } else {
                            for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {

                                user_name = zoneSnapshot.child("username").getValue(String.class);
                                pass_word = zoneSnapshot.child("password").getValue(String.class);


                                if (username.getText().toString().equals(user_name)) {
                                    break;
                                }
                            }
                            if (password.getText().toString().equals(pass_word)) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString( "username", username.getText().toString());
                                editor.putString( "password", password.getText().toString());
                                editor.putBoolean("in_out",true);
                                editor.apply();

                                Intent i = new Intent(SignIn.this , Home.class);
                                startActivity(i);

                                Toast.makeText(SignIn.this, "Log in", Toast.LENGTH_SHORT).show();
                                
                                finish();
                            } else {
                                password.setError("Wrong Password");
                                password.requestFocus();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(SignIn.this, "Have something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    // Method for sign up
    private void sigup(){
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignIn.this , SignUp.class);
                startActivity(i);
                finish();
            }
        });
    }



    // method for hide password
    private void hide_password(){
        hidepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPressed) {
                    hidepassword.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    hidepassword.setImageResource(R.drawable.ic_visibility_black_24dp);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                isPressed = !isPressed;
            }

        });
    }


    private void internet(){

        // For check internet connection
        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
            Toast.makeText(this, "Please check internet connection!", Toast.LENGTH_LONG).show();
        }
    }


}