package com.login.mobi.loginapp;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.login.mobi.loginapp.Database.UserDatabase;

public class PopularityFragment extends Fragment {

    public PopularityFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_popularity, container, false);

        UserDatabase database = ((MyApp) getActivity().getApplicationContext()).database;

        RecyclerView rv = rootView.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new RestaurantAdapter(getContext(), database.restaurantDAO().getAllByPopularity()));

//        TextView textView = (TextView) rootView.findViewById(R.id.textView2);
//        textView.setText("qweqweqweq");
        return rootView;
    }

}
