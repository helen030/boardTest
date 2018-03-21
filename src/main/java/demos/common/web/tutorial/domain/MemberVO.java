package demos.common.web.tutorial;

        import lombok.Data;

        import java.util.Date;

@Data
public class MemberVO {

    private String userid; // 회원아이디
    private String userpw; // 회원 비밀번호
    private String username; // 회원이름
    private String email; // 이메일
    private Date regdate; // 가입일자
    private Date updatedate; // 수정일자

    // getter, setter, toString() ...
    
}
