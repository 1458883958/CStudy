package com.example.wudelin.cstudy.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by wudelin on 2017/11/24.
 *
 * 处理网络请求的工具类
 */

public class HttpUtil {

    public static void sendOkHttpRequest(final String address,okhttp3.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}
