package com.sun.assetmananger.skin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sun.assetmananger.skin.SkinManager;
import com.sun.assetmananger.skin.SkinResource;

/**
 * Copyright (C), 2016-2019
 * File: SkinType.java
 * Author: wds_sun
 * Date: 2019-09-23 11:43
 * Description:
 */
public enum SkinType {

    TEXT_COLOR("textColor") {
        @Override
        public void skin(View view, String mResName) {
            SkinResource skinResource = getSkinResource();
            ColorStateList color = skinResource.getColorByName(mResName);
            if (color == null) return;
            TextView textView = (TextView) view;
            textView.setTextColor(color);
        }
    }, BACKGROUND("background") {
        @Override
        public void skin(View view, String mResName) {
            //背景可能是颜色、图片
            SkinResource skinResource = getSkinResource();
            Drawable drawable = skinResource.getDrawableByName(mResName);
            if (drawable != null) {
                view.setBackgroundDrawable(drawable);
                return;
            }
            ;
            ColorStateList color = skinResource.getColorByName(mResName);
            if (color != null) {
                view.setBackgroundColor(color.getDefaultColor());
            }

        }
    }, SRC("src") {
        @Override
        public void skin(View view, String mResName) {
            SkinResource skinResource = getSkinResource();
            Drawable drawable = skinResource.getDrawableByName(mResName);
            if (drawable != null) {
                ImageView imageView = (ImageView) view;
                imageView.setImageDrawable(drawable);
                return;
            }


        }
    };

    private String mResName;

    SkinType(String resName) {
        this.mResName = resName;
    }


    public abstract void skin(View view, String mResName);

    public String getmResName() {
        return mResName;
    }

    public SkinResource getSkinResource() {
        return SkinManager.getInstance().getSkinResource();
    }

}
