<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-primary div-biz-cmp-task-detail">
	<div class="tab-content">
		<div class="tab-pane fade active in">
			<form action="#" method="get"
				class="form-inline form-validation form-search-init"
				data-grid-search=".grid-biz-cmp-task-detail">
				<input type="hidden" name="queTaskListReq.activityid" id="activityid" value="<s:property value='model.activityid'/>"></input>
                <input type="hidden" name="queTaskListReq.gridCode"  id="gridid"  value="<s:property value='model.gridid'/>"></input>
			</form>
			<div class="row">
				<div class="col-md-12">
					<table class="grid-biz-cmp-task-detail"></table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${base}/biz/manage/cmp/task/cmp-task-detail.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>