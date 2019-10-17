package com.sun.supportcomment;

/**
 * Copyright (C), 2016-2019
 * File: ProxySubject.java
 * Author: wds_sun
 * Date: 2019-10-11 19:02
 * Description:
 */
public class ProxySubject implements ISubject {
    private ISubject subject;



    public ProxySubject(ISubject subject) {
        this.subject = subject;
    }

    @Override
    public void request() {
        if(subject!=null) {
            subject.request();
        }
    }
}
