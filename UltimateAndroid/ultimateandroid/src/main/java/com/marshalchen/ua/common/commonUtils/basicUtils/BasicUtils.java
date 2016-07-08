package com.marshalchen.ua.common.commonUtils.basicUtils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.marshalchen.ua.common.commonUtils.logUtils.Logs;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Some Useful Utils
 * <p>{@link #judgeNotNull(String, String...)}</p>
 * <p>{@link #judgeNotNull(Object)}</p>
 * <p>{@link #getVersionName(android.content.Context)}</p>
 * <p>{@link #getVersionCode(android.content.Context)}</p>
 * <p>{@link #iterateHashMap(java.util.HashMap)}</p>
 * <p>{@link #iterateListHashMap(java.util.List)}</p>
 * <p>{@link #sendIntent(android.content.Context, Class)}</p>
 * <p>{@link #sendIntent(android.content.Context, Class, String, android.os.Parcelable)}</p>
 */
public class BasicUtils {
    /**
     * Print all items of HashMap
     *
     * @param hashMap
     */
    public static void iterateHashMap(HashMap<String, Object> hashMap) {
        for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            Logs.d("key: " + key + "   " + "value:  " + value.toString());
        }
    }

    /**
     * Print all items of HashMap,avoid ConcurrentModificationExceptions
     *
     * @param hashMap
     */
    public static void iterateHashMapConcurrent(HashMap<String, Object> hashMap) {
        Iterator<Map.Entry<String, Object>> iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            Logs.d("key: " + entry.getKey() + "   " + "value:  " + entry.getValue().toString());
            // iterator.remove(); // avoids ConcurrentModificationException
        }
    }


    public static void iterateListHashMap(List list) {
        //support concurrent
        try {
            for (Iterator it = list.iterator(); it.hasNext(); ) {
                iterateHashMapConcurrent((HashMap) it.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logs.e(e.getMessage());
        }


    }


    /**
     * get the version name which defines in AndroidManifest.xml
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String version = "";
        try {
            // get packagemanager
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()--your current package name，0 means get package info
            PackageInfo packInfo = packageManager.getPackageInfo(context
                    .getPackageName(), 0);
            version = packInfo.versionName;

        } catch (Exception e) {
            e.printStackTrace();
            Logs.e(e.getMessage());

        } finally {
            return version;
        }

    }

    /**
     * get the version code which defines in AndroidManifest.xml
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int version = 0;
        try {
            // get packagemanager
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()--your current package name，0 means get package info
            PackageInfo packInfo = packageManager.getPackageInfo(context
                    .getPackageName(), 0);
            version = packInfo.versionCode;

        } catch (Exception e) {
            e.printStackTrace();
            Logs.e(e.getMessage());

        } finally {
            return version;
        }

    }


    /**
     * Format the int number as your want
     *
     * @param num
     * @param formatAs
     * @return
     * @throws Exception
     */
    public static String formatNumber(int num, String formatAs) throws Exception {
        DecimalFormat df = new DecimalFormat(formatAs);
        String str2 = df.format(num);
        return str2;
    }

    /**
     * Launch a new activity. You will not receive any information about when the activity exits.
     *
     * @param context
     * @param classes
     */
    public static void sendIntent(Context context, Class classes) {
        Intent intent = new Intent();
        intent.setClass(context, classes);
        context.startActivity(intent);
    }


    /**
     * Launch a new activity with two pairs of extended String type data.
     *
     * @param context
     * @param classes
     * @param key
     * @param value
     * @param anotherKey
     * @param anotherValue
     */
    public static void sendIntent(Context context, Class classes, String key, String value, String anotherKey, String anotherValue) {
        Intent intent = new Intent();
        intent.setClass(context, classes);
        intent.putExtra(key, value);
        intent.putExtra(anotherKey, anotherValue);
        context.startActivity(intent);
    }


    /**
     * Launch a new activity with one pair of extended String type data.
     *
     * @param context
     * @param classes
     * @param key
     * @param value
     */
    public static void sendIntent(Context context, Class classes, String key, String value) {
        Intent intent = new Intent();
        intent.setClass(context, classes);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }

    /**
     * Launch a new activity with one Parcelable data.
     *
     * @param context
     * @param classes
     * @param key
     * @param value
     */
    public static void sendIntent(Context context, Class classes, String key, Parcelable value) {
        Intent intent = new Intent();
        intent.setClass(context, classes);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }

    /**
     * Launch a new activity with ArrayList data.
     *
     * @param context
     * @param classes
     * @param key
     * @param value
     */
    public static void sendIntent(Context context, Class classes, String key, ArrayList<? extends Parcelable> value) {
        Intent intent = new Intent();
        intent.setClass(context, classes);
        intent.putParcelableArrayListExtra(key, value);
        context.startActivity(intent);
    }

    /**
     * Launch a new activity with one ArrayList data and one pair of String data.
     *
     * @param context
     * @param classes
     * @param key
     * @param value
     * @param anotherKey
     * @param anotherValue
     */
    public static void sendIntent(Context context, Class classes, String key, ArrayList<? extends Parcelable> value, String anotherKey, String anotherValue) {
        Intent intent = new Intent();
        intent.setClass(context, classes);
        intent.putParcelableArrayListExtra(key, value);
        intent.putExtra(anotherKey, anotherValue);
        context.startActivity(intent);
    }


    /**
     * @param string
     * @return
     * @see #judgeNotNull(String, String...)
     */
    public static boolean judgeNotNull(String string) {
        return string != null && !string.equals("") && !string.equals("null") ? true : false;

    }

    /**
     * Judge if a variable of String or String[] is null or ""
     *
     * @param string
     * @param strings
     * @return
     */
    public static boolean judgeNotNull(String string, String... strings) {
        boolean flag = true;
        if (!(string != null && string.trim().length() > 0 && !string.equals("null") && !string.trim().equals("")))
            return false;
        for (String s : strings) {
            if (s == null || string.trim().length() == 0 || s.equals("null")) {
                flag = false;
                break;
            }
        }

        return flag;
    }

    /**
     * Judge if a variable of byte[] is not null and the length of it is above 1
     *
     * @param bytes
     * @return
     */
    public static boolean judgeNotNull(byte[] bytes) {
        return bytes != null && bytes.length >= 1;
    }

    /**
     * Judge if the size of a  map is above 1
     *
     * @param map
     * @return
     */
    public static boolean judgeNotNull(Map map) {
        return map != null && map.size() > 0 ? true : false;
    }

    /**
     * Judge if the size of a  list is above 1
     *
     * @param list
     * @return
     */
    public static boolean judgeNotNull(List list) {
        return list != null && list.size() > 0 ? true : false;

    }

    /**
     * @param list
     * @param lists
     * @return
     * @see #judgeNotNull(java.util.List)
     */
    public static boolean judgeNotNull(List list, List... lists) {
        boolean flag = true;
        if (list == null || list.size() == 0) return false;
        if (judgeNotNull(lists))
            for (List l : lists) {
                if (l == null || l.size() == 0) {
                    flag = false;
                    return false;
                }
            }
        return flag;
    }

    /**
     * Judge if the Object is null
     *
     * @param object
     * @return
     */
    public static boolean judgeNotNull(Object object) {
        if (object == null) return false;
        return true;
    }

    /**
     * Judge if the Objects are all defined.
     *
     * @param object
     * @param objects
     * @return
     */
    public static boolean judgeNotNull(Object object, Object... objects) {
        boolean flag = true;
        if (object == null) return false;
        for (Object o : objects) {
            if (o == null) {
                flag = false;
                return flag;
            }
        }

        return flag;
    }

    /**
     * Check if the service is running
     *
     * @param context
     * @param cls
     * @return
     */
    public static boolean isServiceRunning(Context context, Class<?> cls) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = activityManager
                .getRunningServices(Integer.MAX_VALUE);

        for (ActivityManager.RunningServiceInfo serviceInfo : services) {
            ComponentName componentName = serviceInfo.service;
            String serviceName = componentName.getClassName();
            if (serviceName.equals(cls.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Compare two object
     * if v1 > v2 return 1
     * if v1 is not null, v2 is null return 1
     * if v1 = v2, return 0
     * if v1 is null, v2 is null return 0
     * if v1 < v2, return -1
     * if v1 is null, v2 is not null  return -1
     *
     * @param v1
     * @param v2
     * @return
     */
    public static <V> int compare(V v1, V v2) {
        return v1 == null ? (v2 == null ? 0 : -1) : (v2 == null ? 1 : ((Comparable) v1).compareTo(v2));
    }


    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        Preconditions.checkNotNull(fragmentManager);
        Preconditions.checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
