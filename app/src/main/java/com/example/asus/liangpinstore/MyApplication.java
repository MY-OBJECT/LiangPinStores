package com.example.asus.liangpinstore;

import android.app.Application;
import android.content.Context;

/**
 * Created by asus on 2018/3/29.
 */

public class MyApplication extends Application {

    public static Context applicationContext;
    private static MyApplication instance;
    // login user name
    public final String PREF_USERNAME = "username";

    /**
     * nickname for current user, the nickname instead of ID be shown when user receive notification from APNs
     */
    public static String currentUserNick = "";

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        instance = this;
        // bugly end

        //init demo helper
        DemoHelper.getInstance().init(applicationContext);
    }

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
