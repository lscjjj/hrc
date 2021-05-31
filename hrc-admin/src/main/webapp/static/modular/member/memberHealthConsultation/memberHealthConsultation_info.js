/**
 * 初始化详情对话框
 */
var MemberHealthConsultationInfoDlg = {
    memberHealthConsultationInfoData : {}
};

/**
 * 清除数据
 */
MemberHealthConsultationInfoDlg.clearData = function() {
    this.memberHealthConsultationInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MemberHealthConsultationInfoDlg.set = function(key, val) {
    this.memberHealthConsultationInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MemberHealthConsultationInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MemberHealthConsultationInfoDlg.close = function() {
    parent.layer.close(window.parent.MemberHealthConsultation.layerIndex);
}

/**
 * 收集数据
 */
MemberHealthConsultationInfoDlg.collectData = function() {
    this
    .set('id')
    .set('memberUser')
    .set('family')
    .set('department')
    .set('symptoms')
    .set('consultationInfo')
    .set('createdBy')
    .set('consultationSuggest')
    .set('suggestProduce')
    .set('state')
    .set('createTime');
}

/**
 * 提交添加
 */
MemberHealthConsultationInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    this.clearData();
    this.collectData();
    var att = [];
    // $('.att-link').each(function (){
    //     att.push($(this).attr('href'));
    // });
    $('#docPreId').find('img').each(function(){  att.push($(this).attr('src')); })
    this.set('attachment',att);

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/memberHealthConsultation/add", function(data){
        Feng.success("添加成功!");
        window.parent.MemberHealthConsultation.table.refresh();
        MemberHealthConsultationInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.memberHealthConsultationInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MemberHealthConsultationInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/memberHealthConsultation/update", function(data){
        Feng.success("修改成功!");
        window.parent.MemberHealthConsultation.table.refresh();
        MemberHealthConsultationInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.memberHealthConsultationInfoData);
    ajax.start();
}

$(function() {
    var hrUp = new $MultiDocUpload("doc");
    hrUp.setUploadBarId("progressBar");
    hrUp.init();
});
