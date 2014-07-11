CommonLibsForAndroid
==========================

###Using CommonLibs is a fast way  to help you  develop Android apps 
### 快速开发Android应用的框架  
<br>  

  
Introduction
---
简介
---
#####The lib contains many feature like View Injection,ORM,Asynchronous Http and Image,User scenario testing,many UI Modules etc.And there are also many useful feature like TripleDes Utils,WebViewUtils,Md5Utils etc.The lib will be added by more feature in the future.   
	
If I add a new feature,I will write a demo for it at a same time.Some old features do not have demo but I will try to complete them.If you want to try the demo,you can download the Apk directly. 
	 
Up to now,I have only write the demo of most parts of UI modules and View Injection.The demo is something boring,but you can also see many kinds of UI modules.
	
#####I will try to make the demo more interesting and containing other parts such as ORM,Asynchronous Http and Image in next days.


####框架目前主要包含的功能有View Injection,ORM,异步网络请求和图片加载，自动化脚本测试,磁盘LRU等功能.同时提供了类似于TripleDes、Webview快速设置、Md5处理、String处理等常用工具类，还有多种UI控件效果。并且这些功能正在逐步增加中。
		
部分老的功能还没有Demo，但我会不断的完善。目前每加入一个新功能都会增加Demo.Demo的Apk文件可以直接下载使用.  

框架的Demo目前主要只包含了UI Module部分和View Injection，看上去有些无聊，不过你仍可以查看这些有趣的UI效果.

####我将尽力在接下啦的日子中将Demo做的更有趣，同时也包括了类似ORM，异步图片和网路加载等模块。

###QQ交流群：341970175（请注明Android开发）	

	
Demo Manual
-----
Demo 使用方法
--------------
Demo is rely on appcompat and the CommonLibs, you can change CONFIGURATION in your IDES etc.
***Some IDEs require additional configuration in order to enable annotation processing for Butter Knife,
or you can see [IntelliJ IDEA Configuration for Butter Knife ][101] or [Eclipse Configuration for butter Knife][102].***

Demo依赖于appcompat 和 CommonLibs，你可以在IDE或者配置文件里面添加一下依赖。
**部分IDE需要开启annotation的编译（因为Butter Knife）的缘故，如果不清楚如何开启可以看一下[IntelliJ IDEA Configuration for Butter Knife ][101] or [Eclipse Configuration for butter Knife][102].**

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
  
Demo Manual
-----
Demo 使用方法
--------------
Demo is rely on appcompat and the CommonLibs, you can change CONFIGURATION in your IDES etc.
**Some IDEs require additional configuration in order to enable annotation processing for Butter Knife,
or you can see [IntelliJ IDEA Configuration for Butter Knife ][101] or [Eclipse Configuration for butter Knife][102].**

Demo依赖于appcompat 和 CommonLibs，你可以在IDE或者配置文件里面添加一下依赖。
**部分IDE需要开启annotation的编译（因为Butter Knife）的缘故，如果不清楚如何开启可以看一下[IntelliJ IDEA Configuration for Butter Knife ][101] or [Eclipse Configuration for butter Knife][102].**


目前Demo非常简陋，不断完善中。


##Update：

2014.6.12:```Add descriptions of most of the classes.```

          增加了类说明，还有一些方法名相对明确。


2014.6.14：```Add NineOldAndroids to support Android 2.1~2.3```

           增加了支持2.3的android动画，毕竟在某些地方2.3的占有率并不低。该类库如果不需要可移除。

2014.6.16  ```Add enhanceListView for swipe to dismiss;
           Move NineOldAndroids to Main Lib project.```

           增加了滑动删除的Listview；
           将NineOldAndroids从依赖项目改为放入主Lib工程，方便大家配置使用。

2014.6.18  Add [Shimmer-Android][12]

           增加了Shimmer类库

2014.6.19  ```Add Sample Demo!! It is very sample demo but I will add more in the next week.```

          增加了Demo！现在还很简单，但是我再慢慢补充中。

2014.6.20  Add [TileView][13] and Add demo of TileView

2014.6.22  ``Add colors resource``

2014.6.24  Add [SmoothProgressBar](https://github.com/castorflex/SmoothProgressBar)

          增加了Android风格的进度条的Demo。
2014.6.25 Add [StickyGridHeaders](https://github.com/TonicArtos/StickyGridHeaders)
			
		  增加了头部浮现控件和根据Letter过滤控件
		  
2014.6.26 Add [Flip View](https://github.com/emilsjolander/android-FlipView) like FlipBoard  
          Add [PhotoView](https://github.com/chrisbanes/PhotoView)which can help you zoom image by various touch gestures.
          
          增加了类似FlipBoard的效果。
          增加了手势放缩图片的控件。

2014.6.27 Add [Square Progress](https://github.com/mrwonderman/android-square-progressbar)  
          Add [FolderableLayout](https://github.com/alexvasilkov/FoldableLayout)  
          Add[FoldingNavigationDrawer](https://github.com/tibi1712/FoldingNavigationDrawer-Android)


          增加了几种进度条的加载方式
          增加了折叠的图片处理或者侧边菜单的样式。

2014.6.28 Add [Calendar widget](https://github.com/square/android-times-square)  
          Add Demo.apk

          增加了日历选择控件
          增加了DemoApk
          
2014.6.30   
Add [FaceCrop](https://github.com/Todd-Davies/ProgressWheel)  
          Add [KenBurns](https://github.com/flavioarfaria/KenBurnsView)  
          Add [PanningView](https://github.com/flavienlaurent/PanningView)  
          Add [ProgressWheel](https://github.com/lafosca/AndroidFaceCropper)  
          Add [ViewpagerSlidingTabs](https://github.com/astuetz/PagerSlidingTabStrip)  
          
2014.7.2 Add [Rebound](https://github.com/facebook/rebound)
         Add [Titanic](https://github.com/RomainPiel/Titanic)

2014.7.3 Add [PagerSlidingTabStrip](https://github.com/astuetz/PagerSlidingTabStrip)

2014.7.4 Add [ListviewAnimations](https://github.com/nhaarman/ListViewAnimations)


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







      
      
	
	
