<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-primary">
	<ul class="nav nav-pills">
		<li class="active"><a class="tab-default" data-toggle="tab" href="#tab-auto">列表查询</a></li>
		<li class="tools pull-right" style="opacity:0"><a class="btn default reload" href="javascript:;"><i class="fa fa-refresh"></i></a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane fade active in">
			<div class="row search-form-default">
				<div class="col-md-12">
					<form action="#" method="get" class="form-inline form-validation form-search-init form-biz-ass-index-store-index"
						data-grid-search=".grid-biz-ass-store-ass-target-store-index">
						 <div class="input-group">
						     <div class="form-group" style="margin-left: 10px;">
								<input type="text" name="name" id="id_name" class="form-control" 
									placeholder="指标名称" size="50">
						     </div>
						     <div class="form-group" style="margin-left: 10px;">
								 <s:select id="id_city" name="city" cssStyle="width:150px"
									 list="{}"
									placeholder="指标归属" />
						     </div>
						     <div class="form-group" style="margin-left: 60px;">
								<button class="btn green" id="btn_search" type="submmit">
									<i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
								</button>
								<button class="btn default hidden-inline-xs" type="reset">
									<i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
								</button>
						     </div>
					     </div>
					</form>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<table class="grid-biz-ass-store-ass-target-store-index"></table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${base}/biz/ass/target/ass-target-index.js" />
<%@ include file="/common/ajax-footer.jsp"%>
