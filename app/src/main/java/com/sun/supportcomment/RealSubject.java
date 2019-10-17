package com.sun.supportcomment;

import android.util.Log;

/**
 * Copyright (C), 2016-2019
 * File: RealSubject.java
 * Author: wds_sun
 * Date: 2019-10-11 19:02
 * Description:
 */
public class RealSubject implements ISubject {
    @Override
    public void request() {
        System.out.println("request");
    }
}
