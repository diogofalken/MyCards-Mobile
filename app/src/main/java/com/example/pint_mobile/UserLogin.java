package com.example.pint_mobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserLogin extends AsyncTask<Void, Void,Void> {

    public ListView lv;
    public Context ma;

    private static String url = "";

    // public ArrayList <produto> listaprs = new ArrayList <produto>() ;
    public ArrayList<String> listaprs = new ArrayList <String>() ;
    ProgressDialog pd;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        String target = "";
        HttpHandler sh = new HttpHandler();
        String jsonStr = sh.makeServiceCall(url);

        Log.e("Resposta do URL",  jsonStr);
        int count;

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONArray prs = jsonObj.getJSONArray("users");
                Log.e("Resposta do URL",  String.valueOf(prs.length()));
                for (int i = 0; i < prs.length(); i++) {
                    JSONObject pr = prs.getJSONObject(i);

                    String s_email = pr.getString("email");

                    String s_nomep = pr.getString("senha");

                    //produto prod = new produto(id,nomep);

                    listaprs.add(s_email);


                }
            } catch (final JSONException e) {

            }
        } else {

        }

        return null;
    }
}
