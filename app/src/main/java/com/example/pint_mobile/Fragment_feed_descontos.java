package com.example.pint_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class Fragment_feed_descontos extends Fragment {
    private ListView lv;
    private MyDescontosAdapter adapter;
    private ArrayList<Cartao_empresa_fidelizada> lista = Activity_feed.cartoesFidelizados;
    private ArrayList<Campanha> listaDescontos = new ArrayList<>();
    private String tipos = Dialog_filtros_descontos.tipos;
    private String filtrosArea = Dialog_filtros_descontos.areasInteresse;
    private ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10;
    private String nome_empresa = "", id_cartao = "", id_campanha = "", id_empresa = "";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_feed_descontos, container, false);
        final TextView barra_pesquisa = view.findViewById(R.id.barra_pesquisa);

        sharedPreferences = getContext().getSharedPreferences(Activity_login.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        lv = view.findViewById(R.id.lista_descontos);
        listaDescontos.clear();
        for(Cartao_empresa_fidelizada cartao : lista) {
            for(Campanha campanha : cartao.getListaCampanhas()) {
                campanha.setNomeEmpresa(cartao.getNome());
                campanha.setAreaInteresse(cartao.getArea());
                campanha.setUtilizacoes(cartao.getUtilizacoes());
                campanha.setCor(cartao.getCor());
                listaDescontos.add(campanha);
            }
        }

        aplicarFiltrosTipo(tipos);
        aplicarFiltrosArea(filtrosArea);
        if(listaDescontos.size() != 0) {
            view.findViewById(R.id.sem_empresas).setVisibility(View.GONE);
        }

        adapter = new MyDescontosAdapter(getContext(), listaDescontos);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle arg = new Bundle();
                arg.putString("nome_empresa", nome_empresa);
                arg.putString("id_cliente", sharedPreferences.getString("Id", ""));
                arg.putString("id_campanha", id_campanha);
                arg.putString("id_cartao", id_cartao);
                Dialog_desconto_qr dialog = new Dialog_desconto_qr();
                dialog.setArguments(arg);
                dialog.setTargetFragment(Fragment_feed_descontos.this, 1);
                dialog.show(getFragmentManager(), "Dialog_desconto_qr");
            }
        });

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
            TextView tvNomeEmpresa, tvDescricao, tvDesignacao, tvValor, tvDataFim;
            String nr_carimbos;
            ConstraintLayout cl_carimbos, cl_cupoes, cl_pontos;
            Campanha campanha = getItem(position);
            switch (campanha.getTipoCampanha()) {
                case "0":
                    row = layoutInflater.inflate(R.layout.desconto_cupoes, parent, false);
                    tvNomeEmpresa = row.findViewById(R.id.nome);
                    tvDescricao = row.findViewById(R.id.area_empresa);
                    tvDesignacao = row.findViewById(R.id.designacao);
                    tvValor = row.findViewById(R.id.valor);
                    tvDataFim = row.findViewById(R.id.dataFim);
                    cl_cupoes = row.findViewById(R.id.cl);
                    nome_empresa = campanha.getNomeEmpresa();
                    id_campanha = campanha.getIdCampanha();
                    id_cartao = campanha.getId_cartao();
                    id_empresa = campanha.getId_empresa();

                    cl_cupoes.setBackgroundColor(Color.parseColor(campanha.getCor()));
                    tvNomeEmpresa.setText(campanha.getNomeEmpresa());
                    tvDescricao.setText(campanha.getDesignacao());
                    tvDesignacao.setText(campanha.getDescricao());
                    tvValor.setText(campanha.getValor() + "%");
                    tvDataFim.setText(campanha.getDataFim());

                    break;
                case "1":
                    row = layoutInflater.inflate(R.layout.desconto_carimbos, parent, false);

                    tvNomeEmpresa = row.findViewById(R.id.nome);
                    tvDescricao = row.findViewById(R.id.area_empresa);
                    tvDataFim = row.findViewById(R.id.data_fim);
                    cl_carimbos = row.findViewById(R.id.cl);
                    nr_carimbos = campanha.getUtilizacoes();
                    img1 = row.findViewById(R.id.carimbo1);
                    img2 = row.findViewById(R.id.carimbo2);
                    img3 = row.findViewById(R.id.carimbo3);
                    img4 = row.findViewById(R.id.carimbo4);
                    img5 = row.findViewById(R.id.carimbo5);
                    img6 = row.findViewById(R.id.carimbo6);
                    img7 = row.findViewById(R.id.carimbo7);
                    img8 = row.findViewById(R.id.carimbo8);
                    img9 = row.findViewById(R.id.carimbo9);
                    img10 = row.findViewById(R.id.carimbo10);
                    nome_empresa = campanha.getNomeEmpresa();
                    id_campanha = campanha.getIdCampanha();
                    id_cartao = campanha.getId_cartao();
                    id_empresa = campanha.getId_empresa();

                    //Toast.makeText(getContext(), nr_carimbos, Toast.LENGTH_SHORT).show();


                    cl_carimbos.setBackgroundColor(Color.parseColor(campanha.getCor()));

                    //atualizar o nr de carimbos
                    switch (nr_carimbos.toString()){
                        case "0":
                            colocar_carimbos(0,0,0,0,0,0,0,0,0,0);
                            break;
                        case "1":
                            colocar_carimbos(1,0,0,0,0,0,0,0,0,0);
                            break;
                        case "2":
                            colocar_carimbos(1,1,0,0,0,0,0,0,0,0);
                            break;
                        case "3":
                            colocar_carimbos(1,1,1,0,0,0,0,0,0,0);
                            break;
                        case "4":
                            colocar_carimbos(1,1,1,1,0,0,0,0,0,0);
                            break;
                        case "5":
                            colocar_carimbos(1,1,1,1,1,0,0,0,0,0);
                            break;
                        case "6":
                            colocar_carimbos(1,1,1,1,1,1,0,0,0,0);
                            break;
                        case "7":
                            colocar_carimbos(1,1,1,1,1,1,1,0,0,0);
                            break;
                        case "8":
                            colocar_carimbos(1,1,1,1,1,1,1,1,0,0);
                            break;
                        case "9":
                            colocar_carimbos(1,1,1,1,1,1,1,1,1,0);
                            break;
                        case "10":
                            colocar_carimbos(1,1,1,1,1,1,1,1,1,1);
                            break;


                    }

                    tvNomeEmpresa.setText(campanha.getNomeEmpresa());
                    tvDescricao.setText(campanha.getDesignacao());
                    tvDataFim.setText(campanha.getDataFim());

                    break;
                case "2":
                    row = layoutInflater.inflate(R.layout.desconto_pontos, parent, false);

                    tvNomeEmpresa = row.findViewById(R.id.nome);
                    tvDescricao = row.findViewById(R.id.area_empresa);
                    tvDesignacao = row.findViewById(R.id.designacao);
                    tvValor = row.findViewById(R.id.valor);
                    tvDataFim = row.findViewById(R.id.data_Fim);
                    cl_pontos = row.findViewById(R.id.cl);
                    nome_empresa = campanha.getNomeEmpresa();
                    id_campanha = campanha.getIdCampanha();
                    id_cartao = campanha.getId_cartao();
                    id_empresa = campanha.getId_empresa();

                    cl_pontos.setBackgroundColor(Color.parseColor(campanha.getCor()));
                    tvNomeEmpresa.setText(campanha.getNomeEmpresa());
                    tvDescricao.setText(campanha.getDesignacao());
                    tvDesignacao.setText(campanha.getDescricao());
                    tvValor.setText(campanha.getValor() + " pontos");
                    tvDataFim.setText(campanha.getDataFim());
                    break;
            }
            return row;
        }
    }

    public void aplicarFiltrosTipo(String tipos) {
        if(tipos.charAt(0) == '1') {
            return;
        }

        // Se nao quiser cartoes de pontos
        if(tipos.charAt(1) == '0') {
            Iterator<Campanha> itr = listaDescontos.iterator();
            while (itr.hasNext()) {
                Campanha desconto = itr.next();
                if (desconto.getTipoCampanha().equals("2")) {
                    itr.remove();
                }
            }
        }

        // Se nao quiser cupoes
        if(tipos.charAt(2) == '0') {
            Iterator<Campanha> itr = listaDescontos.iterator();
            while (itr.hasNext()) {
                Campanha desconto = itr.next();
                if (desconto.getTipoCampanha().equals("0")) {
                    itr.remove();
                }
            }
        }

        // Se nao quiser cartoes de carimbos
        if(tipos.charAt(3) == '0') {
            Iterator<Campanha> itr = listaDescontos.iterator();
            while (itr.hasNext()) {
                Campanha desconto = itr.next();
                if (desconto.getTipoCampanha().equals("1")) {
                    itr.remove();
                }
            }
        }
    }

    public void aplicarFiltrosArea(String filtrosArea) {
        if(filtrosArea.charAt(0) == '1') {
            return;
        }

        if(filtrosArea.charAt(1) == '0' ) {
            removerDaLista("Agricultura");
        }

        if(filtrosArea.charAt(2) == '0' ) {
            removerDaLista("Ciência e Tecnologia");
        }

        if(filtrosArea.charAt(3) == '0' ) {
            removerDaLista("Desporto");
        }

        if(filtrosArea.charAt(4) == '0' ) {
            removerDaLista("Educação");
        }

        if(filtrosArea.charAt(5) == '0' ) {
            removerDaLista("Saúde");
        }

        if(filtrosArea.charAt(6) == '0' ) {
            removerDaLista("Restauração");
        }

        if(filtrosArea.charAt(7) == '0' ) {
            removerDaLista("Transportes e Mercadorias");
        }

        if(filtrosArea.charAt(8) == '0' ) {
            removerDaLista("Turismo");
        }
    }

    public void removerDaLista(String area) {

        Iterator<Campanha> itr = listaDescontos.iterator();
        while (itr.hasNext()) {
            Campanha desconto = itr.next();
            if (desconto.getAreaInteresse().equals(area)) {
                itr.remove();
            }
        }
    }

    private void colocar_carimbos(int v1, int v2, int v3, int v4, int v5, int v6, int v7, int v8, int v9, int v10 ){
        if(v1 == 0)
            img1.setImageResource(R.drawable.carimbo1);
        else
            img1.setImageResource(R.drawable.carimbado);

        if(v2 == 0)
            img2.setImageResource(R.drawable.carimbo2);
        else
            img1.setImageResource(R.drawable.carimbado);

        if(v3 == 0)
            img3.setImageResource(R.drawable.carimbo3);
        else
            img1.setImageResource(R.drawable.carimbado);

        if(v4 == 0)
            img4.setImageResource(R.drawable.carimbo4);
        else
            img1.setImageResource(R.drawable.carimbado);

        if(v5 == 0)
            img5.setImageResource(R.drawable.carimbo5);
        else
            img1.setImageResource(R.drawable.carimbado);

        if(v6 == 0)
            img6.setImageResource(R.drawable.carimbo6);
        else
            img1.setImageResource(R.drawable.carimbado);

        if(v7 == 0)
            img7.setImageResource(R.drawable.carimbo7);
        else
            img1.setImageResource(R.drawable.carimbado);

        if(v8 == 0)
            img8.setImageResource(R.drawable.carimbo8);
        else
            img1.setImageResource(R.drawable.carimbado);

        if(v9 == 0)
            img9.setImageResource(R.drawable.carimbo9);
        else
            img1.setImageResource(R.drawable.carimbado);

        if(v10 == 0)
            img10.setImageResource(R.drawable.carimbo10);
        else
            img1.setImageResource(R.drawable.carimbado);
    }
 }
