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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Fragment_feed_cartoes extends Fragment {

    private ListView lv;
    private View view;
    private ImageView add_cartao;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ArrayList<Cartao>  listaCartoes = new ArrayList<>();
    private Fragment_feed_cartoes.MyAdapter adapter;
    private ConstraintLayout cl_sem_cartoes;
    private ArrayList<HashMap<String, String>> cardsList;
    private HashMap<String, String> map = new HashMap<String, String>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feed_cartoes, container, false);

        add_cartao = view.findViewById(R.id.add_cartao);
        cl_sem_cartoes = view.findViewById(R.id.sem_cartoes);

        cl_sem_cartoes.setVisibility(View.GONE);

        sharedPreferences = getContext().getSharedPreferences(Activity_login.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        add_cartao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Activity_listar_empresas.class);
                startActivity(intent);
            }
        });

        cardsList = new ArrayList<>();
        lv = view.findViewById(R.id.lista_cartoes);

        carregar_cartoes();


        return view;
    }

    private void carregar_cartoes(){
        String url = "https://www.mycards.dsprojects.pt/api/cliente/" + sharedPreferences.getString("Id", "") + "/cartao";
        StringRequest getEmpresas = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if(jsonArray.length() == 0){
                        cl_sem_cartoes.setVisibility(View.VISIBLE);
                    }
                    else {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject cartao = jsonArray.getJSONObject(i);

                            HashMap<String, String> arrayInf;
                            carregar_informacao_empresa(cartao.getString("ID_Empresa"), cartao.getString("ID_Cartao"), i);
                        }
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Erro nos cartões!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "GET sem sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(getEmpresas);

        adapter = new MyAdapter(getContext(), listaCartoes);
        lv.setAdapter(adapter);
    }

    void carregar_informacao_empresa(final String id, final String idCartao, final int index){
        String url_inf = "https://www.mycards.dsprojects.pt/api/empresa/" + id;
        StringRequest getDadosEmpresa = new StringRequest(Request.Method.GET, url_inf, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray_inf = new JSONArray(response);
                    JSONObject dados = jsonArray_inf.getJSONObject(0);
                    String localizacao = dados.getString("Localizacao");
                    String AreaInteresse = dados.getString("AreaInteresse");
                    String nome = dados.getString("Nome");
                    String cor = "";
                    String email = dados.getString("Email");
                    switch (AreaInteresse.toString()) {
                        case "Agricultura":
                            cor = "#006600";
                            break;
                        case "Ciência e Tecnologia":
                            cor = "#042C54";
                            break;
                        case "Desporto":
                            cor = "#4d004d";
                            break;
                        case "Educação":
                            cor = "#662200";
                            break;
                        case "Saúde":
                            cor = "#5C7993";
                            break;
                        case "Restauração":
                            cor = "#BD8E02";
                            break;
                        case "Transportes e Mercadorias":
                            cor = "#3F51B5";
                            break;
                        case "Turismo":
                            cor = "#E91E63";
                            break;
                        default:
                            cor = "#5A613A";
                    }
                        listaCartoes.add(new Cartao(
                                id,
                                idCartao,
                                localizacao,
                                nome,
                                AreaInteresse,
                                "123",
                                cor,
                                email
                        ));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Erro nos dados da empresa!", Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "GET sem sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue_inf = Volley.newRequestQueue(getContext());
        requestQueue_inf.add(getDadosEmpresa);
    }

    class MyAdapter extends ArrayAdapter<Cartao> {

        Context context;

        MyAdapter (Context c,  ArrayList<Cartao> objects) {
            super(c, R.layout.cartao_empresa_fidelizada, objects);
            this.context = c;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.cartao_empresa_fidelizada, parent, false);

            TextView nomeEmpresa = row.findViewById(R.id.nome);
            TextView areaEmpresa = row.findViewById(R.id.area);
            TextView nrDescontos = row.findViewById(R.id.email);
            TextView distrito = row.findViewById(R.id.distrito);
            ConstraintLayout cl = row.findViewById(R.id.cl);
            TextView email = row.findViewById(R.id.email);

            String cor = getItem(position).getCor();

            nomeEmpresa.setText(getItem(position).getNome());
            areaEmpresa.setText(getItem(position).getArea());
            nrDescontos.setText(getItem(position).getNr_descontos() + " descontos");
            distrito.setText(getItem(position).getDistrito());
            cl.setBackgroundColor(Color.parseColor(cor));
            email.setText(getItem(position).getEmail());

            return row;
        }
    }
}
