/**
 * 初始化健康档案详情对话框
 */
var MemberHealthRecordInfoDlg = {
    memberHealthRecordInfoData: {}
};

/**
 * 清除数据
 */
MemberHealthRecordInfoDlg.clearData = function () {
    this.memberHealthRecordInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MemberHealthRecordInfoDlg.set = function (key, val) {
    this.memberHealthRecordInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MemberHealthRecordInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MemberHealthRecordInfoDlg.close = function () {
    parent.layer.close(window.parent.MemberHealthRecord ? window.parent.MemberHealthRecord.layerIndex : window.parent.Member.layerIndex);
}

/**
 * 收集数据
 */
MemberHealthRecordInfoDlg.collectData = function () {
    this
        .set('id')
        .set('member')
        .set('project')
        .set('disease')
        .set('evaluation')
        .set('doc')
        .set('remark')
        .set('department')
        .set('createdBy')
        .set('createTime')
        .set('flag')
        .set('surveyInfo')
        .set('evaluaInfo')
        .set('tcmSurveyInfo');
}

// window.onload = function(){
//     $.ajax({
//         url: 'memberHealthRecord/getNameAndCardById'
//         , type: 'post'
//         , async: false
//         , data: {
//             id: localStorage.getItem("id")
//         }
//         , success: function (data) {
//             console.log("111");
//         }
//         , error: function (e) {
//             console.log(e);
//         }
//     });
// }

/**
 * 提交添加
 */
MemberHealthRecordInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();
    var att = [];
    $('.att-link').each(function () {
        att.push($(this).attr('href'));
    });
    this.set('attachment', att);

    var faceImgAttachment = [];
    $('.att-link-faceImgAttachment').each(function () {
        faceImgAttachment.push($(this).attr('src'));
    });
    this.set('faceImgAttachment', faceImgAttachment);

    var tongueImgAttachment = [];
    $('.att-link-tongueImgAttachment').each(function () {
        tongueImgAttachment.push($(this).attr('src'));
    });
    this.set('tongueImgAttachment', tongueImgAttachment);

    var partImgAttachment = [];
    $('.att-link-partImgAttachment').each(function () {
        partImgAttachment.push($(this).attr('src'));
    });
    this.set('partImgAttachment', partImgAttachment);


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/memberHealthRecord/add", function (data) {
        if (data.code != 200) {
            Feng.alert(data.message);
            return;
        }
        Feng.success("添加成功!");
        // window.parent.MemberHealthRecord.table.refresh();
        MemberHealthRecordInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.memberHealthRecordInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MemberHealthRecordInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/memberHealthRecord/update", function (data) {
        Feng.success("修改成功!");
        window.parent.MemberHealthRecord.table.refresh();
        MemberHealthRecordInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.memberHealthRecordInfoData);
    ajax.start();
}

MemberHealthRecordInfoDlg.disease = function () {
    $(".tanc")[0].style.display = "block";

    ajax_back('post', 'json', '/memberHealthRecord/projectSup', function (data) {
        console.log(data)
    })
}

function ajax_back(type, datatype, url, data) {
    $.ajax({
        type: type,
        url: url,
        dataType: datatype,
        async: false,
        success: function (data) {
            if ($(".gd")[0].innerHTML == "") {
                for (let i = 0; i < data.length; i++) {
                    var _div = document.createElement('div')
                    _div.innerHTML = data[i].name;
                    $(".gd")[0].append(_div)
                    _div.onclick = function () {
                        if ($(".first")[0]) {
                            $(".first")[0].className = ""
                        }
                        $(".gd")[0].children[i].className = 'first'
                        if (data[i].id == 1) {
                            $(".ej")[0].innerHTML = null;
                            $(".name")[0].innerHTML = null;
                            $.ajax({
                                type: "post",
                                url: "/memberHealthRecord/solutionsOne",
                                dataType: "json",
                                async: false,
                                success: function (data) {
                                    for (let j = 0; j < data.length; j++) {
                                        // var _div = document.createElement('div')
                                        // _div.innerHTML = data[j].name;
                                        // $(".ej")[0].append(_div)
                                        // _div.onclick = function () {
                                        //     if ($(".bg")[0]) {
                                        //         $(".bg")[0].className = ""
                                        //     }
                                        //     $(".ej")[0].children[j].className = 'bg'
                                        $(".name")[0].innerHTML = null;
                                        // for (let k = 0; k < data[j].length; k++) {
                                        var _div = document.createElement('div')
                                        var _div1 = document.createElement('div')
                                        var _div2 = document.createElement('div')
                                        var _check = document.createElement('input')
                                        _check.type = "checkbox"
                                        _check.onclick = function () {
                                            clickinput(this)
                                        }
                                        // _check.addEventListener('click',function (e) {
                                        //     clickinput(e)
                                        // })
                                        _check.name = data[j].name
                                        _div1.append(_check)
                                        _div1.append(data[j].name);
                                        _div2.innerHTML = data[j].solution;
                                        _div.append(_div1);
                                        _div.append(_div2);
                                        $(".name")[0].append(_div)
                                        // }
                                        // }
                                    }
                                },
                            })
                        } else if (data[i].id == 2) {
                            $(".ej")[0].innerHTML = null;
                            $(".name")[0].innerHTML = null;
                            // MemberHealthRecordInfoDlg.selectSolution();

                            $.ajax({
                                type: "post",
                                url: "/memberHealthRecord/projectSecond",
                                dataType: "json",
                                async: false,
                                success: function (data) {
                                    for (let j = 0; j < data.length; j++) {
                                        var _div = document.createElement('div')
                                        _div.innerHTML = data[j].name;
                                        $(".ej")[0].append(_div)
                                        _div.onclick = function () {
                                            $(".tj")[0].innerHTML = null;
                                            $(".name")[0].innerHTML = null;
                                            if (data[j].id == 7) {
                                                $(".ej")[0].style.display = "block";
                                                if ($(".second")[0]) {
                                                    $(".second")[0].className = ""
                                                }
                                                $(".ej")[0].children[j].className = 'second'
                                                $.ajax({
                                                    type: "post",
                                                    url: "/memberHealthRecord/solutionsTwo",
                                                    dataType: "json",
                                                    async: false,
                                                    success: function (data) {
                                                        for (let j = 0; j < data.length; j++) {
                                                            var _div = document.createElement('div')
                                                            _div.innerHTML = data[j].name;
                                                            $(".tj")[0].append(_div)
                                                            _div.onclick = function () {
                                                                if ($(".bg")[0]) {
                                                                    $(".bg")[0].className = ""
                                                                }
                                                                $(".tj")[0].children[j].className = 'bg'
                                                                $(".name")[0].innerHTML = null;
                                                                for (let k = 0; k < data[j].rps.length; k++) {
                                                                    var _div = document.createElement('div')
                                                                    var _div1 = document.createElement('div')
                                                                    var _div2 = document.createElement('div')
                                                                    var _check = document.createElement('input')
                                                                    _check.type = "checkbox"
                                                                    _check.onclick = function () {
                                                                        clickinput(this)
                                                                    }
                                                                    // _check.addEventListener('click',function (e) {
                                                                    //     clickinput(e)
                                                                    // })
                                                                    _check.name = data[j].rps[k].name
                                                                    _div1.append(_check)
                                                                    _div1.append(data[j].rps[k].name);
                                                                    _div2.innerHTML = data[j].rps[k].solution;
                                                                    _div.append(_div1);
                                                                    _div.append(_div2);
                                                                    $(".name")[0].append(_div)
                                                                }
                                                            }
                                                        }
                                                    },
                                                })
                                            }else if (data[j].id == 4) {
                                                $(".ej")[0].style.display = "block";
                                                if ($(".second")[0]) {
                                                    $(".second")[0].className = ""
                                                }
                                                $(".ej")[0].children[j].className = 'second'
                                                $.ajax({
                                                    type: "post",
                                                    url: "/memberHealthRecord/solutionsFour",
                                                    dataType: "json",
                                                    async: false,
                                                    success: function (data) {
                                                        $(".name")[0].innerHTML = null;
                                                        for (let k = 0; k < data.length; k++) {
                                                            var _div = document.createElement('div')
                                                            var _div1 = document.createElement('div')
                                                            var _div2 = document.createElement('div')
                                                            var _check = document.createElement('input')
                                                            _check.type = "checkbox"
                                                            _check.onclick = function () {
                                                                clickinput(this)
                                                            }
                                                            _check.name = data[k].name
                                                            _div1.append(_check)
                                                            _div1.append(data[k].name);
                                                            _div2.innerHTML = data[k].solution;
                                                            _div.append(_div1);
                                                            _div.append(_div2);
                                                            $(".name")[0].append(_div)
                                                        }
                                                    },
                                                })
                                            }else if (data[j].id == 5) {
                                                $(".ej")[0].style.display = "block";
                                                if ($(".second")[0]) {
                                                    $(".second")[0].className = ""
                                                }
                                                $(".ej")[0].children[j].className = 'second'
                                                $.ajax({
                                                    type: "post",
                                                    url: "/memberHealthRecord/solutionsFive",
                                                    dataType: "json",
                                                    async: false,
                                                    success: function (data) {
                                                        $(".name")[0].innerHTML = null;
                                                        for (let k = 0; k < data.length; k++) {
                                                            var _div = document.createElement('div')
                                                            var _div1 = document.createElement('div')
                                                            var _div2 = document.createElement('div')
                                                            var _check = document.createElement('input')
                                                            _check.type = "checkbox"
                                                            _check.onclick = function () {
                                                                clickinput(this)
                                                            }
                                                            _check.name = data[k].name
                                                            _div1.append(_check)
                                                            _div1.append(data[k].name);
                                                            _div2.innerHTML = data[k].solution;
                                                            _div.append(_div1);
                                                            _div.append(_div2);
                                                            $(".name")[0].append(_div)
                                                        }
                                                    },
                                                })
                                            }else if (data[j].id == 6) {
                                                $(".ej")[0].style.display = "block";
                                                if ($(".second")[0]) {
                                                    $(".second")[0].className = ""
                                                }
                                                $(".ej")[0].children[j].className = 'second'
                                                $.ajax({
                                                    type: "post",
                                                    url: "/memberHealthRecord/solutionsSix",
                                                    dataType: "json",
                                                    async: false,
                                                    success: function (data) {
                                                        $(".name")[0].innerHTML = null;
                                                        for (let k = 0; k < data.length; k++) {
                                                            var _div = document.createElement('div')
                                                            var _div1 = document.createElement('div')
                                                            var _div2 = document.createElement('div')
                                                            var _check = document.createElement('input')
                                                            _check.type = "checkbox"
                                                            _check.onclick = function () {
                                                                clickinput(this)
                                                            }
                                                            _check.name = data[k].name
                                                            _div1.append(_check)
                                                            _div1.append(data[k].name);
                                                            _div2.innerHTML = data[k].solution;
                                                            _div.append(_div1);
                                                            _div.append(_div2);
                                                            $(".name")[0].append(_div)
                                                        }
                                                    },
                                                })
                                            }
                                        }
                                    }
                                },
                            })
                        } else {
                            $(".ej")[0].innerHTML = null;
                            $(".name")[0].innerHTML = null;
                            $.ajax({
                                type: "post",
                                url: "/memberHealthRecord/solutionsThree",
                                dataType: "json",
                                async: false,
                                success: function (data) {
                                    $(".name")[0].innerHTML = null;
                                    for (let k = 0; k < data.length; k++) {
                                        var _div = document.createElement('div')
                                        var _div1 = document.createElement('div')
                                        var _div2 = document.createElement('div')
                                        var _check = document.createElement('input')
                                        _check.type = "checkbox"
                                        _check.onclick = function () {
                                            clickinput(this)
                                        }
                                        _check.name = data[k].name
                                        _div1.append(_check)
                                        _div1.append(data[k].name);
                                        _div2.innerHTML = data[k].solution;
                                        _div.append(_div1);
                                        _div.append(_div2);
                                        $(".name")[0].append(_div)
                                    }
                                },
                            })
                        }
                    }
                }
            }

        },
        data: {
            dataDetail: JSON.stringify(data)
        },
        error: function (err) {
            console.log(err);
        }
    })
}

function clickinput(that) {
    var value = $(that).attr("name");
    var remarks = $(that).parent().next()[0].textContent;
    var solut = value + " " + remarks;
    var cs = document.getElementById('evaluation');
    var cout_c = $(cs).val();
    var array = cout_c.split(",")
    if (cout_c == "") {
        array = []
    }
    // var pat1 = new RegExp(value,"g");
    // var ca = pat1.test(cout_c);

    if (array.indexOf(solut) != -1) {
        array.splice(array.indexOf(solut), 1);
    } else {
        array.push(solut)
    }
    $(cs).val(array.join(","));
    // if(ca){
    //     var cc = cout_c.replace(new RegExp(solut,"g"), "");
    //     if(cc.indexOf(solut) == -1){
    //         cc.splice("，")
    //     }
    //     $(cs).val(cc);
    // }else{
    //     if(cout_c==""){
    //         $(cs).val(cout_c+ ""+solut);
    //     }else{
    //         $(cs).val(cout_c+ "，"+solut);
    //     }
    // }

}

MemberHealthRecordInfoDlg.closeDisease = function () {
    $(".tanc")[0].style.display = "none";
}

/**
 * 选择方案
 */
MemberHealthRecordInfoDlg.selectSolution = function () {
    var index = layer.open({
        type: 2,
        title: '选择健康方案',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/memberHealthRecord/solutions'
    });
    this.layerIndex = index;
}

/**
 * 康复报告
 */
MemberHealthRecordInfoDlg.openRehabilitationProgram = function () {
    var hrid = $("#id").val();
    var index = parent.layer.open({
        type: 2,
        title: '选择健康方案',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/rehabilitationProgram/rehabilitationProgram_update/0?hrid=' + hrid
    });
    parent.layerIndex = index;
}

/**
 * 从表格获取id,name,手机号到当前页面
 */
MemberHealthRecordInfoDlg.getidnameSulute = function (result) {
    let list = result.replace(/#/, '');
    let arr = list.split('&');
    $('#member').val(arr[0]);
    $('#memberValue').val(arr[1]);
    $('#memmobile').html(arr[2]);
    $('#reatetime').html(arr[3]);

}

$(function () {
    // var hrUp = new $DocUpload("doc");
    // hrUp.setUploadBarId("progressBar");
    // hrUp.init();
    var hrUp = new $MultiDocUpload("doc");
    hrUp.setUploadBarId("progressBar");
    hrUp.init();

    var hrUp = new $MultiDocUpload("sdd");
    hrUp.setUploadBarId("progressBar");
    hrUp.init();

    var hrUps = new $MultiDocUpload("faceImgAttachment");
    hrUps.setUploadBarId("progressBar");
    hrUps.init();

    var hrUps = new $MultiDocUpload("addss");
    hrUps.setUploadBarId("progressBar");
    hrUps.init();

    var hrUps = new $MultiDocUpload("tongueImgAttachment");
    hrUps.setUploadBarId("progressBar");
    hrUps.init();

    var hrUps = new $MultiDocUpload("partImgAttachment");
    hrUps.setUploadBarId("progressBar");
    hrUps.init();
    try {

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
            $.post("/member/infoById", {id: result['id']}, function (result) {
                $('#memmobile').text(result.idCard);
            });
        });

        var hrDeptBsSuggest = $("#departmentValue").bsSuggest({
            idField: 'dept',
            keyField: 'name',
            allowNoKeyword: false,
            multiWord: true,
            separator: ",",
            getDataMethod: "url",
            effectiveFieldsAlias: {dept: "编号", name: "名称"},
            showHeader: true,
            url: "/dept/suggestList/",
            processData: function (json) {
                var i, len, data = {value: []};
                if (!json || json.length == 0) {
                    return false
                }
                len = json.length;
                for (i = 0; i < len; i++) {
                    data.value.push({"dept": json[i]['id'], "name": json[i]['name']})
                }
                return data
            }
        });
    } catch (e) {
        console.log(e);
    }
    $("input#departmentValue").on("onSetSelectValue", function (event, result) {
        $('#department').val(result['id']);
        $.post("/mgr/userByDepartment", {dept: result['id']}, function (result) {
            $('#createdBy').empty();
            $.each(result, function (i, dt) {
                $('#createdBy').append($('<option>').val(dt.value).text(dt.key));
            });
        });
    });
    MemberHealthRecordInfoDlg.getidnameSulute(decodeURIComponent(window.location.hash));
});
$("#memberValue").on('click', function () {
    $relute = $('#memberValue').val();
    if ($relute == ' ' || $relute == '') {
        $('#memberValue').val(' ');
    }
});
