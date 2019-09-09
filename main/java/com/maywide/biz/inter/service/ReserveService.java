package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.maywide.biz.inter.pojo.bizMindate.BizMindateBossReq;
import com.maywide.biz.inter.pojo.bizMindate.BizMindateReq;
import com.maywide.biz.inter.pojo.bizMindate.BizMindateResp;
import com.maywide.biz.inter.pojo.bizMindate.reviseMindateSalesParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.bizstopsales.BizStopSalesBossReq;
import com.maywide.biz.inter.pojo.bizstopsales.BizStopSalesReq;
import com.maywide.biz.inter.pojo.bizstopsales.BizStopSalesResp;
import com.maywide.biz.inter.pojo.queOrderBizOpcodeRule.QueOrderBizOpcodeRuleReq;
import com.maywide.biz.inter.pojo.queOrderBizOpcodeRule.QueOrderBizOpcodeRuleResp;
import com.maywide.biz.inter.pojo.querule.QueRuleReq;
import com.maywide.biz.inter.pojo.querule.QueRuleResp;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.CheckUtils;

@Service
@SuppressWarnings("unchecked")
public class ReserveService extends CommonService {

	@Autowired
	private PubService pubService;
	
	@Autowired
	private RuleService ruleService;

	/**
	 * 地市规则查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queRule(QueRuleReq req, QueRuleResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkEmpty(req.getRule(), "规则不能为空");

		String sql = "SELECT RULEID, RULE, RULENAME, CITY, AREAIDS, PERMISSION, VALUE FROM biz_rule WHERE rule=? AND city IN(?,?) ";
		List<Rule> list = getDAO().find(sql, Rule.class, req.getRule(), loginInfo.getCity(), "*");
		resp.setData(findCityRule(list));
		return returnInfo;
	}

	private Rule findCityRule(List<Rule> list) {
		if (list == null || list.isEmpty()) return null;
		Rule rule = null;
		if (list.size() > 1) {
			for (Rule r : list) {
				if (!"*".equals(r.getCity())) {
					rule = r;
					break;
				}
			}
		}
		if (rule == null) {
			rule = list.get(0);
		}
		return rule;
	}

	/**
	 * 指定到期停
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo bizStopSales(BizStopSalesReq req, BizStopSalesResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getCustid(), "客户编码不能为空");
		CheckUtils.checkEmpty(req.getCustname(), "客户名称不能为空");
		CheckUtils.checkEmpty(req.getAddr(), "客户地址不能为空");
		CheckUtils.checkNull(req.getSalesidparams(), "停用信息不能为空");

		BizStopSalesBossReq bossReq = getBossReq4StopSales(req, loginInfo);
		// 将请求做一下转换，并赋默认值
		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
				BizConstant.BossInterfaceService.BIZ_STOP_SALES, bossReq, loginInfo);

		// 写业务表
		CustOrder custOrder = regBizCustorder4StopSales(req, loginInfo);
		resp.setCustorderid(String.valueOf(custOrder.getId()));

		return returnInfo;
	}

	/**
	 *
	 *修改订购产品最少使用期限接口
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo bizMindate(BizMindateReq req, BizMindateResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getCustid(), "客户编码不能为空");
		CheckUtils.checkEmpty(req.getCustname(), "客户名称不能为空");
		CheckUtils.checkEmpty(req.getAddr(), "客户地址不能为空");
		CheckUtils.checkEmpty(req.getOpertype(), "操作类型不能为空");
		List<reviseMindateSalesParam> reviseMindateSalesParams = req.getSalesidparams();
		for(int i = 0 ;i < reviseMindateSalesParams.size(); i++){
			reviseMindateSalesParam reviseMindateSalesParam = reviseMindateSalesParams.get(i);
			CheckUtils.checkEmpty(reviseMindateSalesParam.getServid(), "用户标识不能为空");
			CheckUtils.checkEmpty(reviseMindateSalesParam.getPid(), "产品标识不能为空");
		}
		BizMindateBossReq bossReq = getBizMindateBossReq(req);
		// 将请求做一下转换，并赋默认值
		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
				BizConstant.BossInterfaceService.BIZ_STOP_SALES, bossReq, loginInfo);

		// 写业务表
		CustOrder custOrder = regBizCustorderMindateRevise(req, loginInfo);
		resp.setCustorderid(String.valueOf(custOrder.getId()));

		return returnInfo;
	}



	private BizStopSalesBossReq getBossReq4StopSales(BizStopSalesReq req, LoginInfo loginInfo) {
		BizStopSalesBossReq bossReq = new BizStopSalesBossReq();
		bossReq.setCustid(req.getCustid());
		bossReq.setCity(loginInfo.getCity());
		bossReq.setSalesidparams(req.getSalesidparams());
		return bossReq;
	}

	private BizMindateBossReq getBizMindateBossReq(BizMindateReq req){
		BizMindateBossReq bossReq = new BizMindateBossReq();
		bossReq.setCustid(req.getCustid());
		bossReq.setOpertype(req.getOpertype());
		bossReq.setSalesidparams(req.getSalesidparams());
		return bossReq;
	}

	private CustOrder regBizCustorder4StopSales(BizStopSalesReq req, LoginInfo loginInfo) throws Exception {

		CustOrder bizCustOrder = new CustOrder();
		bizCustOrder.setAddr(req.getAddr());
		bizCustOrder.setAreaid(loginInfo.getAreaid());
		bizCustOrder.setCity(loginInfo.getCity());
		bizCustOrder.setCustid(Long.valueOf(req.getCustid()));
		bizCustOrder.setName(req.getCustname());
		bizCustOrder.setOpcode(BizConstant.BizOpcode.BIZ_SET_STOPTIME);
		bizCustOrder.setOperator(loginInfo.getOperid());
		bizCustOrder.setOprdep(loginInfo.getDeptid());
		bizCustOrder.setOptime(new Date());
		bizCustOrder.setOrdercode(pubService.getOrderCode());
		bizCustOrder.setId(Long.valueOf(req.getBizorderid()));
		bizCustOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.SYNC);
		bizCustOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
		bizCustOrder.setPortalOrder(null);

		getDAO().save(bizCustOrder);

		return bizCustOrder;
	}

	private CustOrder regBizCustorderMindateRevise(BizMindateReq req, LoginInfo loginInfo) throws Exception {

		CustOrder bizCustOrder = new CustOrder();
		bizCustOrder.setAddr(req.getAddr());
		bizCustOrder.setAreaid(loginInfo.getAreaid());
		bizCustOrder.setCity(loginInfo.getCity());
		bizCustOrder.setCustid(Long.valueOf(req.getCustid()));
		bizCustOrder.setName(req.getCustname());
		bizCustOrder.setOpcode(BizConstant.BizOpcode.BIZ_SET_STOPTIME);
		bizCustOrder.setOperator(loginInfo.getOperid());
		bizCustOrder.setOprdep(loginInfo.getDeptid());
		bizCustOrder.setOptime(new Date());
		bizCustOrder.setOrdercode(pubService.getOrderCode());
		bizCustOrder.setId(Long.valueOf(req.getBizorderid()));
		bizCustOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.SYNC);
		bizCustOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
		bizCustOrder.setPortalOrder(null);

		getDAO().save(bizCustOrder);

		return bizCustOrder;
	}



	
	public ReturnInfo queOrderBizOpcodeRule(QueOrderBizOpcodeRuleReq req,QueOrderBizOpcodeRuleResp resp) throws Exception{
		
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		boolean hasOrderBiz = false;
		try{
			CheckUtils.checkNull(req.getCustorderid(),"订单编号不能为空");
			CustOrder order = (CustOrder) getDAO().find(CustOrder.class, req.getCustorderid());
			CheckUtils.checkNull(order, "");
			String bizCode = order.getOpcode();
			CheckUtils.checkEmpty(bizCode, "");
			String city = order.getCity();
			CheckUtils.checkEmpty(city, "");
			Map<String, List<String>> opcodeMap = getData4Rule(city, bizCode);
			CheckUtils.checkNull(opcodeMap, "");
			
		}catch(Exception e){
			hasOrderBiz = false;
		}finally{
			resp.setHasOrderBiz(hasOrderBiz);
			return returnInfo;
		}
		
	}
	
	private Map<String, List<String>> getData4Rule(String city,String bizcode) throws Exception{
		Map<String, List<String>> opcodeMap = null;
		Rule orderRule = ruleService.getRule(city, "CITY_OPCODE_ORDER");
		CheckUtils.checkNull(orderRule, "");
		String values = orderRule.getValue();
		CheckUtils.checkEmpty(values, "");
		String[] opcodeStrs = values.split(";");
		for(String opcodestr:opcodeStrs){
			
		}
		return opcodeMap;
	}
	
	
	private List<String> getPrintBiz(String city,String bizCode) throws Exception{
		List<String> bizcodeList = null;
		Rule printRule = ruleService.getRule(city, "PRINT_SHOW_INFO"); 
		CheckUtils.checkNull(printRule, "");
		String dataValue = printRule.getValue();
		CheckUtils.checkEmpty(dataValue, "");
		if(dataValue.contains(bizCode)){
			bizcodeList  = new ArrayList<String>();
			bizcodeList.add("");
		}
		return bizcodeList;
	}

}
