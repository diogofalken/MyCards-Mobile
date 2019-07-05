package com.example.pint_mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Activity_cartao_fidelizado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartao_fidelizado);

        //remover action bar
        getSupportActionBar().hide();
    }
}
