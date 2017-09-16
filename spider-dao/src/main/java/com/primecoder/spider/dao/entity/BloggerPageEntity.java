package com.primecoder.spider.dao.entity;

import javax.persistence.Table;

/**
 * Created by primecoder on 2017/8/20.
 * E-mail : aprimecoder@gmail.com
 */
@Table(name = "blogger_page_info")
public class BloggerPageEntity {


    private String pageId;

    private String bloggerName;

    private int readCount;

    private int commentCount;

    private int recommendCount;

    private String bloggerPageUrl;

    private String tagId;

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getBloggerName() {
        return bloggerName;
    }

    public void setBloggerName(String bloggerName) {
        this.bloggerName = bloggerName;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getRecommendCount() {
        return recommendCount;
    }

    public void setRecommendCount(int recommendCount) {
        this.recommendCount = recommendCount;
    }

    public String getBloggerPageUrl() {
        return bloggerPageUrl;
    }

    public void setBloggerPageUrl(String bloggerPageUrl) {
        this.bloggerPageUrl = bloggerPageUrl;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
}
