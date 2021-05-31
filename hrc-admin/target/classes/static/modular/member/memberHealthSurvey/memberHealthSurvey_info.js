/**
 * 初始化会员健康状况调查详情对话框
 */
var MemberHealthSurveyInfoDlg = {
    memberHealthSurveyInfoData : {}
};

/**
 * 清除数据
 */
MemberHealthSurveyInfoDlg.clearData = function() {
    this.memberHealthSurveyInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MemberHealthSurveyInfoDlg.set = function(key, val) {
    this.memberHealthSurveyInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MemberHealthSurveyInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MemberHealthSurveyInfoDlg.close = function() {
    parent.layer.close(window.parent.MemberHealthSurvey.layerIndex);
}

/**
 * 收集数据
 */
MemberHealthSurveyInfoDlg.collectData = function() {
    this
        .set('id')
        .set('member')
        .set('memberUser')
        .set('age')
        .set('surveyInfo')
        .set('address')
        .set('department')
        .set('remark')
        .set('createdBy')
        .set('createTime');
}
/**
 * 确定按钮函数
 */
MemberHealthSurveyInfoDlg.member_fix =function (index){

    this.clearData();
    this.collectData();

    // if(window.parent.survey_info.length == 0)
    // {
    //     layui.open('layer', function (){
    //         var layer = layui.layer;
    //         layer.msg('还没有选中！')
    //     })
    // }else{
    //     ajax_back('post', 'json', '/memberHealthSurvey/update',function (data){
    // Feng.success("编辑成功!");
    // })  //url未写
    // console.log('测试成功');
    // }
    var ajax = new $ax(Feng.ctxPath + "/memberHealthSurvey/update", function(data){
        Feng.success("修改成功!");
        window.parent.MemberHealthSurvey.table.refresh();
        MemberHealthSurveyInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.memberHealthSurveyInfoData);
    ajax.start();
}
/**
 * 提交添加
 */
MemberHealthSurveyInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/memberHealthSurvey/add", function(data){
        Feng.success("添加成功!");
        window.parent.MemberHealthSurvey.table.refresh();
        MemberHealthSurveyInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.memberHealthSurveyInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MemberHealthSurveyInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/memberHealthSurvey/update", function(data){
        Feng.success("修改成功!");
        window.parent.MemberHealthSurvey.table.refresh();
        MemberHealthSurveyInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.memberHealthSurveyInfoData);
    ajax.start();
}

var optionCategory = ['阴虚体质', '阳虚体质', '气虚体质', '痰湿体质', '淤血体质', '湿热质', '气郁质', '特禀质', '平和体质', '心脑血管方面', '泌尿系统方面', '骨关节方面', '女性疾病', '其他症状'];
var survey_info = [];
/*
    data array 进行处理的数据
    return_judge 值为1 进行返回处理后的数据
*/
function toucks(data, return_judge) {
    /*
        循环获取体质，添加到数组
    */
    var ars = new Array(14);
    var ars_id = new Array(14);
    var data_count = data;

    for (var j = 0; j < optionCategory.length; j++) {
        var ars_son1 = [];
        var ars_son2 = [];
        var ars_son_id1 = [];
        var ars_son_id2 = [];
        for (var i = 0; i < data.length; i++) {
            //判断名字 和 module的位置 如：阴虚体质的module为1
            if (data_count[i].optionCategory == optionCategory[j] && data_count[i].module == 1) {
                //把体制进行分类
                ars_son1.push(data_count[i].name);
                ars_son_id1.push(data_count[i].id);
            } else if (data_count[i].optionCategory == optionCategory[j] && data_count[i].module == 2) {
                ars_son2.push(data_count[i].name);
                ars_son_id2.push(data_count[i].id);
            } else if (data_count[i].optionCategory == optionCategory[j] && data_count[i].module == 3) {
                ars_son1.push(data_count[i].name);
                ars_son_id1.push(data_count[i].id);
            }

        }
        ars[j] = [];
        ars[j].push(ars_son1, ars_son2);
        ars_id[j] = [];
        ars_id[j].push(ars_son_id1, ars_son_id2);
    }
    if (return_judge == 1) //判断需要返回的是处理后的数据还是执行函数
    {
        return ars
    } else {
        toucks_run(ars, ars_id);
    }
    //执行函数 toucks_run(ars)
}

/*执行函数*/
function toucks_run(ars, ars_id) {
    //循环获取到的数组 ars=[[[1],[2]],[[1],[2]],[[1],[[2]]````] 和id
    //ars 和 ars_id 的数组格式一样
    for (var i = 0; i < ars.length; i++) {
        var vb = ''; //one
        var vc = ''; //two
        var vz = ''; //fast
        var label_spl_a = '#one' + i;
        var label_spl_b = '#two' + i;
        var label_spl_c = '#fast' + i;

        //判断数组中第二个数组数量，如果为0说明是model>2的类型
        if (ars[i][1].length != 0)
        {
            for (var j = 0; j < 2; j++) {
                if (j == 0) {
                    for (var x = 0; x < ars[i][j].length; x++) {
                        vb = '<p id="one_' + ars_id[i][j][x] + '">' + ars[i][j][x] + '<input type="checkbox" onclick="check_if(this)"> </p>';
                        $(label_spl_a).append(vb);
                        let one_id_chil = '#one_' + ars_id[i][j][x];
                        check_initx(ars[i][j][x], one_id_chil);
                    }
                } else if (j == 1) {
                    for (var x = 0; x < ars[i][j].length; x++) {
                        vc = '<p id="two_' + ars_id[i][j][x] + '">' + ars[i][j][x] + '<input type="checkbox" onclick="check_if(this)"> </p>';
                        $(label_spl_b).append(vc);
                        let two_id_chil = '#two_' + ars_id[i][j][x];
                        check_initx(ars[i][j][x], two_id_chil);
                    }
                }
            }
        } else {
            for (var x = 0; x < ars[i][0].length; x++) {
                vz = '<span id="fast_' + ars_id[i][0][x] + '">' + ars[i][0][x] + '<input type="checkbox" onclick="check_if(this)"> </span>';
                $(label_spl_c).append(vz);
                let fast_id_chil = '#fast_' + ars_id[i][0][x];
                check_initx(ars[i][0][x], fast_id_chil);
            }
        }



    }

}

/**
 * 初始化checkbox选中
 */
function check_initx(str ,str_id){
    if($(window.parent.document.getElementById('surveyInfo')).val() != undefined){
        let count_val = $(window.parent.document.getElementById('surveyInfo')).val().split(',');

            count_val.forEach(v => {
                    if(v == str){
                        let find_children = $(str_id).children('input');
                        $(find_children).attr('checked',true);
                    }
                }
            );

    }
}

/**
 * 判断checkbox选中
 * ting 为this
 */
function check_if(ting){
    let check_name = $(ting).parent()[0].innerText;
    let suerver_sf = $(window.parent.document.getElementById('surveyInfo')).val();
    let arr_sf = [];
    if($(ting).attr('checked') == "checked"){
        $(ting).attr('checked',false);
        var survey_info = $(window.parent.document.getElementById('surveyInfo')).val();
        survey_info = survey_info.split(',');
        let num = survey_info.indexOf(check_name);
        survey_info.splice(num,1);
        $(window.parent.document.getElementById('surveyInfo')).val(survey_info.join());
    }else{
        $(ting).attr('checked',true);
        //if($('.form-control')[0].value.length != 0 && survey_info.length == 0)
        if(suerver_sf.length != 0)
        {
            arr_sf.push(suerver_sf,check_name)
        }else{
            arr_sf.push(check_name)
        }
        $(window.parent.document.getElementById('surveyInfo')).val(arr_sf.join());

    }
}

/**
 * 确定按钮函数
 */

var optionCategor_len = [];

function ajax_back(type, datatype, url ,data) {

    $.ajax({
        type: type,
        url: url,
        dataType: datatype,
        async: false,
        success: function(data) {
            console.log(data);
        },
        data: {
            dataDetail: JSON.stringify(data)
        },
        error:function (err)
        {
            console.log(err);
        }
    })
}

$(document).ready(function() {
    var a = [];
    $.post(
        '/healthSurveyOption/list', {},
        function(data, status) {
            toucks(data);
        }
    )

})
