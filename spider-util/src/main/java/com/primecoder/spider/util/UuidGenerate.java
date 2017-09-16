package com.primecoder.spider.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by primecoder on 2017/8/20.
 * E-mail : aprimecoder@gmail.com
 */
@Component
public class UuidGenerate {

    public String generate() {

        return UUID.randomUUID().toString();
    }
}
