package com.login.mobi.loginapp.views.client.order;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.booking.Menu;
import com.login.mobi.loginapp.network.model.restaurantMenu.RestaurantDishTypes;
import com.login.mobi.loginapp.network.model.restaurantMenu.RestaurantDishes;
import com.login.mobi.loginapp.network.requests.restaurantMenu.GetRestaurantDishTypes;
import com.login.mobi.loginapp.network.requests.restaurantMenu.GetRestaurantDishes;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker;
import com.treebo.internetavailabilitychecker.InternetConnectivityListener;

import java.util.ArrayList;
import java.util.List;


// from: https://github.com/dhirajaknurwar/dynamicFragmentTablayout/tree/master/app/src/main/java/com/master/tablayoutwithdynamicfragments

public class TabsMainActivity extends AppCompatActivity implements GetRestaurantDishTypes.GetRestaurantDishTypesInterface, GetRestaurantDishes.GetRestaurantDishesInterface, InternetConnectivityListener {

    // xml elements
    private ViewPager viewPager;
    private TabLayout mTabLayout;
    private ProgressDialog progressDialog;

    // variables
    private List<RestaurantDishTypes> dishTypes = new ArrayList<>();
    public List<Menu> chosenDishList = new ArrayList<>();
    public List<RestaurantDishes> dishes = new ArrayList<>();
    public List<RestaurantDishes> chosenDishListDishNames = new ArrayList<>();

    // Shared Preferences
    SingletonSharedPref sharedPref;
    private String token;
    private String restaurantID; //= "73048e25-ec04-4da2-98c8-0b496daee9ea";
    private String tableID;

    // Snackbar
    View parentLayout;

    private InternetAvailabilityChecker mInternetAvailabilityChecker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_tabs_main_activity);
        parentLayout = findViewById(android.R.id.content);

        progressDialog = new ProgressDialog(this, R.style.ProgressDialogInCenter);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);
        progressDialog.show();

        Intent intent = getIntent();
        restaurantID = intent.getStringExtra("RestaurantID");
        tableID = intent.getStringExtra("TableID");
        Log.d("RestaurantID", "TabsMainActivity - " + restaurantID);
        Log.d("TableID", "TabsMainActivity - " + tableID);

        initViews();

        sharedPref = SingletonSharedPref.getInstance(this);
        token = sharedPref.getString(SingletonSharedPref.TOKEN);
//        GetRestaurantDishTypes getRestaurantDishTypes = new GetRestaurantDishTypes(this, "Bearer " + token);
//        getRestaurantDishTypes.getRestaurantDishTypes();
//
//        GetRestaurantDishes getRestaurantDishes = new GetRestaurantDishes(this, restaurantID, "Bearer " + token);
//        getRestaurantDishes.getRestaurantDishes();


        // Checking internet connection
        InternetAvailabilityChecker.init(this);
        mInternetAvailabilityChecker = InternetAvailabilityChecker.getInstance();
        mInternetAvailabilityChecker.addInternetConnectivityListener(this);

    }

    private void initViews() {

        viewPager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tabs);

        viewPager.setOffscreenPageLimit(5);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Log.d("ACTIVITY DISHES", chosenDishList.toString() + "\n");
                Log.d("ACTIVITY DISHES NAME", chosenDishListDishNames.toString() + "\n");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //setDynamicFragmentToTabLayout();

    }

    private void setDynamicFragmentToTabLayout() {
        Log.d("DISHTYPES LIST SIZE", dishTypes.size() + " ");
        for (int i = 0; i < dishTypes.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(dishTypes.get(i).getTitle()));   // tab name
        }
        TabsDynamicFragmentAdapter mDynamicFragmentAdapter = new TabsDynamicFragmentAdapter(getSupportFragmentManager(), mTabLayout.getTabCount(), dishTypes, restaurantID); //dishes);
        viewPager.setAdapter(mDynamicFragmentAdapter);
        viewPager.setCurrentItem(0);
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    @Override
    public void getRestaurantDishTypes(List<RestaurantDishTypes> response) {
        Log.d("RestaurantDishTypes", response + " ");
        if (response != null) {
            dishTypes = response;
            setDynamicFragmentToTabLayout();
        }
        else
            Log.d("RestaurantDishTypes", "Нет доступных категорий блюд");
    }


    /* Get Restaurant Dishes */
    @Override
    public void getRestaurantDishes(List<RestaurantDishes> response) {
        Log.d("RestaurantDishes", response + " ");
        if (response != null) {
            dishes = response;
        }
        else
            Log.d("RestaurantDishes", "Нет блюд");
    }



    public void makeAnOrder(View v){
        Log.d("ACTIVITY BUTTON ONCLICK", chosenDishList.toString() + " ");

        Intent intent = new Intent(TabsMainActivity.this, OrderedDishesPage.class);
        intent.putExtra("ChosenDishesListForOrder", new Gson().toJson(chosenDishList));     // посылаем все выбранные блюда
        //intent.putExtra("AllDishesList", new Gson().toJson(dishes));
        intent.putExtra("ChosenDishesListInformation", new Gson().toJson(chosenDishListDishNames));
        intent.putExtra("TableID", tableID);
        startActivity(intent);
    }


    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (isConnected) {
            Log.d("Network status","CONNECTED");
            GetRestaurantDishTypes getRestaurantDishTypes = new GetRestaurantDishTypes(this, "Bearer " + token);
            getRestaurantDishTypes.getRestaurantDishTypes();

            GetRestaurantDishes getRestaurantDishes = new GetRestaurantDishes(this, restaurantID, "Bearer " + token);
            getRestaurantDishes.getRestaurantDishes();
        } else {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            Log.d("Network status","NOT CONNECTED");
            Snackbar.make(parentLayout, "Проверьте подключение к интернету", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mInternetAvailabilityChecker.removeInternetConnectivityChangeListener(this);
    }

}