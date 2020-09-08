package com.sun.lifecycleplugin


import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import groovy.io.FileType
import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.apache.commons.codec.digest.DigestUtils



class LifecycleTransform extends Transform {
    @Override
    String getName() {
        return "LifeCycleTransForm"
    }
    //设置自定义TransForm接收的文件类型
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }
    //设置自定义TransForm检索范围
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.PROJECT_ONLY
    }
    //是否支持增量编译
    @Override
    boolean isIncremental() {
        return false
    }


    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)

        //拿到所有的class文件
        Collection<TransformInput> transformInputs = transformInvocation.inputs
        TransformOutputProvider outputProvider = transformInvocation.outputProvider
        if (outputProvider != null) {
            outputProvider.deleteAll()
        }

        transformInputs.each { TransformInput transformInput ->
            // 遍历directoryInputs(文件夹中的class文件) directoryInputs代表着以源码方式参与项目编译的所有目录结构及其目录下的源码文件
            // 比如我们手写的类以及R.class、BuildConfig.class以及MainActivity.class等
            transformInput.directoryInputs.each { DirectoryInput directoryInput ->
                File dir = directoryInput.file
                if (dir) {
                    dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) { File file ->
                        def name = file.name;
                        if (name.endsWith(".class") && !name.startsWith("R\$")
                                && !"R.class".equals(name) && !"BuildConfig.class".equals(name)) {
                            System.out.println("find class: " + file.name)

                            //对class文件进行读取与解析
                            ClassReader classReader = new ClassReader(file.bytes)
                            //对class文件的写入
                            ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                            //访问class文件相应的内容，解析到某一个结构就会通知到ClassVisitor的相应方法
                            ClassVisitor classVisitor = new com.sun.lifeplugin.LifecycleClassVisitor(classWriter)
                            //依次调用 ClassVisitor接口的各个方法
                            classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
                            //toByteArray方法会将最终修改的字节码以 byte 数组形式返回。
                            byte[] bytes = classWriter.toByteArray()

                            //通过文件流写入方式覆盖掉原先的内容，实现class文件的改写。
//                            FileOutputStream outputStream = new FileOutputStream( file.parentFile.absolutePath + File.separator + fileName)
                            FileOutputStream outputStream = new FileOutputStream(file.path)
                            outputStream.write(bytes)
                            outputStream.close()
                        }
                    }
                }

                //处理完输入文件后把输出传给下一个文件
                def dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes,
                        directoryInput.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)


            }

            //对类型为jar文件的input进行遍历
            transformInput.jarInputs.each {
                    //jar文件一般是第三方依赖库jar文件
                JarInput jarInput ->
                    // 重命名输出文件（同目录copyFile会冲突）
                    def jarName = jarInput.name
                    println("jar: $jarInput.file.absolutePath")
                    def md5Name = DigestUtils.md5Hex(jarInput.file.absolutePath)
                    if (jarName.endsWith('.jar')) {
                        jarName = jarName.substring(0, jarName.length() - 4)
                    }
                    def dest = outputProvider.getContentLocation(jarName + md5Name, jarInput.contentTypes, jarInput.scopes, Format.JAR)

                    println("jar output dest: $dest.absolutePath")
                    FileUtils.copyFile(jarInput.file, dest)
            }

        }
    }
}