package com.fss.common.commonUtils.basicUtils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.fss.common.commonUtils.logUtils.Logs;

import java.io.*;

/**
 * To copy database from asset or sdcard to the app directory.
 * User: cym
 * Date: 13-9-25
 * Time: 下午2:30
 * .
 */
public class PackageUtils {

    public static boolean copyDateBaseToMemory(Context context, String dbname, int resource,String signName) {
        String packageName = context.getPackageName();
        String DATABASE_PATH = "/data/data/" + packageName + "/databases/";
        try {
            String path = context.getDatabasePath(dbname).getAbsolutePath();
            if (context.getDatabasePath(dbname).getAbsolutePath().substring(0, path.lastIndexOf("/"))
                    .contains(signName)) {
                DATABASE_PATH = path.substring(0, path.lastIndexOf("/") + 1);
            }
            Logs.d(context.getDatabasePath(dbname).getAbsolutePath() + "  " + context.getApplicationContext().getPackageResourcePath());

        } catch (Exception e) {
            e.printStackTrace();
            Logs.e(e, "");
        }

        try {
            copyDataBaseToPhone(DATABASE_PATH, dbname, context, resource);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Logs.e(e.getMessage());
            return false;
        }

    }

    private static void copyDataBaseToPhone(String DATABASE_PATH, String dbName, Context context, int resource) {

        // 判断数据库是否存在
        boolean dbExist = checkDataBase(DATABASE_PATH, dbName);

        if (dbExist) {
            Logs.d("The database is exist.");
        } else {// 不存在就把raw里的数据库写入手机
            try {
                copyDataBase(DATABASE_PATH, dbName, context, resource);
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * 判断数据库是否存在
     *
     * @return false or true
     */
    private static boolean checkDataBase(String DATABASE_PATH, String dbName) {
        SQLiteDatabase db = null;
        try {
            String databaseFilename = DATABASE_PATH + dbName;
            db = SQLiteDatabase.openDatabase(databaseFilename, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            Logs.e(e, "");
        }
        if (db != null) {
            db.close();
        }
        return db != null ? true : false;
    }

    /**
     * 复制数据库到手机指定文件夹下
     *
     * @throws java.io.IOException
     */
    private static void copyDataBase(String DATABASE_PATH, String dbName, Context context, int resource) throws IOException {
        String databaseFilenames = DATABASE_PATH + dbName;
        File dir = new File(DATABASE_PATH);
        if (!dir.exists())// 判断文件夹是否存在，不存在就新建一个
            dir.mkdir();
        FileOutputStream os = new FileOutputStream(databaseFilenames);// 得到数据库文件的写入流
        InputStream is = context.getResources().openRawResource(resource);// 得到数据库文件的数据流
        // InputStream is=context.getAssets().open(dbName);
        byte[] buffer = new byte[8192];
        int count = 0;
        while ((count = is.read(buffer)) > 0) {
            os.write(buffer, 0, count);
            os.flush();
        }
        is.close();
        os.close();
    }

    public static String getVersionName(Context context) throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context
                .getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }

}
