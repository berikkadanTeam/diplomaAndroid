package com.login.mobi.loginapp.views.client.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;


// from: https://camposha.info/source/android-recyclerview-ratingbarimages-text

public class MenuFragment extends Fragment {
    // xml elements: texts, buttons, recyclerView
    private TextView fullName, email;
    private RecyclerView rv;

    // Shared Preferences
    SingletonSharedPref sharedPref;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.menu, container, false);

        fullName = (TextView) rootView.findViewById(R.id.full_name);
        email = (TextView) rootView.findViewById(R.id.email);

        rv = (RecyclerView) rootView.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new MenuAdapter(getContext(), AddMenuItems.getSpaceships()));




        sharedPref = SingletonSharedPref.getInstance(getContext());
        fullName.setText(sharedPref.getString(SingletonSharedPref.USER_SURNAME) + " " + sharedPref.getString(SingletonSharedPref.USER_NAME));
        email.setText(sharedPref.getString(SingletonSharedPref.USER_EMAIL));

        return rootView;
    }

}
