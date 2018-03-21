package demos.common.web.controller;
//import org.apache.log4j.Logger
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/")
    public String Index(Model model) throws Exception {
        logger.info("index.....");
        model.addAttribute("greeting", "hello world");
        return "home";
    }
}