package com.primecoder.spider.parser;

import com.primecoder.spider.dao.entity.BloggerPageEntity;
import com.primecoder.spider.dao.mapper.BloggerPageMapper;
import com.primecoder.spider.util.UuidGenerate;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by primecoder on 2017/8/21.
 * E-mail : aprimecoder@gmail.com
 */
@Component
public class ParserBloggerTagPage {


    private static final Logger LOGGER = LoggerFactory.getLogger(ParserBloggerTagPage.class);

    @Autowired
    private BloggerPageMapper bloggerPageMapper;

    @Autowired
    private UuidGenerate uuidGenerate;

    public void parser(String filePath,String bloggerName,String tagId) {

        Document tagDoc;

        try {
            tagDoc = Jsoup.parse(new File(filePath), "UTF-8", "");
        } catch (IOException e) {

            LOGGER.error("get path : {} doc error : {}",filePath,e.getMessage());
            return;
        }

        Elements bloggerPageClasses = tagDoc.getElementsByClass("postTitl2");

        for (Element bloggerPageClass : bloggerPageClasses) {
            String bloggerPageUrl = bloggerPageClass.childNode(0).attr("href");

            if (bloggerPageMapper.existByUrl(bloggerPageUrl) > 0) {
                continue;
            }

            String bloggerPageCnt = null;

            try {

                bloggerPageCnt = bloggerPageClass.childNode(2).childNode(0).outerHtml();

            } catch (IndexOutOfBoundsException e) {

                LOGGER.error("parser tag page error : {}!",e.getMessage());
                continue;
            }

            Map<String,Integer> cntMap = getTagCount(bloggerPageCnt);

            if (cntMap == null) {
                continue;
            }

            BloggerPageEntity bloggerPageEntity = new BloggerPageEntity();
            bloggerPageEntity.setPageId(uuidGenerate.generate());
            bloggerPageEntity.setTagId(tagId);
            bloggerPageEntity.setBloggerName(bloggerName);
            bloggerPageEntity.setCommentCount(cntMap.get("commentCnt"));
            bloggerPageEntity.setReadCount(cntMap.get("readCnt"));
            bloggerPageEntity.setRecommendCount(-1);
            bloggerPageEntity.setBloggerPageUrl(bloggerPageUrl);

            bloggerPageMapper.add(bloggerPageEntity);

        }

    }

    private Map<String,Integer> getTagCount(String text) {

        //Artech 2016-09-21 14:43 阅读:3637 评论:13
        int readLeft = text.indexOf("阅读:");

        try {

            String[] arr = text.substring(readLeft).split(":");
            int readCnt = Integer.valueOf(arr[1].substring(0,arr[1].indexOf(" ")));
            int commentCnt = Integer.valueOf(arr[2]);
            Map<String,Integer> cntMap = new HashMap<>();
            cntMap.put("readCnt",readCnt);
            cntMap.put("commentCnt",commentCnt);

            return cntMap;

        } catch (IndexOutOfBoundsException e) {

            LOGGER.error("get tag count error! text is : {}",text);

            return null;
        }

    }
}
