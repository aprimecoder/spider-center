package com.primecoder.spider.download.task;

import com.alibaba.fastjson.JSON;
import com.primecoder.spider.dao.entity.UrlDownloadedEntity;
import com.primecoder.spider.dao.mapper.UrlDownloadedMapper;
import com.primecoder.spider.download.core.HttpClientDownload;
import com.primecoder.spider.message.bean.ParserTagListBean;
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
 * Created by primecoder on 2017/8/20.
 * E-mail : aprimecoder@gmail.com
 */
public class TagListDownloadTask implements ITask{


    private static final Logger LOGGER = LoggerFactory.getLogger(TagListDownloadTask.class);

    private HttpClientDownload httpClientDownload;

    private UrlDownloadedMapper urlDownloadedMapper;

    private Storage storage;

    private IMessageSend iMessageSend;


    private String tagListUrl;

    private String filePath;

    private String bloggerName;

    private String requestId;

    public TagListDownloadTask(String bloggerName,String tagListUrl,String filePath) {

        this.bloggerName = bloggerName;
        this.tagListUrl = tagListUrl;
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
        urlDownloadedEntity.setType(UrlType.TAGLIST.getType());
        urlDownloadedEntity.setFilepath(path);
        urlDownloadedEntity.setUrl(tagListUrl);
        urlDownloadedMapper.add(urlDownloadedEntity);

        LOGGER.info("download {} tag list page!",bloggerName);

        return "success";
    }

    @Override
    public void onSuccess(Object result) {

        if (result == null) {
            LOGGER.error("download url : {} error!",tagListUrl);
        }

        LOGGER.info("download url : {} to path : {} success!",tagListUrl,filePath);

        ParserTagListBean parserTagListBean = new ParserTagListBean();
        parserTagListBean.setFilePath(filePath);
        parserTagListBean.setBloggerName(bloggerName);
        parserTagListBean.setUrlType(UrlType.TAGLIST);

        parserTagListBean.setRequestId(MyThreadLocal.getRequestId());

        iMessageSend.send(JSON.toJSONString(parserTagListBean));
    }

    @Override
    public void onFailure(Throwable t) {

        LOGGER.error(t.getMessage());
    }
}
