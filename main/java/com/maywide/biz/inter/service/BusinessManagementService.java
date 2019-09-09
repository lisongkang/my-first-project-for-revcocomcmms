package com.maywide.biz.inter.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.pojo.quebuslistinfo.QueBuslistinfoBossChildResp;
import com.maywide.biz.inter.pojo.quebuslistinfo.QueBuslistinfoBossReq;
import com.maywide.biz.inter.pojo.quebuslistinfo.QueBuslistinfoBossResp;
import com.maywide.biz.inter.pojo.quebuslistinfo.QueBuslistinfoInterReq;
import com.maywide.biz.inter.pojo.quebuslistinfo.QueBuslistinfoInterResp;
import com.maywide.biz.inter.pojo.quebustrackinfo.QueBustrackinfoBossReq;
import com.maywide.biz.inter.pojo.quebustrackinfo.QueBustrackinfoBossResp;
import com.maywide.biz.inter.pojo.quebustrackinfo.QueBustrackinfoInterReq;
import com.maywide.biz.inter.pojo.quebustrackinfo.QueBustrackinfoInterResp;
import com.maywide.biz.inter.pojo.quebustraillistinfo.QueBustraillistinfoBossChildResp;
import com.maywide.biz.inter.pojo.quebustraillistinfo.QueBustraillistinfoBossReq;
import com.maywide.biz.inter.pojo.quebustraillistinfo.QueBustraillistinfoBossResp;
import com.maywide.biz.inter.pojo.quebustraillistinfo.QueBustraillistinfoInterReq;
import com.maywide.biz.inter.pojo.quebustraillistinfo.QueBustraillistinfoInterResp;
import com.maywide.biz.inter.pojo.quegrpbyareainfo.QueGrpbyareaInfoInterReq;
import com.maywide.biz.inter.pojo.quegrpbyareainfo.QueGrpbyareaInfoInterResp;
import com.maywide.biz.inter.pojo.roamctrl.RoamCtrlBossReq;
import com.maywide.biz.inter.pojo.roamctrl.RoamCtrlInterReq;
import com.maywide.biz.inter.pojo.savebusinfo.SaveBusinfoBossReq;
import com.maywide.biz.inter.pojo.savebusinfo.SaveBusinfoInterReq;
import com.maywide.biz.inter.pojo.savebusinfo.SaveBusinfoInterResp;
import com.maywide.biz.inter.pojo.savebustrackinfo.SaveBustrackinfoBossReq;
import com.maywide.biz.inter.pojo.savebustrackinfo.SaveBustrackinfoInterReq;
import com.maywide.biz.inter.pojo.savebustrackinfo.SaveBustrackinfoInterResp;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.util.BeanUtils;
import com.maywide.test.InterTest;
@Service
@Transactional(rollbackFor = Exception.class)
public class BusinessManagementService extends CommonService {
	@Autowired
	private ParamService paramService;

	/**
	 * 1.2.	查询商机集客经理接口
	 * 描述：根据业务区查询集客经理信息列表。
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queGrpbyareaInfo(QueGrpbyareaInfoInterReq req,QueGrpbyareaInfoInterResp resp) throws Exception{
//	public ReturnInfo queGrpbyareaInfo(QueGrpbyareaInfoInterReq req) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = getLoginInfo();
		//LoginInfo loginInfo = InterTest.getGridTestLoginInfo();
		/*QueGrpbyareaInfoBossReq bossReq = new QueGrpbyareaInfoBossReq();
		//把请求转成boss入参
		BeanUtils.copyPropertiesNotSuperClass(bossReq, req);
		String serverCode = BizConstant.BusinessManagement.QUE_GRPBYAREA_INFO;
		// 调用BOSS接口
		String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(), serverCode, bossReq, loginInfo);
		//把boss返回的出参转成集客的出参
		QueGrpbyareaInfoBossResp bossResp = (QueGrpbyareaInfoBossResp) this.copyBossResp2InterResp(resp,
				QueGrpbyareaInfoBossResp.class, bossRespOutput);*/
		JSONObject requestContent = new JSONObject();

		requestContent.put("areaid", req.getAreaid());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.setDateFormat(dateFormat);

		String response = bossHttpClientService.requestPost(req.getBizorderid(),
				BizConstant.BusinessManagement.QUE_GRPBYAREA_INFO, requestContent.toString(), loginInfo);
		if (StringUtils.isEmpty(response)) {
			throw new BusinessException("BOSS接口调用出错，没有返回数据");
		}

		JsonNode nodeTree = objectMapper.readTree(response);
		parseBossResponse(nodeTree, BizConstant.BusinessManagement.QUE_GRPBYAREA_INFO);

		returnInfo.setMessage(nodeTree.get("output").toString());
		return returnInfo;
	}
	/**
	 * 1.3.	查询商机列表信息接口
	 * 描述：根据业务区，集客经理，商机名称，录入时间段查询商机信息列表。
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queBuslistinfo(QueBuslistinfoInterReq req,QueBuslistinfoInterResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = getLoginInfo();
		//		LoginInfo loginInfo = InterTest.getGridTestLoginInfo();
		QueBuslistinfoBossReq bossReq = new QueBuslistinfoBossReq();
		//把请求转成boss入参
		BeanUtils.copyPropertiesNotSuperClass(bossReq, req);
		String serverCode = BizConstant.BusinessManagement.QUE_BUSLISTINFO;
		// 调用BOSS接口
		String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(), serverCode, bossReq, loginInfo);
		//把boss返回的出参转成集客的出参
		QueBuslistinfoBossResp bossResp = (QueBuslistinfoBossResp) this.copyBossResp2InterResp(resp,
				QueBuslistinfoBossResp.class, bossRespOutput);
		List params = new ArrayList();
		Map<String,String> map = paramService.getKeyValue("SYS_GRPCUSTMANAGER_NAME", "@DYNAMIC_DETAIL", params);
		
		List<QueBuslistinfoBossChildResp> buslist = resp.getBuslist();
		if(buslist!=null){
			for(QueBuslistinfoBossChildResp item:buslist){
				String grpmanagerid = item.getGrpmanagerid();
				item.setGrpmanagername(map.get(grpmanagerid));
			}
		}
		/*for(int i=0;i<buslist.size();i++){
			QueBuslistinfoBossChildResp item = buslist.get(i);
			String grpmanagerid = item.getGrpmanagerid();
			item.setGrpmanagername(map.get(grpmanagerid));
		}*/
//		returnInfo.setMessage(bossRespOutput);
		return returnInfo;
	}
	/**
	 * 1.4.	新建or修改商机信息接口
	 * 描述：新增或者修改商机信息。
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo saveBusinfo(SaveBusinfoInterReq req,SaveBusinfoInterResp resp) throws Exception{
//	public ReturnInfo saveBusinfo(SaveBusinfoInterReq req) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = getLoginInfo();
		//LoginInfo loginInfo = InterTest.getGridTestLoginInfo();
		SaveBusinfoBossReq bossReq = new SaveBusinfoBossReq();
		//把请求转成boss入参
		BeanUtils.copyPropertiesNotSuperClass(bossReq, req);
		String serverCode = BizConstant.BusinessManagement.SAVE_BUSINFO;
		// 调用BOSS接口
		String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(), serverCode, bossReq, loginInfo);
		//把boss返回的出参转成集客的出参
//		SaveBusinfoBossResp bossResp = (SaveBusinfoBossResp) this.copyBossResp2InterResp(resp,
//				SaveBusinfoBossResp.class, bossRespOutput);

		return returnInfo;
	}
	/**
	 * 1.5.	查询商机轨迹列表接口
	 * 描述：根据业务区查询集客经理信息列表。
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queBustraillistinfo(QueBustraillistinfoInterReq req,QueBustraillistinfoInterResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = getLoginInfo();
//		LoginInfo loginInfo = InterTest.getGridTestLoginInfo();
		QueBustraillistinfoBossReq bossReq = new QueBustraillistinfoBossReq();
		//把请求转成boss入参
		BeanUtils.copyPropertiesNotSuperClass(bossReq, req);
		String serverCode = BizConstant.BusinessManagement.QUE_BUSTRAILLISTINFO;
		// 调用BOSS接口
		String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(), serverCode, bossReq, loginInfo);
		//把boss返回的出参转成集客的出参
		QueBustraillistinfoBossResp bossResp = (QueBustraillistinfoBossResp) this.copyBossResp2InterResp(resp,
				QueBustraillistinfoBossResp.class, bossRespOutput);
		List params = new ArrayList();
		Map<String,String> map = paramService.getKeyValue("SYS_GRPCUSTMANAGER_NAME", "@DYNAMIC_DETAIL", params);
		
		List<QueBustraillistinfoBossChildResp> buslist = resp.getBuslist();
		if(buslist!=null){
			for(QueBustraillistinfoBossChildResp item:buslist){
				String perator = item.getOperator();
				item.setOperatorname(map.get(perator));
			}
		}
		return returnInfo;
	}
	
	/**
	 * 1.6.	查询商机跟踪信息接口
	 * 描述：查询商跟踪信息接口。
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queBustrackinfo(QueBustrackinfoInterReq req,QueBustrackinfoInterResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = getLoginInfo();
		//LoginInfo loginInfo = InterTest.getGridTestLoginInfo();
		QueBustrackinfoBossReq bossReq = new QueBustrackinfoBossReq();
		//把请求转成boss入参
		BeanUtils.copyPropertiesNotSuperClass(bossReq, req);
		String serverCode = BizConstant.BusinessManagement.QUE_BUSTRACKINFO;
		// 调用BOSS接口
		String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(), serverCode, bossReq, loginInfo);
		//把boss返回的出参转成集客的出参
		QueBustrackinfoBossResp bossResp = (QueBustrackinfoBossResp) this.copyBossResp2InterResp(resp,
				QueBustrackinfoBossResp.class, bossRespOutput);

		return returnInfo;
	}
	
	/**
	 * 1.7.	跟踪商机接口
	 * 描述：跟踪商机信息保存。
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo saveBustrackinfo(SaveBustrackinfoInterReq req,SaveBustrackinfoInterResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = getLoginInfo();
		//LoginInfo loginInfo = InterTest.getGridTestLoginInfo();
		SaveBustrackinfoBossReq bossReq = new SaveBustrackinfoBossReq();
		//把请求转成boss入参
		BeanUtils.copyPropertiesNotSuperClass(bossReq, req);
		String serverCode = BizConstant.BusinessManagement.SAVE_BUSTRACKINFO;
		// 调用BOSS接口
		String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(), serverCode, bossReq, loginInfo);
		//把boss返回的出参转成集客的出参
//		SaveBustrackinfoBossResp bossResp = (SaveBustrackinfoBossResp) this.copyBossResp2InterResp(resp,
//				SaveBustrackinfoBossResp.class, bossRespOutput);

		return returnInfo;
	}
	/**
	 * 漫游控制
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo roamCtrl(RoamCtrlInterReq req) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		LoginInfo loginInfo = getLoginInfo();
//		LoginInfo loginInfo = InterTest.getGridTestLoginInfo();
		RoamCtrlBossReq bossReq = new RoamCtrlBossReq();
		//把请求转成boss入参
		BeanUtils.copyPropertiesNotSuperClass(bossReq, req);
		String serverCode = BizConstant.BusinessManagement.ROAM_CTRL;
	// 调用BOSS接口，返回的status如果不为0就会报异常，0：数据为null或者有数据，如果有异常会抛出把数据显示在message中
		String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(), serverCode, bossReq, loginInfo);
		returnInfo.setMessage(bossRespOutput);
		return returnInfo;
	}
}
