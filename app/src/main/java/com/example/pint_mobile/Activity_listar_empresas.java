package com.example.pint_mobile;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class Activity_listar_empresas extends AppCompatActivity {
    ListView listView;
    ArrayList<Empresa>  listaEmpresas = new ArrayList<>();
    ArrayList<Cartao_empresa_fidelizada> lista = Activity_feed.cartoesFidelizados;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_empresas);

        //remover action bar
        getSupportActionBar().hide();

        final ImageView button = findViewById(R.id.voltar);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Activity_listar_empresas.this, Activity_feed.class);
                startActivity(intent);
            }
        });

        listView = findViewById(R.id.lista_cartoes);

        String url = "https://www.mycards.dsprojects.pt/api/empresa";
        StringRequest getEmpresas = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject empresa = jsonArray.getJSONObject(i);
                        if(empresaFidelizada(empresa.getString("ID_Empresa")) == false) {
                            listaEmpresas.add(new Empresa(empresa.getString("ID_Empresa"), empresa.getString("Nome"), empresa.getString("AreaInteresse"), empresa.getString("Localizacao"), empresa.getString("Email")));
                        }
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Erro nas empresas", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "GET sem sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(getEmpresas);

        adapter = new MyAdapter(this, listaEmpresas);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dialog_fidelizar_empresa dialog = new Dialog_fidelizar_empresa();
                Bundle args = new Bundle();
                args.putString("id", listaEmpresas.get(position).getId());
                args.putString("nome", listaEmpresas.get(position).getNome());
                args.putString("area", listaEmpresas.get(position).getArea());
                args.putString("distrito", listaEmpresas.get(position).getDistrito());
                args.putString("email", listaEmpresas.get(position).getEmail());
                dialog.setArguments(args);
                dialog.show(getSupportFragmentManager(), "Dialog_fidelizar_empresa");
            }
        });
    }

    class MyAdapter extends ArrayAdapter<Empresa> {

        Context context;

        MyAdapter (Context c,  ArrayList<Empresa> objects) {
            super(c, R.layout.row_empresa, objects);
            this.context = c;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row_empresa, parent, false);
            TextView myTitle = row.findViewById(R.id.nome);
            TextView myDescription = row.findViewById(R.id.area);

            // now set our resources on views
            myTitle.setText(getItem(position).getNome());
            myDescription.setText(getItem(position).getArea());

            return row;
        }
    }

    private boolean empresaFidelizada(String idEmpresa) {
        for(Cartao_empresa_fidelizada cartao : lista ) {
            if(cartao.getId_empresa().equals(idEmpresa)) {
                return  true;
            }
        }
        return false;
    }
}
