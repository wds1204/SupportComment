#include <jni.h>
#include <string>


extern "C" {
extern int patch_main(int argc, char *argv[]);

}
extern "C" {
extern int diff_main(int argc, char *argv[]);

}
//extern int diff_main(int argc, char *argv[]);
extern "C" JNIEXPORT jstring JNICALL
Java_com_sun_bsdiffdemo_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT void JNICALL
Java_com_sun_bsdiffdemo_MainActivity_diff(JNIEnv *env, jobject thiz, jstring old_apk,
                                          jstring new_apk, jstring diff) {


    //将java字符串转换成char指针
    const char *oldApk = env->GetStringUTFChars(old_apk, 0);
    const char *newApk = env->GetStringUTFChars(new_apk, 0);
    const char *output = env->GetStringUTFChars(diff, 0);

    //bspatch ,oldfile ,newfile ,patchfile
    char *argv[] = {"bspatch", const_cast<char *>(oldApk), const_cast<char *>(newApk),
                    const_cast<char *>(output)};
    diff_main(4, argv);
    // 释放相应的指针gc
    env->ReleaseStringUTFChars(old_apk, oldApk);
    env->ReleaseStringUTFChars(new_apk, newApk);
    env->ReleaseStringUTFChars(diff, output);

}

extern "C"
JNIEXPORT void JNICALL
Java_com_sun_bsdiffdemo_MainActivity_patch(JNIEnv *env, jobject thiz, jstring old_apk, jstring diff,
                                           jstring new_path) {


    //将java字符串转换成char指针
    const char *oldApk = env->GetStringUTFChars(old_apk, 0);
    const char *patch = env->GetStringUTFChars(diff, 0);
    const char *output = env->GetStringUTFChars(new_path, 0);

    //bspatch ,oldfile ,newfile ,patchfile
    char *argv[] = {"bspatch", const_cast<char *>(oldApk), const_cast<char *>(output),
                    const_cast<char *>(patch)};
    patch_main(4, argv);

    // 释放相应的指针gc
    env->ReleaseStringUTFChars(old_apk, oldApk);
    env->ReleaseStringUTFChars(diff, patch);
    env->ReleaseStringUTFChars(new_path, output);
}