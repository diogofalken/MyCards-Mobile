package com.example.pint_mobile;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Fragment_registo_informacoes extends Fragment implements Dialog_lista_distritos.OnInputSelectedDistrict, Dialog_registo_data_nascimento.OnInputSelectedDate {

    @Override
    public void sendInputDate(String input) {
        button_data_nasc.setText(input);
    }

    @Override
    public void sendInputDistrict(String input) {
        button_distrito.setText(input);
    }

    private Button button_data_nasc, button_distrito;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_registo_informacoes, container, false);
        button_distrito = view.findViewById(R.id.distrito);
        button_data_nasc =  view.findViewById(R.id.data_nasc);

        //ver e nao ver password
        ImageView see_password = view.findViewById(R.id.see_pass);
        see_password.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                see_not_see_password(view);
            }
        });
        ImageView see_password_again = view.findViewById(R.id.see_pass_again);
        see_password_again.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                see_not_see_password_again(view);
            }
        });


        //escolher data de nascimento
        button_distrito.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Dialog_lista_distritos dialog = new Dialog_lista_distritos();
                dialog.setTargetFragment(Fragment_registo_informacoes.this, 1);
                dialog.show(getFragmentManager(), "Dialog_lista_distritos");
            }
        });

        //escolher distrito
        button_data_nasc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Dialog_registo_data_nascimento dialog = new Dialog_registo_data_nascimento();
                dialog.setTargetFragment(Fragment_registo_informacoes.this, 1);
                dialog.show(getFragmentManager(), "Dialog_registo_data_nascimento");
            }
        });

        return view;
    }


    public void see_not_see_password(View v){
        TextView password = v.findViewById(R.id.senha);
        ImageView img_see = v.findViewById(R.id.see_pass);
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

    public void see_not_see_password_again(View v){
        TextView password = v.findViewById(R.id.senha_again);
        ImageView img_see = v.findViewById(R.id.see_pass_again);
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
