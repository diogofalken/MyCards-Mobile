package com.example.pint_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.LocaleData;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class Fragment_feed_cartoes extends Fragment {

    ListView lv;
    View view;
    ImageView add_cartao;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<Cartao>  listaCartoes = new ArrayList<>();
    Fragment_feed_cartoes.MyAdapter adapter;

    ArrayList<HashMap<String, String>> cardsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feed_cartoes, container, false);

        add_cartao = view.findViewById(R.id.add_cartao);

        sharedPreferences = getContext().getSharedPreferences(Activity_login.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        add_cartao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Activity_listar_empresas.class);
                startActivity(intent);
            }
        });

        //getCards();

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
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject cartao = jsonArray.getJSONObject(i);

                        listaCartoes.add(new Cartao(
                                cartao.getString("ID_Empresa"),
                                cartao.getString("ID_Cartao"),
                                cartao.getString("ID_Cliente"),
                                "continente",
                                "desporto",
                                "20"
                        ));
                    }
                    adapter.notifyDataSetChanged();
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

        /*listaCartoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dialog_fidelizar_empresa dialog = new Dialog_fidelizar_empresa();
                Bundle args = new Bundle();
                args.putString("id", listaEmpresas.get(position).getId());
                args.putString("nome", listaEmpresas.get(position).getNome());
                args.putString("area", listaEmpresas.get(position).getArea());
                dialog.setArguments(args);
                dialog.show(getSupportFragmentManager(), "Dialog_fidelizar_empresa");
            }
        });*/
    }

    class MyAdapter extends ArrayAdapter<Cartao> {

        Context context;

        MyAdapter (Context c,  ArrayList<Cartao> objects) {
            super(c, R.layout.cartao, objects);
            this.context = c;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.cartao, parent, false);

            TextView nomeEmpresa = row.findViewById(R.id.nome);
            TextView areaEmpresa = row.findViewById(R.id.area);
            TextView nrDesoontos = row.findViewById(R.id.nrdescontos);
            TextView id_cliente = row.findViewById(R.id.idcliente);

            nomeEmpresa.setText(getItem(position).getNome());
            areaEmpresa.setText(getItem(position).getArea());
            nrDesoontos.setText(getItem(position).getNr_descontos());
            id_cliente.setText(getItem(position).getId_cliente());

            return row;
        }
    }
}
