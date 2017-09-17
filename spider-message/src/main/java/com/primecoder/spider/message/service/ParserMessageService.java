package com.primecoder.spider.message.service;

import com.primecoder.spider.dao.entity.ParserMessageEntity;
import com.primecoder.spider.dao.mapper.ParserMessageMapper;
import com.primecoder.spider.message.bean.ParserCategoryBean;
import com.primecoder.spider.message.bean.ParserCategoryListBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by primecoder on 2017/9/17.
 * E-mail : aprimecoder@gmail.com
 */
@Component
public class ParserMessageService {


    @Autowired
    private ParserMessageMapper parserMessageMapper;

    public void insertCategoryList(ParserCategoryListBean parserCategoryListBean,boolean isHandler) {

        ParserMessageEntity parserMessageEntity = new ParserMessageEntity();
        parserMessageEntity.setRequestId(parserCategoryListBean.getRequestId());
        parserMessageEntity.setUrlType(parserCategoryListBean.getUrlType().getType());
        parserMessageEntity.setBloggerName(parserCategoryListBean.getBloggerName());
        parserMessageEntity.setFilePath(parserCategoryListBean.getFilePath());
        parserMessageEntity.setHandler(isHandler);
        parserMessageMapper.insert(parserMessageEntity);
    }

    public void insertCategory(ParserCategoryBean parserCategoryBean,boolean isHandler) {

        ParserMessageEntity parserMessageEntity = new ParserMessageEntity();
        parserMessageEntity.setRequestId(parserCategoryBean.getRequestId());
        parserMessageEntity.setUrlType(parserCategoryBean.getUrlType().getType());
        parserMessageEntity.setBloggerName(parserCategoryBean.getBloggerName());
        parserMessageEntity.setFilePath(parserCategoryBean.getTagPath());
        parserMessageEntity.setTagId(parserCategoryBean.getTagId());
        parserMessageEntity.setTagName(parserCategoryBean.getTagName());
        parserMessageEntity.setHandler(isHandler);
        parserMessageMapper.insert(parserMessageEntity);
    }
}
