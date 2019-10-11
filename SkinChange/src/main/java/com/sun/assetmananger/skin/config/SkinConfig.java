package com.sun.assetmananger.skin.config;

/**
 * Copyright (C), 2016-2019, 未来酒店
 * File: SkinConfig.java
 * Author: wds_sun
 * Date: 2019-09-23 18:58
 * Description: 配置
 */
public class SkinConfig {
    //SP的文件名称
    public static final String SKIN_INFO_NAME = "skininfo";


    //保存皮肤文件的路径的名称
    public static final String SKIN_PATH_NAME = "skinPath";

    /**
     * 不需要改变任何东西
     */
    public static final int SKIN_CHANGE_NOTHING = 0;
    /**
     * 换肤成功
     */
    public static final int SKIN_CHANGE_SUCCESS = 1;
    /**
     * 皮肤不存在
     */
    public static final int SKIN_CHANGE_NOEXSIST = -2;
    /**
     * 不是apk
     */
    public static final int SKIN_CHANGE_ERROR = -1;
}
