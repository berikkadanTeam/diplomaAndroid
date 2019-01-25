package com.login.mobi.loginapp.network.requests.restaurantInformation;

import android.util.Log;

import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;
import com.login.mobi.loginapp.network.model.restaurantInformation.RestaurantInformation;
import com.login.mobi.loginapp.network.model.restaurants.Restaurant;
import com.login.mobi.loginapp.network.requests.restaurants.GetRestaurants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetRestaurantInformation {

    GetRestaurantInformation.GetRestaurantInformationInterface anInterface;

    public GetRestaurantInformation(GetRestaurantInformation.GetRestaurantInformationInterface context) {
        anInterface = context;
    }

    public interface GetRestaurantInformationInterface{
        public void getRestaurantInformation(List<RestaurantInformation> response);
    }



    public void getRestaurantInformation(){

        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<List<RestaurantInformation>> call = service.getRestaurantInformation();
        call.enqueue(new Callback<List<RestaurantInformation>>() {
            @Override
            public void onResponse(Call<List<RestaurantInformation>> call, Response<List<RestaurantInformation>> response) {
                anInterface.getRestaurantInformation(response.body());
                Log.d("mylog","body - " + response.body());
            }

            @Override
            public void onFailure(Call<List<RestaurantInformation>> call, Throwable t) {
                anInterface.getRestaurantInformation(null);
                Log.d("mylog","error "+t.getLocalizedMessage());
            }
        });
    }


}
