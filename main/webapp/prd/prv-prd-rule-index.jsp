<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-primary">
	<ul class="nav nav-pills">
		<li class="active"><a class="tab-default" data-toggle="tab" href="#tab-auto">列表查询</a></li>
		<li class="tools pull-right"><a class="btn default reload" href="javascript:;"><i class="fa fa-refresh"></i></a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane fade active in">
			<div class="row search-form-default">
				<div class="col-md-12">
				<form action="#" method="get" class="form-inline form-validation form-search-init"
						data-grid-search=".grid-prv-prd-rule-index">
					</form>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<table class="grid-prv-prd-rule-index"></table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${base}/prd/prv-prd-rule-index.js" />
<%@ include file="/common/ajax-footer.jsp"%>
