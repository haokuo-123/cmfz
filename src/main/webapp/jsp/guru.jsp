<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>上师管理</h2>
<script>
    $(function () {
        $("#guruTable").jqGrid({
            url : '${pageContext.request.contextPath}/guru/findAllGuru',
            datatype : "json",
            colNames : [ '编号', '姓名', '头像', '状态','真实姓名'],
            colModel : [
                {name : 'id',hidden:true,editable:false},
                {name : 'name',editable:true},
                {
                    name: 'photo', align: "center", formatter: function (data) {
                        return "<img style='width: 180px;height: 80px' src='" + data + "'>"
                    }, editable: true, edittype: "file", editoptions: {enctype: "multipart/form-data"}
                },
                {name : 'status',editable:true,edittype:"select",editoptions:{value:"正常:正常;冻结:冻结"}},
                {name : 'nickName',editable:true}
            ],
            height:250,
            autowidth:true,
            styleUI:"Bootstrap",
            rowNum : 3,
            rowList : [ 3,5,10 ],
            pager : '#guruPage',
            sortname : 'id',
            viewrecords : true,
            multiselect:true,
            caption : "用户列表",
            editurl : "${pageContext.request.contextPath}/guru/edit"
        }).navGrid("#guruPage", {edit : true,add : true,del : true,search:false},{
            //控制修改
            closeAfterEdit:true,
            beforeShowForm:function (fmt) {
                fmt.find("#photo").attr("disabled",true);
            }
        },{
            //控制添加
            closeAfterAdd:true,
            afterSubmit:function (data) {
                var status = data.responseJSON.status;
                var id = data.responseJSON.message;
                if(status){
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/guru/upload",
                        type:"post",
                        fileElementId:"photo",
                        data:{id:id},
                        success:function (response) {
                            //自动刷新jqgrid表格
                            $("#guruTable").trigger("reloadGrid");
                        }
                    });
                }
                return "123";
            }
        });
    })




</script>
<table id="guruTable"></table>
<div id="guruPage" style="height: 40px"></div>