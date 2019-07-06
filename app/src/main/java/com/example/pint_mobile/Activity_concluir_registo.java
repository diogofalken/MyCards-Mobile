package com.example.pint_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_concluir_registo extends AppCompatActivity {

    int cor;
    TextView aviso;
    TextView nome;
    TextView email;
    CheckBox checkBox;
    ImageButton concluir;
    ProgressBar progressBar;
    EditText codigo;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concluir_registo);

        cor = getResources().getColor(R.color.corBaseAzul);
        aviso = findViewById(R.id.aviso);
        nome = findViewById(R.id.nome);
        email = findViewById(R.id.email);
        checkBox = findViewById(R.id.politicas);
        concluir = findViewById(R.id.concluir);
        progressBar = findViewById(R.id.progressBar);
        codigo = findViewById(R.id.codigo);

        progressBar.setVisibility(View.GONE);

        //remover action bar
        getSupportActionBar().hide();

        sharedPreferences = getApplicationContext().getSharedPreferences(Activity_registo.PREFERENCES_REGISTO, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        email.setText(sharedPreferences.getString("email", ""));

        //colocar "CÓDIGO DE CONFIRMAÇÂO" a negrito e cor diferente
        String text = "Para concluir o resgisto, introduza o código de confirmação enviado para o email:";
        SpannableString ss = new SpannableString(text);
        StyleSpan bold_aviso = new StyleSpan(Typeface.BOLD);
        ForegroundColorSpan color_aviso = new ForegroundColorSpan(cor);
        ss.setSpan(color_aviso, 38, 59, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(bold_aviso, 38, 59, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        aviso.setText(ss);

        //colocar "bem vindo" bonitoo
        text = "Bem vindo, " + sharedPreferences.getString("primeiro_nome", "") + " " + sharedPreferences.getString("ultimo_nome", "") + "!";
        ss = new SpannableString(text);
        int np = text.length();
        StyleSpan bold_nome = new StyleSpan(Typeface.BOLD);
        ss.setSpan(bold_nome, 11, np-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        nome.setText(ss);


        //colocar "Política de Privacidade!" com onclick e cor diferente
        text = "Li e aceito a Política de Privacidade!";
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
        ss.setSpan(termos, 14, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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

        concluir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if(!checkBox.isChecked()){
                    Toast.makeText(getApplicationContext(), "Leia a política de privacidade!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    String url = "https://www.mycards.dsprojects.pt/authentication/activate_cliente";
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        if (jsonObject.getString("status").equals("true")) {
                                            editor.clear();
                                            editor.commit();
                                            Intent intent = new Intent(Activity_concluir_registo.this, Activity_login.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                        } else {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(getApplicationContext(), "Código de confirmação errado!", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(getApplicationContext(), "catch", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressBar.setVisibility(View.GONE);
                            ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
                            if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
                                Toast.makeText(Activity_concluir_registo.this, "Sem ligação à Internet!", Toast.LENGTH_LONG).show();
                            } else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                //This indicates that the request has either time out or there is no connection
                                Log.i("VolleyError::", error.toString());
                            } else if (error instanceof AuthFailureError) {
                                //Error indicating that there was an Authentication Failure while performing the request
                                Log.i("AuthFailureError::", error.toString());
                            } else if (error instanceof ServerError) {
                                //Indicates that the server responded with a error response
                                Log.i("ServerError::", error.toString());
                                Toast.makeText(Activity_concluir_registo.this, error.toString(), Toast.LENGTH_SHORT).show();
                            } else if (error instanceof NetworkError) {
                                //Indicates that there was network error while performing the request
                                Log.i("NetworkError::", error.toString());
                                Toast.makeText(Activity_concluir_registo.this, "Sem ligação à Internet!", Toast.LENGTH_LONG).show();
                            } else if (error instanceof ParseError) {
                                //Indicates that the server response could not be parsed
                                Log.i("ParseError::", error.toString());
                            }
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("email", sharedPreferences.getString("email", ""));
                            params.put("password", sharedPreferences.getString("senha", ""));
                            params.put("codigoativacao", codigo.getText().toString());
                            return params;
                        }
                    };
                    // requestQueue
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(postRequest);
                }
            }
        });

    }
}
