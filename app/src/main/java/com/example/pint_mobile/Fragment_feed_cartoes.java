package com.example.pint_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Fragment_feed_cartoes extends Fragment {

    private ListView lv;
    private View view;
    private ImageView add_cartao;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ArrayList<Cartao_empresa_fidelizada>  listaCartoes = Activity_feed.cartoesFidelizados;
    private Fragment_feed_cartoes.MyAdapter adapter;
    private ConstraintLayout cl_sem_cartoes;
    private ArrayList<HashMap<String, String>> cardsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feed_cartoes, container, false);

        add_cartao = view.findViewById(R.id.add_cartao);
        cl_sem_cartoes = view.findViewById(R.id.sem_empresas);

        if(listaCartoes.size() != 0) {
            cl_sem_cartoes.setVisibility(View.GONE);
        }

        sharedPreferences = getContext().getSharedPreferences(Activity_login.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        add_cartao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Activity_listar_empresas.class);
                startActivity(intent);
            }
        });

        cardsList = new ArrayList<>();
        lv = view.findViewById(R.id.lista_descontos);

        adapter = new MyAdapter(getContext(), listaCartoes);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), Activity_cartao_fidelizado.class);
                i.putExtra("cartaoFidelizado", listaCartoes.get(position));
                i.putExtra("idCliente", sharedPreferences.getString("Id", ""));
                i.putParcelableArrayListExtra("campanhas", listaCartoes.get(position).getListaCampanhas());
                startActivity(i);
            }
        });

        return view;
    }

    class MyAdapter extends ArrayAdapter<Cartao_empresa_fidelizada> {

        Context context;

        MyAdapter (Context c,  ArrayList<Cartao_empresa_fidelizada> objects) {
            super(c, R.layout.cartao_empresa_fidelizada, objects);
            this.context = c;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.cartao_empresa_fidelizada, parent, false);

            TextView nomeEmpresa = row.findViewById(R.id.nome);
            TextView areaEmpresa = row.findViewById(R.id.area_empresa);
            TextView nrDescontos = row.findViewById(R.id.nrdescontos);
            TextView distrito = row.findViewById(R.id.distrito);
            ConstraintLayout cl = row.findViewById(R.id.cl);
            TextView email = row.findViewById(R.id.email);
            TextView nrPontos = row.findViewById(R.id.nrpontos);

            String cor = getItem(position).getCor();

            

            switch (getItem(position).getArea()) {
                case "Agricultura":
                    cl.setBackgroundResource(R.drawable.border_cartao_agricultura);
                    break;
                case "Ciência e Tecnologia":
                    cl.setBackgroundResource(R.drawable.border_cartao_ciencia_tecnologia);
                    break;
                case "Desporto":
                    cl.setBackgroundResource(R.drawable.border_cartao_desporto);
                    break;
                case "Educação":
                    cl.setBackgroundResource(R.drawable.border_cartao_educacao);
                    break;
                case "Saúde":
                    cl.setBackgroundResource(R.drawable.border_cartao_saude);
                    break;
                case "Restauração":
                    cl.setBackgroundResource(R.drawable.border_cartao_restauracao);
                    break;
                case "Transportes e Mercadorias":
                    cl.setBackgroundResource(R.drawable.border_cartao_transportes_mercadorias);
                    break;
                case "Turismo":
                    cl.setBackgroundResource(R.drawable.border_cartao_turismo);
                    break;
                default:
                    break;
            }

            nomeEmpresa.setText(getItem(position).getNome());
            areaEmpresa.setText(getItem(position).getArea());
            nrDescontos.setText(getItem(position).getNr_descontos());
            distrito.setText(getItem(position).getDistrito());
            email.setText(getItem(position).getEmail());
            nrPontos.setText(getItem(position).getPontos());

            return row;
        }
    }
}
