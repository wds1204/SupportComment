package com.sun.assetmananger.skin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.sun.assetmananger.skin.callback.ISkinChangeListener;
import com.sun.assetmananger.skin.config.SPUtil;
import com.sun.assetmananger.skin.config.SkinConfig;
import com.sun.assetmananger.skin.attr.SkinView;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C), 2016-2019, 未来酒店
 * File: SkinManager.java
 * Author: wds_sun
 * Date: 2019-09-23 11:18
 * Description: 皮肤管理类
 */
public class SkinManager {

    private static SkinManager mInstance;

    static {
        mInstance = new SkinManager();
    }

    private Map<ISkinChangeListener, List<SkinView>> mSkinViews = new HashMap<>();


    private Context mContext;
    private SkinResource skinResource;

    public static SkinManager getInstance() {
        return mInstance;
    }

    public void init(Context context) {
        this.mContext = context.getApplicationContext();
        String skinPath = SPUtil.getSkinPath(mContext);
        Log.e("TAG", "skinPath init" + skinPath);
        if (TextUtils.isEmpty(skinPath)) {
            return;
        }

        File skinFile = new File(skinPath);
        if (!skinFile.exists()) {
            clearSkinInfo();
            return;
        }

        initSkinResource(skinPath);
    }

    public int loadSkin(final String skinPath) {
        //校验签名
        //初始化资源管理器
        File file = new File(skinPath);
        if (!file.exists()) {
            return SkinConfig.SKIN_CHANGE_NOEXSIST;
        }
        String packageName = mContext.getPackageManager().getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES).packageName;
        if (TextUtils.isEmpty(packageName)) {
            return SkinConfig.SKIN_CHANGE_ERROR;

        }
        String currentSkinPath = SPUtil.getSkinPath(mContext);
        if (skinPath.equals(currentSkinPath)) {
            return SkinConfig.SKIN_CHANGE_NOTHING;
        }

        initSkinResource(skinPath);

        changeSkin(skinPath);
        //保存皮肤状态
        savaSkinStatus(skinPath);

        return SkinConfig.SKIN_CHANGE_SUCCESS;
    }

    /**
     * 保存皮肤路径
     *
     * @param skinPath
     */
    private void savaSkinStatus(String skinPath) {
        SPUtil.saveSkinPath(mContext, skinPath);
    }


    public List<SkinView> getSkinViews(ISkinChangeListener changeListener) {
        return mSkinViews.get(changeListener);
    }


    public SkinResource getSkinResource() {
        return skinResource;
    }

    public boolean needChangeSkin() {
        String skinPath = SPUtil.getSkinPath(mContext);
        return skinPath != null && !TextUtils.isEmpty(skinPath) ? true : false;
    }

    public void changeSkin(SkinView skinView) {
        String currentSkinPath = SPUtil.getSkinPath(mContext);

        if (!TextUtils.isEmpty(currentSkinPath)) {
            skinView.skin();
        }
    }

    /**
     * 初始化皮肤的Resource
     *
     * @param path
     */
    private void initSkinResource(String path) {
        skinResource = new SkinResource(mContext, path);
    }

    /**
     * 恢复默认皮肤
     */
    public void restoreDefault() {
        String currentSkinPath = SPUtil.getSkinPath(mContext);
        if (TextUtils.isEmpty(currentSkinPath)) {
            return;
        }
        clearSkinInfo();
        String path = mContext.getPackageResourcePath();
        initSkinResource(path);
        changeSkin(path);
    }

    /**
     * 清空皮肤信息
     */
    private void clearSkinInfo() {
        SPUtil.cleanSkinPath(mContext);
    }

    /**
     * 切换皮肤
     *
     * @param path 当前皮肤的路径
     */
    private void changeSkin(String path) {
        for (ISkinChangeListener changeListener : mSkinViews.keySet()) {
            List<SkinView> skinViews = mSkinViews.get(changeListener);
            for (SkinView skinView : skinViews) {
                skinView.skin();
            }

            changeListener.changeSkin(skinResource);
        }
    }

    /**
     * 移除回调，怕引起内存泄露
     */
    public void unregister(ISkinChangeListener skinChangeListener) {
        mSkinViews.remove(skinChangeListener);
    }



    /**
     * 注册
     *
     * @param skinViews
     * @param changeListener
     */
    public void registerSkinView(List<SkinView> skinViews, ISkinChangeListener changeListener) {
        mSkinViews.put(changeListener, skinViews);

    }
}
