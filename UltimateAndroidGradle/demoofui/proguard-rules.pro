# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Volumes/MAC3/adt/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keep class com.marshalchen.** { *; }
-keep class com.marshalchen.common.** { *; }
-keep interface com.marshalchen.**{*;}
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

-keep class com.loopj.android.** { *; }
-keep interface com.loopj.android.** { *; }

-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
    public static java.lang.String TABLENAME;
}
-keep class **$Properties
-keep class com.google.gson.**{*;}
-keep class com.nostra13.universalimageloader.**{*;}
-dontwarn com.squareup.okhttp.**
-keep  class freemarker.template.**{*;}
-dontwarn com.squareup.okhttp.**
-dontwarn de.greenrobot.daogenerator.**
-dontwarn in.srain.cube.views.ptr.**
-dontwarn org.xmlpull.v1.**
-keep class org.xmlpull.v1.**{*;}
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }