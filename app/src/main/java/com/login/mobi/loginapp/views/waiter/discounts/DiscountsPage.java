package com.login.mobi.loginapp.views.waiter.discounts;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.discounts.Discount;
import com.login.mobi.loginapp.network.requests.discounts.GetDiscounts;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;

import java.util.ArrayList;
import java.util.List;

public class DiscountsPage extends AppCompatActivity implements GetDiscounts.GetDiscountsInterface{

    public DiscountsPage() { }

    // xml elements: recyclerView, buttons
    private TextView pageTitle, discountsMainTextView;
    private RecyclerView rv;

    // variables
    private DiscountsAdapter adapter;
    private List<Discount> list = new ArrayList<>();

    // Snackbar
    View parentLayout;

    // Progress Dialog
    private ProgressDialog progressDialog;

    // Shared Preferences
    SingletonSharedPref sharedPref;
    private String token, restaurantID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_discounts);
        parentLayout = findViewById(android.R.id.content);

        sharedPref = SingletonSharedPref.getInstance(this);
        token = sharedPref.getString(SingletonSharedPref.TOKEN);
        restaurantID = sharedPref.getString(SingletonSharedPref.RESTAURANT_ID);

        progressDialog = new ProgressDialog(this, R.style.ProgressDialogInCenter);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);
        progressDialog.show();

        pageTitle = (TextView) findViewById(R.id.page_title);
        pageTitle.setText("Акции ресторана");
        discountsMainTextView = (TextView) findViewById(R.id.discountsMainTextView);

        /* Discounts RecyclerView */
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DiscountsAdapter(this, list);
        rv.setAdapter(adapter);
        rv.setItemAnimator(new DefaultItemAnimator());
//        if (progressDialog.isShowing())
//            progressDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetDiscounts getDiscounts = new GetDiscounts(this,"Bearer " + token);
        getDiscounts.getDiscounts();
    }

    @Override
    public void getDiscounts(List<Discount> response) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (response != null && response.size()>0) {
            Log.d("Discounts", response.toString() + " ");
            //list = response;
            //adapter.arrayChanged(list);

            // Вывод только тех акций, которые проходят в ресторане данного официанта
            for (int i=0; i<response.size(); i++){
                if (response.get(i).getRestaurantId() == restaurantID)
                    list.add(response.get(i));
            }
            if (list.size() > 0)
                adapter.arrayChanged(list);
            else
                discountsMainTextView.setText("Акций в ресторане нет");
        }
        else if (response == null || response.isEmpty() || response.size() == 0){
            discountsMainTextView.setText("Акций нет");
            //Snackbar.make(parentLayout, "У Вас нет бронирований", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

    }
}