package com.primecoder.spider.download.thread;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadFactory;

/**
 * Created by primecoder on 2017/8/19.
 * E-mail : aprimecoder@gmail.com
 */
@Component
public class UrlDownloadedThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {

        return new UrlDownloadHandlerThread(r);
    }
}
