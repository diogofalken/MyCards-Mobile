package com.example.pint_mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Fragment_registo_notificacoes extends Fragment {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private RadioGroup rg;
    private RadioButton rb, rb0, rb1, rb2, rb3;
    private String not;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_registo_notificacoes, container, false);

        rg = view.findViewById(R.id.rg);
        rb0 = view.findViewById(R.id.not0);
        rb1 = view.findViewById(R.id.not1);
        rb2 = view.findViewById(R.id.not2);
        rb3 = view.findViewById(R.id.not3);

        sharedPreferences = getContext().getSharedPreferences(Activity_registo.PREFERENCES_REGISTO, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        rb0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveInfSharedNotificacoes(view);
            }
        });

        rb1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveInfSharedNotificacoes(view);
            }
        });

        rb2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveInfSharedNotificacoes(view);
            }
        });

        rb3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveInfSharedNotificacoes(view);
            }
        });

        return view;
    }

    void saveInfSharedNotificacoes(View view){
        int radioId = rg.getCheckedRadioButtonId();
        rb = view.findViewById(radioId);
        not = rb.getContentDescription().toString();
        editor.putString("notificacoes", not);
        editor.commit();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        String notific = sharedPreferences.getString("notificacoes", "");
        if(notific.equals("0")){
            rg.check(R.id.not0);
        } else if(notific.equals("1")){
            rg.check(R.id.not1);
        } else if(notific.equals("2")){
            rg.check(R.id.not2);
        } else if(notific.equals("3")){
            rg.check(R.id.not3);
        }


    }
 }
