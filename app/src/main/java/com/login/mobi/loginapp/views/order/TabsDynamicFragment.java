package com.login.mobi.loginapp.views.order;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.restaurantMenu.RestaurantDishes;
import com.login.mobi.loginapp.network.requests.restaurantMenu.GetRestaurantDishes;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;

import java.util.ArrayList;
import java.util.List;


public class TabsDynamicFragment extends Fragment implements GetRestaurantDishes.GetRestaurantDishesInterface{

    // Shared Preferences
    SingletonSharedPref sharedPref;
    private String token;
    private String restaurantID = "0f45bb63-68f4-4831-ab57-5a00f430f93a";

    // variables
    private EditText searchEditText;
    private TabsDishesAdapter adapter;
    private List<RestaurantDishes> list = new ArrayList<>();
    int dishTypeID = 1;
    private RecyclerView rv;
    private TextView fragmentMainTextView;
    //List<RestaurantDishes> dishes = new ArrayList<>();

    public static TabsDynamicFragment newInstance() {
        return new TabsDynamicFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabs_dynamic_fragment_github, container, false);
        initViews(view);

        /* Dishes RecyclerView */
        rv = view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TabsDishesAdapter((TabsMainActivity)getActivity(), list, dishTypeID);
        rv.setAdapter(adapter);
        rv.setItemAnimator(new DefaultItemAnimator());

        /* Search */
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchQuery = searchEditText.getText().toString();
                TabsDynamicFragment.this.searchFunc(searchQuery);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        sharedPref = SingletonSharedPref.getInstance(getContext());
        token = sharedPref.getString(SingletonSharedPref.TOKEN);
        Log.d("TOKEN", token + " ");
        GetRestaurantDishes getRestaurantDishes = new GetRestaurantDishes(this, restaurantID, "Bearer " + token);
        getRestaurantDishes.getRestaurantDishes();

        return view;
    }


    private void initViews(View view) {
        searchEditText = (EditText) view.findViewById(R.id.search_edit_text);
        fragmentMainTextView = view.findViewById(R.id.fragmentMainTextView);
        int position = getArguments().getInt("Position");
        //fragmentMainTextView.setText(String.valueOf("Category :  " + getArguments().getInt("Position")));

        dishTypeID = getArguments().getInt("DishTypeID");
        Log.d("TAB'S FRAGMENT", " DISH TYPE ID FROM FRAGMENT ADAPTER" + dishTypeID);

//        String jsonData2 = getArguments().getString("Dishes");
//        Type collectionType2 = new TypeToken<List<RestaurantDishes>>(){}.getType();
//        dishes = (List<RestaurantDishes>) new Gson().fromJson(jsonData2, collectionType2);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /* Search dish by name */
    public void searchFunc(String text) {
        ArrayList<RestaurantDishes> founded = new ArrayList<>();
        for (RestaurantDishes s : list) {
            int i = s.getNameOfDish().toLowerCase().indexOf(text.toLowerCase());
            if (i >= 0) {
                founded.add(s);
            }
        }
        adapter.arrayChanged(founded);
    }

    /* Get Restaurant Dishes */
    @Override
    public void getRestaurantDishes(List<RestaurantDishes> response) {
        Log.d("RestaurantDishes", response + " ");
        if (response != null) {
            for (int i=0; i<response.size(); i++){
                if (dishTypeID == response.get(i).getTypeId())
                    list.add(response.get(i));
            }
            if (list.size() == 0){
                fragmentMainTextView.setText("Нет блюд в данном разделе");
            }
            adapter.arrayChanged(list);
        }
        else
            Toast.makeText(getContext(), "NO DISHES in this section", Toast.LENGTH_LONG).show();
    }
}