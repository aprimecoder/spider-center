package com.primecoder.spider.parser;

import com.primecoder.spider.dao.entity.BloggerPageEntity;
import com.primecoder.spider.dao.mapper.BloggerPageMapper;
import com.primecoder.spider.util.UuidGenerate;
import com.primecoder.spider.util.constant.Constant;
import com.primecoder.spider.util.context.ContextHolder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * Created by primecoder on 2017/8/20.
 * E-mail : aprimecoder@gmail.com
 */
@Component
public class ParserBloggerCategoryPage {


    private static final Logger LOGGER = LoggerFactory.getLogger(ParserBloggerCategoryPage.class);

    @Autowired
    private UuidGenerate uuidGenerate;

    @Autowired
    private BloggerPageMapper bloggerPageMapper;

    public void parser(String categoryPath, String bloggerName, String tagId, int type, String tagName) {

        File file = new File(categoryPath);

        Document doc;

        try {
            doc = Jsoup.parse(file, "UTF-8", "");

            if (null != doc) {

                Elements bloggerPageClasses = doc.getElementsByClass("entrylistItem");
                for (Element bloggerPageClass : bloggerPageClasses) {

                    String bloggerPageUrl;

                    try {
                        bloggerPageUrl = bloggerPageClass.childNode(1).childNode(0).attr("href");
                    } catch (IndexOutOfBoundsException e) {
                        LOGGER.error("parser category error! file : {}",file.getAbsolutePath());
                        continue;
                    }

                    int urlCnt = bloggerPageMapper.existByUrl(bloggerPageUrl);
                    if (urlCnt > 0) {
                        continue;
                    }

                    String readText = "";

                    try {

                        readText = bloggerPageClass.childNode(5).childNode(2).outerHtml();

                    } catch (IndexOutOfBoundsException e) {

                        LOGGER.error("parser category error! file : {}",file.getAbsolutePath());
                    }

                    int readCount = getCatagoryCount(readText);

                    if (readCount == -1) {
                        LOGGER.error("parser category error! file : {}",file.getAbsolutePath());
                    }

                    String commentText = "";
                    int commentCount = -1 ;
                    try {
                        commentText = bloggerPageClass.childNode(5).childNode(3).childNode(0).outerHtml();
                        commentCount = getCatagoryCount(commentText);
                    } catch (IndexOutOfBoundsException e) {

                        LOGGER.error("parser category error! file : {}",file.getAbsolutePath());
                    }

                    BloggerPageEntity bloggerPageEntity = new BloggerPageEntity();
                    bloggerPageEntity.setPageId(uuidGenerate.generate());
                    bloggerPageEntity.setTagId(tagId);
                    bloggerPageEntity.setBloggerName(bloggerName);
                    bloggerPageEntity.setCommentCount(commentCount);
                    bloggerPageEntity.setReadCount(readCount);
                    bloggerPageEntity.setRecommendCount(-1);
                    bloggerPageEntity.setBloggerPageUrl(bloggerPageUrl);

                    bloggerPageMapper.add(bloggerPageEntity);

                }
            }
        } catch (IOException e) {
            LOGGER.error("parser tag page : {} error : {}",file.getAbsolutePath(),e.getMessage());
        }
    }

    private int getCatagoryCount(String text) {

        int leftIndex = text.lastIndexOf("(");
        int rightIndex = text.lastIndexOf(")");

        int cnt = -1;

        try {

            cnt = Integer.valueOf(text.substring(leftIndex + 1,rightIndex));

        } catch (StringIndexOutOfBoundsException e) {

            LOGGER.error("get category count error! text is : {}",text);
        } catch (NumberFormatException e) {
            LOGGER.error("get category count error! text is : {}",text);
        }

        return cnt;
    }
}
