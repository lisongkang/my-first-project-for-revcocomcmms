<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="container-fluid data-view">
    <div class="well form-horizontal">
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="timeType" label="限制方式"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="city" label="地市"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="createOper" label="创建人"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="objId" label="限制对象ID"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="planId" label="体验授权方案标识"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:date name="updateTime" format="timestamp" label="最近修改时间"/>                       
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="limitNums" label="限制次数"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="name" label="体验授权名称"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:date name="createTime" format="timestamp" label="创建时间"/>                       
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="updateOper" label="最近修改人"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="objType" label="限制对象类型"/>
            </div>
        </div>
    </div>    
</div>