package com.sun.bsdiffdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Copyright (C), 2016-2019,
 * File: SignCheck.java
 * Author: wds_sun
 * Date: 2019-10-09 19:19
 * Description: 安装工具包
 */
public class UriparseUtils {
    /**
     * 创建一个图片文件输出路径的uri
     *
     * @param context 上线文
     * @param file    文件路径
     * @return uri地址
     */
    private static Uri getUriForFile(Context context, File file) {
        return FileProvider.getUriForFile(context, getFileProvider(context), file);
    }

    /**
     * 获取fileProvider路径 适配6.0
     *
     * @param context 上下文
     * @return fileProvider路径
     */
    private static String getFileProvider(Context context) {
        return context.getApplicationInfo().packageName + ".fileprovider";
    }


    /**
     * 兼容8.0的安装apk
     * @param context
     * @param filePath
     */
    public static void installApkComp(Context context,String filePath) throws FileNotFoundException
            ,NullPointerException{
        if (filePath == null || "".equals(filePath)) {
            throw new NullPointerException("路径字符是null");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("文件不存在");
        }
        if (Build.VERSION.SDK_INT >= 26) {
            boolean b = context.getPackageManager().canRequestPackageInstalls();
            if (b) {
                installApk(context,filePath);
                //安装应用的逻辑(写自己的就可以)
            } else {
                //设置安装未知应用来源的权限
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        } else {
            installApk(context,filePath);
        }
    }

    /**
     * 安装apk,私有目录
     * @param mContext
     * @param filePath
     */
    public static void installApk(Context mContext,String filePath){
        File apkFile = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.w("TAG", "版本大于 N ，开始使用 fileProvider 进行安装");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = getUriForFile(mContext,apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        mContext.startActivity(intent);
    }

}
