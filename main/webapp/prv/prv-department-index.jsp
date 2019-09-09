<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.core-3.5.js"></script>

<s:token />
<div class="controls commonTreeLeft" id="deptTreeLeft">
	<ul id="deptTree" class="ztree"></ul>
</div>
<div id="deptManageRight" class="commonTreeRight">
	<%@ include file="/prv/prv-department-list.jsp"%>
</div>
<script src="${base}/prv/prv-department-index.js" />
<%@ include file="/common/ajax-footer.jsp"%>