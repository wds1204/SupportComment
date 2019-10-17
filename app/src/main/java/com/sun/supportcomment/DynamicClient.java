package com.sun.supportcomment;

import android.app.ActivityManager;
import android.os.Build;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Copyright (C), 2016-2019,
 * File: DynamicClient.java
 * Author: wds_sun
 * Date: 2019-10-11 19:20
 * Description:
 */
public class DynamicClient {

    public static void main(String[] args) {
        RealSubject realSubject = new RealSubject();
        Class<?>[] interfaces = realSubject.getClass().getInterfaces();
        ClassLoader classLoader = realSubject.getClass().getClassLoader();
//        Singleton<IActivityManager> defaultSingleton;
//
//        defaultSingleton = (Singleton<IActivityManager>) ReflectUtil.getField(ActivityManager.class, null, "IActivityManagerSingleton");

        SubjectInvocationHandler subjectInvocationHandler = new SubjectInvocationHandler(realSubject);
        ISubject iSubject = (ISubject) Proxy.newProxyInstance(ISubject.class.getClassLoader(),//classLoader
                new Class<?>[]{ISubject.class},//接口
                subjectInvocationHandler
        );
        System.out.println("iSubject==="+iSubject.getClass().getName());
        iSubject.request();

    }

    private static class SubjectInvocationHandler implements InvocationHandler {

        private final RealSubject realSubject;

        public SubjectInvocationHandler(RealSubject realSubject) {
            this.realSubject=realSubject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("invoke");
            return method.invoke(realSubject,args);
        }
    }
}
