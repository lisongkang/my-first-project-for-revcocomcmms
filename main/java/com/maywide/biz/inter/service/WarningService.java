package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SpringBeanUtil;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.queTagCusts.CmpCustInfo;
import com.maywide.biz.inter.pojo.queTagCusts.CmpResultBean;
import com.maywide.biz.inter.pojo.queTagCusts.QueTagCustListReq;
import com.maywide.biz.inter.pojo.queTagCusts.QueTagCustListResp;
import com.maywide.biz.inter.pojo.queTagCusts.TagCustInfo;
import com.maywide.biz.inter.pojo.queWarnTag.DataQueryResult;
import com.maywide.biz.inter.pojo.queWarnTag.QueWarnTagListReq;
import com.maywide.biz.inter.pojo.queWarnTag.QueWarnTagListResp;
import com.maywide.biz.manage.tag.entity.SysTag;
import com.maywide.core.dao.support.Page;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.CheckUtils;

@Service
public class WarningService extends CommonService{
	
	@Autowired
	private CustSpreadService tmpService;
	
	
	/**
	 * 查询预警标签
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queWarningTagList(QueWarnTagListReq req,QueWarnTagListResp resp) throws Exception{
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
		List params = new ArrayList();
		params.add(loginInfo.getCity());
		params.add("Y");
		
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT a.TAGID tagId,a.TAGNAME tagName,0 custcount");
		sb.append("		FROM sys_tag a");
		sb.append("		WHERE a.city = ?");
		sb.append("		AND a.ISSHOW = ?");
		
		List<DataQueryResult> datas = getDAO().find(sb.toString(), DataQueryResult.class, params.toArray());
		resp.setDatas(datas);
		
		return returnInfo;
	}
	
	/**
	 * 查询该预警标签下的所有客户
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queTagCustList(QueTagCustListReq req,QueTagCustListResp resp) throws Exception{
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getTagId(), "查询的标签id不能为空");
		
		SysTag sysTag = (SysTag) getDAO().find(SysTag.class, Long.parseLong(req.getTagId()));
		if(sysTag == null){
			CheckUtils.checkNull(null, "根据当前标签查询不到相关信息");
		}
		
		int limit = StringUtils.isBlank(req.getLimit())?30:Integer.parseInt(req.getLimit());
		
		
		resp.setMemo(sysTag.getTagdesc());
		resp.setTagName(sysTag.getTagname());
		
		String dataResultStr = getCmpHttpKeyInfoOutPut(BizConstant.CmpPath.CUST_LIST, getParamsMap(req, loginInfo,limit,sysTag.getTagcode()));
		CmpResultBean result = (CmpResultBean) BeanUtil.jsonToObject(dataResultStr, CmpResultBean.class);
		resp.setCount(result.getTotalcount());
		resp.setDatas(getTagCustInfo4Result(result));
		
		return returnInfo;
	}
	
	private List<TagCustInfo> getTagCustInfo4Result(CmpResultBean result) throws Exception{
		CheckUtils.checkNull(result,"暂无该标签的数据信息");
		List<TagCustInfo> datas = new ArrayList<TagCustInfo>();
		if(result.getData() != null){
			for(CmpCustInfo info:result.getData()){
				TagCustInfo custInfo = new TagCustInfo();
				custInfo.setCustid(Long.parseLong(info.getCustid()));
				custInfo.setCustName(info.getCustname());
				custInfo.setLinkAddr(info.getAddrs());
				custInfo.setMobile(info.getTelephones());
				datas.add(custInfo);
			}
		}
		return datas;
	}
	
	private Map<String, String> getParamsMap(QueTagCustListReq req,LoginInfo loginInfo,int limit,String tagCode){
		Map<String,String> map = new HashMap<String, String>();
		map.put("tagid", tagCode);
		map.put("city", loginInfo.getCity());
//		map.put("areaid",loginInfo.getAreaid().toString());
		map.put("gridid", req.getGridCode());
		if(req.getIndex() <= -1){
			map.put("ispage","0");
		}else{
			map.put("ispage","1");
		}
		map.put("pageidx", req.getIndex()+"");
		map.put("pagesize", req.getLimit());
		return map;
	}

}
