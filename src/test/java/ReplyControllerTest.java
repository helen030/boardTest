import demos.common.web.commons.paging.Criteria;
import demos.common.web.domain.ReplyVO;
import demos.common.web.reply.Persistence.ReplyDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-config/*.xml"})
public class ReplyControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(ReplyControllerTest.class);

    @Inject
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Inject
    private ReplyDAO replyDAO;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        logger.info("setup...");
    }

    @Test
    public void testReplyPaging() throws Exception {

        Criteria criteria = new Criteria();
        criteria.setPerPageNum(20);
        criteria.setPage(2);

        List<ReplyVO> replies = replyDAO.listPaging(1000, criteria);

        for (ReplyVO reply : replies) {
            logger.info(reply.getReplyNo() + " : " + reply.getReplyText());
        }

    }
}