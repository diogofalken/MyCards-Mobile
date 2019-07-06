package com.example.pint_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class Dialog_logout extends DialogFragment {

   Button sim, nao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_logout, container, false);

        nao = view.findViewById(R.id.voltar);
        sim = view.findViewById(R.id.sim);

        //fechar pop up
        nao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(Activity_login.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent i = new Intent(getActivity(), Activity_login.class);
                startActivity(i);
            }
        });



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        float h =  325 * getResources().getDisplayMetrics().density;
        float w =  150 * getResources().getDisplayMetrics().density;
        int height = Math.round(h);
        int width = Math.round(w);
        window.setLayout(height, width);
        window.setGravity(Gravity.CENTER);
    }




}

