package com.marshalchen.common.commonUtils.logUtils;



import java.io.*;
import java.util.Properties;


public class LogWriter {

    public static final String LOG_CONFIGFILE_NAME = "log.properties";

    public static final String LOGFILE_TAG_NAME = "logfile";


  //  private final String DEFAULT_LOG_FILE_NAME = "./logs/logtext.log";
    private final String DEFAULT_LOG_FILE_NAME = "/sdcard/qingdaonews/nfclog.log";

    private static LogWriter logWriter;

    private PrintWriter writer;

    private String logFileName;


    private LogWriter() throws LogException{
        this.init();
    }
    private LogWriter(String fileName) throws LogException{
        this.logFileName = fileName;
        this.init();
    }
    /**
     * Get LogWriter Singleton Instance
     * @return
     * @throws LogException
     */
    public synchronized static LogWriter getLogWriter()throws LogException{
        if (logWriter == null){
            logWriter = new LogWriter();
        }
        return logWriter;
    }
    public synchronized static LogWriter getLogWriter(String logFileName)throws LogException{
        if (logWriter == null){
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
     * 初始化LogWriter
     * @throws LogException
     */
    private void init() throws LogException{
        //如果用户没有在参数中指定日志文件名，则从配置文件中获取。
        if (this.logFileName == null){
            this.logFileName = this.getLogFileNameFromConfigFile();
            //如果配置文件不存在或者也没有指定日志文件名，则用默认的日志文件名。
            if (this.logFileName == null){
                this.logFileName = DEFAULT_LOG_FILE_NAME;
            }
        }
        File logFile = new File(this.logFileName);
        try {
            //    其中的FileWriter()中的第二个参数的含义是:是否在文件中追加内容
            //  PrintWriter()中的第二个参数的含义是：自动将数据flush到文件中
            writer = new PrintWriter(new FileWriter(logFile, true), true);
            System.out.println("file location：" + logFile.getAbsolutePath());
        } catch (IOException ex) {
            String errmsg = "open error:" + logFile.getAbsolutePath();
            //  System.out.println(errmsg);
            throw new LogException(errmsg, ex);
        }
    }
    /**
     * 从配置文件中取日志文件名
     * @return
     */
    private String getLogFileNameFromConfigFile() {
        try {
            Properties pro = new Properties();
            //在类的当前位置,查找属性配置文件log.properties
            InputStream fin = getClass().getResourceAsStream(LOG_CONFIGFILE_NAME);
            if (fin != null){
                pro.load(fin);//载入配置文件
                fin.close();
                return pro.getProperty(LOGFILE_TAG_NAME);
            } else {
                System.err.println("open error: log.properties" );
            }
        }catch (IOException ex) {
            System.err.println("open error: log.properties" );
        }
        return null;
    }

    public void close() {
        logWriter = null;
        if (writer != null){
            writer.close();
        }
    }

//    public static void main(String[] args) {
//        LogWriter logger = null;
//        try {
//            String fileName = "C:/temp/temp0/logger.log";
//            logger = LogWriter.getLogWriter(fileName);
//            logger.log("First log!");
//            logger.log("log");
//            logger.log("Third log");
//            logger.log("log");
//            logger.close();
//            //ReadFromFile.readFileByLines(fileName);
//        } catch (LogException e) {
//            e.printStackTrace();
//        }
//    }


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



