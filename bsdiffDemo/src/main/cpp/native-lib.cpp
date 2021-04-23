#include <jni.h>
#include <string>
#include <sys/epoll.h>
#include <sys/eventfd.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>

//#include <cutils/log.h>
#include <utils/Looper.h>
#include <utils/Timers.h>

#include <unistd.h>
#include <fcntl.h>
#include <limits.h>
#include <inttypes.h>
#include <sys/eventfd.h>

#define handle_error(msg) \
   do { perror(msg); exit(EXIT_FAILURE); } while (0)


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





extern "C"
JNIEXPORT void JNICALL
Java_com_sun_bsdiffdemo_MainActivity_nativeMain(JNIEnv *env, jobject thiz, jint argc,
                                                jintArray argv) {
    // TODO: implement nativeMain()


    int efd, j;
    uint64_t u;
    ssize_t s;
    jint *array;
//    array = env->GetIntArrayElements( argv, NULL);//复制数组元素到array内存空间
    array = env->GetIntArrayElements( argv, NULL); //推荐使用

    if (argc < 2) {
        fprintf(stderr, "Usage: %s <num>...\n", argv[0]);
        exit(EXIT_FAILURE);
    }


    efd = eventfd(0, EFD_SEMAPHORE);
    if (efd == -1)
        handle_error("eventfd");

    switch (fork()) {
        case 0:
            for (j = 1; j < argc; j++) {
                printf("Child writing %s to efd\n", argv[j]);
                u = strtoull(reinterpret_cast<const char *>(array[j]), NULL, 0);
                s = write(efd, &u, sizeof(uint64_t));
                if (s != sizeof(uint64_t))
                    handle_error("write");
            }
            printf("Child completed write loop\n");

            exit(EXIT_SUCCESS);

        default:
            sleep(2);

            printf("Parent about to read\n");

            s = read(efd, &u, sizeof(uint64_t));
            if (s != sizeof(uint64_t))
                handle_error("read");
            printf("Parent read %llu (0x%llx) from efd\n",
                   (unsigned long long) u, (unsigned long long) u);

            exit(EXIT_SUCCESS);

        case -1:
            handle_error("fork");
    }
}