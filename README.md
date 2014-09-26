UltimateAndroid
==========================

###Version:0.1.0 beta
###当前版本：0.1.0 beta

[![Build Status](https://drone.io/github.com/cymcsg/UltimateAndroid/status.png)](https://drone.io/github.com/cymcsg/UltimateAndroid/latest)
<br>
#####Formerly known as CommonLibsForAndroid
#####原名： CommonLibsForAndroid
<br>
###Using UltimateAndroid is a fast way  to help you  develop Android apps 


### 快速开发Android应用的框架  
<br>  
[English Introduction](#english_introduction)

[中文简介](#chinese_introduction)

[English Quick Setup](https://github.com/cymcsg/UltimateAndroid/blob/master/Manual_English.md)

[中文说明](https://github.com/cymcsg/UltimateAndroid/blob/master/Tutorials/TableofContents.md)

###[中文详细教程](Tutorials/TableofContents.md)

<br>
<h2 ><a name="english_introduction">Introduction</h2>  

---

#####The lib contains many feature like View Injection,ORM,Asynchronous Http and Image,User scenario testing,many UI Modules,Https Utils etc.And there are also many useful feature like WebViewUtils,DaoUtils, TripleDes Utils etc.The lib will be added by more feature in the future.   
	
If I add a new feature,I will write a demo for it at a same time.Some old features do not have demo but I will try to complete them.If you want to try the demo,you can download the Apk directly. 
	 
Up to now,I have only write the demo of most parts of UI modules and View Injection.The demo is something boring,but you can also see many kinds of UI modules.
	
#####I will try to make the demo more interesting and containing other parts such as ORM,Asynchronous Http and Image in next days.Welcome to fork.



	
Demo Manual
-----



Demo is rely on appcompat and the UltimateAndroid, you can change CONFIGURATION in your IDES etc.

##Important:
***Most of  IDEs require additional configuration in order to enable annotation processing for Butter Knife,
or you can see [IntelliJ IDEA Configuration for Butter Knife ][101] or [Eclipse Configuration for butter Knife][102].***


Please set the ides before you run the demo apps.

```UltimateAndroid  depends on appcompat.UltimateAndroidUi  depends on UltimateAndroid.And the DemoOfUi is depends on UltimateAndroidUi.```

```However,if you want to use UltimateAndroid without the UIModules,you can just depend on UltimateAndroid and this will make the app more flexible.```

##UI Modules
* Support animations for Android 2.3
* EnhanceListView for listview which can swipe to dismiss the item
* Shimmer and Titanic to let the textview more amazing
* SmoothProgressBar to let the progress bar like Gmail or Google +
* Flip View for implementing flipping between views as seen in the popular Flipboard application
* PhotoView to help produce an easily usable implementation of a zooming Android ImageView
* PagerSlidingTabStrip and  ViewPagerIndicator to help customing View Pager more easily.
* SwipeBackLayout to help you finish a activity by swipe the screen.  
  ``And there are also many UI modules which I do not mention here.``  
  

 <h2 ><a name="chinese_introduction"></a>简介</h2>   

---
####框架目前主要包含的功能有View Injection,ORM,异步网络请求和图片加载，自动化脚本测试,磁盘LRU等功能.同时提供了类似于TripleDes、Webview快速设置、Md5处理、String处理,Https处理等常用工具类，还有多种UI控件效果。并且这些功能正在逐步增加中。
		
部分老的功能还没有Demo，但我会不断的完善。目前每加入一个新功能都会增加Demo.Demo的Apk文件可以直接下载使用.  

使用DemoOfUI的时候,需要注意```UltimateAndroid 依赖 appcompat.UltimateAndroidUi 依赖 UltimateAndroid.DemoOfUi 依赖 UltimateAndroidUi.```

```如果你不需要使用UiModule的话，可以直接依赖UltimateAndroid，这样体积会更纤细。```


我将尽力在接下啦的日子中将Demo做的更有趣，同时也包括了类似ORM，异步图片和网路加载等模块。





###QQ交流群：341970175（请注明Android开发）

Demo 使用方法
--------------

Demo依赖于appcompat 和 UltimateAndroid，你可以在IDE或者配置文件里面添加一下依赖。

##重要:
**大部分IDE需要开启annotation的编译（因为Butter Knife）的缘故，如果不清楚如何开启可以看一下[IntelliJ IDEA Configuration for Butter Knife ][101] or [Eclipse Configuration for butter Knife][102].**

* 支持Android2.3 上面的动画效果
* Listview的滑动删除
* 动态的textview
* 类似Gmail和Google+的进度条
* 类似FlipBoard的翻页效果
* 放大缩写图片的模块
* 影视效果的图片
* 更方便的定制ViewPager
* 滑动后退

  ``还有许多其他模块没有提到``  

目前Demo非常简陋，不断完善中。


Some  Demo of Ui:


![tutorials2](http://blog.marshalchen.com/images/tutorial2-1.gif)

![tutorials2](http://blog.marshalchen.com/images/tutorial2-2.gif)

![tutorials2](http://blog.marshalchen.com/images/tutorial2-3.gif)

![tutorials2](http://blog.marshalchen.com/images/tutorial2-4.gif)

![tutorials2](http://blog.marshalchen.com/images/tutorial2-5.gif)

![tutorials2](http://blog.marshalchen.com/images/tutorial2-6.gif)

![tutorials2](http://blog.marshalchen.com/images/tutorial2-7.gif)

![tutorials2](http://blog.marshalchen.com/images/tutorial2-8.gif)

![tutorials2](http://blog.marshalchen.com/images/tutorial2-9.gif)



The UltimateAndroid use many opensource program and I am very grateful to the author of them.
The opensource program which I use:

1.JakeWharton's [Butter Knife][1] for View Injection.

2.loopj's [Asynchronous Http Client for Android][2].

3.nostra13's [Universal Image Loader for Android][3]

4.greenrobot's [greenDAO][4]

5.pardom's [ActiveAndroid][5]

6.JakeWharton's [DiskLruCache][6]

7.Issacw0ng's [SwipeBackLayout][7]

8.[google-gson][8]

9.RobotiumTech's [robotium][9]

10.JakeWharton's [NineOldAndroids][10]

11.JakeWharton's [Android-ViewPagerIndicator][11]

If there's anything I forgot to mention,I would be very appreciated for helping me notice it.

And there are also many useful feature like TripleDes Utils,WebViewUtils,Md5Utils etc.

TripleDes Utils,WebViewUtils,Md5Utils


目前主要包含的功能有View Injection,ORM,异步网络请求和图片加载，自动化脚本测试,磁盘LRU等功能，同时提供了类似于TripleDes、Webview快速设置、Md5
处理、String处理等常用工具类，还有类似于滑动返回、带动画的expandable listview等UI效果，以及类似于圆角图片，图像模糊等多种
控件效果。并且这些功能正在逐步增加中。

欢迎各种fork与提意见。

如果大家有需要的功能，欢迎随时提意见。


License
--------

    Copyright 2014 Marshal Chen

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


 [1]: https://github.com/JakeWharton/butterknife
 [2]: https://github.com/loopj/android-async-http
 [3]: https://github.com/nostra13/Android-Universal-Image-Loader
 [4]: https://github.com/greenrobot/greenDAO
 [5]: https://github.com/pardom/ActiveAndroid
 [6]: https://github.com/JakeWharton/DiskLruCache
 [7]: https://github.com/Issacw0ng/SwipeBackLayout
 [8]: https://code.google.com/p/google-gson/
 [9]: https://github.com/RobotiumTech/robotium
 [10]:https://github.com/JakeWharton/NineOldAndroids
 [11]:https://github.com/JakeWharton/Android-ViewPagerIndicator
 [12]:https://github.com/RomainPiel/Shimmer-android
 [101]:http://jakewharton.github.io/butterknife/ide-idea.html
 [102]:http://jakewharton.github.io/butterknife/ide-eclipse.html
 [13]:https://github.com/moagrius/TileView/tree/master
=======







      
      
	
	
