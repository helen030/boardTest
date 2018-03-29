package demos.common.web.reply.Persistence;

import demos.common.web.commons.paging.Criteria;
import demos.common.web.domain.ReplyVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReplyDAOImpl implements ReplyDAO {

    private static final String NAMESPACE = "demos.common.web.mappers.replyMapper";

    private final SqlSession sqlSession;

    public ReplyDAOImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }


    @Override
    public List<ReplyVO> list(Integer articleNo) throws Exception {
        return sqlSession.selectList(NAMESPACE + ".list", articleNo);
    }

    @Override
    public void create(ReplyVO replyVO) throws Exception {
        sqlSession.insert(NAMESPACE + ".create", replyVO);
    }

    @Override
    public void update(ReplyVO replyVO) throws Exception {
        sqlSession.update(NAMESPACE + ".update", replyVO);
    }

    @Override
    public void delete(Integer replyNo) throws Exception {
        sqlSession.delete(NAMESPACE + ".delete", replyNo);
    }

    @Override
    public List<ReplyVO> listPaging(Integer articleNo, Criteria criteria) throws Exception {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("articleNo", articleNo);
        paramMap.put("criteria", criteria);

        return sqlSession.selectList(NAMESPACE + ".listPaging", paramMap);
    }

    @Override
    public int countReply(Integer articleNo) throws Exception {
        return sqlSession.selectOne(NAMESPACE + ".countReply", articleNo);
    }
}
