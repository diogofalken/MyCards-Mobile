package com.example.pint_mobile;

import android.content.Context;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Dialog_lista_distritos extends DialogFragment {

    public interface OnInputSelectedDistrict{
        void sendInputDistrict(String input);
    }
    public OnInputSelectedDistrict onInputSelectedDistrict;

    Button cancel, ok;
    RadioGroup rg;
    RadioButton rb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_registo_lista_distritos, container, false);

        cancel = view.findViewById(R.id.nao);
        ok = view.findViewById(R.id.enviar);
        rg = view.findViewById(R.id.radio_group);

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
                int radioId = rg.getCheckedRadioButtonId();
                rb = view.findViewById(radioId);
                onInputSelectedDistrict.sendInputDistrict(rb.getText().toString());
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

        double w = width_screen * 0.9;
        int width = (int) w;

        double h = height_screen * 0.75;
        int height = (int) h;

        getDialog().getWindow().setLayout(width, height);
        getDialog().getWindow().setGravity(Gravity.CENTER);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onInputSelectedDistrict = (OnInputSelectedDistrict) getTargetFragment();
        }catch (ClassCastException e){

        }
    }
}

