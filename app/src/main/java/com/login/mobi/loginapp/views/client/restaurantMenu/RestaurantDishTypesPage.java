package com.login.mobi.loginapp.views.client.restaurantMenu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.booking.Menu;
import com.login.mobi.loginapp.network.model.restaurantMenu.RestaurantDishTypes;
import com.login.mobi.loginapp.network.requests.restaurantMenu.GetRestaurantDishTypes;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;

import java.util.ArrayList;
import java.util.List;


public class RestaurantDishTypesPage extends AppCompatActivity implements GetRestaurantDishTypes.GetRestaurantDishTypesInterface{

    public RestaurantDishTypesPage() { }

    // xml elements: editText, recyclerView
    private EditText searchEditText;
    private RecyclerView rv;
    private List<Menu> totalList = new ArrayList<>();

    // variables
    private RestaurantDishTypesAdapter adapter;
    private List<RestaurantDishTypes> list = new ArrayList<>();
    private String restaurantID;

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
        setContentView(R.layout.restaurant_dish_types_and_search);
        parentLayout = findViewById(android.R.id.content);

        progressDialog = new ProgressDialog(this, R.style.ProgressDialogInCenter);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);
        progressDialog.show();


        /* Dish Types RecyclerView*/
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RestaurantDishTypesAdapter(this, list);
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
                RestaurantDishTypesPage.this.searchFunc(searchQuery);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        /* Получение ID ресторана с RestaurantInformationPage */
        Intent intent = getIntent();
        restaurantID = intent.getStringExtra("RestaurantID");
        adapter.setRestaurantID(restaurantID);



        sharedPref = SingletonSharedPref.getInstance(this);
        token = sharedPref.getString(SingletonSharedPref.TOKEN);
        GetRestaurantDishTypes getRestaurantDishTypes = new GetRestaurantDishTypes(this, "Bearer " + token);
        getRestaurantDishTypes.getRestaurantDishTypes();

    }



    /* Search dish type by name */
    public void searchFunc(String text) {
        ArrayList<RestaurantDishTypes> founded = new ArrayList<>();
        for (RestaurantDishTypes s : list) {
            int i = s.getTitle().toLowerCase().indexOf(text.toLowerCase());
            if (i >= 0) {
                founded.add(s);
            }
        }
        adapter.arrayChanged(founded);
    }


    /* Get restaurant dish types */
    @Override
    public void getRestaurantDishTypes(List<RestaurantDishTypes> response) {
        Log.d("RestaurantDishTypes", response + " ");
        if (progressDialog.isShowing())
            progressDialog.dismiss();
        if (response != null) {
            list = response;
            adapter.arrayChanged(list);
        }
        else
            Snackbar.make(parentLayout, "Нет доступных категорий блюд", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode==1){
                Log.d("FUN", "onActivityResult: " + data.getStringExtra("result"));
                Gson gson = new Gson();
                totalList.addAll((List<Menu>)gson.fromJson(data.getStringExtra("result"), new TypeToken<List<Menu>>(){}.getType()));
                Log.d("FUN2222", totalList.size() + " ");

            }
        }
    }
}