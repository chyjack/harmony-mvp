package com.chy.demo.mvp.utils;

import com.chy.demo.mvp.BuildConfig;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

/**
 * Author: CaiHuangyu
 * Description: 日志输出工具
 * Date: 2021-03-14
 */
public class Logger {

    private static final String TAG_LOG = "sjmy";
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.LOG_APP, 0, TAG_LOG);
    private static final String LOG_FORMAT = "%{public}s: %{public}s";

    private Logger() {}

    public static void i(String msg) {
        i(TAG_LOG, msg);
    }

    public static void i(String tag, String msg) {
        HiLog.info(LABEL_LOG, LOG_FORMAT, tag, msg);
    }

    public static void d(String msg) {
        if (BuildConfig.DEBUG) {
            d(TAG_LOG, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            HiLog.debug(LABEL_LOG, LOG_FORMAT, tag, msg);
        }
    }

    public static void e(String msg) {
        e(TAG_LOG, msg);
    }

    public static void e(String tag, String msg) {
        HiLog.error(LABEL_LOG, LOG_FORMAT, tag, msg);
    }

    public static void e(Throwable e, String msg) {
        String stackTrace = HiLog.getStackTrace(e);
        String logFormat = "%{public}s\n\t%{public}s";
        HiLog.error(LABEL_LOG, logFormat, stackTrace, msg);
    }
}
