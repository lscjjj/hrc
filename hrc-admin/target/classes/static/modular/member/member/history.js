/**
 * 会员管理初始化
 */
var History = {
    id: "HistoryTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
History.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'realName', visible: true, align: 'center', valign: 'middle'},
        {title: '性别', field: 'gender', visible: true, align: 'center', valign: 'middle',formatter: function (value,row) {
                return row.gender == 1 ? '男':"女"}
                    },
        {title: '年龄', field: 'age', visible: true, align: 'center', valign: 'middle'},
        {title: '身份证号', field: 'idCard', visible: true, align: 'center', valign: 'middle'},
        {title: '电话号码', field: 'phone', visible: true, align: 'center', valign: 'middle'},
        {title: '职业', field: 'occupation', visible: true, align: 'center', valign: 'middle'},
        // {title: '邮箱', field: 'email', visible: true, align: 'center', valign: 'middle'},
        {title: '建档门店', field: 'clinicName', visible: true, align: 'center', valign: 'middle'},
        {title: '建档人员', field: 'creatorName', visible: true, align: 'center', valign: 'middle'},
        {title: '建档时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
History.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        History.seItem = selected[0];
        return true;
    }
};

/**
 * 删除会员
 */
History.delete = function () {
    if (this.check()) {
        var item = this;
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/member/delete", function (data) {
                if(data.code == 200) {
                    Feng.success("删除成功!");
                    History.table.refresh();
                }else{
                    Feng.alert(data.message);
                    return;
                }
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("memberId",item.seItem.id);
            ajax.start();
        }
        Feng.confirm("是否删除该会员?", operation);
    }
};

/**
 * 查询会员列表
 */
History.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    $($('.input-outline')[0]).val(queryData['condition']);
    $($('.input-outline')[0]).focus();
    $($('.input-outline')[0]).blur();
    // Member.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = History.initColumn();
    var table = new BSTable(History.id, "/member/update_history_list/"+localStorage.getItem("uid"), defaultColunms);
    table.setPaginationType("client");
    History.table = table.init();

    var memberCardBsSuggesthzss = $("#condition").bsSuggest({
        idField: 'id',
        keyField: 'name',
        allowNoKeyword: false,
        multiWord: true,
        separator: ",",
        getDataMethod: "url",
        effectiveFieldsAlias: {member: "编号", name: "姓名", mobile: "手机号", idCard: "身份证"},
        showHeader: true,
        url: "/member/suggestList/",
        processData: function (json) {
            var i, len, data = {value: []};
            if (!json || json.length == 0) {
                return false
            }
            len = json.length;
            for (i = 0; i < len; i++) {
                data.value.push({"member": json[i]['id'], "name": json[i]['name'], "mobile": json[i]['mobile'], "idCard": json[i]['idCard']})
            }
            return data
        }
    });

});
$("#condition").on('click',function (){
    $relute = $('#condition').val();
    if($relute == ' ' || $relute == ''){
        $('#condition').val(' ');
    }
});

