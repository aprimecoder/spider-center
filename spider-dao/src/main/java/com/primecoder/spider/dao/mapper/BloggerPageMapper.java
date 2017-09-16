package com.primecoder.spider.dao.mapper;

import com.primecoder.spider.dao.MyMapper;
import com.primecoder.spider.dao.entity.BloggerPageEntity;

/**
 * Created by primecoder on 2017/8/20.
 * E-mail : aprimecoder@gmail.com
 */
public interface BloggerPageMapper extends MyMapper<BloggerPageEntity> {


    int existByUrl(String url);

    void add(BloggerPageEntity bloggerPageEntity);
}
