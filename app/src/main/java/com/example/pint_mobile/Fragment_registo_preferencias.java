package com.example.pint_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

public class Fragment_registo_preferencias extends Fragment {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private CheckBox agricultura;
    private CheckBox cienciaTecnologia;
    private CheckBox desporto;
    private CheckBox educacao;
    private CheckBox restauracao;
    private CheckBox saude;
    private CheckBox transportesMercadorias;
    private CheckBox turismo;
    private String pref;
    private String c1 = "0", c2 = "0", c3 = "0", c4 = "0", c5 = "0", c6 = "0", c7 = "0", c8 = "0";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_registo_preferencias, container, false);

        agricultura = view. findViewById(R.id.agricultura);
        cienciaTecnologia = view. findViewById(R.id.cienciaTecnologia);
        desporto = view. findViewById(R.id.desporto);
        educacao = view. findViewById(R.id.educacao);
        restauracao = view. findViewById(R.id.restauracao);
        saude = view. findViewById(R.id.saude);
        transportesMercadorias = view. findViewById(R.id.transportesMercadorias);
        turismo = view. findViewById(R.id.turismo);

        sharedPreferences = getContext().getSharedPreferences(Activity_registo.PREFERENCES_REGISTO, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        agricultura.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveInfSharedPreferences();
            }
        });
        cienciaTecnologia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveInfSharedPreferences();
            }
        });
        desporto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveInfSharedPreferences();
            }
        });
        educacao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveInfSharedPreferences();
            }
        });
        restauracao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveInfSharedPreferences();
            }
        });
        saude.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveInfSharedPreferences();
            }
        });
        transportesMercadorias.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveInfSharedPreferences();
            }
        });
        turismo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveInfSharedPreferences();
            }
        });

        return view;
    }

    void saveInfSharedPreferences(){
        if(agricultura.isChecked()){
            c1 = "1";
        } else{
            c1 = "0";
        }
        if(cienciaTecnologia.isChecked()){
            c2 = "1";
        } else{
            c2 = "0";
        }
        if(desporto.isChecked()){
            c3 = "1";
        } else{
            c3 = "0";
        }
        if(educacao.isChecked()){
            c4 = "1";
        } else{
            c4 = "0";
        }
        if(restauracao.isChecked()){
            c5 = "1";
        } else{
            c5 = "0";
        }
        if(saude.isChecked()){
            c6 = "1";
        } else{
            c6 = "0";
        }
        if(transportesMercadorias.isChecked()){
            c7 = "1";
        } else{
            c7 = "0";
        }
        if(turismo.isChecked()){
            c8 = "1";
        } else{
            c8 = "0";
        }
        pref = c1 + c2 + c3 + c4 + c5 + c6 + c7 + c8;
        editor.putString("preferencias", pref);
        editor.commit();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        String prefs = sharedPreferences.getString("preferencias", "");
        if(prefs.substring(0, 1).equals("1"))
            agricultura.setChecked(true);
        if(prefs.substring(1, 2).equals("1"))
            cienciaTecnologia.setChecked(true);
        if(prefs.substring(2, 3).equals("1"))
            desporto.setChecked(true);
        if(prefs.substring(3, 4).equals("1"))
            educacao.setChecked(true);
        if(prefs.substring(4, 5).equals("1"))
            restauracao.setChecked(true);
        if(prefs.substring(5, 6).equals("1"))
            saude.setChecked(true);
        if(prefs.substring(6, 7).equals("1"))
            transportesMercadorias.setChecked(true);
        if(prefs.substring(7, 8).equals("1"))
            turismo.setChecked(true);

    }
 }
