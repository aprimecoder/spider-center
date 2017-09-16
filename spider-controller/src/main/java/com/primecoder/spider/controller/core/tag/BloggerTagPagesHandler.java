package com.primecoder.spider.controller.core.tag;

import com.alibaba.fastjson.JSONObject;
import com.primecoder.spider.download.task.TagDownloadTask;
import com.primecoder.spider.util.task.TaskExecute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by primecoder on 2017/8/21.
 * E-mail : aprimecoder@gmail.com
 */
@Component
public class BloggerTagPagesHandler {

    @Autowired
    private TaskExecute taskExecute;

    public void handler(JSONObject pageObj) {

        if (null != pageObj) {

            int pageCnt = pageObj.getInteger("pageCnt");
            for (int i = 0;i< pageCnt;i++) {
                String tagUrl = pageObj.getString("preUrl") + i;
                String tagPath = pageObj.getString("prePath") + i + ".html";
                String bloggerName = pageObj.getString("bloggerName");
                String tagId = pageObj.getString("tagId");

                TagDownloadTask tagDownloadTask = new TagDownloadTask(tagUrl,tagPath,bloggerName,tagId);
                taskExecute.submit(tagDownloadTask);
            }
        }
    }
}
