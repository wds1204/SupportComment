package com.sun.supportcomment;

import android.os.Build;
import android.view.Choreographer;

import androidx.annotation.RequiresApi;

import java.util.concurrent.TimeUnit;

/**
 * Copyright (C), 2016-2020, 未来酒店
 * File: BlockDetectByChoreographer.java
 * Author: wds_sun
 * Date: 2020-07-13 16:18
 * Description:
 */
public class BlockDetectByChoreographer {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void start() {
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            long lastFrameTimeNanos = 0;
            long currentFrameTimeNanos = 0;

            @Override
            public void doFrame(long frameTimeNanos) {

                System.out.println("doFrame=====Thread=="+Thread.currentThread().getName());
                if(lastFrameTimeNanos == 0){
                    lastFrameTimeNanos = frameTimeNanos;
                }
                currentFrameTimeNanos = frameTimeNanos;
                long diffMs = TimeUnit.MILLISECONDS.convert(currentFrameTimeNanos-lastFrameTimeNanos, TimeUnit.NANOSECONDS);
                if (diffMs > 16.6f) {
                    long droppedCount = (long) (diffMs / 16.6);
                }
                if (LogMonitor.getInstance().isMonitor()) {
                    LogMonitor.getInstance().removeMonitor();
                }
                LogMonitor.getInstance().startMonitor();
                Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }
}
