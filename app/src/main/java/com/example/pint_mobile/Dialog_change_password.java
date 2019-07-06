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
import android.widget.EditText;
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

public class Dialog_change_password extends DialogFragment {


    Button cancel, ok;
    SharedPreferences sharedPreferences;
    private String info;
    String senhaAtualValor, senhaNovaValor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_change_password, container, false);

        cancel = view.findViewById(R.id.tentar);
        ok = view.findViewById(R.id.enviar);

        sharedPreferences = getContext().getSharedPreferences(Activity_login.MyPREFERENCES, Context.MODE_PRIVATE);

        //fechar pop up ao carregar em cancelar
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        //Atualizar Password
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText senhaAtual = (EditText) view.findViewById(R.id.senha_atual);
                senhaAtualValor = senhaAtual.getText().toString();

                EditText senhaNova = (EditText) view.findViewById(R.id.senha_nova);
                senhaNovaValor = senhaNova.getText().toString();

                EditText senhaNovaAgain = (EditText) view.findViewById(R.id.senha_nova_again);
                String senhaNovaAgainValor = senhaNovaAgain.getText().toString();

                if(!senhaNovaValor.equals(senhaNovaAgainValor)) {
                    Toast.makeText(getActivity().getApplicationContext(), "Password não são iguais!", Toast.LENGTH_SHORT).show();
                } else if(senhaAtual.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Password Antiga Errada!", Toast.LENGTH_SHORT).show();
                }
                else {
                String url = "https://www.mycards.dsprojects.pt/authentication/updatePassword_cliente";
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if(jsonObject.getString("status").equals("true")) {
                                        Toast.makeText(getActivity().getApplicationContext(), "Password alterada com sucesso!", Toast.LENGTH_SHORT).show();
                                        dismiss();
                                    } else {
                                        Toast.makeText(getActivity().getApplicationContext(), "Password não alterada!", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    ConnectivityManager conMgr = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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
                        params.put("email", sharedPreferences.getString("Email", ""));
                        params.put("prepassword",senhaAtualValor);
                        params.put("password", senhaNovaValor);
                        return params;
                    }
                };
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(postRequest);
            }

            }
        });

        return view;
    }

    @Override
    public void onResume() { //adaptar width e height do dialogFragment de acordo com a tela android
        super.onResume();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width_screen = size.x;
        int height_screen = size.y;

        double w = width_screen * 0.9;
        int width = (int) w;

        double h = height_screen * 0.53;
        int height = (int) h;

        getDialog().getWindow().setLayout(width, height);
        getDialog().getWindow().setGravity(Gravity.CENTER);
    }

}

