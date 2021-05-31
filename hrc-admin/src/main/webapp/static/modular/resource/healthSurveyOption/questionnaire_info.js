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
                    }
                } else if (j == 1) {
                    for (var x = 0; x < ars[i][j].length; x++) {
                        vc = '<p id="two_' + ars_id[i][j][x] + '">' + ars[i][j][x] + '<input type="checkbox" onclick="check_if(this)"> </p>';
                        $(label_spl_b).append(vc);
                    }
                }
            }
        } else {
            for (var x = 0; x < ars[i][0].length; x++) {
                vz = '<span id="fast_' + ars_id[i][0][x] + '">' + ars[i][0][x] + '<input type="checkbox" onclick="check_if(this)"> </span>';
                $(label_spl_c).append(vz);
            }
        }



    }

}

/**
 * 判断checkbox选中
 * ting 为this
 */
function check_if(ting){
    let check_name = $(ting).parent()[0].innerText;

    if($(ting).attr('checked') == "checked"){
        $(ting).attr('checked',false);
        let num = survey_info.indexOf(check_name);
        survey_info.splice(num,1);
        console.log(survey_info);
        $('.form-control')[0].value = survey_info.join();
    }else{
        $(ting).attr('checked',true);
        survey_info.push(check_name);
        console.log(survey_info);
        $('.form-control')[0].value = survey_info.join();
    }
}

/**
 * 确定按钮函数
 */
// function member_fix (index){
//
//     if(survey_info.length == 0)
//     {
//         layui.open('layer', function (){
//             var layer = layui.layer;
//             layer.msg('还没有选中！')
//         })
//     }else{
//         ajax_back('post', 'json', '/memberHealthSurvey/update' ,survey_info)  //url未写
//         console.log('测试成功');
//     }
// }

var optionCategor_len = [];
/* function touck(data ,elem ,arrayx ,strx){
    var vb = '';
    if ($.isEmptyObject(data)){
        data = {};
    }else if(!$.isArray(arrayx)){
        arrayx = [];
    }else if(elem == '' || elem == null || elem == undefined){
        elem = '';
    }

    if(elem.substr(0,1) != '#'){
        elem = '#'+ elem;
    }

    if(arrayx.length == 1){
            var val_num = $.inArray(strx,optionCategory)
            arrayx = [arrayx[0] ,optionCategor_len[val_num]-1];
    }
    var num_x;
    var num_hzs = 0;
    if($.inArray(strx,optionCategory) == 0){  //判断strx在optionCategory的位置
                    num_x = 0;
                }else
                {
                    num_x = 1;
                    for (var d=0;d<$.inArray(strx,optionCategory);d++){
                        num_hzs += optionCategor_len[d];
                    }
                }
    var num_a = num_hzs + arrayx[0] ; //开始获取data的位置
    var num_b = num_hzs + arrayx[1] ; //结束获取data的位置
    if($.inArray(strx,optionCategory) == 0)
    {
        num_a = arrayx[0];
        num_b = arrayx[1];
    }

    for(var f = num_a;f<=num_b;f++){  //打印标签
        if($.inArray(strx,optionCategory)<=8){
            vb += '<p>' + data[f].name + '<input type="checkbox"> </p>';
        }else{
            vb += '<span>' + data[f].name + '<input type="checkbox"> </span>';
        }
    }

    if('vb' != ''){
        $(elem).append(vb);
    }
} */


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
            /*                         for(var j=0;j<optionCategory.length;j++){
                                        var num_len = 0;
                                        for (var k=0;k<data.length;k++){
                                            if(data[k].optionCategory == optionCategory[j])
                                            {
                                                num_len += 1;
                                            }
                                        }
                                        optionCategor_len.push(num_len);
                                    } */
            toucks(data);
        }
    )

})