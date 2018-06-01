package com.jinhe.myframe.app;

import android.app.Application;

/**
 * Created by LC on 2018/5/26.
 */
public class MyApplication extends Application{

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
    public static synchronized MyApplication getInstance() {
        if (instance == null)
            instance = new MyApplication();
        return instance;
    }

}
