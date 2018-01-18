/*
 主页加载方法
 @eric
 */
//系统时间显示
setInterval("document.getElementById('nowTime').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());", 1000);
var setting = {
    data: {
        simpleData: {
            enable: true
        }
    },
    view: {
        selectedMulti: false
    },
    callback: {
        onClick: function (e, id, node) {
            var zTree = $.fn.zTree.getZTreeObj("menuTree");
            if (node.isParent) {
                zTree.expandNode();
            } else {
                addTabs(node.name, node.file);
            }
        }
    }
};

var zNodes = [
    {id: 1, pId: 0, name: "任务管理", open: true},
    {id: 11, pId: 1, name: "任务列表", file: basePath + "/list.do"},
    {id: 12, pId: 1, name: "运行中任务", file: basePath + "/running.do"},
    {id: 13, pId: 1, name: "计划任务", file: basePath + "/plan.do"}
];

$(function () {
    $.fn.zTree.init($("#menuTree"), setting, zNodes);
    var zTree = $.fn.zTree.getZTreeObj("menuTree");

    //中间部分tab
    $('#tabs').tabs({
        border: false,
        fit: true,
        onSelect: function (title, index) {
            var treeNode = zTree.getNodeByParam("name", title, null);
            zTree.selectNode(treeNode);
        }
    });

    $('.index_panel').panel({
        width: 300,
        height: 200,
        closable: true,
        minimizable: true,
        title: 'My Panel'
    });

});

//添加一个选项卡面板 
function addTabs(title, url, icon) {
    if (!$('#tabs').tabs('exists', title)) {
        $('#tabs').tabs('add', {
            title: title,
            content: '<iframe src="' + url + '" frameBorder="0" border="0" scrolling="no" style="width: 100%; height: 100%;"/>',
            closable: true
        });
    } else {
        $('#tabs').tabs('select', title);
    }
}