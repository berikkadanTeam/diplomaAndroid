package com.login.mobi.loginapp.views.client.menu.discounts;

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
    private TextView discountsMainTextView;
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
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_discounts);
        parentLayout = findViewById(android.R.id.content);

        sharedPref = SingletonSharedPref.getInstance(this);
        token = sharedPref.getString(SingletonSharedPref.TOKEN);

        progressDialog = new ProgressDialog(this, R.style.ProgressDialogInCenter);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);
        progressDialog.show();

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
            list = response;
            adapter.arrayChanged(list);
        }
        else if (response == null || response.isEmpty() || response.size() == 0){
            discountsMainTextView.setText("Акций нет");
            //Snackbar.make(parentLayout, "У Вас нет бронирований", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

    }
}