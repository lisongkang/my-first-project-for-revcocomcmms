<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="div-grid-biz-extendattr-prv-attrrule-index">
<div class="tabbable tabbable-primary">
	<ul class="nav nav-pills">
		<li class="active"><a class="tab-default" data-toggle="tab" href="#tab-auto">列表查询</a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane fade active in">
			<div class="row search-form-default">
				<div class="col-md-12">
					<form action="#" method="get" class="form-inline form-validation form-search-init form-az-quota-azblindex"
						data-grid-search=".grid-az-quota-azblindex">
						<div class="input-group">
							<div class="form-group">
								<s:select id="id_city" name="AzSerchBlParamsBo.city" cssStyle="width:150px"
									 list="{}"
									placeholder="请选择地市" />
							</div>

						     <span class="input-group-btn">
								<button class="btn green" type="submit" id="btn_search">
									<i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
								</button>
								<button class="btn default hidden-inline-xs" type="reset">
									<i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
								</button>
							</span>
						</div>
					</form>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<table class="grid-az-quota-azblindex"></table>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
<script src="${base}/az/quotabl/az-quota-azblindex.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>
