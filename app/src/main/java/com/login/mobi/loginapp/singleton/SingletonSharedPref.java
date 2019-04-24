package com.login.mobi.loginapp.singleton;

/**
 * Created by nurilkaa on 5/12/18.
 */

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/*
 * A Singleton for managing your SharedPreferences.
 *
 * You should make sure to change the SETTINGS_NAME to what you want
 * and choose the operating made that suits your needs, the default is
 * MODE_PRIVATE.
 *
 * IMPORTANT: The class is not thread safe. It should work fine in most
 * circumstances since the write and read operations are fast. However
 * if you call edit for bulk updates and do not commit your changes
 * there is a possibility of data loss if a background thread has modified
 * preferences at the same time.
 *
 * Usage:
 *
 * int sampleInt = ${NAME}.getInstance(context).getInt(Key.SAMPLE_INT);
 * ${NAME}.getInstance(context).set(Key.SAMPLE_INT, sampleInt);
 *
 * If ${NAME}.getInstance(Context) has been called once, you can
 * simple use ${NAME}.getInstance() to save some precious line space.
 */
public class SingletonSharedPref {
    // TODO: CHANGE THIS TO SOMETHING MEANINGFUL
    private static final String SETTINGS_NAME = "BLOOM_INFO";
    private static SingletonSharedPref sSharedPrefs;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private boolean mBulkUpdate = false;
    public static final String TOKEN = "Token ";
    public static final String USER_ID = "1234";
    public static final String USER_SURNAME_NAME = "Tolegenova Nurila";
    public static final String USER_SURNAME = "Tolegenova";
    public static final String USER_NAME = "Tolegenova Nurila";
    public static final String USER_EMAIL = "nurilkaa@gmail.com";
    public static final String ROLE = "User";
    public static final String RESTAURANT_ID = "12345";
    /**
    * Class for keeping all the keys used for shared preferences in one place.
    */
    public static class Key {
        /* Recommended naming convention:
        * ints, floats, doubles, longs:
        * SAMPLE_NUM or SAMPLE_COUNT or SAMPLE_INT, SAMPLE_LONG etc.
        *
        * boolean: IS_SAMPLE, HAS_SAMPLE, CONTAINS_SAMPLE
        *
        * String: SAMPLE_KEY, SAMPLE_STR or just SAMPLE
        */
        public static final String SAMPLE_INT = "a_sample_key";
        public static final String ACTION = "action";
        public static final String regType = "regType";
        public static final String TYPE = "type";
        public static final String PHONE = "phone";
        public static final String PASSWORD = "password";
        public static final String EMAIL = "email";
        public static final String NAME= "name";
        public static final String SURNAME= "surname";
        public static final String TOKEN= "Token ";
        public static final String OWNER_ID = "owner_id";
        public static final String NAME_OF_SALON = "name_of_salon";
        public static final String PLACE_DESCRIPTION="place_description";
        public static final String PLACE_ID = "place_id";
        public static final String CODE = "code";
        public static final String LOCATION_X = "LOCATION_X";
        public static final String LOCATION_Y = "LOCATION_Y";
        public static final String CITY = "city";
        public static final String ADDRESS = "address";
        public static final String EXPIRES_IN = "expiresIn";

    }


    private SingletonSharedPref(Context context) {
        mPref = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
    }


    public static SingletonSharedPref getInstance(Context context) {
        if (sSharedPrefs == null) {
            sSharedPrefs = new SingletonSharedPref(context.getApplicationContext());
        }
        return sSharedPrefs;
    }

    public static SingletonSharedPref getInstance() {
        if (sSharedPrefs != null) {
            return sSharedPrefs;
        }

        //Option 1:
        throw new IllegalArgumentException("Should use getInstance(Context) at least once before using this method.");

    }

    public void put(String key, String val) {
        doEdit();
        mEditor.putString(key, val);
        doCommit();
    }

    public void put(String key, int val) {
        doEdit();
        mEditor.putInt(key, val);
        doCommit();
    }

    public void put(String key, boolean val) {
        doEdit();
        mEditor.putBoolean(key, val);
        doCommit();
    }

    public void put(String key, float val) {
        doEdit();
        mEditor.putFloat(key, val);
        doCommit();
    }

    /**
    * Convenience method for storing doubles.
    *
    * There may be instances where the accuracy of a double is desired.
    * SharedPreferences does not handle doubles so they have to
    * cast to and from String.
    *
    * @param key The name of the preference to store.
    * @param val The new value for the preference.
    */
    public void put(String key, double val) {
        doEdit();
        mEditor.putString(key, String.valueOf(val));
        doCommit();
    }

    public void put(String key, long val) {
        doEdit();
        mEditor.putLong(key, val);
        doCommit();
    }

    public String getString(String key, String defaultValue) {
        return mPref.getString(key, defaultValue);
    }

    public String getString(String key) {
        return mPref.getString(key, null);
    }

    public int getInt(String key) {
        return mPref.getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        return mPref.getInt(key, defaultValue);
    }

    public long getLong(String key) {
        return mPref.getLong(key, 0);
    }

    public long getLong(String key, long defaultValue) {
        return mPref.getLong(key, defaultValue);
    }

    public float getFloat(String key) {
        return mPref.getFloat(key, 0);
    }

    public float getFloat(String key, float defaultValue) {
        return mPref.getFloat(key, defaultValue);
    }

    /**
    * Convenience method for retrieving doubles.
    *
    * There may be instances where the accuracy of a double is desired.
    * SharedPreferences does not handle doubles so they have to
    * cast to and from String.
    *
    * @param key The name of the preference to fetch.
    */
    public double getDouble(String key) {
        return getDouble(key, 0);
    }

    /**
    * Convenience method for retrieving doubles.
    *
    * There may be instances where the accuracy of a double is desired.
    * SharedPreferences does not handle doubles so they have to
    * cast to and from String.
    *
    * @param key The name of the preference to fetch.
    */
    public double getDouble(String key, double defaultValue) {
        try {
            return Double.valueOf(mPref.getString(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return mPref.getBoolean(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        return mPref.getBoolean(key, false);
    }

    public Set<String> getStringSet(String key){
        return mPref.getStringSet(key,new HashSet<>());
    }

    /**
     * Remove keys from SharedPreferences.
     *
     * @param keys The name of the key(s) to be removed.
     */
    public void remove(String ... keys) {
        doEdit();
        for (String key : keys) {
            mEditor.remove(key);
        }
        doCommit();
    }

    /**
     * Remove all keys from SharedPreferences.
     */
    public void clear() {
        doEdit();
        mEditor.clear();
        doCommit();
    }

    public void edit() {
        mBulkUpdate = true;
        mEditor = mPref.edit();
    }

    public void commit() {
        mBulkUpdate = false;
        mEditor.commit();
        mEditor = null;
    }

    private void doEdit() {
        if (!mBulkUpdate && mEditor == null) {
            mEditor = mPref.edit();
        }
    }

    private void doCommit() {
        if (!mBulkUpdate && mEditor != null) {
            mEditor.commit();
            mEditor = null;
        }
    }

    public SharedPreferences getmPref() {
        return mPref;
    }

    public void setmPref(SharedPreferences mPref) {
        this.mPref = mPref;
    }

}