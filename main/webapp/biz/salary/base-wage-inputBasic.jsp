<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-base-wage" action="${base}/biz/salary/base-wage!doSave"
	method="post">
	<div class="form-body">
    <s:hidden name="id" />
    <s:hidden name="achievementId" />
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
					<s:select name="areaid" id="id_areaid" value="areaid"  list="areaMap2" required="true" />
				</div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<label class="control-label">网格</label>
				<div class="controls">
					<s:hidden name="grid" id="hid_grid" disabled="true" />
					<s:select name="grid" id="id_grid"  list="{}"  required="true" />
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<label class="control-label">网格人员</label>
				<div class="controls">
					<s:hidden name="operid" id="hid_operid" disabled="true" />
					<s:select name="operid" id="id_operid" list="{}"  required="true" />
				</div>
			</div>
		</div>
	</div>

	<div class="row ">
		<div class="col-md-6">
			<div class="form-group">
				<label class="control-label">薪酬类型</label>
				<div class="controls">
					<s:select id="id_type" name="type" list="#{1:'基本薪酬',2:'运维薪酬'}"  required="true"></s:select>
				</div>
			</div>
		</div>
	</div>
	<div id="div_base_wage" style="display: none;">
		<div class="row">
			<div class="col-md-6">
				  <div class="form-group">
					<label class="control-label">岗位</label>
					<div class="controls">
						<s:textfield  name="position"  placeholder="可在此编辑内容详情" class="form-control"   />
					</div>
				  </div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				  <div class="form-group">
					<label class="control-label">职级</label>
					<div class="controls">
						<s:textfield  name="positionLevel"  placeholder="可在此编辑内容详情" class="form-control"  />
					</div>
				  </div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">档次</label>
					<div class="controls">
						<s:textfield  name="level"  placeholder="可在此编辑内容详情" class="form-control" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">薪酬</label>
					<div class="controls">
						<s:textfield  name="amount"  placeholder="可在此编辑内容详情" class="number form-control" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">生效时间</label>
					<div class="controls">
						<s3:datetextfield name="stime" format="yyyy-MM-dd HH:mm" data-timepicker="true" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">到期时间</label>
					<div class="controls">
						<s3:datetextfield name="etime" format="yyyy-MM-dd HH:mm" data-timepicker="true" />
					</div>
				</div>
			</div>
		</div>
	</div>
		<div id="div_achievement" style="display: none;">
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label">月份</label>
						<div class="controls">
							<s3:datetextfield id="id_dateMonth" name="dateMonth" format="yyyy-MM-dd HH:mm:00" data-timepicker="true" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label">运维薪酬</label>
						<div class="controls">
							<s:textfield  name="achievementAmount"  placeholder="可在此编辑内容详情" class="number form-control" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" name='btn_submitBaseWageConfig' data-grid-reload=".grid-biz-salary-base-wage-list">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>

<script type="text/javascript">
</script>
<script src="${base}/biz/salary/base-wage-inputBasic.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>