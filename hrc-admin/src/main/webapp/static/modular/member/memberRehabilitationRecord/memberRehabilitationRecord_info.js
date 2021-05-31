/**
 * 初始化详情对话框
 */
var MemberRehabilitationRecordInfoDlg = {
    memberRehabilitationRecordInfoData : {}
};

/**
 * 清除数据
 */
MemberRehabilitationRecordInfoDlg.clearData = function() {
    this.memberRehabilitationRecordInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MemberRehabilitationRecordInfoDlg.set = function(key, val) {
    this.memberRehabilitationRecordInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MemberRehabilitationRecordInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MemberRehabilitationRecordInfoDlg.close = function() {
    parent.layer.close(window.parent.MemberRehabilitationRecord.layerIndex);
}

/**
 * 收集数据
 */
MemberRehabilitationRecordInfoDlg.collectData = function() {
    this
    .set('id')
    .set('member')
    .set('department')
    .set('produce')
    .set('feel')
    .set('createTime');
}

/**
 * 提交添加
 */
MemberRehabilitationRecordInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/memberRehabilitationRecord/add", function(data){
        Feng.success("添加成功!");
        window.parent.MemberRehabilitationRecord.table.refresh();
        MemberRehabilitationRecordInfoDlg.close();
        $(window.parent)[0].MemberRehabilitationRecord.getrecure(); //重新初始化頁面
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.memberRehabilitationRecordInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MemberRehabilitationRecordInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/memberRehabilitationRecord/update", function(data){
        Feng.success("修改成功!");
        window.parent.MemberRehabilitationRecord.table.refresh();
        MemberRehabilitationRecordInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.memberRehabilitationRecordInfoData);
    ajax.start();
    window.parent.MemberRehabilitationRecord.getrecure();
}

$(function() {

});
