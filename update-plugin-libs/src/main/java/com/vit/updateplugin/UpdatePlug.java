package com.vit.updateplugin;

import android.util.Log;

import com.vit.updateplugin.callback.OnCheckUpdateListener;
import com.vit.updateplugin.check.AbstractApiChecker;
import com.vit.updateplugin.check.DefaultApiCheck;
import com.vit.updateplugin.parse.AbstractParser;

import org.xml.sax.Parser;

import java.util.HashMap;
import java.util.Map;
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

public class UpdatePlug {

    private static String TAG
            = UpdatePlug.class.getSimpleName();

    private String method;
    private String url;
    private Map<String, String> params;
    private Map<String, String> headers;
    private Class<? extends AbstractApiChecker> mApiCheckerClazz;
    private Class<? extends AbstractParser> mParserClazz;
    private OnCheckUpdateListener mOnCheckUpdateListener;


    private UpdatePlug(Builder builder) {
        this.method = builder.method == null ? "GET" : builder.method;
        this.url = builder.url == null ? "" : builder.url;
        this.headers = builder.headers == null ? new HashMap<String, String>(16) : builder.headers;
        this.params = builder.params == null ? new HashMap<String, String>(16) : builder.params;
        this.mOnCheckUpdateListener = builder.mOnCheckUpdateListener;
        this.mApiCheckerClazz = builder.mApiCheckerClazz;
        this.mParserClazz = builder.mParserClazz;
    }

    public String getMethod() {
        if (method == null || method.length() == 0) {
            return "GET";
        }
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getParams() {
        if (params == null) {
            params = new HashMap<>(16);
        }
        return params;
    }

    public Map<String, String> getHeaders() {
        if (headers == null) {
            headers = new HashMap<>(16);
        }
        return headers;
    }

    public Class<? extends AbstractApiChecker> getApiCheckerClazz() {
        if (mApiCheckerClazz == null) {
            return DefaultApiCheck.class;
        }
        return mApiCheckerClazz;
    }

    public Class<? extends AbstractParser> getParserClazz() {
        if (mParserClazz == null) {
            // TODO: 2018/10/22 return default parser 
        }
        return mParserClazz;
    }

    public OnCheckUpdateListener getOnCheckUpdateListener() {
        if (mOnCheckUpdateListener == null) {
            // TODO: 2018/10/22 retrun default callback
        }
        return mOnCheckUpdateListener;
    }

    public void check() {
        // 开始检查更新
        System.out.println();
        Log.d(TAG, "update plug start check");
        Log.d(TAG, "========================= update info =======================");
        Log.d(TAG, "》 url: " + url);
        Log.d(TAG, "》 method: " + method);
        Log.d(TAG, "》 headers size: " + headers.size());
        Log.d(TAG, "》 params size: " + params.size());
        Log.d(TAG, "========================= update info =======================");
        System.out.println();

        Launcher.get()
                .launchCheck(this);

    }

    /**
     * 用于构建一个 UpdatePlug 对象
     */
    public static class Builder {

        private String method;
        private String url;
        private Map<String, String> params;
        private Map<String, String> headers;
        private Class<? extends AbstractApiChecker> mApiCheckerClazz;
        private Class<? extends AbstractParser> mParserClazz;
        private OnCheckUpdateListener mOnCheckUpdateListener;

        public Builder(String url) {
            this.url = url;
        }

        public Builder asGet() {
            this.method = "GET";
            return this;
        }

        public Builder asPost() {
            this.method = "POST";
            return this;
        }

        public Builder setParams(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public Builder addParams(String key, String value) {
            if (params == null) {
                params = new HashMap<>(16);
            }
            params.put(key, value);
            return this;
        }

        public Builder setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder addHeader(String key, String value) {
            if (headers == null) {
                headers = new HashMap<>(16);
            }
            headers.put(key, value);
            return this;
        }

        public Builder setApiCheckerClazz(Class<? extends AbstractApiChecker> mApiCheckerClazz) {
            this.mApiCheckerClazz = mApiCheckerClazz;
            return this;
        }

        public Builder setParserClazz(Class<? extends AbstractParser> mParserClazz) {
            this.mParserClazz = mParserClazz;
            return this;
        }

        public Builder setOnCheckUpdateListener(OnCheckUpdateListener mOnCheckUpdateListener) {
            this.mOnCheckUpdateListener = mOnCheckUpdateListener;
            return this;
        }

        public UpdatePlug create() {
            return new UpdatePlug(this);
        }

    }

}
