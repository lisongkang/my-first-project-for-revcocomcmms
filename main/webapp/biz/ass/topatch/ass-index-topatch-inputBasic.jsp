<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-ass-topatch-ass-index-topatch-inputBasic"
	action="${base}/biz/ass/topatch/ass-index-topatch!doSave" method="post">
	<s:hidden name="id" />
	<s:hidden name="version" />
	<input type="hidden" id="city" name="city" value="<s:property value="model.city"/>">
	<input type="hidden" id="grids" name="grids" value="<s:property value="model.grids"/>">
	
	<s:token />
	<div class="form-body">
        
	    <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">所属网格</label>
					<div class="controls">
		                 <select id="tgridid" name="tgridid" placeholder="请选择网格" onchange="AssIndexTopatchInputBasic.changeGrid();" />
		             </div>
				</div>
            </div>
            
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">考核内容</label>
					<div class="controls">
		                <input type="text" id="asscontent" name="asscontent" value="<s:property value="model.asscontent"/>" readonly="readonly">
						<input type="hidden" id="assid" name="assid" value="<s:property value="model.id"/>">
					</div>
				</div>
            </div>            
        </div>
	
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">片区</label>
					<div class="controls">
		                <!-- <select id="patchid" name="patchid" /> -->
		                <s:select id="patchid" name="patchid" list="{}"  multiple="true" placeholder="请选择片区"/>
					     
					</div>
				</div>
            </div>
            
             <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">接受任务数</label>
					<div class="controls">
		                <s:textfield name="revnum" />
					</div>
				</div>
            </div>
            
        </div>	
        
		<div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">完成目标方式</label>
					<div class="controls">
		                <select id="mode" name="mode" onchange="AssIndexTopatchInputBasic.changeMode();"/>
					</div>
				</div>
            </div>
            
            <div class="col-md-6">
				<div class="form-group" id="sameAssnum" style="display:block">
					<label class="control-label">每期目标数</label>
					<div class="controls">
		                <s:textfield name="assnum" id="assnum" />
					</div>
				</div>
				
				<div class="form-group" id="diffAssnum" style="display:none">
					<label class="control-label">分别指定每期目标数</label>
					<div class="controls">
		                <s:textfield name="serialno2" onclick="AssIndexTopatchInputBasic.clickDiffAssnum();" readonly="true" placeholder="点击指定每期目标数"/>
		                <input type="hidden" id=serialno name="serialno" >
					</div>
				</div>
            </div>
            
        </div>
        
        
       <!--  <div class="row" id="list_diffAssnum" style="display:none">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">已添加列表</label>
					<table width="100%" class="c-table" id="prds_table">
						<thead><tr>	
							<th width="25%">期数</th>
							<th width="25%">目标数</th>
						</tr></thead>
						<tbody id="prds_tbody">
						</tbody>
					</table>
				</div>
            </div>
        </div> -->
        
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">考核起始日期</label>
					<div class="controls">
		                <s3:datetextfield name="bdate" format="date"/>  
					</div>
				</div>
            </div>
        </div>
        
       <%--  <div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label">关联附件</label>
					<div class="controls">
						<s3:files listUrlPrefix="/common/pubpost/pub-post!attachmentList" listUrlId="%{model.id}" />
					</div>
				</div>
			</div>
		</div> --%>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="button" name="btn_topatch" data-grid-reload=".grid-biz-ass-topatch-ass-index-topatch-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel"  id="btn_cancel" type="button">取消</button>
	</div>
</form>
<script src="${base}/biz/ass/topatch/ass-index-topatch-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>