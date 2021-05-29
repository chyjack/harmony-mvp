package com.chy.demo.mvp.utils;

import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Author:CaiHuangyu
 * Description:提供线程相关处理的类
 * Date:2017-10-26
 */
public class ThreadUtil {

    private static final String TAG = "ThreadUtil";
    private volatile static ExecutorService mThreadPool;
    private volatile static EventHandler uiHandler;

    private static Map<Object, List<Runnable>> uiTasks = new ConcurrentHashMap<>();
    private static Map<Object, Map<Runnable, Future<?>>> subTasks = new ConcurrentHashMap<>();

    /**
     * 主线程异步执行
     */
    public static void postOnUiThread(Object token, Runnable runnable) {
        postDelayOnUiThread(token, runnable, 0);
    }

    /**
     * 主线程延时执行
     */
    public static void postDelayOnUiThread(Object token, Runnable runnable, long delayMillis) {
        if (uiHandler == null) {
            synchronized (ThreadUtil.class) {
                if (uiHandler == null) {
                    uiHandler = new EventHandler(EventRunner.getMainEventRunner());
                }
            }
        }
        uiHandler.postTask(runnable, delayMillis);
        addUiRunnable(token, runnable);
    }

    /**
     * 提供不依赖Activity Context的runOnUiThread方法
     */
    public static void runOnUiThread(Object token, Runnable runnable) {
        if (EventRunner.getMainEventRunner().isCurrentRunnerThread()) {
            runnable.run();
        } else {
            if (uiHandler == null) {
                synchronized (ThreadUtil.class) {
                    if (uiHandler == null) {
                        uiHandler = new EventHandler(EventRunner.getMainEventRunner());
                    }
                }
            }
            uiHandler.postTask(runnable);
            addUiRunnable(token, runnable);
        }
    }

    /**
     * 提供线程管理的子线程调用方法
     */
    public static void runOnSubThread(Object token, Runnable runnable) {
        if (EventRunner.getMainEventRunner().isCurrentRunnerThread()) {
            if (mThreadPool == null) {
                synchronized (ThreadUtil.class) {
                    if (mThreadPool == null) {
                        mThreadPool = Executors.newCachedThreadPool();
                    }
                }
            }
            Future<?> future = mThreadPool.submit(runnable);
            addSubRunnable(token, runnable, future);
        } else {
            runnable.run();
        }
    }

    public static void postSubThread(Object token, Runnable runnable) {
        if (mThreadPool == null) {
            synchronized (ThreadUtil.class) {
                if (mThreadPool == null) {
                    mThreadPool = Executors.newCachedThreadPool();
                }
            }
        }
        Future<?> future = mThreadPool.submit(runnable);
        addSubRunnable(token, runnable, future);
    }

    private static void addUiRunnable(Object token, Runnable runnable) {
        if (token == null) {
            return;
        }
        synchronized (ThreadUtil.class) {
            List<Runnable> uiRunnableList = uiTasks.get(token);
            if (uiRunnableList == null) {
                uiRunnableList = new ArrayList<>();
            }
            uiRunnableList.add(runnable);
            uiTasks.put(token, uiRunnableList);
        }
    }

    private static void addSubRunnable(Object token, Runnable runnable, Future<?> future) {
        if (token == null) {
            return;
        }
        synchronized (ThreadUtil.class) {
            Map<Runnable, Future<?>> subRunnableMap = subTasks.get(token);
            if (subRunnableMap == null) {
                subRunnableMap = new HashMap<>();
            }
            subRunnableMap.put(runnable, future);
            subTasks.put(token, subRunnableMap);
        }
    }

    public static void removeUiRunnable(Runnable r) {
        synchronized (ThreadUtil.class) {
            if (uiHandler != null) {
                for (List<Runnable> uiRunnableList : uiTasks.values()) {
                    for (Runnable runnable : uiRunnableList) {
                        if (r == runnable) {
                            uiHandler.removeTask(runnable);
                        }
                    }
                }
            }
        }
    }

    public static void removeSubThreadRunnable(Runnable r) {
        synchronized (ThreadUtil.class) {
            for (Map<Runnable, Future<?>> subRunnableMap : subTasks.values()) {
                for (Runnable runnable : subRunnableMap.keySet()) {
                    if (r == runnable) {
                        Future<?> future = subRunnableMap.get(runnable);
                        if (future != null && !future.isDone()) {
                            future.cancel(true);
                        }
                    }
                }
            }
        }
    }

    public static void removeRunnable(Object token) {
        synchronized (ThreadUtil.class) {
            List<Runnable> uiRunnableList;
            if (uiHandler != null && (uiRunnableList = uiTasks.remove(token)) != null) {
                for (Runnable runnable : uiRunnableList) {
                    uiHandler.removeTask(runnable);
                    Logger.d(TAG + ":移除未执行的主线程任务");
                }
            }
            Map<Runnable, Future<?>> subRunnableMap = subTasks.remove(token);
            if (subRunnableMap != null) {
                for (Runnable r : subRunnableMap.keySet()) {
                    Future<?> future = subRunnableMap.get(r);
                    if (future != null && !future.isDone()) {
                        future.cancel(true);
                        Logger.d(TAG + ":终止线程任务");
                    }
                }
            }
        }
    }
}