package demos.common.web.controller;

import demos.common.web.domain.SampleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * RestApi test를 위해 클레스 생성
 *
 * # @RestController 는 별도의 뷰를 제공하지 않는 형태로 서비스를 실행 하므로, 리턴데이터가 예외적인 상황에서 문제가 발생 할 수 있다.
 * 웹의 경우 HTTP상태코드 가 이러한 정보를 나타내는데 사용된다.
 *
 * 스프링에서 제공하는 ResponseEntity 타입은 개발자가 직접 결과 데이터와 HTTP 상태코드를 직접 제어할 수 있는 클래스다.
 * ResponseEntity 를 사용하면 404나 500같은 에러를 전송하고 싶은 데이터와 함께 전송할 수 있기 때문에 좀더 세밀한 제어가 가능해진다.
 * */

@RestController
@RequestMapping("ajax/test")//@RequestMapping : 응답을 수행할 HTTP 요청을 명시
public class AjaxController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //# 객체를 JSON으로 리턴할 경우 메서드 작성법
    @RequestMapping(value = "/sendVO")
    public SampleVO sendVO() {

        logger.info("sendVO() called...");

        SampleVO sample = new SampleVO();
        sample.setSampleNo(1);
        sample.setFirstName("준서");
        sample.setLastName("박");

        return sample;
    }

    //# 컬렉션 타입(Map)의 객체를 리턴할 경우 메서드 작성법
    @RequestMapping("/sendList")
    public List<SampleVO> sendList() {

        List<SampleVO> samples = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SampleVO sample = new SampleVO();
            sample.setSampleNo(i);
            sample.setFirstName("준서");
            sample.setLastName("박" + i);
            samples.add(sample);
        }

        return samples;
    }


    //## ResponseEntity ex1. HTTP 상태코드만 보내기
    @RequestMapping("/sendErrorAuth")
    public ResponseEntity<Void> sendListAuth(){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //# ResponseEntity ex2. 결과데이터와 HTTP 상태코드 같이 보내기
    @RequestMapping("/sendErrorNot")
    public ResponseEntity<List<SampleVO>> sendListNot() {

        List<SampleVO> samples = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SampleVO sample = new SampleVO();
            sample.setSampleNo(i);
            sample.setFirstName("더블");
            sample.setLastName("에스" + i);
            samples.add(sample);
        }

        return new ResponseEntity<>(samples, HttpStatus.NOT_FOUND);
    }


    /**
     * AJAX(Asynchronous Javascript and XML)
     *
     * 화면의 전환이나 깜빡임없이 서버에서 데이터를 받는 방법
     * 웹의 경우 HTTP상태코드 가 이러한 정보를 나타내는데 사용된다.
     *
     * 스프링에서 제공하는 ResponseEntity 타입은 개발자가 직접 결과 데이터와 HTTP 상태코드를 직접 제어할 수 있는 클래스다.
     * ResponseEntity 를 사용하면 404나 500같은 에러를 전송하고 싶은 데이터와 함께 전송할 수 있기 때문에 좀더 세밀한 제어가 가능해진다.
     * */
}