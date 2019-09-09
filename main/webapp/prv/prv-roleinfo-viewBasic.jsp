<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="container-fluid data-view">
    <div class="well form-horizontal">
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="name" label="name"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:date name="etime" format="timestamp" label="etime"/>                       
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="memo" label="memo"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:date name="stime" format="timestamp" label="stime"/>                       
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="creator" label="creator"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="areas" label="areas"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="rolelevel" label="rolelevel"/>
            </div>
        </div>
    </div>    
</div>