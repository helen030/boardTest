package demos.common.web.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * RestApi test를 위해 객체 생성
 * */
@Setter
@Getter
@ToString
public class SampleVO {
    private Integer sampleNo;
    private String firstName;
    private String lastName;
}
