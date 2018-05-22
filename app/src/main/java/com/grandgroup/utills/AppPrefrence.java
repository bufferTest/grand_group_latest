package com.grandgroup.utills;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPrefrence {
    private SharedPreferences sharedpref;
    private static AppPrefrence appPrefrence;

    public AppPrefrence(Context context) {
        sharedpref = context.getSharedPreferences("grandgroup", Context.MODE_PRIVATE);
    }

    public static AppPrefrence init(Context context) {
        if (appPrefrence == null) {
            appPrefrence = new AppPrefrence(context);
        }
        return appPrefrence;
    }

    public void putInteger(String key, int value) {
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.putInt(key, value);
        editor.apply();
    }


    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return sharedpref.getString(key, "");
    }

    public int getInteger(String key) {
        return sharedpref.getInt(key, -1);
    }

    public boolean getBoolean(String key) {
        return sharedpref.getBoolean(key, false);
    }

    public void clearSharedPrefrences() {
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.clear().apply();
    }
}
