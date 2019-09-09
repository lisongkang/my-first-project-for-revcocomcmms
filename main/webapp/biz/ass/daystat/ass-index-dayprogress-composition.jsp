<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.exhide-3.5.js"></script>

<s:token />
<div class="controls commonTreeLeft" id="dayTreeLeft">
	<ul id="dayGridTree" class="ztree"></ul>
</div>
<div id="dayGridManageRight" class="commonTreeRight">
	<%@ include file="/biz/ass/daystat/ass-index-dayprogress-index.jsp"%>
</div>
<script src="${base}//biz/ass/daystat/ass-index-dayprogress-composition.js" />

<%@ include file="/common/ajax-footer.jsp"%>
