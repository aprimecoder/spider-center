package com.primecoder.spider.controller.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.primecoder.spider.controller.core.tag.BloggerCategoryListEntityHandler;
import com.primecoder.spider.controller.core.tag.BloggerTagListEntityHandler;
import com.primecoder.spider.controller.core.tag.BloggerTagPagesHandler;
import com.primecoder.spider.dao.entity.BloggerTagEntity;
import com.primecoder.spider.message.bean.*;
import com.primecoder.spider.parser.*;
import com.primecoder.spider.util.constant.Constant;
import com.primecoder.spider.util.constant.UrlType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by primecoder on 2017/8/19.
 * E-mail : aprimecoder@gmail.com
 */
@Component
public class MessageHandler {


    private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandler.class);

    @Autowired
    private ParserBloggerCategoryListPage parserBloggerCategoryListPage;

    @Autowired
    private BloggerCategoryListEntityHandler bloggerCategoryListEntityHandler;

    @Autowired
    private BloggerTagListEntityHandler bloggerTagListEntityHandler;

    @Autowired
    private ParserBloggerCategoryPage parserBloggerCategoryPage;

    @Autowired
    private ParserBloggerTagListPage parserBloggerTagListPage;

    @Autowired
    private ParserBloggerTagIndex parserBloggerTagIndex;

    @Autowired
    private BloggerTagPagesHandler bloggerTagPagesHandler;

    @Autowired
    private ParserBloggerTagPage parserBloggerTagPage;

    @JmsListener(destination = Constant.QUEUE_NAME)
    public void receive(String message) {

        LOGGER.info("receive a message : {}",message);

        ParserBean parserBean = JSON.parseObject(message,ParserBean.class);
        handler(parserBean);
    }

    private void handler(ParserBean parserBean) {

        UrlType urlType = parserBean.getUrlType();

        switch (urlType) {

            case CATEGORYLIST :

                ParserCategoryListBean parserCategoryListBean = (ParserCategoryListBean)parserBean;

                List<BloggerTagEntity> bloggerCategoryEntities
                        = parserBloggerCategoryListPage.parser(parserCategoryListBean.getFilePath(), parserCategoryListBean.getBloggerName());
                bloggerCategoryListEntityHandler.handler(bloggerCategoryEntities);
                break;

            case CATEGORY :
                ParserCategoryBean parserCategoryBean = (ParserCategoryBean)parserBean;
                parserBloggerCategoryPage.parser(parserCategoryBean.getTagPath(),parserCategoryBean.getBloggerName(),
                        parserCategoryBean.getTagId(),urlType.getType(),parserCategoryBean.getTagName());

                break;
            case TAGLIST:
                ParserTagListBean parserTagListBean = (ParserTagListBean)parserBean;
                List<BloggerTagEntity> bloggerTagEntities
                        = parserBloggerTagListPage.parser(parserTagListBean.getFilePath(),parserTagListBean.getBloggerName());
                bloggerTagListEntityHandler.handler(bloggerTagEntities);
                break;

            case TAGINDEX :
                ParserTagIndexBean parserTagIndexBean = (ParserTagIndexBean)parserBean;
                JSONObject pageObj = parserBloggerTagIndex.parser(parserTagIndexBean.getTagPath(),parserTagIndexBean.getBloggerName(),
                        parserTagIndexBean.getTagId(),parserTagIndexBean.getUrlType().getType(),parserTagIndexBean.getTagName());
                bloggerTagPagesHandler.handler(pageObj);
                break;
            case TAG :
                ParserTagBean parserTagBean = (ParserTagBean)parserBean;
                parserBloggerTagPage.parser(parserTagBean.getTagFilePath(),parserTagBean.getBloggerName(),parserTagBean.getTagId());
        }
    }
}
