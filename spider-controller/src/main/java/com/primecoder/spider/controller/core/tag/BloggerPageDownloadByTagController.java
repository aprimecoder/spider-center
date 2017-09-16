package com.primecoder.spider.controller.core.tag;

import com.primecoder.spider.download.task.TagListDownloadTask;
import com.primecoder.spider.util.constant.Constant;
import com.primecoder.spider.dao.mapper.BloggerInfoMapper;
import com.primecoder.spider.util.task.TaskExecute;
import com.primecoder.spider.download.task.CategoryListDownloadTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by primecoder on 2017/8/17.
 * E-mail : aprimecoder@gmail.com
 */
@Component
public class BloggerPageDownloadByTagController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BloggerPageDownloadByTagController.class);


    @Autowired
    private BloggerInfoMapper bloggerInfoMapper;

    @Autowired
    private TaskExecute taskExecute;


    public void start() {

        List<String> bloggerNameList = bloggerInfoMapper.selectBloggerNameBySize(1);
        LOGGER.info("get bloggerName ：{} from database!",bloggerNameList);

        for (String bloggerName : bloggerNameList) {
            downloadCategoryList(bloggerName);
            downloadTagList(bloggerName);

            bloggerInfoMapper.setDownloaded(bloggerName);
        }

    }


    private void downloadCategoryList(String bloggerName) {

        String categoryListUrl = Constant.URL_PRE + bloggerName + "/mvc/blog/sidecolumn.aspx?blogApp=" + bloggerName;
        String filePath = Constant.BLOGGER_BASE_PATH + bloggerName + "\\category-list-page.html";

        CategoryListDownloadTask categoryListDownloadTask = new CategoryListDownloadTask(categoryListUrl,bloggerName,filePath);
        taskExecute.submit(categoryListDownloadTask);
    }

    private void downloadTagList(String bloggerName) {

        String tagListUrl = Constant.URL_PRE + bloggerName + "/tag/";
        String tagListPath = Constant.BLOGGER_BASE_PATH + bloggerName + "\\tag-list-page.html";

        TagListDownloadTask tagListDownloadTask = new TagListDownloadTask(bloggerName,tagListUrl,tagListPath);
        taskExecute.submit(tagListDownloadTask);
    }

}
