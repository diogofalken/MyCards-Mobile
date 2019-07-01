package com.example.pint_mobile;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_login extends AppCompatActivity {

    private TextView recuperar_senha;
    private Button login;
    private EditText editText_email;
    private EditText editText_senha;
    private String email;
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //remover action bar
        getSupportActionBar().hide();

        recuperar_senha = findViewById(R.id.recuperar);
        login = findViewById(R.id.entrar);
        editText_email = findViewById(R.id.email);
        editText_senha = findViewById(R.id.senha);

        //colocar "Registe-se já!" com onclick e cor diferente
        TextView textView = findViewById(R.id.registar);
        String text = "Ainda não tem conta? Registe-se já!";
        SpannableString ss = new SpannableString(text);
        ClickableSpan registo = new ClickableSpan() {
            @Override
            public void onClick(View widget) {//mudar para a activity Activity_registo
                Intent i = new Intent(Activity_login.this, Activity_registo.class);
                startActivity(i);
                finish();
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                int cor = getResources().getColor(R.color.corBaseAzul);
                ds.setColor(cor);
            }
        };
        ss.setSpan(registo, 21, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                email = editText_email.getText().toString();
                senha = editText_senha.getText().toString();
                if(validateLogin(email, senha)){
                    goToFeed();
                }
            }
        });

        recuperar_senha.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Dialog_recuperar_senha dialog = new Dialog_recuperar_senha();
                dialog.show(getSupportFragmentManager(), "Dialog_recuperar_senha");
            }
        });
    }

    private boolean validateLogin(String email, String senha){
        if(email == null || email.trim().length() == 0){
            Toast.makeText(this, "Introduza o seu email!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(senha == null || senha.trim().length() == 0){
            Toast.makeText(this, "Introduza a sua senha!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void goToFeed() {
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        bundle.putString("senha", senha);

        Intent intent = new Intent(this, Activity_feed.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void see_not_see_password(View v){
        TextView password = findViewById(R.id.senha);
        ImageView img_see = findViewById(R.id.see_pass);
        Context mContext = getApplicationContext();
        Drawable drawable_see = ContextCompat.getDrawable(
                mContext,
                R.drawable.ic_see_password
        );
        Drawable drawable_not_see = ContextCompat.getDrawable(
                mContext,
                R.drawable.ic_not_see_password
        );
        if(password.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){ //password visivel
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            img_see.setImageDrawable(drawable_not_see);

        }
        else{ //password nao visivel
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            img_see.setImageDrawable(drawable_see);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        // guardar dados antes da mudança de activity
        String texto_email = editText_email.getText().toString();
        String texto_senha = editText_senha.getText().toString();
        outState.putString("texto_email", texto_email);
        outState.putString("texto_senha", texto_senha);
    }

    @Override
    public void onRestoreInstanceState(Bundle inState){
        super.onRestoreInstanceState(inState);

        // recuperar dados depois da mudança de activity
        String texto_email = inState.getString("texto_email");
        String texto_senha = inState.getString("texto_senha");
        editText_email.setText(texto_email);
        editText_senha.setText(texto_senha);
    }
}
