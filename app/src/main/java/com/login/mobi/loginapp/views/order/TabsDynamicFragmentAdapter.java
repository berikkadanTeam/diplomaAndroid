package com.login.mobi.loginapp.views.order;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.login.mobi.loginapp.network.model.restaurantMenu.RestaurantDishTypes;

import java.util.List;


public class TabsDynamicFragmentAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    List<RestaurantDishTypes> dishTypes;
    //List<RestaurantDishes> dishes;

    TabsDynamicFragmentAdapter(FragmentManager fm, int NumOfTabs, List<RestaurantDishTypes> dishTypes){ //List<RestaurantDishes> dishes) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.dishTypes = dishTypes;
        //this.dishes = dishes;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle b = new Bundle();
        b.putInt("Position", position);

        b.putInt("DishTypeID", dishTypes.get(position).getId());

        //b.putString("Dishes", new Gson().toJson(dishes));

        Fragment frag = TabsDynamicFragment.newInstance();
        frag.setArguments(b);
        return frag;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}