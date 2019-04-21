package com.login.mobi.loginapp.network.requests.order;

import android.util.Log;

import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;
import com.login.mobi.loginapp.network.model.order.MyOrders;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetMyOrders {
    String userID;
    String token;
    GetMyOrdersInterface anInterface;

    public GetMyOrders(GetMyOrdersInterface context, String userID, String token) {
        anInterface = context;
        this.userID = userID;
        this.token = token;
    }

    public interface GetMyOrdersInterface{
        public void getMyOrders(List<MyOrders> response, int code);
    }



    public void getMyOrders(){

        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<List<MyOrders>> call = service.getMyOrders(token, userID);
        call.enqueue(new Callback<List<MyOrders>>() {
            @Override
            public void onResponse(Call<List<MyOrders>> call, Response<List<MyOrders>> response) {
                anInterface.getMyOrders(response.body(), response.code());
                Log.d("mylog","body - " + response.body());
            }

            @Override
            public void onFailure(Call<List<MyOrders>> call, Throwable t) {
                anInterface.getMyOrders(null, -1);
                Log.d("mylog","error " + t.getLocalizedMessage());
            }
        });
    }
}
