<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<ul class="nav nav-pills">
	<li class="active"><a class="tab-default" data-toggle="tab" href="#tab-auto">列表查询</a></li>
	<li class="tools pull-right" style="opacity:0"><a class="btn default reload" href="javascript:;"><i class="fa fa-refresh"></i></a></li>
</ul>

<div class="tab-content">
	<div class="tab-pane fade active in">
		<div class="row search-form-default">
			<div class="col-md-12">
			    <form action="#" method="get" class="form-inline form-validation form-search-init form-biz-cmp-task-index"
						data-grid-search=".grid-biz-cmp-task-index">
						<div class="input-group">
						    <div class="form-group">
								
								<s:select id="id_city" name="queActivityListReq.city" cssStyle="width:250px"
									 list="{}"
									placeholder="请选择地市" />
								<s:hidden id="backupCityId" />
							</div>
							<div class="form-group">
								<s:select id="id_gridcode" name="queActivityListReq.gridCode" cssStyle="width:480px"
									 list="{}"
									placeholder="请选择网格" multiple="true"/>
							</div>
							
							
							<span class="input-group-btn">
								<button class="btn green" id="btn_search" type="submmit">
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
					<table class="grid-biz-cmp-task-index"></table>
				</div>
	    </div>
   </div>
</div>
				
<script src="${base}/biz/manage/cmp/task/cmp-task-index.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>
