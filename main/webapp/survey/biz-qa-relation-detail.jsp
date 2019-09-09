<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-qa-relation-detailBasic" action="" method="post" 
	data-editrulesurl="">
	<s:hidden name="id" />
	<s:hidden name="version" />
	<s:token />
     <div class="row">
			<div class="col-md-12">
				<div class="form-group">
				 <label class="control-label">选项详情</label>
				 <div class="controls">
					<table width="99%" class="c-table">
						<thead><tr>
							<th width="20%">选项</th>
							<th width="80%" >内容</th>
						</tr></thead>
						<tbody>
						<s:iterator value="model" status="status" var="answer">
						   <tr>
							   <td><s:property value="#answer.acode" /></td>
							   <td><s:property value="#answer.acontent"/></td>
						   </tr>
						</s:iterator>
						</tbody>
					</table>
				  </div>
				</div>
			</div>
		</div>
		
		<div class="form-actions center">
		   <button class="btn blue default" type="button" onclick = "BizQaRelationDetailBasic.doClose()">返回</button>
	    </div>
</form>
<script src="${base}/survey/biz-qa-relation-detail.js" />
<%@ include file="/common/ajax-footer.jsp"%>