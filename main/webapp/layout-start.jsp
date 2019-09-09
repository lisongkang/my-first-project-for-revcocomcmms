<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/app-ver.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title><s:property value="%{systemTitle}" /></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">

<!-- Basic Javascripts -->
<script src="assets/plugins/jquery-1.10.2.min.js?_=${buildVersion}" type="text/javascript"></script>
<script src="assets/plugins/jquery.blockui.min.js?_=${buildVersion}" type="text/javascript"></script>

<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="assets/plugins/font-awesome/css/font-awesome.min.css?_=${buildVersion}" rel="stylesheet" type="text/css" />
<link href="assets/plugins/bootstrap/css/bootstrap.min.css?_=${buildVersion}" rel="stylesheet" type="text/css" />
<link href="assets/plugins/uniform/css/uniform.default.css?_=${buildVersion}" rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link rel="stylesheet" type="text/css" href="assets/plugins/select2/select2_metro.css?_=${buildVersion}" />

<!-- END PAGE LEVEL STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="assets/css/style-metronic.css?_=${buildVersion}" rel="stylesheet" type="text/css" />
<link href="assets/css/style.css?_=${buildVersion}" rel="stylesheet" type="text/css" />
<link href="assets/css/style-responsive.css?_=${buildVersion}" rel="stylesheet" type="text/css" />
<link href="assets/css/plugins.css?_=${buildVersion}" rel="stylesheet" type="text/css" />
<link href="assets/css/themes/light.css?_=${buildVersion}" rel="stylesheet" type="text/css" id="style_color" />

<link rel="stylesheet" type="text/css"
	href="assets/plugins/bootstrap-switch/static/stylesheets/bootstrap-switch-metro.css?_=${buildVersion}" />
<link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap-datepicker/css/datepicker.css?_=${buildVersion}" />
<link rel="stylesheet" type="text/css"
	href="assets/plugins/bootstrap-timepicker/compiled/timepicker.css?_=${buildVersion}" />
<link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap-colorpicker/css/colorpicker.css?_=${buildVersion}" />
<link rel="stylesheet" type="text/css"
	href="assets/plugins/bootstrap-daterangepicker/daterangepicker-bs2.css?_=${buildVersion}" />
<link rel="stylesheet" type="text/css"
	href="assets/plugins/bootstrap-datetimepicker/css/datetimepicker.css?_=${buildVersion}" />
<link rel="stylesheet" type="text/css"
	href="assets/plugins/bootstrap-editable/bootstrap-editable/css/bootstrap-editable.css?_=${buildVersion}" />
<link rel="stylesheet" type="text/css"
	href="assets/plugins/bootstrap-editable/inputs-ext/address/address.css?_=${buildVersion}" />
<link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap-toastr/toastr.min.css?_=${buildVersion}" />

<link rel="stylesheet" type="text/css"
	href="assets/plugins/jquery-ui/redmond/jquery-ui-1.10.3.custom.min.css?_=${buildVersion}">
<link rel="stylesheet" type="text/css" href="assets/extras/jquery-jqgrid/plugins/ui.multiselect.css?_=${buildVersion}">
<link rel="stylesheet" type="text/css" href="assets/extras/jquery-jqgrid/css/ui.jqgrid.css?_=${buildVersion}">
<link rel="stylesheet" type="text/css" href="assets/app/bootstrap-jqgrid.css?_=${buildVersion}" />

<link rel="stylesheet" type="text/css" href="assets/extras/kindeditor/themes/default/default.css?_=${buildVersion}">

<link rel="stylesheet" type="text/css" href="assets/extras/jquery-ztree/css/zTreeStyle/zTreeStyle.css?_=${buildVersion}">
<link rel="stylesheet" type="text/css" href="assets/extras/jquery-ztree/css/demo.css?_=${buildVersion}">
<link rel="stylesheet" type="text/css"
	href="assets/plugins/jquery-file-upload/css/jquery.fileupload-ui.css?_=${buildVersion}" />

<link rel="stylesheet" type="text/css" href="assets/extras/tooltipster/css/tooltipster.css?_=${buildVersion}" />
<link rel="stylesheet" type="text/css"
	href="assets/extras/tooltipster/css/themes/tooltipster-light.css?_=${buildVersion}" />
<link rel="stylesheet" type="text/css"
	href="assets/extras/tooltipster/css/themes/tooltipster-noir.css?_=${buildVersion}" />
<link rel="stylesheet" type="text/css"
	href="assets/extras/tooltipster/css/themes/tooltipster-punk.css?_=${buildVersion}" />
<link rel="stylesheet" type="text/css"
	href="assets/extras/tooltipster/css/themes/tooltipster-shadow.css?_=${buildVersion}" />

<link href="assets/plugins/fullcalendar/fullcalendar.css?_=${buildVersion}" rel="stylesheet" />

<link href="assets/css/pages/tasks.css?_=${buildVersion}" rel="stylesheet" type="text/css" />
<link href="assets/css/pages/search.css?_=${buildVersion}" rel="stylesheet" type="text/css" />
<link href="assets/css/pages/lock.css?_=${buildVersion}" rel="stylesheet" type="text/css" />

<link href="assets/app/custom.css?_=${buildVersion}" rel="stylesheet" type="text/css" />
<link href="assets/css/style-re.css?_=${buildVersion}" rel="stylesheet" type="text/css" />

<!-- END THEME STYLES -->
<link rel="shortcut icon" href="${base}/pub/favicon.ico" />

<!-- 年月控件 -->
<link type="text/css" rel="stylesheet" href="assets/plugins/jedate/skin/jedate.css?_=${buildVersion}">
<script src="assets/plugins/jedate/jquery.jedate.min.js?_=${buildVersion}"></script>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed page-body" style="min-height: 600px">
	<script>
        $("body").block({
            message : '<img src="${base}/assets/img/ajax-modal-loading.gif" width="80px" align="">',
            centerY : true,
            css : {
                top : '10%',
                border : 'none',
                padding : '2px',
                backgroundColor : 'none'
            },
            overlayCSS : {
                backgroundColor : '#000',
                opacity : 0.6,
                cursor : 'wait'
            }
        });

    </script>
	<!-- BEGIN HEADER -->
	<div class="header navbar navbar-inverse navbar-fixed-top">

		<!-- BEGIN TOP NAVIGATION BAR -->
		<div class="header-inner">
			<!-- BEGIN LOGO -->
			<a class="navbar-brand" href="javascript:window.location.reload()"
				style="padding-top: 5px; padding-bottom: 5px; margin-left: 2px; width: 400px">
				<h3 style="margin-top: 2px; margin-bottom: 0px; color: #dddddd;">
					<%-- <img src="resources/images/logo.jpg"/> --%>
					<s:property value="%{systemTitle}" />
				</h3>
			</a>
			<!-- END LOGO -->
			<!-- BEGIN RESPONSIVE MENU TOGGLER -->
			<a href="javascript:;" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"> <img
				src="assets/img/menu-toggler.png" alt="" />
			</a>

			<!-- END RESPONSIVE MENU TOGGLER -->
			<!-- BEGIN TOP NAVIGATION MENU -->
			<ul class="nav navbar-nav pull-right">
				<s:if test="#request.notifyListSize > 0">
					<!-- BEGIN NOTIFICATION DROPDOWN -->
					<li class="dropdown" id="header_notification_bar"><a href="javascript:;" class="dropdown-toggle"
						data-toggle="dropdown" data-hover="dropdown" data-close-others="true"> <i class="fa fa-comment-o"></i> <span
							class="badge"><s:property value='#request.notifyListSize' /></span>
					</a>
						<ul class="dropdown-menu extended notification">
							<li>
								<p><span><s:property value='#request.notifyListSize' /></span>条未读消息</p>
							</li>
							<li>
								<ul id="fddd" class="dropdown-menu-list scroller">
									<s:iterator value="#request.notifyList" status="status" var="notify">
										<li><a id="<s:property value="#notify.id" />" href="${base}/common/pubpost/pub-post!view?id=<s:property value="#notify.id" />"> <span class="label label-icon label-success"><i class="fa fa-bell-o"></i></span>
											<s:property value="#notify.htmlTitle" />
										</a></li>
									</s:iterator>
								</ul>
							</li>
						</ul></li>
					</s:if>
				<!-- END NOTIFICATION DROPDOWN -->
				<!-- BEGIN ENVELOPE DROPDOWN -->
				<li class="dropdown user"><a href="javascript:;" class="dropdown-toggle"
					data-toggle="dropdown" data-close-others="true"> <span class="phone"><i class="fa fa-envelope-o"></i></span><i class="fa"></i>
				</a></li>
				<!-- END ENVELOPE DROPDOWN -->
				<!-- BEGIN Mobile DROPDOWN 
				<li class="dropdown user"><a href="javascript:;" class="dropdown-toggle"
					data-toggle="dropdown" data-close-others="true"> <span class="phone"><i class="fa fa-mobile"></i></span><i class="fa fa-angle-down"></i>
				</a>
					<ul class="dropdown-menu" id="dropdown-menu-mobile">
						<li><a href="${base}/pub/android_client.apk" target="_blank"><i class="fa fa-android"></i> Android客户端下载</a></li>
						<li class="divider divider-menus"></li>
					</ul></li>
				-->
				<!-- END Mobile DROPDOWN -->

				<!-- BEGIN USER LOGIN DROPDOWN -->
				<li class="dropdown user"><a href="javascript:;" class="dropdown-toggle"
					data-toggle="dropdown" data-close-others="true"> <span class="username"><i class="fa fa-certificate"></i> <s:property
								value="%{authUserDetails.usernameDisplay}" /></span> <i class="fa fa-angle-down"></i>
				</a>
					<ul class="dropdown-menu">
						<li class="hide"><a href="extra_profile.html"><i class="fa fa-user"></i> My Profile</a></li>
						<li class="hide"><a href="page_calendar.html"><i class="fa fa-calendar"></i> My Calendar</a></li>
						<li class="hide"><a href="inbox.html"><i class="fa fa-envelope"></i> My Inbox <span
								class="badge badge-danger">3</span></a></li>
						<li class="hide"><a href="javascript:;"><i class="fa fa-tasks"></i> My Tasks <span
								class="badge badge-success">7</span></a></li>
						<li class="divider hide"></li>
						<li><a href="javascript:;" id="trigger_fullscreen"><i class="fa fa-move"></i> 全屏显示</a></li>
						<!-- 暂时未用到，先屏蔽 -->
						<%-- <li><a id="trigger_passwd" href="${base}/prv/prv-operator!passwd" title="修改密码"><i class="fa fa-key"></i> 修改密码</a></li> --%>
						<li><a href="javascript:;" id="a-lock-screen" rel="address:/lock"><i class="fa fa-lock"></i> 锁定系统</a></li>
						<%-- <li><a href="javascript:;"><i class="fa fa-sign-out"></i> 切换部门</a></li> --%>
						<li><a href="javascript:;" id="a-logout"><i class="fa fa-sign-out"></i> 注销登录</a></li>
					</ul></li>
				<!-- END USER LOGIN DROPDOWN -->

			</ul>
			<!-- END TOP NAVIGATION MENU -->

		</div>
      <!-- <div class="toggler"></div> -->
		<!-- BEGIN STYLE CUSTOMIZER -->
<!-- 		<div class="theme-panel hidden-xs hidden-sm pull-right" style="margin-top: -3px; position: absolute; right: 0px">
			<div class="toggler"></div>
			<div class="toggler-close"></div>
			<div class="theme-options">
				<div class="theme-option theme-colors clearfix">
					<span>颜色样式</span>
					<ul>
						<li class="color-black current color-default" data-style="default"></li>
						<li class="color-blue" data-style="blue"></li>
						<li class="color-brown" data-style="brown"></li>
						<li class="color-purple" data-style="purple"></li>
						<li class="color-grey" data-style="grey"></li>
						<li class="color-white color-light" data-style="light"></li>
					</ul>
				</div>
				<div class="theme-option">
					<span>页面布局</span> <select class="layout-option form-control input-small">
						<option value="fluid" selected="selected">扩展</option>
						<option value="boxed">收缩</option>
					</select>
				</div>
				<div class="theme-option">
					<span>页面头部</span> <select class="header-option form-control input-small">
						<option value="fixed" selected="selected">固定</option>
						<option value="default">自动</option>
					</select>
				</div>
				<div class="theme-option">
					<span>侧边菜单</span> <select class="sidebar-option form-control input-small">
						<option value="fixed">浮动</option>
						<option value="default" selected="selected">自动</option>
					</select>
				</div>
				<div class="theme-option">
					<span>页面底部</span> <select class="footer-option form-control input-small">
						<option value="fixed">固定</option>
						<option value="default" selected="selected">自动</option>
					</select>
				</div>
				<div class="theme-option">
					<span>右键菜单</span> <select class="context-menu-option form-control input-small">
						<option value="enable" selected="selected">启用</option>
						<option value="disable">禁用</option>
					</select>
				</div>
				<div class="theme-option">
					<span>表格布局</span> <select class="grid-shrink-option form-control input-small">
						<option value="auto">自动</option>
						<option value="true" selected="selected">收缩</option>
					</select>
				</div>
			</div>
		</div> -->
		<!-- END BEGIN STYLE CUSTOMIZER -->

		<!-- END TOP NAVIGATION BAR -->
	</div>
	<!-- END HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
		<div class="modal fade" id="portlet-config" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						<h4 class="modal-title">Modal title</h4>
					</div>
					<div class="modal-body">Widget settings form goes here</div>
					<div class="modal-footer">
						<button type="button" class="btn blue">Save changes</button>
						<button type="button" class="btn default" data-dismiss="modal">Close</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
		<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
		<!-- BEGIN SIDEBAR1 -->
		<div class="page-sidebar navbar-collapse collapse">
			<!-- BEGIN SIDEBAR MENU1 -->
			<ul class="page-sidebar-menu">
				<li>
					<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
					<div class="sidebar-toggler hidden-xs"></div> <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
				</li>
				<li>
					<!-- BEGIN RESPONSIVE QUICK SEARCH FORM -->
					<div class="sidebar-search">
						<div class="form-container">
							<div class="input-box">
								<a href="javascript:;" class="remove"></a> <input type="text" name="search" placeholder="菜单项快速查询过滤..." value=""
									title="试试输入菜单名称拼音首字母" /> <input type="button" class="submit" value=" " />
							</div>
						</div>
					</div> <!-- END RESPONSIVE QUICK SEARCH FORM -->
				</li>
				<s:iterator value="#request.rootMenus" status="status" var="root">
					<li class="menu-root <s:property value="%{#root.open?'open':''}" />"><a href="javascript:;" onclick="Biz.showMenu(this);"
						data-code="<s:property value="#root.code" />"> <i class="fa fa-cogs"></i> <span class="title"><s:property
									value="#root.name" /></span> <span class="arrow <s:property value="%{#root.open?'open':''}" />"></span>
					</a> <s:if test="#root.childrenSize>0">
							<ul class="sub-menu" style="display: <s:property value="%{#root.open?'block':'none'}" />;">
								<s:iterator value="#root.children" var="childLevel1">
									<s:if test="#childLevel1.childrenSize>0">
										<li><a href="javascript:;" data-code="<s:property value="#childLevel1.code" />"> <i
												class="fa fa-ellipsis-vertical"></i> <span class="title"><s:property value="#childLevel1.name" /></span> <span
												class="arrow <s:property value="%{#childLevel1.open?'open':''}" />"></span>
										</a>
											<ul class="sub-menu" style="display: <s:property value="%{#childLevel1.open?'block':'none'}" />;">
												<s:iterator value="#childLevel1.children" var="childLevel2">
													<s:if test="#childLevel2.childrenSize>0">
														<li><a href="javascript:;" data-code="<s:property value="#childLevel2.code" />"> <i
																class="fa fa-ellipsis-vertical"></i> <span class="title"><s:property value="#childLevel2.name" /></span>
																<span class="arrow <s:property value="%{#childLevel2.open?'open':''}" />"></span>
														</a>
															<ul class="sub-menu" style="display: <s:property value="%{#childLevel2.open?'block':'none'}" />;">
																<s:iterator value="#childLevel2.children" var="childLevel3">
																	<li><a class="ajaxify" data-code="<s:property value="#childLevel2.code" />"
																		href="${base}<s:property value='#childLevel3.url' />"
																		rel="address:/<s:property value='#childLevel3.id' />"> <i class="fa fa-dot-circle-o"></i> <s:property
																				value="#childLevel3.name" /></a></li>
																</s:iterator>
															</ul></li>
													</s:if>
													<s:else>
														<li><a class="ajaxify" data-code="<s:property value="#childLevel2.code" />"
															href="${base}<s:property value='#childLevel2.url' />"
															rel="address:/<s:property value='#childLevel2.id' />" target="_blank"> <i class="fa fa-dot-circle-o"></i>
																<s:property value="#childLevel2.name" /></a></li>
													</s:else>
												</s:iterator>
											</ul></li>
									</s:if>
									<s:else>
										<li><a class="ajaxify" data-code="<s:property value="#childLevel1.code" />"
											href="${base}<s:property value='#childLevel1.url' />" rel="address:/<s:property value='#childLevel1.id' />">
												<i class="fa fa-dot-circle-o"></i> <s:property value="#childLevel1.name" />
										</a></li>
									</s:else>
								</s:iterator>
							</ul>
						</s:if></li>
				</s:iterator>
			</ul>
			<!-- END SIDEBAR MENU1 -->
		</div>
		<!-- END SIDEBAR1 -->
		<!-- BEGIN PAGE -->
		<div class="page-content">
			<div class="row" style="margin-left: 0px; margin-right: 0px">
				<div class="col-md-12" style="padding-left: 0px; padding-right: 0px">
					<ul class="page-breadcrumb breadcrumb" id="layout-nav" style="margin-top: 0px; margin-bottom: 5px;">
						<li class="btn-group" style="right: 0px;">
						
							<button data-close-others="true" data-delay="1000" data-toggle="dropdown" class="btn blue dropdown-toggle"
								type="button">
								<span><i class="fa fa-reorder"></i> 访问列表</span> <i class="fa fa-angle-down"></i>
							</button>
							<ul role="menu" class="dropdown-menu">
							</ul>
							<button id="btn-dashboard" class="btn default btn-dashboard" type="button">
								<i class="fa fa-home"></i> Dashboard
							</button>
							<button class="btn default btn-close-active" type="button">
								<i class="fa fa-times"></i>
							</button>
						</li>
						<li><i class="fa fa-home"></i> <a href="javascript:;">Dashboard</a></li>
					</ul>
					<div class="tab-content">
						<div id="tab_content_dashboard"></div>
					</div>
				</div>
			</div>
		</div>
		<!-- BEGIN PAGE -->

	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<div class="footer">
		<div class="footer-inner">
			&copy;
			 Copyright 2015 Maywide Technology Corporation All Rights Reserved
		</div>
		<div class="footer-tools">
			<span class="go-top"> <i class="fa fa-angle-up"></i>
			</span>
		</div>
	</div>
	<!-- END FOOTER -->

	<!-- BEGIN FileUpload FORM -->
	<div class="modal fade" id="fileupload-dialog" tabindex="-1" role="basic" aria-hidden="true">
		<div class="modal-dialog modal-wide">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
					<h4 class="modal-title">文件上传</h4>
				</div>
				<div class="modal-body">
					<form id="fileupload" enctype="multipart/form-data" method="POST">
						<input type="hidden" name="attachmentName" value="attachments" />
						<div class="row fileupload-buttonbar">
							<div class="col-lg-7">
								<!-- The fileinput-button span is used to style the file input field as button -->
								<span class="btn green fileinput-button"> <i class="fa fa-plus"></i> <span>添加文件...</span> <input
									type="file" multiple="" name="files">
								</span>
								<button class="btn blue start" type="submit">
									<i class="fa fa-upload"></i> <span>开始上传</span>
								</button>
								<button class="btn yellow cancel" type="reset">
									<i class="fa fa-ban"></i> <span>取消上传</span>
								</button>
								<!-- The loading indicator is shown during file processing -->
								<span class="fileupload-loading"></span>
							</div>
							<!-- The global progress information -->
							<div class="col-lg-5 fileupload-progress fade">
								<!-- The global progress bar -->
								<div aria-valuemax="100" aria-valuemin="0" role="progressbar" class="progress progress-striped active">
									<div style="width: 0%;" class="progress-bar progress-bar-success"></div>
								</div>
								<!-- The extended global progress information -->
								<div class="progress-extended">&nbsp;</div>
							</div>
						</div>
						<table class="table table-striped clearfix" role="presentation">
							<tbody class="files"></tbody>
						</table>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn default" data-dismiss="modal">取消</button>
					<button type="submit" class="btn blue btn-add">添加</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- END FileUpload FORM -->


   <!-- BEGIN FileUpload 自定义文件上传  -->
  <!--  <div class="upload_file_dialog_content" style="display:none">
	    <div class="row">
	          <div class="col-md-8">
				  <div class="form-group">   
				    <label class="control-label">上传文件</label>
				    <div class="controls">
				        <input id="selectedFileDir" name="" type="text"  class="form-control">
				        <input type="file" name="fileToUpload" id="fileToUpload" onchange="Biz.fileSelected();" style="display:none;">
			        </div>
			      </div>
		      </div>
		      <div >
			      <button class="btn blue"  name=''  onclick="Biz.fileSelect();" >
					<i class="fa "></i> 浏览...
				  </button>
			 </div>
	    </div>
	</div> -->
   <!--  END FileUpload -->
	<div class="page-lock" id="page-lock" style="text-align: left; color: #eeeeee; display: none">
		<div class="page-logo">
			<h3>
				<s:property value="%{systemTitle}" />
			</h3>
		</div>
		<div class="page-body">
			<div class="tile bg-blue" style="margin-top: 10px; margin-left: 10px">
				<div class="tile-body">
					<i class="fa fa-lock"></i>
				</div>
			</div>
			<div class="page-lock-info">
				<h1>
					<s:property value="%{authUserDetails.usernameDisplay}" />
				</h1>
				<form id="form-unlock" action="${base}/layout!unlock" class="form-inline" method="post">
					<div class="input-group">
						<input type="password" name="password" placeholder="输入解锁码..." class="form-control" autocomplete="off"
							required="true"> <span class="input-group-btn">
							<button class="btn blue icn-only" type="submit">
								<i class="m-icon-swapright m-icon-white"></i>
							</button>
						</span>
					</div>
					<div class="relogin">请输入登录密码解锁</div>
				</form>
			</div>
		</div>
		<div class="page-footer">
			&copy; Copyright 2015 Maywide Technology Corporation All Rights Reserved
		</div>
	</div>

	<button type="button" class="btn " id="btn-profile-param" title="点击收藏记忆当前表单元素数据" style="display: none">
		<i class="fa fa-heart-o"></i>
	</button>
	
	<!-- BEGIN FORGOT PASSWORD FORM -->
	<div class="modal fade" id="pub-post-view" tabindex="-1" role="basic" aria-hidden="true">
		<div class="modal-dialog modal-full">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
					<h4 class="modal-title">公告内容</h4>
				</div>
				<div class="modal-body">
					<div class="form-horizontal form-bordered form-label-stripped">
						<div class="form-body">
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label">标题</label>
										<div class="controls">
											<p class="form-control-static">
												<s:property value="htmlTitle" escapeHtml="false" />
											</p>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label">内容</label>
										<div class="controls">
											<p class="form-control-static">
												<s:property value="htmlContent" escapeHtml="false" />
											</p>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label">发布时间</label>
										<div class="controls">
											<p class="form-control-static">
												<s:date name="publishTime" format="yyyy-MM-dd HH:mm:ss" />
											</p>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label">到期时间</label>
										<div class="controls">
											<p class="form-control-static">
												<s:date name="expireTime" format="yyyy-MM-dd HH:mm:ss" />
											</p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- END FORGOT PASSWORD FORM -->

	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<script id="template-upload" type="text/x-tmpl">
        {% for (var i=0, file; file=o.files[i]; i++) { %}
            <tr class="template-upload fade">
                <td>
                    <p class="name">{%=file.name%}</p>
                    {% if (file.error) { %}
                        <div><span class="label label-danger">Error</span> {%=file.error%}</div>
                    {% } %}
                </td>
                <td align="right">
                    <p class="size">{%=o.formatFileSize(file.size)%}</p>
                    {% if (!o.files.error) { %}
                        <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0">
                        <div class="progress-bar progress-bar-success" style="width:0%;"></div>
                        </div>
                    {% } %}
                </td>
                <td align="right">
                    {% if (!o.files.error && !i && !o.options.autoUpload) { %}
                        <button type="button" class="btn blue start">
                            <i class="fa fa-upload"></i>
                            <span>上传</span>
                        </button>
                    {% } %}
                    {% if (!i) { %}
                        <button type="button" class="btn red cancel">
                            <i class="fa fa-ban"></i>
                            <span>取消</span>
                        </button>
                    {% } %}
                </td>
            </tr>
        {% } %}
    </script>
	<!-- The template to display files available for download -->
	<script id="template-download" type="text/x-tmpl">
        {% for (var i=0, file; file=o.files[i]; i++) { %}
            <tr class="template-download">
                <td>
                    <p class="name">
                        <input type="hidden" name="{%=file.attachmentName%}" value="{%=file.id%}"/>
                        {% if (file.url) { %}
                            <a target="_blank" href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" {%=file.thumbnailUrl?'data-gallery':''%}>{%=file.name%}</a>
                        {% } else { %}
                            <span>{%=file.name%}</span>
                        {% } %}
                    </p>
                    {% if (file.error) { %}
                        <div><span class="label label-danger">Error</span> {%=file.error%}</div>
                    {% } %}
                </td>
                <td align="right" class="td-size">
                    <span class="size">{%=file.size%}</span>
                </td>
                <td align="right" class="td-op">
                        <button type="button" class="btn default" onclick="$(this).closest('tr').remove()">
                            <i class="fa fa-trash-o"></i>
                            <span>删除</span>
                        </button>
                </td>
            </tr>
        {% } %}
    </script>

	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<!--[if lt IE 9]>
	<script src="assets/plugins/respond.min.js?_=${buildVersion}"></script>
	<script src="assets/plugins/excanvas.min.js?_=${buildVersion}"></script> 
	<![endif]-->

	<script src="assets/plugins/jquery-migrate-1.2.1.min.js?_=${buildVersion}" type="text/javascript"></script>
	<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script src="assets/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js?_=${buildVersion}" type="text/javascript"></script>
	<script src="assets/plugins/bootstrap/js/bootstrap.min.js?_=${buildVersion}" type="text/javascript"></script>
	<script src="assets/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js?_=${buildVersion}"
		type="text/javascript"></script>
	<script src="assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js?_=${buildVersion}" type="text/javascript"></script>

	<script src="assets/plugins/jquery.cookie.min.js?_=${buildVersion}" type="text/javascript"></script>
	<script src="assets/plugins/uniform/jquery.uniform.min.js?_=${buildVersion}" type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<script type="text/javascript" src="assets/plugins/select2/select2.min.js?_=${buildVersion}"></script>
	<script type="text/javascript" src="assets/plugins/jquery-validation/dist/jquery.validate.js?_=${buildVersion}"></script>
	<script type="text/javascript" src="assets/plugins/jquery-validation/dist/additional-methods.min.js?_=${buildVersion}"></script>
	<script type="text/javascript" src="assets/plugins/jquery-validation/localization/messages_zh.js?_=${buildVersion}"></script>

	<script type="text/javascript" src="assets/plugins/fuelux/js/spinner.min.js?_=${buildVersion}"></script>
	<script type="text/javascript" src="assets/plugins/bootstrap-fileupload/bootstrap-fileupload.js?_=${buildVersion}"></script>
	<script type="text/javascript" src="assets/plugins/select2/select2.min.js?_=${buildVersion}"></script>
	<script type="text/javascript" src="assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js?_=${buildVersion}"></script>
	<script type="text/javascript"
		src="assets/plugins/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js?_=${buildVersion}"></script>
	<script type="text/javascript"
		src="assets/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js?_=${buildVersion}"></script>
	<script type="text/javascript"
		src="assets/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js?_=${buildVersion}"
		charset="UTF-8"></script>
	<script type="text/javascript" src="assets/plugins/clockface/js/clockface.js?_=${buildVersion}"></script>
	<script type="text/javascript" src="assets/plugins/bootstrap-daterangepicker/daterangepicker.js?_=${buildVersion}"></script>
	<script type="text/javascript" src="assets/plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.js?_=${buildVersion}"></script>
	<script type="text/javascript" src="assets/plugins/bootstrap-timepicker/js/bootstrap-timepicker.js?_=${buildVersion}"></script>
	<script type="text/javascript" src="assets/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js?_=${buildVersion}"></script>
	<script type="text/javascript" src="assets/plugins/jquery.input-ip-address-control-1.0.min.js?_=${buildVersion}"></script>
	<script type="text/javascript" src="assets/plugins/jquery-multi-select/js/jquery.multi-select.js?_=${buildVersion}"></script>
	<script type="text/javascript" src="assets/plugins/jquery-multi-select/js/jquery.quicksearch.js?_=${buildVersion}"></script>
	<script src="assets/plugins/jquery.pwstrength.bootstrap/src/pwstrength.js?_=${buildVersion}" type="text/javascript"></script>
	<script src="assets/plugins/bootstrap-switch/static/js/bootstrap-switch.min.js?_=${buildVersion}"
		type="text/javascript"></script>
	<script src="assets/plugins/jquery-tags-input/jquery.tagsinput.min.js?_=${buildVersion}" type="text/javascript"></script>
	<script src="assets/plugins/bootstrap-markdown/js/bootstrap-markdown.js?_=${buildVersion}" type="text/javascript"></script>
	<script src="assets/plugins/bootstrap-markdown/lib/markdown.js?_=${buildVersion}" type="text/javascript"></script>
	<script src="assets/plugins/bootstrap-maxlength/bootstrap-maxlength.min.js?_=${buildVersion}" type="text/javascript"></script>
	<script src="assets/plugins/bootstrap-touchspin/bootstrap.touchspin.js?_=${buildVersion}" type="text/javascript"></script>
	<script type="text/javascript" src="assets/plugins/jquery.pulsate.min.js?_=${buildVersion}"></script>
	<script src="assets/plugins/bootstrap-toastr/toastr.min.js?_=${buildVersion}"></script>
	<script src="assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js?_=${buildVersion}" type="text/javascript"></script>
	<script src="assets/plugins/backstretch/jquery.backstretch.min.js?_=${buildVersion}" type="text/javascript"></script>
	<script src="assets/plugins/bootbox/bootbox.min.js?_=${buildVersion}" type="text/javascript"></script>
	<script type="text/javascript"
		src="assets/plugins/bootstrap-editable/bootstrap-editable/js/bootstrap-editable.min.js?_=${buildVersion}"></script>
	<script type="text/javascript" src="assets/plugins/bootstrap-editable/inputs-ext/address/address.js?_=${buildVersion}"></script>

	<script src="assets/plugins/flot/jquery.flot.js?_=${buildVersion}"></script>
	<script src="assets/plugins/flot/jquery.flot.resize.js?_=${buildVersion}"></script>
	<script src="assets/plugins/flot/jquery.flot.pie.js?_=${buildVersion}"></script>
	<script src="assets/plugins/flot/jquery.flot.stack.js?_=${buildVersion}"></script>
	<script src="assets/plugins/flot/jquery.flot.crosshair.js?_=${buildVersion}"></script>
	<script src="assets/plugins/flot/jquery.flot.time.js?_=${buildVersion}"></script>

	<script src="assets/plugins/fancybox/source/jquery.fancybox.pack.js?_=${buildVersion}"></script>

	<script src="assets/plugins/fullcalendar/lib/moment.min.js?_=${buildVersion}"></script>
	<script src="assets/plugins/fullcalendar/fullcalendar.min.js?_=${buildVersion}"></script>
	<script src="assets/plugins/fullcalendar/lang/zh-cn.js?_=${buildVersion}"></script>

	<!-- BEGIN:File Upload Plugin JS files-->
	<!-- The Templates plugin is included to render the upload/download listings -->
	<script src="assets/plugins/jquery-file-upload/js/vendor/tmpl.min.js?_=${buildVersion}"></script>
	<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
	<script src="assets/plugins/jquery-file-upload/js/vendor/load-image.min.js?_=${buildVersion}"></script>
	<!-- The Canvas to Blob plugin is included for image resizing functionality -->
	<script src="assets/plugins/jquery-file-upload/js/vendor/canvas-to-blob.min.js?_=${buildVersion}"></script>
	<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
	<script src="assets/plugins/jquery-file-upload/js/jquery.iframe-transport.js?_=${buildVersion}"></script>
	<!-- The basic File Upload plugin -->
	<script src="assets/plugins/jquery-file-upload/js/jquery.fileupload.js?_=${buildVersion}"></script>
	<!-- The File Upload processing plugin -->
	<script src="assets/plugins/jquery-file-upload/js/jquery.fileupload-process.js?_=${buildVersion}"></script>
	<!-- The File Upload image preview & resize plugin -->
	<script src="assets/plugins/jquery-file-upload/js/jquery.fileupload-image.js?_=${buildVersion}"></script>
	<!-- The File Upload audio preview plugin -->
	<script src="assets/plugins/jquery-file-upload/js/jquery.fileupload-audio.js?_=${buildVersion}"></script>
	<!-- The File Upload video preview plugin -->
	<script src="assets/plugins/jquery-file-upload/js/jquery.fileupload-video.js?_=${buildVersion}"></script>
	<!-- The File Upload validation plugin -->
	<script src="assets/plugins/jquery-file-upload/js/jquery.fileupload-validate.js?_=${buildVersion}"></script>
	<!-- The File Upload user interface plugin -->
	<script src="assets/plugins/jquery-file-upload/js/jquery.fileupload-ui.js?_=${buildVersion}"></script>
	
	<!-- The main application script -->
	<!-- The XDomainRequest Transport is included for cross-domain file deletion for IE 8 and IE 9 -->
	<!--[if (gte IE 8)&(lt IE 10)]>
    <script src="assets/plugins/jquery-file-upload/js/cors/jquery.xdr-transport.js?_=${buildVersion}"></script>
    <![endif]-->
	<!-- END:File Upload Plugin JS files-->

	<script src="assets/extras/pinyin.js?_=${buildVersion}"></script>
	<script src="assets/extras/JSPinyin.js?_=${buildVersion}"></script>

	<script src="assets/extras/jquery-jqgrid/plugins/ui.multiselect.js?_=${buildVersion}"></script>
	<script src="assets/extras/jquery-jqgrid/js/i18n/grid.locale-cn.js?_=${buildVersion}"></script>
	<script src="assets/extras/jquery-jqgrid/js/jquery.jqGrid.src.js?_=111"></script>

	<script src="assets/extras/jquery-ztree/js/jquery.ztree.all-3.5.js?_=${buildVersion}"></script>

	<script src="assets/extras/jquery.address/jquery.address-1.5.min.js?_=${buildVersion}"></script>

	<script src="assets/extras/kindeditor/kindeditor-ext.js?_=${buildVersion}"></script>
	<script type="text/javascript">
        var WEB_ROOT = "${base}";
        var AUTH_USER = {
            uid : '<s:property value="%{authUserDetails.uid}" />',
            username : '<s:property value="%{authUserDetails.username}" />'
        };
    <%out.println("KindEditor.options.uploadJson = '"
					+ request.getContextPath()
					+ "/pub/image-upload!execute;JSESSIONID=" + session.getId()
					+ "'");%>
        
    </script>

	<script type="text/javascript" src="assets/extras/tooltipster/js/jquery.tooltipster.min.js?_=${buildVersion}"></script>
	<script src="assets/scripts/app.js?_=${buildVersion}"></script>
	<script src="assets/extras/jquery.form.js?_=${buildVersion}"></script>
	<script src="assets/extras/bootstrap-contextmenu.js?_=${buildVersion}"></script>
	<script src="assets/extras/taffydb/taffy-min.js?_=${buildVersion}"></script>
	<script src="assets/app/dynamic-table.js?_=${buildVersion}"></script>
	<script src="assets/app/util.js?_=${buildVersion}"></script>
	<script src="assets/app/global.js?_=${buildVersion}"></script>
	<script src="assets/app/grid.js?_=${buildVersion}"></script>
	<script src="assets/app/form-validation.js?_=${buildVersion}"></script>
	<script src="assets/app/page.js?_=${buildVersion}"></script>
	<script src="resources/js/biz.js?_=${buildVersion}"></script>
    <script src="assets/scripts/ajaxfileupload.js?"></script>
	<script>
        $(function() {
            App.init();
            Util.init();
            Global.init();
            FormValidation.init();
            Biz.init();
            //操作员登录信息
          	var str = '${LOGIN_INFO}';
        	Biz.LOGIN_INFO = eval('(' + str + ')');

        	App.unblockUI($("body"));
        });

        $("#fddd li").each(function(i, item) {
			if (item.children[0].id != null) {
				$("#" + item.children[0].id).click(function(g){$(this).popupDialog({size:"auto"});g.preventDefault();return false});
			}
        });
    </script>
</body>
<!-- END BODY -->
</html>