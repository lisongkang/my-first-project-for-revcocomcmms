<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-primary">
	<!-- <ul class="nav nav-pills">
		<li class="active"><a class="tab-default" data-toggle="tab" href="#tab-auto">列表查询</a></li>
		<li class="tools pull-right"><a class="btn default reload" href="javascript:;"><i class="fa fa-refresh"></i></a></li>
	</ul> -->
	<div class="tab-content">
		<div class="tab-pane fade active in">
			 <div class="row search-form-default" style="width: 0px; height: 0px;">
				<div class="col-md-12">
					<form action="#" method="get" class="form-inline form-validation form-search-init form-biz-ass-topatch-ass-index-topatch-index"
						data-grid-search=".grid-biz-ass-topatch-ass-index-topatch-index">
						
						<input type="hidden" id="assid" name="assid" value="<s:property value="model.id"/>">
						
						<%-- <div class="input-group">
							<div class="input-cont">
								<input type="text" name="search['CN_patchid']" class="form-control" placeholder="片区代码...">
							</div>
							<span class="input-group-btn">
								<button class="btn green" type="submmit">
									<i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
								</button>
								<button class="btn default hidden-inline-xs" type="reset">
									<i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
								</button>
							</span>
						</div> --%>
					</form>
				</div>
			</div> 
			<div class="row">
				<div class="col-md-12">
					<table class="grid-biz-ass-topatch-ass-index-topatch-index"></table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${base}/biz/ass/topatch/ass-index-topatch-index.js" />
<%@ include file="/common/ajax-footer.jsp"%>
