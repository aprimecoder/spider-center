package com.primecoder.spider.controller.core.tag;

import com.primecoder.spider.dao.entity.BloggerTagEntity;
import com.primecoder.spider.download.task.CategoryDownloadTask;
import com.primecoder.spider.util.task.TaskExecute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by primecoder on 2017/8/20.
 * E-mail : aprimecoder@gmail.com
 */
@Component
public class BloggerCategoryListEntityHandler {


    @Autowired
    private TaskExecute taskExecute;

    public void handler(List<BloggerTagEntity> bloggerTagEntities) {

        if (null == bloggerTagEntities) {
            return;
        }

        for (BloggerTagEntity bloggerTagEntity : bloggerTagEntities) {

            int tagCount = Integer.valueOf(bloggerTagEntity.getTagCount());
            if (tagCount > 0) {

                CategoryDownloadTask categoryDownloadTask = new CategoryDownloadTask(bloggerTagEntity);
                taskExecute.submit(categoryDownloadTask);
            }
        }
    }
}
