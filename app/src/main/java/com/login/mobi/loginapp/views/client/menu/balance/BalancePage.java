package com.login.mobi.loginapp.views.client.menu.balance;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.ServerResponse;
import com.login.mobi.loginapp.network.requests.balance.FillBalance;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;


public class BalancePage extends AppCompatActivity implements FillBalance.FillBalanceInterface{

    // xml elements: texts, buttons
    private Button fillBtn;
    private EditText balance;

    // Shared Preferences
    SingletonSharedPref sharedPref;
    private String userID, token;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_balance_page);

        sharedPref = SingletonSharedPref.getInstance(this);
        userID = sharedPref.getString(SingletonSharedPref.USER_ID);
        token = sharedPref.getString(SingletonSharedPref.TOKEN);


        progressDialog = new ProgressDialog(this, R.style.ProgressDialogInCenter);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);
        //progressDialog.show();

        balance = (EditText) findViewById(R.id.balance);

        fillBtn = findViewById(R.id.fill_balance);
        fillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void getFillBalanceResponse(ServerResponse response, int code) {

    }
}
