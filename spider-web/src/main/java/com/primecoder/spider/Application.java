package com.primecoder.spider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by primecoder on 2017/8/19.
 * E-mail : aprimecoder@gmail.com
 */
@SpringBootApplication(
        scanBasePackages = {
                "com.primecoder.spider"})
@MapperScan("com.primecoder.spider.dao.mapper")
public class Application {


    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

        //ContextHolder.setContext(applicationContext);
    }
}
