package com.vit.updateplugin;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

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

    private UpdatePlug(Builder builder) {
        this.method = builder.method == null ? "GET" : builder.method;
        this.url = builder.url == null ? "" : builder.url;
        this.headers = builder.headers == null ? new HashMap<String, String>(16) : builder.headers;
        this.params = builder.params == null ? new HashMap<String, String>(16) : builder.params;
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
    }

    /**
     * 用于构建一个 UpdatePlug 对象
     */
    public static class Builder {

        private String method;
        private String url;
        private Map<String, String> params;
        private Map<String, String> headers;

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

        public UpdatePlug create() {
            return new UpdatePlug(this);
        }

    }

}
