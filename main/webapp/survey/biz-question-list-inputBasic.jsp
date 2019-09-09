<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

 <form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-survey-biz-question-list-inputBasic"
	action="${base}/survey/biz-question-list!doSave" method="post" 
	data-editrulesurl="${base}/survey/biz-question-list!buildValidateRules">
	<s:hidden name="id" />
	<s:hidden name="version" />
	<s:token />
	<div class="form-body">
       <div class="row">
            <div class="col-md-12">
				<div class="form-group">
					<label class="control-label">题目内容</label>
					<div class="controls">
		                <s:textarea rows="3"  id="id_qcontent" name="qcontent" />
					</div>
				</div>
            </div>
        </div> 
      
       <div class="row">
           <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">是否开放式</label>	
					<div class="controls">
		                 <s:select id="id_isopen" name="isopen" required="true"  list="#{'Y':'是','N':'否' }"/>
                      
					</div>
				</div>
            </div> 
            
           <div class="col-md-6" id = "id_IsopenData" style="display:none">
				<div class="form-group">
					<label class="control-label">是否图片</label>
					<div class="controls">
		                <s:select id="id_ok" name="isok" required="true"  list="#{'Y':'是','N':'否' }"/>
					</div>
				</div>
            </div>
            
	         <div class="col-md-6" id="id_NopenData1" style="display:none">
					<div class="form-group">
						<label class="control-label">是否单选</label>
						<div class="controls">
			                <s:select id="id_isonly" name="isonly" list="#{'Y':'是','N':'否' }"/>
						</div>
					</div>
	        </div>
      </div>  
        
      <div id="id_NopenData" style="display:none">
            <div class="row">
	            <div class="col-md-3">
					<div class="form-group">
					      <label class="control-label">答案</label>
					     <div class="controls">
						    <s:select stlye="float" id="answerType" list="#{'A':'A','B':'B','C':'C','D':'D','E':'E','F':'F'}"/>
					     </div>
					</div>
	            </div>
	          <div class="col-md-6" >
					<div class="form-group">
			                <s:textfield name="qcontent" id="answerContent" placeholder="请输入内容" style="margin-top:5px"/>
			                
					</div>
	            </div>
	            <div class="col-md-1">
					<div class="form-group">
						   <button class="btn blue" type="button" name="" onclick="bizQuestionListInputBasic.addNewAnswer();" style="margin-top:2px">
			                                                               添加答案
		                   </button>
					</div>
	            </div>
           </div>
	  </div> 
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="button" onclick="bizQuestionListInputBasic.saveQuetionData();">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
	
</form>
<script src="${base}/survey/biz-question-list-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>