package com.primecoder.spider.util;

/**
 * Created by primecoder on 2017/9/16.
 * E-mail : aprimecoder@gmail.com
 */
public class MyThreadLocal {

    private static ThreadLocal<String> requestThreadLocal = new ThreadLocal<>();


    public static void setRequestId(String requestId) {

        requestThreadLocal.set(requestId);
    }

    public static String getRequestId() {

        return requestThreadLocal.get();
    }

    public static void removeRequestId() {

        requestThreadLocal.remove();
    }
}
