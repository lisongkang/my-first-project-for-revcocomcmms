package com.maywide.biz.cmp.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.maywide.biz.cmp.pojo.CmpCallbackReq;
import com.maywide.biz.cmp.pojo.CmpCallbackResp;
import com.maywide.biz.cmp.pojo.CmpPushReq;
import com.maywide.biz.cmp.pojo.CmpPushResp;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;

@Service
public class CmpService extends CommonService {

	public ReturnInfo quePushInfo(CmpPushReq req, CmpPushResp resp) throws Exception {
		CheckUtils.checkNull(req, "请求信息不能为空");
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
			return returnInfo;
		}
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new NameValuePair("custid", req.getCustid()));
		String response = getCmpHttpInfOutput(BizConstant.CmpPath.PUSH, params);
		if (StringUtils.isEmpty(response)) {
			throw new BusinessException("CMP接口调用出错，没有返回数据");
		}

		CmpPushResp cmpPushResp = (CmpPushResp) BeanUtil.jsonToObject(response, CmpPushResp.class);
		BeanUtils.copyProperties(resp, cmpPushResp);
		return returnInfo;
	}

	public ReturnInfo pushCallback(CmpCallbackReq req, CmpCallbackResp resp) throws Exception {
		CheckUtils.checkNull(req, "请求信息不能为空");
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new NameValuePair("custid", req.getCustid()));
		params.add(new NameValuePair("tempid", req.getTempid()));
		params.add(new NameValuePair("rcode", req.getRcode()));
		String response = getCmpHttpInfOutput(BizConstant.CmpPath.CALLBACK, params);

		CmpCallbackResp cmpPushResp = (CmpCallbackResp) BeanUtil.jsonToObject(response,
				CmpCallbackResp.class);
		BeanUtils.copyProperties(resp, cmpPushResp);
		return returnInfo;
	}
}
