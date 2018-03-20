package demos.common.web.First;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RedirectController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/doE")
    public String doE(RedirectAttributes redirectAttributes) {

        logger.info("/doE called and redirect to /doF");
        redirectAttributes.addAttribute("msg", "this is the message with redirected");

        return "redirect:/doF";
    }

    @RequestMapping("/doF")
    public void doF(@ModelAttribute String msg) {

        logger.info("/doF called" + msg);

    }


}