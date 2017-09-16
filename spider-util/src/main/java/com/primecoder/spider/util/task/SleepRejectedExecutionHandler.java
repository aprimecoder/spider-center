package com.primecoder.spider.util.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by primecoder on 2017/8/17.
 * E-mail : aprimecoder@gmail.com
 */
public class SleepRejectedExecutionHandler implements RejectedExecutionHandler {


    private static final Logger LOGGER = LoggerFactory.getLogger(SleepRejectedExecutionHandler.class);

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

        LOGGER.info("###############reject task,current queue size :{}",String.valueOf(executor.getQueue().size()));

        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {

            LOGGER.error(e.getMessage());
        }
    }
}
