package com.login.mobi.loginapp.network.requests.order.waiter;

import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;
import com.login.mobi.loginapp.network.model.ServerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptOrder {

    String token, orderID, waiterID;
    AcceptOrder.AcceptOrderInterface anInterface;

    public AcceptOrder(AcceptOrder.AcceptOrderInterface anInterface, String token, String orderID, String waiterID) {
        this.anInterface = anInterface;
        this.token = token;
        this.orderID = orderID;
        this.waiterID = waiterID;
    }

    public interface AcceptOrderInterface{
        public void getAcceptOrderResponse(ServerResponse response, int code);
    }



    public void acceptOrder(){
        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call = service.acceptOrder(token, orderID, waiterID);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                anInterface.getAcceptOrderResponse(response.body(), response.code());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                anInterface.getAcceptOrderResponse(null, -1);
            }
        });
    }

}
