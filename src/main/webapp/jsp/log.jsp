<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>日志管理</h2>
<script>
    $(function () {

        $("#logTable").jqGrid({
            styleUI: 'Bootstrap',
            url: "${pageContext.request.contextPath}/log/findAll",//用来加载远程数据
            datatype: "json",  //用来指定返回数据类型
            cellEdit: false,//开启单元格编辑
            autowidth: true,//自适应父容器
            colNames: ["编号", "执行的操作", "管理员", "操作时间","执行结果"],   //表格标题
            pager:"#logPage",
            height:250,
            rowNum : 3,
            caption : "日志列表",
            rowList : [ 3,5,10 ],
            closeAfterEdit:true,
            editurl:"${pageContext.request.contextPath}/log/edit",
            colModel: [
                {
                    name: "id",hidden:true

                },
                {
                    name: "thing"
                },
                {
                    name: "name"
                },
                {
                    name: "date",
                },
                {
                    name: "flag",
                },
            ]
        }).jqGrid('navGrid', '#logPage', {edit : false,add : false,del : true});

    });
</script>
<table id="logTable"></table>
<div id="logPage" style="height: 40px"></div>


