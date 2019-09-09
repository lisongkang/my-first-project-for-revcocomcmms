<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-primary">
	<ul class="nav nav-pills">
		<li class="active"><a class="tab-default" data-toggle="tab"
			href="#tab-auto"><s:property value="#parameters.tabName"/></a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane fade active in">
			<div class="row search-form-default">
				<div class="col-md-12">
					<form action="#" method="get"
						class="form-inline form-validation form-search-init"
						data-grid-search=".grid-market-res-multiple-patch-index">
						<div class="input-group">
							<div class="input-cont">
								<input type="text" name="search['CN_patchname_OR_defcode']"
									class="form-control" placeholder="小区名称、代码...">
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
					<table class="grid-market-res-multiple-patch-index"></table>
				</div>
			</div>
			<div class="row form-actions right" style="width: 102%">
				<button class="btn blue" type="button" id="multiplePatchSureBtn">
					<i class="fa fa-check"></i> 保存
				</button>
			</div>
		</div>
	</div>
</div>
<script>
    var tabName = '<s:property value="#parameters.tabName"/>';
	var singleSelect = <s:property value="#parameters.singleSelect"/>;
	var listUrl = '<s:property value="#parameters.listUrl"/>'; //字符串要加引号
	var trueListUrl = listUrl.replace("_PARAM", "?").replace(
			new RegExp("_AND", "g"), "&").replace(new RegExp("_EQ", "g"), "=");
</script>
<script src="${base}/market/res-patch-multiple-selection.js" />
<%@ include file="/common/ajax-footer.jsp"%>