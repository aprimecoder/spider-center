package com.primecoder.spider.dao.entity;

import javax.persistence.Table;

/**
 * Created by primecoder on 2017/9/16.
 * E-mail : aprimecoder@gmail.com
 */
@Table(name="blgger_parser_message")
public class ParserMessageEntity {

    private String requestId;

    private Integer urlType;

    private String bloggerName;

    private String tagId;

    private String tagName;

    private String filePath;

    private Boolean isHandler;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Integer getUrlType() {
        return urlType;
    }

    public void setUrlType(Integer urlType) {
        this.urlType = urlType;
    }

    public String getBloggerName() {
        return bloggerName;
    }

    public void setBloggerName(String bloggerName) {
        this.bloggerName = bloggerName;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Boolean getHandler() {
        return isHandler;
    }

    public void setHandler(Boolean handler) {
        isHandler = handler;
    }
}
