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
					<form action="#" method="get" class="form-inline form-validation form-search-init form-az-quota-index"
						data-grid-search=".grid-az-quota-index">
						<div class="input-group">
							<div class="form-group">
								<s:select id="id_city" name="AzSearchParamsBo.city" cssStyle="width:150px"
									 list="{}"
									placeholder="请选择地市" />
							</div>
							<div class="form-group">
								<div class="controls" style="width:100px;margin-left: 20px">
									<input type="text" class="form-control input-large" name="newnumber" placeholder="编号...">
								</div>
							</div>
							<div class="form-group">
								<div class="controls" style="width:70px;">
									<input type="text" class="form-control input-large" name="constname" placeholder="名称...">
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

						<input type="file" id="id_importExcel" name="myFile" style="display: block;float: left"
							   accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel">
						<button style="float: left" class="btn blue" type="button" id="btn_import" name="btn_import" data-grid-reload=".grid-az-quota-index">
							<i class="fa fa-check"></i> 确定导入
						</button>
						<a href="${base}/az/quota/az-quota!downTemplate"
						   style="float: left;margin-left: 5%;margin-bottom: 1px;color: red;text-decoration:underline;">下载Excel模板</a>
					</form>



				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<table class="grid-az-quota-index"></table>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
<script src="${base}/az/quota/az-quota-index.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>
