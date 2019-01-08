//package com.login.mobi.loginapp;
//
//import android.arch.persistence.room.Room;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//
//import com.login.mobi.loginapp.Database.UserDatabase;
//import com.login.mobi.loginapp.Models.SeptemberEntities;
//
//import java.util.ArrayList;
//
///**
// * A simple {@link Fragment} subclass.
// */
//
//public class SeptemberFragment extends Fragment {
//
//    public SeptemberFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        UserDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(),
//                UserDatabase.class, "database-name").allowMainThreadQueries().build();
//        View rootView = inflater.inflate(R.layout.fragment_september, container, false);
//
//        ArrayList<SeptemberEntities> septemberList;
//        septemberList = (ArrayList<SeptemberEntities>)db.septemberDAO().getAll();
//
//        final CheckBox checkBox = rootView.findViewById(R.id.checkBox);
//        final CheckBox checkBox2 = rootView.findViewById(R.id.checkBox2);
//        final CheckBox checkBox3 = rootView.findViewById(R.id.checkBox3);
//        checkBox.setText(septemberList.get(0).getText());
//        checkBox2.setText(septemberList.get(1).getText());
//        checkBox3.setText(septemberList.get(2).getText());
//        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    HomeActivity.getFavoriteList().add(checkBox.getText().toString());
//                } else {
//                    HomeActivity.getFavoriteList().remove(checkBox.getText().toString());
//                }
//            }
//        });
//
//        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    HomeActivity.getFavoriteList().add(checkBox2.getText().toString());
//                } else {
//                    HomeActivity.getFavoriteList().remove(checkBox2.getText().toString());
//
//                }
//            }
//        });
//
//        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    HomeActivity.getFavoriteList().add(checkBox3.getText().toString());
//                } else {
//                    HomeActivity.getFavoriteList().remove(checkBox3.getText().toString());
//
//                }
//            }
//        });
//
//        // Inflate the layout for this fragment
//        return rootView;
//    }
//
//}
