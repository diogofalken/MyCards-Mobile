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

public class Dialog_desconto_qr extends DialogFragment {

    private Button cancelar, notificar;
    private TextView nome;
    private Dialog_loading loading = new Dialog_loading();
    private String id_cliente, id_cartao, id_campanha;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_desconto_qr, container, false);

        cancelar = view.findViewById(R.id.voltar);
        notificar = view.findViewById(R.id.enviar);
        nome = view.findViewById(R.id.nome);

        Bundle args = getArguments();
        nome.setText(args.getString("nome_empresa"));

        id_cliente = args.getString("id_cliente");
        id_campanha = args.getString("id_campanha");
        id_cartao = args.getString("id_cartao");





        //fechar pop up ao carregar em cancelar
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        notificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.show(getFragmentManager(), "Dialog_loading");
                notificar_empresa();
            }
        });


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

        double w = width_screen * 0.8;
        int width = (int) w;

        double h = height_screen * 0.7;
        int height = (int) h;

        getDialog().getWindow().setLayout(width, height);
        getDialog().getWindow().setGravity(Gravity.CENTER);
    }

    private void notificar_empresa(){
        String url = "https://www.mycards.dsprojects.pt/api/cliente/" + id_cliente + "/cartao/" + id_cartao + "/instanciacampanha/" + id_campanha;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("true")) {
                                Toast.makeText(getContext(), "Empresa notificada com sucesso!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getContext(), Activity_feed.class);
                                startActivity(i);
                            } else {
                                loading.dismiss();
                                Toast.makeText(getContext(), "Não foi possível notificar a empresa!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {Toast.makeText(getContext(), "catch", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                            loading.dismiss();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();

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
                params.put("Notificacao", "1");
                return params;
            }
        };
        // requestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(postRequest);
    }
}

