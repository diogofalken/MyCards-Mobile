package com.example.pint_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
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
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
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

import java.util.HashMap;
import java.util.Map;

public class Activity_feed extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNav;
    ImageView icon_left, icon_right;
    EditText barra_pesquisa;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String info;
    float valor_rating = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        sharedPreferences = getSharedPreferences(Activity_login.MyPREFERENCES, Context.MODE_PRIVATE);

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

        //calcular rating
        new Getrating().execute();


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
        icon_right.setImageResource(R.drawable.ic_search);
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

        //abrir barra de pesquisa
        icon_right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(barra_pesquisa.getVisibility() == View.GONE) {
                    icon_right.setImageResource(R.drawable.ic_close);
                    barra_pesquisa.setVisibility(View.VISIBLE);
                }
                else {
                    barra_pesquisa.setVisibility(View.GONE);
                    icon_right.setImageResource(R.drawable.ic_search);
                }
            }
        });
    }

    private void open_fragment_cartoes(){
        Fragment fragment = new Fragment_feed_cartoes();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();

        icon_left.setVisibility(View.GONE);
        icon_right.setVisibility(View.GONE);
        barra_pesquisa.setVisibility(View.GONE);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

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

    private class Getrating extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://www.mycards.dsprojects.pt/api/cliente/" + sharedPreferences.getString("Id", "") + "/rating";
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONArray ratings = new JSONArray(jsonStr);

                    for (int i = 0; i < ratings.length(); i++) {
                        JSONObject c = ratings.getJSONObject(i);
                        String valor = c.getString("valor");
                        int x = Integer.parseInt(valor);
                        valor_rating += x;
                    }
                    valor_rating = valor_rating / ratings.length();
                    //String z = String.
                    //Toast.makeText(getApplicationContext()), valor_rating, Toast.LENGTH_SHORT).show();

                } catch (final JSONException e) {
                    Toast.makeText(getApplicationContext(), "catch", Toast.LENGTH_SHORT).show();
                }

            } else { //nenhum rating
                valor_rating = 0;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

}
