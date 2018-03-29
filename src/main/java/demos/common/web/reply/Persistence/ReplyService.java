package demos.common.web.reply.Persistence;

import demos.common.web.commons.paging.Criteria;
import demos.common.web.domain.ReplyVO;

import java.util.List;

public interface ReplyService {
    List<ReplyVO> list(Integer articleNo) throws Exception;

    void create(ReplyVO replyVO) throws Exception;

    void update(ReplyVO replyVO) throws Exception;

    void delete(Integer replyNo) throws Exception;




    void addReply(ReplyVO replyVO) throws Exception;

    List<ReplyVO> getReplies(int articleNo) throws Exception;

    void modifyReply(ReplyVO replyVO) throws Exception;

    void removeReply(Integer replyNo) throws Exception;

    //댓글 페이징 처리하기
    List<ReplyVO> listPaging(Integer articleNo, Criteria creteria) throws Exception;

    int countReply(Integer articleNo) throws Exception;
}
