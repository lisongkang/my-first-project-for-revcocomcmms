<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-ass-monstat-ass-index-monprogress-inputBasic"
	action="${base}/biz/ass/monstat/ass-index-monprogress!doSave" method="post" 
	data-editrulesurl="${base}/biz/ass/monstat/ass-index-monprogress!buildValidateRules">
	<s:hidden name="id" />
	<s:hidden name="version" />
	<s:token />
	<div class="form-actions">
		<button class="btn blue" type="submit" data-grid-reload=".grid-biz-ass-monstat-ass-index-monprogress-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
	<div class="form-body">
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">统计月份YYYYMM</label>
					<div class="controls">
		                <s:textfield name="tmonth" />
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">考核ID</label>
					<div class="controls">
		                <s:textfield name="assid" />
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">统计对象</label>
					<div class="controls">
		                <s:textfield name="objtype" />
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">对象ID</label>
					<div class="controls">
		                <s:textfield name="objid" />
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">任务总数</label>
					<div class="controls">
		                <s:textfield name="total" />
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">累计完成数</label>
					<div class="controls">
		                <s:textfield name="comnum" />
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">当月完成数</label>
					<div class="controls">
		                <s:textfield name="curnum" />
					</div>
				</div>
            </div>
        </div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" data-grid-reload=".grid-biz-ass-monstat-ass-index-monprogress-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script src="${base}/biz/ass/monstat/ass-index-monprogress-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>