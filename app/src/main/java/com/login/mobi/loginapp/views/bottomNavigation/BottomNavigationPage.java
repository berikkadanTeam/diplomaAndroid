package com.login.mobi.loginapp.views.bottomNavigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.userInformation.UserInformation;
import com.login.mobi.loginapp.network.requests.userInformation.GetUserInformation;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;
import com.login.mobi.loginapp.views.authorization.WelcomePage;
import com.login.mobi.loginapp.views.menu.MenuFragment;
import com.login.mobi.loginapp.views.order.OrderFragment;
import com.login.mobi.loginapp.views.restaurants.RestaurantFragmentTest;

import static com.login.mobi.loginapp.singleton.SingletonSharedPref.USER_EMAIL;
import static com.login.mobi.loginapp.singleton.SingletonSharedPref.USER_SURNAME_NAME;

public class BottomNavigationPage extends AppCompatActivity implements GetUserInformation.GetUserInformationInterface{

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
                case R.id.navigation_delivery:
                    mTextMessage.setText(R.string.title_delivery);
                    return true;
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


        sharedPref = SingletonSharedPref.getInstance(this);
        if(sharedPref.getString(SingletonSharedPref.TOKEN) == null){
            startActivity(new Intent(this, WelcomePage.class));
        }
//      else if(sharedPref.getInt(EXPIRES_IN > System.currentTimeMillis()/1000)){
//      }

        userID = sharedPref.getString(SingletonSharedPref.USER_ID);
        token = sharedPref.getString(SingletonSharedPref.TOKEN);
        //GetUserInformation getUserInformation = new GetUserInformation(this, userID, "Bearer " + token);
        //getUserInformation.getUserInformation();


        loadFragment(new OrderFragment());  // открывать по дефолту первый фрагмент

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


    @Override
    public void getUserInformation(UserInformation response) {
        Log.d("UserInformation", response.toString() + " ");
        sharedPref.put(USER_SURNAME_NAME,response.getLastName() + " " + response.getFirstName());
        sharedPref.put(USER_EMAIL,response.getUserName());
        //sharedPref.put(ROLE, response.getRoles());

    }


}
