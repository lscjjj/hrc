/**
 * 管理初始化
 */
var MemberHealthConsultation = {
    id: "MemberHealthConsultationTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MemberHealthConsultation.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '主键id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '会员id', field: 'memberUser', visible: false, align: 'center', valign: 'middle'},
        {title: '咨询成员', field: 'appellationName', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'memberUserName', visible: true, align: 'center', valign: 'middle'},
        {title: '电话', field: 'memberUserPhone', visible: true, align: 'center', valign: 'middle'},
        {title: '症状表现', field: 'symptoms', visible: true, align: 'center', valign: 'middle'},
        {title: '咨询门店', field: 'departmentName', visible: true, align: 'center', valign: 'middle'},
        {title: '咨询内容', field: 'consultationInfo', visible: true, align: 'center', valign: 'middle'},
        {title: '操作员', field: 'createdBy', visible: false, align: 'center', valign: 'middle'},
        {title: '康复建议', field: 'consultationSuggest', visible: false, align: 'center', valign: 'middle'},
        {title: '建议康复产品', field: 'suggestProduce', visible: false, align: 'center', valign: 'middle'},
        {title: '状态', field: 'stateName', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
MemberHealthConsultation.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MemberHealthConsultation.seItem = selected[0];
        return true;
    }
};


/**
 * 打开查看详情
 */
MemberHealthConsultation.openMemberHealthConsultationDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/sysMemberHealthConsultation/sysMemberHealthConsultation_detail/' + MemberHealthConsultation.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 回复
 */
MemberHealthConsultation.openMemberHealthConsultationUpdate = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/sysMemberHealthConsultation/sysMemberHealthConsultation_update/' + MemberHealthConsultation.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
MemberHealthConsultation.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/sysMemberHealthConsultation/delete", function (data) {
            Feng.success("删除成功!");
            MemberHealthConsultation.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("sysMemberHealthConsultationId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询列表
 */
MemberHealthConsultation.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    MemberHealthConsultation.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = MemberHealthConsultation.initColumn();
    var table = new BSTable(MemberHealthConsultation.id, "/sysMemberHealthConsultation/list", defaultColunms);
    table.setPaginationType("client");
    MemberHealthConsultation.table = table.init();
});
