package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.BizCustorderOrderstatus;
import com.maywide.biz.cons.BizConstant.PermarkType;
import com.maywide.biz.cons.BizConstant.UAPInterfaceService;
import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.inter.pojo.bizBusinessCustFresh.BizBusinessCustFreshReq;
import com.maywide.biz.inter.pojo.bizBusinessCustFresh.BizBusinessCustFreshResp;
import com.maywide.biz.inter.pojo.bizBusinessCustFresh.UapUserInfoReq;
import com.maywide.biz.inter.pojo.bizBusinessCustFresh.UapUserInfoResp;
import com.maywide.biz.inter.pojo.bizfreshauth.BizFreshauthBossReq;
import com.maywide.biz.inter.pojo.quePermarkType.BusinessPermarkType;
import com.maywide.biz.inter.pojo.queryProduct.QueryBusTypeResp;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.security.remote.socket.ServiceResponse;
import com.maywide.core.util.CheckUtils;

@Service
public class InterBusinessCustomService extends CommonService {
	
	@Autowired
	CustSpreadService testService;
	
	@Autowired
	PubService pubService;
	
	@Autowired
	private RuleService ruleService;
	
	private final String PERMARKRULE = "CITY_PERMARK";
	
	@Autowired
	private ParamService paramService;
	
	/**
	 * 商业客户刷新授权
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo bizBusinessCustFresh(BizBusinessCustFreshReq req,
			BizBusinessCustFreshResp resp) throws Exception{
		
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
//		LoginInfo loginInfo = testService.getGridTestLoginInfo();
		CheckUtils.checkEmpty(req.getPermark(),"设备业务类型不能为空!");
		UapUserInfoResp uapResp = getUapUserInfoResp4Str(req,loginInfo);
		CustOrder order = recordCustOrder(uapResp, loginInfo,req.getBizorderid());
		
		BizFreshauthBossReq req2Boss = getBizFreshauthBossReq2Boss(req,uapResp);
		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
                BizConstant.BossInterfaceService.BIZ_FRESHAUTH, req2Boss,
                loginInfo);
		order.setOrderstatus(BizCustorderOrderstatus.SYNC);
		getDAO().save(order);
		resp.setCustorderid(order.getId());
		ReturnVisitTaskService.addTask(Long.parseLong(req.getBizorderid()));
		return returnInfo;
	}
	
	private BizFreshauthBossReq getBizFreshauthBossReq2Boss(BizBusinessCustFreshReq req,UapUserInfoResp uapResp){
		BizFreshauthBossReq req2Boss = new BizFreshauthBossReq();
		req2Boss.setKeyno(req.getDevNo());
		req2Boss.setPermark(req.getPermark());
		req2Boss.setServid(uapResp.getServid());
		return req2Boss;
	}
	
	private UapUserInfoResp getUapUserInfoResp4Str(BizBusinessCustFreshReq req,LoginInfo loginInfo) throws Exception{
		UapUserInfoReq req2Uap = initUserInfoReq(req,loginInfo);
		UapUserInfoResp uapUserInfoResp = new UapUserInfoResp();
		String userInfoStr = getUapSocketInfOutput(req.getBizorderid(), UAPInterfaceService.UAP_QUERYGDUSERINFO, req2Uap, loginInfo);
		ServiceResponse resp = uapSocketClientService.socketResp2Respobj(
				userInfoStr, uapUserInfoResp);
		uapUserInfoResp = (UapUserInfoResp) resp.getOutput();
		return uapUserInfoResp;
	}
	
	private UapUserInfoReq initUserInfoReq(BizBusinessCustFreshReq req,LoginInfo loginInfo){
		UapUserInfoReq infoReq = new UapUserInfoReq();
		infoReq.setArea(loginInfo.getAreaid());
		infoReq.setCity(loginInfo.getCity());
		infoReq.setDevNo(req.getDevNo());
		infoReq.setPermark(req.getPermark());
		String type = req.getPermark().equals(PermarkType.CM)?"2":"1"; 
		infoReq.setType(type);
		return infoReq;
	}
	
	private CustOrder recordCustOrder(UapUserInfoResp resp,
			LoginInfo loginInfo,String orderid) throws Exception{
		CustOrder custOrder = new CustOrder();
		custOrder.setId(Long.parseLong(orderid));
		custOrder.setAreaid(loginInfo.getAreaid());
		custOrder.setCity(loginInfo.getCity());
		custOrder.setCustid(Long.parseLong(resp.getCustid()));
		custOrder.setName(resp.getUserName());
		custOrder.setOpcode(BizConstant.BizOpcode.BIZ_FRESHAUTH);
		custOrder.setOperator(loginInfo.getOperid());
		custOrder.setOprdep(loginInfo.getDeptid());
		custOrder.setOptime(new Date());
		custOrder.setOrdercode(pubService.getOrderCode());
		custOrder.setOrderstatus(BizCustorderOrderstatus.INIT);
		custOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
		custOrder.setPortalOrder(null);
		if(StringUtils.isNotBlank(resp.getPhoneNumber())){
			custOrder.setVerifyphone(resp.getPhoneNumber());
		}
		getDAO().save(custOrder);
		return custOrder;
	}
	
	/**
	 * 查询商业业务类型接口
	 * @param resps
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queBusinessPermarkType(ArrayList<BusinessPermarkType> resps) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
//		LoginInfo loginInfo = getLoginInfo();
		LoginInfo loginInfo = testService.getGridTestLoginInfo();
		
		Rule cityRule = ruleService.getRule(loginInfo.getCity(), PERMARKRULE);
		if(cityRule == null || StringUtils.isBlank(cityRule.getValue())){
			cityRule = ruleService.getRule("*", PERMARKRULE);
			CheckUtils.checkNull(cityRule, "数据配置异常,请联系相关人员进行配置");
		}
		hanlerCityValue(cityRule.getValue(),resps);
		return returnInfo;
	}
	
	private void hanlerCityValue(String value,ArrayList<BusinessPermarkType> resps) throws Exception{
		String[] permarks = value.split(",");
		for(String permark:permarks){
			PrvSysparam sysparam = paramService.getData("SYS_PERMARK", permark);
			BusinessPermarkType resp = new BusinessPermarkType(permark, sysparam.getMname());
			resps.add(resp);
		}
	}
}
