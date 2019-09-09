<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-explication-config" action="${base}/biz/salary/explication-config!doSave"
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
					<s:select name="areaid" id="id_areaid" value="extraAttributes.areaids"  list="areaMap" multiple="true"  required="true" />
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
        <div class="col-md-8">
			  <div class="form-group">   
			    <label class="control-label">内容详情</label>
			    <div class="controls">
			   	    <s:textarea id="id_context" name="context" rows="3"  maxlength="200"  placeholder="可在此编辑内容详情" class="form-control"  required="true"/>
		        </div>
		      </div>
		</div>
	</div>

			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label">生效时间</label>
						<div class="controls">
							<s3:datetextfield name="stime" format="yyyy-MM-dd HH:mm" data-timepicker="true" required="true" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label">到期时间</label>
						<div class="controls">
							<s3:datetextfield name="etime" format="yyyy-MM-dd HH:mm" data-timepicker="true" required="true"/>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" name='btn_submitExpConfig' data-grid-reload=".grid-biz-salary-explication-config-list">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>

<script type="text/javascript">
</script>
<script src="${base}/biz/salary/explication-config-inputBasic.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>