<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

            <h3><i class="fa fa-warning text-red"></i> ${exception.getMessage()}</h3>
            <ul>
                <c:forEach items="${exception.getStackTrace()}" var="stack">
                    <li>${stack.toString()}</li>
                </c:forEach>
            </ul>

        </section>
        <!-- /.content -->
    </div>
    <%@ include file="../include/footer.jsp"%>
</div>
<!-- ./wrapper -->
<%@ include file="../include/plugin_js.jsp"%>
</body>
</html>

