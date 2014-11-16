UltimateAndroid rapid development framework tutorial (a): Deployment of the Framework 
==========================
``The target of writing this tutorial is to help you use the Ultimate Android Framework better`` 

``Project address is https://github.com/cymcsg/UltimateAndroid.git,you can ask questions directly on github  issue if you have any question.``

    Note：
    1.You need Jdk1.7 or set 1.7 as your java compile level；
    
    2.My OS environment is OS X 10.9，IDE is eclipse4.4，Intellij Idea 13.1.
    
    3.The framework needs the lastest Android Sdk.It needs Android Api 21 now,but most part of the framework can run on even Android 2.1.


####a、Android Studio Using Gradle
1.```git clone https://github.com/cymcsg/UltimateAndroid.git```or download the Zip directly.

2.``Import Non-Android Studio Project``(need the version of Android Stuido>0.9.0),then you can choose ``UltimateAndroidGradle`` folder.

3.Please pay attention to the version of SDK should be above API 21,the version of gradle should be above 2.1, the java compile level should be above 1.7.

4.Click ``Preference-Compiler-Annotation Processors``,open ``annotation processing``
![Intellij](http://blog.marshalchen.com/images/intellij1.png)

5.Then you can run the demos。

####b、Eclipse：
1.```git clone https://github.com/cymcsg/UltimateAndroid.git```or download the Zip directly.

2.Import ``appcompat``,``UltimateAndroid`` in the folder.If you need UiModules,please import ``UltimateAndroidUi``.If you need Ui Demo,please import ``DemoOfUi``.（import Android-existing android code）

3.Set the project depend on the jars and set the libraries Export（in the latest version of SDK + ADT is automatically configured），then you can  set that  UltimateAndroid depend on Appcompat，UltimateAndroidUI depend on Ultimate,DemoOfUI depend on UltimateAndroidUi，If  there are some  error, please notice that if the project's compile level is up to 1.7

4.Right click the  project, select ``Java Compiler`` and then ``Annotation Processing``, open annotation processing and add butterknife  jar  . (All items will need to use View Injection settings, so in addition to UltimateAndroid, DemoOfUi and Demo also need to be set).
![Eclipse1](http://blog.marshalchen.com/images/eclipse1.png)
![Eclipse2](http://blog.marshalchen.com/images/eclipse2.png)
Notice: If you can't find the ``Annotation Processing``,please install the plugin of ``Java Development Tools``

![eclipse3](http://i.stack.imgur.com/ewIn8.png)

5.Then you can run the demos.




####c、Intellij Idea
1.```git clone https://github.com/cymcsg/UltimateAndroid.git```or download the Zip directly.

2.Import ``appcompat``,``UltimateAndroid`` in the folder.If you need UiModules,please import UltimateAndroidUi.If you need Ui Demo,please import ``DemoOfUi``.

3.Set the project depend on the jars and set the libraries Export，then you can  set that  UltimateAndroid depend on Appcompat，UltimateAndroidUI depend on Ultimate,DemoOfUI depend on UltimateAndroidUi，If  there are some  error, please notice that if the project's compile level is up to 1.7.

4.Click ``Preference-Compiler-Annotation Processors``,open ``annotation processing``
![Intellij](http://blog.marshalchen.com/images/intellij1.png)

5.Then you can run the demos。