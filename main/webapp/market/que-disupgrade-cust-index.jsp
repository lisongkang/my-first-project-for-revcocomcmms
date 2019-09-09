<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.exhide-3.5.js"></script>

<s:token />
<div class="controls commonTreeLeft" id="qdcTreeLeft">
	<ul id="qdcGridTree" class="ztree"></ul>
</div>
<div id="qdcGridManageRight" class="commonTreeRight">
	<%@ include file="/market/que-disupgrade-cust-list.jsp"%>
</div>
<script src="${base}/market/que-disupgrade-cust-index.js" />

<%@ include file="/common/ajax-footer.jsp"%>
