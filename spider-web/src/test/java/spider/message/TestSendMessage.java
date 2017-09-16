package spider.message;

import com.primecoder.spider.Application;
import com.primecoder.spider.message.core.IMessageSend;
import com.primecoder.spider.util.constant.Constant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by primecoder on 2017/8/19.
 * E-mail : aprimecoder@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class TestSendMessage {


    @Autowired
    @Qualifier(Constant.BeanName.ACTIVE_MQ_MESSAGE_SEND)
    private IMessageSend iMessageSend;


    @Test
    public void send() {

        iMessageSend.send("test001");
    }
}
