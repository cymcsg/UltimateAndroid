UltimateAndroid快速开发框架教程（一）:部署框架
-----
````为了方便大家更好的使用UltimateAndroid进行Android快速开发，特撰写此教程。不当之处，还请多多指教。```` 

````由于目前使用Eclipse和Intellij Idea（Android Studio）作为开发工具的人都非常多，为了方便大家部署，针对这两种开发环境都进行相应的说明````

``项目地址是https://github.com/cymcsg/UltimateAndroid.git，如有疑问可以直接在github的issue上提问，或者加入QQ群讨论：341970175，加群请注明Android开发``




    注意事项：
    1.JDK,Android Sdk配置完毕，JDK建议是JDK1.7版本，否则需要修改项目语言版本；
    2.本教程的操作系统环境是OS X 10.9，IDE环境eclipse4.4，Intellij Idea 13.1，Android Sdk 为4.4.2和23.0，Android Studio是在Intellij基础上衍生出来的，配置方法相似。
    3.由于本人使用Intellij Idea，所以之后的教程将基本已Intellij Idea环境下为样本。


####一、Eclipse环境下：
1.在命令行下```git clone https://github.com/cymcsg/UltimateAndroid.git```或者直接下载Zip包，并解压。

2.将目录下的``appcompat``,``UltimateAndroid``两个目录导入，如果需要看UI Demo的话导入``DemoOfUI``,如果需要看项目Demo的话导入``Demo``。

3.将各项目下的libs目录的jar包引入项目依赖并设置为Export（在最新版SDK+ADT中是自动配置好的），并添加UltimateAndroid依赖与Appcompat，DemoOfUI依赖于UltimateAndroid，如果这时项目有报错，注意看一下是不是没修改JRE的版本为1.7。

4.右键项目，选择``Java Compiler``下的``Annotation Processing``如图，开启annotation processing并将butterknife 的jar包加入。（所有使用View Injection的项目都需要设置，所以除了UltimateAndroid之外，DemoOfUi和Demo也需要设置）。
![Eclipse1](http://blog.marshalchen.com/images/eclipse1.png)
![Eclipse2](http://blog.marshalchen.com/images/eclipse2.png)
5.之后就可以运行DemoOfUI或者Demo了。


####二、Intellij Idea（Android Studio）环境下
1.在命令行下```git clone https://github.com/cymcsg/UltimateAndroid.git```或者直接下载Zip包，并解压。

2.将目录下的``appcompat``,``UltimateAndroid``两个目录导入，如果需要看UI Demo的话导入``DemoOfUI``,如果需要看项目Demo的话导入``Demo``。

3.将各项目下的libs目录的jar包引入项目依赖并设置为Export，并添加UltimateAndroid依赖与Appcompat，DemoOfUI依赖于UltimateAndroid，如果这时项目有报错，注意看一下是不是没修改JRE的版本为1.7。

4.点击``Preference-Compiler-Annotation Processors``,打开``annotation processing``（所有使用View Injection的项目都需要设置，所以除了UltimateAndroid之外，DemoOfUi和Demo也需要设置）
![Intellij](http://blog.marshalchen.com/images/intellij1.png)

5.之后就可以运行DemoOfUI或者Demo了。