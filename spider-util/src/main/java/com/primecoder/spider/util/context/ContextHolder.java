package com.primecoder.spider.util.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by primecoder on 2017/8/19.
 * E-mail : aprimecoder@gmail.com
 */
@Component
public class ContextHolder implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContextHolder.class);

    private static ApplicationContext applicationContext;

    public static Object getBean(String beanName) {

        return applicationContext.getBean(beanName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        if (ContextHolder.applicationContext == null) {
            ContextHolder.applicationContext = applicationContext;
        }

        LOGGER.info(ContextHolder.applicationContext.toString());
    }
}
