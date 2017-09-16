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
import java.util.List;

/**
 * Created by primecoder on 2017/8/20.
 * E-mail : aprimecoder@gmail.com
 */
@Component
public class ParserBloggerTagListPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParserBloggerTagListPage.class);

    @Autowired
    private BloggerTagMapper bloggerTagMapper;

    @Autowired
    private UuidGenerate uuidGenerate;

    public List<BloggerTagEntity> parser(String tagListPath, String bloggerName) {

        File file = new File(tagListPath);

        Document doc;

        List<BloggerTagEntity> tagList = new ArrayList<>();

        try {
            doc = Jsoup.parse(file, "UTF-8", "");

            Elements links = doc.getElementsByTag("td");

            for (Element link : links) {

                if (link.childNodes().size() > 1) {

                    String tagText = "";
                    String tagUrl = "";
                    String countText = "";
                    String tagCount = "";
                    try {
                        tagText = link.childNode(1).childNode(0).outerHtml();
                        tagUrl = link.childNode(1).attr("href");
                        countText = link.childNode(2).childNode(0).outerHtml();
                        tagCount = countText.substring(1,countText.length() - 1);
                    } catch (IndexOutOfBoundsException e) {

                        continue;
                    }

                    BloggerTagEntity bloggerTagEntity = new BloggerTagEntity();
                    bloggerTagEntity.setTagId(uuidGenerate.generate());
                    bloggerTagEntity.setTagName(tagText);
                    bloggerTagEntity.setTagUrl(tagUrl);
                    bloggerTagEntity.setBloggerName(bloggerName);
                    bloggerTagEntity.setTagCount(tagCount);
                    bloggerTagEntity.setTagType(UrlType.TAG.getType());

                    bloggerTagMapper.add(bloggerTagEntity);

                    tagList.add(bloggerTagEntity);
                }

            }

        } catch (IOException e) {

            LOGGER.error("parser tag list page : {} error : {}",file.getAbsolutePath(),e.getMessage());
        }

        LOGGER.info("parser tag list page : {} success!",file.getAbsolutePath());

        return tagList;
    }
}
