/**
 * 初始化会员充值记录详情对话框
 */
var MembershipChargeLogInfoDlg = {
    id: "MembershipChargeLogTablelgc",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MembershipChargeLogInfoDlg.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '序号', field: 'id', visible: true, align: 'center', valign: 'middle'},
        {title: '会员姓名', field: 'memberName', visible: true, align: 'center', valign: 'middle'},
        {title: '身份证号', field: 'idCard', visible: true, align: 'center', valign: 'middle'},
        {title: '会员卡号', field: 'cardNumber', visible: true, align: 'center', valign: 'middle'},
        // {title: '电话号码', field: 'mobile', visible: true, align: 'center', valign: 'middle'},
        {title: '充值金额', field: 'amount', visible: true, align: 'center', valign: 'middle'},
        {title: '赠送金额', field: 'givenAmount', visible: true, align: 'center', valign: 'middle'},
        {title: '充值卡金额', field: 'remainingAmount', visible: true, align: 'center', valign: 'middle'},
        {title: '建档门店', field: 'departmentName', visible: true, align: 'center', valign: 'middle'},
        {title: '建档时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
        {title: '建档人员', field: 'createdName', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 清除数据
 */
MembershipChargeLogInfoDlg.clearData = function() {
    this.membershipChargeLogInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MembershipChargeLogInfoDlg.set = function(key, val) {
    this.membershipChargeLogInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MembershipChargeLogInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MembershipChargeLogInfoDlg.close = function() {
    parent.layer.close(window.parent.MembershipChargeLog.layerIndex);
}

/**
 * 收集数据
 */
MembershipChargeLogInfoDlg.collectData = function() {
    this
    .set('id')
    .set('card')
    .set('amount')
    .set('givenAmount')
    .set('department')
    .set('created')
    .set('memo')
    .set('createTime')
    .set('flag');
}

/**
 * 提交添加
 */
MembershipChargeLogInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/membershipChargeLog/add", function(result){
        if(result && result.code!=200){
            Feng.error(result.message);
        }else {
            Feng.success("添加成功!");
            window.parent.MembershipChargeLog.table.refresh();
            window.MembershipChargeLogInfoDlg.table.refresh();
            MembershipChargeLogInfoDlg.search();
        }
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.membershipChargeLogInfoData);
    ajax.start();
}

/**
 * 返回上一页
 */
MembershipChargeLogInfoDlg.closeindexs = function (){
    //当你在iframe页面关闭自身时
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}

/**
 * 提交修改
 */
MembershipChargeLogInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/membershipChargeLog/update", function(data){
        Feng.success("修改成功!");
        window.parent.MembershipChargeLog.table.refresh();
        MembershipChargeLogInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.membershipChargeLogInfoData);
    ajax.start();
}

/**
 * 查询会员充值记录列表
 */
MembershipChargeLogInfoDlg.search = function () {
    var queryData = {};
    queryData['condition'] = $("#cardValue").val();
    $($('.input-outline')[0]).val(queryData['condition']);
    $($('.input-outline')[0]).focus();
    $($('.input-outline')[0]).blur();
};

MembershipChargeLogInfoDlg.openAddMembershipCard = function (arr) {
    var index = parent.layer.open({
        type: 2,
        title: '添加会员卡',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/membershipCard/membershipCard_add#'+ arr[2] + '&' + arr[3]
    });
    parent.layerIndex = index;
};

MembershipChargeLogInfoDlg.closeindexs = function ()
{
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
};

$(function() {

    var defaultColunms = MembershipChargeLogInfoDlg.initColumn();
    var table = new BSTable(MembershipChargeLogInfoDlg.id, "/membershipChargeLog/list", defaultColunms);
    table.setPaginationType("client");
    MembershipChargeLogInfoDlg.table = table.init();


    var memberCardBsSuggest = $("#cardName").bsSuggest({
        idField: 'id',
        keyField: 'name',
        allowNoKeyword: false,
        multiWord: true,
        separator: ",",
        getDataMethod: "url",
        effectiveFieldsAlias: {id: "编号",number: "卡号", memberId:"会员id",name: "姓名", mobile: "手机号", idCard: "身份证",balance: "充值卡金额"},
        showHeader: true,
        url: "/membershipCard/suggestList/",
        processData: function (json) {
            var i, len, data = {value: []};
            if (!json || json.length == 0) {
                return false
            }
            len = json.length;
            for (i = 0; i < len; i++) {
                if (json[i]['id'] == "" || json[i]['id'] == null){
                    json[i]['id'] = "暂未添加";
                }
                if (json[i]['number'] == "" || json[i]['number'] == null){
                    json[i]['number'] = "暂未添加";
                }
                data.value.push({"id": json[i]['id'],"number": json[i]['number'], "memberId": json[i]['memberId'],"name": json[i]['name'], "mobile": json[i]['mobile'], "idCard": json[i]['idCard'],"balance": json[i]['balance']})
            }
            return data
        }
    });
    /**
     * 下拉点击事件
     */
    $("input#cardName").on("onSetSelectValue", function (event, result) {
        $('#card').val(result['id']);
        var arr = [];
        for(var i=0;i<$('.jhover').children().length;i++)
        {
            arr[i] = $($('.jhover').children()[i]).text();
        }
        $('#Identitycard').val(arr[5]);
        $('#Telephone').val(arr[4]);
        $('#cardValue').val(arr[1]);
        $('#cardName').val(arr[3]);
        $('#balance').val(arr[6]);

        if($('#cardValue').val() == '暂未添加')
        {
            MembershipChargeLogInfoDlg.openAddMembershipCard(arr);
        }
        MembershipChargeLogInfoDlg.search();
    });

    $("#created").val($('#userId').val());
    $("#department").val($('#departmentId').val());
    $("#department").change(function(){
        $.post("/mgr/userByDepartment",{dept:$("#department").val()},function(result){
            $('#created').empty();
            $.each(result,function(i,dt){
            $('#created').append($('<option>').val(dt.value).text(dt.key));
        });
    });
    });

    $("#btn-add-membership-card").click(function () {
        MembershipChargeLogInfoDlg.openAddMembershipCard();
    });

    $('#amount').on('input',function(){
        var amount = $(this).val();
        var membershipCard = $('#card').val();
        if(parseFloat(amount)>0 && parseInt(membershipCard)>0) {
            $.post("/membershipChargeLog/chargePresent", {
                amount: amount,
                membershipCard: membershipCard
            }, function (result) {
                if (result.code == 200) {
                    var dr = '';
                    if(result.data.level)
                        dr = "充值后金额为["+result.data.balance+"],等级为["+result.data.level+"], 赠送["+result.data.present+"]元"
                    $('#chargeTip').text(dr);
                } else {
                    Feng.info(result.message);
                }
            });
        }
    });

    $('#cardName').keydown(function (event){
        if(event.keyCode == 8)
        {
            $('#Identitycard').val('');
            $('#Telephone').val('');
            $('#cardValue').val('');
            $('#balance').val('');
        }
    });
    var now=new Date();
    var year=now.getFullYear();
    var month=now.getMonth();
    var day=now.getDate();
    var hours=now.getHours();
    var minutes=now.getMinutes();
    var seconds=now.getSeconds();
    $('#givenalotxianmu').val(""+year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds+"");
});
$("#cardName").on('click',function (){
    $relute = $('#cardName').val();
    if($relute == ' ' || $relute == ''){
        $('#cardName').val(' ');
    }
});

