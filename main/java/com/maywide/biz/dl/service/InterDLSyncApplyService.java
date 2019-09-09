package com.maywide.biz.dl.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.ServerCityBossInterface;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.biz.dl.pojo.asyncOrder.BizAsyncOrder2BossReq;
import com.maywide.biz.dl.pojo.asyncOrder.BizInstallCommit2BossReq;
import com.maywide.biz.dl.pojo.asyncOrder.CommonParam;
import com.maywide.biz.dl.pojo.asyncOrder.InstallParams;
import com.maywide.biz.dl.pojo.asyncOrder.PrdParam;
import com.maywide.biz.dl.pojo.asyncOrder.UserParam;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.queSyncPercomb.DevProductInfo;
import com.maywide.biz.inter.pojo.queSyncPercomb.PercombCmParam;
import com.maywide.biz.inter.pojo.queSyncPercomb.PercombDevParam;
import com.maywide.biz.inter.pojo.queSyncPercomb.QueSyncPercombReq;
import com.maywide.biz.inter.pojo.queSyncPercomb.QueSyncPercombResp;
import com.maywide.biz.inter.pojo.sycnChlInstall.ChlInstallParam;
import com.maywide.biz.inter.pojo.sycnChlInstall.GetAccton2BossReq;
import com.maywide.biz.inter.pojo.sycnChlInstall.GetAccton2BossResp;
import com.maywide.biz.inter.pojo.sycnChlInstall.InstallUserParam;
import com.maywide.biz.inter.pojo.syncApplyInstall.SyncApplyInstallReq;
import com.maywide.biz.inter.pojo.syncApplyInstall.SyncApplyInstallResp;
import com.maywide.biz.inter.service.InterSyncApplyService;
import com.maywide.biz.market.entity.ApplyBank;
import com.maywide.biz.market.entity.ApplyInstall;
import com.maywide.biz.market.entity.ApplyProduct;
import com.maywide.biz.market.entity.ApplyProductSelect;
import com.maywide.biz.market.entity.BizPortalOrder;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.CheckUtils;
import com.maywide.core.util.DateUtils;
import com.maywide.core.util.EhcacheUtil;

@Service
public class InterDLSyncApplyService extends InterSyncApplyService {

	@Override
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
		return returnInfo;
	}

	@Override
	protected void fillCMParam(List<PercombCmParam> params,String custid,String bizOrderid,LoginInfo loginInfo) throws Exception {
		if(StringUtils.isNotBlank(SysConfig.getServiceCity()) && params != null && !params.isEmpty()){
			params.get(0).setCmacctno(addCmAccount(custid, bizOrderid, loginInfo));
			params.get(0).setCmacctnoPwd(getAcctonPwd(params.get(0).getCmacctno()));
		}
	}

	/**
	 * 大连  需要获取宽带帐号
	 * @throws Exception 
	 */
	private String addCmAccount(String custid,String bizOrderid,LoginInfo loginInfo) throws Exception{
		GetAccton2BossResp boss2Resp = null;
		Object resp = EhcacheUtil.getAccount(custid);
		if(resp == null){
			GetAccton2BossReq req2Boss = new GetAccton2BossReq(custid);
			String resultInfoput = getBossHttpInfOutput(bizOrderid, BizConstant.ServerCityBossInterface.BIZ_CM_GETACCTNOWG,
					req2Boss, loginInfo);
			 boss2Resp = (GetAccton2BossResp) BeanUtil.jsonToObject(resultInfoput, GetAccton2BossResp.class);
			EhcacheUtil.putAccount(custid, boss2Resp);
		}else{
			 boss2Resp = (GetAccton2BossResp)resp;
		}
		return boss2Resp.getCmacctno();
	}
	
	
	/***
	 * 大连 获取宽带的帐号密码
	 * @param account
	 * @return
	 * @throws BusinessException
	 */
	private String getAcctonPwd(String account) throws BusinessException{
		if(StringUtils.isBlank(account)) throw new BusinessException("宽带帐号有误,请连BOSS管理员查询");
		if(account.length() <= 6) return account;
		return account.substring(account.length() - 6, account.length());
	}

	@Override
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
		
		InstallUserParam userParams = req.getUserparams().get(0);
		BizPortalOrder bizPortalOrder = register4PortalOrder(Long.parseLong(req.getBizorderid()));
		CustOrder custOrder = register4Custoer(req, loginInfo,bizPortalOrder);
		ApplyInstall applyInstall = register4ApplyInstall(custOrder, req, loginInfo);
		List<ApplyProduct> applyProducts = register4ApplyPrds(req, loginInfo, custOrder);
		
		if (req.getBankparams() != null && req.getBankparams().size() > 0) {
			ApplyBank applyBank = register4ApplyBank(req.getBankparams().get(0), loginInfo, custOrder.getId(),
					userParams.getPayway());
		}
		BizAsyncOrder2BossReq req2Boss = getAsyncOrder2BossReq(req, loginInfo, custOrder, applyProducts);
		String resultPutInfo = getBossHttpInfOutput(req.getBizorderid(), ServerCityBossInterface.BIZ_ASYNC_ORDER, req2Boss,
				loginInfo);
		resp.setCustOrderid(req.getBizorderid());
		hanlderResp(resultPutInfo, resp, bizPortalOrder,custOrder);
		return returnInfo;
	}
	
	
	private BizAsyncOrder2BossReq getAsyncOrder2BossReq(SyncApplyInstallReq req,
			LoginInfo loginInfo,CustOrder custOrder,List<ApplyProduct> applyProducts) throws Exception{
		BizAsyncOrder2BossReq req2Boss = new BizAsyncOrder2BossReq();
		req2Boss.setAreaid(loginInfo.getAreaid());
		req2Boss.setBankparams(req.getBankparams());
		req2Boss.setCity(loginInfo.getCity());
		req2Boss.setCustid(custOrder.getCustid());
		req2Boss.setDescribe(req.getDescribe());
		req2Boss.setInstallparams(getInstallParams(req.getInstallparams(),req.getUserparams()));
		req2Boss.setOncefeeparams(req.getOncefeeparams());
		req2Boss.setOpcode(custOrder.getOpcode());
		req2Boss.setOperator(loginInfo.getOperid());
		req2Boss.setOprdep(loginInfo.getDeptid());
		req2Boss.setOptime(DateUtils.formatTime(new Date()));
		req2Boss.setOrdercode(custOrder.getOrdercode());
		req2Boss.setOrderid(custOrder.getId());
		req2Boss.setOrderstatus("SYNC");
		req2Boss.setPrdparams(getPrdParams(applyProducts,custOrder.getId()));
		req2Boss.setPublicparam(getPublicParams(req));
		req2Boss.setSynctime(DateUtils.formatTime(new Date()));
		req2Boss.setSystemid("GRID");
		req2Boss.setUserparams(getUserParams(req.getUserparams(), req.getInstallparams()));
		return req2Boss;
	}
	
	private CommonParam getPublicParams(SyncApplyInstallReq req){
		CommonParam param = new CommonParam();
		param.setHouseid(Long.parseLong(req.getHouseid()));
		param.setInstalltype(req.getUserparams().get(0).getInstalltype());
		param.setPredate(req.getInstallparams().get(0).getPredate());
		param.setServkind("1");
		return param;
	}
	
	private List<InstallParams> getInstallParams(List<ChlInstallParam> reqParams,List<InstallUserParam> userParams){
		List<InstallParams> installParams = new ArrayList<InstallParams>();
		if((reqParams != null && !reqParams.isEmpty())&&(userParams != null && !userParams.isEmpty())){
			InstallUserParam user = userParams.get(0);
			ChlInstallParam installParam = reqParams.get(0);
			String[] permarkStrs = installParam.getPermark().split(",");
			for(String permark:permarkStrs){
				InstallParams param = new InstallParams();
				param.setBankid(installParam.getBankid());
				param.setCmno(installParam.getCmno());   //EOC设备
				param.setCmpid(user.getCmpid());				//EOC设备产品
				param.setCmuseprop(installParam.getCmuseprop());  //EOC设备来源
				param.setFeekind(installParam.getFeekind());
				param.setLogicdevno(installParam.getLogicdevno());
				param.setMemo(installParam.getMemo());
				param.setPayway(installParam.getPayway());
				param.setPermark(permark);
				param.setSm_useprop(installParam.getSmnouseprop());
				param.setSmnopid(user.getSmnopid());			//智能卡产品
				param.setStb_useprop(installParam.getStbuseprop());
				param.setStbno(installParam.getStbno());
				param.setStbpid(user.getStbpid());	//机顶盒产品
				installParams.add(param);
			}
			/*for(ChlInstallParam installParam:reqParams){
				InstallParams param = new InstallParams();
				param.setBankid(installParam.getBankid());
				param.setCmno(installParam.getCmno());   //EOC设备
				param.setCmpid(user.getCmpid());				//EOC设备产品
				param.setCmuseprop(installParam.getCmuseprop());  //EOC设备来源
				param.setFeekind(installParam.getFeekind());
				param.setLogicdevno(installParam.getLogicdevno());
				param.setMemo(installParam.getMemo());
				param.setPayway(installParam.getPayway());
				param.setPermark(installParam.getPermark());
				param.setSm_useprop(installParam.getSmnouseprop());
				param.setSmnopid(user.getSmnopid());			//智能卡产品
				param.setStb_useprop(installParam.getStbuseprop());
				param.setStbno(installParam.getStbno());
				param.setStbpid(user.getStbpid());	//机顶盒产品
				installParams.add(param);
			}*/
		}
		return installParams;
	}
	
	private List<PrdParam> getPrdParams(List<ApplyProduct> applyProducts,Long orderid) throws Exception{
		List<PrdParam> prdParams = new ArrayList<PrdParam>();
		if(applyProducts != null && !applyProducts.isEmpty()){
			for(ApplyProduct product:applyProducts){
				PrdParam param = new PrdParam();
				param.setCount(product.getCount().toString());
				param.setLogicdevno(product.getLogicdevno());
				if(product.getPid() != null){
					param.setPid(product.getPid().toString());
				}
				if(product.getSalespkgid() != null){
					param.setSalespkgid(product.getSalespkgid().toString());
				}
				if(StringUtils.isNotBlank(product.getUnit())){
					param.setUnit(product.getUnit());	
				}
				constructSelprds(product.getKnowid(),orderid, param);
				if(StringUtils.isNotBlank(product.getUnit())){
					param.setUnit(product.getUnit());	
				}
				prdParams.add(param);
			}
		}
		return prdParams;
	}
	
	private void constructSelprds(Long knowid, Long orderid,PrdParam param) throws Exception{
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
	
	private List<UserParam> getUserParams(List<InstallUserParam> userParams,List<ChlInstallParam> installParams){
		List<UserParam> params = new ArrayList<UserParam>();
		if((userParams != null && !userParams.isEmpty())&&(installParams != null && !installParams.isEmpty())){
			ChlInstallParam chlInstallParam = installParams.get(0);
			/*InstallUserParam userParam = userParams.get(0);
			String[] permarkStr = userParam.getPermark().split(",");*/
			/*for(String permark:permarkStr){
				UserParam param = new UserParam();
				param.setAuthmode(userParam.getAuthmode());
				param.setCmacctno(userParam.getCmacctno());
				param.setIpmode(userParam.getIpmode());
				param.setIpnum(userParam.getIpnum());
				param.setIscabl(userParam.getIscabl());
				param.setIsinner(userParam.getIsineer());
				param.setLogicdevno(chlInstallParam.getLogicdevno());
				param.setOutdevno(userParam.getOutdevno());
				param.setPassword(userParam.getPassword());
				param.setPermark(permark);
				param.setPservid(chlInstallParam.getPservid());
				param.setServtype(chlInstallParam.getServtype());
				param.setSubinputway(userParam.getSubinputway());
				param.setInputway(userParam.getInputway());
				params.add(param);
			}*/
			
			for(InstallUserParam user:userParams){
				UserParam param = new UserParam();
				param.setAuthmode(user.getAuthmode());
				param.setCmacctno(user.getCmacctno());
				param.setIpmode(user.getIpmode());
				param.setIpnum(user.getIpnum());
				param.setIscabl(user.getIscabl());
				param.setIsinner(user.getIsineer());
				param.setOutdevno(user.getOutdevno());
				param.setPassword(user.getPassword());
				param.setPermark(user.getPermark());
				if(param.getPermark().endsWith("2")){
					param.setLogicdevno(chlInstallParam.getCmno());
				}else{
					param.setLogicdevno(chlInstallParam.getLogicdevno());
				}
				param.setPservid(chlInstallParam.getPservid());
				param.setServtype(chlInstallParam.getServtype());
				param.setSubinputway(user.getSubinputway());
				param.setInputway(user.getInputway());
				params.add(param);
			}
		}
		return params;
	}

	@Override
	protected String quest2Boss(String bizOrderid, CustOrder custOrder, BizPortalOrder bizPortalOrder, String bankaccno,
			String paycode, String payreqid, String payway,String multipaywayflag, LoginInfo loginInfo) throws Exception {
		BizInstallCommit2BossReq req2Boss = getBizInstallCommit2BossReq(bizPortalOrder.getResporderid().toString(), payway, bankaccno, payreqid);
		String resultInfo = getBossHttpInfOutput(bizOrderid, ServerCityBossInterface.BIZ_INSTALL_COMMIT, req2Boss, loginInfo);
		return resultInfo;
	}
	
	private BizInstallCommit2BossReq getBizInstallCommit2BossReq(String orderid,String payway,String bankaccno,String payreqid){
		BizInstallCommit2BossReq req2Boss = new BizInstallCommit2BossReq();
		req2Boss.setBankaccno(bankaccno);
		req2Boss.setOrderid(orderid);
		req2Boss.setPayreqid(payreqid);
		req2Boss.setPayway(payway);
		return req2Boss;
	}

	@Override
	protected void handlerDevRule(String percomb, String psubclass, List<DevProductInfo> productInfos)
			throws Exception {
	}
	
	@Override
	protected void handlerData(String jsonStr, BizPortalOrder bizPortalOrder, CustOrder custOrder) throws Exception {
		bizPortalOrder.setStatus(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_PAYED);
		getDAO().update(bizPortalOrder);
		getDAO().update(custOrder);
	}
	
}
