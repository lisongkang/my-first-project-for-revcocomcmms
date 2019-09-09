<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
						<form action="#" method="get" class="form-inline form-validation form-search-init form-az-royaprint-az-royaprint-azroyaindex"
							  data-grid-search=".grid-az-royaprint-az-royaprint-azroyaindex">
							<div class="input-group">
								<div class="form-group">
									<s:hidden id="backupCityId" />
									<s:select id="id_city" name="AzRoyaSerchParamsBO.city"
											  cssClass="form-control input-medium" list="areaMap"
											  placeholder="请选择地市" />
								</div>
								<div class="form-group">
									<s:hidden id="backupAreaId" />
									<s:select id="id_area" name="AzRoyaSerchParamsBO.areaid"
											  cssClass="form-control input-medium" list="{}"
											  placeholder="请选择业务区" />
								</div>
								<div class="form-group">
									<s:select id="prostatus"
											  name="AzRoyaSerchParamsBO.prostatus"
											  cssClass="form-control input-medium" list="{}"
											  placeholder="请选择申请单状态..." />
								</div>
								<div class="form-group">
									<s:textfield id="proname" name="AzRoyaSerchParamsBO.proname"
												 cssClass="form-control input-medium" placeholder="请输入项目名称..."
												 maxlength="20" />
								</div>

								<div class="form-group">
									<s:textfield id="constructorname" name="AzRoyaSerchParamsBO.constructors"
												 cssClass="form-control input-medium" placeholder="请输入施工员名称..."
												 maxlength="20" />
								</div>
								<div class="form-group" id="timeRangeDiv">
									<input type="text" id="timeRange"
										   name="AzRoyaSerchParamsBO.timeRange"
										   class="form-control input-medium input-daterangepicker"
										   placeholder="完成时间段">
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
						<table class="grid-az-royaprint-az-royaprint-azroyaindex"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${base}/az/royaprint/az-royaprint-azroyaindex.js" />
<%@ include file="/common/ajax-footer.jsp"%>