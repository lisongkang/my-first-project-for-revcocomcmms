<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-base-wage-honus" action="${base}/biz/salary/base-wage-honus!doSave"
	method="post">
	<div class="form-body">
    <s:hidden name="id" />

		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">分公司</label>
					<div class="controls">
						<s:select name="city" id="id_city" list="cityMap" required="true"  />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">支公司</label>
					<div class="controls">
						<s:select name="areaid" id="id_areaid" value="areaid"  list="{}" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">登录名</label>
					<div class="controls">
						<s:textfield id="loginname" name="loginname" type="text"  class="form-control"  required="true" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">姓名</label>
					<div class="controls">
						<s:textfield id="name" name="name" type="text"  class="form-control"  required="true" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">生效年月</label>
					<div class="controls">
						<s3:datetextfield  id="sdateMonth" name="sdateMonth"  format="yyyy-MM-dd HH:mm:00" data-timepicker="true" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">失效年月</label>
					<div class="controls">
						<s3:datetextfield  id="edateMonth" name="edateMonth"  format="yyyy-MM-dd HH:mm:00" data-timepicker="true" />
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">基本工资:</label>
					<div class="controls">
						<s:textfield id="amount" name="amount" type="text"  class="form-control"  required="true" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">绩效奖金基数:</label>
					<div class="controls">
						<s:textfield id="honus" name="honus" type="text"  class="form-control"  required="true" />
					</div>
				</div>
			</div>
		</div>


	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" name='btn_submitBaseWageHonusConfig' data-grid-reload=".grid-biz-salary-base-wage-honus-list">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>

<script type="text/javascript">
</script>
<script src="${base}/biz/salary/base-wage-honus-inputBasic.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>