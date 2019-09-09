package com.maywide.biz.inter.service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.*;
import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.applyinstall.ExdevList;
import com.maywide.biz.inter.pojo.applyinstall.KnowProducts;
import com.maywide.biz.inter.pojo.applyinstall.SelectProduct;
import com.maywide.biz.inter.pojo.bizInstallCommit.BizInstallCommit2BossReq;
import com.maywide.biz.inter.pojo.bizInstallCommit.BizInstallCommit2BossResp;
import com.maywide.biz.inter.pojo.bizInstallCommit.BizInstallCommitReq;
import com.maywide.biz.inter.pojo.bizInstallCommit.BizInstallCommitResp;
import com.maywide.biz.inter.pojo.bizLockCmccAcctno.BizLockCmccAcctnoReq;
import com.maywide.biz.inter.pojo.bizpreprocess.SalespkgKnowObjInfoBO;
import com.maywide.biz.inter.pojo.queCmccAcctno.QueAcctnoBossResp;
import com.maywide.biz.inter.pojo.queCmccAcctno.QueCmccAcctnoReq;
import com.maywide.biz.inter.pojo.queCmccAcctno.QueCmccAcctnoResp;
import com.maywide.biz.inter.pojo.queDevPrdinfo.QueDevPrdinfoBossResp;
import com.maywide.biz.inter.pojo.queDevPrdinfo.QueDevPrdinfoReq;
import com.maywide.biz.inter.pojo.queDevPrdinfo.QueDevPrdinfoResp;
import com.maywide.biz.inter.pojo.queSyncPercomb.*;
import com.maywide.biz.inter.pojo.quereshouse.QueResHouseBossReq;
import com.maywide.biz.inter.pojo.quereshouse.QueResHouseBossResp;
import com.maywide.biz.inter.pojo.quereshouse.QueResHouseInterReq;
import com.maywide.biz.inter.pojo.quereshouse.QueResHouseInterResp;
import com.maywide.biz.inter.pojo.sycnChlInstall.*;
import com.maywide.biz.inter.pojo.syncApplyInstall.SyncApplyInstallReq;
import com.maywide.biz.inter.pojo.syncApplyInstall.SyncApplyInstallResp;
import com.maywide.biz.market.entity.*;
import com.maywide.biz.pay.unify.manager.ParamsManager;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;
import com.maywide.core.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class InterSyncApplyService extends CommonService {

	@Autowired
	private PubService pubService;
	
	@Autowired
	private RuleService ruleService;

	@Autowired
	private UsingProductService usingProductService;

	@Autowired
	private InterApplyService interApplyService;

	private final String sourceGcode = "SYS_DEV_UPROP";

	private final String servtypeGcode = "SYS_SERV_TYPE";

	private final String vodInputWayGcode = "SYS_VOD_INPUTWAY";

	private final String cmInputWayGcode = "SYS_CM_INPUTWAY";

	private final String authmodeGcode = "BIZ_AUTHMODE";

	private final String ipModeGcode = "BIZ_IPMODE";

	private final String cmTypeGcode = "SERV_CM_TYPE";

	private final String feekindGcode = "SYS_CHARGETYPE";

	private final String feeWayGcode = "SYS_FEEWAY";

	private final String ottRtoTwoSwitch = "OTTR_TWO_SWITCH";

	private final String installTypeGcode = "BIZ_INSTALLTYPE";
	
	private final String sysUecodeGcode = "SYS_UECODE";
	
	protected final String suffixGcode = "CMACCTNO_LAST";
	
	private final String digitPermark = "1";
	
	private final String cmPermark = "2";
	
	private final String vodPermark = "3";
	
	private final String CITY_ORDER_GOODS_RULE = "CITY_ORDER_GOODS";

	private final String CITY_ISASH_PLACING_RULE= "CITY_ISASH_PLACING";
	
	/**
	 * 新设备开户
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo quePercombInfo(QueSyncPercombReq req, QueSyncPercombResp resp) throws Exception {
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
		List<PercombDevParam> datas = getPercombDevInfo(loginInfo, req.getOpmode(), loginInfo.getCity()
				,req.getCustid(),req.getBizorderid());
		resp.setOpmode(req.getOpmode());
		resp.setDatas(datas);
		setCommonInfo2Resp(loginInfo.getCity(), resp);
		addCityOpmodeRule(loginInfo.getCity(),req.getOpmode(),resp);
		controllerAcctno(loginInfo.getCity(),resp);
		//录入宽带类型是移动，电信，联调是否需要输入账号密码规则
		//inputIsAshPlacing(loginInfo.getCity(),resp);
		return returnInfo;
	}

	/*private void inputIsAshPlacing(String city,QueSyncPercombResp resp) throws Exception{
		Rule rule = ruleService.getRule(city,CITY_ISASH_PLACING_RULE);
		if(rule == null){
			rule = ruleService.getRule("*", CITY_ISASH_PLACING_RULE);
		}
		if(rule != null && StringUtils.isNotBlank(rule.getValue())){
			resp.setIsAshPlacing(rule.getValue().trim());
		}

	}*/
	
	private void controllerAcctno(String city,QueSyncPercombResp resp) throws Exception{
		Rule rule = ruleService.getRule(city,"ACCTNO_VIEW");
		if(rule == null){
			rule = ruleService.getRule("*","ACCTNO_VIEW");
		}
		if(rule != null){
			resp.setAcctnoShow("Y");
		}else{
			resp.setAcctnoShow("N");
		}
	}
	
	
	/**
	 * 修改规则为没有配置则默认要全省订购,有的话则不需要订购(有就不需要，没有就需要的原则)
	 * @param city
	 * @param opmode
	 * @param resp
	 * @throws Exception
	 */
	protected void addCityOpmodeRule(String city,String opmode,QueSyncPercombResp resp) throws Exception{
		Rule rule = ruleService.getRule(city,CITY_ORDER_GOODS_RULE);
		if(rule == null){
			rule = ruleService.getRule("*", CITY_ORDER_GOODS_RULE);
		}
		if(rule != null && StringUtils.isNotBlank(rule.getValue())){
			String[] opmodes = rule.getValue().split(",");
			for(String value:opmodes){
				if(value.equals(opmode)){
					resp.setOrdergoods("N");
				}
			}
		}
	}

	/**
	 * 获取业务组合下的设备信息(包含了Lable)
	 * 
	 * @param percomb
	 * @throws Exception
	 */
	protected List<PercombDevParam> getPercombDevInfo(LoginInfo loginInfo, String opmodes, 
			String city,String custid,String bizOrderid) throws Exception {
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT a.RECID recid,a.PERCOMB percomb,b.combname combname,b.permarks permarks");
		sb.append("		FROM  SYS_PERCOMB_PARAM a ,biz_percomb_cfg b");
		sb.append("		WHERE a.PERCOMB = b.percomb");
		sb.append("		and b.OPMODES LIKE ? ");
		params.add("%" + opmodes + "%");
		sb.append("		AND a.CITY = ? ");
		params.add(city);
		sb.append("		ORDER BY a.SORT");
		List<PercombDevParam> resultList = getDAO().find(sb.toString(), PercombDevParam.class, params.toArray());
		if (resultList != null && !resultList.isEmpty()) {
			for (int i = 0; i < resultList.size(); i++) {
				PercombDevParam devParam = resultList.get(i);
				List<PercombDevInfo> devInfos = getDevInfo(loginInfo, devParam.getRecid(),devParam.getPercomb());
				devParam.setDevinfoList(devInfos);
				if(devParam.getPermarks().contains(digitPermark)){
					devParam.setDigitParams(getDigitParams(devParam.getRecid()));	
				}
				if(devParam.getPermarks().contains(vodPermark)){
					devParam.setVodParams(getVodParams(devParam.getRecid()));	
				}
				if(devParam.getPermarks().contains(cmPermark)){
					devParam.setCmParams(getCmParams(devParam.getRecid()));
					fillCMParam(devParam.getCmParams(),custid,bizOrderid,loginInfo);
				}
				List<PercombOnceParam> onceParams = checkExtraInfo(devParam.getRecid());
				devParam.setOnceParams(onceParams);
			}
		} 
		return resultList;
	}
	
	protected void fillCMParam(List<PercombCmParam> params,String custid,
			String bizOrderid,LoginInfo loginInfo)throws Exception{}
	
	
	

	/**
	 * 获取设备信息
	 * 
	 * @param percomb
	 * @throws Exception
	 */
	protected List<PercombDevInfo> getDevInfo(LoginInfo loginInfo, Long recid,String percomb) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT a.LABLE devLable ,a.PSUBCLASS psubClass,a.USEPROPS userprops,a.ISMUST isMust ");
		sb.append("		FROM sys_dev_param a ");
		sb.append("		WHERE a.PP_RECID = ?");
		sb.append("		 ORDER BY a.SORT");
		List<PercombDevInfo> devList = getDAO().find(sb.toString(), PercombDevInfo.class, recid);
		if (devList != null || !devList.isEmpty()) {
			for (int i = 0; i < devList.size(); i++) {
				PercombDevInfo info = devList.get(i);
				List<PrvSysparam> sources = getParams(sourceGcode, info.getUserprops());// 获取设备来源
				List<DevProductInfo> products = getDevInfoProduct(loginInfo.getAreaid(), info.getPsubClass(),percomb);
				info.setSourceList(sources);
				info.setProductList(products);
			}
		}
		return devList;
	}

	/**
	 * 获取设备对应的产品
	 * 
	 * @throws Exception
	 */
	private List<DevProductInfo> getDevInfoProduct(Long areaid, String psubclass,String percomb) throws Exception {
		if(StringUtils.isBlank(psubclass)) return null;
		PrvSysparam sqlParam = new PrvSysparam();
		sqlParam.setMcode("@DYNAMIC_LEVEL");
		sqlParam.setGcode(psubclass);
		List<PrvSysparam> prvparams = getDAO().find(sqlParam);
		if(prvparams == null || prvparams.isEmpty()){
			return null;
		}
		sqlParam = prvparams.get(0);
		String sql = sqlParam.getData();
		CheckUtils.checkNull(sql, "产品设备参数配置为空,请联系管理员");
		List<DevProductInfo> productInfos = getDAO().find(sql, DevProductInfo.class, areaid);
		handlerDevRule(percomb,psubclass,productInfos);
		return productInfos;
	}
	
	/**
	 * 处理设备产品规则
	 * @param percomb
	 * @param psubclass
	 * @param productInfos
	 * @throws Exception
	 */
	protected void handlerDevRule(String percomb,String psubclass,List<DevProductInfo> productInfos) throws Exception{
		if(psubclass.equals("PRD_PCODESTBLIST") || psubclass.equals("PRD_PCODESMLIST")){
			String kind = psubclass.equals("PRD_PCODESTBLIST")?"2":"1";
			String sql = "SELECT a.DEVPRDS FROM biz_percomb_dev_cfg a WHERE a.KIND = ? AND a.PERCOMB = ?";
			List params = new ArrayList();
			params.add(kind);
			params.add(percomb);
			List<DevProductInfo> devprods = getDAO().find(sql, DevProductInfo.class, params.toArray());
			if(devprods != null && !devprods.isEmpty()){
				DevProductInfo result = devprods.get(0);
				if(StringUtils.isBlank(result.getDevprds()) || result.getDevprds().equals("*")) return;
				List<DevProductInfo> tmp = new ArrayList<DevProductInfo>();
				String[] prods = result.getDevprds().split(",");
				for(String str:prods){
					for(DevProductInfo info:productInfos){
						if(info.getPid().equals(str)){
							tmp.add(info);
							break;
						}
					}
				}
				if(!tmp.isEmpty()){
					productInfos.clear();
					productInfos.addAll(tmp);
				}
			}
		}
	}
	

	/**
	 * 获取数字业务参数
	 * 
	 * @throws Exception
	 */
	private List<PercombDigitParam> getDigitParams(Long ppRecid) throws Exception {
		String sql = "SELECT a.SERVTYPE,a.ISCABLE,a.PLATFORM FROM sys_digit_param a WHERE a.PP_RECID = ?";
		List<PercombDigitParam> digitParams = getDAO().find(sql, PercombDigitParam.class, ppRecid);
		if (digitParams != null || !digitParams.isEmpty()) {
			for (PercombDigitParam digit : digitParams) {
				List<PrvSysparam> servtypeList = getParams(servtypeGcode, digit.getServtype());
				digit.setServtypeList(servtypeList);
				List<PrvSysparam> platformList = getParams(sysUecodeGcode, digit.getPlatform());
				digit.setPlatformList(platformList);
			}
		}
		return digitParams;
	}

	/**
	 * 获取互动业务参数
	 * 
	 * @throws Exception
	 */
	private List<PercombVodParam> getVodParams(Long ppRecid) throws Exception {
		String sql = "SELECT a.VODINPUTWAY vodinputway,a.ISINMAC isinmac,a.CREDITLIMIT creditlimit FROM sys_vod_param a WHERE a.PP_RECID = ?";
		List<PercombVodParam> vodParams = getDAO().find(sql, PercombVodParam.class, ppRecid);
		if (vodParams != null && !vodParams.isEmpty()) {
			for (int i = 0; i < vodParams.size(); i++) {
				PercombVodParam vodParam = vodParams.get(i);
				List<PrvSysparam> inputWayList = getParams(vodInputWayGcode, vodParam.getVodinputway());
				vodParam.setInputWayList(inputWayList);
			}
		}
		return vodParams;
	}

	/**
	 * 获取宽带业务参数
	 * 
	 * @throws Exception
	 */
	private List<PercombCmParam> getCmParams(Long recid) throws Exception {
		String sql = "SELECT a.AUTHMODE authmode,a.CMINPUTWAY cminputway,a.CMTYPE cmtype,a.IPMODE ipmode,a.CMLAST cmLast FROM SYS_CM_PARAM a WHERE a.PP_RECID = ?";
		List<PercombCmParam> cmParams = getDAO().find(sql, PercombCmParam.class, recid);
		if (cmParams != null && !cmParams.isEmpty()) {
			for (int i = 0; i < cmParams.size(); i++) {
				PercombCmParam param = cmParams.get(i);
				List<PrvSysparam> inputWayList = getParams(cmInputWayGcode, param.getCminputway());
				param.setInputWayList(inputWayList);
				List<PrvSysparam> authmodeList = getParams(authmodeGcode, param.getAuthmode());
				param.setAuthmodeList(authmodeList);
				List<PrvSysparam> ipModes = getParams(ipModeGcode, param.getIpmode());
				param.setIpmodeList(ipModes);
				List<PrvSysparam> cmTypeList = getParams(cmTypeGcode, param.getCmtype());
				param.setTypeList(cmTypeList);
				if(StringUtils.isNotBlank(param.getCmLast())){
					param.setSuffixList(getDLCmsuffix(param.getCmLast()));
				}
			}
		}
		return cmParams;
	}

	/**
	 *  获取大连网格开户宽带后缀
	 * @throws Exception 
	 */
	private List<String> getDLCmsuffix(String suffix) throws Exception{
		String[] suffixStrs = suffix.split(",");
		return Arrays.asList(suffixStrs);
	}



	protected void setCommonInfo2Resp(String city, QueSyncPercombResp resp) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT a.FEEKIND feekind,a.PAYWAY payway,a.INSTALLTYPE installType");
		sb.append("		FROM sys_common_param a");
		sb.append("		WHERE  (a.CITY = ? OR a.CITY = ? )");
		sb.append("		ORDER BY a.SORT ");
		sb.append("		LIMIT 1");
		List<PercombCommonInfo> datas = getDAO().find(sb.toString(), PercombCommonInfo.class, city, "*");
		if (datas != null && !datas.isEmpty()) {
			PercombCommonInfo info = datas.get(0);
			List<PrvSysparam> charges = getParams(feekindGcode, info.getFeekind());
			List<PrvSysparam> paymentList = getParams(feeWayGcode, info.getPayway());
			List<PrvSysparam> installTypeList = getParams(installTypeGcode, info.getInstallType());
			resp.setCharges(charges);
			resp.setInstallationList(installTypeList);
			resp.setPaymentList(paymentList);
		}
	}

	/**
	 * 检查是否含有额外信息
	 * 
	 * @throws Exception
	 */
	protected List<PercombOnceParam> checkExtraInfo(Long recid) throws Exception {
		String sql = "SELECT a.PCODE pcode,a.PNAME pName,a.SUM sum FROM sys_oncefee_param a WHERE a.PP_RECID =  ?";
		List<PercombOnceParam> onceParams = getDAO().find(sql, PercombOnceParam.class, recid);
		if (onceParams != null && !onceParams.isEmpty()) {
			for (int i = 0; i < onceParams.size(); i++) {
				PercombOnceParam onceParam = onceParams.get(i);
				String[] sums = onceParam.getSum().split(",");
				onceParam.setSums(Arrays.asList(sums));
			}
		}
		return onceParams;
	}

	/**
	 * 实时开户接口
	 * 
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo syncApplyInstall(SyncApplyInstallReq req, SyncApplyInstallResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req, "请求对象不能为空");
		CheckUtils.checkEmpty(req.getBizorderid(), "业务订单号不能为空");
		CheckUtils.checkNull(req.getInstallparams(), "报装信息参数不能为空");
		if (req.getUserparams() == null || req.getUserparams().isEmpty()) {
			CheckUtils.checkNull(null, "用户参数信息不能为空");
		}

//		String city=loginInfo.getCity();
//		if (usingProductService.queBizRule(city)){
//			CheckUtils.checkNull(req.getEjectparams(), "退订产品参数不能为空");
//		}

		//临时  这样做   几个参数要放到 installParam  而不是外层  这样app不用改
//		setInstallparams(req);
		
		InstallUserParam userParams = req.getUserparams().get(0);
		BizPortalOrder bizPortalOrder = register4PortalOrder(Long.parseLong(req.getBizorderid()));
		CustOrder custOrder = register4Custoer(req, loginInfo,bizPortalOrder);
		ApplyInstall applyInstall = register4ApplyInstall(custOrder, req, loginInfo);
		List<ApplyProduct> applyProducts = register4ApplyPrds(req, loginInfo, custOrder);
		if (req.getBankparams() != null && req.getBankparams().size() > 0) {
			ApplyBank applyBank = register4ApplyBank(req.getBankparams().get(0), loginInfo, custOrder.getId(),
					userParams.getPayway());
		}
		register4SelectPrd(req.getSelectProducts(), loginInfo, custOrder.getId());

		SyncChlInstallReq req2Boss = getSyncChinstallReq(req, loginInfo, applyProducts, custOrder.getId());
		String resultPutInfo = getBossHttpInfOutput(req.getBizorderid(), BossInterfaceService.BIZ_CHL_INSTALL, req2Boss,
				loginInfo);
		resp.setCustOrderid(req.getBizorderid());
		hanlderResp(resultPutInfo, resp, bizPortalOrder,custOrder);
		
		//ywp 开户成功后用新地址去调用boss接口查询是否是非标准地址，isstd=N即非标准地址
		/*StringBuffer newAddrStr = new StringBuffer();
		SyncInstallAddr newAddr = req2Boss.getAddrparam();
		//开始拼接入参
		QueResHouseInterReq req1 = new QueResHouseInterReq();
		req1.setCity(req2Boss.getCity());
		req1.setAreaid(req2Boss.getAreaid()+"");
		String whladdr = req.getWhladdr();
		req1.setAddr(whladdr);
		req1.setHouseid(newAddr.getHouseid());
		req1.setIsstd("N");
		req1.setBizorderid(getNewBizOrderid());
		QueResHouseInterResp resp1 = new QueResHouseInterResp();
		callBossInf4queResHouse(req1,resp1,loginInfo);
		if(null!=resp1){
			String tableName = "biz_grid_house_"+req2Boss.getCity().toLowerCase();
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO biz_grid_house_cz (houseid, addrid, status, patchid, areaid, netstruct, whladdr, whgridcode, ywgridcode, gridid) ");
			sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			List param = new ArrayList();
			String totalRecords = resp1.getTotalRecords();
			if(null!=totalRecords&&Integer.parseInt(totalRecords)>0){
				List<ResHousesBO> houses = resp1.getHouses();
				for(ResHousesBO item:houses){
					param.clear();
					param.add(item.getHouseid());
					param.add(item.getHouseno());
					param.add(item.getStatus());
					param.add(item.getPatchid());
					param.add(item.getAreaid());
					param.add("0");//netstruct先用0测试
					param.add(item.getWhladdr());
					param.add("A2");//whgridcode先用A2测试
					param.add("CZ");//ywgridcode先用CZ测试
					param.add("-1");
					getDAO().executeSql(sql.toString(), param.toArray());
				}
			}
		}*/
		return returnInfo;
	}
	private String getNewBizOrderid() throws Exception {
//		return getDAO().getSequence("SEQ_BIZ_CUSTORDER_ID").toString();
		return getBizorderid();
	}

	private void callBossInf4queResHouse(QueResHouseInterReq req, QueResHouseInterResp resp, LoginInfo loginInfo)
			throws Exception {

		// 将请求做一下转换，并赋默认值
		QueResHouseBossReq req2Boss = getQueResHouseInterReq2Boss(req);

		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.QUE_RESHOUSE,
				req2Boss, loginInfo);

		copyBossResp2InterResp4queResHouse(resp, bossRespOutput);

	}

	private void copyBossResp2InterResp4queResHouse(QueResHouseInterResp resp, String jsonStr) throws Exception {
		QueResHouseBossResp bossResp = (QueResHouseBossResp) BeanUtil.jsonToObject(jsonStr, QueResHouseBossResp.class);

		// 因为字段是一样的，这里可以偷懒一下，
		// 后面如果接口对外字段和boss返回字段不一致，则自行写使用set方法设置值
		BeanUtils.copyProperties(resp, bossResp);
		// resp.setAcctkind(bossResp.getAcctkind());

	}
	private QueResHouseBossReq getQueResHouseInterReq2Boss(QueResHouseInterReq req) {

		QueResHouseBossReq queResHouseBossReq = new QueResHouseBossReq();
		queResHouseBossReq.setAddr(req.getAddr());
		queResHouseBossReq.setAreaid(req.getAreaid());
		queResHouseBossReq.setCity(req.getCity());
		queResHouseBossReq.setCurrentPage(req.getCurrentPage());
		queResHouseBossReq.setPagesize(req.getPagesize());
		queResHouseBossReq.setPatch(req.getPatch());
		queResHouseBossReq.setRightControl(req.getRightControl());
		queResHouseBossReq.setHouseid(req.getHouseid());
		queResHouseBossReq.setHouseid(req.getHouseid());
		queResHouseBossReq.setIsstd(req.getIsstd());
		return queResHouseBossReq;
	}
//	private void setInstallparams(SyncApplyInstallReq req){
//		ChlInstallParam installParam = req.getInstallparams().get(0);
//		installParam.setFitkind(req.getFitkind());
//		installParam.setFitattr(req.getFitattr());
//		installParam.setFitpid(req.getFitpid());
//		installParam.setFituseprop(req.getFituseprop());
//		
//		installParam.setExdevList(req.getExdevList());
//		installParam.setRecycleFitList(req.getRecycleFitList());
//	}

	protected void hanlderResp(String jsonStr, SyncApplyInstallResp resp, BizPortalOrder bizPortalOrder,CustOrder custOrder)
			throws Exception {
		SyncChlInstallResp resp2Boss = (SyncChlInstallResp) BeanUtil.jsonToObject(jsonStr, SyncChlInstallResp.class);
		bizPortalOrder.setResporderid(Long.parseLong(resp2Boss.getOrderid()));
		bizPortalOrder.setFees(resp2Boss.getSums());
		custOrder.setOrderstatus(BizCustorderOrderstatus.SYNC);
		custOrder.setBusinessdescipt(String.format("%s,%s", custOrder.getBusinessdescipt() == null ? "" : custOrder.getBusinessdescipt(),
				resp2Boss.getNewhouseid() == null ? "" : resp2Boss.getNewhouseid()));
		resp.setSums(resp2Boss.getSums());
		getDAO().update(bizPortalOrder);
		getDAO().update(custOrder);
	}

	/**
	 * 写入业务轨迹表
	 * 
	 * @param req
	 * @param loginInfo
	 * @return
	 * @throws Exception
	 */
	protected CustOrder register4Custoer(SyncApplyInstallReq req, LoginInfo loginInfo,BizPortalOrder bizPortalOrder) throws Exception {
		CustOrder custOrder = new CustOrder();
		custOrder.setId(Long.parseLong(req.getBizorderid()));
		custOrder.setAddr(req.getWhladdr());
		custOrder.setAreaid(loginInfo.getAreaid());
		custOrder.setCity(loginInfo.getCity());
		if(StringUtils.isNotBlank(req.getDescribe())){
			custOrder.setDescrip(req.getDescribe());
		}else if(StringUtils.isNotBlank(req.getMemo())){
			custOrder.setDescrip(req.getMemo());
		}
		custOrder.setName(req.getCustname());
		custOrder.setOpcode(BizConstant.BizOpcode.BIZ_USER_NEW);
		custOrder.setOperator(loginInfo.getOperid());
		custOrder.setOprdep(loginInfo.getDeptid());
		custOrder.setOptime(new Date());
		custOrder.setOrdercode(pubService.getOrderCode());
		custOrder.setOrderstatus(BizCustorderOrderstatus.INIT);
		custOrder.setSyncmode(BizCustorderSyncmode.SYNC);
		custOrder.setVerifyphone(req.getVerifyphone());
		custOrder.setCustid(req.getCustid());
		// custOrder.setPortalOrder(portalOrder);
		/*if (StringUtils.isNotBlank(req.getHouseid()) && StringUtils.isNotBlank(req.getPatchid())) {
			BizGridInfo bizGridInfo = pubService.getGrid4Biz(Long.parseLong(req.getHouseid()),
					Long.parseLong(req.getPatchid()), loginInfo);
			if (bizGridInfo != null) {
				custOrder.setGridid(bizGridInfo.getId());
			}
		}*/
		custOrder.setBusinessdescipt(req.getUserparams().get(0).getInstalltype());
		custOrder.setPortalOrder(null);
		getDAO().save(custOrder);
		return custOrder;
	}

	/**
	 * 写入订单账单表
	 * 
	 * @param payway
	 * @param paycode
	 * @param orderid
	 * @return
	 * @throws Exception
	 */
	protected BizPortalOrder register4PortalOrder(Long orderid) throws Exception {
		BizPortalOrder portalOrder = new BizPortalOrder();
		portalOrder.setCreatetime(new Date());
		portalOrder.setOrdertype(PORTAL_ORDER_ORDERTYPE.PORTAL_ORDER_TYPE_PRD);
		portalOrder.setId(orderid);
		portalOrder.setStatus(PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_NOPAY);
		getDAO().save(portalOrder);
		return portalOrder;
	}

	/**
	 * 写入报装参数表
	 * 
	 * @param custOrder
	 * @param req
	 * @param loginInfo
	 * @return
	 * @throws Exception
	 */
	protected ApplyInstall register4ApplyInstall(CustOrder custOrder, SyncApplyInstallReq req, LoginInfo loginInfo)
			throws Exception {
		ApplyInstall applyInstall = new ApplyInstall();
		applyInstall.setAreaid(loginInfo.getAreaid());
		applyInstall.setCity(loginInfo.getCity());
		applyInstall.setCustid(custOrder.getCustid());
		ChlInstallParam installParam = req.getInstallparams().get(0);
		applyInstall.setHouseid(installParam.getHouseid());
		applyInstall.setLinkphone(installParam.getLinkphone());
		applyInstall.setLogicdevno(installParam.getLogicdevno());
		applyInstall.setName(req.getCustname());
		applyInstall.setOlogicdevno(installParam.getOlogicdevno());
		applyInstall.setOrderid(custOrder.getId());
		if(StringUtils.isNotBlank(installParam.getOservid())){
			applyInstall.setOservid(Long.parseLong(installParam.getOservid()));
		}
		
		if (StringUtils.isNotBlank(req.getPatchid())) {
			applyInstall.setPatchid(Long.parseLong(req.getPatchid()));
		}
		applyInstall.setPercomb(installParam.getPercomb());
		applyInstall.setPermark(installParam.getPermark());
		applyInstall.setPredate(DateUtils.parseDate(installParam.getPredate()));
		applyInstall.setPservid(installParam.getPservid());
		applyInstall.setSmuseprop(installParam.getSmnouseprop());
		applyInstall.setStbback(installParam.getStbback());
		applyInstall.setStbno(installParam.getStbno());
		applyInstall.setWhladdr(custOrder.getAddr());
		getDAO().save(applyInstall);
		return applyInstall;
	}

	protected List<ApplyProduct> register4ApplyPrds(SyncApplyInstallReq req, LoginInfo loginInfo, CustOrder custOrder)
			throws Exception {

		List<ApplyProduct> retList = new ArrayList();
		String[] knowidArray = req.getKnowids().split(",");
		String[] countArray = req.getCounts().split(",");
		String[] unitArray = req.getUnits().split(",");
		String[] mindateArray = req.getMindates().split(",");
		String[] ispostponeArray = req.getIspostpones().split(",");
		
		
		if (null == knowidArray || knowidArray.length <= 0) {
			CheckUtils.checkNull(null, "登记报装申请产品信息:产品信息不能为空");
		}
		if (countArray == null || countArray.length <= 0) {
			CheckUtils.checkNull(null, "登记报装申请产品信息:产品订购周期不能为空");
		}
		if (unitArray == null || unitArray.length <= 0) {
			CheckUtils.checkNull(null, "登记报装申请产品信息:产品周期单位不能为空");
		}
		if (ispostponeArray == null || ispostponeArray.length <= 0) {
			CheckUtils.checkNull(null, "登记报装申请产品信息:顺延标识不能为空");
		}
		if (knowidArray.length != countArray.length) {
			CheckUtils.checkNull(null, "登记报装申请产品信息:产品订购周期与产品数不符");
		}
		if (unitArray.length != countArray.length) {
			CheckUtils.checkNull(null, "登记报装申请产品信息:产品订购周期数与产品单位数不符");
		}
		if (ispostponeArray.length != countArray.length) {
			CheckUtils.checkNull(null, "登记报装申请产品信息:产品订购周期数与顺延标识不符");
		}

		String[] stimeArray;

		if (StringUtils.isBlank(req.getStimes())) {
			stimeArray = new String[knowidArray.length];
		} else {
			stimeArray = req.getStimes().split(",", -1);
			if (stimeArray.length != countArray.length) {
				CheckUtils.checkNull(null, "登记报装申请产品信息:产品订购周期数与预约开始时间不符");
			}
		}
		ChlInstallParam installParam = req.getInstallparams().get(0);
		for (int i = 0; i < knowidArray.length; i++) {
			String knowid = knowidArray[i];
			String count = countArray[i];
			String unit = unitArray[i];
			String ispostpone = ispostponeArray[i];
			String stime = StringUtils.isBlank(stimeArray[i]) ? null : stimeArray[i];
			String mindate=mindateArray[i];
			if (StringUtils.isBlank(knowid))
				continue;
			SalespkgKnowObjInfoBO knowObjInfo = interApplyService.getSalespkgKnowObjInfo(knowid);
			CheckUtils.checkNull(knowObjInfo, "登记报装申请产品信息:根据营销标识[" + knowid + "]查询不营销配置信息");
			ApplyProduct product = register4Prd(knowObjInfo, loginInfo, Long.parseLong(count), Long.parseLong(knowid),
					unit, installParam.getLogicdevno(), custOrder.getId(), ispostpone, stime,mindate);
			retList.add(product);
		}
		return retList;
	}

	/**
	 * 写入报装产品表
	 * 
	 * @param knowObjInfo
	 * @param loginInfo
	 * @param count
	 * @param knowid
	 * @param unit
	 * @param logicdevno
	 * @param orderid
	 * @param servid
	 * @return
	 * @throws Exception
	 */
	private ApplyProduct register4Prd(SalespkgKnowObjInfoBO knowObjInfo, LoginInfo loginInfo, Long count, Long knowid,
			String unit, String logicdevno, Long orderid, String ispostpone, String stime,String mindate) throws Exception {
		ApplyProduct bizApplyProduct = new ApplyProduct();
		bizApplyProduct.setCity(loginInfo.getCity());
		bizApplyProduct.setCount(count);
		bizApplyProduct.setKnowid(knowid);
		bizApplyProduct.setLogicdevno(logicdevno);
		bizApplyProduct.setOrderid(orderid);
		bizApplyProduct.setOstatus(BizConstant.BizApplyProductOstatus.ORDER);
		bizApplyProduct.setIspostpone(ispostpone);
		bizApplyProduct.setStime(stime);
		bizApplyProduct.setMindate(mindate);
		// bizApplyProduct.setServid(servid);
		if(knowObjInfo.getType().equals(BizConstant.PrdSalespkgKnowObjtype.PRD)){
			bizApplyProduct.setPid(Long.parseLong(knowObjInfo.getId()));
		}
		bizApplyProduct.setSalespkgid(knowObjInfo.getType().equals(BizConstant.PrdSalespkgKnowObjtype.SALESPKG)
				? Long.valueOf(knowObjInfo.getId()) : null);
		if(knowObjInfo.getType().equals(BizConstant.PrdSalespkgKnowObjtype.GOODS)
				||knowObjInfo.getType().equals(BizConstant.PrdSalespkgKnowObjtype.GOODS_TYPE)){
			bizApplyProduct.setSalesid(Long.valueOf(knowObjInfo.getId()));
			if(StringUtils.isNotBlank(knowObjInfo.getPid())){
				bizApplyProduct.setPid(Long.parseLong(knowObjInfo.getPid()));
			}
		}
		if(unit.equals("日")){
			bizApplyProduct.setUnit("0");
		}else if(unit.equals("月")){
			bizApplyProduct.setUnit("1");
		}else if(unit.equals("年")||unit.equals("周期")){
			bizApplyProduct.setUnit("2");
		}else if(unit.equals("次")){
			bizApplyProduct.setUnit(null);
		}else{
			bizApplyProduct.setUnit(unit);
		}
		getDAO().save(bizApplyProduct);
		return bizApplyProduct;
	}

	/**
	 * 写入报装的银行信息
	 * 
	 * @param param
	 * @param loginInfo
	 * @param orderid
	 * @param payway
	 * @return
	 * @throws Exception
	 */
	protected ApplyBank register4ApplyBank(SyncInstallBankParam param, LoginInfo loginInfo, Long orderid, String payway)
			throws Exception {
		if(param == null) return null;
		ApplyBank applyBank = new ApplyBank();
		applyBank.setAcctkind(param.getAcctkind());
		applyBank.setAcctname(param.getAcctname());
		applyBank.setAcctno(param.getAcctno());
		applyBank.setAccttype(param.getAccttype());
		applyBank.setBankcode(param.getBankcode());
		applyBank.setCity(loginInfo.getCity());
		applyBank.setOrderid(orderid);
		applyBank.setPayway(payway);
		if(StringUtils.isNotBlank(param.getServid())){
			applyBank.setServid(Long.parseLong(param.getServid()));
		}
		getDAO().save(applyBank);
		return applyBank;
	}

	/**
	 * 写入选择性产品
	 * 
	 * @param selectProducts
	 * @throws Exception
	 */
	protected void register4SelectPrd(List<KnowProducts> selectProducts, LoginInfo loginInfo, Long bizOrderid)
			throws Exception {
		if (selectProducts == null || selectProducts.isEmpty())
			return;
		for (KnowProducts kProducts : selectProducts) {
			ApplyProduct applyProduct = new ApplyProduct();
			applyProduct.setKnowid(Long.parseLong(kProducts.getKnowId()));
			List<ApplyProduct> applyProductList = getDAO().find(applyProduct);
			if (applyProductList != null && applyProductList.size() > 0 && applyProductList.get(0) != null) {
				applyProduct = applyProductList.get(0);
				for (SelectProduct product : kProducts.getProducts()) {
					ApplyProductSelect applyProductSelect = new ApplyProductSelect();
					applyProductSelect.setCity(loginInfo.getCity());
					applyProductSelect.setKnowid(Long.parseLong(kProducts.getKnowId()));
					applyProductSelect.setOrderid(bizOrderid);
					applyProductSelect.setPrecid(applyProduct.getRecid());
					applyProductSelect.setPid(product.getProductId());
					applyProductSelect.setSelectid(Long.parseLong(kProducts.getSelectId()));
					getDAO().save(applyProductSelect);
				}
			}
		}
	}

	private SyncChlInstallReq getSyncChinstallReq(SyncApplyInstallReq req, LoginInfo loginInfo,
			List<ApplyProduct> products, Long orderid) throws Exception {
		SyncChlInstallReq req2Boss = new SyncChlInstallReq();
		if(req.getAddrs() != null && !req.getAddrs().isEmpty()){
			req2Boss.setAddrparam(req.getAddrs().get(0));
		}
		
		req2Boss.setAreaid(loginInfo.getAreaid());
		req2Boss.setBankparams(req.getBankparams());
		req2Boss.setCity(loginInfo.getCity());
		req2Boss.setCustid(req.getCustid());
		req2Boss.setDescribe(req.getDescribe());
		
		req2Boss.setInstallparams(req.getInstallparams());
		req2Boss.setIscrtorder(req.getIscrtorder());
		req2Boss.setMemo(req.getMemo());
		req2Boss.setOncefeeparams(req.getOncefeeparams());
		req2Boss.setOpcode(req.getOpcode());
		req2Boss.setOperator(loginInfo.getOperid());
		req2Boss.setOprdep(loginInfo.getDeptid());
		req2Boss.setOptime(req.getOptime());
		req2Boss.setPrdparams(getPrdParams(products, req.getInstallparams().get(0), orderid));
		req2Boss.setSystemid(req.getSystemid());
		req2Boss.setUserparams(req.getUserparams());
//		req2Boss.setFitattr(req.getFitattr());
//		req2Boss.setFitkind(req.getFitkind());
//		req2Boss.setFitpid(req.getFitpid());
//		req2Boss.setFituseprop(req.getFituseprop());
		req2Boss.setIscrtorder("Y");
		/*if(null != req2Boss.getAddrparam() && StringUtils.isBlank(req2Boss.getAddrparam().getHouseid())) {
			req2Boss.getAddrparam().setHouseid(houseid);
		}*/
//		req2Boss.setExdevList(req.getExdevList());
//		req2Boss.setRecycleFitList(req.getRecycleFitList());
		
		//ywp后面要去掉R，如果kind不为空，则改为R
//		if(null!=req2Boss.getExdevList()){
//			for(ExdevList item:req2Boss.getExdevList()){
//				if(null!=item.getKind()&&!"null".equals(item.getKind())){
//					item.setKind("R");
//				}
//			}
//		}
		if (req2Boss.getInstallparams() != null&& req2Boss.getInstallparams().size() > 0) {
			if (null != req2Boss.getInstallparams().get(0).getExdevList()) {
				for (ExdevList item : req2Boss.getInstallparams().get(0).getExdevList()) {
					if (null != item.getKind()
							&& !"null".equals(item.getKind())) {
						//是否OTTr-2转换
						Rule rule = ruleService.getRule("*", ottRtoTwoSwitch);
						if(rule != null && rule.getValue()!= null){
							if(rule.getValue().equalsIgnoreCase("Y")){
								item.setKind("2");
								item.setIsopen("N");
							}
							else {
								item.setKind("R");
							}
						}

					}

				}
			}
		}

		//是否缴清欠费
		if (StringUtils.isEmpty(req.getBizfeein())){
			req2Boss.setBizfeein("Y");
		}else {
			req2Boss.setBizfeein(req.getBizfeein());
		}

		//退订参数
		if (req.getEjectparams()!=null){

			req2Boss.setEjectparams(req.getEjectparams());
		}
		return req2Boss;
	}

	private List<SyncPrdParam> getPrdParams(List<ApplyProduct> products, ChlInstallParam installParam, Long orderid)
			throws Exception {
		List<SyncPrdParam> prdParams = new ArrayList<SyncPrdParam>();
		for (ApplyProduct product : products) {
			SyncPrdParam param = new SyncPrdParam();
			param.setCount(product.getCount().toString());
			param.setIspostpone(product.getIspostpone());
			param.setLogicdevno(installParam.getLogicdevno());
			param.setStime(product.getStime());
			if(product.getPid() != null){
				param.setPid(product.getPid().toString());
			}
			if(product.getSalespkgid() != null){
				param.setSalespkgid(product.getSalespkgid().toString());
			}
			if(product.getSalesid() != null){
				param.setSalesid(product.getSalesid().toString());
			}
			// param.setServid(servid);可以为空 置空
			constructSelprds(product.getKnowid(), orderid, param);
			if(StringUtils.isNotBlank(product.getUnit())){
				param.setUnit(product.getUnit());	
			}
			//ywp 加上最小使用期限
			if(StringUtils.isNotBlank(product.getMindate())&&!product.getMindate().equals("null")){
				param.setMindate(product.getMindate());
			}
			//ywp mincount去掉
			/*if(StringUtils.isNotBlank(product.getMindate())&&!product.getMindate().equals("null")){
				param.setMincount(product.getMindate());
			}*/
			prdParams.add(param);
		}
		return prdParams;
	}

	private void constructSelprds(Long knowid, Long orderid, SyncPrdParam param) throws Exception {
		ApplyProductSelect applyProductSelect = new ApplyProductSelect();
		applyProductSelect.setOrderid(orderid);
		applyProductSelect.setKnowid(knowid);
		List<ApplyProductSelect> li = getDAO().find(applyProductSelect);
		if (li != null && !li.isEmpty()) {
			StringBuffer selprds = new StringBuffer();
			for (ApplyProductSelect productSelect : li) {
				selprds.append(productSelect.getPid());
				selprds.append(",");
			}
			param.setSelprds(selprds.toString());
			param.setSelectid(li.get(0).getSelectid().toString());
		}
	}

	/**
	 * 开户确认提交接口
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo bizInstallCommit(BizInstallCommitReq req,BizInstallCommitResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		 LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
//		LoginInfo loginInfo = tmpService.getGridTestLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req, "请求对象不能为空");
		CheckUtils.checkEmpty(req.getBizorderid(), "业务订单号不能为空");
		CheckUtils.checkNull(req.getCustOrderid(), "订单编号不能为空");
		
		
		CustOrder custOrder = (CustOrder) getDAO().find(CustOrder.class,req.getCustOrderid());
		CheckUtils.checkNull(custOrder, "查询不到该订单记录");
		BizPortalOrder bizPortalOrder = (BizPortalOrder) getDAO().find(BizPortalOrder.class, req.getCustOrderid());
		CheckUtils.checkNull(bizPortalOrder, "查询不到该订单记录");
		
		String orderStatus = bizPortalOrder.getStatus();
		if(orderStatus.equals(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_LOGICDEL)
				||orderStatus.equals(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_PAYED)
				||orderStatus.equals(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_OVERDUE)
				||orderStatus.equals(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_BOSSPAD)){
			CheckUtils.checkNull(null, "该订单状态不支持支付,请确认");
		}
		String multipay = "N";
        if(StringUtils.isNotBlank(req.getMultipaywayflag()) && req.getMultipaywayflag().equalsIgnoreCase("Y")) {
        	if(StringUtils.isNotBlank(req.getCashe())) {
        		double payFees = Double.parseDouble(bizPortalOrder.getFees()) - Double.parseDouble(req.getCashe());
        		if(payFees > 0.0) {
        			bizPortalOrder.setMultipay("Y");
        			multipay = "Y";
        		}
        		bizPortalOrder.setPayFees(Double.toString(payFees));
        	}
        }
		String resultInfo = quest2Boss(req.getBizorderid(),custOrder,bizPortalOrder,req.getBankaccno(),req.getPaycode(),req.getPayreqid(),req.getPayway(),multipay,loginInfo);
		//getWgpaywayChangegetPayway
		bizPortalOrder.setWgpayway(req.getPayway());
		bizPortalOrder.setPayway(req.getPayway());
		bizPortalOrder.setPaycode(req.getPaycode());
        if (StringUtils.isNotEmpty(req.getPayreqid())) {
        	bizPortalOrder.setPayreqid(req.getPayreqid());
        }
        bizPortalOrder.setPaytime(new Date());
		handlerData(resultInfo,bizPortalOrder,custOrder);
		resp.setOrderid(req.getCustOrderid().toString());
		ReturnVisitTaskService.addTask(req.getCustOrderid());
		return returnInfo;
	}
	
	protected String quest2Boss(String bizOrderid,CustOrder custOrder,BizPortalOrder bizPortalOrder,String bankaccno,String paycode,String payreqid,String payway,String multipaywayflag,LoginInfo loginInfo) throws Exception{
		BizInstallCommit2BossReq req2Boss = getBizInstallCommit2BossReq(custOrder, bizPortalOrder, bankaccno, paycode, payreqid, payway,multipaywayflag);
		ParamsManager.isCorrectData(payway,paycode);
		String resultInfo = getBossHttpInfOutput(bizOrderid, BossInterfaceService.BIZ_INSTALL_COMMIT, req2Boss, loginInfo);
		return resultInfo;
	}
	
	protected void handlerData(String jsonStr,BizPortalOrder bizPortalOrder,CustOrder custOrder) throws Exception{
		BizInstallCommit2BossResp resp2Boss = (BizInstallCommit2BossResp) BeanUtil.jsonToObject(jsonStr, BizInstallCommit2BossResp.class);
		bizPortalOrder.setStatus(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_PAYED);
		custOrder.setBossserialno(resp2Boss.getSerialno());
		getDAO().update(bizPortalOrder);
		getDAO().update(custOrder);
	}
	
	private BizInstallCommit2BossReq getBizInstallCommit2BossReq(
			CustOrder custOrder,BizPortalOrder bizPortalOrder,String bankaccno
			,String paycode,String payreqid,String payway,String multipaywayflag){
		BizInstallCommit2BossReq req2Boss = new BizInstallCommit2BossReq();
		req2Boss.setBankaccno(bankaccno);
		req2Boss.setCustid(custOrder.getCustid().toString());
		req2Boss.setOrderid(bizPortalOrder.getResporderid().toString());
		req2Boss.setPaycode(paycode);
		req2Boss.setPayreqid(payreqid);
		req2Boss.setPayway(payway);
		req2Boss.setMultipaywayflag(multipaywayflag);
		if (StringUtils.isNotBlank(custOrder.getBusinessdescipt())) {
			String[] strings = custOrder.getBusinessdescipt().split(",", -1);
			if (strings.length > 0 && StringUtils.isNotBlank(strings[0])) {
				req2Boss.setInstalltype(strings[0]);
			}
			if (strings.length > 1 && StringUtils.isNotBlank(strings[1])) {
				req2Boss.setHouseid(strings[1]);
			}
		}
		req2Boss.setFeebacktype(1);
		return req2Boss;
	}

	/**
	 * 查询号码池
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queCmccAcctno(QueCmccAcctnoReq req,QueCmccAcctnoResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		
		QueCmccAcctnoReq req2Boss = new QueCmccAcctnoReq();
		BeanUtils.copyPropertiesNotSuperClass(req2Boss, req);
		
		String responseStr = getBossHttpInfOutput(req.getBizorderid(), BossInterfaceService.QUE_CMMC_ACCTNO, req2Boss, loginInfo);
		QueAcctnoBossResp bossResp = (QueAcctnoBossResp) BeanUtil.jsonToObject(responseStr, QueAcctnoBossResp.class);
		resp.setDatas(bossResp.getResult());
		return returnInfo;
	}
	
	
	public ReturnInfo bizLockCmccAcctno(BizLockCmccAcctnoReq req) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkEmpty(req.getAcctno(), "宽带帐号信息不能为空");
		
		
		BizLockCmccAcctnoReq req2Boss = new BizLockCmccAcctnoReq();
		BeanUtils.copyPropertiesNotSuperClass(req2Boss, req);
		
		String response = getBossHttpInfOutput(req.getBizorderid(), BossInterfaceService.LOCK_CMCC_ACCTNO, req2Boss, loginInfo);
		return returnInfo;
	}
	
	public ReturnInfo bizUnLockCmccAcctno(BizLockCmccAcctnoReq req) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkEmpty(req.getAcctno(), "宽带帐号信息不能为空");
		
		
		BizLockCmccAcctnoReq req2Boss = new BizLockCmccAcctnoReq();
		BeanUtils.copyPropertiesNotSuperClass(req2Boss, req);
		
		String response = getBossHttpInfOutput(req.getBizorderid(), BossInterfaceService.UNLOCK_CMCC_ACCTNO, req2Boss, loginInfo);
		return returnInfo;
	}
	
	/**
	 * 查询设备产品信息
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queDevPrdinfo(QueDevPrdinfoReq req,QueDevPrdinfoResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		
		QueDevPrdinfoReq req2Boss = new QueDevPrdinfoReq();
		BeanUtils.copyPropertiesNotSuperClass(req2Boss, req);

		//是否OTTr-2转换
		Rule rule = ruleService.getRule("*", ottRtoTwoSwitch);
		if(rule != null && rule.getValue()!= null){
			if(rule.getValue().equalsIgnoreCase("Y")){
				if(req2Boss.getKind().equalsIgnoreCase("R")) {
					req2Boss.setKind("2");
				}
			}
		}
		String outputStr = getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.QUE_DEVPRDINFO, req2Boss, loginInfo);
		List<QueDevPrdinfoBossResp> datas = new Gson().fromJson(outputStr, new TypeToken<List<QueDevPrdinfoBossResp>>() {}.getType());
		resp.setDatas(datas);
		return returnInfo;
	}
}
