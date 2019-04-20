package com.login.mobi.loginapp.network.requests.booking.admin;

import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;
import com.login.mobi.loginapp.network.model.ServerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RejectBooking {

    String token, bookingId;
    RejectBooking.RejectBookingInterface rejectBookingInterface;

    public RejectBooking(RejectBooking.RejectBookingInterface rejectBookingInterface, String token, String bookingId) {
        this.rejectBookingInterface = rejectBookingInterface;
        this.token = token;
        this.bookingId = bookingId;
    }

    public interface RejectBookingInterface{
        public void getRejectBookingResponse(ServerResponse response, int code);
    }



    public void rejectBooking(){
        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call = service.rejectBooking(token, bookingId);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                rejectBookingInterface.getRejectBookingResponse(response.body(), response.code());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                rejectBookingInterface.getRejectBookingResponse(null, -1);
            }
        });
    }


}
