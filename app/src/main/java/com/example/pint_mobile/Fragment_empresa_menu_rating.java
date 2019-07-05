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

public class Fragment_empresa_menu_rating extends Fragment {

    private TextView aviso, nr_rating;
    private String s_nome, s_id;
    private String rating = "00000";
    private ImageView star1, star2, star3, star4, star5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_empresa_menu_rating, container, false);

        aviso = view.findViewById(R.id.aviso);
        star1 = view.findViewById(R.id.star1);
        star2 = view.findViewById(R.id.star2);
        star3 = view.findViewById(R.id.star3);
        star4 = view.findViewById(R.id.star4);
        star5 = view.findViewById(R.id.star5);
        nr_rating = view.findViewById(R.id.nr_rating);

        s_nome = getArguments().getString("nome");
        s_id = getArguments().getString("id");

        aviso.setText("Avalie a empresa '" + s_nome + "' com uma avaliação de 0 a 5 e desta forma irá ajudar ao crescimento da mesma.");

        star1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                star1.setImageResource(R.drawable.star_sim);
                star2.setImageResource(R.drawable.star_nao);
                star3.setImageResource(R.drawable.star_nao);
                star4.setImageResource(R.drawable.star_nao);
                star5.setImageResource(R.drawable.star_nao);
                nr_rating.setText("1/5");
            }
        });
        star2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                star1.setImageResource(R.drawable.star_sim);
                star2.setImageResource(R.drawable.star_sim);
                star3.setImageResource(R.drawable.star_nao);
                star4.setImageResource(R.drawable.star_nao);
                star5.setImageResource(R.drawable.star_nao);
                nr_rating.setText("2/5");
            }
        });
        star3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                star1.setImageResource(R.drawable.star_sim);
                star2.setImageResource(R.drawable.star_sim);
                star3.setImageResource(R.drawable.star_sim);
                star4.setImageResource(R.drawable.star_nao);
                star5.setImageResource(R.drawable.star_nao);
                nr_rating.setText("3/5");
            }
        });
        star4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                star1.setImageResource(R.drawable.star_sim);
                star2.setImageResource(R.drawable.star_sim);
                star3.setImageResource(R.drawable.star_sim);
                star4.setImageResource(R.drawable.star_sim);
                star5.setImageResource(R.drawable.star_nao);
                nr_rating.setText("4/5");
            }
        });
        star5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                star1.setImageResource(R.drawable.star_sim);
                star2.setImageResource(R.drawable.star_sim);
                star3.setImageResource(R.drawable.star_sim);
                star4.setImageResource(R.drawable.star_sim);
                star5.setImageResource(R.drawable.star_sim);
                nr_rating.setText("5/5");
            }
        });
        return view;
    }
}