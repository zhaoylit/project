<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
    $(function() {
        /*  加载页面时初始化数据表格 */
        $('#tableList').datagrid({
 			title:'广告预览与审核页面',
            url:'${ctx}/javascript/js/adSource.json',
            loadFilter:pagerFilter,
            toolbar:'#tb',
            fit:true,
            method:'post',
            loadMsg:'数据加载中,请稍等...',
            fitColumns:true,
            idField:"id",
            pagination:true,
            pageSize:10,
            pageList:[5,10,15,20,100],//每页的个数
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck: true,
            sortName:'id',
            sortOrder:'desc',
            queryParams: {
            },
            toolbar: [],
            columns:[[
                {field:'ck',width:'3%',checkbox:"true"},
                {field:'id',title:'ID',width:'3%',align:'center'},
                {field:'airportName',title:'机场',width:'12%',align:'center'},
                {field:'cusName',title:'客户名称',width:'8%',align:'center'},
                {field:'freq',title:'播放频次',width:'5%',align:'center'},
                {field:'name',title:'名称',width:'7%',align:'center'},
                {field:'adtype',title:'广告播放形式类型',width:'8%',align:'center'},
                {field:'pubbegintime',title:'开始时间',width:'12%',align:'center'},
                {field:'pubendtime',title:'结束时间',width:'12%',align:'center'}	,
                {field:'uploadtime',title:'上传时间',width:'12%',align:'center'}	,
                {field:'checkStateName',title:'审核状态',width:'6%',align:'center'}	,
                {field:'operator',title:'操作',width:'12%',align:'center',formatter:function(value,row,index){
                    return '<a class="editCls" href="javascript:MediaLook(\''+row.adtype+'\');"></a><a class="check" id="check'+row.id+'"  href="javascript:confirm(\''+row.id+'\');"></a>';
                }},
            ]],
            onBeforeLoad:function(data){
                //添加搜索条件
                if($(".searchBox").length == 0){
                    var searchTool = '<div class="searchBox" style="padding:3px 0 0 25px;height:40px;line-height:30px;">';
                    searchTool+='<td>机场:&nbsp;<input id="airportId" style="width:150px;height:30px;"></td>&nbsp;&nbsp;&nbsp;&nbsp;';
                    searchTool+='<td style="">广告客户:&nbsp;<input id="cusid" style="width:150px;height:30px;">';
                    searchTool+='<td><a href="javascript:reloadTable();" class="easyui-linkbutton sousuo"  style="margin-left: 2%;"></a></td>';
                    searchTool+='<td style=""><a href="javascript:deleteAdInit();" class="easyui-linkbutton delete"  style="margin-left: 2%;"></a></td>';
                    searchTool+='<td><a href="javascript:delever();" class="easyui-linkbutton delever"  style="margin-left: 2%;"></a></td>';
                    searchTool+='</div>';
                    $(".datagrid-toolbar").append(searchTool);
                    //初始化机场数据
                        $('#airportId').combobox({
                            url:'${ctx}/javascript/js/airport.json',
                            valueField:'airportId',
                            textField:'airportName'
                        });
                    //初始化广告客户数据
                    $('#cusid').combobox({
                        url:'${ctx}/javascript/js/ad_person.json',
                        valueField:'cusid',
                        textField:'cusName'
                    });

                    /* $.post("type.json",{},function(data){
                     data.splice(0,0,{dictKey:'-1',dictValue:'全部'});
                     $("#activityTypeSearch").combobox({
                     data:data,
                     editable:false,
                     valueField:'dictKey',
                     textField:'dictValue',
                     onSelect:function(record){
                     $("#activityTypeSearch").val(record.dictKey);
                     reloadTable();
                     }
                     });
                     },"json");*/
                }
            },
            onLoadSuccess:function(data){
            	if(data.result=='0'){
    	    		$.messager.alert('提示',data.message,'error');
    	    		return;
    	    	}
                //初始化编辑按钮
                $('.editCls').linkbutton({text:'预览',plain:true,iconCls:'icon-edit'});
                $('.check').linkbutton({text:'审核',plain:true,iconCls:'icon-tip'});
                $('.sousuo').linkbutton({text:'搜索',plain:true,iconCls:'icon-search'});
                $('.delete').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
                $('.delever').linkbutton({text:'发布',plain:true,iconCls:'icon-print'});
                //初始化表格提示插件
                $('#tableList').datagrid('doCellTip', {
                    onlyShowInterrupt : true,
                    position : 'bottom',
                    maxWidth : '200px',
                    specialShowFields : [{
                        field : 'status',
                        showField : 'statusDesc'
                    }],
                    tipStyler : {
                        'backgroundColor' : '#fff000',
                        borderColor : '#ff0000',
                        boxShadow : '1px 1px 3px #292929'
                    }
                });
            }
        });
    });

    // 审核通过之后发布的一条广告
    function delever(){
        $.messager.confirm('请确认','确定要将已审核和待审核的广告发布吗?',function(r){
            if(r){
                $.ajax({
                    type:'post',
                    url:"",   ///////////////后台的广告发布接口
                    dataType:'json',
                    success:function(data){
                        if(data.result == "1"){
                            $.messager.alert('提示',data.message,'info', function(r){
                                reloadTable();
                                dialog.dialog('close');
                            });
                        }else{
                            $.messager.alert('操作失败',data.message,'error');
                        }
                    },

                });

            }
        });
    }
    // 删除一条广告信息
    //刷新表格
    function reloadTable(){
        $('#tableList').datagrid('load', {
            airportId:$("#airportId").val(),
            cusid:$("#cusid").val(),
        });
    }
    //  确认审核页面的弹出框
    function confirm(id) {
                var check=$("#check"+id).text();
                if(check=="已审核"){
//                    alert("该广告已经审核通过,请勿重复审核");
                    $.messager.alert('提示信息','该广告已经审核通过,请勿重复审核！');
                }else{
                    $.messager.confirm('请确认','确定审核这条广告吗？',function(r){
                        if(r){
//                           alert("要把你选中的这个OK信息传给后台");
//                            $("#check"+id).text("已审核").css("color","red");
                            $.ajax({
                                type:'post',
                                url:"",
                                data:{
                                    id:id,
                                },
                                success:function(data){
                                    if(data.result == "1"){
                                        $.messager.alert('提示',data.message,'info', function(r){
                                            reloadTable();
                                            dialog.dialog('close');
                                        });
                                    }else{
                                        $.messager.alert('操作失败',data.message,'error');
                                    }
                                },
                                dataType:'json'
                            });

                        }
                    });

                }

            }
    // 编辑预览视频和图片的弹出框
    function MediaLook(adtype){
        var dialog = $('<div></div>').dialog({
            title:adtype != "视频" ? "图片预览页面" : "视频预览页面",
            height:'80%',
            width:'60%',
            top:'10%',
            minimizable:false,
            maximizable:false,
            collapsible:false,
            shadow: true,
            modal: true,
            href:'Media.html',
            onClose:function(){
                $(this).dialog('destroy');
            },
            buttons : [
                {
                    text : '确认审核',
                    iconCls : 'icon-cancel',
                    handler : function() {
                        dialog.dialog('destroy');
                    }
                },
                {
                    text : '关闭',
                    iconCls : 'icon-cancel',
                    handler : function() {
                        dialog.dialog('destroy');
                    }
                }
            ]
        });
    }
    //批量删除条目
    function deleteAdInit(){
        var ids = [];
        var checkedItems = $('#tableList').datagrid('getChecked');
        $.each(checkedItems,function(index,item){
            ids.push(item.id);
        });
        if(ids.length == 0){
            $.messager.alert('提示',"请选择要删除的用户",'warning');
            return;
        }
        $.messager.confirm("确认信息","确定删除选中的"+ids.length+"条数据吗？", function (r) {
            if (r){
                $.ajax({
                    type:'post',
                    url:"",  //后台给的删除接口
                    data:{
                        ids:ids.join(",")
                    },
                    success:function(data){
                        if(data.result == "1"){
                            $.messager.alert('提示',"操作成功",'info', function(r){
                                reloadTable();
                            });
                        }else{
                            $.messager.alert('操作失败',data.message,'error');
                        }
                    },
                    dataType:'json'
                });
            }
        });
        return false;
    }
    // 分页过滤器的修改
    function pagerFilter(data){
        if (typeof data.length == 'number' && typeof data.splice == 'function'){// is array
            data = {
                total: data.length,
                rows: data
            }
        }
        var dg = $(this);
        var opts = dg.datagrid('options');
        var pager = dg.datagrid('getPager');
        pager.pagination({
            onSelectPage:function(pageNum, pageSize){
                opts.pageNumber = pageNum;
                opts.pageSize = pageSize;
                pager.pagination('refresh',{pageNumber:pageNum,pageSize:pageSize});
                dg.datagrid('loadData',data);
            }
        });
        if (!data.originalRows){
            data.originalRows = (data.rows);
        }
        var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
        var end = start + parseInt(opts.pageSize);
        data.rows = (data.originalRows.slice(start, end));
        return data;
    }
</script>
<table id="tableList" style="width:100%;margin:20px;"></table>
</body>
</html>