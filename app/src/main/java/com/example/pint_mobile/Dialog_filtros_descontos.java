package com.example.pint_mobile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Dialog_filtros_descontos extends DialogFragment {

    Button close;
    LinearLayout ll_tipos;
    LinearLayout ll_empresas;
    TextView tv_tipos;
    TextView tv_empresas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_filtros_descontos, container, false);

        ll_tipos = view.findViewById(R.id.linearLayoutTipos);
        ll_empresas = view.findViewById(R.id.linearLayoutEmpresas);
        tv_tipos = view.findViewById(R.id.textViewTipos);
        tv_empresas = view.findViewById(R.id.textViewEmpresas);
        close = view.findViewById(R.id.voltar);


        //fechar pop up
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
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

