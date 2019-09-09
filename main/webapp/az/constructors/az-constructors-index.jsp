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
					<form action="#" method="get" class="form-inline form-validation form-search-init form-az-constructors-index"
						data-grid-search=".grid-az-constructors-index">
						<div class="input-group">
							<div class="form-group">
								<div class="controls" style="width:70px;margin-left: 20px">
									<input type="text" class="form-control input-large" name="AzConstParamsBo.loginname" placeholder="登录账户..">
								</div>
							</div>
							<div class="form-group" style="margin-left: 10px">
								<div class="controls" >
									<input type="text" class="form-control input-large" name="AzConstParamsBo.name" placeholder="名称...">
								</div>
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
					<table class="grid-az-constructors-index"></table>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
<script src="${base}/az/constructors/az-constructors-index.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>
