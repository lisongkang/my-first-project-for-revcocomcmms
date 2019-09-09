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
			<div class="row search-form-default" style="display: none;">
				<!-- 搜索暂时不用 -->
				<div class="col-md-12">
					<form action="#" method="get"
						class="form-inline form-validation form-search-init"
						data-grid-search=".grid-market-grid-info-gridaddress-multiple">
						<div class="input-group">
							<div class="input-cont">
								<input type="text" name="search['CN_whladdr']"
									class="form-control" placeholder="地址...">
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
					<table class="grid-market-grid-info-gridaddress-multiple"></table>
				</div>
			</div>
			<div class="row form-actions right" style="width: 102%">
				<button class="btn blue" type="button" id="multipleAddressSureBtn">
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
<script src="${base}/market/grid-info-gridaddress-multiple-selection.js" />
<%@ include file="/common/ajax-footer.jsp"%>