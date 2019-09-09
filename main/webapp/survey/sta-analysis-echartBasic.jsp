<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/echartconfig.jsp"%> 

<form class="form-horizontal form-bordered form-label-stripped form-validation form-sta-analysis-echartBasic" action="" method="post" 
	data-editrulesurl="">
	<s:hidden name="sid" />
	<s:hidden name="version" />
	<s:token />
      <div class="row">
			<div class="col-md-12">
				 <div class="controls">
					<table width="99%" class="c-table">
						<tbody>
						<s:iterator value="model" status="status" var="question">
						   <tr>
							   <td width="45%" >
							      <s:set name="qid" value="#question.id"></s:set> 
							      <s:set name="isonly" value="#question.isonly"></s:set> 
						          <div id="chart_${qid}" name = "${isonly}" class="div_chart" style="width: 400px;height:300px;"></div>
							   </td>
							   <td>
							     <table width="99%" class="c-table">
							       <thead>
							          <tr>
							            <th style="text-align:left;" colspan="2"> 
							               <s:property value="#question.qno"/>、<s:property value="#question.qcontent"/>
							            </th>
						             </tr>
						            </thead>
						           <tbody>
								     <s:iterator value="#question.answerList" status="status" var="answer">
								        <tr>
							              <td width="10%">
							                <s:property value="#answer.acode"/>
							              </td>
							               <td  width="90%" style="text-align:left;">
							                <s:property value="#answer.acontent"/>
							              </td>
							              </tr>
								     </s:iterator>
								      </tbody>
								 </table>
							   </td>
						   </tr>
						</s:iterator>
						</tbody>
					</table>
				  </div>
				</div>
			</div>
		
		
</form>

 
<script type="text/javascript">
	var sid = '<s:property value="#request.sid" />';
</script>
 <script src="${base}/survey/sta-analysis-echartBasic.js"/>
 <%@ include file="/common/ajax-footer.jsp"%> 