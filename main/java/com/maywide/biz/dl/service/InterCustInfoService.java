package com.maywide.biz.dl.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maywide.biz.cons.BizConstant.ServerCityBossInterface;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.dl.pojo.queConfirmFee.QueConfirmFeeBossReq;
import com.maywide.biz.dl.pojo.queConfirmFee.QueConfirmFeeReq;
import com.maywide.biz.dl.pojo.queConfirmFee.QueConfirmFeeResp;
import com.maywide.biz.dl.pojo.queCustInfo.QueCustInfoBossReq;
import com.maywide.biz.dl.pojo.queCustInfo.QueCustInfoReq;
import com.maywide.biz.dl.pojo.queCustInfo.QueCustInfoResp;
import com.maywide.biz.dl.pojo.queDevSalesOper.QueDevSalesOperBossReq;
import com.maywide.biz.dl.pojo.queDevSalesOper.QueDevSalesOperReq;
import com.maywide.biz.dl.pojo.queDevSalesOper.QueDevSalesOperResp;
import com.maywide.biz.dl.pojo.queFeesInfo.QueFeesInfoBossReq;
import com.maywide.biz.dl.pojo.queFeesInfo.QueFeesInfoReq;
import com.maywide.biz.dl.pojo.queFeesInfo.QueFeesInfoResp;
import com.maywide.biz.dl.pojo.quePointInfo.PointInfoBean;
import com.maywide.biz.dl.pojo.quePointInfo.QuePointInfoBossReq;
import com.maywide.biz.dl.pojo.quePointInfo.QuePointInfoReq;
import com.maywide.biz.dl.pojo.quePointInfo.QuePointInfoResp;
import com.maywide.biz.dl.pojo.queUserCount.QueUserCountBossReq;
import com.maywide.biz.dl.pojo.queUserCount.QueUserCountReq;
import com.maywide.biz.dl.pojo.queUserCount.QueUserCountResp;
import com.maywide.biz.dl.pojo.queUserInfo.QueUserInfoBossReq;
import com.maywide.biz.dl.pojo.queUserInfo.QueUserInfoReq;
import com.maywide.biz.dl.pojo.queUserInfo.QueUserInfoResp;
import com.maywide.biz.dl.pojo.queWoekDayReport.QueWorkDayReportBpssReq;
import com.maywide.biz.dl.pojo.queWoekDayReport.QueWorkDayReportReq;
import com.maywide.biz.dl.pojo.queWoekDayReport.QueWorkDayReportResp;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.service.PubService;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;

@Service
public class InterCustInfoService extends CommonService {

	@Autowired
	private PubService pubService;
	
	/**
	 *查询客户住宅信息
	 * @param req
	 * @param resp
	 * @return(待联调)
	 * @throws Exception
	 */
	public ReturnInfo queCustInfo(QueCustInfoReq req,QueCustInfoResp resp) throws Exception{
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
//		
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
		QueCustInfoBossReq req2Boss = getQueCustInfoBossReq(req, loginInfo);
		
		String outputStr = getBossHttpInfOutput(req.getBizorderid(), ServerCityBossInterface.QUE_CUSTINFOWG, req2Boss, loginInfo);
		handlerCustInfoResp(outputStr, resp);
		return returnInfo;
	}
	
	private void handlerCustInfoResp(String jsonStr,QueCustInfoResp resp) throws Exception{
		QueCustInfoResp bossResp = (QueCustInfoResp) BeanUtil.jsonToObject(jsonStr, QueCustInfoResp.class);
		BeanUtils.copyProperties(resp, bossResp);
	}
	
	private QueCustInfoBossReq getQueCustInfoBossReq(QueCustInfoReq req,LoginInfo loginInfo) throws Exception{
		QueCustInfoBossReq req2Boss = new QueCustInfoBossReq();
		req2Boss.setAddrjd(req.getAddrjd());
		req2Boss.setAddrlh(req.getAddrlh());
		req2Boss.setNetids(pubService.getGridcodes(loginInfo));
		return req2Boss;
	}
	
	
	/**
	 * 住宅用户信息查询
	 * @param req
	 * @param resp
	 * @return(待联调)
	 * @throws Exception
	 */
	public ReturnInfo queUserInfo(QueUserInfoReq req,QueUserInfoResp resp) throws Exception{
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
//		
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		QueUserInfoBossReq req2Boss = getQueUserInfoBossReq(req);
		
		String jsonOutPutStr = getBossHttpInfOutput(req.getBizorderid(), ServerCityBossInterface.QUE_USERINFOWG, req2Boss, loginInfo);
		handlerUserInfoResp(jsonOutPutStr,resp);
		return returnInfo;
	}
	
	private void handlerUserInfoResp(String jsonStr,QueUserInfoResp resp) throws Exception{
		QueUserInfoResp bossResp = (QueUserInfoResp) BeanUtil.jsonToObject(jsonStr, QueUserInfoResp.class);
		BeanUtils.copyProperties(resp, bossResp);
	}
	
	private QueUserInfoBossReq getQueUserInfoBossReq(QueUserInfoReq req){
		QueUserInfoBossReq req2Boss = new QueUserInfoBossReq();
		req2Boss.setChouseid(req.getChouseid());
		req2Boss.setMarkno(req.getMarkno());
		req2Boss.setPermark(req.getPermark());
		return req2Boss;
	}
	
	/**
	 * 用户保有数查询接口
	 * @param req
	 * @param resp
	 * @return(待联调)
	 * @throws Exception
	 */
	public ReturnInfo queUserCount(QueUserCountReq req,QueUserCountResp resp) throws Exception{
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
//		
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
		QueUserCountBossReq req2Boss = getQueUserCountBossReq(loginInfo, req);
		
		String bossJsonStr = getBossHttpInfOutput(req.getBizorderid(), ServerCityBossInterface.QUE_USERCOUNTWG, req2Boss, loginInfo);
		
		handlerUserCount(bossJsonStr,resp);
		return returnInfo;
	}
	
	private void handlerUserCount(String jsonStr,QueUserCountResp resp) throws Exception{
		QueUserCountResp bossResp = (QueUserCountResp) BeanUtil.jsonToObject(jsonStr, QueUserCountResp.class);
		BeanUtils.copyProperties(resp, bossResp);
	}
	
	private QueUserCountBossReq getQueUserCountBossReq(LoginInfo loginInfo,QueUserCountReq req) throws Exception{
		QueUserCountBossReq req2Boss = new QueUserCountBossReq();
		req2Boss.setCurrentPage(req.getCurrentPage());
		req2Boss.setPagesize(req.getPagesize());
		req2Boss.setCusttype(req.getCusttype());
		req2Boss.setPermark(req.getPermark());
		if(StringUtils.isBlank(req.getNetids())){
			req2Boss.setNetids(pubService.getGridcodes(loginInfo));	
		}else{
			req2Boss.setNetids(req.getNetids());
		}
		
		return req2Boss;
	}
	
	
	/**
	 * 设备销售报表接口
	 * @param req
	 * @param resp
	 * @return(待联调,需要提供查询支付方式的接口)
	 * @throws Exception
	 */
	public ReturnInfo queDevSalesOper(QueDevSalesOperReq req,QueDevSalesOperResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
//		
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
		QueDevSalesOperBossReq req2Boss = getQueDevSalesOperBossReq(loginInfo,req);
		
		String bossJsonStr = getBossHttpInfOutput(req.getBizorderid(), ServerCityBossInterface.QUE_DEVSALESOPERWG, req2Boss, loginInfo);
		handlerDevSalesResp(bossJsonStr,resp);
		return returnInfo;
	}
	
	private void handlerDevSalesResp(String jsonStr,QueDevSalesOperResp resp) throws Exception{
		QueDevSalesOperResp bossResp = (QueDevSalesOperResp) BeanUtil.jsonToObject(jsonStr, QueDevSalesOperResp.class);
		BeanUtils.copyProperties(resp, bossResp);
	}
	
	private QueDevSalesOperBossReq getQueDevSalesOperBossReq(LoginInfo loginInfo,QueDevSalesOperReq req){
		QueDevSalesOperBossReq req2Boss = new QueDevSalesOperBossReq();
		req2Boss.setDevpids(req.getDevpids());
		req2Boss.setEtime(req.getEtime());
		req2Boss.setKinds(req.getKinds());
		req2Boss.setOperid(loginInfo.getOperid());
		req2Boss.setPayways(req.getPayways());
		req2Boss.setStime(req.getStime());
		req2Boss.setSubkinds(req.getSubkinds());
		return req2Boss;
	}
	
	/**
	 * 客户确认未收款查询接口
	 * @param req
	 * @param resp
	 * @return(待联调)
	 * @throws Exception
	 */
	public ReturnInfo queConfirmFees(QueConfirmFeeReq req,QueConfirmFeeResp resp) throws Exception{
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
		QueConfirmFeeBossReq req2Boss = getQueConfirmFeeBossReq(req, loginInfo);
		
		String bossJsonStr = getBossHttpInfOutput(req.getBizorderid(), ServerCityBossInterface.QUE_CONFIRMFEEWG, req2Boss, loginInfo);
		handlerConfimFeeResp(bossJsonStr,resp);
		
		return returnInfo;
	}
	
	private void handlerConfimFeeResp(String jsonStr,QueConfirmFeeResp resp) throws Exception{
		QueConfirmFeeResp bossResp = (QueConfirmFeeResp) BeanUtil.jsonToObject(jsonStr, QueConfirmFeeResp.class);
		BeanUtils.copyProperties(resp, bossResp);
	}
	
	private QueConfirmFeeBossReq getQueConfirmFeeBossReq(QueConfirmFeeReq req,LoginInfo loginInfo) throws Exception{
		QueConfirmFeeBossReq req2Boss = new QueConfirmFeeBossReq();
		req2Boss.setCurrentPage(req.getCurrentPage());
		req2Boss.setPagesize(req.getPagesize());
		if(StringUtils.isNotBlank(req.getGridcode())){
			req2Boss.setNetids(req.getGridcode());
		}else{
			req2Boss.setNetids(pubService.getGridcodes(loginInfo));
		}
		return req2Boss;
	}
	
	
	
	/**
	 * 营业日报查询接口
	 * @param req
	 * @param resp
	 * @return(待联调,需提供查询支付方式跟业务类型接口给app)
	 * @throws Exception
	 */
	public ReturnInfo queWorkDayReport(QueWorkDayReportReq req,QueWorkDayReportResp resp) throws Exception{
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
		QueWorkDayReportBpssReq req2Boss = getQueWorkDayReportBpssReq(loginInfo, req);
		
		String bossJsonStr = getBossHttpInfOutput(req.getBizorderid(), ServerCityBossInterface.QUE_WORKDAYREPORTWG, req2Boss, loginInfo);
		handlerReportResp(bossJsonStr,resp);
		return returnInfo;
	}
	
	private void handlerReportResp(String jsonStr,QueWorkDayReportResp resp) throws Exception{
		QueWorkDayReportResp bossResp = (QueWorkDayReportResp) BeanUtil.jsonToObject(jsonStr, QueWorkDayReportResp.class);
		BeanUtils.copyProperties(resp, bossResp);
	}
	
	private QueWorkDayReportBpssReq getQueWorkDayReportBpssReq(LoginInfo loginInfo,QueWorkDayReportReq req){
		QueWorkDayReportBpssReq req2Boss = new QueWorkDayReportBpssReq();
		req2Boss.setAreaid(loginInfo.getAreaid());
		req2Boss.setEtime(req.getEtime());
		req2Boss.setOperid(loginInfo.getOperid());
		req2Boss.setPayway(req.getPayways());
		req2Boss.setPermark(req.getPermark());
		req2Boss.setRfeecode(req.getRfeecode());
		req2Boss.setStime(req.getStime());
		return req2Boss;
	}
	
	private LoginInfo getUserLogin(){
		LoginInfo  loginInfo = new LoginInfo();
		loginInfo.setAreaid(1422l);
		loginInfo.setCity("1");
		loginInfo.setDeptid(232205l);
		loginInfo.setLoginname("715010");
		loginInfo.setOperid(655067l);
		return loginInfo;
	}
	
	/**
	 * 网格收入指标查询
	 * @param req
	 * @param resp
	 * @return(待联调)
	 * @throws Exception
	 */
	public ReturnInfo queFeesInfo(QueFeesInfoReq req,QueFeesInfoResp resp) throws Exception{

		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
		QueFeesInfoBossReq req2Boss = getQueFeesInfoBossReq(req, loginInfo);
		String bossJsonStr = getBossHttpInfOutput(req.getBizorderid(), ServerCityBossInterface.GET_FEES_INFO, req2Boss, loginInfo); 
		hanlderFeesInfoResp(bossJsonStr,resp);
		return returnInfo;
	}
	
	private QueFeesInfoBossReq getQueFeesInfoBossReq(QueFeesInfoReq req,LoginInfo loginInfo){
		QueFeesInfoBossReq req2Boss = new QueFeesInfoBossReq();
		req2Boss.setDeptid(loginInfo.getDeptid());
		req2Boss.setSday(req.getSday());
		req2Boss.setEday(req.getEday());
		req2Boss.setOperid(loginInfo.getOperid());
		req2Boss.setPaywaps(req.getPaywaps());
		return req2Boss;
	}
	
	private void hanlderFeesInfoResp(String jsonStr,QueFeesInfoResp resp) throws Exception{
		QueFeesInfoResp bossResp = (QueFeesInfoResp) BeanUtil.jsonToObject(jsonStr, QueFeesInfoResp.class);
		BeanUtils.copyProperties(resp, bossResp);
	}
	
	/**
	 * 网格积分排名查询
	 * @param req
	 * @param resp
	 * @return(待联调)
	 * @throws Exception
	 */
	public ReturnInfo quePointInfo(QuePointInfoReq req,QuePointInfoResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
		QuePointInfoBossReq req2Boss = getQuePointInfoBossReq(req, loginInfo);
		String bossStr = getBossHttpInfOutput(req.getBizorderid(), ServerCityBossInterface.GET_POINT_INFO, req2Boss, loginInfo);
		handlerPointResp(bossStr,resp);
		return returnInfo;
	}
	
	
	private void handlerPointResp(String jsonStr,QuePointInfoResp resp) throws Exception{
		QuePointInfoResp bossResp = (QuePointInfoResp) BeanUtil.jsonToObject(jsonStr, QuePointInfoResp.class);
		BeanUtils.copyProperties(resp, bossResp);
	}
	
	private QuePointInfoBossReq getQuePointInfoBossReq(QuePointInfoReq req,LoginInfo loginInfo) throws Exception{
		QuePointInfoBossReq req2Boss = new QuePointInfoBossReq();
		req2Boss.setPatchs(req.getPatchs());
		if(req2Boss.getPatchs() == null || req2Boss.getPatchs().isEmpty()){
			List<PointInfoBean> datas = new ArrayList<PointInfoBean>();
			String codeStrs = pubService.getGridcodes(loginInfo);
			String[] codes = codeStrs.split(",");
			for(String code:codes){
				datas.add(new PointInfoBean(code));
			}
			req2Boss.setPatchs(datas);
		}
		return req2Boss;
	}
}
