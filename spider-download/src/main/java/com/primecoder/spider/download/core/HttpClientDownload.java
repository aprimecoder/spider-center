package com.primecoder.spider.download.core;

import com.primecoder.spider.download.util.CookieMgr;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UTFDataFormatException;

/**
 * Created by primecoder on 2017/5/6.
 * E-mail : aprimecoder@gmail.com
 */
@Component
public class HttpClientDownload {


    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientDownload.class);

    @Autowired
    private CookieMgr cookieMgr;

    public String download(String url) {

        HttpClient httpClient = new DefaultHttpClient();

        if (url == null) {
            return null;
        }

        try {

            HttpGet httpGet = new HttpGet(url);

            httpGet.setHeader("Cookie",cookieMgr.getCookie());

            HttpResponse httpResponse;

            httpResponse = httpClient.execute(httpGet);

            LOGGER.info("download url:{} success",url);

            return EntityUtils.toString(httpResponse.getEntity());

        } catch(UTFDataFormatException e){

            LOGGER.error("download url:{} error,msg:{}",url,e.getMessage());

        } catch(IOException e) {

            LOGGER.error("download url:{} error,msg:{}",url,e.getMessage());

        } catch (IllegalStateException e) {

            LOGGER.error("download url:{} error,msg:{}",url,e.getMessage());
        } catch (IllegalArgumentException e) {

            LOGGER.error("download url:{} error,msg:{}",url,e.getMessage());
        }

        return null;

    }
}
