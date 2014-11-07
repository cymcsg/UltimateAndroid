package com.marshalchen.common.commonUtils.dbUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.marshalchen.common.commonUtils.logUtils.Logs;

import java.io.*;

/**
 * To copy database from asset or sdcard to the app directory.
 * User: cym
 * Date: 13-9-25
 * Time: 下午2:30
 * .
 */
public class DataBaseUtils {

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


        boolean dbExist = checkDataBase(DATABASE_PATH, dbName);

        if (dbExist) {
            Logs.d("The database is exist.");
        } else {
            try {
                copyDataBase(DATABASE_PATH, dbName, context, resource);
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     *
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



}
