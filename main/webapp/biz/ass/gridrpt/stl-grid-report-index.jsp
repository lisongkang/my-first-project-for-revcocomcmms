<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-primary">
	<ul class="nav nav-pills">
		<li class="active"><a class="tab-default" data-toggle="tab" href="#tab-auto">列表查询</a></li>
		<li class="tools pull-right"  style="opacity:0"><a class="btn default reload" href="javascript:;"><i class="fa fa-refresh"></i></a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane fade active in">
			<div class="row search-form-default">
				<div class="col-md-12">
					<form action="#" method="get" class="form-inline form-validation form-search-init form-biz-ass-gridrpt-stl-grid-report-index"
						data-grid-search=".grid-biz-ass-gridrpt-stl-grid-report-index">
						<div class="input-group">
							
                            <div class="form-group">
                                <s:select id="id_first_gridid" name="monprogressParamBO.firstGridid" cssClass="form-control input-medium" list="firstGrididMap"
                                  placeholder="请选择网格..."/>
                            </div>
            
	                        <div class="form-group">
							    <input type="text" name="_startMonth" id="_startMonth"
								   placeholder="查询起始月">
						    </div>
						     ~
						    <div class="form-group">
							    <input type="text" name="_endMonth" id="_endMonth"
								   placeholder="查询结束月">
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
					<table class="grid-biz-ass-gridrpt-stl-grid-report-index"></table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${base}/biz/ass/gridrpt/stl-grid-report-index.js" />
<%@ include file="/common/ajax-footer.jsp"%>
