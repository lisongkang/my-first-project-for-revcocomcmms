<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="${base}/assets/plugins/contextjs/context.js"></script>
<link rel="stylesheet" type="text/css" href="${base}/assets/plugins/contextjs/context.css">
<style type="text/css">
#treeLeft {
	float: left;
	width: 19%;
}

#treeLeft #treeDiv {
	min-height: 480px;
	max-height: 480px;
	overflow-y: auto;
}

#treeLeft #treeDiv #dataGridTree {
	display: none;
}

#gridRight {
	position: relative;
	float: right;
	width: 80%;
}

#gridManageRight {
	float: right;
	width: 100%;
}

#gridRight .maskDiv {
	position: absolute;
	right: 0;
	width: 100%;
	z-index: 100;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
	-moz-opacity: 0.2;
	opacity: 0.2;
	filter: alpha(opacity = 20);
	background-color: white;
	display: none;
}

#gridRight #maskDivUp {
	top: 0;
	height: 38px;
}

#gridRight #maskDivDown {
	bottom: 0;
	height: 635px;
}

/* Webkit内核兼容CSS */
@media screen and (-webkit-min-device-pixel-ratio:0) {
	#gridRight #maskDivDown {
		height: 570px;
	}
}
</style>
<s:token />
<div class="controls" id="treeLeft">
	<s:select id="treeType" name="treeType" list="#{0:'基础网格',1:'数据网格'}" value="0" />
	<div id="treeDiv">
		<ul id="gridTree" class="ztree"></ul>
		<ul id="dataGridTree" class="ztree"></ul>
	</div>
</div>
<div id="gridRight">
	<div id="gridManageRight">
		<%@ include file="/market/grid-info-list.jsp"%>
	</div>
	<div id="maskDivUp" class="maskDiv"></div>
	<div id="maskDivDown" class="maskDiv"></div>
</div>
<script src="${base}/market/grid-info-index.js" />
<%@ include file="/common/ajax-footer.jsp"%>