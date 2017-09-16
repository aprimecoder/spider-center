package spider.dao.mapper;

import com.primecoder.spider.dao.mapper.BloggerInfoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by primecoder on 2017/8/19.
 * E-mail : aprimecoder@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestBloggerInfoMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestBloggerInfoMapper.class);

    @Autowired
    private BloggerInfoMapper bloggerInfoMapper;

    @Test
    public void testSelectBloggerNameBySize() {

        List<String> bloggerNames = bloggerInfoMapper.selectBloggerNameBySize(10);

        LOGGER.info(bloggerNames.toString());
    }
}
