<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="div-biz-sta-marketing-result-order-list">
	<div class="row search-form-default">
		<div class="col-md-12">
			<form action="#" method="get"
				class="form-inline form-validation form-biz-sta-marketing-result-order-list"
				data-grid-search=".grid-biz-sta-marketing-result-order-list">
				<div class="form-group">
					<s:hidden id="backupAreaId" />
					<s:select id="areaid" name="staMarketResultParamBo.areaids"
						cssClass="form-control input-medium" list="{}" multiple="true"
						placeholder="请选择业务区..." />
				</div>
				<div class="form-group">
					<s:select id="depart" name="staMarketResultParamBo.departs"
						cssClass="form-control input-medium" list="{}" multiple="true"
						placeholder="请选择操作部门..." />
				</div>
				<div class="form-group">
					<s:select id="operator" name="staMarketResultParamBo.operators"
						cssClass="form-control input-medium" list="{}" multiple="true"
						placeholder=" 请输入操作员..." />
				</div>
				<div class="form-group">
					<s:textfield id="customer" name="staMarketResultParamBo.customer"
						cssClass="form-control input-medium" placeholder="请输入客户名称..."
						maxlength="20" />
				</div>
				<div class="form-group">
					<s:select id="orderStatus"
						name="staMarketResultParamBo.orderStatus"
						cssClass="form-control input-medium" list="{}"
						placeholder="请选择订单状态..." />
				</div>
			<%--	<div class="form-group">
					<s:select id="payStatus"
							  name="staMarketResultParamBo.payStatuss"
							  cssClass="form-control input-medium" list="{}" multiple="true"
							  placeholder="请选择支付状态..." />
				</div>--%>
				<%-- <div class="form-group">
						<s:textfield id="bossSerialNo"
							name="staMarketResultParamBo.bossSerialNo"
							cssClass="form-control input-medium" placeholder="请输入BOSS流水号..."
							maxlength="20" />
					</div> 暂时取消该查询条件，这个字段数据库中全都为空--%>
				<div class="form-group">
					<s:select id="opCode" name="staMarketResultParamBo.opCode"
						cssClass="form-control input-medium" list="{}"
						placeholder="请选择业务..." />
				</div>
				<div class="form-group" id="timeRangeDiv">
					<input type="text" id="timeRange"
						name="staMarketResultParamBo.timeRange"
						class="form-control input-medium input-daterangepicker"
						placeholder="统计时段">
				</div>

				<div class="form-group">
					<s:textfield id="custid" name="staMarketResultParamBo.custid"
								 cssClass="form-control input-medium" placeholder="请输入客户编号..."
								 maxlength="20" />
				</div>
				<div class="form-group">
					<s:select id="ifFilterRegression"
							  name="staMarketResultParamBo.ifFilterRegression"
							  cssClass="form-control input-medium" list="{}"
							  placeholder="默认已排除回退操作记录..."/>
				</div>
				<%--<div class="form-group">
					<s:select id="ifBusinessRollback"
							  name="staMarketResultParamBo.ifBusinessRollback"
							  cssClass="form-control input-medium" list="{}" multiple="true"
							  placeholder="请选择是否业务回退..." />
				</div> 和订单状态是一样的 屏蔽 防止查询乱--%>

				<button class="btn green" type="submmit"
					onclick="return StaMarketOrderList.searchFormObj.checkInput();">
					<i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
				</button>
				<button class="btn default hidden-inline-xs" type="button"
					onclick="StaMarketOrderList.searchFormObj.reset();">
					<i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
				</button>
			</form>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<table class="grid-biz-sta-marketing-result-order-list"></table>
		</div>
	</div>
</div>
<script
	src="${base}/biz/sta/marketingresult/sta-marketingresult-order-list.js" />
<%@ include file="/common/ajax-footer.jsp"%>