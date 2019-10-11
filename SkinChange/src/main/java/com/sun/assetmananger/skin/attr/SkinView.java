package com.sun.assetmananger.skin.attr;

import android.view.View;

import java.util.List;

/**
 * Copyright (C), 2016-2019
 * File: SkinView.java
 * Author: wds_sun
 * Date: 2019-09-23 11:40
 * Description:
 */
public class SkinView {

    private View mView;

    private List<SkinAttr> attrs;

    public SkinView(View mView, List<SkinAttr> attrs) {
        this.mView = mView;
        this.attrs = attrs;

    }



    public void skin() {
        for (SkinAttr attr : attrs) {
            attr.skin(mView);

        }

    }
}
