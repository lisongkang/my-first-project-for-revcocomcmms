package com.maywide.biz.inter.service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.quetaginfo.QueTagInfoCmpReq;
import com.maywide.biz.inter.pojo.quetaginfo.QueTagInfoCmpResp;
import com.maywide.biz.inter.pojo.quetaginfo.QueTagInfoResp;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.CheckUtils;

/**
 * 
 *<p> 
 * 预警挽留 接口调用服务类
 *<p>
 * Create at 2017-2-21
 *
 *@autor zhuangzhitang
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class InterWarnTagInfoService extends CommonService{
	
	/**
	 * 接口名称：标签信息查询接口
	 * 接口说明：返回指定条件下的标签的信息
	 * @param req
	 * @param resp
	 * service:info.json
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queTagInfo(QueTagInfoCmpReq req,QueTagInfoResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getCity(), "查询参数【地市】不能为空");
		
		// 调用CMP接口:标签信息查询接口
	    callCMPInf4QueryTagInfo(req, resp, loginInfo);
	    
		return returnInfo;
	}

	@SuppressWarnings("unchecked")
	private void callCMPInf4QueryTagInfo(QueTagInfoCmpReq req,
			QueTagInfoResp resp, LoginInfo loginInfo) throws Exception {
		
		String  dataResultStr= getCMPHttpInfOutput(BizConstant.CmpPath.TAG_INFO, getInfoParamsMap(req));
		//System.out.println("标签信息查询接口:info.json 返回内容："+ dataResultStr);
		
		JSONArray array = new JSONArray(dataResultStr);
		List<QueTagInfoCmpResp> datas = BeanUtil.jsonToObject(array, QueTagInfoCmpResp.class);
	
		resp.setDatas(datas);
	}

	private Map<String, String> getInfoParamsMap(QueTagInfoCmpReq req) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("city", req.getCity());
		map.put("areaid",req.getAreaid());
		return map;
	}
	
	/***
	 * 公共调用cmp接口
	 * @param servName : 接口名称 ：例如  http://10.205.17.141:8090/mcmp/api/v1_00/tag/info.json?key=2948C14F7ADC8896E0538D11CD0A3FF2&city=-1&areaid=-1
	 *                  servName :info.json
	 * @param params :  入参  
	 * @return
	 * @throws Exception
	 */
	private String getCMPHttpInfOutput(String servName,Map<String, String> params) throws Exception{
		String  response= getCmpHttpKeyInfoOutPut(servName, params);
		if (StringUtils.isEmpty(response)) {
			throw new BusinessException("CMP接口调用出错，没有返回数据");
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.setDateFormat(dateFormat);

		JsonNode nodeTree = objectMapper.readTree(response);
		parseCMPResponse(nodeTree, servName);
		JsonNode outputNode = nodeTree.get("data");

		String cmpRespOutput = null;
		if (null != outputNode) {
			cmpRespOutput = outputNode.toString();
		}

		return cmpRespOutput;
	}
	
	/**
	 * 解析CMP接口返回值
	 * @param nodeTree
	 * @param interfaceName
	 * @throws Exception
	 */
	public void parseCMPResponse(JsonNode nodeTree, String interfaceName)
			throws Exception {
		if (IErrorDefConstant.ERROR_SUCCESS_CODE != nodeTree.get("code")
				.asInt()) {
			String exceptionDesc = "";
			if (interfaceName != null) {
				String infMark = null;
				exceptionDesc = getErrorConvert(interfaceName,
						nodeTree.get("code").asText(),infMark);
				if (StringUtils.isNotBlank(exceptionDesc)) {
					throw new BusinessException(exceptionDesc);
				}
			}
			if (nodeTree.get("msg") == null) {
				exceptionDesc = "未知错误";
			} else {
				exceptionDesc = nodeTree.get("msg").asText();
			}
			throw new BusinessException(exceptionDesc);
		}
	}
	
	

}
