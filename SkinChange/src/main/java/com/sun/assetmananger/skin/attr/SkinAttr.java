package com.sun.assetmananger.skin.attr;

import android.view.View;

/**
 * Copyright (C), 2016-2019, 未来酒店
 * File: SkinAttr.java
 * Author: wds_sun
 * Date: 2019-09-23 11:41
 * Description:
 */
public class SkinAttr {
    private String mResName;
    private SkinType mType;

    public SkinAttr(String resName, SkinType skinType) {
        this.mResName=resName;
        this.mType=skinType;
    }

    public void skin(View view) {
        mType.skin(view,mResName);

    }
}
