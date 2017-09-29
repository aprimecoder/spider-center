package spider.init;

import com.primecoder.spider.Application;
import com.primecoder.spider.message.init.InitMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by primecoder on 2017/9/20.
 * E-mail : aprimecoder@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class TestInitMessageService {

    @Autowired
    private InitMessageService initMessageService;


    @Test
    public void test() {

        initMessageService.initIntoDB();
    }
}
