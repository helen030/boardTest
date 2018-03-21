import demos.common.web.domain.ArticleVO;
import demos.common.web.domain.MemberVO;
import demos.common.web.persistence.ArticleDAO;
import demos.common.web.persistence.MemberDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-config/application-context.xml"})
public class ArticleDAOTest {

    private static final Logger logger = LoggerFactory.getLogger(ArticleDAOTest.class);

    @Inject
    private ArticleDAO articleDAO;

    @Test
    public void tesCreate() throws Exception {

        logger.info("start - logger msg");

        ArticleVO article = new ArticleVO();
        article.setTitle("새글 작성 테스트 제목");
        article.setContent("새글 작성 테스트 내용");
        article.setWriter("새글 작성자");

        logger.info("end - logger msg");

        articleDAO.create(article);
    }

    @Test
    public void testUpdate() throws Exception {
        ArticleVO article = new ArticleVO();
        article.setArticleNo(1);
        article.setTitle("글 수정 테스트 제목");
        article.setContent("글 수정 테스트 내용");
        article.setWriter("글 수정 작성자");
        articleDAO.update(article);
    }

    @Test
    public void testRead() throws Exception{
        logger.info(articleDAO.read(1).toString());
    }

    @Test
    public void testDelete() throws Exception {
        articleDAO.delete(1);
    }

}