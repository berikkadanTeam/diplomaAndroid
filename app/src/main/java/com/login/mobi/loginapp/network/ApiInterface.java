package com.login.mobi.loginapp.network;

/**
 * Created by nurilkaa on 10/2/18.
 */

import com.login.mobi.loginapp.network.model.ServerResponse;
import com.login.mobi.loginapp.network.model.authorization.SignIn;
import com.login.mobi.loginapp.network.model.booking.MyBookings;
import com.login.mobi.loginapp.network.model.booking.MyRestaurantBookings;
import com.login.mobi.loginapp.network.model.booking.TableBookingWithPreorder;
import com.login.mobi.loginapp.network.model.cities.Cities;
import com.login.mobi.loginapp.network.model.discounts.Discount;
import com.login.mobi.loginapp.network.model.order.MyOrders;
import com.login.mobi.loginapp.network.model.order.Order;
import com.login.mobi.loginapp.network.model.personnel.Personnel;
import com.login.mobi.loginapp.network.model.restaurantInformation.RestaurantInformation;
import com.login.mobi.loginapp.network.model.restaurantMenu.RestaurantDishTypes;
import com.login.mobi.loginapp.network.model.restaurantMenu.RestaurantDishes;
import com.login.mobi.loginapp.network.model.restaurants.Restaurant;
import com.login.mobi.loginapp.network.model.userInformation.UserInformation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

//import okhttp


public interface ApiInterface {

    @GET("api/Restaurants/GetRestaurants")
    Call<List<Restaurant>> getRestaurants();

    @GET("api/Restaurants/GetCities")
    Call<List<Cities>> getCities();

    @FormUrlEncoded
    @POST("api/accounts")
    Call<ServerResponse> signup(
            @Field("email")     String email,
            @Field("password")  String password,
            @Field("firstName") String firstName,
            @Field("lastName")  String lastName,
            @Field("location")  String location,
            @Field("UserRole")  String role
    );

    @FormUrlEncoded
    @POST("api/auth/login")
    Call<SignIn> signin(
            @Field("userName")  String username,
            @Field("password")  String password
    );

    // http://qaru.site/questions/419191/retrofit-authorization-bearer-token
    @GET("api/Users/GetUserInfo")
    Call<UserInformation> getUserInformation(
            @Header("Authorization") String authHeader,
            @Query("UserId") String userID
    );

    @GET("api/Restinfo/GetDishType")
    Call<List<RestaurantDishTypes>> getRestaurantDishTypes(
            @Header("Authorization") String authHeader
    );

    @GET("/api/Restinfo/GetRestaurantMenu")
    Call<List<RestaurantDishes>> getRestaurantDishes(
            @Header("Authorization") String authHeader,
            @Query("restarauntId") String restaurantID
    );

    @POST("/api/Booking/BookingTable")  // бронирование
    Call<ServerResponse> bookTable(
            @Header("Authorization") String authHeader,
            @Body TableBookingWithPreorder bookingData
    );

    @GET("api/Booking/GetBookedTableForClient")
    Call<List<MyBookings>> getMyBookings(
            @Header("Authorization") String authHeader,
            @Query("userId") String userID
    );

    @DELETE("/api/booking/DeleteReserve")   // отмена бронирования
    Call<ServerResponse> deleteBooking(
            @Header("Authorization") String authHeader,
            @Query("reserveId") String id
    );

    @POST("/api/Order/MakeOrder")
    Call<ServerResponse> makeAnOrder(
            @Header("Authorization") String authHeader,
            @Body Order orderData
    );

    @GET("api/Order/GetOrder")
    Call<List<MyOrders>> getMyOrders(
            @Header("Authorization") String authHeader,
            @Query("UserId") String userID
    );

    @GET("/api/Restinfo/GetPromotion")
    Call<List<Discount>> getDiscounts(
            @Header("Authorization") String authHeader
    );

    @GET("api/Restaurants/GetRestaurants")
    Call<List<RestaurantInformation>> getRestaurantInformation();
    // OR
    @GET("api/Restaurants/GetRestaurants")
    Call<RestaurantInformation> getRestaurantInformation(@Query("restaurantId") String restaurantId);




    // ADMIN APIs
    @GET("api/Restinfo/GetUserRestaurant")
    Call<RestaurantInformation> getRestaurantInformation(
            @Header("Authorization") String authHeader,
            @Query("userId") String userID
    );

    @GET("/api/Restinfo/GetRestaurantPersonal")
    Call<List<Personnel>> getPersonnel(
            @Header("Authorization") String authHeader,
            @Query("restaurantId") String restaurantID
    );

    @GET("api/Booking/GetBookedTables")
    Call<List<MyRestaurantBookings>> getMyRestaurantBookings(
            @Header("Authorization") String authHeader,
            @Query("restarauntId") String restarauntID
    );

    @POST("/api/Booking/ConfirmationReserv")
    Call<ServerResponse> confirmBooking(
            @Header("Authorization") String authHeader,
            @Query("reserveId") String id
    );

    @PUT("/api/Booking/RejectTableReserv")
    Call<ServerResponse> rejectBooking(
            @Header("Authorization") String authHeader,
            @Query("reserveId") String id
    );

    // WAITER APIs

}
