<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>管理员登录</title>
    <link href="favicon.ico" rel="shortcut icon" />
    <link href="../boot/css/bootstrap.min.css" rel="stylesheet">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script>
        $(function () {
            $("#captchaImage").click(function () {
                $("#captchaImage").prop("src", "${pageContext.request.contextPath}/code/getCode?time=" + new Date().getTime());
            });
        });

        $(function () {
            $("#log").click(function () {
                var username = $("#form-username").val();
                var password = $("#form-password").val();
                var inputCode = $("#form-code").val();
                if(username && password && inputCode){
                    $.ajax({
                        url: "${pageContext.request.contextPath}/admin/login",
                        type: "POST",
                        data: $("#loginForm").serialize(),
                        dataType: "json",
                        success: function (data) {
                            if(data.status){
                                location.href = "${pageContext.request.contextPath}/jsp/main.jsp"
                            }else{
                                $("#msg").html("<font color='red'>"+data.message+"<font>");
                            }
                        }
                    })
                }else{
                    $("#msg").html("<font color='red'>请输入完整信息<font>");
                }
            })
        })



    </script>
</head>
<body style=" background: url(..); background-size: 100%;">


<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">

            <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
        </div>
        <form id="loginForm" method="post" action="">
            <span id="msg"></span>
        <div class="modal-body" id = "model-body">
            <div class="form-group">
                <input type="text" class="form-control"placeholder="用户名" id="form-username" autocomplete="off" name="username">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" id="form-password" placeholder="密码" autocomplete="off" name="password">
            </div>
            <div class="form-group">
                <div class="container-fluid">
                    <div class="row">
                    <div class="col-sm-8" style="padding-left: 0px;">
                        <input class="form-control" type="text" name="inputCode" id="form-code" required placeholder="验证码">
                    </div>
                    <div class="col-sm-2">
                <img id="captchaImage" class="captchaImage" src="${pageContext.request.contextPath}/code/getCode">
                    </div>

                </div>
                </div>
            </div>

        </div>
        <div class="modal-footer">
            <div class="form-group">
                <button type="button" class="btn btn-primary form-control" id="log">登录</button>
            </div>

        </div>
        </form>
    </div>
</div>
</body>
</html>
