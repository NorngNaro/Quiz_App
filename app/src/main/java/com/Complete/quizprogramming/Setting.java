package com.Complete.quizprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.Complete.quizprogramming.databinding.ActivitySettingBinding;

public class Setting extends AppCompatActivity {

    ActivitySettingBinding binding;
    SharePrefer sharePrefer = new SharePrefer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        final SharedPreferences sharedPreferences = getSharedPreferences(sharePrefer.USER_INFO, MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        show_sound();
        show_vibrate();
        show_music();


        binding.switchSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.e("TEST", "onCheckedChanged: b"+b );
                if (b) {
                    btn_click();
                    editor.putBoolean(sharePrefer.SOUND,true);
                    editor.apply();
                } else {
                    btn_click();
                    editor.putBoolean(sharePrefer.SOUND,false);
                    editor.apply();
                }
            }
        });

        binding.switchMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.e("TEST", "onCheckedChanged: b"+b );
                if (b) {
                    btn_click();
                    editor.putBoolean(sharePrefer.MUSIC,true);
                    editor.apply();
                } else {
                    btn_click();
                    editor.putBoolean(sharePrefer.MUSIC,false);
                    editor.apply();
                }
            }
        });

        binding.switchVibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.e("TEST", "onCheckedChanged: b"+b );
                if (b) {
                    btn_click();
                    editor.putBoolean(sharePrefer.VIBRATE,true);
                    editor.apply();
                } else {
                    btn_click();
                    editor.putBoolean(sharePrefer.VIBRATE,false);
                    editor.apply();
                }
            }
        });



        binding.backSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_click();
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

    private void show_sound(){
        SharedPreferences sharedPreferences = getSharedPreferences(sharePrefer.USER_INFO, MODE_PRIVATE);
        boolean set =sharedPreferences.getBoolean(sharePrefer.SOUND,false);
        if(set){
            binding.switchSound.setChecked(true);
        }else {
            binding.switchSound.setChecked(false);
        }
    }
    private void show_music(){
        SharedPreferences sharedPreferences = getSharedPreferences(sharePrefer.USER_INFO, MODE_PRIVATE);
        boolean set =sharedPreferences.getBoolean(sharePrefer.MUSIC,false);
        if(set){
            binding.switchMusic.setChecked(true);
        }else {
            binding.switchMusic.setChecked(false);
        }
    }
    private void show_vibrate(){
        SharedPreferences sharedPreferences = getSharedPreferences(sharePrefer.USER_INFO, MODE_PRIVATE);
        boolean set =sharedPreferences.getBoolean(sharePrefer.VIBRATE,false);
        if(set){
            binding.switchVibrate.setChecked(true);
        }else {
            binding.switchVibrate.setChecked(false);
        }
    }


    public void btn_click(){
        SharedPreferences sharedPreferences = getSharedPreferences(sharePrefer.USER_INFO, MODE_PRIVATE);
        boolean hear =sharedPreferences.getBoolean(sharePrefer.SOUND,false);
        if(hear){
            MediaPlayer ring= MediaPlayer.create(this,R.raw.clickbtn);
            ring.start();
        }

    }


}