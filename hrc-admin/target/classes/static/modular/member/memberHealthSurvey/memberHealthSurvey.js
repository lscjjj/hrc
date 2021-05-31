/**
 * 会员健康状况调查管理初始化
 */
var MemberHealthSurvey = {
    id: "MemberHealthSurveyTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MemberHealthSurvey.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle' ,width: '80'},
            {title: '会员姓名', field: 'memberName', visible: true, align: 'center', valign: 'middle' ,width: '80'},
            {title: '性别', field: 'memberSex', visible: true, align: 'center', valign: 'middle' ,width: '80'},
            {title: '年龄', field: 'age', visible: true, align: 'center', valign: 'middle' ,width: '80'},
            {title: '电话', field: 'memberPhone', visible: true, align: 'center', valign: 'middle' ,width: '200'},
            {title: '地址', field: 'memberAddress', visible: true, align: 'center', valign: 'middle' ,width: '200'},
            {title: '健康状况', field: 'surveyInfo', visible: true, align: 'center', valign: 'middle' ,width: '300'},
            {title: '建档门店', field: 'departmentName', visible: false, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: false, align: 'center', valign: 'middle'},
            {title: '操作员', field: 'createdByName', visible: false, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: false, align: 'center', valign: 'middle'}
    ];
};
/**
 * 弹出健康调查表格
 */
// MemberHealthSurvey.layerOpen = function (){
//     if (this.check()) {
//         layer.open({
//             title: '健康调查表'
//             , type: 2
//             , content: Feng.ctxPath + 'memberHealthSurvey/memberHealthSurvey_option/' + MemberHealthSurvey.seItem.id
//             , area: ['1000px', '670px']
//             , yes: function (index, layero) {
//                 let cloxs = 'layui-layer-iframe'+index;
//                 MemberHealthSurveyInfoDlg.member_fix();
//                 // layer.close(index)
//             }
//             , btn2: function (index, layero) {
//                 // layer.close(index);
//             }
//         });
//     }
// };



/**
 * 检查是否选中
 */
MemberHealthSurvey.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MemberHealthSurvey.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加会员健康状况调查
 */
MemberHealthSurvey.openAddMemberHealthSurvey = function () {
    var index = layer.open({
        type: 2,
        title: '添加会员健康状况调查',
        area: ['1050px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/memberHealthSurvey/memberHealthSurvey_add'
    });
    this.layerIndex = index;
};

/**
 * 打开修改会员健康状况调查
 */
MemberHealthSurvey.openMemberHealthSurveyUpdate = function () {

        var index = layer.open({
            type: 2,
            title: '会员身体自检报告',
            area: ['1050px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/memberHealthSurvey/memberHealthSurvey_option'
        });
        this.layerIndex = index;

};

/**
 * 打开中医体检报告
 */
MemberHealthSurvey.openMemberHealthSurveymedicine = function () {

    var index = layer.open({
        type: 2,
        title: '中医体检报告',
        area: ['1050px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/memberHealthSurvey/memberHealthSurvert_medicine'
    });
    this.layerIndex = index;

};

/**
 * 打开查看会员健康状况详情
 */
MemberHealthSurvey.openMemberHealthSurveyDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '会员健康状况调查详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/memberHealthSurvey/memberHealthSurvey_detail/' + MemberHealthSurvey.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除会员健康状况调查
 */
MemberHealthSurvey.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/memberHealthSurvey/delete", function (data) {
            Feng.success("删除成功!");
            MemberHealthSurvey.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("memberHealthSurveyId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询会员健康状况调查列表
 */
MemberHealthSurvey.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    MemberHealthSurvey.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = MemberHealthSurvey.initColumn();
    var table = new BSTable(MemberHealthSurvey.id, "/memberHealthSurvey/list", defaultColunms );
    table.setPaginationType("client");
    MemberHealthSurvey.table = table.init();
    var ss = 1;
});
