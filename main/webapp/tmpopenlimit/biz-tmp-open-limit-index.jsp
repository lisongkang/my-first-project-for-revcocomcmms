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
						data-grid-search=".grid-channel-biz-tmpopenlimit-biz-tmp-open-limit-index">
						<div class="input-group">
						    <div class="form-group">
							    <select name="timeType1" id="timeType1" placeholder="限制方式..." class="input-medium">
								   <option value="0">按月</option>
								   <option value="1">按天</option>
						    	</select>
						    </div>
							<div class="form-group">
							<select name="objType1" id="objType1" placeholder="限制对象类型..." class="input-medium">
								<option value="0">部门</option>
								<option value="1">工号</option>
							</select>
						</div>
						<div class="form-group">
							<input type="text"  name="objName1" id="objName1" placeholder="限制对象名称" >
						</div>
						<div class="form-group">
							<input type="text" name="name1" id="name1" placeholder="方案名称" >
						</div>
				
						<button class="btn green" type="submmit">
									<i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
								</button>
								<button class="btn default hidden-inline-xs" type="reset">
									<i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
								</button>
						</div>
					</form>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<table class="grid-channel-biz-tmpopenlimit-biz-tmp-open-limit-index"></table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${base}/tmpopenlimit/biz-tmp-open-limit-index.js" />
<%@ include file="/common/ajax-footer.jsp"%>
