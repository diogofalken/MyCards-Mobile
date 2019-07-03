package com.example.pint_mobile;

import android.content.Intent;
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

public class Dialog_save_changes extends DialogFragment {

   Button sim, nao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_save_changes, container, false);

        nao = view.findViewById(R.id.tentar);
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
                Intent i = new Intent(getContext(), Activity_feed.class);
                getContext().startActivity(i);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        float h =  325 * getResources().getDisplayMetrics().density;
        float w =  175 * getResources().getDisplayMetrics().density;
        int height = Math.round(h);
        int width = Math.round(w);
        window.setLayout(height, width);
        window.setGravity(Gravity.CENTER);
    }




}

