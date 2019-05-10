package com.login.mobi.loginapp.network.requests.authorization;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;
import com.login.mobi.loginapp.network.model.ServerResponse;
import com.login.mobi.loginapp.network.model.authorization.SignIn;

import java.io.IOException;
import java.lang.reflect.Type;

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
        public void errorMessage(String error);
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
                Gson gson = new Gson();
                Type type = new TypeToken<ServerResponse>(){}.getType();
                ServerResponse response_message = new ServerResponse();
                try {
                    if (response.code() != 200) {
                        response_message = gson.fromJson(response.errorBody().string(), type);
                        if (response_message.getErr() != null) {
                            anInterface.errorMessage(response_message.getErr().get(0));
                            Log.d("postSignIn", "error message - " + response_message.getErr().get(0));
                        }
                        else if(response_message.getPassword() != null) {
                            anInterface.errorMessage(response_message.getPassword().get(0));
                            Log.d("postSignIn", "error password - " + response_message.getPassword().get(0));
//                        Log.d("postSignIn","code - " + ((ServerResponse)new Gson().fromJson
//                                (response.errorBody().string(),new TypeToken<ServerResponse>(){}.getType())).getErr().get(0));
                        }
                        else if(response_message.getStatus() != null){
                            anInterface.errorMessage(response_message.getStatus());
                            Log.d("postSignIn", "error password - " + response_message.getStatus());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SignIn> call, Throwable t) {
                anInterface.signIn(null,500);   // second parameter is a code
                Log.d("postSignIn","error - " + t.getLocalizedMessage());
            }
        });

    }



}
