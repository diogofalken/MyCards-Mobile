package com.example.pint_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Activity_registo extends AppCompatActivity{

    int fragment_number = 1;
    static final String PREFERENCES_REGISTO = "dados_registo";
    Fragment fragment = new Fragment_registo_informacoes();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String email = null;
    private String senha = null;
    private String senha_conf = null;
    private String primeiro_nome = null;
    private String ultimo_nome = null;
    private String data_nasc = null;
    private String distrito = null;
    private String info;
    private ProgressBar progressBar;


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
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);


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

        sharedPreferences = getApplicationContext().getSharedPreferences(PREFERENCES_REGISTO, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("senha", senha);
        editor.putString("senha_conf", senha_conf);
        editor.putString("primeiro_nome", primeiro_nome);
        editor.putString("ultimo_nome", ultimo_nome);
        editor.putString("data_nasc", data_nasc);
        editor.putString("distrito", distrito);
        editor.putString("preferencias", "00000000");
        editor.putString("notificacoes", "0");
        editor.commit();



        //quando se clica no botao next
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            switch (fragment_number) {
                 case 1: //informaçoes
                     if(validateRegistoInformacoes()) {
                         fragment = new Fragment_registo_preferencias();
                         getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
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
                     }
                     break;

                 case 2: //preferencias
                     fragment = new Fragment_registo_notificacoes();
                     getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                     next.setImageResource(R.drawable.ic_check);
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
                     progressBar.setVisibility(View.VISIBLE);
                     registo();
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
                        fragment = new Fragment_registo_preferencias();
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

    //errrrroooooooo
    boolean testarIdade() {
        String data = sharedPreferences.getString("data_nasc", "");
        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();


        int dia = Integer.parseInt(data.split("/")[0]);
        int mes = Integer.parseInt(data.split("/")[1].split("/")[0]);
        int ano = Integer.parseInt(data.split("/")[1].split("/")[1]);
        //Date dataAtual = Calendar.getInstance().getTime();

        //Toast.makeText(getApplicationContext(), dia, Toast.LENGTH_SHORT).show();
        /*if (dataAtual.getYear() - ano >= 18) {
            return true;
        }
        if(dataAtual.getMonth() > mes) {
            return  true;
        }
        if (dataAtual.getHours() >= dia) {
            return true;
        }*/
        return false;
    }

    int retorna_width(int width_screen, double multiplicar, int subtrair){
        double x = width_screen;
        x *= multiplicar;
        x -= subtrair;
        int y = (int) x;
        return y;
    }

    boolean validateRegistoInformacoes(){
        if(sharedPreferences.getString("email", "").equals("")) {
            Toast.makeText(getApplicationContext(), "Introduza um email!", Toast.LENGTH_SHORT).show();
        } else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(sharedPreferences.getString("email", "")).matches()) {
            Toast.makeText(getApplicationContext(), "Introduza um email correto!", Toast.LENGTH_SHORT).show();
        } else if(sharedPreferences.getString("senha", "").equals("")) {
            Toast.makeText(getApplicationContext(), "Introduza uma senha!", Toast.LENGTH_SHORT).show();
        } else if(
                sharedPreferences.getString("senha", "").length() < 8 ||
                sharedPreferences.getString("senha", "").toLowerCase().equals(sharedPreferences.getString("senha", "")) ||
                        sharedPreferences.getString("senha", "").toUpperCase().equals(sharedPreferences.getString("senha", ""))  ||
                        !sharedPreferences.getString("senha", "").matches("[a-zA-Z ]*\\d+.*")
                ){
            Toast.makeText(getApplicationContext(), "Introduza uma senha com tamanho mínimo de 8 caracteres e pelo menos uma letra maiúscula, uma letra minúscula e um número!", Toast.LENGTH_SHORT).show();
        } else if(sharedPreferences.getString("senha_conf", "").equals("")) {
            Toast.makeText(getApplicationContext(), "Confirme a senha que introduziu!", Toast.LENGTH_SHORT).show();
        } else if(sharedPreferences.getString("primeiro_nome", "").equals("")) {
            Toast.makeText(getApplicationContext(), "Introduza o seu primeiro nome!", Toast.LENGTH_SHORT).show();
        } else if(sharedPreferences.getString("ultimo_nome", "").equals("")) {
            Toast.makeText(getApplicationContext(), "Introduza o seu último nome!", Toast.LENGTH_SHORT).show();
        } else if(sharedPreferences.getString("data_nasc", "").equals("")) {
            Toast.makeText(getApplicationContext(), "Introduza a sua data de nascimento!", Toast.LENGTH_SHORT).show();
        }/* else if(!testarIdade()) {
            Toast.makeText(getApplicationContext(), "Precisa ter mais do que 18 anos para se registar!", Toast.LENGTH_SHORT).show();
        } */else if(sharedPreferences.getString("distrito", "").equals("")) {
            Toast.makeText(getApplicationContext(), "Introduza o seu distrito!", Toast.LENGTH_SHORT).show();
        } else if(!sharedPreferences.getString("senha", "").equals(sharedPreferences.getString("senha_conf", ""))) {
            Toast.makeText(getApplicationContext(), "Introduza senhas iguais!", Toast.LENGTH_SHORT).show();
        } else
            return true;
        return false;
    }

    void registo(){
        String url = "https://www.mycards.dsprojects.pt/authentication/signup_cliente";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("true")) {
                                Intent intent = new Intent(Activity_registo.this, Activity_concluir_registo.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "O email '" + sharedPreferences.getString("email", "") + "' já existe!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {Toast.makeText(getApplicationContext(), "catch", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Activity_registo.this, "Sem ligação à Internet!", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    //This indicates that the request has either time out or there is no connection
                    Log.i("VolleyError::", error.toString());
                } else if (error instanceof AuthFailureError) {
                    //Error indicating that there was an Authentication Failure while performing the request
                    Log.i("AuthFailureError::", error.toString());
                } else if (error instanceof ServerError) {
                    //Indicates that the server responded with a error response
                    Log.i("ServerError::", error.toString());
                    Toast.makeText(Activity_registo.this, error.toString(), Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    //Indicates that there was network error while performing the request
                    Log.i("NetworkError::", error.toString());
                    Toast.makeText(Activity_registo.this, "Sem ligação à Internet!", Toast.LENGTH_LONG).show();
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
                params.put("primeironome", sharedPreferences.getString("primeiro_nome", ""));
                params.put("ultimonome", sharedPreferences.getString("ultimo_nome", ""));
                params.put("datanascimento", sharedPreferences.getString("data_nasc", ""));
                params.put("localizacao", sharedPreferences.getString("distrito", ""));
                params.put("preferencias", sharedPreferences.getString("preferencias", ""));
                params.put("notificacoes", sharedPreferences.getString("notificacoes", ""));
                return params;
            }
        };
        // requestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
    }
}
