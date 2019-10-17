package com.sun.pluginsupport;

import android.app.Application;

/**
 * Copyright (C), 2016-2019, 未来酒店
 * File: App.java
 * Author: wds_sun
 * Date: 2019-10-17 10:36
 * Description:
 */
public class App extends Application {
    @Override
    public void onCreate() {
        try {
            HookStartActivityUtil hook = new HookStartActivityUtil(this,getClass());
            hook.hookStartActivity();
            hook.hookLaunchActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onCreate();

    }
}
