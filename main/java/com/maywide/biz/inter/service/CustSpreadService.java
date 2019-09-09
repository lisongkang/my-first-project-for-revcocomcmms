package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.BossInterfaceService;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.extendattr.entity.SysCustAttr;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.chargeFeeBook.ChargeFeeBookBossReq;
import com.maywide.biz.inter.pojo.chargeFeeBook.ChargeFeeBookReq;
import com.maywide.biz.inter.pojo.chargeFeeBook.ChargeFeeBookResp;
import com.maywide.biz.inter.pojo.mkFeeBook.ChargeBossResp;
import com.maywide.biz.inter.pojo.mkFeeBook.MakeFeeBookReq;
import com.maywide.biz.inter.pojo.mkFeeBook.MakeFeeBookResp;
import com.maywide.biz.inter.pojo.queAdContent.AdvertContent;
import com.maywide.biz.inter.pojo.queAdContent.QueAdContentResp;
import com.maywide.biz.inter.pojo.queAdtImg.AdvertImgBean;
import com.maywide.biz.inter.pojo.queAdtImg.QueAdvertImgResp;
import com.maywide.biz.inter.pojo.queConditions.QueConditionReq;
import com.maywide.biz.inter.pojo.queConditions.QueConditionResp;
import com.maywide.biz.inter.pojo.queCustAttr.Attrudie;
import com.maywide.biz.inter.pojo.queCustAttr.Attrudie2BossListReq;
import com.maywide.biz.inter.pojo.queCustAttr.Attrudie2BossReq;
import com.maywide.biz.inter.pojo.queCustAttr.CustAttrBean;
import com.maywide.biz.inter.pojo.queCustAttr.CustAttrChildBean;
import com.maywide.biz.inter.pojo.queCustAttr.CustAttrudieBean;
import com.maywide.biz.inter.pojo.queCustAttr.FillCustAttrReq;
import com.maywide.biz.inter.pojo.queCustAttr.FillCustAttrResp;
import com.maywide.biz.inter.pojo.queCustAttr.QueCustAttrReq;
import com.maywide.biz.inter.pojo.queCustAttr.QueCustAttrResp;
import com.maywide.biz.inter.pojo.queFeeBook.FeeBookBossReq;
import com.maywide.biz.inter.pojo.queFeeBook.QueFeeBookReq;
import com.maywide.biz.inter.pojo.queFeeBook.QueFeeBookResp;
import com.maywide.biz.market.entity.BizPortalOrder;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.biz.prv.entity.PrvAdvertImg;
import com.maywide.biz.prv.entity.PrvCitySearch;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class CustSpreadService extends CommonService {
	
	
	@Autowired
	private PubService pubService;
	
	final String SRC_ENTER = "0";
	
	final String SRC_RADIO = "1";
	
	final String SRC_CHOICE = "2";
	
	final String SRC_DATE = "3";

	public ReturnInfo queCustAtturide(QueCustAttrReq req,QueCustAttrResp resp) throws Exception{
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getCustid(), "客户id不能为空");
		
		List params = new ArrayList();
		params.add(loginInfo.getCity());
		params.add(req.getCustid());
		
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT A.RECID attrId ,A.ATTRCODE attrCode,A.ATTRNAME arrtName,C.ATTRVALUE attrValue,");
		sb.append("		A.VALUESRC valueSrc,A.IFNECESSARY IFNECESSARY,A.SCOPEFLAG scopeFlag,");
		sb.append("		A.MINVALUE min,A.MAXVALUE max,A.CREATEOPER creater,");
		sb.append("		D.MCODE mcode,D.MNAME mname,D.GCODE gcode,ST.SNAME sname");
		sb.append("		FROM PRV_ATTRRULE A INNER JOIN SYS_ATTR_AREA B");
		sb.append("		ON A.RECID = B.ATTRRULEID AND B.CITY = ?");
		sb.append("		LEFT JOIN SYS_CUST_ATTR C");
		sb.append("		ON A.RECID = C.ATTRRULEID AND C.CUSTID = ?");
		sb.append("		LEFT JOIN PRV_SYSPARAM D ON D.GCODE = A.VALUEPARAM");
		sb.append("		LEFT JOIN ");
		sb.append("		(SELECT T.MCODE,T.GCODE,S.ATTRRULEID,T.MNAME SNAME");
		sb.append("		FROM PRV_SYSPARAM T,SYS_CUST_ATTR S");
		sb.append("		WHERE T.MCODE IN (S.ATTRVALUE ) ) ST");
		sb.append("		ON ST.ATTRRULEID = A.RECID");
		sb.append("		ORDER BY A.CREATETIME,A.RECID,D.MCODE");
		
		List<CustAttrudieBean> datas = getDAO().find(sb.toString(), CustAttrudieBean.class, params.toArray());
		if(datas != null && !datas.isEmpty()){
			List<CustAttrBean> results = getCustAttrBeans(datas);
			resp.setDatas(results);
		}
		return returnInfo;
	}
	
	
	private List<CustAttrBean> getCustAttrBeans(List<CustAttrudieBean> datas){
		List<CustAttrBean> beans = new ArrayList<CustAttrBean>();
		Map<Long, List<CustAttrudieBean>> map = new LinkedHashMap<Long, List<CustAttrudieBean>>();
		for(CustAttrudieBean bean:datas){
			List<CustAttrudieBean> tmps = map.get(bean.getAttrId());
			if(tmps == null){
				tmps = new ArrayList<CustAttrudieBean>();
				map.put(bean.getAttrId(), tmps);
			}
			tmps.add(bean);
		}
		Iterator<Entry<Long, List<CustAttrudieBean>>> iterator = map.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<Long, List<CustAttrudieBean>> entry = iterator.next();
			CustAttrBean bean = getCustAttrBeanByList(entry.getValue());
			beans.add(bean);
		}
		return beans;
	}
	
	private CustAttrBean getCustAttrBeanByList(List<CustAttrudieBean> datas){
		CustAttrBean bean = new CustAttrBean();
		bean.setAttrId(datas.get(0).getAttrId());
		bean.setAttrCode(datas.get(0).getAttrCode());
		bean.setAttrValue(datas.get(0).getAttrValue());
		bean.setAttrName(datas.get(0).getArrtName());
		bean.setGcode(datas.get(0).getGcode());
		bean.setIfNecessary(datas.get(0).getIfNecessary());
		bean.setMax(datas.get(0).getMax());
		bean.setMin(datas.get(0).getMin());
		bean.setScopeFlag(datas.get(0).getScopeFlag());
		bean.setValueSrc(datas.get(0).getValueSrc());
		bean.setOptions(getCustAttrChildBeanList(datas));
		return bean;
	}
	
	private List<CustAttrChildBean> getCustAttrChildBeanList(List<CustAttrudieBean> datas){
		if(datas.get(0).getValueSrc().equals(SRC_ENTER) || datas.get(0).equals(SRC_DATE)){
			return null;
		}
		List<CustAttrChildBean> options = new ArrayList<CustAttrChildBean>();
		for(CustAttrudieBean data:datas){
			CustAttrChildBean bean = new CustAttrChildBean();
			bean.setGcode(data.getGcode());
			bean.setMname(data.getMname());
			bean.setSname(data.getSname());
			bean.setMcode(data.getMcode());
			options.add(bean);
		}
		return options;
	}
	
	
	/**
	 * 填充/更新客户属性
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo fillCustAttride(FillCustAttrReq req,FillCustAttrResp resp) throws Exception{
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getCustid(),"客户id不能为空");
		CheckUtils.checkEmpty(req.getOperid(), "操作员信息不能为空");
		CheckUtils.checkNull(req.getAttrs(), "客户属性不能为空");
		
		
		Date date  = new Date();
		
		req2Boss(req.getAttrs(),"0",Long.parseLong(req.getCustid()),loginInfo,req.getBizorderid());
		try{
			for(Attrudie attrudie:req.getAttrs()){
				boolean save = false;
				SysCustAttr custAttr = new SysCustAttr();
				custAttr.setAttrruleid(attrudie.getAttrId());
				custAttr.setAttrcode(attrudie.getAttrCode());
				custAttr.setCustid(Long.parseLong(req.getCustid()));
				List<SysCustAttr> tmps = getDAO().find(custAttr);
				if(tmps != null && !tmps.isEmpty()){
					custAttr = tmps.get(0);
				}else{
					save = true;
				}
				updateOrSaveCustAttr(custAttr,Long.parseLong(req.getOperid()),date,attrudie.getAttrValue(),save);
			}
		}catch(Exception e){
			resp.setMsg("添加客户属性值失败");
		}
		
		return returnInfo;
	}
	/**
	 * 更新或保存新的客户属性
	 * @param custAttr
	 * @param operid
	 * @param date
	 * @param attrValue
	 * @param save
	 * @throws Exception
	 */
	private void updateOrSaveCustAttr(SysCustAttr custAttr,Long operid,Date date,String attrValue,boolean save) throws Exception{
		if(!save){
			custAttr.setUpdateoper(operid);
			custAttr.setUpdatetime(date);
			custAttr.setAttrvalue(attrValue);
			getDAO().update(custAttr);
		}else{
			custAttr.setCreateoper(operid);
			custAttr.setCreatetime(date);
			custAttr.setUpdateoper(operid);
			custAttr.setUpdatetime(date);
			custAttr.setAttrvalue(attrValue);
			getDAO().save(custAttr);
		}
	}
	
	/**
	 * 向BOSS发起请求用于记录客户扩展属性(重新修改)
	 * @param
	 * @param status
	 * @param custid
	 * @param loginInfo
	 * @throws Exception
	 */
	private void req2Boss(List<Attrudie> attrudies,String status,Long custid,LoginInfo loginInfo
			,String bizOrderid) throws Exception{
		Attrudie2BossListReq req2Boss = new Attrudie2BossListReq();
		req2Boss.setCustid(custid);
		List<Attrudie2BossReq> datas = new ArrayList<Attrudie2BossReq>();
		for(Attrudie attrudie:attrudies){
			String value = StringUtils.isBlank(attrudie.getValueCode())?attrudie.getAttrValue():attrudie.getValueCode();
			Attrudie2BossReq req = new Attrudie2BossReq(attrudie.getAttrCode(), value,status);
			datas.add(req);
		}
		req2Boss.setCepam(datas);
		String response = getBossHttpInfOutput(bizOrderid, BossInterfaceService.CUST_PT_EXPINFO, req2Boss, loginInfo);
		
	}
	
	
	@Deprecated
	public ReturnInfo queAdvertImg(QueAdvertImgResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
		List params = new ArrayList();
		params.add(loginInfo.getCity());
		params.add("*");
		
		StringBuffer sb = new StringBuffer();
		sb.append("		FROM PrvAdvertImg  WHERE 1 = 1");
		sb.append("		AND (city = ? OR city = ?)");
		
		List<PrvAdvertImg> tmps = getDAO().find(sb.toString(), params.toArray());
		if(tmps != null && !tmps.isEmpty()){
			resp.setDatas(getAdvertDatas(tmps));
		}
		
		return returnInfo;
	}
	
	private List<AdvertImgBean> getAdvertDatas(List<PrvAdvertImg> tmps) throws Exception{
		List<AdvertImgBean> datas = new ArrayList<AdvertImgBean>();
		for(PrvAdvertImg advert:tmps){
			AdvertImgBean bean = new AdvertImgBean();
			bean.setPath(advert.getPath());
			bean.setToClz(advert.getToClz());
			bean.setUrl(advert.getUrl());
			bean.setParams(comperParams(advert.getParams(), advert.getParams_value()));
			datas.add(bean);
		}
		
		return datas;
	}
	
	private Map<String, String> comperParams(String paramStr,String valueStr) throws Exception{
		Map<String, String> params = new HashMap<String, String>();
		String[] paramStrs = paramStr.split(",");
		String[] valueStrs = valueStr.split(",");
		if(!(paramStrs.length == valueStrs.length)){
			CheckUtils.checkNull(null, "筛选条件有错，请联系管理员重新输入");
		}
		for(int i = 0;i < paramStrs.length;i++){
			params.put(paramStrs[i], valueStrs[i]);
		}
		return params;
	}
	
	/**
	 * 查询首页广告轮播内容
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queHomeAdvert(QueAdContentResp resp) throws Exception{
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
		List params = new ArrayList();
		
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT a.ADID adId,a.ADNAME adName,a.ADTYPE adType,a.ADOBJ adPath,a.ADSITE adImg");
		sb.append("		FROM ad_config a");
		sb.append("		WHERE (a.CITY = ? OR a.CITY = ?)");
		sb.append("		AND a.ADSTATUS = ?");
		sb.append("		ORDER BY a.PRI desc");
		sb.append("		LIMIT 6");
		
		params.add("*");
		params.add(loginInfo.getCity());
		params.add("5");
		
		List<AdvertContent> datas = getDAO().find(sb.toString(), AdvertContent.class, params.toArray());
		resp.setDatas(datas);
		
		return returnInfo;
	}
	
	public ReturnInfo queConditions(QueConditionReq req,QueConditionResp resp) throws Exception{
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
		List params = new ArrayList();
		params.add("*");
		params.add("%"+loginInfo.getCity()+"%");
		
		StringBuffer sb = new StringBuffer();
		sb.append("		FROM PrvCitySearch  WHERE 1 = 1");
		sb.append("		AND (city = ? OR city like ?)");
		if(StringUtils.isNotBlank(req.getIsGroup())){
			sb.append("		AND isGroup is not null");
		}else{
			sb.append("		AND isGroup is null");
		}
		
		List<PrvCitySearch> datas = getDAO().find(sb.toString(), params.toArray());
		if(datas != null && !datas.isEmpty()){
			resp.setDatas(datas);
		}
		
		return returnInfo;
		
	}
	
	/**
	 * 查询客户账户余额
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queCustFeeBook(QueFeeBookReq req,QueFeeBookResp resp) throws Exception{
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getCustid(),"custid不能为空"); 
//		CheckUtils.checkEmpty(req.getHouseid(), "地址信息不能为空");
//		CheckUtils.checkEmpty(req.getPatchid(), "地址信息不能为空");
		
//		pubService.checkBizAcceptRight(Long.parseLong(req.getHouseid()),Long.parseLong(req.getPatchid()), loginInfo);
		
		FeeBookBossReq req2Boss = getReq4BossReq(req);
		
		String bossOutPut = getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.QUE_FEEBOOKINFO, req2Boss, loginInfo);
		copyResult2Resp(resp,bossOutPut);
		return returnInfo;
	}
	
	private FeeBookBossReq getReq4BossReq(QueFeeBookReq req){
		FeeBookBossReq req2Boss = new FeeBookBossReq();
		req2Boss.setCustid(req.getCustid());
		req2Boss.setFeecodestr(req.getFeecodestr());
		req2Boss.setKeyno(req.getKeyno());
		req2Boss.setPermark(req.getPermark());
		return req2Boss;
	}
	
	private void copyResult2Resp(QueFeeBookResp resp,String jsonStr) throws Exception{
		QueFeeBookResp rsp = (QueFeeBookResp) BeanUtil.jsonToObject(jsonStr, QueFeeBookResp.class);
		 BeanUtils.copyProperties(resp, rsp);
	}
	
	
	/**
	 * 生成充值订单
	 * @return
	 * @throws Exception 
	 */
	public ReturnInfo makeFeeBookOrder(MakeFeeBookReq req,MakeFeeBookResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
		//校验网格权限
		pubService.checkBizAcceptRight(Long.parseLong(req.getHouseid()),Long.parseLong(req.getPatchid()), loginInfo);
		
		ChargeFeeBookBossReq req2Boss = getChargeFB2BossReq(req);
		
		/**调用BOSS充值接口生成订单**/
		String outputStr = getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.BIZ_CHARGING, req2Boss, loginInfo);
		
		register4FeeBook(outputStr,loginInfo,req,resp);
		
		return returnInfo;
	}
	
	
	
	private ChargeFeeBookBossReq getChargeFB2BossReq(MakeFeeBookReq req){
		ChargeFeeBookBossReq req2Boss = new ChargeFeeBookBossReq();
		req2Boss.setCustid(req.getCustid());
		req2Boss.setFees(req.getFee());
		req2Boss.setGdno("");
		req2Boss.setGdnoid("");
		req2Boss.setIspkg("N");
		req2Boss.setKeyno(req.getKeyno());
		return req2Boss;
	}
	
	
	/**
	 * 为充值写订单
	 * @throws Exception 
	 */
	private void register4FeeBook(String bossOutput,LoginInfo loginInfo,MakeFeeBookReq req,MakeFeeBookResp resp) throws Exception{
		
		
		BizGridInfo bizGrid = pubService.getGrid4Biz(Long.valueOf(req.getHouseid()), Long.valueOf(req.getPatchid()),
				loginInfo);
		
		ChargeBossResp bossResp = (ChargeBossResp) BeanUtil.jsonToObject(bossOutput, ChargeBossResp.class);
		
		CustOrder bizCustOrder = new CustOrder();
		bizCustOrder.setId(Long.parseLong(req.getBizorderid()));
		bizCustOrder.setAddr(req.getAddr());
		bizCustOrder.setAreaid(loginInfo.getAreaid());
		bizCustOrder.setCity(loginInfo.getCity());
		bizCustOrder.setCustid(Long.parseLong(req.getCustid()));
		bizCustOrder.setOpcode(BizConstant.BossInterfaceService.BIZ_CHARGING);
		bizCustOrder.setName(req.getCustName());
		bizCustOrder.setOperator(loginInfo.getOperid());
		bizCustOrder.setOprdep(loginInfo.getDeptid());
		bizCustOrder.setOptime(new Date());
		bizCustOrder.setGridid(bizGrid.getId());
		bizCustOrder.setOrdercode(pubService.getOrderCode().toString());
		bizCustOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.INIT);
		bizCustOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
		getDAO().save(bizCustOrder);
		BizPortalOrder order = register4PortalOrder(bossResp,req.getFee(),req.getBizorderid());
		bizCustOrder.setPortalOrder(order);
		getDAO().update(order);
		resp.setOrderCode(bizCustOrder.getOrdercode());
		resp.setFeeNums(order.getFees());
		resp.setOrderid(req.getBizorderid());
	}
	
	private BizPortalOrder register4PortalOrder(ChargeBossResp resp,String fees,String orderid) throws Exception{
		
		BizPortalOrder order = new BizPortalOrder();
		order.setId(Long.parseLong(orderid));
		order.setCreatetime(new Date());
		order.setFees(fees);
		order.setStatus(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_NOPAY);
		order.setOrdertype(resp.getOrdertype());  //orderType是干什么？
		order.setFeestr(resp.getFeename());
		order.setResporderid(Long.parseLong(resp.getOrderid()));
		getDAO().save(order);
		return order;
	}
	
	
	/**(网格客户端充值)充值*/
	public ReturnInfo chargeFeeBook(ChargeFeeBookReq req,ChargeFeeBookResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
		CheckUtils.checkEmpty(req.getCustid(),"客户信息不能为空");
		CheckUtils.checkEmpty(req.getKeyno(),"充值账户信息不能为空");
		
		ChargeFeeBookBossReq req2Boss = getChargeFeeBookBossReq(req);
		checkCustOrder(req.getOrderid(),req.getFees());
		String status = "success";
		resp.setStatus(status);
		return returnInfo;
	}
	
	/**校验订单信息**/
	private void checkCustOrder(String orderId,String fees) throws Exception{
		CustOrder custOrder = new CustOrder();
		custOrder.setId(Long.parseLong(orderId));
		List<CustOrder> tmpOrders = getDAO().find(custOrder);
		if(tmpOrders == null || tmpOrders.isEmpty()){
			CheckUtils.checkNull(null, "查询不到该订单信息");
		}
		custOrder = tmpOrders.get(0);
		BizPortalOrder bizPortalOrder = custOrder.getPortalOrder();
		if(bizPortalOrder == null ){
			CheckUtils.checkNull(null, "查询不到该订单信息");
		}
		if(!bizPortalOrder.getFees().equals(fees)){
			CheckUtils.checkNull(null, "订单金额有出入,请确认");
		}
	}
	
	private ChargeFeeBookBossReq getChargeFeeBookBossReq(ChargeFeeBookReq req){
		
		ChargeFeeBookBossReq req2Boss = new ChargeFeeBookBossReq();
		req2Boss.setCustid(req.getCustid());
		req2Boss.setFees(req.getFees());
		req2Boss.setKeyno(req.getKeyno());
		req2Boss.setIspkg("N");
		req2Boss.setGdno("");
		req2Boss.setGdnoid("");
		
		return req2Boss;
	}
	
	public LoginInfo getTestLoginInfo(){
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setCity("GZ");
		loginInfo.setLoginname("GZCYKFACS");
		loginInfo.setAreaid(103l);
		loginInfo.setDeptid(2866960l);
		loginInfo.setOperid(104129l);
		return loginInfo;
	}
	
	public LoginInfo getGridTestLoginInfo(){
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setCity("ZS");
		loginInfo.setLoginname("GZCYKFCMMS");
		loginInfo.setName("网格CMMS");
		loginInfo.setOperid(658733l);
		loginInfo.setAreaid(715l);
		loginInfo.setDeptid(101031l);
		loginInfo.setRoleid(100000000000000l);
		return loginInfo;
	}
	
}
