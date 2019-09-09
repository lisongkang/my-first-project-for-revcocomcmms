<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-primary" id="div-biz-memo-cfg-index">
	<ul class="nav nav-pills">
		<li class="active"><a class="tab-default" data-toggle="tab"
			href="#tab-auto">备注配置列表</a></li>
		<li class="tools pull-right"><a class="btn default reload"
			href="javascript:;"><i class="fa fa-refresh"></i></a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane fade active in">
			<div class="row search-form-default">
				<div class="col-md-12">
					<form action="#" method="get" class="form-inline form-validation form-search-init"
						  data-grid-search=".grid-memo-cfg-index">
						<div class="input-group">
							<div class="input-cont" style="float: left; width: 400px">
								<input type="text" name="bizMemoCfgParamBo.memotxt" class="form-control" placeholder="备注内容...">
							</div>
							<div class="form-group" style="float: left; width: 300px">
								<s:select id="opcodes" name="bizMemoCfgParamBo.opcodes" cssClass="form-control input-medium" list="{}"
										  placeholder="请选择操作代码"/>
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
					<table class="grid-memo-cfg-index"></table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${base}/memo/biz-memo-cfg-index.js" />
<%@ include file="/common/ajax-footer.jsp"%>