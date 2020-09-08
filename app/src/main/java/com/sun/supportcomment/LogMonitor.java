package com.sun.supportcomment;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;

/**
 * Copyright (C), 2016-2020, 未来酒店
 * File: LogMonitor.java
 * Author: wds_sun
 * Date: 2020-07-13 15:08
 * Description:
 */
public class LogMonitor {
    private static final  String TAG=LogMonitor.class.getName();
    private static LogMonitor sInstance = new LogMonitor();
    private HandlerThread mLogThread = new HandlerThread("log");
    private Handler mIoHandler;
    private static final long TIME_BLOCK = 52L;
    private LogMonitor() {
        mLogThread.start();
        mIoHandler = new Handler(mLogThread.getLooper());
    }

    private static Runnable mLogRunnable = new Runnable() {
        @Override
        public void run() {
            StringBuilder sb = new StringBuilder();
            StackTraceElement[] stackTrace = Looper.getMainLooper().getThread().getStackTrace();
            for (StackTraceElement s : stackTrace) {
                sb.append(s.toString() + "\n");
            }
            Log.e(TAG, sb.toString());
        }
    };

    public static LogMonitor getInstance() {
        return sInstance;
    }
    public boolean isMonitor() {
        return mIoHandler.hasCallbacks(mLogRunnable);
    }
    public void startMonitor() {
        mIoHandler.postDelayed(mLogRunnable, TIME_BLOCK);
    }
    public void removeMonitor() {
        mIoHandler.removeCallbacks(mLogRunnable);
    }
}
