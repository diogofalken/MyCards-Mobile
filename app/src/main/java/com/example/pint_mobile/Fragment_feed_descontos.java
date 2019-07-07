package com.example.pint_mobile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Fragment_feed_descontos extends Fragment {
    private ListView lv;
    private MyDescontosAdapter adapter;
    private ArrayList<Cartao_empresa_fidelizada> lista = Activity_feed.cartoesFidelizados;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_feed_descontos, container, false);
        final TextView barra_pesquisa = view.findViewById(R.id.barra_pesquisa);

        lv = view.findViewById(R.id.lista_descontos);

        ArrayList<Campanha> teste = new ArrayList<>();
        teste.add(new Campanha("1", "Iphone", "Iphone", "2019/20/20", "2019/20/20", "50", "0"));

        adapter = new MyDescontosAdapter(getContext(), lista.get(0).getListaCampanhas());
        lv.setAdapter(adapter);

        return view;
    }

    class MyDescontosAdapter extends ArrayAdapter<Campanha> {

        Context context;

        MyDescontosAdapter (Context c,  ArrayList<Campanha> objects) {
            super(c, R.layout.desconto_cupoes, objects);
            this.context = c;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = null;
            TextView tvNomeEmpresa, tvDescricao, tvDesignacao, tvValor;
            Campanha campanha = getItem(position);
            switch (campanha.getTipoCampanha().toString()) {
                case "0":
                    row = layoutInflater.inflate(R.layout.desconto_cupoes, parent, false);
                    tvNomeEmpresa = row.findViewById(R.id.nome_empresa);
                    tvDescricao = row.findViewById(R.id.area_empresa);
                    tvDesignacao = row.findViewById(R.id.designacao);
                    tvValor = row.findViewById(R.id.valor);

                    tvNomeEmpresa.setText("JEFF");
                    tvDescricao.setText(campanha.getDescricao());
                    tvDesignacao.setText(campanha.getDesignacao());
                    tvValor.setText(campanha.getValor());
                    break;
                case "1":
                    row = layoutInflater.inflate(R.layout.desconto_carimbos, parent, false);

                    tvNomeEmpresa = row.findViewById(R.id.nome_empresa);
                    tvDescricao = row.findViewById(R.id.area_empresa);
                    tvDesignacao = row.findViewById(R.id.designacao);
                    tvValor = row.findViewById(R.id.valor);

                    tvNomeEmpresa.setText("JEFF");
                    tvDescricao.setText(campanha.getDescricao());
                    tvDesignacao.setText(campanha.getDesignacao());
                    tvValor.setText(campanha.getValor());
                    break;
                case "2":
                    row = layoutInflater.inflate(R.layout.desconto_pontos, parent, false);

                    tvNomeEmpresa = row.findViewById(R.id.nome_empresa);
                    tvDescricao = row.findViewById(R.id.area_empresa);

                    tvNomeEmpresa.setText("JEFF");
                    tvDescricao.setText(campanha.getDescricao());
                    break;
            }
            return row;
        }
    }
 }
