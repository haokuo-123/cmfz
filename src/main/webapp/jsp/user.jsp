<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>用户管理</h2>
<script>
    $(function () {
        $("#userTable").jqGrid({
            url : '${pageContext.request.contextPath}/user/findAll',
            datatype : "json",
            colNames : [ '编号', '手机号', '头像', '密码','法名','真实姓名','性别','地区', '状态','注册日期','最后登录'],
            colModel : [
                {name : 'id',hidden:true,editable:false},
                {name : 'phone',editable:true},
                {
                    name: 'photo', align: "center", formatter: function (data) {
                        return "<img style='width: 180px;height: 80px' src='" + data + "'>"
                    }, editable: true, edittype: "file", editoptions: {enctype: "multipart/form-data"}
                },
                {name : 'password',editable:true},
                {name : 'name',editable:true},
                {name : 'nickName',editable:true},
                {name : 'sex',editable:true,edittype:"select",editoptions:{value:"男:男;女:女"}},
                {name : 'location',editable:true},
                {name : 'status',editable:true,edittype:"select",editoptions:{value:"正常:正常;冻结:冻结"}},
                {name : 'rigestDate',editable:true,edittype:"date"},
                {name : 'lastLogin',editable:true,edittype:"date"}
            ],
            height:250,
            autowidth:true,
            styleUI:"Bootstrap",
            rowNum : 3,
            rowList : [ 3,5,10 ],
            pager : '#userPage',
            sortname : 'id',
            viewrecords : true,
            multiselect:true,
            caption : "用户列表",
            editurl : "${pageContext.request.contextPath}/user/edit"
        }).navGrid("#userPage", {edit : true,add : true,del : true,search:false},{
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
                        url:"${pageContext.request.contextPath}/user/upload",
                        type:"post",
                        fileElementId:"photo",
                        data:{id:id},
                        success:function (response) {
                            //自动刷新jqgrid表格
                            $("#userTable").trigger("reloadGrid");
                        }
                    });
                }
                return "123";
            }
        });
    })




</script>
<table id="userTable"></table>
<div id="userPage" style="height: 40px"></div>