<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.exhide-3.5.js"></script>

<s:token />
<style type="text/css">
#custTreeLeft {
	float: left;
	width: 19%;
	min-height: 500px;
	max-height: 500px;
	overflow-y: auto;
}

/* #custGridManageRight {
	float: right;
	width: 80%;
}
 */
#custTreeLeft .ztree * {
	font-size: 14px !important;
}

#custTreeLeft .ztree li span.button.manageGrid_ico_open {
	background-position: -110px -16px;
	margin-right: 2px;
	vertical-align: top;
	*vertical-align: middle;
}

#custTreeLeft .ztree li span.button.manageGrid_ico_close, .ztree li span.button.manageGrid_ico_docu {
	background-position: -110px 0;
	margin-right: 2px;
	vertical-align: top;
	*vertical-align: middle;
}

#custTreeLeft .ztree li span.button.patchGrid_ico_docu {
	background-position: -110px -32px;
	margin-right: 2px;
	vertical-align: top;
	*vertical-align: middle;
}
</style>

<div class="controls" id="custTreeLeft">
	<ul id="custGridTree" class="ztree"></ul>
</div>
<div id="custGridManageRight">
	<%@ include file="/market/market-batch-cust.jsp"%>
</div>
<script src="${base}/market/market-batch-custcomposition.js" />

<%@ include file="/common/ajax-footer.jsp"%>
