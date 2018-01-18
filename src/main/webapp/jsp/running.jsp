<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include  file="../common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>用户管理</title>
        <link rel="stylesheet" type="text/css" href="${basePath}/resources/css/default.css"/>
        <link rel="stylesheet" type="text/css" href="${basePath}/resources/js/jquery-easyui-1.3.5/themes/gray/easyui.css"/>
        <link rel="stylesheet" type="text/css" href="${basePath}/resources/js/jquery-easyui-1.3.5/themes/icon.css" />
        <script type="text/javascript" src="${basePath}/resources/js/jquery-easyui-1.3.5/jquery-1.10.2.min.js"></script>
        <script type="text/javascript" src="${basePath}/resources/js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="${basePath}/resources/js/extends.js"></script>
        <script type="text/javascript" src="${basePath}/resources/js/common.js"></script>
        <script>
            $(function () {
                $("#dg").datagrid({
                    height: $("#body").height() - $('#search_area').height() - 5,
                    width: $("#body").width(),
                    idField: 'id',
                    //data: data,
                    url: "${basePath}/task/runningTasks.do",
                    singleSelect: true,
                    nowrap: true,
                    fitColumns: true,
                    rownumbers: true,
                    showPageList: false,
                    columns: [[
                            {field: 'name', title: '任务名', width: 100, halign: "center", align: "left"},
                            {field: 'groupName', title: '任务组名', width: 100, halign: "center", align: "left"},
                            {field: 'status', title: '状态', width: 100, halign: "center", align: "left"},
                            {field: 'content', title: '内容', width: 100, halign: "center", align: "left"},
                            {field: 'cronExpression', title: '表达式', width: 100, halign: "center", align: "left"},
                            {field: 'description', title: '简介', width: 100, halign: "center", align: "left"}
                        ]],
                    toolbar: '#tt_btn',
                });
            $("#reload").on("click", function () {
                $('#dg').datagrid('reload');  
                });
            })

            //监听窗口大小变化
                window.onresize = function () {
                    setTimeout(domresize, 100);
            };
            //改变表格宽高
            function domresize() {
                    $('#dg').datagrid('resize', {
            height: $("#body").height() - $('#search_area').height() - 5,
                    width: $("#body").width()
                });
            }

        </script>
    </head>
    <body class="easyui-layout" >
        <div id="body" region="center" > 
            <!-- 数据表格区域 -->
            <table id="dg" style="table-layout:fixed;" ></table>
            <div id="tt_btn">
                <a href="javascript:void(0)"  id="reload" class="easyui-linkbutton" iconCls="icon-reload" plain="true">刷新</a>
            </div>
        </div>
    </body>
</html>
