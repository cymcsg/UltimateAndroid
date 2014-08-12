package com.fss.common.commonUtils.basicUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import com.fss.common.commonUtils.logUtils.Logs;

import java.text.DecimalFormat;
import java.util.*;

/**
 * User: cym
 * Date: 13-9-24
 * Time: 下午3:28
 */
public class BasicUtils {
    /**
     * Print all items of HashMap(which value is or can be convert to String)
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

        //支持并发
        try {
            for (Iterator it = list.iterator(); it.hasNext(); ) {
                iterateHashMap((HashMap) it.next(), classAndMethodName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logs.e(e.getMessage());
        }

//Better speed,but not very good at concurrent
//        for(int    i=0;    i<list.size();    i++)    {
//              HashMap hashMap  =  (HashMap)  list.get(i);
//            iterateHashMap(hashMap, classAndMethodName);
//
//        }

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

    // 获取当前应用的版本号：
    public static String getVersionName(Context context) {
        String version = "";
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
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

    public static void popAlertDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setPositiveButton("OK", null)
                .setTitle(title)
                .setMessage(message).show();
    }

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

    public static boolean judgeNotNull(String string) {
        // return string != null && !string.equals("") && !string.equals("null") ? true : false;

        return judgeNotNull(string, new String[0]);
    }

    public static boolean judgeNotNull(String string, String... strings) {
        boolean flag = true;
        if (!(string != null && !string.equals("") && !string.equals("null"))) return false;
        for (String s : strings) {
            if (s == null || s.equals("") || s.equals("null")) {
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
        return list != null && list.size() > 0 ? true : false;
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
                break;
            }
        }

        return flag;
    }
}
