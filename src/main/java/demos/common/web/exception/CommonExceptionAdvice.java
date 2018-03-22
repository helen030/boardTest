package demos.common.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice//이 클래스의 객체가 컨트롤러에서 발생하는 Exception을 전문적으로 처리하는 클래스라는 것을 명시
public class CommonExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);

    /***
     * Model을 파라미터로 사용하는 것을 지원하지 않기 때문에 ModelAndView타입을 직접 사용하는 형태로 작성해야한다.
     * ModelAndView는 하나의 객체에 Model데이터와 View의 처리를 동시에 처리할 수 있는 객체이다.
     * 만약 예외가 발생하게 되면 예외가 발생한 내용이 담긴 데이터를 exception에 담고, common_error.jsp에 전달하게 된다.
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)//적절한 타입의 Exception을 처리
    public ModelAndView commonException(Exception e){
        logger.info(e.toString());
        logger.info("Exception");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", e);
        modelAndView.setViewName("/commons/common_error");

        return modelAndView;

    }
}
