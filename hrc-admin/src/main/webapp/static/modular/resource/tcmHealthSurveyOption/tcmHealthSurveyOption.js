/**
 * 管理初始化
 */
var TcmHealthSurveyOption = {
    id: "TcmHealthSurveyOptionTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TcmHealthSurveyOption.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '类别', field: 'category', visible: true, align: 'center', valign: 'middle'},
            {title: '类别名称', field: 'optionCategory', visible: true, align: 'center', valign: 'middle'},
            {title: '提示', field: 'tips', visible: true, align: 'center', valign: 'middle'},
            {title: '类别细分', field: 'module', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
TcmHealthSurveyOption.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        TcmHealthSurveyOption.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
TcmHealthSurveyOption.openAddTcmHealthSurveyOption = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['1100px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/tcmHealthSurveyOption/tcmHealthSurveyOption_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
TcmHealthSurveyOption.openTcmHealthSurveyOptionDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/tcmHealthSurveyOption/tcmHealthSurveyOption_update/' + TcmHealthSurveyOption.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
TcmHealthSurveyOption.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/tcmHealthSurveyOption/delete", function (data) {
            Feng.success("删除成功!");
            TcmHealthSurveyOption.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("tcmHealthSurveyOptionId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询列表
 */
TcmHealthSurveyOption.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    TcmHealthSurveyOption.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = TcmHealthSurveyOption.initColumn();
    var table = new BSTable(TcmHealthSurveyOption.id, "/tcmHealthSurveyOption/list", defaultColunms);
    table.setPaginationType("client");
    TcmHealthSurveyOption.table = table.init();
});
