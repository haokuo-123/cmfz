<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>轮播图管理</h2>
<script>
    $(function () {
        $("#bannerTable").jqGrid({
            url : '${pageContext.request.contextPath}/banner/findAll',
            datatype : "json",
            colNames : [ '编号', '名称', '封面', '描述', '状态','上传日期'],
            colModel : [
                {name : 'id',hidden:true,editable:false},
                {name : 'title',editable:true},
                {name : 'cover',editable:true,edittype:"file",formatter:function (value,option,rows) {
                        return "<img style='width:100px;height:60px;' src='${pageContext.request.contextPath}/banner/image/"+rows.cover+"'>";
                    }},
                {name : 'description',editable:true},
                {name : 'status',editable:true,edittype:"select",editoptions:{value:"正常:正常;冻结:冻结"}},
                {name : 'createDate'}
            ],
            height:250,
            autowidth:true,
            styleUI:"Bootstrap",
            rowNum : 3,
            rowList : [ 3,5,10 ],
            pager : '#bannerPage',
            sortname : 'id',
            viewrecords : true,
            multiselect:true,
            caption : "轮播图列表",
            editurl : "${pageContext.request.contextPath}/banner/edit"
        }).navGrid("#bannerPage", {edit : true,add : true,del : true,search:false},{
            //控制修改
            closeAfterEdit:true,
            beforeShowForm:function (fmt) {
                fmt.find("#cover").attr("disabled",true);
            }
        },{
            //控制添加
            closeAfterAdd:true,
            afterSubmit:function (data) {
                var status = data.responseJSON.status;
                var id = data.responseJSON.message;
                if(status){
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/banner/upload",
                        type:"post",
                        fileElementId:"cover",
                        data:{id:id},
                        success:function (response) {
                            //自动刷新jqgrid表格
                            $("#bannerTable").trigger("reloadGrid");
                        }
                    });
                }
                return "123";
            }
        });
    })


    function showExcel(){
        $("#myModal1").modal("show");
    }
    function sub1() {
        $.ajaxFileUpload({
            url: "${pageContext.request.contextPath}/banner/put",
            type: "post",
            data: {
            },
            datatype: "json",
            fileElementId: "file",
            success: function (data) {
                $("#bannerTable").trigger("reloadGrid");
                $("#myModal1").modal("hide");
            }
        })

    }








</script>

<ul class="nav nav-tabs">
    <li><a href="${pageContext.request.contextPath}/banner/export">导出轮播图信息</a></li>
    <li><a onclick="showExcel()">导入轮播图信息</a></li>
    <li><a>Excel模板下载</a></li>
</ul>
<table id="bannerTable"></table>
<div id="bannerPage" style="height: 40px"></div>

