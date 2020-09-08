package com.sun.lifeplugin;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Copyright (C), 2016-2020, 未来酒店
 * File: LifecycleClassVisitor.java
 * Author: wds_sun
 * Date: 2020-09-08 10:23
 * Description:
 */
public class LifecycleClassVisitor extends ClassVisitor {
    private final ArrayList<String> names;
    private final ArrayList<String> classnames;


    private String className;
    private String superName;
    private static final String[] methodNames = {"attachBaseContext", "onCreate", "onStart", "onResume", "onPause", "onStop", "onDestroy"};
    private static final String[] superNames = {"androidx/appcompat/app/AppCompatActivity", "android/app/Application",};

    public LifecycleClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
        List<String> list = Arrays.asList(methodNames);
        List<String> classlist = Arrays.asList(superNames);
        names = new ArrayList<>(list);
        classnames = new ArrayList<>(classlist);
    }

    /**
     * @param version    JDK版本
     * @param access     类修饰信息 public private
     * @param name       全类名
     * @param signature  泛型信息
     * @param superName  继承的类名
     * @param interfaces 接口信息
     */
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);

        this.className = name;
        this.superName = superName;
    }

    /**
     * @param access     方法修饰 private public
     * @param name       方法名
     * @param descriptor 返回类型 int boolean
     * @param signature  泛型信息
     * @param exceptions 异常信息
     * @return
     */
    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        System.out.println("ClassVisitor visitMethod name-------" + name + ", superName is " + superName);
        MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);

        if (isContains(superName, classnames)) {
            if (isContains(name, names)) {
                return new LifecycleMethodVisitor(mv, className, name);
            }
        }

        return mv;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }


    private boolean isContains(String name, ArrayList<String> list) {
        return list.contains(name);
    }
}
