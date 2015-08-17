package com.aiven.seafox.controller.log;

import android.util.Log;


import com.aiven.seafox.controller.log.action.LogRecodeUtils;
import com.aiven.seafox.controller.log.model.LogMode;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author Aiven
 * @date 2014-6-3  下午6:12:53
 * @email aiven163@sina.com
 * @Description 日志记录输出工具
 */
public class Logs {

    /**
     * 输出异常信息
     *
     * @param e
     * @Description
     * @author Aiven
     */
    public static void logE(Exception e) {
        if (e == null)
            return;
        if (LogConfig.Debug) {
            e.printStackTrace();
            if (LogConfig.recodeAble) {
                Writer writer = new StringWriter();
                PrintWriter printWriter = new PrintWriter(writer);
                e.printStackTrace(printWriter);
                Throwable cause = e.getCause();
                while (cause != null) {
                    cause.printStackTrace(printWriter);
                    cause = cause.getCause();
                }
                printWriter.close();
                String result = writer.toString();
                writer = null;
                LogRecodeUtils.getInstance().addLog(new LogMode("ERROR", result));
            }
        }
    }

    /**
     * 通过Logcat输出日志信息
     *
     * @param tag
     * @param msg
     */
    public static void logError(String tag, String msg) {
        if (tag == null) {
            tag = LogConfig.DEFAULT_TAG;
        }
        if (msg == null)
            msg = "null";
        if (LogConfig.Debug) {
            Log.e(tag, msg);
            if (LogConfig.recodeAble) {
                LogRecodeUtils.getInstance().addLog(new LogMode(tag, msg));
            }
        }
    }

    /**
     * 通过Logcat输出日志信息
     *
     * @param tag
     * @param msg
     * @Description
     * @author Aiven
     */
    public static void logI(String tag, String msg) {
        if (tag == null) {
            tag = LogConfig.DEFAULT_TAG;
        }
        if (msg == null)
            msg = "null";
        if (LogConfig.Debug) {
            Log.i(tag, msg);
            if (LogConfig.recodeAble) {
                LogRecodeUtils.getInstance().addLog(new LogMode(tag, msg));
            }
        }
    }

    /**
     * 通过Logcat输出日志信息
     *
     * @param tag
     * @param msg
     * @Description
     * @author Aiven
     */
    public static void logV(String tag, String msg) {
        if (tag == null) {
            tag = LogConfig.DEFAULT_TAG;
        }
        if (msg == null)
            msg = "null";
        if (LogConfig.Debug) {
            Log.v(tag, msg);
            if (LogConfig.recodeAble) {
                LogRecodeUtils.getInstance().addLog(new LogMode(tag, msg));
            }
        }
    }

    /**
     * 通过Logcat输出日志信息
     *
     * @param tag
     * @param msg
     * @Description
     * @author Aiven
     */
    public static void logW(String tag, String msg) {
        if (tag == null) {
            tag = LogConfig.DEFAULT_TAG;
        }
        if (msg == null)
            msg = "null";
        if (LogConfig.Debug) {
            Log.w(tag, msg);
            if (LogConfig.recodeAble) {
                LogRecodeUtils.getInstance().addLog(new LogMode(tag, msg));
            }
        }
    }

    /**
     * 通过Logcat输出日志信息
     *
     * @param tag
     * @param msg
     * @Description
     * @author Aiven
     */
    public static void logD(String tag, String msg) {
        if (tag == null) {
            tag = LogConfig.DEFAULT_TAG;
        }
        if (msg == null)
            msg = "null";
        if (LogConfig.Debug) {
            Log.d(tag, msg);
            if (LogConfig.recodeAble) {
                LogRecodeUtils.getInstance().addLog(new LogMode(tag, msg));
            }
        }
    }

    /**
     * 通过打印模式输出日志
     *
     * @param tag
     * @param msg
     * @Description
     * @author Aiven
     */
    public static void logPrint(String tag, String msg) {
        if (tag == null) {
            tag = LogConfig.DEFAULT_TAG;
        }
        if (msg == null) {
            msg = "null";
        }
        if (LogConfig.Debug) {
            System.out.println(tag + "  " + msg);
            if (LogConfig.recodeAble) {
                LogRecodeUtils.getInstance().addLog(new LogMode(tag, msg));
            }
        }
    }

    /**
     * 通过打印模式输出日志
     *
     * @param tag
     * @param msg
     * @Description
     * @author Aiven
     */
    public static void logPrint(String tag, Object msg) {
        if (tag == null) {
            tag = LogConfig.DEFAULT_TAG;
        }
        if (msg == null) {
            msg = "null";
        }
        if (LogConfig.Debug) {
            System.out.println(tag + "  " + msg);
            if (LogConfig.recodeAble) {
                LogRecodeUtils.getInstance().addLog(new LogMode(tag, msg.toString()));
            }
        }
    }

    /**
     * 通过打印模式输出日志
     *
     * @param msg
     * @Description
     * @author Aiven
     */
    public static void logPrint(Object msg) {
        if (msg == null) {
            msg = "null";
        }
        if (LogConfig.Debug) {
            System.out.println(LogConfig.DEFAULT_TAG + "  " + msg);
            if (LogConfig.recodeAble) {
                LogRecodeUtils.getInstance().addLog(new LogMode(LogConfig.DEFAULT_TAG, msg.toString()));
            }
        }
    }

    public static void logCrash(String tag, String msg) {
        if (msg == null) {
            msg = "null";
        }
        if (LogConfig.Debug) {
            Log.e(LogConfig.DEFAULT_TAG, msg);
            if (LogConfig.recodeAble) {
                LogMode md = new LogMode(LogConfig.DEFAULT_TAG, msg.toString());
                md.setCrashInfo(true);
                LogRecodeUtils.getInstance().addLog(md);
            }
        }
    }

    public static void clearAllLogs() {
        String filePath = LogConfig.getLogSavePath();
        File file = new File(filePath);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (int j = 0; j < files.length; j++) {
                    files[j].delete();
                }
            }
        }
    }
}
