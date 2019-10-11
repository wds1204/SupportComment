# SupportComment
热更新+增量更新+换肤技术

**增量更新**

增量更新的原理非常简单，简单的说就是通过某种算法计算出新版本和旧版本的不同，生成一个差分包。客户端通过下载这个差分包到本地，然后在通过某种算法将差分包合并本地的安装包，

形成一个新版本的安装包。

目前是BSDiff/Patch这种方案实现最多的。

 *bsfif可以通过命令行生成差分包和合并差分包，查看这个[链接](https://blog.csdn.net/mysimplelove/article/details/95482984)*

### 代码项目中实现差分包和合并差分包
1.需要下载整合bsdiff和bzip2

需要下载 [bsdiff-4.3](http://www.daemonology.net/bsdiff/bsdiff-4.3.tar.gz)/[bzip2](https://www.sourceware.org/bzip2/)

解压bsdiff复制bsdiff.c和bspatch.c文件
解压下载的bzip2-1.0.8压缩包,保留里面的.c和.h 
把这些都放入到项目的cpp文件夹中,修改CMakeLists文件,如下:
```
# 查找文件系统中指定模式的路径，如/* 匹配根目录的文件（注意路径）
file(GLOB bzip_source ${CMAKE_SOURCE_DIR}/bzip2-1.0.8/*.c)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
        #模块名
        native-lib

        # Sets the library as a shared library.
        # 动态库/分享可以
        SHARED

        # Provides a relative path to your source file(s).
        native-lib.cpp
        #配置相应的文件引用
        bsdiff.c
        bspatch.c
        #这个相当与别名引用，上面设置了一个别名
        ${bzip_source}
)
```
接下来就开始编写native方法，具体的请查看[bsdiffDemo](https://github.com/wds1204/SupportComment/tree/master/bsdiffDemo/src/main)项目：

**一键快速换肤**


**在Applocation中初始化**
```java
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this);
    }
}
```
**所有页面继承BaseSkinActivity，换肤时传入需要加载的皮肤资源路径，注意读写权限申请：**


```java
public class MainActivity extends BaseSkinActivity {
    public void change(View view) {
         String skinPath = Environment.getExternalStorageDirectory() + File.separator + "plugin.skin";
         SkinManager.getInstance().loadSkin(skinPath);

     }
     
    @Override
    public void changeSkin(SkinResource resource) {
        super.changeSkin(resource);
    }
 }
```
如有特殊自定义View需要换肤，可在提高的changeSkin回调中自己实现换肤功能。



