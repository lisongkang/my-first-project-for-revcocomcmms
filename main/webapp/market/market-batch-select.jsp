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
					<form action="#" method="get" class="form-inline form-validation form-search-init form-market-market-batch-select"
						data-grid-search=".grid-biz-market-market-batch-select">
						<div class="input-group">
							<div class="form-group">
                                 <input type="hidden" id="mbid" name="marketBatchParamBO.mbid" class="form-control input-medium" placeholder="">
                            </div> 
                            
                            <div class="form-group">
                                 <label class="control-label">启用状态</label>
					             <div class="controls">
						              <s:radio name="selecttype" list="#{'0':'全部客户营销任务','1':'分别选择营销任务'}"  value="0"/>
						              <span class="help-block"></span>
					             </div>
                            </div>
						        
						    <button class="btn default hidden-inline-xs" id="selectCustBtn" onclick="MarketBatchSelect.selectMarketBatchs();">
							     <i class="fa fa-check"></i>&nbsp; 选择任务
						    </button>
						</div>
					</form>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<table class="grid-biz-market-market-batch-select"></table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${base}/market/market-batch-select.js" />
<%@ include file="/common/ajax-footer.jsp"%>
