function jqprin_add() {
    $("#questionnaire").jqprint({
        debug: false, //如果是true则可以显示iframe查看效果（iframe默认高和宽都很小，可以再源码中调大），默认是false
        importCSS: true, //true表示引进原来的页面的css，默认是true。（如果是true，先会找$("link[media=print]")，若没有会去找$("link")中的css文件）
        printContainer: true, //表示如果原来选择的对象必须被纳入打印（注意：设置为false可能会打破你的CSS规则）。
        operaSupport: false //表示如果插件也必须支持歌opera浏览器，在这种情况下，它提供了建立一个临时的打印选项卡。默认是true
    });
}

layui.use('layer', function() {
    var layer = layui.layer;

    layer.open({
        type: 3
        ,icon:2
        ,time: 2000
    })

})
/*
    data 为json对象数据      object
    elem 为id名字，挂在元素上   string
    arrayx 为需要的数据从哪到哪 如：[0,3]  array
    strx 给定值         string

*/
var optionCategory = ['阴虚体质', '阳虚体质', '气虚体质', '痰湿体质', '淤血体质', '湿热质', '气郁质', '特禀质', '平和体质', '心脑血管方面', '泌尿系统方面', '骨关节方面', '女性疾病', '其他症状'];

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
        label_spl_a = '#one' + i;
        label_spl_b = '#two' + i;
        label_spl_c = '#fast' + i;

        if (ars[i][1].length != 0) //判断数组中第二个数组数量，如果为0说明是model>2的类型
        {
            for (var j = 0; j < 2; j++) {
                if (j == 0) {
                    for (var x = 0; x < ars[i][j].length; x++) {
                        vb = '<p id="one_' + ars_id[i][j][x] + '">' + ars[i][j][x] + '<input type="checkbox"> </p>'
                        $(label_spl_a).append(vb)
                    }
                } else if (j == 1) {
                    for (var x = 0; x < ars[i][j].length; x++) {
                        vc = '<p id="two_' + ars_id[i][j][x] + '">' + ars[i][j][x] + '<input type="checkbox"> </p>'
                        $(label_spl_b).append(vc)
                    }
                }
            }
        } else {
            for (var x = 0; x < ars[i][0].length; x++) {
                vz = '<span id="fast_' + ars_id[i][0][x] + '">' + ars[i][0][x] + '<input type="checkbox"> </span>';
                $(label_spl_c).append(vz);
            }
        }
    }

}

var optionCategor_len = [];

function ajax_back(type, datatype, url) {

    $.ajax({
        type: type,
        url: url,
        dataType: datatype,
        async: false,
        success: function(data) {
            datax = data
        }
    })
    return datax
}

$(document).ready(function() {
    var a = [];
    $.post(
        '/memberHealthSurvey/list', {},
        function(data, status) {
            toucks(data);
        }
    )

})