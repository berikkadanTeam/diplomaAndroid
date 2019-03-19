package com.login.mobi.loginapp.network.requests.restaurantInformation;

import android.util.Log;

import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;
import com.login.mobi.loginapp.network.model.restaurantInformation.RestaurantInformation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetRestaurantInformation {
    String userID;
    String token;
    GetRestaurantInformation.GetRestaurantInformationInterface anInterface;

    public GetRestaurantInformation(GetRestaurantInformation.GetRestaurantInformationInterface context, String userID, String token) {
        anInterface = context;
        this.userID = userID;
        this.token = token;
    }

    public interface GetRestaurantInformationInterface{
        public void getRestaurantInformation(RestaurantInformation response);
    }



    public void getRestaurantInformation(){

        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<RestaurantInformation> call = service.getRestaurantInformation(token, userID);
        call.enqueue(new Callback<RestaurantInformation>() {
            @Override
            public void onResponse(Call<RestaurantInformation> call, Response<RestaurantInformation> response) {
                anInterface.getRestaurantInformation(response.body());
                Log.d("mylog","body - " + response.body());
            }

            @Override
            public void onFailure(Call<RestaurantInformation> call, Throwable t) {
                anInterface.getRestaurantInformation(null);
                Log.d("mylog","error "+t.getLocalizedMessage());
            }
        });
    }


}
