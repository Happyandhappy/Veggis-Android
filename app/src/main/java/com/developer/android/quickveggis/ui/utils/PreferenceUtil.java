package com.developer.android.quickveggis.ui.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtil {

    public static void saveBooleanToPreference(Context context, String key, boolean value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBooleanFromPreference(Context context, String key, boolean defaultValue){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        try{
            return prefs.getBoolean(key, defaultValue);
        }catch (Exception e){
            e.printStackTrace();
            return defaultValue;
        }
    }
}
