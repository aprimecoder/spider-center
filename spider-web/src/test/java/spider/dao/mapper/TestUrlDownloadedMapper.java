package spider.dao.mapper;

import com.primecoder.spider.dao.entity.UrlDownloadedEntity;
import com.primecoder.spider.dao.mapper.UrlDownloadedMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by primecoder on 2017/8/19.
 * E-mail : aprimecoder@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUrlDownloadedMapper {


    @Autowired
    private UrlDownloadedMapper urlDownloadedMapper;

    @Test
    public void testAdd() {

        UrlDownloadedEntity urlDownloadedEntity = new UrlDownloadedEntity();
        urlDownloadedEntity.setBloggerName("prime-test");
        urlDownloadedEntity.setFilepath("prime|test");
        urlDownloadedEntity.setType(1);
        urlDownloadedEntity.setUrl("www.prime.com");

        urlDownloadedMapper.add(urlDownloadedEntity);
    }
}
