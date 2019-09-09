package com.maywide.biz.inter.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.maywide.biz.inter.pojo.queBossHouse.*;
import com.maywide.biz.market.pojo.BizGridHouse;
import com.maywide.core.service.PersistentService;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.install.BankParams;
import com.maywide.biz.inter.pojo.install.BizAsyncOrderBossResp;
import com.maywide.biz.inter.pojo.install.InstallOrderAsyncReq;
import com.maywide.biz.inter.pojo.install.InstallParams;
import com.maywide.biz.inter.pojo.install.PaywayParams;
import com.maywide.biz.inter.pojo.install.PrdParams;
import com.maywide.biz.market.entity.ApplyBank;
import com.maywide.biz.market.entity.ApplyInstall;
import com.maywide.biz.market.entity.ApplyProduct;
import com.maywide.biz.market.entity.ApplyProductSelect;
import com.maywide.biz.market.entity.BizPortalOrder;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.biz.market.pojo.InstallBasedataReq;
import com.maywide.biz.market.pojo.InstallBasedataResp;
import com.maywide.biz.market.pojo.QueryResHouseReq;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;
import com.maywide.core.util.DateUtils;
import com.maywide.test.InterTest;

@Service
@Transactional(rollbackFor = Exception.class)
public class InstallService extends CommonService {
	@Autowired
	private ParamService paramService;
	@Autowired
	private PersistentService persistentService;
	@Autowired
	private RuleService ruleService;

	@Transactional(readOnly = true)
	public ReturnInfo getInstallBasedata(InstallBasedataReq req,
			InstallBasedataResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		List<PrvSysparam> list = null;

		Assert.notNull(req.getCity(), "操作员所在城市编码[city]不能为空！");
		list = paramService.getData(BizConstant.SysparamGcode.SYS_ACCT_KIND);
		resp.getAcctKinds().addAll(list);

		list = paramService.getData(BizConstant.SysparamGcode.SYS_BANK);
		resp.getBanks().addAll(list);

		list = paramService.getData(BizConstant.SysparamGcode.SYS_CARD_TYPE);
		resp.getCardTypes().addAll(list);

		list = paramService.getData(BizConstant.SysparamGcode.SYS_ACCT_TYPE);
		resp.getAcctTypes().addAll(list);

		PrvSysparam param = paramService.getData(
				BizConstant.SysparamGcode.PRV_CITY, req.getCity());
		if (param != null) {
			list = paramService.getSubData(BizConstant.SysparamGcode.PRV_TOWN,
					param.getId());
			resp.getAreaIds().addAll(list);
		}

		Rule bankRule = ruleService.getRule(req.getCity(), "SYS_BANK_DEFAULT");
		if(bankRule != null && resp.getBanks() != null){
			List<PrvSysparam> unContainList = new ArrayList<PrvSysparam>();
			for(PrvSysparam sysparam:resp.getBanks()){
				if(!bankRule.getValue().contains(sysparam.getMcode())){
					unContainList.add(sysparam);
				}
			}
			resp.getBanks().removeAll(unContainList);
		}

		return returnInfo;
	}

	/**
	 * 手工同步BOSS地址
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queBossHouseForHouieid(QueBossHouseInterReq req, QueHouseInterResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getHouseid(), "houseid不能为空!");
		CheckUtils.checkEmpty(req.getWhladdr(), "要同步的地址不能为空!");
		//根据houseid 查boss 地址   先查本地库   如果有  则提示前端不能再同步  本地没有则插入本地地市库表
		List params = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM( ");
		sql.append("SELECT o.houseid,o.addrid,o.status,o.netstruct,o.whladdr,");
		sql.append("o.whgridcode,o.ywgridcode,o.gridid,o.patchid,o.areaid");
		sql.append(" FROM biz_grid_house_").append(loginInfo.getCity()).append(" o");
		sql.append(" WHERE o.houseid=? ) n");
		params.add(req.getHouseid());
		List<BizGridHouse> bizGridHouseList = getDAO().find(sql.toString(),BizGridHouse.class,
				params.toArray());
		int code = 0;
		if(bizGridHouseList!=null && bizGridHouseList.size()> 0){
			CheckUtils.checkNull(null, "该地址已经同步");
		}else {
			//本地没有找打   去BOSS查询  插入本地表
			QueBossHouseIBossReq bossReq = new QueBossHouseIBossReq();
			//bossReq.setHouseid(req.getHouseid());
			bossReq.setCurrentPage("1");
			bossReq.setPagesize("30");
			//bossReq.setAddr("广东省中山市小榄镇区竹源小榄竹源兰兴巷31号");
			bossReq.setAddr(req.getWhladdr());
			bossReq.setCity(req.getCity());
			bossReq.setRightControl("N");
			Rule rule = ruleService.getRule(req.getCity(), BizConstant.BizRuleParams.SHOWALL_ADDR);
			boolean flag = true;//如果为false则不加isstd:Y
			if(null!=rule){
				String areaids = rule.getAreaIds();
				if(null!=areaids){
					if("*".equals(areaids)|| !areaids.equals("")){
						flag = false;
					}
				}
			}
			if(flag){
				bossReq.setIsstd("Y");
			}
			String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.QUE_RESHOUSE,
					bossReq, loginInfo);
			QueBossHouseInterResp bossResp = (QueBossHouseInterResp) BeanUtil.jsonToObject(bossRespOutput, QueBossHouseInterResp.class);
			if(bossResp.getHouses()== null || bossResp.getHouses().size() <= 0){
				CheckUtils.checkNull(null, "找不到唯一地址，不能同步");
			}
			boolean index = false;
			if(bossResp.getHouses()!= null && bossResp.getHouses().size()>0){
				List<HouseBossInfo> houses = bossResp.getHouses();
				for (HouseBossInfo houseBossInfo:houses){
					if(houseBossInfo.getHouseid().equals(req.getHouseid())){
						index = true;
						StringBuffer sqlBuffer = new StringBuffer();
						sqlBuffer.append("INSERT INTO biz_grid_house_").append(req.getCity()).append(" (houseid, addrid, status, patchid, areaid, netstruct, whladdr, whgridcode, ywgridcode, gridid) ");
						sqlBuffer.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
						String houseid = houseBossInfo.getHouseid();
						String addrid = houseBossInfo.getAddrid();
						String status = houseBossInfo.getStatus();
						String patchid = houseBossInfo.getPatchid();
						String areaid = houseBossInfo.getAreaid();
						String netstruct = houseBossInfo.getNetstruct();
						String whladdr = houseBossInfo.getWhladdr();
						String whgridcode = houseBossInfo.getWhgridcode();
						String ywgridcode = req.getCity();
						String gridid = "-1";
						persistentService.clear();
						int i = persistentService.executeSql(sqlBuffer.toString(),houseid,addrid,status,patchid,areaid,netstruct,whladdr,whgridcode,ywgridcode,gridid);
				        if(i > 0){//成功
					        code = 1;
				        }else {
				        	code = 0;
				       }
			     }
		     }
			}
			if(!index){
				CheckUtils.checkNull(null, "找不到唯一地址，不能同步");
			}

		}
		resp.setCode(code);
		return returnInfo;
	}
	/**
	 * 查询BOSS地址库
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queryResHouse(QueryResHouseReq req) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
//		LoginInfo loginInfo = InterTest.getGridTestLoginInfo2();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		JSONObject requestContent = new JSONObject();
		requestContent.put("pagesize", "10");
		if (StringUtils.isNotBlank(req.getPagesize())) {
			requestContent.put("pagesize", req.getPagesize());
		}
		if (StringUtils.isNotBlank(req.getCurrentPage())) {
			requestContent.put("currentPage", req.getCurrentPage());
		}
		if (StringUtils.isNotEmpty(req.getAddr())) {
			requestContent.put("addr", req.getAddr());
		}
		if (StringUtils.isNotEmpty(req.getAreaid())) {
			requestContent.put("areaid", req.getAreaid());
		}
		if (StringUtils.isNotEmpty(req.getPatch())) {
			requestContent.put("patch", req.getPatch());
		}
		if (StringUtils.isNotEmpty(req.getCity())) {
			requestContent.put("city", req.getCity());
		}
		if (StringUtils.isNotEmpty(req.getRightControl())) {
			requestContent.put("rightControl", req.getRightControl());
		}
		//ywp 如果有设置，就显示全部，如果为空的话，默认显示标准地址
		Rule rule = ruleService.getRule(req.getCity(), BizConstant.BizRuleParams.SHOWALL_ADDR);
		boolean flag = true;//如果为false则不加isstd:Y
		if(null!=rule){
			String areaids = rule.getAreaIds();
			if(null!=areaids){
				if("*".equals(areaids)||areaids.indexOf(req.getAreaid())>-1){
					flag = false;
				}
			}
		}
		if(flag){
			requestContent.put("isstd", "Y");
		}
		String response = bossHttpClientService.requestPost(
				req.getBizorderid(),
				BizConstant.BossInterfaceService.QUE_RESHOUSE,
				requestContent.toString(), loginInfo);
		if (StringUtils.isEmpty(response)) {
			throw new BusinessException("BOSS接口调用出错，没有返回数据");
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.setDateFormat(dateFormat);

		JsonNode nodeTree = objectMapper.readTree(response);
		parseBossResponse(nodeTree,
				BizConstant.BossInterfaceService.QUE_RESHOUSE);

		// resp = nodeTree.get("output").toString();
		returnInfo.setMessage(nodeTree.get("output").toString());

		return returnInfo;
	}



	/**
	 * 用户资料补录
	 * 
	 * @param req
	 * @param
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo installOrderSync(InstallOrderAsyncReq req) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		Long orderid =  Long.parseLong(req.getOrderid());
		CheckUtils.checkNull(req, "请求信息不能为空");
		CustOrder custOrder  = new CustOrder();
		custOrder.setId(Long.parseLong(req.getOrderid()));
		List<CustOrder> custorders = getDAO().find(custOrder);
		InstallOrderAsyncReq installOrderAsyncReq = new InstallOrderAsyncReq();
		if(req.getAddrparam() != null){
			installOrderAsyncReq.setAddrparam(req.getAddrparam());
		}
		if(req.getOnecefeeparams() != null){
			installOrderAsyncReq.setOnecefeeparams(req.getOnecefeeparams());
		}
		for (CustOrder object : custorders) {
			installOrderAsyncReq.setCustid         (getConvertString(object.getCustid()))         ; 	                                        
			installOrderAsyncReq.setOrderid        (getConvertString(object.getId())    )     ;                                        
			installOrderAsyncReq.setOrdercode      (object.getOrdercode())         ;                                        
			installOrderAsyncReq.setOrderstatus    (object.getOrderstatus())         ;                                        
			installOrderAsyncReq.setSynctime       (DateUtils.formatTime(new Date()))         ; 
			if(StringUtils.isNotBlank(req.getOpcode())){
				installOrderAsyncReq.setOpcode         (req.getOpcode())         ;
			}else{
				installOrderAsyncReq.setOpcode         (object.getOpcode())         ;
			}
			installOrderAsyncReq.setOptime         (getConvertString(DateUtils.formatTime(object.getOptime())))         ;                                        
			installOrderAsyncReq.setOprdep         (getConvertString(object.getOprdep()))         ;                                        
			installOrderAsyncReq.setOperator       (getConvertString(object.getOperator()))         ;                                        
			installOrderAsyncReq.setAreaid         (getConvertString(object.getAreaid()))         ;                                        
			installOrderAsyncReq.setDescribe       (object.getDescrip())         ;                                        
			installOrderAsyncReq.setCity           (object.getCity())         ;      
			if(StringUtils.isNotBlank(req.getSystemid())){
				installOrderAsyncReq.setSystemid       (req.getSystemid())         ; 
			}else{
				installOrderAsyncReq.setSystemid       ("GRID")         ; 
			}

		}
		ApplyInstall applyInstall  = new ApplyInstall();
		applyInstall.setOrderid(orderid);
		List<ApplyInstall>  applyInstalls = getDAO().find(applyInstall);
		InstallParams installParam ;
		for (ApplyInstall object : applyInstalls) {
			installParam = new InstallParams();
			installParam.setHouseid        (getConvertString(object.getHouseid()))  ; 
			installParam.setAddr			(object.getWhladdr())  ; 
			installParam.setPatchid        (getConvertString(object.getPatchid()))  ; 
			installParam.setName           (object.getName())  ; 
			installParam.setCardtype       (object.getCardtype())  ; 
			installParam.setCardno         (object.getCardno())  ; 
			installParam.setLinkman        (object.getName());
			installParam.setLinkphone      (object.getLinkphone())  ; 
			installParam.setMobile         (object.getLinkphone())  ; 
			installParam.setPermark        (object.getPercomb())  ; 
			installParam.setSmnouseprop		(object.getSmuseprop());
			installParam.setStbuseprop		(object.getStbuseprop());
			if(StringUtils.isNotBlank(req.getOmode())){
				installParam.setOmode(req.getOmode());
			}else{
				if(object.getOservid()==null){
					installParam.setOmode          ("0")  ;  
				}else{
					installParam.setOmode          ("1")  ;  	    		
				}
			}
			installParam.setServid         (getConvertString(object.getOservid()))  ; // 没有servid，用oservid赋值
			installParam.setOservid        (getConvertString(object.getOservid()));

			installParam.setOlogicdevno    (object.getOlogicdevno())  ; 
			installParam.setDevback        (object.getDevback())  ; 
			installParam.setStbback        (object.getStbback())  ; 

			installParam.setPredate        ((object.getPredate()!=null?DateUtils.getFormatDateString(object.getPredate(),DateUtils.DEFAULT_TIME_FORMAT):null))  ; 
			installParam.setLogicdevno     (object.getLogicdevno())  ; 
			installParam.setStbno          (object.getStbno())  ; 
			installParam.setPercomb			(object.getPercomb());
			//	    	installParam.setBankid         (object.get)  ; // 无bankid
			//	    	installParam.setFeekind        (object.get)  ;  // 无feedkind
			if(StringUtils.isBlank(object.getPservid())){
				installParam.setServtype       ("0")  ; // 无servtype, 传一个默认值0, 客户要求开副机，这个值就要传1
			}else{
				installParam.setServtype       ("1")  ;
			}
			//	    	installParam.setPservid        (object.getPservid())  ; // 无pservid
			installParam.setMaindevno		(object.getPservid());
			//	    	installParam.setMemo           (object.d)  ;    // 无memo    	
			installOrderAsyncReq.getInstallparams().add(installParam);
		}
		ApplyProduct applyProduct = new ApplyProduct();
		applyProduct.setOrderid(orderid);
		List<ApplyProduct>  applyProducts = getDAO().find(applyProduct);
		PrdParams prdparam ;

		for (ApplyProduct object : applyProducts) {
			prdparam = new PrdParams();
			prdparam.setServid 		   (getConvertString(object.getServid()))	;                                                                                                   
			prdparam.setLogicdevno     (object.getLogicdevno())  ;                           
			prdparam.setSalespkgid     (getConvertString(object.getSalespkgid()) ) ;                           
			prdparam.setPid            (getConvertString(object.getPid())  );                           
			prdparam.setSalesid(getConvertString(object.getSalesid()));                         
			prdparam.setCount          (getConvertString(object.getCount()) ) ;                           
			prdparam.setUnit           (getConvertString(object.getUnit()))  ;   
			ApplyProductSelect applyProductSelect = new ApplyProductSelect();
			applyProductSelect.setOrderid(orderid);
			applyProductSelect.setKnowid(object.getKnowid());
			List<ApplyProductSelect> li = getDAO().find(applyProductSelect);
			StringBuffer  selprds = new StringBuffer();
			for (ApplyProductSelect productSelect : li) {
				selprds.append(productSelect.getPid());
				selprds.append(",");
			}
			if (StringUtils.isNotEmpty(selprds.toString())) {
				prdparam.setSelprds(selprds.toString()) ;
			}
			if(li != null && li.size() > 0 && li.get(0) != null){
				applyProductSelect = li.get(0);
				prdparam.setSelectid(Long.toString(applyProductSelect.getSelectid()));
				/*if(prdparam.getPid() == null ||prdparam.getPid().equals("")){
        			prdparam.setPid(Long.toString(applyProductSelect.getPid()));
        		}*/
			}

			installOrderAsyncReq.getPrdparams().add(prdparam);
		}

		// 查询BIZ_PORTAL_ORDER表，获取payway信息
		BizPortalOrder order = new BizPortalOrder();
		order.setId(orderid);
		List<BizPortalOrder>  orders = getDAO().find(order);
		if (orders != null && orders.size() > 0) {
			// 只有一个payway
			BizPortalOrder bizPortalOrder = orders.get(0);
			PaywayParams paywayParam = new PaywayParams();
			paywayParam.setPaycode(bizPortalOrder.getPaycode());
			paywayParam.setPayway(bizPortalOrder.getPayway());

			installOrderAsyncReq.setPaywayparam(paywayParam);
		}

		// 查询BIZ_APPLY_BANK表， 获取BANK信息
		ApplyBank quebank = new ApplyBank();
		quebank.setOrderid(orderid);
		List<ApplyBank>  banks = getDAO().find(quebank);
		if (banks != null && banks.size() > 0) {
			// 只有一个bank
			ApplyBank bank = banks.get(0);
			BankParams bankparam = new BankParams();
			bankparam.setAcctname(bank.getAcctname());
			bankparam.setBankcode(bank.getBankcode());
			bankparam.setAcctno(bank.getAcctno());
			bankparam.setAcctkind(bank.getAcctkind());
			bankparam.setAccttype(bank.getAccttype());
			bankparam.setServid(getConvertString(bank.getServid()));

			installOrderAsyncReq.setBankparam(bankparam);

		}

		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
				BizConstant.BossInterfaceService.BIZ_ASYNC_ORDER, installOrderAsyncReq,
				loginInfo);
		JsonObject object = new JsonObject();
		if(bossRespOutput == null){
			throw new BusinessException("同步工单BOSS接口调用失败。");
		}else if(bossRespOutput.equals("null")){
			object.addProperty("returnCode", "000");
			object.addProperty("serialno", "");
			object.addProperty("message", "");
			object.addProperty("orderid", "");
		}else if(StringUtils.isNotBlank(getOrderId(bossRespOutput))){
			object.addProperty("returnCode", "000");
			object.addProperty("serialno", "");
			object.addProperty("message", "");
			object.addProperty("orderid", getOrderId(bossRespOutput));
		}else{
			object.addProperty("returnCode", "001");
			object.addProperty("serialno", "");
			object.addProperty("orderid", "");
			object.addProperty("message", bossRespOutput);
		}

		BizAsyncOrderBossResp resp = new BizAsyncOrderBossResp();
		copyBossResp2InterResp4installOrderAsync(resp, object.toString());
		// 000-成功, 其他-失败
		if (!"000".equals(resp.getReturnCode())) {
			if (StringUtils.isNotEmpty(resp.getMessage())) {
				throw new BusinessException(resp.getMessage());
			} else {
				throw new BusinessException("同步工单BOSS接口调用失败。");
			}
		}
		if(StringUtils.isNotBlank(resp.getOrderid())){
			BizPortalOrder portalOrder = new BizPortalOrder();
			portalOrder.setId(orderid);
			List<BizPortalOrder> tmps = getDAO().find(portalOrder);
			if(tmps != null && !tmps.isEmpty()){
				portalOrder = tmps.get(0);
				portalOrder.setResporderid(Long.parseLong(resp.getOrderid()));
				getDAO().update(portalOrder);
			}
		}

		return returnInfo;
	}

	private String getOrderId(String output) throws JsonProcessingException, IOException{
		if(StringUtils.isBlank(output)){
			return null;
		}
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			ObjectMapper objectMapper = new ObjectMapper();

			objectMapper.setDateFormat(dateFormat);

			JsonNode nodeTree = objectMapper.readTree(output);

			return nodeTree.get("orderid").toString();
		}catch(Exception e){
			return null;
		}
	}

	private void copyBossResp2InterResp4installOrderAsync(
			BizAsyncOrderBossResp resp, String jsonStr) throws Exception {

		BizAsyncOrderBossResp bossResp = (BizAsyncOrderBossResp) BeanUtil
				.jsonToObject(jsonStr, BizAsyncOrderBossResp.class);

		// 因为字段是一样的，这里可以偷懒一下，
		// 后面如果接口对外字段和boss返回字段不一致，则自行写使用set方法设置值
		BeanUtils.copyProperties(resp, bossResp);
	}

	private String getConvertString(Object field) {
		if (field == null) {
			return null;
		}
		return String.valueOf(field);
	}

}
