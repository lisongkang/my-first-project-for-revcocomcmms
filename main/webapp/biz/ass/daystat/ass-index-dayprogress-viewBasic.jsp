<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="container-fluid data-view">
    <div class="well form-horizontal">
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="tdate" label="统计日期YYYYMMDD"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="assid" label="考核ID"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="objtype" label="统计对象"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="objid" label="对象ID"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="total" label="任务总数"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="curnum" label="当日完成数"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
                <s2:property value="rate" label="完成率"/>
            </div>
        </div>
    </div>    
</div>