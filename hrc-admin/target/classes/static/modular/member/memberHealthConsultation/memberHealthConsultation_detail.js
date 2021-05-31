/**
 * 查看详细数据渲染
 */
function hzsdata(id){
    layui.use('laytpl', function(){
        var laytpl = layui.laytpl;

        $.ajax({
            url: '/memberHealthConsultation/memberHealthConsultation_detail/' + id
            ,type: 'post'
            ,async: false
            ,data: ''
            ,success: function(data){
                data_con = data
            }
            ,error: function(e)
            {
                console.log(e);
            }
        });

        var getTpl = detailed_src.innerHTML
            ,view = document.getElementById('detailed_view');

        laytpl(getTpl).render(data_con,function(html){
            view.innerHTML = html;
        });
    });
};

$(function () {
    let id_member = window.location.hash.split('=');
    var data_con = null;

    hzsdata(id_member[1]);
});