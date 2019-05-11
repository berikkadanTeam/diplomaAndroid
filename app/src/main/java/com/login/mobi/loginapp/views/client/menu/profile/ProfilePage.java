package com.login.mobi.loginapp.views.client.menu.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.userInformation.UserInformation;
import com.login.mobi.loginapp.network.requests.userInformation.GetUserInformation;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;
import com.login.mobi.loginapp.views.client.authorization.WelcomePage;


public class ProfilePage extends AppCompatActivity implements GetUserInformation.GetUserInformationInterface {
    // xml elements: texts, buttons
    private Button exitBtn;
    FloatingActionButton editProfileBtn;
    private TextView fullName, balance, surname, name, birthDate, email, phone;
    private CardView balanceBlock;

    // Shared Preferences
    SingletonSharedPref sharedPref;
    private String userID, token, role;


    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_profile_page);


        sharedPref = SingletonSharedPref.getInstance(this);
        userID = sharedPref.getString(SingletonSharedPref.USER_ID);
        token = sharedPref.getString(SingletonSharedPref.TOKEN);
        role = sharedPref.getString(SingletonSharedPref.ROLE);
        GetUserInformation getUserInformation = new GetUserInformation(this, userID, "Bearer " + token);
        //getUserInformation.getUserInformation();
        getUserInformation.getUserInformation(sharedPref.getmPref());


        progressDialog = new ProgressDialog(this, R.style.ProgressDialogInCenter);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);
        progressDialog.show();

        balanceBlock = (CardView) findViewById(R.id.cardView);
        balance = (TextView) findViewById(R.id.balance);
        fullName = (TextView) findViewById(R.id.full_name);
        balance = (TextView) findViewById(R.id.balance);
        surname = (TextView) findViewById(R.id.surname);
        name = (TextView) findViewById(R.id.name);
        //birthDate = (TextView) findViewById(R.id.birth_date);
        email = (TextView) findViewById(R.id.email);
        phone = (TextView) findViewById(R.id.phone);

        editProfileBtn = findViewById(R.id.edit_profile_button);
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                startActivity(new Intent(ProfilePage.this, ProfileEditPage.class));
            }
        });

        exitBtn = findViewById(R.id.exit_button);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPref.clear();
                //Intent intent = new Intent(ProfilePage.this, WelcomePage.class); // == startActivity(new Intent(RestaurantInformationPage.this, RestaurantWebViewPage.class));
                //startActivity(intent);
                startActivity(new Intent(ProfilePage.this, WelcomePage.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                ProfilePage.this.finish();
            }
        });
    }

    @Override
    public void getUserInformation(UserInformation response) {
            Log.d("UserInformation", response.toString() + " ");
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            Log.d("ROLE", role);
            // Блок баланса отображается только у клиента
            if (role.equalsIgnoreCase("Admin") || role.equalsIgnoreCase("Waiter") || role.equalsIgnoreCase("KitchenStuff")){
                balanceBlock.setVisibility(View.GONE);
            } else {
                balanceBlock.setVisibility(View.VISIBLE);
                balance.setText(Integer.toString(response.getVirtualMoney()) + " ₸");
            }

            //balance.setText(Integer.toString(response.getVirtualMoney()) + " ₸");
            fullName.setText(response.getLastName() + " " + response.getFirstName());
            surname.setText(response.getLastName());
            name.setText(response.getFirstName());
            //birthDate.setText("Пока такие данные не присылаются");
            email.setText(response.getUserName());
            if (response.getPhoneNumber() != null)
                phone.setText(response.getPhoneNumber());
            else
                phone.setText("");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
