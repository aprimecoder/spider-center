package com.primecoder.spider.util.task;

import com.google.common.util.concurrent.FutureCallback;

import java.util.concurrent.Callable;

/**
 * Created by primecoder on 2017/7/12.
 * E-mail : aprimecoder@gmail.com
 */
public interface ITask<T> extends Callable<T>, FutureCallback<T> {

    T call() throws Exception;

    void onSuccess(T result);

    void onFailure(Throwable t);
}
