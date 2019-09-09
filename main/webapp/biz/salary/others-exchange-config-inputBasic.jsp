<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-others-exchange-config" action="${base}/biz/salary/others-exchange-config!doSave"
	method="post">
	<div class="form-body">
    <s:hidden name="id" />
    <s:hidden name="status" />
    <div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<label class="control-label">分公司</label>
				<div class="controls">
	                <s:select name="city" id="id_city" list="cityMap"  required="true" />
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<label class="control-label">支公司</label>
				<div class="controls">
					<s:select name="areaid" id="id_areaid" value="extraAttributes.areaids"  list="areaMap"  required="true" />
				</div>
			</div>
		</div>
	</div>
	<%--<div class="row">--%>
		<%--<div class="col-md-6">--%>
			<%--<div class="form-group">--%>
				<%--<label class="control-label">网格</label>--%>
				<%--<div class="controls">--%>
					<%--<s:hidden name="grid" id="hid_grid" disabled="true" />--%>
					<%--<s:select name="grid" id="id_grid"  list="{}"    />--%>
				<%--</div>--%>
			<%--</div>--%>
		<%--</div>--%>
	<%--</div>--%>
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<label class="control-label">类型</label>
				<div class="controls">
					<s:select name="type"  list="typeMap"  required="true" />
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<label class="control-label">生效年月</label>
				<div class="controls">
					<s3:datetextfield  name="sdateMonth" format="yyyy-MM-dd HH:mm:00" data-timepicker="true" />
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<label class="control-label">截至年月</label>
				<div class="controls">
					<s3:datetextfield  name="edateMonth" format="yyyy-MM-dd HH:mm:00" data-timepicker="true" />
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<label class="control-label">起始控制线</label>
				<div class="input-group controls">
					<s:textfield name="scontrol" class="form-control" />
					<span class="input-group-addon">%</span>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<label class="control-label">截至控制线</label>
				<div class="input-group controls">
					<s:textfield name="econtrol" class="form-control" />
					<span class="input-group-addon">%</span>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<label class="control-label">最低保有率</label>
				<div class="input-group controls">
					<s:textfield name="minCentsPrice" class="form-control"  required="true" />
					<span class="input-group-addon">%</span>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<label class="control-label">最高保有率</label>
				<div class="input-group controls">
					<s:textfield name="maxCentsPrice" class="form-control"  required="true" />
					<span class="input-group-addon">%</span>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<label class="control-label">计算公式类型</label>
				<div class="controls">
					<s:select name="formulaType"  list="formulaTypeMap"  required="true" />
				</div>
			</div>
		</div>
	</div>

    <%--<div class="row">--%>
        <%--<div class="col-md-6">--%>
			  <%--<div class="form-group">   --%>
			    <%--<label class="control-label">用户数</label>--%>
			    <%--<div class="controls">--%>
					<%--<s:textfield name="userNum"  class="number form-control"  required="true"/>--%>
		        <%--</div>--%>
		      <%--</div>--%>
		<%--</div>--%>
	<%--</div>--%>
	<%--<div class="row">--%>
		<%--<div class="col-md-6">--%>
			<%--<div class="form-group">--%>
				<%--<label class="control-label">保有系数</label>--%>
				<%--<div class="controls">--%>
					<%--<s:textfield name="coefficient" class="double form-control"  required="true"/>--%>
				<%--</div>--%>
			<%--</div>--%>
		<%--</div>--%>
	<%--</div>--%>

	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" name='btn_submitOecConfig' data-grid-reload=".grid-biz-salary-others-exchange-config-list">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>

<script type="text/javascript">
</script>
<script src="${base}/biz/salary/others-exchange-config-inputBasic.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>