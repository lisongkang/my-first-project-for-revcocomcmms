<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.exhide-3.5.js"></script>

<s:token />
<div class="controls commonTreeLeft" id="sumMonTreeLeft">
	<ul id="sumMonGridTree" class="ztree"></ul>
</div>
<div id="sumMonGridManageRight" class="commonTreeRight">
	<%@ include file="/biz/ass/monstat/ass-index-monprogress-sumindex.jsp"%>
</div>
<script src="${base}//biz/ass/monstat/ass-index-monprogress-sumcomposition.js" />

<%@ include file="/common/ajax-footer.jsp"%>
