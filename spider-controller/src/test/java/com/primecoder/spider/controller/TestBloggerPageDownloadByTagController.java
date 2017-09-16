package com.primecoder.spider.controller;

import com.primecoder.spider.Application;
import com.primecoder.spider.controller.core.tag.BloggerPageDownloadByTagController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by primecoder on 2017/8/22.
 * E-mail : aprimecoder@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestBloggerPageDownloadByTagController {

    @Autowired
    private BloggerPageDownloadByTagController bloggerPageDownloadByTagController;

    @Test
    public void test() {

        bloggerPageDownloadByTagController.start();
    }
}
