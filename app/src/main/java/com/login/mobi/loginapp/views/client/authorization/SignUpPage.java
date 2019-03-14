package com.login.mobi.loginapp.views.client.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.ServerResponse;
import com.login.mobi.loginapp.network.model.cities.Cities;
import com.login.mobi.loginapp.network.requests.authorization.PostSignUp;
import com.login.mobi.loginapp.network.requests.cities.GetCities;

import java.util.List;

public class SignUpPage extends AppCompatActivity implements GetCities.GetCitiesInterface, PostSignUp.PostSignUpInterface{

    private EditText email;
    private EditText password;
    private EditText firstName;
    private EditText lastName;

    private AutoCompleteTextView acTextView;

    private Button btnCancel;
    private Button btnRegister;

    // Snackbar
    View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_page);
        parentLayout = findViewById(android.R.id.content);

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
                signUp (str1, str2, str3, str4, str5);
            }
        });



        GetCities getCities = new GetCities(this);
        getCities.getCities();

    }

    public void signUp(String str1, String str2, String str3, String str4, String str5){
        Log.d("SignUpActivity",str1 + " " + str2 + " " + str3 + " " + str4 + " " + str5);
        PostSignUp postSignUp = new PostSignUp(this, str1, str2, str3, str4, str5);
        postSignUp.postSignUp();
    }


    @Override
    public void getCities(List<Cities> response) {
        if(response != null){
            Log.d("Cities",response.toString());
            Toast.makeText(this,"" + response.size(), Toast.LENGTH_LONG).show();

            String[] arrayOfCities = new String[response.size()];
            for (int i = 0; i < response.size(); i++) {
                Cities element = response.get(i);
                arrayOfCities[i] = response.get(i).getCityTitle();
            }
            //Create Array Adapter
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, arrayOfCities);
            acTextView.setThreshold(1);     //Set the number of characters the user must type before the drop down list is shown
            acTextView.setAdapter(adapter); //Set the adapter

        } else{
            Toast.makeText(this,"Список городов пуст", Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public void signUp(ServerResponse response, int code) {
        Log.d("CODE", String.valueOf(code));
        if (code == 200){
            Snackbar.make(parentLayout, "Регистрация прошла успешно!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SignUpPage.this, SignInPage.class));
                    finish();
                }
            }, 1000);


        } else {
            if (response.getDuplicateUserName()!= null){
                Snackbar.make(parentLayout, "Пользователь с электронной почтой " + email.getText().toString() + " уже существует, введите другой почтовый ящик", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }

        }
    }

}
