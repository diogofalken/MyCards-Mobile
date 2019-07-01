package com.example.pint_mobile;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Activity_registo extends AppCompatActivity {

    int fragment_number = 1;
    Fragment fragment = new Fragment_registo_informacoes();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);

        //remover action bar
        getSupportActionBar().hide();

        //voltar para a activity Activity_login
        final ImageView button = findViewById(R.id.voltar_login);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Activity_registo.this, Activity_login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height_screen = displaymetrics.heightPixels;
        int width_screen = displaymetrics.widthPixels;
        android.view.ViewGroup.LayoutParams layoutParams;
        final ImageButton next = findViewById(R.id.proximo);
        final ImageButton previous = findViewById(R.id.anterior);
        final Button inf = findViewById(R.id.Informações);
        final Button pref = findViewById(R.id.Preferências);
        final Button not = findViewById(R.id.Notificações);

        //ajustar tamanho dos botoes consoante o tamanho da tela (telemovel)
        //buttons avancar e recuar nos passos de Activity_registo
        layoutParams = next.getLayoutParams();
        layoutParams.width = retorna_width(width_screen, 0.5, 68);
        layoutParams.height = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
        next.setLayoutParams(layoutParams);

        layoutParams = previous.getLayoutParams();
        layoutParams.width = retorna_width(width_screen, 0.5, 68);
        layoutParams.height = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
        previous.setLayoutParams(layoutParams);

        //buttons a indicar a fase de Activity_registo (inf, pref e not)
        layoutParams = inf.getLayoutParams();
        layoutParams.width = retorna_width(width_screen, 0.33, 50);
        layoutParams.height = 60;
        inf.setLayoutParams(layoutParams);

        layoutParams = pref.getLayoutParams();
        layoutParams.width = retorna_width(width_screen, 0.33, 50);
        layoutParams.height = 60;
        pref.setLayoutParams(layoutParams);

        layoutParams = not.getLayoutParams();
        layoutParams.width = retorna_width(width_screen, 0.33, 50);
        layoutParams.height = 60;
        not.setLayoutParams(layoutParams);

        previous.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();

        //quando se clica no botao next
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            switch (fragment_number) {
                 case 1: //informaçoes
                     fragment = new Fragment_registo_preferencia();
                     getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                     previous.setVisibility(View.VISIBLE);
                     inf.setBackgroundResource(R.drawable.border_register_step3);
                     inf.setTextColor(getResources().getColor(R.color.corBranca));
                     inf.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                     inf.setTextSize(8);
                     pref.setBackgroundResource(R.drawable.border_register_step2);
                     pref.setTextColor(getResources().getColor(R.color.corBaseAzul));
                     pref.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                     pref.setTextSize(10);
                     fragment_number = 2;
                     break;

                 case 2: //preferencias
                     fragment = new Fragment_registo_notificacoes();
                     getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                     next.setImageResource(R.drawable.ic_confirmation_email);
                     pref.setBackgroundResource(R.drawable.border_register_step3);
                     pref.setTextColor(getResources().getColor(R.color.corBranca));
                     pref.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                     pref.setTextSize(8);
                     not.setBackgroundResource(R.drawable.border_register_step2);
                     not.setTextColor(getResources().getColor(R.color.corBaseAzul));
                     not.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                     not.setTextSize(10);
                     fragment_number = 3;
                     break;

                 case 3: //notificaçoes -> concluir Activity_registo
                     Intent intent = new Intent(Activity_registo.this, Activity_concluir_registo.class);
                     startActivity(intent);
                     overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                     break;
             }
            }
        });

        //quando se clica no botao previous
        previous.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (fragment_number) {
                    case 1: //informaçoes (nao existe botao)
                        break;

                    case 2: //preferencias
                        fragment = new Fragment_registo_informacoes();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                        previous.setVisibility(View.GONE);
                        pref.setBackgroundResource(R.drawable.border_register_step1);
                        pref.setTextColor(getResources().getColor(R.color.corTextButtonStep1));
                        pref.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        pref.setTextSize(8);
                        inf.setBackgroundResource(R.drawable.border_register_step2);
                        inf.setTextColor(getResources().getColor(R.color.corBaseAzul));
                        inf.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        inf.setTextSize(10);
                        fragment_number = 1;
                        break;

                    case 3: //notificaçoes -> concluir Activity_registo
                        fragment = new Fragment_registo_preferencia();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                        next.setImageResource(R.drawable.ic_next);
                        pref.setBackgroundResource(R.drawable.border_register_step2);
                        pref.setTextColor(getResources().getColor(R.color.corBaseAzul));
                        pref.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        pref.setTextSize(10);
                        not.setBackgroundResource(R.drawable.border_register_step1);
                        not.setTextColor(getResources().getColor(R.color.corTextButtonStep1));
                        not.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        not.setTextSize(8);
                        fragment_number = 2;
                        break;
                }
            }
        });
    }

    int retorna_width(int width_screen, double multiplicar, int subtrair){
        double x = width_screen;
        x *= multiplicar;
        x -= subtrair;
        int y = (int) x;
        return y;
    }



}
