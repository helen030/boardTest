package demos.common.web.controller;
//import org.apache.log4j.Logger

import demos.common.web.commons.paging.Criteria;
import demos.common.web.commons.paging.PageMaker;
import demos.common.web.domain.ArticleVO;
import demos.common.web.persistence.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;

@Controller
@RequestMapping("article")
public class ArticleController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ArticleService articleService;

    @Inject
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // 등록 페이지로 이동
    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String writeGET() {
        logger.info("writeGET.....");
        return "/article/write";
    }

    // 등록 처리
    /**
     *
     * 게시글 등록 처리요청을 처리하고, redirect되는 시점에 RedirectAttributes객체의 addFlashAttribute()를 이용해 처리가 성공적으로 이루어진 것을 알리기 위해
     * regSuccess메시지를 String데이터로 저장해준다.
     *
     * # RedirectAttributes객체의 addFlashAttribute()
     * post방식처럼 url뒤에 parameter를 추가하지 않아도 화면에 값을 받을수 있다
     *
     * */
    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public String writePOST(ArticleVO articleVO, RedirectAttributes redirectAttributes) throws Exception {

        logger.info("write POST...");
        logger.info(articleVO.toString());
        articleService.create(articleVO);
        redirectAttributes.addFlashAttribute("msg", "regSuccess");

        return "redirect:/article/list";
    }

    // 목록 페이지 이동
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) throws Exception {

        logger.info("list ...");
        model.addAttribute("articles", articleService.listAll());

        return "/article/list";
    }

    // 이 멍충이가 pageMaker 에 get/set을 안만들어서 오전을 다 날리고......
    @RequestMapping(value = "/listPaging", method = RequestMethod.GET)
    public String listPaging(Model model, Criteria criteria) throws Exception {
        logger.info("listPaging ...");

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCriteria(criteria);
        // 수정
        pageMaker.setTotalCount(articleService.countArticles(criteria));

        model.addAttribute("articles", articleService.listCriteria(criteria));
        model.addAttribute("pageMaker", pageMaker);

        return "/article/list_paging";
    }

    // 조회 페이지 이동
    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public String read(@RequestParam("articleNo") int articleNo,
                       Model model) throws Exception {

        logger.info("read ...");
        ArticleVO vo = articleService.read(articleNo);
        if(vo == null){
            throw new Exception("exception");
        }else{
            model.addAttribute("article", vo);
        }

        return "/article/read";
    }

    // 수정 페이지 이동
    @RequestMapping(value = "/modify", method = RequestMethod.GET)
    public String modifyGET(@RequestParam("articleNo") int articleNo,
                            Model model) throws Exception {

        logger.info("modifyGet ...");
        model.addAttribute("article", articleService.read(articleNo));

        return "/article/modify";
    }

    // 수정 처리
    /**
     *
     * 게시글 삭제 처리요청을 처리하고, redirect되는 시점에 RedirectAttributes객체의 addFlashAttribute()를
     * 이용해 처리가 성공적으로 이루어진 것을 알리기 위해 modSuccess메시지를 String데이터로 저장해준다.
     *
     * */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String modifyPOST(ArticleVO articleVO,
                             RedirectAttributes redirectAttributes) throws Exception {

        logger.info("modifyPOST ...");
        articleService.update(articleVO);
        redirectAttributes.addFlashAttribute("msg", "modSuccess");

        return "redirect:/article/list";
    }

    // 삭제 처리
    /**
     *
     * 게시글 삭제 처리요청을 처리하고, redirect되는 시점에 RedirectAttributes객체의 addFlashAttribute()를
     * 이용해 처리가 성공적으로 이루어진 것을 알리기 위해 delSuccess메시지를 String데이터로 저장해준다.
     *
     * */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(@RequestParam("articleNo") int articleNo,
                         RedirectAttributes redirectAttributes) throws Exception {

        logger.info("remove ...");
        articleService.delete(articleNo);
        redirectAttributes.addFlashAttribute("msg", "delSuccess");

        return "redirect:/article/list";
    }


    /*****************************************************************************************/
    /**
     * 특정 목록페이지에서
     * 특정 게시글을 조회, 수정, 삭제한 뒤
     * 다시 목록을 이동할 때
     * 특정 목록페이지로 다시 이동하도록 구현
     *
     * 현재 목록페이지의 번호(page), 페이지당 출력할 게시글의 갯수(perPageNum), 조회게시글의 번호(articleNo) 를
     * 페이지 이동시마다 가지고 있어야 하므로
     * 게시글 조회, 수정, 삭제 메서드에 Criteria 타입의 criteria변수를 파라미터로 추가 시켜주고,
     * 각 JSP페이지에 값들을 <form>태그안에 hidden값으로 세팅해줘야 한다.
     * */

    //조회
    @RequestMapping(value = "/readPaging", method = RequestMethod.GET)
    public String readPaging(@RequestParam("articleNo") int articleNo,
                             @ModelAttribute("criteria") Criteria criteria,
                             Model model) throws Exception {

        model.addAttribute("article", articleService.read(articleNo));

        return "/article/read_paging";
    }

    //수정 GET 타입
    @RequestMapping(value = "/modifyPaging", method = RequestMethod.GET)
    public String modifyGETPaging(@RequestParam("articleNo") int articleNo,
                                  @ModelAttribute("criteria") Criteria criteria,
                                  Model model) throws Exception {

        logger.info("modifyGetPaging ...");
        model.addAttribute("article", articleService.read(articleNo));

        return "/article/modify_paging";
    }

    //수정 POST 타입
    @RequestMapping(value = "/modifyPaging", method = RequestMethod.POST)
    public String modifyPOSTPaging(ArticleVO articleVO,
                                   Criteria criteria,
                                   RedirectAttributes redirectAttributes) throws Exception {

        logger.info("modifyPOSTPaging ...");
        articleService.update(articleVO);
        redirectAttributes.addAttribute("page", criteria.getPage());
        redirectAttributes.addAttribute("perPageNum", criteria.getPerPageNum());
        redirectAttributes.addFlashAttribute("msg", "modSuccess");

        return "redirect:/article/listPaging";
    }

    //삭제
    @RequestMapping(value = "/removePaging", method = RequestMethod.POST)
    public String removePaging(@RequestParam("articleNo") int articleNo,
                               Criteria criteria,
                               RedirectAttributes redirectAttributes) throws Exception {

        logger.info("remove ...");
        articleService.delete(articleNo);
        redirectAttributes.addAttribute("page", criteria.getPage());
        redirectAttributes.addAttribute("perPageNum", criteria.getPerPageNum());
        redirectAttributes.addFlashAttribute("msg", "delSuccess");

        return "redirect:/article/listPaging";
    }


}