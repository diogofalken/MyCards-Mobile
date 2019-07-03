package com.example.pint_mobile;

import android.content.Intent;
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

    ArrayList<HashMap<String, String>> cardsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feed_cartoes, container, false);

        add_cartao = view.findViewById(R.id.add_cartao);

        add_cartao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Activity_listar_empresas.class);
                startActivity(intent);
            }
        });

        //getCards();

        cardsList = new ArrayList<>();
        lv = view.findViewById(R.id.lista_cartoes);

        new GetCards().execute();

        return view;
    }

    private class GetCards extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://www.mycards.dsprojects.pt/api/cliente/1/cartao/";
            String jsonStr = sh.makeServiceCall(url);


            if (jsonStr != null) {
                try {
                    // Getting JSON Array node
                    JSONArray cards = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < cards.length(); i++) {
                        JSONObject c = cards.getJSONObject(i);
                        String id = c.getString("id");
                        String name = c.getString("name");
                        String email = c.getString("email");
                        String address = c.getString("address");
                        String gender = c.getString("gender");

                        // Phone node is JSON Object
                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("name", name);
                        contact.put("email", email);
                        contact.put("mobile", mobile);

                        // adding contact to contact list
                        cardsList.add(contact);
                    }
                } catch (final JSONException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(getContext(), cardsList,
                    R.layout.card, new String[]{ "id"},
                    new int[]{R.id.id});
            lv.setAdapter(adapter);
        }
    }



}
