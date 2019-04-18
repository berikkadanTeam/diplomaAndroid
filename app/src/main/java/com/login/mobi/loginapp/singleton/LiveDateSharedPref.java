package com.login.mobi.loginapp.singleton;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by nurilkaa on 7/12/18.
 */

public class LiveDateSharedPref extends ViewModel {
    private MutableLiveData<SingletonSharedPref> mSharedPreferences = new MutableLiveData<>();

    public void setShared(SingletonSharedPref shared) {
        mSharedPreferences.setValue(shared);
    }

    public LiveData<SingletonSharedPref> getShared() {
        return mSharedPreferences;
    }
}
