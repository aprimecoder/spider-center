package com.primecoder.spider.message.init;

import com.primecoder.spider.dao.mapper.BloggerInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by primecoder on 2017/9/20.
 * E-mail : aprimecoder@gmail.com
 */
@Component
public class GenerateMessage {


    @Autowired
    private BloggerInfoMapper bloggerInfoMapper;

    public void generateIntoDB() {

        List<String> bloggerNameList = bloggerInfoMapper.selectBloggerNameBySize(1);
    }
}
