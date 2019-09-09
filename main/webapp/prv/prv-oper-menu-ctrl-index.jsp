<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-primary">
	<ul class="nav nav-pills">
		<li class="active"><a class="tab-default" data-toggle="tab"
			href="#tab-auto">列表查询</a></li>
		<li class="tools pull-right"><a class="btn default reload"
			href="javascript:;"><i class="fa fa-refresh"></i></a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane fade active in">
			<div class="row search-form-default">
				<div class="col-md-12">
					<form action="#" method="get"
						class="form-inline form-validation form-search-init"
						data-grid-search=".grid-biz-prv-oper-menu-ctrl-index">
						<!-- <div class="input-group">
							<div class="form-group">
								<input type="text" name="operid" id="operid"
									placeholder="工号id...">
							</div>
							<div class="form-group">
								<input type="text" name="menuid" id="menuid"
									placeholder="限制对象名称">
							</div>
							<div class="form-group">
								<input type="text" name="controlcode" id="controlcode"
									placeholder="权限代码">
							</div>
							<div class="form-group">
								<select name="controlvalue" id="controlvalue"
									class="input-medium">
									<option value="0">低权</option>
									<option value="5">中权</option>
									<option value="9">高权</option>
								</select>
							</div>

							<button class="btn green" type="submit">
								<i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
							</button>
							<button class="btn default hidden-inline-xs" type="reset">
								<i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
							</button>
						</div> -->
					</form>
				</div>
				<div class="row">
					<div class="col-md-12">
						<table class="grid-biz-prv-oper-menu-ctrl-index"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${base}/prv/prv-oper-menu-ctrl-index.js" />
<%@ include file="/common/ajax-footer.jsp"%>