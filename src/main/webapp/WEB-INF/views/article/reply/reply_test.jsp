<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
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
                        <h3 class="box-title">댓글 작성</h3>
                    </div>
                    <div class="box-body">
                        <div class="form-group">
                            <label for="newReplyText">댓글 내용</label>
                            <input class="form-control" id="newReplyText" name="replyText" placeholder="댓글 내용을 입력해주세요">
                        </div>
                        <div class="form-group">
                            <label for="newReplyWriter">댓글 작성자</label>
                            <input class="form-control" id="newReplyWriter" name="replyWriter" placeholder="댓글 작성자를 입력해주세요">
                        </div>
                        <button type="button" class="replyAddBtn">댓글저장</button>
                    </div>
                    <div class="box-footer">
                        <ul id="replies">

                        </ul>
                    </div>
                    <div class="box-footer">
                        <div class="text-center">
                            <ul class="pagination pagination-sm no-margin">

                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="modifyModal" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">댓글 수정창</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="replyNo">댓글 번호</label>
                                <input class="form-control" id="replyNo" name="replyNo" readonly>
                            </div>
                            <div class="form-group">
                                <label for="replyText">댓글 내용</label>
                                <input class="form-control" id="replyText" name="replyText" placeholder="댓글 내용을 입력해주세요">
                            </div>
                            <div class="form-group">
                                <label for="replyWriter">댓글 작성자</label>
                                <input class="form-control" id="replyWriter" name="replyWriter" readonly>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default pull-left" data-dismiss="modal">닫기</button>
                            <button type="button" class="btn btn-success modalModBtn">수정</button>
                            <button type="button" class="btn btn-danger modalDelBtn">삭제</button>
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
<%@ include file="../../include/plugin_js.jsp"%>
</body>
<script>
    var articleNo = 1000;
    getReplies();

    function getReplies() {
        //@RestController의 경우 객체를 JSON방식으로 전달하기 때문에
        // jQuery를 이용해서 호출할 때는 getJSON()를 아래와 같이 사용한다.
        $.getJSON("/replies/"+articleNo, function (data) {
            console.log(data);

            var str = "";
            $(data).each(function () {//받아온 댓글 객체를 each()함수를 통해 루프를 돌면서 <li>태그를 만들어낸다.
                //data-로 시작되는 속성은 이름이나 갯수에 상관없이 id나 name속성을 대신해서 사용하기에 편리하다.
                str += "<li data-replyNo = '"+this.replyNo+"' class='replyLi'>"
                    +"<p class='replyText'>"+this.replyText+"</p>"
                    +"<p class='replyWriter'>"+this.replyWriter+"</p>"
                    +"<button type='button' class='btn btn-xs btn-success' data-toggle='modal' data-target='#modifyModal'>댓글 수정</button>"
                    +"</li>"
                    +"<hr/>";
            });

            $("#replies").html(str);
        });
    }

    $(".replyAddBtn").on("click", function () {
        //화면에서 입력받은 변수값 처리
        var replyText = $("#newReplyText");
        var replyWriter = $("#newReplyWriter");

        var replyTextVal = replyText.val();
        var replyWriterVal = replyWriter.val();

        //AJAX 통신 : POST
        $.ajax({
            type:"post",
            url:"/replies",
            headers:{
                "Content-type":"application/json",
                "X-HTTP-Method-Overried":"POST"
            },
            dataType : "text",
            data : JSON.stringify({
                articleNo : articleNo,
                replyText : replyTextVal,
                replyWriter : replyWriterVal
            }),
            success : function (result) {
                // 성공적인 댓글 등록처리 알림
                if(result =="regSuccess"){
                    alert("댓글 등록완료!");
                }
                getReplies();// 댓글 목록 출력 함수 호출
                replyText.val("");// 댓글 내용 초기화a
                replyWriter.val("");// 댓글 작성자 초기화
            }
        })
    })

    $("#replies").on("click", ".replyLi button", function(){
        var reply = $(this).parent();
        var replyNo = reply.attr("data-replyNo");
        var replyText = reply.find(".replyText").text();
        var replyWriter = reply.find(".replyWriter").text();
        $("#replyNo").val(replyNo);
        $("#replyText").val(replyText);
        $("#replyWriter").val(replyWriter);
    });

    $(".modalModBtn").on("click", function(){
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
        })

    });

    $(".modalDelBtn").on("click", function(){
        var replyNo = $(this).parent().parent().find("#replyNo").val();
        $.ajax({
            type:"delete",
            url:"/replies/"+articleNo,
            headers:{
                "Content-type":"application/json",
                "X-HTTP-Method-Overried":"DELETE"
            },
            dataType : "text",
            success : function (result) {
                console.log("result:"+result);
                // 성공적인 댓글 등록처리 알림
                if(result =="delSuccess"){
                    alert("댓글 삭제 완료!");
                    $("#modifyModal").modal("hide");
                    getReplies();// 댓글 목록 출력 함수 호출
                }
            }
        })

    });

</script>
</html>

<%--
<html>
	<body>
		<ul>
			<li>1...</li>
			...
			<li>1000...</li>
			<!-- 이런 li가 1000개! -->
		</ul>
	</body>
</html>

일단 가장 먼저 생각나는 코드는 아래와 같다.
$("ul li").on("click", function(){
	// ...
});
동작은 할것으로 보이지만 이렇게 코드를 작성하는 경우 이벤트 바인드 개수가 li 요소 개수만큼이다. 어떻게 보면 비효율처럼 보인다. 이런 경우를 위해 다음과 같은 방식도 존재한다.


$("ul").on("click", "li", function(){
	// ...
});
위 같은 코드는 ul 태그 하나에 이벤트를 붙인다. 하지만 하위 li 요소에 대해 이벤트를 지정할 수 있다. 따라서 하나의 이벤트 바인딩으로 깔끔한 처리가 가능한 것이다.
--%>

