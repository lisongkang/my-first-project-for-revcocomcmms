<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-primary">
	<ul class="nav nav-pills">
		<li class="active"><a class="tab-default" data-toggle="tab"
			href="#tab-auto"><s:property value="#parameters.tabName" /></a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane fade active in">
			<div class="row search-form-default">
				<div class="col-md-12">
					<form action="#" method="get"
						class="form-inline form-validation form-search-init"
						data-grid-search=".grid-survey-quesiont-multiple">
						<div class="input-group">
							<div class="input-cont">
								<div class="form-body">
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label">题目名称、录入工号</label>
												<div class="controls">
													<input type="text" class="form-control input-large"
														name="search['CN_operator_OR_qcontent']"
														placeholder="题目名称、录入工号...">
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label">录入时间</label>
												<div class="controls">
													<s3:datetextfield name="search['CN_intime']" format="date"
														placeholder="录入时间" cssClass="input-large" />
												</div>
											</div>
										</div>
									</div>
								</div>
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
					<table class="grid-survey-quesiont-multiple"></table>
				</div>
			</div>
			<div class="row form-actions center" style="width: 102%">
				<button class="btn blue" type="button" id="multipleQuestionAddBtn"
					style="margin-left: -10%;">&or;</button>
				<button class="btn blue" type="button"
					id="multipleQuestionDeleteBtn">&and;</button>
				<ul class="nav nav-pills" style="float: left; margin-left: 5px;">
					<li class="active"><a class="tab-default" data-toggle="tab"
						href="#tab-auto">问卷：<s:property value="#parameters.sname" />已选题目
					</a></li>
				</ul>
				<button class="btn blue" type="button" id="submitSurveyBtnUp"
					style="float: right; margin-right: 10px;">
					<i class="fa fa-check"></i> 提交
				</button>
			</div>

			<!-- 必须有这个隐藏表单，否则下面的表格不能生成 -->
			<form action="#" method="get"
				class="form-inline form-validation form-search-init"
				data-grid-search=".grid-survey-quesiont-dest" style="display: none;"></form>
			<div class="row">
				<div class="col-md-12">
					<table class="grid-survey-quesiont-dest"></table>
				</div>
			</div>
			<div class="row form-actions right" style="width: 102%">
				<button class="btn blue" type="button" id="submitSurveyBtnDown">
					<i class="fa fa-check"></i> 提交
				</button>
			</div>
		</div>
	</div>
</div>
<script>
	var singleSelect = <s:property value="#parameters.singleSelect"/>;
	var listUrl = '<s:property value="#parameters.listUrl"/>'; //字符串要加引号
	var trueListUrl = listUrl.replace("_PARAM", "?").replace(
			new RegExp("_AND", "g"), "&").replace(new RegExp("_EQ", "g"), "=");
	var sname = '<s:property value="#parameters.sname"/>';
</script>
<script src="${base}/survey/biz-survey-list-question-multiple-selection.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>