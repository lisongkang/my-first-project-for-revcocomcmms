<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-ass-index-topatch-assnumlist"
	action="#" method="post" 
	data-editrulesurl="">
	<s:token />

	<div class="form-body control-label-sm s_item">
        
        <div class="row">
            <div class="col-md-10">
				<div class="form-group">
					<label class="control-label">每期数列表</label>
					<div class="controls">
					<table width="100%" class="c-table" id="prds_table">
						<thead><tr>	
							<th width="50%">期数</th>
							<th width="50%">目标数</th>
						</tr></thead>
						<tbody id="prds_tbody">
						
						<s:iterator value="model" status="status" var="phase">
						<tr >
							<td><s:property value="#phase.pnoname" /></td>
							<td><s:property value="#phase.assnum" /></td>
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
			<button class="btn default" type="button" onclick = "AssIndexTopatchAssnumlist.doClose()">返回</button>
	</div>
</form>
<script src="${base}/biz/ass/topatch/ass-index-topatch-assnumlist.js" />
<%@ include file="/common/ajax-footer.jsp"%>