package com.maywide.biz.iomproj.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.iomproj.pojo.QueImoprojListBean;
import com.maywide.biz.iomproj.pojo.QueImoprojListReq;
import com.maywide.biz.iomproj.pojo.QueImoprojReq;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.CheckUtils;

@Service
public class RequestIomporjService extends CommonService{

	/**
	 *查询装维工单信息
	 * @param req
	 * @param datas
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queImoprojList(QueImoprojReq req,ArrayList<QueImoprojListBean> datas) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		String operName = "cmmsData";
		QueImoprojListReq req2 = new QueImoprojListReq(req);
		String result = getImoprojRespone(operName,req2);
		Type type = new TypeToken<List<QueImoprojListBean>>() {  
        }.getType();
		List<QueImoprojListBean> list = new Gson().fromJson(result, type);
		if(list != null && !list.isEmpty()){
			datas.addAll(list);
		}
		return returnInfo;
	}
	
	
}
