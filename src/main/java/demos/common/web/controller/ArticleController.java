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
//
//    @RequestMapping(value = "/listCriteria", method = RequestMethod.GET)
//    public String listCriteria(Model model, Criteria criteria) throws Exception{
//        logger.info("listCriteria");
//        model.addAttribute("articles", articleService.listCriteria(criteria));
//        return "/article/list_criteria";
//    }

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



}