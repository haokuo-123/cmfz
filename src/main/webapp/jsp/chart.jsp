<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="../boot/css/back.css">
    <link rel="stylesheet" href="../jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="../jqgrid/css/jquery-ui.css">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script src="../boot/js/bootstrap.min.js"></script>
    <script src="../jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="../jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="../boot/js/ajaxfileupload.js"></script>
    <script src="../echarts/echarts.min.js"></script>
    <!-- 将https协议改为http协议 -->
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script type="text/javascript">
        var goEasy = new GoEasy({
            host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
           /* appkey: "BC-69489dfd130b4b8195d4b1a675650c13",*/ //替换为您的应用appkey
            appkey: "BC-e16fb777a6b2422fa18ac579f346cf48", //替换为您的应用appkey
        });
        goEasy.subscribe({
            channel: "cmfz", //替换为您自己的channel
            onMessage: function (message) {
                var data = JSON.parse(message.content);
                $("#show").append("<p>"+"<h3 style='color:fuchsia'>"+data+"</h3>"+"</p>");
            }
        });

        $(function () {

            $("#send").click(function () {

                    $.ajax({
                        url: "${pageContext.request.contextPath}/user/chart",
                        type: "POST",
                        data: $("#MessageForm").serialize(),
                        dataType: "json",
                        success: function (data) {

                        }
                    })
                $("#message-name").val("");
            })
        })


    </script>

</head>
<body>
<div class="row">
<div class="col-lg-6 col-md-offset-3">
<div style="width: 750px;height: 500px; background-color:#f8da4e; overflow: auto">
    <h1 style="text-align: center; color:red">聊天室</h1>

   <span id="show"></span>

</div>
<br/>
<form id="MessageForm" method="post" action="">


    <div class="input-group">
        <input type="text" class="form-control" id="message-name"  name="message">
        <span class="input-group-btn">
        <button class="btn btn-default" type="button" id="send">发送</button>
      </span>
    </div><!-- /input-group -->

</form>
</div>
</div>
</body>