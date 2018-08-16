<#macro baseStyle pageTitle bodyClazz styles=[] scripts=[]>
<!DOCTYPE html>
<!--
                       ::
                      :;J7, :,                        ::;7:
                      ,ivYi, ,                       ;LLLFS:
                      :iv7Yi                       :7ri;j5PL
                     ,:ivYLvr                    ,ivrrirrY2X,
                     :;r@Wwz.7r:                :ivu@kexianli.
                    :iL7::,:::iiirii:ii;::::,,irvF7rvvLujL7ur
                   ri::,:,::i:iiiiiii:i:irrv177JX7rYXqZEkvv17
                ;i:, , ::::iirrririi:i:::iiir2XXvii;L8OGJr71i
              :,, ,,:   ,::ir@mingyi.irii:i:::j1jri7ZBOS7ivv,
                 ,::,    ::rv77iiiriii:iii:i::,rvLq@huhao.Li
             ,,      ,, ,:ir7ir::,:::i;ir:::i:i::rSGGYri712:
           :::  ,v7r:: ::rrv77:, ,, ,:i7rrii:::::, ir7ri7Lri
          ,     2OBBOi,iiir;r::        ,irriiii::,, ,iv7Luur:
        ,,     i78MBBi,:,:::,:,  :7FSL: ,iriii:::i::,,:rLqXv::
        :      iuMMP: :,:::,:ii;2GY7OBB0viiii:i:iii:i:::iJqL;::
       ,     ::::i   ,,,,, ::LuBBu BBBBBErii:i:i:i:i:i:i:r77ii
      ,       :       , ,,:::rruBZ1MBBqi, :,,,:::,::::::iiriri:
     ,               ,,,,::::i:  @arqiao.       ,:,, ,:::ii;i7:
    :,       rjujLYLi   ,,:::::,:::::::::,,   ,:i,:,,,,,::i:iii
    ::      BBBBBBBBB0,    ,,::: , ,:::::: ,      ,,,, ,,:::::::
    i,  ,  ,8BMMBBBBBBi     ,,:,,     ,,, , ,   , , , :,::ii::i::
    :      iZMOMOMBBM2::::::::::,,,,     ,,,,,,:,,,::::i:irr:i:::,
    i   ,,:;u0MBMOG1L:::i::::::  ,,,::,   ,,, ::::::i:i:iirii:i:i:
    :    ,iuUuuXUkFu7i:iii:i:::, :,:,: ::::::::i:i:::::iirr7iiri::
    :     :rk@Yizero.i:::::, ,:ii:::::::i:::::i::,::::iirrriiiri::,
     :      5BMBBBBBBSr:,::rv2kuii:::iii::,:i:,, , ,,:,:i@petermu.,
          , :r50EZ8MBBBBGOBBBZP7::::i::,:::::,: :,:,::i;rrririiii::
              :jujYY7LS0ujJL7r::,::i::,::::::::::::::iirirrrrrrr:ii:
           ,:  :@kevensun.:,:,,,::::i:i:::::,,::::::iir;ii;7v77;ii;i,
           ,,,     ,,:,::::::i:iiiii:i::::,, ::::iiiir@xingjief.r;7:i,
        , , ,,,:,,::::::::iiiiiiiiii:,:,:::::::::iiir;ri7vL77rrirri::
         :,, , ::::::::i:::i:::i:i::,,,,,:,::i:i:::iir;@Secbone.ii:::
         
         				  这是一只具有魔性的doge.
		 
-->
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <title><#if module_entity??>${module_entity.name!}</#if><#if controller_entity??> - ${controller_entity.name!}</#if> - 后台管理系统 - ${company_name!}</title>

    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="${ctx!}/assets/css/bootstrap.css" />
    <link rel="stylesheet" href="${ctx!}/assets/css/font-awesome.css" />

    <link rel="stylesheet" href="${ctx!}/assets/css/jquery-ui.custom.css" />
    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="${ctx!}/assets/css/chosen.css" />
    <link rel="stylesheet" href="${ctx!}/assets/css/bootstrap-duallistbox.css" />
    <link rel="stylesheet" href="${ctx!}/assets/css/bootstrap-multiselect.css" />
    <link rel="stylesheet" href="${ctx!}/assets/css/jquery.gritter.css" />
    <link rel="stylesheet" href="${ctx!}/assets/css/select2.css" />
    <link rel="stylesheet" href="${ctx!}/assets/css/datepicker.css" />
    <link rel="stylesheet" href="${ctx!}/assets/css/bootstrap-editable.css" />
    <link rel="stylesheet" href="${ctx!}/assets/css/colorpicker.css" />

    <link rel="stylesheet" href="${ctx!}/assets/css/ui.jqgrid.css" />

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
        /*
        form .popover {
            white-space: nowrap;
            color: #ff0000;
            font-family: "Microsoft YaHei", 微软雅黑, "Microsoft JhengHei", 华文细黑, STHeiti, MingLiu;
            box-shadow: none;
            font-size: 12px;
        }
        */
        .ywhh{word-break:break-all; overflow:auto;}
        .rr{color:#FF0000;vertical-align: middle;padding-right: 5px;font-style: normal;}
        .cccr{color:#CCC;vertical-align: middle;padding-right: 5px;font-style: normal;}
        .cr{color:#FF0000;}
        .fontsize12{font-size: 12px !important;}
        .tooltip{font-size:16px; color:#FF0000}
        .rdcss{width: 230px;}
        .padding5px{padding:8px 25px;}
    </style>
    <script>
        $(document).ready(function() {
            $("th[id=sorting]").click(function(){
                var column = $(this).attr("attr");
                var className = $(this).attr("class");
                var order = "asc";
                if(className=="sorting_asc"){
                    order = "desc";
                    className = "sorting_desc";
                }else{
                    className = "sorting_asc";
                }
                $(this).attr("class", className);
                $("#orderBy").val(column);
                $("#order").val(order);
                $("#search-form").submit();
            });
        });
    </script>
</head>
<body class="${bodyClazz}">
	<#nested />
</body>
</html>
</#macro>

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
<@baseStyle pageTitle="" bodyClazz="no-skin" styles=mStyles scripts=mScripts>

<!-- 顶部栏开始 -->
<div id="navbar" class="navbar navbar-default" <#if navBgColor?? && navBgColor!=''>style="background:${navBgColor}"</#if>>
    <!-- gritter 弹窗点击 -->
    <button id="${gritter_type!}" style="display:none"></button>
    <script type="text/javascript">
        try{ace.settings.check('navbar' , 'fixed')}catch(e){}
    </script>
    <div class="navbar-container" id="navbar-container">
        <!-- #section:basics/sidebar.mobile.toggle -->
        <!-- /section:basics/sidebar.mobile.toggle -->
        <div class="navbar-header pull-left">
            <!-- #section:basics/navbar.layout.brand -->
            <a href="${ctx!}/" class="navbar-brand">
                <small>
				<#if system_bottom_logo??>
                    <img src="${system_left_top_logo!}" height="25px">
				<#else />
                    <i class="fa fa-pagelines"></i>
				</#if>
                    &nbsp;&nbsp;${project_name!}</small>
                <!--<small><img src="${ctx!}${syslogo!}"><small class="sub-brand">后台系统</small></small>-->
            </a>
            <!-- /section:basics/navbar.layout.brand -->
            <!-- #section:basics/navbar.toggle -->
            <!-- /section:basics/navbar.toggle -->
        </div>
        <script type="text/javascript">
            //ajax 异步请求数据
            function memory(){
                $.ajax({
                    type : "POST",
                    url : "${ctx!}/monitor/memory${suffix!}",
                    datatype : "json",
                    success : function(data) {
                        if(data==null || data==''){
                            data = 0;
                        }
                        $("#memory_show").html(data+"%");
                        $("#memory_progree").css("width",data+"%");
                    }
                });
            }
        </script>
        <!-- #section:basics/navbar.dropdown -->
        <div class="navbar-buttons navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">
                <li class="transparent">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#" aria-expanded="false">
                        <i class="ace-icon fa fa-bell"></i>
                    </a>

                    <div class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
                        <div class="tabbable">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a data-toggle="tab" href="#navbar-tasks">
                                        <i class="ace-icon fa fa-tachometer" style="color:${btnStyle!}"></i>&nbsp;系统检测<!--<span class="badge badge-danger">4</span>-->
                                    </a>
                                </li>
                                <li>
                                    <a data-toggle="tab" href="#navbar-messages">
                                        <i class="ace-icon fa fa-envelope" style="color:${btnStyle!}"></i>&nbsp;消息&nbsp;<span class="badge badge-danger">5</span>
                                    </a>
                                </li>
                            </ul><!-- .nav-tabs -->
                            <div class="tab-content">
                                <div id="navbar-tasks" class="tab-pane in active">
                                    <ul class="dropdown-menu-right dropdown-navbar dropdown-menu">
                                        <li class="dropdown-content ace-scroll" style="position: relative;"><div class="scroll-track" style="display: none;"><div class="scroll-bar"></div></div><div class="scroll-content">
                                            <ul class="dropdown-menu dropdown-navbar">
                                                <li>
                                                    <a href="#">
                                                        <div class="clearfix">
                                                            <span class="pull-left">JVM 检测</span>&nbsp;&nbsp;
														<#if dev_model?? && dev_model=='false'><span data-rel="tooltip" data-placement="right" data-original-title="实时刷新" class="tooltip-info"><i class="fa fa-refresh fa-spin fa-1x fa-fw margin-bottom"></i></span></#if>
                                                            <span class="pull-right" id="memory_show">0%</span>
                                                        </div>
                                                        <div class="progress progress-mini">
                                                            <div style="width:0%" class="progress-bar" id="memory_progree"></div>
                                                        </div>
                                                    </a>
                                                </li>
                                                <!--
                                                <li>
                                                    <a href="#">
                                                        <div class="clearfix">
                                                            <span class="pull-left">Hardware Upgrade</span>
                                                            <span class="pull-right">35%</span>
                                                        </div>

                                                        <div class="progress progress-mini">
                                                            <div style="width:35%" class="progress-bar progress-bar-danger"></div>
                                                        </div>
                                                    </a>
                                                </li>
                                                <li>
                                                    <a href="#">
                                                        <div class="clearfix">
                                                            <span class="pull-left">Unit Testing</span>
                                                            <span class="pull-right">15%</span>
                                                        </div>

                                                        <div class="progress progress-mini">
                                                            <div style="width:15%" class="progress-bar progress-bar-warning"></div>
                                                        </div>
                                                    </a>
                                                </li>
                                                <li>
                                                    <a href="#">
                                                        <div class="clearfix">
                                                            <span class="pull-left">Bug Fixes</span>
                                                            <span class="pull-right">90%</span>
                                                        </div>

                                                        <div class="progress progress-mini progress-striped active">
                                                            <div style="width:90%" class="progress-bar progress-bar-success"></div>
                                                        </div>
                                                    </a>
                                                </li>
                                                -->
                                            </ul>
                                        </div></li>
                                        <!--
                                        <li class="dropdown-footer">
                                            <a href="#">
                                                See tasks with details
                                                <i class="ace-icon fa fa-arrow-right"></i>
                                            </a>
                                        </li>
                                        -->
                                    </ul>
                                </div><!-- /.tab-pane -->

                                <div id="navbar-messages" class="tab-pane">
                                    <ul class="dropdown-menu-right dropdown-navbar dropdown-menu">
                                        <li class="dropdown-content ace-scroll" style="position: relative;"><div class="scroll-track" style="display: none;"><div class="scroll-bar"></div></div><div class="scroll-content" style="max-height: 345px;">
                                            <ul class="dropdown-menu dropdown-navbar">
                                                <li>
                                                    <a href="#">
                                                        <img src="${ctx!}/assets/avatars/default_1.png" class="msg-photo" alt="Alex's Avatar">
														<span class="msg-body">
															<span class="msg-title">
																<span class="blue">Alex:</span>
																	预留测试版本
															</span>

															<span class="msg-time">
																<i class="ace-icon fa fa-clock-o"></i>
																<span>1分钟前</span>
															</span>
														</span>
                                                    </a>
                                                </li>

                                                <li>
                                                    <a href="#">
                                                        <img src="${ctx!}/assets/avatars/default_2.png" class="msg-photo" alt="Susan's Avatar">
														<span class="msg-body">
															<span class="msg-title">
																<span class="blue">Susan:</span>
																Vestibulum id ligula porta felis euismod ...
															</span>

															<span class="msg-time">
																<i class="ace-icon fa fa-clock-o"></i>
																<span>20分钟前</span>
															</span>
														</span>
                                                    </a>
                                                </li>

                                                <li>
                                                    <a href="#">
                                                        <img src="${ctx!}/assets/avatars/default_3.png" class="msg-photo" alt="Bob's Avatar">
														<span class="msg-body">
															<span class="msg-title">
																<span class="blue">Bob:</span>
																Nullam quis risus eget urna mollis ornare ...
															</span>

															<span class="msg-time">
																<i class="ace-icon fa fa-clock-o"></i>
																<span>3:15 pm</span>
															</span>
														</span>
                                                    </a>
                                                </li>

                                                <li>
                                                    <a href="#">
                                                        <img src="${ctx!}/assets/avatars/default_4.png" class="msg-photo" alt="Kate's Avatar">
														<span class="msg-body">
															<span class="msg-title">
																<span class="blue">Kate:</span>
																Ciao sociis natoque eget urna mollis ornare ...
															</span>

															<span class="msg-time">
																<i class="ace-icon fa fa-clock-o"></i>
																<span>1:33 pm</span>
															</span>
														</span>
                                                    </a>
                                                </li>

                                                <li>
                                                    <a href="#">
                                                        <img src="${ctx!}/assets/avatars/default_5.png" class="msg-photo" alt="Fred's Avatar">
														<span class="msg-body">
															<span class="msg-title">
																<span class="blue">Fred:</span>
																Vestibulum id penatibus et auctor  ...
															</span>

															<span class="msg-time">
																<i class="ace-icon fa fa-clock-o"></i>
																<span>10:09 am</span>
															</span>
														</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div></li>
                                        <li class="dropdown-footer">
                                            <a href="javascript:;">
                                                查看所有消息
                                                <i class="ace-icon fa fa-arrow-right"></i>
                                            </a>
                                        </li>
                                    </ul>
                                </div><!-- /.tab-pane -->
                            </div><!-- /.tab-content -->
                        </div><!-- /.tabbable -->
                    </div><!-- /.dropdown-menu -->
                </li>
                <script>
                    var devModel = '${dev_model!}';
                    //if(devModel=='true'){
                    memory();
                    //}else{
                    //	window.setInterval(memory,3000);//三秒请求一次内存使用情况
                    //}
                </script>
			<#if platformList?? && (platformList?size>0) && is_root?? && is_root=='1'>
                <li class="purple">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#" aria-expanded="false">
                        <i class="ace-icon fa fa-tasks"></i>
                        <span class="badge badge-important" style="vertical-align: sub;"><#list platformList as pl><#if platform_id?? && platform_id==pl.val>${pl.project_name!}</#if></#list></span>
                    </a>
                    <ul class="dropdown-menu-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">
                        <li class="dropdown-header">
                            <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
                            选择需要管理的系统平台
                        </li>
                        <li class="dropdown-content ace-scroll" style="position: relative;">
                            <div class="scroll-track" style="display: none;"><div class="scroll-bar"></div></div><div class="scroll-content">
                            <ul class="dropdown-menu dropdown-navbar navbar-pink">
							<#list platformList as pl>
                                <li>
                                    <a href="javascript:;" onclick="selectPa('${pl.label!}');">
                                        <div class="clearfix">
                                            <span><img src="${pl.system_left_top_logo!}" height="25px" width="25px"></span>
                                            <span>&nbsp;&nbsp;${pl.project_name!}</span>
										<#if platform_id?? && platform_id==pl.val>
                                            <i class="ace-icon fa fa-check"></i>
										</#if>
                                        </div>
                                    </a>
                                </li>
							</#list>
                            </ul>
                        </div>
                        </li>
                    </ul>
                </li>
                <!-- 设置js -->
                <script>
                    function selectPa(platform){
                        $.ajax({
                            url: "/sys/change.action?platform="+platform,
                            async: true,
                            dataType:"json",
                            success: function(json){
                                if(json==1){
                                    location.reload();
                                }
                            },
                        });
                    }
                </script>
			</#if>
                <!-- 个人信息 -->
                <!-- #section:basics/navbar.user_menu -->
                <li class="light-blue" style="float:left !important;">
                    <button type="button" class="navbar-toggle menu-toggler pull-left" onclick="nav_sh()">
                        <span class="sr-only">Toggle sidebar</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
                        <script>function nav_sh(){
                            if($("#navigation_").css("display")=='block'){
                                $("#navigation_").css("display", "none");
                            }else{
                                $("#navigation_").css("display", "block");
                            }
                        }</script>
                    </button>
                </li>
                <p class="light-blue" style="float:left !important; margin:0 0 1px;">
                    <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
                        <span class="sr-only">Toggle sidebar</span><span class="icon-bar"></span><span class="icon-bar"></span></span>
                    </button>
                </p>
                <li class="light-blue" style="float:right !important;">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle" <#if navBgColor?? && navBgColor!=''>style="background:${navBgColor}"</#if>>
                        <img class="nav-user-photo" src="${ctx!}${page_user.avatar!}" alt="<#if page_user.realname??>${page_user.realname}<#else />${page_user.username!}</#if>" />
						<span class="user-info">
							<small>管理员: </small>
						<#if page_user.realname??>${page_user.realname}<#else />${page_user.username!}</#if>
						</span>
                        <i class="ace-icon fa fa-caret-down"></i>
                    </a>
                    <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                        <li>
                            <a href="${ctx!}/profile/info/edit${suffix!}">
                                <i class="ace-icon fa fa-user"></i>
                                个人信息
                            </a>
                        </li>
                        <li>
                            <a href="${ctx!}/profile/config/edit${suffix!}">
                                <i class="ace-icon fa fa-gear"></i>
                                个人设置
                            </a>
                        </li>
                        <li>
                            <a href="${ctx!}/profile/pwd/edit${suffix!}">
                                <i class="ace-icon fa fa-edit"></i>
                                修改密码
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="${ctx!}/logout${suffix}">
                                <i class="ace-icon fa fa-power-off"></i>
                                退出
                            </a>
                        </li>
                    </ul>
                </li>
                <!-- /section:basics/navbar.user_menu -->
            </ul>
        </div>
        <nav role="navigation" class="navbar-menu pull-left">
            <ul class="nav navbar-nav" id="navigation_">
			<#if (module_menu?size>0)>
			<#list module_menu as mm>
			<#if mm.is_show == 1>
                <li<#if module_entity.action_key?index_of(mm.action_key) == 0> class="active"</#if>>
                    <a href="<#if mm.action_key??><#if mm.action_key?index_of('http') == 0>${mm.action_key!}${mm.params!}<#else />${ctx!}${mm.action_key!}${mm.params!}</#if><#else />javascript:;</#if>" target="${mm.target!}">
					<#if mm.icon??><i class="ace-icon fa ${mm.icon} bigger-120"></i></#if>
					${mm.name!}
                    </a>
                </li>
			</#if>
			</#list>
			</#if>
            </ul>
        </nav>
        <!-- /section:basics/navbar.dropdown -->
    </div><!-- /.navbar-container -->
</div>
<!-- 顶部栏结束 -->


<!-- 左侧栏开始 -->
<div class="main-container" id="main-container">
    <script type="text/javascript">
        try{ace.settings.check('main-container' , 'fixed')}catch(e){}
    </script>

    <!-- #section:basics/sidebar -->
    <div id="sidebar" class="sidebar                  responsive">
        <script type="text/javascript">
            try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
        </script>

        <div class="sidebar-shortcuts" id="sidebar-shortcuts">
            <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
                <!-- 这是左侧栏顶部的四个button -->
                <button class="btn btn-success">
                    <i class="ace-icon fa fa-signal"></i>
                </button>
                <button class="btn btn-info">
                    <i class="ace-icon fa fa-pencil"></i>
                </button>
                <!-- #section:basics/sidebar.layout.shortcuts -->
                <button class="btn btn-warning">
                    <i class="ace-icon fa fa-users"></i>
                </button>
                <button class="btn btn-danger">
                    <i class="ace-icon fa fa-cogs"></i>
                </button>
                <!-- /section:basics/sidebar.layout.shortcuts -->
            </div>
            <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                <!-- 左侧栏四个button，收起时显示的 -->
                <span class="btn btn-success"></span>
                <span class="btn btn-info"></span>
                <span class="btn btn-warning"></span>
                <span class="btn btn-danger"></span>
            </div>
        </div><!-- /.sidebar-shortcuts -->
        <ul class="nav nav-list">
            <!--<li class="">
                <a href="index.html">
                    <i class="menu-icon fa fa-tachometer"></i>
                    <span class="menu-text"> 控制台 </span>
                </a>
                <b class="arrow"></b>
            </li>-->
		<#list menu_children as menu>
            <!-- 判断其下的子节点是否有展示在菜单的，如果有，则显示 -->
		<#assign threeLevel = 0 />
		<#if (menu.children?? && menu.children?size > 0)>
		<#list menu.children as sub_menu>
		<#if sub_menu.is_show==1 && sub_menu.level==3>
		<#assign threeLevel = 1 />
		</#if>
		</#list>
		</#if>
		<#if menu.is_show==1>
            <li<#if controller_entity?? && controller_entity.action_key?index_of(menu.action_key) == 0> class="highlight active<#if (menu.children?? && menu.children?size > 0)> open</#if>"</#if>>
                <a href="<#if menu.action_key??><#if menu.action_key?index_of('http') == 0>${menu.action_key!}${menu.params!}<#else />${ctx!}${menu.action_key!}${menu.params!}</#if><#else />javascript:;</#if>" target="${menu.target}"<#if (menu.children?? && menu.children?size > 0) && threeLevel==1> class="dropdown-toggle"</#if>>
				<#if menu.icon??><i class="menu-icon fa ${menu.icon}" style="color:${tabStyle!}"></i></#if>
                    <span class="menu-text"> ${menu.name!} </span>
                    <b class="arrow<#if (menu.children?? && menu.children?size > 0) && threeLevel==1> fa fa-angle-down</#if>"></b>
                </a>
                <b class="arrow"></b>
			<#if (menu.children?? && menu.children?size > 0) && threeLevel==1>
                <ul class="submenu">
				<#list menu.children as sub_menu>
				<#if sub_menu.is_show==1>
				<#assign functionKey = "0" />
				<#assign subMenuKey = "1" />
				<#if function_entity??>
				<#assign functionKey = function_entity.action_key+function_entity.params! />
				</#if>
				<#if sub_menu??>
				<#assign subMenuKey = sub_menu.action_key+sub_menu.params! />
				</#if>

                    <li<#if click_url?? && click_url?index_of(subMenuKey) == 0> class="active"</#if>>
                        <a href="<#if sub_menu.action_key??><#if sub_menu.action_key?index_of('http') == 0>${sub_menu.action_key}${sub_menu.params!}<#else />${ctx!}${sub_menu.action_key!}${sub_menu.params!}</#if><#else />javascript:;</#if>" target="${sub_menu.target}"<#if (sub_menu.children?? && sub_menu.children?size > 0)> class="dropdown-toggle"</#if>>
                            <i class="menu-icon fa fa-caret-right"></i>
						<#if sub_menu.name??>
						${sub_menu.name!}
						<#else />
						${sub_menu.function_name!}
						</#if>
                        </a>
                        <b class="arrow"></b>
                    </li>
				</#if>
				</#list>
                </ul>
			</#if>
            </li>
		</#if>
		</#list>
        </ul><!-- /.nav-list -->
        <!-- #section:basics/sidebar.layout.minimize -->
        <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
            <i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
        </div>
        <!-- /section:basics/sidebar.layout.minimize -->
        <script type="text/javascript">
            try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
        </script>
    </div>
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
					${module_entity.name!}
                    </li>
				<#if menu_level?? && menu_level=1>
                    <li>${module_entity.function_name!}</li>
				<#elseif menu_level?? && menu_level==2>
				<#if controller_entity??>
                    <li>${controller_entity.name!}</li>
                    <li>${controller_entity.function_name!}</li>
				</#if>
				<#else />
				<#if controller_entity??>
                    <li>${controller_entity.name!}</li>
				</#if>
				<#if function_entity??>
                    <li><#if function_entity.name??>${function_entity.name!}<#else />${function_entity.function_name!}</#if></li>
				</#if>
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
    <!-- 版权信息: 开始 -->
    <div class="footer">
        <div class="footer-inner">
            <div class="footer-content">
			<#if system_bottom_logo??>
                <img src="${system_bottom_logo!}" height="16px" style="vertical-align: sub">
			<#else />
                <i class="ace-icon fa fa-pagelines green"></i>
			</#if>
			${system_copyright!}
                <!-- gritter 弹窗点击 -->
                <button id="${gritter_type!}" style="display:none"></button>
                <div id="dialog-confirm" class="hide">
                    <div class="alert alert-info bigger-110">
                        此操作可能为永久删除，不会进入回收站
                    </div>
                    <div class="space-6"></div>
                    <p class="bigger-110 bolder center grey">
                        <i class="ace-icon fa fa-hand-o-right blue bigger-120"></i>
                        您确定要删除吗 ？
                    </p>
                </div><!-- #dialog-confirm -->
            </div>
        </div>
        <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
            <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
        </a>
    </div>
    <!-- 版权信息: 结束 -->
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
    <script src="${ctx!}/assets/js/jquery.nestable.js"></script>
    <script src="${ctx!}/assets/js/jquery.bootstrap-duallistbox.js"></script>
    <script src="${ctx!}/assets/js/jquery.raty.js"></script>
    <script src="${ctx!}/assets/js/bootstrap-multiselect.js"></script>
    <script src="${ctx!}/assets/js/select2.js"></script>
    <script src="${ctx!}/assets/js/typeahead.jquery.js"></script>

    <script src="${ctx!}/assets/js/jquery-ui.custom.js"></script>
    <script src="${ctx}/assets/js/x-editable/bootstrap-editable.js"></script>
    <script src="${ctx}/assets/js/x-editable/ace-editable.js"></script>

    <script src="${ctx}/assets/js/jquery.ui.touch-punch.js"></script>
    <script src="${ctx!}/assets/js/jquery.gritter.js"></script>
    <script src="${ctx}/assets/js/bootbox.js"></script>
    <script src="${ctx}/assets/js/jquery.easypiechart.js"></script>
    <script src="${ctx}/assets/js/date-time/bootstrap-datepicker.js"></script>
    <script src="${ctx}/assets/js/jquery.hotkeys.js"></script>
    <script src="${ctx}/assets/js/bootstrap-wysiwyg.js"></script>
    <script src="${ctx}/assets/js/fuelux/fuelux.spinner.js"></script>
    <script src="${ctx}/assets/js/x-editable/bootstrap-editable.js"></script>
    <script src="${ctx}/assets/js/x-editable/ace-editable.js"></script>
    <script src="${ctx}/assets/js/jquery.maskedinput.js"></script>


    <script src="${ctx}/assets/js/chosen.jquery.js"></script>
    <script src="${ctx}/assets/js/fuelux/fuelux.spinner.js"></script>
    <script src="${ctx}/assets/js/date-time/bootstrap-datepicker.js"></script>
    <script src="${ctx}/assets/js/date-time/bootstrap-timepicker.js"></script>
    <script src="${ctx}/assets/js/date-time/moment.js"></script>
    <script src="${ctx}/assets/js/date-time/daterangepicker.js"></script>
    <script src="${ctx}/assets/js/date-time/bootstrap-datetimepicker.js"></script>
    <script src="${ctx}/assets/js/bootstrap-colorpicker.js"></script>
    <script src="${ctx}/assets/js/jquery.knob.js"></script>
    <script src="${ctx}/assets/js/jquery.autosize.js"></script>
    <script src="${ctx}/assets/js/jquery.inputlimiter.1.3.1.js"></script>
    <script src="${ctx}/assets/js/bootstrap-tag.js"></script>

    <!-- page specific plugin scripts -->
    <script src="${ctx}/assets/js/fuelux/fuelux.wizard.js"></script>
    <!--<script src="${ctx}/assets/js/additional-methods.js"></script>-->

    <!-- page specific plugin scripts -->
    <script src="${ctx}/assets/js/fuelux/fuelux.tree.js"></script>

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

            function showErrorAlert (reason, detail) {
                var msg='';
                if (reason==='unsupported-file-type') { msg = "Unsupported format " +detail; }
                else {
                    //console.log("error uploading file", reason, detail);
                }
                $('<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button>'+
                        '<strong>File upload error</strong> '+msg+' </div>').prependTo('#alerts');
            }

            var demo1 = $('select[name="duallistbox_demo1[]"]').bootstrapDualListbox({
                infoTextFiltered: '<span class="label label-purple label-lg">筛选</span>',
                filterPlaceHolder:"过滤筛选",
                moveAllLabel:"选择全部",
                filterTextClear:"显示全部",
                removeAllLabel: '移除全部',
                infoText: '共 {0}',
                infoTextEmpty: '0'
            });

            $('#editor1').ace_wysiwyg({
                toolbar:
                        [
                            'font',
                            {name:'bold', className:'btn-info'},
                            {name:'italic', className:'btn-info'},
                            {name:'strikethrough', className:'btn-info'},
                            {name:'underline', className:'btn-info'},
                            null,
                            {name:'createLink', className:'btn-pink'},
                            {name:'unlink', className:'btn-pink'},
                            null,
                            'foreColor'
                        ],
                'wysiwyg': {
                    fileUploadError: showErrorAlert
                }
            }).prev().addClass('wysiwyg-style2');


            var container1 = demo1.bootstrapDualListbox('getContainer');
            container1.find('.btn').addClass('btn-white btn-info btn-bold');

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

            //select2
            $('.select2').css('width','270px').select2({allowClear:true})
            $('#select2-multiple-style .btn').on('click', function(e){
                var target = $(this).find('input[type=radio]');
                var which = parseInt(target.val());
                if(which == 2) $('.select2').addClass('tag-input-style');
                else $('.select2').removeClass('tag-input-style');
            });

            var oldie = /msie\s*(8|7|6)/.test(navigator.userAgent.toLowerCase());
            $('.easy-pie-chart.percentage').each(function(){
                $(this).easyPieChart({
                    barColor: $(this).data('color'),
                    trackColor: '#EEEEEE',
                    scaleColor: false,
                    lineCap: 'butt',
                    lineWidth: 8,
                    animate: oldie ? false : 1000,
                    size:75
                }).css('color', $(this).data('color'));
            });

            $('a[ data-original-title]').not('[data-rel=popover]').tooltip();
            $('[data-rel=tooltip]').tooltip();
            $('[data-rel=popover]').popover({html:true});

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

            //change profile
            $('[data-toggle="buttons"] .btn').on('click', function(e){
                var target = $(this).find('input[type=radio]');
                var which = parseInt(target.val());
                $('.user-profile').parent().addClass('hide');
                $('#user-profile-'+which).parent().removeClass('hide');
            });


            ////////////// Gritter Notifications
            var gl = '${gritter_light!}';
            var gritterType = '${gritter_type!}';
            var gritterClassName = '';
            if(gritterType=="gritter-center"){
                gritterClassName = 'gritter-info';
            }

            var gritterTime = '${gritter_time!}';
            if(gritterTime==null || gritterTime==''){
                gritterTime = 3000;
            }
            $('#'+gritterType).on(ace.click_event, function(){
                $.gritter.add({
                    title: '${gritter_title!}',
                    text: '${gritter_content!}',
                    image: '${gritter_image!}',
                    sticky: false,
                    time: gritterTime,
                    before_open: function(){
                        if($('.gritter-item-wrapper').length >= 3){
                            return false;
                        }
                    },
                    class_name: gritterClassName+' '+gritterType+' '+gl
                });
                return false;
            });
            //目前没有用到，保留
            $("#gritter-remove").on(ace.click_event, function(){
                $.gritter.removeAll();
                return false;
            });
            if(gritterType!=null && gritterType!=''){
                $("#"+gritterType).click();
            }

            //override dialog's title function to allow for HTML titles
            $.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
                _title: function(title) {
                    var $title = this.options.title || '&nbsp;'
                    if( ("title_html" in this.options) && this.options.title_html == true )
                        title.html($title);
                    else title.text($title);
                }
            }));

            $( "a[id=id-btn-dialog2]" ).on('click', function(e) {
                e.preventDefault();
                var url = $(this).attr("attr");
                $( "#dialog-confirm" ).removeClass('hide').dialog({
                    resizable: false,
                    width: '320',
                    modal: true,
                    title: "<div class='widget-header'><h4 class='smaller'><i class='ace-icon fa fa-exclamation-triangle red'></i> delete</h4></div>",
                    title_html: true,
                    buttons: [
                        {
                            html: "<i class='ace-icon fa fa-trash-o bigger-110'></i>&nbsp; 确 定删 除",
                            "class" : "btn btn-danger btn-minier padding5px",
                            click: function() {
                                location.href=url;
                                $( this ).dialog( "close" );
                            }
                        }
                        ,
                        {
                            html: "<i class='ace-icon fa fa-times bigger-110'></i>&nbsp; 取 消",
                            "class" : "btn btn-minier padding5px",
                            click: function() {
                                $( this ).dialog( "close" );
                            }
                        }
                    ]
                });
            });

            //selectmenu
            //$("select[id=number]").selectmenu({ position: { my : "left top", at: "left top" } })


            //in ajax mode, remove remaining elements before leaving page
            $(document).one('ajaxloadstart.page', function(e) {
                $('[class*=select2]').remove();
                $('select[name="duallistbox_demo1[]"]').bootstrapDualListbox('destroy');
                $('.rating').raty('destroy');
                $('.multiselect').multiselect('destroy');
            });
        });
    </script>

    <!-- the following scripts are used in demo only for onpage help and you don't need them -->
    <link rel="stylesheet" href="${ctx}/assets/css/ace.onpage-help.css" />
    <link rel="stylesheet" href="${ctx}/docs/assets/js/themes/sunburst.css" />
    <style>
        .com-btn{
            display: inline-block;
            margin: 2px
            padding: 1px 4px;
            border: 1px solid #ccc;
            text-decoration: none;
            color:#333;
            background: #fff;
            border-radius: 4px;
        }
    </style>

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

<#macro pager pageUrl formId=''>
<!-- 分页宏 -->
<#if (pageUrl?index_of('?') > -1)>
<#assign linker = '&'/>
<#else />
<#assign linker = '?'/>
</#if>
<div class="row">
    <div class="col-md-12">
        <div class="dataTables_paginate paging_simple_numbers" id="dynamic-table_paginate">
            <div style="vertical-align: super; margin-right:10px; float:left; font-weight:bold">共 <font style="color:#0000FF">${page.totalRow}</font> 条数据</div>
            <ul class="pagination">
           		<li>
					<input onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" type="text" id="jump_page_num" style="float: left; width:50px;border-right-style: hidden;text-align: center;border-radius: 5px 0 0 5px !important; padding: 3px 4px 6px;">
				</li>
				<li class="jump" id="dynamic-table_next" aria-controls="dynamic-table">
					<a href="javascript:;" style="margin-right:15px;border-radius: 0 1px 1px 0 !important;">GO</a>
				</li>
                <li class="paginate_button previous<#if page.pageNumber == 1> disabled</#if>" id="dynamic-table_previous" aria-controls="dynamic-table">
                    <a href="<#if page.pageNumber == 1>javascript:;<#else />${pageUrl!}${linker}pageNumber=${page.pageNumber - 1}</#if>">上一页</a>
                </li>
			<#if (page.totalPage < 10)>
			<#list 1..page.totalPage as num>
                <li class="paginate_button<#if page.pageNumber == num> active</#if>" aria-controls="dynamic-table">
                    <a href="<#if page.pageNumber == num>javascript:;<#else />${pageUrl!}${linker}pageNumber=${num}</#if>">${num}</a>
                </li>
			</#list>
			<#else />
			<#if (page.pageNumber < 6)>
			<#list 1..8 as num>
                <li class="paginate_button<#if page.pageNumber == num> active</#if>" aria-controls="dynamic-table">
                    <a href="<#if page.pageNumber == num>javascript:;<#else />${pageUrl!}${linker}pageNumber=${num}</#if>">${num}</a>
                </li>
			</#list>
                <li class="paginate_button" aria-controls="dynamic-table">
                    <a href="${pageUrl!}${linker}pageNumber=${page.totalPage}">...${page.totalPage}</a>
                </li>
			<#elseif (page.pageNumber > (page.totalPage - 5))>
                <li class="paginate_button" aria-controls="dynamic-table">
                    <a href="${pageUrl!}${linker}pageNumber=1">1...</a>
                </li>
			<#list (page.totalPage - 7)..page.totalPage as num>
                <li class="paginate_button<#if page.pageNumber == num> active</#if>" aria-controls="dynamic-table">
                    <a href="<#if page.pageNumber == num>javascript:;<#else />${pageUrl!}${linker}pageNumber=${num}</#if>">${num}</a>
                </li>
			</#list>
			<#else />
                <li class="paginate_button" aria-controls="dynamic-table">
                    <a href="${pageUrl!}${linker}pageNumber=1">1...</a>
                </li>
			<#list (page.pageNumber - 3)..(page.pageNumber + 3) as num>
                <li class="paginate_button<#if page.pageNumber == num> active</#if>" aria-controls="dynamic-table">
                    <a href="<#if page.pageNumber == num>javascript:;<#else />${pageUrl!}${linker}pageNumber=${num}</#if>">${num}</a>
                </li>
			</#list>
                <li class="paginate_button" aria-controls="dynamic-table">
                    <a href="${pageUrl!}${linker}pageNumber=${page.totalPage}">...${page.totalPage}</a>
                </li>
			</#if>
			</#if>
                <li class="paginate_button next<#if page.pageNumber == page.totalPage> disabled</#if>" id="dynamic-table_next" aria-controls="dynamic-table">
                    <a href="<#if page.pageNumber == page.totalPage>javascript:;<#else />${pageUrl!}${linker}pageNumber=${page.pageNumber + 1}</#if>">下一页</a>
                </li>
            </ul>
        </div>
    </div>
</div>
<#if formId != ''>
<script>
    $(function() {
        $('.paginate_button > a').click(function() {
            if ($(this).attr('href') != 'javascript:;') {
                $('#${formId}').attr('action', $(this).attr('href')).submit();
                return false;
            }
        });
        $('.jump > a').click(function() {
			if ($(this).attr('href') == 'javascript:;') {
				var jpn = $("#jump_page_num").val();
				if(jpn!=null && jpn!='' && parseInt(jpn)>0){
					$('#${formId}').attr('action', "${pageUrl!}${linker}pageNumber="+jpn).submit();
					return false;
				}
			}
		});
    });
</script>
</#if>
</#macro>
<#macro fancybox>
<!-- Add jQuery library -->
<script type="text/javascript" src="${ctx!}/assets/js/fancybox/lib/jquery-1.10.1.min.js"></script>
<!-- Add mousewheel plugin (this is optional) -->
<script type="text/javascript" src="${ctx!}/assets/js/fancybox/lib/jquery.mousewheel-3.0.6.pack.js"></script>
<!-- Add fancyBox main JS and CSS files -->
<script type="text/javascript" src="${ctx!}/assets/js/fancybox/source/jquery.fancybox.js?v=2.1.5"></script>
<link rel="stylesheet" type="text/css" href="${ctx!}/assets/js/fancybox/source/jquery.fancybox.css?v=2.1.5" media="screen" />
<!-- Add Button helper (this is optional) -->
<link rel="stylesheet" type="text/css" href="${ctx!}/assets/js/fancybox/source/helpers/jquery.fancybox-buttons.css?v=1.0.5" />
<script type="text/javascript" src="${ctx!}/assets/js/fancybox/source/helpers/jquery.fancybox-buttons.js?v=1.0.5"></script>
<!-- Add Thumbnail helper (this is optional) -->
<link rel="stylesheet" type="text/css" href="${ctx!}/assets/js/fancybox/source/helpers/jquery.fancybox-thumbs.css?v=1.0.7" />
<script type="text/javascript" src="${ctx!}/assets/js/fancybox/source/helpers/jquery.fancybox-thumbs.js?v=1.0.7"></script>
<!-- Add Media helper (this is optional) -->
<script type="text/javascript" src="${ctx!}/assets/js/fancybox/source/helpers/jquery.fancybox-media.js?v=1.0.6"></script>

</#macro>
<#macro preview>
<script src="${ctx}/assets/js/preview/preview.js" type="text/javascript"></script>
<link href="${ctx}/assets/js/preview/preview.css" type="text/css" rel="stylesheet"/>
<script src="${ctx}/assets/js/preview/autoResizeImage.js" type="text/javascript"></script>
</#macro>

<#macro ueditor>
<script type="text/javascript" charset="utf-8">
    window.CONTEXT_PATH = "${ctx!}";
    window.OSS_STATIC_PATH = "${ossStaticPath!}";
</script>
<!-- 配置文件 -->
<script type="text/javascript" src="${ctx!}/assets/js/my-ueditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="${ctx!}/assets/js/my-ueditor/ueditor.all.min.js"></script>
</#macro>

<#macro umeditor>
<!-- 配置文件 -->
<script type="text/javascript" src="${ctx!}/assets/js/umeditor/umeditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="${ctx!}/assets/js/umeditor/umeditor.js"></script>
<link href="${ctx!}/assets/js/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
</#macro>
<#macro uploadimg>
<style>
    .main-content-inner{float: none;}
</style>
<link href="${ctx!}/assets/js/cropper/dist/cropper.css" rel="stylesheet">
<link href="${ctx!}/assets/js/cropper2/css/main.css" rel="stylesheet">
<div class="container" id="crop-avatar">
    <div class="container" id="crop-avatar">
        <!-- Current avatar -->
        <!-- Cropping modal -->
        <div class="modal fade" id="avatar-modal" aria-hidden="true"
             aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <form class="avatar-form" action="crop.php" enctype="multipart/form-data" method="post">
                        <div class="modal-header">
                            <button class="close" data-dismiss="modal" type="button">&times;</button>
                            <h4 class="modal-title" id="avatar-modal-label">上传裁剪头像</h4>
                        </div>
                        <div class="modal-body">
                            <div class="avatar-body">
                                <!-- Upload image and data -->
                                <div class="avatar-upload">
                                    <input class="avatar-src" name="avatar_src" type="hidden">
                                    <input class="avatar-data" name="avatar_data" type="hidden">
                                    <label for="avatarInput">本地上传</label> <input class="avatar-input" id="avatarInput" name="avatar_file" type="file">
                                </div>
                                <!-- Crop and preview -->
                                <div class="row">
                                    <div class="col-md-9">
                                        <div class="avatar-wrapper"></div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="avatar-preview preview-lg"></div>
                                        <div class="avatar-preview preview-md"></div>
                                        <div class="avatar-preview preview-sm"></div>
                                    </div>
                                </div>
                                <div class="row avatar-btns">
                                    <div class="col-md-3">
                                        <button class="btn btn-primary btn-block avatar-save" type="submit">Done</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- /.modal -->
        <!-- Loading state -->
        <div class="loading" aria-label="Loading" role="img" tabindex="-1"></div>
    </div>
    <script src="${ctx!}/assets/js/cropper/assets/js/jquery.min.js"></script>
    <script src="${ctx!}/assets/js/cropper/dist/cropper.js"></script>
    <script src="${ctx!}/assets/js/cropper/js/main.js"></script>
</#macro>

<#macro datepicker>
    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="${ctx!}/assets/css/chosen.css" />
    <link rel="stylesheet" href="${ctx!}/assets/css/datepicker.css" />
    <link rel="stylesheet" href="${ctx!}/assets/css/bootstrap-timepicker.css" />
    <link rel="stylesheet" href="${ctx!}/assets/css/daterangepicker.css" />
    <link rel="stylesheet" href="${ctx!}/assets/css/bootstrap-datetimepicker.css" />
    <link rel="stylesheet" href="${ctx!}/assets/css/colorpicker.css" />
</#macro>
<#macro wdatePicker>
    <!-- page specific plugin styles -->
    <script language="javascript" type="text/javascript" src="${ctx!}/assets/js/My97DatePicker/WdatePicker.js"></script>
</#macro>

<#macro clipboard>
    <!-- 这里是JS代码部分 -->
    <script type="text/javascript" src="${ctx!}/assets/js/zeroclipboard/ZeroClipboard.min.js" ></script>
    <script type="text/javascript">
        $(document).ready(function () {
            ZeroClipboard.config({
                moviePath: "http://zeroclipboard.org/javascripts/zc/ZeroClipboard_1.3.1.swf",
                cacheBust: false,
                forceHandCursor: true,
                autoActivate: false,
                debug: true
            });

            var client = new ZeroClipboard();

            client.on("load", function (client) {
                console.log("load");

                client.on("complete", function (client, args) {
                    console.log("complete");
                });

                client.on("dataRequested", function (client, args) {
                    console.log("dataRequested");
                    client.setText("TestX");
                });

                client.on("mouseover", function (client) {
                    console.log("mouse over");
                });

                client.on("mouseout", function (client) {
                    ZeroClipboard.deactivate();
                    console.log("mouse out");
                });

                client.on("mousedown", function (client) {
                    console.log("mouse down");
                });

                client.on("mouseup", function (client) {
                    console.log("mouse up");
                });
            });
        });
    </script>
</#macro>

<#macro validate>
    <!-- 这里是JS代码部分 -->
    <!--<script src="${ctx}/assets/js/bt-validate.js"></script>-->
    <script src="${ctx!}/assets/js/validate/jquery.validate.js"></script>
    <link rel="stylesheet" href="${ctx!}/assets/js/validate/jquery.validate.css">
    <script src="${ctx!}/assets/js/validate/messages_cn.js"></script>
    <script src="${ctx!}/assets/js/validate/jquery.metadata.js"></script>
    <script>
        $().ready(function() {
            $(".bt-validate").validate({
                submitHandler: function(form) {
                    // 防止表单重复提交，前端js限制
                    $(form).find(":submit").button('loading');
                    form.submit();
                }
            });
        });
    </script>
</#macro>