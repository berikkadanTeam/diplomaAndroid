package com.login.mobi.loginapp.views.client.menu.profile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.ServerResponse;
import com.login.mobi.loginapp.network.model.userInformation.UserInformation;
import com.login.mobi.loginapp.network.requests.userInformation.GetUserInformation;
import com.login.mobi.loginapp.network.requests.userInformation.UpdateUserInformation;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;


// from: https://awsrh.blogspot.com/2017/10/modern-profile-ui-design-in-android.html

public class ProfileEditPage extends AppCompatActivity implements GetUserInformation.GetUserInformationInterface, UpdateUserInformation.UpdateUserInformationInterface {
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
        //birthDate = (EditText) findViewById(R.id.birth_date);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);



        saveBtn = findViewById(R.id.save_button);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ProfileEditPage.this, WelcomePage.class));
                boolean fieldsOK = validate(new EditText[] { surname, name });
                if (!fieldsOK) {
                    updateProfileInformation();
                }
            }
        });
    }


    private boolean validate (EditText[] fields){
        boolean foundedEmptyEditText = false;
        for (int i = 0; i < fields.length; i++){
            EditText currentField = fields[i];
            if (currentField.getText().toString().length() <= 0){
                currentField.setError("Заполните поле");
                foundedEmptyEditText = true;
                //return false;
            }
        }
        if (!foundedEmptyEditText)
            return false;
        else
            return true;
    }


    @Override
    public void getUserInformation(UserInformation response) {
        Log.d("UserInformation", response.toString() + " ");
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        surname.setText(response.getLastName());
        name.setText(response.getFirstName());
        //birthDate.setText("Пока такие данные не присылаются");
        email.setText(response.getUserName());
        if (response.getPhoneNumber() != null)
            phone.setText(response.getPhoneNumber());
        else
            phone.setText("");
    }


    private void updateProfileInformation(){
        UpdateUserInformation updateProfile = new UpdateUserInformation(this, "Bearer " + token, userID, surname.getText().toString(), name.getText().toString());
        updateProfile.updateProfile();
    }


    @Override
    public void getUpdateUserInformationResponse(ServerResponse response, int code) {
        if (code == 200){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProfileEditPage.this);
            alertDialogBuilder.setIcon(R.drawable.icon_user_edit);
            alertDialogBuilder.setTitle("Данные профиля успешно изменены!");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(ProfileEditPage.this, ProfilePage.class);
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProfileEditPage.this);
            alertDialogBuilder.setIcon(R.drawable.icon_error);
            alertDialogBuilder.setTitle("Ошибка!");
            alertDialogBuilder.setMessage("Данные профиля не удалось изменить");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(ProfileEditPage.this, ProfilePage.class);
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }


}
