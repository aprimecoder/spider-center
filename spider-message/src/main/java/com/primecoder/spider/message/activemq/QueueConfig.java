package com.primecoder.spider.message.activemq;

import com.primecoder.spider.util.constant.Constant;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;

/**
 * Created by primecoder on 2017/8/19.
 * E-mail : aprimecoder@gmail.com
 */
@EnableJms
@Configuration
public class QueueConfig {

    @Bean
    public Queue messageQueue() {
        ActiveMQQueue queue = new ActiveMQQueue(Constant.QUEUE_NAME);
        return queue;
    }

}
