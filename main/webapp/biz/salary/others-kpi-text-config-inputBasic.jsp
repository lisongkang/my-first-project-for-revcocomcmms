<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-others-kpi-text-config" action="${base}/biz/salary/others-kpi-text-config!doSave"
	method="post">
	<div class="form-body">
    <s:hidden name="id" />
    <%--<s:hidden name="adstatus" />--%>
     <%--<s:hidden name="optid" />--%>
    <div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<label class="control-label">分公司</label>
				<div class="controls">
	                <s:select name="city" id="id_city" list="cityMap" required="true" />
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<label class="control-label">支公司</label>
				<div class="controls">
					<s:select name="areaid" id="id_areaid"  value="extraAttributes.areaids"  list="areaMap"  required="true" />
				</div>
			</div>
		</div>
	</div>
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
			    <label class="control-label">项目名称</label>
			    <div class="controls">
			   	    <s:textfield id="id_context" name="context"   required="true"/>
		        </div>
		      </div>
		</div>
	</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">优先级</label>
					<div class="controls">
						<s:textfield id="id_context" name="rank"  class="number form-control"  required="true" maxlength="5" />
					</div>
				</div>
			</div>
		</div>
		<div class="row" id="div_show" style="display: none">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">是否展示</label>
					<div class="controls">
						<s:select id="id_showInTemplate" name="showInTemplate"  list="showMap"  />
					</div>
				</div>
			</div>
		</div>


	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" name='btn_submitOthConfig' data-grid-reload=".grid-biz-salary-others-kpi-text-config-list">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>

<script type="text/javascript">
</script>
<script src="${base}/biz/salary/others-kpi-text-config-inputBasic.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>