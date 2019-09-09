package com.maywide.biz.inter.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.inter.pojo.queTagByCustid.CmpCustTag;
import com.maywide.biz.inter.pojo.queTagByCustid.QueTagByCustidReq;
import com.maywide.biz.inter.pojo.queTagByCustid.QueTagByCustidResp;
import com.maywide.biz.inter.pojo.queWorkOrderListByCustid.CustomerWorkTagBean;
import com.maywide.biz.inter.pojo.queWorkOrderListByCustid.QueWorkOrderListByCustidReq;
import com.maywide.biz.inter.pojo.queWorkOrderListByCustid.QueWorkOrderListByCustidResp;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.CheckUtils;

@Service
public class CmpTagService extends CommonService {

	
	public ReturnInfo queTagByCustid(QueTagByCustidReq req,QueTagByCustidResp resp) throws Exception{
		
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		
		CheckUtils.checkNull(req.getCustid(), "客户编号不能为空");
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("custid", req.getCustid().toString());
		
		String  dataResultStr= getCmpHttpKeyInfoOutPut(BizConstant.CmpPath.TAG_FOR_CUSTID, map,true);
		CmpCustTag dataTag  = (CmpCustTag) BeanUtil.jsonToObject(dataResultStr, CmpCustTag.class);
		resp.setTagData(dataTag);
		return returnInfo;
	}
	
	
	public ReturnInfo queWorkOrderListByCustid(QueWorkOrderListByCustidReq req,QueWorkOrderListByCustidResp resp) throws Exception{
		
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("custid", req.getCustid().toString());
		
		String  dataResultStr= getCmpHttpKeyInfoOutPut(BizConstant.CmpPath.WORK_ORDER_LIST_CUSTID, map,true);
		CustomerWorkTagBean workTagBean = (CustomerWorkTagBean) BeanUtil.jsonToObject(dataResultStr, CustomerWorkTagBean.class);
		resp.setData(workTagBean);
		return returnInfo;
		
	}
	
}
