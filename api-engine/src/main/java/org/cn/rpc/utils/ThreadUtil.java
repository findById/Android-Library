package org.cn.rpc.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadUtil {

    private static final int CORE_POOL_SIZE = 1;
    private static int MAX_POOL_SIZE = 10;

    private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
        private final ThreadFactory defaultFactory = Executors.defaultThreadFactory();
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = defaultFactory.newThread(r);
            if (!thread.isDaemon()) {
                thread.setDaemon(true);
            }
            thread.setName("TASK-THREAD-" + threadNumber.getAndIncrement());
            return thread;
        }
    };

    public static ExecutorService newExecutorService() {
        return new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, 0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<Runnable>(), THREAD_FACTORY);
    }

    public static void setMaxPoolSize(int maxPoolSize) {
        MAX_POOL_SIZE = maxPoolSize;
    }

    private static ExecutorService instance;

    public static ExecutorService getThreadPool() {
        synchronized (ThreadUtil.class) {
            if (instance == null) {
                instance = newExecutorService();
            }
            return instance;
        }
    }

    public static void shutdown() {
        if (instance != null) {
            instance.shutdownNow();
        }
    }

}
