<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-ass-index-topatch-assnum"
	action="#" method="post" 
	data-editrulesurl="">
	<input type="hidden" id="unit" name="unit" value="<s:property value="model.unit"/>">
	<input type="hidden" id="serialno" name="serialno" value="<s:property value="model.serialno"/>">
	
	<s:token />

	<div class="form-body control-label-sm s_item">
        
        <div class="row">
            <div class="col-md-4">
                <div class="form-group">
                    <label class="control-label">期数</label>
                    <div class="controls">
                        <select id="pno" name="pno" />
                    </div>
                </div>
            </div>
            
            <div class="col-md-4">
                <div class="form-group">
                    <label class="control-label">目标数</label>
                    <div class="controls">
                        <input type="text" id="assnum" name="assnum" >
                    </div>
                </div>
            </div>
            
            <div class="col-md-2">
				<div class="form-group">
					<label class="control-label"></label>
					<div class="controls">
					<button class="btn default" type="button" onclick = "AssIndexTopatchAssnum.addNewRow()">添加</button>
					</div>
				</div>
			</div>
        </div>
        
        <!-- <button class="btn default" type="button" onclick = "AssIndexTopatchAssnum.addRow()">添加</button>
         -->
        <div class="row">
            <div class="col-md-10">
				<div class="form-group">
					<label class="control-label">已添加列表</label>
					<div class="controls">
					<table width="100%" class="c-table" id="prds_table">
						<thead><tr>	
							<th width="40%">期数</th>
							<th width="40%">目标数</th>
							<th width="20%">操作</th>
						</tr></thead>
						<tbody id="prds_tbody">
						
						
						<s:iterator value="model.phaselist" status="status" var="phase">
						
						<tr id='pno<s:property value="#phase.pno"/>'>
							<td><s:property value="#phase.pnoname" /></td>
							<td><s:property value="#phase.assnum" /></td>
							<td><span class='btn-delete' onclick='AssIndexTopatchAssnum.delRow("<s:property value='#phase.pno'/>")'>删除</span></td>
						</tr>
					    </s:iterator>
					    
						
						</tbody>
					</table>
					</div>
				</div>
            </div>
            
            
        </div>
        
	</div>
	<div class="form-actions right">
	    <button class="btn blue" type="button" onclick = "AssIndexTopatchAssnum.doSubmit()">提交</button>
		<button class="btn default" type="button" onclick = "AssIndexTopatchAssnum.doClose()">返回</button>
	</div>
</form>
<script src="${base}/biz/ass/topatch/ass-index-topatch-assnum.js" />
<%@ include file="/common/ajax-footer.jsp"%>