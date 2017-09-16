package com.primecoder.spider.message.activemq;

import com.primecoder.spider.message.core.IMessageSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

/**
 * Created by primecoder on 2017/8/19.
 * E-mail : aprimecoder@gmail.com
 */
@Component
public class ActiveMqMessageSend implements IMessageSend{


    @Autowired
    private Queue messageQueue;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void send(String message) {

        jmsTemplate.convertAndSend(messageQueue, message);
    }
}
