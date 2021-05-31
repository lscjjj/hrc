/**
 * 初始化详情对话框
 */
var MemberUserInfoDlg = {
    memberUserInfoData : {}
};

/**
 * 清除数据
 */
MemberUserInfoDlg.clearData = function() {
    this.memberUserInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MemberUserInfoDlg.set = function(key, val) {
    this.memberUserInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MemberUserInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MemberUserInfoDlg.close = function() {
    parent.layer.close(window.parent.MemberUser.layerIndex);
}

/**
 * 收集数据
 */
MemberUserInfoDlg.collectData = function() {
    this
    .set('id')
    .set('memberUser')
    .set('name')
    .set('wechat')
    .set('birthday')
    .set('email')
    .set('phone')
    .set('address')
    .set('createtime');
}

/**
 * 提交添加
 */
MemberUserInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/memberUser/add", function(data){
        Feng.success("添加成功!");
        window.parent.MemberUser.table.refresh();
        MemberUserInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.memberUserInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MemberUserInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/memberUser/update", function(data){
        Feng.success("修改成功!");
        // window.parent.MemberUser.table.refresh();
        MemberUserInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.memberUserInfoData);
    ajax.start();
}

$(function() {

});
