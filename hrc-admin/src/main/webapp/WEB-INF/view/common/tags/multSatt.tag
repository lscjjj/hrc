@/*
        文件上传参数的说明:
        name : 名称
        id : 头像的id
@*/
<div class="form-group" style="display: flex;flex-direction: column;text-align: center;flex: 1">
        <div>
                <div id="${id}PreId">

                </div>
        </div>
        <div>
                <div class="upload-btn" id="${id}BtnId">

                        <img src="${ctxPath}/static/img/lgcupdate.png" width="35px" height="36px">
                </div>
                <label class="control-label head-scu-label" style="margin-top: 0;">${name}</label>
        </div>
        <input type="hidden" id="${id}" value="${iconImg!}"/>
</div>