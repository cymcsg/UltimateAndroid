package com.marshalchen.common.commonUtils.fileUtils;

import com.marshalchen.common.commonUtils.logUtils.Logs;

import java.io.*;
import java.util.zip.*;

/**
 * Zip an unzip utils that can use zip or gzip
 */
public class ZipUtils {


    /**
     * Unzip file
     *
     * @param unzipfileName
     * @param mDestPath
     * @return
     */
    public static int unZip(String unzipfileName, String mDestPath) {
        if (!mDestPath.endsWith("/")) {
            mDestPath = mDestPath + "/";
        }
        FileOutputStream fileOut = null;
        ZipInputStream zipIn = null;
        ZipEntry zipEntry = null;
        File file = null;
        int readedBytes = 0;
        byte buf[] = new byte[4096];
        try {
            zipIn = new ZipInputStream(new BufferedInputStream(new FileInputStream(unzipfileName)));
            while ((zipEntry = zipIn.getNextEntry()) != null) {
                file = new File(mDestPath + zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    file.mkdirs();
                } else {

                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    fileOut = new FileOutputStream(file);
                    while ((readedBytes = zipIn.read(buf)) > 0) {
                        fileOut.write(buf, 0, readedBytes);
                    }
                    fileOut.close();
                }
                zipIn.closeEntry();

            }
            return 1;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            Logs.e(e, "");
            return 0;
        } finally {
            try {
                if (fileOut != null) fileOut.close();
                if (zipIn != null) zipIn.close();
            } catch (Exception e) {
                e.printStackTrace();
                Logs.e(e, "");
            }

        }
    }

    /**
     * Unzip file using InputStream unzipfileStream
     *
     * @param unzipfileStream
     * @param mDestPath
     * @return
     */
    public static int unZip(InputStream unzipfileStream, String mDestPath) {
        if (!mDestPath.endsWith("/")) {
            mDestPath = mDestPath + "/";
        }
        FileOutputStream fileOut = null;
        ZipInputStream zipIn = null;
        ZipEntry zipEntry = null;
        File file = null;
        int readedBytes = 0;
        byte buf[] = new byte[4096];
        try {
            zipIn = new ZipInputStream(new BufferedInputStream(unzipfileStream));
            while ((zipEntry = zipIn.getNextEntry()) != null) {
                Logs.d(zipEntry.getName());
                file = new File(mDestPath + zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    file.mkdirs();
                } else {
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    fileOut = new FileOutputStream(file);
                    while ((readedBytes = zipIn.read(buf)) > 0) {
                        fileOut.write(buf, 0, readedBytes);
                    }
                    fileOut.close();
                }
                zipIn.closeEntry();

            }
            return 1;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return 0;
        } finally {
            try {
                if (fileOut != null) fileOut.close();
                if (zipIn != null) zipIn.close();
            } catch (Exception e) {
                e.printStackTrace();
                Logs.e(e, "");
            }
        }
    }


    /**
     * Gzip file.
     *
     * @param zipFileName
     * @param mDestFile
     * @throws Exception
     */
    public static void gzips(String zipFileName, String mDestFile) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(zipFileName), "UTF-8"));

        BufferedOutputStream out = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(mDestFile)));
        int c;
        while ((c = in.read()) != -1) {

            out.write(String.valueOf((char) c).getBytes("UTF-8"));
        }
        in.close();
        out.close();

    }

    /**
     * Unzip a gzip file with default output file path.
     *
     * @param inFileName
     * @throws Exception
     */
    public static void unzipGzips(String inFileName) throws Exception {
        unzipGzips(inFileName, FileUtils.getFileNameWithoutExtension(inFileName));
    }

    /**
     * Unzip a gzip file.
     *
     * @param inFileName
     * @param outFileName
     * @throws Exception
     */
    public static void unzipGzips(String inFileName, String outFileName) throws Exception {
        GZIPInputStream in = null;
        FileOutputStream out = null;
        try {

            if (!FileUtils.getFileExtension(inFileName).equalsIgnoreCase("gz")) {
                System.err.println("File name must have extension of \".gz\"");
                System.exit(1);
            }

            System.out.println("Opening the compressed file.");

            try {
                in = new GZIPInputStream(new FileInputStream(inFileName));
            } catch (FileNotFoundException e) {
                System.err.println("File not found. " + inFileName);
                System.exit(1);
            }
            System.out.println("Open the output file.");

            try {
                out = new FileOutputStream(outFileName);
            } catch (FileNotFoundException e) {
                System.err.println("Could not write to file. " + outFileName);
                System.exit(1);
            }

            System.out.println("Transfering bytes from compressed file to the output file.");
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            System.out.println("Closing the file and stream");


        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
                Logs.e(e, "");
            }
        }
    }

    /**
     * Zip a file.
     *
     * @param fileFrom
     * @param fileTo
     */
    public static void zipFile(String fileFrom, String fileTo) {
        FileInputStream in = null;
        FileOutputStream out = null;
        ZipOutputStream zipOut = null;
        try {
            File file = new File(fileFrom);
            file.getName();
            in = new FileInputStream(fileFrom);
            out = new FileOutputStream(fileTo);
            zipOut = new ZipOutputStream(out);
            ZipEntry entry = new ZipEntry(file.getName());
            zipOut.putNextEntry(entry);
            int nNumber;
            byte[] buffer = new byte[512];
            while ((nNumber = in.read(buffer)) != -1) {
                zipOut.write(buffer, 0, nNumber);

            }
            zipOut.close();

            out.close();
            in.close();
            System.out.print("success" + file.getName());
        } catch (IOException e) {

            System.out.println(e);
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (zipOut != null) zipOut.close();
            } catch (Exception e) {
                e.printStackTrace();
                Logs.e(e, "");
            }
        }
    }
}

