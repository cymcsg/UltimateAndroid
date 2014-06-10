package com.fss.Common.commonUtils.fileUtils;

import com.fss.Common.commonUtils.logUtils.Logs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: cym
 * Date: 13-10-23
 * Time: 上午9:55
 * To change this template use File | Settings | File Templates.
 */
public class UpdateUtils {
    public static void updateInMemory(String fileName) {
        String[] args = {"chmod", "604", fileName};
        exec(args);

    }

    public static void exec(String fileName) {
        try {
            Process p = Runtime.getRuntime().exec("chmod 644 " + fileName);
            int status = p.waitFor();
            if (status == 0) {
                //chmod succeed
            } else {
                //chmod failed
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception e) {
            Logs.e(e, "");
        }
    }

    public static String exec(String[] args) {
        String result = "";
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        Process process = null;
        InputStream errIs = null;
        InputStream inIs = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int read = -1;
            process = processBuilder.start();
            errIs = process.getErrorStream();
            while ((read = errIs.read()) != -1) {
                baos.write(read);
            }
            baos.write('n');
            inIs = process.getInputStream();
            while ((read = inIs.read()) != -1) {
                baos.write(read);
            }
            byte[] data = baos.toByteArray();
            result = new String(data);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (errIs != null) {
                    errIs.close();
                }
                if (inIs != null) {
                    inIs.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (process != null) {
                process.destroy();
            }
        }
        System.out.println(result);
        return result;
    }

}
