<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="row">
	<div class="col-md-12">
		<div class="table-responsive">
			<table id="table-orderprds"
				class="table table-striped table-bordered table-hover">
				<tbody>
					<s:iterator value="model" status="status" var="know">
						<tr onclick="AssIndexStoreInputBasic.onSelectRow(this);" id='<s:property value="#status.index+1"/>'>
							<td hidden="true" name="knowid"><s:property value="#know.knowid" /></td>
							<td name="knowname"><s:property value="#know.knowname" /></td>
							<td name="price">￥<s:property value="#know.price" /></td>
							<td hidden="true" name="objid"><s:property value="#know.objid" /></td>
							<td hidden="true" name="objtype"><s:property value="#know.objtype" /></td>
							<td name="objtypeshow"><s:if test="#know.objtype==0">产品</s:if>
								<s:elseif test="#know.objtype==1">营销方案</s:elseif></td>
							<td name="tocust"><s:property value="#know.tocust" /></td>
							<td name="feeexp"><s:property value="#know.feeexp" /></td>
							
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
	</div>
</div>
<script src="${base}/biz/ass/store/ass-index-store-orderPrds.js" />
<%@ include file="/common/ajax-footer.jsp"%>