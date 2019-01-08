package com.login.mobi.loginapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.v4.view.PagerAdapter;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.login.mobi.loginapp.Database.RestaurantDAO;
import com.login.mobi.loginapp.Database.UserDatabase;
import com.login.mobi.loginapp.Models.AugustEntities;
import com.login.mobi.loginapp.Models.Restaurant;
import com.login.mobi.loginapp.Models.SeptemberEntities;
import com.login.mobi.loginapp.Models.Table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tabPopularity;
    private TabItem tabName;
    private PagerAdapter pageAdapter;
    private TabItem tabSeptember;
    private TabItem tabAugust;
    private TabItem tabFavorites;
    private Button button;
    public static Set<String> favoriteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        UserDatabase db = ((MyApp) getApplicationContext()).database;

        db.restaurantDAO().nukeTable();
//        db.tableDAO().nukeTable();

        List<Restaurant> restaurantList = new ArrayList<>();

        int idCounter = 0;

        Restaurant fellini = new Restaurant(idCounter,"Fellini", "The Italian restaurant Fellini Grill Pasta Bar opened in 2013 and quickly became favorite among locals thanks to its homemade dishes cooked by the Italian chef and a unique location offering a beautiful panoramic view on the Zailiysky Alatau mountains.",
                "https://logopond.com/logos/02dc607e4ed709fe8a4e83efc689ee8a.png","77/8 Al Farabi Street, Esentai Mall", 12, 0, R.drawable.schema1);
        idCounter++;
        Restaurant turandot = new Restaurant(idCounter,"Turandot", "Turandot in Almaty is one of 12 restaurants of Turandot & Bolognese restaurant chain across Kazakhstan.",
                "http://bestfood.kz/d/204081/d/turandot.png", "282 Zharokov Street; 157A Abay Avenue;", 20, 0, R.drawable.schema2);
        idCounter++;
        Restaurant daredzhani = new Restaurant(idCounter,"Daredzhani", "The restaurant’s menu is inspired by trips to the Caucasus",
                "https://sxodim.com/uploads/almaty/2015/12/daredzhani1.jpg", "40/85 Kazybek Bi Street (corner of Kunayev Street)", 12, 0, R.drawable.schema1);
        idCounter++;

        db.restaurantDAO().insert(fellini);
        db.restaurantDAO().insert(turandot);
        db.restaurantDAO().insert(daredzhani);

        db.tableDAO().insert(new Table(1, fellini.id));
        db.tableDAO().insert(new Table(2, fellini.id));
        db.tableDAO().insert(new Table(3, fellini.id));
        db.tableDAO().insert(new Table(4, fellini.id));
        db.tableDAO().insert(new Table(5, fellini.id));
        db.tableDAO().insert(new Table(6, fellini.id));
        db.tableDAO().insert(new Table(7, fellini.id));
        db.tableDAO().insert(new Table(8, fellini.id));
        db.tableDAO().insert(new Table(9, fellini.id));
        db.tableDAO().insert(new Table(10, fellini.id));
        db.tableDAO().insert(new Table(11, fellini.id));
        db.tableDAO().insert(new Table(12, fellini.id));

        db.tableDAO().insert(new Table(1, turandot.id));
        db.tableDAO().insert(new Table(2, turandot.id));
        db.tableDAO().insert(new Table(3, turandot.id));
        db.tableDAO().insert(new Table(4, turandot.id));
        db.tableDAO().insert(new Table(5, turandot.id));
        db.tableDAO().insert(new Table(6, turandot.id));
        db.tableDAO().insert(new Table(7, turandot.id));
        db.tableDAO().insert(new Table(8, turandot.id));
        db.tableDAO().insert(new Table(9, turandot.id));
        db.tableDAO().insert(new Table(10, turandot.id));
        db.tableDAO().insert(new Table(11, turandot.id));
        db.tableDAO().insert(new Table(12, turandot.id));
        db.tableDAO().insert(new Table(13, turandot.id));
        db.tableDAO().insert(new Table(14, turandot.id));
        db.tableDAO().insert(new Table(15, turandot.id));
        db.tableDAO().insert(new Table(16, turandot.id));
        db.tableDAO().insert(new Table(17, turandot.id));
        db.tableDAO().insert(new Table(18, turandot.id));
        db.tableDAO().insert(new Table(19, turandot.id));
        db.tableDAO().insert(new Table(20, turandot.id));

        db.tableDAO().insert(new Table(1, daredzhani.id));
        db.tableDAO().insert(new Table(2, daredzhani.id));
        db.tableDAO().insert(new Table(3, daredzhani.id));
        db.tableDAO().insert(new Table(4, daredzhani.id));
        db.tableDAO().insert(new Table(5, daredzhani.id));
        db.tableDAO().insert(new Table(6, daredzhani.id));
        db.tableDAO().insert(new Table(7, daredzhani.id));
        db.tableDAO().insert(new Table(8, daredzhani.id));
        db.tableDAO().insert(new Table(9, daredzhani.id));
        db.tableDAO().insert(new Table(10, daredzhani.id));
        db.tableDAO().insert(new Table(11, daredzhani.id));
        db.tableDAO().insert(new Table(12, daredzhani.id));

        db.augustDAO().nukeTable();
        db.septemberDAO().nukeTable();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("BookMe");
        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.tabLayout);
        tabAugust = findViewById(R.id.tabAugust);
        tabSeptember = findViewById(R.id.tabSeptember);
        viewPager = findViewById(R.id.viewPager);

        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    public static Set<String> getFavoriteList() { return favoriteList; }

    public static ArrayList<String> getArrayList(Set<String> set) {
        ArrayList<String> al = new ArrayList<>();
        for (String str : set)
            al.add(str);
        return al;
    }

}
