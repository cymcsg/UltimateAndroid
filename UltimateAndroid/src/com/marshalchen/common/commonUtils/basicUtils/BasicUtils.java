package com.marshalchen.common.commonUtils.basicUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import com.marshalchen.common.commonUtils.logUtils.Logs;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Some Useful Utils
 * <p>{@link #judgeNotNull(String, String...)}</p>
 * <p>{@link #judgeNotNull(Object)}</p>
 * <p>{@link #getVersionName(android.content.Context)}</p>
 * <p>{@link #getVersionCode(android.content.Context)}</p>
 * <p>{@link #iterateHashMap(java.util.HashMap, String)}</p>
 * <p>{@link #iterateListHashMap(java.util.List, String)}</p>
 * <p>{@link #sendIntent(android.content.Context, Class)}</p>
 * <p>{@link #sendIntent(android.content.Context, Class, String, android.os.Parcelable)}</p>
 * <p>{@link #getSharedPreferences(android.content.Context, String, String)}</p>
 * <p>{@link #putSharedPreferences(android.content.Context, String, String, String)}</p>
 */
public class BasicUtils {
    /**
     * Print all items of HashMap(Notice:The value should be or can be convert to String)
     *
     * @param hashMap
     * @param classAndMethodName
     */
    public static void iterateHashMap(HashMap hashMap, String classAndMethodName) {
        Iterator iterator = hashMap.entrySet().iterator();
        Logs.d(classAndMethodName);
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            Logs.d(obj.toString());
            //Method2
//            Map.Entry entry = (Map.Entry) obj;
//            Object key = entry.getKey();
//            Object val = entry.getValue();
//            Logs.d(key.toString());
//            Logs.d(val.toString());

        }
    }

    public static void iterateListHashMap(List list, String classAndMethodName) {

        //support concurrent
        try {
            for (Iterator it = list.iterator(); it.hasNext(); ) {
                iterateHashMap((HashMap) it.next(), classAndMethodName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logs.e(e.getMessage());
        }


    }

    public static int convertStringToInt(String num) {
        //Logs.d("inmessage-----"+num);
        if (num == null) {
            return 0;
        }
        int numInt = 0;
        try {
            numInt = Integer.valueOf(num);
        } catch (Exception e) {
            e.printStackTrace();
            Logs.e(e.getMessage());
            Logs.e("num----" + num);
        } finally {
            return numInt;
        }

    }

    public static long convertStringToLong(String num) {
        long numInt = 0;
        try {
            numInt = Long.valueOf(num);
        } catch (Exception e) {
            e.printStackTrace();
            Logs.e(e.getMessage());
        } finally {
            return numInt;
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
     * Pop a simple alertdialog
     *
     * @param context
     * @param title
     * @param message
     */
    public static void popAlertDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setPositiveButton("OK", null)
                .setTitle(title)
                .setMessage(message).show();
    }

    /**
     * Pop a simple alertdialog
     *
     * @param context
     * @param title
     * @param message
     */
    public static void popAlertDialog(Context context, int title, String message) {
        new AlertDialog.Builder(context)
                .setPositiveButton("OK", null)
                .setTitle(title)
                .setMessage(message).show();
    }


    /**
     * @param str
     * @param formatAs
     * @return
     */
    public static String formatNumber(String str, String formatAs) {
        DecimalFormat df = new DecimalFormat(formatAs);
        String str2 = df.format(Integer.parseInt(str));
        return str2;
    }

    /**
     * @param num
     * @param formatAs
     * @return
     */
    public static String formatNumber(int num, String formatAs) {
        DecimalFormat df = new DecimalFormat(formatAs);
        String str2 = df.format(num);
        return str2;
    }

    public static void sendIntent(Context context, Class classes) {
        Intent intent = new Intent();
        intent.setClass(context, classes);
        context.startActivity(intent);
    }

    public static void sendIntent(Context context, Class classes, String key, String value, String anotherKey, String anotherValue) {
        Intent intent = new Intent();
        intent.setClass(context, classes);
        intent.putExtra(key, value);
        intent.putExtra(anotherKey, anotherValue);
        context.startActivity(intent);
    }

    public static void sendIntent(Context context, Class classes, String key, String value) {
        Intent intent = new Intent();
        intent.setClass(context, classes);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }

    public static void sendIntent(Context context, Class classes, String key, Parcelable value) {
        Intent intent = new Intent();
        intent.setClass(context, classes);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }

    public static void sendIntent(Context context, Class classes, String key, ArrayList<? extends Parcelable> value) {
        Intent intent = new Intent();
        intent.setClass(context, classes);
        intent.putParcelableArrayListExtra(key, value);
        context.startActivity(intent);
    }

    public static void sendIntent(Context context, Class classes, String key, ArrayList<? extends Parcelable> value, String anotherKey, String anotherValue) {
        Intent intent = new Intent();
        intent.setClass(context, classes);
        intent.putParcelableArrayListExtra(key, value);
        intent.putExtra(anotherKey, anotherValue);
        context.startActivity(intent);
    }

    public static String getSharedPreferences(Context context, String fileName, String parameterName) {
        SharedPreferences sp = context.getSharedPreferences(fileName, 0);
        String parameter = sp.getString(parameterName, "");
        return parameter;
    }

    public static String getSharedPreferences(Context context, String fileName, String parameterName, String otherDefaultReturns) {
        SharedPreferences sp = context.getSharedPreferences(fileName, 0);
        String parameter = sp.getString(parameterName, otherDefaultReturns);
        return parameter;
    }

    public static void putSharedPreferences(Context context, String fileName, String parameterName, String parameterValue) {
        SharedPreferences.Editor sharedDate = context.getSharedPreferences(fileName, 0).edit();
        sharedDate.putString(parameterName, parameterValue);
        sharedDate.commit();
    }

    public static void putSharedPreferences(Context context, String fileName, List<HashMap<String, String>> list) {
        SharedPreferences.Editor sharedDate = context.getSharedPreferences(fileName, 0).edit();
        for (HashMap<String, String> map : list) {
            sharedDate.putString(map.keySet().toString(), map.get(map.keySet().toString()));
        }
        sharedDate.commit();
    }

    public static void putSharedPreferences(Context context, String fileName, HashMap<String, String> hashMap) {
        SharedPreferences.Editor sharedDate = context.getSharedPreferences(fileName, 0).edit();
        if (BasicUtils.judgeNotNull(hashMap)) {
            Iterator iterator = hashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                sharedDate.putString(key.toString(), value.toString());

            }
        }
        sharedDate.commit();
    }

    /**
     * @param string
     * @return
     * @see #judgeNotNull(String, String...)
     */
    public static boolean judgeNotNull(String string) {
        // return string != null && !string.equals("") && !string.equals("null") ? true : false;

        return judgeNotNull(string, new String[0]);
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

    public static boolean judgeNotNull(byte[] bytes) {
        return bytes != null && bytes.length >= 1;
    }

    public static boolean judgeNotNull(Map map) {
        return map != null && map.size() > 0 ? true : false;
    }

    public static boolean judgeNotNull(List list) {
        //return list != null && list.size() > 0 ? true : false;
        return judgeNotNull(list, null);
    }

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

    public static boolean judgeNotNull(Object object) {
        return judgeNotNull(object, new Object[0]);
    }

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
}
