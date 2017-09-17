package com.primecoder.spider.endpoint;

import com.primecoder.spider.controller.core.tag.BloggerPageDownloadByTagController;
import com.primecoder.spider.util.MyThreadLocal;
import com.primecoder.spider.util.UuidGenerate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by primecoder on 2017/9/16.
 * E-mail : aprimecoder@gmail.com
 */
@RestController
public class SpiderTriggerApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpiderTriggerApi.class);


    @Autowired
    private BloggerPageDownloadByTagController bloggerPageDownloadByTagController;

    @Autowired
    private UuidGenerate uuidGenerate;

    @RequestMapping(value = "/trigger/tag",
            method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void startSpiderByTag(@RequestHeader(name = "X-Request-ID",required = false) String requestId) {

        if (null == requestId || "".equals(requestId)) {
            requestId = uuidGenerate.generate();
        }

        MyThreadLocal.setRequestId(requestId);

        bloggerPageDownloadByTagController.start();

        MyThreadLocal.removeRequestId();
    }
}
