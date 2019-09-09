<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="container-fluid data-view">
	<div class="well form-horizontal">
	<s:if test="%{#request.order!=null}">
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">订单编码:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:property value="#request.order.ordercode" />
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">同步方式:</label>
					<div class="controls">
						<p class="form-control-static">
							<data:translate gcode="BIZ_CUSTORDER_SYNCMODE" mcode="${order.syncmode}"/>
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">订单状态:</label>
					<div class="controls">
						<p class="form-control-static">
							<data:translate gcode="BIZ_CUSTORDER_ORDERSTATUS" mcode="${order.orderstatus}"/>
						</p>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">客户id:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:property value="#request.order.custid" />
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">客户姓名:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:property value="#request.order.name" />
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">业务操作:</label>
					<div class="controls">
						<p class="form-control-static">
							<data:translate gcode="BIZ_OPCODE" mcode="${order.opcode}"/>
						</p>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">操作时间:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:date name="#request.order.optime" format="yyyy-MM-dd HH:mm:ss" />
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">操作员编号:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:property value="#request.order.operator" />
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">操作员部门:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:property value="#request.order.oprdep" />
						</p>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">业务区:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:property value="#request.order.areaid" />
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">网格编号:</label>
					<div class="controls">
						<p class="form-control-static">
							<data:translateProperty className="Grid" propertyName="gridname" conditionProperty="gridid" conditionValue="${order.gridid}" />
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">业务说明:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:property value="#request.order.descrip" />
						</p>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">城市:</label>
					<div class="controls">
						<p class="form-control-static">
							<data:translate gcode="PRV_CITY" mcode="${order.city}"/>
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-8">
				<div class="form-group">
					<label class="control-label">地址:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:property value="#request.order.addr" />
						</p>
					</div>
				</div>
			</div>
		</div>
	</s:if>
	</div>
</div>