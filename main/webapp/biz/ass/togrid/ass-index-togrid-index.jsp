<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-primary">

	<div class="tab-content">
		<div class="tab-pane fade active in">
			<div class="row">
			       <form action="#" method="get" class="form-inline form-validation form-search-init"
						data-grid-search=".grid-biz-ass-togrid-ass-index-togrid-index">
						<input type="hidden" id="assid" name="assid" value="<s:property value="model.id"/>">	
					</form>
			
				<div class="col-md-12">
					<table class="grid-biz-ass-togrid-ass-index-togrid-index"></table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${base}/biz/ass/togrid/ass-index-togrid-index.js" />
<%@ include file="/common/ajax-footer.jsp"%>
