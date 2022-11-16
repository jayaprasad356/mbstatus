package com.marwadibrothers.mbstatus.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SharedPreferencesHelper {

    public static String APP_KEY;
    private static SharedPreferences sharedPreferences;
    private SharedPreferences pref = null;
    private Context parentActivity;
    public  static String STORAGE_NAME = "Prefrance_Storage";
    public  static String STORAGE_LOGIN = "storage_login";
    public  static String STORAGE_BUSINESS_ACCOUNT= "STORAGE_BUSINESS_ACCOUNT";
    public SharedPreferencesHelper(Context context) {
        parentActivity = context;
        APP_KEY = context.getPackageName().replaceAll("\\.", "_").toLowerCase();
    }

    public static void set(String key, String value) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
            prefsEditor.putString(key, value);
            prefsEditor.commit();
        }
    }

    public void setString(String key, String value) {
        pref = parentActivity.getSharedPreferences(APP_KEY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        pref = parentActivity.getSharedPreferences(APP_KEY,
                Context.MODE_PRIVATE);
        return pref.getString(key, "");

    }

    public void setDouble(String key, double value) {
        pref = parentActivity.getSharedPreferences(APP_KEY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value + "");
        editor.apply();
    }

    public Double getDouble(String key) {
        pref = parentActivity.getSharedPreferences(APP_KEY,
                Context.MODE_PRIVATE);
        if (pref.getString(key, "").length() > 0) {
            return Double.parseDouble(pref.getString(key, ""));
        } else {
            return null;
        }
    }

    public void setBoolean(String key, boolean value) {
        pref = parentActivity.getSharedPreferences(APP_KEY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        pref = parentActivity.getSharedPreferences(APP_KEY,
                Context.MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }


    public void setInt(String key, int value) {
        pref = parentActivity.getSharedPreferences(APP_KEY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key) {
        pref = parentActivity.getSharedPreferences(APP_KEY,
                Context.MODE_PRIVATE);
        return pref.getInt(key, 0);
    }

    public void clearData(Context context) {
        SharedPreferences settings = context.getSharedPreferences(APP_KEY, Context.MODE_PRIVATE);
        settings.edit().clear().apply();
    }

    public void clearLoginData(Context context) {
        SharedPreferences settings = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
        settings.edit().clear().apply();
    }

    public static void saveObjectToSharedPreference(Context context, String serializedObjectKey, Object object) {
        SharedPreferences sharedPreferences = context.getSharedPreferences( STORAGE_NAME, 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        final Gson gson = new Gson();
        String serializedObject = gson.toJson(object);
        sharedPreferencesEditor.putString(serializedObjectKey, serializedObject);
        sharedPreferencesEditor.apply();
    }
    public static <GenericClass> GenericClass getSavedObjectFromPreference(Context context, String preferenceKey, Class<GenericClass> classType) {
        SharedPreferences sharedPreferences = context.getSharedPreferences( STORAGE_NAME, 0);
        if (sharedPreferences.contains(preferenceKey)) {
            final Gson gson = new Gson();
            return gson.fromJson(sharedPreferences.getString(preferenceKey, ""), classType);
        }
        return null;
    }
}
