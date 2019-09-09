package com.maywide.biz.channel.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maywide.biz.channel.pojo.QueryServInfo;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.entity.RemotecallLog;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.core.security.remote.BossHttpClientImpl;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.biz.inter.service.ReturnVisitTaskService;
import com.maywide.biz.channel.pojo.BizFeeinInterResp;
import com.maywide.biz.channel.pojo.Pcode;
import com.maywide.biz.channel.pojo.Pcodes;

@Service
public class ProductService extends CommonService {
	
	/**
	 * 缴费接口
	 * @param queryInfo
	 * @param output
	 * @return
	 */
	public ReturnInfo doOrderCommit(String orderId) {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		try {
			
			JSONObject requestContent = new JSONObject(); 
	        requestContent.put("custorderid", orderId);
	        requestContent.put("payway", BizConstant.PORTAL_ORDER_PAYWAY.PORTAL_ORDER_PAYWAY_THIRDPAY);
	        requestContent.put("paycode", BizConstant.PORTAL_ORDER_PAYCODE.OTHER_ALIPAY);
	        
	        String serverCode = "";
			if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
				serverCode = BizConstant.ServerCityBossInterface.BIZ_ORDERCOMMIT;
			}else{
				serverCode = BizConstant.BossInterfaceService.BIZ_ORDERCOMMIT;
			}
			
	        RemotecallLog callLog = BossHttpClientImpl.requestPost(serverCode, requestContent.toString());
	        if (StringUtils.isEmpty(callLog.getResponse())) {
				throw new Exception("BOSS接口调用出错，没有返回数据");
			}
	        
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.setDateFormat(dateFormat);
	        
	        JsonNode nodeTree = objectMapper.readTree(callLog.getResponse());
	        parseBossResponse(nodeTree, BizConstant.BossInterfaceService.BIZ_ORDERCOMMIT);
	        
	        if (nodeTree.get("output") != null) {
	        	JsonNode outputNode = nodeTree.get("output");
	        	String code = outputNode.get("code").asText();
	        	
	        	// 00000为成功，其它为失败
	        	if (!"00000".equals(code)){
	        		returnInfo.setCode(-1L);
	        	}else {
	        		ReturnVisitTaskService.addTask(Long.parseLong(orderId));
	        	}
	        	
	        }
			
		} catch (Exception e) {
			setExceptionInfo(e, returnInfo);
		}
		
		return returnInfo;
	}
	
	
	public ReturnInfo doFeeIn(QueryServInfo queryInfo, BizFeeinInterResp output) {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		try {
			
			JSONObject requestContent = new JSONObject(); 
	        requestContent.put("fees", queryInfo.getFees());
	        requestContent.put("keyno", queryInfo.getKeyno());
	        String serverCode = "";
			if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
				serverCode = BizConstant.ServerCityBossInterface.BIZ_FEEIN;
			}else{
				serverCode = BizConstant.BossInterfaceService.BIZ_FEEIN;
			}
	        RemotecallLog callLog = BossHttpClientImpl.requestPost(serverCode, requestContent.toString());
	        if (StringUtils.isEmpty(callLog.getResponse())) {
				throw new Exception("BOSS接口调用出错，没有返回数据");
			}
	        
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.setDateFormat(dateFormat);
	        
	        JsonNode nodeTree = objectMapper.readTree(callLog.getResponse());
	        parseBossResponse(nodeTree, BizConstant.BossInterfaceService.BIZ_FEEIN);
	        
	        if (nodeTree.get("output") != null) {
	        	JsonNode outputNode = nodeTree.get("output");
	        	
	        	String orderId = outputNode.get("orderid").asText();
	        	output.setBossorderid(orderId);
	        	output.setOrdertype(outputNode.get("ordertype").asText());
	        	output.setFeename(outputNode.get("feename").asText());
	        	output.setSums(outputNode.get("sums").asText());
	        	
	        	// 调用订单确认接口
	        	ReturnInfo retInfo = doOrderCommit(orderId);
	        	if (retInfo.getCode() != IErrorDefConstant.ERROR_SUCCESS_CODE){
	        		returnInfo.setCode(-1L);
	        		returnInfo.setMessage("订单["+orderId+"]确认失败");
	        	}
	        }
			
		} catch (Exception e) {
			setExceptionInfo(e, returnInfo);
		}
		
		return returnInfo;
	}
	
	
	/**
	 * 查询用户产品以及欠费
	 * @param queryInfo
	 * @param pcodes
	 * @return
	 */
	public ReturnInfo queryServInfo(QueryServInfo queryInfo, Pcodes pcodes) {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		try {
			JSONObject requestContent = new JSONObject(); 
	        requestContent.put("keyno", queryInfo.getKeyno());
	        requestContent.put("pagesize", "100");
	        
//	        String response = BossHttpClientImpl.requestPost(QUE_SERVPRDINFO, requestContent.toString());
	        String serverCode = "";
	        if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
	        	serverCode = BizConstant.ServerCityBossInterface.QUE_SERVPRDINFO;
	        }else{
	        	serverCode = BizConstant.BossInterfaceService.QUE_SERVPRDINFO;
	        }
	        RemotecallLog callLog = BossHttpClientImpl.requestPost(serverCode, requestContent.toString());
	        if (StringUtils.isEmpty(callLog.getResponse())) {
				throw new Exception("BOSS接口调用出错，没有返回数据");
			}
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        ObjectMapper objectMapper = new ObjectMapper();
	        
	        objectMapper.setDateFormat(dateFormat);
	        
	        JsonNode nodeTree = objectMapper.readTree(callLog.getResponse());
	        parseBossResponse(nodeTree, BizConstant.BossInterfaceService.QUE_SERVPRDINFO);
	        
	        Pcode[] pcodeArray = null;
	        if (nodeTree.get("output") != null) {
	        	JsonNode responseNode = nodeTree.get("output");
	        	JsonNode retInfoNode = responseNode.get("prods");
	        	pcodeArray = objectMapper.readValue(retInfoNode.toString(), Pcode[].class);
	        }
	        
			if (pcodeArray != null && pcodeArray.length > 0) {
				pcodes.getPcodeList().addAll(Arrays.asList(pcodeArray));
			}
			
			queryCustInfo(queryInfo.getKeyno(),pcodes); // 查询客户名称
			
//			response = BossHttpClientImpl.requestPost(QUE_ARREARDET, requestContent.toString());
			callLog = BossHttpClientImpl.requestPost(BizConstant.BossInterfaceService.QUE_ARREARDET, requestContent.toString());
			if (StringUtils.isEmpty(callLog.getResponse())) {
				throw new Exception("BOSS接口调用出错，没有返回数据");
			}
			nodeTree = objectMapper.readTree(callLog.getResponse());
			String fees = "0";
			if (nodeTree.get("status") != null 
					&& nodeTree.get("status").asInt() == IErrorDefConstant.ERROR_SUCCESS_CODE) {
				if (nodeTree.get("output") != null) {
					if (nodeTree.get("output").get("arrearsun") != null) {
						fees = nodeTree.get("output").get("arrearsun").asText();
					}
				}
			}
			pcodes.setFees(fees);
			
			saveUserPhoneId(queryInfo);
		} catch (Exception e) {
			setExceptionInfo(e, returnInfo);
		}
		
		return returnInfo;
	}
	
	/**
	 * 缴费接口
	 * @param queryInfo
	 * @param output
	 * @return
	 */
	public ReturnInfo queryCustInfo(String keyNo,Pcodes pcodes) {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		try {
			
			JSONObject queConditionObject = new JSONObject(); 
			queConditionObject.put("keywordtype", "smno"); // keywordtype=smno,为智能卡号；
			queConditionObject.put("quekeyword", keyNo);
			
			JSONArray queConditionsArray = new JSONArray();
			queConditionsArray.put(queConditionObject);
			
			JSONObject requestContent = new JSONObject(); 
//			requestContent.put("city", "GZ");
//			requestContent.put("currentPage", "1");
//			requestContent.put("pagesize", "100");
			requestContent.put("isNotQueServInfo", "Y"); 
			requestContent.put("queConditions", queConditionsArray);
			
			String serverCode = "";
			if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
				serverCode = BizConstant.ServerCityBossInterface.QUE_SERVSTINFO;
			}else{
				serverCode = BizConstant.BossInterfaceService.QUE_SERVSTINFO;
			}
	        
	        RemotecallLog callLog = BossHttpClientImpl.requestPost(serverCode, requestContent.toString());
	        if (StringUtils.isEmpty(callLog.getResponse())) {
				throw new Exception("BOSS接口调用出错，没有返回数据");
			}
	        
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.setDateFormat(dateFormat);
	        
	        JsonNode nodeTree = objectMapper.readTree(callLog.getResponse());
	        parseBossResponse(nodeTree, serverCode);
	        
	        if (nodeTree.get("output") != null) {
	        	JsonNode outputNode = nodeTree.get("output");
	        	JsonNode custsNode = outputNode.get("custs");
	        	
	        	if ((custsNode != null) && (custsNode.size() > 0)){
	        		JsonNode custNode =  custsNode.get(0);
	        		String custName = custNode.get("custname").asText();
	        		pcodes.setName(custName);
	        	}
	        }
			
		} catch (Exception e) {
			setExceptionInfo(e, returnInfo);
		}
		
		return returnInfo;
	}
	
	private void saveUserPhoneId(QueryServInfo queryInfo) throws Exception {
		if (StringUtils.isBlank(queryInfo.getPhoneId()) 
				|| StringUtils.isBlank(queryInfo.getKeyno())) return;
		
		DAO.executeUpdate("DELETE FROM UserInfo WHERE keyno = ?", queryInfo.getKeyno());
		
		DAO.executeSql("INSERT INTO user_info(keyno, phone_id) VALUES (?, ?)", 
				queryInfo.getKeyno(), queryInfo.getPhoneId());
	}
}
