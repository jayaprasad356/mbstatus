package com.marwadibrothers.mbstatus.base;

import android.app.Application;
import android.content.Context;

import com.facebook.ads.AdSettings;
import com.marwadibrothers.mbstatus.models.customFrame.CustomFrameItem;
import com.marwadibrothers.mbstatus.storage.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App extends Application {

    public static Context context;

    public static int addLogoImagePosition = -1;
    public static String name = "a_default.png";
    public static Map<String, String> paramsMap = new HashMap<>();

    public static final String BANNER_KEY = "admob_banner_key";

    public static SharedPreferencesHelper sharedPreferences;
    public static List<CustomFrameItem> getUserCustomFrameList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        sharedPreferences = new SharedPreferencesHelper(context);
//        AdSettings.setTestMode(true);
//        AdSettings.addTestDevice("328404cebf50ec1fdb05795c0007a8a7");
//        AdSettings.addTestDevice("88888888-aaaa-bbbb-cccc-111111111111");
//        FacebookSdk.setIsDebugEnabled(true);
    }
}
