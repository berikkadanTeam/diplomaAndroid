package com.login.mobi.loginapp.network.requests.authorization;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;
import com.login.mobi.loginapp.network.model.authorization.SignIn;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostSignIn {

    PostSignInInterface anInterface;

    String email;
    String password;

    public PostSignIn(Context context, String email, String password) {
        anInterface = (PostSignInInterface) context;
        this.email = email;
        this.password = password;
    }

    public interface PostSignInInterface{
        public void signIn(SignIn response, int code);
    }



    public void postSignIn(){

        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<SignIn> call = service.signin(email,password);
        call.enqueue(new Callback<SignIn>() {
            @Override
            public void onResponse(Call<SignIn> call, Response<SignIn> response) {
                anInterface.signIn(response.body(),response.code());
                Log.d("postSignIn","body - " + new Gson().toJson(response.body()));
                Log.d("postSignIn","code - " + response.code());
            }

            @Override
            public void onFailure(Call<SignIn> call, Throwable t) {
                anInterface.signIn(null,500);   // second parameter is a code
                Log.d("postSignIn","error - " + t.getLocalizedMessage());
            }
        });

    }



}
