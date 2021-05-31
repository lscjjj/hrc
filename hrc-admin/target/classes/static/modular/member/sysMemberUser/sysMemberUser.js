/**
 * 管理初始化
 */
var MemberUser = {
    id: "MemberUserTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MemberUser.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '主键id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '会员姓名', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '生日', field: 'birthday', visible: true, align: 'center', valign: 'middle'},
        {title: '电话', field: 'phone', visible: true, align: 'center', valign: 'middle'},
        {title: '微信', field: 'wechat', visible: true, align: 'center', valign: 'middle'},
        {title: '地址', field: 'address', visible: true, align: 'center', valign: 'middle'},
        {title: '电子邮件', field: 'email', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createtime', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
MemberUser.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MemberUser.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
MemberUser.openAddMemberUser = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/sysMemberUser/sysMemberUser_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
MemberUser.openMemberUserDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/sysMemberUser/sysMemberUser_detail/' + MemberUser.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
MemberUser.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/sysMemberUser/delete", function (data) {
            Feng.success("删除成功!");
            MemberUser.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("sysMemberUserId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询列表
 */
MemberUser.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    MemberUser.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = MemberUser.initColumn();
    var table = new BSTable(MemberUser.id, "/sysMemberUser/list", defaultColunms);
    table.setPaginationType("client");
    MemberUser.table = table.init();
});
