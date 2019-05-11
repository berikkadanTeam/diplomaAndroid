package com.login.mobi.loginapp.views.client.authorization;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.ServerResponse;
import com.login.mobi.loginapp.network.requests.authorization.PostSignUp;

public class SignUpPage extends AppCompatActivity implements PostSignUp.PostSignUpInterface{ //GetCities.GetCitiesInterface,

    private EditText email, password, firstName, lastName, phoneNumber;

    private AutoCompleteTextView acTextView;

    private Button btnCancel, btnRegister;

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
        phoneNumber = findViewById(R.id.phone_number);
        btnCancel = findViewById(R.id.cancel);
        btnRegister = findViewById(R.id.register);

        //Find TextView control
        acTextView = (AutoCompleteTextView) findViewById(R.id.cities);

        phoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

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
                String str6 = phoneNumber.getText().toString();

                if (str2.length() < 6) {
                    password.setError("Пароль должен состоять минимум из 6 символов");
                    //Snackbar.make(parentLayout, "Пароль должен состоять минимум из 6 символов", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
//                    if (!str1.isEmpty() && !str2.isEmpty() && !str3.isEmpty() && !str4.isEmpty() && !str5.isEmpty()) {
//                        signUp(str1, str2, str3, str4, str5);
//                    } else {
//                        Snackbar.make(parentLayout, "Заполните все поля", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//                    }
                    boolean fieldsOK = validate(new EditText[] { email, password, firstName, lastName, acTextView, phoneNumber });
                    if (!fieldsOK) {
                        if (!isEmailValid(str1))
                            email.setError("Адрес электронной почты недействителен");
                        else
                            signUp(str1, str2, str3, str4, str5, str6);
                    }
                }
            }

            //}
        });


//        GetCities getCities = new GetCities(this);
//        getCities.getCities();

    }

    public void signUp (String str1, String str2, String str3, String str4, String str5, String str6){
        Log.d("SignUp",str1 + " " + str2 + " " + str3 + " " + str4 + " " + str5 + " " + str6);
        PostSignUp postSignUp = new PostSignUp(this, str1, str2, str3, str4, str5, str6);
        postSignUp.postSignUp();
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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

//    @Override
//    public void getCities(List<Cities> response) {
//        if(response != null){
//            Log.d("Cities",response.toString());
//            Toast.makeText(this,"" + response.size(), Toast.LENGTH_LONG).show();
//
//            String[] arrayOfCities = new String[response.size()];
//            for (int i = 0; i < response.size(); i++) {
//                Cities element = response.get(i);
//                arrayOfCities[i] = response.get(i).getCityTitle();
//            }
//            //Create Array Adapter
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, arrayOfCities);
//            acTextView.setThreshold(1);     //Set the number of characters the user must type before the drop down list is shown
//            acTextView.setAdapter(adapter); //Set the adapter
//
//        } else{
//            Toast.makeText(this,"Список городов пуст", Toast.LENGTH_LONG).show();
//        }
//    }



    @Override
    public void signUp(ServerResponse response, int code) {
        Log.d("CODE", String.valueOf(code));
        if (code == 200){
//            Snackbar.make(parentLayout, "Регистрация прошла успешно!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    startActivity(new Intent(SignUpPage.this, SignInPage.class));
//                    finish();
//                }
//            }, 1000);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignUpPage.this);
            alertDialogBuilder.setIcon(R.drawable.icon_email_connect);
            alertDialogBuilder.setTitle("Письмо отправлено!");
            alertDialogBuilder.setMessage(response.getStatus());
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(SignUpPage.this, SignInPage.class);
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


        } else {
            if (response.getDuplicateUserName() != null){
                //Snackbar.make(parentLayout, "Пользователь с электронной почтой " + email.getText().toString() + " уже существует, введите другую почту", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignUpPage.this);
                alertDialogBuilder.setIcon(R.drawable.icon_email_error);
                alertDialogBuilder.setTitle("Ошибка...");
                alertDialogBuilder.setMessage("Пользователь с электронной почтой " + email.getText().toString() + " уже существует, введите другую почту");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setNegativeButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                      Intent intent = new Intent(SignUpPage.this, SignInPage.class);
//                      startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
//                      finish();
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, WelcomePage.class));
        finish();
    }
}
