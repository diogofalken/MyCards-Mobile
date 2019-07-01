package com.example.pint_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_termos_condicoes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termos_condicoes);

        //remover action bar
        getSupportActionBar().hide();

        //voltar para a activity conta_criada_sucesso
        final ImageView button = findViewById(R.id.voltar_conta_criada_sucesso);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Activity_termos_condicoes.this, Activity_concluir_registo.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        // inserir termos e condiçoes
        TextView termos = findViewById(R.id.termos_condicoes);
        StringBuilder stringBuilder = new StringBuilder();
        String msg = " Termos e condições! ";
        for (int i = 0; i < 100; i++) {
            stringBuilder.append(msg);
        }
        termos.setText(stringBuilder.toString());

    }

    public void onclick_aceito_termos(View v){
        //mudar para a activity Activity_registo ao clicar no botao "aceito"
        Intent intent = new Intent(getApplicationContext(), Activity_concluir_registo.class);
        Bundle parametro = new Bundle();

        parametro.putBoolean("resposta_termos", Boolean.TRUE);
        intent.putExtras(parametro);

        startActivity(intent);
    }

}
