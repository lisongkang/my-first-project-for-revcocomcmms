<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-primary">
	<ul class="nav nav-pills">
		<li class="active"><a class="tab-default" data-toggle="tab" href="#tab-auto">列表查询</a></li>
		<li class="tools pull-right"><a class="btn default reload" href="javascript:;"><i class="fa fa-refresh"></i></a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane fade active in">
			<div class="row search-form-default">
				<div class="col-md-12">
					<form action="#" method="get" class="form-inline form-validation form-search-init"
						data-grid-search=".grid-biz-prd-catalog-index">
						<div class="input-group">
							<div class="input-cont" style="float: left; width: 400px">
								<input type="text" name="search['CN_catalogname']" class="form-control" placeholder="目录名称...">
							</div>
							<div class="form-group" style="float: left; width: 300px">
								<s:select id="ctype" name="search['CN_ctype']" cssClass="form-control input-medium" list="{}"
										  placeholder="请选择目录类型"/>
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
					<table class="grid-biz-prd-catalog-index"></table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${base}/prd/catalog-index.js" />
<%@ include file="/common/ajax-footer.jsp"%>
