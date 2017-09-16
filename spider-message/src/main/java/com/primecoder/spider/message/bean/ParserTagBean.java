package com.primecoder.spider.message.bean;

/**
 * Created by primecoder on 2017/8/22.
 * E-mail : aprimecoder@gmail.com
 */
public class ParserTagBean extends ParserBean{

    private String tagFilePath;

    private String tagId;

    private String bloggerName;

    public String getTagFilePath() {
        return tagFilePath;
    }

    public void setTagFilePath(String tagFilePath) {
        this.tagFilePath = tagFilePath;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getBloggerName() {
        return bloggerName;
    }

    public void setBloggerName(String bloggerName) {
        this.bloggerName = bloggerName;
    }
}
