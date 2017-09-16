package com.primecoder.spider.parser;

import com.alibaba.fastjson.JSONObject;
import com.primecoder.spider.util.constant.Constant;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * Created by primecoder on 2017/8/20.
 * E-mail : aprimecoder@gmail.com
 */
@Component
public class ParserBloggerTagIndex {


    private static final Logger LOGGER = LoggerFactory.getLogger(ParserBloggerTagIndex.class);



    public JSONObject parser(String tagIndexPath, String bloggerName, String tagId, int type, String tagName) {

        File file = new File(tagIndexPath);

        Document doc;

        try {
            doc = Jsoup.parse(file, "UTF-8", "");

            if (null != doc) {

                Elements pageClasses = doc.getElementsByClass("Pager");

                String text;

                try {

                    text = pageClasses.get(0).childNode(0).childNode(0).outerHtml();

                } catch (IndexOutOfBoundsException e) {

                    LOGGER.error("parser file : {} tag error!",file.getAbsolutePath());

                    return null;
                }

                if (null == text) {

                    LOGGER.error("parser file : {} tag error!",file.getAbsolutePath());

                    return null;
                }

                int pageCnt = getPageCnt(text);

                LOGGER.info("tag type : {} page count : {}",type,pageCnt);

                if (pageCnt > 0) {
                    JSONObject obj = new JSONObject();
                    obj.put("bloggerName",bloggerName);
                    obj.put("preUrl","http://www.cnblogs.com/" + bloggerName + "/tag/"
                            + tagName + "/default.html?page=");
                    obj.put("prePath",Constant.BLOGGER_BASE_PATH + bloggerName + "\\" + tagName + "\\tag-page");
                    obj.put("pageCnt",pageCnt);
                    obj.put("tagId",tagId);

                    return obj;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private int getPageCnt(String text) {

        int leftIndex = text.indexOf("共");
        int rightIndex = text.indexOf("页");
        int pageCnt = 0;

        try {

            pageCnt = Integer.valueOf(text.substring(leftIndex + 1,rightIndex));

        } catch (StringIndexOutOfBoundsException e) {

            LOGGER.error(e.getMessage());
        }

        return pageCnt;
    }
}
