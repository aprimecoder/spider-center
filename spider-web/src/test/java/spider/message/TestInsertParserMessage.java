package spider.message;

import com.primecoder.spider.Application;
import com.primecoder.spider.dao.entity.ParserMessageEntity;
import com.primecoder.spider.dao.mapper.ParserMessageMapper;
import com.primecoder.spider.util.UuidGenerate;
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
public class TestInsertParserMessage {


    @Autowired
    private ParserMessageMapper parserMessageMapper;

    @Autowired
    private UuidGenerate uuidGenerate;

    @Test
    public void test() {

        ParserMessageEntity parserMessageEntity = new ParserMessageEntity();
        parserMessageEntity.setRequestId(uuidGenerate.generate());
        parserMessageEntity.setUrlType(0);

        parserMessageMapper.insert(parserMessageEntity);
    }
}
