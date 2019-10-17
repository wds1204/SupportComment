package com.sun.pluginsupport;

import android.content.ComponentName;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Copyright (C)
 * File: HookStartActivityUtil.java
 * Author: wds_sun
 * Date: 2019-10-16 15:15
 * Description:
 */
public class HookStartActivityUtil {
    private static final String TAG = HookStartActivityUtil.class.getName();

    public void hookStartActivity() throws Exception {
        Class<?> aClass = Class.forName("android.app.IActivityManager");

        Class<?> aClass1 = Class.forName("android.app.ActivityManager");

        //获取ActivityManager里面 IActivityManagerSingleton属性
        Field declaredField = aClass1.getDeclaredField("IActivityManagerSingleton");

        declaredField.setAccessible(true);
        Object aDefault = declaredField.get(null);

        Class<?> singletonClass = Class.forName("android.util.Singleton");
        Field mInstance = singletonClass.getDeclaredField("mInstance");
        mInstance.setAccessible(true);
        Object iamInstance = mInstance.get(aDefault);


        HookInvocationHandler hookInvocationHandler = new HookInvocationHandler(iamInstance);
        iamInstance = Proxy.newProxyInstance(aClass.getClassLoader()
                , new Class[]{aClass}, hookInvocationHandler);

        mInstance.set(aDefault, iamInstance);
    }

    private class HookInvocationHandler implements InvocationHandler {
        private Object mObject;

        public HookInvocationHandler(Object mObject) {
            this.mObject = mObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.e("TAG", "Hook Name----------->" + method.getName());

            if (method.getName().equals("startActivity")) {
                Intent intents = (Intent) args[2];
                ComponentName component = intents.getComponent();
                String className = component.getClassName();
                String shortClassName = component.getShortClassName();
                String packageName = component.getPackageName();

                Log.e(TAG, "-------->\n"+"shortClassName=>"+shortClassName+" \npackageName=>"+packageName+" \nclassName=>"+className);
            }
            return method.invoke(mObject, args);
        }
    }

    public static final int EXECUTE_TRANSACTION = 159;

    public void hookLaunchActivity() throws Exception {

        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");

        Field sCurrentActivityThread = activityThreadClass.getDeclaredField("sCurrentActivityThread");

        sCurrentActivityThread.setAccessible(true);
        Object sCurrentActivityThreadObj = sCurrentActivityThread.get(null);
        //获取mH  handle
        Field mHFiled = activityThreadClass.getDeclaredField("mH");
        mHFiled.setAccessible(true);
        Handler mH = (Handler) mHFiled.get(sCurrentActivityThreadObj);
//
        Field mCallbackFiled = Handler.class.getDeclaredField("mCallback");

        mCallbackFiled.setAccessible(true);
//
        mCallbackFiled.set(mH,new ActivityThreadHandlerCallBack());


    }


    class ActivityThreadHandlerCallBack implements Handler.Callback{

        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case EXECUTE_TRANSACTION:

                    Log.e("TAG", "what------->"+msg.what);

                    break;
            }
            return false;
        }
    }
}
