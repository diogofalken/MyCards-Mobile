package com.example.pint_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Activity_cartao_fidelizado extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{

    private ConstraintLayout cl_nome_empresa;
    private TextView nome_empresa;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ImageView menu;
    private JSONObject dados;
    private String localizacao;
    private String AreaInteresse;
    private String nome;
    private String cor;
    private String email;
    private String id_empresa;
    private String rating;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartao_fidelizado);

        sharedPreferences = getSharedPreferences(Activity_login.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //remover action bar
        getSupportActionBar().hide();

        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        menu = findViewById(R.id.menu);

        final ImageView button = findViewById(R.id.voltar);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Activity_cartao_fidelizado.this, Activity_feed.class);
                startActivity(intent);
            }
        });

        Bundle b = new Bundle();
        b = getIntent().getExtras();
        id_empresa = b.getString("id_empresa");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View v = navigationView.getHeaderView(0);
        cl_nome_empresa = v.findViewById(R.id.clEmpresa);
        nome_empresa = v.findViewById(R.id.nome_empresa);

        //abrir menu perfil
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });

        Fragment fragment = new Fragment_empresa_menu_descontos();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().findItem(R.id.nav_descontos).setChecked(true);

        carregar_dados_empresa(id_empresa);

        carregar_rating(id_empresa, sharedPreferences.getString("Id", ""));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_descontos:
                navigationView.getMenu().findItem(R.id.nav_descontos).setChecked(true);
                navigationView.getMenu().findItem(R.id.nav_rating).setChecked(false);
                navigationView.getMenu().findItem(R.id.nav_inf).setChecked(false);
                Fragment fragment = new Fragment_empresa_menu_descontos();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment).commit();
                break;

            case R.id.nav_rating:
                navigationView.getMenu().findItem(R.id.nav_descontos).setChecked(false);
                navigationView.getMenu().findItem(R.id.nav_rating).setChecked(true);
                navigationView.getMenu().findItem(R.id.nav_inf).setChecked(false);
                Bundle bundle_rating = new Bundle();
                bundle_rating.putString("nome", nome);
                bundle_rating.putString("id_empresa", id_empresa);
                bundle_rating.putString("id_cliente", sharedPreferences.getString("Id", ""));
                bundle_rating.putString("rating", rating);
                fragment = new Fragment_empresa_menu_rating();
                fragment.setArguments(bundle_rating);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment).commit();
                break;

            case R.id.nav_inf:
                navigationView.getMenu().findItem(R.id.nav_descontos).setChecked(false);
                navigationView.getMenu().findItem(R.id.nav_rating).setChecked(false);
                navigationView.getMenu().findItem(R.id.nav_inf).setChecked(true);
                Bundle bundle_inf = new Bundle();
                bundle_inf.putString("nome", nome);
                bundle_inf.putString("email", email);
                bundle_inf.putString("area", AreaInteresse);
                bundle_inf.putString("distrito", localizacao);
                fragment = new Fragment_empresa_menu_informacoes();
                fragment.setArguments(bundle_inf);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment).commit();
                break;

            case R.id.nav_cancel:
                //Dialog_logout dialog = new Dialog_logout();
                //dialog.show(getSupportFragmentManager(), "Dialog_logout");
                break;
        }
        drawerLayout.closeDrawer(Gravity.RIGHT);
        return true;
    }

    private void carregar_dados_empresa(String id){
        String url_inf = "https://www.mycards.dsprojects.pt/api/empresa/" + id;
        StringRequest getDadosEmpresa = new StringRequest(Request.Method.GET, url_inf, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray_inf = new JSONArray(response);
                    JSONObject dados = jsonArray_inf.getJSONObject(0);
                    localizacao = dados.getString("Localizacao");
                    AreaInteresse = dados.getString("AreaInteresse");
                    nome = dados.getString("Nome");
                    cor = "azul";
                    email = dados.getString("Email");
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

                    nome_empresa.setText(nome);
                    cl_nome_empresa.setBackgroundColor(Color.parseColor(cor));
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

    private void carregar_rating(String id_empresa, String id_cliente){
        String url = "https://www.mycards.dsprojects.pt/api/empresa/" + id_empresa + "/rating/" + id_cliente;
        StringRequest getRatingEmpresa = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("message");
                        rating = jsonArray.getJSONObject(0).getString("Rating");
                    } else {
                        rating = "0";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Erro no rating da empresa!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "GET sem sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue_inf = Volley.newRequestQueue(getApplicationContext());
        requestQueue_inf.add(getRatingEmpresa);

    }

    public void refreshMyData(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
