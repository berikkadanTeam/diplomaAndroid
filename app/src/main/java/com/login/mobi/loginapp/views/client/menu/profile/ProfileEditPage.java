package com.login.mobi.loginapp.views.client.menu.profile;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.userInformation.UserInformation;
import com.login.mobi.loginapp.network.requests.userInformation.GetUserInformation;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;


// from: https://awsrh.blogspot.com/2017/10/modern-profile-ui-design-in-android.html

public class ProfileEditPage extends AppCompatActivity implements GetUserInformation.GetUserInformationInterface {
    // xml elements: texts, buttons
    private Button saveBtn;
    FloatingActionButton editProfileBtn;
    private EditText surname, name, birthDate, email, phone;

    // Shared Preferences
    SingletonSharedPref sharedPref;
    private String userID, token;


    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_profile_edit_page);

        sharedPref = SingletonSharedPref.getInstance(this);
        userID = sharedPref.getString(SingletonSharedPref.USER_ID);
        token = sharedPref.getString(SingletonSharedPref.TOKEN);
        GetUserInformation getUserInformation = new GetUserInformation(this, userID, "Bearer " + token);
//      getUserInformation.getUserInformation();
        getUserInformation.getUserInformation(sharedPref.getmPref());


        progressDialog = new ProgressDialog(this, R.style.ProgressDialogInCenter);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);
        progressDialog.show();


        surname = (EditText) findViewById(R.id.surname);
        name = (EditText) findViewById(R.id.name);
        birthDate = (EditText) findViewById(R.id.birth_date);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);



        saveBtn = findViewById(R.id.save_button);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ProfileEditPage.this, WelcomePage.class));
            }
        });
    }

    @Override
    public void getUserInformation(UserInformation response) {
        Log.d("UserInformation", response.toString() + " ");
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        surname.setText(response.getLastName());
        name.setText(response.getFirstName());
        birthDate.setText("Пока такие данные не присылаются");
        email.setText(response.getUserName());
        phone.setText("Пока такие данные не присылаются");
    }
}
