package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.BizCustorderOrderstatus;
import com.maywide.biz.cons.BizConstant.BizCustorderSyncmode;
import com.maywide.biz.cons.BizConstant.BossInterfaceService;
import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.bizPreAuthPrds.BizPreAuthPrdsBossReq;
import com.maywide.biz.inter.pojo.bizPreAuthPrds.BizPreAuthPrdsReq;
import com.maywide.biz.inter.pojo.bizPreAuthPrds.BizPreAuthPrdsResp;
import com.maywide.biz.inter.pojo.quePermarkNumber.QuePermarkNumberResp;
import com.maywide.biz.inter.pojo.quePreAuthPrds.ParamBean;
import com.maywide.biz.inter.pojo.quePreAuthPrds.PermarkNumBean;
import com.maywide.biz.inter.pojo.quePreAuthPrds.QuePreAuthPrdsBossReq;
import com.maywide.biz.inter.pojo.quePreAuthPrds.QuePreAuthPrdsReq;
import com.maywide.biz.inter.pojo.quePreAuthPrds.QuePreAuthPrdsResp;
import com.maywide.biz.inter.pojo.quepreauthlog.PreAuthLogBean;
import com.maywide.biz.inter.pojo.quepreauthlog.QuePreAuthLogBossReq;
import com.maywide.biz.inter.pojo.quepreauthlog.QuePreAuthLogReq;
import com.maywide.biz.inter.pojo.quepreauthlog.QuePreAuthLogResp;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.biz.prv.entity.PrvOperMenuCtrl;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;

@Service
public class PreauthorizationService extends CommonService {

	@Autowired
	private PubService pubService;
	
	
	@Autowired
	private RuleService ruleService;
	
	private final String bizRule = "PRE_AUTH_PARAM";
	
	private final String cmSuffixRule = "CM_PEMARK_SUFFIX"; 
	
	private final String changeRule = "TIME_CHANGE_RULE";
	
	private final String cmEmptyRule = "CM_ACCOUNT_EMPTY_RULE";
	
	private final String paramRuleStr = "DIGIT_CITY_PARAMS";
	
	
	public ReturnInfo quePermarkNumber(QuePermarkNumberResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
		
		Rule paramRule = ruleService.getRule(loginInfo.getCity(),bizRule);
		CheckUtils.checkNull(paramRule, "该地市暂不支持预授权功能");
		CheckUtils.checkEmpty(paramRule.getValue(), "数据配置错误,请联系管理人员配置数据");
		resp.setDatas(getPermarkList(paramRule.getValue(),loginInfo.getCity()));
		
		Rule dateRule = ruleService.getRule(changeRule);
		if(dateRule != null && StringUtils.isNotBlank(dateRule.getValue())){
			if(dateRule.getValue().contains(loginInfo.getCity())){
				resp.setDateNotChange(true);
			}
		}
		Rule empty = ruleService.getRule(cmEmptyRule);
		if(empty != null && StringUtils.isNotBlank(empty.getValue())){
			if(empty.getValue().contains(loginInfo.getCity())){
				resp.setEmptyAble(true);
			}
		}
		return returnInfo;
	}
	
	private List<PermarkNumBean> getPermarkList(String values,String city) throws Exception{
		List<PermarkNumBean> datas = new ArrayList<PermarkNumBean>();
		String[] permarkListStr = values.split(",");
		for(String value:permarkListStr){
			String[] beans = value.split(":");
			String permark = beans[0];
			int number = Integer.parseInt(beans[1]);
			if(permark.equals("2")){
				datas.add(getCmSuffixPemark(permark, number, city));
			}else if(permark.equals("1")){
				datas.add(new PermarkNumBean(permark,number,getDigitParamBeanList(city)));
			}else{
				datas.add(new PermarkNumBean(permark,number));
			}
		}
		return datas;
	}
	
	private List<ParamBean> getDigitParamBeanList(String city) throws Exception{
		Rule paramRule = ruleService.getRule(city, paramRuleStr);
		if(paramRule == null || StringUtils.isBlank(paramRule.getValue())){
			return null;
		}
		List<ParamBean> datas = new ArrayList<ParamBean>();
		String[] params = paramRule.getValue().split(",");
		for(String str:params){
			String[] beans = str.split(":");
			datas.add(new ParamBean(beans[0], beans[1]));
		}
		return datas;
	}
	
	private PermarkNumBean getCmSuffixPemark(String permark,int number,String city) throws Exception{
		Rule suffixRule = ruleService.getRule(city, cmSuffixRule);
		if(suffixRule == null || StringUtils.isBlank(suffixRule.getValue())){
			return new PermarkNumBean(permark, number);
		}else{
			return new PermarkNumBean(permark, number, suffixRule.getValue());
		}
	}
	
	/**
	 * 查询预授权产品
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo quePreAuthPrds(QuePreAuthPrdsReq req,QuePreAuthPrdsResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
		QuePreAuthPrdsBossReq req2Boss = getQuePreAuthPrdsBossReq(loginInfo,req);
		
		String result = getBossHttpInfOutput(req.getBizorderid(), BossInterfaceService.QUE_PREAUTHPRDS, req2Boss, loginInfo);
		handlerResp(result, resp);
		return returnInfo;
	}
	
	private void handlerResp(String jsonStr,QuePreAuthPrdsResp resp) throws Exception{
		QuePreAuthPrdsResp bossResp = (QuePreAuthPrdsResp) BeanUtil.jsonToObject(jsonStr, QuePreAuthPrdsResp.class);
		BeanUtils.copyProperties(resp, bossResp);
	}
	
	
	private QuePreAuthPrdsBossReq getQuePreAuthPrdsBossReq(LoginInfo loginInfo,QuePreAuthPrdsReq req){
		QuePreAuthPrdsBossReq req2Boss = new QuePreAuthPrdsBossReq();
		req2Boss.setAreaid(loginInfo.getAreaid());
		req2Boss.setCity(loginInfo.getCity());
//		req2Boss.setDeptid(loginInfo.getDeptid());
//		req2Boss.setOperid(loginInfo.getOperid());
		req2Boss.setPermark(req.getPermark());
//		req2Boss.setRoleid(loginInfo.getRoleid());
		
		return req2Boss;
	}
	
	/**
	 * 办理预授权
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo bizPreAuthPrds(BizPreAuthPrdsReq req,BizPreAuthPrdsResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		String keyno = (req.getKeynoList() == null || req.getKeynoList().isEmpty())?
				"":req.getKeynoList().get(0) == null?
						"":req.getKeynoList().get(0).getKeyno();
		checkOperMenuCtrl(loginInfo,req.getPermark(),keyno);
		
		CustOrder custOrder = getCustOrder(req,loginInfo);
		
		BizPreAuthPrdsBossReq req2Boss = getBizPreAuthPrdsBossReq(req, loginInfo);
		
		String result = getBossHttpInfOutput(req.getBizorderid(), BossInterfaceService.AUTH_PREAUTHPRDS, req2Boss, loginInfo);
		updateOrder(custOrder);
		resp.setCustorderid(custOrder.getId().toString());
		return returnInfo;
	}
	
	private void checkOperMenuCtrl(LoginInfo loginInfo,String permark,String keyno) throws BusinessException{
		PrvOperMenuCtrl menuCtrl = null; 
		try{
			List params = new ArrayList();
			StringBuffer sql = new StringBuffer();
			sql.append("	SELECT * FROM prv_oper_menuctrl");
			sql.append("	WHERE operid = ?");
			sql.append("	AND menuid = (");
			sql.append("	SELECT s.menuid FROM prv_menudef s WHERE s.sysid = ?");
			sql.append("	AND s.bizcode =?)");
			params.add(loginInfo.getOperid());
			params.add(20);
			params.add("AUTH_PREAUTHPRDS");
			List<PrvOperMenuCtrl> datas =getDAO().find(sql.toString(), PrvOperMenuCtrl.class, params.toArray());
			if(datas != null && !datas.isEmpty()){
				menuCtrl = datas.get(0);
			}
		}catch(Exception e){
			menuCtrl = null;
		}finally{
			if(menuCtrl != null){
				if(menuCtrl.getTimelength().equals("0")){//没有时间限制,即是永久类型
					if(menuCtrl.getControlvalue().equals("0")){//表示最低权限
						int value = Integer.parseInt(menuCtrl.getValue());
						int num = 0;
						try{
							QuePreAuthLogReq req = new QuePreAuthLogReq();
							req.setPermark(permark);
							req.setKeyno(keyno);
//							req.setBizorderid(getDAO().getSequence("SEQ_BIZ_CUSTORDER_ID").toString());
							req.setBizorderid(getBizorderid());
							req.setOperid(loginInfo.getOperid().toString());
							QuePreAuthLogResp resp = new QuePreAuthLogResp();
							quePreAuthLog(req, resp);
							if(resp.getDatas() != null && !resp.getDatas().isEmpty()){
								for(PreAuthLogBean bean:resp.getDatas()){
									if(bean.getStatus().equals("0") || bean.getStatus().equals("1")){
										num++;
									}
								}
							}
						}catch(Exception e){
							log.error(e.getMessage());
						}
						if(num >= value){
							throw new BusinessException("当前操作员办理预授权次数已达到上限");
						}
					}
				}else{}
			}
		}
	}
	
	private void updateOrder(CustOrder custOrder) throws Exception{
		custOrder.setOrderstatus(BizCustorderOrderstatus.SYNC);
		getDAO().save(custOrder);
	}
	
	private CustOrder getCustOrder(BizPreAuthPrdsReq req,LoginInfo loginInfo) throws Exception{
		CustOrder order = new CustOrder();
		if(StringUtils.isNotBlank(req.getAddress())){
			order.setAddr(req.getAddress());
		}
		order.setAreaid(loginInfo.getAreaid());
		order.setCity(loginInfo.getCity());
		if(StringUtils.isNotBlank(req.getCustid())){
			order.setCustid(Long.parseLong(req.getCustid()));
		}
		order.setId(Long.parseLong(req.getBizorderid()));
		if(StringUtils.isNotBlank(req.getCustname())){
			order.setName(req.getCustname());
		}
		order.setOpcode(BizConstant.BizOpcode.AUTH_PREAUTHPRDS);
		order.setOperator(loginInfo.getOperid());
		order.setOprdep(loginInfo.getDeptid());
		order.setOptime(new Date());
		order.setOrdercode(pubService.getOrderCode());
		order.setOrderstatus(BizCustorderOrderstatus.INIT);
		order.setPortalOrder(null);
		order.setSyncmode(BizCustorderSyncmode.SYNC);
		getDAO().save(order);
		return order;
	}
	
	private BizPreAuthPrdsBossReq getBizPreAuthPrdsBossReq(BizPreAuthPrdsReq req,LoginInfo loginInfo){
		BizPreAuthPrdsBossReq req2Boss = new BizPreAuthPrdsBossReq();
		req2Boss.setAreaid(loginInfo.getAreaid().toString());
		req2Boss.setAuthsetid(req.getAuthsetid());
		req2Boss.setEtime(req.getEtime());
		req2Boss.setKeynoList(req.getKeynoList());
		req2Boss.setMemo(req.getMemo());
		req2Boss.setPermark(req.getPermark());
		req2Boss.setRegincode(req.getRegincode());
		req2Boss.setStime(req.getStime());
		req2Boss.setGatewayAuth(req.getGatewayAuth());
		return req2Boss;
	}
	
	public ReturnInfo quePreAuthLog(QuePreAuthLogReq req, QuePreAuthLogResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getPermark(), "业务类型不能为空！");

		QuePreAuthLogBossReq req2Boss = getQuePreAuthLogBossReq(req, loginInfo);
		String result = getBossHttpInfOutput(req.getBizorderid(), BossInterfaceService.QUE_AUTH_PREAUTHPRDS,
				req2Boss, loginInfo);
		handlerPreAuthLogResp(result, resp);
		return returnInfo;
	}
	
	@SuppressWarnings("serial")
	private void handlerPreAuthLogResp(String result, QuePreAuthLogResp resp) {
		List<PreAuthLogBean> datas = new Gson().fromJson(result, new TypeToken<List<PreAuthLogBean>>() {
		}.getType());
		resp.setDatas(datas);
	}

	private QuePreAuthLogBossReq getQuePreAuthLogBossReq(QuePreAuthLogReq req, LoginInfo loginInfo)
			throws Exception {
		QuePreAuthLogBossReq req2Boss = new QuePreAuthLogBossReq();
		BeanUtils.copyPropertiesNotSuperClass(req2Boss, req);
		req2Boss.setOperid(String.valueOf(loginInfo.getOperid()));
		return req2Boss;
	}
	
}
