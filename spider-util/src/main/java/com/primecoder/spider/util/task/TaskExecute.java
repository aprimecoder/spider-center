package com.primecoder.spider.util.task;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * Created by primecoder on 2017/7/12.
 * E-mail : aprimecoder@gmail.com
 */
@Component
public class TaskExecute {


    @Autowired
    private ListeningExecutorService listeningExecutorService;

    public void submit(ITask task) {

        ListenableFuture listenableFuture = listeningExecutorService.submit(task);

        Futures.addCallback(listenableFuture, task);

    }
}
