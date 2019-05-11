package com.login.mobi.loginapp.views.client.menu.balance;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.ServerResponse;
import com.login.mobi.loginapp.network.requests.balance.FillBalance;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;
import com.login.mobi.loginapp.views.client.menu.profile.ProfilePage;


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
                String howMuch = balance.getText().toString();
                if (TextUtils.isEmpty(howMuch))
                    balance.setError("Введите сумму");
                else {
                    if (Double.parseDouble(howMuch) < 1) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BalancePage.this);
                        alertDialogBuilder.setIcon(R.drawable.icon_money_heart);
                        alertDialogBuilder.setTitle("Ошибка...");
                        alertDialogBuilder.setMessage("Невозможно пополнить баланс на сумму " + howMuch + " ₸");
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.setNegativeButton("ОК", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } else {
                        //Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        fillMyBalance();
                    }
                }
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void fillMyBalance(){
        FillBalance fillBalance = new FillBalance(this, "Bearer " + token, userID, balance.getText().toString());
        fillBalance.fillBalance();
    }


    @Override
    public void getFillBalanceResponse(ServerResponse response, int code) {
        if (code == 200){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BalancePage.this);
            alertDialogBuilder.setIcon(R.drawable.icon_money_heart);
            alertDialogBuilder.setTitle("Баланс успешно пополнен!");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(BalancePage.this, ProfilePage.class);
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BalancePage.this);
            alertDialogBuilder.setIcon(R.drawable.icon_error);
            alertDialogBuilder.setTitle("Ошибка!");
            alertDialogBuilder.setMessage("Не удалось пополнить баланс");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setNegativeButton("ОК", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
}
