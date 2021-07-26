var t1D;
/**
 * 初始化会员结算详情对话框
 */
var MemberSettlementInfoDlg = {
    memberSettlementInfoData: {}
};

/**
 * 清除数据
 */
MemberSettlementInfoDlg.clearData = function () {
    this.memberSettlementInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MemberSettlementInfoDlg.set = function (key, val) {
    this.memberSettlementInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MemberSettlementInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MemberSettlementInfoDlg.close = function () {
    parent.layer.close(window.parent.MemberSettlement.layerIndex);
}

/**
 * 收集数据
 */
MemberSettlementInfoDlg.collectData = function () {
    this
        .set('id')
        .set('treatment')
        .set('paymentMethod')
        .set('paymentAmount')
        .set('memberRating')
        .set('foregroundRating')
        .set('technicianRating')
        .set('managerRating')
        .set('membershipCard')
        .set('member')
        .set('status')
        .set('createTime')
        .set('flag')
        .set('transferCardConsumption')
        .set('transferAmount')
        .set('otherBusiness');
}

/**
 * 提交添加
 */
MemberSettlementInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    var cs = new Array();
    var flag = true;    // true继续;false取消
    var t;
    if(t1D == 1){
        t = $("#treatment1").val();
    }else{
        t = $("#treatment").val();
    }
    $("#consumablePane").find(" > div").each(function (i, ele) {
        var c = new Object();
        var cv = $(ele).find(".consumableValue").val();
        var ca = $(ele).find(".consumableAmount").val();
        if (cv == '' && t == '' && isNaN(cv) && isNaN(t)) {
            // Feng.error('请检查耗材使用情况!');
            flag = false;
            Feng.error("耗材和康复项目其中一个不得为空!");
            return;
        }
        c.id = cv;
        c.amount = ca;
        cs.push(c);
    });
    if (!flag)
        return;
    this.set("consumable", JSON.stringify(cs));

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/memberSettlement/add", function (data) {
        if (data.code == 400) {
            Feng.alert(data.message);
            return;
        }
        Feng.success("添加成功!");
        window.parent.MemberSettlement.table.refresh();
        MemberSettlementInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.memberSettlementInfoData);
    ajax.start();
}
function jump(){
    MemberSettlementInfoDlg.close();
}
/**
 * 提交修改
 */
MemberSettlementInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/memberSettlement/update", function (data) {
        Feng.success("修改成功!");
        window.parent.MemberSettlement.table.refresh();
        MemberSettlementInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.memberSettlementInfoData);
    ajax.start();
}

MemberSettlementInfoDlg.addConsumable = function () {
    var dept = $('#department').val();
    if (dept == '') {
        Feng.error("请选择所在门店!");
        return;
    }
    $('#consumablePane').append("<div class=\"col-sm-12\" style=\"display: flex;margin-top: 10px;\"><div class=\"input-group col-sm-9\" style=\"float: left;display: flex;width: 100%;height: 40px;line-height: 40px;text-align: center;\"><input type=\"text\" class=\"form-control consumableName\" placeholder=\"请选择耗材\" style=\"margin-left: 20px;\"><input type='hidden' class='consumableValue'/><div class=\"input-group-btn\" style=\"width: 30px;\"><button type=\"button\" class=\"btn btn-white dropdown-toggle\" data-toggle=\"dropdown\"><span class=\"caret\"></span></button><ul class=\"dropdown-menu dropdown-menu-right\" role=\"menu\" style=\"margin-left: 1000px;\"></ul></div><div class=\"tableWidth\"><span class=\"number\" style=\"font-size: 15px;color: #999999\"\">未识别</span></div><div class=\"tableWidth\"><span class=\"specification\" style=\"font-size: 15px;color: #999999\">未识别</span></div><div class=\"tableWidth\"><span class=\"measureUnit\" style=\"font-size: 15px;color: #999999\">未识别</span></div><div class=\"tableWidth\"><span class=\"price\" style=\"font-size: 15px;color: #999999\">未识别</span></div><div class=\"tableWidth\"> <input type='hidden' class='consumableAvailable'/> <input type=\"text\" class=\"form-control consumableAmount\" placeholder=\"请输入数量\"></div><div class=\"tableWidth\"><input class=\"form-control\" id=\"kprice\" name=\"kprice\" type=\"text\" placeholder=\"输入金额\" style=\"margin-left: 10px;\" readonly><button type=\"button\" class=\"btn btn-danger delete-consumable\" onclick=\"MemberSettlementInfoDlg.deleteConsumable(event)\" class=\"delete-consumable\" style=\"margin-left: 2%;margin-right: 10px;\"><i class=\"fa fa-remove\"></i>&nbsp;删除</button></div></div>");
    $(".consumableName").on('click', function () {
        $relute = $(this).val();
        if ($relute == ' ' || $relute == '') {
            $(this).val(' ');
        }
    });
    $(function () {
        $(".consumableName").bsSuggest({
            idField: 'consumable',
            keyField: 'name',
            allowNoKeyword: false,
            multiWord: true,
            separator: ",",
            getDataMethod: "url",
            effectiveFieldsAlias: {consumable: "编号", name: "名称", available: "可用数量"},
            showHeader: true,
            url: "/consumable/list?id=" + dept,
            listAlign: "left",
            processData: function (json) {
                var i, len, data = {value: []};
                if (!json || json.length == 0) {
                    return false
                }
                len = json.length;

                for (i = 0; i < len; i++) {
                    data.value.push({
                        "consumable": json[i]['id'],
                        "name": json[i]['name'],
                        "available": json[i]['amount']
                    })
                }
                return data
            }
        });
    });
    $("input.consumableName").on("onSetSelectValue", function (event, result) {
        var id = result['id'];
        var selected = $("input.consumableValue[value='" + id + "']").length > 0;
        if (selected) {
            Feng.error("您已选择了该耗材!");
            $(this).val('');
            return;
        }
        var $ca = $(this).parent().children()[7].children[0];
        var $number = $(this).parent().children()[3].children[0];
        var $specification = $(this).parent().children()[4].children[0];
        var $measureUnit = $(this).parent().children()[5].children[0];
        var $price = $(this).parent().children()[6].children[0];
        $(this).siblings('input.consumableValue').val(id);
        $.post("/consumable/list", {c: dept}, function (result) {
            for (let i = 0; i < result.length; i++) {
                if (id == result[i].id) {
                    $ca.value = result[i].amount;
                    $number.innerHTML = result[i].number;
                    $specification.innerHTML = result[i].specification;
                    $measureUnit.innerHTML = result[i].measureUnit;
                    $price.innerHTML = result[i].price;
                }
            }
        });
    });
    var total;
    $("input.consumableAmount").change(function () {
        var ca = parseInt($(this).val());
        var kprice = $(this).parent().parent().children()[8].firstElementChild;
        var price = parseInt($(this).parent().parent().children()[6].children[0].innerHTML);
        var available = parseInt($(this).parent().parent().children()[7].firstElementChild.value);
        if (!available) {
            Feng.error("请选择耗材后再输入数量");
            $(this).val('');
            return;
        }
        if (ca > available) {
            Feng.error("库存耗材数量不足,请确认!");
            $(this).val('');
            kprice.value = ""
        } else {
            total = 0;
            kprice.value = ca * price
            for (var i = 0; i < $("input.consumableAmount").length; i++) {
                var a = parseInt($("input.consumableAmount")[i].value)
                if (a) {
                    total += a
                }
            }
            $("#count").val(total)
        }
        calcPrice($("#paymentMethod").val(), $('#membershipCard').val());
    });
    $(".delete-consumable").click(function () {
        // Feng.confirm("是否删除该耗材?", new function () {
        var a = parseInt($(this).parent().parent().children()[7].children[1].value);
        total = total - a;
        $("#count").val(total);
        calcPrice($("#paymentMethod").val(), $('#membershipCard').val());
        $(this).parent().parent().remove();
        // });
    });
}

$(function () {

    var memberBsSuggest = $("#memberValue").bsSuggest({
        idField: 'member',
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
                data.value.push({
                    "member": json[i]['id'],
                    "name": json[i]['name'],
                    "mobile": json[i]['mobile'],
                    "idCard": json[i]['idCard']
                })
            }
            return data
        }
    });
    $("input#memberValue").on("onSetSelectValue", function (event, result) {
        $('#member').val(result['id']);
        var arr = [];
        for (var i = 0; i < $('.jhover').children().length; i++) {
            arr[i] = $($('.jhover').children()[i]).text();
        }
        $('#cardid').val(arr[3]);
        $("#hyk")[0].checked = false;
        $("#xj")[0].checked = false;
        $("#hyk")[0].click();
        $.post("/treatment/treatmentByMember", {member: result['id']}, function (dt) {
            // var dt = JSON.parse(result);
            // if(dt.length>0){
            //     $('.treatment-pane').show();
            // }
            $('#treatment')[0].innerHTML = ""
            if (dt.length > 0) {
                $('#treatment').show();
                $('#treatment1').hide();
                t1D = 2
                $("#kfprice").val(dt[0].kfprice);
            } else {
                $('#treatment').hide();
                $('#treatment1')[0].value = "";
                $('#treatment1').show();
                t1D = 1
            }
            $.each(dt, function (idx, item) {
                $('#treatment').append('<option value="' + item['pid'] + '">' + item['projects'] + '(' + item['time'] + ')</option>');
            });
        });
        $("#treatment1").change(function () {
            var a = parseInt($("#treatment1").val());
            $.post("/project/selectPrice", {id: a}, function (dt) {
                if (dt.length > 0) {
                    $("#kfprice").val(dt[0].priceOnce1);
                }
                calcPrice($("#paymentMethod").val(), $('#membershipCard').val());
            });
        });
        $("#treatment").change(function () {
            var a = parseInt($("#treatment").val());
            $.post("/project/selectPrice", {id: a}, function (dt) {
                if (dt.length > 0) {
                    $("#kfprice").val(dt[0].priceOnce1);
                }
                calcPrice($("#paymentMethod").val(), $('#membershipCard').val());
            });
        });
    });
    var memberSettlementBsSuggest = $("#treatmentValue").bsSuggest({
        idField: 'treatment',
        keyField: 'name',
        allowNoKeyword: false,
        multiWord: true,
        separator: ",",
        getDataMethod: "url",
        effectiveFieldsAlias: {treatment: "编号", name: "姓名", projects: "康护项目", time: "康护时间"},
        showHeader: true,
        url: "/treatment/suggestList/",
        processData: function (json) {
            var i, len, data = {value: []};
            if (!json || json.length == 0) {
                return false
            }
            len = json.length;
            for (i = 0; i < len; i++) {
                data.value.push({
                    "treatment": json[i]['id'],
                    "name": json[i]['name'],
                    "projects": json[i]['projects'],
                    "time": json[i]['time']
                })
            }
            return data
        }
    });
    $("input#treatmentValue").on("onSetSelectValue", function (event, result) {
        $('#treatment').val(result['id']);
        $.post("/memberSettlement/experienced", {treatment: result['id']}, function (r) {
            // var r = JSON.parse(result);
            if (r.exped != 1) {    // 已经体验过该项目
                $('#paymentMethod').find('option[value="0"]').remove();
            }
        });
        $.post("/treatment/projById", {treatment: result['id']}, function (r) {
            $('#projs').text(r.projects);
            $('#treatmentTime').text(r.time);
        });
    });

    $('#paymentMethod').change(function () {
        var pm = $(this).val();
        if (!pm || pm.length == 0) {
            return;
        }
        var tid = $('#treatment').val();
        var member = $('#member').val();
        // if(!tid||tid.length==0){
        //     Feng.error("请选择康复会员!");
        //     $(this).val('');
        //     return;
        // }
        var $mc = $('#membershipCard');

        // 会员卡选择
        if (pm == 1) {
            $.post("/memberSettlement/cardByTreatment", {treatment: tid, member: member}, function (result) {
                // var r = JSON.parse(result);
                if (result.status != 1) {
                    Feng.error(result.message);
                } else {
                    if (result.message != null && result.message.length > 0) {
                        Feng.info(result.message);
                    }

                    $mc.empty();
                    $mc.append($('<option>').val('').text('请选择会员卡'));
                    $.each(result.data, function (i, dt) {
                        $mc.append($('<option selected = "selected">').val(dt.value).text(dt.key));
                    });
                    $('.mcpane').show();
                    $('#membershipCard').trigger('change');
                }
            });
            return;
        } else {
            $('.mcpane').hide();
            $mc.empty();
            $mc.append($('<option>').val('').text('请选择会员卡'));
        }
        calcPrice(pm, null);
    });
    $('#hyk').click(function () {
        var pm = $(this).val();
        $("#paymentMethod").val(pm)
        if (!pm || pm.length == 0) {
            return;
        }
        var tid = $('#treatment').val();
        var member = $('#member').val();
        // if(!tid||tid.length==0){
        //     Feng.error("请选择康复会员!");
        //     $(this).val('');
        //     return;
        // }
        var $mc = $('#membershipCard');

        // 会员卡选择
        if (pm == 1) {
            $.post("/memberSettlement/cardByTreatment", {treatment: tid, member: member}, function (result) {
                // var r = JSON.parse(result);

                if (result.status != 1) {
                    Feng.error(result.message);
                    $mc.empty();
                    $('.mcpane').hide();
                } else {
                    if (result.message != null && result.message.length > 0) {
                        Feng.info(result.message);
                    }
                    $mc.empty();
                    $mc.append($('<option>').val('').text('请选择会员卡'));
                    $.each(result.data, function (i, dt) {
                        $mc.append($('<option selected = "selected">').val(dt.value).text(dt.key));
                    });
                    $('.mcpane').show();
                    $('#membershipCard').trigger('change');
                }
            });
            return;
        } else {
            $('.mcpane').hide();
            $mc.empty();
            $mc.append($('<option>').val('').text('请选择会员卡'));
        }
        calcPrice(pm, null);
    });
    $('#xj').click(function () {
        var pm = $(this).val();
        $("#paymentMethod").val(pm)
        if (!pm || pm.length == 0) {
            return;
        }
        var tid = $('#treatment').val();
        var member = $('#member').val();
        // if(!tid||tid.length==0){
        //     Feng.error("请选择康复会员!");
        //     $(this).val('');
        //     return;
        // }
        var $mc = $('#membershipCard');

        calcPrice(pm, null);
    });
    $("#otherPay").click(function () {
        $(".tc")[0].style.display = "block";
        // Feng.error("此功能正在开发中!");
    });
    $('#membershipCard').change(function () {
        var mc = $('#membershipCard').val();
        if (!mc || mc.length == 0) {
            $("#paymentAmount").val('');
            return;
        }
        calcPrice(1, mc);
    });
    $('.mcpane').hide();
});

function closeTc(){
    $(".tc")[0].style.display = "none";
}

function calcPrice(pm, mc) {
    if($('#treatment1')[0].style.display == "none"){
        var tid = $('#treatment').val();
    }else{
        var tid = $('#treatment1').val();
    }
    var member = $('#member').val();
    // if(!tid||tid.length==0){
    //     Feng.error("请选择康复会员!");
    //     $('#paymentMethod').val('');
    //     return;
    // }
    var cs = new Array();
    var flag = true;    // true继续;false取消
    $("#consumablePane").find(" > div").each(function (i, ele) {
        var c = new Object();
        var cv = $(ele).find(".consumableValue").val();
        var ca = $(ele).find(".consumableAmount").val();
        if (cv == '' || ca == '' || isNaN(cv) || isNaN(ca)) {
            // Feng.error('请检查耗材使用情况!');
            flag = false;
            return;
        }
        c.id = cv;
        c.amount = ca;
        cs.push(c);
    });
    // 计算价格
    $.post("/memberSettlement/calcPrice", {
        treatment: tid,
        paymentMethod: pm,
        member: member,
        membershipCard: mc,
        consumable: JSON.stringify(cs)
    }, function (result) {
        // var r = JSON.parse(result);
        if (result.status != 1) {
            $("#paymentAmount").val('');
            if (pm == 1) {
                $('#membershipCard').val('');
            }
            Feng.error(result.message);
        } else {
            if (result.message != null && result.message.length > 0) {
                Feng.info(result.message);
            }
            var a = 0;
            if(result.kfprice == 0){
                a = parseInt($("#kfprice").val()) ? parseInt($("#kfprice").val()) : 0;
            }
            // $("#xj")[0].click();
            $("#paymentAmount").val(parseInt(result.price) + a);
            // $("#kprice").val(result.kprice); //康复产品的结算金额
            // $("#kfprice").val(result.kfprice); //康复项目的结算金额
        }

    });

    // var dept = $('#department').val();
    // if(dept==''){
    //     Feng.error("请选择所在门店!");
    //     return;
    // }
    // $('#consumablePane').append("<div class=\"col-sm-12\"><div class=\"input-group col-sm-9\" style=\"float:left;\"><input type=\"text\" class=\"form-control consumableName\" placeholder=\"请选择耗材\"><input type='hidden' class='consumableValue'/> <input type='hidden' class='consumableAvailable'/> <div class=\"input-group-btn\"><button type=\"button\" class=\"btn btn-white dropdown-toggle\" data-toggle=\"dropdown\"><span class=\"caret\"></span></button><ul class=\"dropdown-menu dropdown-menu-right\" role=\"menu\"></ul></div><input type=\"text\" class=\"form-control consumableAmount\" placeholder=\"请输入数量\"></div><button type=\"button\" class=\"btn btn-danger delete-consumable\" onclick=\"MemberSettlementInfoDlg.deleteConsumable(event)\" class=\"delete-consumable\"><i class=\"fa fa-remove\"></i>&nbsp;删除</button></div>");
    // $(".consumableName").bsSuggest({
    //     idField: 'consumable',
    //     keyField: 'name',
    //     allowNoKeyword: false,
    //     multiWord: true,
    //     separator: ",",
    //     getDataMethod: "url",
    //     effectiveFieldsAlias: {consumable: "编号", name: "名称",available:"可用数量"},
    //     showHeader: true,
    //     url: "/consumable/suggestList/"+dept+"/",
    //     listAlign: "left",
    //     processData: function (json) {
    //         var i, len, data = {value: []};
    //         if (!json || json.length == 0) {
    //             return false
    //         }
    //         len = json.length;
    //         for (i = 0; i < len; i++) {
    //             data.value.push({"consumable": json[i]['id'], "name": json[i]['name'], "available": json[i]['available']})
    //         }
    //         return data
    //     }
    // });
    // $("input.consumableName").on("onSetSelectValue", function (event, result) {
    //     var id = result['id'];
    //     var selected = $("input.consumableValue[value='"+id+"']").length>0;
    //     if(selected){
    //         Feng.error("您已选择了该耗材!");
    //         $(this).val('');
    //         return;
    //     }
    //     var $ca = $(this).siblings('input.consumableAvailable');
    //     $(this).siblings('input.consumableValue').val(id);
    //     $.post("/consumable/available",{c:id},function(result){
    //         $ca.val(result.available);
    //     });
    // });
    // $("input.consumableAmount").change(function () {
    //     var ca = parseInt($(this).val());
    //     var available = parseInt($(this).parent().children("input.consumableAvailable").first().val());
    //     if(ca>available){
    //         Feng.error("库存耗材数量不足,请确认!");
    //         $(this).val('');
    //     }
    // });
    // $(".delete-consumable").click(function () {
    //     // Feng.confirm("是否删除该耗材?", new function () {
    //     $(this).parent().remove();
    //     // });
    // });
}
