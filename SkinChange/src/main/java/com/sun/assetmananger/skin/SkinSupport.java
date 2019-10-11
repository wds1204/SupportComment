package com.sun.assetmananger.skin;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.sun.assetmananger.skin.attr.SkinAttr;
import com.sun.assetmananger.skin.attr.SkinType;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2016-2019, 未来酒店
 * File: SkinSupport.java
 * Author: wds_sun
 * Date: 2019-09-23 11:27
 * Description: 解析辅助类
 */
public class SkinSupport {

    /**
     * 获取skinAttr的属性
     * @param context
     * @param attrs
     * @return
     */
    public static List<SkinAttr> getSkinAttrs(Context context, AttributeSet attrs) {
        //src、background、texColor
        List<SkinAttr> skinAttrs = new ArrayList<>();
        int count = attrs.getAttributeCount();
        for(int i = 0; i < count; i++) {
            //获取名称 、值
            String attributeName = attrs.getAttributeName(i);
            String attributeValue = attrs.getAttributeValue(i);

            SkinType skinType=getSkinType(attributeName);
            if(skinType!=null) {
                //资源名称 目前vlaue @1112133
                String resName=getResName(context,attributeValue);
                if(TextUtils.isEmpty(resName)) {
                    continue;
                }
                SkinAttr skinAttr = new SkinAttr(resName,skinType);
                skinAttrs.add(skinAttr);

            }

        }
        return skinAttrs;
    }

    /**
     * 获取资源的名称
     * @param context
     * @param attributeValue
     * @return
     */
    private static String getResName(Context context, String attributeValue) {
        if(attributeValue.startsWith("@")) {
            attributeValue = attributeValue.substring(1);

            int resId=Integer.parseInt(attributeValue);


            return context.getResources().getResourceEntryName(resId);
        }
        return null;
    }

    /**
     * 获取名称
     * @param attributeName
     * @return
     */
    private static SkinType getSkinType(String attributeName) {
        SkinType[] values = SkinType.values();
        for (SkinType value : values) {
            if(value.getmResName().equals(attributeName)) {
                return value;
                
            }
        }
        return null;
    }


}
