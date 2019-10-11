package com.sun.assetmananger;

import android.app.Application;

import com.sun.assetmananger.skin.SkinManager;

/**
 * Copyright (C), 2016-2019, 未来酒店
 * File: App.java
 * Author: wds_sun
 * Date: 2019-09-23 16:34
 * Description: application
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this);
    }
}
