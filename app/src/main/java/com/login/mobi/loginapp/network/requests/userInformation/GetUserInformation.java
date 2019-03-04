package com.login.mobi.loginapp.network.requests.userInformation;

import android.util.Log;

import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;
import com.login.mobi.loginapp.network.model.userInformation.UserInformation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetUserInformation {
    String userID;
    String token;
    com.login.mobi.loginapp.network.requests.userInformation.GetUserInformation.GetUserInformationInterface anInterface;

    public GetUserInformation(com.login.mobi.loginapp.network.requests.userInformation.GetUserInformation.GetUserInformationInterface context, String userID, String token) {
        anInterface = context;
        this.userID = userID;
        this.token = token;
    }

    public interface GetUserInformationInterface{
        public void getUserInformation(UserInformation response);
    }



    public void getUserInformation(){

        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<UserInformation> call = service.getUserInformation(token, userID);
        call.enqueue(new Callback<UserInformation>() {
            @Override
            public void onResponse(Call<UserInformation> call, Response<UserInformation> response) {
                anInterface.getUserInformation(response.body());
                Log.d("mylog","body - " + response.body());
            }

            @Override
            public void onFailure(Call<UserInformation> call, Throwable t) {
                anInterface.getUserInformation(null);
                Log.d("mylog","error " + t.getLocalizedMessage());
            }
        });
    }


}
