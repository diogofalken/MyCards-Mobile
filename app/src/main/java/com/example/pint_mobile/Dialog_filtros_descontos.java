package com.example.pint_mobile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Dialog_filtros_descontos extends DialogFragment {

    Button close, aplicar;
    LinearLayout ll_tipos;
    LinearLayout ll_empresas;
    TextView tv_tipos;
    TextView tv_empresas;
    static public String tipos = "1000";
    static public String areasInteresse = "100000000";
    CheckBox tipo_todos, tipo1, tipo2, tipo3;
    CheckBox area_todos, area1, area2, area3, area4, area5, area6, area7, area8;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_filtros_descontos, container, false);

        ll_tipos = view.findViewById(R.id.linearLayoutTipos);
        ll_empresas = view.findViewById(R.id.linearLayoutEmpresas);
        tv_tipos = view.findViewById(R.id.textViewTipos);
        tv_empresas = view.findViewById(R.id.textViewEmpresas);
        close = view.findViewById(R.id.voltar);
        aplicar = view.findViewById(R.id.sim);

        tipo_todos = view.findViewById(R.id.checkBox2);
        tipo1 = view.findViewById(R.id.checkBox5);
        tipo2 = view.findViewById(R.id.checkBox);
        tipo3 = view.findViewById(R.id.checkBox3);

        area_todos = view.findViewById(R.id.checkBox6);
        area1 = view.findViewById(R.id.aaaaaa);
        area2 = view.findViewById(R.id.bbbbbbb);
        area3 = view.findViewById(R.id.checkBox7);
        area4 = view.findViewById(R.id.checkBox8);
        area5 = view.findViewById(R.id.checkBox9);
        area6 = view.findViewById(R.id.checkBox10);
        area7 = view.findViewById(R.id.checkBox11);
        area8 = view.findViewById(R.id.checkBox12);

        tipo_todos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipo_todos.isChecked()){
                    tipo1.setChecked(false);
                    tipo2.setChecked(false);
                    tipo3.setChecked(false);
                }
            }
        });
        tipo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipo_todos.isChecked())
                    tipo_todos.setChecked(false);
            }
        });
        tipo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipo_todos.isChecked())
                    tipo_todos.setChecked(false);
            }
        });
        tipo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipo_todos.isChecked())
                    tipo_todos.setChecked(false);
            }
        });



        area_todos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(area_todos.isChecked()){
                    area1.setChecked(false);
                    area2.setChecked(false);
                    area3.setChecked(false);
                    area4.setChecked(false);
                    area5.setChecked(false);
                    area6.setChecked(false);
                    area7.setChecked(false);
                    area8.setChecked(false);
                }
            }
        });
        area1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(area_todos.isChecked())
                    area_todos.setChecked(false);
            }
        });
        area2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(area_todos.isChecked())
                    area_todos.setChecked(false);
            }
        });
        area3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(area_todos.isChecked())
                    area_todos.setChecked(false);
            }
        });
        area4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(area_todos.isChecked())
                    area_todos.setChecked(false);
            }
        });
        area5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(area_todos.isChecked())
                    area_todos.setChecked(false);
            }
        });
        area6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(area_todos.isChecked())
                    area_todos.setChecked(false);
            }
        });
        area7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(area_todos.isChecked())
                    area_todos.setChecked(false);
            }
        });
        area8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(area_todos.isChecked())
                    area_todos.setChecked(false);
            }
        });






        //fechar pop up
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        aplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipos = "";
                areasInteresse = "";

                // List View de Tipos de descontos
                for (int i = 0; i < ll_tipos.getChildCount(); i++){
                    View vi = ll_tipos.getChildAt(i);
                    if (vi instanceof CheckBox){
                        CheckBox cb = (CheckBox)vi;
                        if(cb.isChecked()) {
                            tipos += 1;
                        }
                        else {
                            tipos += 0;
                        }
                    }
                }

                if(tipos.equals("0000")) {
                    tipos = "1000";
                }

                // List View de Areas de Interesse
                for (int i = 0; i < ll_empresas.getChildCount(); i++){
                    View vi = ll_empresas.getChildAt(i);
                    if (vi instanceof CheckBox){
                        CheckBox cb = (CheckBox)vi;
                        if(cb.isChecked()) {
                            areasInteresse += 1;
                        }
                        else {
                            areasInteresse += 0;
                        }
                    }
                }

                if(areasInteresse.equals("000000000")) {
                    areasInteresse = "100000000";
                }

                getDialog().dismiss();

                Fragment fragment = new Fragment_feed_descontos();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });


        //abrir e fechar linear layouts
        tv_tipos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ll_tipos.getVisibility() == view.VISIBLE) {
                    ll_tipos.setVisibility(View.GONE);
                    tv_tipos.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_settings, 0, R.drawable.ic_arrow_up, 0);

                } else {
                    ll_tipos.setVisibility(View.VISIBLE);
                    tv_tipos.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_settings, 0, R.drawable.ic_arrow_down, 0);
                }
            }
        });
        tv_empresas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ll_empresas.getVisibility() == view.VISIBLE) {
                    ll_empresas.setVisibility(View.GONE);
                    tv_empresas.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_empresas, 0, R.drawable.ic_arrow_up, 0);

                } else {
                    ll_empresas.setVisibility(View.VISIBLE);
                    tv_empresas.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_empresas, 0, R.drawable.ic_arrow_down, 0);
                }
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        float h =  325 * getResources().getDisplayMetrics().density;
        float w =  500 * getResources().getDisplayMetrics().density;
        int height = Math.round(h);
        int width = Math.round(w);
        window.setLayout(height, width);
        window.setGravity(Gravity.CENTER);
    }
}

