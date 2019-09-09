package com.maywide.biz.dl.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.ServerCityBossInterface;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.dl.pojo.bizViceFeeKindChg.BizViceFeeKindChg2BossReq;
import com.maywide.biz.dl.pojo.bizViceFeeKindChg.BizViceFeeKindChgReq;
import com.maywide.biz.dl.pojo.bizViceFeeKindChg.BizViceFeeKindChgResp;
import com.maywide.biz.dl.pojo.queViceFeek.QueViceFeeKind2BossReq;
import com.maywide.biz.dl.pojo.queViceFeek.QueViceFeeKindReq;
import com.maywide.biz.dl.pojo.queViceFeek.QueViceFeeKindResp;
import com.maywide.biz.dl.pojo.queViceFeek.ViceFeeKindBean;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.service.PubService;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.CheckUtils;

@Service
public class VicFeeKindService extends CommonService {
	
	@Autowired
	private PubService pubService;
	
	private Logger logger = LoggerFactory.getLogger(CardRelationService.class);
	
	/**
	 * 用户收费类型查询接口
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queViceFeeKind(QueViceFeeKindReq req,QueViceFeeKindResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
		CheckUtils.checkEmpty(req.getCustid(), "客户信息不能为空");
		
		QueViceFeeKind2BossReq req2Boss = getQueViceFeeKind2BossReq(req);
		
		String resultStr = getBossHttpInfOutput(req.getBizorderid(), ServerCityBossInterface.QUE_VICEFEEKIND, req2Boss, loginInfo);
		
		List<ViceFeeKindBean> dataList = new Gson().fromJson(resultStr, new TypeToken<List<ViceFeeKindBean>>() {}.getType());
		resp.setDataList(dataList);
		return returnInfo;
	}
	
	private QueViceFeeKind2BossReq getQueViceFeeKind2BossReq(QueViceFeeKindReq req){
		QueViceFeeKind2BossReq req2Boss = new QueViceFeeKind2BossReq();
		req2Boss.setCustid(req.getCustid());
		return req2Boss;
	}
	
	
	/**
	 * 副机优惠收费类型变更接口
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo bizViceFeeKindChg(BizViceFeeKindChgReq req,BizViceFeeKindChgResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getCustid(), "客户信息不能为空");
		
		BizViceFeeKindChg2BossReq req2Boss = getBizViceFeeKindChg2BossReq(req);
		String resultStr = getBossHttpInfOutput(req.getBizorderid(), ServerCityBossInterface.BIZ_VICEFEEKIND_CHG, req2Boss,
				loginInfo);
		CustOrder custOrder = register4Custorder(req, loginInfo);
		resp.setCustorderid(custOrder.getId());
		return returnInfo;
	}
	
	private BizViceFeeKindChg2BossReq getBizViceFeeKindChg2BossReq(BizViceFeeKindChgReq req){
		BizViceFeeKindChg2BossReq req2Boss = new BizViceFeeKindChg2BossReq();
		req2Boss.setCustid(req.getCustid());
		req2Boss.setNewChargetype(req.getNewChargetype());
		req2Boss.setServids(req.getServids());
		return req2Boss;
	}
	
	private CustOrder register4Custorder(BizViceFeeKindChgReq req,LoginInfo loginInfo) throws Exception{
		CustOrder custOrder = new CustOrder();
		custOrder.setId(Long.parseLong(req.getBizorderid()));
		custOrder.setAreaid(loginInfo.getAreaid());
		custOrder.setCity(loginInfo.getCity());
		custOrder.setOprdep(loginInfo.getDeptid());
		custOrder.setName(req.getCustName());
		custOrder.setCustid(Long.parseLong(req.getCustid()));
		custOrder.setOpcode(BizConstant.BizOpcode.BIZ_VICEFEEKIND_CHG);
		custOrder.setAddr(req.getAddress());
		custOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
		custOrder.setOperator(loginInfo.getOperid());
		custOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.SYNC);
		custOrder.setOptime(new Date());
		custOrder.setOrdercode(pubService.getOrderCode());
		custOrder.setPortalOrder(null);
		getDAO().save(custOrder);
		getDAO().flushSession();
		return custOrder;
	}
}
