import demos.common.web.commons.paging.Criteria;
import demos.common.web.domain.ArticleVO;
import demos.common.web.persistence.ArticleDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-config/application-context.xml"})
public class URITest {

    private static final Logger logger = LoggerFactory.getLogger(URITest.class);

    @Inject
    private ArticleDAO articleDAO;

    @Test
    public void tesURI() throws Exception {

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .path("/article/read")
                .queryParam("articleNo" , 12)
                .queryParam("perPageNum",20)
                .build();
        logger.info("/article/read?articleNo=12&perPageNum=20");


        logger.info(uriComponents.toString());

    }

    @Test
    public void tesURI2() throws Exception {

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .path("/{module}/{page}")
                .queryParam("articleNo" , 12)
                .queryParam("perPageNum",20)
                .build()
                .expand("article","read")
                .encode();
        logger.info("/article/read?articleNo=12&perPageNum=20");
        logger.info(uriComponents.toString());

    }
}