package com.example.pint_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment_empresa_menu_informacoes extends Fragment {

    private TextView nome, area, email, distrito;
    private String s_nome, s_area, s_email, s_distrito;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_empresa_menu_informacoes, container, false);

        /*nome = view.findViewById(R.id.nome);
        area = view.findViewById(R.id.area);
        email = view.findViewById(R.id.email);
        distrito = view.findViewById(R.id.distrito);

        Bundle bundle = this.getArguments();
        s_nome = bundle.getString("nome");
        s_email = bundle.getString("email");
        s_area = bundle.getString("area");
        s_distrito = bundle.getString("distrito");

        nome.setText(s_nome);
        email.setText(s_email);
        area.setText(s_area);
        distrito.setText(s_distrito);*/

        return view;
    }
}