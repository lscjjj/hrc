/**
 * 会员充值记录管理初始化
 */
var MembershipChargeLog = {
    id: "MembershipChargeLogTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MembershipChargeLog.initColumn = function () {
    return [

        {field: 'selectItem', radio: true},
        {title: '序号', field: 'id', visible: true, align: 'center', valign: 'middle'},
        {title: '会员姓名', field: 'memberName', visible: true, align: 'center', valign: 'middle'},
        {title: '身份证号', field: 'idCard', visible: true, align: 'center', valign: 'middle'},
        {title: '电话号码', field: 'mobile', visible: true, align: 'center', valign: 'middle'},
        {title: '会员卡号', field: 'cardNumber', visible: true, align: 'center', valign: 'middle'},
        {title: '充值金额', field: 'amount', visible: true, align: 'center', valign: 'middle'},
        {title: '赠送金额', field: 'givenAmount', visible: true, align: 'center', valign: 'middle'},
        {title: '充值卡金额', field: 'remainingAmount', visible: true, align: 'center', valign: 'middle'},
        {title: '建档门店', field: 'departmentName', visible: true, align: 'center', valign: 'middle'},
        {title: '充值时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
        {title: '建档人员', field: 'createdName', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
MembershipChargeLog.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MembershipChargeLog.seItem = selected[0];
        return true;
    }
};

/**
 * 点击会员充值
 */
MembershipChargeLog.openAddMembershipChargeLog = function (id) {
    var index = layer.open({
        type: 2,
        title: '会员卡充值',
        area: ['1500px', '720px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/membershipChargeLog/membershipChargeLog_add/' + id
    });
    this.layerIndex = index;
    layer.full(index);
};
/**
 * 跳转到充值记录页面
 */
MembershipChargeLog.toNavrshipChargeLog = function (){
    var index = layer.open({
        type: 2,
        title: '会员充值记录',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: '/membershipChargeLog'
    });
    this.layerIndex = index;
    layer.full(index);
}
/**
 * 打开查看会员充值记录详情
 */
MembershipChargeLog.openMembershipChargeLogDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '会员充值记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/membershipChargeLog/membershipChargeLog_update/' + MembershipChargeLog.seItem.id
        });
        this.layerIndex = index;``
    }
};

/**
 * 删除会员充值记录
 */
MembershipChargeLog.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/membershipChargeLog/delete", function (data) {
            Feng.success("删除成功!");
            MembershipChargeLog.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("membershipChargeLogId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询会员充值记录列表
 */
MembershipChargeLog.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    MembershipChargeLog.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = MembershipChargeLog.initColumn();
    var table = new BSTable(MembershipChargeLog.id, "/membershipChargeLog/list", defaultColunms);
    table.setPaginationType("client");
    MembershipChargeLog.table = table.init();
});

$('#clickretu').on('click',function (){
    var indexshzs = parent.layer.getFrameIndex(window.name);
    parent.layer.close(indexshzs); //再执行关闭
});
