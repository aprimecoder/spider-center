package com.primecoder.spider.download.thread;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.primecoder.spider.util.task.SleepRejectedExecutionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * Created by primecoder on 2017/8/19.
 * E-mail : aprimecoder@gmail.com
 */
@Configuration
public class ThreadPoolConfig {


    @Value("${url.download.threadpool.coresize}")
    private int handlerCoreSize;

    @Value("${url.download.threadpool.maxsize}")
    private int handlerMaxSize;

    @Value("${url.download.threadpool.keepalive}")
    private long handlerKeepAlive;

    @Value("${url.download.threadpool.blockingqueue.size}")
    private int handlerQueueSize;


    @Bean
    public ExecutorService executorService() {

        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(1000);

        ExecutorService executorServive = new ThreadPoolExecutor(handlerCoreSize, handlerMaxSize, handlerKeepAlive,
                TimeUnit.MILLISECONDS, workQueue,new SleepRejectedExecutionHandler());

        return executorServive;
    }

    @Bean
    public ListeningExecutorService listeningExecutorService(ExecutorService executorService) {

        return MoreExecutors.listeningDecorator(executorService);
    }


}
