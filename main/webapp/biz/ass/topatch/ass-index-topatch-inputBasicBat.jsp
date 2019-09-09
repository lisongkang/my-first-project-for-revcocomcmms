<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-ass-topatch-ass-index-topatch-inputBasicBat"
	action="${base}/biz/ass/topatch/ass-index-topatch!doSaveBat" method="post">
	<s:hidden name="id" />
	<s:hidden name="version" />
	
	<s:token />
	<div class="form-body">
        
	    <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">所属网格</label>
					<div class="controls">
		                 <select id="tgridid" name="tgridid" onchange="AssIndexTopatchInputBasicBat.changeGrid();" />
					</div>
				</div>
            </div>
            
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">考核内容</label>
					<div class="controls">
		                <%-- <input type="text" id="asscontent" name="asscontent" value="<s:property value="model.asscontent"/>" readonly="readonly">
						<input type="hidden" id="assid" name="assid" value="<s:property value="model.id"/>">
						 --%>
						<select id="assid" name="assid"  />
					
					</div>
				</div>
            </div>            
        </div>
	
		<div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">完成目标方式</label>
					<div class="controls">
		                <select id="mode" name="mode" disabled="disabled"/>
					</div>
				</div>
            </div>
            
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">考核起始日期</label>
					<div class="controls">
		                <s3:datetextfield name="bdate" format="date"/>  
					</div>
				</div>
            </div>
        </div>
        
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">文件导入</label>
					<div class="controls" id="fileDiv">
						<s3:files name="batfiles" id="batfiles" listUrlPrefix="/biz/ass/topatch/ass-index-topatch!attachmentList" listUrlId="%{model.id}" />
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<label class="control-label" style="position: absolute;left: 10px;color: red;width: 350px;margin-left: 0;">文件格式：片区代码 任务数（注意空格，可多行）</label>
			</div>
		</div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="button" name="btn_topatchBat">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel"  id="btn_cancel" type="button">取消</button>
	</div>
</form>
<script src="${base}/biz/ass/topatch/ass-index-topatch-inputBasicBat.js" />
<%@ include file="/common/ajax-footer.jsp"%>