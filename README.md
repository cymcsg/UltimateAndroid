CommonLibs of Android Development
===================================

A fast way to develop Android apps.

The lib contains many feature like View Injection,ORM,Asynchronous Http and Image,User scenario testing,etc.And it will be added by more feature in the future.


About Demos and docs: I will add some demos and docs in next two weeks with some other
fuctional feature.Thanks very much for star the project.

在未来的两周将进行Demo和文档的更新，同时还有一系列功能上的改进，感谢大家支持。

Demo Manual
Demo 使用方法
--------------
Demo is rely on appcompat and the CommonLibs, you can change CONFIGURATION in your IDES etc.
**Some IDEs require additional configuration in order to enable annotation processing for Butter Knife,
or you can see [IntelliJ IDEA Configuration for Butter Knife ][101] or [Eclipse Configuration for butter Knife][102].**

Demo依赖于appcompat 和 CommonLibs，你可以在IDE或者配置文件里面添加一下依赖。
**部分IDE需要开启annotation的编译（因为Butter Knife）的缘故，如果不清楚如何开启可以看一下[IntelliJ IDEA Configuration for Butter Knife ][101] or [Eclipse Configuration for butter Knife][102].**

目前Demo非常简陋，不断完善中。


Update：

2014.6.12:```Add descriptions of most of the classes.```

          增加了类说明，还有一些方法名相对明确。


2014.6.14：```Add NineOldAndroids to support Android 2.1~2.3```

           增加了支持2.3的android动画，毕竟在某些地方2.3的占有率并不低。该类库如果不需要可移除。

2014.6.16  ```Add enhanceListView for swipe to dismiss;
           Move NineOldAndroids to Main Lib project.```

           增加了滑动删除的Listview；
           将NineOldAndroids从依赖项目改为放入主Lib工程，方便大家配置使用。

2014.6.18  ```Add [Shimmer-Android][12]```

           增加了Shimmer类库

2014.6.19  ```Add Sample Demo!! It is very sample demo but I will add more in the next week.```

          增加了Demo！现在还很简单，但是我再慢慢补充中。

2014.6.20  ```Add [TileView][13] and Add demo of TileView```

2014.6.22  ``Add colors resource``

2014.6.24  ```Add [SmoothProgressBar](https://github.com/castorflex/SmoothProgressBar)```

          增加了Android风格的进度条的Demo。




The CommonLibs use many opensource program and I am very grateful to the author of them.
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




