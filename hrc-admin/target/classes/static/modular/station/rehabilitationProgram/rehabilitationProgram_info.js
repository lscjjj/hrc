/**
 * 初始化康复方案详情对话框
 */
var RehabilitationProgramInfoDlg = {
    //rehabilitationProgramInfoData : {}
    id: "RehabilitationProgramaddTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};
/**
 * 初始化表格的列
 */
RehabilitationProgramInfoDlg.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '会员姓名', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '手机号', field: 'phone', visible: true, align: 'center', valign: 'middle'},
        {title: '建档日期', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
RehabilitationProgramInfoDlg.check = function (ids, createTime) {
    // var selected = $('#' + this.id).bootstrapTable('getSelections');
    // if(selected.length == 0){
    //     Feng.info("请先选中表格中的某一记录！");
    //     return false;
    // }else{
    //     RehabilitationProgramInfoDlg.seItem = selected[0];
    this.openrehabilitationProgramadd(ids, createTime);
    // }
};

/**
 * 跳转健康方案页面
 */
RehabilitationProgramInfoDlg.openrehabilitationProgramadd = function (ids, createTime) {
    var index = parent.layer.open({
        type: 2,
        title: '添加健康方案',
        area: ['1100px', '700px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/rehabilitationProgram/rehabilitationProgram_update/0?hrid=' + ids + '#' + createTime
    });
    parent.layerIndex = index;
}

/**
 * 清除数据
 */
RehabilitationProgramInfoDlg.clearData = function () {
    this.rehabilitationProgramInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
RehabilitationProgramInfoDlg.set = function (key, val) {
    this.rehabilitationProgramInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
RehabilitationProgramInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
RehabilitationProgramInfoDlg.close = function () {
    parent.layer.close(window.parent.RehabilitationProgram.layerIndex);
}

/**
 * 收集数据
 */
RehabilitationProgramInfoDlg.collectData = function () {
    this
        .set('id')
        .set('member')
        .set('disease')
        .set('description')
        .set('curativeEffect')
        .set('remark')
        .set('department')
        .set('createdBy')
        .set('createTime')
        .set('flag')
        .set('surveyInfo')
        .set('finalEffect')
        .set('customerSatisfaction');
}

/**
 * 提交添加
 */
RehabilitationProgramInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();
    var att = [];
    $('.att-link').each(function () {
        att.push($(this).attr('href'));
    });
    this.set('attachment', att);

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/rehabilitationProgram/add", function (data) {
        Feng.success("添加成功!");
        window.parent.RehabilitationProgram.table.refresh();
        RehabilitationProgramInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.rehabilitationProgramInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
RehabilitationProgramInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/rehabilitationProgram/update", function (data) {
        Feng.success("修改成功!");
        window.parent.RehabilitationProgram.table.refresh();
        RehabilitationProgramInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.rehabilitationProgramInfoData);
    ajax.start();
}

/**
 * 从表格获取id,name,手机号到当前页面
 */
RehabilitationProgramInfoDlg.getidnameSulutes = function (result) {
    let list = result.replace(/#/, '');
    let arr = list.split('&');
    $('#createtimess').html(arr[0]);
    $('#rjzjbb').html(arr[0]);
}

$(function () {

    var defaultColunms = RehabilitationProgramInfoDlg.initColumn();
    var table = new BSTable(RehabilitationProgramInfoDlg.id, "/rehabilitationProgram/toAdd", defaultColunms);
    table.setPaginationType("client");
    RehabilitationProgramInfoDlg.table = table.init();

    var hrUp = new $MultiDocUpload("doc");
    hrUp.setUploadBarId("progressBar");
    hrUp.init();

    var hrUp = new $MultiDocUpload("sdd");
    hrUp.setUploadBarId("progressBar");
    hrUp.init();

    var hrUps = new $MultiDocUpload("faceImgAttachment");
    hrUps.setUploadBarId("progressBar");
    hrUps.init();

    var hrUps = new $MultiDocUpload("tongueImgAttachment");
    hrUps.setUploadBarId("progressBar");
    hrUps.init();

    var hrUps = new $MultiDocUpload("partImgAttachment");
    hrUps.setUploadBarId("progressBar");
    hrUps.init();
    var memberBsSuggest = $("#memberValue").bsSuggest({
        idField: 'member',
        keyField: 'name',
        allowNoKeyword: false,
        multiWord: true,
        separator: ",",
        getDataMethod: "url",
        effectiveFieldsAlias: {member: "编号", name: "姓名", mobile: "手机号"},
        showHeader: true,
        url: "/member/suggestList/",
        processData: function (json) {
            var i, len, data = {value: []};
            if (!json || json.length == 0) {
                return false
            }
            len = json.length;
            for (i = 0; i < len; i++) {
                data.value.push({"member": json[i]['id'], "name": json[i]['name'], "mobile": json[i]['mobile']})
            }
            return data
        }
    });
    var patt = $("#patt").val();
    if (patt && patt.length > 0) {
        $.each(patt.split(','), function (idx, dt) {
            var $att = $('<div class="att-pane"><a class="att-link" target="_blank" href="' + dt + '">查看文档</a>&nbsp;&nbsp;<a href="javascript:void(0);" class="att-del">删除</a> </div>')
            $("#docPreId").append($att);

        })
    }

    var faceImgAttachment = $("#faceImgAttachment").val();
    if (faceImgAttachment && faceImgAttachment.length > 0) {
        $.each(faceImgAttachment.split(','), function (idx, dt) {
            if(idx == 1) {
                var $att = $('<div class="att-pane"><a href="' + dt + '" target="_blank"><img class="att-links" target="_blank" src="' + dt + '" width="100px" height="100px"/></a></div>')
                $("#faceImgAttachmentPreId").append($att);
                $('#faceImgAttachmentBtnId').hide();
            }
        })
    }

    var tongueImgAttachment = $("#tongueImgAttachment").val();
    if (tongueImgAttachment && tongueImgAttachment.length > 0) {
        $.each(tongueImgAttachment.split(','), function (idx, dt) {
            if(idx == 1) {
                var $att = $('<div class="att-pane"><a href="' + dt + '" target="_blank"><img class="att-links" target="_blank" src="' + dt + '" width="100px" height="100px"/></a></div>')
                $("#tongueImgAttachmentPreId").append($att);
                $('#tongueImgAttachmentBtnId').hide();
            }
        })
    }

    var partImgAttachment = $("#partImgAttachment").val();
    if (partImgAttachment && partImgAttachment.length > 0) {
        $.each(partImgAttachment.split(','), function (idx, dt) {
            if(idx == 1) {
                var $att = $('<div class="att-pane"><a href="' + dt + '" target="_blank"><img class="att-links" target="_blank" src="' + dt + '" width="100px" height="100px"/></a></div>')
                $("#partImgAttachmentPreId").append($att);
                $('#partImgAttachmentBtnId').hide();
            }
        })
    }

    $("input#memberValue").on("onSetSelectValue", function (event, result) {
        $('#member').val(result['id']);
    });
    RehabilitationProgramInfoDlg.getidnameSulutes(decodeURIComponent(window.location.hash));
    $('select#solution').multiselect('refresh');
    $('select#solution').change(function () {
        $('#description').text('');
        $($(this).val()).each(function (i, dt) {
            $('#description').append(dt + "&#13;&#10;")
        })
    })
    //RehabilitationProgramInfoDlg.getidnameSulutes(decodeURIComponent(window.location.hash));

    // $("#category").change(function () {
    //     var category = $(this).val();
    //     $.post("/healthProgram/programByCategory",{category:category},function(result){
    //         $('#symptom').empty();
    //         $('#symptom').append($('<option>').val('').text(' -- 请选择症状 -- '));
    //         $.each(result,function(i,dt){
    //             $('#symptom').append($('<option>').val(dt.value).text(dt.key));
    //         });
    //     });
    // });
    // var slen = $('select#solution optgroup').length;
    // $('select#solution optgroup').each(function (index,opg) {
    //     $opg=$('select#solution optgroup').eq(index);
    //     var opid = $opg.attr('data-id');
    //     $.post("/rehabilitationProgram/projectsByCategory",{pc:opid},function(result){
    //         $.each(result,function(i,dt){
    //             $opg.append($('<option>').val(dt.solution).text(dt.name));
    //         });
    //         if(index == slen-1){
    //             $('select#solution').multiselect();
    //         }
    //     });
    // });

    // $("#symptom").change(function () {
    //     var symptom = $(this).val();
    //    $('#description').text(symptom);
    // });
    $('#RehabilitationProgramaddTable').on('click-row.bs.table', function (e, row, $element) {
        RehabilitationProgramInfoDlg.check(row.id, row.createTime);
    });
});
