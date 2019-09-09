<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-memo-cfg-inputBasic"
      action="${base}/memo/biz-memo-cfg!doSave" method="post"
      data-editrulesurl="${base}/memo/biz-memo-cfg!buildValidateRules">
    <s:hidden name="id"/>
    <s:hidden name="version"/>
    <s:token/>
    <div class="form-body">
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">地市</label>
                    <div class="controls">
                        <s:select  id="id_city" name="city" list="cityMap" required="true"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">业务区</label>
                    <div class="controls">
                        <s:select id="id_areas" name="areas" list="townMap" placeholder="请选择业务区" multiple="true"
                                  required="true"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">业务操作</label>
                    <div class="controls">
                        <s:select id="id_opcodes" name="opcodes" list="opcodesMap" placeholder="请选择业务操作"
                                  required="true"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">排序优先级</label>
                    <div class="controls">
                        <s:textfield name="pri" placeholder="请输入1-10数字：数字越大，优先级越高"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">备注内容</label>
                    <div class="controls">
                        <s:textarea id="memotxt" name="memotxt" maxlength="50" rows="4" required="true"/>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <div class="form-actions right" id="id_extendattr_action">
        <button class="btn blue" type="submit" data-grid-reload=".grid-memo-cfg-index" name="submitAttrrule">
            <i class="fa fa-check"></i> 保存
        </button>
        <button class="btn default btn-cancel" type="button">取消</button>
    </div>
</form>
<script type="text/javascript">
    var extendattr_initCity = '<s:property value="model.city" />';
    var extendattr_initAreas = '<s:property value="model.areas" />';
    var extendattr_initOpcodes = '<s:property value="model.opcodes" />';
</script>
<script src="${base}/memo/biz-memo-cfg-inputBasic.js"/>
<%@ include file="/common/ajax-footer.jsp" %>