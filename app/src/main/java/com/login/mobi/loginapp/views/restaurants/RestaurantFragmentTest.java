package com.login.mobi.loginapp.views.restaurants;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.restaurants.Restaurant;
import com.login.mobi.loginapp.network.requests.restaurants.GetRestaurants;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.List;


public class RestaurantFragmentTest extends Fragment implements GetRestaurants.GetRestaurantsInterface{

    public RestaurantFragmentTest() { }

    private EditText searchEditText;
    private RecyclerView rv;
    private RestaurantAdapter adapter;
    private List<Restaurant> list = new ArrayList<>();

    private RecyclerView sortBy;

    private ProgressDialog progressDialog;

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.restaurant_fragment_and_search_filter, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Идёт загрузка ресторанов");
        progressDialog.show();

        rv = rootView.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RestaurantAdapter(getContext(), list);
        rv.setAdapter(adapter);
        rv.setItemAnimator(new DefaultItemAnimator());


        /* Search */
        searchEditText = (EditText) rootView.findViewById(R.id.search_edit_text);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchQuery = searchEditText.getText().toString();
                RestaurantFragmentTest.this.searchFunc(searchQuery);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });


        /* Filter dialog window */
        final DialogPlus filterDialog = DialogPlus.newDialog(getContext())
                .setContentHolder(new ViewHolder(R.layout.filter_page))
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        switch (view.getId()) {
                            case R.id.filter_done_button:
                                // СЮДА НУЖНО ДОПИСАТЬ ФИЛЬТР С recyclerView
                                sortBy = view.findViewById(R.id.sort_by_smth_filter_recyclerview);
                                String[] mApps = {"Instagram", "Pinterest", "Pocket", "Twitter"};
                                //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mApps);
                                //ArrayAdapter<String> adapter2 = new ArrayAdapter<>(view, android.R.layout.simple_list_item_1, mApps);
                                //DialogPlusBuilder builder = DialogPlus.newDialog(getView().setAdapter(adapter);
                                //sortBy.setAdapter(adapter);
                                dialog.dismiss();
                        }
                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel(DialogPlus dialog) {
                        Toast.makeText(getContext(), "Никакой фильтр не был применён", Toast.LENGTH_LONG).show();
                    }
                })
                .setExpanded(true)
                .setCancelable(true)
                .create();


        /* When clicked on Filter button */
        CardView filterButton = (CardView) rootView.findViewById(R.id.filter_button);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog.show();
            }
        });


        GetRestaurants getRestaurants = new GetRestaurants(this);
        getRestaurants.getRestaurants();


        return rootView;
    }

    /* Search restaurant by name */
    public void searchFunc(String text) {
        ArrayList<Restaurant> founded = new ArrayList<>();
        for (Restaurant s : list) {
            int i = s.getName().toLowerCase().indexOf(text.toLowerCase());
            if (i >= 0) {
                founded.add(s);
            }
        }
        adapter.arrayChanged(founded);
    }


    /* Display all restaurants in recyclerView */
    @Override
    public void getRestaurants(List<Restaurant> response) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(50);
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        if (response != null) {
            list = response;
            adapter.arrayChanged(list);
        } else {
            //Toast.makeText(getContext(), "Нет доступных ресторанов", Toast.LENGTH_LONG).show();
            Snackbar.make(rootView, "Нет доступных ресторанов", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }



}