package com.login.mobi.loginapp.views.client.restaurantMenu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.restaurantMenu.RestaurantDishes;
import com.login.mobi.loginapp.network.requests.restaurantMenu.GetRestaurantDishes;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;

import java.util.ArrayList;
import java.util.List;


public class RestaurantDishesPage extends AppCompatActivity implements GetRestaurantDishes.GetRestaurantDishesInterface{

    public RestaurantDishesPage() { }

    // xml elements: editText, recyclerView, buttons
    private TextView amountOfDishTextView;
    private EditText searchEditText;
    private RecyclerView rv;
    private LinearLayout preOrderBtn;

    // variables
    private RestaurantDishesAdapter adapter;
    private List<RestaurantDishes> list = new ArrayList<>();
    int dishTypeID;
    private String restaurantID;
    private int amountOfDish = 1;

    // Progress Dialog
    private ProgressDialog progressDialog;

    // Snackbar
    View parentLayout;

    // Shared Preferences
    SingletonSharedPref sharedPref;
    private String token;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_dishes_and_search);
        parentLayout = findViewById(android.R.id.content);


        progressDialog = new ProgressDialog(this, R.style.ProgressDialogInCenter);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);
        progressDialog.show();


        /* Получение ID ресторана & ID dishType с RestaurantDishTypesAdapter */
        Intent intent = getIntent();
        dishTypeID = intent.getIntExtra("DishTypeID",-1);
        restaurantID = intent.getStringExtra("RestaurantID");
        Log.d("RestaurantDishesPage", restaurantID + " " + dishTypeID);


        /* Dish Types RecyclerView*/
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RestaurantDishesAdapter(this, list, dishTypeID);
        rv.setAdapter(adapter);
        rv.setItemAnimator(new DefaultItemAnimator());

        /* Search */
        searchEditText = (EditText) findViewById(R.id.search_edit_text);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchQuery = searchEditText.getText().toString();
                RestaurantDishesPage.this.searchFunc(searchQuery);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        // preOrder button
        //preOrderBtn.setVisibility(View.VISIBLE);
        preOrderBtn = findViewById(R.id.open_preorder_menu_total);
        preOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(parentLayout, "PREORDER", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                //startActivity(new Intent(RestaurantDishesPage.this, RestaurantDishesPreorderPage.class));
                Intent returnIntent = new Intent();
                Gson gson = new Gson();

                returnIntent.putExtra("result", gson.toJson(adapter.getSelected()));
                setResult(RESULT_OK, returnIntent);
                finish();

            }
        });


        sharedPref = SingletonSharedPref.getInstance(this);
        token = sharedPref.getString(SingletonSharedPref.TOKEN);
        GetRestaurantDishes getRestaurantDishes = new GetRestaurantDishes(this, restaurantID, "Bearer " + token);
        getRestaurantDishes.getRestaurantDishes();

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


    /* Get restaurant dishes */
    @Override
    public void getRestaurantDishes(List<RestaurantDishes> response) {
        Log.d("RestaurantDishes", response + " ");
        if (progressDialog.isShowing())
            progressDialog.dismiss();
        if (response != null) {
            //list = response;
            for (int i=0; i<response.size(); i++){
                if (dishTypeID == response.get(i).getTypeId())
                    list.add(response.get(i));
            }
            if (list.size() == 0){
                Snackbar.make(parentLayout, "Нет блюд в данном разделе", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
            adapter.arrayChanged(list);
        }
        else
            Snackbar.make(parentLayout, "Нет блюд в данном разделе", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }


//    public void displayButton(int i){
//        preOrderBtn.setVisibility(View.VISIBLE);
//    }


}