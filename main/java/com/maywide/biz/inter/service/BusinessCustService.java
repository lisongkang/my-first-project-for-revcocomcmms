package com.maywide.biz.inter.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maywide.biz.cons.BizConstant.BossInterfaceService;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.queCommCust.QueCommCustBossReq;
import com.maywide.biz.inter.pojo.queCommCust.QueCommCustReq;
import com.maywide.biz.inter.pojo.queCommCust.QueCommCustResp;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;

@Service
public class BusinessCustService extends CommonService {

	@Autowired
	private PubService pubService;

	/*@Autowired
	private CustSpreadService spreadService;*/
	
	/**
	 * 查询商业用户接口
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queCommCust(QueCommCustReq req,QueCommCustResp resp) throws Exception{
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
		QueCommCustBossReq req2Boss = getQueCommCustBossReq(req, loginInfo);
		String jsonStr = getBossHttpInfOutput(req.getBizorderid(), BossInterfaceService.QUE_COMM_CUST, req2Boss, loginInfo);
		handlerResult(jsonStr,resp);
		
		return returnInfo;
	}
	
	private void handlerResult(String jsonStr,QueCommCustResp resp) throws Exception{
		QueCommCustResp bossResp = (QueCommCustResp) BeanUtil.jsonToObject(jsonStr, QueCommCustResp.class);
		BeanUtils.copyProperties(resp, bossResp);
	}
	
	
	private QueCommCustBossReq getQueCommCustBossReq(QueCommCustReq req,LoginInfo loginInfo) throws Exception{
		QueCommCustBossReq req2Boss = new QueCommCustBossReq();
		req2Boss.setCurrentPage(req.getCurrentPage());
		req2Boss.setPageSize(req.getPageSize());
		req2Boss.setCustid(req.getCustid());
		if(StringUtils.isBlank(req.getGridcode())){
			req2Boss.setGridcodes(pubService.getGridcodes(loginInfo));
		}else{
			req2Boss.setGridcodes(req.getGridcode());
		}
		return req2Boss;
	}
	
	
}
