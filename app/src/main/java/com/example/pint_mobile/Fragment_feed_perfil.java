package com.example.pint_mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class Fragment_feed_perfil extends Fragment {

    private TextView nome, distrito, email;
    private TextView nr_descontos, nr_descontos_usados, nr_cartoes;
    private CircleImageView image;
    private ImageView rating;
    SharedPreferences sharedPreferences;
    ArrayList<Cartao_empresa_fidelizada> listaCartoes = Activity_feed.cartoesFidelizados;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_perfil, container, false);

        Bitmap bitmap = new ImageSaver(this.getContext()).
                setFileName("myImage.png").
                setDirectoryName("images").
                load();

        nome = view.findViewById(R.id.nome_utilizador);
        distrito = view.findViewById(R.id.id_email);
        email = view.findViewById(R.id.id_nome);
        image = view.findViewById(R.id.user_pic);
        nr_cartoes = view.findViewById(R.id.nr_cartoes);
        nr_descontos = view.findViewById(R.id.nr_descontos);
        nr_descontos_usados = view.findViewById(R.id.nr_descontos_usados);
        rating = view.findViewById(R.id.rating);

        image.setImageBitmap(bitmap);

        sharedPreferences = getContext().getSharedPreferences(Activity_login.MyPREFERENCES, Context.MODE_PRIVATE);

        nome.setText(sharedPreferences.getString("PrimeiroNome", "") + " " +  sharedPreferences.getString("UltimoNome", ""));
        email.setText(sharedPreferences.getString("Email", ""));
        distrito.setText(sharedPreferences.getString("Localizacao", "") + ", Portugal");
        setRating(sharedPreferences.getString("Rating", ""));
        nr_cartoes.setText(Integer.toString(listaCartoes.size()));

        contarDescontos();
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

    private void contarDescontos() {
        int nDescontos = 0, nDescontosUsados = 0;

        for(Cartao_empresa_fidelizada cartao : listaCartoes) {
            nDescontos += cartao.getListaCampanhas().size();
            nDescontosUsados += Integer.parseInt(cartao.getUtilizacoes());
        }
        nr_descontos.setText(Integer.toString(nDescontos));
        nr_descontos_usados.setText(Integer.toString(nDescontosUsados));
    }
}
