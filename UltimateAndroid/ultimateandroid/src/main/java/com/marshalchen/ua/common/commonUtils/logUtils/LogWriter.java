package com.marshalchen.ua.common.commonUtils.logUtils;


import java.io.*;
import java.util.Properties;

/**
 *  A class to help you log in memory.
 *
 *  Usage:
 *  LogWriter logger = null;
    try {
    String fileName = "/tmp/logger.log";
    logger = LogWriter.getLogWriter(fileName);
    logger.log("First log!");
    logger.log("log");
    logger.log("Third log");
    logger.log("log");
    logger.close();
    //ReadFromFile.readFileByLines(fileName);
    } catch (LogException e) {
      e.printStackTrace();
    }
 */
public class LogWriter {

    public static final String LOG_CONFIGFILE_NAME = "log.properties";

    public static final String LOGFILE_TAG_NAME = "logfile";


    //  private final String DEFAULT_LOG_FILE_NAME = "./logs/logtext.log";

    private static LogWriter logWriter;

    private PrintWriter writer;

    private static String logFileName;


//    private LogWriter() throws LogException {
//        this.init();
//    }

    private LogWriter(String fileName) throws LogException {
        this.logFileName = fileName;
        this.init();
    }

    /**
     * Get LogWriter Singleton Instance
     *
     * @return
     * @throws LogException
     */
    public synchronized static LogWriter getLogWriter() throws LogException {
        if (logWriter == null) {
            logWriter = new LogWriter(logFileName);
        }
        return logWriter;
    }

    public synchronized static LogWriter getLogWriter(String logFileName) throws LogException {
        if (logWriter == null) {
            logWriter = new LogWriter(logFileName);
        }
        return logWriter;
    }


    public synchronized void log(String logMsg) {
        this.writer.println(new java.util.Date() + ": " + logMsg);
    }


    public synchronized void log(Exception ex) {
        writer.println(new java.util.Date() + ": ");
        ex.printStackTrace(writer);
    }

    /**
     * Init LogWriter
     *
     * @throws LogException
     */
    private void init() throws LogException {
        if (this.logFileName == null) {
            this.logFileName = this.getLogFileNameFromConfigFile();

        }
        File logFile = new File(this.logFileName);
        try {

            writer = new PrintWriter(new FileWriter(logFile, true), true);
            System.out.println("file location：" + logFile.getAbsolutePath());
        } catch (IOException ex) {
            String errmsg = "open error:" + logFile.getAbsolutePath();
            //  System.out.println(errmsg);
            throw new LogException(errmsg, ex);
        }
    }


    private String getLogFileNameFromConfigFile() {
        try {
            Properties pro = new Properties();
            InputStream fin = getClass().getResourceAsStream(LOG_CONFIGFILE_NAME);
            if (fin != null) {
                pro.load(fin);
                fin.close();
                return pro.getProperty(LOGFILE_TAG_NAME);
            } else {
                System.err.println("open error: log.properties");
            }
        } catch (IOException ex) {
            System.err.println("open error: log.properties");
        }
        return null;
    }

    public void close() {
        logWriter = null;
        if (writer != null) {
            writer.close();
        }
    }




    class LogException extends Exception {
        public LogException() {
            super();
        }

        public LogException(String msg) {
            super(msg);
        }

        public LogException(String msg, Throwable cause) {
            super(msg, cause);
        }

        public LogException(Throwable cause) {
            super(cause);
        }
    }

}



