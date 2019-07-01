package com.example.pint_mobile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

public class Fragment_feed_cartoes extends Fragment {

    GridView gridViewv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_cartoes, container, false);

        gridViewv = view.findViewById(R.id.cartoes);

        Dialog_logout dialog_logout = new Dialog_logout();
        //gridViewv.addView(dialog_logout);
        return view;
    }
}
