package com.login.mobi.loginapp.views.waiter;

import com.login.mobi.loginapp.R;

import java.util.ArrayList;


public class AddMenuItems {

    public static ArrayList<MenuItem> getSpaceships() {

        ArrayList<MenuItem> menuList = new ArrayList<>();

        MenuItem s = new MenuItem();
        s.setName("Мой профиль");
        s.setImage(R.drawable.icon_profile_rounded);
        menuList.add(s);

        s = new MenuItem();
        s.setName("Ресторан");
        s.setImage(R.drawable.icon_food_2);
        menuList.add(s);

        s = new MenuItem();
        s.setName("Бронирования");
        s.setImage(R.drawable.icon_calendar);
        menuList.add(s);

        s = new MenuItem();
        s.setName("Заказы");
        s.setImage(R.drawable.icon_clock);
        menuList.add(s);

        s = new MenuItem();
        s.setName("Баланс");
        s.setImage(R.drawable.icon_money_heart);
        menuList.add(s);

        s = new MenuItem();
        s.setName("Акции");
        s.setImage(R.drawable.icon_discount);
        menuList.add(s);

        return menuList;
    }

}
