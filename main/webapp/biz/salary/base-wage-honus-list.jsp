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
					<form action="#" method="get"
						  class="form-inline form-validation form-search-init form-biz-salary-base-wage-honus-list"
						  data-grid-search=".grid-biz-salary-base-wage-honus-list">
					<div class="col-md-12">

							<div class="form-group">
								<s:select id="areaid" name="BaseWageHonus.areaid" cssStyle="width:250px"
										  list="{}"  placeholder="请选择业务区..." />
							</div>
							<div class="form-group">
								<s:textfield id="name" name="BaseWageHonus.name"
											 cssClass="form-control input-medium" placeholder="网格人员名称..."
											 maxlength="20" />
							</div>
						    <div class="form-group">
								<s:textfield id="loginname" name="BaseWageHonus.loginname"
											 cssClass="form-control input-medium" placeholder="登录名..."
											 maxlength="20" />
						    </div>
							<div class="form-group" style="width:250px">
								<s3:datetextfield  id="dateMonth" name="BaseWageHonus.dateMonth" placeholder="请选择月份..." format="yyyy-MM-dd HH:mm:00" data-timepicker="true" />
							</div>
							<span style="float: right;">
								<button class="btn green" type="submit" id="btn_search">
									<i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
								</button>
								<button class="btn default hidden-inline-xs" type="reset">
									<i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
								</button>
							</span>
					</div>
					<div class="col-md-8">
						<div class="dropdown col-md-2">
							<button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
								模板下载
								<span class="caret"></span>
							</button>
							<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
								<li><a href="javascript:SalaryBaseWageHonusList.excelDown()">基础工资及奖金导入模板</a></li>
							</ul>
						</div>
						<div class="dropdown col-md-2">
							<button class="btn btn-default dropdown-toggle" type="button" id="dropUpMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
								批量导入
								<span class="caret"></span>
							</button>
							<ul class="dropdown-menu" aria-labelledby="dropUpMenu1">
								<li><a href="javascript:SalaryBaseWageHonusList.excelImport()">基础工资及奖金</a></li>
							</ul>
						</div>
					</div>
					</form>
				</div>
				<div class="row">
					<div class="col-md-12">
						<table class="grid-biz-salary-base-wage-honus-list"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script
	src="${base}/biz/salary/base-wage-honus-list.js" />
<%@ include file="/common/ajax-footer.jsp"%>