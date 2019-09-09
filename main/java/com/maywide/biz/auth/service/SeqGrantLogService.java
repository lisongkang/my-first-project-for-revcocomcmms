package com.maywide.biz.auth.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.springframework.stereotype.Service;

import com.maywide.biz.auth.pojo.queSeqGrant.GrantlistBean;
import com.maywide.biz.auth.pojo.queSeqGrant.QueSeqGrantReq;
import com.maywide.biz.auth.pojo.queSeqGrant.QueSeqGrantResp;
import com.maywide.biz.auth.pojo.queSeqGrant.QueSeqGrantServiceResp;
import com.maywide.biz.auth.pojo.queSeqGrantLog.QueSeqGrantLogReq;
import com.maywide.biz.auth.pojo.queSeqGrantLog.QueSeqGrantLogResp;
import com.maywide.biz.auth.pojo.queSeqGrantLog.QueSeqGrantLogServiceResp;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.AuthServiceCode;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.queGatewayFault.QueGatewayFaultBO;
import com.maywide.biz.inter.pojo.queGatewayFault.QueGatewayFaultReq;
import com.maywide.biz.inter.pojo.queGatewayFault.QueGatewayFaultResp;
import com.maywide.biz.inter.pojo.queOrderPkgInfo.QueOrderPkgInfoResp;
import com.maywide.biz.inter.pojo.queSeqVlanLog.QueSeqVlanLogReq;
import com.maywide.biz.inter.pojo.queSeqVlanLog.QueSeqVlanLogResp;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;
import com.maywide.core.util.DateUtils;

@Service
public class SeqGrantLogService extends CommonService {

	/**
	 * 查询授权已发送记录
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queSeqGrant(QueSeqGrantReq req,QueSeqGrantResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkEmpty(req.getCustid(), "客户编号不能为空");
		QueSeqGrantReq req2Boss = new QueSeqGrantReq();
		BeanUtils.copyPropertiesNotSuperClass(req2Boss,req);
		String response = getAuthImplResponse(loginInfo, req.getBizorderid(),AuthServiceCode.QUE_SEQ_GRANT, req2Boss);
		invokeResp(response,resp);
		return returnInfo;
	}
	/**
	 * 解析参数
	 * @param response
	 * @param resp
	 * @throws Exception
	 */
	private void invokeResp(String response,QueSeqGrantResp resp) throws Exception{
		if(StringUtils.isBlank(response)){
			throw new BusinessException("接口请求出错");
		}
		QueSeqGrantServiceResp serviceResp = (QueSeqGrantServiceResp) BeanUtil.jsonToObject(response, QueSeqGrantServiceResp.class);
		if(!serviceResp.getRtcode().equals("000")){
			throw new BusinessException(serviceResp.getRtmsg());
		}
		resp.setCurrentpage(serviceResp.getCurrentpage());
		resp.setGrantlist(serviceResp.getGrantlist());
		resp.setPagesize(serviceResp.getPagesize());
		resp.setTotalrecords(serviceResp.getTotalrecords());
	}
	
	/**
	 * 查询授权未发送记录
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queSeqGrantLog(QueSeqGrantLogReq req,QueSeqGrantLogResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkEmpty(req.getCustid(), "客户编号不能为空");
		
		QueSeqGrantLogReq req2Boss = new QueSeqGrantLogReq();
		BeanUtils.copyPropertiesNotSuperClass(req2Boss,req);
		String response = getAuthImplResponse(loginInfo, req.getBizorderid(), AuthServiceCode.QUE_SEQ_GRANT_LOG, req2Boss);
		invokeResp(response,resp);
		return returnInfo;
	}
	
	private void invokeResp(String response,QueSeqGrantLogResp resp) throws Exception{
		if(StringUtils.isBlank(response)){
			throw new BusinessException("接口请求出错");
		}
		QueSeqGrantLogServiceResp serviceResp = (QueSeqGrantLogServiceResp) BeanUtil.jsonToObject(response, QueSeqGrantLogServiceResp.class);
		if(!serviceResp.getRtcode().equals("000")){
			throw new BusinessException(serviceResp.getRtmsg());
		}
		resp.setCurrentpage(serviceResp.getCurrentpage());
		resp.setGrantlist(serviceResp.getGrantlist());
		resp.setPagesize(serviceResp.getPagesize());
		resp.setTotalrecords(serviceResp.getTotalrecords());
	}
	
	/**
	 * 网口网管排障查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queGatewayFault(QueGatewayFaultReq req,QueGatewayFaultResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
		CheckUtils.checkNull(req, "请求对象不能为空");
		CheckUtils.checkEmpty(req.getCustid(), "客户编号不能为空");
		
		
		CheckUtils.checkEmpty(req.getCity(), "[city]不能为空");
//		CheckUtils.checkEmpty(req.getDevno(), "[devno]不能为空");
		
//		QueSeqGrantReq queSeqGrantBossReq= new QueSeqGrantReq();
//		queSeqGrantBossReq.setBizorderid(getDAO().getSequence("SEQ_BIZ_CUSTORDER_ID").toString());
////		queSeqGrantBossReq.setServid(req.getServid());
//		queSeqGrantBossReq.setCustid(req.getCustid());
////		queSeqGrantBossReq.setDevno(req.getDevno());
//
//		queSeqGrantBossReq.setPagesize("100");
		
		//第一个接口  获取 其中5个
		List<QueGatewayFaultBO> gatewayList = initGatewayList();
		if (req.getServid()!=null) {
			String[] servids = req.getServid().split(",");
			if (servids != null) {
				for (String servid : servids) {
					QueSeqGrantReq queSeqGrantBossReq = new QueSeqGrantReq();
					queSeqGrantBossReq.setServid(servid);
					queSeqGrantBossReq.setCustid(req.getCustid());
					queSeqGrantBossReq.setPagesize("100");
					queSeqGrant(queSeqGrantBossReq, loginInfo, 1, gatewayList);
				}
			}
		}
		
//		queSeqGrant(queSeqGrantBossReq, loginInfo, 1, gatewayList);
        
        //VLAN下发ACS查询
		if (StringUtils.isNotEmpty(req.getMac())) {
			String[] macs = req.getMac().split(",");
			if (macs!=null) {
				for (String mac : macs) {
					QueSeqVlanLog(gatewayList, loginInfo, req.getCity(), mac, null);
				}
			}
		}
		if (StringUtils.isNotEmpty(req.getSn())) {
			String[] sns = req.getSn().split(",");
			if (sns!=null) {
				for (String sn : sns) {
					QueSeqVlanLog(gatewayList, loginInfo, req.getCity(), null, sn);
				}
			}
		}
		
		
		
//        QueSeqVlanLogReq queSeqVlanLogReq = new QueSeqVlanLogReq();
//        queSeqVlanLogReq.setBizorderid(getDAO().getSequence("SEQ_BIZ_CUSTORDER_ID").toString());
//        queSeqVlanLogReq.setCity(req.getCity());
//        queSeqVlanLogReq.setMac(req.getMac());
//        queSeqVlanLogReq.setSn(req.getDevno());//把devno作为接口要的SN
//        queSeqVlanLogReq.setStarttime(DateUtils.getCurrentDate());
//        queSeqVlanLogReq.setEndtime(DateUtils.getNextDate());
//        String queSeqGrantBossresponse = getBossHttpInfOutput(queSeqVlanLogReq.getBizorderid(),
//        		BizConstant.BossInterfaceService.SEQ_VLAN_LOG, queSeqVlanLogReq, loginInfo);
//        JSONArray array = new JSONArray(queSeqGrantBossresponse);
//        List<QueSeqVlanLogResp> queSeqVlanLogBOList = BeanUtil.jsonToObject(array, QueSeqVlanLogResp.class);
//    
//		gatewayList = handleGatewayListStatusForType1(gatewayList, queSeqVlanLogBOList);
		
		resp.setGatewayList(gatewayList);
		
		
		return returnInfo;
	}
	
	/**
	 * 查询所有的授权数据,页码自增，每次100条，遍历处理
	 * @param queSeqGrantBossReq
	 * @param loginInfo
	 * @param currentpage
	 * @param gatewayList
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	private void queSeqGrant(QueSeqGrantReq queSeqGrantBossReq, LoginInfo loginInfo,
			int currentpage,List<QueGatewayFaultBO> gatewayList) throws NumberFormatException, Exception {
		
//		queSeqGrantBossReq.setBizorderid(getDAO().getSequence("SEQ_BIZ_CUSTORDER_ID").toString());
		queSeqGrantBossReq.setBizorderid(getBizorderid());
		queSeqGrantBossReq.setCurrentpage(currentpage + "");

		String queSeqGrantResponse = getAuthImplResponse(loginInfo,queSeqGrantBossReq.getBizorderid(),AuthServiceCode.QUE_SEQ_GRANT, queSeqGrantBossReq);

		QueSeqGrantResp queSeqGrantResp = new QueSeqGrantResp();
		invokeResp(queSeqGrantResponse, queSeqGrantResp);

		List<GrantlistBean> grantList = queSeqGrantResp.getGrantlist();

		//处理数据
		gatewayList = handleGatewayListStatusForType2(gatewayList, grantList);

		//总数大于当前页码*每页数量，还有下一页
		if (Integer.valueOf(queSeqGrantResp.getPagesize())>0 && Integer.valueOf(queSeqGrantResp.getTotalrecords())>0 
				&&Integer.valueOf(queSeqGrantResp.getTotalrecords()) > 
		    Integer.valueOf(queSeqGrantResp.getCurrentpage())* Integer.valueOf(queSeqGrantResp.getPagesize())) {
			currentpage++;
			queSeqGrant(queSeqGrantBossReq, loginInfo, currentpage,gatewayList);
		}
	}
	/**
	 * VLAN下发ACS查询
	 * @param gatewayList
	 * @param loginInfo
	 * @param city
	 * @param mac
	 * @param sn
	 * @throws Exception
	 */
	private void QueSeqVlanLog(List<QueGatewayFaultBO> gatewayList,LoginInfo loginInfo ,String city,String mac,String sn) throws Exception{
		QueSeqVlanLogReq queSeqVlanLogReq = new QueSeqVlanLogReq();
//        queSeqVlanLogReq.setBizorderid(getDAO().getSequence("SEQ_BIZ_CUSTORDER_ID").toString());
		queSeqVlanLogReq.setBizorderid(getBizorderid());
        queSeqVlanLogReq.setCity(city);
        queSeqVlanLogReq.setMac(mac);
        queSeqVlanLogReq.setSn(sn);
//        queSeqVlanLogReq.setStarttime(DateUtils.getCurrentDate());
        queSeqVlanLogReq.setStarttime(DateUtils.getFormatDateAdd(new Date(), -65, "yyyy-MM-dd"));
        queSeqVlanLogReq.setEndtime(DateUtils.getNextDate());
        String queSeqGrantBossresponse = getBossHttpInfOutput(queSeqVlanLogReq.getBizorderid(),
        		BizConstant.BossInterfaceService.SEQ_VLAN_LOG, queSeqVlanLogReq, loginInfo);
        JSONArray array = new JSONArray(queSeqGrantBossresponse);
        List<QueSeqVlanLogResp> queSeqVlanLogBOList = BeanUtil.jsonToObject(array, QueSeqVlanLogResp.class);
        gatewayList = handleGatewayListStatusForType1(gatewayList, queSeqVlanLogBOList);
	}
	
	/**
	 * 初始化网管数据，状态status默认未-1
	 * @return
	 */
	private List<QueGatewayFaultBO> initGatewayList(){
		List<QueGatewayFaultBO> gatewayList = new ArrayList<QueGatewayFaultBO>();
		QueGatewayFaultBO item = new QueGatewayFaultBO();
		item.setName("CA");
		item.setDesc("CA（集中NAGRA）接收BOSS发送的数字业务开通授权");
		item.setStatus("-1");
		item.setType("2");
		gatewayList.add(item);
		
		item = new QueGatewayFaultBO();
		item.setName("全家看");
		item.setDesc("全家看（省网ACS网关）接收BOSS发送的1路或4路业务开通授权");
		item.setStatus("-1");
		item.setType("2");
		gatewayList.add(item);
		
		item = new QueGatewayFaultBO();
		item.setName("互动");
		item.setDesc("BO(集中同洲VOD) 接收BOSS发送的互动业务开通授权");
		item.setStatus("-1");
		item.setType("2");
		gatewayList.add(item);
		
		item = new QueGatewayFaultBO();
		item.setName("宽带");
		item.setDesc("省RADIUS接收BOSS发送的宽带开通授权");
		item.setStatus("-1");
		item.setType("2");
		gatewayList.add(item);
		
		item = new QueGatewayFaultBO();
		item.setName("IPTV");
		item.setDesc("通过IPTV通道开通服务");
		item.setStatus("-1");
		item.setType("2");
		gatewayList.add(item);
		
		
		
		
		item = new QueGatewayFaultBO();
		item.setName("网管");
		item.setDesc("ONU设备上线后，向网管拿到宽带VLAN、互动VLAN信息，网管将宽带VLAN、互动VLAN信息发送给BOSS");
		item.setStatus("-1");
		item.setType("1");
		gatewayList.add(item);
		
		item = new QueGatewayFaultBO();
		item.setName("ACS");
		item.setDesc("ACS接收BOSS 下发的宽带VLAN、互动VLAN、PPPOE信息");
		item.setStatus("-1");
		item.setType("1");
		gatewayList.add(item);
		
		item = new QueGatewayFaultBO();
		item.setName("BOSS");
		item.setDesc("1.BOSS受理网关开户，开户成功后，下发授权到CA、全家看、互动、宽带;2.BOSS接收网管发送宽带VLAN、互动VLAN信息");
		item.setStatus("1");
		item.setType("1");
		gatewayList.add(item);
		
		return gatewayList;
	}
	
	/**
	 * 处理授权已发送记录（CA+全家看+互动+宽带）
	 * @param gatewayList
	 * @param grantList
	 * @return
	 */
	private List<QueGatewayFaultBO> handleGatewayListStatusForType2(List<QueGatewayFaultBO> gatewayList,List<GrantlistBean> grantList){
		if (grantList == null) {
			return gatewayList;
		}
		for (GrantlistBean grant : grantList) {
			String concatType = grant.getItype() + ":" + grant.getSubtype2()
					+ ":" + grant.getOpcode2();
			int pos = -1;
			if ("CA:NAGRA_JZ:I0".equals(concatType)||"CA:NSCA_JZ:I0".equals(concatType)) {
				pos = 0;
			}
			if ("CA:ACS_JZ:I0".equals(concatType)) {
				pos = 1;
			}
			if ("VOD:TZVOD_JZ:KK".equals(concatType)) {
				pos = 2;
			}
			if ("CM:DG_YXCM:I0".equals(concatType)) {
				pos = 3;
			}
			if ("IPTV:IPTV_JZ:KK".equals(concatType)) {
				pos = 4;
			}
			if (pos >= 0) {//有找到对应的类型
				// 时间比对，已最后的时间为准
				if (StringUtils.isNotEmpty(gatewayList.get(pos).getOptime())) {
					if (grant.getOptime().compareTo(
							gatewayList.get(pos).getOptime()) > 0) {
						if (grant.getStatus().contains("成功")) {
							gatewayList.get(pos).setStatus("1");
						} else if (grant.getStatus().contains("失败")) {
							gatewayList.get(pos).setStatus("0");
						}
						gatewayList.get(pos).setOptime(grant.getOptime());
					}
				} else {
					gatewayList.get(pos).setOptime(grant.getOptime());
					if (grant.getStatus().contains("成功")) {
						gatewayList.get(pos).setStatus("1");
					} else if (grant.getStatus().contains("失败")) {
						gatewayList.get(pos).setStatus("0");
					}
				}
			}
		}

		return gatewayList;
	}
	
	/**
	 * 处理VLAN下发ACS查询 （网管+ACS）
	 * @param gatewayList
	 * @param queSeqVlanLogBOList
	 * @return
	 */
	private List<QueGatewayFaultBO> handleGatewayListStatusForType1(List<QueGatewayFaultBO> gatewayList,List<QueSeqVlanLogResp> queSeqVlanLogBOList ){
		if (queSeqVlanLogBOList == null) {
			return gatewayList;
		}
		for (QueSeqVlanLogResp seqVlan : queSeqVlanLogBOList) {
			int pos = -1;
			if ("0".equals(seqVlan.getSendtype())) {
				pos = 5;
			}
			if ("1".equals(seqVlan.getSendtype())) {
				pos = 6;
			}
			if (pos > 0) {
				// 时间比对，已最后的时间为准
				if (StringUtils.isNotEmpty(gatewayList.get(pos).getOptime())) {
					if (seqVlan.getOptime().compareTo(
							gatewayList.get(pos).getOptime()) > 0) {
						if ("000".equals(seqVlan.getRescode())) {
							gatewayList.get(pos).setStatus("1");
						} else {
							gatewayList.get(pos).setStatus("0");
						}
						gatewayList.get(pos).setOptime(seqVlan.getOptime());
					}
				} else {
					gatewayList.get(pos).setOptime(seqVlan.getOptime());
					if ("000".equals(seqVlan.getRescode())) {
						gatewayList.get(pos).setStatus("1");
					} else {
						gatewayList.get(pos).setStatus("0");
					}
				}
				
			}
		}
		return gatewayList;
	}
}
