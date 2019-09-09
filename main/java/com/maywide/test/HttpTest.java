package com.maywide.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.LoginUser;
import com.maywide.biz.core.pojo.RequestWrapper;
import com.maywide.biz.core.pojo.ResponseWrapper;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.market.entity.MarketBatch;
import com.maywide.biz.prd.entity.Catalog;
import com.maywide.biz.prd.entity.CatalogItem;
import com.maywide.biz.prd.entity.SalespkgKnow;
import com.maywide.biz.prv.bo.LoginParam;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.security.encrypt.ISecurityService;
import com.maywide.core.security.encrypt.SecurityFactory;
import com.maywide.core.security.remote.HttpClientImpl;
import com.maywide.core.security.remote.RemoteCall;
import com.maywide.core.util.DateUtils;

public class HttpTest {
	public static void main(String[] args) {
		try {
			ISecurityService sservice = SecurityFactory.getSecurityService();
			
			ObjectMapper jsonMapper = null;
			jsonMapper = new ObjectMapper();
            jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			
	        RemoteCall remoteCall = new RemoteCall();
	        remoteCall.setInterfaceName("test22");
	        remoteCall.setProtocol("HTTP");
	        remoteCall.setRemoteIP("127.0.0.1");
	        remoteCall.setRemotePort(8081);
	        remoteCall.setCallUrl("/moss/api");
	        
	        /*ObjectMapper jsonMapper2 = new ObjectMapper();
	        jsonMapper2.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	        JSONObject bodyObj = new JSONObject(); 
	        bodyObj.put("loginname", "admin");
	        RequestWrapper requestWrapper2 = jsonMapper.readValue("badmin", RequestWrapper.class);
	        requestWrapper2.getRequestBody();*/
	        
	        /*JSONObject jsonObject = new JSONObject(); 
	        jsonObject.put("api", "login");
	        JSONObject bodyObj = new JSONObject(); 
	        bodyObj.put("loginname", "000000");
	        bodyObj.put("password", "111111");
	        jsonObject.put("requestBody", bodyObj.toString());*/
	        
	        /*JSONObject jsonObject = new JSONObject(); 
	        jsonObject.put("api", "queryTest22");
	        jsonObject.put("requestBody", 35);
	        
	        System.out.println(jsonObject);
	        
	        RequestWrapper requestWrapper = new RequestWrapper();
            requestWrapper = jsonMapper.readValue(jsonObject.toString(), RequestWrapper.class);
	        System.out.println(requestWrapper.getApi());
	        String response = HttpClientImpl.call(remoteCall, jsonObject.toString());
	        System.out.println(response);*/
	        
	        /*LoginParam loginUser = new LoginParam();
	        loginUser.setLoginname("admin");
	        loginUser.setPassword("123456");
	        loginUser.setDeptid(1L);
	        
	        ObjectMapper mapper = new ObjectMapper();
	        System.out.println(mapper.writeValueAsString(loginUser));
	        
	        RequestWrapper request = new RequestWrapper();
	        request.setApi("login");
	        request.setRequestBody(mapper.writeValueAsString(loginUser));
	        
	        System.out.println(mapper.writeValueAsString(request));
	        
	        ResponseWrapper response = new ResponseWrapper();
	        ReturnInfo returnInfo = new ReturnInfo();
	        returnInfo.setCode(0L);
	        returnInfo.setMessage("操作成功");
	        
	        List<Long> list = new ArrayList<Long>();
	        list.add(99l);
	        
	        LoginInfo loginInfo = new LoginInfo();
	        loginInfo.setOperid(1l);
	        loginInfo.setLoginname("admin");
	        loginInfo.setName("王杰");
	        loginInfo.setCity("j0");
	        loginInfo.setCityname("江门市");
	        loginInfo.setAreaid(1063351l);
	        loginInfo.setAreaname("江门市外海镇");
	        loginInfo.setDepartCount(1);
	        loginInfo.setDeptid(188188l);
	        loginInfo.setDeptname("大塘营业厅");
	        loginInfo.setRoleid(3l);
	        loginInfo.setRolename("客服人员");
	        loginInfo.setRootmenuid(list);
	        
	        response.setReturnInfo(returnInfo);
	        response.setResponseBody(mapper.writeValueAsString(loginInfo));
	        
	        System.out.println(mapper.writeValueAsString(response));*/
	        
	        /*LoginParam loginUser = new LoginParam();
	        loginUser.setLoginname("admin");
	        loginUser.setPassword("123456");
	        loginUser.setDeptid(1L);
	        
	        ObjectMapper mapper = new ObjectMapper();
	        System.out.println(mapper.writeValueAsString(loginUser));
	        
	        RequestWrapper request = new RequestWrapper();
	        request.setApi("login");
	        request.setRequestBody(mapper.writeValueAsString(loginUser));
	        
	        System.out.println(mapper.writeValueAsString(request));
	        
	        ResponseWrapper response = new ResponseWrapper();
	        ReturnInfo returnInfo = new ReturnInfo();
	        returnInfo.setCode(0L);
	        returnInfo.setMessage("操作成功");
	        
	        List<Long> list = new ArrayList<Long>();
	        list.add(99l);
	        
	        LoginInfo loginInfo = new LoginInfo();
	        loginInfo.setOperid(1l);
	        loginInfo.setLoginname("admin");
	        loginInfo.setName("王杰");
	        loginInfo.setCity("j0");
	        loginInfo.setCityname("江门市");
	        loginInfo.setAreaid(1063351l);
	        loginInfo.setAreaname("江门市外海镇");
	        loginInfo.setDepartCount(1);
	        loginInfo.setDeptid(188188l);
	        loginInfo.setDeptname("大塘营业厅");
	        loginInfo.setRoleid(3l);
	        loginInfo.setRolename("客服人员");
	        loginInfo.setRootmenuid(list);
	        
	        response.setReturnInfo(returnInfo);
	        response.setResponseBody(mapper.writeValueAsString(loginInfo));
	        
	        System.out.println(mapper.writeValueAsString(response));*/
	        
	        /*List<PrvSysparam> list = new ArrayList<PrvSysparam>();
	        PrvSysparam param = new PrvSysparam();
	        param.setId(10L);
	        param.setGcode("PRV_CITY");
	        param.setMcode("1");
	        param.setMname("广州");
	        list.add(param);
	        
	        param = new PrvSysparam();
	        param.setId(119967L);
	        param.setGcode("PRV_CITY");
	        param.setMcode("DG");
	        param.setMname("东莞");
	        list.add(param);
	        
	        ObjectMapper mapper = new ObjectMapper();
	        System.out.println(mapper.writeValueAsString(list));*/
	        
	        /*List<Catalog> list = new ArrayList<Catalog>();
	        Catalog catalog = new Catalog();
	        catalog.setId(1L);
	        catalog.setCatalogname("热点");
	        catalog.setCatalogdesc("描述");
	        catalog.setCity("GZ");
	        catalog.setAreaid(10);
	        catalog.setCatalogstatus("Y");
	        catalog.setPri(5);
	        catalog.setCondtiontype("0");
	        catalog.setCondtionvalue("1");
	        list.add(catalog);
	        ObjectMapper mapper = new ObjectMapper();
	        System.out.println(mapper.writeValueAsString(list));*/
	        
	        /*List<SalespkgKnow> list = new ArrayList<SalespkgKnow>();
	        SalespkgKnow know = new SalespkgKnow();
	        know.setKnowid(1L);
	        know.setKnowname("欧洲体育");
	        know.setBrief("5大联赛和欧冠打包");
	        know.setCity("GZ");
	        know.setObjid(100L);
	        know.setObjtype("0");
	        know.setFeeexp("推荐导语");
	        know.setOpcodes("KH,JF");
	        list.add(know);
	        ObjectMapper mapper = new ObjectMapper();
	        System.out.println(mapper.writeValueAsString(list));*/
	        
	        /*List<CatalogItem> list = new ArrayList<CatalogItem>();
	        CatalogItem item = new CatalogItem();
	        item.setItemid(1l);
	        item.setCatalogid(1l);
	        item.setKnowid(1);
	        item.setPri(5);
	        item.setCity("GZ");
	        list.add(item);
	        ObjectMapper mapper = new ObjectMapper();
	        System.out.println(mapper.writeValueAsString(list));*/
	        
	        /*List<MarketBatch> list = new ArrayList<MarketBatch>();
	        MarketBatch batch = new MarketBatch();
	        batch.setRecid(1l);
	        batch.setBatchno("100");
	        batch.setAreaids("10,188");
	        batch.setAreanames("广州,潮州");
	        batch.setKnowid(9);
	        batch.setKnowname("娱乐12");
	        batch.setAppdate(new Date());
	        batch.setSappdate(DateUtils.formatDate(new Date()));
	        batch.setStatus("1");
	        batch.setCity("GZ");
	        batch.setNums(100);
	        
	        list.add(batch);
	        ObjectMapper mapper = new ObjectMapper();
	        System.out.println(mapper.writeValueAsString(list));*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
