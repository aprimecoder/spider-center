package com.primecoder.spider.download.task;

import com.alibaba.fastjson.JSON;
import com.primecoder.spider.dao.entity.UrlDownloadedEntity;
import com.primecoder.spider.dao.mapper.UrlDownloadedMapper;
import com.primecoder.spider.download.core.HttpClientDownload;
import com.primecoder.spider.message.bean.ParserCategoryListBean;
import com.primecoder.spider.message.core.IMessageSend;
import com.primecoder.spider.storage.core.Storage;
import com.primecoder.spider.util.MyThreadLocal;
import com.primecoder.spider.util.constant.Constant;
import com.primecoder.spider.util.constant.UrlType;
import com.primecoder.spider.util.context.ContextHolder;
import com.primecoder.spider.util.task.ITask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by primecoder on 2017/8/17.
 * E-mail : aprimecoder@gmail.com
 */
public class CategoryListDownloadTask implements ITask{

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryListDownloadTask.class);

    private HttpClientDownload httpClientDownload;

    private UrlDownloadedMapper urlDownloadedMapper;

    private Storage storage;

    private IMessageSend iMessageSend;

    private String tagListUrl;

    private String bloggerName;

    private String filePath;

    private String requestId;

    public CategoryListDownloadTask(String tagListUrl, String bloggerName, String filePath) {

        this.tagListUrl = tagListUrl;
        this.bloggerName = bloggerName;
        this.filePath = filePath;

        urlDownloadedMapper = (UrlDownloadedMapper) ContextHolder.getBean(Constant.BeanName.URL_DOWNLOADED_MAPPER);
        httpClientDownload = (HttpClientDownload) ContextHolder.getBean(Constant.BeanName.HTTP_CLIENT_DOWNLOAD);
        storage = (Storage)ContextHolder.getBean(Constant.BeanName.STORAGE);
        iMessageSend = (IMessageSend) ContextHolder.getBean(Constant.BeanName.ACTIVE_MQ_MESSAGE_SEND);

        this.requestId = MyThreadLocal.getRequestId();
    }

    @Override
    public Object call() throws Exception {

        MyThreadLocal.setRequestId(this.requestId);

        String content = httpClientDownload.download(tagListUrl);

        if (null == content) {
            return null;
        }

        storage.storage(filePath,content);

        String path = filePath.replaceAll("\\\\","|");

        UrlDownloadedEntity urlDownloadedEntity = new UrlDownloadedEntity();
        urlDownloadedEntity.setBloggerName(bloggerName);
        urlDownloadedEntity.setType(UrlType.CATEGORYLIST.getType());
        urlDownloadedEntity.setFilepath(path);
        urlDownloadedEntity.setUrl(tagListUrl);
        urlDownloadedMapper.add(urlDownloadedEntity);

        LOGGER.info("download {} category list page!",bloggerName);

        return "success";
    }

    @Override
    public void onSuccess(Object result) {

        if (result == null) {
            LOGGER.error("download url : {} error!",tagListUrl);
            return;
        }

        LOGGER.info("download url : {} to path : {} success!",tagListUrl,filePath);

        //send to message queue
        ParserCategoryListBean parserCategoryListBean = new ParserCategoryListBean();
        parserCategoryListBean.setBloggerName(bloggerName);
        parserCategoryListBean.setFilePath(filePath);
        parserCategoryListBean.setUrlType(UrlType.CATEGORYLIST);

        parserCategoryListBean.setRequestId(MyThreadLocal.getRequestId());

        iMessageSend.send(JSON.toJSONString(parserCategoryListBean));
    }

    @Override
    public void onFailure(Throwable t) {

        LOGGER.error(t.getMessage());
    }
}
