package com.sun.bsdiffdemo.diff;

/**
 * Copyright (C), 2016-2019, 未来酒店
 * File: Bean.java
 * Author: wds_sun
 * Date: 2019-12-30 11:56
 * Description:
 */
public class Bean {

    public String name;
    public String subString;
    public String id;
    public String content;

    public Bean(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public Bean deepCopy() {
        return new Bean(id, content);
    }
}
