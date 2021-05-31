$(function() {

    var memberFamilyBsSuggest = $("#memberFamilyValue").bsSuggest({
        idField: 'member_user',
        keyField: 'appellation',
        allowNoKeyword: false,
        multiWord: true,
        separator: ",",
        getDataMethod: "url",
        effectiveFieldsAlias: {member_user: "编号", appellation: "称呼", name: "姓名"},
        showHeader: true,
        url: "/memberFamily/suggestList/",
        processData: function (json) {
            var i, len, data = {value: []};
            if (!json || json.length == 0) {
                return false
            }
            len = json.length;
            for (i = 0; i < len; i++) {
                data.value.push({"member_user": json[i]['id'], "appellation": json[i]['appellation'], "name": json[i]['name']})
            }
            return data
        }
    });
    $("input#memberFamilyValue").on("onSetSelectValue", function (event, result) {
        $('#family').val(result['id']);
        $.post("/memberFamily/infoById",{id:result['id']},function(result){
            $('#name').text(result.name);
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
    $("input#departmentValue").on("onSetSelectValue", function (event, result) {
        $('#department').val(result['id']);
        $.post("/mgr/userByDepartment",{dept:result['id']},function(result){
            $('#createdBy').empty();
            $.each(result,function(i,dt){
                $('#createdBy').append($('<option>').val(dt.value).text(dt.key));
            });
        });
    });
});