package demos.common.web.persistence;

import demos.common.web.commons.paging.Criteria;
import demos.common.web.domain.ArticleVO;

import javax.swing.*;
import java.beans.ExceptionListener;
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

    List<ArticleVO> listPaging(int page) throws Exception;

    //페이징
    List<ArticleVO> listCriteria(Criteria criteria) throws Exception;

    int countArticles(Criteria criteria) throws Exception;

}
