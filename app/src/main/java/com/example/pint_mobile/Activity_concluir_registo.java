package com.example.pint_mobile;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_concluir_registo extends AppCompatActivity {

    int cor;
    ImageView voltar;
    TextView aviso;
    CheckBox checkBox;
    ImageButton concluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concluir_registo);

        cor = getResources().getColor(R.color.corBaseAzul);
        voltar = findViewById(R.id.voltar_registo);
        aviso = findViewById(R.id.aviso);
        checkBox = findViewById(R.id.termos_condicoes);
        concluir = findViewById(R.id.concluir);

        //remover action bar
        getSupportActionBar().hide();

        //voltar para a activity Activity_registo
        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Activity_concluir_registo.this, Activity_registo.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        //colocar "CÓDIGO DE CONFIRMAÇÂO" a negrito e cor diferente
        String text = "Para concluir o resgisto, introduza o código de confirmação enviado para o email:";
        SpannableString ss = new SpannableString(text);
        StyleSpan bold_aviso = new StyleSpan(Typeface.BOLD);
        ForegroundColorSpan color_aviso = new ForegroundColorSpan(cor);
        ss.setSpan(color_aviso, 38, 59, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(bold_aviso, 38, 59, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        aviso.setText(ss);


        //colocar "Termos e Condições!" com onclick e cor diferente
        text = "Li e aceito os Termos e Condições!";
        ss = new SpannableString(text);
        ClickableSpan termos = new ClickableSpan() {
            @Override
            public void onClick(View widget) {//mudar para a activity Activity_login
                Intent i = new Intent(Activity_concluir_registo.this, Activity_termos_condicoes.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                int cor = getResources().getColor(R.color.corBaseAzul);
                ds.setColor(cor);
            }
        };
        ss.setSpan(termos, 15, 34, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        checkBox.setText(ss);
        checkBox.setMovementMethod(LinkMovementMethod.getInstance());


        //colocar checkbox dos termos "checked" quando o user lê os termos e condiçoes
        Intent intent_termos = getIntent();
        Bundle parametro = intent_termos.getExtras();
        if(parametro != null){
            Boolean res = parametro.getBoolean("resposta_termos");
            if (res == Boolean.TRUE){
                checkBox.setChecked(true);
            }
        }

        //concluir Activity_registo/Activity_login
        concluir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Activity_concluir_registo.this, Activity_feed.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

    }
}
