package com.example.pint_mobile;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Dialog_fidelizar_empresa extends DialogFragment {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Button cancel, ok;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_fidelizar_empresa, container, false);

        cancel = view.findViewById(R.id.tentar);
        ok = view.findViewById(R.id.enviar);
        TextView tvNome = view.findViewById(R.id.nome);
        TextView tvPontos = view.findViewById(R.id.pontos);
        TextView tvCupoes = view.findViewById(R.id.cupoes);
        TextView tvCarimbos = view.findViewById(R.id.carimbos);

        //fechar pop up ao carregar em cancelar
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        tvNome.setText(getArguments().getString("nome"));
        tvPontos.setText("12");
        tvCupoes.setText("0");
        tvCarimbos.setText("1");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width_screen = size.x;
        int height_screen = size.y;

        double w = width_screen * 0.9;
        int width = (int) w;

        double h = height_screen * 0.75;
        int height = (int) h;

        getDialog().getWindow().setLayout(width, height);
        getDialog().getWindow().setGravity(Gravity.CENTER);
    }
}

