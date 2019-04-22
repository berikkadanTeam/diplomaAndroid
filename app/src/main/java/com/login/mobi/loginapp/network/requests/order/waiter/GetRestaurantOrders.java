package com.login.mobi.loginapp.network.requests.order.waiter;

import android.util.Log;

import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;
import com.login.mobi.loginapp.network.model.order.MyOrders;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetRestaurantOrders {

    String restaurantID;
    String token;
    GetRestaurantOrders.GetRestaurantOrdersInterface anInterface;

    public GetRestaurantOrders(GetRestaurantOrders.GetRestaurantOrdersInterface context, String restaurantID, String token) {
        anInterface = context;
        this.restaurantID = restaurantID;
        this.token = token;
    }

    public interface GetRestaurantOrdersInterface{
        public void getRestaurantOrders(List<MyOrders> response, int code);
    }



    public void getRestaurantOrders(){

        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<List<MyOrders>> call = service.getRestaurantOrders(token, restaurantID);
        call.enqueue(new Callback<List<MyOrders>>() {
            @Override
            public void onResponse(Call<List<MyOrders>> call, Response<List<MyOrders>> response) {
                anInterface.getRestaurantOrders(response.body(), response.code());
                Log.d("mylog","body - " + response.body());
            }

            @Override
            public void onFailure(Call<List<MyOrders>> call, Throwable t) {
                anInterface.getRestaurantOrders(null, -1);
                Log.d("mylog","error " + t.getLocalizedMessage());
            }
        });
    }

}
