package com.login.mobi.loginapp.network.requests.authorization;

import android.content.Context;
import android.util.Log;

import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostSignUp {

    PostSignUpInterface anInterface;

    String email;
    String firstName;
    String lastName;
    String password;
    String location;

    public PostSignUp(Context context, String email, String firstName, String lastName, String password, String location) {
        anInterface = (PostSignUpInterface) context;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.password = password;
    }

    public interface PostSignUpInterface{
        public void signUp(int response);
    }



    public void postSignUp(){

        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<String> call = service.signup(email, firstName, lastName, password, location);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("nurila",response.code()+"");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("nurila","Failure");
            }
        });
    }



}
