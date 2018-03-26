package demos.common.web.commons.paging;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 기본생성자,
 * 현재페이지 = 1
 * 페이지당 출력 게시글 수 = 10
 * */
public class PageMaker {

    //DB에서 계산되는 데이터
    private int totalCount;

    //계산식을 통해 만들어지는 데이터
    private int startPage;//시작페이지번호
    private int endPage;//끝페이지번호
    private boolean prev;
    private boolean next;

    private int displayPageNum = 10; //하단의 번호 갯수

    private Criteria criteria;

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public void setTotalCount(int totalCount){
        this.totalCount = totalCount;
        calcData();
    }

    private void calcData() {
        // 끝 페이지 계산
        // endPage = ( 현재 설정된 페이지 / 화면에 나와야 할 하단의 번호 갯수 ) * 화면에 나와야 할 하단의 번호 갯수
        endPage = (int)( Math.ceil(criteria.getPage() / (double) displayPageNum) * displayPageNum );

        //시작 페이지 계산
        // startPage = (끝페이지 - 화면에 나와야할 번호 갯수) + 1
        startPage = (endPage - displayPageNum) + 1;

        // 끝 페이지 번호 보정 계산
        // 끝 페이지 번호 = (전체게시글갯수 / 페이지당 출력할 게시글 갯수)
        int tempEndPage = (int)(Math.ceil(totalCount / (double)criteria.getPerPageNum()));

        //이전의 결과 값과 보정된 결과 값을 비교 후, 보정한 결과 값을 페이지 끝 번호 변수에 저장
       if(endPage > tempEndPage){
            endPage = tempEndPage;
        }

        //이전링크 는 시작페이지 번화 가 1인지 아닌지 검사한다.
        prev = startPage == 1 ? false : true;

       //다음링크 는 끝페이지 * 페이지당 출력할 게시글 갯수 >= 전체개시글 갯수 ? false : true
       next = endPage * criteria.getPerPageNum() >= totalCount ? false : true ;
    }

    //UriComponentsBuilder 를 이용하기 위해 추가하였다.
    //자동으로 uri 를 생성해 전달 준다...
    public String makeQuery(int page){
        //Criteria criteria = new Criteria();
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .queryParam("page" , page)
                .queryParam("perPageNum",criteria.getPerPageNum())
                .build();

        return uriComponents.toUriString();

    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public boolean isPrev() {
        return prev;
    }

    public void setPrev(boolean prev) {
        this.prev = prev;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public int getDisplayPageNum() {
        return displayPageNum;
    }

    public void setDisplayPageNum(int displayPageNum) {
        this.displayPageNum = displayPageNum;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    @Override
    public String toString() {
        return "PageMaker{" +
                "totalCount=" + totalCount +
                ", startPage=" + startPage +
                ", endPage=" + endPage +
                ", prev=" + prev +
                ", next=" + next +
                ", displayPageNum=" + displayPageNum +
                ", criteria=" + criteria +
                '}';
    }
}