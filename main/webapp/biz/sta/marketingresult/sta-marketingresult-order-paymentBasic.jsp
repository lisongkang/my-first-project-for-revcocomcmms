<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-custorder-cust-order-paymentBasic"
	action="" method="post" 
	data-editrulesurl="">
	<s:hidden name="id" />
	<s:hidden name="version" />
	<s:token />
	    <div class="row">
			<div class="col-md-12">
				<div class="form-group">
				 <label class="control-label">基础信息:</label>
				 <div class="controls">
					<table width="99%" class="c-table">
						<thead><tr>
							<th width="15%">业务操作</th>
							<th width="20%">操作时间</th>
							<th width="15%">订单状态</th>
							<th width="15%">操作员</th>
							<th width="15%">操作员部门</th>
							<th width="10%">业务区</th>
						</tr></thead>
						<tbody>
						<tr>
						<td><s:property value="model.opcodename" /></td>
						<td><s:property value="model.optime" /></td>
						<td><s:property value="model.orderstatusname" /></td>
						<td><s:property value="model.operatorname" /></td>
						<td><s:property value="model.oprdepname" /></td>
						<td><s:property value="model.areaname" /></td>
						</tr>
						</tbody>
					</table>
				  </div>
				</div>
			</div>
		</div>
		<!--E  基础信息-->
		<!--S  客户信息-->
	    <div class="row">
			<div class="col-md-12">
				<div class="form-group">
				 <label class="control-label">客户信息:</label>
				 <div class="controls" >
					<table width="99%" class="cus-table">
						<tbody>
						<tr><td class="fl c6">客户姓名:</td><td class="fl"><s:property value="model.custname" /></td></tr>
						<tr><td class="fl c6">客户地址:</td><td class="fl"><s:property value="model.addr" /></td></tr>
						<tr><td class="fl c6">缴费设备编号：</td><td class="fl"><s:property value="model.keyno" /></td></tr>
						<tr><td class="fl c6">缴费金额:</td><td class="fl price"><s:property value="model.owefees" /></td></tr>
						<tr><td class="fl c6">缴费时间:</td><td class="fl"><s:property value="model.optime" /></td></tr>
						<tr><td class="fl c6">支付方式:</td><td class="fl" ><s:property value="model.paywayname" /></td></tr>
						<tr><td class="fl c6">接收验证码手机号:</td><td class="fl"><s:property value="model.verifyphone" /></td></tr>
						</tbody>
					</table>
				  </div>
				</div>
			</div>
		</div>

	<div class="form-actions center">
		<button class="btn blue default" type="button" onclick = "CustOrderPaymentBasic.doClose()">返回</button>
	</div>
	
</form>
<script src="${base}/biz/sta/marketingresult/sta-marketingresult-order-paymentBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>