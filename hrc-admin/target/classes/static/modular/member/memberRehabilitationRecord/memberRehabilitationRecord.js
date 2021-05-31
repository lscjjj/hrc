/**
 * 管理初始化
 */
var MemberRehabilitationRecord = {
    id: "MemberRehabilitationRecordTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    Record_data: null,
    Record_num: null
};

/**
 * 初始化表格的列
 */
MemberRehabilitationRecord.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '会员', field: 'memberName', visible: true, align: 'center', valign: 'middle'},
            {title: '使用地点', field: 'departmentName', visible: true, align: 'center', valign: 'middle'},
            {title: '使用产品', field: 'produce', visible: true, align: 'center', valign: 'middle'},
            {title: '使用感受', field: 'feel', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
MemberRehabilitationRecord.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MemberRehabilitationRecord.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
MemberRehabilitationRecord.openAddMemberRehabilitationRecord = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/memberRehabilitationRecord/memberRehabilitationRecord_add'
    });
    this.layerIndex = index;
};

/**
 * 打开修改
 */
MemberRehabilitationRecord.openMemberRehabilitationRecordDetail = function () {
        var index = layer.open({
            type: 2,
            title: '修改',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/memberRehabilitationRecord/memberRehabilitationRecord_update/' + MemberRehabilitationRecord.seItem.id
        });
        this.layerIndex = index;
        MemberRehabilitationRecord.getrecure();
};

/**
 * 删除x
 */
MemberRehabilitationRecord.delete = function () {
    // if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/memberRehabilitationRecord/delete", function (data) {
            Feng.success("删除成功!");
            MemberRehabilitationRecord.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("memberRehabilitationRecordId",this.seItem.id);
        ajax.start();
        MemberRehabilitationRecord.getrecure();
    // }
};
/**
 * 选择radio
 */
MemberRehabilitationRecord.check_plus = function (ting){
    if($(ting).attr('checked') == "checked"){
        $(ting).attr('checked',false);
    }else{
        $(ting).attr('checked',true);
        MemberRehabilitationRecord.Record_data.forEach(function (item,index){
            if(item.id == ting.id){
                MemberRehabilitationRecord.seItem = MemberRehabilitationRecord.Record_data[index];
            }
        });
    }
};

/**
 * 查询列表
 */
MemberRehabilitationRecord.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    MemberRehabilitationRecord.table.refresh({query: queryData});
};
/**
 *  康复信息初始化获取
 */
MemberRehabilitationRecord.getrecure = function (){
    layui.use('laytpl', function() {
        var laytpl = layui.laytpl;
        $.ajax({
            url: 'memberRehabilitationRecord/list'
            , type: 'post'
            , async: false
            , data: ''
            , success: function (data) {
                MemberRehabilitationRecord.Record_data = data;  //数据接口
                Record_num = data.length;
            }
            , error: function (e) {
                console.log(e);
            }
        });

        var getTpl = Record_src.innerHTML
            , view = document.getElementById('Record_view');

        laytpl(getTpl).render([MemberRehabilitationRecord.Record_data,Record_num], function (html) {
            view.innerHTML = html;
        });
    });
}

$(function () {
    var defaultColunms = MemberRehabilitationRecord.initColumn();
    var table = new BSTable(MemberRehabilitationRecord.id, "/memberRehabilitationRecord/list", defaultColunms);
    table.setPaginationType("client");
    MemberRehabilitationRecord.table = table.init();
    MemberRehabilitationRecord.getrecure();
});
