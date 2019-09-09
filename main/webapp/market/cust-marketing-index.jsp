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
					<form action="#" method="get" class="form-inline form-validation form-search-init form-market-cust-marketing-index"
						data-grid-search=".grid-biz-market-cust-marketing-index">
						<div class="input-group">
							<!-- <div class="input-cont">
								<input type="text" name="search['CN_code']" class="form-control" placeholder="代码...">
							</div> -->
							
							<div class="form-group">
							    <input type="text" id="id_knowname" name="marketBatchParamBO.knowname" value="<s:property value="model.knowname"/>" class="form-control input-medium" readonly="true">
                                <input type="hidden" id="id_knowid" name="marketBatchParamBO.knowid" value="<s:property value="model.knowid"/>" class="form-control input-medium">
                            </div>  
                            
                            <div class="form-group">
                                <input type="text" id="id_deptname" name="marketBatchParamBO.deptname" value="<s:property value="model.deptname"/>" class="form-control input-medium" readonly="true">
                                <input type="hidden" id="id_deptid" name="marketBatchParamBO.deptid" value="<s:property value="model.deptid"/>" class="form-control input-medium">
                            </div>
							
							<div class="form-group">
                                <s:select id="id_queOperids" name="marketBatchParamBO.queOperids" cssClass="form-control input-medium" list="{}"  multiple="true"
                                     placeholder="请选择推送网格人员..."/>
                            </div>    
                            
                            <div class="form-group">
                                <input type="text" id="id_custname" name="marketBatchParamBO.custname" class="form-control input-medium" placeholder="请输入客户名称...">
                            </div> 
			                <div class="form-group">
                                <s:select id="id_dealstatus" name="marketBatchParamBO.dealstatus" cssClass="form-control input-medium" list="#{'0':'初始','1':'已接单','2':'处理中','3':'结束'}"
                                     placeholder="请选择处理状态..."/>
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
					<table class="grid-biz-market-cust-marketing-index"></table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${base}/market/cust-marketing-index.js" />
<%@ include file="/common/ajax-footer.jsp"%>
