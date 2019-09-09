package com.maywide.biz.dl.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.ServerCityBossInterface;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.dl.pojo.bizUpCardRel.BizUpCardRel2BossReq;
import com.maywide.biz.dl.pojo.bizUpCardRel.BizUpCardRelationReq;
import com.maywide.biz.dl.pojo.bizUpCardRel.BizUpCardRelationResp;
import com.maywide.biz.dl.pojo.queCardRel.Que2BossCardRelationReq;
import com.maywide.biz.dl.pojo.queCardRel.QueCardRelationReq;
import com.maywide.biz.dl.pojo.queCardRel.QueCardRelationResp;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.checkAcpt.CheckAcceptReq;
import com.maywide.biz.inter.pojo.checkAcpt.CheckAcceptResp;
import com.maywide.biz.inter.service.PubService;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;

@Service
public class CardRelationService extends CommonService {

	@Autowired
	private PubService pubService;
	
	private Logger logger = LoggerFactory.getLogger(CardRelationService.class);

	/**
	 * 查询主副机关系接口
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queCardRelation(QueCardRelationReq req, QueCardRelationResp resp) throws Exception {

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkEmpty(req.getCustid(), "客户信息不能为空");

		Que2BossCardRelationReq req2Boss = new Que2BossCardRelationReq();
		req2Boss.setCustid(req.getCustid());

		String resultStr = getBossHttpInfOutput(req.getBizorderid(), ServerCityBossInterface.QUE_CARD_RELATION,
				req2Boss, loginInfo);
		copyVaule2Resp(resultStr, resp);

		return returnInfo;
	}

	private void copyVaule2Resp(String jsonStr, QueCardRelationResp resp) throws Exception {
		QueCardRelationResp value = (QueCardRelationResp) BeanUtil.jsonToObject(jsonStr, QueCardRelationResp.class);
		BeanUtils.copyProperties(resp, value);
	}

	/**
	 * 更新主副机关系接口
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo bizUpCardRelation(BizUpCardRelationReq req,BizUpCardRelationResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getCustid(), "客户信息不能为空");
		CheckUtils.checkEmpty(req.getChouseid(), "安装地址不能为空");
		CheckUtils.checkEmpty(req.getNewservtype(), "新的主机类型不能为空");

		CustOrder custOrder = regist4CustOrder(loginInfo, req);

		BizUpCardRel2BossReq req2Boss = getBizUpCardRel2BossReq(req);

		String resultStr = getBossHttpInfOutput(req.getBizorderid(), ServerCityBossInterface.UP_CARD_RELATION, req2Boss,
				loginInfo);
		resp.setCustOrderid(custOrder.getId());
		return returnInfo;
	}

	private BizUpCardRel2BossReq getBizUpCardRel2BossReq(BizUpCardRelationReq req) {
		BizUpCardRel2BossReq req2Boss = new BizUpCardRel2BossReq();
		req2Boss.setChouseid(req.getChouseid());
		req2Boss.setCustid(req.getCustid());
		req2Boss.setNewservtype(req.getNewservtype());
		req2Boss.setPservid(req.getPservid());
		req2Boss.setServid(req.getServid());
		return req2Boss;
	}

	private CustOrder regist4CustOrder(LoginInfo loginInfo, BizUpCardRelationReq req) throws Exception {
		CustOrder custOrder = new CustOrder();
		custOrder.setId(Long.parseLong(req.getBizorderid()));
		custOrder.setAreaid(loginInfo.getAreaid());
		custOrder.setCity(loginInfo.getCity());
		custOrder.setOprdep(loginInfo.getDeptid());
		custOrder.setName(req.getCustName());
		custOrder.setCustid(Long.parseLong(req.getCustid()));
		custOrder.setOpcode(BizConstant.BizOpcode.BIZ_UP_CARD_RELATION);
		custOrder.setAddr(req.getAddress());
		custOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
		custOrder.setOperator(loginInfo.getOperid());
		custOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.INIT);
		custOrder.setOptime(new Date());
		custOrder.setOrdercode(pubService.getOrderCode());
		custOrder.setPortalOrder(null);
		getDAO().save(custOrder);
		getDAO().flushSession();
		return custOrder;
	}
	
	/**
	 * 大连报盘查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo checkBizAccept(CheckAcceptReq req, CheckAcceptResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		resp.setIsbank("N");
		resp.setMsg("");
		return returnInfo;
	}
}
