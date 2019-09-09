<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-sys-smstemp-config-inputBasic" action="${base}/biz/manage/smstemp/sys-smstemp-config!doSave"
	method="post">
   <s:hidden name="id" />
   <s:hidden name="tstatus" />
   <s:hidden name="opid" />
   <div class="form-body">
		<div class="row">
		  <div class="col-md-12">
				<div class="form-group">
					<label class="control-label">模板标题</label>
					<div class="controls">
						<s:textarea name="tname" required="true" rows="1" cssStyle="width:98%" maxlength="60"/>
					</div>
				</div>
			</div>
		</div>
		<div class="con-fixing" id="id_smstempvarinfo" >
		<div class="row">
		   	<div class="col-md-12">
				 <label style="font-size:11px;color: red;">备注：编辑短信模板时如需变量请选择以下字段</label>
				 
				 <div class="con-name controls" id="id_smstempvar">
					     	<ul class="p-name s_item_spe" id="id_smsvar">
		                	</ul>
		                	<ul class="p-name s_item_spe" id="id_smsvar1" style="display: none;">
		                	</ul>
		                	
					      <div id="showMore" class="showMore" onclick="Biz.showMore(this,'#id_smsvar1');"><span>+显示更多</span></div>
			     </div>
			 </div>     
		</div>
		</div>
		
		<div class="row">
			<div class="col-md-12">
				  <div class="form-group">   
				    <label class="control-label">模板内容</label>
				    <div class="controls">
				    <s:textarea id="id_tcontent" name="tcontent" rows="5"  maxlength="255" required="true"  placeholder="可在此编辑短信模板" class="form-control"/>
			        </div>
			      </div>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-12">
				  <div class="form-group">   
				    <label class="control-label">备注</label>
				    <div class="controls">
				    <s:textarea id="id_tcontent" name="memo" rows="3"  maxlength="255" required="true"  placeholder="可在此编辑备注信息" class="form-control"/>
			        </div>
			      </div>
			</div>
		</div>
	</div>
	
	<div class="form-actions right">
		<button class="btn blue" type="submit" name='btn_submitSysSmstempConfig' data-grid-reload=".grid-sys-smstemp-config-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>


<script src="${base}/biz/manage/smstemp/sys-smstemp-config-inputBasic.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>