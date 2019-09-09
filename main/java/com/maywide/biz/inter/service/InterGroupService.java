package com.maywide.biz.inter.service;

import org.springframework.stereotype.Service;

import com.maywide.biz.cons.BizConstant.BossInterfaceService;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.inter.pojo.createGroupCustInfo.CreateGroupCustInfoReq;
import com.maywide.biz.inter.pojo.createGroupCustInfo.CreateGroupCustInfoResp;
import com.maywide.biz.inter.pojo.queCustCityArea.QueCustCityAreaResp;
import com.maywide.biz.inter.pojo.queCustCityArea.QueGrpAreaCtlReq;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;

@Service
public class InterGroupService extends CommonService {
	

	public ReturnInfo queCustCityArea(QueCustCityAreaResp resp) throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();

		QueGrpAreaCtlReq req2Boss = new QueGrpAreaCtlReq();
		req2Boss.setMenuid("19909");
		
//		String responseStr = getBossHttpInfOutput(getDAO().getSequence("SEQ_BIZ_CUSTORDER_ID")
//				.toString(), BossInterfaceService.QUE_GRP_AREA_CTL, req2Boss, loginInfo);
		String responseStr = getBossHttpInfOutput(getBizorderid(), BossInterfaceService.QUE_GRP_AREA_CTL, req2Boss, loginInfo);
		
		System.out.println(responseStr);
		resp.setContent(responseStr);
		return returnInfo;
	}

	public ReturnInfo createGroupCustInfo(CreateGroupCustInfoReq req, CreateGroupCustInfoResp resp) throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		
		CreateGroupCustInfoReq req2Boss = new CreateGroupCustInfoReq();
		BeanUtils.copyPropertiesNotSuperClass(req2Boss, req);
		
		String responseStr = getBossHttpInfOutput(req.getBizorderid(), BossInterfaceService.BIZ_CREATECUSTINF, req2Boss, loginInfo);
		CreateGroupCustInfoResp bossResp = (CreateGroupCustInfoResp) BeanUtil.jsonToObject(responseStr, CreateGroupCustInfoResp.class);
		resp = bossResp;
		return returnInfo;
	}
	
}
