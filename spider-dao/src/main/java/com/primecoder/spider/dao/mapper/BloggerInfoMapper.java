package com.primecoder.spider.dao.mapper;

import com.primecoder.spider.dao.BloggerInfoDao;
import com.primecoder.spider.dao.MyMapper;
import com.primecoder.spider.dao.entity.BloggerInfoEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by primecoder on 2017/8/19.
 * E-mail : aprimecoder@gmail.com
 */
@Component
public interface BloggerInfoMapper extends MyMapper<BloggerInfoEntity>{

    List<String> selectBloggerNameBySize(int size);

    void setDownloaded(String bloggerName);

    int countUnDownloadBlogger();

    List<String> selectAllBloggerName();
}
