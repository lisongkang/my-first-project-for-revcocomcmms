<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-primary">
	<ul class="nav nav-pills">
		<li class="active"><a class="tab-default" data-toggle="tab"
			href="#tab-auto">问卷统计列表</a></li>
		<li class="tools pull-right"><a class="btn default reload"
			href="javascript:;"><i class="fa fa-refresh"></i></a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane fade active in">
			<div class="row search-form-default">
				<div class="col-md-12">
					<form action="#" method="get"
						class="form-inline form-validation form-search-init form-biz-survey-list-index"
						data-grid-search=".grid-survey-list-analysis-index">
						<div class="input-group">
							<div class="form-group">
								<s:hidden id="backupCityId" />
								<s:select id="id_city" name="bizSurveyListParamBo.cityid"
									cssClass="form-control input-medium" list="areaMap"
									placeholder="请选择地市" />
							</div>
							<div class="form-group">
								<s:hidden id="backupAreaId" />
								<s:select id="id_area" name="bizSurveyListParamBo.areaids"
									cssClass="form-control input-medium" list="{}"
									placeholder="请选择业务区" multiple="true" />
							</div>
							<div class="form-group">
								<s:select id="id_status" name="bizSurveyListParamBo.status"
									list="surveyStatusMap" placeholder="请选择状态" />
							</div>
							<div class="form-group">
								<s:select id="id_isreal" name="bizSurveyListParamBo.isrealName"
									list="realNameStutusMap" placeholder="是否实名制" />
							</div>
							<span class="input-group-btn">
								<button class="btn green" type="submmit">
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
					<table class="grid-survey-list-analysis-index"></table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${base}/survey/biz-survey-list-analysis.js" />
<%@ include file="/common/ajax-footer.jsp"%>