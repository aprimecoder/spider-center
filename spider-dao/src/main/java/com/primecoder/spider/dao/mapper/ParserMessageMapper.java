package com.primecoder.spider.dao.mapper;

import com.primecoder.spider.dao.MyMapper;
import com.primecoder.spider.dao.entity.ParserMessageEntity;
import org.springframework.stereotype.Component;

/**
 * Created by primecoder on 2017/9/17.
 * E-mail : aprimecoder@gmail.com
 */
@Component
public interface ParserMessageMapper extends MyMapper<ParserMessageEntity>{

    int countBloggerInitMessage(String bloggerName);
}
