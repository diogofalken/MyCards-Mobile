package com.example.pint_mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

public class Dialog_recuperar_senha extends DialogFragment {

    Button cancel, recuperar;
    public EditText email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_recuperar_senha, container, false);

        cancel = view.findViewById(R.id.voltar);
        recuperar = view.findViewById(R.id.enviar);
        email = view.findViewById(R.id.email2);

        //fechar pop up ao carregar em cancelar
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.mycards.dsprojects.pt/authentication/recoverPassword_cliente";

                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getString("status").equals("true")) {
                                        Toast.makeText(getActivity(), "Foi enviado um em email com a sua senha para o seu Email!", Toast.LENGTH_LONG).show();
                                        getDialog().dismiss();

                                    } else {
                                        Toast.makeText(getActivity().getApplicationContext(), "Não foi enviado Email!", Toast.LENGTH_SHORT).show();
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
                        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
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
                        params.put("email", email.getText().toString());
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(postRequest);
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

        double h = height_screen * 0.35;
        int height = (int) h;

        getDialog().getWindow().setLayout(width, height);
        getDialog().getWindow().setGravity(Gravity.CENTER);
    }


}
