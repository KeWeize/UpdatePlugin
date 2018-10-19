package com.vit.updateplugin.check;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * <p> <p/>
 *
 * @author kewz
 */

public class DefaultApiCheck extends AbstractApiChecker {

    @Override
    protected String check(String url, String method, Map<String, String> headers,
                           Map<String, String> params) throws IOException {
        HttpURLConnection conn = null;
        if (method.equalsIgnoreCase("POST")) {
            conn = createPostRequest(url, headers, params);
        } else {
            conn = createGetRequest(url, headers, params);
        }
        int responseConde = conn.getResponseCode();
        if (responseConde < 200 || responseConde >= 300) {
            conn.disconnect();
            // error
        }
        BufferedReader bis = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

        StringBuilder sb = new StringBuilder();
        String lines;
        while ((lines = bis.readLine()) != null) {
            sb.append(lines);
        }

        conn.disconnect();
        return sb.toString();
    }

    /**
     * 生成一个 POST 的 HttpURLConnection
     *
     * @param url
     * @param headers
     * @param params
     * @return
     * @throws IOException
     */
    private HttpURLConnection createPostRequest(String url, Map<String, String> headers,
                                                Map<String, String> params) throws IOException {
        URL mPostUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) mPostUrl.openConnection();
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setConnectTimeout(10 * 1000);
        conn.setRequestMethod("POST");
        inflateHeaders(headers, conn);
        String paramsStr = createParamsString(params);
        conn.getOutputStream().write(paramsStr.getBytes("utf-8"));
        return conn;
    }

    /**
     * 生成一个 GET 的 HttpURLConnection
     *
     * @param url
     * @param params
     * @return
     */
    private HttpURLConnection createGetRequest(String url, Map<String, String> headers,
                                               Map<String, String> params) throws IOException {
        StringBuilder sb = new StringBuilder(url);
        if (params != null && params.size() > 0) {
            sb.append("?");
            sb.append(createParamsString(params));
        }
        URL mGetUrl = new URL(sb.toString());
        HttpURLConnection conn = (HttpURLConnection) mGetUrl.openConnection();
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setConnectTimeout(10 * 1000);
        conn.setRequestMethod("GET");
        inflateHeaders(headers, conn);
        conn.connect();
        return conn;
    }

    /**
     * 添加 headers
     *
     * @param headers
     * @param connection
     */
    private void inflateHeaders(Map<String, String> headers, HttpURLConnection connection) {
        Set<String> keys = headers.keySet();
        for (String key : keys) {
            connection.setRequestProperty(key, headers.get(key));
        }
    }

    /**
     * 根据 Map 生成 params 字符串
     *
     * @param params
     * @return
     */
    private String createParamsString(Map<String, String> params) {
        if (params == null || params.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String key : params.keySet()) {
            sb.append(key).append("=").append(params.get(key)).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

}
