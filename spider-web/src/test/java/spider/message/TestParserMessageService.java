package spider.message;

import com.primecoder.spider.Application;
import com.primecoder.spider.message.service.ParserMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by primecoder on 2017/9/17.
 * E-mail : aprimecoder@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class TestParserMessageService {

    @Autowired
    private ParserMessageService parserMessageService;


    @Test
    public void test() {

        parserMessageService.updateHandler(94);
    }
}
