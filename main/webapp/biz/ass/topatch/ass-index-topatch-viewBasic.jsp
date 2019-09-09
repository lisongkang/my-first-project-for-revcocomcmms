<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation"
	action="" method="post" 
	data-editrulesurl="">
	<s:hidden name="id" />
	<s:hidden name="version" />
	<s:token />
	<div class="form-body">
	    <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label class="control-label">考核内容</label>
                    <div class="controls">
                        <div class="form-control-static">
                            <s:property value="model.asscontent" />
                        </div>
                    </div>
                </div>
            </div>
            
        </div>
        
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">考核参数</label>
                    <div class="controls">
                        <div class="form-control-static">
                            <s:property value="model.assparamName" />
                        </div>                        
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">考核对象</label>
                    <div class="controls">
                        <div class="form-control-static">
                            <s:property value="model.assobj" />
                        </div>                                       
                    </div>
                </div>
            </div>
           
        </div>
        
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">任务单位</label>
                    <div class="controls">
                        <div class="form-control-static">
                            <s:property value="model.taskunitName" />
                        </div>                          
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">任务总数</label>
                    <div class="controls">
                        <div class="form-control-static">
                            <s:property value="model.totalnum" />
                        </div>                                         
                    </div>
                </div>
            </div>
        </div>
        
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">考核周期单位</label>
                    <div class="controls">
                        <div class="form-control-static">
                            <s:property value="model.unitname" />
                        </div>                          
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">制定部门</label>
                    <div class="controls">
                        <div class="form-control-static">
                            <s:property value="model.deptName" />
                        </div>                          
                    </div>
                </div>
            </div>            
        </div>        
        
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">操作员</label>
                    <div class="controls">
                        <div class="form-control-static">
                            <s:property value="model.opername" />
                        </div>                                        
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">操作时间</label>
                    <div class="controls">
                        <div class="form-control-static">
                            <s:property value="model.opdate" />
                        </div>                          
                    </div>
                </div>
            </div>
        </div>               
        
	</div>

</form>

<%@ include file="/common/ajax-footer.jsp"%>