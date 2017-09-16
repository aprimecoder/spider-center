package com.primecoder.spider.storage.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Created by primecoder on 2017/5/6.
 * E-mail : aprimecoder@gmail.com
 */
@Component
public class Storage {

    private static final Logger LOGGER = LoggerFactory.getLogger(Storage.class);

    public void storage(String filePath,String content) {

        if (null == filePath || null == content) {

            LOGGER.error("filePath or content is null!");

            return;
        }

        File f = new File(filePath);

        FileOutputStream fos = null;

        try {

            if (!f.exists()) {
                f.createNewFile();

            }

            fos = new FileOutputStream(f);

            byte[] contentInBytes = content.getBytes();
            fos.write(contentInBytes);
            fos.flush();
            fos.close();

            LOGGER.info("storage to path:{}",filePath);

        } catch (UTFDataFormatException e){

            LOGGER.error("storage filePath:{} error,msg:{}",filePath,e.getMessage());

        } catch (FileNotFoundException e) {

            LOGGER.error("storage filePath:{} error,msg:{}",filePath,e.getMessage());

        } catch (IOException e) {

            LOGGER.error("storage filePath:{} error,msg:{}",filePath,e.getMessage());

        } finally {

            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    LOGGER.error("storage filePath:{} error,msg:{}",filePath,e.getMessage());
                }
            }

        }
    }
}
