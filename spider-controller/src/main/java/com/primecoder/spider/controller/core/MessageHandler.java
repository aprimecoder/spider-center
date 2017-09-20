package com.primecoder.spider.controller.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.primecoder.spider.controller.core.tag.BloggerCategoryListEntityHandler;
import com.primecoder.spider.controller.core.tag.BloggerTagListEntityHandler;
import com.primecoder.spider.controller.core.tag.BloggerTagPagesHandler;
import com.primecoder.spider.dao.entity.BloggerTagEntity;
import com.primecoder.spider.dao.entity.ParserMessageEntity;
import com.primecoder.spider.dao.mapper.ParserMessageMapper;
import com.primecoder.spider.message.bean.*;
import com.primecoder.spider.message.service.ParserMessageService;
import com.primecoder.spider.parser.*;
import com.primecoder.spider.util.MyThreadLocal;
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

    @Autowired
    private ParserMessageService parserMessageService;

    @JmsListener(destination = Constant.QUEUE_NAME)
    public void receive(String message) {

        LOGGER.info("receive a message : {}",message);

        ParserBean parserBean = JSON.parseObject(message,ParserBean.class);

        MyThreadLocal.setRequestId(parserBean.getRequestId());

        handler(parserBean,message);
    }

    private void handler(ParserBean parserBean,String message) {

        UrlType urlType = parserBean.getUrlType();
        int messageId;

        switch (urlType) {

            case CATEGORYLIST :

                ParserCategoryListBean parserCategoryListBean = JSON.parseObject(message,ParserCategoryListBean.class);

                messageId = parserMessageService.insertCategoryList(parserCategoryListBean,false);

                List<BloggerTagEntity> bloggerCategoryEntities
                        = parserBloggerCategoryListPage.parser(parserCategoryListBean.getFilePath(), parserCategoryListBean.getBloggerName());
                bloggerCategoryListEntityHandler.handler(bloggerCategoryEntities);

                parserMessageService.updateHandler(messageId);

                break;

            case CATEGORY :
                ParserCategoryBean parserCategoryBean = JSON.parseObject(message,ParserCategoryBean.class);

                messageId = parserMessageService.insertCategory(parserCategoryBean,false);

                parserBloggerCategoryPage.parser(parserCategoryBean.getTagPath(),parserCategoryBean.getBloggerName(),
                        parserCategoryBean.getTagId(),urlType.getType(),parserCategoryBean.getTagName());

                parserMessageService.updateHandler(messageId);

                break;
            case TAGLIST:
                ParserTagListBean parserTagListBean = JSON.parseObject(message,ParserTagListBean.class);

                messageId = parserMessageService.insertTagList(parserTagListBean,false);

                List<BloggerTagEntity> bloggerTagEntities
                        = parserBloggerTagListPage.parser(parserTagListBean.getFilePath(),parserTagListBean.getBloggerName());
                bloggerTagListEntityHandler.handler(bloggerTagEntities);

                parserMessageService.updateHandler(messageId);

                break;

            case TAGINDEX :
                ParserTagIndexBean parserTagIndexBean = JSON.parseObject(message,ParserTagIndexBean.class);

                messageId = parserMessageService.insertTagIndex(parserTagIndexBean,false);

                JSONObject pageObj = parserBloggerTagIndex.parser(parserTagIndexBean.getTagPath(),parserTagIndexBean.getBloggerName(),
                        parserTagIndexBean.getTagId(),parserTagIndexBean.getUrlType().getType(),parserTagIndexBean.getTagName());
                bloggerTagPagesHandler.handler(pageObj);

                parserMessageService.updateHandler(messageId);

                break;
            case TAG :
                ParserTagBean parserTagBean = JSON.parseObject(message,ParserTagBean.class);

                messageId = parserMessageService.insertTag(parserTagBean,false);

                parserBloggerTagPage.parser(parserTagBean.getTagFilePath(),parserTagBean.getBloggerName(),parserTagBean.getTagId());

                parserMessageService.updateHandler(messageId);

                break;
            default:
                parserBean.setUrlType(UrlType.ERROR);
                parserMessageService.insertError(parserBean);
        }
    }
}
