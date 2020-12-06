package com.politechnika.shootingrange.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.politechnika.shootingrange.models.User;

public class SharedPrefManager{

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static Gson gson;
    private static final String SHARED_PREF_NAME = "mysharedpreferences";
    private static final String KEY_USER = "user";

    private SharedPrefManager(Context context) {
        mCtx = context;
        gson = new Gson();
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public User getUser(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String userJson = sharedPreferences.getString(KEY_USER, null);
        User user = gson.fromJson(userJson, User.class);
        return user;
    }

    public void setUser(User user){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String userJson = gson.toJson(user);
        editor.putString(KEY_USER, userJson);
        editor.apply();
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true; 
    }
}
