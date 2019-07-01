package com.example.pint_mobile;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class Activity_feed extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNav;
    ImageView icon_left, icon_right;
    EditText barra_pesquisa;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

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


}
