package com.login.mobi.loginapp.views.client.menu;

import com.login.mobi.loginapp.R;

import java.util.ArrayList;


public class AddMenuItems {

    public static ArrayList<MenuItem> getSpaceships() {

        ArrayList<MenuItem> menuList = new ArrayList<>();

        MenuItem s = new MenuItem();
        s.setName("Мой профиль");
        //s.setImage(R.drawable.icon_profile_rounded);
        s.setImage(R.drawable.icon_user_with_border_48px);
        menuList.add(s);

        s = new MenuItem();
        s.setName("Мои бронирования");
        s.setImage(R.drawable.icon_calendar);
        menuList.add(s);

        s = new MenuItem();
        s.setName("Мои заказы");
        s.setImage(R.drawable.icon_clock);
        menuList.add(s);

//        s = new MenuItem();
//        s.setName("Мои доставки");
//        s.setImage(R.drawable.icon_delivery_blue);
//        menuList.add(s);

        s = new MenuItem();
        s.setName("Пополнить баланс");
        s.setImage(R.drawable.icon_money_heart);
        menuList.add(s);

        s = new MenuItem();
        s.setName("Акции");
        s.setImage(R.drawable.icon_discount);
        menuList.add(s);

        return menuList;
    }

}
