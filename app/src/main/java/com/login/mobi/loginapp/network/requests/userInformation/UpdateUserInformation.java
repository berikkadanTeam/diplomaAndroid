package com.login.mobi.loginapp.network.requests.userInformation;

import android.content.Context;

import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;
import com.login.mobi.loginapp.network.model.ServerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserInformation {

    String token, userID, surname, name;
    UpdateUserInformation.UpdateUserInformationInterface anInterface;

    public UpdateUserInformation(Context context, String token, String userID, String surname, String name) {
        anInterface = (UpdateUserInformation.UpdateUserInformationInterface) context;
        this.token = token;
        this.userID = userID;
        this.surname = surname;
        this.name = name;
    }

    public interface UpdateUserInformationInterface{
        public void getUpdateUserInformationResponse(ServerResponse response, int code);
    }



    public void updateProfile(){
        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call = service.updateProfile(token, userID, surname, name);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                anInterface.getUpdateUserInformationResponse(response.body(), response.code());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                anInterface.getUpdateUserInformationResponse(null, -1);
            }
        });
    }

}
