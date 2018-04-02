package demos.common.web.controller;

import demos.common.web.commons.paging.Criteria;
import demos.common.web.commons.paging.PageMaker;
import demos.common.web.domain.ArticleVO;
import demos.common.web.domain.ReplyVO;
import demos.common.web.persistence.ArticleService;
import demos.common.web.reply.Persistence.ReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * # REST 방식에서 쓰이는 주요 애너테이션들
 @PathVariable : URI의 경로에서 원하는 데이터를 추출하는 용도로 사용한다.
 @RequestBody : 전송된 JSON 데이터를 객체로 변환해주는 애너테이션으로 @ModelAttribute 와 유사한 역할을 하지만 JSON 에서 사용한다는 점이 차이점이다.

 # Overloaded POST : 브라우저에서 PUT, PATCH, DELETE방식을 지원하기 위한 필터 추가
 브라우저에 따라 GET과 POST방식을 지원하고, PUT, PATCH, DELETE방식은 지원하지 않는 경우가 발생할 수 있다.
 해결책은 브라우저에서 POST방식으로 전송하고, 추가적인 정보를 이용해 PUT, PATCH, DELETE와 같은 정보를 함께 전송하는 것이다.
 이것을 Overloaded POST라고 한다. <form>태그를 이용해 데이터를 전송할 때, POST방식으로 전송하되 _method라는 추가적인 정보를 이용한다.

 스프링은 이를 위해 HiddenHttpMethodFilter라는 것을 제공한다.
 <form>태그 내에 <input type="hidden" name="_method" value="PUT">와 같은 형태로 사용한다.
 이렇게 설정함으로써 GET, POST방식만을 지원하는 브라우저에서 REST방식을 사용할 수 있게 된다.
 이렇게 사용하기 위해서는 아래와 같이 web.xml에 필터를 추가해주면 된다.
 *
 * */
@RestController
@RequestMapping("/replies")
public class ReplyController {

    private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);

    private final ReplyService replyService;

    @Inject
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    // 댓글 등록처리
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> register(@RequestBody ReplyVO replyVO) {

        ResponseEntity<String> entity = null;
        try{
            replyService.addReply(replyVO);
            entity = new ResponseEntity<>("regSuccess", HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }

        return entity;
    }

    // 댓글 목록 출력 메서드
    @RequestMapping(value = "/{articleNo}", method = RequestMethod.GET)
    public ResponseEntity<List<ReplyVO>>  list(@PathVariable("articleNo") int articleNo) throws Exception {
        ResponseEntity<List<ReplyVO>> entity = null;
        try{
            entity = new ResponseEntity<List<ReplyVO>>(replyService.getReplies(articleNo), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    // 댓글 수정처리
    @RequestMapping(value = "", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity<String> update(@RequestBody ReplyVO replyVO) throws Exception {
        ResponseEntity<String> entity = null;
        try {
            replyService.modifyReply(replyVO);
            entity = new ResponseEntity<>("modSuccess",HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    //댓글 삭제 처리
    @RequestMapping(value = "/{replyNo}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable("replyNo") Integer replyNo) throws Exception {
        ResponseEntity<String> entity = null;
        try {
            replyService.removeReply(replyNo);
            entity = new ResponseEntity<String>("delSuccess",HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @RequestMapping(value = "/{articleNo}/{page}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> listPaging(@PathVariable("articleNo") Integer articleNo,
                                                          @PathVariable("page") Integer page) {

        ResponseEntity<Map<String, Object>> entity = null;

        try {

            Criteria criteria = new Criteria();
            criteria.setPage(page);

            List<ReplyVO> replies = replyService.listPaging(articleNo, criteria);
            int repliesCount = replyService.countReply(articleNo);

            PageMaker pageMaker = new PageMaker();
            pageMaker.setCriteria(criteria);
            pageMaker.setTotalCount(repliesCount);

            Map<String, Object> map = new HashMap<>();
            map.put("replies", replies);
            map.put("pageMaker", pageMaker);

            entity = new ResponseEntity<>(map, HttpStatus.OK);

        } catch (Exception e) {

            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.OK);

        }

        return entity;
    }
}