package com.primecoder.spider.message.init;

import com.primecoder.spider.dao.entity.ParserMessageEntity;
import com.primecoder.spider.dao.mapper.BloggerInfoMapper;
import com.primecoder.spider.dao.mapper.ParserMessageMapper;
import com.primecoder.spider.message.bean.ParserInitBean;
import com.primecoder.spider.util.MyThreadLocal;
import com.primecoder.spider.util.UuidGenerate;
import com.primecoder.spider.util.constant.UrlType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by primecoder on 2017/9/20.
 * E-mail : aprimecoder@gmail.com
 */
@Component
public class InitMessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitMessageService.class);

    @Autowired
    private BloggerInfoMapper bloggerInfoMapper;

    @Autowired
    private ParserMessageMapper parserMessageMapper;

    @Autowired
    private UuidGenerate uuidGenerate;

    public void init() {


    }

    public void initIntoDB() {

        List<String> bloggerNameList = bloggerInfoMapper.selectAllBloggerName();
        LOGGER.info("RequestId : {} unDownload blogger size : {}",
                MyThreadLocal.getRequestId(),bloggerNameList.size());

        int initSize = 0;

        for (String bloggerName : bloggerNameList) {

            initSize++;

            LOGGER.info("**********" + initSize);

            int cnt = parserMessageMapper.countBloggerInitMessage(bloggerName);
            if (cnt != 0) {
                continue;
            }

            ParserMessageEntity parserMessageEntity = new ParserMessageEntity();
            parserMessageEntity.setBloggerName(bloggerName);
            parserMessageEntity.setHandler(false);
            parserMessageEntity.setUrlType(UrlType.INIT.getType());
            parserMessageEntity.setRequestId(uuidGenerate.generate());

            parserMessageMapper.insert(parserMessageEntity);
        }
    }

}
