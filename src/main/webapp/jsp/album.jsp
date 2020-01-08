<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $(function () {
        $("#albumTable").jqGrid(
            {
                url : '${pageContext.request.contextPath}/album/findAll',
                datatype : "json",
                height : 300,
                colNames : [ 'ID', '标题', '封面','分数', '作者', '播音','章节数', '描述','状态','创建时间' ],
                colModel : [
                    {name : 'id',hidden:true,align:"center"},
                    {name : 'title',align:"center",editable:true},
                    {name : 'cover',align:"center",formatter:function (data) {
                            return "<img style='height: 80px;width: 180px' src='"+data+"'>";
                        },editable:true,edittype: "file",editoptions:{enctype:"multipart/form-data"}},
                    {name : 'score',align:"center",editable:true},
                    {name : 'author',align:"center",editable:true},
                    {name : 'broadcast',align:"center",editable:true},
                    {name : 'count',align:"center",editable:true},
                    {name : 'description',align:"center",editable:true},
                    {name : 'status',align:"center",formatter:function (data) {
                            if (data=="1"){
                                return "展示";
                            } else return "冻结";
                        },editable:true,edittype:"select",editoptions: {value:"1:展示;2:冻结"}},
                    {name : 'createDate',align:"center",editable:true,edittype:"date"}

                ],
                rowNum : 2,
                rowList : [ 8, 10, 20, 30 ],
                pager : '#albumPage',
                sortname : 'id',
                viewrecords : true,
                sortorder : "desc",
                multiselect : true,
                autowidth:true,
                styleUI:"Bootstrap",
                subGrid : true,
                caption : "专辑列表",
                editurl:'${pageContext.request.contextPath}/album/edit',
                // 添加子表格方法
                subGridRowExpanded : function(subgrid_id, row_id) {
                    addSubgrid(subgrid_id,row_id);
                },
                subGridRowColapsed : function(subgrid_id, row_id) {
                    // this function is called before removing the data
                    //var subgrid_table_id;
                    //subgrid_table_id = subgrid_id+"_t";
                    //jQuery("#"+subgrid_table_id).remove();
                }
            });
        $("#albumTable").jqGrid('navGrid', '#albumPage', {
                add : true,
                edit : true,
                del : true
            },
            // {} 编辑 {} 添加 {} 删除
            {
                closeAfterEdit:true,
                beforeShowForm:function (fmt) {
                    fmt.find("#cover").attr("disabled",true);
                }
            },{
                closeAfterAdd:true,
                afterSubmit:function (data) {
                    var status = data.responseJSON.status;
                    var id = data.responseJSON.message;
                    if(status){
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/album/upload",
                        type:"post",
                        datatype:"json",
                        data:{id:id},
                        fileElementId:"cover",
                        success:function (response) {
                            $("#albumTable").trigger("reloadGrid")
                        }
                    });
                    }
                    return "123";
                }

            },{
                closeAfterDel:true,
            }
        );
    });
    // subgrid_id: 行id  row_id: 数据id
    function addSubgrid(subgrid_id,row_id){
        var subgrid_table_id, pager_id;
        subgrid_table_id = subgrid_id + "_t";
        pager_id = "p_" + subgrid_table_id;
        $("#" + subgrid_id).html(
            "<table id='" + subgrid_table_id
            + "' class='scroll'></table><div id='"
            + pager_id + "' class='scroll'></div>");
        $("#" + subgrid_table_id).jqGrid(
            {
                url : "${pageContext.request.contextPath}/chapter/findAll?albumId="+row_id,
                datatype : "json",
                colNames : [ 'Id', '标题', '大小','时长','创建时间','操作'],
                colModel : [
                    {name : "id",align:"center",hidden: true},
                    {name : "title",align:"center",editable:true},
                    {name : "size",align:"center"},
                    {name : "time",align:"center"},
                    {name : "createTime",align:"center",editable:true,edittype:"date"},
                    {name : "url",formatter:function (cellvalue, options, rowObject) {
                            var button = "<button type=\"button\" class=\"btn btn-primary\" onclick=\"download('"+cellvalue+"')\">下载</button>&nbsp;&nbsp;";
                            //
                            button+= "<button type=\"button\" class=\"btn btn-warning\" onclick=\"onPlay('"+cellvalue+"')\">在线播放</button>";
                            return button;
                        },editable:true,edittype:"file",editoptions:{enctype:"multipart/form-data"}}
                ],
                rowNum : 2,
                pager : pager_id,
                height : '100%',
                styleUI:"Bootstrap",
                multiselect : true,
                autowidth: true,
                editurl: "${pageContext.request.contextPath}/chapter/editChapter?albumId="+row_id
            });
        $("#" + subgrid_table_id).jqGrid('navGrid',
            "#" + pager_id, {
                edit : true,
                add : true,
                del : true
            },
            // {} 编辑 {} 添加 {} 删除
            {
                closeAfterEdit:true,
                beforeShowForm:function (fmt) {
                    fmt.find("#url").attr("disabled",true);
                }
            },{
                closeAfterAdd:true,
                afterSubmit:function (response,postData) {
                    var chapterId = response.responseJSON.chapterId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/chapter/upload",
                        type:"post",
                        datatype:"json",
                        data:{chapterId:chapterId},
                        fileElementId:"url",
                        success:function (data) {
                            $("#" + subgrid_table_id).trigger("reloadGrid");
                        }
                    });
                    return postData;
                }

            },{
                closeAfterDel:true,
            });
    }
    function onPlay(cellValue) {
        $("#music").attr("src",cellValue);
        $("#myModal").modal("show");
    }
    function download(cellValue) {
        location.href = "${pageContext.request.contextPath}/chapter/download?url="+cellValue;
    }
</script>

<h2>专辑管理</h2>
<div class="panel">
    <table id="albumTable"></table>
    <div id="albumPage" style="height: 50px"></div>
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <audio id="music" src="" controls="controls">
        </audio>
    </div><!-- /.modal -->

</div>