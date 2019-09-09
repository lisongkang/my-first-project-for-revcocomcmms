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
					<form action="#" method="get" class="form-inline form-validation form-search-init form-ad-audit-index"
						data-grid-search=".grid-ad-audit-index">
						<div class="input-group">
							<div class="form-group">
								<s:select id="id_city" name="adSearchParamsBo.city" cssStyle="width:150px"
									 list="{}"
									placeholder="请选择地市" />
						     </div>
						     <div class="form-group">
								<s:select id="id_adtype" name="adSearchParamsBo.adtype" cssStyle="width:150px"
									 list="{}"
									placeholder="广告类型" />
						     </div>
						     
						      <div class="form-group">
								<s:select id="id_adstatus" name="adSearchParamsBo.adstatus" cssStyle="width:150px"
									 list="{}"
									placeholder="广告状态" />
						     </div>
						      <div class="form-group">
						      
						       <input type="text"  id="id_optimeRange"
						        name="adSearchParamsBo.optimeRange"
						          class="form-control input-daterangepicker"
						           placeholder="录入时间" style="width:200px">
						     </div>
						     <div class="form-group">
							     <input type="text"  id = "id_auditTimeRang"
							        name="adSearchParamsBo.auditTimeRang"
							          class="form-control input-daterangepicker"
							           placeholder="审核时间" style="width:200px">
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
					<table class="grid-ad-audit-index"></table>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
<script src="${base}/ad/adset/ad-set-audit.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>
