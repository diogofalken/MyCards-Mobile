package com.example.pint_mobile;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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
import java.util.Iterator;

public class Fragment_feed_descontos extends Fragment {
    private ListView lv;
    private MyDescontosAdapter adapter;
    private ArrayList<Cartao_empresa_fidelizada> lista = Activity_feed.cartoesFidelizados;
    private ArrayList<Campanha> listaDescontos = new ArrayList<>();
    private String tipos = Dialog_filtros_descontos.tipos;
    private String filtrosArea = Dialog_filtros_descontos.areasInteresse;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_feed_descontos, container, false);
        final TextView barra_pesquisa = view.findViewById(R.id.barra_pesquisa);

        lv = view.findViewById(R.id.lista_descontos);
        listaDescontos.clear();
        for(Cartao_empresa_fidelizada cartao : lista) {
            for(Campanha campanha : cartao.getListaCampanhas()) {
                campanha.setNomeEmpresa(cartao.getNome());
                campanha.setAreaInteresse(cartao.getArea());
                campanha.setUtilizacoes(cartao.getUtilizacoes());
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

                    //dar cor consoante a area empresa
                    switch (campanha.getAreaInteresse()) {
                        case "Agricultura":
                            cl_cupoes.setBackgroundColor(Color.parseColor("#006600"));
                            break;
                        case "Ciência e Tecnologia":
                            cl_cupoes.setBackgroundColor(Color.parseColor("#042C54"));
                            break;
                        case "Desporto":
                            cl_cupoes.setBackgroundColor(Color.parseColor("#4d004d"));
                            break;
                        case "Educação":
                            cl_cupoes.setBackgroundColor(Color.parseColor("#662200"));
                            break;
                        case "Saúde":
                            cl_cupoes.setBackgroundColor(Color.parseColor("#5C7993"));
                            break;
                        case "Restauração":
                            cl_cupoes.setBackgroundColor(Color.parseColor("#BD8E02"));
                            break;
                        case "Transportes e Mercadorias":
                            cl_cupoes.setBackgroundColor(Color.parseColor("#3F51B5"));
                            break;
                        case "Turismo":
                            cl_cupoes.setBackgroundColor(Color.parseColor("#E91E63"));
                            break;
                        default:
                            break;
                    }

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
                    Toast.makeText(getContext(), nr_carimbos, Toast.LENGTH_SHORT).show();

                    //dar cor consoante a area empresa
                    switch (campanha.getAreaInteresse()) {
                        case "Agricultura":
                            cl_carimbos.setBackgroundColor(Color.parseColor("#006600"));
                            break;
                        case "Ciência e Tecnologia":
                            cl_carimbos.setBackgroundColor(Color.parseColor("#042C54"));
                            break;
                        case "Desporto":
                            cl_carimbos.setBackgroundColor(Color.parseColor("#4d004d"));
                            break;
                        case "Educação":
                            cl_carimbos.setBackgroundColor(Color.parseColor("#662200"));
                            break;
                        case "Saúde":
                            cl_carimbos.setBackgroundColor(Color.parseColor("#5C7993"));
                            break;
                        case "Restauração":
                            cl_carimbos.setBackgroundColor(Color.parseColor("#BD8E02"));
                            break;
                        case "Transportes e Mercadorias":
                            cl_carimbos.setBackgroundColor(Color.parseColor("#3F51B5"));
                            break;
                        case "Turismo":
                            cl_carimbos.setBackgroundColor(Color.parseColor("#E91E63"));
                            break;
                        default:
                            break;
                    }

                    //atualizar o nr de carimbos
                    switch (nr_carimbos){
                        case "0":
                            row.findViewById(R.id.carimbo1).setBackgroundResource(R.drawable.carimbo1);
                            row.findViewById(R.id.carimbo2).setBackgroundResource(R.drawable.carimbo2);
                            row.findViewById(R.id.carimbo3).setBackgroundResource(R.drawable.carimbo3);
                            row.findViewById(R.id.carimbo4).setBackgroundResource(R.drawable.carimbo4);
                            row.findViewById(R.id.carimbo5).setBackgroundResource(R.drawable.carimbo5);
                            row.findViewById(R.id.carimbo6).setBackgroundResource(R.drawable.carimbo6);
                            row.findViewById(R.id.carimbo7).setBackgroundResource(R.drawable.carimbo7);
                            row.findViewById(R.id.carimbo8).setBackgroundResource(R.drawable.carimbo8);
                            row.findViewById(R.id.carimbo9).setBackgroundResource(R.drawable.carimbo9);
                            row.findViewById(R.id.carimbo10).setBackgroundResource(R.drawable.carimbo10);
                            break;
                        case "1":
                            row.findViewById(R.id.carimbo1).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo2).setBackgroundResource(R.drawable.carimbo2);
                            row.findViewById(R.id.carimbo3).setBackgroundResource(R.drawable.carimbo3);
                            row.findViewById(R.id.carimbo4).setBackgroundResource(R.drawable.carimbo4);
                            row.findViewById(R.id.carimbo5).setBackgroundResource(R.drawable.carimbo5);
                            row.findViewById(R.id.carimbo6).setBackgroundResource(R.drawable.carimbo6);
                            row.findViewById(R.id.carimbo7).setBackgroundResource(R.drawable.carimbo7);
                            row.findViewById(R.id.carimbo8).setBackgroundResource(R.drawable.carimbo8);
                            row.findViewById(R.id.carimbo9).setBackgroundResource(R.drawable.carimbo9);
                            row.findViewById(R.id.carimbo10).setBackgroundResource(R.drawable.carimbo10);
                            break;
                        case "2":
                            row.findViewById(R.id.carimbo1).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo2).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo3).setBackgroundResource(R.drawable.carimbo3);
                            row.findViewById(R.id.carimbo4).setBackgroundResource(R.drawable.carimbo4);
                            row.findViewById(R.id.carimbo5).setBackgroundResource(R.drawable.carimbo5);
                            row.findViewById(R.id.carimbo6).setBackgroundResource(R.drawable.carimbo6);
                            row.findViewById(R.id.carimbo7).setBackgroundResource(R.drawable.carimbo7);
                            row.findViewById(R.id.carimbo8).setBackgroundResource(R.drawable.carimbo8);
                            row.findViewById(R.id.carimbo9).setBackgroundResource(R.drawable.carimbo9);
                            row.findViewById(R.id.carimbo10).setBackgroundResource(R.drawable.carimbo10);
                            break;
                        case "3":
                            row.findViewById(R.id.carimbo1).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo2).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo3).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo4).setBackgroundResource(R.drawable.carimbo4);
                            row.findViewById(R.id.carimbo5).setBackgroundResource(R.drawable.carimbo5);
                            row.findViewById(R.id.carimbo6).setBackgroundResource(R.drawable.carimbo6);
                            row.findViewById(R.id.carimbo7).setBackgroundResource(R.drawable.carimbo7);
                            row.findViewById(R.id.carimbo8).setBackgroundResource(R.drawable.carimbo8);
                            row.findViewById(R.id.carimbo9).setBackgroundResource(R.drawable.carimbo9);
                            row.findViewById(R.id.carimbo10).setBackgroundResource(R.drawable.carimbo10);
                            break;
                        case "4":
                            row.findViewById(R.id.carimbo1).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo2).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo3).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo4).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo5).setBackgroundResource(R.drawable.carimbo5);
                            row.findViewById(R.id.carimbo6).setBackgroundResource(R.drawable.carimbo6);
                            row.findViewById(R.id.carimbo7).setBackgroundResource(R.drawable.carimbo7);
                            row.findViewById(R.id.carimbo8).setBackgroundResource(R.drawable.carimbo8);
                            row.findViewById(R.id.carimbo9).setBackgroundResource(R.drawable.carimbo9);
                            row.findViewById(R.id.carimbo10).setBackgroundResource(R.drawable.carimbo10);
                            break;
                        case "5":
                            row.findViewById(R.id.carimbo1).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo2).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo3).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo4).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo5).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo6).setBackgroundResource(R.drawable.carimbo6);
                            row.findViewById(R.id.carimbo7).setBackgroundResource(R.drawable.carimbo7);
                            row.findViewById(R.id.carimbo8).setBackgroundResource(R.drawable.carimbo8);
                            row.findViewById(R.id.carimbo9).setBackgroundResource(R.drawable.carimbo9);
                            row.findViewById(R.id.carimbo10).setBackgroundResource(R.drawable.carimbo10);
                            break;
                        case "6":
                            row.findViewById(R.id.carimbo1).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo2).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo3).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo4).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo5).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo6).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo7).setBackgroundResource(R.drawable.carimbo7);
                            row.findViewById(R.id.carimbo8).setBackgroundResource(R.drawable.carimbo8);
                            row.findViewById(R.id.carimbo9).setBackgroundResource(R.drawable.carimbo9);
                            row.findViewById(R.id.carimbo10).setBackgroundResource(R.drawable.carimbo10);
                            break;
                        case "7":
                            row.findViewById(R.id.carimbo1).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo2).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo3).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo4).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo5).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo6).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo7).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo8).setBackgroundResource(R.drawable.carimbo8);
                            row.findViewById(R.id.carimbo9).setBackgroundResource(R.drawable.carimbo9);
                            row.findViewById(R.id.carimbo10).setBackgroundResource(R.drawable.carimbo10);
                            break;
                        case "8":
                            row.findViewById(R.id.carimbo1).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo2).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo3).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo4).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo5).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo6).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo7).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo8).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo9).setBackgroundResource(R.drawable.carimbo9);
                            row.findViewById(R.id.carimbo10).setBackgroundResource(R.drawable.carimbo10);
                            break;
                        case "9":
                            row.findViewById(R.id.carimbo1).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo2).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo3).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo4).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo5).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo6).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo7).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo8).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo9).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo10).setBackgroundResource(R.drawable.carimbo10);
                            break;
                        case "10":
                            row.findViewById(R.id.carimbo1).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo2).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo3).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo4).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo5).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo6).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo7).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo8).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo9).setBackgroundResource(R.drawable.carimbado);
                            row.findViewById(R.id.carimbo10).setBackgroundResource(R.drawable.carimbado);
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

                    tvNomeEmpresa.setText(campanha.getNomeEmpresa());
                    tvDescricao.setText(campanha.getDesignacao());
                    tvDesignacao.setText(campanha.getDescricao());
                    tvValor.setText(campanha.getValor());
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
 }
