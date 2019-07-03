package com.example.pint_mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_registo_informacoes extends Fragment implements Dialog_lista_distritos.OnInputSelectedDistrict, Dialog_registo_data_nascimento.OnInputSelectedDate {

    @Override
    public void sendInputDate(String input) {
        button_data_nasc.setText(input);
    }

    @Override
    public void sendInputDistrict(String input) {
        button_distrito.setText(input);
    }

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private Button button_data_nasc, button_distrito;
    private EditText email;
    private EditText senha;
    private EditText senha_conf;
    private EditText primeiro_nome;
    private EditText ultimo_nome;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_registo_informacoes, container, false);
        button_distrito = view.findViewById(R.id.distrito);
        button_data_nasc =  view.findViewById(R.id.data_nasc);
        email = view.findViewById(R.id.email);
        senha = view.findViewById(R.id.senha);
        senha_conf = view.findViewById(R.id.senha_again);
        primeiro_nome = view.findViewById(R.id.primeiro_nome);
        ultimo_nome = view.findViewById(R.id.ultimo_nome);

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


        button_distrito.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Dialog_lista_distritos dialog = new Dialog_lista_distritos();
                dialog.setTargetFragment(Fragment_registo_informacoes.this, 1);
                dialog.show(getFragmentManager(), "Dialog_lista_distritos");
            }
        });

        button_data_nasc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Dialog_registo_data_nascimento dialog = new Dialog_registo_data_nascimento();
                dialog.setTargetFragment(Fragment_registo_informacoes.this, 1);
                dialog.show(getFragmentManager(), "Dialog_registo_data_nascimento");
            }
        });

        sharedPreferences = getContext().getSharedPreferences(Activity_registo.PREFERENCES_REGISTO, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveInfSharedPreferences();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        senha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveInfSharedPreferences();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        senha_conf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveInfSharedPreferences();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        primeiro_nome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveInfSharedPreferences();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        ultimo_nome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveInfSharedPreferences();
            }
            @Override
            public void afterTextChanged(Editable s) {
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

    public void saveInfSharedPreferences() {
        editor.putString("email", email.getText().toString());
        editor.putString("senha", senha.getText().toString());
        editor.putString("senha_conf", senha_conf.getText().toString());
        editor.putString("primeiro_nome", primeiro_nome.getText().toString());
        editor.putString("ultimo_nome", ultimo_nome.getText().toString());
        editor.putString("data_nasc", button_data_nasc.getText().toString());
        editor.putString("distrito", button_distrito.getText().toString());
        editor.commit();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        String s_email = sharedPreferences.getString("email", "");
        String s_senha = sharedPreferences.getString("senha", "");
        String s_senha_conf = sharedPreferences.getString("senha_conf", "");
        String s_primeiro_nome = sharedPreferences.getString("primeiro_nome", "");
        String s_ultimo_nome = sharedPreferences.getString("ultimo_nome", "");
        String s_data_nasc = sharedPreferences.getString("data_nasc", "");
        String s_distrito = sharedPreferences.getString("distrito", "");
        email.setText(s_email);
        senha.setText(s_senha);
        senha_conf.setText(s_senha_conf);
        primeiro_nome.setText(s_primeiro_nome);
        ultimo_nome.setText(s_ultimo_nome);
        button_data_nasc.setText(s_data_nasc);
        button_distrito.setText(s_distrito);
    }

}


