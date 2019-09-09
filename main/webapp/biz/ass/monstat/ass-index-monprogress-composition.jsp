<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.exhide-3.5.js"></script>

<s:token />
<div class="controls commonTreeLeft" id="monTreeLeft">
	<ul id="monGridTree" class="ztree"></ul>
</div>
<div id="monGridManageRight" class="commonTreeRight">
	<%@ include file="/biz/ass/monstat/ass-index-monprogress-index.jsp"%>
</div>
<script src="${base}//biz/ass/monstat/ass-index-monprogress-composition.js" />

<%@ include file="/common/ajax-footer.jsp"%>
