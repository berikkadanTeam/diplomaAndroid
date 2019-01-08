//package com.login.mobi.loginapp;
//
//import android.arch.lifecycle.Observer;
//import android.arch.lifecycle.ViewModelProviders;
//import android.arch.persistence.room.Room;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.Toast;
//
//import com.login.mobi.loginapp.Database.UserDatabase;
//import com.login.mobi.loginapp.Models.AugustEntities;
//import com.login.mobi.loginapp.Models.Restaurant;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * A simple {@link Fragment} subclass.
// */
//
//public class AugustFragment extends Fragment {
//
//    public AugustFragment() {
//        // Required empty public constructor
//    }
//
//
//    RestaurantViewModel restaurantViewModel;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        UserDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(),
//                UserDatabase.class, "database-name").allowMainThreadQueries().build();
//
//        View rootView = inflater.inflate(R.layout.fragment_august, container, false);
//
//        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
//        recyclerView.setHasFixedSize(true);
//
//        final RestaurantAdapter adapter = new RestaurantAdapter();
//        recyclerView.setAdapter(adapter);
//
//        restaurantViewModel = ViewModelProviders.of(this).get(RestaurantViewModel.class);
//        restaurantViewModel.getAllRestaurantsByPopularity().observe(this, new Observer<List<Restaurant>>() {
//            @Override
//            public void onChanged(@Nullable List<Restaurant> restaurants) {
//                adapter.setRestaurants(restaurants);
//            }
//        });
////        ArrayList<AugustEntities> augustList;
////        augustList = (ArrayList<AugustEntities>)db.augustDAO().getAll();
////        Log.d("qwe", augustList.toString());
////        final CheckBox checkBox = rootView.findViewById(R.id.checkBox);
////        final CheckBox checkBox2 = rootView.findViewById(R.id.checkBox2);
////        final CheckBox checkBox3 = rootView.findViewById(R.id.checkBox3);
////        checkBox.setText(augustList.get(0).getText());
////        checkBox2.setText(augustList.get(1).getText());
////        checkBox3.setText(augustList.get(2).getText());
////        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
////            @Override
////            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                if (isChecked) {
////                    HomeActivity.getFavoriteList().add(checkBox.getText().toString());
////                } else {
////                    HomeActivity.getFavoriteList().remove(checkBox.getText().toString());
////                }
////            }
////        });
////
////        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
////            @Override
////            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                if (isChecked) {
////                    HomeActivity.getFavoriteList().add(checkBox2.getText().toString());
////                } else {
////                    HomeActivity.getFavoriteList().remove(checkBox2.getText().toString());
////
////                }
////            }
////        });
////
////        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
////            @Override
////            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                if (isChecked) {
////                    HomeActivity.getFavoriteList().add(checkBox3.getText().toString());
////                } else {
////                    HomeActivity.getFavoriteList().remove(checkBox3.getText().toString());
////
////                }
////            }
////        });
//
//        // Inflate the layout for this fragment
//        return rootView;
//    }
//
//}
