package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.queGoods.QueGoodsCatalogResp;
import com.maywide.biz.inter.pojo.queGoods.QueGoodsListReq;
import com.maywide.biz.inter.pojo.queryProduct.QueCatalogReq;
import com.maywide.biz.inter.pojo.queryProduct.QueCatalogResp;
import com.maywide.biz.inter.pojo.queryProduct.QuePkgListReq;
import com.maywide.biz.inter.pojo.queryProduct.QuePkgListResp;
import com.maywide.biz.inter.pojo.queryProduct.QueProductListResp;
import com.maywide.biz.inter.pojo.queryProduct.QueProductReq;
import com.maywide.biz.inter.pojo.queryProduct.QueSearchReq;
import com.maywide.biz.inter.pojo.queryProduct.QueSearchResp;
import com.maywide.biz.inter.pojo.queryProduct.QueryBusTypeResp;
import com.maywide.biz.inter.pojo.querysalespkgknow.Cycle;
import com.maywide.biz.inter.pojo.queuserprd.QueUserPrdBossReq;
import com.maywide.biz.inter.pojo.queuserprd.QueUserPrdBossResp;
import com.maywide.biz.inter.pojo.queuserprd.UserPrdsBO;
import com.maywide.biz.prd.entity.CatalogCondtion;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.CheckUtils;


@Service
@Transactional(rollbackFor = Exception.class)
public class KnowledgeService extends CommonService {
	
	@Autowired
	private RuleService ruleService;
	
	@Autowired
	private ParamService paramService;
	
	@Autowired
	private InterPrdService interPrdService;
	
	private final String PERMARKRULE = "CITY_PERMARK";
	
	public ReturnInfo queryBusinessType(ArrayList<QueryBusTypeResp> resps) throws Exception{
		
		ReturnInfo returnInfo =initReturnInfo();
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
		Rule cityRule = ruleService.getRule(loginInfo.getCity(), PERMARKRULE);
		if(cityRule == null || StringUtils.isBlank(cityRule.getValue())){
			cityRule = ruleService.getRule("*", PERMARKRULE);
			CheckUtils.checkNull(cityRule, "数据配置异常,请联系相关人员进行配置");
		}
		CheckUtils.checkEmpty(cityRule.getValue(),"数据配置异常,请联系相关人员进行配置");
		hanlerCityValue(cityRule.getValue(),resps);
		
		return returnInfo;
	}
	
	private void hanlerCityValue(String value,ArrayList<QueryBusTypeResp> resps) throws Exception{
		String[] permarks = value.split(",");
		for(String permark:permarks){
			PrvSysparam sysparam = paramService.getData("SYS_PERMARK", permark);
			QueryBusTypeResp resp = new QueryBusTypeResp(Integer.parseInt(permark), sysparam.getMname());
			resps.add(resp);
		}
	}
	//下面接口省网没有用到
	public ReturnInfo queryCatalog(QueCatalogReq req,ArrayList<QueCatalogResp> resps) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		
		LoginInfo loginInfo = getLoginInfo();
		String objType = req.getObjType();
		if(StringUtils.isBlank(objType)){
			objType = BizConstant.PrdSalespkgKnowObjtype.PRD;
		}
		List params = new ArrayList();
		params.add(objType);
		params.add("%"+loginInfo.getCity()+"%");
		params.add("Y");
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT DISTINCT (A.CATALOGID) catalogId, A.CATALOGNAME catalogName, C.OBJTYPE objType");
		sb.append("		FROM PRD_CATALOG A,PRD_CATALOG_ITEM B,PRD_SALESPKG_KNOW C,PRD_PCODE D");
		sb.append("		WHERE 1 = 1 AND B.KNOWID = C.KNOWID");
		sb.append("		AND A.CATALOGID = B.CATALOGID");
		sb.append("		AND C.OBJTYPE = ?");
		sb.append("		AND A.CITY LIKE ?");
		sb.append("		AND A.CATALOGSTATUS = ? ");
		if(StringUtils.isNotBlank(req.getType())){
			sb.append("		AND D.PERMARK IN ( ? )");
			params.add(req.getType());
		}
		
		sb.append("		ORDER BY A.PRI");
		
		
		List<QueCatalogResp> datas= getDAO().find(sb.toString(),QueCatalogResp.class, params.toArray());
		if(datas != null && datas.size() > 0){
			List<QueCatalogResp> retSet = new ArrayList<QueCatalogResp>();
			List<QueCatalogResp> opSet = new ArrayList<QueCatalogResp>();
			for(QueCatalogResp child:datas){
				CatalogCondtion condtion = new CatalogCondtion();
				condtion.setCatalogid(child.getCatalogId());
				List<CatalogCondtion> condtionList = getDAO().find(condtion);
				if (BeanUtil.isListNull(condtionList)) {
					// 没有限制条件的目录
					retSet.add(child);
				}else{
					for (CatalogCondtion obj : condtionList) {
						if (StringUtils.isBlank(obj.getContiontype())) {
							// 没有限制条件的目录
							retSet.add(child);
						} else if (BizConstant.CATALOG_CONDITION_TYPE.OPERATOR.equals(obj.getContiontype())) {
							// 指定操作员的目录
							if (obj.getContionvalue().equals(loginInfo.getOperid())) {
								retSet.add(child);
							}
						} else if (BizConstant.CATALOG_CONDITION_TYPE.DEPT.equals(obj.getContiontype())) {
							// 指定部门的目录
							if (obj.getContionvalue().equals(loginInfo.getDeptid())) {
								retSet.add(child);
							}
						}else if(BizConstant.CATALOG_CONDITION_TYPE.OPCODE.equals(obj.getContiontype())){
							if(StringUtils.isNotBlank(req.getBizcode()) && obj.getContionvalue().equalsIgnoreCase(req.getBizcode())){
								opSet.add(child);
							}
						}
					}
				}
			}
			if(!opSet.isEmpty()){
				retSet.clear();
				retSet.addAll(opSet);
			}
			for(QueCatalogResp data:retSet){
				data.setPermark(req.getType()+"");
			}
			resps.addAll(retSet);
		}
		return returnInfo;
	}
	
	public ReturnInfo queryCatalogPkg(QuePkgListReq req,ArrayList<QuePkgListResp> resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		List params = new ArrayList();
		params.add("Y");
		params.add("%"+loginInfo.getCity()+"%");
		params.add(BizConstant.PrdSalespkgKnowObjtype.SALESPKG);
		
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT DISTINCT (C.KNOWID) knowId,A.CATALOGID catalogId,");
		sb.append("		 C.KNOWNAME knowName,A.CATALOGNAME catalogName,");
		sb.append("		C.PRICE price,C.OBJTYPE objType,C.BRIEF brief,C.WORDEXP introduce,C.OBJID objId,");
		sb.append("		C.IMAGE img,C.ICON icon");
		sb.append("		, CASE (SELECT ordertype FROM PRD_SALESPKG WHERE salespkgid = C.OBJID LIMIT 1) WHEN 0 THEN '次' ELSE '周期' END unitName ");
		sb.append("		FROM PRD_CATALOG A,PRD_CATALOG_ITEM B,PRD_SALESPKG_KNOW C");
		sb.append("		WHERE 1 = 1 AND A.CATALOGID = B.CATALOGID");
		sb.append("		AND B.KNOWID = C.KNOWID ");
		sb.append("		AND A.CATALOGSTATUS = ?");
		sb.append("		AND A.CITY LIKE ? ");
		sb.append("		AND C.OBJTYPE = ? ");
		
		if(StringUtils.isNotBlank(req.getCatalogid())){
			sb.append("		AND A.CATALOGID = ?");
			params.add(req.getCatalogid());
		}
		
		List<QuePkgListResp> datas = getDAO().find(sb.toString(),QuePkgListResp.class, params.toArray());
		if(datas != null && datas.size() > 0){
			List<QuePkgListResp> retSet = new ArrayList<QuePkgListResp>();
			List<QuePkgListResp> opSet = new ArrayList<QuePkgListResp>();
			for(QuePkgListResp child:datas){
				CatalogCondtion condtion = new CatalogCondtion();
				condtion.setCatalogid(child.getCatalogId());
				List<CatalogCondtion> condtionList = getDAO().find(condtion);
				if (BeanUtil.isListNull(condtionList)) {
					// 没有限制条件的目录
					retSet.add(child);
				}else{
					for (CatalogCondtion obj : condtionList) {
						if (StringUtils.isBlank(obj.getContiontype())) {
							// 没有限制条件的目录
							retSet.add(child);
						} else if (BizConstant.CATALOG_CONDITION_TYPE.OPERATOR.equals(obj.getContiontype())) {
							// 指定操作员的目录
							if (obj.getContionvalue().equals(loginInfo.getOperid())) {
								retSet.add(child);
							}
						} else if (BizConstant.CATALOG_CONDITION_TYPE.DEPT.equals(obj.getContiontype())) {
							// 指定部门的目录
							if (obj.getContionvalue().equals(loginInfo.getDeptid())) {
								retSet.add(child);
							}
						}else if(BizConstant.CATALOG_CONDITION_TYPE.OPCODE.equals(obj.getContiontype())){
							if(StringUtils.isNotBlank(req.getBizcode()) && obj.getContionvalue().equalsIgnoreCase(req.getBizcode())){
								opSet.add(child);
							}
						}
					}
				}
			}
			if(!opSet.isEmpty()){
				resp.addAll(opSet);
			}else{
				resp.addAll(retSet);
			}
			
		}
		if(StringUtils.isNotBlank(req.getCustid())){
			QueUserPrdBossReq req2Boss = getQueUserPrdReq2Boss(req.getCustid());
			String serverCode = "";
			if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
				serverCode = BizConstant.ServerCityBossInterface.QUE_USERPRDINFO;
			}else{
				serverCode = BizConstant.BossInterfaceService.QUE_USERPRDINFO;
			}
			String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
					serverCode, req2Boss,
	                loginInfo);
			if(StringUtils.isNotBlank(bossRespOutput)){
			 QueUserPrdBossResp bossResp = (QueUserPrdBossResp) BeanUtil
		                .jsonToObject(bossRespOutput, QueUserPrdBossResp.class);
			 for(QuePkgListResp product:resp){
				 for(UserPrdsBO prd:bossResp.getProds()){
					 if(prd.getPid().equals(product.getObjId())){
						 product.setOrdered(true);
						 break;
					 }
				 }
			 }
			}
		}
		return returnInfo;
	}
	
	
	public ReturnInfo queryProductList(QueProductReq req,ArrayList<QueProductListResp> resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		String[] permarks = req.getPermark().split(",");
		List params = new ArrayList();
		params.add("%"+loginInfo.getCity()+"%");
		params.add("*");
		params.add(req.getCatalogId());
		params.add(req.getObjType());
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT A.PID pId , A.PCODE pCode ,C.KNOWNAME pName,A.PERMARK perMark,B.CATALOGID catalogId ,");
		sb.append("		B.KNOWID knowId, C.BRIEF brief ,C.OBJID objId , C.OBJTYPE objType , C.PRICE price");
		sb.append("		, C.WORDEXP introduce ,C.ICON icon ,C.IMAGE img");
		sb.append("		FROM PRD_PCODE A,PRD_CATALOG_ITEM B,PRD_SALESPKG_KNOW C");
		sb.append(" 	WHERE 1 = 1 AND B.KNOWID = C.KNOWID AND C.OBJID = A.PID");
		sb.append("		AND (C.CITY LIKE ? OR C.CITY = ?)");
		sb.append("		AND B.CATALOGID = ?");
		sb.append("		AND C.OBJTYPE = ?");
		sb.append("		AND A.PERMARK in (");
		for(int i = 0;i < permarks.length ;i++){
			params.add(permarks[i]);
			sb.append("?");
			if(i != permarks.length -1){
				sb.append(",");
			}
		}
		sb.append("	)");
		sb.append("		ORDER BY B.PRI");
		List<QueProductListResp> datas = getDAO().find(sb.toString(), QueProductListResp.class, params.toArray());
		if(datas != null && datas.size() > 0){
			for(QueProductListResp child:datas){
				child.setUnitName("月");
				child.setMaxCycle(12);
				child.setMinCycle(1);
				if(loginInfo.getCity().equalsIgnoreCase("fs")){
					child.setDefaultCycle(0);
				}else{
					child.setDefaultCycle(1);
				}
				
			}
			resp.addAll(datas);
		}
		if(StringUtils.isNotBlank(req.getCustid())){
			QueUserPrdBossReq req2Boss = getQueUserPrdReq2Boss(req.getCustid());
			String serverCode = "";
			if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
				serverCode = BizConstant.ServerCityBossInterface.QUE_USERPRDINFO;
			}else{
				serverCode = BizConstant.BossInterfaceService.QUE_USERPRDINFO;
			}
			String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
					serverCode, req2Boss,
	                loginInfo);
			if(StringUtils.isNotBlank(bossRespOutput)){
			 QueUserPrdBossResp bossResp = (QueUserPrdBossResp) BeanUtil
		                .jsonToObject(bossRespOutput, QueUserPrdBossResp.class);
			 for(QueProductListResp product:resp){
				 for(UserPrdsBO prd:bossResp.getProds()){
					 if(prd.getPid().equals(Long.toString(product.getObjId()))){
						 product.setOrdered(true);
						 break;
					 }
				 }
			 }
			}
		}
		return returnInfo;
	}
	
	 private QueUserPrdBossReq getQueUserPrdReq2Boss(String custid) {
	        QueUserPrdBossReq queUserPrdBossReq = new QueUserPrdBossReq();
	        queUserPrdBossReq.setCustid(custid);
	        queUserPrdBossReq.setPermark("");
	        queUserPrdBossReq.setPstatus("");
	        queUserPrdBossReq.setServid("");

	        return queUserPrdBossReq;
	    }
	
	
	public ReturnInfo searchGoods(QueSearchReq req,ArrayList<QueSearchResp> resps) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		
		Rule rule = ruleService.getRule("*", "UPDATE_CITY");
		if(rule != null && StringUtils.isNotBlank(rule.getValue()) && rule.getValue().contains(loginInfo.getCity())){
			List<QueSearchResp> goodsDatas = getSearchGoodsList(req.getCondition(),loginInfo);
			if(goodsDatas != null && goodsDatas.size()>0){
				resps.addAll(goodsDatas);
			}
		}else{
			if(StringUtils.isNotBlank(req.getCondition())){
				List<QueSearchResp> pkgDatas = getSearchPkgList(req.getCondition(),loginInfo);
				List<QueSearchResp> prdDatas = getSearchPrdList(req.getCondition(),loginInfo);
				if(pkgDatas != null){
					resps.addAll(pkgDatas);
				}
				if(prdDatas != null){
					resps.addAll(prdDatas);
				}
				
			}
			if(resps != null && resps.size() > 0){
				for(QueSearchResp resp:resps){
					if(resp.getObjType().equals(BizConstant.PrdSalespkgKnowObjtype.PRD)){
						resp.setTypeName(BizConstant.PrdSalespkgKnowObjtypeName.PRD);
					}else if(resp.getObjType().equals(BizConstant.PrdSalespkgKnowObjtype.SALESPKG)){
						resp.setTypeName(BizConstant.PrdSalespkgKnowObjtypeName.SALESPKG);
					}else if(resp.getObjType().equals(BizConstant.PrdSalespkgKnowObjtype.SALESPKG)){
						resp.setTypeName(BizConstant.PrdSalespkgKnowObjtypeName.SALESPKG);
					}else if(resp.getObjType().equals(BizConstant.PrdSalespkgKnowObjtype.GOODS)){
						resp.setTypeName(BizConstant.PrdSalespkgKnowObjtypeName.GOODS);
					}else if(resp.getObjType().equals(BizConstant.PrdSalespkgKnowObjtype.GOODS_TYPE)){
						resp.setTypeName(BizConstant.PrdSalespkgKnowObjtypeName.GOODS_TYPE);
					}
				}
			}
		}
		return returnInfo;
	}
	
	
	private List<QueSearchResp> getSearchGoodsList(String content,LoginInfo loginInfo) throws Exception{
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DISTINCT (C.KNOWID) knowId,B.CATALOGID catalogId,");
		sb.append("	C.KNOWNAME knowName,C.OBJTYPE objType ");
		sb.append("	FROM PRD_SALES A,PRD_CATALOG_ITEM B,PRD_SALESPKG_KNOW C,PRD_CATALOG D ");
		sb.append("	WHERE 1 = 1 ");
		sb.append("	AND D.CATALOGID = B.CATALOGID");
		sb.append("	AND D.CATALOGSTATUS = ?");
		sb.append("	AND B.KNOWID = C.KNOWID");
		sb.append("	AND A.CITY LIKE ? ");
		sb.append("	AND C.CITY = ? ");
		sb.append("	AND C.KNOWNAME LIKE ? ");
		sb.append("	AND C.OBJTYPE in (?,?)");
		sb.append("	ORDER BY B.PRI");
		params.add("Y");
		params.add("%"+loginInfo.getCity()+"%");
		params.add(loginInfo.getCity());
		params.add("%"+content+"%");
		params.add(BizConstant.PrdSalespkgKnowObjtype.GOODS);
		params.add(BizConstant.PrdSalespkgKnowObjtype.GOODS_TYPE);
		List<QueSearchResp> datas = getDAO().find(sb.toString(), QueSearchResp.class, params.toArray());
		return datas;
	}
	
	
	/**
	 * 
	 * @param content
	 * @param loginInfo
	 * @return
	 * @throws Exception
	 */
	private List<QueSearchResp> getSearchPkgList(String content,LoginInfo loginInfo) throws Exception{
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DISTINCT (C.KNOWID) knowId,B.CATALOGID catalogId,");
		sb.append("	C.KNOWNAME knowName,C.OBJTYPE objType ");
		sb.append("	FROM PRD_CATALOG A, PRD_CATALOG_ITEM B,PRD_SALESPKG_KNOW C");
		sb.append("	WHERE 1 = 1 ");
		sb.append("	AND A.CATALOGID = B.CATALOGID");
		sb.append("	AND A.CATALOGSTATUS = ?");
		sb.append("	AND B.KNOWID = C.KNOWID");
		sb.append("	AND C.CITY = ? ");
		sb.append("	AND C.KNOWNAME LIKE ? ");
		sb.append("	AND C.OBJTYPE = ?");
		sb.append("	ORDER BY B.PRI");
		params.add("Y");
		params.add(loginInfo.getCity());
		params.add("%"+content+"%");
		params.add(1);
		List<QueSearchResp> datas = getDAO().find(sb.toString(), QueSearchResp.class, params.toArray());
		return datas;
	}
	
	private List<QueSearchResp> getSearchPrdList(String content,LoginInfo loginInfo) throws Exception{
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DISTINCT (C.KNOWID)knowId,A.PERMARK permark,B.CATALOGID catalogId,");
		sb.append("		C.KNOWNAME knowName,C.OBJTYPE objType");
		sb.append("		FROM prd_pcode A,prd_catalog_item B, prd_salespkg_know C,prd_catalog D");
		sb.append("		WHERE 1 = 1 ");
		sb.append("		AND B.KNOWID = C.KNOWID");
		sb.append("		AND B.CATALOGID = D.CATALOGID  ");
		sb.append("		AND C.OBJID = A.PID  ");
		sb.append("		AND B.CITY LIKE ? ");
		sb.append("		AND C.CITY = ? ");
		sb.append("		AND C.KNOWNAME LIKE ? ");
		sb.append("		AND D.CATALOGSTATUS = ? ");
		sb.append("		AND C.OBJTYPE = ?");
		sb.append("		 ORDER BY B.PRI ");
		params.add("%"+loginInfo.getCity()+"%");
		params.add(loginInfo.getCity());
		params.add("%"+content+"%");
		params.add("Y");
		params.add(0);
		List<QueSearchResp> datas = getDAO().find(sb.toString(), QueSearchResp.class, params.toArray());
		return datas;
	}

	public ReturnInfo queGoodsCatalog(ArrayList<QueGoodsCatalogResp> resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();

		LoginInfo loginInfo = getLoginInfo();

		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT DISTINCT d.KNOWID FROM ");
		sb.append("		prd_salespkg_know d,prd_sales C");
		sb.append("		WHERE D.OBJID = C.SALESID");
		sb.append("		AND  ( d.OBJTYPE = '");
		sb.append(BizConstant.PrdSalespkgKnowObjtype.GOODS+"'");
		sb.append("	or d.OBJTYPE = '");
		sb.append(BizConstant.PrdSalespkgKnowObjtype.GOODS_TYPE +"'");
		sb.append(" or d.OBJTYPE = '");
		sb.append(BizConstant.PrdSalespkgKnowObjtype.SALESPKG+"')");
		sb.append("		AND d.city = '");
		sb.append(loginInfo.getCity());
		sb.append("'");
		sb.append("		UNION");
		sb.append("		SELECT DISTINCT d1.KNOWID FROM prd_salespkg b,prd_salespkg_know d1  WHERE d1.`OBJID` = b.salespkgid");
		sb.append("		AND d1.city = '");
		sb.append(loginInfo.getCity());
		sb.append("'");
		String sql = sb.toString();
		List params = getDAO().findObjectList(sql);
		if(params != null && params.size() > 0){
			sql = creatSecondSql(params.size());
			params = getDAO().findObjectList(sql, params.toArray());
			if(params != null && params.size() > 0){
				sql = creatCatalogSql(params.size());
				params.add("Y");
				params.add(loginInfo.getCity());
				params.add("0");
				List<QueGoodsCatalogResp> datas = getDAO().find(sql, QueGoodsCatalogResp.class, params.toArray());
				if(datas != null && datas.size() > 0 ){
					List<QueGoodsCatalogResp> retSet = new ArrayList<QueGoodsCatalogResp>();
					List<QueGoodsCatalogResp> opSet = new ArrayList<QueGoodsCatalogResp>();
					for(QueGoodsCatalogResp child:datas){
						CatalogCondtion condtion = new CatalogCondtion();
						condtion.setCatalogid(child.getCatalogId());
						List<CatalogCondtion> condtionList = getDAO().find(condtion);
						if (BeanUtil.isListNull(condtionList)) {
							// 没有限制条件的目录
							retSet.add(child);
						}else{
							for (CatalogCondtion obj : condtionList) {
								if (StringUtils.isBlank(obj.getContiontype())) {
									// 没有限制条件的目录
									retSet.add(child);
								} else if (BizConstant.CATALOG_CONDITION_TYPE.OPERATOR.equals(obj.getContiontype())) {
									// 指定操作员的目录
									if (obj.getContionvalue().equals(loginInfo.getOperid())) {
										retSet.add(child);
									}
								} else if (BizConstant.CATALOG_CONDITION_TYPE.DEPT.equals(obj.getContiontype())) {
									// 指定部门的目录
									if (obj.getContionvalue().equals(loginInfo.getDeptid())) {
										retSet.add(child);
									}
								}else if(BizConstant.CATALOG_CONDITION_TYPE.OPCODE.equals(obj.getContiontype())){
									if(obj.getContionvalue().equalsIgnoreCase("BIZ_PRD_ORDER")){
										opSet.add(child);
									}
								}
							}
						}
					}
					if(!opSet.isEmpty()){
						retSet.clear();
						retSet.addAll(opSet);
					}
					resp.addAll(retSet);
				}
			}
		}
		return returnInfo;
	}



	public ReturnInfo queGoodsSceneCatalog(ArrayList<QueGoodsCatalogResp> resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = getLoginInfo();
		List<Object> params = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT A.CATALOGID CATALOGID ,A.CATALOGNAME CATALOGNAME");
		sb.append("		FROM PRD_CATALOG A WHERE 1 = 1");
		sb.append(" 	AND A.CATALOGSTATUS = ? ");
		sb.append("		AND A.CITY = ? ");
		sb.append( " AND A.CTYPE = ? ");
		sb.append("	ORDER BY A.PRI");
		params.add("Y");
		params.add(loginInfo.getCity());
		params.add("1");
		List<QueGoodsCatalogResp> datas = getDAO().find(sb.toString(), QueGoodsCatalogResp.class, params.toArray());
		resp.addAll(datas);
		return returnInfo;
	}
	
	private String creatSecondSql(int length){
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT DISTINCT B.CATALOGID");
		sb.append("		FROM PRD_CATALOG_ITEM B");
		sb.append("		WHERE B.KNOWID IN(");
		for(int i = 0;i < length ;i++){
			sb.append("?");
			if(i != length -1){
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}
	
	private String creatCatalogSql(int length){
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT A.CATALOGID CATALOGID ,A.CATALOGNAME CATALOGNAME");
		sb.append("		FROM PRD_CATALOG A WHERE 1 = 1");
		sb.append("		AND  A.CATALOGID IN (");
		for(int i = 0;i < length ;i++){
			sb.append("?");
			if(i != length -1){
				sb.append(",");
			}
		}
		sb.append("	)");
		sb.append(" 	AND A.CATALOGSTATUS = ? ");
		sb.append("		AND A.CITY = ? ");
		sb.append( " AND A.CTYPE = ? ");
		sb.append("	ORDER BY A.PRI");
		return sb.toString();
	}
	
	
	
	public ReturnInfo queGoodsList(QueGoodsListReq req,ArrayList<QuePkgListResp> resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		
		List params = new ArrayList();
		
		StringBuffer sb = new StringBuffer();
		sb.append("	SELECT  DISTINCT (D.KNOWID) KNOWID,A.CATALOGID CATALOGID,A.CATALOGNAME CATALOGNAME,");
		sb.append("		D.KNOWNAME,D.IMAGE,D.PRICE,D.OBJID OBJID,D.OBJTYPE OBJTYPE,D.ICON ICON,");
		sb.append("		D.WORDEXP INTRODUCE,D.BRIEF BRIEF,E.PERMARK perMark,C.PID pid");
		sb.append("		FROM PRD_CATALOG A,PRD_CATALOG_ITEM B,PRD_SALES C,PRD_SALESPKG_KNOW D ,PRD_PCODE E");
		sb.append("		WHERE 1 = 1 ");
		sb.append("		AND C.PID = E.PID");
		sb.append("		AND A.CATALOGID = B.CATALOGID");
		sb.append("		AND B.KNOWID = D.KNOWID");
		sb.append("		AND C.SALESID = D.OBJID");
		sb.append("		AND A.CATALOGSTATUS = ?");
		sb.append("		AND A.CITY LIKE ?");
		params.add("Y");
		params.add("%"+loginInfo.getCity()+"%");
		if (StringUtils.isNotBlank(req.getCatalogId())) {
			sb.append("		AND A.CATALOGID = ?");
			params.add(req.getCatalogId());
		}
		if (StringUtils.isNotBlank(req.getSalespkgcode())) {
			sb.append("		AND C.salescode = ?");
			params.add(req.getSalespkgcode());
		}
		sb.append("		");
		sb.append("		UNION ");
		sb.append("		(SELECT  (D1.KNOWID) KNOWID,");
		sb.append("		 A1.CATALOGID CATALOGID,");
		sb.append("		A1.CATALOGNAME CATALOGNAME,");
		sb.append("		D1.KNOWNAME,D1.IMAGE,D1.PRICE,D1.OBJID OBJID,");
		sb.append("		D1.OBJTYPE OBJTYPE,D1.ICON ICON,D1.WORDEXP INTRODUCE,");
		sb.append("		D1.BRIEF BRIEF,'' perMark,F1.SALESPKGID pid");
		sb.append("		FROM");
		sb.append("		PRD_CATALOG A1,PRD_CATALOG_ITEM B1,PRD_SALESPKG_KNOW D1,prd_salespkg F1");
		sb.append("		WHERE A1.CATALOGID = B1.CATALOGID");
		sb.append("		AND B1.KNOWID = D1.KNOWID");
		sb.append("		AND F1.SALESPKGID = D1.OBJID");
		sb.append("		AND A1.CATALOGSTATUS = ?");
		sb.append("		AND A1.CITY LIKE ?");
		params.add("Y");
		params.add("%"+loginInfo.getCity()+"%");
		if (StringUtils.isNotBlank(req.getCatalogId())) {
			sb.append("		AND A1.CATALOGID = ?");
			params.add(req.getCatalogId());
		}
		if (StringUtils.isNotBlank(req.getSalespkgcode())) {
			sb.append("		AND F1.salespkgcode = ?");
			params.add(req.getSalespkgcode());
		}
		sb.append("		)");
		List<QuePkgListResp> datas = getDAO().find(sb.toString(), QuePkgListResp.class, params.toArray());
		if(datas != null){
			datas = new ArrayList<QuePkgListResp>(new HashSet<QuePkgListResp>(datas));
		}
		
		for(QuePkgListResp data:datas){
			if(data.getObjType().equals(BizConstant.PrdSalespkgKnowObjtype.GOODS)||
					data.getObjType().equals(BizConstant.PrdSalespkgKnowObjtype.GOODS_TYPE)){
				data.setUnitName("月");
			}else{
				Cycle cycle = interPrdService.gettPkgUnit(Long.parseLong(data.getObjId()));
				if(cycle != null){
					data.setUnitName(cycle.getUnitName());
				}else{
					data.setUnitName("周期");
				}
			}
			
		}
		resp.addAll(datas);
		if(StringUtils.isNotBlank(req.getCustid())){
			QueUserPrdBossReq req2Boss = getQueUserPrdReq2Boss(req.getCustid());
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
				 for(QuePkgListResp product:resp){
					 for(UserPrdsBO prd:bossResp.getProds()){
						 if(prd.getPid().equals(product.getPid())){
							 product.setOrdered(true);
							 break;
						 }
					 }
				 }
			}
		}
		return returnInfo;
	}
	
}
