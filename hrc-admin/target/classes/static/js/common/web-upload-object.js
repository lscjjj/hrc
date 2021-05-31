/**
 * web-upload 工具类
 * 
 * 约定：
 * 上传按钮的id = 图片隐藏域id + 'BtnId'
 * 图片预览框的id = 图片隐藏域id + 'PreId'
 * 
 * @author frank
 */
(function() {
	
	var $WebUpload = function(pictureId) {
		this.pictureId = pictureId;
		this.uploadBtnId = pictureId + "BtnId";
		this.uploadPreId = pictureId + "PreId";
		this.uploadUrl = Feng.ctxPath + '/mgr/upload';
		this.fileSizeLimit = 100 * 1024 * 1024;
		this.picWidth = 800;
		this.picHeight = 800;
        this.uploadBarId = null;
	};

	$WebUpload.prototype = {
		/**
		 * 初始化webUploader
		 */
		init : function() {
			var uploader = this.create();
			this.bindEvent(uploader);
			return uploader;
		},
		
		/**
		 * 创建webuploader对象
		 */
		create : function() {
			var webUploader = WebUploader.create({
				auto : true,
				pick : {
					id : '#' + this.uploadBtnId,
					multiple : false,// 只上传一个
				},
				accept : {
					title : 'Images',
					extensions : 'gif,jpg,jpeg,bmp,png',
                    mimeTypes : 'image/gif,image/jpg,image/jpeg,image/bmp,image/png'
				},
				swf : Feng.ctxPath
						+ '/static/js/plugins/webuploader/Uploader.swf',
				disableGlobalDnd : true,
				duplicate : true,
				server : this.uploadUrl,
				fileSingleSizeLimit : this.fileSizeLimit
			});
			
			return webUploader;
		},

		/**
		 * 绑定事件
		 */
		bindEvent : function(bindedObj) {
			var me =  this;
			bindedObj.on('fileQueued', function(file) {
			    console.log(file);
				var $li = $('<div><img width="100px" height="100px"></div>');
				var $img = $li.find('img');

				$("#" + me.uploadPreId).append($li);

				// 生成缩略图
				bindedObj.makeThumb(file, function(error, src) {
					if (error) {
						$img.replaceWith('<span>不能预览</span>');
						return;
					}
					$img.attr('src', src);
				}, me.picWidth, me.picHeight);
			});

			// 文件上传过程中创建进度条实时显示。
			bindedObj.on('uploadProgress', function(file, percentage) {
                $("#"+me.uploadBarId).css("width",percentage * 100 + "%");
			});

			// 文件上传成功，给item添加成功class, 用样式标记上传成功。
			bindedObj.on('uploadSuccess', function(file,response) {
				Feng.success("上传成功");
				$("#" + me.pictureId).val(response);
			});

			// 文件上传失败，显示上传出错。
			bindedObj.on('uploadError', function(file) {
				Feng.error("上传失败，图片请上传jpg格式");
			});

			// 其他错误
			bindedObj.on('error', function(type) {
				if ("Q_EXCEED_SIZE_LIMIT" == type) {
					Feng.error("文件大小超出了限制");
				} else if ("Q_TYPE_DENIED" == type) {
					Feng.error("文件类型不满足");
				} else if ("Q_EXCEED_NUM_LIMIT" == type) {
					Feng.error("上传数量超过限制");
				} else if ("F_DUPLICATE" == type) {
					Feng.error("图片选择重复");
				} else {
					Feng.error("上传过程中出错");
				}
			});

			// 完成上传完了，成功或者失败
			bindedObj.on('uploadComplete', function(file) {
			});
		},

        /**
         * 设置图片上传的进度条的id
         */
        setUploadBarId: function (id) {
            this.uploadBarId = id;
        }
	};

	window.$WebUpload = $WebUpload;

	/* 文件上传 */

    var $DocUpload = function(docId) {
        this.docId = docId;
        this.uploadBtnId = docId + "BtnId";
        this.uploadPreId = docId + "PreId";
        this.uploadUrl = Feng.ctxPath + '/mgr/docUpload';
        this.fileSizeLimit = 100 * 1024 * 1024;
        this.picWidth = 800;
        this.picHeight = 800;
        this.uploadBarId = null;
    };

    $DocUpload.prototype = {
        /**
         * 初始化webUploader
         */
        init : function() {
            var uploader = this.create();
            this.bindEvent(uploader);
            return uploader;
        },

        /**
         * 创建webuploader对象
         */
        create : function() {
            var webUploader = WebUploader.create({
                auto : true,
                pick : {
                    id : '#' + this.uploadBtnId,
                    multiple : false,// 只上传一个
                },
                accept : {
                    title : '文档文件',
                    extensions : 'gif,jpg,jpeg,bmp,png,doc,docx,ppt,pptx,xls,xlsx,txt,pdf',
                    mimeTypes : 'image/gif,image/jpg,image/jpeg,image/bmp,image/png,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-powerpoint,application/vnd.openxmlformats-officedocument.presentationml.presentation,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,text/plain,application/pdf,'
                },
                swf : Feng.ctxPath
                + '/static/js/plugins/webuploader/Uploader.swf',
                disableGlobalDnd : true,
                duplicate : true,
                server : this.uploadUrl,
                fileSingleSizeLimit : this.fileSizeLimit
            });

            return webUploader;
        },

        /**
         * 绑定事件
         */
        bindEvent : function(bindedObj) {
            var me =  this;
            bindedObj.on('fileQueued', function(file) {
                var $li = $('<div><img width="40px" height="40px"></div>');
                var $img = $li.find('img');

                $("#" + me.uploadPreId).html($li);

                // 生成缩略图
                bindedObj.makeThumb(file, function(error, src) {
                    if (error) {
                        $img.replaceWith('<a id="docA" target="_blank" href="'+src+'">查看文档</a>');
                        return;
                    }
                    $img.attr('src', src);
                }, me.picWidth, me.picHeight);
            });

            // 文件上传过程中创建进度条实时显示。
            bindedObj.on('uploadProgress', function(file, percentage) {
                $("#"+me.uploadBarId).css("width",percentage * 100 + "%");
            });

            // 文件上传成功，给item添加成功class, 用样式标记上传成功。
            bindedObj.on('uploadSuccess', function(file,response) {
                Feng.success("上传成功");
                $("#" + me.docId).val(response);
                $("#docA").prop('href',response);
            });

            // 文件上传失败，显示上传出错。
            bindedObj.on('uploadError', function(file) {
                Feng.error("上传失败，图片请上传jpg格式");
            });

            // 其他错误
            bindedObj.on('error', function(type) {
                if ("Q_EXCEED_SIZE_LIMIT" == type) {
                    Feng.error("文件大小超出了限制");
                } else if ("Q_TYPE_DENIED" == type) {
                    Feng.error("文件类型不满足");
                } else if ("Q_EXCEED_NUM_LIMIT" == type) {
                    Feng.error("上传数量超过限制");
                } else if ("F_DUPLICATE" == type) {
                    Feng.error("图片选择重复");
                } else {
                    Feng.error("上传过程中出错");
                }
            });

            // 完成上传完了，成功或者失败
            bindedObj.on('uploadComplete', function(file) {
            });
        },

        /**
         * 设置图片上传的进度条的id
         */
        setUploadBarId: function (id) {
            this.uploadBarId = id;
        }
    };

    window.$DocUpload = $DocUpload;

// 多附件上传

    var $MultiDocUpload = function(docId) {
        this.docId = docId;
        this.uploadBtnId = docId + "BtnId";
        this.uploadPreId = docId + "PreId";
        this.uploadUrl = Feng.ctxPath + '/mgr/docUpload';
        this.fileSizeLimit = 100 * 1024 * 1024;
        this.picWidth = 800;
        this.picHeight = 800;
        this.uploadBarId = null;
    };
    $MultiDocUpload.prototype = {
        /**
         * 初始化webUploader
         */
        init : function() {
            var uploader = this.create();
            this.bindEvent(uploader);
            return uploader;
        },

        /**
         * 创建webuploader对象
         */
        create : function() {
            var webUploader = WebUploader.create({
                auto : true,
                pick : {
                    id : '#' + this.uploadBtnId,
                    multiple : false,// 只上传一个
                },
                accept : {
                    title : '文档文件',
                    extensions : 'gif,jpg,jpeg,bmp,png,doc,docx,ppt,pptx,xls,xlsx,txt,pdf',
                    mimeTypes : 'image/gif,image/jpg,image/jpeg,image/bmp,image/png,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-powerpoint,application/vnd.openxmlformats-officedocument.presentationml.presentation,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,text/plain,application/pdf,'
                },
                swf : Feng.ctxPath
                + '/static/js/plugins/webuploader/Uploader.swf',
                disableGlobalDnd : true,
                duplicate : true,
                server : this.uploadUrl,
                fileSingleSizeLimit : this.fileSizeLimit
            });

            return webUploader;
        },

        /**
         * 绑定事件
         */
        bindEvent : function(bindedObj) {
            var me =  this;
            var img_test = null;
            var ismun = null;
            var isbig_test = 0;
            bindedObj.on('fileQueued', function(file) {
                //console.log(file);
                let isFile = file.type == "image/jpeg"|| file.type == "image/jpg";
                let isvalue = $("#docPreId").parent().prev()[0].innerHTML;
                if(isvalue == "病症照片" && isFile){
                        if ($("#docPreId").children().length < 3) {
                            var $li = $('<div><img width="100px" height="100px"></div>');
                            img_test = $li.find('img');
                            $("#" + me.uploadPreId).append($li);
                        } else {
                            ismun = 1;
                            return;
                        }
                }else if(file.type != "image/png"){
                    isbig_test = 1;
                    switch (me.docId) {
                        case 'doc':
                            var $att = $('<div class="att-pane"><a class="att-link" target="_blank" href="">'+file.name+'</a>&nbsp;&nbsp;<a href="javascript:void(0);" class="att-del">删除</a> </div>')
                            break;
                        case 'faceImgAttachment':
                            var $att = $('<div class="att-pane"><a class="att-link-faceImgAttachment" href="" target="_blank"><img class="att-link-faceImgAttachment" target="_blank" width="100px" height="100px" src=""/></a></div>')
                            break;
                        case 'tongueImgAttachment':
                            var $att = $('<div class="att-pane"><a class="att-link-tongueImgAttachment" href="" target="_blank"><img class="att-link-tongueImgAttachment" target="_blank" width="100px" height="100px" src=""/></a></div>')
                            break;
                        case 'partImgAttachment':
                            var $att = $('<div class="att-pane"><a class="att-link-partImgAttachment" href="" target="_blank"><img class="att-link-partImgAttachment" target="_blank" width="100px" height="100px" src=""/></a></div>')
                            break;
                    }
                    // if (me.docId =='doc'){
                    //     var $att = $('<div class="att-pane"><a class="att-link" target="_blank" href="">'+file.name+'</a>&nbsp;&nbsp;<a href="javascript:void(0);" class="att-del">删除</a> </div>')
                    // }
                    // if (me.docId == 'faceImgAttachment'){
                    //     var $att = $('<div class="att-pane"><a class="att-links" target="_blank" href="">'+file.name+'</a>&nbsp;&nbsp;<a href="javascript:void(0);" class="att-del">删除</a> </div>')
                    // }
                        // var $att = $('<div class="att-pane"><a class="att-link" target="_blank" href="">'+file.name+'</a>&nbsp;&nbsp;<a href="javascript:void(0);" class="att-del">删除</a> </div>')
                    $("#" + me.uploadPreId).append($att);
                };

            });

            // 文件上传过程中创建进度条实时显示。
            bindedObj.on('uploadProgress', function(file, percentage) {
                $("#"+me.uploadBarId).css("width",percentage * 100 + "%");
            });

            // 文件上传成功，给item添加成功class, 用样式标记上传成功。
            bindedObj.on('uploadSuccess', function(file,response) {
                if(ismun == 1){
                    Feng.error("上传超過3張");
                    return;
                }else if(file.type == "image/png"){
                    Feng.error("无法上传png格式图片，请上上传jpg格式");
                    return ;
                } else{
                    Feng.success("上传成功");
                    switch (me.docId) {
                        case 'doc':
                            $('.att-link:last').prop('href',response);
                            break;
                        case 'faceImgAttachment':
                            $('.att-link-faceImgAttachment:last').prop('src',response);
                            $('.att-link-faceImgAttachment').prop('href',response);
                            $('#faceImgAttachmentBtnId').hide()
                            break;
                        case 'tongueImgAttachment':
                            $('.att-link-tongueImgAttachment:last').prop('src',response);
                            $('.att-link-tongueImgAttachment').prop('href',response);
                            $('#tongueImgAttachmentBtnId').hide()
                            break;
                        case 'partImgAttachment':
                            $('.att-link-partImgAttachment:last').prop('src',response);
                            $('.att-link-partImgAttachment').prop('href',response);
                            $('#partImgAttachmentBtnId').hide()
                            break;
                    }
                    // if (me.docId =='doc'){
                    //     $('.att-link:last').prop('href',response);
                    // }
                    // if (me.docId == 'faceImgAttachment'){
                    //     $('.att-links:last').prop('href',response);
                    // }

                }
                // 生成缩略图
                bindedObj.makeThumb(file, function(error, src) {
                    if (error) {
                        $(img_test).replaceWith('<span>不能预览</span>');
                        return;
                    }
                    $(img_test).attr('src', response);
                }, me.picWidth, me.picHeight);

            });

            // 文件上传失败，显示上传出错。
            bindedObj.on('uploadError', function(file) {
                Feng.error("上传失败，图片请上传jpg格式");
            });

            // 其他错误
            bindedObj.on('error', function(type) {
                if ("Q_EXCEED_SIZE_LIMIT" == type) {
                    Feng.error("文件大小超出了限制");
                } else if ("Q_TYPE_DENIED" == type) {
                    Feng.error("文件类型不满足");
                } else if ("Q_EXCEED_NUM_LIMIT" == type) {
                    Feng.error("上传数量超过限制");
                } else if ("F_DUPLICATE" == type) {
                    Feng.error("图片选择重复");
                } else {
                    Feng.error("上传过程中出错");
                }
            });

            // 完成上传完了，成功或者失败
            bindedObj.on('uploadComplete', function(file) {
            });
        },

        /**
         * 设置图片上传的进度条的id
         */
        setUploadBarId: function (id) {
            this.uploadBarId = id;
        }
    };

    window.$MultiDocUpload = $MultiDocUpload;


}());