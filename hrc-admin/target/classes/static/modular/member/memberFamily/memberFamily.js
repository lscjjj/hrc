/**
 * 管理初始化
 */
var MemberFamily = {
    id: "MemberFamilyTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MemberFamily.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '账号id', field: 'memberUser', visible: true, align: 'center', valign: 'middle'},
            {title: '称呼', field: 'appellation', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '性别', field: 'gender', visible: true, align: 'center', valign: 'middle'},
            {title: '生日', field: 'birthday', visible: true, align: 'center', valign: 'middle'},
            {title: '既往病史', field: 'medicalHistory', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
MemberFamily.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MemberFamily.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
MemberFamily.openAddMemberFamily = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/memberFamily/memberFamily_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
MemberFamily.openMemberFamilyDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/memberFamily/memberFamily_update/' + MemberFamily.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
MemberFamily.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/memberFamily/delete", function (data) {
            Feng.success("删除成功!");
            MemberFamily.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("memberFamilyId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询列表
 */
MemberFamily.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    MemberFamily.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = MemberFamily.initColumn();
    var table = new BSTable(MemberFamily.id, "/memberFamily/list", defaultColunms);
    table.setPaginationType("client");
    MemberFamily.table = table.init();
});
