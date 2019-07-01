package com.example.pint_mobile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment_profile_menu_notificacoes extends Fragment {

    TextView cancelar, guardar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_menu_profile_notificacoes, container, false);

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
                //guardar
            }
        });



        return view;
    }
}
