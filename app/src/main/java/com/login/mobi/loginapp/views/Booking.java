package com.login.mobi.loginapp.views;

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

}
