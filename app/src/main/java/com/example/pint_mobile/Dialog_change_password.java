package com.example.pint_mobile;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Dialog_change_password extends DialogFragment {


    Button cancel, ok;
    RadioGroup rg;
    ImageView see_password1, see_password2, see_password3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_change_password, container, false);

        cancel = view.findViewById(R.id.tentar);
        ok = view.findViewById(R.id.enviar);
        see_password1 = view.findViewById(R.id.see_pass1);
        see_password2 = view.findViewById(R.id.see_pass2);
        see_password3 = view.findViewById(R.id.see_pass3);

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
                //alterar password
            }
        });

        see_password1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                see_not_see_password1(view);
            }
        });

        see_password2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                see_not_see_password2(view);
            }
        });

        see_password3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                see_not_see_password3(view);
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

    public void see_not_see_password1(View v){
        TextView password = v.findViewById(R.id.senha_atual);
        ImageView img_see = v.findViewById(R.id.see_pass1);
        Context mContext = getContext();
        Drawable drawable_see = ContextCompat.getDrawable(
                mContext,
                R.drawable.ic_see_password
        );
        Drawable drawable_not_see = ContextCompat.getDrawable(
                mContext,
                R.drawable.ic_not_see_password
        );
        if(password.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){ //password visivel
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            img_see.setImageDrawable(drawable_not_see);

        }
        else{ //password nao visivel
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            img_see.setImageDrawable(drawable_see);
        }
    }

    public void see_not_see_password2(View v){
        TextView password = v.findViewById(R.id.senha_nova);
        ImageView img_see = v.findViewById(R.id.see_pass2);
        Context mContext = getContext();
        Drawable drawable_see = ContextCompat.getDrawable(
                mContext,
                R.drawable.ic_see_password
        );
        Drawable drawable_not_see = ContextCompat.getDrawable(
                mContext,
                R.drawable.ic_not_see_password
        );
        if(password.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){ //password visivel
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            img_see.setImageDrawable(drawable_not_see);

        }
        else{ //password nao visivel
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            img_see.setImageDrawable(drawable_see);
        }
    }

    public void see_not_see_password3(View v){
        TextView password = v.findViewById(R.id.senha_nova_again);
        ImageView img_see = v.findViewById(R.id.see_pass3);
        Context mContext = getContext();
        Drawable drawable_see = ContextCompat.getDrawable(
                mContext,
                R.drawable.ic_see_password
        );
        Drawable drawable_not_see = ContextCompat.getDrawable(
                mContext,
                R.drawable.ic_not_see_password
        );
        if(password.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){ //password visivel
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            img_see.setImageDrawable(drawable_not_see);

        }
        else{ //password nao visivel
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            img_see.setImageDrawable(drawable_see);
        }
    }
 
}

