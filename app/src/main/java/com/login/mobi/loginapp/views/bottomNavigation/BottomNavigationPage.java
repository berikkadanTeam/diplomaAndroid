package com.login.mobi.loginapp.views.bottomNavigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;
import com.login.mobi.loginapp.views.authorization.WelcomePage;

import static com.login.mobi.loginapp.singleton.SingletonSharedPref.Key.EXPIRES_IN;
import static java.lang.System.currentTimeMillis;

public class BottomNavigationPage extends AppCompatActivity {

    private TextView mTextMessage;
    SingletonSharedPref sharedPref;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_order:
                    mTextMessage.setText(R.string.title_order);
                    return true;
                case R.id.navigation_book:
                    mTextMessage.setText(R.string.title_book);
                    return true;
                case R.id.navigation_delivery:
                    mTextMessage.setText(R.string.title_delivery);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation_page);

        sharedPref = SingletonSharedPref.getInstance(this);
        if(sharedPref.getString(SingletonSharedPref.TOKEN) == null){
            startActivity(new Intent(this, WelcomePage.class));
        }
//        else if(sharedPref.getInt(EXPIRES_IN > System.currentTimeMillis()/1000)){
//
//        }


        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
