package demos.common.web.First;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JasonController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/doJson")
    @ResponseBody
    public ProductVO doJson() {

        ProductVO productVO = new ProductVO("laptop", 3000000);

        return productVO;
    }


}
/**
 * Exception
 * java.lang.IllegalArgumentException: No converter found for return value of type: class demos.common.web.ProductVO
 *
 * 1. pom.xml
 *      jackson-core, jackson-databind dependency 추가
 * 2. dispatcher-servlet
 *      Mvc message-convrter mappingjackson2httpmessageconverter 추가
 */


/**
 * MappingJackson2HttpMessageConverter을 이용하지 않고 JSON으로 연동하는 방법
 * pom.xml과 server.xml에 위의 것들을 설정할 필요가 없이 JS파일에만 처리해주면 된다.
 *
 *  JavaScript Code
         var param = {
             "name": "홍길동",
             "age": 22
         };

         $.ajax({
             type: 'POST',
             dataType: 'JSON',
             data:  JSON.stringify(search),
             contentType:"application/json; charset=UTF-8",
             url:  'test/testing/select',
         error: function() {
            //에러처리
            },
         success: function(returnJSON) {
            //성공처리
            }
         }):

    data부분을 JSON.stringfy로 변환해주고 contentType에 application/json;charset=UTF-8만 추가해주면 간단하게 처리된다.
 *
 *
 */
