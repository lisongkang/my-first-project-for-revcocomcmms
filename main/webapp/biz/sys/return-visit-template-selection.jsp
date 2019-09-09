<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div id="div_biz-prd-sales-select">
	<div class="tabbable tabbable-primary">
		<div class="tab-content">
			<div class="tab-pane fade active in">
				<div class="row">
					<div class="col-md-12">
					<form action="#" id="form-biz-prd-sales-select" method="get" class="form-inline form-validation form-search-init"
						data-grid-search=".return-visit-template-index">
						</form>
						<table class="return-visit-template-index"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${base}/biz/sys/return-visit-template-selection.js" />
<%@ include file="/common/ajax-footer.jsp"%>
