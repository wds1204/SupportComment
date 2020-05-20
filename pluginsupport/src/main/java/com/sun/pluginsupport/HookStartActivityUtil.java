package com.sun.pluginsupport;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.sun.pluginsupport.utils.Reflector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import androidx.annotation.NonNull;

/**
 * Copyright (C)
 * File: HookStartActivityUtil.java
 * Author: wds_sun
 * Date: 2019-10-16 15:15
 * Description:
 */
public class HookStartActivityUtil {
    private static final String TAG = HookStartActivityUtil.class.getName();
    public static final int EXECUTE_TRANSACTION = 159;

    public static final int LAUNCH_ACTIVITY = 100;
    private final Class<?> mProxClass;
    private Context mContext;
    private String mProxyActivity = "com.sun.pluginsupport.StubActivity";

    public HookStartActivityUtil(Context mContext, Class<?> proxClass) {
        this.mContext = mContext;
        this.mProxClass = proxClass;
    }

    public void hookStartActivity() throws Exception {

        Class<?> aClass = Class.forName("android.app.IActivityManager");

        Object aDefault;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            aDefault=Reflector.on("android.app.ActivityManager").field("IActivityManagerSingleton").get();
        }else {
            aDefault=Reflector.on("android.app.ActivityManager").field("gDefault").get();
        }


        Field mInstanceField = Reflector.on("android.util.Singleton").field("mInstance").getField();

        Object iamInstance = Reflector.on("android.util.Singleton").field("mInstance").get(aDefault);



        HookInvocationHandler hookInvocationHandler = new HookInvocationHandler(iamInstance);
        iamInstance = Proxy.newProxyInstance(aClass.getClassLoader()
                , new Class[]{aClass}, hookInvocationHandler);

        mInstanceField.set(aDefault, iamInstance);
    }



    private class HookInvocationHandler implements InvocationHandler {
        private Object mObject;

        public HookInvocationHandler(Object mObject) {
            this.mObject = mObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if (method.getName().equals("startActivity")) {

                Intent realIntent = (Intent) args[2];

                // 代理的Intent
                Intent proxyIntent = new Intent();
                proxyIntent.setComponent(new ComponentName(mContext, mProxyActivity));
                // 把原来的Intent绑在代理Intent上面
                proxyIntent.putExtra("realIntent", realIntent);
                args[2] = proxyIntent;

            }
            return method.invoke(mObject, args);
        }
    }


    public void hookLaunchActivity() throws Exception {



        Object sCurrentActivityThreadObj = Reflector.on("android.app.ActivityThread").field("sCurrentActivityThread").get();
        Handler mH = Reflector.on("android.app.ActivityThread").field("mH").get(sCurrentActivityThreadObj);

        Field mCallbackFiled = Handler.class.getDeclaredField("mCallback");
        mCallbackFiled.setAccessible(true);

        mCallbackFiled.set(mH, new ActivityThreadHandlerCallBack());

    }


    class ActivityThreadHandlerCallBack implements Handler.Callback {

        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Log.e("TAG", "what------->" + msg.what);
            switch (msg.what) {
                case EXECUTE_TRANSACTION:
                case LAUNCH_ACTIVITY:

                    handleLaunchActivity(msg);
                    break;
            }
            return false;
        }
    }

    private void handleLaunchActivity(Message msg) {

        try {
            Object obj = msg.obj;
            Field intentField = obj.getClass().getDeclaredField("intent");
            intentField.setAccessible(true);
            Intent proxyIntent = (Intent) intentField.get(obj);
            //获取代理的意图
            Intent realIntent = proxyIntent.getParcelableExtra("realIntent");
            //还原真实的意图
            if (realIntent != null) {
                intentField.set(obj, realIntent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
