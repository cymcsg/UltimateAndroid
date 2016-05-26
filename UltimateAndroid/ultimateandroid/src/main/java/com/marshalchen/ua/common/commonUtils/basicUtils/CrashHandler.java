package com.marshalchen.ua.common.commonUtils.basicUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;
import com.marshalchen.ua.common.commonUtils.logUtils.Logs;

/**
 * Catch the uncaught exception
 * Usage:
 *  CrashHandler crashHandler = CrashHandler.getInstance();
    crashHandler.init(getApplicationContext(), "/crash/", "Something error,please try again");
 */
public class CrashHandler implements UncaughtExceptionHandler {

    public static String TAG = "Chen";
    String showMessage = "There is something wrong with the app.";

    private UncaughtExceptionHandler mDefaultHandler;

    private static CrashHandler INSTANCE = new CrashHandler();

    private Context mContext;

    private Map<String, String> infos = new HashMap<String, String>();


    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    String crashFilePath = "/crash/";

    private CrashHandler() {
    }

    /**
     * Singleton
     */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * Initialize
     *
     * @param context
     * @param crashFilePath
     */
    public void init(Context context, String crashFilePath, String showMessage) {
        mContext = context;

        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(this);
        // this.systemServiceObject = systemServiceObject;
        this.crashFilePath = crashFilePath;
        if (BasicUtils.judgeNotNull(showMessage)) {
            this.showMessage = showMessage;
        }
    }

    /**
     * Initialize
     *
     * @param context
     */
    public void init(Context context) {
        init(context, "/crash/");
    }

    /**
     * Initialize
     *
     * @param context
     */
    public void init(Context context, String crashFilePath) {
        init(context, crashFilePath, "");
    }

    /**
     * Caught Exception
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Logs.e("error : ", e);
            }
            Logs.d("uncaught exception is catched!");
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }


    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, showMessage, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        collectDeviceInfo(mContext);

        String filemameString = saveCrashInfo2File(ex);
        Logs.d("filemameString", filemameString);
        return true;
    }

    /**
     * Collect Device info
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);

            }
        } catch (NameNotFoundException e) {
            Logs.e("an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Logs.d(field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Logs.e("an error occured when collect crash info", e);
            }
        }
    }

    /**
     * Save Info to files
     *
     * @param ex
     * @return filename
     */
    private String saveCrashInfo2File(Throwable ex) {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                String path =
                        //StorageUtils.getCacheDirectory(mContext) +
                        Environment.getExternalStorageDirectory().getAbsolutePath() +
                                (BasicUtils.judgeNotNull(crashFilePath) ? crashFilePath : "/crash/");
                Logs.d("path----" + path);
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            Logs.e("an error occured while writing file...", e);
        }
        return null;
    }

}  


