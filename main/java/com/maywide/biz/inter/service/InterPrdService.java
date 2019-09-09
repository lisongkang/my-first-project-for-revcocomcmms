package com.maywide.biz.inter.service;

import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.entity.ApkPackage;
import com.maywide.biz.core.entity.Combination;
import com.maywide.biz.core.entity.Kits;
import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.entity.CustBizNetWorkOrderPool;
import com.maywide.biz.inter.pojo.bizSaleCmit.BizSaleCommitReq;
import com.maywide.biz.inter.pojo.bizfeein.*;
import com.maywide.biz.inter.pojo.bizpreprocess.DeveloperBossBO;
import com.maywide.biz.inter.pojo.deviceSor.DeviceSourceInfo;
import com.maywide.biz.inter.pojo.deviceSor.DeviceSourceQuesp;
import com.maywide.biz.inter.pojo.deviceSor.DeviceSourceResp;
import com.maywide.biz.inter.pojo.devuse.DevUseBean;
import com.maywide.biz.inter.pojo.ordercommit.OrderCommitBossReq;
import com.maywide.biz.inter.pojo.ordercommit.OrderCommitBossResp;
import com.maywide.biz.inter.pojo.ordercommit.OrderCommitInterReq;
import com.maywide.biz.inter.pojo.ordercommit.OrderCommitInterResp;
import com.maywide.biz.inter.pojo.queCustAddress.CustAddrsResp;
import com.maywide.biz.inter.pojo.queCustAddress.QueCustomerAddressReq;
import com.maywide.biz.inter.pojo.queCustAddress.QueCustomerBossReq;
import com.maywide.biz.inter.pojo.queDevBack.QueDevIsBackReq;
import com.maywide.biz.inter.pojo.queDevBack.QueDevIsBackResp;
import com.maywide.biz.inter.pojo.queOrderNotice.QueOrderNoticeReq;
import com.maywide.biz.inter.pojo.queOrderNotice.QueOrderNoticeResp;
import com.maywide.biz.inter.pojo.queServPrdInfo.QueServPrdBossInterReq;
import com.maywide.biz.inter.pojo.queServPrdInfo.QueServPrdInterReq;
import com.maywide.biz.inter.pojo.queServPrdInfo.QueServPrdInterResp;
import com.maywide.biz.inter.pojo.queServPrdInfo.ServPrdParentBean;
import com.maywide.biz.inter.pojo.quebossorder.*;
import com.maywide.biz.inter.pojo.queproduct.ProductInfo;
import com.maywide.biz.inter.pojo.queproduct.QueProductQuesp;
import com.maywide.biz.inter.pojo.queproduct.QueProductResp;
import com.maywide.biz.inter.pojo.querySalespkgKnowZs.QuerySalespkgKnowInterReqZs;
import com.maywide.biz.inter.pojo.querycatalog.QueryCatalogInterReq;
import com.maywide.biz.inter.pojo.querycatalog.QueryCatalogInterResp;
import com.maywide.biz.inter.pojo.querysalespkgknow.*;
import com.maywide.biz.inter.pojo.queservprddet.QueServPrddetBossReq;
import com.maywide.biz.inter.pojo.queservprddet.QueServPrddetBossResp;
import com.maywide.biz.inter.pojo.queservprddet.QueServPrddetInterReq;
import com.maywide.biz.inter.pojo.queservprddet.QueServPrddetInterResp;
import com.maywide.biz.inter.pojo.queuserprd.*;
import com.maywide.biz.inter.pojo.queversion.QueVersionInterReq;
import com.maywide.biz.inter.pojo.queversion.QueVersionInterResp;
import com.maywide.biz.inter.pojo.sendrandomcode.SendRandomCodeUapReq;
import com.maywide.biz.market.entity.ApplyArrear;
import com.maywide.biz.market.entity.BizPortalOrder;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.biz.pay.unify.manager.ParamsManager;
import com.maywide.biz.pay.weixin.WeixinPayService;
import com.maywide.biz.prd.entity.*;
import com.maywide.biz.prv.entity.OrderAction;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;
import com.maywide.core.util.DateTimeUtil;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional(rollbackFor = Exception.class)
public class InterPrdService extends CommonService {

	@Autowired
	private PersistentService persistentService;

	@Autowired
	private PubService pubService;
	@Autowired
	private ParamService paramService;
	@Autowired
	private WeixinPayService weixinPayService;
	@Autowired
	private RuleService ruleService;
	@Autowired
	private InterApplyService applyService;


	
	@Transactional(readOnly = true)
	public ReturnInfo querySalespkgKnowZs(QuerySalespkgKnowInterReqZs req, QuerySalespkgKnowInterResp resp)
			throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		List<SalespkgKnowsBO> salespkgKnowsList = queSalespkgKnowZs(req);
		List<ImSalepkgKnowsBO> realDatas = new ArrayList<ImSalepkgKnowsBO>();
		for (int i = 0; i < salespkgKnowsList.size(); i++) {
			SalespkgKnowsBO bo = salespkgKnowsList.get(i);
			ImSalepkgKnowsBO imBo = new ImSalepkgKnowsBO(bo);
			if (bo.getObjtype().equals(BizConstant.PrdSalespkgKnowObjtype.GOODS)
					|| bo.getObjtype().equals(BizConstant.PrdSalespkgKnowObjtype.GOODS_TYPE)) {
				imBo.setDefauNumber(0);
				imBo.setUnitName("月");
				imBo.setModify(false);
			} else {
				Cycle cycle = getPackageCycle(imBo, loginInfo);
				if(cycle != null){
					imBo.setDefauNumber(Integer.parseInt(cycle.getDefaNumber()));
				}
				Cycle cycle2 = getUnitName(bo);
				if (cycle2 != null && StringUtils.isNotBlank(cycle2.getUnitName())) {
					imBo.setUnitName(cycle2.getUnitName());
				} else {
					imBo.setUnitName("周期");
				}
				if (cycle2 != null && cycle2.getDefaNumber() != null && cycle != null) {
					imBo.setUnitName(cycle2.getUnitName());
					imBo.setScopeflag(cycle2.getScopeflag());
					if (cycle2.getDefaNumber().equals("0")) {
						if (cycle2.getCycleNum() != null && cycle2.getCycleNum() == 1) {
							imBo.setMinNumber(0);
							imBo.setMaxNumber(1);
						}
					} else if (cycle2.getDefaNumber().equals("1")) {
						imBo.setMinNumber(0);
						imBo.setMaxNumber(1);
					}
				} else {
					if (cycle2 == null || cycle2.getDefaNumber() != null) {
						imBo.setUnitName("周期");
						imBo.setModify(false);
						imBo.setDefauNumber(1);
					}
				}
				hanlderCityPrdRule(loginInfo,imBo,req.getOpcode(),cycle2,cycle);
			}
			realDatas.add(imBo);
		}
		
		if(req.isNeeddetail()){
			addRule2SaleKnowle(realDatas,loginInfo);
			addIsPostpone2SaleKnowle(realDatas,loginInfo);
		}
		
		
		if(StringUtils.isNotBlank(req.getCustid()) && req.isNeeddetail()){
			if(StringUtils.isBlank(req.getOpcode()) || req.getOpcode().equalsIgnoreCase("BIZ_PRD_ORDER")){
QueUserPrdBossReq req2Boss = getQueUserPrdReq2Boss(req.getCustid(),req.getKeyno(),req.getPermark());
				
				String serverCode = "";
				if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
					serverCode = BizConstant.ServerCityBossInterface.QUE_USERPRDINFO;
				}else{
					serverCode = BizConstant.BossInterfaceService.QUE_USERPRDINFO;
				}
				
				String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
						serverCode, req2Boss,loginInfo);
				if(StringUtils.isNotBlank(bossRespOutput)){
					 QueUserPrdBossResp bossResp = (QueUserPrdBossResp) BeanUtil
				                .jsonToObject(bossRespOutput, QueUserPrdBossResp.class);
					 if(realDatas != null && realDatas.size() > 0){
						 for(ImSalepkgKnowsBO imBo:realDatas){
							 if(imBo.getKnowObjDet().getGoods() == null){
								 for(UserPrdsBO userPrdsBO:bossResp.getProds()){
									 if(userPrdsBO.getPid().equals(Long.toString(imBo.getObjid()))){
										 imBo.setOrdered(true);
										 break;
									 }
								 }
							 }else{
								 for(UserPrdsBO userPrdsBO:bossResp.getProds()){
									 if(userPrdsBO.getPid().equals(imBo.getKnowObjDet().getGoods().getPid())){
										 imBo.setOrdered(true);
										 break;
									 }
								 }
							 }
						 }
					 }
				}
			}
			
		}
		resp.setKnows(realDatas);

		return returnInfo;
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<SalespkgKnowsBO> queSalespkgKnowZs(QuerySalespkgKnowInterReqZs req) throws Exception {

		StringBuffer sql = new StringBuffer();
		List paramList = new ArrayList();
		// sql.append(" select
		// knowid,knowname,brief,objtype,objid,price,opcodes,tocust,wordexp,feeexp,city
		// from( ");
		sql.append(" SELECT * from( ");
		sql.append(" SELECT b.knowiD, ");
		sql.append("        b.knowname, ");
		sql.append("        b.brief, ");
		sql.append("        b.objtype, ");
		sql.append("        b.objid, ");
		sql.append("        b.price, ");
		sql.append("        b.opcodes, ");
		sql.append("        b.tocust, ");
		sql.append("        b.wordexp, ");
		sql.append("        b.feeexp, ");
		sql.append("        b.icon, ");
		sql.append("        b.city");
//		sql.append("		c.ispostpone");
		sql.append("   FROM goods_series_item a,PRD_SALESPKG_KNOW b ");
		sql.append("  WHERE a.knowid = b.knowid ");
		if (StringUtils.isNotBlank(req.getKnowname())) {
			sql.append("    AND b.knowname LIKE ? ");
			paramList.add(req.getKnowname() + "%");
		}

		if (req.getSeriesId() != null) {
			sql.append("    AND a.series_id = ? ");
			paramList.add(req.getSeriesId());
		}

		if (StringUtils.isNotBlank(req.getKnowid())) {
			sql.append("    AND a.knowid = ? ");
			paramList.add(req.getKnowid());
		}

		if (req.getObjtype() != null) {
			sql.append("    AND b.objtype = ? ");
			paramList.add(req.getObjtype());
		}

		if (req.getSalespkgcode() != null) {
			sql.append(" AND ( b.objid IN (SELECT SALESPKGID FROM prd_salespkg WHERE salespkgcode=? ) ");
			sql.append(" OR b.objid IN (SELECT salesid FROM prd_sales WHERE salescode=? ) ) ");
			paramList.add(req.getSalespkgcode());
			paramList.add(req.getSalespkgcode());
		}
		if(StringUtils.isNotBlank(req.getOpcode())){
			sql.append("		AND (EXISTS (");
			sql.append("		SELECT W.SALESPKGID FROM prd_salespkg W");
			sql.append("		WHERE W.SALESPKGID = b.OBJID");
			sql.append("		AND FIND_IN_SET(?,W.BIZCODE)) ");
			sql.append("		OR b.objtype <> ? )");
			paramList.add(req.getOpcode());
			paramList.add(BizConstant.PrdSalespkgKnowObjtype.SALESPKG);
		}
		sql.append("	ORDER BY a.PRI");
		sql.append("    )v ");

		List<SalespkgKnowsBO> salespkgKnowsList = getDAO().find(sql.toString(), SalespkgKnowsBO.class,
				paramList.toArray());

		List<SalespkgKnowsBO> choiceList = new ArrayList<SalespkgKnowsBO>();

		if (salespkgKnowsList != null) {
			for (SalespkgKnowsBO bo : salespkgKnowsList) {
				if (bo.getObjtype().equals(BizConstant.PrdSalespkgKnowObjtype.PRD) && req.getBusType() != -1) {
					String bussineSql = "select pd.PNAME as pname,pd.PCLASS as pcls from PRD_PCODE pd where pd.PERMARK = ? and pd.PID = ?";
					List<Product> product = getDAO().find(bussineSql, Product.class, req.getBusType(), bo.getObjid());
					if (product == null || product.isEmpty()) {
						choiceList.add(bo);
					}
				}
			}
		}
		salespkgKnowsList.removeAll(choiceList);

		// 如果不需要详细信息则返回
		if (!req.isNeeddetail()) {
			return salespkgKnowsList;
		}

		if (null != salespkgKnowsList && salespkgKnowsList.size() > 0 && !salespkgKnowsList.isEmpty()) {
			for (SalespkgKnowsBO know : salespkgKnowsList) {
				if (StringUtils.isNotEmpty(know.getKnowname())) {
					know.setKnowname(know.getKnowname().replace("\"", ""));
				}
				if (StringUtils.isNotEmpty(know.getBrief())) {
					know.setBrief(know.getBrief().replace("\"", ""));
				}
				if (StringUtils.isNotEmpty(know.getTocust())) {
					know.setTocust(know.getTocust().replace("\"", ""));
					know.setTocustTitle("建议推荐目标客户特征");
				}
				if (StringUtils.isNotEmpty(know.getWordexp())) {
					know.setWordexp(know.getWordexp().replace("\"", ""));
				}
				if (StringUtils.isNotEmpty(know.getFeeexp())) {
					know.setFeeexp(know.getFeeexp().replace("\"", ""));
				}
				KnowObjDet knowObjDet = getKnowObjDetInfo(know.getObjtype(), know.getObjid());
				// knowObjDet
				know.setKnowObjDet(knowObjDet);
			}
		}
		return salespkgKnowsList;
	}

	
	@Transactional(readOnly = true)
	public ReturnInfo queryCatalog(QueryCatalogInterReq req, QueryCatalogInterResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");

		StringBuffer sql = new StringBuffer();
		List paramList = new ArrayList();

		sql.append(" SELECT * FROM ( ");
		sql.append(" SELECT t.catalogid id, t.* ");
		sql.append("   FROM prd_catalog t ");
		sql.append("  WHERE 1 = 1 ");

		sql.append("    AND t.city = ? ");
		paramList.add(loginInfo.getCity());

		if (StringUtils.isNotBlank(req.getCatalogname())) {
			sql.append("    AND t.CATALOGNAME = ? ");
			paramList.add(req.getCatalogname());
		}
		if (StringUtils.isNotBlank(req.getCatalogstatus())) {
			sql.append("    AND t.CATALOGSTATUS = ? ");
			paramList.add(req.getCatalogstatus());
		}

		sql.append("  ORDER BY t.pri ");
		sql.append(" ) v ");

		List<Catalog> catalogList = getDAO().find(sql.toString(), Catalog.class, paramList.toArray());

		List<Catalog> retSet = new ArrayList<Catalog>();
		List<Catalog> opSet = new ArrayList<Catalog>();
		if (BeanUtil.isListNotNull(catalogList)) {
			for (Catalog catalog : catalogList) {
				CatalogCondtion condtion = new CatalogCondtion();
				condtion.setCatalogid(catalog.getId());
				List<CatalogCondtion> condtionList = getDAO().find(condtion);
				if (BeanUtil.isListNull(condtionList)) {
					// 没有限制条件的目录
					retSet.add(catalog);
				} else {
					for (CatalogCondtion obj : condtionList) {
						if (StringUtils.isBlank(obj.getContiontype())) {
							// 没有限制条件的目录
							retSet.add(catalog);
						} else if (BizConstant.CATALOG_CONDITION_TYPE.OPERATOR.equals(obj.getContiontype())) {
							// 指定操作员的目录
							if (obj.getContionvalue().equals(loginInfo.getOperid())) {
								retSet.add(catalog);
							}
						} else if (BizConstant.CATALOG_CONDITION_TYPE.DEPT.equals(obj.getContiontype())) {
							// 指定部门的目录
							if (obj.getContionvalue().equals(loginInfo.getDeptid())) {
								retSet.add(catalog);
							}
						}else if(BizConstant.CATALOG_CONDITION_TYPE.OPCODE.equals(obj.getContiontype())){
							if(StringUtils.isNotBlank(req.getBizcode()) && obj.getContionvalue().equalsIgnoreCase(req.getBizcode())){
								opSet.add(catalog);
							}
						}
					}
				}
			}
		}

		List<Catalog> retList = new ArrayList();
		if(!opSet.isEmpty()){
			retList.addAll(opSet);
		}else{
			retList.addAll(retSet);
		}
		resp.setCatalogs(retList);

		return returnInfo;
	}

	@Transactional(readOnly = true)
	public ReturnInfo querySalespkgKnow(QuerySalespkgKnowInterReq req, QuerySalespkgKnowInterResp resp)
			throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		List<SalespkgKnowsBO> salespkgKnowsList = queSalespkgKnow(req);
		List<ImSalepkgKnowsBO> realDatas = new ArrayList<ImSalepkgKnowsBO>();
		for (int i = 0; i < salespkgKnowsList.size(); i++) {
			SalespkgKnowsBO bo = salespkgKnowsList.get(i);
			ImSalepkgKnowsBO imBo = new ImSalepkgKnowsBO(bo);
			if (bo.getObjtype().equals(BizConstant.PrdSalespkgKnowObjtype.GOODS)
					|| bo.getObjtype().equals(BizConstant.PrdSalespkgKnowObjtype.GOODS_TYPE)) {
				imBo.setDefauNumber(0);
				imBo.setUnitName("月");
				imBo.setModify(false);
			} else {
				Cycle cycle = getPackageCycle(imBo, loginInfo);
				if(cycle != null){
					imBo.setDefauNumber(Integer.parseInt(cycle.getDefaNumber()));
				}
				Cycle cycle2 = getUnitName(bo);
				if (cycle2 != null && StringUtils.isNotBlank(cycle2.getUnitName())) {
					imBo.setUnitName(cycle2.getUnitName());
				} else {
					imBo.setUnitName("周期");
				}
				if (cycle2 != null && cycle2.getDefaNumber() != null && cycle != null) {
					imBo.setUnitName(cycle2.getUnitName());
					imBo.setScopeflag(cycle2.getScopeflag());
					if (cycle2.getDefaNumber().equals("0")) {
						if (cycle2.getCycleNum() != null && cycle2.getCycleNum() == 1) {
							imBo.setMinNumber(0);
							imBo.setMaxNumber(1);
						}
					} else if (cycle2.getDefaNumber().equals("1")) {
						imBo.setMinNumber(0);
						imBo.setMaxNumber(1);
					}
				} else {
					if (cycle2 == null || cycle2.getDefaNumber() != null) {
						imBo.setUnitName("周期");
						imBo.setModify(false);
						imBo.setDefauNumber(1);
					}
				}
				hanlderCityPrdRule(loginInfo,imBo,req.getOpcode(),cycle2,cycle);
			}
			realDatas.add(imBo);
		}
		
		if(req.isNeeddetail()){
			addRule2SaleKnowle(realDatas,loginInfo);
			addIsPostpone2SaleKnowle(realDatas,loginInfo);
		}
		
		
		if(StringUtils.isNotBlank(req.getCustid()) && req.isNeeddetail()){
			if(StringUtils.isBlank(req.getOpcode()) || req.getOpcode().equalsIgnoreCase("BIZ_PRD_ORDER")){
                QueUserPrdBossReq req2Boss = getQueUserPrdReq2Boss(req.getCustid(),req.getKeyno(),req.getPermark());
				
				String serverCode = "";
				if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
					serverCode = BizConstant.ServerCityBossInterface.QUE_USERPRDINFO;
				}else{
					serverCode = BizConstant.BossInterfaceService.QUE_USERPRDINFO;
				}
				
				String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
						serverCode, req2Boss,loginInfo);
				if(StringUtils.isNotBlank(bossRespOutput)){
					 QueUserPrdBossResp bossResp = (QueUserPrdBossResp) BeanUtil
				                .jsonToObject(bossRespOutput, QueUserPrdBossResp.class);
					 if(realDatas != null && realDatas.size() > 0){
						 for(ImSalepkgKnowsBO imBo:realDatas){
							 if(imBo.getKnowObjDet().getGoods() == null){
								 for(UserPrdsBO userPrdsBO:bossResp.getProds()){
									 if(userPrdsBO.getPid().equals(Long.toString(imBo.getObjid()))){
										 imBo.setOrdered(true);
										 break;
									 }
								 }
							 }else{
								 for(UserPrdsBO userPrdsBO:bossResp.getProds()){
									 if(userPrdsBO.getPid().equals(imBo.getKnowObjDet().getGoods().getPid())){
										 imBo.setOrdered(true);
										 break;
									 }
								 }
							 }
						 }
					 }
				}
			}
			
		}
		resp.setKnows(realDatas);

		return returnInfo;
	}
	
	private void addRule2SaleKnowle(List<ImSalepkgKnowsBO> realDatas,LoginInfo loginInfo) throws Exception{
		Rule chgAbleRule = ruleService.getRule("PRD_GOODS_CHG_UNABLE");
		if(chgAbleRule != null){
			String datas = chgAbleRule.getValue();
			if(datas.contains(loginInfo.getCity())){
				for(ImSalepkgKnowsBO bo:realDatas){
					bo.setChgAble(true);
				}
			}
		}
		
	}
	
	private void addIsPostpone2SaleKnowle(List<ImSalepkgKnowsBO> realDatas,LoginInfo loginInfo) throws Exception{
		if(realDatas == null || realDatas.isEmpty()) return;
		if(realDatas.get(0).getObjtype().equals(BizConstant.PrdSalespkgKnowObjtype.GOODS)){
			for(ImSalepkgKnowsBO bo:realDatas){
				bo.setIspostpone("Y");
			}
			return;
		}
		if(realDatas.get(0).getObjtype().equals(BizConstant.PrdSalespkgKnowObjtype.PRD)){
			List params = new ArrayList();
			StringBuffer sb = new StringBuffer();
			sb.append("		SELECT * FROM (");
			sb.append("		SELECT A.* FROM PRD_ORDERACTION A,PRD_PCODE B");
			sb.append("		WHERE A.PSUBCLASS = B.PSUBCLASS");
			sb.append("		AND (A.PID = ? OR A.PID = ?)");
			sb.append("		AND (A.AREAID = ? OR A.AREAID = ?)");
			sb.append(" 	)v ");
			sb.append("		ORDER BY PRI DESC");
			sb.append("		LIMIT 1");
			params.add("*");
			params.add(realDatas.get(0).getObjid().toString());
			params.add("*");
			params.add(loginInfo.getAreaid().toString());
			
			List<OrderAction> datas = getDAO().find(sb.toString(), OrderAction.class, params.toArray());
			if(datas == null || datas.isEmpty()){
				for(ImSalepkgKnowsBO bo:realDatas){
					bo.setIspostpone("Y");
				}
				return;
			}
			OrderAction action = datas.get(0);
			for(ImSalepkgKnowsBO bo:realDatas){
				bo.setIspostpone(action.getIspostpone());
			}
			return;
		}
		
	}
	
	private void hanlderCityPrdRule(LoginInfo loginInfo,ImSalepkgKnowsBO imBo,String opcode,Cycle cycle2,Cycle cycle) throws Exception{
		if(StringUtils.isBlank(opcode)){
			return;
		}
		Rule rule = ruleService.getRule( "PRD_UNIT_MODIFY_RULE",loginInfo.getCity(),opcode);
		if(rule != null){
			if (cycle2 != null && cycle2.getDefaNumber() != null && cycle2.getDefaNumber().equals("0")) {
				imBo.setDefauNumber(Integer.parseInt(rule.getValue()));
			} else {
				imBo.setDefauNumber(0);
			}
		}
		PrvSysparam prvSysparam = paramService.getData("BIZ_FORCE_MODIFY", loginInfo.getCity());
		if(prvSysparam != null && StringUtils.isNotBlank(prvSysparam.getData())){
			if(prvSysparam.getData().contains(opcode)){
				imBo.setModify(false);
			}
		}
	}
	
	 private QueUserPrdBossReq getQueUserPrdReq2Boss(String custid,String keyno,String permark) {
	        QueUserPrdBossReq queUserPrdBossReq = new QueUserPrdBossReq();
	        queUserPrdBossReq.setCustid(custid);
	        queUserPrdBossReq.setPermark(permark);
	        queUserPrdBossReq.setPstatus("");
	        if(StringUtils.isNotBlank(keyno)){
	        	queUserPrdBossReq.setServid(keyno);
	        }else{
	        	queUserPrdBossReq.setServid("");
	        }

	        return queUserPrdBossReq;
	    }

	private Cycle getPackageCycle(SalespkgKnowsBO bo, LoginInfo loginInfo) throws Exception {
		StringBuffer sqlSb = new StringBuffer();
		sqlSb.append("SELECT t.data defaNumber");
		sqlSb.append("	FROM prv_sysparam t	");
		sqlSb.append("	WHERE 1 = 1	");
		sqlSb.append("AND t.mcode = ?	");
		sqlSb.append("AND t.gcode = ?	");
		String gcode = bo.getObjtype().equals("0") ? "PRD_CITY_ORDERNUM" : "PKG_CITY_ORDERNUM";
		List<Cycle> datas = getDAO().find(sqlSb.toString(), Cycle.class, loginInfo.getCity(), gcode);
		if (datas != null && datas.size() > 0) {
			return datas.get(0);
		} else {
			return null;
		}
	}

	private Cycle getUnitName(SalespkgKnowsBO bo) throws Exception {
		if (bo.getObjtype().equals("0")) {
			Cycle cycle = new Cycle();
			cycle.setUnitName("月");
			return cycle;
		}
		Cycle cycle = gettPkgUnit(bo.getObjid()); // PRD_SALESPKG.ORDERTYPE 0 按次订购  1按周期订购 
		return cycle;
	}
	
	public Cycle gettPkgUnit(Long salespkgId) throws Exception{
		List params = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("		SELECT T.SCOPEFLAG scopeflag,");
		sql.append("		(SELECT DISTINCT (MIN(P.PRESENTCYCLE)) FROM PRD_SALESPKG_SOFT P WHERE P.SALESPKGID  = T.SALESPKGID) cycleNum ,");
		sql.append("		CASE T.ORDERTYPE WHEN 0 THEN '次' ELSE '周期' END unitName");
		sql.append("		FROM PRD_SALESPKG T ");
		sql.append("		WHERE T.SALESPKGID = ?");
		params.add(salespkgId);
		List<Cycle> cycles = getDAO().find(sql.toString(), Cycle.class, params.toArray());
		if(cycles != null && !cycles.isEmpty()){
			return cycles.get(0);
		}
		return null;
	}
	

	@Transactional(readOnly = true)
	public List<SalespkgKnowsBO> queSalespkgKnow(QuerySalespkgKnowInterReq req) throws Exception {

		StringBuffer sql = new StringBuffer();
		List paramList = new ArrayList();
		// sql.append(" select
		// knowid,knowname,brief,objtype,objid,price,opcodes,tocust,wordexp,feeexp,city
		// from( ");
		sql.append(" SELECT * from( ");
		sql.append(" SELECT b.knowiD, ");
		sql.append("        b.knowname, ");
		sql.append("        b.brief, ");
		sql.append("        b.objtype, ");
		sql.append("        b.objid, ");
		sql.append("        b.price, ");
		sql.append("        b.opcodes, ");
		sql.append("        b.tocust, ");
		sql.append("        b.wordexp, ");
		sql.append("        b.feeexp, ");
		sql.append("        b.icon, ");
		sql.append("        b.city");
//		sql.append("		c.ispostpone");
		sql.append("   FROM prd_Catalog_Item a,PRD_SALESPKG_KNOW b ");
		sql.append("  WHERE a.knowid = b.knowid ");
		if (StringUtils.isNotBlank(req.getKnowname())) {
			sql.append("    AND b.knowname LIKE ? ");
			paramList.add(req.getKnowname() + "%");
		}

		if (req.getCatalogid() != null) {
			sql.append("    AND a.catalogid = ? ");
			paramList.add(req.getCatalogid());
		}

		if (StringUtils.isNotBlank(req.getKnowid())) {
			sql.append("    AND a.knowid = ? ");
			paramList.add(req.getKnowid());
		}

		if (req.getObjtype() != null) {
			sql.append("    AND b.objtype = ? ");
			paramList.add(req.getObjtype());
		}

		if (req.getSalespkgcode() != null) {
			sql.append(" AND ( b.objid IN (SELECT SALESPKGID FROM prd_salespkg WHERE salespkgcode=? ) ");
			sql.append(" OR b.objid IN (SELECT salesid FROM prd_sales WHERE salescode=? ) ) ");
			paramList.add(req.getSalespkgcode());
			paramList.add(req.getSalespkgcode());
		}
		if(StringUtils.isNotBlank(req.getOpcode())){
			sql.append("		AND (EXISTS (");
			sql.append("		SELECT W.SALESPKGID FROM prd_salespkg W");
			sql.append("		WHERE W.SALESPKGID = b.OBJID");
			sql.append("		AND FIND_IN_SET(?,W.BIZCODE)) ");
			sql.append("		OR b.objtype <> ? )");
			paramList.add(req.getOpcode());
			paramList.add(BizConstant.PrdSalespkgKnowObjtype.SALESPKG);
		}
		sql.append("	ORDER BY a.PRI");
		sql.append("    )v ");

		List<SalespkgKnowsBO> salespkgKnowsList = getDAO().find(sql.toString(), SalespkgKnowsBO.class,
				paramList.toArray());

		List<SalespkgKnowsBO> choiceList = new ArrayList<SalespkgKnowsBO>();

		if (salespkgKnowsList != null) {
			for (SalespkgKnowsBO bo : salespkgKnowsList) {
				if (bo.getObjtype().equals(BizConstant.PrdSalespkgKnowObjtype.PRD) && req.getBusType() != -1) {
					String bussineSql = "select pd.PNAME as pname,pd.PCLASS as pcls from PRD_PCODE pd where pd.PERMARK = ? and pd.PID = ?";
					List<Product> product = getDAO().find(bussineSql, Product.class, req.getBusType(), bo.getObjid());
					if (product == null || product.isEmpty()) {
						choiceList.add(bo);
					}
				}
			}
		}
		salespkgKnowsList.removeAll(choiceList);

		// 如果不需要详细信息则返回
		if (!req.isNeeddetail()) {
			return salespkgKnowsList;
		}

		if (null != salespkgKnowsList && salespkgKnowsList.size() > 0 && !salespkgKnowsList.isEmpty()) {
			for (SalespkgKnowsBO know : salespkgKnowsList) {
				if (StringUtils.isNotEmpty(know.getKnowname())) {
					know.setKnowname(know.getKnowname().replace("\"", ""));
				}
				if (StringUtils.isNotEmpty(know.getBrief())) {
					know.setBrief(know.getBrief().replace("\"", ""));
				}
				if (StringUtils.isNotEmpty(know.getTocust())) {
					know.setTocust(know.getTocust().replace("\"", ""));
					know.setTocustTitle("建议推荐目标客户特征");
				}
				if (StringUtils.isNotEmpty(know.getWordexp())) {
					know.setWordexp(know.getWordexp().replace("\"", ""));
				}
				if (StringUtils.isNotEmpty(know.getFeeexp())) {
					know.setFeeexp(know.getFeeexp().replace("\"", ""));
				}
				KnowObjDet knowObjDet = getKnowObjDetInfo(know.getObjtype(), know.getObjid());
				// knowObjDet
				know.setKnowObjDet(knowObjDet);
			}
		}
		return salespkgKnowsList;
	}
	
	
	@Transactional(readOnly = true)
	public List<SalespkgKnowsBO> queSalespkgKnow2(QuerySalespkgKnowInterReq req) throws Exception {

		StringBuffer sql = new StringBuffer();
		List paramList = new ArrayList();

		// sql.append(" select
		// knowid,knowname,brief,objtype,objid,price,opcodes,tocust,wordexp,feeexp,city
		// from( ");
		sql.append(" SELECT * from( ");
		sql.append(" SELECT b.knowiD, ");
		sql.append("        b.knowname, ");
		sql.append("        b.brief, ");
		sql.append("        b.objtype, ");
		sql.append("        b.objid, ");
		sql.append("        b.price, ");
		sql.append("        b.opcodes, ");
		sql.append("        b.tocust, ");
		sql.append("        b.wordexp, ");
		sql.append("        b.feeexp, ");
		sql.append("        b.city ");
		sql.append("   FROM PRD_SALESPKG_KNOW b ");
		sql.append("  WHERE 1 = 1 ");
		if (StringUtils.isNotBlank(req.getKnowname())) {
			sql.append("    AND b.knowname LIKE ? ");
			paramList.add(req.getKnowname() + "%");
		}

		if (StringUtils.isNotBlank(req.getKnowid())) {
			sql.append("    AND b.knowiD = ? ");
			paramList.add(req.getKnowid());
		}

		if (req.getObjtype() != null) {
			sql.append("    AND b.objtype = ? ");
			paramList.add(req.getObjtype());
		}
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		// 超级管理员可以查看所有管理考核指标
		if (!"GZCYKFA0001".equals(loginInfo.getLoginname())) {
			sql.append(" AND b.city = ? ");
			paramList.add(loginInfo.getCity());
		}

		sql.append("    )v ");

		List<SalespkgKnowsBO> salespkgKnowsList = getDAO().find(sql.toString(), SalespkgKnowsBO.class,
				paramList.toArray());

		// 如果不需要详细信息则返回
		if (!req.isNeeddetail()) {
			return salespkgKnowsList;
		}

		if (null != salespkgKnowsList && salespkgKnowsList.size() > 0 && !salespkgKnowsList.isEmpty()) {
			for (SalespkgKnowsBO know : salespkgKnowsList) {
				KnowObjDet knowObjDet = getKnowObjDetInfo(know.getObjtype(), know.getObjid());
				know.setKnowObjDet(knowObjDet);
			}
		}

		return salespkgKnowsList;
	}

	/**
	 * 获取知识库对象信息
	 * 
	 * @param objtype
	 * @param objid
	 * @return
	 * @throws Exception
	 */
	public KnowObjDet getKnowObjDetInfo(String objtype, Long objid) throws Exception {
		CheckUtils.checkEmpty(objtype, "获取知识库对象信息:对象类型不能为空");
		CheckUtils.checkNull(objid, "获取知识库对象信息:对象id不能为空");

		KnowObjDet retKnowObjDet = new KnowObjDet();
		if (BizConstant.PrdSalespkgKnowObjtype.PRD.equals(objtype)) {
			KnowPrdBO prd = getKnowPcodeInfo(objid);
			retKnowObjDet.setPrd(prd);
		} else if (BizConstant.PrdSalespkgKnowObjtype.SALESPKG.equals(objtype)) {
			KnowSalespkgBO pkg = getKnowSalespkgInfo(objid);
			retKnowObjDet.setPkg(pkg);
		} else if (BizConstant.PrdSalespkgKnowObjtype.GOODS.equals(objtype)
				|| BizConstant.PrdSalespkgKnowObjtype.GOODS_TYPE.equals(objtype)) {
			KnowGoodsBO goodsBO = getKnowSalesGoodsInfo(objid);
			retKnowObjDet.setGoods(goodsBO);
		} else {
			throw new BusinessException("获取知识库对象信息:对象类型[" + objtype + "]未定义");
		}

		return retKnowObjDet;
	}

	private KnowGoodsBO getKnowSalesGoodsInfo(Long objid) throws Exception {
		CheckUtils.checkNull(objid, "获取商品信息失败:商品id不能为空");

		// 取商品信息
		Sales sale = new Sales();
		sale.setId(objid);
		List<Sales> sales = getDAO().find(sale);
		if (null == sales || sales.size() <= 0 || sales.isEmpty()) {
			throw new BusinessException("获取商品信息失败：根据商品id[" + objid + "]查询不到商品");
		}
		sale = sales.get(0);
		KnowGoodsBO bo = new KnowGoodsBO();
		bo.setChargeMode(sale.getChargeMode());
		bo.setCycle(sale.getCycle());
		bo.setFirstFee(sale.getFirstFee());
		bo.setIsBasic(sale.getIsBasic());
		bo.setMemo(sale.getMemo());
		bo.setOrderSrc(sale.getOrderSrc());
		bo.setSalesCode(sale.getSalesCode());
		bo.setSalesName(sale.getSalesName());
		bo.setStatus(bo.getStatus());
		bo.setSums(sale.getSums());
		bo.setUnit(sale.getUnit());
		bo.setPid(Long.toString(sale.getPid()));
		return bo;
	}

	private KnowSalespkgBO getKnowSalespkgInfo(Long objid) throws Exception {
		CheckUtils.checkNull(objid, "获取营销方案信息：营销方案id不能为空");

		// 取营销方案
		Salespkg salespkgVO = new Salespkg();
		salespkgVO.setId(objid);
		List<Salespkg> salespkgList = getDAO().find(salespkgVO);
		if (null == salespkgList || salespkgList.size() <= 0 || salespkgList.isEmpty()) {
			throw new BusinessException("获取营销方案信息：根据营销方案id[" + objid + "]查询不到营销方案");
		}

		Salespkg salespkg = salespkgList.get(0);

		// 取营销方案明细
		SalespkgDetailBO salespkgDetail = getSalespkgDetail(salespkg.getId());

		// 取营销方案中必选优惠
		SalespkgDetailBO required = getPkgRequiredSales(salespkgDetail);

		// 取营销方案中可选优惠
		// 1)普通可选
		SalespkgDetailBO optional = getPkgOptionalSales(salespkgDetail);
		// 2)分组可选 --TODO，暂不实现

		// 组半装返回对象
		KnowSalespkgBO retKnowSalespkgBO = new KnowSalespkgBO();
		retKnowSalespkgBO.setSalespkgid(BeanUtil.object2String(salespkg.getId()));
		retKnowSalespkgBO.setPcode(salespkg.getSalespkgcode());
		retKnowSalespkgBO.setPname(salespkg.getSalespkgname());

		retKnowSalespkgBO.setRequired(required);
		retKnowSalespkgBO.setOptional(optional);

		return retKnowSalespkgBO;
	}

	private SalespkgDetailBO getSalespkgDetail(Long salespkgid) throws Exception {
		CheckUtils.checkNull(salespkgid, "获取营销方案明细信息:营销方案id不能为空");

		// 软件优惠
		List<SalespkgSoftsBO> softPrdsList = getSalespkgSoftPrd(salespkgid);

		// 选择性优惠
		List<SalespkgSelectsBO> selectPrdsList = getSalespkgSelectPrd(salespkgid);

		// 其它优惠，比如硬件优惠、账户优惠等--TODO，暂不实现

		SalespkgDetailBO retSalespkgDetailBO = new SalespkgDetailBO();
		retSalespkgDetailBO.setSoftPrds(softPrdsList);
		retSalespkgDetailBO.setSelectPrds(selectPrdsList);

		return retSalespkgDetailBO;
	}

	private List<SalespkgSelectsBO> getSalespkgSelectPrd(Long salespkgid) throws Exception {
		CheckUtils.checkNull(salespkgid, "获取营销方案选择性优惠:营销方案id不能为空");

		SalespkgSelect salespkgSelectVO = new SalespkgSelect();
		salespkgSelectVO.setSalespkgid(salespkgid);

		List<SalespkgSelect> salespkgSelectList = getDAO().find(salespkgSelectVO);
		List<SalespkgSelectsBO> retSelectPrdsList = new ArrayList();
		if (null != salespkgSelectList && salespkgSelectList.size() > 0 && !salespkgSelectList.isEmpty()) {
			for (SalespkgSelect select : salespkgSelectList) {

				SalespkgSelectsBO selectsBO = copySalespkgSelects2SalespkgSelectsBO(select);

				retSelectPrdsList.add(selectsBO);

			}
		}

		return retSelectPrdsList;
	}

	private SalespkgSelectsBO copySalespkgSelects2SalespkgSelectsBO(SalespkgSelect select) throws Exception {
		CheckUtils.checkNull(select, "获取营销方案选择性优惠明细信息:请求对象不能为空");
		CheckUtils.checkNull(select.getId(), "获取营销方案选择性优惠明细信息:选择方案id不能为空");

		// 获取选择性优惠的产品明细
		SalespkgSelectDet selectDetVO = new SalespkgSelectDet();
		selectDetVO.setSelectid(select.getId());

		List<SalespkgSelectDet> selectDetList = getDAO().find(selectDetVO);
		if (null == selectDetList) {
			throw new BusinessException("获取选择性优惠产品信息：根据选择方案id[" + select.getId() + "]查询不到产品");
		}
		List<SalespkgSelectDetsBO> selectDetPrdList = new ArrayList();
		for (SalespkgSelectDet selectDet : selectDetList) {

			KnowPrdBO knowPcode = getKnowPcodeInfo(selectDet.getPid());
			if (null == knowPcode) {
				throw new BusinessException("获取选择性优惠产品信息：根据产品id[" + selectDet.getPid() + "]查询不到产品");
			}
			SalespkgSelectDetsBO selectDetsBO = new SalespkgSelectDetsBO();
			BeanUtils.copyProperties(selectDetsBO, knowPcode);

			selectDetsBO.setId(BeanUtil.object2String(selectDet.getId()));
			selectDetPrdList.add(selectDetsBO);
		}

		// 组装返回对象
		SalespkgSelectsBO retSelectsBO = new SalespkgSelectsBO();

		if (null != select.getFixedate()) {
			retSelectsBO.setFixedate(DateTimeUtil.formatDate(select.getFixedate(), DateTimeUtil.PATTERN_DATETIME));
		}
		retSelectsBO.setIspostpone(select.getIspostpone());
		retSelectsBO.setOptionflag(select.getOptionflag());
		retSelectsBO.setPrds(selectDetPrdList);
		retSelectsBO.setPresentcycle(BeanUtil.object2String(select.getPresentcycle()));
		retSelectsBO.setSelectdesc(select.getSelectdesc());
		retSelectsBO.setSelectid(BeanUtil.object2String(select.getId()));
		retSelectsBO.setSelectnum(select.getSelectnum());
		retSelectsBO.setTimetype(select.getTimetype());
		retSelectsBO.setUnit(select.getUnit());

		return retSelectsBO;
	}

	/**
	 * 获取营销方案软件优惠
	 * 
	 * @param salespkgid
	 * @return
	 * @throws Exception
	 */
	private List<SalespkgSoftsBO> getSalespkgSoftPrd(Long salespkgid) throws Exception {
		CheckUtils.checkNull(salespkgid, "获取营销方案软件优惠:营销方案id不能为空");

		SalespkgSoft salespkgSoftVO = new SalespkgSoft();
		salespkgSoftVO.setSalespkgid(salespkgid);

		List<SalespkgSoft> salespkgSoftList = getDAO().find(salespkgSoftVO);
		List<SalespkgSoftsBO> softPrdsList = new ArrayList();
		if (null != salespkgSoftList && salespkgSoftList.size() > 0 && !salespkgSoftList.isEmpty()) {
			for (SalespkgSoft soft : salespkgSoftList) {

				KnowPrdBO knowPcode = getKnowPcodeInfo(soft.getPid());
				if (null == knowPcode) {
					throw new BusinessException("获取软件优惠产品信息：根据产品id[" + soft.getPid() + "]查询不到产品");
				}
				SalespkgSoftsBO softsBO = new SalespkgSoftsBO();
				BeanUtils.copyProperties(softsBO, knowPcode);

				softsBO.setId(BeanUtil.object2String(soft.getId()));

				if (null != soft.getFixedate()) {
					softsBO.setFixedate(DateTimeUtil.formatDate(soft.getFixedate(), DateTimeUtil.PATTERN_DATETIME));
				}
				softsBO.setIsmasterprd(soft.getIsmasterprd());
				softsBO.setIspostpone(soft.getIspostpone());
				softsBO.setOptionflag(soft.getOptionflag());
				softsBO.setPresentcycle(BeanUtil.object2String(soft.getPresentcycle()));
				softsBO.setTimetype(soft.getTimetype());
				softsBO.setUnit(soft.getUnit());

				softPrdsList.add(softsBO);

			}
		}

		return softPrdsList;
	}

	private SalespkgDetailBO getSalespkgDetailGroupByOptionflag(SalespkgDetailBO salespkgDetail, String optionflag)
			throws Exception {
		CheckUtils.checkNull(salespkgDetail, "根据选择标识分组营销方案优惠信息:营销方案明细信息不能为空");
		CheckUtils.checkEmpty(optionflag, "根据选择标识分组营销方案优惠信息:分组择标识不能为空");

		SalespkgDetailBO retSalespkgDetailBO = new SalespkgDetailBO();

		List<SalespkgSoftsBO> softPrdList = null;
		if (null != salespkgDetail.getSoftPrds() && salespkgDetail.getSoftPrds().size() > 0
				&& !salespkgDetail.getSoftPrds().isEmpty()) {

			softPrdList = new ArrayList();
			for (SalespkgSoftsBO softBO : salespkgDetail.getSoftPrds()) {
				if (optionflag.equals(softBO.getOptionflag())) {

					softPrdList.add(softBO);
				}

			}
		}

		List<SalespkgSelectsBO> selectPrdList = null;
		if (null != salespkgDetail.getSelectPrds() && salespkgDetail.getSelectPrds().size() > 0
				&& !salespkgDetail.getSelectPrds().isEmpty()) {

			selectPrdList = new ArrayList();
			for (SalespkgSelectsBO selectBO : salespkgDetail.getSelectPrds()) {
				if (optionflag.equals(selectBO.getOptionflag())) {

					selectPrdList.add(selectBO);
				}
			}
		}

		retSalespkgDetailBO.setSoftPrds(softPrdList);
		retSalespkgDetailBO.setSelectPrds(selectPrdList);

		return retSalespkgDetailBO;

	}

	private SalespkgDetailBO getPkgRequiredSales(SalespkgDetailBO salespkgDetail) throws Exception {
		CheckUtils.checkNull(salespkgDetail, "获取营销方案必选优惠:营销方案明细信息不能为空");

		SalespkgDetailBO retSalespkgDetailBO = getSalespkgDetailGroupByOptionflag(salespkgDetail,
				BizConstant.SalespkgOptionflag.REQUIRED);

		return retSalespkgDetailBO;
	}

	private SalespkgDetailBO getPkgOptionalSales(SalespkgDetailBO salespkgDetail) throws Exception {
		CheckUtils.checkNull(salespkgDetail, "获取营销方案可选优惠:营销方案明细信息不能为空");

		// 应该要先过滤掉已经分组了的可选优惠,待有这种需求的时候再处理--TODO，暂不实现

		SalespkgDetailBO optionalNoSelected = getSalespkgDetailGroupByOptionflag(salespkgDetail,
				BizConstant.SalespkgOptionflag.OPTIONAL_SELECTED);

		SalespkgDetailBO optionalSelected = getSalespkgDetailGroupByOptionflag(salespkgDetail,
				BizConstant.SalespkgOptionflag.OPTIONAL_NO_SELECTED);

		List<SalespkgSoftsBO> softPrdList = new ArrayList();
		List<SalespkgSelectsBO> selectPrdList = new ArrayList();

		if (null != optionalNoSelected) {
			if (optionalNoSelected.getSoftPrds() != null) {
				softPrdList.addAll(optionalNoSelected.getSoftPrds());
			}
			if (optionalNoSelected.getSelectPrds() != null) {
				selectPrdList.addAll(optionalNoSelected.getSelectPrds());
			}
		}

		if (null != optionalSelected) {
			if (optionalSelected.getSoftPrds() != null) {
				softPrdList.addAll(optionalSelected.getSoftPrds());
			}
			if (optionalSelected.getSelectPrds() != null) {
				selectPrdList.addAll(optionalSelected.getSelectPrds());
			}
		}

		SalespkgDetailBO retSalespkgDetailBO = new SalespkgDetailBO();
		retSalespkgDetailBO.setSoftPrds(softPrdList);
		retSalespkgDetailBO.setSelectPrds(selectPrdList);

		return retSalespkgDetailBO;
	}

	private KnowPrdBO getKnowPcodeInfo(Long objid) throws Exception {
		CheckUtils.checkNull(objid, "获取产品信息：产品id不能为空");

		Pcode pcodeVO = new Pcode();
		pcodeVO.setId(objid);
		List<Pcode> pcodeList = getDAO().find(pcodeVO);
		if (null == pcodeList || pcodeList.size() <= 0 || pcodeList.isEmpty()) {
			throw new BusinessException("获取产品信息：根据产品id[" + objid + "]查询不到产品");
		}

		Pcode pcode = pcodeList.get(0);

		KnowPrdBO retKnowPocdeBO = new KnowPrdBO();
		// BeanUtils.copyProperties(retKnowPocdeBO, pcode);
		retKnowPocdeBO.setExternalid(pcode.getExternalid());
		retKnowPocdeBO.setPclass(pcode.getPclass());
		retKnowPocdeBO.setPcode(pcode.getPcode());
		retKnowPocdeBO.setPermark(pcode.getPermark());
		retKnowPocdeBO.setPid(BeanUtil.object2String(pcode.getId()));
		retKnowPocdeBO.setPname(pcode.getPname());
		retKnowPocdeBO.setProdtype(pcode.getProdtype());
		retKnowPocdeBO.setPsubclass(pcode.getPsubclass());

		return retKnowPocdeBO;
	}

	public SalespkgKnowsBO queSalespkgKnowDetByKnowid(Long knowid) throws Exception {

		CheckUtils.checkNull(knowid, "获取知识库营销对象明细信息:营销标识不能为空");

		SalespkgKnowsBO retSalespkgKnowsBO = null;
		QuerySalespkgKnowInterReq queSalespkgKnowParam = new QuerySalespkgKnowInterReq();
		queSalespkgKnowParam.setKnowid(knowid.toString());
		List<SalespkgKnowsBO> salespkgKnowsList = this.queSalespkgKnow(queSalespkgKnowParam);
		if (null != salespkgKnowsList && salespkgKnowsList.size() > 0 && !salespkgKnowsList.isEmpty()) {
			retSalespkgKnowsBO = salespkgKnowsList.get(0);
		}

		return retSalespkgKnowsBO;
	}
	
	public SalespkgKnowsBO queSalespkgKnowDetByKnowidZs(Long knowid) throws Exception {

		CheckUtils.checkNull(knowid, "获取知识库营销对象明细信息:营销标识不能为空");

		SalespkgKnowsBO retSalespkgKnowsBO = null;
		QuerySalespkgKnowInterReqZs queSalespkgKnowParam = new QuerySalespkgKnowInterReqZs();
		queSalespkgKnowParam.setKnowid(knowid.toString());
		List<SalespkgKnowsBO> salespkgKnowsList = this.queSalespkgKnowZs(queSalespkgKnowParam);
		if (null != salespkgKnowsList && salespkgKnowsList.size() > 0 && !salespkgKnowsList.isEmpty()) {
			retSalespkgKnowsBO = salespkgKnowsList.get(0);
		}

		return retSalespkgKnowsBO;
	}

	/*
	 * public ReturnInfo doFeeins(BizFeeIntersReq req,BizFeeinInterResp resp)
	 * throws Exception{ ReturnInfo returnInfo = new ReturnInfo();
	 * returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
	 * returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
	 * 
	 * LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
	 * CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE); for(int i = 0 ; i <
	 * req.getFees().size() ;i++){ CheckUtils.checkNull(req, "请求信息不能为空");
	 * CheckUtils.checkEmpty(req.getBizorderid(), "缴纳欠费接口：业务订单号不能为空");
	 * CheckUtils.checkEmpty(req.getKeyno().get(i), "缴纳欠费接口：智能卡号[keyno]不能为空");
	 * CheckUtils.checkEmpty(req.getFees().get(i), "缴纳欠费接口：缴费金额[fees]不能为空");
	 * CheckUtils.checkEmpty(req.getHouseid().get(i), "获取客户订单:地址id不能为空");
	 * CheckUtils.checkEmpty(req.getPatchid().get(i), "获取客户订单:片区id不能为空");
	 * 
	 * pubService.checkBizAcceptRight(Long.valueOf(req.getHouseid().get(i)),
	 * Long.valueOf(req.getPatchid().get(i)), loginInfo);
	 * 
	 * // 写客户订单表 regCustOrder4doFeein(req, loginInfo,i); // 登记缴纳欠费信息表
	 * regApplyArrear4doFeein(req, loginInfo,i);
	 * 
	 * } // 将请求做一下转换，并赋默认值 BizFeeinBossReq bossReq =
	 * getBizFeeinBossReq2Boss(req);
	 * 
	 * // 调用BOSS接口 String bossRespOutput =
	 * this.getBossHttpInfOutput(req.getBizorderid(),
	 * BizConstant.BossInterfaceService.BIZ_FEEIN, bossReq, loginInfo);
	 * resp.setCustorderid(req.getBizorderid()); // 保存boss订单信息
	 * this.savePortalOrder(req.getBizorderid(), bossRespOutput); // 拼装返回信息
	 * copyBossResp2InterResp4doFeein(resp, bossRespOutput); return returnInfo;
	 * }
	 */

	private BizFeeinBossReq getBizFeeinBossReq2Boss(BizFeeIntersReq req) {

		BizFeeinBossReq bossReq = new BizFeeinBossReq();
		// BeanUtils.copyPropertiesNotSuperClass(bossReq, req);// 不复制父类的字段，不然报错
		// 如与boss有不同的字段，在这里另外做特殊处理
		double fees = 0;
		for (int i = 0; i < req.getFees().size(); i++) {
			fees += Double.parseDouble(req.getFees().get(i));
			bossReq.setKeyno(req.getKeyno().get(i));
			if (req.getPermark().size() > i) {
				bossReq.setPermark(req.getPermark().get(i));
			} else {
				bossReq.setPermark("");
			}
			if (req.getIsorder().size() > i) {
				bossReq.setIsorder(req.getIsorder().get(i));
			} else {
				bossReq.setIsorder("N");
			}
			if (req.getGdno().size() > i) {
				bossReq.setGdno(req.getGdno().get(i));
			} else {
				bossReq.setGdno("");
			}
		}
		bossReq.setFees(fees + "");
		return bossReq;
	}

	/**
	 * 缴欠费接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo doFeein(BizFeeinInterReq req, BizFeeinInterResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getBizorderid(), "缴纳欠费接口：业务订单号不能为空");
		CheckUtils.checkEmpty(req.getKeyno(), "缴纳欠费接口：智能卡号[keyno]不能为空");
		CheckUtils.checkEmpty(req.getFees(), "缴纳欠费接口：缴费金额[fees]不能为空");
		CheckUtils.checkEmpty(req.getHouseid(), "获取客户订单:地址id不能为空");
		CheckUtils.checkEmpty(req.getPatchid(), "获取客户订单:片区id不能为空");

		pubService.checkBizAcceptRight(Long.valueOf(req.getHouseid()), Long.valueOf(req.getPatchid()), loginInfo);

		// 写客户订单表
		CustOrder custOrder = regCustOrder4doFeein(req, loginInfo);
		// 登记缴纳欠费信息表
		regApplyArrear4doFeein(req, loginInfo,Double.parseDouble(req.getFees()));

		// 将请求做一下转换，并赋默认值
		BizFeeinBossReq bossReq = getBizFeeinBossReq2Boss(req);
		if(null!=custOrder&&null!=custOrder.getPortalOrder()){
			ParamsManager.isCorrectData(custOrder.getPortalOrder().getPayway(),custOrder.getPortalOrder().getPaycode());
		}
		String serverCode = "";
		if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
			serverCode = BizConstant.ServerCityBossInterface.BIZ_FEEIN;
		}else{
			serverCode = BizConstant.BossInterfaceService.BIZ_FEEIN;
		}
		// 调用BOSS接口
		String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(),
				serverCode, bossReq, loginInfo);
		BizFeeinBossResp bossResp = (BizFeeinBossResp) BeanUtil.jsonToObject(bossRespOutput, BizFeeinBossResp.class);
		custOrder.setBossserialno(bossResp.getSerialno());
		// 保存boss订单信息
		this.savePortalOrder(req.getBizorderid(), bossRespOutput);
		getDAO().save(custOrder);
		// 拼装返回信息
		resp.setBossorderid(bossResp.getOrderid());
		resp.setFeename(bossResp.getFeename());
		resp.setOrdertype(bossResp.getOrdertype());
		resp.setSums(bossResp.getSums());

		
		resp.setCustorderid(req.getBizorderid());

		return returnInfo;
	}

	public ReturnInfo doFeeinsByAddress(BizFeeinInterReq req, BizFeeinInterResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getBizorderid(), "缴纳欠费接口：业务订单号不能为空");
		CheckUtils.checkEmpty(req.getKeyno(), "缴纳欠费接口：智能卡号[keyno]不能为空");
		CheckUtils.checkEmpty(req.getFees(), "缴纳欠费接口：缴费金额[fees]不能为空");
		CheckUtils.checkEmpty(req.getHouseid(), "获取客户订单:地址id不能为空");
		CheckUtils.checkEmpty(req.getPatchid(), "获取客户订单:片区id不能为空");
		CheckUtils.checkEmpty(req.getChouseid(), "缴纳欠费接口：缴费地址不能为空");
		//ywp 设置对应的业务区不判断网格权限
		/*Rule rule = ruleService.getRule(loginInfo.getCity(), BizConstant.BizRuleParams.NOT_JUDGE_GRID);
		boolean flag = true;
		if(null!=rule){
			String areaids = rule.getAreaIds();
			if(null!=areaids){
				if("*".equals(areaids)||areaids.indexOf(req.getAreaid())>-1){
					flag = false;
				}
			}
		}
		if(flag){
			pubService.checkBizAcceptRight(Long.valueOf(req.getHouseid()), Long.valueOf(req.getPatchid()), loginInfo);
		}*/
		pubService.checkBizAcceptRight(Long.valueOf(req.getHouseid()), Long.valueOf(req.getPatchid()), loginInfo);
		// 写客户订单表
		regCustOrder4doFeein(req, loginInfo);

		BizFeeinAndOrderBossReq boss2Req = getBizFeeinAndOrderBossReq2Boss(req, loginInfo);
		// 调用BOSS接口
		String serverCode = "";
		if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
			serverCode = BizConstant.ServerCityBossInterface.BIZ_FEEIN_AND_ORDER;
		}else{
			serverCode = BizConstant.BossInterfaceService.BIZ_FEEIN_AND_ORDER;
		}
		
		String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(),
				serverCode, boss2Req, loginInfo);

			
		
		// 保存boss订单信息
		this.savePortalOrder(req.getBizorderid(), bossRespOutput);

		// 拼装返回信息
		copyBossResp2InterResp4doFeein(resp, bossRespOutput);
		// 登记缴纳欠费信息表
		regApplyArrear4doFeein(req, loginInfo,Double.parseDouble(resp.getSums()));	
		resp.setCustorderid(req.getBizorderid());

		return returnInfo;
	}

	private BizFeeinAndOrderBossReq getBizFeeinAndOrderBossReq2Boss(BizFeeinInterReq req,LoginInfo loginInfo) throws Exception {
		BizFeeinAndOrderBossReq bossReq = new BizFeeinAndOrderBossReq();
		bossReq.setChouseid(req.getChouseid());
		bossReq.setCustid(req.getCustid());
		bossReq.setGdno("");
		bossReq.setGdnoid("");
		bossReq.setIscrtorder(req.getIsorder());
		bossReq.setKeyno(req.getKeyno());
		if(req.getOrderparams() != null && !req.getOrderparams().isEmpty()){
			bossReq.setOrderparams(req.getOrderparams());	
		}
		if(StringUtils.isNotBlank(req.getQuicknum())){
			bossReq.setQuicknum(req.getQuicknum());
		}
		bossReq.setDeveloper(new DeveloperBossBO("1", loginInfo.getDeptid(), loginInfo.getName()));
		return bossReq;
	}

	private void regApplyArrear4doFeein(BizFeeinInterReq req, LoginInfo loginInfo,Double fees) throws Exception {
		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getBizorderid(), "缴纳欠费接口：业务订单号不能为空");
		CheckUtils.checkEmpty(req.getCustid(), "缴纳欠费接口客户id不能为空");
		// CheckUtils.checkEmpty(req.getKeyno(), "登记缴纳欠信息：智能卡号[keyno]不能为空");
		CheckUtils.checkEmpty(req.getFees(), "登记缴纳欠信息：缴费金额[fees]不能为空");

		ApplyArrear applyArrear = new ApplyArrear();
		applyArrear.setCity(loginInfo.getCity());
		applyArrear.setCustid(Long.valueOf(req.getCustid()));
		// applyArrear.setEtime(etime);
		applyArrear.setFees(fees);
		applyArrear.setKeyno(req.getKeyno());
		applyArrear.setOrderid(Long.valueOf(req.getBizorderid()));
		applyArrear.setPermark(req.getPermark());
		// applyArrear.setPid(pid);
		// applyArrear.setRecid(recid);
		// applyArrear.setStime(stime);

		getDAO().save(applyArrear);

	}

	private BizFeeinBossReq getBizFeeinBossReq2Boss(BizFeeinInterReq req) {

		BizFeeinBossReq bossReq = new BizFeeinBossReq();
		// BeanUtils.copyPropertiesNotSuperClass(bossReq, req);// 不复制父类的字段，不然报错
		// 如与boss有不同的字段，在这里另外做特殊处理
		bossReq.setFees(req.getFees());
		bossReq.setKeyno(req.getKeyno());
		bossReq.setPermark(req.getPermark());
		bossReq.setIsorder(req.getIsorder());
		bossReq.setGdno(req.getGdno());

		return bossReq;
	}

	private void regCustOrder4doFeein(BizFeeIntersReq req, LoginInfo loginInfo, int index) throws Exception {
		CheckUtils.checkNull(req, "获取客户订单:请求参数不能为空");
		CheckUtils.checkNull(loginInfo, "获取客户订单:登录信息不能为空");
		CheckUtils.checkEmpty(req.getBizorderid(), "获取客户订单:订单id不能为空");
		CheckUtils.checkEmpty(req.getHouseid().get(index), "获取客户订单:地址id不能为空");
		CheckUtils.checkEmpty(req.getPatchid().get(index), "获取客户订单:片区id不能为空");
		CheckUtils.checkEmpty(req.getCustid(), "获取客户订单:客户id不能为空");
		CheckUtils.checkEmpty(req.getName(), "获取客户订单:客户姓名不能为空");
		CheckUtils.checkEmpty(req.getWhladdr(), "获取客户订单:地址不能为空");

		String bossserialno = null;
		BizGridInfo bizGrid = pubService.getGrid4Biz(Long.valueOf(req.getHouseid().get(index)),
				Long.valueOf(req.getPatchid().get(index)), loginInfo);

		CustOrder bizCustOrder = new CustOrder();
		bizCustOrder.setAddr(req.getWhladdr());
		bizCustOrder.setAreaid(Long.valueOf(req.getAreaid()));
		bizCustOrder.setBossserialno(bossserialno);
		bizCustOrder.setCanceltime(null);
		bizCustOrder.setCity(loginInfo.getCity());
		bizCustOrder.setCustid(Long.valueOf(req.getCustid()));
		bizCustOrder.setDescrip(req.getDescrip());
		bizCustOrder.setGridid(bizGrid.getId());
		bizCustOrder.setLockoper(null);
		bizCustOrder.setName(req.getName());
		bizCustOrder.setOpcode(BizConstant.BossInterfaceService.BIZ_FEEIN);
		bizCustOrder.setOperator(loginInfo.getOperid());
		bizCustOrder.setOprdep(loginInfo.getDeptid());
		bizCustOrder.setOptime(new Date());
		bizCustOrder.setOrdercode(pubService.getOrderCode().toString());
		bizCustOrder.setId(Long.valueOf(req.getBizorderid()));
		bizCustOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.SYNC);
		bizCustOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
		bizCustOrder.setPortalOrder(null);

		getDAO().save(bizCustOrder);
	}

	private CustOrder regCustOrder4doFeein(BizFeeinInterReq req, LoginInfo loginInfo) throws Exception {
		CheckUtils.checkNull(req, "获取客户订单:请求参数不能为空");
		CheckUtils.checkNull(loginInfo, "获取客户订单:登录信息不能为空");
		CheckUtils.checkEmpty(req.getBizorderid(), "获取客户订单:订单id不能为空");
		CheckUtils.checkEmpty(req.getHouseid(), "获取客户订单:地址id不能为空");
		CheckUtils.checkEmpty(req.getPatchid(), "获取客户订单:片区id不能为空");
		CheckUtils.checkEmpty(req.getCustid(), "获取客户订单:客户id不能为空");
		CheckUtils.checkEmpty(req.getName(), "获取客户订单:客户姓名不能为空");
		CheckUtils.checkEmpty(req.getWhladdr(), "获取客户订单:地址不能为空");

		String bossserialno = null;
		BizGridInfo bizGrid = pubService.getGrid4Biz(Long.valueOf(req.getHouseid()), Long.valueOf(req.getPatchid()),
				loginInfo);

		CustOrder bizCustOrder = new CustOrder();
		bizCustOrder.setAddr(req.getWhladdr());
		bizCustOrder.setAreaid(Long.valueOf(req.getAreaid()));
		bizCustOrder.setBossserialno(bossserialno);
		bizCustOrder.setCanceltime(null);
		bizCustOrder.setCity(loginInfo.getCity());
		bizCustOrder.setCustid(Long.valueOf(req.getCustid()));
		bizCustOrder.setDescrip(req.getDescrip());
		bizCustOrder.setGridid(bizGrid.getId());
		bizCustOrder.setLockoper(null);
		bizCustOrder.setName(req.getName());
		bizCustOrder.setOpcode(BizConstant.BossInterfaceService.BIZ_FEEIN);
		bizCustOrder.setOperator(loginInfo.getOperid());
		bizCustOrder.setOprdep(loginInfo.getDeptid());
		bizCustOrder.setOptime(new Date());
		bizCustOrder.setOrdercode(pubService.getOrderCode().toString());
		bizCustOrder.setId(Long.valueOf(req.getBizorderid()));
		bizCustOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.SYNC);
		bizCustOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
		bizCustOrder.setPortalOrder(null);
		bizCustOrder.setVerifyphone(req.getVerifyphone());

		getDAO().save(bizCustOrder);
		
		return bizCustOrder;
	}

	private void copyBossResp2InterResp4doFeein(BizFeeinInterResp resp, String jsonStr) throws Exception {

		BizFeeinBossResp bossResp = (BizFeeinBossResp) BeanUtil.jsonToObject(jsonStr, BizFeeinBossResp.class);

		// 如果字段是一样的，这里可以偷懒一下，
		// 后面如果接口对外字段和boss返回字段不一致，则自行写使用set方法设置值
		// BeanUtils.copyProperties(resp, bossResp);
		resp.setBossorderid(bossResp.getOrderid());
		resp.setFeename(bossResp.getFeename());
		resp.setOrdertype(bossResp.getOrdertype());
		resp.setSums(bossResp.getSums());

	}

	@Transactional
	public ReturnInfo doOrderCommit(OrderCommitInterReq req, OrderCommitInterResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		try {
			LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
			CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

			CheckUtils.checkNull(req, "请求信息不能为空");
			CheckUtils.checkEmpty(req.getCustorderid(), "订购确认接口:订单号[custorderid]不能为空");
			CheckUtils.checkEmpty(req.getPayway(), "订购确认接口：支付方式[payway]不能为空");
			CheckUtils.checkEmpty(req.getPaycode(), "订购确认接口：支付编码[paycode]不能为空");

			// pubService.checkBizAcceptRight(loginInfo);

			// 网格记录的boss订单信息
			CustOrder custOrder = (CustOrder) getDAO().find(CustOrder.class, Long.valueOf(req.getCustorderid()));
			if (null == custOrder) {
				throw new BusinessException("订购确认接口:根据订单id[" + req.getCustorderid() + "]查询不到订单信息");
			}
			BizPortalOrder order = getBizPortalOrder4doOrderCommitInter(req);
			if (null == order) {
				throw new BusinessException("订购确认接口:根据订单id[" + req.getCustorderid() + "]查询不到订单信息");
			}
			if(order.getStatus().equals(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_UNIFY) ||
					order.getStatus().equals(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_ELECTION)){
				req.setPaycode(order.getPaycode());
				if(order.getStatus().equals(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_UNIFY)){
					if(!order.getPayway().equals("33")){
						req.setPayway("33");
					}else{
						req.setPayway(order.getPayway());
					}
				}
				if (StringUtils.isNotBlank(order.getPayreqid())) {
					req.setPayreqid(order.getPayreqid());
				}
			}
			// 取boss订单信息；
			QueBossorderBO bossorder = getBossOrder4doOrderCommitInter(order.getResporderid().toString());
			if (null == bossorder) {
				throw new BusinessException("订购确认接口:根据boss订单id[" + order.getResporderid() + "]查询不到boss订单信息");
			}

			// 网格记录的boss订单为未支付，且boss订单为未支付，支付
			// 网格记录的boss订单为未支付，且boss订单不为未支付，更新
			// 网格记录的boss订单不为未支付，报错
			if (!BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_NOPAY.equals(order.getStatus())
					&& !BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_UNIFY.equals(order.getStatus())
					&& !BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_ELECTION.equals(order.getStatus())) {
				throw new BusinessException("订购确认接口:订单状态为【"
						+ paramService.getMname("BIZ_PORTAL_ORDER_STATUS", order.getStatus()) + "】，不能进行支付操作");

			} else {
				// 网格记录的boss订单为未支付，且boss订单为支付成功，报错--不能直接更新为支付成功，因为可能导致支付方式和boss记录的不一致

				if (!BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_NOPAY.equals(bossorder.getStatus())
						&& !BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_PAYED.equals(bossorder.getStatus())) {

					// 网格记录的boss订单为未支付，且boss订单为不未支付成功 且不为已支付，更新
					order.setStatus(bossorder.getStatus());
					getDAO().save(order);

					String retMsg = "支付失败,订单已失效";
					returnInfo.setCode(IErrorDefConstant.ERROR_InvocationTargetException);

					returnInfo.setMessage(retMsg);
					resp.setCustorderid(req.getCustorderid());
					return returnInfo;

				} else if (BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_PAYED.equals(bossorder.getStatus())) {
					throw new BusinessException("订单状态与boss系统不一致，请联系管理进行数据核对");
				} else {
					// 继续执行进入调用订单确认接口
				}
			}
			String multipay = "N";
	        if(StringUtils.isNotBlank(req.getMultipaywayflag()) && req.getMultipaywayflag().equalsIgnoreCase("Y")) {
	        	if(StringUtils.isNotBlank(req.getCashe())) {
	        		double payFees = Double.parseDouble(order.getFees()) - Double.parseDouble(req.getCashe());
	        		if(payFees > 0.0) {
	        			order.setMultipay("Y");
	        			multipay = "Y";
	        		}
	        		order.setPayFees(Double.toString(payFees));
	        	}
	        }

			// 将网格接口请转成boss接口请求
			OrderCommitBossReq bossReq = getOrderCommitBossReq2Boss(req,multipay);
			bossReq.setOrderid(String.valueOf(order.getResporderid()));
			
			//此处对大连的统一支付平台功能进行筛选
			if(StringUtils.isNotBlank(SysConfig.getServiceCity()) && 
					StringUtils.isNotBlank(order.getPayway())){
				bossReq.setPayway(order.getPayway());
			}
			String serverCode = "";
			if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
				serverCode = BizConstant.ServerCityBossInterface.BIZ_ORDERCOMMIT;
			}else{
				serverCode = BizConstant.BossInterfaceService.BIZ_ORDERCOMMIT;
			}
			ParamsManager.isCorrectData(req.getPayway(), req.getPaycode());
			// 调用BOSS接口
			String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(),
					serverCode, bossReq, loginInfo);

			// 拼装返回信息
			copyBossResp2InterResp4doOrderCommit(resp, bossRespOutput);
			resp.setCustorderid(req.getCustorderid());
			try {
				sendBusinessSMS(req, loginInfo, bossorder);// 调用发送短信的接口
			} catch (Exception e) {
				log.error("产品订购BOSS成功,调用短信发送失败");
			}
			// 更新订单状态
			// updateOrderStatus(order,BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_PAYED,req);
			custOrder.setBossserialno(resp.getSerialno());
			order.setStatus(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_PAYED);
			order.setPayway(req.getPayway());
			order.setPaycode(req.getPaycode());
			if (StringUtils.isNotEmpty(req.getPayreqid())) {
				order.setPayreqid(req.getPayreqid());
			}
			//getWgpaywayChangegetPayway
	        if (StringUtils.isNotBlank(req.getPayway())) {
	        	order.setWgpayway(req.getPayway());
			}
			order.setPaytime(new Date());

			getDAO().saveOrUpdate(order);
			getDAO().saveOrUpdate(custOrder);
			ReturnVisitTaskService.addTask(custOrder.getId());

		} catch (Exception e) {
			boolean weixinRefund = false;
			if (req.getPaycode().equals("41000")) {// weixin refund
				try {
					weixinPayService.refund(req.getCustorderid());
				} catch (Exception e2) {
					weixinRefund = true;
				}
			}
			String msg = (weixinRefund) ? e.getMessage() + ",微信支付回退失败，请手动回退" : e.getMessage();
			throw new BusinessException(msg);
		}

		//组网业务更新订单池状态
		if(req.getCustorderid()!= null){
			updateBizNwtworkPool(req);
		}

		return returnInfo;
	}

	private void updateBizNwtworkPool(final OrderCommitInterReq req) {
		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					//先查是不是和订单池关联的订单
					StringBuffer sqlBuffer = new StringBuffer();
					sqlBuffer.append("SELECT a.biznetorder_id as id,a.custid,a.custname,a.preserialno,a.link_Addr as linkAddr,a.createoper ");
					sqlBuffer.append("	,a.createtime,a.pretime,a.def_Name defName ,a.opcode,a.defdesc,a.remark,a.applydept,a.payway,a.result ");
					sqlBuffer.append("	,a.datadesc,a.whgridcode,a.applyoperid,a.operid,a.optime,a.areaid,a.status as status  ");
					sqlBuffer.append(" from biz_networkbus_custorder_pool a WHERE  preprocessorder = ? ");
					List params = new ArrayList();
					params.add(req.getCustorderid());
					try {
						List<CustBizNetWorkOrderPool> custBizNetWorkOrderPoolList = getDAO().find(sqlBuffer.toString(), CustBizNetWorkOrderPool.class,
								params.toArray());
						if(custBizNetWorkOrderPoolList==null || custBizNetWorkOrderPoolList.size() < 1 ){
							return;
						}
					}catch (Exception e){
						log.error("查询异常");
					}

					//更新组网订单池状态为 2
					//更新订单池订单对应状态
					StringBuffer sql = new StringBuffer();
					sql.append(" UPDATE biz_networkbus_custorder_pool set status = 2 where preprocessorder = ? ");
					try {
						persistentService.clear();
						int i = persistentService.executeSql(sql.toString(),req.getCustorderid());
						if(i<=0){
							log.error("更新失败");
						}

					}catch (Exception e){
						log.error("更新异常");
					}
				}
			}).start();

		}catch (Exception e){
			log.info("update file " + e.getMessage());
		}
	}
	/**
	 * 发送业务短信
	 * 
	 * @param loginInfo
	 * @param bossorderBO
	 * @throws Exception
	 */
	private void sendBusinessSMS(OrderCommitInterReq req, LoginInfo loginInfo, QueBossorderBO bossorderBO)
			throws Exception {
		if (StringUtils.isBlank(req.getMobile()))
			return;
		String mobile = req.getMobile();
		if (mobile.length() > 11) {
			mobile = mobile.substring(0, 11);
		}
		if (!isPhoneNumber(mobile))
			return;
		Rule rule = ruleService.getRule("*", "BUSINESS_SMS_CITY");
		if (rule == null || StringUtils.isBlank(rule.getValue()))
			return;
		if (!rule.getValue().contains(loginInfo.getCity()) && 
				!rule.getValue().equals("*"))
			return;
		SendRandomCodeUapReq sendReq = new SendRandomCodeUapReq();
		sendReq.setAreaid(loginInfo.getAreaid() + "");
		sendReq.setBranchNO(loginInfo.getCity());
		sendReq.setOpcode(BizConstant.UAPOpcode.SEND);
		sendReq.setPhoneNum(mobile);
		sendReq.setUserIP("192.168.1.1");
		sendReq.setSmsContent(composeContent(bossorderBO, req));
		String uapRespOutput = getUapSocketInfOutput(req.getBizorderid(),
				BizConstant.UAPInterfaceService.SEND_RANDOM_CODE, sendReq, loginInfo);
	}

	private String composeContent(QueBossorderBO bossorderBO, OrderCommitInterReq req) {
		StringBuffer sb = new StringBuffer();
		sb.append("【广东广电】");
		sb.append("尊敬的用户,您已成功订购!");
		sb.append("智能卡号:尾号  ");
		sb.append(getLastNo(req.getSmartCardNo()));
		sb.append(";");
		sb.append("消费时间:");
		sb.append(DateTimeUtil.currentDateTime());
		sb.append(";");
		sb.append("商品名称:");
		sb.append(getPrdNames(bossorderBO));
		sb.append(";");
		sb.append("价格:");
		sb.append(getPrices(bossorderBO));
		sb.append(getUnits(bossorderBO));
		sb.append(",");
		sb.append("感谢您对广东广电网络的支持");
		return sb.toString();
	}

	private String getLastNo(String carNo) {
		String no = null;
		if (carNo.length() > 10) {
			no = carNo.substring(carNo.length() - 10, carNo.length());
		} else {
			no = carNo;
		}
		return no;
	}

	private String getPrdNames(QueBossorderBO bossorderBO) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bossorderBO.getOrderdets().size(); i++) {
			QueBossorderdetBO bo = bossorderBO.getOrderdets().get(i);
			sb.append(bo.getPname());
			sb.append(",");
		}
		String prdsName = sb.toString();
		if (prdsName.endsWith(",")) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();

	}

	private String getUnits(QueBossorderBO bossorderBO) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bossorderBO.getOrderdets().size(); i++) {
			QueBossorderdetBO bo = bossorderBO.getOrderdets().get(i);
			String t = "";
			if (bo.getUnit().equals("0")) {
				t = "月";
			} else {
				t = "周期";
			}
			String unit = "元" + "/" + t;
			sb.append(unit);
			sb.append(",");
		}
		String prdsName = sb.toString();
		if (prdsName.endsWith(",")) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	private String getPrices(QueBossorderBO bossorderBO) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bossorderBO.getOrderdets().size(); i++) {
			QueBossorderdetBO bo = bossorderBO.getOrderdets().get(i);
			sb.append(bo.getFee());
			sb.append(",");
		}
		String prdsName = sb.toString();
		if (prdsName.endsWith(",")) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	private boolean isPhoneNumber(String phoneNumber) {
		String matcherStr = "^1[3|4|5|8][0-9]\\d{8}$";// ^1[3|4|5|8][0-9]\d{8}$
		Pattern pattern = Pattern.compile(matcherStr);
		Matcher m = pattern.matcher(phoneNumber);
		return m.matches();
	}

	private QueBossorderBO getBossOrder4doOrderCommitInter(String bossorderid) throws Exception {

		QueBossorderInterResp bossorderResp = new QueBossorderInterResp();
		QueBossorderInterReq bossorderReq = new QueBossorderInterReq();
//		bossorderReq.setBizorderid(getDAO().getSequence("SEQ_BIZ_CUSTORDER_ID").toString());
		bossorderReq.setBizorderid(getBizorderid());
		bossorderReq.setBossorderid(bossorderid);
		this.queBossorder(bossorderReq, bossorderResp);

		QueBossorderBO retBossorder = null;
		if (BeanUtil.isListNotNull(bossorderResp.getOrders())) {
			retBossorder = bossorderResp.getOrders().get(0);
		}

		return retBossorder;

	}

	private void copyBossResp2InterResp4doOrderCommit(OrderCommitInterResp resp, String bossRespOutput)
			throws Exception {
		OrderCommitBossResp bossResp = (OrderCommitBossResp) this.copyBossResp2InterResp(resp,
				OrderCommitBossResp.class, bossRespOutput);
		// 如与boss有不同的字段，在这里另外做特殊处理
		// resp.setXxx(bossResp.getXxx());
	}

	private BizPortalOrder getBizPortalOrder4doOrderCommitInter(OrderCommitInterReq req) throws Exception {
		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getCustorderid(), "查询订单信息：订单号[custorderid]不能为空");

		BizPortalOrder order = (BizPortalOrder) getDAO().find(BizPortalOrder.class, Long.valueOf(req.getCustorderid()));
		if (null == order) {
			throw new BusinessException("获取订单信息：根据订单id[" + req.getCustorderid() + "]查询不到订单信息");
		}

		return order;
	}

	private OrderCommitBossReq getOrderCommitBossReq2Boss(OrderCommitInterReq req,String multipay) throws Exception {

		OrderCommitBossReq bossReq = new OrderCommitBossReq();
		// BeanUtils.copyPropertiesNotSuperClass(bossReq, req);//不复制父类的字段，不然报错
		// 如与boss有不同的字段，在这里另外做特殊处理
		// bossReq.setXxx(req.getXxx());
		bossReq.setOrderid(req.getCustorderid());
		bossReq.setPayway(req.getPayway());
		bossReq.setPaycode(req.getPaycode());
		if (StringUtils.isNotEmpty(req.getPayreqid())) {
			bossReq.setPayreqid(req.getPayreqid());
		}
		if (StringUtils.isNotEmpty(req.getBankaccno())) {
			bossReq.setBankaccno(req.getBankaccno());
		}
		bossReq.setMultipaywayflag(multipay);
		// // 支付方式
		// String payway = "";
		// // 支付编码
		// String paycode = "";
		// // 第方交交易流水号
		// String payreqid = req.getPayreqid();
		// if (BizConstant.SysPayway.SYS_PAYWAY_CASH.equals(req.getPayway())) {
		// payway =
		// BizConstant.PORTAL_ORDER_PAYWAY.PORTAL_ORDER_PAYWAY_THIRDPAY;
		// paycode = BizConstant.PORTAL_ORDER_PAYCODE.OTHER_CASH;
		//
		// if (StringUtils.isBlank(payreqid)) {
		// payreqid = UUID.randomUUID().toString();
		// }
		//
		// } else if (BizConstant.SysPayway.SYS_PAYWAY_CHEQUE.equals(req
		// .getPayway())) {
		// payway =
		// BizConstant.PORTAL_ORDER_PAYWAY.PORTAL_ORDER_PAYWAY_THIRDPAY;
		// paycode = BizConstant.PORTAL_ORDER_PAYCODE.OTHER_CHEQUE;
		//
		// if (StringUtils.isBlank(payreqid)) {
		// payreqid = UUID.randomUUID().toString();
		// }
		// } else if
		// (BizConstant.SysPayway.SYS_PAYWAY_POS.equals(req.getPayway())) {
		// payway =
		// BizConstant.PORTAL_ORDER_PAYWAY.PORTAL_ORDER_PAYWAY_THIRDPAY;
		// paycode = BizConstant.PORTAL_ORDER_PAYCODE.OTHER_POS;
		// }
		return bossReq;
	}

	/**
	 * 查询boss订单接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queBossorder(QueBossorderInterReq req, QueBossorderInterResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getBizorderid(), "查询boss订单信息：业务订单号不能为空");
		CheckUtils.checkEmpty(req.getBossorderid(), "查询boss订单信息：boss订单号不能为空");

		// pubService.checkBizAcceptRight(Long.valueOf(req.getHouseid()),
		// Long.valueOf(req.getPatchid()), loginInfo);

		// 将请求做一下转换，并赋默认值
		QueBossorderBossReq bossReq = getQueBossorderBossReq2Boss(req);
		
		String serverCode = "";
		if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
			serverCode = BizConstant.ServerCityBossInterface.QUE_ORDERINFO;
		}else{
			serverCode = BizConstant.BossInterfaceService.QUE_ORDERINFO;
		}

		// 调用BOSS接口
		String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(),
				serverCode, bossReq, loginInfo);

		// 拼装返回信息
		copyBossResp2InterResp4queBossorder(resp, bossRespOutput);
		// resp.setCustorderid(req.getBizorderid());

		return returnInfo;
	}

	private void copyBossResp2InterResp4queBossorder(QueBossorderInterResp resp, String bossRespOutput)
			throws Exception {
		QueBossorderBossResp bossResp = (QueBossorderBossResp) this.copyBossResp2InterResp(resp,
				QueBossorderBossResp.class, bossRespOutput);
		// 如与boss有不同的字段，在这里另外做特殊处理
		// resp.setXxx(bossResp.getXxx());

	}

	private QueBossorderBossReq getQueBossorderBossReq2Boss(QueBossorderInterReq req) {
		QueBossorderBossReq bossReq = new QueBossorderBossReq();
		bossReq.setCurrentPage(req.getCurrentPage());
		bossReq.setPagesize(req.getPagesize());
		bossReq.setOrderid(req.getBossorderid());

		return bossReq;
	}

	// 查询版本号接口 begin
	/**
	 * 查询版本号接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ReturnInfo queVersion(QueVersionInterReq req, QueVersionInterResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		// LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		// CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		

		ApkPackage apkPackage = getApkVisionInfo(req);

		// 拼装返回信息
		resp.setDescript(apkPackage.getDescript());
		resp.setDownloadurl(apkPackage.getDownloadUrl());
		resp.setVersion(apkPackage.getVersion());
		resp.setUpdatetime(apkPackage.getUpdateTime());

		return null;
	}

	private ApkPackage getApkVisionInfo(QueVersionInterReq req) throws Exception {
		CheckUtils.checkNull(req, "查询版本号:请求信息不能为空");
		// CheckUtils.checkEmpty(req.getBizorderid(), "查询版本号:业务订单号不能为空");
		CheckUtils.checkEmpty(req.getAppid(), "查询版本号:应用签名不能为空");

		ApkPackage retApkPackage = new ApkPackage();

		StringBuffer sql = new StringBuffer();
		List paramList = new ArrayList();

		sql.append(" select * from (");
		sql.append(" select p.package_id id, p.* ");
		sql.append("   from prv_apk_package p ");
		sql.append("  where 1 = 1 ");
		sql.append("    and p.packages = ? ");
		paramList.add(req.getAppid());
		sql.append("  order by p.package_id desc ");
		sql.append(" ) v ");

		List<ApkPackage> apkPackageList = getDAO().find(sql.toString(), ApkPackage.class, paramList.toArray());
		if (BeanUtil.isListNull(apkPackageList)) {
			throw new BusinessException("查询不到版本信息");
		}

		retApkPackage = apkPackageList.get(0);
		return retApkPackage;
	}
	// 查询版本号接口 end

	/**
	 * 查询消费记录接口
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queServPrddet(QueServPrddetInterReq req, QueServPrddetInterResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		// 调boss接口,并将结果存入resp
		callBossInf4queServPrddet(req, resp, loginInfo);

		return returnInfo;
	}

	private void callBossInf4queServPrddet(QueServPrddetInterReq req, QueServPrddetInterResp resp, LoginInfo loginInfo)
			throws Exception {

		// 将请求做一下转换，并赋默认值
		QueServPrddetBossReq req2Boss = getQueServPrddetInterReq2Boss(req);

		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
				BizConstant.BossInterfaceService.QUE_SERVPRDDET, req2Boss, loginInfo);

		copyBossResp2InterResp4queServPrddet(resp, bossRespOutput);

	}

	private void copyBossResp2InterResp4queServPrddet(QueServPrddetInterResp resp, String jsonStr) throws Exception {
		QueServPrddetBossResp bossResp = (QueServPrddetBossResp) BeanUtil.jsonToObject(jsonStr,
				QueServPrddetBossResp.class);

		// 因为字段是一样的，这里可以偷懒一下，
		// 后面如果接口对外字段和boss返回字段不一致，则自行写使用set方法设置值
		BeanUtils.copyProperties(resp, bossResp);
		// resp.setAcctkind(bossResp.getAcctkind());

	}

	private QueServPrddetBossReq getQueServPrddetInterReq2Boss(QueServPrddetInterReq req) {

		QueServPrddetBossReq queServPrddetBossReq = new QueServPrddetBossReq();
		queServPrddetBossReq.setCurrentPage(req.getCurrentPage());
		queServPrddetBossReq.setEtime(req.getEtime());
		queServPrddetBossReq.setKeyno(req.getKeyno());
		queServPrddetBossReq.setPagesize(req.getPagesize());
		queServPrddetBossReq.setPid(req.getPid());
		queServPrddetBossReq.setStime(req.getStime());
		return queServPrddetBossReq;
	}

	@Transactional(readOnly = true)
	public ReturnInfo queAttprdList(QueArrprdResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		String sql = "select sk.knowname objName,sk.objid objId,sk.objtype objtype from prd_salespkg_know sk where sk.city = ? and sk.issales = ? order by sk.objtype";
		List<Kits> list = getDAO().find(sql, Kits.class, loginInfo.getCity(), "y");
		if (BeanUtil.isListNotNull(list)) {
			resp.setDatas(list);
		}
		return returnInfo;
	}

	@Transactional(readOnly = true)
	public ReturnInfo quePercomb(QuepercombReq req, QuepercombResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		Map<String, List<DeviceSourceInfo>> percombDevice = new HashMap<String, List<DeviceSourceInfo>>();
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		StringBuffer sb = new StringBuffer();
		sb.append("		select bc.combname combname,");
		sb.append("		bc.percomb percomb,");
		sb.append("		bc.permarks permarks");
		sb.append("		from biz_percomb_cfg bc");
		sb.append("		where 1 = 1");
		if (req.getBizcode() != null) {
			if (req.getBizcode().equals("BIZ_ASYNC_USER_NEW")) {
				sb.append("		and (bc.opmodes like ? ");
				sb.append("		or bc.opmodes like ?) ");
			} else {
				sb.append("		and	bc.opmodes like ?");
			}
		}
		sb.append("		and ( bc.citys like ?");
		sb.append("		or bc.citys = ? )");
		sb.append("		order by bc.sort");
		List params = new ArrayList();
		if (req.getBizcode() != null) {
			if (req.getBizcode().equals("BIZ_ASYNC_USER_NEW")) {
				params.add("%0%");
				params.add("%1%");
			} else {
				params.add("%1%");
			}
		}
		params.add("%" + loginInfo.getCity() + "%");
		params.add("*");
		List<Combination> list = getDAO().find(sb.toString(), Combination.class, params.toArray());
		if (BeanUtil.isListNotNull(list)) {
			resp.setDatas(list);
			for (Combination combination : list) {
				List<DeviceSourceInfo> data = getPercombDevice(getPercombSourceList(combination.getPercomb()));
				percombDevice.put(combination.getPercomb(), data);
			}
			resp.setDevices(percombDevice);
		}
		if (list != null && list.size() > 1) {
			int position = -1;
			for (int i = 0; i < list.size(); i++) {
				Combination combination = list.get(i);
				if (combination.getCombname().equals("U宽频业务")) {
					position = i;
					break;
				}
			}
			if (position != -1) {
				Combination combination = list.remove(position);
				list.add(0, combination);
			}
		}
		return returnInfo;
	}

	/**
	 * 获得对应的业务码,设备来源，
	 * 
	 * @param percomb
	 * @return
	 * @throws Exception
	 */
	private List<String> getPercombSourceList(String percomb) throws Exception {
		List<String> result = new ArrayList<String>();
		String sql = " SELECT t.percomb percomb,t.useprops combname ,t.devprds permarks "
				+ "from biz_percomb_dev_cfg t where t.percomb = ? and t.kind = 2 ORDER BY t.sort";
		List<Combination> list = getDAO().find(sql, Combination.class, percomb);
		if (!list.isEmpty()) {
			Combination combination = list.get(0);
			String[] datas = combination.getCombname().split("~");
			for (String str : datas) {
				result.add(str);
			}
		}
		return result;
	}

	private List<DeviceSourceInfo> getPercombDevice(List<String> useprors) throws Exception {
		if (useprors.isEmpty())
			return new ArrayList<DeviceSourceInfo>();
		StringBuffer sb = new StringBuffer();
		List param = new ArrayList();
		sb.append("select t.mcode sourceid,t.mname sourcename from prv_sysparam t where t.gcode = ? and t.mcode in (");
		param.add("SYS_DEV_UPROP");
		for (int i = 0; i < useprors.size(); i++) {
			sb.append("?");
			param.add(useprors.get(i));
			if (i != useprors.size() - 1) {
				sb.append(",");
			}
		}
		sb.append(")");
		String sql = sb.toString();
		List<DeviceSourceInfo> data = getDAO().find(sql, DeviceSourceInfo.class, param.toArray());
		if (data != null && data.size() > 0) {
			int top = -1;
			for (int i = 0; i < data.size(); i++) {
				DeviceSourceInfo info = data.get(i);
				if (info.getSourceName().equals("套餐配机")) {
					top = i;
					break;
				}
			}
			if (top != -1) {
				DeviceSourceInfo info = data.remove(top);
				data.add(0, info);
			}
		}
		return data;
	}

	@Transactional(readOnly = true)
	public ReturnInfo queryDeviceSource(DeviceSourceQuesp quesp, DeviceSourceResp resp) {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		List<DeviceSourceInfo> infos = new ArrayList<DeviceSourceInfo>();
		DeviceSourceInfo info1 = new DeviceSourceInfo();
		info1.setSourceId("0");
		info1.setSourceName("购买");

		DeviceSourceInfo info2 = new DeviceSourceInfo();
		info2.setSourceId("5");
		info2.setSourceName("赠送");

		DeviceSourceInfo info3 = new DeviceSourceInfo();
		info3.setSourceId("4");
		info3.setSourceName("配置");
		infos.add(info1);
		infos.add(info2);
		infos.add(info3);
		resp.setDatas(infos);
		return returnInfo;
	}

	@Transactional(readOnly = true)
	public ReturnInfo queProductInfo(QueProductQuesp quesp, QueProductResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setAreaid(681l);
		
//		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
//		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		resp.setMsg("");
		resp.setFlag(0);
		String[] knowledgs = quesp.getKnowledgeIds().split(",");
		String sql = "select pk.objtype objType , pk.objid objId from prd_salespkg_know pk where pk.knowid = ?";
		List<ProductInfo> list = new ArrayList<ProductInfo>();
		for (String know : knowledgs) {
			List<ProductInfo> result = getDAO().find(sql, ProductInfo.class, know);
			if (result != null && result.size() > 0) {
				list.add(result.get(0));
			}
		}

		List<ProductInfo> products = new ArrayList<ProductInfo>();
		List<ProductInfo> packs = new ArrayList<ProductInfo>();
		List<ProductInfo> goodsList = new ArrayList<ProductInfo>();
		for (int i = 0; i < list.size(); i++) {
			ProductInfo info = list.get(i);
			if (info.getObjType().equals(BizConstant.PrdSalespkgKnowObjtype.PRD)) {
				products.add(info);
			} else if (info.getObjType().equals(BizConstant.PrdSalespkgKnowObjtype.SALESPKG)) {
				packs.add(info);
			} else if (info.getObjType().equals(BizConstant.PrdSalespkgKnowObjtype.GOODS)
					|| info.getObjType().equals(BizConstant.PrdSalespkgKnowObjtype.GOODS_TYPE)) {
				goodsList.add(info);
			}
		}
		if (!products.isEmpty()) {
			if (packs.size() == 0) {
				if (hasBase(products))
					return returnInfo;
			} else {
				if( hasBase(products)){
					return returnInfo;
				}
				if(hasBase(queryProduct(packs)))
					return returnInfo;
			}
		} else if (!packs.isEmpty()) {
			if(hasBase(queryProduct(packs)))
				return returnInfo;
		} else if (!goodsList.isEmpty()) {
			if (goodsHasBase(goodsList))
				return returnInfo;
		}
		List paramsList = paramService.getData("BIZ_NONBASE_AREAIDS", loginInfo.getCity(), null, null);
		if (paramsList == null || paramsList.size() == 0 || paramsList.get(0) == null) {
			resp.setFlag(1);
			resp.setMsg("所选套餐中未包含基本包，请选择基本包或含有基本包的套餐后提交");
		} else {
			PrvSysparam pvrParam = (PrvSysparam) paramsList.get(0);
			String data = pvrParam.getData();
			if (data.contains(quesp.getAreaId())) {
				resp.setFlag(0);
				resp.setMsg("所选套餐中未包含基本包，是否继续提交?");
			} else {
				resp.setFlag(1);
				resp.setMsg("所选套餐中未包含基本包，请选择基本包或含有基本包的套餐后提交");
			}
		}
		return returnInfo;
	}

	private List<ProductInfo> queryProduct(List<ProductInfo> packs) throws Exception {
		List<ProductInfo> products = new ArrayList<ProductInfo>();
		if (packs == null || packs.isEmpty())
			return products;
		
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT PSS.PID objId,PSS.ISPOSTPONE objType ");
		sb.append("		FROM PRD_SALESPKG_SOFT PSS");
		sb.append("		where 1 = 1 AND");
		sb.append("		PSS.SALESPKGID IN (");
		for(int i = 0 ;i < packs.size() ;i++){
			sb.append("?");
			params.add(packs.get(i).getObjId());
			if(i != packs.size() - 1){
				sb.append(",");
			}
		}
		sb.append("		)");
		
		List<ProductInfo> result = getDAO().find(sb.toString(), ProductInfo.class, params.toArray());
		if (result != null && result.size() > 0) {
			for(int i = 0;i < result.size() ;i++){
				result.get(i).setObjType("0");
				products.add(result.get(i));
			}
		}
		return products;
	}

	private boolean goodsHasBase(List<ProductInfo> goods) throws Exception {
		for (ProductInfo str : goods) {
			List params = new ArrayList();
			StringBuffer sb = new StringBuffer();
			sb.append("		SELECT PD.ISBASE OBJTYPE,PD.PID OBJID");
			sb.append("		FROM PRD_PCODE PD");
			sb.append("		WHERE 1 = 1");
			sb.append("		AND PD.PCLASS = ?");
			sb.append("		AND PD.PID =  (");
			sb.append("		SELECT B.PID");
			sb.append("		FROM PRD_SALES B");
			sb.append("		WHERE 1 = 1");
			sb.append("		AND b.SALESID = ?");
			params.add("0");
			params.add(str.getObjId());
			List<ProductInfo> result = getDAO().find(sb.toString(), ProductInfo.class, params.toArray());
			if (result != null && result.size() > 0) {
				if (result.get(0).getObjType().equalsIgnoreCase("y")) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean hasBase(List<ProductInfo> product) throws Exception {
		List params = new ArrayList();
		params.add(0);
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT PD.ISBASE objType,PD.PID objId");
		sb.append("		FROM PRD_PCODE PD");
		sb.append("		WHERE 1 = 1 AND");
		sb.append("		PD.PCLASS = ? AND");
		sb.append("		PD.PID IN (");
		for(int i = 0; i < product.size() ;i++){
			sb.append(" ?");
			params.add(product.get(i).getObjId());
			if(i != product.size() - 1){
				sb.append(",");
			}
		}
		sb.append(" )");
		List<ProductInfo> result = getDAO().find(sb.toString(), ProductInfo.class,params.toArray());
		if (result != null && result.size() > 0) {
			for(ProductInfo info:result){
				if(info.getObjType().equalsIgnoreCase("y")){
					return true;
				}
			}
		}
		return false;
	}

	public ReturnInfo queOrderFeeNotice(QueOrderNoticeReq req, ArrayList<QueOrderNoticeResp> resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求对象不能为空");
		CheckUtils.checkEmpty(req.getCustomerId(), "匹配条件[customerId]不能为空");

		PrvSysparam sysparam = paramService.getData("BIZ_CHG_ONCEFEES", loginInfo.getCity());
		if (sysparam != null) {
			if (StringUtils.isNotBlank(sysparam.getData())) {
				String[] contents = sysparam.getData().split(";");
				if (contents != null && contents.length != 0) {
					for (String content : contents) {
						QueOrderNoticeResp res = getOrderNoticeResp(content, loginInfo);
						resp.add(res);
					}
				}
			}
		}
		return returnInfo;
	}

	private QueOrderNoticeResp getOrderNoticeResp(String content, LoginInfo loginInfo) throws Exception {
		if (StringUtils.isBlank(content))
			return null;
		String[] datas = content.split("~");
		if (datas == null || datas.length == 0 || datas.length < 3)
			return null;
		QueOrderNoticeResp resp = new QueOrderNoticeResp();
		resp.setpCode(datas[0]);
		resp.setpName(datas[1]);
		resp.setSums(datas[2]);
		Rule rule = ruleService.getRule(loginInfo.getCity(), "ONEFEE_PRICES");
		if (rule != null) {
			if (StringUtils.isNotBlank(rule.getValue())) {
				String[] prices = rule.getValue().split("~");
				if (prices != null) {
					List<String> feeList = new ArrayList<String>(Arrays.asList(prices));
					resp.setPrices(feeList);
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
		return resp;
	}

	/**
	 * 查询用户住宅地址信息
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queCustomAddresses(QueCustomerAddressReq req, ArrayList<CustAddrsResp> resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		QueCustomerBossReq bosReq = new QueCustomerBossReq();
		bosReq.setCustid(req.getCustid());
		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.QUE_CUSTADDR,
				bosReq, loginInfo);
		if (StringUtils.isNotBlank(bossRespOutput) && !bossRespOutput.equals("null")) {
			JSONObject obj = new JSONObject(bossRespOutput);
			JSONArray array = obj.getJSONArray("custAddrs");
			for (int i = 0; i < array.length(); i++) {
				JSONObject data = array.getJSONObject(i);
				CustAddrsResp res = new CustAddrsResp();
				res.setChouseid(data.optString("chouseid"));
				res.setCustid(data.optString("custid"));
				res.setHouseid(data.optString("houseid"));
				res.setWhladdr(data.optString("whladdr"));
				resp.add(res);
			}

		}
		return returnInfo;
	}

	public ReturnInfo queDevIsBack(QueDevIsBackReq req, QueDevIsBackResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		Date date = new Date();
		int num = DateTimeUtil.CompareTo(date, DateTimeUtil.parseDate(req.getServices(), "yyyy-MM-dd hh:mm:ss"));
		String s = "";
		if (num < 0) {
			s = "1";
		} else {
			s = "0";
		}
		List paramList = new ArrayList();
		paramList.add(req.getAreaid());
		paramList.add(req.getOldsrc());
		paramList.add(s);
		paramList.add(req.getOldown());

		String sql = "select distinct isback isBack from biz_devuse_rule t"
				+ " where t.areaid = ? and t.oldsrc1 = ? and t.services = ? and t.oldown1 = ?";
		List<DevUseBean> datas = getDAO().find(sql, DevUseBean.class, paramList.toArray());
		DevUseBean devRule = null;
		if (datas != null && datas.size() == 1) {
			resp.setChoseAble(false);
			devRule = datas.get(0);
			if (devRule.getIsBack().equalsIgnoreCase("y")) {
				resp.setBack(true);
			} else {
				resp.setBack(false);
			}
		} else {
			resp.setChoseAble(true);
		}

		return returnInfo;
	}
	
	public ReturnInfo queServPrdInfo(QueServPrdInterReq req,QueServPrdInterResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getKeyno(),"设备信息号不能为空");
		QueServPrdBossInterReq bossReq = getQueServPrdBossInterReq(req);
		 String serverCode = "";
	        if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
	        	serverCode = BizConstant.ServerCityBossInterface.QUE_SERVPRDINFO;
	        }else{
	        	serverCode = BizConstant.BossInterfaceService.QUE_SERVPRDINFO;
	        }
		String responese = getBossHttpInfOutput(req.getBizorderid(),serverCode, bossReq, loginInfo);
		JSONObject respone = new JSONObject(responese);
		List<com.maywide.biz.channel.pojo.Pcode> allDatas = getServPcodes(respone.getJSONArray("prods"));
		if(allDatas != null && !allDatas.isEmpty()){
			boolean isUpdateCity = isUpdateCity(loginInfo.getCity());
			String goods = "商品";
			String baseprd = "基本包";
			String prd = "产品";
			String pkg = "营销方案";
			Map<String, List<com.maywide.biz.channel.pojo.Pcode>> datamap = new HashMap<String, List<com.maywide.biz.channel.pojo.Pcode>>();
			for(int i = 0 ; i < allDatas.size() ;i++){
				String name = null;
				com.maywide.biz.channel.pojo.Pcode pcode = allDatas.get(i);
				
				if (isUpdateCity) {
					name = goods;
				}else {
					if(pcode.getFlag().equals("0")){
							name = prd;
					}else{
						name = pkg;
					}
				}
				
				if(pcode.getIsbase().equals("Y")){
					name = baseprd;
				}
				
				List<com.maywide.biz.channel.pojo.Pcode> childDatas = datamap.get(name);
				if(childDatas == null){
					childDatas = new ArrayList<com.maywide.biz.channel.pojo.Pcode>();
					childDatas.add(pcode);
					datamap.put(name, childDatas);
				}else{
					childDatas.add(pcode);
				}
			}
			if(datamap.size() > 0){
				List<ServPrdParentBean> datas = new ArrayList<ServPrdParentBean>();
				Iterator<Entry<String, List<com.maywide.biz.channel.pojo.Pcode>>> iterator = datamap.entrySet().iterator();
				while(iterator.hasNext()){
					Entry<String, List<com.maywide.biz.channel.pojo.Pcode>> entry = iterator.next();
					ServPrdParentBean bean = new ServPrdParentBean();
					bean.setName(entry.getKey());
					bean.setProducts(entry.getValue());
					datas.add(bean);
				}
				List<ServPrdParentBean> results = new ArrayList<ServPrdParentBean>();
				for(ServPrdParentBean bean:datas){
					ServPrdParentBean tmpBean = new ServPrdParentBean();
					tmpBean.setName(bean.getName());
					List<com.maywide.biz.channel.pojo.Pcode> tmp = new ArrayList<com.maywide.biz.channel.pojo.Pcode>();
					tmpBean.setProducts(tmp);
					for(com.maywide.biz.channel.pojo.Pcode pcode:bean.getProducts()){
						com.maywide.biz.channel.pojo.Pcode tmpCode = new com.maywide.biz.channel.pojo.Pcode();
						tmpCode.setEtime(pcode.getEtime());
						tmpCode.setIsbase(pcode.getIsbase());
						tmpCode.setIspostpone(pcode.getIspostpone());
						tmpCode.setPcode(pcode.getPcode());
						tmpCode.setPcodeStr(pcode.getPcodeStr());
						tmpCode.setPermark(pcode.getPermark());
						tmpCode.setPname(pcode.getPname());
						tmpCode.setPrice(pcode.getPrice());
						tmpCode.setProdarrears(pcode.getProdarrears());
						tmpCode.setProdstatus(pcode.getProdstatus());
						tmpCode.setSalescode(pcode.getSalescode());
						tmpCode.setSalesname(pcode.getSalesname());
						tmpCode.setStime(pcode.getStime());
						tmpCode.setUnit(pcode.getUnit());
						tmpCode.setFlag(pcode.getFlag());
						tmpCode.setFees(pcode.getFees());
						if (isUpdateCity) {
							tmpCode.setFlag(BizConstant.PrdSalespkgKnowObjtype.GOODS);
						} else {
							if(!tmpCode.getFlag().equals("0")){
								tmpCode.setFlag("1");
							}
						}
						tmp.add(tmpCode);
					}
					results.add(tmpBean);
				}
				resp.setDatas(results);
			}
		
		}
		
		return returnInfo;
	}
	
	public boolean isUpdateCity(String city) throws Exception {
		Rule rule = ruleService.getRule("*", "UPDATE_CITY");
		return rule != null && StringUtils.isNotBlank(rule.getValue()) && rule.getValue().contains(city);
	}
	
	private List<com.maywide.biz.channel.pojo.Pcode> getServPcodes(JSONArray array) throws JSONException{
		List<com.maywide.biz.channel.pojo.Pcode> datas  = new ArrayList<com.maywide.biz.channel.pojo.Pcode>();
		for(int i = 0; i < array.length() ;i++){
			com.maywide.biz.channel.pojo.Pcode pcode = new com.maywide.biz.channel.pojo.Pcode();
			JSONObject obj = array.getJSONObject(i);
			pcode.setEtime(obj.getString("etime"));
			pcode.setFlag(obj.getString("flag"));
			pcode.setIsbase(obj.getString("isbase"));
			pcode.setIspostpone(obj.getString("ispostpone"));
			pcode.setPcode(obj.getString("pcode"));
			pcode.setPcodeStr(obj.getString("pcodeStr"));
			pcode.setPermark(obj.getString("permark"));
			pcode.setPname(obj.getString("pname"));
			pcode.setPrice(obj.getString("price"));
			pcode.setProdarrears(obj.getString("prodarrears"));
			pcode.setProdfees(obj.getString("prodfees"));
			pcode.setProdstatus(obj.getString("prodstatus"));
			pcode.setSalescode(obj.getString("salescode"));
			pcode.setSalesname(obj.getString("salesname"));
			pcode.setSalespkgname(obj.getString("salespkgname"));
			pcode.setStime(obj.getString("stime"));
			pcode.setUnit(obj.getString("unit"));
			pcode.setFees(obj.getString("fees"));
			datas.add(pcode);
		}
		return datas;
	}
	
	private QueServPrdBossInterReq getQueServPrdBossInterReq(QueServPrdInterReq req){
		QueServPrdBossInterReq bossReq = new QueServPrdBossInterReq();
		bossReq.setCurrentPage("1");
		bossReq.setFlag(req.getFlag());
		bossReq.setKeyno(req.getKeyno());
		bossReq.setPstatus("*");
		bossReq.setPagesize("100");
		return bossReq;
		
	}

}
