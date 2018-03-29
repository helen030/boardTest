package demos.common.web.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ReplyVO {
    private Integer replyNo;
    private Integer ArticleNo;
    private String replyText;
    private String replyWriter;
    private Date regDate;
    private Date updateDate;
}
