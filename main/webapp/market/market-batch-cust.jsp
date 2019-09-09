<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-primary">
  <!--   <ul class="nav nav-pills">
		<li class="active"><a class="tab-default" data-toggle="tab" href="#tab-auto">列表查询</a></li>
		<li class="tools pull-right" style="opacity:0"><a class="btn default reload" href="javascript:;"><i class="fa fa-refresh"></i></a></li>
	</ul> -->
	<div class="tab-content">
		<div class="tab-pane fade active in">
			<div class="row search-form-default">
				<div class="col-md-12">
					<form action="#" method="get" class="form-inline form-validation form-search-init form-market-market-batch-cust"
						data-grid-search=".grid-market-market-batch-cust">
						
						<div class="form-group">
                            <s:hidden id="id_strGrids" name="strGrids" />
                        </div> 
						<div class="form-group">
						   <input type="text" id="knowname" name="knowname" style="width:442" value="<s:property value="model.knowname"/>" readonly="readonly">
						   <input type="hidden" id="knowid" name="knowid" value="<s:property value="model.knowid"/>">
					    </div>
					                            
						<div class="form-group">
						     <s:select id="patchid" name="patchid" cssClass="form-control input-large" list="{}" placeholder="请选择片区..."/>
						</div>
						<div class="form-group">
						     <s:select id="netattr" name="netattr" cssClass="form-control input-medium" list="#{'0':'单向','1':'双向'}" placeholder="请选择单双向属性..."/>
						</div>
				<%-- 		<div class="form-group">
							 <s:select id="custfilter" name="custfilter" cssClass="form-control input-medium" list="#{'0':'过滤','1':'不过滤'}" placeholder="是否过滤推送客户"/>
						</div> --%>
						<button class="btn green" type="submmit" onclick="MarketBatchCust.checkInput();">
							<i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
						</button>
						<button class="btn default hidden-inline-xs" type="reset">
							<i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
						</button>
						<button class="btn default hidden-inline-xs" id="selectCustBtn" onclick="MarketBatchCust.selectCusts();">
							<i class="fa fa-check"></i>&nbsp; 选中客户
						</button></form>

				</div>
			</div>

			<div class="row">
				<div class="col-md-12">
					<table class="grid-market-market-batch-cust"></table>
				</div>
			</div>
		</div>
	</div>
</div>			

<script src="${base}/market/market-batch-cust.js" />
<%@ include file="/common/ajax-footer.jsp"%>