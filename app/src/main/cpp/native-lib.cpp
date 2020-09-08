//#include <jni.h>
//#include <string>
//#include <stdlib.h>
//#include <string.h>
//#include <stdio.h>
//#include <assert.h>
//
//
////extern "C" JNIEXPORT jstring JNICALL
////Java_com_sun_supportcomment_MainActivity_stringFromJNI(
////        JNIEnv *env,
////        jobject /* this */) {
////    std::string hello = "Hello from C++";
////    return env->NewStringUTF(hello.c_str());
////}
//
//jstring native_hello(JNIEnv *env, jobject thiz)
//{
//    return env->NewStringUTF("动态注册JNI");
//}
//
///**
//* 方法对应表
//*/
//static JNINativeMethod gMethods[] = {
//        {"stringFromJNI", "()Ljava/lang/String;", (void*)native_hello},
//};
//
///*
//* 为某一个类注册本地方法
//*/
//static int registerNativeMethods(JNIEnv* env
//        , const char* className
//        , JNINativeMethod* gMethods, int numMethods) {
//    jclass clazz;
//    clazz = (env)->FindClass(className);
//    if (clazz == NULL) {
//        return 0;
//    }
//    if ((env)->RegisterNatives( clazz, gMethods, numMethods) < 0) {
//        return 0;
//    }
//
//    return 1;
//}
//
//static int registerNatives(JNIEnv *env) {
//    //指定要注册的类
//    //com.sun.supportcomment.HelloJni
//    const char* kClassName = "com/sun/supportcomment/MainAvtivity";
//    return registerNativeMethods(env, kClassName, gMethods,
//                                 sizeof(gMethods) / sizeof(gMethods[0]));
//}
//
//JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void* reserved){
//    JNIEnv* env =NULL;
//    jint result =-1;
//    printf("result===== %s\n",result);
//    if ((vm)->GetEnv( (void**) &env, JNI_VERSION_1_4) != JNI_OK) {
//        return -1;
//    }
//    assert(env!=NULL);
//
//    if (registerNatives(env)!=1) {//注册
//        printf("result===== %s\n",result);
//        return -1;
//    }
//
//    //成功
//    result = JNI_VERSION_1_4;
//    printf("result==== %s\n",result);
//    return result;
//}
