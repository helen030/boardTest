package demos.common.web.First;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StringController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/doC")
    public String doC(@ModelAttribute("msg") String msg) {
        logger.info("/doC called");
        return "tutorial/result";
    }
}