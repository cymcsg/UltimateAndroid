package com.marshalchen.ua.common.commonUtils.dbUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.marshalchen.ua.common.commonUtils.fileUtils.FileUtils;

import java.io.*;

/**
 * Some utils about database like getting or creating database.
 *
 */
public class DataBaseUtils {

    public static void copyDatabase(Context context, File dbFile, String databaseName) throws IOException {
        InputStream stream = context.getAssets().open(databaseName);
        FileUtils.writeFile(dbFile, stream);
        stream.close();
    }

    /**
     * Open a database which is used for reading only.
     * If the database is not exists,the method will create a database from the assets{@link #copyDatabase(android.content.Context, java.io.File, String)}.
     *
     * @param context
     * @param databaseName
     * @return
     * @throws SQLiteException
     */
    public synchronized SQLiteDatabase getReadableDatabase(Context context,String databaseName) throws SQLiteException{
        File dbFile = context.getDatabasePath(databaseName);
        if (dbFile != null && !dbFile.exists()) {
            try {
                copyDatabase(context,dbFile,databaseName);
            } catch (IOException e) {
                throw new RuntimeException("Copying database error", e);
            }
        }
        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READONLY);
    }

    /**
     * Open a database which is used for both reading and writing.
     * If the database is not exists,the method will create a database from the assets{@link #copyDatabase(android.content.Context, java.io.File, String)}.
     * @param context
     * @param databaseName
     * @return
     */
    public synchronized SQLiteDatabase getWritableDatabase(Context context,String databaseName) {
        File dbFile = context.getDatabasePath(databaseName);
        if (dbFile != null && !dbFile.exists()) {
            try {
                copyDatabase(context,dbFile,databaseName);
            } catch (IOException e) {
                throw new RuntimeException("Copying database error", e);
            }
        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
    }
}
