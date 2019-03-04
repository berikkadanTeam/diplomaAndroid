package com.login.mobi.loginapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.login.mobi.loginapp.network.model.restaurantMenu.RestaurantDishTypes;
import com.login.mobi.loginapp.network.requests.restaurantMenu.GetRestaurantDishTypes;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;

import java.util.ArrayList;
import java.util.List;

public class TabsDynamically extends AppCompatActivity implements GetRestaurantDishTypes.GetRestaurantDishTypesInterface{

    private ViewPager viewPager;
    private TabLayout tabLayout;
    // Shared Preferences
    SingletonSharedPref sharedPref;
    private String token;
    private List<RestaurantDishTypes> list = new ArrayList<>();
    private int noOfTabs = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_dynamically);

        viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.htab_tabs);
        tabLayout.setupWithViewPager(viewPager);



        sharedPref = SingletonSharedPref.getInstance(this);
        token = sharedPref.getString(SingletonSharedPref.TOKEN);
        GetRestaurantDishTypes getRestaurantDishTypes = new GetRestaurantDishTypes(this, "Bearer " + token);
        getRestaurantDishTypes.getRestaurantDishTypes();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), noOfTabs);
        //jsonResArray get from web service
//        for (int i = 0; i < list.size(); i++) {
//            //adapter.addFrag(new TabsDynamicallyFragment(list.get(i)), list.get(i).getTitle());
//            adapter.addFrag(new TabsDynamicallyFragment(TabsDynamicallyFragment(), "HY"));
//        }

        int count = 10;
        for (int i=0; i<count; i++){

//            TabsDynamicallyFragment fView = new TabsDynamicallyFragment();
//            View view = fView.getView();

//            TextView txtTabItemNumber = (TextView)view.findViewById(R.id.txtTabItemNumber);
//            txtTabItemNumber.setText("TAB " + i);
//            adapter.addFrag(fView,"TAB " + i);
        }

        viewPager.setAdapter(adapter);
    }

static class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List <Fragment> mFragmentList = new ArrayList< >();
    private final List< String > mFragmentTitleList = new ArrayList < >();
    private int noOfItems;

    public ViewPagerAdapter(FragmentManager manager, int noOfItems) {
        super(manager);
        this.noOfItems = noOfItems;
    }

    @Override
    public Fragment getItem(int position) {
        //return mFragmentList.get(position);
        return TabsDynamicallyFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}

    @Override
    public void getRestaurantDishTypes(List<RestaurantDishTypes> response) {
        Log.d("RestaurantDishTypes", response + " ");
        if (response != null) {
            list = response;
        }
        else
            Log.d("RestaurantDishTypes", "Нет доступных категорий блюд");
    }

}