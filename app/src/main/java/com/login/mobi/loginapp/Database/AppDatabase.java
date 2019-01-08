package com.login.mobi.loginapp.Database;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.login.mobi.loginapp.Models.AugustEntities;
import com.login.mobi.loginapp.Models.Book;
import com.login.mobi.loginapp.Models.Restaurant;
import com.login.mobi.loginapp.Models.SeptemberEntities;
import com.login.mobi.loginapp.Models.Table;
import com.login.mobi.loginapp.Models.User;

@Database(entities = {AugustEntities.class, SeptemberEntities.class, Restaurant.class,
        Book.class, Table.class, User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract AugustDAO augustDAO();

    public abstract SeptemberDAO septemberDAO();

    public abstract RestaurantDAO restaurantDAO();

    public abstract BookDAO bookDAO();

    public abstract TableDAO tableDAO();

    public abstract UserDao getUserDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private RestaurantDAO restaurantDAO;

        private PopulateDbAsyncTask(AppDatabase db) {
            restaurantDAO = db.restaurantDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            restaurantDAO.insert(new Restaurant("Fellini", "The Italian restaurant Fellini Grill Pasta Bar opened in 2013 and quickly became favorite among locals thanks to its homemade dishes cooked by the Italian chef and a unique location offering a beautiful panoramic view on the Zailiysky Alatau mountains.",
//                                                "77/8 Al Farabi Street, Esentai Mall", 0, null));
//            restaurantDAO.insert(new Restaurant("Turandot", "Turandot in Almaty is one of 12 restaurants of Turandot & Bolognese restaurant chain across Kazakhstan.",
//                    "282 Zharokov Street; 157A Abay Avenue;", 0, null));
//            restaurantDAO.insert(new Restaurant("Daredzhani", "The restaurant’s menu is inspired by trips to the Caucasus",
//                    "40/85 Kazybek Bi Street (corner of Kunayev Street)", 0, null));
            return null;
        }
    }
}
