package com.maywide.test;

import java.io.StringWriter;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.rpc.ServiceException;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonplugin.JSONUtil;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.RequestWrapper;
import com.maywide.biz.inter.pojo.bizbindbank.BindbankServInfoBO;
import com.maywide.biz.inter.pojo.bizpreprocess.ChopcodesBO;
import com.maywide.biz.inter.pojo.bizpreprocess.EjectParamsInterBO;
import com.maywide.biz.inter.pojo.bizpreprocess.EjectPkgsInterBO;
import com.maywide.biz.inter.pojo.bizpreprocess.OrderParamsInterBO;
import com.maywide.biz.inter.pojo.callcenter.orderdetail.Order;
import com.maywide.biz.inter.pojo.callcenter.orderdetail.OrderDetail;
import com.maywide.biz.inter.pojo.callcenter.orderdetail.OrderDetailResponse;
import com.maywide.biz.inter.pojo.callcenter.orderdetail.OrderRequest;
import com.maywide.biz.inter.pojo.callcenter.orderdetail.OrderResponse;
import com.maywide.biz.inter.pojo.callcenter.orderdetail.OrderRet;
import com.maywide.biz.inter.pojo.callcenter.orderdetail.OrderTasks;
import com.maywide.biz.inter.pojo.callcenter.orderdetail.QueOrder;
import com.maywide.biz.inter.pojo.callcenter.orderdetail.QueOrderDetailReq;
import com.maywide.biz.inter.pojo.callcenter.orderdetail.QueOrderReq;
import com.maywide.biz.inter.pojo.chgdev.ChgDevTmpReq;
import com.maywide.biz.inter.pojo.chgdev.ChgDevWGReq;
import com.maywide.biz.inter.pojo.chgdev.CustChangeDevBO;
import com.maywide.biz.inter.pojo.chgdev.PrdPriceBO;
import com.maywide.biz.inter.pojo.install.InstallOrderAsyncReq;
import com.maywide.biz.inter.pojo.oss.order.WorkAssignServiceOrderListRequest;
import com.maywide.biz.inter.pojo.querycatalog.PersonCustPtReq;
import com.maywide.biz.inter.pojo.querycatalog.QueCustMarktInfoReq;
import com.maywide.biz.inter.pojo.querycatalog.QuePage;
import com.maywide.biz.inter.pojo.querycatalog.QueryBizLogParam;
import com.maywide.biz.inter.pojo.queservstinfo.QueConditionsBO;
import com.maywide.biz.inter.pojo.queservstinfo.QueServExpireNumReq;
import com.maywide.biz.inter.pojo.queservstinfo.QueServExpireReq;
import com.maywide.biz.inter.pojo.queservstinfo.QueSoonExpcustReq;
import com.maywide.biz.market.pojo.BizAttprdProcReq;
import com.maywide.biz.prd.entity.Salespkg;
import com.maywide.biz.ws.callcenter.WorkAssignService;
import com.maywide.biz.ws.callcenter.WorkAssignServiceServiceLocator;
import com.maywide.biz.ws.oss.Channel2IomCallService;
import com.maywide.biz.ws.oss.Channel2IomCallServiceServiceLocator;
import com.maywide.core.security.encrypt.ISecurityService;
import com.maywide.core.security.encrypt.SecurityFactory;
import com.maywide.core.security.remote.HttpClientImpl;
import com.maywide.core.security.remote.RemoteCall;
import com.maywide.core.security.remote.socket.XmlConverter;
import com.maywide.test.pojo.BizBindbankInterReqTest;
import com.maywide.test.pojo.BizPreprocessInterReqTest;
import com.maywide.test.pojo.QueServstInfoInterReqTest;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;

public class InterTest {
	public static LoginInfo getGridTestLoginInfo2(){
		LoginInfo loginInfo = new LoginInfo();
		//所在部门对应的城市id;prv_department：city
		loginInfo.setCity("GZ");
		loginInfo.setLoginname("GZCYKFACS");
		loginInfo.setName("网格CMMS");
		//操作员工号ID
		loginInfo.setOperid(658733l);
		//所在部门对应的地区id;prv_department：areaid
		loginInfo.setAreaid(100l);
		//登录时选的部门id
		loginInfo.setDeptid(2866010l);
		//角色id
		loginInfo.setRoleid(100000000000000l);
		return loginInfo;
	}
	//ywplogin
    public static LoginInfo getGridTestLoginInfo1(){
		LoginInfo loginInfo = new LoginInfo();
		//所在部门对应的城市id;prv_department：city
		loginInfo.setCity("FS");
		loginInfo.setLoginname("GZCYKFACS");
		loginInfo.setName("网格测试工号");
		//操作员工号ID
		loginInfo.setOperid(104129l);
		//所在部门对应的地区id;prv_department：areaid
		loginInfo.setAreaid(715l);
		//登录时选的部门id
		loginInfo.setDeptid(101144l);
		//角色id
		loginInfo.setRoleid(100000000000015l);
		return loginInfo;
	}
  //ywplogin
    public static LoginInfo getGridTestLoginInfo(){
		LoginInfo loginInfo = new LoginInfo();
		//所在部门对应的城市id;prv_department：city
		loginInfo.setCity("ZS");
		loginInfo.setLoginname("GZCYKFACS");
		loginInfo.setName("网格测试工号");
		//操作员工号ID
		loginInfo.setOperid(104129l);
		//所在部门对应的地区id;prv_department：areaid
		loginInfo.setAreaid(715l);
		//登录时选的部门id
		loginInfo.setDeptid(101144l);
		//角色id
		loginInfo.setRoleid(100000000000015l);
		return loginInfo;
	}

    public static LoginInfo getGridTestLoginInfo3(){
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setCity("ZS");
        loginInfo.setLoginname("GZCYKFCMMS");
        loginInfo.setName("网格CMMS");
        loginInfo.setOperid(658733L);
        loginInfo.setAreaid(715L);
        loginInfo.setDeptid(101031L);
        loginInfo.setRoleid(100000000000000L);
        return loginInfo;
    }
	//ywp如果是开发模式，则屏蔽解密，并且设置默认的用户
		public static final boolean devMode = true;
    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
        	
            ObjectMapper jsonMapper = null;
            jsonMapper = new ObjectMapper();
            jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            RemoteCall remoteCall = new RemoteCall();
            remoteCall.setProtocol("HTTP");
            remoteCall.setCallUrl("/grid/api");
            remoteCall.setRemoteIP("127.0.0.1");
            remoteCall.setRemotePort(8080);
//            queArreardet(jsonMapper, remoteCall);
//            remoteCall.setRemotePort(8089);

//            setRemoteCallIpPort(remoteCall,
//                    TestConstant.CallIpPortType.LOCAL_DEV);

        	/*String a = "2345678900987654321";
        	String b = "345678900987654321";
        	String c = "12345678900987654321";
        	String d = "00987654321";
        	a = a.substring(a.length()-10,a.length());
        	b = b.substring(b.length()-10,b.length());
        	c = c.substring(c.length()-10,c.length());
        	d = d.substring(d.length()-10,d.length());
        	System.out.println(a);
        	System.out.println(b);
        	System.out.println(c);
        	System.out.println(d);*/
            // applyInstall(jsonMapper, remoteCall);
            // createCust(jsonMapper, remoteCall);
//            editCust(jsonMapper, remoteCall);
//            getCustPubInfo(jsonMapper, remoteCall);
            // queryDepartment(jsonMapper, remoteCall);
//            login(jsonMapper, remoteCall);

//             queryResHouse(jsonMapper, remoteCall);
//             queryCustInfo(jsonMapper, remoteCall);
            // getOperMenu(jsonMapper, remoteCall);
            // queryCustMarketing(jsonMapper, remoteCall);
            // queryMarketBatch(jsonMapper, remoteCall);
            // queTempopenPlan(jsonMapper, remoteCall);
            // bizTempopen(jsonMapper, remoteCall);
//			 bizFreshauth(jsonMapper, remoteCall);
//             bizBindbank(jsonMapper, remoteCall);
//             queCustbank(jsonMapper, remoteCall);
//             dealCustMarketing(jsonMapper, remoteCall);
            // getData(jsonMapper, remoteCall);
            // saveCustMarketing(jsonMapper, remoteCall);
            // querySalespkgKnow(jsonMapper, remoteCall);
//             queServstInfo(jsonMapper, remoteCall);
            // queryCustMarketing(jsonMapper, remoteCall);
            // queGridmanager(jsonMapper, remoteCall);
            // queUserPrd(jsonMapper, remoteCall);
            // queUserPkg(jsonMapper, remoteCall);
            // queryCatalog(jsonMapper, remoteCall);
            // queCustorder(jsonMapper, remoteCall);
            // bizPreprocess(jsonMapper, remoteCall);
            // queArreardet(jsonMapper, remoteCall);
            // doFeein(jsonMapper, remoteCall);
            // doOrderCommit(jsonMapper, remoteCall);
            // queApplyKnowDet(jsonMapper, remoteCall);

            // testPostMethod();
            // testGetRespserialno();
            // testFor();
//            queCustDevInfo(jsonMapper, remoteCall);
//            getCustFittingInfo(jsonMapper, remoteCall);
//            checkNewStbDev(jsonMapper, remoteCall);
//            saveTmp(jsonMapper, remoteCall);
//            queryBizLogByPage(jsonMapper, remoteCall);
//            chgdevsave(jsonMapper, remoteCall);
            //            testRreplaceSensitiveInfo();
//            queCustMarktInfo(jsonMapper, remoteCall);
//            getWS();
//            toEntity(null);
//            toXML();
//            queCallCenterOrder(jsonMapper, remoteCall);
//            test_wsorder();
//            queCallCenterOrderDetail(jsonMapper, remoteCall);
//            getOssWS();
//            queOssOrder(jsonMapper, remoteCall);
//            queOssOrderDetail(jsonMapper, remoteCall);
//            BIZ_ASYNC_ORDER(jsonMapper, remoteCall);
//            queServExpire(jsonMapper, remoteCall);
//            queSoonExpcust(jsonMapper, remoteCall);
//            queServExpirenum(jsonMapper, remoteCall);
//            bizAttprdProc(jsonMapper, remoteCall);
        	
        	
        	
//        	String number = "0.0.2";
//        	String a =  number.replaceAll("\\.","");
//        	int numberf = Integer.parseInt(a);
//        	System.out.println(numberf);
            
            
        	//集客商机管理
            //1.2.	查询商机集客经理接口
//            queGrpbyareaInfo(jsonMapper, remoteCall);
            //1.3.	查询商机列表信息接口
            queBuslistinfo(jsonMapper, remoteCall);
            //1.4.	新建or修改商机信息接口
//          saveBusinfo(jsonMapper, remoteCall);
            //1.5.	查询商机轨迹列表接口
//            queBustraillistinfo(jsonMapper, remoteCall);
            //1.6.	查询商机跟踪信息接口
//            queBustrackinfo(jsonMapper, remoteCall);
            //1.7.	跟踪商机接口
//            saveBustrackinfo(jsonMapper, remoteCall);
            //根据gcode获得业务顺序和是否有权限
//            queProcessSequenceByCity(jsonMapper, remoteCall);
            //收款超时提醒，一个月后仍未收费，通知所在单位总监；三个月未收费通知到省公司
//            queMsgList(jsonMapper, remoteCall);
//            roamCtrl(jsonMapper, remoteCall);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  //testnow
    public static void roamCtrl(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "roamCtrl");
        JSONObject bodyObj = new JSONObject();
        
        
        bodyObj.put("roamstatus", "0");
        bodyObj.put("loginname", "76915650211@dg.gcable");
        
//        jsonObject.put("requestBody", bodyObj.toString());
        ISecurityService securityService = SecurityFactory.getSecurityService();
        String requestBody = securityService.encrypt(bodyObj.toString());
        jsonObject.put("requestBody", requestBody);

        System.out.println(jsonObject);
        String requestStr = jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }
    public static void queRule(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "roamCtrl");
        JSONObject bodyObj = new JSONObject();
        
        
        bodyObj.put("roamstatus", "0");
        bodyObj.put("loginname", "76915650211@dg.gcable");
        
//        jsonObject.put("requestBody", bodyObj.toString());
        ISecurityService securityService = SecurityFactory.getSecurityService();
        String requestBody = securityService.encrypt(bodyObj.toString());
        jsonObject.put("requestBody", requestBody);

        System.out.println(jsonObject);
        String requestStr = jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }
    public static void queMsgList(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queMsgList");
        JSONObject bodyObj = new JSONObject();
        
        
//        bodyObj.put("city", "DG");
        
//        jsonObject.put("requestBody", bodyObj.toString());
        ISecurityService securityService = SecurityFactory.getSecurityService();
        String requestBody = securityService.encrypt(bodyObj.toString());
        jsonObject.put("requestBody", requestBody);

        System.out.println(jsonObject);
        String requestStr = jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }
    public static void queProcessSequenceByCity(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queProcessSequenceByCity");
        JSONObject bodyObj = new JSONObject();
        
        
        bodyObj.put("city", "DG");
        
//        jsonObject.put("requestBody", bodyObj.toString());
        ISecurityService securityService = SecurityFactory.getSecurityService();
        String requestBody = securityService.encrypt(bodyObj.toString());
        jsonObject.put("requestBody", requestBody);

        System.out.println(jsonObject);
        String requestStr = jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }
    public static void saveBustrackinfo(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "saveBustrackinfo");
        JSONObject bodyObj = new JSONObject();
        
        JSONObject bustrack = new JSONObject();
        JSONObject cust = new JSONObject();
        bustrack.put("busid", "44910");
        bustrack.put("busstatus", "6");
        bustrack.put("tdate", "2018-11-19");
        bustrack.put("trackmemo", "");
        cust.put("id", "760003060681");
        cust.put("phone", "");
        cust.put("mobile", "18290000000");
        cust.put("markno", "FS6800155605");
        cust.put("name", "广东鼎熙通信实业有限公司");
        cust.put("cardtype", "0");
        cust.put("cardno", "34567890000");
        cust.put("subtype", "40");
        cust.put("secsubtype", "200");
        cust.put("linkman", "200");
        cust.put("linkaddr", "200");
        bodyObj.put("bustrack", bustrack);
        bodyObj.put("cust", cust);
        
//        jsonObject.put("requestBody", bodyObj.toString());
        ISecurityService securityService = SecurityFactory.getSecurityService();
        String requestBody = securityService.encrypt(bodyObj.toString());
        jsonObject.put("requestBody", requestBody);

        System.out.println(jsonObject);
        String requestStr = jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }
    public static void saveBusinfo(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "saveBusinfo");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("custid", "760003060681");
        bodyObj.put("busname", "龙思妹");
        bodyObj.put("bustype", "3");
        bodyObj.put("busstatus", "3");
        bodyObj.put("apply", "4");
        bodyObj.put("contime", "2018-11-16");
        bodyObj.put("budget", "10");
        bodyObj.put("nums", "5");
        bodyObj.put("linkman", "帅爷");
        bodyObj.put("phone", "13245679999");
        bodyObj.put("description", "跨境电商度死对都偶偶偶");
        bodyObj.put("memo", "点击收款对");
        bodyObj.put("optype", "I");
        bodyObj.put("busid", "");
//        jsonObject.put("requestBody", bodyObj.toString());
        ISecurityService securityService = SecurityFactory.getSecurityService();
        String requestBody = securityService.encrypt(bodyObj.toString());
        jsonObject.put("requestBody", requestBody);

        System.out.println(jsonObject);
        String requestStr = jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }
    public static void queBustrackinfo(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queBustrackinfo");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("busid", "44907");
//        jsonObject.put("requestBody", bodyObj.toString());
        ISecurityService securityService = SecurityFactory.getSecurityService();
        String requestBody = securityService.encrypt(bodyObj.toString());
        jsonObject.put("requestBody", requestBody);

        System.out.println(jsonObject);
        String requestStr = jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }
    public static void queBustraillistinfo(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queBustraillistinfo");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("busid", "44907");
        bodyObj.put("pagesize", "10");
        bodyObj.put("currentPage", "1");
//        jsonObject.put("requestBody", bodyObj.toString());
        ISecurityService securityService = SecurityFactory.getSecurityService();
        String requestBody = securityService.encrypt(bodyObj.toString());
        jsonObject.put("requestBody", requestBody);

        System.out.println(jsonObject);
        String requestStr = jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }
    public static void queBuslistinfo(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queBuslistinfo");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("areastr", "");
        bodyObj.put("grpmanagerid", "");
        bodyObj.put("busname", "龙思妹123");
        bodyObj.put("stime", "2016-12-01");
        bodyObj.put("etime", "");
        bodyObj.put("rightstr", "7");
        bodyObj.put("pagesize", "10");
        bodyObj.put("currentPage", "1");
        
       /* bodyObj.put("areastr", "684");
        bodyObj.put("grpmanagerid", "500970");
        bodyObj.put("busname", "九江镇海寿岛应急广播管理系统项目");
        bodyObj.put("stime", "2016-12-01");
        bodyObj.put("etime", "");
        bodyObj.put("rightstr", "7");
        bodyObj.put("pagesize", "10");
        bodyObj.put("currentPage", "1");*/
        
//        jsonObject.put("requestBody", bodyObj.toString());
        ISecurityService securityService = SecurityFactory.getSecurityService();
        String requestBody = securityService.encrypt(bodyObj.toString());
        jsonObject.put("requestBody", requestBody);

        System.out.println(jsonObject);
        String requestStr = jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }
    
    public static LoginInfo getTestLoginInfo(){
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setCity("GZ");
		loginInfo.setLoginname("GZCYKFACS");
		loginInfo.setAreaid(103l);
		loginInfo.setDeptid(2866960l);
		loginInfo.setOperid(104129l);
		return loginInfo;
	}
    
    private static void testRreplaceSensitiveInfo() {

        String cardno = "4792299302360112";
        String pwdcardno = replaceSensitiveInfo(cardno, "acctno");

        System.out.println(cardno);
        System.out.println(pwdcardno);
    }

    

    // 替换敏感信息
    public static String replaceSensitiveInfo(String oldstr, String proctype) {
        if (StringUtils.isBlank(oldstr)) {
            return oldstr;
            // oldstr = oldstr.substring(beginIndex, endIndex);
        }

        int length = oldstr.length();

        if (proctype.equals("cardno")) {
            if (length >= 18) {
                oldstr = oldstr.substring(0, 6) + genStr("*", 8)
                        + oldstr.substring(14, length);
            } else if (length >= 15) {
                oldstr = oldstr.substring(0, 6) + genStr("*", 6)
                        + oldstr.substring(12, length);
            } else if (length > 10) {
                oldstr = genStr("*", 6) + oldstr.substring(6, length);
            } else if (oldstr.length() > 4) {
                oldstr = genStr("*", 3) + oldstr.substring(3, oldstr.length());
            }
        }  else if (proctype.equals("addr")) {
            if (length > 18) {
                oldstr = oldstr.substring(0, length - 10) + genStr("*", 10);
            } else if (oldstr.length() > 10) {
                oldstr = oldstr.substring(0, length - 6) + genStr("*", 6);
            } else if (oldstr.length() > 4) {
                oldstr = oldstr.substring(0, length - 4) + genStr("*", 4);
            } else if (oldstr.length() > 3) {
                oldstr = oldstr.substring(0, length - 3) + genStr("*", 3);
            } else if (oldstr.length() > 2) {
                oldstr = oldstr.substring(0, length - 2) + genStr("*", 2);
            } else {
                oldstr = genStr("*", length);
            }
        } else if (proctype.equals("phone")) {
            if (length >= 18) {
                oldstr = oldstr.substring(0, 4) + genStr("*", 10)
                        + oldstr.substring(14, length);
            } else if (length > 13) {
                oldstr = oldstr.substring(0, 6) + genStr("*", 4)
                        + oldstr.substring(10, length);
            } else if (length > 10) {
                oldstr = oldstr.substring(0, 3) + genStr("*", 4)
                        + oldstr.substring(7, length);
            } else if (length > 7) {
                oldstr = oldstr.substring(0, 2) + genStr("*", 4)
                        + oldstr.substring(6, length);
            } else if (length > 4) {
                oldstr = genStr("*", 3) + oldstr.substring(3, length);
            }
        } else if (proctype.equals("acctno")) {
            if (length >= 12) {
                oldstr = oldstr.substring(0, length - 8) + genStr("*", 4)
                        + oldstr.substring(length - 4, length);
            } else if (length > 6) {
                oldstr = oldstr.substring(0, length - 4) + genStr("*", 4);
            } else if (length > 4) {
                oldstr = genStr("*", 3) + oldstr.substring(3, length);
            }
        } else if (proctype.equals("custname")) {

            if (length >= 4) {
                oldstr = oldstr.substring(0, 2)
                        + genStr("*", (length + 1) / 2)
                        + oldstr.substring(2 + (length + 1) / 2,
                                oldstr.length());
            } else if (length > 1) {
                oldstr = oldstr.substring(0, 1) + genStr("*", 1)
                        + oldstr.substring(2, oldstr.length());
                ;
            }
        } else {

            if (length >= 4) {
                oldstr = oldstr.substring(0, 2)
                        + genStr("*", (length + 1) / 2)
                        + oldstr.substring(2 + (length + 1) / 2,
                                oldstr.length());
            } else if (length > 1) {
                oldstr = oldstr.substring(0, 1) + genStr("*", 1)
                        + oldstr.substring(2, oldstr.length());
                ;
            }
        }

        return oldstr;
    }

    private static String genStr(String srcStr, int length) {
        String retStr = srcStr;
        if (null != srcStr && "" != srcStr) {
            for (int i = 1; i < length; i++) {
                retStr = retStr + srcStr;
            }
        }
        return retStr;
    };

    private static void testFor() {

        String ss = String.valueOf(new Date().getTime());

        StringBuffer s = new StringBuffer();
        int n = s.lastIndexOf(",");
        s.append("14213, werew ,21321dd, ");
        int n1 = s.lastIndexOf(",");

        s.setLength(n);
        System.out.print(s);

        String a1 = ",,343,";
        String a2 = ",";
        String[] a1A = a1.split(",");
        String[] a2A = a2.split(",");

        // 事实证明增强for循环不会自动判断list为null的，所以在使用前要自行进行非空检查，不为null才能用增强for循环
        List<Salespkg> list = null;
        list = new ArrayList();
        list.addAll(null);
        list.clear();
        for (Salespkg obj : list) {
            System.out.print(obj.getId());
        }

    }

    private static void testPostMethod() {

        PostMethod postMethod = new PostMethod();
        postMethod
                .addParameter("requestContent",
                        "{\"output\":{\"serialno1\":\"12321\",\"serialno\":{\"serialno\":\"bb\"}}}");

        NameValuePair nv = postMethod.getParameter("requestContent");

        NameValuePair nv1 = postMethod.getParameter("requestContent1");
        String n1 = nv1.getName();

        String n = nv.getName();
        String v = nv.getValue();

        System.out.print(v);
    }

    private static String testGetRespserialno() throws Exception {
        String response = "{\"output\":{\"serialno1\":\"12321\",\"serialno\":{\"serialno\":\"bb\"}}}";
        if (StringUtils.isBlank(response)) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setDateFormat(dateFormat);

        JsonNode nodeTree = objectMapper.readTree(response);
        JsonNode outputNode = nodeTree.get("output");

        String bossRespOutput = outputNode.get("serialno").toString();

        return bossRespOutput;
    }

    private static void queApplyKnowDet(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queApplyKnowDet");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("recid", "76");
        bodyObj.put("custorderid", "1004826");
        bodyObj.put("knowid", "3");
        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);
        String requestStr = jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }

    public static void doOrderCommit(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "bizOrdercommit");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("custorderid", "1003960");
        bodyObj.put("payway", "11");
        bodyObj.put("paycode", "020120");
        bodyObj.put("payreqid", "123456798");
        bodyObj.put("bankaccno", "440502004602444416");
        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);
        String requestStr = jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }

    public static void doFeein(ObjectMapper jsonMapper, RemoteCall remoteCall)
            throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "bizFeein");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("fees", "52");
        bodyObj.put("keyno", "8757003702137490");
        bodyObj.put("gdno", "123456798");
        bodyObj.put("areaid", "100");
        bodyObj.put("patchid", "1719");
        bodyObj.put("custid", "123456");
        bodyObj.put("name", "测试");
        bodyObj.put("whladdr", "广州");
        bodyObj.put("descrip", "测试");
        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);
        String requestStr = jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }
    
    public static void queGrpbyareaInfo(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queGrpbyareaInfo");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("areaid", "100");
//        jsonObject.put("requestBody", bodyObj.toString());
        ISecurityService securityService = SecurityFactory.getSecurityService();
        String requestBody = securityService.encrypt(bodyObj.toString());
        jsonObject.put("requestBody", requestBody);
        //ywp开发模式不用加密
//        bodyObj.put("keyno", "87600~!@#$%.,=");
//        ISecurityService securityService = SecurityFactory.getSecurityService();
//        String requestBody = securityService.encrypt(bodyObj.toString());
//        System.out.println(requestBody);
//        jsonObject.put("requestBody", requestBody);

        System.out.println(jsonObject);
        String requestStr = jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }
    
    public static void queArreardet(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queArreardet");
        JSONObject bodyObj = new JSONObject();
        // bodyObj.put("catalogid", "760000426351");
        bodyObj.put("keyno", "8760002541450457");
        jsonObject.put("requestBody", bodyObj.toString());
        //ywp开发模式不用加密
//        bodyObj.put("keyno", "87600~!@#$%.,=");
//        ISecurityService securityService = SecurityFactory.getSecurityService();
//        String requestBody = securityService.encrypt(bodyObj.toString());
//        System.out.println(requestBody);
//        jsonObject.put("requestBody", requestBody);

        System.out.println(jsonObject);
        String requestStr = jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }

    public static void bizPreprocess(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        BizPreprocessInterReqTest req = new BizPreprocessInterReqTest();
        req.setAreaid("100");
        req.setBuff(null);
        req.setCustid("3600390521");
        req.setDescrip("测试产品订购");
        req.setGdno(null);
        req.setGdnoid(null);
        req.setIscrtorder("Y");
        req.setName("钟卓璿");
        req.setPatchid("1758");
        req.setWhladdr("广东省广州市海珠区瑞宝街道嘉鸿花园二街4号（金玉堂A座）1605房");

        // 订购参数
        List<OrderParamsInterBO> orderparams = new ArrayList();

        OrderParamsInterBO orderparam = new OrderParamsInterBO();

        /*
         * //订产品 orderparam.setChopcodes(null); orderparam.setCount("1");
         * orderparam.setIspostpone("N");
         * orderparam.setKeyno("8002002601372649"); orderparam.setKnowid("4");
         * orderparam.setSelpcodes(null); orderparam.setServid("3600115633");
         * orderparam.setUnit("1");//0：天；1：月；2：年
         */

        // 订营销方案
        orderparam.setCount("1");
        orderparam.setIspostpone("N");
        orderparam.setKeyno("8002002601372649");
        orderparam.setKnowid("3");
        orderparam.setSelpcodes("B000305,");
        orderparam.setServid("3600115633");
        orderparam.setUnit("1");// 0：天；1：月；2：年

        List<ChopcodesBO> chopcodes = new ArrayList();
        ChopcodesBO chopcodesBO1 = new ChopcodesBO();
        chopcodesBO1.setSelectid("269554");
//        chopcodesBO1.setPcodestr("B000303,B000304");
        chopcodes.add(chopcodesBO1);

        ChopcodesBO chopcodesBO2 = new ChopcodesBO();
        chopcodesBO2.setSelectid("269553");
//        chopcodesBO2.setPcodestr("C013062,");
        chopcodes.add(chopcodesBO2);

        orderparam.setChopcodes(chopcodes);

        orderparams.add(orderparam);
        req.setOrderparams(orderparams);

        // 退订参数
        List<EjectParamsInterBO> ejectparams = new ArrayList();
        EjectParamsInterBO ejectparam = new EjectParamsInterBO();

        List<EjectPkgsInterBO> ejectPkgs = new ArrayList();
        EjectPkgsInterBO ejectPkg = new EjectPkgsInterBO();
        ejectparam.setEjecttype("2");// 0-退订产品，2-退订营销方案
        ejectPkg.setPkginsid("16097229,");
        ejectPkgs.add(ejectPkg);

        ejectparam.setEjectPkgs(ejectPkgs);
        ejectparams.add(ejectparam);

        req.setEjectparams(ejectparams);

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(req));

        RequestWrapper request = new RequestWrapper();
        request.setApi("bizPreprocess");
        request.setRequestBody(mapper.writeValueAsString(req));

        System.out.println(mapper.writeValueAsString(request));

        String requestStr = mapper.writeValueAsString(request);

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);
    }

    public static void queCustorder(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queCustorder");
        JSONObject bodyObj = new JSONObject();
        // bodyObj.put("catalogid", "760000426351");
        bodyObj.put("city", "GZ");
        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);
        String requestStr = jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }

    public static void queryCatalog(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queryCatalog");
        JSONObject bodyObj = new JSONObject();
        // bodyObj.put("catalogid", "760000426351");
        bodyObj.put("catalogstatus", "Y");
        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);
        String requestStr = jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }

    public static void queUserPkg(ObjectMapper jsonMapper, RemoteCall remoteCall)
            throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queUserPkg");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("custid", "760000426351");
        bodyObj.put("servid", "760000963482");
        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);
        String requestStr = jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }

    public static void queUserPrd(ObjectMapper jsonMapper, RemoteCall remoteCall)
            throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queUserPrd");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("custid", "760000426351");
        bodyObj.put("servid", "760000963482");
        bodyObj.put("pstatus", "00");
        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);
        String requestStr = jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }

    public static void queGridmanager(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        // 1. city 1 所属分公司
        //
        // 2. objtype ? 网格对象类型 0：片区型
        // 1：网格型
        // 3. objid ? 网格对象id 如果是片区型，可通过参数
        // RES_PATCH取片区
        // 4. gridid ? 网格id 可通过参数BIZ_GRID取网格
        // 5. gridname ? 网格名称 模糊查询
        // 6. ismain ? 社区经理主标识 Y/N,
        // 一个网格只能配置一个主标识为Y的社区经理
        // 7. areamgername ? 网格经理名

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queGridmanager");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("city", "GZ");
        bodyObj.put("objtype", "0");
        bodyObj.put("objid", "1719");
        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);
        String requestStr = jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }

    public static void queServstInfo(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        QueServstInfoInterReqTest req = new QueServstInfoInterReqTest();

        req.setPagesize("5");
        req.setCurrentPage("1");

        List<QueConditionsBO> queConditions = new ArrayList();

        QueConditionsBO queCondition = new QueConditionsBO();
        queCondition.setKeywordtype("custname");
        queCondition.setQuekeyword("何仲元");

        queConditions.add(queCondition);
        req.setQueConditions(queConditions);

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(req));

        RequestWrapper request = new RequestWrapper();
        request.setApi("queServstInfo");
        request.setRequestBody(mapper.writeValueAsString(req));

        System.out.println(mapper.writeValueAsString(request));

        String requestStr = mapper.writeValueAsString(request);

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);
    }

    public static void querySalespkgKnow(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "querySalespkgKnow");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("catalogid", "2");
        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);
        String requestStr = jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }

    public static void getData(ObjectMapper jsonMapper, RemoteCall remoteCall)
            throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "getData");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("gcode", "PRV_AREA_BY_CITY");
        // bodyObj.put("scope", "1103476");
        bodyObj.put("mcode", "GZ");
        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);
        String requestStr = jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }

    public static void saveCustMarketing(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        // 1. knowid 1 营销标识
        // 2. custid 1 客户ID
        // 3. patchid 1 片区id
        // 4. pri 1 优先级 高，中，低
        // 5. name 1 客户名称
        // 6. linkphone 1 联系电话/手机
        // 7. houseid ? 住宅地址ID
        // 8. whladdr 1 住宅地址
        // 9. areaid 1 业务区

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "saveCustMarketing");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("knowid", "1");
        bodyObj.put("custid", "3600601273");
        bodyObj.put("patchid", "1719");
        bodyObj.put("pri", "H");
        bodyObj.put("name", "张 霞");
        bodyObj.put("linkphone", "13555554444");
        bodyObj.put("whladdr", "广东省广州市越秀区流花街道解放北路桂花岗四街13号之一403房");
        bodyObj.put("areaid", "100");

        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);
        String requestStr = jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }

    public static void dealCustMarketing(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        // 1. markid 1 营销主键
        // 2. dealType 1 处理类型 dealType=0,转派；
        // dealType=1,接单；
        // dealType =2,保存结果
        // 3. areamger ? 转派的网格经理ID 如果dealType=0-转派,此字段表不可为空;
        // 4. dealstatus ? 处理结果 如果dealType =2-保存结果，此字段不可为空
        // 5. result ? 结果说明 如果dealType=0-转派,此字段表示转派说明，不可为空;
        // 如果dealType =2-保存结果，此字段表示结果说明，不可为空

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "dealCustMarketing");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("markid", "1");
        bodyObj.put("dealType", "2");
        bodyObj.put("areamger", "2");
        bodyObj.put("result", "0");
        bodyObj.put("resultexp", "测试接单成功");

        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);
        String requestStr = jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }

    private static void queCustbank(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        // BizBindbankInterReqTest req = new BizBindbankInterReqTest();
        //
        // req.setAcctkind("2");
        //
        //
        // ObjectMapper mapper = new ObjectMapper();
        // System.out.println(mapper.writeValueAsString(req));
        //
        // RequestWrapper request = new RequestWrapper();
        // request.setApi("bizBindbank");
        // request.setRequestBody(mapper.writeValueAsString(req));
        //
        // System.out.println(mapper.writeValueAsString(request));
        //
        // String requestStr = mapper.writeValueAsString(request);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queCustbank");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("custid", "3600601273");

//        jsonObject.put("requestBody", bodyObj.toString());
        ISecurityService securityService = SecurityFactory.getSecurityService();
        String requestBody = securityService.encrypt(bodyObj.toString());
        jsonObject.put("requestBody", requestBody);

        System.out.println(jsonObject);
        String requestStr = jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }

    public static void bizBindbank(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        BizBindbankInterReqTest req = new BizBindbankInterReqTest();

        req.setAcctkind("2");
        req.setAcctname("测试账号1");
        req.setAcctno("477612501880138212");
        req.setAccttype("0");
//        req.setAction("2");
        // req.setApi(api);
        req.setBankid("760001238049");
        req.setAreaid("100");
        req.setBankcode("C");
        req.setBankid("760000294803");
        req.setCardno("440103196805316023");
        req.setCardtype("1");
        req.setCustid("3600601273");
        req.setDeductrate("0");
        req.setDefaulted("N"); 
        req.setDescrip("用户测试");
        req.setName("张 霞");
        req.setPatchid("1719");
        req.setTransacctno("477612501880138212");
        req.setWhladdr("广东省广州市越秀区流花街道解放北路桂花岗四街13号之一403房");
        BindbankServInfoBO serv = new BindbankServInfoBO();
        serv.setServid("760004543172");
        List<BindbankServInfoBO> servs = new ArrayList();
        servs.add(serv);
        req.setServs(servs);

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(req));

        RequestWrapper request = new RequestWrapper();
        request.setApi("bizBindbank");
        request.setRequestBody(mapper.writeValueAsString(req));

        System.out.println(mapper.writeValueAsString(request));

        String requestStr = mapper.writeValueAsString(request);

        // JSONObject jsonObject = new JSONObject();
        // jsonObject.put("api", "bizBindbank");
        // JSONObject bodyObj = new JSONObject();
        // bodyObj.put("custid", "3600601273");
        // //bodyObj.put("bankid", "3600601273");
        // bodyObj.put("acctname", "测试账号1");
        // bodyObj.put("bankcode", "C");
        // bodyObj.put("acctno", "477612501880138212");
        // bodyObj.put("transacctno", "477612501880138212");
        // bodyObj.put("acctkind", "2");
        // bodyObj.put("accttype", "0");
        // //bodyObj.put("deductrate", "");
        // bodyObj.put("defaulted", "N");
        // bodyObj.put("cardtype", "1");
        // bodyObj.put("cardno", "440103196805316023");
        // bodyObj.put("servs", "[{\"servid\":\"760004543172\"}]");
        //
        // bodyObj.put("areaid", "100");
        // //bodyObj.put("custid", "3600601273");
        // bodyObj.put("name", "张 霞");
        // bodyObj.put("patchid", "1719");
        // bodyObj.put("whladdr", "广东省广州市越秀区流花街道解放北路桂花岗四街13号之一403房");
        // bodyObj.put("descrip", "用户测试");
        //
        //
        // jsonObject.put("requestBody", bodyObj.toString());
        //
        // System.out.println(jsonObject);
        // String requestStr =jsonObject.toString();

        String response = HttpClientImpl.call(remoteCall, requestStr);
        System.out.println(response);

    }

    public static void bizFreshauth(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "bizFreshauth");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("servid", "3600524519");
        bodyObj.put("keyno", "40188428920");
        bodyObj.put("permark", "1");

        bodyObj.put("areaid", "100");
        bodyObj.put("custid", "3600601273");
        bodyObj.put("name", "张 霞");
        bodyObj.put("patchid", "1719");
        bodyObj.put("whladdr", "广东省广州市越秀区流花街道解放北路桂花岗四街13号之一403房");
        bodyObj.put("descrip", "用户点通测试");

        // bodyObj.put("logicdevno", "40188428920");
        bodyObj.put("logicstbno", "127301124600003954");
        // bodyObj.put("opkind", null);
        // bodyObj.put("pid", "587707");
        // bodyObj.put("days", "7");

        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);

        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);

    }

    public static void bizTempopen(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "bizTempopen");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("keyno", "40188428920");
        bodyObj.put("planid", "174797");
        bodyObj.put("areaid", "100");
        bodyObj.put("custid", "3600601273");
        bodyObj.put("name", "张 霞");
        bodyObj.put("patchid", "1719");
        bodyObj.put("whladdr", "广东省广州市越秀区流花街道解放北路桂花岗四街13号之一403房");
        bodyObj.put("descrip", "用户点通测试");
        bodyObj.put("servid", "3600524519");
        // bodyObj.put("logicdevno", "40188428920");
        bodyObj.put("logicstbno", "127301124600003954");
        bodyObj.put("planname", "欠费用户点通");
        bodyObj.put("pids", "587707");
        bodyObj.put("days", "7");

        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);

        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);

    }

    public static void queTempopenPlan(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queTempopenPlan");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("pagesize", "5");
        bodyObj.put("currentPage", "1");
        bodyObj.put("areaid", "100");

        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);

        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);

    }

    public static void applyInstall(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "applyInstall");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("dealType", "0");
        bodyObj.put("custid", "3600041634");
        bodyObj.put("name", "罗华荣");
        bodyObj.put("cardtype", "1");
        bodyObj.put("cardno", "8002003224584610");
        bodyObj.put("linkphone", "13888888888");
        bodyObj.put("areaid", 188188);
        bodyObj.put("houseid", 201);
        bodyObj.put("patchid", 1);
        bodyObj.put("whladdr", "天河路140号703房");
        bodyObj.put("knowids", "1,2,3");
        bodyObj.put("descrip", "业务说明");
        bodyObj.put("logicdevno", "8002003224584610");
        bodyObj.put("stbno", "800200");
        bodyObj.put("bankcode", "A");
        bodyObj.put("acctkind", "0");
        bodyObj.put("acctno", "476666666666666");
        bodyObj.put("accttype", "0");
        bodyObj.put("acctname", "罗华荣");
        bodyObj.put("predate", "2015-11-01T14:12:35");

        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);

        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);

    }

    public static void createCust(ObjectMapper jsonMapper, RemoteCall remoteCall)
            throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "createCust");

        JSONObject bodyObj = new JSONObject();
        bodyObj.put("areaid", "100");
        bodyObj.put("name", "测试接口3");
        bodyObj.put("cardtype", "1");
        bodyObj.put("cardno", "1234554321");
        // bodyObj.put("phone", "");
        bodyObj.put("mobile", "13344445555");

        // JSONObject bodyObj = new JSONObject();
        // bodyObj.put("areaid", "815");
        // bodyObj.put("name", "test");
        // bodyObj.put("cardtype", "1");
        // bodyObj.put("cardno", "123456789123456789");
        // bodyObj.put("phone", "13800138000");
        // bodyObj.put("mobile", "13800138000");

        ObjectMapper mapper = new ObjectMapper();
        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);

        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);

    }

    public static void queryResHouse(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        // {"rightControl":"N","pagesize":"10","addr":"客村","currentPage":"1","city":"GZ"}

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queryResHouse");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("addr", "客村");
        bodyObj.put("city", "GZ");
        bodyObj.put("pagesize", "10");
        bodyObj.put("currentPage", "1");
        bodyObj.put("rightControl", "N");

        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);

        RequestWrapper requestWrapper = new RequestWrapper();
        requestWrapper = jsonMapper.readValue(jsonObject.toString(),
                RequestWrapper.class);
        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println("123:"+response);

    }

    public static void queryDepartment(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        // --3.1.1.查询用户部门信息接口【api=queryDepartment】
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queryDepartment");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("loginname", "InterTest");
        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);

        RequestWrapper requestWrapper = new RequestWrapper();
        requestWrapper = jsonMapper.readValue(jsonObject.toString(),
                RequestWrapper.class);
        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);
    }

    public static void queryCustInfo(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queryCustInfo");
        JSONObject bodyObj = new JSONObject();
        // bodyObj.put("identifierType", "1"); //手机号
        // bodyObj.put("identifier", "13800138000");

        // bodyObj.put("identifierType", "0");
        // bodyObj.put("identifier", "1234554321");

        bodyObj.put("identifierType", "5");
        bodyObj.put("identifier", "3601042564");

        bodyObj.put("city", "GZ");
        jsonObject.put("requestBody", bodyObj.toString());
        // jsonObject.put("requestBody", "\"SYS_ACCT_TYPE\"");

        System.out.println(jsonObject);

        RequestWrapper requestWrapper = new RequestWrapper();
        requestWrapper = jsonMapper.readValue(jsonObject.toString(),
                RequestWrapper.class);
        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);

    }

    public static void login(ObjectMapper jsonMapper, RemoteCall remoteCall)
            throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "login");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("loginname", "WGYX_NG");
        bodyObj.put("password", "123");
        bodyObj.put("deptid", "3004151");
        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);

        RequestWrapper requestWrapper = new RequestWrapper();
        requestWrapper = jsonMapper.readValue(jsonObject.toString(),
                RequestWrapper.class);
        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);
    }

    public static void getOperMenu(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "getOperMenu");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("operid", "1");
        bodyObj.put("roleid", "1");
        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);

        RequestWrapper requestWrapper = new RequestWrapper();
        requestWrapper = jsonMapper.readValue(jsonObject.toString(),
                RequestWrapper.class);
        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);
    }

    public static void queryCustMarketing(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queryCustMarketing");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("pagesize", "2");
        bodyObj.put("currentPage", "1");
        // bodyObj.put("batchno", "111112");
        // bodyObj.put("custname", "张");
        bodyObj.put("addr", "78号");
        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);

        RequestWrapper requestWrapper = new RequestWrapper();
        requestWrapper = jsonMapper.readValue(jsonObject.toString(),
                RequestWrapper.class);
        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);
    }

    public static void queryMarketBatch(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queryMarketBatch");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("pagesize", "2");
        bodyObj.put("currentPage", "2");
        bodyObj.put("batchno", "111112");
        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);

        RequestWrapper requestWrapper = new RequestWrapper();
        requestWrapper = jsonMapper.readValue(jsonObject.toString(),
                RequestWrapper.class);
        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);
    }

    public static void getInstallBasedata(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "getInstallBasedata");
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("city", "GZ");
        // bodyObj.put("mcode", "0");
        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);

        RequestWrapper requestWrapper = new RequestWrapper();
        requestWrapper = jsonMapper.readValue(jsonObject.toString(),
                RequestWrapper.class);
        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);

    }

    //
    // LoginParam loginUser = new LoginParam();
    // loginUser.setLoginname("admin");
    // loginUser.setPassword("123456");
    // loginUser.setDeptid(1L);
    //
    // ObjectMapper mapper = new ObjectMapper();
    // System.out.println(mapper.writeValueAsString(loginUser));
    //
    // RequestWrapper request = new RequestWrapper();
    // request.setApi("login");
    // request.setRequestBody(mapper.writeValueAsString(loginUser));
    //
    // System.out.println(mapper.writeValueAsString(request));
    //

    /*
     * JSONObject jsonObject = new JSONObject(); jsonObject.put("api",
     * "queryUserInfo"); JSONObject bodyObj = new JSONObject();
     * bodyObj.put("icno", "8002003224584610"); jsonObject.put("requestBody",
     * bodyObj.toString()); //jsonObject.put("requestBody",
     * "\"SYS_ACCT_TYPE\"");
     * 
     * System.out.println(jsonObject);
     * 
     * RequestWrapper requestWrapper = new RequestWrapper(); requestWrapper =
     * jsonMapper.readValue(jsonObject.toString(), RequestWrapper.class); String
     * response = HttpClientImpl.call(remoteCall, jsonObject.toString());
     * System.out.println(response);
     */

    private static void setRemoteCallIpPort(RemoteCall remoteCall,
            String callIpPortType) throws Exception {
        /* 主机地址格式ip:port */
        String[] callIpPort = callIpPortType.split(":");
        if (callIpPort.length < 2) {
            throw new Exception("主机地址有误，请检查");
        }

        remoteCall.setRemoteIP(callIpPort[0]);
        remoteCall.setRemotePort(Integer.valueOf(callIpPort[1]));

    }
    public static void editCust(ObjectMapper jsonMapper, RemoteCall remoteCall)
            throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "editCust");
 
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("id", "760000944479");
        bodyObj.put("linkman", "潘顺和44");
        bodyObj.put("servkind", "1"); 
        bodyObj.put("cardno", "44010619790423561x");                 
        bodyObj.put("cardtype", "1");        
        bodyObj.put("name", "潘顺和"); 
        bodyObj.put("custtype", "0");         
        bodyObj.put("subtype", "00"); 
        bodyObj.put("areaid", "100");
        bodyObj.put("mobile", "13928708789");
        bodyObj.put("grade", "2");
        bodyObj.put("memo", "13928708789"); 
        bodyObj.put("phone", "13928708789");
        bodyObj.put("birth", "2000-01-01");
        bodyObj.put("grade", "1");
        bodyObj.put("interest", "打球");
        bodyObj.put("trade", "1");
        bodyObj.put("occupation", "工程师");
        bodyObj.put("company", "CY");


//        bodyObj.put("name", "测试接口3");;
//        bodyObj.put("cardtype", "1");
//        bodyObj.put("cardno", "1234554321");
//        // bodyObj.put("phone", "");
//        bodyObj.put("mobile", "13344445555");

        // JSONObject bodyObj = new JSONObject();
        // bodyObj.put("areaid", "815");
        // bodyObj.put("name", "test");
        // bodyObj.put("cardtype", "1");
        // bodyObj.put("cardno", "123456789123456789");
        // bodyObj.put("phone", "13800138000");
        // bodyObj.put("mobile", "13800138000");

        ObjectMapper mapper = new ObjectMapper();
        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);

        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);

    }    

    public static void getCustPubInfo(ObjectMapper jsonMapper, RemoteCall remoteCall)
            throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "getCustPubInfo");

        JSONObject bodyObj = new JSONObject();
        bodyObj.put("id", "760000944479"); 

        ObjectMapper mapper = new ObjectMapper();
        jsonObject.put("requestBody", bodyObj.toString());

        System.out.println(jsonObject);

        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);

    }    
    public static void queCustDevInfo(ObjectMapper jsonMapper,RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queCustDevInfo");
        JSONObject bodyObj = new JSONObject();
        // bodyObj.put("identifierType", "1"); //手机号
        // bodyObj.put("identifier", "13800138000");

        // bodyObj.put("identifierType", "0");
        // bodyObj.put("identifier", "1234554321");

        bodyObj.put("custid", "3600781212"); 

//        bodyObj.put("city", "GZ");
        jsonObject.put("requestBody", bodyObj.toString());
        // jsonObject.put("requestBody", "\"SYS_ACCT_TYPE\"");

        System.out.println(jsonObject);

        RequestWrapper requestWrapper = new RequestWrapper();
        requestWrapper = jsonMapper.readValue(jsonObject.toString(),
                RequestWrapper.class);
        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);

    }
    public static void getCustFittingInfo(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "getCustFittingInfo");
        JSONObject bodyObj = new JSONObject();
        // bodyObj.put("identifierType", "1"); //手机号
        // bodyObj.put("identifier", "13800138000");

        // bodyObj.put("identifierType", "0");
        // bodyObj.put("identifier", "1234554321");

//        bodyObj.put("custid", "5600529911");
        bodyObj.put("kind", "6"); 
        bodyObj.put("areaid", "100"); 



//        bodyObj.put("city", "GZ");
        jsonObject.put("requestBody", bodyObj.toString());
        // jsonObject.put("requestBody", "\"SYS_ACCT_TYPE\"");

        System.out.println(jsonObject);

        RequestWrapper requestWrapper = new RequestWrapper();
        requestWrapper = jsonMapper.readValue(jsonObject.toString(),
                RequestWrapper.class);
        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);

    }    

    public static void checkNewStbDev(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "checkNewStbDev");
        JSONObject bodyObj = new JSONObject(); 

        bodyObj.put("custid","3601532721");
        bodyObj.put("oldkind","1");
        bodyObj.put("newdevno","8002002477015413");
        bodyObj.put("reason","1");
        bodyObj.put("olddevno","8002002477029877");
        bodyObj.put("newuseprop","4");
        bodyObj.put("rectype","1");
        bodyObj.put("serviceFlag","0");
        bodyObj.put("newfees","0");
        


        //326686
        jsonObject.put("requestBody", bodyObj.toString());
        // jsonObject.put("requestBody", "\"SYS_ACCT_TYPE\"");

        System.out.println(jsonObject);

        RequestWrapper requestWrapper = new RequestWrapper();
        requestWrapper = jsonMapper.readValue(jsonObject.toString(),
                RequestWrapper.class);
        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);

    }   
    public static void saveTmp(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "saveTmp");
        JSONObject bodyObj = new JSONObject();
        ChgDevTmpReq chgDevTmpReq = new ChgDevTmpReq();
        PrdPriceBO newpricebo= new PrdPriceBO();
        CustChangeDevBO bo  = new CustChangeDevBO();
        bo.setCdevid(3602940211l);
        bo.setCustid(3601532721l);
        bo.setDamagereason("1");
        bo.setDepart(2866960l);
        
        bo.setDevChangeRoc("M");
        bo.setInputtype("I");
        bo.setJnjure("0");
        bo.setNewdevno("8002002477015413");
        bo.setNewownertype("0");
        bo.setNewsubkind("110000");
        bo.setNewuseprop("4");
        bo.setOldFeeFlag("0");
        bo.setOldkind("1");
        bo.setOldsubkind("110000");
        bo.setPartlen(0L);
        bo.setPid("326686");
        bo.setReason("1");
        bo.setRecycleType("1");
        bo.setResid(3600369596l);
        bo.setType("0");
        bo.setNewpricebo(null); 
////        newpricebo.setFeecode("AAys");             
////        newpricebo.setIfeecode("PA35");    
////        newpricebo.setRfeecode("QS18"); 
//        newpricebo.setSpay(0.0d); 
        
        chgDevTmpReq.setAreaid("100");
        chgDevTmpReq.setBuff(null);
        chgDevTmpReq.setCustid("3600390521");
        chgDevTmpReq.setDescrip("测试产品订购");
        chgDevTmpReq.setGdno(null);
        chgDevTmpReq.setGdnoid(null);
        chgDevTmpReq.setHouseid("201");
        
        chgDevTmpReq.setIscrtorder("Y");
        chgDevTmpReq.setName("钟卓璿");
        chgDevTmpReq.setPatchid("1758");
        chgDevTmpReq.setWhladdr("广东省广州市海珠区瑞宝街道嘉鸿花园二街4号（金玉堂A座）1605房");
        
        chgDevTmpReq.setBo(bo);
        System.out.println(JSONUtil.serialize(chgDevTmpReq));
        String str = JSONUtil.serialize(chgDevTmpReq);
        jsonObject.put("requestBody",str);
        System.out.println(jsonObject);

        RequestWrapper requestWrapper = new RequestWrapper();
        requestWrapper = jsonMapper.readValue(jsonObject.toString(),
                RequestWrapper.class);
        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);

    }        
    
    
    public static void queryBizLogByPage(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queryBizLogByPage");
        JSONObject bodyObj = new JSONObject(); 

        PersonCustPtReq custPtReq = new PersonCustPtReq();
        QueryBizLogParam bizLogParam  = new QueryBizLogParam();
//        bizLogParam.setSerialno("638787900000000007AV");        
        bizLogParam.setInfo("3600193817");

        QuePage page = new QuePage();
        page.setPageNo(1);
        page.setPageSize(50); 
        page.setAutoCount(true);
        page.setOrder("asc");
        page.setOrderBy("optime");
        
        
        custPtReq.setPage(page);
        custPtReq.setParam(bizLogParam);
        
        

        //326686
        jsonObject.put("requestBody", JSONUtil.serialize(custPtReq));
        // jsonObject.put("requestBody", "\"SYS_ACCT_TYPE\"");

        System.out.println(jsonObject);

        RequestWrapper requestWrapper = new RequestWrapper();
        requestWrapper = jsonMapper.readValue(jsonObject.toString(),
                RequestWrapper.class);
        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);

    } 

    public static void chgdevsave(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "chgdevsave");
        JSONObject bodyObj = new JSONObject(); 

        ChgDevWGReq custPtReq = new ChgDevWGReq();   
        custPtReq.setCustid("3601532721");
        custPtReq.setOrderid("336236");
        custPtReq.setInstalltype("0");
        

        //326686
        jsonObject.put("requestBody", JSONUtil.serialize(custPtReq));
        // jsonObject.put("requestBody", "\"SYS_ACCT_TYPE\"");

        System.out.println(jsonObject);

        RequestWrapper requestWrapper = new RequestWrapper();
        requestWrapper = jsonMapper.readValue(jsonObject.toString(),
                RequestWrapper.class);
        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);

    }  

    public static void queCustMarktInfo(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queCustMarktInfo");
        JSONObject bodyObj = new JSONObject(); 

        QueCustMarktInfoReq custPtReq = new QueCustMarktInfoReq();    
        custPtReq.setLogicno("3601487518");
        custPtReq.setIscust("N");
        QuePage page = new QuePage();
        page.setPageNo(1);
        page.setPageSize(25); 
        page.setAutoCount(true);
        page.setOrder("asc");
        page.setOrderBy("operator"); 
        
        custPtReq.setNetattr("1");
        custPtReq.setPatchids("1505,1505");

        custPtReq.setPagesize("10");
        custPtReq.setCurrentPage("1");
        //326686
        jsonObject.put("requestBody", JSONUtil.serialize(custPtReq));
        // jsonObject.put("requestBody", "\"SYS_ACCT_TYPE\"");

        System.out.println(jsonObject);

        RequestWrapper requestWrapper = new RequestWrapper();
        requestWrapper = jsonMapper.readValue(jsonObject.toString(),
                RequestWrapper.class);
        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);

    }     

    public static WorkAssignService getCallCenterWS() throws Exception{
		WorkAssignService sw = (WorkAssignService) new WorkAssignServiceServiceLocator().getWorkAssignService();
		String str = (String)sw.operateOrder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?><request><action>orderList</action><username>cc_datang</username><passwd>26b2def2a6a5f1ef1a0672d380f931ea</passwd><request-content><custid>760001091835</custid><category>appealWorkflow</category><status>0</status></request-content></request>");
    	StringBuffer sb  = new StringBuffer();

//		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>")
//		.append(" <request>")
//		.append("<action>orderDetail</action>")
//		.append("<username>cc_datang</username>")
//		.append("<passwd>26b2def2a6a5f1ef1a0672d380f931ea</passwd>")
//		.append("<request-content>")
//		.append("<orderid>")
//		.append("T1510127544955")
//		.append("</orderid>")
//		.append("<standby></standby>")
//		.append("</request-content>")
//		.append(" </request>");
		str = (String)sw.operateOrder(sb.toString());

		
		OrderDetailResponse Order = (OrderDetailResponse)XmlConverter.toEntity(str,new Class[]{OrderDetailResponse.class,OrderTasks.class,OrderRet.class});

		System.out.println(Order.getRetCode());
//		toEntity(str);
		
		return sw;
	}  
    public static Channel2IomCallService getOssWS() throws Exception{
		Channel2IomCallService sw = (Channel2IomCallService) new Channel2IomCallServiceServiceLocator().getChannelOrderService();
        WorkAssignServiceOrderListRequest assignServiceOrderListRequest= new WorkAssignServiceOrderListRequest();
        assignServiceOrderListRequest.setCustid("6901616923");
        assignServiceOrderListRequest.setStatus("1");        
        assignServiceOrderListRequest.setEffDate("2015-09-02 15:00:00");
        assignServiceOrderListRequest.setExpDate("2015-09-29 15:00:00");
        assignServiceOrderListRequest.setCategory("10S");
        
        toCompactXML(assignServiceOrderListRequest);
        String xml = "<request><action>orderList</action>"+toCompactXML(assignServiceOrderListRequest)+"</request>";
//         String xml = "<request><action>orderList</action><request-content><custid>6901223236</custid><category>10S</category><status>0</status></request-content></request>";
       System.out.println(xml); 
       String retxml = sw.queryOrderFromChannel(xml);
//		res = (OrderDetailResponse)XmlConverter.toEntity(retxml,new Class[]{OrderDetailResponse.class,OrderTasks.class,OrderRet.class});

    	

       OrderResponse res = (OrderResponse)XmlConverter.toEntity(retxml,new Class[]{OrderResponse.class,OrderDetail.class,Order.class});

//		OrderDetailResponse Order = (OrderDetailResponse)XmlConverter.toEntity(retxml,new Class[]{OrderDetailResponse.class,OrderTasks.class,OrderRet.class});

		System.out.println(retxml);
//		toEntity(str);
		
		return sw;
	}      
 
    public static void toEntity(String inputXML){
    	StringBuffer sb  = new StringBuffer();
    	sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
    	.append("<response>")
    	.append("   <retCode>00000</retCode>")
    	.append("   <retMsg>操作成功!</retMsg>")
    	.append("   <response-content>")
    	.append("     <orders>")
    	.append("       <order>")
    	.append("         <orderid>T1510127544955</orderid>")
    	.append("         <category>投诉建议</category>")
    	.append("         <subtype>意见|建议/政策</subtype>")
    	.append("         <orderDate>2015-10-12 10:06:56</orderDate>")
    	.append("         <status>处理中</status>")
    	.append("         <custId>123123</custId>")
    	.append("         <standby1></standby1>")
    	.append("         <standby2></standby2>")
    	.append("         <standby3></standby3>")
    	.append("         <limitDate></limitDate>")
    	.append("         <alertDate></alertDate>")
    	.append("       </order>")
    	.append("     </orders>")
    	.append("   </response-content>")
    	.append(" </response>")
    	;

    	
    	inputXML = sb.toString();
        //注册使用了注解的VO
        OrderResponse Order = (OrderResponse)XmlConverter.toEntity(inputXML,new Class[]{OrderResponse.class,OrderDetail.class,Order.class});
        System.out.println(Order.getResponse().getOrders().get(0).getOrderid());
        
    }
    
    public static void toXML(){
        XStream xStream = new XStream();
        OrderRequest orderDetail  = new OrderRequest();
        orderDetail.setAction("orderList");
        orderDetail.setUsername("cc_datang");
        orderDetail.setPasswd("26b2def2a6a5f1ef1a0672d380f931ea");
       
        QueOrder queOrder = new QueOrder();
        queOrder.setCustid("760001091835");
        queOrder.setCategory("appealWorkflow");
        queOrder.setStatus("0");
        
         orderDetail.setQueorder(queOrder);
        xStream.setMode(XStream.NO_REFERENCES);
        System.out.println(XmlConverter.toXML(new Class[]{OrderRequest.class,QueOrder.class}, orderDetail));;

    }    

    public static void queCallCenterOrder(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queCallCenterOrder");
        JSONObject bodyObj = new JSONObject(); 
        QueOrderReq queOrderReq = new QueOrderReq();
        OrderRequest orderDetail  = new OrderRequest();
        orderDetail.setAction("orderList");
        orderDetail.setUsername("cc_datang");
        orderDetail.setPasswd("26b2def2a6a5f1ef1a0672d380f931ea");
       
        QueOrder queOrder = new QueOrder();
        queOrder.setCustid("3600043469");
        queOrder.setCategory("appealWorkflow");
        queOrder.setStatus("0");
        orderDetail.setQueorder(queOrder);
        queOrderReq.setOrderreq(orderDetail);
//        queOrderReq.setApi("queCallCenterOrder");
        jsonObject.put("requestBody", JSONUtil.serialize(queOrderReq));

        System.out.println(jsonObject);
        
        

        RequestWrapper requestWrapper = new RequestWrapper();
        requestWrapper = jsonMapper.readValue(jsonObject.toString(),
                RequestWrapper.class);
        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);

    } 
    public static void  test_wsorder() throws RemoteException, ServiceException{
        QueOrderReq queOrderReq = new QueOrderReq();
        OrderRequest orderDetail  = new OrderRequest();
        orderDetail.setAction("orderList");
        orderDetail.setUsername("cc_datang");
        orderDetail.setPasswd("26b2def2a6a5f1ef1a0672d380f931ea");
       
        QueOrder queOrder = new QueOrder();
        queOrder.setCustid("6900211586");
        queOrder.setCategory("appealWorkflow");
        queOrder.setStatus("1");
        
        orderDetail.setQueorder(queOrder);
        queOrderReq.setOrderreq(orderDetail);
    	String xml = XmlConverter.toXML(new Class[]{OrderRequest.class,QueOrder.class}, queOrderReq.getOrderreq());
    	StringBuffer sb = new StringBuffer();
    	sb.append("<request>")
    	.append("   <action>orderList</action>")
    	.append("   <username>cc_datang</username>")
    	.append("   <passwd>26b2def2a6a5f1ef1a0672d380f931ea</passwd>")
    	.append("   <request-content>")
    	.append("     <custid>5600669641</custid>")
    	.append("     <category>appealWorkflow</category>")
    	.append("     <status>0</status>")
    	.append("     <effDate>2015-01-02 15:00:00</effDate>")
    	.append("     <expDate>2015-09-29 15:00:00</expDate>")
    	.append("   </request-content>")
    	.append(" </request>");

    	StringBuffer reqxml  = new StringBuffer();
    	reqxml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//    	reqxml.append(xml);
    	reqxml.append(sb.toString());
    	System.out.println(reqxml);
    	//调用客服接口，返回retxml
		WorkAssignService sw = (WorkAssignService) new WorkAssignServiceServiceLocator().getWorkAssignService();

    	String retxml = sw.operateOrder(reqxml.toString());
    	System.out.println(retxml);
    	//转换返回结果
    	OrderResponse retres = (OrderResponse)XmlConverter.toEntity(retxml,new Class[]{OrderResponse.class,OrderDetail.class,Order.class});
//    	BeanUtils.copyProperties(res, retres);
    	
    }
    public static void queCallCenterOrderDetail(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queCallCenterOrderDetail");
        JSONObject bodyObj = new JSONObject(); 
        QueOrderDetailReq queOrderReq = new QueOrderDetailReq();
        queOrderReq.setOrderid("T1510127544955");
        jsonObject.put("requestBody", JSONUtil.serialize(queOrderReq));

        System.out.println(jsonObject);
        

        RequestWrapper requestWrapper = new RequestWrapper();
        requestWrapper = jsonMapper.readValue(jsonObject.toString(),
                RequestWrapper.class);
        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);

    }  
    public static void queOssOrder(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queOssOrder");
        JSONObject bodyObj = new JSONObject(); 
        WorkAssignServiceOrderListRequest assignServiceOrderListRequest= new WorkAssignServiceOrderListRequest();
        assignServiceOrderListRequest.setCustid("3600381684");
        assignServiceOrderListRequest.setStatus("0");        
//        assignServiceOrderListRequest.setEffDate("2015-09-02 15:00:00");
//        assignServiceOrderListRequest.setExpDate("2015-09-29 15:00:00");
        assignServiceOrderListRequest.setCategory("10S");
        
        jsonObject.put("requestBody", JSONUtil.serialize(assignServiceOrderListRequest));

        System.out.println(jsonObject);
        

        RequestWrapper requestWrapper = new RequestWrapper();
        requestWrapper = jsonMapper.readValue(jsonObject.toString(),
                RequestWrapper.class);
        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);

    }  
    public static void queOssOrderDetail(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queOssOrderDetail");
        JSONObject bodyObj = new JSONObject(); 
        QueOrderDetailReq queOrderReq = new QueOrderDetailReq();
        queOrderReq.setOrderid("27273575");
        jsonObject.put("requestBody", JSONUtil.serialize(queOrderReq));

        System.out.println(jsonObject);
        

        RequestWrapper requestWrapper = new RequestWrapper();
        requestWrapper = jsonMapper.readValue(jsonObject.toString(),
                RequestWrapper.class);
        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);

    }      
	public static String toCompactXML(Object in) {
		StringWriter out = new StringWriter();
		CompactWriter writer = new CompactWriter(out);
		String result = "";
		try {
			XStream xstream = new XStream();
			xstream.marshal(in, writer);
			Pattern pattern = Pattern.compile(" class=\"[^\"]+\"");
			Matcher matcher = pattern.matcher(out.toString());
			result = matcher.replaceAll("");

			Pattern p = Pattern.compile("com+[.]+maywide+[^>]*");
			Matcher m = p.matcher(result);
			result = m.replaceAll("request-content");
		} finally {
			writer.flush();
		}

		return result;
	}    
	public static void BIZ_ASYNC_ORDER(ObjectMapper jsonMapper,
	            RemoteCall remoteCall) throws Exception {

	        JSONObject jsonObject = new JSONObject();
	        jsonObject.put("api", "installOrderSync");
	        JSONObject bodyObj = new JSONObject(); 
	        InstallOrderAsyncReq installOrderAsyncReq = new InstallOrderAsyncReq();                                    
	        installOrderAsyncReq.setOrderid        ("1002318")         ;                   
	        jsonObject.put("requestBody", JSONUtil.serialize(installOrderAsyncReq));

	        System.out.println(jsonObject);
	        

	        RequestWrapper requestWrapper = new RequestWrapper();
	        requestWrapper = jsonMapper.readValue(jsonObject.toString(),
	                RequestWrapper.class);
	        String response = HttpClientImpl
	                .call(remoteCall, jsonObject.toString());
	        System.out.println(response);
	}
	public static void queServExpire(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queServExpire"); 
        QueServExpireReq req = new QueServExpireReq();   
        req.setPatchids   ("30675");
        req.setQuetype    ("0");
        req.setCustid     ("760000391927");
//        req.setCustname   ("");
//        req.setLogicdevno ();
        req.setObjid      ("326147");
        req.setSdate      ("2015-01-07");
        req.setEdate      ("2015-01-08");
        req.setPagesize   ("10");
        req.setCurrentpage("1");
        jsonObject.put("requestBody", JSONUtil.serialize(req));

        System.out.println(jsonObject);
        

        RequestWrapper requestWrapper = new RequestWrapper();
        requestWrapper = jsonMapper.readValue(jsonObject.toString(),
                RequestWrapper.class);
        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);
	}
	public static void queSoonExpcust(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queSoonExpcust"); 
        QueSoonExpcustReq req = new QueSoonExpcustReq(); 
 
        req.setServid   ("760000793720");
        req.setObjtype  ("1");  
        req.setCustid     ("760000391927"); 
        req.setObjid      ("326147"); 
        jsonObject.put("requestBody", JSONUtil.serialize(req));

        System.out.println(jsonObject);
        

        RequestWrapper requestWrapper = new RequestWrapper();
        requestWrapper = jsonMapper.readValue(jsonObject.toString(),
                RequestWrapper.class);
        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);
	}
	public static void queServExpirenum(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "queServExpirenum"); 
        QueServExpireNumReq req = new QueServExpireNumReq();   
        req.setPatchids   ("86806");
        req.setQuetype    ("1");
        req.setObjid      ("587707"); 
        jsonObject.put("requestBody", JSONUtil.serialize(req));

        System.out.println(jsonObject);
        

        RequestWrapper requestWrapper = new RequestWrapper();
        requestWrapper = jsonMapper.readValue(jsonObject.toString(),
                RequestWrapper.class);
        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);
	}	
		
	public static void bizAttprdProc(ObjectMapper jsonMapper,
            RemoteCall remoteCall) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("api", "bizAttprdProc"); 
        BizAttprdProcReq req = new BizAttprdProcReq();   
        req.setAttkind("1");
        req.setCustid("760000391927");
        req.setServid("760000793720");
        req.setLogicdevno("1231231231231");
        req.setObjtype("1");
        req.setObjid("326147");
        req.setEdate("2015-01-07");
        req.setOpt("0");
        jsonObject.put("requestBody", JSONUtil.serialize(req));

        System.out.println(jsonObject);
        

        RequestWrapper requestWrapper = new RequestWrapper();
        requestWrapper = jsonMapper.readValue(jsonObject.toString(),
                RequestWrapper.class);
        String response = HttpClientImpl
                .call(remoteCall, jsonObject.toString());
        System.out.println(response);
	}		
}
