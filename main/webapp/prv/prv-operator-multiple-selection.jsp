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
						class="form-inline form-validation form-search"
						data-grid-search=".grid-auth-multiple-user-index">
						<div class="input-group">
							<div class="input-cont">
								<input type="text" name="search['CN_loginname_OR_name']"
									class="form-control" placeholder="登录账号、名称--">
							</div>
							<span class="input-group-btn">
								<button class="btn green" type="submmit">
									<i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
								</button>
								<button class="btn default" type="reset">
									<i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
								</button>
							</span>
						</div>
					</form>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<table class="grid-auth-multiple-user-index" data-grid="table"></table>
				</div>
			</div>
			<div class="row form-actions right" style="width: 102%">
				<button class="btn blue" type="button" id="multipleOperSureBtn">
					<i class="fa fa-check"></i> 保存
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
</script>
<script src="${base}/prv/prv-operator-multiple-selection.js" />
<%@ include file="/common/ajax-footer.jsp"%>