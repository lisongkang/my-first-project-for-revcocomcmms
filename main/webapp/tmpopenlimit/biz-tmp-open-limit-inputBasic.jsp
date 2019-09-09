<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-channel-biz-tmpopenlimit-biz-tmp-open-limit-inputBasic"
	action="${base}/tmpopenlimit/biz-tmp-open-limit!doSave" method="post" 
	data-editrulesurl="${base}/tmpopenlimit/biz-tmp-open-limit!buildValidateRules">
	<s:hidden id = "id" name="id" />
	<s:hidden id = "city" name = "city" />
	<s:hidden id = "planId" name= "planId" />
	<s:hidden id = "createOper" name ="createOper" />
	
	<s:hidden id = "updateOper" name ="updateOper"  />
	
	<s:hidden id = "objId" name ="objId" />
	<s:token />
	
	<div class="form-body">
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">限制方式</label>
					<div class="controls">
		               	<s:select name="timeType" id="timeType" list="#{'0':'按月','1':'按天'}" /> 		                
					</div>
				</div>
            </div>
        </div>
        
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">限制对象类型</label>
					<div class="controls">
		               <s:select name="objType" id= "objType" list="#{'0':'部门','1':'工号'}" />
					</div>
				</div>
            </div>
        </div>
        
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">限制对象</label>
					<div class="controls">
		                  <s:textfield type="text" id="objName" name="objName" placeholder="点击选择对象..."
                            onclick="TmpOpenLimitInputBasic.selectDepartmentTabs('.form-channel-biz-tmpopenlimit-biz-tmp-open-limit-inputBasic');" readonly="readonly" onfocus="this.blur()"/>
					</div>
				</div>
            </div>
        </div>
        
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">限制次数</label>
					<div class="controls">
		                <s:textfield id="limitNums" name="limitNums" maxlength="5"/>
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">授权名称</label>
					<div class="controls">
		                <s:textfield type="text" id="name" name="name" placeholder="点击选择授权名称..."
                            onclick="TmpOpenLimitInputBasic.selectOpenTabs('.form-channel-biz-tmpopenlimit-biz-tmp-open-limit-inputBasic');" readonly="readonly" onfocus="this.blur()"/>
					</div>
				</div>
            </div>
        </div>
        
       <div id ="dates_div">
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">创建日期</label>
					<div class="controls">
		                <s3:datetextfield id = "createTime" name ="createTime"  format="yyyy-MM-dd HH:mm:ss" />
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">修改日期</label>
					<div class="controls">
		               <s3:datetextfield id = "updateTime" name ="updateTime"  format="yyyy-MM-dd HH:mm:ss"/>
					</div>
				</div>
            </div>
        </div>
       </div>
        
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit"  name="btn_tmpopenlimit" data-grid-reload=".grid-channel-biz-tmpopenlimit-biz-tmp-open-limit-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script src="${base}/tmpopenlimit/biz-tmp-open-limit-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>