<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
      <div class="row">
		<div class="col-md-12">
		    <table class="grid-biz-survey-biz-question-list-index" data-grid="table" style="center"></table>
		</div>
	  </div>
<script type="text/javascript">
	var sid = '<s:property value="#request.sid" />';
</script>
<script src="${base}/survey/biz-survey-list-quetionlist.js" />
<%@ include file="/common/ajax-footer.jsp"%>

