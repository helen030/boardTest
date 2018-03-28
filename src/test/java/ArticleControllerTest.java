import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-config/*.xml"})
public class ArticleControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(ArticleControllerTest.class);

    @Inject
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        logger.info("setup...");
    }

    //model and view 와 responseBody 를 사용하는 것의 차이
    /***
     * ModelAndView:
        View name = article/normal/read
        View = null
        Attribute = article
        value = ArticleVO(articleNo=20, title=16번째 제목, content=16번째 내용, writer=16번째 작성자, regDate=Sun Mar 25 01:55:25 KST 2018, viewCnt=0)
        errors = []



     *
     *
     *
     * @throws Exception
     */
    @Test
    public void testArticleControllerTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/article/normal/read?articleNo=20"))
                .andDo(print())
                .andExpect(status().isOk());
        /**
         *
         ModelAndView:
             View name = article/normal/read
             View = null
             Attribute = article
             value = ArticleVO(articleNo=20, title=16번째 제목, content=16번째 내용, writer=16번째 작성자, regDate=Sun Mar 25 01:55:25 KST 2018, viewCnt=0)
             errors = []

         FlashMap:
            Attributes = null

         MockHttpServletResponse:
             Status = 200
             Error message = null
             Headers = {}
             Content type = null
             Body =
             Forwarded URL = /WEB-INF/views/article/normal/read.jsp
             Redirected URL = null
             Cookies = []
         * */


        mockMvc.perform(MockMvcRequestBuilders.get("/article/normal/readResBody?articleNo=20"))
                .andDo(print())
                .andExpect(status().isOk());

        /**
         ModelAndView:
             View name = null
             View = null
             Model = null

         FlashMap:
            Attributes = null

         MockHttpServletResponse:
             Status = 200
             Error message = null
             Headers = {Content-Type=[application/json;charset=UTF-8]}
             Content type = application/json;charset=UTF-8
             Body = {"articleNo":20,"title":"16번째 제목","content":"16번째 내용","writer":"16번째 작성자","regDate":1521910525000,"viewCnt":0}
             Forwarded URL = null
             Redirected URL = null
             Cookies = []
         *
         * */


        mockMvc.perform(MockMvcRequestBuilders.get("/article/normal/readResBodyMav?articleNo=20"))
                .andDo(print())
                .andExpect(status().isOk());
        /**
         *
         * ModelAndView:
             View name = article/normal/read
             View = null
             Attribute = modelAndView
             value = ModelAndView: reference to view with name 'article/normal/read'; model is {article=ArticleVO(articleNo=20, title=16번째 제목, content=16번째 내용, writer=16번째 작성자, regDate=Sun Mar 25 01:55:25 KST 2018, viewCnt=0)}
             errors = []
             Attribute = article
             value = ArticleVO(articleNo=20, title=16번째 제목, content=16번째 내용, writer=16번째 작성자, regDate=Sun Mar 25 01:55:25 KST 2018, viewCnt=0)
             errors = []

         FlashMap:
            Attributes = null

         MockHttpServletResponse:
             Status = 200
             Error message = null
             Headers = {}
             Content type = null
             Body =
             Forwarded URL = /WEB-INF/views/article/normal/read.jsp
             Redirected URL = null
             Cookies = []
         * */
    }
}