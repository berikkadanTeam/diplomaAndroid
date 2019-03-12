package com.login.mobi.loginapp.network.requests.order;

import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;
import com.login.mobi.loginapp.network.model.ServerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MakeAnOrder {
    String token;
    com.login.mobi.loginapp.network.model.order.Order order;
    OrderInterface orderInterface;

    public MakeAnOrder(com.login.mobi.loginapp.network.model.order.Order order, OrderInterface orderInterface, String token) {
        this.order = order;
        this.orderInterface = orderInterface;
        this.token = token;
    }

    public interface OrderInterface{
        public void getOrder(ServerResponse response);
    }



    public void makeAnOrder(){
        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call = service.makeAnOrder(token, order);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                orderInterface.getOrder(response.body());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                orderInterface.getOrder(null);
            }
        });
    }


}
