/**
 * 初始化详情对话框
 */
var MemberFamilyInfoDlg = {
    memberFamilyInfoData : {}
};

/**
 * 清除数据
 */
MemberFamilyInfoDlg.clearData = function() {
    this.memberFamilyInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MemberFamilyInfoDlg.set = function(key, val) {
    this.memberFamilyInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MemberFamilyInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MemberFamilyInfoDlg.close = function() {
    parent.layer.close(window.parent.MemberFamily.layerIndex);
}

/**
 * 收集数据
 */
MemberFamilyInfoDlg.collectData = function() {
    this
    .set('id')
    .set('memberUser')
    .set('appellation')
    .set('name')
    .set('gender')
    .set('birthday')
    .set('medicalHistory')
    .set('createtime');
}

/**
 * 提交添加
 */
MemberFamilyInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/memberFamily/add", function(data){
        Feng.success("添加成功!");
        window.parent.MemberFamily.table.refresh();
        MemberFamilyInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.memberFamilyInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MemberFamilyInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/memberFamily/update", function(data){
        Feng.success("修改成功!");
        window.parent.MemberFamily.table.refresh();
        MemberFamilyInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.memberFamilyInfoData);
    ajax.start();
}

$(function() {

});
