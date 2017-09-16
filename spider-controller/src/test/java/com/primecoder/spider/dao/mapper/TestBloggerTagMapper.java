package com.primecoder.spider.dao.mapper;

import com.primecoder.spider.dao.entity.BloggerTagEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by primecoder on 2017/8/19.
 * E-mail : aprimecoder@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestBloggerTagMapper {

    @Autowired
    private BloggerTagMapper bloggerTagMapper;

    @Test
    public void testAdd() {

        BloggerTagEntity bloggerTagEntity = new BloggerTagEntity();
        bloggerTagEntity.setBloggerName("12");
        bloggerTagEntity.setTagCount("123");
        bloggerTagEntity.setTagId("123");
        bloggerTagEntity.setTagName("123");
        bloggerTagEntity.setTagType(1);
        bloggerTagEntity.setTagUrl("www");

        bloggerTagMapper.add(bloggerTagEntity);
    }
}
