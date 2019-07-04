package com.example.pint_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Dialog_fidelizar_empresa extends DialogFragment {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button cancel, fidelizar;
    String valor;
    int nCarimbos = 0, nCupoes = 0, nPontos = 0, x;
    TextView tvCarimbos, tvPontos, tvCupoes;
    private ImageView rating;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_fidelizar_empresa, container, false);

        cancel = view.findViewById(R.id.voltar);
        fidelizar = view.findViewById(R.id.enviar);

        TextView tvNome = view.findViewById(R.id.nome);
        TextView tvEmail = view.findViewById(R.id.tvEmail);
        TextView tvArea = view.findViewById(R.id.tvArea);
        TextView tvDistrito = view.findViewById(R.id.tvDistrito);

        tvPontos = view.findViewById(R.id.pontos);
        tvCupoes = view.findViewById(R.id.cupoes);
        tvCarimbos = view.findViewById(R.id.carimbos);
        rating = view.findViewById(R.id.rating);

        sharedPreferences = getContext().getSharedPreferences(Activity_login.MyPREFERENCES, Context.MODE_PRIVATE);

        //fechar pop up ao carregar em cancelar
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        fidelizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fidelizarEmpresa(sharedPreferences.getString("Id", ""), getArguments().getString("id"));
            }
        });

        tvNome.setText(getArguments().getString("nome"));
        tvEmail.setText(getArguments().getString("email"));
        tvArea.setText(getArguments().getString("area"));
        tvDistrito.setText(getArguments().getString("distrito"));

        String url = "https://www.mycards.dsprojects.pt/api/empresa/" + getArguments().getString("id") + "/campanha";
        StringRequest getCampanhas = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject campanha = jsonArray.getJSONObject(i);

                        switch (campanha.getString("TipoCampanha")) {
                            case "0":
                                nCupoes++;
                                break;

                            case "1":
                                nCarimbos++;
                                break;

                            case "2":
                                nPontos++;
                                break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Erro nas empresas", Toast.LENGTH_SHORT).show();
                }
                tvPontos.setText(Integer.toString(nPontos));
                tvCupoes.setText(Integer.toString(nCupoes));
                tvCarimbos.setText(Integer.toString(nCarimbos));
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "GET sem sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(getCampanhas);

        calcularRatingEmpresa(getArguments().getString("id"));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width_screen = size.x;
        int height_screen = size.y;

        double w = width_screen * 0.9;
        int width = (int) w;

        double h = height_screen * 0.75;
        int height = (int) h;

        getDialog().getWindow().setLayout(width, height);
        getDialog().getWindow().setGravity(Gravity.CENTER);
    }

    private void calcularRatingEmpresa(String id) {
        String url = "https://www.mycards.dsprojects.pt/api/empresa/" +  id + "/rating";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ratings = new JSONArray(response);
                            if(ratings.length() != 0){
                                for (int i = 0; i < ratings.length(); i++) {
                                    JSONObject r = ratings.getJSONObject(i);
                                    String valor = r.getString("Rating");
                                    Integer integer = Integer.parseInt(valor);
                                    x += integer;
                                }
                                x = x / ratings.length();
                                valor = String.valueOf(x);
                            }
                            else{
                                valor = "0";
                            }
                            setRating(valor);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
        };
        // requestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(postRequest);
    }

    private void setRating(String rat){
        switch (rat) {
            case "0":
                rating.setImageResource(R.drawable.rating_user0);
                break;

            case "1":
                rating.setImageResource(R.drawable.rating_user1);
                break;

            case "2":
                rating.setImageResource(R.drawable.rating_user2);
                break;

            case "3":
                rating.setImageResource(R.drawable.rating_user3);
                break;

            case "4":
                rating.setImageResource(R.drawable.rating_user4);
                break;

            case "5":
                rating.setImageResource(R.drawable.rating_user5);
                break;
        }
    }

    private void fidelizarEmpresa(String idCliente, final String idEmpresa) {
        String url = "https://www.mycards.dsprojects.pt/api/cliente/" + idCliente + "/cartao";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("true")) {
                                Toast.makeText(getContext(), "Obrigado por se fidelizar!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), Activity_feed.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getContext(), "Não foi possivel fidelizar neste momento!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
                params.put("idempresa", idEmpresa);
                return params;
            }
        };
        // requestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(postRequest);
    }
}

