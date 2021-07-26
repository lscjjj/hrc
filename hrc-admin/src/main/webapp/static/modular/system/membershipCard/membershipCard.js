/**
 * 会员卡管理初始化
 */
var MembershipCard = {
    id: "MembershipCardTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MembershipCard.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        // {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
        {title: '会员姓名', field: 'userName', visible: true, align: 'center', valign: 'middle'},
        {title: '身份证号', field: 'idCard', visible: true, align: 'center', valign: 'middle'},
        {title: '卡号', field: 'number', visible: true, align: 'center', valign: 'middle'},
        {title: '充值金额', field: 'amount', visible: true, align: 'center', valign: 'middle'},
        {title: '赠送金额', field: 'givenAmount', visible: true, align: 'center', valign: 'middle'},
        {title: '充值卡余额', field: 'balance', visible: true, align: 'center', valign: 'middle'},
        // {title: '会员卡级别', field: 'levelName', visible: true, align: 'center', valign: 'middle'},
        // {title: '会员折扣', field: 'discountText', visible: true, align: 'center', valign: 'middle'},
        // {title: '绑定项目', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        // {title: '过期时间', field: 'dueDate', visible: true, align: 'center', valign: 'middle'},
        {title: '建档门店', field: 'departmentName', visible: true, align: 'center', valign: 'middle'},
        {title: '建档人员', field: 'createdName', visible: true, align: 'center', valign: 'middle'},
        {title: '建档时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
MembershipCard.check = function (pass) {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        if(pass)
        {
            MembershipCard.lgcid = '';
            return true;
        }
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MembershipCard.seItem = selected[0];
        MembershipCard.lgcid = MembershipCard.seItem.id;
        return true;
    }
};

/**
 * 点击添加会员卡
 */
MembershipCard.openAddMembershipCard = function () {
    var index = layer.open({
        type: 2,
        title: '添加会员卡',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/membershipCard/membershipCard_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看会员卡详情
 */
MembershipCard.openMembershipCardDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '会员卡详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/membershipCard/membershipCard_update/' + MembershipCard.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 点击会员充值
 */
MembershipCard.openAddMembershipCards = function () {
    let pass = true;
    if (this.check(pass)) {
        if(!MembershipCard.seItem)
        {

        }
        MembershipChargeLog.openAddMembershipChargeLog(MembershipCard.lgcid);
    }
}

/**
 * 删除会员卡
 */
MembershipCard.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/membershipCard/delete", function (data) {
            Feng.success("删除成功!");
            MembershipCard.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("membershipCardId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询会员卡列表
 */
MembershipCard.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    $($('.input-outline')[0]).val(queryData['condition']);
    $($('.input-outline')[0]).focus();
    $($('.input-outline')[0]).blur();
    // MembershipCard.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = MembershipCard.initColumn();
    var table = new BSTable(MembershipCard.id, "/membershipCard/list", defaultColunms);
    table.setPaginationType("client");
    MembershipCard.table = table.init();

    var memberCardBsSuggesthzs = $("#condition").bsSuggest({
        idField: 'id',
        keyField: 'name',
        allowNoKeyword: false,
        multiWord: true,
        separator: ",",
        getDataMethod: "url",
        effectiveFieldsAlias: {id: "编号", name: "姓名",number: "卡号",  mobile: "手机号", idCard: "身份证"},
        showHeader: true,
        url: "/membershipCard/suggestList/",
        processData: function (json) {
            var i, len, data = {value: []};
            if (!json || json.length == 0) {
                return false
            }
            len = json.length;
            for (i = 0; i < len; i++) {
                if (json[i]['id'] == "" || json[i]['id'] == null || json[i]['id'] == undefined){
                    json[i]['id'] = "暂未添加";
                }
                if (json[i]['number'] == "" || json[i]['number'] == null || json[i]['number'] == undefined){
                    json[i]['number'] = "暂未添加";
                }
                data.value.push({"id": json[i]['id'], "name": json[i]['name'],"number": json[i]['number'], "mobile": json[i]['mobile'], "idCard": json[i]['idCard']})
            }
            return data
        }
    });

});

/**
 *  会员卡添加弹窗
 */
$('#btn-add-membership-cards').click(function (){
    MembershipChargeLogInfoDlg.openAddMembershipCard();
});
$("#condition").on('click',function (){
    $relute = $('#condition').val();
    if($relute == ' ' || $relute == ''){
        $('#condition').val(' ');
    }
});
