<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="container-fluid data-view">
    <div class="well form-horizontal">
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="catalogname" label="名称"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="catalogstatus" label="状态"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="areaid" label="区域"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="pri" label="排序优先级"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="condtiontype" label="适用条件类型"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="condtionvalue" label="适用条件值"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="city" label="地市"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="catalogdesc" label="描述"/>
            </div>
        </div>
    </div>    
</div>