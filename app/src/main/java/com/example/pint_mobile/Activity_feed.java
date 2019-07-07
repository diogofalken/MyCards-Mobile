package com.example.pint_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Activity_feed extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

    /* Variaveis Gerais */
    public static ArrayList<Cartao_empresa_fidelizada> cartoesFidelizados = new ArrayList<Cartao_empresa_fidelizada>();

    BottomNavigationView bottomNav;
    ImageView icon_left, icon_right;
    EditText barra_pesquisa;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Integer x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        cartoesFidelizados.clear();

        sharedPreferences = getSharedPreferences(Activity_login.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //remover action bar
        getSupportActionBar().hide();

        bottomNav = findViewById(R.id.bottom_nav);
        icon_left = findViewById(R.id.icon_left);
        icon_right = findViewById(R.id.icon_right);
        barra_pesquisa = findViewById(R.id.barra_pesquisa);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);

        //tornar o fragment dos descontos como fragment inicial
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_feed_descontos()).commit();

        //colocar icon descontos checked do menu nav assim que inicia a activity
        bottomNav.getMenu().findItem(R.id.nav_wallet).setChecked(false);
        bottomNav.getMenu().findItem(R.id.nav_home).setChecked(true);

        open_fragment_descontos();

        navigationView.setNavigationItemSelectedListener(this);
        String id = getIntent().getStringExtra("id");

        calcular_rating_cliente();

        getCartoesFidelizados();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_wallet:
                            open_fragment_cartoes();
                            break;

                        case R.id.nav_home:
                            open_fragment_descontos();
                            break;

                        case R.id.nav_profile:
                            open_fragment_perfil();
                            break;
                    }
                    return true;
                }
            };

    private void open_fragment_descontos(){
        Fragment fragment = new Fragment_feed_descontos();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();

        //colocar icons respetivos ao fragment dos descontos
        icon_left.setVisibility(View.VISIBLE);
        icon_right.setVisibility(View.VISIBLE);
        icon_left.setImageResource(R.drawable.ic_filter);
        icon_right.setImageResource(R.drawable.ic_reload);
        barra_pesquisa.setVisibility(View.GONE);

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        //abrir pop up dos filtros
        icon_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_filtros_descontos dialog = new Dialog_filtros_descontos();
                dialog.show(getSupportFragmentManager(), "Dialog_filtros_descontos");
            }
        });

        icon_right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                refreshMyData();
            }
        });
    }

    private void open_fragment_cartoes(){
        Fragment fragment = new Fragment_feed_cartoes();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();

        icon_left.setVisibility(View.GONE);
        icon_right.setVisibility(View.VISIBLE);
        icon_right.setImageResource(R.drawable.ic_reload);
        barra_pesquisa.setVisibility(View.GONE);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        icon_right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                refreshMyData();
            }
        });

    }

    private void open_fragment_perfil(){
        Fragment fragment = new Fragment_feed_perfil();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();

        //colocar icons respetivos ao fragment dos descontos
        icon_left.setVisibility(View.GONE);
        icon_right.setVisibility(View.VISIBLE);
        icon_right.setImageResource(R.drawable.ic_menu_perfil);

        barra_pesquisa.setVisibility(View.GONE);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        //abrir menu perfil
        icon_right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_edit_perfil:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Fragment_profile_menu_perfil()).commit();
                break;

            case R.id.nav_edit_preferencias:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Fragment_profile_menu_preferencias()).commit();
                break;

            case R.id.nav_edit_notificacoes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Fragment_profile_menu_notificacoes()).commit();
                break;

            case R.id.nav_logout:
                Dialog_logout dialog = new Dialog_logout();
                dialog.show(getSupportFragmentManager(), "Dialog_logout");
                break;
        }
        drawerLayout.closeDrawer(Gravity.RIGHT);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Dialog_logout dialog = new Dialog_logout();
            dialog.show(getSupportFragmentManager(), "Dialog_logout");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void calcular_rating_cliente() {
        String url = "https://www.mycards.dsprojects.pt/api/cliente/" + sharedPreferences.getString("Id", "") + "/rating";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ratings = new JSONArray(response);
                            if(ratings.length() != 0){
                                for (int i = 0; i < ratings.length(); i++) {
                                    JSONObject r = ratings.getJSONObject(i);
                                    String valor = r.getString("Rating");
                                    Integer integer = Integer.parseInt(valor);
                                    x += integer;
                                }
                                x = x / ratings.length();
                                String valor = String.valueOf(x);
                                editor.putString("Rating", valor);
                                editor.commit();
                            }
                            else{
                                editor.putString("Rating", "0");
                                editor.commit();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
                if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
                    Toast.makeText(Activity_feed.this, "Sem ligação à Internet!", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    //This indicates that the request has either time out or there is no connection
                    Log.i("VolleyError::", error.toString());
                } else if (error instanceof AuthFailureError) {
                    //Error indicating that there was an Authentication Failure while performing the request
                    Log.i("AuthFailureError::", error.toString());
                } else if (error instanceof ServerError) {
                    //Indicates that the server responded with a error response
                    Log.i("ServerError::", error.toString());
                    Toast.makeText(Activity_feed.this, error.toString(), Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    //Indicates that there was network error while performing the request
                    Log.i("NetworkError::", error.toString());
                    Toast.makeText(Activity_feed.this, "Sem ligação à Internet!", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    //Indicates that the server response could not be parsed
                    Log.i("ParseError::", error.toString());
                }
            }
        }) {
        };
        // requestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
    }

    private void getCartoesFidelizados() {
        String url = "https://www.mycards.dsprojects.pt/api/cliente/" + sharedPreferences.getString("Id", "") + "/cartao";
        StringRequest getCartoes = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject cartao = jsonArray.getJSONObject(i);
                        carregar_informacao_empresa(cartao.getString("ID_Empresa"), cartao.getString("ID_Cartao"), cartao.getString("Pontos"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Erro nos cartões!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "GET sem sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(getCartoes);
    }

    void carregar_informacao_empresa(final String id, final String idCartao, final String pontos){
        String url_inf = "https://www.mycards.dsprojects.pt/api/empresa/" + id;
        StringRequest getDadosEmpresa = new StringRequest(Request.Method.GET, url_inf, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray_inf = new JSONArray(response);
                    JSONObject dados = jsonArray_inf.getJSONObject(0);
                    String localizacao = dados.getString("Localizacao");
                    String AreaInteresse = dados.getString("AreaInteresse");
                    String nome = dados.getString("Nome");
                    String cor = "verde";
                    String email = dados.getString("Email");

                    switch (AreaInteresse.toString()) {
                        case "Agricultura":
                            cor = "#006600";
                            break;
                        case "Ciência e Tecnologia":
                            cor = "#042C54";
                            break;
                        case "Desporto":
                            cor = "#4d004d";
                            break;
                        case "Educação":
                            cor = "#662200";
                            break;
                        case "Saúde":
                            cor = "#5C7993";
                            break;
                        case "Restauração":
                            cor = "#BD8E02";
                            break;
                        case "Transportes e Mercadorias":
                            cor = "#3F51B5";
                            break;
                        case "Turismo":
                            cor = "#E91E63";
                            break;
                        default:
                            cor = "#5A613A";
                    }

                    getCampanhas(id,idCartao,localizacao,nome,AreaInteresse,cor, email, pontos);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Erro nos dados da empresa!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "GET sem sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue_inf = Volley.newRequestQueue(getApplicationContext());
        requestQueue_inf.add(getDadosEmpresa);
    }

    void getCampanhas(final String id, final String idCartao, final String localizacao, final String nome, final String AreaInteresse, final String cor, final String email, final String pontos) {
        String url = "https://www.mycards.dsprojects.pt/api/cliente/" + sharedPreferences.getString("Id", "") + "/cartao/" + idCartao + "/instanciacampanha";
        final StringRequest getCampanhas = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    int nUtilizacoes = 0;
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = jsonArray.getJSONObject(i);
                        nUtilizacoes += Integer.parseInt(data.getString("Utilizado"));
                    }
                    Cartao_empresa_fidelizada auxCartao = new Cartao_empresa_fidelizada(
                            id,
                            idCartao,
                            nome,
                            email,
                            AreaInteresse,
                            Integer.toString(jsonArray.length()),
                            localizacao,
                            cor,
                            Integer.toString(nUtilizacoes),
                            pontos
                    );
                    cartoesFidelizados.add(auxCartao);
                    calcularRatingEmpresa(id, auxCartao);
                    editor.commit();
                    getDescontos(auxCartao);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Erro nos cartões!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "GET sem sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(getCampanhas);
    }

    void getDescontos(final Cartao_empresa_fidelizada cartao) {
        String url = "https://www.mycards.dsprojects.pt/api/empresa/" + cartao.getId_empresa() + "/campanha";
        StringRequest getDescontos = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    ArrayList<Campanha> lista = new ArrayList<Campanha>();
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = jsonArray.getJSONObject(i);
                        lista.add(new Campanha(
                                data.getString("ID_Campanha"),
                                data.getString("Designacao"),
                                data.getString("Descricao"),
                                data.getString("DataInicio"),
                                data.getString("DataFim"),
                                data.getString("Valor"),
                                data.getString("TipoCampanha")
                        ));
                    }
                    cartao.setListaCampanhas(lista);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Erro nos cartões!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "GET sem sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(getDescontos);
    }

    private void calcularRatingEmpresa(String id, final Cartao_empresa_fidelizada cartao) {
        String url = "https://www.mycards.dsprojects.pt/api/empresa/" +  id + "/rating";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String valor= "0";
                        float x = 0;
                        try {
                            JSONArray ratings = new JSONArray(response);
                            if(ratings.length() != 0){
                                for (int i = 0; i < ratings.length(); i++) {
                                    JSONObject r = ratings.getJSONObject(i);
                                    valor = r.getString("Rating");
                                    x += Float.parseFloat(valor);
                                }
                                valor = Integer.toString(Math.round(x / ratings.length()));
                            }
                            cartao.setRating(valor);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
                if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
                    Toast.makeText(getApplicationContext(), "Sem ligação à Internet!", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    //This indicates that the request has either time out or there is no connection
                    Log.i("VolleyError::", error.toString());
                } else if (error instanceof AuthFailureError) {
                    //Error indicating that there was an Authentication Failure while performing the request
                    Log.i("AuthFailureError::", error.toString());
                } else if (error instanceof ServerError) {
                    //Indicates that the server responded with a error response
                    Log.i("ServerError::", error.toString());
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    //Indicates that there was network error while performing the request
                    Log.i("NetworkError::", error.toString());
                    Toast.makeText(getApplicationContext(), "Sem ligação à Internet!", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    //Indicates that the server response could not be parsed
                    Log.i("ParseError::", error.toString());
                }
            }
        }) {
        };
        // requestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(postRequest);
    }

    public void refreshMyData(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
