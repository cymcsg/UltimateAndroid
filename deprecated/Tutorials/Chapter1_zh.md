UltimateAndroid快速开发框架教程（一）:部署框架
==========================
``为了方便大家更好的使用UltimateAndroid进行Android快速开发，特撰写此教程。不当之处，还请多多指教。`` 

``由于目前使用Eclipse和Intellij Idea（Android Studio）作为开发工具的人都非常多，为了方便大家部署，针对这两种开发环境都进行相应的说明``

``项目地址是https://github.com/cymcsg/UltimateAndroid.git，如有疑问可以直接在github的issue上提问，或者加入QQ群讨论：341970175，加群请注明Android开发``

    注意事项：
    1.JDK,Android Sdk配置完毕，JDK建议是JDK1.7版本，否则需要修改项目语言版本至1.7（很多人都询问这个问题，重要）；
    
    2.本教程的操作系统环境是OS X 10.9，IDE环境eclipse4.4，Intellij Idea 13.1，Android Studio是在Intellij基础上衍生出来的，配置方法相似。
    
    3.项目需要最新的Android Sdk，目前是Android Api 21，但是绝大部分内容可以在Android 2.1的系统上运行。

####一、Android Stuidio下使用Gradle项目：
1.在命令行下```git clone https://github.com/cymcsg/UltimateAndroid.git```或者直接下载Zip包，并解压。

2.``Import Non-Android Studio Project``(需要Android Studio版本version>0.9.0)，之后根据需要选择框架或者Demo，可以直接导入UltimateAndroidGradle文件夹，gradle的版本需要>=2.1

3.注意检查一下SDK的版本是否为5.0以上，java compile level是否为1.7以上。

4.点击``Preference-Compiler-Annotation Processors``,打开``annotation processing``（所有使用View Injection的项目都需要设置，所以除了UltimateAndroid之外，DemoOfUi和Demo也需要设置）
![Intellij](http://blog.marshalchen.com/images/intellij1.png)

5.之后就可以运行DemoOfUI或者Demo了。


####二、Eclipse环境下：
1.在命令行下```git clone https://github.com/cymcsg/UltimateAndroid.git```或者直接下载Zip包，并解压。

2.将目录下的``appcompat``,``UltimateAndroid``两个目录导入，如果需要UiModule则导入``UiModule``如果需要看UI Demo的话导入``DemoOfUI``,如果需要看项目Demo的话导入``Demo``。（注意需要导入Android项目existing android code而不是existing project）

3.将各项目下的libs目录的jar包引入项目依赖并设置为Export（在最新版SDK+ADT中是自动配置好的），并添加UltimateAndroid依赖与Appcompat，UltimateAndroidUi依赖于UltimateAndroid，DemoOfUI依赖于UltimateAndroidUi，如果这时项目有报错，注意看一下是不是没修改JRE的版本为1.7。

4.右键项目，选择``Java Compiler``下的``Annotation Processing``如图，开启annotation processing并将butterknife 的jar包加入。（所有使用View Injection的项目都需要设置，所以除了UltimateAndroid之外，DemoOfUi和Demo也需要设置）。
![Eclipse1](http://blog.marshalchen.com/images/eclipse1.png)
![Eclipse2](http://blog.marshalchen.com/images/eclipse2.png)

注意: 如果你找不到 ``Annotation Processing``,请安装eclipse的插件 ``Java Development Tools``

![eclipse3](http://i.stack.imgur.com/ewIn8.png)

5.之后就可以运行DemoOfUI或者Demo了。


####三、Intellij Idea 环境下
1.在命令行下```git clone https://github.com/cymcsg/UltimateAndroid.git```或者直接下载Zip包，并解压。

2.将目录下的``appcompat``,``UltimateAndroid``两个目录导入，如果需要UiModule则导入``UiModule``如果需要看UI Demo的话导入``DemoOfUI``,如果需要看项目Demo的话导入``Demo``。（注意需要导入Android项目existing android code而不是existing project）

3.将各项目下的libs目录的jar包引入项目依赖并设置为Export，并添加UltimateAndroid依赖与Appcompat，UltimateAndroidUi依赖于UltimateAndroid，DemoOfUI依赖于UltimateAndroidUi，如果这时项目有报错，注意看一下是不是没修改JRE的版本为1.7。

4.点击``Preference-Compiler-Annotation Processors``,打开``annotation processing``（所有使用View Injection的项目都需要设置，所以除了UltimateAndroid之外，DemoOfUi和Demo也需要设置）
![Intellij](http://blog.marshalchen.com/images/intellij1.png)

5.之后就可以运行DemoOfUI或者Demo了。