package com.marshalchen.common.commonUtils.logUtils;



import java.io.*;
import java.util.Properties;

/**
 * 日志工具类
 * 使用了单例模式，保证只有一个实例。
 * 为了更方便的配置日志文件名，使用属性文件配置。
 * 也可以在程序中指定日志文件名。
 */
public class LogWriter {
    //  日志的配置文件
    public static final String LOG_CONFIGFILE_NAME = "log.properties";
    //  日志文件名在配置文件中的标签
    public static final String LOGFILE_TAG_NAME = "logfile";

    //    默认的日志文件的路径和文件名称
  //  private final String DEFAULT_LOG_FILE_NAME = "./logs/logtext.log";
    private final String DEFAULT_LOG_FILE_NAME = "/sdcard/qingdaonews/nfclog.log";
    //    该类的唯一的实例
    private static LogWriter logWriter;
    //  文件输出流
    private PrintWriter writer;
    //  日志文件名
    private String logFileName;
    /**
     * 默认构造函数
     */
    private LogWriter() throws LogException{
        this.init();
    }
    private LogWriter(String fileName) throws LogException{
        this.logFileName = fileName;
        this.init();
    }
    /**
     * 获取LogWriter的唯一实例。
     * @return
     * @throws com.marshalchen.common.commonUtils.logUtils.LogException
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

    /**
     * 往日志文件中写一条日志信息
     * 为了防止多线程同时操作(写)日志文件，造成文件”死锁”。使用synchronized关键字
     * @param logMsg    日志消息
     */
    public synchronized void log(String logMsg) {
        this.writer.println(new java.util.Date() + ": " + logMsg);
    }
    /**
     * 往日志文件中写一条异常信息
     * 使用synchronized关键字。
     * @param ex    待写入的异常
     */
    public synchronized void log(Exception ex) {
        writer.println(new java.util.Date() + ": ");
        ex.printStackTrace(writer);
    }

    /**
     * 初始化LogWriter
     * @throws com.marshalchen.common.commonUtils.logUtils.LogException
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
            System.out.println("日志文件的位置：" + logFile.getAbsolutePath());
        } catch (IOException ex) {
            String errmsg = "无法打开日志文件:" + logFile.getAbsolutePath();
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
                System.err.println("无法打开属性配置文件: log.properties" );
            }
        }catch (IOException ex) {
            System.err.println("无法打开属性配置文件: log.properties" );
        }
        return null;
    }
    //关闭LogWriter
    public void close() {
        logWriter = null;
        if (writer != null){
            writer.close();
        }
    }

    public static void main(String[] args) {
        LogWriter logger = null;
        try {
            String fileName = "C:/temp/temp0/logger.log";
            logger = LogWriter.getLogWriter(fileName);
            logger.log("First log!");
            logger.log("第二个日志信息");
            logger.log("Third log");
            logger.log("第四个日志信息");
            logger.close();
            //ReadFromFile.readFileByLines(fileName);
        } catch (LogException e) {
            e.printStackTrace();
        }
    }

    /**
     * Log操作过程中的异常
     */
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



