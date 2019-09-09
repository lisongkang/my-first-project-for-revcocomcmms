package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.applyinstall.ApplyInstallInterReq;
import com.maywide.biz.inter.pojo.applyinstall.ApplyInstallInterResp;
import com.maywide.biz.inter.pojo.bizAnalogchg.BizAnalogChgReq;
import com.maywide.biz.inter.pojo.bizAnalogchg.BizAnalogChgResp;
import com.maywide.biz.inter.pojo.bizAnalogchg.SubsidaryMachine;
import com.maywide.biz.inter.pojo.bizOrderRecom.BizOrderCommitReq;
import com.maywide.biz.inter.pojo.bizOrderRecom.BizOrderCommitResp;
import com.maywide.biz.inter.pojo.bizOrderRecom.BizOrderRecommitBossReq;
import com.maywide.biz.inter.pojo.queDevByStb.QueDevByStbReq;
import com.maywide.biz.inter.pojo.queDevByStb.QueDevByStbResp;
import com.maywide.biz.inter.pojo.queIntegrated.QueIntegratedReq;
import com.maywide.biz.inter.pojo.queIntegrated.QueIntegratedResp;
import com.maywide.biz.inter.pojo.quePromitions.PromitionsBean;
import com.maywide.biz.inter.pojo.quePromitions.QuePromitionsReq;
import com.maywide.biz.inter.pojo.quePromitions.QuePromitionsResp;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.CheckUtils;


@Service
@Transactional(rollbackFor = Exception.class)
public class PromotionService extends CommonService {
	
	@Autowired
	private InterApplyService applyService;

	public ReturnInfo quePromitionsList(QuePromitionsReq req,QuePromitionsResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT b.KNOWID knowid,b.KNOWNAME knowname,b.price ");
		sb.append("		,b.wordexp wordexp,b.FEEEXP feeexp");
		sb.append("		,a.isdefault isdef ,a.ismain isMain,a.percomb percomeCode");
		sb.append("		FROM prv_analogchg_product a,prd_salespkg_know b ");
		sb.append("		WHERE a.objid = b.OBJID");
		sb.append("		AND b.CITY = ?");
		sb.append("		AND a.ismain like ?");
		sb.append("		ORDER BY a.sort");
		
		List params = new ArrayList();
		params.add(loginInfo.getCity());
		params.add("%"+req.getIsMain()+"%");
		
		List<PromitionsBean> datas = getDAO().find(sb.toString(), PromitionsBean.class, params.toArray());
		if(datas != null && !datas.isEmpty()){
			resp.setDatas(datas);
		}
		
		return returnInfo;
	}
	
	public ReturnInfo bizAnalogChg(BizAnalogChgReq req,BizAnalogChgResp resp) throws Exception{
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req, "请求对象不能为空");
		CheckUtils.checkNull(req.getOservid(),"整转设备信息不能为空");
		
		List<ApplyInstallInterResp> datas = new ArrayList<ApplyInstallInterResp>();
		
		ApplyInstallInterResp applyInstallInterResp = new ApplyInstallInterResp();
		applyService.applyInstall(getApplyInstallInterReq(req), applyInstallInterResp);
		datas.add(applyInstallInterResp);
		if(req.getMachines() != null && !req.getMachines().isEmpty()){
			List<ApplyInstallInterReq> installInterReqs = getApplyInstallInterReqs(req.getMachines(),req,loginInfo);
			for(ApplyInstallInterReq childReq:installInterReqs){
				ApplyInstallInterResp childResp = new ApplyInstallInterResp();
				applyService.applyInstall(childReq, childResp);
				datas.add(childResp);
			}
		}
		
		return returnInfo;
	}
	
	private ApplyInstallInterReq getApplyInstallInterReq(BizAnalogChgReq req) throws Exception{
		ApplyInstallInterReq dataReq = new ApplyInstallInterReq();
		dataReq.setAcctkind(req.getAcctkind());
		dataReq.setAcctname(req.getAcctname());
		dataReq.setAcctno(req.getAcctno());
		dataReq.setAccttype(req.getAccttype());
		dataReq.setAddrs(req.getAddrs());
		dataReq.setApi(req.getApi());
		dataReq.setAreaid(req.getAreaid());
		dataReq.setBankcode(req.getBankcode());
		dataReq.setBizCode(req.getBizCode());
		dataReq.setBizorderid(req.getBizorderid());
		dataReq.setCardno(req.getCardno());
		dataReq.setCardSource(req.getCardSource());
		dataReq.setCardtype(req.getCardtype());
		dataReq.setCheckGrid(req.isCheckGrid());
		dataReq.setCounts(req.getCounts());
		dataReq.setCustid(req.getCustid());
		dataReq.setDealType(req.getDealType());
		dataReq.setDescrip(req.getDescrip());
		dataReq.setDevback(req.getDevback());
		dataReq.setDeviceSource(req.getDeviceSource());
		dataReq.setFeeParams(req.getFeeParams());
		dataReq.setHouseid(req.getHouseid());
		dataReq.setKnowids(req.getKnowids());
		dataReq.setLinkphone(req.getLinkphone());
		dataReq.setLogicdevno(req.getLogicdevno());
		dataReq.setName(req.getName());
		dataReq.setOlogicdevno(req.getOlogicdevno());
		dataReq.setOservid(req.getOservid());
		dataReq.setPatchid(req.getPatchid());
		dataReq.setPayCode(req.getPayCode());
		dataReq.setPayway(req.getPayway());
		dataReq.setPercombCode(getPercomb(req.getPercombCode()));
		dataReq.setPredate(req.getPredate());
		dataReq.setPservid(req.getPservid());
		dataReq.setSelectProducts(req.getSelectProducts());
		dataReq.setStbback(req.getStbback());
		dataReq.setStbno(req.getStbno());
		dataReq.setUnits(req.getUnits());
		dataReq.setWhladdr(req.getWhladdr());
		dataReq.setSystem(req.getSystem());
		return dataReq;
	}
	
	private List<ApplyInstallInterReq> getApplyInstallInterReqs(List<SubsidaryMachine> datas
			,BizAnalogChgReq req,LoginInfo loginInfo) throws Exception{
		List<ApplyInstallInterReq> installInterReqs = new ArrayList<ApplyInstallInterReq>();
		for(SubsidaryMachine machine:datas){
			ApplyInstallInterReq childReq = new ApplyInstallInterReq();
			childReq.setAcctkind(req.getAcctkind());
			childReq.setAcctname(req.getAcctname());
			childReq.setAcctno(req.getAcctno());
			childReq.setAccttype(req.getAccttype());
			childReq.setAddrs(req.getAddrs());
			childReq.setApi(req.getApi());
			childReq.setAreaid(req.getAreaid());
			childReq.setBankcode(req.getBankcode());
			childReq.setBizCode(req.getBizCode());
//			childReq.setBizorderid(Long.toString(getDAO().getSequence("SEQ_BIZ_CUSTORDER_ID")));
			childReq.setBizorderid(getBizorderid());
			childReq.setCardno(machine.getCardno());
			childReq.setCardSource(req.getCardSource());
			childReq.setCardtype(req.getCardtype());
			childReq.setCheckGrid(req.isCheckGrid());
			childReq.setCounts(machine.getCounts());
			childReq.setCustid(req.getCustid());
			childReq.setDealType(req.getDealType());
			childReq.setDescrip(req.getDescrip());
			childReq.setDevback(req.getDevback());
			childReq.setDeviceSource(req.getDeviceSource());
			childReq.setFeeParams(machine.getFeeParams());
			childReq.setHouseid(req.getHouseid());
			childReq.setKnowids(machine.getKnowids());
			childReq.setLinkphone(req.getLinkphone());
			childReq.setLogicdevno(machine.getCardno());
			childReq.setName(req.getName());
			childReq.setOlogicdevno(req.getOlogicdevno());
			childReq.setOservid(req.getOservid());
			childReq.setPatchid(req.getPatchid());
			childReq.setPayCode(req.getPayCode());
			childReq.setPayway(req.getPayway());
			childReq.setPercombCode(getPercomb(machine.getPercombs()));
			childReq.setPredate(req.getPredate());
			childReq.setPservid(req.getLogicdevno());
			childReq.setStbback(req.getStbback());
			childReq.setStbno(machine.getServid());
			childReq.setUnits(machine.getUnits());
			childReq.setWhladdr(req.getWhladdr());
			childReq.setSystem(req.getSystem());
			installInterReqs.add(childReq);
		}
		return installInterReqs;
	}
	
	private String getPercomb(String percomb) {
		String[] percombStrs = percomb.split(",");
		if(percomb.length() >= 2){
			if(percomb.contains("2") && percomb.contains("3")){
				return "9";
			}
		}
		Arrays.sort(percombStrs);
		return percombStrs[percombStrs.length-1];
	}
	
	
	public ReturnInfo bizOrderRecommit(BizOrderCommitReq req,BizOrderCommitResp resp) throws Exception{
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req.getCustorderid(), "订单编号不能为空");
		
		CustOrder order = new CustOrder();
		order.setOrdercode(req.getCustorderid().toString());
		
		List<CustOrder> datas = getDAO().find(order);
		if(datas == null || datas.isEmpty()){
			CheckUtils.checkNull(null, "查询不到该订单记录");
		}
		
		
		BizOrderRecommitBossReq req2Boss = getBizOrderRecommit2Boss(req.getCustorderid());
		
		String bossResult = getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.BIZ_ORDERRECOM, req2Boss, loginInfo);
		resp.setResult(bossResult);
		
		return returnInfo;
	}
	
	private BizOrderRecommitBossReq getBizOrderRecommit2Boss(Long custOrderid){
		BizOrderRecommitBossReq req2Boss = new BizOrderRecommitBossReq();
		req2Boss.setOrdercode(custOrderid.toString());
		return req2Boss;
	}
	
	
	public ReturnInfo queDevByStb(QueDevByStbReq req,QueDevByStbResp resp) throws Exception{
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req.getStbno(), "[stbno]不能为空");
		
		
		String bossResult = getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.QUE_DEV_BY_STB, req, loginInfo);
		resp = (QueDevByStbResp) this.copyBossResp2InterResp(resp, QueDevByStbResp.class, bossResult);
		return returnInfo;
	}
	
	/**
	 * 根据机顶盒号和智能卡号查询出一体机机顶盒账号  --番禺
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queIntegrated(QueIntegratedReq req,QueIntegratedResp resp) throws Exception{
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req.getCardno(), "[cardno]不能为空");
		CheckUtils.checkNull(req.getDevno(), "[devno]不能为空");
		
		
		String bossResult = getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.QUE_INTEGRATED, req, loginInfo);
		resp = (QueIntegratedResp) this.copyBossResp2InterResp(resp, QueIntegratedResp.class, bossResult);
		return returnInfo;
	}
	
	
	
}
