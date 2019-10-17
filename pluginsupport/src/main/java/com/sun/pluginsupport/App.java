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
        super.onCreate();
        HookStartActivityUtil hook = new HookStartActivityUtil();
        try {
            hook.hookStartActivity();
            hook.hookLaunchActivity();
        } catch (Exception e) {
            System.out.println("Exception---->"+e.toString());
            e.printStackTrace();
        }
    }
}
