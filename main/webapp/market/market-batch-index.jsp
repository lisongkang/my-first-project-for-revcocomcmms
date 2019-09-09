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
					<form action="#" method="get" class="form-inline form-validation form-search-init form-market-market-batch-index"
						data-grid-search=".grid-biz-market-market-batch-index">
						<div class="input-group">
							<!-- <div class="input-cont">
								<input type="text" name="search['CN_code']" class="form-control" placeholder="代码...">
							</div> -->
							
                            <div class="form-group">
                                 <s:select id="id_city" name="marketBatchParamBO.city" cssClass="form-control input-medium" list="{}"
                                      placeholder="请选择分公司..."/>
                            </div>
                            <div class="form-group">
                                 <s:select id="id_market_batch" name="marketBatchParamBO.mbid" cssClass="form-control input-medium" list="marketBatchMap"
                                      placeholder="请选择营销任务..."/>
                            </div>
                            <div class="form-group">
                                 <s:select id="id_status" name="marketBatchParamBO.status" cssClass="form-control input-medium" list="statusMap"
                                      placeholder="请选择状态..."/>
                            </div>
                            <div class="form-group">
                                 <s:select id="id_dept" name="marketBatchParamBO.deptids" cssClass="form-control input-medium" list="{}"  multiple="true"
                                      placeholder="请选择录入部门..."/>
                            </div>                   
            
						    <div class="form-group" id="id_que_apptime_div">
                                 <input type="text" id="id_que_apptime" name="que_apptime" class="form-control input-medium input-daterangepicker"
                                    placeholder="录入日期">
                            </div> 
								<button class="btn green" type="submmit" id="searchBtn">
									<i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
								</button>
								<button class="btn default hidden-inline-xs" type="reset">
									<i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
								</button>
								<button class="btn blue hidden-inline-xs" id="deleteBtn" onclick="MarketBatchIndex.deleteMarketBatchs();">
							        <i class="fa fa-check"></i>&nbsp; 删除任务
						        </button>
						</div>
					</form>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<table class="grid-biz-market-market-batch-index"></table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${base}/market/market-batch-index.js" />
<%@ include file="/common/ajax-footer.jsp"%>
