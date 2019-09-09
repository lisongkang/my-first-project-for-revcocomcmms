package com.maywide.biz.inter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maywide.biz.inter.pojo.queryOssLoginInfo.Exts;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.queryOssLoginInfo.OssLoginInfo;
import com.maywide.biz.inter.pojo.queryOssLoginInfo.QueryOssLoginInfoReq;
import com.maywide.biz.inter.pojo.queryOssLoginInfo.QueryOssLoginInfoResp;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.CheckUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class OssService extends CommonService{

	//OSS 用户登录，成功后得到Access-Token和Access-Uid
	public ReturnInfo queryOssLoginInfo(
			QueryOssLoginInfoResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		 LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		 CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username",loginInfo.getLoginname());
		PrvOperator prvOperator = new PrvOperator();
		prvOperator.setLoginname(loginInfo.getLoginname());
		List<PrvOperator> prvOperatorList =  getDAO().find(prvOperator);
		if (prvOperatorList!=null &&prvOperatorList.size()>0) {
			prvOperator = prvOperatorList.get(0);
		}
		params.put("password",prvOperator.getPasswd());

		JSONObject requestContent = new JSONObject();
		requestContent.put("relatedOrgCuid", loginInfo.getDeptid());

		params.put("exts", requestContent);

		QueryOssLoginInfoReq req = new QueryOssLoginInfoReq();
		req.setUsername(loginInfo.getLoginname());
		req.setPassword(prvOperator.getPasswd());
		
		Exts exts = new Exts();
		exts.setRelatedOrgCuid(loginInfo.getDeptid()+"");
		req.setExts(exts);

		String response = getOssHttpOutPut("ids.admin.boot/api/admin/user/login", req);
		
		OssLoginInfo ossLoginInfo = (OssLoginInfo) BeanUtil.jsonToObject(response, OssLoginInfo.class);
		if ("200".equals(ossLoginInfo.getStatusCode()) && ossLoginInfo.getData()!=null) {
			resp.setUid(ossLoginInfo.getData().getUid());
			resp.setToken(ossLoginInfo.getData().getToken());
		}


//		ossId

		
		return returnInfo;
	}
}
