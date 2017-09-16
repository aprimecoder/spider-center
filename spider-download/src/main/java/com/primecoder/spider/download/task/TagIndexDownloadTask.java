package com.primecoder.spider.download.task;

import com.alibaba.fastjson.JSON;
import com.primecoder.core.util.dir.DirMgr;
import com.primecoder.spider.dao.entity.BloggerTagEntity;
import com.primecoder.spider.dao.entity.UrlDownloadedEntity;
import com.primecoder.spider.dao.mapper.UrlDownloadedMapper;
import com.primecoder.spider.download.core.HttpClientDownload;
import com.primecoder.spider.message.bean.ParserTagIndexBean;
import com.primecoder.spider.message.core.IMessageSend;
import com.primecoder.spider.storage.core.Storage;
import com.primecoder.spider.util.UuidGenerate;
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
public class TagIndexDownloadTask implements ITask{

    private static final Logger LOGGER = LoggerFactory.getLogger(TagIndexDownloadTask.class);

    private HttpClientDownload httpClientDownload;

    private UrlDownloadedMapper urlDownloadedMapper;

    private Storage storage;

    private IMessageSend iMessageSend;

    private UuidGenerate uuidGenerate;

    private BloggerTagEntity bloggerTagEntity;

    public TagIndexDownloadTask(BloggerTagEntity bloggerTagEntity) {

        this.bloggerTagEntity = bloggerTagEntity;

        urlDownloadedMapper = (UrlDownloadedMapper) ContextHolder.getBean(Constant.BeanName.URL_DOWNLOADED_MAPPER);
        httpClientDownload = (HttpClientDownload) ContextHolder.getBean(Constant.BeanName.HTTP_CLIENT_DOWNLOAD);
        storage = (Storage)ContextHolder.getBean(Constant.BeanName.STORAGE);
        iMessageSend = (IMessageSend) ContextHolder.getBean(Constant.BeanName.ACTIVE_MQ_MESSAGE_SEND);
        uuidGenerate = (UuidGenerate) ContextHolder.getBean(Constant.BeanName.UUID_GENERATE);
    }

    @Override
    public Object call() throws Exception {

        String content = httpClientDownload.download(bloggerTagEntity.getTagUrl());

        if (null == content) {
            return null;
        }

        String tagName = bloggerTagEntity.getTagName();
        String filePath = Constant.BLOGGER_BASE_PATH + bloggerTagEntity.getBloggerName() + "\\" + tagName;
        DirMgr.mkdir(filePath);

        String tagPath = filePath + "\\tag-page.html";

        storage.storage(tagPath,content);

        String path = tagPath.replaceAll("\\\\","|");

        UrlDownloadedEntity urlDownloadedEntity = new UrlDownloadedEntity();
        urlDownloadedEntity.setPageId(uuidGenerate.generate());
        urlDownloadedEntity.setBloggerName(bloggerTagEntity.getBloggerName());
        urlDownloadedEntity.setType(UrlType.TAG.getType());
        urlDownloadedEntity.setFilepath(path);
        urlDownloadedEntity.setUrl(bloggerTagEntity.getTagUrl());
        urlDownloadedMapper.add(urlDownloadedEntity);

        return tagPath;

    }

    @Override
    public void onSuccess(Object result) {

        if (null == result) {
            return;
        }

        ParserTagIndexBean parserTagIndexBean = new ParserTagIndexBean();
        parserTagIndexBean.setTagPath((String)result);
        parserTagIndexBean.setUrlType(UrlType.TAGINDEX);
        parserTagIndexBean.setTagName(bloggerTagEntity.getTagName());
        parserTagIndexBean.setTagId(bloggerTagEntity.getTagId());
        parserTagIndexBean.setBloggerName(bloggerTagEntity.getBloggerName());

        iMessageSend.send(JSON.toJSONString(parserTagIndexBean));
    }

    @Override
    public void onFailure(Throwable t) {

    }
}
