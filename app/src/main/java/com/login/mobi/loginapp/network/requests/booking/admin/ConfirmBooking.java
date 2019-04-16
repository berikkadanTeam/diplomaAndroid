package com.login.mobi.loginapp.network.requests.booking.admin;

import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.BaseApi;
import com.login.mobi.loginapp.network.model.ServerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmBooking {

    String token, bookingId;
    boolean isConfirmed;
    ConfirmBooking.ConfirmBookingInterface confirmBookingInterface;

    public ConfirmBooking(ConfirmBooking.ConfirmBookingInterface confirmBookingInterface, String token, String bookingId, boolean isConfirmed) {
        this.confirmBookingInterface = confirmBookingInterface;
        this.token = token;
        this.bookingId = bookingId;
        this.isConfirmed = isConfirmed;
    }

    public interface ConfirmBookingInterface{
        public void getConfirmBookingResponse(ServerResponse response, int code);
    }



    public void confirmBooking(){
        ApiInterface service = BaseApi.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call = service.confirmBooking(token, bookingId, isConfirmed);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                confirmBookingInterface.getConfirmBookingResponse(response.body(), response.code());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                confirmBookingInterface.getConfirmBookingResponse(null, -1);
            }
        });
    }


}
