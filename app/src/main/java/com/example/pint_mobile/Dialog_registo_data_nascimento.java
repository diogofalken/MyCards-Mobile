package com.example.pint_mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

public class Dialog_registo_data_nascimento extends DialogFragment {

    public interface OnInputSelectedDate{
        void sendInputDate(String input);
    }
    public OnInputSelectedDate onInputSelectedDate;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Button cancel, ok;
    DatePicker datePicker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_registo_data_nascimento, container, false);

        cancel = view.findViewById(R.id.voltar);
        ok = view.findViewById(R.id.enviar);
        datePicker = view.findViewById(R.id.date_picker);

        //fechar pop up ao carregar em cancelar
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        //fechar pop up ao carregar em ok
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth()+1;
                int year =  datePicker.getYear();
                String data = year + "-" + month + "-" + day;
                onInputSelectedDate.sendInputDate(data);

                sharedPreferences = getActivity().getSharedPreferences(Activity_registo.PREFERENCES_REGISTO, Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("data_nasc", data);
                editor.commit();

                getDialog().dismiss();
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

        double w = width_screen * 0.8;
        int width = (int) w;

        double h = height_screen * 0.58;
        int height = (int) h;

        getDialog().getWindow().setLayout(width, height);
        getDialog().getWindow().setGravity(Gravity.CENTER);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onInputSelectedDate = (OnInputSelectedDate) getTargetFragment();
        }catch (ClassCastException e){

        }
    }
}

