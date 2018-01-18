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
            var url;
            function newUser() {
                $('#dlg').dialog('open').dialog('center').dialog('setTitle', 'New User');
                $('#fm').form('clear');
                url = basePath+'/task/addJobs2.do';
            }
            function editUser() {
                var row = $('#dg').datagrid('getSelected');
                if (row) {
                    $('#dlg').dialog('open').dialog('center').dialog('setTitle', 'Edit User');
                    $('#fm').form('load', row);
                    url =basePath+'/task/updateJob2.do?id=' + row.id;
                }
            }

            function saveUser() {
                $('#fm').form('submit', {
                    url: url,
                    onSubmit: function () {
                        return $(this).form('validate');
                    },
                    success: function (result) {
                        var result = eval('(' + result + ')');
                        if (result.errorMsg) {
                            $.messager.show({
                                title: 'Error',
                                msg: result.errorMsg
                            });
                        } else {
                            $('#dlg').dialog('close');        // close the dialog
                            $('#dg').datagrid('reload');    // reload the user data
                        }
                    }
                });
            }

            $(function () {
                $("#dg").datagrid({
                    height: $("#body").height() - $('#search_area').height() - 5,
                    width: $("#body").width(),
                    idField: 'id',
                    //data: data,
                    url: "${basePath}/task/tasks.do",
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
                    pagination: true
                });


                //新增弹出框
                $("#save").on("click", function () {
                    newUser();
                });
                //修改
                $("#update").on("click", function () {
                    editUser();
                });
                $("#run").on("click", function () {
                    var row = $('#dg').datagrid('getSelected');
                    if (row) {
                        $parent.messager.confirm('My Title', 'Are you executeJob this job?', function (r) {
                            if (r) {
                                console.log(row)
                                $.post(basePath+"/task/executeJob2.do", {groupName:row.groupName,name:row.name}, function (data) {
                                    query();
                                })
                            }
                        });
                    }
                });
                //删除
                $("#delete").on("click", function () {
                    var row = $('#dg').datagrid('getSelected');
                    console.log(row)
                    row.cashGift={};
                    if (row) {
                        $parent.messager.confirm('My Title', 'Are you confirm this?', function (r) {
                            if (r) {
                                $.post(basePath+"/task/deleteJob2.do", row, function (data) {
                                    query();
                                })
                            }
                        });
                    }
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

            function query() {
                var param = {};
                if ($("#name").val()) {
                    param.name = $("#name").val();
                }
                if ($("#groupName").val()) {
                    param.groupName = $("#groupName").val();
                }
                $("#dg").datagrid("load", param);
            }

        </script>
    </head>
    <body class="easyui-layout" >
        <div id="body" region="center" > 
            <!-- 查询条件区域 -->
            <div id="search_area" >
                <div id="conditon">
                    <table border="0">
                        <tr>
                            <td>任务名：</td>
                            <td ><input  name="name" id="name"   /></td>
                            <td>&nbsp;任务组名：</td>
                            <td><input  name="groupName" id="groupName"  /></td>
                            <td>
                                <a  href="javascript:query()" class="easyui-linkbutton my-search-button" iconCls="icon-search" plain="true">查询</a> 
                                <a  href="javascript:void(0)" class="easyui-linkbutton my-search-button" iconCls="icon-reset" plain="true" >重置</a>
                            </td>
                        </tr>
                    </table>
                </div>
                <span id="openOrClose">111</span> 
            </div>
            <!-- 数据表格区域 -->
            <table id="dg" style="table-layout:fixed;" ></table>
            <!-- 表格顶部工具按钮 -->
            <div id="tt_btn">
                <a href="javascript:void(0)"  id="save" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
                <a href="javascript:void(0)"  id="update" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a> 
                <a href="javascript:void(0)"  id="run" class="easyui-linkbutton" iconCls="icon-reload" plain="true">立即运行</a>
                <a href="javascript:void(0)"  id="delete" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
            </div>
            <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
                <div class="ftitle">User Information</div>
                <form id="fm" method="post" novalidate>
                    <table>
                        <tr>
                            <td><label>TaskName:</label></td>
                            <td><input name="name" class="easyui-textbox" required="true"></td>
                        </tr>
                        <tr>
                            <td><label>GroupName:</label></td>
                            <td><input name="groupName" class="easyui-textbox" required="true"></td>
                        </tr>
                        <tr>
                            <td><label>Status:</label></td>
                            <td> 
                                <select name="status" required="true">
                                    <option value="1">启用</option>
                                    <option value="0">禁用</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><label>cronExpression:</label></td>
                            <td><input name="cronExpression" class="easyui-textbox" required="true"></td>
                        </tr>
                        <tr>
                            <td><label>content:</label></td>
                            <td><input name="content" class="easyui-textbox" required="true"></td>
                        </tr>
                        <tr>
                            <td><label>description:</label></td>
                            <td><input name="description" class="easyui-textbox" required="true"></td>
                        </tr>
                    </table>
                </form>
            </div>
            <div id="dlg-buttons">
                <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()">Save</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
            </div>
        </div>
    </body>
</html>
