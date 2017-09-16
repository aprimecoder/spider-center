package com.primecoder.spider.message.bean;

import com.primecoder.spider.util.constant.UrlType;

/**
 * Created by primecoder on 2017/8/19.
 * E-mail : aprimecoder@gmail.com
 */
public class ParserCategoryListBean extends ParserBean{

    private String filePath;

    private String bloggerName;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getBloggerName() {
        return bloggerName;
    }

    public void setBloggerName(String bloggerName) {
        this.bloggerName = bloggerName;
    }
}
