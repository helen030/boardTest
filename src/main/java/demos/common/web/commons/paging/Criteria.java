package demos.common.web.commons.paging;

/**
 * 기본생성자,
 * 현재페이지 = 1
 * 페이지당 출력 게시글 수 = 10
 * */
public class Criteria {

    //외부에서 입력되는 데이터
    int page;//현재페이지변호
    int perPageNum;//페이지당 출력 게시글 갯수

    public Criteria() {
        this.page = 1;
        this.perPageNum = 10;
    }

    public int getPage() {//sql Mapper 가 사용할 메서드 이다.
        return page;
    }

    public void setPage(int page) {//음수가 들어오지 못하도록 한다.
        if(page <= 0){
            this.page = 1;
            return;
        }

        this.page = page;
    }

    public void setPerPageNum(int perPageNum) {
        if(perPageNum <= 0 || perPageNum > 100){
            this.perPageNum = 10;
            return;
        }

        this.perPageNum = perPageNum;
    }

    public int getPerPageNum(){
        return this.perPageNum;
    }

    public int getPageStart(){//게시글의 시작 위치를 설정한다.
        //현재 페이지 시작 게시글 번호 = (현재페이지 번호 - 1) * 페이지당 출력 게시글
        return (this.page - 1) * perPageNum;
    }
}
/**
 * 하단 페이지 번호 출력하기 위해서서는 아래와 같다.
 * 시작페이지번호
 * 끝페이지번호
 * 전체게시글갯수
 * 이전페이지링크 :
 * */
