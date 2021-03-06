package com.login.mobi.loginapp.network.requests.authorization;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;
import com.login.mobi.loginapp.network.model.ServerResponse;

import java.io.IOException;
import java.lang.reflect.Type;

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
    String phoneNumber;
    String role = "CommonUser";

    public PostSignUp(PostSignUpInterface context, String email, String password, String firstName, String lastName, String location, String phoneNumber) {
        anInterface = (PostSignUpInterface) context;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.phoneNumber = phoneNumber;
    }

    public interface PostSignUpInterface{
        public void signUp(ServerResponse response, int code);
    }



    public void postSignUp(){

        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call = service.signup(email, password, firstName, lastName, location, role, phoneNumber);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                Log.d("postSignUp",response.code() + " " + response.body());

                Gson gson = new Gson();
                Type type = new TypeToken<ServerResponse>(){}.getType();
                ServerResponse response_message = new ServerResponse();
                try {
                    if (response.code() != 200) {
                            response_message = gson.fromJson(response.errorBody().string(), type);
                            if (response_message.getDuplicateUserName() != null) {
                                anInterface.signUp(response_message, response.code());
                                //Log.d("postSignIn", "error message - " + response_message.get().get(0));
                            }
                            else
                                anInterface.signUp(response_message, response.code());
                    }
                    else{
                        anInterface.signUp(response.body(),response.code());
                    }
                    if (response.code() == 200 && response_message.getStatus() != null){
                        anInterface.signUp(response_message,response.code());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                anInterface.signUp(null,-1);
                Log.d("postSignUp","error - " + t.getLocalizedMessage());
            }
        });
    }



}
