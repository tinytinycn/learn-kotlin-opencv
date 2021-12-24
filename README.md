# learn-kotlin-opencv

学习使用kotlin编程语言, 进行opencv计算机视觉开发

## 开发环境

- macOS Catalina 10.15.7
- 2.4 GHz 四核Intel Core i5
- Java 8

> openjdk version "1.8.0_292"
OpenJDK Runtime Environment (AdoptOpenJDK)(build 1.8.0_292-b10)
OpenJDK 64-Bit Server VM (AdoptOpenJDK)(build 25.292-b10, mixed mode)

- Python 2.7.16
- OpenCV 4.5.5
- IntelliJ IDEA 2021.3 (Ultimate Edition)

## 准备工作

1. 安装homebrew
2. homebrew 安装sdkman, 进行Java JDK版本管理 `sdk list java`, 选择JDK 8进行安装
3. macOS终端执行 `xcode-select install` , 安装Xcode或Xcode Command Line Tools 成功，则GCC ready
4. homebrew 安装cmake `brew install cmake`
5. homebrew 安装ant `brew install ant`, 编译java 包需要
6. 接下来，使用opencv源码进行编译并生成Java 8版本的jar包， 注意，采用`brew install opencv`的方式安装是不支持Java版本的，
   采用 `brew install --build-from-source opencv`的方式openCV 会安装至 `/usr/local/Cellar/opencv/x.x.x/`下, 此目录下包含需要的 jar包和
   dylib库文件. 此方案编译后的Java 类文件版本较新, 因此运行Java开发程序的时候推荐使用最新JDK版本运行, 否则会出现以下问题. 若需要打包分发Java开发程序, 那么，最好编译为较旧版本(即，采用源码编译的方式).
   JDK11不能允许Java开发程序, 我们通过sdkman 切换至较新JDK17版本后, 即可成功运行

> /Users/tinytinycn/.sdkman/candidates/java/11.0.2-open/bin/java -Djava.library.path=/usr/local/Cellar/opencv/4.5.4_1/share/java/opencv4 -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=54635:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/tinytinycn/IdeaProjects/learn-java-opencv/out/production/learn-java-opencv:/usr/local/Cellar/opencv/4.5.4_1/share/java/opencv4/opencv-454.jar com.company.Main
> Exception in thread "main" java.lang.UnsupportedClassVersionError: org/opencv/core/Core has been compiled by a more recent version of the Java Runtime (class file version 61.0), this version of the Java Runtime only recognizes class file versions up to 55.0
> at java.base/java.lang.ClassLoader.defineClass1(Native Method)
> at java.base/java.lang.ClassLoader.defineClass(ClassLoader.java:1016)
> at java.base/java.security.SecureClassLoader.defineClass(SecureClassLoader.java:174)
> at java.base/jdk.internal.loader.BuiltinClassLoader.defineClass(BuiltinClassLoader.java:802)
> at java.base/jdk.internal.loader.BuiltinClassLoader.findClassOnClassPathOrNull(BuiltinClassLoader.java:700)
> at java.base/jdk.internal.loader.BuiltinClassLoader.loadClassOrNull(BuiltinClassLoader.java:623)
> at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:581)
> at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:178)
> at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:521)
> at com.company.Main.main(Main.java:16)

7. 安装git
8. 创建源码下载目录, `mkdir ~/your_opencv_project`
9. 进入源码下载目录`cd ~/your_opencv_project` 下载源码, `git clone https://github.com/opencv/opencv.git` (
   按个人实际需要是否下载opencv_contrib的源码)
10. 创建编译目录, `mkdir build`
11. 进入编译目录, `cd build`
12. 创建安装目录, `mkdir /user/local/Cellar/opencv_jdk8`
13. 编译检查 `cmake -DCMAKE_BUILD_TYPE=Release -DBUILD_SHAERED_LIBS=OFF -DCMAKE_INSTALL_PREFIX=/usr/local/Cellar/opencv_jdk8 ../`

> -- Java:
> -- ant:                         /usr/local/bin/ant (ver 1.10.11)
> -- JNI:                         /Library/Java/JavaVirtualMachines/jdk1.8.0_251.jdk/Contents/Home/include /Library/Java/JavaVirtualMachines/jdk1.8.0_251.jdk/Contents/Home/include/darwin /Library/Java/JavaVirtualMachines/jdk1.8.0_251.jdk/Contents/Home/include
> -- Java wrappers:               YES
> -- Java tests:                  YES
> --
> -- Install to:                    /usr/local
> -- -----------------------------------------------------------------
>
> -- Configuring done
>
> -- Generating done

14. 检查相关依赖是否安装， 确保ant,JNI,Java wrappers等环境变量正常
15. 执行 `make -j8` 进行编译, 等待编译结束
16. 编译成功100%后, 执行 `make install`, 进行安装
17. java 8 版本的opencv jar 和依赖库安装至 `/usr/local/Cellar/opencv_jdk8/share/java/opencv4/`
18. 创建Kotlin 项目，在IntelliJ IDEA 中配置项目模块依赖, 让编辑器可以找到动态库所在的位置
19. kotlin代码中 需要加载Java 库文件 
```kotlin
System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
```
20. 在External Libraries 中可以查看opencv-455.jar 的 META-INF/MANIFEST.MF, 可以发现编译java版本为 1.8

> Manifest-Version: 1.0
> Ant-Version: Apache Ant 1.10.12
> Created-By: 1.8.0_292-b10 (AdoptOpenJDK)
> Specification-Title: OpenCV
> Specification-Version: 4.5.5-pre
> Implementation-Title: OpenCV
> Implementation-Version: 4.5.4-347-g9777fbacf6
> Implementation-Date: 星期五 十二月 24 2021 11:40:16 CST

## 参考链接
- [Installation in MacOS](https://docs.opencv.org/4.5.4/d0/db2/tutorial_macos_install.html)
- [01-installing-opencv-for-java](https://opencv-java-tutorials.readthedocs.io/en/latest/01-installing-opencv-for-java.html#install-opencv-3-x-under-macos)
- [编译opencv精简静态库](https://heroinlin.github.io/2018/04/18/opencv/build_opencv_simple_lib/)
- [在C++/Python/Java/ObjectC中使用OpenCV](https://zhuanlan.zhihu.com/p/90035154)
- [macOS和Linux上手动编译OpenCV并作为依赖添加到Python/C++/Java](https://juejin.cn/post/7027845214756667423)