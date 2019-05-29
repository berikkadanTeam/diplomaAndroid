package com.login.mobi.loginapp.views.client.bottomNavigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.widget.TextView;

import com.login.mobi.loginapp.CommonActivity;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;
import com.login.mobi.loginapp.views.client.menu.MenuFragment;
import com.login.mobi.loginapp.views.client.order.OrderFragment;
import com.login.mobi.loginapp.views.client.restaurants.RestaurantFragmentTest;

public class BottomNavigationPage extends CommonActivity {

    private TextView mTextMessage;

    SingletonSharedPref sharedPref;
    private String userID, token;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            Class fragmentClass = null;

            switch (item.getItemId()) {
                case R.id.navigation_order:
                    loadFragment(new OrderFragment());
                    return true;
                case R.id.navigation_book:
                    loadFragment(new RestaurantFragmentTest());
                    return true;
//                case R.id.navigation_delivery:
//                    mTextMessage.setText(R.string.title_delivery);
//                    return true;
                case R.id.navigation_menu:
                    loadFragment(new MenuFragment());
                    return true;
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container_for_fragments, fragment).commit();

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation_page);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Перенесла проверку роли на SignInPage

//        sharedPref = SingletonSharedPref.getInstance(this);
//        switch (sharedPref.getString(SingletonSharedPref.ROLE)){
//            case "Admin":{
//                startActivity(new Intent(this, MainMenuActivity.class));
//                finish();
//                break;
//            }
//        }
//        if(sharedPref.getString(SingletonSharedPref.TOKEN) == null){
//            startActivity(new Intent(this, WelcomePage.class));
//        }
//      else if(sharedPref.getInt(EXPIRES_IN > System.currentTimeMillis()/1000)){
//      }



        loadFragment(new OrderFragment());  // открывать по дефолту первым этот фрагмент

    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_for_fragments, fragment)
                    .commit();
            return true;
        }
        return false;
    }


}
