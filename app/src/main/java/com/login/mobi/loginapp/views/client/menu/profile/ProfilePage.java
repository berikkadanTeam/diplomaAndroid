package com.login.mobi.loginapp.views.client.menu.profile;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.NetworkUtils;
import com.login.mobi.loginapp.network.SignalRService;
import com.login.mobi.loginapp.network.model.userInformation.UserInformation;
import com.login.mobi.loginapp.network.requests.userInformation.GetUserInformation;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;
import com.login.mobi.loginapp.views.client.authorization.WelcomePage;


// from: https://awsrh.blogspot.com/2017/10/modern-profile-ui-design-in-android.html

public class ProfilePage extends AppCompatActivity implements GetUserInformation.GetUserInformationInterface {
    // xml elements: texts, buttons
    private Button exitBtn;
    FloatingActionButton editProfileBtn;
    private TextView fullName, balance, surname, name, birthDate, email, phone;

    // Shared Preferences
    SingletonSharedPref sharedPref;
    private String userID, token;

    // variables
    private boolean isInternetConnection = false;
    private LinearLayout errorLayout;
    private ImageView errorImageView;
    private TextView errorMessageTextView;

    private ProgressDialog progressDialog;


    private boolean bounded;
    private SignalRService signalRService;

    private ServiceConnection signalRServiceConnection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bounded = true;
            SignalRService.LocalBinder localBinder= (SignalRService.LocalBinder) service;
            signalRService=localBinder.getInstance();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bounded=false;
            signalRService=null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_profile_page);

        errorLayout = (LinearLayout) findViewById(R.id.error_container);
        errorLayout.setVisibility(View.GONE);
        errorImageView = (ImageView) findViewById(R.id.error_imageview);
        errorMessageTextView = (TextView) findViewById(R.id.error_textview);
        CardView tryAgainButton = (CardView) findViewById(R.id.error_try_again_button);
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorLayout.setVisibility(View.GONE);
                getSupportActionBar().show();
            }
        });

        if (NetworkUtils.isNetworkConnected(this) == false){
            errorLayout.setVisibility(View.VISIBLE);
        } else {

            sharedPref = SingletonSharedPref.getInstance(this);
            userID = sharedPref.getString(SingletonSharedPref.USER_ID);
            token = sharedPref.getString(SingletonSharedPref.TOKEN);
            GetUserInformation getUserInformation = new GetUserInformation(this, userID, "Bearer " + token);
            //getUserInformation.getUserInformation();
            getUserInformation.getUserInformation(sharedPref.getmPref());


            progressDialog = new ProgressDialog(this, R.style.ProgressDialogInCenter);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            progressDialog.setCancelable(false);
            progressDialog.show();

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

            connectToSignalRService();
        }
    }

    private void connectToSignalRService() {
        Intent signalRServiceIntent=new Intent(getBaseContext(),SignalRService.class);
        startService(signalRServiceIntent);
        bindService(signalRServiceIntent,signalRServiceConnection,BIND_AUTO_CREATE);
    }

    @Override
    public void getUserInformation(UserInformation response) {
            Log.d("UserInformation", response.toString() + " ");
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            balance.setText(Integer.toString(response.getVirtualMoney()));
            fullName.setText(response.getLastName() + " " + response.getFirstName());
            surname.setText(response.getLastName());
            name.setText(response.getFirstName());
            //birthDate.setText("Пока такие данные не присылаются");
            email.setText(response.getUserName());
            phone.setText("НЕТ ТАКОЙ ИНФЫ");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(bounded){
            unbindService(signalRServiceConnection);
        }
    }

}
