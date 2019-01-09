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

import java.util.List;

public class SignUpPage extends AppCompatActivity implements GetCities.GetCitiesInterface{

    private EditText email;
    private EditText password;
    private EditText firstName;
    private EditText lastName;

    private Button btnCancel;
    private Button btnRegister;

    String[] languages = { "C","C++","Java","C#","PHP","JavaScript","jQuery","AJAX","JSON" };


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


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpPage.this, WelcomePage.class));
                finish();
            }
        });



        //Create Array Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, languages);
        //Find TextView control
        AutoCompleteTextView acTextView = (AutoCompleteTextView) findViewById(R.id.cities);
        //Set the number of characters the user must type before the drop down list is shown
        acTextView.setThreshold(1);
        //Set the adapter
        acTextView.setAdapter(adapter);

    }


    @Override
    public void getCities(List<Cities> response) {
        if(response != null){
            //String [] cities = response.toArray(new String[response.size()]);
            Toast.makeText(this, response.size(), Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this,"NULL", Toast.LENGTH_LONG).show();
        }

    }
}
