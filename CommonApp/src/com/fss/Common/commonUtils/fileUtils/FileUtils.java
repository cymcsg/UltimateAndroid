package com.fss.Common.commonUtils.fileUtils;

import android.content.Context;
import android.os.Environment;
import com.fss.Common.commonUtils.logUtils.Logs;

import java.io.*;

/**
 * TO do something with File,like read ,del etc.
 * User: cym
 * Date: 13-10-23
 * Time: 上午11:01
 *
 */
public class FileUtils {
    public static String readFilesToString(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists() || file.isDirectory())
            throw new FileNotFoundException();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String temp = null;
        StringBuffer sb = new StringBuffer();
        temp = br.readLine();
        while (temp != null) {
            sb.append(temp + "\n");
            temp = br.readLine();
        }
        return sb.toString();
    }

    public static String getCurrentDataPath(Context context) {
        String currentDataPath = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            currentDataPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/qingdaobus";

        } else {
            currentDataPath= context.getFilesDir().getAbsolutePath();
        }
        return currentDataPath;
    }

    public static void writeFileFromString(String fileName, String content) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File(fileName));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(content.getBytes());
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        fileOutputStream.close();
    }

    public static void writeFileFromStringBuffers(String fileName, String content) throws IOException {
        String s = new String();
        String s1 = new String();
        try {
            File f = new File(fileName);
            if (f.exists()) {
                Logs.d("文件存在");
            } else {
                Logs.d("文件不存在，正在创建...");
                if (f.createNewFile()) {
                    Logs.d("文件创建成功！");
                } else {
                    Logs.d("文件创建失败！");
                }
            }
            BufferedReader input = new BufferedReader(new FileReader(f));
            while ((s = input.readLine()) != null) {
                s1 += s + "\n";
            }
            System.out.println("文件内容：" + s1);
            input.close();
            s1 += content;
            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write(s1);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建文件夹
     *
     * @param fileName 目录
     */
    public static void createDir(String fileName) throws IOException {
        File dir = new File(fileName);
        if (!dir.exists())
            dir.mkdir();
    }/** */

    /**
     * 创建新文件
     *
     * @param path     目录
     * @param fileName 文件名
     * @throws java.io.IOException
     */
    public static void createFile(String path, String fileName) throws IOException {
        File file = new File(path + "/" + fileName);
        if (!file.exists())
            file.createNewFile();
    }/** */
    /**
     * 删除文件
     *
     * @param fileName 文件名
     */
    public void delFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists() && file.isFile())
            file.delete();
    }

    /** */

    public static void deleteFile(String fileName) throws IOException {
        File f = new File(fileName);
        if (f.isDirectory()) {//如果是目录，先递归删除
            String[] list = f.list();
            for (int i = 0; i < list.length; i++) {
                deleteFile(fileName + "//" + list[i]);//先删除目录下的文件
            }
        }
        f.delete();
    }

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath);  //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete();  //删除空文件夹

        } catch (Exception e) {
            System.out.println("删除文件夹操作出错");
            e.printStackTrace();

        }

    }

    /**
     * 删除文件夹里面的所有文件
     *
     * @param path String  文件夹路径  如  c:/fqf
     */
    public static void delAllFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
            }
        }
    }


    /**
     * 追加文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true
     *
     * @param fileName
     * @param content
     */
    public static void WriteStreamAppendByFileOutputStream(String fileName, String content) throws IOException {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName, true)));
            out.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 追加文件：使用FileWriter
     *
     * @param fileName
     * @param content
     */
    public static void WriteStreamAppendByFileWriter(String fileName, String content) throws IOException {
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 追加文件：使用RandomAccessFile
     *
     * @param fileName 文件名
     * @param content  追加的内容
     */
    public static void WriteStreamAppendByRandomAccessFile(String fileName, String content) throws IOException {
        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String  原文件路径  如：c:/fqf.txt
     * @param newPath String  复制后路径  如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) throws IOException {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {  //文件存在时
                InputStream inStream = new FileInputStream(oldPath);  //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;  //字节数  文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }

    /**
     * 复制单个文件
     *
     * @param is      InputStream
     * @param newPath String  复制后路径  如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFileFromAssest(InputStream is, String newPath) throws IOException {
        FileOutputStream fs = null;
        try {
            int bytesum = 0;
            int byteread = 0;
            if (is != null) {  //文件存在时

                fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = is.read(buffer)) != -1) {
                    bytesum += byteread;  //字节数  文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                is.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        } finally {
            try {
                is.close();
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
                Logs.e(e, "");
            }
        }

    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String  原文件路径  如：c:/fqf
     * @param newPath String  复制后路径  如：f:/fqf/ff
     * @return boolean
     */
    public void copyFolder(String oldPath, String newPath) throws IOException {

        try {
            (new File(newPath)).mkdirs();  //如果文件夹不存在  则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();

        }

    }

    /**
     * 移动文件到指定目录
     *
     * @param oldPath String  如：c:/fqf.txt
     * @param newPath String  如：d:/fqf.txt
     */
    public void moveFile(String oldPath, String newPath) throws IOException {
        copyFile(oldPath, newPath);
        delFile(oldPath);

    }

    /**
     * 移动文件到指定目录
     *
     * @param oldPath String  如：c:/fqf.txt
     * @param newPath String  如：d:/fqf.txt
     */
    public void moveFolder(String oldPath, String newPath) throws IOException {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);

    }


}
