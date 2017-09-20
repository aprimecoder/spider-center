package com.primecoder.spider.message.service;

import com.primecoder.spider.dao.entity.ParserMessageEntity;
import com.primecoder.spider.dao.mapper.ParserMessageMapper;
import com.primecoder.spider.message.bean.*;
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

    public int insertCategoryList(ParserCategoryListBean parserCategoryListBean,boolean isHandler) {

        ParserMessageEntity parserMessageEntity = new ParserMessageEntity();
        parserMessageEntity.setRequestId(parserCategoryListBean.getRequestId());
        parserMessageEntity.setUrlType(parserCategoryListBean.getUrlType().getType());
        parserMessageEntity.setBloggerName(parserCategoryListBean.getBloggerName());
        parserMessageEntity.setFilePath(parserCategoryListBean.getFilePath());
        parserMessageEntity.setHandler(isHandler);
        parserMessageMapper.insert(parserMessageEntity);

        return parserMessageEntity.getId();
    }

    public int insertCategory(ParserCategoryBean parserCategoryBean,boolean isHandler) {

        ParserMessageEntity parserMessageEntity = new ParserMessageEntity();
        parserMessageEntity.setRequestId(parserCategoryBean.getRequestId());
        parserMessageEntity.setUrlType(parserCategoryBean.getUrlType().getType());
        parserMessageEntity.setBloggerName(parserCategoryBean.getBloggerName());
        parserMessageEntity.setFilePath(parserCategoryBean.getTagPath());
        parserMessageEntity.setTagId(parserCategoryBean.getTagId());
        parserMessageEntity.setTagName(parserCategoryBean.getTagName());
        parserMessageEntity.setHandler(isHandler);
        parserMessageMapper.insert(parserMessageEntity);

        return parserMessageEntity.getId();
    }

    public int insertTagList(ParserTagListBean parserTagListBean,boolean isHandler) {

        ParserMessageEntity parserMessageEntity = new ParserMessageEntity();
        parserMessageEntity.setRequestId(parserTagListBean.getRequestId());
        parserMessageEntity.setUrlType(parserTagListBean.getUrlType().getType());
        parserMessageEntity.setBloggerName(parserTagListBean.getBloggerName());
        parserMessageEntity.setFilePath(parserTagListBean.getFilePath());
        parserMessageEntity.setHandler(isHandler);
        parserMessageMapper.insert(parserMessageEntity);

        return parserMessageEntity.getId();
    }

    public int insertTagIndex(ParserTagIndexBean parserTagIndexBean,boolean isHandler) {

        ParserMessageEntity parserMessageEntity = new ParserMessageEntity();
        parserMessageEntity.setRequestId(parserTagIndexBean.getRequestId());
        parserMessageEntity.setUrlType(parserTagIndexBean.getUrlType().getType());
        parserMessageEntity.setBloggerName(parserTagIndexBean.getBloggerName());
        parserMessageEntity.setFilePath(parserTagIndexBean.getTagPath());
        parserMessageEntity.setTagId(parserTagIndexBean.getTagId());
        parserMessageEntity.setTagName(parserTagIndexBean.getTagName());
        parserMessageEntity.setHandler(isHandler);

        parserMessageMapper.insert(parserMessageEntity);

        return parserMessageEntity.getId();
    }

    public int insertTag(ParserTagBean parserTagBean,boolean isHandler) {

        ParserMessageEntity parserMessageEntity = new ParserMessageEntity();
        parserMessageEntity.setRequestId(parserTagBean.getRequestId());
        parserMessageEntity.setUrlType(parserTagBean.getUrlType().getType());
        parserMessageEntity.setBloggerName(parserTagBean.getBloggerName());
        parserMessageEntity.setFilePath(parserTagBean.getTagFilePath());
        parserMessageEntity.setTagId(parserTagBean.getTagId());
        parserMessageEntity.setHandler(isHandler);

        parserMessageMapper.insert(parserMessageEntity);

        return parserMessageEntity.getId();
    }

    public int insertError(ParserBean parserBean) {

        ParserMessageEntity parserMessageEntity = new ParserMessageEntity();
        parserMessageEntity.setRequestId(parserBean.getRequestId());
        parserMessageEntity.setUrlType(parserBean.getUrlType().getType());
        parserMessageEntity.setHandler(true);

        parserMessageMapper.insert(parserMessageEntity);

        return parserMessageEntity.getId();
    }

    public void updateHandler(int id) {

        ParserMessageEntity parserMessageEntity = new ParserMessageEntity();
        parserMessageEntity.setId(id);
        parserMessageEntity.setHandler(true);

        parserMessageMapper.updateByPrimaryKeySelective(parserMessageEntity);
    }
}
