package com.sun.lifecycleplugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Copyright (C), 2016-2020, 未来酒店
 * File: LifeCyclePlugin.java
 * Author: wds_sun
 * Date: 2020-09-08 10:16
 * Description:
 */
class LifecyclePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {

        System.out.println("register_LifeCyclePlugin")
        def android =project.extensions.getByType(AppExtension);
        LifecycleTransform lifeCycleTransform=new LifecycleTransform();
        android.registerTransform(lifeCycleTransform)


    }
}
