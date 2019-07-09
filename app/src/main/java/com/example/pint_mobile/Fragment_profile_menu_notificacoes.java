package com.example.pint_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.HashMap;
import java.util.Map;

public class Fragment_profile_menu_notificacoes extends Fragment {

    TextView cancelar, guardar;
    String Notificacoes ="";
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_menu_profile_notificacoes, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        sharedPreferences = getContext().getSharedPreferences(Activity_login.MyPREFERENCES, Context.MODE_PRIVATE);
        RadioGroup radioGroupaux = (RadioGroup)view.findViewById(R.id.radioGroup);


        switch (sharedPreferences.getString("Notificacoes", "")) {
            case "0":
                radioGroupaux.check(R.id.radioButton1);
                break;
            case "3":
                radioGroupaux.check(R.id.radioButton4);
                break;
        }
        cancelar = view.findViewById(R.id.cancel);
        guardar = view.findViewById(R.id.save);

        cancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Dialog_save_changes dialog = new Dialog_save_changes();
                dialog.setTargetFragment(Fragment_profile_menu_notificacoes.this, 1);
                dialog.show(getFragmentManager(), "Dialog_save_changes");
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                RadioGroup radioGroup = (RadioGroup)view.findViewById(R.id.radioGroup);

                int radioButtonID = radioGroup.getCheckedRadioButtonId();

                RadioButton radioButton = (RadioButton)radioGroup.findViewById(radioButtonID);
                String selectedtext = (String) radioButton.getText();

                switch (selectedtext) {
                    case "Não receber notificações":
                        Notificacoes = "0";
                        break;
                    case "Receber todas as notificações":
                        Notificacoes = "3";
                        break;
                }

                String url = "https://www.mycards.dsprojects.pt/api/cliente/" + sharedPreferences.getString("Id", "");
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getString("status").equals("true")) {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("Notificacoes", Notificacoes);
                                        editor.commit();
                                        Notificacoes = "";
                                        Toast.makeText(getContext(), "Update efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getContext(), Activity_feed.class);
                                        getContext().startActivity(i);
                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getContext(), "Update Errado!", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getContext(), "Update Errado", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            //This indicates that the request has either time out or there is no connection
                            Log.i("VolleyError::", error.toString());
                        } else if (error instanceof AuthFailureError) {
                            //Error indicating that there was an Authentication Failure while performing the request
                            Log.i("AuthFailureError::", error.toString());
                        } else if (error instanceof ServerError) {
                            //Indicates that the server responded with a error response
                            Log.i("ServerError::", error.toString());
                        } else if (error instanceof NetworkError) {
                            //Indicates that there was network error while performing the request
                            Log.i("NetworkError::", error.toString());
                        } else if (error instanceof ParseError) {
                            //Indicates that the server response could not be parsed
                            Log.i("ParseError::", error.toString());
                        }
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Notificacoes", Notificacoes);
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
}