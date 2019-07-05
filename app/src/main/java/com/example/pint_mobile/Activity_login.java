package com.example.pint_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class Activity_login extends AppCompatActivity {

    private TextView recuperar_senha;
    private Button login;
    private EditText editText_email;
    private EditText editText_senha;
    private String email;
    private String senha;
    private String info;
    private ProgressBar progressBar;
    static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //remover action bar
        getSupportActionBar().hide();

        recuperar_senha = findViewById(R.id.recuperar);
        login = findViewById(R.id.entrar);
        editText_email = findViewById(R.id.id_nome);
        editText_senha = findViewById(R.id.senha);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);

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
                    progressBar.setVisibility(View.VISIBLE);
                    login();
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
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        String texto_email = editText_email.getText().toString();
        String texto_senha = editText_senha.getText().toString();
        savedInstanceState.putString("texto_email", texto_email);
        savedInstanceState.putString("texto_senha", texto_senha);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        String texto_email = savedInstanceState.getString("texto_email");
        String texto_senha = savedInstanceState.getString("texto_senha");
        editText_email.setText(texto_email);
        editText_senha.setText(texto_senha);
    }

    private void login() {
        String url = "https://www.mycards.dsprojects.pt/authentication/signin_cliente";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("true")) {

                                info = jsonObject.getString("message");
                                JSONObject cliente = new JSONObject(info);
                                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("Id",cliente.getString("ID_Cliente"));
                                editor.putString("Email",cliente.getString("Email"));
                                editor.putString("Localizacao",cliente.getString("Localizacao"));
                                editor.putString("DataRegisto",cliente.getString("DataRegisto"));
                                editor.putString("Ativo",cliente.getString("Ativo"));
                                editor.putString("PrimeiroNome",cliente.getString("PrimeiroNome"));
                                editor.putString("UltimoNome",cliente.getString("UltimoNome"));
                                editor.putString("DataNascimento",cliente.getString("DataNascimento"));
                                editor.putString("Preferencias",cliente.getString("Preferencias"));
                                editor.putString("Notificacoes",cliente.getString("Notificacoes"));
                                editor.putString("Rating", "0");
                                editor.commit();

                                Intent intent = new Intent(getApplicationContext(), Activity_feed.class);
                                startActivity(intent);
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Credenciais erradas!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
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
                if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
                    Toast.makeText(Activity_login.this, "Sem ligação à Internet!", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    //This indicates that the request has either time out or there is no connection
                    Log.i("VolleyError::", error.toString());
                } else if (error instanceof AuthFailureError) {
                    //Error indicating that there was an Authentication Failure while performing the request
                    Log.i("AuthFailureError::", error.toString());
                } else if (error instanceof ServerError) {
                    //Indicates that the server responded with a error response
                    Log.i("ServerError::", error.toString());
                    Toast.makeText(Activity_login.this, error.toString(), Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    //Indicates that there was network error while performing the request
                    Log.i("NetworkError::", error.toString());
                    Toast.makeText(Activity_login.this, "Sem ligação à Internet!", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    //Indicates that the server response could not be parsed
                    Log.i("ParseError::", error.toString());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", senha);
                return params;
            }
        };
        // requestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
    }
}
