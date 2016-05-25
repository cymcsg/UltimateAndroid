package com.marshalchen.common.demoofui.appintroexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.demoofui.appintroexample.animations.*;

public class AppintroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_inrto_activity_main);
    }

    public void startDefaultIntro(View v){
        Intent intent = new Intent(this, DefaultIntro.class);
        startActivity(intent);
    }

    public void startCustomIntro(View v){
        Intent intent = new Intent(this, CustomIntro.class);
        startActivity(intent);
    }

    public void startSecondLayoutIntro(View v){
        Intent intent = new Intent(this, SecondLayoutIntro.class);
        startActivity(intent);
    }

    public void startFadeAnimation(View v){
        Intent intent = new Intent(this, FadeAnimation.class);
        startActivity(intent);
    }

    public void startCustomAnimation(View v){
        Intent intent = new Intent(this, CustomAnimation.class);
        startActivity(intent);
    }
}