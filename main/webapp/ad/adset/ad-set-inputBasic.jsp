<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-ad-set-inputBasic" action="${base}/ad/adset/ad-set!doSave"
	method="post">
	<div class="form-body">
    <s:hidden name="id" />
    <s:hidden name="adstatus" />
     <s:hidden name="optid" />
    <div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<label class="control-label">地市</label>
				<div class="controls">
	                <s:select name="city" id="id_city" list="areaMap" />
				</div>
			</div>
		</div>
		
		
	</div>
	<div class="row">
	   
	   <div class="col-md-6">
			  <div class="form-group">   
			    <label class="control-label">广告缩略图</label>
			    <div class="controls">
			        <s:textfield id="id_adsite" name="adsite" type="text"  class="form-control" maxlength="60" required="true" />
		        </div>
		      </div>
	      </div> 
		<div style ="margin-top:5px" >
		    <button class="btn blue"  name='uploadAdImgBtn'  >
			<i class="fa "></i>上传
		   </button>
	   </div>
	   
	   
	</div>
	<div class="row">
		<div class="col-md-6">
		   <div class="form-group">   
			    <label class="control-label">优先级</label>
			    <div class="controls">
			    <s:textfield  id="id_pri" name="pri" maxlength="2" required="true"  class="form-control"/>
		        </div>
		    </div>
		</div>
	</div>
	<div class="row">
        <div class="col-md-12">
			  <div class="form-group">   
			    <label class="control-label">广告名称</label>
			    <div class="controls">
			    <s:textarea id="id_adname" rows='1' name="adname"  required="true" maxlength="60" />
		        </div>
		      </div>
		</div>
	</div>
	<div class="row">
        <div class="col-md-6">
			  <div class="form-group">   
			    <label class="control-label">广告类型</label>
			    <div class="controls">
			     <s:select id="id_adtype" name="adtype"  required="true" list="adtypeMap" />
		        </div>
		      </div>
		</div>
		<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">广告对象</label>
					<div class="controls">
						<div class="input-icon right">
							<i class="fa fa-ellipsis-horizontal fa-select-obj"></i>
							<s:textfield name="extraAttributes.objname" id="id_objname" required="true" maxlength="60"/>
							<s:hidden name="adobj" />
						</div>
					</div>
				</div>
			</div>
	</div>
	
	
    <div class="row">
        <div class="col-md-12">
			  <div class="form-group">   
			    <label class="control-label">备注</label>
			    <div class="controls">
			    <s:textarea id="id_memo" name="memo" rows="3"  maxlength="60"  placeholder="可在此编辑备注信息" class="form-control"/>
		        </div>
		      </div>
		</div>
	</div>
	
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" name='btn_submitAdConfig' data-grid-reload=".grid-ad-set-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script type="text/javascript">
</script>
<script src="${base}/ad/adset/ad-set-inputBasic.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>