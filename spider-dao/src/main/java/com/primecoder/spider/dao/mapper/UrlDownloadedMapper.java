package com.primecoder.spider.dao.mapper;

import com.primecoder.spider.dao.MyMapper;
import com.primecoder.spider.dao.entity.UrlDownloadedEntity;
import org.springframework.stereotype.Component;

/**
 * Created by primecoder on 2017/8/19.
 * E-mail : aprimecoder@gmail.com
 */
@Component
public interface UrlDownloadedMapper extends MyMapper<UrlDownloadedEntity>{


    void add(UrlDownloadedEntity urlDownloadedEntity);

}
