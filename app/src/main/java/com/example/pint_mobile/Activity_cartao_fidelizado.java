package com.example.pint_mobile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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

import java.util.ArrayList;

public class Activity_cartao_fidelizado extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{

    private ConstraintLayout cl_nome_empresa;
    private TextView nome_empresa;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ImageView menu;
    private JSONObject dados;
    private String localizacao, AreaInteresse, nome, cor, email, id_empresa, id_cartao, id_cliente, rating, ratingEmpresa, facebook, twitter, linkedIn;
    public static ArrayList<Campanha> campanhas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartao_fidelizado);


        //remover action bar
        getSupportActionBar().hide();

        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        menu = findViewById(R.id.menu);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View v = navigationView.getHeaderView(0);
        cl_nome_empresa = v.findViewById(R.id.clEmpresa);
        nome_empresa = v.findViewById(R.id.nome);

        final ImageView button = findViewById(R.id.voltar);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Activity_cartao_fidelizado.this, Activity_feed.class);
                startActivity(intent);
            }
        });

        Cartao_empresa_fidelizada cartao = getIntent().getParcelableExtra("cartaoFidelizado");
        nome_empresa.setText(cartao.getNome());
        localizacao = cartao.getDistrito();
        AreaInteresse = cartao.getArea();
        nome = cartao.getNome();
        cor = cartao.getCor();
        email = cartao.getEmail();
        id_empresa = cartao.getId_empresa();
        id_cartao = cartao.getId_cartao();
        id_cliente = getIntent().getExtras().getString("idCliente");
        ratingEmpresa = cartao.getRating();
        facebook = cartao.getFacebook();
        twitter = cartao.getTwitter();
        linkedIn = cartao.getLinkedin();

        // Todas as campanhas deste cartao
        campanhas = getIntent().getParcelableArrayListExtra("campanhas");

        for(Campanha campanha : campanhas) {
            campanha.setNomeEmpresa(cartao.getNome());
            campanha.setCor(cartao.getCor());
        }

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
        cl_nome_empresa.setBackgroundColor(Color.parseColor(cor));

        carregar_rating(id_empresa, id_cliente);

        MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_cancel);
        SpannableString s = new SpannableString(menuItem.getTitle());
        int cor = getResources().getColor(R.color.corCancelarFidelizacao);
        s.setSpan(new ForegroundColorSpan(cor), 0, s.length(), 0);
        menuItem.setTitle(s);
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
                bundle_rating.putString("id_cliente", id_cliente);
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
                bundle_inf.putString("rating", ratingEmpresa);
                bundle_inf.putString("facebook", facebook);
                bundle_inf.putString("twitter", twitter);
                bundle_inf.putString("linkedIn", linkedIn);
                fragment = new Fragment_empresa_menu_informacoes();
                fragment.setArguments(bundle_inf);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment).commit();
                break;

            case R.id.nav_cancel:
                Dialog_cancelar_fidelizacao dialog = new Dialog_cancelar_fidelizacao();
                Bundle args = new Bundle();
                args.putString("id_cliente", id_cliente);
                args.putString("id_cartao", id_cartao);
                args.putString("id_empresa", id_empresa);
                dialog.setArguments(args);
                dialog.show(getSupportFragmentManager(), "Dialog_cancelar_fidelizacao");
                break;
        }
        drawerLayout.closeDrawer(Gravity.RIGHT);
        return true;
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
                Toast.makeText(getApplicationContext(), "getRatingEmpresa sem sucesso", Toast.LENGTH_SHORT).show();
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