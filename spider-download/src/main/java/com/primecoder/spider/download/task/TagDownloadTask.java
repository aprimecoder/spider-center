package com.primecoder.spider.download.task;

import com.alibaba.fastjson.JSON;
import com.primecoder.spider.dao.entity.UrlDownloadedEntity;
import com.primecoder.spider.dao.mapper.UrlDownloadedMapper;
import com.primecoder.spider.download.core.HttpClientDownload;
import com.primecoder.spider.message.bean.ParserTagBean;
import com.primecoder.spider.message.core.IMessageSend;
import com.primecoder.spider.storage.core.Storage;
import com.primecoder.spider.util.UuidGenerate;
import com.primecoder.spider.util.constant.Constant;
import com.primecoder.spider.util.constant.UrlType;
import com.primecoder.spider.util.context.ContextHolder;
import com.primecoder.spider.util.task.ITask;

/**
 * Created by primecoder on 2017/8/21.
 * E-mail : aprimecoder@gmail.com
 */
public class TagDownloadTask implements ITask{


    private HttpClientDownload httpClientDownload;

    private UrlDownloadedMapper urlDownloadedMapper;

    private Storage storage;

    private IMessageSend iMessageSend;

    private UuidGenerate uuidGenerate;

    private String url;

    private String path;

    private String bloggerName;

    private String tagId;

    public TagDownloadTask(String url,String path,String bloggerName,String tagId) {

        this.url = url;
        this.path = path;
        this.bloggerName = bloggerName;
        this.tagId = tagId;

        urlDownloadedMapper = (UrlDownloadedMapper) ContextHolder.getBean(Constant.BeanName.URL_DOWNLOADED_MAPPER);
        httpClientDownload = (HttpClientDownload) ContextHolder.getBean(Constant.BeanName.HTTP_CLIENT_DOWNLOAD);
        storage = (Storage)ContextHolder.getBean(Constant.BeanName.STORAGE);
        iMessageSend = (IMessageSend) ContextHolder.getBean(Constant.BeanName.ACTIVE_MQ_MESSAGE_SEND);
        uuidGenerate = (UuidGenerate) ContextHolder.getBean(Constant.BeanName.UUID_GENERATE);
    }

    @Override
    public Object call() throws Exception {

        String content = httpClientDownload.download(url);

        if (null == content) {
            return null;
        }

        storage.storage(path,content);

        UrlDownloadedEntity urlDownloadedEntity = new UrlDownloadedEntity();
        urlDownloadedEntity.setPageId(uuidGenerate.generate());
        urlDownloadedEntity.setBloggerName(bloggerName);
        urlDownloadedEntity.setType(UrlType.TAG.getType());
        urlDownloadedEntity.setFilepath(path.replaceAll("\\\\","|"));
        urlDownloadedEntity.setUrl(url);
        urlDownloadedMapper.add(urlDownloadedEntity);

        return path;
    }

    @Override
    public void onSuccess(Object result) {

        if (null == result) {
            return;
        }

        ParserTagBean parserTagBean = new ParserTagBean();
        parserTagBean.setBloggerName(bloggerName);
        parserTagBean.setTagId(tagId);
        parserTagBean.setUrlType(UrlType.TAG);
        parserTagBean.setTagFilePath(path);

        iMessageSend.send(JSON.toJSONString(parserTagBean));
    }

    @Override
    public void onFailure(Throwable t) {

    }
}
