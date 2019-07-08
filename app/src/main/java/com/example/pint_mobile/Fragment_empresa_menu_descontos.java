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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_empresa_menu_descontos extends Fragment {

    private ArrayList<Campanha> listaCampanhas = Activity_cartao_fidelizado.campanhas;
    private ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_empresa_menu_descontos, container, false);

        ListView lv = view.findViewById(R.id.listaDescontos);

        sharedPreferences = getContext().getSharedPreferences(Activity_login.MyPREFERENCES, Context.MODE_PRIVATE);

        if(listaCampanhas.size() != 0) {
            view.findViewById(R.id.textView7).setVisibility(View.GONE);
        }

        MyDescontosAdapter adapter = new MyDescontosAdapter(getContext(), listaCampanhas);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle arg = new Bundle();
                arg.putString("nome_empresa", listaCampanhas.get(position).getNomeEmpresa());
                arg.putString("id_cliente", sharedPreferences.getString("Id", ""));
                arg.putString("id_campanha", listaCampanhas.get(position).getIdCampanha());
                arg.putString("id_cartao", listaCampanhas.get(position).getId_cartao());
                Dialog_desconto_qr dialog = new Dialog_desconto_qr();
                dialog.setArguments(arg);
                dialog.setTargetFragment(Fragment_empresa_menu_descontos.this, 1);
                dialog.show(getFragmentManager(), "Dialog_desconto_qr");
            }
        });

        return view;
    }


    class MyDescontosAdapter extends ArrayAdapter<Campanha> {

        Context context;

        MyDescontosAdapter(Context c, ArrayList<Campanha> objects) {
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
            Campanha campanha = listaCampanhas.get(position);
            switch (campanha.getTipoCampanha()) {
                case "0":
                    row = layoutInflater.inflate(R.layout.desconto_cupoes, parent, false);
                    tvNomeEmpresa = row.findViewById(R.id.nome);
                    tvDescricao = row.findViewById(R.id.area_empresa);
                    tvDesignacao = row.findViewById(R.id.designacao);
                    tvValor = row.findViewById(R.id.valor);
                    tvDataFim = row.findViewById(R.id.dataFim);
                    cl_cupoes = row.findViewById(R.id.cl);

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


                    cl_carimbos.setBackgroundColor(Color.parseColor(campanha.getCor()));

                    //atualizar o nr de carimbos
                    switch (nr_carimbos){
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

    private void colocar_carimbos(int v1, int v2, int v3, int v4, int v5, int v6, int v7, int v8, int v9, int v10 ){
        if(v1 == 0)
            img1.setImageResource(R.drawable.carimbo1);
        else
            img1.setImageResource(R.drawable.carimbado);

        if(v2 == 0)
            img2.setImageResource(R.drawable.carimbo2);
        else
            img2.setImageResource(R.drawable.carimbado);

        if(v3 == 0)
            img3.setImageResource(R.drawable.carimbo3);
        else
            img3.setImageResource(R.drawable.carimbado);

        if(v4 == 0)
            img4.setImageResource(R.drawable.carimbo4);
        else
            img4.setImageResource(R.drawable.carimbado);

        if(v5 == 0)
            img5.setImageResource(R.drawable.carimbo5);
        else
            img5.setImageResource(R.drawable.carimbado);

        if(v6 == 0)
            img6.setImageResource(R.drawable.carimbo6);
        else
            img6.setImageResource(R.drawable.carimbado);

        if(v7 == 0)
            img7.setImageResource(R.drawable.carimbo7);
        else
            img7.setImageResource(R.drawable.carimbado);

        if(v8 == 0)
            img8.setImageResource(R.drawable.carimbo8);
        else
            img8.setImageResource(R.drawable.carimbado);

        if(v9 == 0)
            img9.setImageResource(R.drawable.carimbo9);
        else
            img9.setImageResource(R.drawable.carimbado);

        if(v10 == 0)
            img10.setImageResource(R.drawable.carimbo10);
        else
            img10.setImageResource(R.drawable.carimbado);
    }
}