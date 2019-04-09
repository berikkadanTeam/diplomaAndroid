package com.login.mobi.loginapp.views.client;

import com.login.mobi.loginapp.network.model.booking.Menu;
import com.login.mobi.loginapp.network.model.restaurants.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class Booking {
    public static String tableID;
    public static Restaurant restaurant;
    public static String date, time, preferences;
    public static Integer guests;
    public static List<Menu> preorder = new ArrayList<>();

    public static void clear(){
        tableID = null;
        restaurant = null;
        date = null;
        time = null;
        preferences = null;
        guests = null;
        preorder = new ArrayList<>();
    }
}
