<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="div-task-info-inputBasic">
<form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-task-info-inputBasic"
	action="${base}/task/biz-task-info!doSave" method="post">
	<s:hidden name="model.taskid" id="id_taskid"/>
	<s:hidden name="model.version" />
	<s:hidden name="model.type" id="id_old_type"/>
	<s:hidden name="model.cometype" id="id_old_cometype"/>
	<s:token />
	<div class="form-body">
        <div class="row">
            <div class="col-md-10">
				<div class="form-group">
		                <label class="control-label">任务标题</label>
						<div class="controls">
						     <s:textfield id="id_tasktitle" name="model.tasktitle" required="true" placeholder="请输入内容" />
						</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-5">
				<div class="form-group">
					<label class="control-label">来源分类</label>
					<div class="controls">
		                 <s:select id="id_cometype" name="model.cometype" required="true"  list="{}"
                      placeholder="来源分类"  />
					</div>
				</div>
            </div>
            <div class="col-md-5">
				<div class="form-group">
					<label class="control-label">客户编号</label>
					<div class="controls">
		                <s:textfield id="id_cusid" name="model.cusid"  placeholder="请输入客户编号" />
					</div>
				</div>
            </div>
        </div>
        
        <div class="row">
            <div class="col-md-5">
				<div class="form-group">
					<label class="control-label">任务类型</label>
					<div class="controls">
		                <s:select id="id_type" name="model.type" required="true"  list="{}"
                      placeholder="任务类型"  />
					</div>
				</div>
            </div>
            <div class="col-md-5">
				<div class="form-group">
					<label class="control-label">优先级</label>
					<div class="controls">
		                <s:textfield id="id_pri" name="model.pri" required="true" placeholder="请输入正数" />
					</div>
				</div>
            </div>
        </div>
        
        <div class="row">
            <div class="col-md-5">
				<div class="form-group">
					<label class="control-label">开始时间</label>
					<div class="controls">
		                <s3:datetextfield name="model.stime"  id="id_stime" format="date"/>      
					</div>
				</div>
            </div>
            <div class="col-md-5">
				<div class="form-group">
					<label class="control-label">截止时间</label>
					<div class="controls">
		                <s3:datetextfield name="model.ctime"  id="id_ctime" format="date"/>  
					</div>
				</div>
            </div>
        </div>
        
         <div class="row">
            <div class="col-md-10">
				<div class="form-group">
					<label class="control-label">任务内容</label>
					<div class="controls">
		                <s:textfield id="id_taskdesc" name="model.taskdesc" required="true" placeholder="请输入内容" />
					</div>
				</div>
            </div>
        </div>
	</div>
	
	<div class="form-actions right">
		<button class="btn blue btn_taskinfonew" type="button">
			<i class="fa fa-check"></i> 确定
		</button>
		<button class="btn default btn-cancel1" type="button">取消</button>
	</div>
</form>

</div>
<script src="${base}/task/biz-task-info-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>