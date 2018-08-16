<!-- 定于顶宏 -->
<#macro baseStyle pageTitle bodyClazz styles=[] scripts=[]>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta charset="utf-8" />
	<title>${pageTitle} - 后台管理系统</title>

	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

	<!-- bootstrap & fontawesome -->
	<link rel="stylesheet" href="${ctx!}/assets/css/bootstrap.css" />
	<link rel="stylesheet" href="${ctx!}/assets/css/font-awesome.css" />

	<!-- page specific plugin styles -->
	<link rel="stylesheet" href="${ctx!}/assets/css/jquery-ui.custom.css" />
	<link rel="stylesheet" href="${ctx!}/assets/css/jquery.gritter.css" />
	<link rel="stylesheet" href="${ctx!}/assets/css/select2.css" />
	<link rel="stylesheet" href="${ctx!}/assets/css/datepicker.css" />
	<link rel="stylesheet" href="${ctx!}/assets/css/bootstrap-editable.css" />

	<!-- text fonts -->
	<link rel="stylesheet" href="${ctx!}/assets/css/ace-fonts.css" />

	<!-- ace styles -->
	<link rel="stylesheet" href="${ctx!}/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />

	<!--[if lte IE 9]>
		<link rel="stylesheet" href="${ctx!}/assets/css/ace-part2.css" class="ace-main-stylesheet" />
	<![endif]-->

	<!--[if lte IE 9]>
	  <link rel="stylesheet" href="${ctx!}/assets/css/ace-ie.css" />
	<![endif]-->
	<#list styles as style>
		<link rel="stylesheet" href="${style}" />
	</#list>

	<!-- inline styles related to this page -->

	<!-- ace settings handler -->
	<script src="${ctx!}/assets/js/ace-extra.js"></script>

	<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

	<!--[if lte IE 8]>
	<script src="${ctx!}/assets/js/html5shiv.js"></script>
	<script src="${ctx!}/assets/js/respond.js"></script>
	<![endif]-->
	
	<!--[if !IE]> -->
	<script type="text/javascript">
	window.jQuery || document.write("<script src='${ctx!}/assets/js/jquery-2.1.4.min.js'>"+"<"+"/script>");
	</script>
	<!-- <![endif]-->
	<!--[if IE]>
	<script type="text/javascript">
	window.jQuery || document.write("<script src='${ctx!}/assets/js/jquery1x.js'>"+"<"+"/script>");
	</script>
	<![endif]-->
	
	<#list scripts as script>
		<script src="${script}"></script>
	</#list>
	<style>
	form .popover {
		white-space: nowrap;
		color: #ff0000;
		font-family: "Microsoft YaHei", 微软雅黑, "Microsoft JhengHei", 华文细黑, STHeiti, MingLiu;
		box-shadow: none;
		font-size: 12px;
	}
	.ywhh{word-break:break-all; overflow:auto;}
</style>
</head>
	<body class="${bodyClazz}">
		<#nested />
	</body>
</html>
</#macro>

<#macro webUploader>
	<!--引入CSS-->
	<link rel="stylesheet" type="text/css" href="${ctx!}/assets/js/webuploader-0.1.5/webuploader.css">
	<link rel="stylesheet" type="text/css" href="${ctx!}/assets/js/webuploader-0.1.5/image-upload/style.css" />

	<!--引入JS-->
	<script type="text/javascript"
			src="${ctx!}/assets/js/webuploader-0.1.5/image-upload/jquery.js"></script>
	<script type="text/javascript"
			src="${ctx!}/assets/js/webuploader-0.1.5/webuploader.min.js"></script>
	<script type="text/javascript"
			src="${ctx!}/assets/js/webuploader-0.1.5/image-upload/upload.js"></script>
</#macro>

<#macro webUploaderToOSS>
<!--引入CSS-->
<link rel="stylesheet" type="text/css" href="${ctx!}/assets/js/webuploader-0.1.5/webuploader.css">
<link rel="stylesheet" type="text/css" href="${ctx!}/assets/js/webuploader-0.1.5/image-upload/style.css" />

<!--引入JS-->
<script type="text/javascript"
        src="${ctx!}/assets/js/webuploader-0.1.5/image-upload/jquery.js"></script>
<script type="text/javascript"
        src="${ctx!}/assets/js/webuploader-0.1.5/webuploader.min.js"></script>
<script type="text/javascript"
        src="${ctx!}/assets/js/webuploader-0.1.5/image-upload/uploadToOss.js"></script>
</#macro>

<#macro alertMsg>
<!-- 弹窗消息宏 -->
	<#if msg_content??>
    <div class="alert ${msg_type!}" id="msgtypeid">
        <button type="button" class="close" data-dismiss="alert">
            <i class="ace-icon fa fa-times" id="closemt"></i>
        </button>
        <strong>消息提醒:</strong>
	${msg_content}
    </div>
	</#if>
<script>
    var mtype = '${msg_type!}';
    if(mtype!=null && mtype!=''){
        setTimeout(func,"3000");//三秒后执行
    }
    function func(){
        $("#closemt").click();
    }
</script>
<script>
    function removeConfirm(url) {
        bootbox.confirm({
            title: '消息提醒:',
            message: '您确定要删除该数据项?',
            buttons: {
                confirm: {
                    label: '确定',
                    className: 'btn-primary btn-sm',
                },
                cancel: {
                    label: '取消',
                    className: 'btn-sm',
                }
            },
            callback: function(result) {
                if(result) {
                    location.href = url;
                }
            },
            title: '确认信息'
        });
    }
    function infoConfirm(url, message) {
        bootbox.confirm({
            title: '消息提醒:',
            message: message,
            buttons: {
                confirm: {
                    label: '确定',
                    className: 'btn-primary btn-sm',
                },
                cancel: {
                    label: '取消',
                    className: 'btn-sm',
                }
            },
            callback: function(result) {
                if(result) {
                    location.href = url;
                }
            },
            title: '确认信息'
        });
    }
    function alertMsg(message) {
        bootbox.alert({
            buttons: {
                ok: {
                    label: '确定',
                    className: 'btn-primary btn-sm'
                }
            },
            message: message,
            title: '提示信息'
        });
    }
</script>
</#macro>

<!-- 内容体-宏， mainLayout是宏的使用名称 -->
<#macro mainLayout>
<#assign mScripts = [
	<!-- '${static!}/js/ace/elements.scroller.js',
	'${static!}/js/ace/ace.js',
	'${static!}/js/ace/ace.sidebar.js',
	'${static!}/js/ace/ace.sidebar-scroll-1.js',
	'${static!}/js/bootbox.js',
	'${static!}/js/ace/elements.fileinput.js' -->
] />
<#assign mStyles = [
	
] />
<@baseStyle pageTitle="${page_name!}" bodyClazz="no-skin" styles=mStyles scripts=mScripts>

<div class="main-container" id="main-container">
	<!-- 左侧菜单: 结束 -->
	<div class="main-content">
		<div class="main-content-inner">
			<!-- #section:basics/content.breadcrumbs -->
			<div class="breadcrumbs" id="breadcrumbs">
				<script type="text/javascript">
					try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
				</script>
				<ul class="breadcrumb">
					<li>
						<i class="ace-icon fa fa-home home-icon"></i>
						${page_path_1!}
					</li>
					<#if page_path_2??>
						<li>${page_path_2!}</li>
						<li>${page_name!}</li>
					<#else />
						<li>${page_name!}</li>
					</#if>
				</ul><!-- /.breadcrumb -->
				<!-- #section:basics/content.searchbox -->
				<!--
				<div class="nav-search" id="nav-search">
					<form class="form-search">
						<span class="input-icon">
							<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
							<i class="ace-icon fa fa-search nav-search-icon"></i>
						</span>
					</form>
				</div>
				-->
				<!-- /.nav-search -->
				<!-- /section:basics/content.searchbox -->
			</div>
			<!-- 面包屑: 结束 -->
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
						<!-- 功能区: 开始 -->
						<#nested />
						<!-- 功能区: 结束 -->
					</div>
				</div>
			</div>
			
		</div>
	</div>
		<!-- basic scripts -->

		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='${ctx}/assets/js/jquery.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
		<script type="text/javascript">
		 window.jQuery || document.write("<script src='${ctx}/assets/js/jquery1x.js'>"+"<"+"/script>");
		</script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='${ctx}/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
		</script>
		<script src="${ctx}/assets/js/bootstrap.js"></script>

		<!-- page specific plugin scripts -->

		<!--[if lte IE 8]>
		  <script src="${ctx}/assets/js/excanvas.js"></script>
		<![endif]-->
		<script src="${ctx}/assets/js/jquery-ui.custom.js"></script>
		<script src="${ctx}/assets/js/jquery.ui.touch-punch.js"></script>
		<script src="${ctx}/assets/js/jquery.gritter.js"></script>
		<script src="${ctx}/assets/js/bootbox.js"></script>
		<script src="${ctx}/assets/js/jquery.easypiechart.js"></script>
		<script src="${ctx}/assets/js/date-time/bootstrap-datepicker.js"></script>
		<script src="${ctx}/assets/js/jquery.hotkeys.js"></script>
		<script src="${ctx}/assets/js/bootstrap-wysiwyg.js"></script>
		<script src="${ctx}/assets/js/select2.js"></script>
		<script src="${ctx}/assets/js/fuelux/fuelux.spinner.js"></script>
		<script src="${ctx}/assets/js/x-editable/bootstrap-editable.js"></script>
		<script src="${ctx}/assets/js/x-editable/ace-editable.js"></script>
		<script src="${ctx}/assets/js/jquery.maskedinput.js"></script>

		<!-- ace scripts -->
		<script src="${ctx}/assets/js/ace/elements.scroller.js"></script>
		<script src="${ctx}/assets/js/ace/elements.colorpicker.js"></script>
		<script src="${ctx}/assets/js/ace/elements.fileinput.js"></script>
		<script src="${ctx}/assets/js/ace/elements.typeahead.js"></script>
		<script src="${ctx}/assets/js/ace/elements.wysiwyg.js"></script>
		<script src="${ctx}/assets/js/ace/elements.spinner.js"></script>
		<script src="${ctx}/assets/js/ace/elements.treeview.js"></script>
		<script src="${ctx}/assets/js/ace/elements.wizard.js"></script>
		<script src="${ctx}/assets/js/ace/elements.aside.js"></script>
		<script src="${ctx}/assets/js/ace/ace.js"></script>
		<script src="${ctx}/assets/js/ace/ace.ajax-content.js"></script>
		<script src="${ctx}/assets/js/ace/ace.touch-drag.js"></script>
		<script src="${ctx}/assets/js/ace/ace.sidebar.js"></script>
		<script src="${ctx}/assets/js/ace/ace.sidebar-scroll-1.js"></script>
		<script src="${ctx}/assets/js/ace/ace.submenu-hover.js"></script>
		<script src="${ctx}/assets/js/ace/ace.widget-box.js"></script>
		<script src="${ctx}/assets/js/ace/ace.settings.js"></script>
		<script src="${ctx}/assets/js/ace/ace.settings-rtl.js"></script>
		<script src="${ctx}/assets/js/ace/ace.settings-skin.js"></script>
		<script src="${ctx}/assets/js/ace/ace.widget-on-reload.js"></script>
		<script src="${ctx}/assets/js/ace/ace.searchbox-autocomplete.js"></script>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			//no-skin	skin-1	skin-2	skin-3
			
			jQuery(function($) {
				
				//获取边栏锁定
				var navbar_fixed = '${navbar_fixed!}';
				if(navbar_fixed!=null && navbar_fixed!='' && navbar_fixed=='yes'){
					ace.settings.navbar_fixed(null, true);
				}else{
					ace.settings.navbar_fixed(null, false);
				}
				var sidebar_fixed = '${sidebar_fixed!}';
				if(sidebar_fixed!=null && sidebar_fixed!='' && sidebar_fixed=='yes'){
					ace.settings.sidebar_fixed(null, true);
				}else{
					ace.settings.sidebar_fixed(null, false);
				}
				var breadcrumbs_fixed = '${breadcrumbs_fixed!}';
				if(breadcrumbs_fixed!=null && breadcrumbs_fixed!='' && breadcrumbs_fixed=='yes'){
					ace.settings.breadcrumbs_fixed(null, true);
				}else{
					ace.settings.breadcrumbs_fixed(null, false);
				}
			
				//editables on first profile page
				$.fn.editable.defaults.mode = 'inline';
				$.fn.editableform.loading = "<div class='editableform-loading'><i class='ace-icon fa fa-spinner fa-spin fa-2x light-blue'></i></div>";
			    $.fn.editableform.buttons = '<button type="submit" class="btn btn-info editable-submit"><i class="ace-icon fa fa-check"></i></button>'+
			                                '<button type="button" class="btn editable-cancel"><i class="ace-icon fa fa-times"></i></button>';    
				
				//editables 
				
				//text editable
			    $('#username')
				.editable({
					type: 'text',
					name: 'username'
			    });
			
			
				
				//select2 editable
				var countries = [];
			    $.each({ "CA": "Canada", "IN": "India", "NL": "Netherlands", "TR": "Turkey", "US": "United States"}, function(k, v) {
			        countries.push({id: k, text: v});
			    });
			
				var cities = [];
				cities["CA"] = [];
				$.each(["Toronto", "Ottawa", "Calgary", "Vancouver"] , function(k, v){
					cities["CA"].push({id: v, text: v});
				});
				cities["IN"] = [];
				$.each(["Delhi", "Mumbai", "Bangalore"] , function(k, v){
					cities["IN"].push({id: v, text: v});
				});
				cities["NL"] = [];
				$.each(["Amsterdam", "Rotterdam", "The Hague"] , function(k, v){
					cities["NL"].push({id: v, text: v});
				});
				cities["TR"] = [];
				$.each(["Ankara", "Istanbul", "Izmir"] , function(k, v){
					cities["TR"].push({id: v, text: v});
				});
				cities["US"] = [];
				$.each(["New York", "Miami", "Los Angeles", "Chicago", "Wysconsin"] , function(k, v){
					cities["US"].push({id: v, text: v});
				});
				
				var currentValue = "NL";
			    $('#country').editable({
					type: 'select2',
					value : 'NL',
					//onblur:'ignore',
			        source: countries,
					select2: {
						'width': 140
					},		
					success: function(response, newValue) {
						if(currentValue == newValue) return;
						currentValue = newValue;
						
						var new_source = (!newValue || newValue == "") ? [] : cities[newValue];
						
						//the destroy method is causing errors in x-editable v1.4.6+
						//it worked fine in v1.4.5
						/**			
						$('#city').editable('destroy').editable({
							type: 'select2',
							source: new_source
						}).editable('setValue', null);
						*/
						
						//so we remove it altogether and create a new element
						var city = $('#city').removeAttr('id').get(0);
						$(city).clone().attr('id', 'city').text('Select City').editable({
							type: 'select2',
							value : null,
							//onblur:'ignore',
							source: new_source,
							select2: {
								'width': 140
							}
						}).insertAfter(city);//insert it after previous instance
						$(city).remove();//remove previous instance
						
					}
			    });
			
				$('#city').editable({
					type: 'select2',
					value : 'Amsterdam',
					//onblur:'ignore',
			        source: cities[currentValue],
					select2: {
						'width': 140
					}
			    });
			
			
				
				//custom date editable
				$('#signup').editable({
					type: 'adate',
					date: {
						//datepicker plugin options
						    format: 'yyyy/mm/dd',
						viewformat: 'yyyy/mm/dd',
						 weekStart: 1
						 
						//,nativeUI: true//if true and browser support input[type=date], native browser control will be used
						//,format: 'yyyy-mm-dd',
						//viewformat: 'yyyy-mm-dd'
					}
				})
			
			    $('#age').editable({
			        type: 'spinner',
					name : 'age',
					spinner : {
						min : 16,
						max : 99,
						step: 1,
						on_sides: true
						//,nativeUI: true//if true and browser support input[type=number], native browser control will be used
					}
				});
				
			
			    $('#login').editable({
			        type: 'slider',
					name : 'login',
					
					slider : {
						 min : 1,
						  max: 50,
						width: 100
						//,nativeUI: true//if true and browser support input[type=range], native browser control will be used
					},
					success: function(response, newValue) {
						if(parseInt(newValue) == 1)
							$(this).html(newValue + " hour ago");
						else $(this).html(newValue + " hours ago");
					}
				});
			
				$('#about').editable({
					mode: 'inline',
			        type: 'wysiwyg',
					name : 'about',
			
					wysiwyg : {
						//css : {'max-width':'300px'}
					},
					success: function(response, newValue) {
					}
				});
				
				
				
				// *** editable avatar *** //
				try {//ie8 throws some harmless exceptions, so let's catch'em
			
					//first let's add a fake appendChild method for Image element for browsers that have a problem with this
					//because editable plugin calls appendChild, and it causes errors on IE at unpredicted points
					try {
						document.createElement('IMG').appendChild(document.createElement('B'));
					} catch(e) {
						Image.prototype.appendChild = function(el){}
					}
			
					var last_gritter
					$('#avatar').editable({
						type: 'image',
						name: 'avatar',
						value: null,
						image: {
							//specify ace file input plugin's options here
							btn_choose: 'Change Avatar',
							droppable: true,
							maxSize: 110000,//~100Kb
			
							//and a few extra ones here
							name: 'avatar',//put the field name here as well, will be used inside the custom plugin
							on_error : function(error_type) {//on_error function will be called when the selected file has a problem
								if(last_gritter) $.gritter.remove(last_gritter);
								if(error_type == 1) {//file format error
									last_gritter = $.gritter.add({
										title: 'File is not an image!',
										text: 'Please choose a jpg|gif|png image!',
										class_name: 'gritter-error gritter-center'
									});
								} else if(error_type == 2) {//file size rror
									last_gritter = $.gritter.add({
										title: 'File too big!',
										text: 'Image size should not exceed 100Kb!',
										class_name: 'gritter-error gritter-center'
									});
								}
								else {//other error
								}
							},
							on_success : function() {
								$.gritter.removeAll();
							}
						},
					    url: function(params) {
							// ***UPDATE AVATAR HERE*** //
							//for a working upload example you can replace the contents of this function with 
							//examples/profile-avatar-update.js
			
							var deferred = new $.Deferred
			
							var value = $('#avatar').next().find('input[type=hidden]:eq(0)').val();
							if(!value || value.length == 0) {
								deferred.resolve();
								return deferred.promise();
							}
			
			
							//dummy upload
							setTimeout(function(){
								if("FileReader" in window) {
									//for browsers that have a thumbnail of selected image
									var thumb = $('#avatar').next().find('img').data('thumb');
									if(thumb) $('#avatar').get(0).src = thumb;
								}
								
								deferred.resolve({'status':'OK'});
			
								if(last_gritter) $.gritter.remove(last_gritter);
								last_gritter = $.gritter.add({
									title: 'Avatar Updated!',
									text: 'Uploading to server can be easily implemented. A working example is included with the template.',
									class_name: 'gritter-info gritter-center'
								});
								
							 } , parseInt(Math.random() * 800 + 800))
			
							return deferred.promise();
							
							// ***END OF UPDATE AVATAR HERE*** //
						},
						
						success: function(response, newValue) {
						}
					})
				}catch(e) {}
				
				/**
				//let's display edit mode by default?
				var blank_image = true;//somehow you determine if image is initially blank or not, or you just want to display file input at first
				if(blank_image) {
					$('#avatar').editable('show').on('hidden', function(e, reason) {
						if(reason == 'onblur') {
							$('#avatar').editable('show');
							return;
						}
						$('#avatar').off('hidden');
					})
				}
				*/
			
				//another option is using modals
				$('#avatar2').on('click', function(){
					var modal = 
					'<div class="modal fade">\
					  <div class="modal-dialog">\
					   <div class="modal-content">\
						<div class="modal-header">\
							<button type="button" class="close" data-dismiss="modal">&times;</button>\
							<h4 class="blue">Change Avatar</h4>\
						</div>\
						\
						<form class="no-margin">\
						 <div class="modal-body">\
							<div class="space-4"></div>\
							<div style="width:75%;margin-left:12%;"><input type="file" name="file-input" /></div>\
						 </div>\
						\
						 <div class="modal-footer center">\
							<button type="submit" class="btn btn-sm btn-success"><i class="ace-icon fa fa-check"></i> Submit</button>\
							<button type="button" class="btn btn-sm" data-dismiss="modal"><i class="ace-icon fa fa-times"></i> Cancel</button>\
						 </div>\
						</form>\
					  </div>\
					 </div>\
					</div>';
					
					
					var modal = $(modal);
					modal.modal("show").on("hidden", function(){
						modal.remove();
					});
			
					var working = false;
			
					var form = modal.find('form:eq(0)');
					var file = form.find('input[type=file]').eq(0);
					file.ace_file_input({
						style:'well',
						btn_choose:'Click to choose new avatar',
						btn_change:null,
						no_icon:'ace-icon fa fa-picture-o',
						thumbnail:'small',
						before_remove: function() {
							//don't remove/reset files while being uploaded
							return !working;
						},
						allowExt: ['jpg', 'jpeg', 'png', 'gif'],
						allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
					});
			
					form.on('submit', function(){
						if(!file.data('ace_input_files')) return false;
						
						file.ace_file_input('disable');
						form.find('button').attr('disabled', 'disabled');
						form.find('.modal-body').append("<div class='center'><i class='ace-icon fa fa-spinner fa-spin bigger-150 orange'></i></div>");
						
						var deferred = new $.Deferred;
						working = true;
						deferred.done(function() {
							form.find('button').removeAttr('disabled');
							form.find('input[type=file]').ace_file_input('enable');
							form.find('.modal-body > :last-child').remove();
							
							modal.modal("hide");
			
							var thumb = file.next().find('img').data('thumb');
							if(thumb) $('#avatar2').get(0).src = thumb;
			
							working = false;
						});
						
						
						setTimeout(function(){
							deferred.resolve();
						} , parseInt(Math.random() * 800 + 800));
			
						return false;
					});
							
				});
			
				
			
				//////////////////////////////
				$('#profile-feed-1').ace_scroll({
					height: '250px',
					mouseWheelLock: true,
					alwaysVisible : true
				});
			
				$('a[ data-original-title]').tooltip();
			
				$('.easy-pie-chart.percentage').each(function(){
				var barColor = $(this).data('color') || '#555';
				var trackColor = '#E2E2E2';
				var size = parseInt($(this).data('size')) || 72;
				$(this).easyPieChart({
					barColor: barColor,
					trackColor: trackColor,
					scaleColor: false,
					lineCap: 'butt',
					lineWidth: parseInt(size/10),
					animate:false,
					size: size
				}).css('color', barColor);
				});
			  
				///////////////////////////////////////////
			
				//right & left position
				//show the user info on right or left depending on its position
				$('#user-profile-2 .memberdiv').on('mouseenter touchstart', function(){
					var $this = $(this);
					var $parent = $this.closest('.tab-pane');
			
					var off1 = $parent.offset();
					var w1 = $parent.width();
			
					var off2 = $this.offset();
					var w2 = $this.width();
			
					var place = 'left';
					if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) place = 'right';
					
					$this.find('.popover').removeClass('right left').addClass(place);
				}).on('click', function(e) {
					e.preventDefault();
				});
			
			
				///////////////////////////////////////////
				$('#user-profile-3')
				.find('input[type=file]').ace_file_input({
					style:'well',
					btn_choose:'Change avatar',
					btn_change:null,
					no_icon:'ace-icon fa fa-picture-o',
					thumbnail:'large',
					droppable:true,
					
					allowExt: ['jpg', 'jpeg', 'png', 'gif'],
					allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
				})
				.end().find('button[type=reset]').on(ace.click_event, function(){
					$('#user-profile-3 input[type=file]').ace_file_input('reset_input');
				})
				.end().find('.date-picker').datepicker().next().on(ace.click_event, function(){
					$(this).prev().focus();
				})
				$('.input-mask-phone').mask('(999) 999-9999');
			
				$('#user-profile-3').find('input[type=file]').ace_file_input('show_file_list', [{type: 'image', name: $('#avatar').attr('src')}]);
			
			
				////////////////////
				//change profile
				$('[data-toggle="buttons"] .btn').on('click', function(e){
					var target = $(this).find('input[type=radio]');
					var which = parseInt(target.val());
					$('.user-profile').parent().addClass('hide');
					$('#user-profile-'+which).parent().removeClass('hide');
				});
				
				
				
				/////////////////////////////////////
				$(document).one('ajaxloadstart.page', function(e) {
					//in ajax mode, remove remaining elements before leaving page
					try {
						$('.editable').editable('destroy');
					} catch(e) {}
					$('[class*=select2]').remove();
				});
			});
		</script>

		<!-- the following scripts are used in demo only for onpage help and you don't need them -->
		<link rel="stylesheet" href="${ctx}/assets/css/ace.onpage-help.css" />
		<link rel="stylesheet" href="${ctx}/docs/assets/js/themes/sunburst.css" />

		<script type="text/javascript"> ace.vars['base'] = '..'; </script>
		<script src="${ctx}/assets/js/ace/elements.onpage-help.js"></script>
		<script src="${ctx}/assets/js/ace/ace.onpage-help.js"></script>
		<script src="${ctx}/docs/assets/js/rainbow.js"></script>
		<script src="${ctx}/docs/assets/js/language/generic.js"></script>
		<script src="${ctx}/docs/assets/js/language/html.js"></script>
		<script src="${ctx}/docs/assets/js/language/css.js"></script>

</div>
</@baseStyle>
</#macro>
