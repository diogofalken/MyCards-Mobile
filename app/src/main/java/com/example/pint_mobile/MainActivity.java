package com.example.pint_mobile;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static int time = 2000;
    AnimationDrawable animation_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //remover action bar
        getSupportActionBar().hide();

        ImageView loading = findViewById(R.id.imageloading);
        animation_loading = (AnimationDrawable)loading.getDrawable();
        animation_loading.start();

        //mudar de activity após 2s
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, Activity_login.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        }, time);
    }

}
