<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
<%@ include file="../include/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <%@ include file="../include/header.jsp"%>
    <%@ include file="../include/left.jsp"%>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Page Header
                <small>Optional description</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
                <li class="active">Here</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content container-fluid">
            <div class="col-lg-12">
                <div class="box box-primary">
                    <div class="box-header with-border">
                        <h3 class="box-title">게시글 목록</h3>
                    </div>
                    <div class="box-body">
                        <table class="table table-bordered">
                            <tbody>
                            <tr>
                                <th style="width: 30px">#</th>
                                <th>제목</th>
                                <th style="width: 100px">작성자</th>
                                <th style="width: 150px">작성시간</th>
                                <th style="width: 60px">조회</th>
                            </tr>
                            <c:forEach items="${articles}" var="article">
                                <tr>
                                    <td>${article.articleNo}</td>

                                    <%-- 파라미터 를 get 방식으로 보냄 --%>
                                    <%--<td><a href="${path}/article/read?articleNo=${article.articleNo}">${article.title}</a></td>--%>

                                    <%-- 파라미터 를 UriComponentsBuilder 를 이용한  방식으로 보냄 --%>
                                    <td><a href="${path}/article/readPaging${pageMaker.makeQuery(pageMaker.criteria.page)}&articleNo=${article.articleNo}">${article.title}</a></td>

                                    <td>${article.writer}</td>
                                    <td><fmt:formatDate value="${article.regDate}" pattern="yyyy-MM-dd a HH:mm"/></td>
                                    <td><span class="badge bg-red">${article.viewCnt}</span></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="box-footer">
                        <div class="text-center">
                            <p> 1. 파라미터 를 get 방식으로 보냄 </p>
                            <%-- 주소 창에 page 만 보임
                            <ul class="pagination ">
                                <c:if test="${pageMaker.prev}">
                                    <li><a href="${path}/article/listPaging?page=${pageMaker.startPage - 1}">이전</a></li>
                                </c:if>
                                <c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx">
                                    <li <c:out value="${pageMaker.criteria.page == idx ? 'class=active' : ''}"/>>
                                        <a href="${path}/article/listPaging?page=${idx}">${idx}</a>
                                    </li>
                                </c:forEach>
                                <c:if test="${pageMaker.next && pageMaker.endPage > 0}">
                                    <li><a href="${path}/article/listPaging?page=${pageMaker.endPage + 1}">다음</a></li>
                                </c:if>
                            </ul>--%>

                            <p> 2. 파라미터 를 UriComponentsBuilder 를 이용한 방식으로 보냄 </p>
                            <%-- 주소 창에 page, perPageNum 이 같이 연동됨 --%>
                            <ul class="pagination">
                                <c:if test="${pageMaker.prev}">
                                    <li><a href="${path}/article/listPaging${pageMaker.makeQuery(pageMaker.startPage - 1)}">이전</a></li>
                                </c:if>
                                <c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx">
                                    <li <c:out value="${pageMaker.criteria.page == idx ? 'class=active' : ''}"/>>
                                        <a href="${path}/article/listPaging${pageMaker.makeQuery(idx)}">${idx}</a>
                                    </li>
                                </c:forEach>
                                <c:if test="${pageMaker.next && pageMaker.endPage > 0}">
                                    <li><a href="${path}/article/listPaging${pageMaker.makeQuery(pageMaker.endPage + 1)}">다음</a></li>
                                </c:if>
                            </ul>

                            <p> 3. 파라미터 를 javascript 를 이용한 방식으로 보냄 </p>
                            <%-- 3. 페이지에서 일어나는 이벤트 를 제어 하므로 href 의 속성을 단순히 페이지번호만을 의미하도록 변경하여야 한다. --%>
                            <%--<ul class="pagination">
                                <c:if test="${pageMaker.prev}">
                                    <li><a href="${pageMaker.startPage - 1}">이전</a></li>
                                </c:if>
                                <c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx">
                                    <li <c:out value="${pageMaker.criteria.page == idx ? 'class=active' : ''}"/>>
                                        <a href="${idx}">${idx}</a>
                                    </li>
                                </c:forEach>
                                <c:if test="${pageMaker.next && pageMaker.endPage > 0}">
                                    <li><a href="${pageMaker.endPage + 1}">다음</a></li>
                                </c:if>
                            </ul>--%>
                            <%-- 3. 자바스크립트를 이용 할 경우 page와 perPageNum값을 넘겨주도록 한다  --%>
                            <%--<form id="listPageForm">
                                <input type="hidden" name="page" value="${pageMaker.criteria.page}">
                                <input type="hidden" name="perPageNum" value="${pageMaker.criteria.perPageNum}">
                            </form>--%>

                        </div>


                        <div class="pull-right">
                            <button type="button" class="btn btn-success btn-flat" id="writeBtn">
                                <i class="fa fa-pencil"></i> 글쓰기
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <%@ include file="../include/footer.jsp"%>ㅍ
</div>
<!-- ./wrapper -->
<%@ include file="../include/plugin_js.jsp"%>
<script>
    var result = "${msg}";
    if (result == "regSuccess") {
        alert("게시글 등록이 완료되었습니다.");
    } else if (result == "modSuccess") {
        alert("게시글 수정이 완료되었습니다.");
    } else if (result == "delSuccess") {
        alert("게시글 삭제가 완료되었습니다.");
    }

    $(function(){
        $(".btn-flat").on("click", function () {
            self.location = "/article/write"
        });


        <%-- 3. 자바스크립트를 이용 할 경우 이벤트를 처리할 자바스크립트 코드를 작성해준다.
        $(".pagination li a").on("click", function (event) {
            event.preventDefault();
            var targetPage = $(this).attr("href");
            var listPageForm = $("#listPageForm");
            listPageForm.find("[name='page']").val(targetPage);
            listPageForm.attr("action", "/article/listPaging").attr("method", "get");
            listPageForm.submit();
        });
        --%>
    })

</script>
</body>
</html>
