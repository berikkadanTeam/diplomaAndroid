package com.login.mobi.loginapp.views.client.restaurantMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.login.mobi.loginapp.network.model.restaurantMenu.RestaurantDishTypes;

import java.util.List;


public class PreorderTabsDynamicFragmentAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    List<RestaurantDishTypes> dishTypes;
    String restaurantID;

    PreorderTabsDynamicFragmentAdapter(FragmentManager fm, int NumOfTabs, List<RestaurantDishTypes> dishTypes, String restaurantID){
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.dishTypes = dishTypes;
        this.restaurantID = restaurantID;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle b = new Bundle();
        b.putInt("Position", position);

        b.putInt("DishTypeID", dishTypes.get(position).getId());
        b.putString("RestaurantID", restaurantID);

        Fragment frag = PreorderTabsDynamicFragment.newInstance();
        frag.setArguments(b);
        return frag;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}