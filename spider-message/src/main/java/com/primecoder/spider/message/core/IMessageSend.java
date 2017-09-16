package com.primecoder.spider.message.core;

import org.springframework.stereotype.Component;

/**
 * Created by primecoder on 2017/8/17.
 * E-mail : aprimecoder@gmail.com
 */
@Component
public interface IMessageSend {


    void send(String message);
}
