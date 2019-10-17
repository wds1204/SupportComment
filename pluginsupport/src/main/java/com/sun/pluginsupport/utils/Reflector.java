package com.sun.pluginsupport.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.sun.pluginsupport.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Copyright (C), 2016-2019, 未来酒店
 * File: Reflector.java
 * Author: wds_sun
 * Date: 2019-10-17 18:33
 * Description:
 */
public class Reflector {
    protected Class<?> mType;

    protected Constructor mConstructor;
    protected Field mField;
    protected Method mMethod;
    protected Object mCaller;


    public static final String TAG = Reflector.class.getName();


    public static class ReflectedException extends Exception {

        public ReflectedException(String message) {
            super(message);
        }

        public ReflectedException(String message, Throwable cause) {
            super(message, cause);
        }

    }


    public static Reflector on(String className) throws ClassNotFoundException {
        Reflector reflector = new Reflector();

        reflector.mType = Class.forName(className);
        return reflector;
    }


    public Reflector field(@NonNull String name) throws NoSuchFieldException {

        mField = findField(name);
        Log.e(TAG, "mField==="+mField);
        mField.setAccessible(true);
        mConstructor = null;
        mMethod = null;
        return this;
    }

    protected Field findField(String name) throws NoSuchFieldException {
        try {
            return mType.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            for (Class<?> cls = mType; cls != null; cls = cls.getSuperclass()) {
                try {
                    return cls.getDeclaredField(name);
                } catch (NoSuchFieldException ex) {
                    // Ignored
                }
            }
            throw e;
        }
    }
    public Field getField(){
        return mField;
    }
    public <R> R get() throws IllegalAccessException {
        return get(mCaller);
    }

    public <R> R get(@NonNull Object caller) throws IllegalAccessException {
        return  mField!=null?(R) mField.get(caller):null;

    }


}
