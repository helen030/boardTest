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
public class ControllerTest {
    /**
     *** 테스트 코드를 작성하고 테스트를 하는 것의 장점
     * 웹페이지를 테스트하려면 매번 입력 항목을 입력해서 제대로 동작하는지 확인하는데,
     * 여러번 웹페이지를 입력하는 것보다 테스트 코드를 통해 처리하는 것이 개발 시간을 단축할 수 있다.
     * JSP 등에서 발생하는 에러를 해결하는 과정에서 매법 WAS에 만들어진 Controller 코드를 수정해서 배포하는 작업은 많은 시간을 소모한다.
     * Controller에서 결과 데이터만을 확인할 수 있기 떄문에 문제 발생시 원인을 파악할 수 있는 시간이 절약된다.
     * */
    private static final Logger logger = LoggerFactory.getLogger(ControllerTest.class);

    @Inject
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        logger.info("setup...");
    }

    @Test
    public void testDoA() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/doA"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDoB() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/doB"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDoC() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/doC?msg=world"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDoD() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/doD"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("productVO"));
    }

    @Test
    public void testDoE() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/doE"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doF?msg=this+is+the+message+with+redirected"));
    }

    @Test
    public void testDoJson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/doJson"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf-8"));
    }




}