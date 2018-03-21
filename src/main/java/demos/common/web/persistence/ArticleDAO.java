package demos.common.web.persistence;

import demos.common.web.domain.ArticleVO;

import java.util.List;
/**
 * 영속(DAO)계층 구현하기
 * ArticleVO
 * ArticleDAO
 * ArticleDAOImpl
 * */

public interface ArticleDAO {

    void create(ArticleVO articleVO) throws Exception;

    ArticleVO read(Integer articleNo) throws Exception;

    void update(ArticleVO articleVO) throws Exception;

    void delete(Integer articleNo) throws Exception;

    List<ArticleVO> listAll() throws Exception;
}
