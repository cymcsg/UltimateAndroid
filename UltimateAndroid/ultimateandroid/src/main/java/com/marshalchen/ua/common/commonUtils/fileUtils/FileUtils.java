package com.marshalchen.ua.common.commonUtils.fileUtils;

import android.content.Context;
import android.os.Environment;

import com.marshalchen.ua.common.commonUtils.basicUtils.BasicUtils;
import com.marshalchen.ua.common.commonUtils.logUtils.Logs;

import java.io.*;

/**
 * To do something with file,like reading ,writing etc.
 */
public class FileUtils {

    /**
     * Read file to String
     *
     * @param fileName
     * @param charset
     * @return
     * @throws IOException
     */
    public static String readFile(String fileName, String charset) throws IOException {
        File file = new File(fileName);
        return readFile(file, charset);
    }

    /**
     * Read file to String with default charset 'UTF-8'
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String readFile(String fileName) throws IOException {
        return readFile(fileName, "UTF-8");
    }


    /**
     * Read file to String with default charset 'UTF-8'
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String readFile(File fileName) throws IOException {
        return readFile(fileName, "UTF-8");
    }


    /**
     * Read file to String
     *
     * @param fileName
     * @param charset
     * @return
     * @throws IOException
     */
    public static String readFile(File fileName, String charset) throws IOException {
        if (fileName == null || !fileName.isFile()) {
            return null;
        }
        StringBuilder sb = new StringBuilder("");
        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(fileName), charset);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!sb.toString().equals("")) {
                    sb.append("\r\n");
                }
                sb.append(line);
            }
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            Logs.e(e, "IOException");
            throw e;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Logs.e(e, "IOException occurred when closing BufferedReader");
                    throw e;
                }
            }
        }
    }

    public static String getCurrentDataPath(Context context) throws IOException {

        return getCurrentDataPath(context, "");
    }

    public static String getCurrentDataPath(Context context, String folderName) throws IOException {
        String currentDataPath = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            currentDataPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folderName;
            makeDirs(currentDataPath);
        } else {
            currentDataPath = context.getFilesDir().getAbsolutePath();
        }
        return currentDataPath;
    }


    /**
     * Create New File
     *
     * @param path     Folder Name
     * @param fileName File Name
     * @throws java.io.IOException
     */
    public static void createFile(String path, String fileName) throws IOException {
        File file = new File(path + "/" + fileName);
        if (!file.exists())
            file.createNewFile();
    }


    /**
     * Append file using FileOutputStream
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
     * Append file using FileWriter
     *
     * @param fileName
     * @param content
     */
    public static void writeStreamAppendByFileWriter(String fileName, String content) throws IOException {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Append file using RandomAccessFile
     *
     * @param fileName
     * @param content
     */
    public static void WriteStreamAppendByRandomAccessFile(String fileName, String content) throws IOException {
        try {
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            long fileLength = randomFile.length();
            // Write point to the end of file.
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Copy file
     *
     * @param oldPath String
     * @param newPath String
     * @return boolean
     */
    public static void copyFileFromPath(String oldPath, String newPath) throws IOException {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("copy failed");
            e.printStackTrace();

        }

    }

    /**
     * Copy File from InputStream
     *
     * @param is      InputStream
     * @param newPath String
     * @return boolean
     */
    public static void copyFileFromInputStream(InputStream is, String newPath) throws IOException {
        FileOutputStream fs = null;
        try {
            int bytesum = 0;
            int byteread = 0;
            if (is != null) {

                fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                int length;
                while ((byteread = is.read(buffer)) != -1) {
                    bytesum += byteread;
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                is.close();
            }
        } catch (Exception e) {
            System.out.println("Copy Failed");
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
     * If {@code append} is true and the file already exists, it will be appended to; otherwise
     * it will be truncated. The file will be created if it does not exist.
     *
     * @param filePath
     * @param content
     * @param append   Indicates whether or not to append to an existing file.
     * @return return false if content is empty, true otherwise
     * @throws RuntimeException if an error occurs while operator FileWriter
     */
    public static boolean writeFile(String filePath, String content, boolean append) throws IOException {
        if (BasicUtils.judgeNotNull(content)) {
            return false;
        }

        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            throw e;
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
    }


    /**
     * If the file already exists, it will be truncated. The file will be created if it does not exist.
     *
     * @param filePath
     * @param content
     * @return
     */
    public static boolean writeFile(String filePath, String content) throws IOException {
        return writeFile(filePath, content, false);
    }


    /**
     * Write file with InputStream
     *
     * @param filePath
     * @param stream
     * @return
     */
    public static boolean writeFile(String filePath, InputStream stream) {
        return writeFile(filePath, stream, false);
    }

    /**
     * write file
     *
     * @param filePath the file to be opened for writing.
     * @param stream   the input stream
     * @param append   if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
     * @return return true
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    public static boolean writeFile(String filePath, InputStream stream, boolean append) {
        return writeFile(filePath != null ? new File(filePath) : null, stream, append);
    }

    /**
     * write file, the bytes will be written to the begin of the file
     *
     * @param file
     * @param stream
     * @return
     * @see {@link #writeFile(java.io.File, java.io.InputStream, boolean)}
     */
    public static boolean writeFile(File file, InputStream stream) {
        return writeFile(file, stream, false);
    }

    /**
     * Write file
     *
     * @param file   the file to be opened for writing.
     * @param stream the input stream
     * @param append if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
     * @return return true
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    public static boolean writeFile(File file, InputStream stream, boolean append) {
        OutputStream o = null;
        try {
            makeDirs(file.getAbsolutePath());
            o = new FileOutputStream(file, append);
            byte data[] = new byte[1024];
            int length = -1;
            while ((length = stream.read(data)) != -1) {
                o.write(data, 0, length);
            }
            o.flush();
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (o != null) {
                try {
                    o.close();
                    stream.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * Copy file
     *
     * @param sourceFilePath
     * @param destFilePath
     * @return
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    public static boolean copyFile(String sourceFilePath, String destFilePath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(sourceFilePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        }
        return writeFile(destFilePath, inputStream);
    }


    /**
     * Get only the file name without extension
     *
     * @param filePath
     * @return
     */
    public static String getFileNameWithoutExtension(String filePath) {
        if (isFileExist(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(".");
        int filePosi = filePath.lastIndexOf(File.separator);
        if (filePosi == -1) {
            return (extenPosi == -1 ? filePath : filePath.substring(0, extenPosi));
        }
        if (extenPosi == -1) {
            return filePath.substring(filePosi + 1);
        }
        return (filePosi < extenPosi ? filePath.substring(filePosi + 1, extenPosi) : filePath.substring(filePosi + 1));
    }

    /**
     * Get  the file name with extension
     *
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        if (isFileExist(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
    }

    /**
     * Get folder name from the filepath
     *
     * @param filePath
     * @return
     */
    public static String getFolderName(String filePath) {

        if (isFileExist(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
    }

    /**
     * Get extension of the file
     *
     * @param filePath
     * @return
     */
    public static String getFileExtension(String filePath) {
        if (!BasicUtils.judgeNotNull(filePath)) {
            return "";
        }

        int extenPosi = filePath.lastIndexOf(".");
        int filePosi = filePath.lastIndexOf(File.separator);
        if (extenPosi == -1) {
            return "";
        }
        return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
    }

    /**
     * Create Folder
     *
     * @param dirPath
     * @return
     */
    public static boolean makeDirs(String dirPath) {
        File folder = new File(dirPath);
        if (folder.exists()) {
            return false;
        }
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }


    /**
     * Indicates if the file represents a file exists.
     *
     * @param filePath
     * @return
     */
    public static boolean isFileExist(String filePath) {
        if (!BasicUtils.judgeNotNull(filePath)) {
            return false;
        }

        File file = new File(filePath);
        return (file.exists() && file.isFile());
    }

    /**
     * Indicates if the file represents a directory exists.
     *
     * @param directoryPath
     * @return
     */
    public static boolean isFolderExist(String directoryPath) {
        if (!BasicUtils.judgeNotNull(directoryPath)) {
            return false;
        }
        File dire = new File(directoryPath);
        return (dire.exists() && dire.isDirectory());
    }

    /**
     * Delete the File no matter it's a file or folder.If the file path is a folder,this method
     * will delete all the file in the folder.
     *
     * @param filePath
     * @return
     */
    public static boolean deleteFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        return file.delete();
    }

    /**
     * Get size of the file
     *
     * @param path
     * @return Return the length of the file in bytes. Return -1 if the file does not exist.
     */
    public static long getFileSize(String path) {
        if (!BasicUtils.judgeNotNull(path)) {
            return -1;
        }
        File file = new File(path);
        return (file.exists() && file.isFile() ? file.length() : -1);
    }


    /**
     * Get file which from assets
     *
     * @param context
     * @param fileName The name of the asset to open.
     * @return
     */
    public static String getFileFromAssets(Context context, String fileName) {
        if (context == null || BasicUtils.judgeNotNull(fileName)) {
            return null;
        }

        StringBuilder s = new StringBuilder("");
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get file which from Raw
     * @param context
     * @param resId
     * @return
     */
    public static String getFileFromRaw(Context context, int resId) {
        if (context == null) {
            return null;
        }

        StringBuilder s = new StringBuilder();
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().openRawResource(resId));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
