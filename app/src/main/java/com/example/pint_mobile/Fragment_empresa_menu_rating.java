package com.example.pint_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import java.util.HashMap;
import java.util.Map;

public class Fragment_empresa_menu_rating extends Fragment {

    private TextView aviso, nr_rating;
    private String s_nome, s_id_empresa, s_id_cliente, s_rating;
    private ImageView star1, star2, star3, star4, star5;
    private Button guardar;
    private ProgressBar progressBar;
    private String rating_empresa;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_empresa_menu_rating, container, false);

        aviso = view.findViewById(R.id.aviso);
        star1 = view.findViewById(R.id.star1);
        star2 = view.findViewById(R.id.star2);
        star3 = view.findViewById(R.id.star3);
        star4 = view.findViewById(R.id.star4);
        star5 = view.findViewById(R.id.star5);
        nr_rating = view.findViewById(R.id.nr_rating);
        guardar = view.findViewById(R.id.guardar);
        progressBar = view .findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);

        s_nome = getArguments().getString("nome");
        s_id_empresa = getArguments().getString("id_empresa");
        s_id_cliente = getArguments().getString("id_cliente");
        s_rating = getArguments().getString("rating");

        aviso.setText("Avalie a empresa '" + s_nome + "' com uma avaliação de 0 a 5 e desta forma irá ajudar ao crescimento da mesma.");

        atualizar_rating(s_rating);

        star1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                guardar.setVisibility(View.VISIBLE);
                star1.setImageResource(R.drawable.star_sim);
                star2.setImageResource(R.drawable.star_nao);
                star3.setImageResource(R.drawable.star_nao);
                star4.setImageResource(R.drawable.star_nao);
                star5.setImageResource(R.drawable.star_nao);
                nr_rating.setText("1/5");
            }
        });
        star2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                guardar.setVisibility(View.VISIBLE);
                star1.setImageResource(R.drawable.star_sim);
                star2.setImageResource(R.drawable.star_sim);
                star3.setImageResource(R.drawable.star_nao);
                star4.setImageResource(R.drawable.star_nao);
                star5.setImageResource(R.drawable.star_nao);
                nr_rating.setText("2/5");
            }
        });
        star3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                guardar.setVisibility(View.VISIBLE);
                star1.setImageResource(R.drawable.star_sim);
                star2.setImageResource(R.drawable.star_sim);
                star3.setImageResource(R.drawable.star_sim);
                star4.setImageResource(R.drawable.star_nao);
                star5.setImageResource(R.drawable.star_nao);
                nr_rating.setText("3/5");
            }
        });
        star4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                guardar.setVisibility(View.VISIBLE);
                star1.setImageResource(R.drawable.star_sim);
                star2.setImageResource(R.drawable.star_sim);
                star3.setImageResource(R.drawable.star_sim);
                star4.setImageResource(R.drawable.star_sim);
                star5.setImageResource(R.drawable.star_nao);
                nr_rating.setText("4/5");
            }
        });
        star5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                guardar.setVisibility(View.VISIBLE);
                star1.setImageResource(R.drawable.star_sim);
                star2.setImageResource(R.drawable.star_sim);
                star3.setImageResource(R.drawable.star_sim);
                star4.setImageResource(R.drawable.star_sim);
                star5.setImageResource(R.drawable.star_sim);
                nr_rating.setText("5/5");
            }
        });
        guardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String rat = nr_rating.getText().toString();
                String[] parts = rat.split("/");
                rating_empresa = parts[0];
                String url = "https://www.mycards.dsprojects.pt/api/empresa/" + s_id_empresa + "/rating";
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getString("status").equals("true")) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getContext(), "Avaliação enviada com sucesso!", Toast.LENGTH_SHORT).show();
                                        ((Activity_cartao_fidelizado)getActivity()).refreshMyData();
                                    } else {
                                        //progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getContext(), "Erro!", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        ConnectivityManager conMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
                        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
                            Toast.makeText(getContext(), "Sem ligação à Internet!", Toast.LENGTH_LONG).show();
                        } else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            //This indicates that the request has either time out or there is no connection
                            Log.i("VolleyError::", error.toString());
                        } else if (error instanceof AuthFailureError) {
                            //Error indicating that there was an Authentication Failure while performing the request
                            Log.i("AuthFailureError::", error.toString());
                        } else if (error instanceof ServerError) {
                            //Indicates that the server responded with a error response
                            Log.i("ServerError::", error.toString());
                            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NetworkError) {
                            //Indicates that there was network error while performing the request
                            Log.i("NetworkError::", error.toString());
                            Toast.makeText(getContext(), "Sem ligação à Internet!", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            //Indicates that the server response could not be parsed
                            Log.i("ParseError::", error.toString());
                        }
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("idCliente", s_id_cliente);
                        params.put("rating", rating_empresa);
                        return params;
                    }
                };
                // requestQueue
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(postRequest);
            }
        });
        return view;
    }

    private void atualizar_rating(String rating){
        switch(rating){
            case"0":
                star1.setImageResource(R.drawable.star_nao);
                star2.setImageResource(R.drawable.star_nao);
                star3.setImageResource(R.drawable.star_nao);
                star4.setImageResource(R.drawable.star_nao);
                star5.setImageResource(R.drawable.star_nao);
                nr_rating.setText("-");
                guardar.setVisibility(View.GONE);
                break;

            case"1":
                star1.setImageResource(R.drawable.star_sim);
                star2.setImageResource(R.drawable.star_nao);
                star3.setImageResource(R.drawable.star_nao);
                star4.setImageResource(R.drawable.star_nao);
                star5.setImageResource(R.drawable.star_nao);
                nr_rating.setText("1/5");
                break;

            case"2":
                star1.setImageResource(R.drawable.star_sim);
                star2.setImageResource(R.drawable.star_sim);
                star3.setImageResource(R.drawable.star_nao);
                star4.setImageResource(R.drawable.star_nao);
                star5.setImageResource(R.drawable.star_nao);
                nr_rating.setText("2/5");
                break;

            case"3":
                star1.setImageResource(R.drawable.star_sim);
                star2.setImageResource(R.drawable.star_sim);
                star3.setImageResource(R.drawable.star_sim);
                star4.setImageResource(R.drawable.star_nao);
                star5.setImageResource(R.drawable.star_nao);
                nr_rating.setText("3/5");
                break;

            case"4":
                star1.setImageResource(R.drawable.star_sim);
                star2.setImageResource(R.drawable.star_sim);
                star3.setImageResource(R.drawable.star_sim);
                star4.setImageResource(R.drawable.star_sim);
                star5.setImageResource(R.drawable.star_nao);
                nr_rating.setText("4/5");
                break;

            case"5":
                star1.setImageResource(R.drawable.star_sim);
                star2.setImageResource(R.drawable.star_sim);
                star3.setImageResource(R.drawable.star_sim);
                star4.setImageResource(R.drawable.star_sim);
                star5.setImageResource(R.drawable.star_sim);
                nr_rating.setText("5/5");
                break;
        }
    }
}