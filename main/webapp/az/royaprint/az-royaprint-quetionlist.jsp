<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="row">
    <s:hidden name="id" />
    <s:hidden name="version" />
    <s:token />
    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <!--startprint-->
                <div class="controls">
                    <div style="text-align: center;font-size: 18px">广东省广播电视网络股份有限公司<s:property value="model.bizApplication.buildunit"/></div>
                    <div style="text-align: center;font-size: 14px">小额工程项目施工费用发放表</div>
                    <table width="99%" class="c-table" border="1" cellspacing="0" cellpadding="0">
                        <tbody>
                            <tr>
                                <td width="20">项目名称 :</td>
                                <td colspan="6"><s:property value="model.bizApplication.proname" /></td>
                            </tr>
                            <tr>
                                <td width="80">施工地址 :</td>
                                <td colspan="6"><s:property value="model.bizApplication.buildaddr"/></td>
                            </tr>
                        </tbody>
                    </table>
                    <table width="99%" class="c-table" border="1" cellspacing="0" cellpadding="0">
                        <thead>
                        <tr>
                            <th align="left">序号</th>
                            <th align="left">施工人员姓名</th>
                            <th align="left">所属部门/单位</th>
                            <th align="left">身份证号</th>
                            <th align="left">银行账号</th>
                            <th align="left">施工费小计</th>
                            <th width="13%" align="left">备注</th>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator value="model.bizApplyConstBOList" status="status" var="answer">
                            <tr>
                                <td><s:property value="#status.index+1" /></td>
                                <td><s:property value="#answer.constructorname"/></td>
                                <td><s:property value="#answer.depts"/></td>
                                <td><s:property value="#answer.idcard"/></td>
                                <td><s:property value="#answer.bankcard"/></td>
                                <td><s:property value="#answer.royalty"/></td>
                                <td></td>
                            </tr>
                        </s:iterator>
                        <tr>
                            <td colspan="1">总计:</td>
                            <td colspan="6"><s:property value="model.bizApplication.procost" /></td>
                        </tr>
                        <tr>
                            <td colspan="3">网格管理责任部门或运维管理部门(盖章) :</td>
                            <td colspan="4"> </td>
                        </tr>
                        <tr>
                            <td style="text-align: left">编制(网格):</td>
                            <td style="text-align: left" colspan="2">审核(责任部门负责人):</td>
                            <td style="text-align: left">审批(分管领导):</td>
                            <td style="text-align: left">确认(人力):</td>
                            <td style="text-align: left" colspan="2">核发(财务):</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <!--endprint-->
                <div class="controls" style="padding-top: 10px; padding-left: 600px">
                    <input type="button" style="width: 50px;height: 40px;" onclick=" a()" value="打印"/>
                </div>
            </div>
        </div>

    </div>
</div>
<script type="text/javascript">
    var sid = '<s:property value="#request.proid" />';
</script>
<script type="text/css">

</script>
<style media="print" type="text/css">
    @page {
        size: auto;  /* auto is the initial value */
        margin: 0mm; /* this affects the margin in the printer settings */
    }
</style>
<script type="text/javascript" language="javascript">
    function a(){
        var pronum = '<s:property value="model.bizApplication.pronum" />'
        //直接调用浏览器打印功能
        bdhtml=window.document.body.innerHTML;
        //定义打印区域起始字符，根据这个截取网页局部内容
        sprnstr="<!--startprint-->";
        //打印区域开始的标记
        eprnstr="<!--endprint-->";
        // 打印区域结束的标记
        prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);
        prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
        // 还原网页内容
        window.document.body.innerHTML=prnhtml;
        $.ajax({
            url:WEB_ROOT + '/az/royaprint/az-royaprint!printRecord?pronum=' + pronum,
            data:{},
            type:'POST',
            success:function(res){
                console.log(res)
            },
            error:function(res){
            }
        })
        // 开始打印
        window.print();
    }
</script>
<%@ include file="/common/ajax-footer.jsp"%>
