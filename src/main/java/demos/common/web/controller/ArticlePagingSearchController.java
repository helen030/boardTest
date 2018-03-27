package demos.common.web.controller;
//import org.apache.log4j.Logger

import demos.common.web.commons.paging.Criteria;
import demos.common.web.commons.paging.PageMaker;
import demos.common.web.commons.paging.SearchCriteria;
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
@RequestMapping("article/paging/search")
public class ArticlePagingSearchController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ArticleService articleService;

    @Inject
    public ArticlePagingSearchController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String writeGET() {

        logger.info("paging writeGET() called...");

        return "article/search/write";
    }

    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public String writePOST(ArticleVO articleVO,
                            RedirectAttributes redirectAttributes) throws Exception {

        logger.info("paging writePOST() called...");

        articleService.create(articleVO);
        redirectAttributes.addFlashAttribute("msg", "regSuccess");

        return "redirect:/article/paging/search/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, SearchCriteria searchCriteria) throws Exception {

        logger.info("search list() called ...");

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCriteria(searchCriteria);
//        pageMaker.setTotalCount(articleService.countArticles(searchCriteria));
        pageMaker.setTotalCount(articleService.countSearchedArticles(searchCriteria));

//        model.addAttribute("articles", articleService.listCriteria(searchCriteria));
        model.addAttribute("articles", articleService.listSearch(searchCriteria));
        model.addAttribute("pageMaker", pageMaker);

        return "article/search/list";
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public String read(@RequestParam("articleNo") int articleNo,
                       @ModelAttribute("searchCriteria") SearchCriteria searchCriteria,
                       Model model) throws Exception {

        logger.info("paging read() called ...");
        model.addAttribute("article", articleService.read(articleNo));

        return "article/search/read";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.GET)
    public String modifyGET(@RequestParam("articleNo") int articleNo,
                            @ModelAttribute("searchCriteria") SearchCriteria searchCriteria,
                            Model model) throws Exception {

        logger.info("paging modifyGet() called ...");
        model.addAttribute("article", articleService.read(articleNo));

        return "article/search/modify";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String modifyPOST(ArticleVO articleVO,
                             SearchCriteria searchCriteria,
                             RedirectAttributes redirectAttributes) throws Exception {

        logger.info("paging modifyPOST() called ...");
        articleService.update(articleVO);
        redirectAttributes.addAttribute("page", searchCriteria.getPage());
        redirectAttributes.addAttribute("perPageNum", searchCriteria.getPerPageNum());
        redirectAttributes.addAttribute("searchType", searchCriteria.getSearchType());
        redirectAttributes.addAttribute("keyword", searchCriteria.getKeyword());
        redirectAttributes.addFlashAttribute("msg", "modSuccess");

        return "redirect:/article/paging/search/list";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(@RequestParam("articleNo") int articleNo,
                         SearchCriteria searchCriteria,
                         RedirectAttributes redirectAttributes) throws Exception {

        logger.info("paging remove() called ...");
        articleService.delete(articleNo);
        redirectAttributes.addAttribute("page", searchCriteria.getPage());
        redirectAttributes.addAttribute("searchType", searchCriteria.getSearchType());
        redirectAttributes.addAttribute("keyword", searchCriteria.getKeyword());
        redirectAttributes.addAttribute("perPageNum", searchCriteria.getPerPageNum());
        redirectAttributes.addFlashAttribute("msg", "delSuccess");

        return "redirect:/article/paging/search/list";
    }


}