package com.primecoder.spider.parser;

import com.primecoder.spider.dao.entity.BloggerTagEntity;
import com.primecoder.spider.dao.mapper.BloggerTagMapper;
import com.primecoder.spider.util.UuidGenerate;
import com.primecoder.spider.util.constant.UrlType;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by primecoder on 2017/8/19.
 * E-mail : aprimecoder@gmail.com
 */
@Component
public class ParserBloggerCategoryListPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParserBloggerCategoryListPage.class);

    @Autowired
    private BloggerTagMapper bloggerTagMapper;

    @Autowired
    private UuidGenerate uuidGenerate;

    public List<BloggerTagEntity> parser(String categoryListPath,String bloggerName) {

        File file = new File(categoryListPath);

        Document doc;

        List<BloggerTagEntity> categoryList = new ArrayList<>();

        try {
            doc = Jsoup.parse(file, "UTF-8", "");
            Elements es = doc.getElementsByTag("li");

            for (Element e : es) {

                String url = e.childNode(0).attr("href");

                LOGGER.info("bloggerName : {} tag url : {}",bloggerName,url);

                if ((url != null) && url.contains("category")) {

                    String text = e.childNode(0).childNode(0).outerHtml();

                    Map<String,String> tagMap = getTagName(text);

                    BloggerTagEntity bloggerTagEntity = new BloggerTagEntity();

                    bloggerTagEntity.setTagId(uuidGenerate.generate());

                    bloggerTagEntity.setBloggerName(bloggerName);
                    bloggerTagEntity.setTagCount(tagMap.get("count"));
                    bloggerTagEntity.setTagName(tagMap.get("name"));

                    bloggerTagEntity.setTagUrl(url);

                    bloggerTagEntity.setTagType(UrlType.CATEGORY.getType());

                    bloggerTagMapper.add(bloggerTagEntity);

                    categoryList.add(bloggerTagEntity);
                }
            }
        } catch (IOException e) {

            LOGGER.info("parser file : {} category error : {}!",file.getAbsolutePath(),e.getMessage());
        }


        return categoryList;

    }

    private Map<String,String> getTagName(String text) {

        //01 Java 基础语法(12)
        int rightIndex = text.lastIndexOf("(");
        int rightIndex0 = text.lastIndexOf(")");

        if (rightIndex < 0) {
            Map<String,String> tagMap = new HashMap<>();
            tagMap.put("name",text);
            tagMap.put("count","0");
            return tagMap;
        }

        LOGGER.info(text);

        String name = null;
        String count = null;

        try {

            name = text.substring(0,rightIndex);
            count = text.substring(rightIndex + 1,rightIndex0);

        } catch (StringIndexOutOfBoundsException e) {

            LOGGER.error("parser category name error!text : {}",text);

        }

        if (name == null || count == null) {
            Map<String,String> tagMap = new HashMap<>();
            tagMap.put("name",text);
            tagMap.put("count","0");
            return tagMap;
        }


        try {
            Integer.valueOf(count);
        } catch (NumberFormatException e) {
            Map<String,String> tagMap = new HashMap<>();
            tagMap.put("name",text);
            tagMap.put("count","0");
            return tagMap;
        }


        Map<String,String> tagMap = new HashMap<>();
        tagMap.put("name",name);
        tagMap.put("count",count);

        return tagMap;
    }
}
