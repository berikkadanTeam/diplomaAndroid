package com.login.mobi.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.login.mobi.loginapp.network.model.cities.Cities;
import com.login.mobi.loginapp.network.requests.GetCities;
import com.login.mobi.loginapp.network.requests.auth.PostSignUp;

import java.util.List;

public class SignUpPage extends AppCompatActivity implements GetCities.GetCitiesInterface,PostSignUp.PostSignUpInterface{

    private EditText email;
    private EditText password;
    private EditText firstName;
    private EditText lastName;

    private AutoCompleteTextView acTextView;

    private Button btnCancel;
    private Button btnRegister;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_page);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        btnCancel = findViewById(R.id.cancel);
        btnRegister = findViewById(R.id.register);

        //Find TextView control
        acTextView = (AutoCompleteTextView) findViewById(R.id.cities);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpPage.this, WelcomePage.class));
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str1 = email.getText().toString();
                String str2 = password.getText().toString();
                String str3 = firstName.getText().toString();
                String str4 = lastName.getText().toString();
                String str5 = acTextView.getText().toString();
                signUp(str1, str2, str3, str4, str5);
            }
        });






        Log.d("Cities","cities");
        GetCities getCities = new GetCities(this);
        getCities.getCities();


    }

    public void signUp(String str1, String str2, String str3, String str4, String str5){
        Log.d("nurila",str1 + str2 + str3 + str4 + str5);
        PostSignUp postSignUp = new PostSignUp(this,str1, str2, str3, str4, str5);
        postSignUp.postSignUp();
    }

    @Override
    public void getCities(List<Cities> response) {
        if(response != null){
            Toast.makeText(this,"" + response.size(), Toast.LENGTH_LONG).show();

            String[] arrayOfCities = new String[response.size()];

            for (int i = 0; i < response.size(); i++) {
                Cities element = response.get(i);
                arrayOfCities[i] = response.get(i).getCityTitle();
            }
            //Create Array Adapter
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, arrayOfCities);
            //Set the number of characters the user must type before the drop down list is shown
            acTextView.setThreshold(1);
            //Set the adapter
            acTextView.setAdapter(adapter);

        }else{
            Toast.makeText(this,"NULL", Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public void signUp(int response) {

    }
}
