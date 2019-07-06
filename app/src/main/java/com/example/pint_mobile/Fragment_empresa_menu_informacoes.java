package com.example.pint_mobile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_empresa_menu_informacoes extends Fragment {

    private TextView nome, area, email, distrito;
    private String s_nome, s_area, s_email, s_distrito;
    ImageView rating;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_empresa_menu_informacoes, container, false);

        nome = view.findViewById(R.id.id_nome);
        area = view.findViewById(R.id.id_area);
        email = view.findViewById(R.id.id_email);
        distrito = view.findViewById(R.id.id_distrito);


        s_nome = getArguments().getString("nome");
        s_email = getArguments().getString("email");
        s_area = getArguments().getString("area");
        s_distrito = getArguments().getString("distrito");


        rating = view.findViewById(R.id.rating);
        setRating(getArguments().getString("rating"));

        nome.setText(s_nome);
        email.setText(s_email);
        area.setText(s_area);
        distrito.setText(s_distrito);

        return view;
    }

    private void setRating(String rat){
        switch (rat) {
            case "0":
                rating.setImageResource(R.drawable.rating_user0);
                break;

            case "1":
                rating.setImageResource(R.drawable.rating_user1);
                break;

            case "2":
                rating.setImageResource(R.drawable.rating_user2);
                break;

            case "3":
                rating.setImageResource(R.drawable.rating_user3);
                break;

            case "4":
                rating.setImageResource(R.drawable.rating_user4);
                break;

            case "5":
                rating.setImageResource(R.drawable.rating_user5);
                break;
        }
    }

}

