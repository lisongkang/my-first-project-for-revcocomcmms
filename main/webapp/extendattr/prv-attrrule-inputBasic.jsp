<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-extendattr-prv-attrrule-inputBasic"
	action="${base}/extendattr/prv-attrrule!doSave" method="post" 
	data-editrulesurl="${base}/extendattr/prv-attrrule!buildValidateRules">
	<s:hidden name="id" />
	<s:hidden name="version" />
	<s:token />
	<div class="form-body">
	   <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">适用地市</label>
					<div class="controls">
		               <s:select id="id_city" name="city"   list="extendsCityMap"  placeholder="请选择地市" multiple="true"  required="true"/>
					</div>
				</div>
            </div>
        </div>
         <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">属性名称</label>
					<div class="controls">
		                <s:textfield name="attrname" id="id_attrname" placeholder="请用中文命名 ，例如 ：城市" required="true"/>
					</div>
				</div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">属性标识</label>
					<div class="controls">
		                <s:textfield name="attrcode" id="id_attrcode" placeholder="请根据属性名称英文命名" required="true"/>
		                
					</div>
				</div>
            </div>
        </div>
     <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">属性值来源</label>
					<div class="controls">
					    <s:select id="id_valuesrc" name="valuesrc"   list="valuesrcMap"  placeholder="请选择属性值来源" required="true" />
					</div>
				</div>
            </div>
        </div>
        <div id="id_valueparm_and_mname">
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">对应参数</label>
					<div class="controls">
		                <s:textfield name="valueparam" id="id_valueparam" placeholder="请用英文命名，例如 ：SYS_NO_YES" required="true"/>
					</div>
				</div>
            </div>
        </div>
        
         <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">参数选项</label>
					<div class="controls">
		                <s:textfield name="mname" id="id_mname" placeholder="请用逗号隔开填写参数，例如 ：男,女" required="true"/>
					</div>
				</div>
            </div>
        </div>
       </div>
       <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">是否必填</label>
					<div class="controls">
					   	<s:select id="id_ifnecessary" name="ifnecessary"   list="ifnecessaryMap"  placeholder="请选择..." required="true" />
					</div>
				</div>
            </div>
        </div>
       
      <!--  暂时屏蔽掉 -->
      <%--   <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">默认值</label>
					<div class="controls">
		                <s:textfield name="defaultvalue" id="id_defaultvalue" />
					</div>
				</div>
            </div>
        </div> --%>
	</div>
	<div class="form-actions right" id= "id_extendattr_action">
		<button class="btn blue" type="button" data-grid-reload=".grid-biz-extendattr-prv-attrrule-index" name="submitAttrrule">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script type="text/javascript">
	var extendattr_initCity = '<s:property value="model.city" />';
	var extendattr_isOktoUpdate = '<s:property value="model.flag" />';
	var extendattr_mname = '<s:property value="model.mnames" />'
</script>
<script src="${base}/extendattr/prv-attrrule-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>