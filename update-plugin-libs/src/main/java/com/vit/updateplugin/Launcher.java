package com.vit.updateplugin;

import com.vit.updateplugin.check.AbstractApiChecker;
import com.vit.updateplugin.parse.AbstractParser;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p> <p/>
 *
 * @author kewz
 */
class Launcher {

    /**
     * 请求网络使用的线程池
     */
    private static final Executor THREAD_POOL_EXECUTOR;
    private static int CORE_POOL_SIZE = 1;
    private static int MAX_POOL_SIZE = CORE_POOL_SIZE * 2 + 1;
    private static int KEEP_ALIVE_SECONDS = 30;

    static {
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE_SECONDS, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(8),
                new ThreadFactory() {
                    private final AtomicInteger mCount = new AtomicInteger(1);

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
                    }
                });
    }

    private static Launcher mInstance
            = new Launcher();

    public static Launcher get() {
        return mInstance;
    }

    public void launchCheck(UpdatePlug mUpdateConfig) {
        try {
            Class<? extends AbstractApiChecker> mApiCheckerClazz
                    = mUpdateConfig.getApiCheckerClazz();

            AbstractApiChecker mCheck = mApiCheckerClazz.newInstance();
            mCheck.setCheckInfo(mUpdateConfig.getUrl(), mUpdateConfig.getMethod(),
                    mUpdateConfig.getHeaders(), mUpdateConfig.getParams());

            if (mUpdateConfig.getParserClazz() == null) {
                // 解析对象缺失
                throw new RuntimeException("parserClass missing," +
                        "you must call the method setParserClazz(...) to injection parserClazz");
            }
            mCheck.setParser(mUpdateConfig.getParserClazz().newInstance());
            mCheck.setOnCheckUpdateListener(mUpdateConfig.getOnCheckUpdateListener());

            THREAD_POOL_EXECUTOR.execute(mCheck);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
