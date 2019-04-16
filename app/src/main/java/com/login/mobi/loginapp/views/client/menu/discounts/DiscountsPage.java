package com.login.mobi.loginapp.views.client.menu.discounts;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;
import com.login.mobi.loginapp.views.client.menu.AddMenuItems;

public class DiscountsPage extends AppCompatActivity {

    public DiscountsPage() { }

    // xml elements: recyclerView, buttons
    private TextView discountsMainTextView;
    private RecyclerView rv;

    // variables
    private DiscountsAdapter adapter;
    //private List<MyOrders> list = new ArrayList<>();

    // Snackbar
    View parentLayout;

    // Progress Dialog
    private ProgressDialog progressDialog;

    // Shared Preferences
    SingletonSharedPref sharedPref;
    private String userID, token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_discounts);
        parentLayout = findViewById(android.R.id.content);


        progressDialog = new ProgressDialog(this, R.style.ProgressDialogInCenter);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);
        progressDialog.show();


        /* Discounts RecyclerView */
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DiscountsAdapter(this, AddMenuItems.getSpaceships());
        rv.setAdapter(adapter);
        rv.setItemAnimator(new DefaultItemAnimator());
        if (progressDialog.isShowing())
            progressDialog.dismiss();
        discountsMainTextView = (TextView) findViewById(R.id.discountsMainTextView);
    }



}