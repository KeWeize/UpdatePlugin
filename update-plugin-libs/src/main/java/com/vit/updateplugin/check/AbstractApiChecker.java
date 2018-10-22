package com.vit.updateplugin.check;

import android.util.Log;

import com.vit.updateplugin.callback.OnCheckUpdateListener;
import com.vit.updateplugin.parse.AbstractParser;
import com.vit.updateplugin.parse.UpdateBean;
import com.vit.updateplugin.utils.UpdateUtils;

import java.util.Map;

/**
 * <p> <p/>
 *
 * @author kewz
 */

public abstract class AbstractApiChecker implements Runnable {

    private static String TAG
            = AbstractApiChecker.class.getSimpleName();

    private String url;
    private String method;
    private Map<String, String> headers;
    private Map<String, String> params;
    private AbstractParser mParser;
    private OnCheckUpdateListener mCheckUpdateListener;

    /**
     * 由UpdatePlug 注入请求信息时使用
     *
     * @param url
     * @param method
     * @param headers
     * @param params
     */
    public final void setCheckInfo(String url, String method, Map<String, String> headers, Map<String, String> params) {
        this.url = url;
        this.method = method;
        this.headers = headers;
        this.params = params;
    }

    /**
     * 由 Update 注入解析数据的解析器
     *
     * @param parser
     */
    public final void setParser(AbstractParser parser) {
        this.mParser = parser;
    }

    /**
     * 注入回调接口实例
     *
     * @param mCheckUpdateListener
     */
    public final void setOnCheckUpdateListener(OnCheckUpdateListener mCheckUpdateListener) {
        this.mCheckUpdateListener = mCheckUpdateListener;
    }

    @Override
    public void run() {
        try {
            if (url == null || method == null) {
                // 请求更新接口数据缺失
                throw new RuntimeException("request info missing, " +
                        "you must call the method setCheckInfo(...) to injection request info");
            }
            onResponse(check(url, method, headers, params));
        } catch (Throwable t) {
            // error
        }
    }

    /**
     * 由子类继承，用于请求更新接口获取更新数据
     *
     * @param url
     * @param method
     * @param headers
     * @param params
     * @return
     */
    protected abstract String check(String url, String method, Map<String, String> headers,
                                    Map<String, String> params) throws Exception;

    /**
     * 获取到更新接口，使用 Parser 解析数据
     *
     * @param response
     */
    public final void onResponse(String response) {
        if (mParser == null) {
            // 解析对象缺失
            throw new RuntimeException("parser missing," +
                    "you must call the method setParser(...) to injection parser");
        }
        UpdateBean mUpdate = mParser.parse(response);
        try {
            if (UpdateUtils.checkShouldUpdate(mUpdate)) {
                Log.d(TAG, "onResponse: need to update");
            } else {
                Log.d(TAG, "onResponse: need't to update");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "onResponse: ");
        }
    }

}
