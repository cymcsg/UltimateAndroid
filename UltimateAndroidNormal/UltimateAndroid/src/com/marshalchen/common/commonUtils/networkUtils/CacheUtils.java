package com.marshalchen.common.commonUtils.networkUtils;

import android.graphics.Bitmap;
import android.net.Uri;
import com.marshalchen.common.commonUtils.urlUtils.MD5Utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * User: cym
 * Date: 13-10-30
 *
 */
public class CacheUtils {

    public static Uri getImageURI(String path, File cache) throws Exception {
        String name = MD5Utils.getMD5(path) + path.substring(path.lastIndexOf("."));
        File file = new File(cache, name);
        if (file.exists()) {
            return Uri.fromFile(file);//Uri.fromFile(path)这个方法能得到文件的URI
        } else {
            // 从网络上获取图片
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            if (conn.getResponseCode() == 200) {

                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                is.close();
                fos.close();

                return Uri.fromFile(file);
            }
        }
        return null;
    }

    public static void saveFile(Bitmap bm, String path, String fileName) throws IOException {
        String uri = path;
        if (!uri.endsWith("/")) {
            uri = uri + "/";
        }
        uri=uri+fileName;
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(uri);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
       // SharedPreferences sp=
    }

}
