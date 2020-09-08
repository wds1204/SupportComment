package com.sun.supportcomment;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

/**
 * Copyright (C), 2016-2020, 未来酒店
 * File: PersonService.java
 * Author: wds_sun
 * Date: 2020-03-24 10:27
 * Description:
 */
public class PersonService extends Service {
    public PersonService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IPersonImpl();
    }


    class IPersonImpl extends IPersonAidlInterface.Stub{
        private String name;
        private int age;

        @Override
        public void setName(String name) throws RemoteException {
            this.name=name;

        }

        @Override
        public void setAge(int age) throws RemoteException {
            this.age=age;
        }

        @Override
        public String getInfo() throws RemoteException {
            return "name:"+name+"\t\tage:"+age;
        }
    }

}
