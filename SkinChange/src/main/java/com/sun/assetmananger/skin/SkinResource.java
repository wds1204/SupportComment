package com.sun.assetmananger.skin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;


import java.lang.reflect.Method;

/**
 * Copyright (C), 2016-2019
 * File: SkinResource.java
 * Author: wds_sun
 * Date: 2019-09-23 11:26
 * Description: 皮肤的资源
 */
public class SkinResource {
    private Resources mSkinResource;
    private  String packageName;

    public SkinResource(Context context, String skinPath) {
        try {
            Resources superRes = context.getResources();
            // 创建AssetManager，但是不能直接new所以只能通过反射
            AssetManager assetManager = AssetManager.class.newInstance();
            // 反射获取addAssetPath方法
            Method addAssetPathMethod = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            // 反射调用addAssetPath方法
            addAssetPathMethod.setAccessible(true);
            addAssetPathMethod.invoke(assetManager, skinPath);
            // 创建皮肤的Resources对象
            mSkinResource = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
            //获取包名
            PackageInfo packageInfo = context.getPackageManager().getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES);
            packageName = packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", e.getMessage());
        }
    }

    public Drawable getDrawableByName(String resName) {
        try {
            int resId = mSkinResource.getIdentifier(resName, "drawable", packageName);
            Drawable drawable = mSkinResource.getDrawable(resId);
            return drawable;
        } catch (Exception e) {
            e.printStackTrace();;
            return null;
        }

    }

    public ColorStateList getColorByName(String resName) {
        try {
            int resId = mSkinResource.getIdentifier(resName, "color", packageName);
            ColorStateList color = mSkinResource.getColorStateList(resId);
            return color;
        } catch (Exception e) {
            e.printStackTrace();;
            return null;
        }

    }

    public Resources getmSkinResource() {
        return mSkinResource;
    }
}
