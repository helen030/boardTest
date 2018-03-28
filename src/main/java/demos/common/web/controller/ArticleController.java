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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;

@Controller
@RequestMapping("/article/normal")
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleService articleService;

    @Inject
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String writeGET() {

        logger.info("normal writeGET() called...");

        return "article/normal/write";
    }

    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public String writePOST(ArticleVO articleVO,
                            RedirectAttributes redirectAttributes) throws Exception {

        logger.info("normal writePOST() called...");
        logger.info(articleVO.toString());
        articleService.create(articleVO);
        redirectAttributes.addFlashAttribute("msg", "regSuccess");

        return "redirect:/article/normal/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) throws Exception {

        logger.info("normal list() called ...");
        model.addAttribute("articles", articleService.listAll());

        return "article/normal/list";
    }

    @RequestMapping(value = "/listCriteria", method = RequestMethod.GET)
    public String listCriteria(Model model, Criteria criteria) throws Exception {
        logger.info("normal listCriteria() ...");
        model.addAttribute("articles", articleService.listCriteria(criteria));
        return "article/normal/list_criteria";
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public String read(@RequestParam("articleNo") int articleNo,
                       Model model) throws Exception {

        logger.info("normal read() called ...");
        model.addAttribute("article", articleService.read(articleNo));

        return "article/normal/read";
    }

    @RequestMapping(value = "/readResBody", method = RequestMethod.GET)
    @ResponseBody
    public ArticleVO readResBody(@RequestParam("articleNo") int articleNo,
                       Model model) throws Exception {

        logger.info("normal readResBody() called ...");
        ArticleVO vo = articleService.read(articleNo);

        return vo;
    }

    @RequestMapping(value = "/readResBodyMav", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView readResBodyMav(@RequestParam("articleNo") int articleNo,
                                       ModelAndView mav) throws Exception {

        logger.info("normal readResBody() called ...");
        mav.setViewName("article/normal/read");
        mav.addObject("article", articleService.read(articleNo));

        return mav;
    }

    @RequestMapping(value = "/modify", method = RequestMethod.GET)
    public String modifyGET(@RequestParam("articleNo") int articleNo,
                            Model model) throws Exception {

        logger.info("normal modifyGet() called ...");
        model.addAttribute("article", articleService.read(articleNo));

        return "article/normal/modify";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String modifyPOST(ArticleVO articleVO,
                             RedirectAttributes redirectAttributes) throws Exception {

        logger.info("normal modifyPOST() called ...");
        articleService.update(articleVO);
        redirectAttributes.addFlashAttribute("msg", "modSuccess");

        return "redirect:/article/normal/list";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(@RequestParam("articleNo") int articleNo,
                         RedirectAttributes redirectAttributes) throws Exception {

        logger.info("normal remove() ...");
        articleService.delete(articleNo);
        redirectAttributes.addFlashAttribute("msg", "delSuccess");

        return "redirect:/article/normal/list";
    }

}