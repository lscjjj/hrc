/**
 * 会员管理初始化
 */
var Member = {
    id: "MemberTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Member.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'realName', visible: true, align: 'center', valign: 'middle'},
        {title: '性别', field: 'genderName', visible: true, align: 'center', valign: 'middle'},
        {title: '年龄', field: 'age', visible: true, align: 'center', valign: 'middle'},
        {title: '身份证号', field: 'idCard', visible: true, align: 'center', valign: 'middle'},
        {title: '电话号码', field: 'phone', visible: true, align: 'center', valign: 'middle'},
        {title: '职业', field: 'occupation', visible: true, align: 'center', valign: 'middle'},
        // {title: '邮箱', field: 'email', visible: true, align: 'center', valign: 'middle'},
        {title: '建档门店', field: 'clinicName', visible: true, align: 'center', valign: 'middle'},
        {title: '建档人员', field: 'creatorName', visible: true, align: 'center', valign: 'middle'},
        {title: '建档时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
        {title: '详细', visible: true, align: 'center', valign: 'middle',formatter: function (value) { return '<button class="btn">详细</button>';}},
    ];
};

/**
 * 检查是否选中
 */
Member.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Member.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加会员
 */
Member.openAddMember = function () {
    var index = layer.open({
        type: 2,
        title: '添加会员',
        area: ['1100px', '700px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/member/member_add'
    });
    this.layerIndex = index;
    layer.full(index);
};

/**
 * 打开查看会员详情
 */
Member.openMemberDetail = function (e) {
    console.log(this);
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '会员详情',
            area: ['1100px', '700px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/member/member_update/' + Member.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);
    }
};

/**
 * 删除会员
 */
Member.delete = function () {
    if (this.check()) {
        var item = this;
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/member/delete", function (data) {
                if(data.code == 200) {
                    Feng.success("删除成功!");
                    Member.table.refresh();
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
Member.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    $($('.input-outline')[0]).val(queryData['condition']);
    $($('.input-outline')[0]).focus();
    $($('.input-outline')[0]).blur();
    // Member.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Member.initColumn();
    var table = new BSTable(Member.id, "/member/list", defaultColunms);
    table.setPaginationType("client");
    Member.table = table.init();

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

/**
 * 点击打开详细
 */
$("#MemberTable").on('click-row.bs.table', function (e, row, $element, field) {
    if(field == "11"){
        var index = layer.open({
            type: 2,
            title: '会员详情',
            area: ['1100px', '700px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/member/member_update/' + row.id
        });
        this.layerIndex = index;
        layer.full(index);
    }
})