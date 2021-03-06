<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../../include/plugin_js.jsp"%>
    <script>
        $(function () {
            
            //댓글들
            var articleNo = "${article.articleNo}";//현재 게시글 번호
            console.log("articleNo:"+articleNo)
            var replyPageNum = 1;//댓글 페이지 번호 초기화
            
            //댓글 내용 : 줄바꿈/공백처리
            Handlebars.registerHelper("escape", function (replyText) {
                var text = Handlebars.Utils.escapeExpression(replyText);
                text = text.replace(/(\r\n|\n|\r)/gm,"<br/>");
                text = text.replace(/( )/gm,"&nbsp;");
                return new Handlebars.SafeString(text);
            });

            //댓글등록일자 : 날짜/시간 2자리로 맞추기
            Handlebars.registerHelper("prettifyDate", function (timeValue) {
                var dateObj = new Date(timeValue);
                var year = dateObj.getFullYear();
                var month = dateObj.getMonth() + 1;
                var date = dateObj.getHours();
                var hours = dateObj.getHours();
                var minutes = dateObj.getMinutes();

                //2자리 숫자로 변환
                month < 10 ? month = '0' + month : month;
                date < 10 ? date = '0' + date : date;
                hours < 10 ? hours = '0' + hours : hours;
                minutes < 10 ? minutes = '0' + minutes : minutes;
                return year + "-" + month + "-" + date + "-" + hours+ ":" + minutes;
            });

            //댓글목록 함수호출
            getReplies("/replies/" + articleNo + "/" + replyPageNum);
            
            //버튼 클릭할때 데이터 보내기
            var formObj = $("form[role='form']");

            $(".modBtn").on("click", function () {
                console.log(formObj);
                formObj.attr("action", "/article/reply/modify");
                formObj.attr("method", "get");
                formObj.submit();
            });

            $(".delBtn").on("click", function () {
                formObj.attr("action", "/article/reply/remove");
                formObj.submit();
            });

            $(".listBtn").on("click", function () {
                formObj.attr("method", "get");
                formObj.attr("action", "/article/reply/list");
                formObj.submit();
            });



            //댓글 목록 함수
            function getReplies(repliesUri) {
                $.getJSON(repliesUri, function (data) {
                    console.log(data);
                    printReplyCount(data.pageMaker.totalCount);
                    printReplies(data.replies, $(".repliesDiv"), $("#replyTemplate"));
                    printReplyPaging(data.pageMaker, $(".pagination"));
                })
            }

            function printReplyCount(totalCount) {
                var replyCount = $(".replyCount");
                var collapsedBox = $(".collapsed-box");

                // 댓글이 없으면
                if (totalCount === 0) {
                    replyCount.html(" 댓글이 없습니다. 의견을 남겨주세요");
                    collapsedBox.find(".btn-box-tool").remove();
                    return;
                }

                // 댓글이 존재하면
                replyCount.html(" 댓글목록 (" + totalCount + ")");
                collapsedBox.find(".box-tools").html(
                    "<button type='button' class='btn btn-box-tool' data-widget='collapse'>"
                    + "<i class='fa fa-plus'></i>"
                    + "</button>"
                );
            }


            function printReplies(replies, targetArea, templateObj) {
                var replyTemplate = Handlebars.compile(templateObj.html());
                var html = replyTemplate(replies);
                $(".replyDiv").remove();
                targetArea.html(html);
            }


            function printReplyPaging(pageMaker, targetArea) {
                var str = "";
                if (pageMaker.prev) {
                    str += "<li><a href='" + (pageMaker.startPage - 1) + "'>이전</a></li>";
                }
                for (var i = pageMaker.startPage, len = pageMaker.endPage; i <= len; i++) {
                    var strClass = pageMaker.criteria.page == i ? "class=active" : "";
                    str += "<li " + strClass + "><a href='" + i + "'>" + i + "</a></li>";
                }
                if (pageMaker.next) {
                    str += "<li><a href='" + (pageMaker.endPage + 1) + "'>다음</a></li>";
                }
                targetArea.html(str);
            }

            // 댓글 페이지 번호 클릭 이벤트
            $(".pagination").on("click", "li a", function (event) {
                event.preventDefault();
                replyPageNum = $(this).attr("href");
                getReplies("/replies/" + articleNo + "/" + replyPageNum);
            });

            // 댓글 저장 버튼 클릭 이벤트

            //댓글 저장 버튼 클릭 이벤트가 발생하면 jQuery 선택자를 통해 입력된 값들을 각각 변수에 담고 JSON으로 변환시켜 AJAX POST방식으로 값을 넘겨준다.
            //댓글 등록처리가 성공적으로 처리되면 결과값으로 받은 문자열(regSuccess)을 통해 알림창을 띄워준다.
            //페이지 번호는 1로 초기화하여 댓글 목록을 다시 호출해 첫번째 목록화면으로 이동한다.
            //마지막으로 댓글내용과 댓글 작성자 입력칸은 비워지게 처리를 해준다.

            $(".replyAddBtn").on("click", function () {

                // 입력 form 선택자
                var replyWriterObj = $("#newReplyWriter");
                var replyTextObj = $("#newReplyText");
                var replyWriter = replyWriterObj.val();
                var replyText = replyTextObj.val();

                // 댓글 입력처리 수행
                $.ajax({
                    type : "post",
                    url : "/replies/",
                    headers : {
                        "Content-Type" : "application/json",
                        "X-HTTP-Method-Override" : "POST"
                    },
                    dataType : "text",
                    data : JSON.stringify({
                        articleNo : articleNo,
                        replyWriter : replyWriter,
                        replyText : replyText
                    }),
                    success: function (result) {
                        console.log("result : " + result);
                        if (result === "regSuccess") {
                            alert("댓글이 등록되었습니다.");
                            replyPageNum = 1;  // 페이지 1로 초기화
                            getReplies("/replies/" + articleNo + "/" + replyPageNum); // 댓글 목록 호출
                            replyTextObj.val("");   // 댓글 입력창 공백처리
                            replyWriterObj.val("");   // 댓글 입력창 공백처리
                        }
                    }
                });
            });

            // 댓글 수정을 위해 modal창에 선택한 댓글의 값들을 세팅
            $(".repliesDiv").on("click", ".replyDiv", function (event) {
                var reply = $(this);
                $(".replyNo").val(reply.attr("data-replyNo"));
                $("#replyText").val(reply.find(".oldReplyText").text());
            });

// modal 창의 댓글 수정버튼 클릭 이벤트
            $(".modalModBtn").on("click", function () {
                var replyNo = $(".replyNo").val();
                var replyText = $("#replyText").val();
                $.ajax({
                    type : "put",
                    url : "/replies",
                    headers : {
                        "Content-Type" : "application/json",
                        "X-HTTP-Method-Override" : "PUT"
                    },
                    dataType : "text",
                    data: JSON.stringify({
                        replyNo:replyNo,
                        replyText : replyText
                    }),
                    success: function (result) {
                        console.log("result : " + result);
                        if (result === "modSuccess") {
                            alert("댓글이 수정되었습니다.");
                            getReplies("/replies/" + articleNo + "/" + replyPageNum); // 댓글 목록 호출
                            $("#modModal").modal("hide"); // modal 창 닫기
                        }
                    }
                })
            });

            /*$(".modalModBtn").on("click", function(){
                var reply = $(this).parent().parent();
                var replyNo = reply.find("#replyNo").val();
                var replyText = reply.find("#replyText").val();
                $.ajax({
                    type:"put",
                    url : "/replies",
                    headers:{
                        "Content-type" : "application/json",
                        "X-HTTP-Method-Override" : "PUT"
                    },
                    data : JSON.stringify({
                        replyNo:replyNo,
                        replyText:replyText
                    }),
                    dataType : "text",
                    success : function (result) {
                        console.log("result:"+result);
                        // 성공적인 댓글 등록처리 알림
                        if(result =="modSuccess"){
                            alert("댓글 수정 완료!");
                            $("#modifyModal").modal("hide");
                            getReplies();// 댓글 목록 출력 함수 호출
                        }
                    }
                });
            });*/

// modal 창의 댓글 삭제버튼 클릭 이벤트
            $(".modalDelBtn").on("click", function () {
                var replyNo = $(".replyNo").val();
                $.ajax({
                    type: "delete",
                    url: "/replies/" + replyNo,
                    headers: {
                        "Content-Type" : "application/json",
                        "X-HTTP-Method-Override" : "DELETE"
                    },
                    dataType: "text",
                    success: function (result) {
                        console.log("result : " + result);
                        if (result === "delSuccess") {
                            alert("댓글이 삭제되었습니다.");
                            getReplies("/replies/" + articleNo + "/" + replyPageNum); // 댓글 목록 호출
                            $("#delModal").modal("hide"); // modal 창 닫기
                        }
                    }
                });
            });
        });

    </script>
</head>
<%@ include file="../../include/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <%@ include file="../../include/header.jsp"%>
    <%@ include file="../../include/left.jsp"%>
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
                        <h3 class="box-title">글제목 ::: ${article.title}</h3>
                    </div>
                    <div class="box-body" style="height: 700px">
                        ${article.content}
                    </div>
                    <div class="box-footer">
                        <div class="user-block">
                            <img class="img-circle img-bordered-sm" src="" alt="user image">
                            <span class="username">
                                <a href="#">${article.writer}</a>
                            </span>
                            <span class="description"><fmt:formatDate pattern="yyyy-MM-dd a HH:mm" value="${article.regDate}"/></span>
                        </div>
                    </div>
                    <div class="box-footer">
                        <form role="form" method="post">
                            <input type="hidden" name="articleNo" value="${article.articleNo}">
                            <%-- 목록페이지의 정보를 저장하기위해  JSP 추가 --%>
                            <input type="hidden" name="page" value="${searchCriteria.page}">
                            <input type="hidden" name="perPageNum" value="${searchCriteria.perPageNum}">
                            <input type="hidden" name="searchType" value="${searchCriteria.searchType}">
                            <input type="hidden" name="keyword" value="${searchCriteria.keyword}">
                        </form>
                        <button type="submit" class="btn btn-primary listBtn"><i class="fa fa-list"></i> 목록</button>
                        <div class="pull-right">
                            <button type="submit" class="btn btn-warning modBtn"><i class="fa fa-edit"></i> 수정</button>
                            <button type="submit" class="btn btn-danger delBtn"><i class="fa fa-trash"></i> 삭제</button>
                        </div>
                    </div>

<%--댓글 작성--%>
                    <div class="box box-warning">
                        <div class="box-header with-border">
                            <a class="link-black text-lg"><i class="fa fa-pencil"></i> 댓글작성</a>
                        </div>
                        <div class="box-body">
                            <form class="form-horizontal">
                                <div class="form-group margin">
                                    <div class="col-sm-10">
                                        <textarea class="form-control" id="newReplyText" rows="3" placeholder="댓글내용..." style="resize: none"></textarea>
                                    </div>
                                    <div class="col-sm-2">
                                        <input class="form-control" id="newReplyWriter" type="text" placeholder="댓글작성자...">
                                    </div>
                                    <hr/>
                                    <div class="col-sm-2">
                                        <button type="button" class="btn btn-primary btn-block replyAddBtn"><i class="fa fa-save"></i> 저장</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <%--댓글 목록--%>
                    <%-- Handlebars 를 이용한 JS 템플릿을 적용할거야 --%>
                    <div class="box box-success collapsed-box">
                        <%--댓글 유무 / 댓글 갯수 / 댓글 펼치기, 접기--%>
                        <div class="box-header with-border">
                            <a href="" class="link-black text-lg"><i class="fa fa-comments-o margin-r-5 replyCount"></i></a>
                            <div class="box-tools">
                                <button type="button" class="btn btn-box-tool" data-widget="collapse">
                                    <i class="fa fa-plus"></i>
                                </button>
                            </div>
                        </div>
                        <%--댓글 목록--%>
                        <div class="box-body repliesDiv">

                        </div>
                        <%--댓글 페이징--%>
                        <div class="box-footer">
                            <div class="text-center">
                                <ul class="pagination pagination-sm no-margin" >

                                </ul>
                            </div>
                        </div>
                    </div>


                </div>
            </div>

            <%--댓글 수정 modal 영역--%>
            <div class="modal fade" id="modModal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">댓글수정</h4>
                        </div>
                        <div class="modal-body" data-rno>
                            <input type="hidden" class="replyNo"/>
                            <%--<input type="text" id="replytext" class="form-control"/>--%>
                            <textarea class="form-control" id="replyText" rows="3" style="resize: none"></textarea>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default pull-left" data-dismiss="modal">닫기</button>
                            <button type="button" class="btn btn-primary modalModBtn">수정</button>
                        </div>
                    </div>
                </div>
            </div>

            <%--댓글 삭제 modal 영역--%>
            <div class="modal fade" id="delModal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">댓글 삭제</h4>
                            <input type="hidden" class="rno"/>
                        </div>
                        <div class="modal-body" data-rno>
                            <p>댓글을 삭제하시겠습니까?</p>
                            <input type="hidden" class="rno"/>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default pull-left" data-dismiss="modal">아니요.</button>
                            <button type="button" class="btn btn-primary modalDelBtn">네. 삭제합니다.</button>
                        </div>
                    </div>
                </div>
            </div>



        </section>

        <!-- /.content -->
    </div>
    <%@ include file="../../include/footer.jsp"%>
</div>
<!-- ./wrapper -->

</body>

<%-- 댓글 목록을 출력하기 위한 템플릿 코드(하나의 댓글을 구성한다) --%>
<%-- 댓글 등록일자의 prettifyDate와 댓글 내용의 escape은 'Handlebars의 확장기능을 사용한 것' 으로 아래의 JS코드에서 처리해준다. --%>
<script id="replyTemplate" type="text/x-handlebars-template">
    {{#each.}}
    <div class="post replyDiv" data-replyNo="{{replyNo}}">
        <div class="user-block">
            <img class="img-circle img-bordered-sm" src="/dist/img/user1-128-128.jsp" alt="user image">
            <span class="username">
                <a href="#">{{replyWriter}}</a>
                <a href="#" class="pull-right btn-box-tool replyDelBtn" data-toggle="modal" data-target="#delModal">
                    <i class="fa fa-times">삭제</i>
                </a>
                <a href="#" class="pull-right btn-box-tool replyModBtn" data-toggle="modal" data-target="#modModal">
                    <i class="fa fa-edit">삭제</i>
                </a>
            </span>
            <span class="description">{{prettifyDate regDate}}</span>
        </div>
        <div class="oldReplyText">{{{escape replyText}}}</div>
        <br/>
    </div>
    {{/each}}
</script>
</html>
