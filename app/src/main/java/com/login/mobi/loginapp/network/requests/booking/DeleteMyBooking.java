package com.login.mobi.loginapp.network.requests.booking;

import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;
import com.login.mobi.loginapp.network.model.ServerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteMyBooking {

    String token, bookingId;
    DeleteMyBooking.DeleteMyBookingInterface deleteBookingInterface;

    public DeleteMyBooking(DeleteMyBooking.DeleteMyBookingInterface deleteBookingInterface, String token, String bookingId) {
        this.deleteBookingInterface = deleteBookingInterface;
        this.token = token;
        this.bookingId = bookingId;
    }

    public interface DeleteMyBookingInterface{
        public void getDeleteBookingResponse(ServerResponse response, int code);
    }



    public void deleteBooking(){
        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call = service.deleteBooking(token, bookingId);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                deleteBookingInterface.getDeleteBookingResponse(response.body(), response.code());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                deleteBookingInterface.getDeleteBookingResponse(null, -1);
            }
        });
    }

}

