package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.BossInterfaceService;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.inter.pojo.bizFeeinByAddAndOrder.BizFeeinByAddAndOrder2BossReq;
import com.maywide.biz.inter.pojo.bizFeeinByAddAndOrder.BizFeeinByAddAndOrderReq;
import com.maywide.biz.inter.pojo.bizFeeinByAddAndOrder.BizFeeinByAddAndOrderResp;
import com.maywide.biz.inter.pojo.bizfeein.BizFeeinBossResp;
import com.maywide.biz.inter.pojo.queFeeinServ.QueFeeinServBean;
import com.maywide.biz.inter.pojo.queFeeinServ.QueFeeinServParentBean;
import com.maywide.biz.inter.pojo.queFeeinServ.QueFeeinServReq;
import com.maywide.biz.inter.pojo.queFeeinServ.QueFeeinServResp;
import com.maywide.biz.market.entity.ApplyArrear;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;

@Service
public class InterUserPayService extends CommonService {

	@Autowired
	private PubService pubService;

	/** 快速续费产品列表 **/
	public ReturnInfo queFeeinServ(QueFeeinServReq req, QueFeeinServResp resp) throws Exception {

		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkEmpty(req.getCustid(), "客户编号不能为空!");
		if(StringUtils.isNotBlank(req.getLogindevs())){
			String[] logins = req.getLogindevs().split(",");
			List<QueFeeinServParentBean> allDatas = new ArrayList<QueFeeinServParentBean>();
			for(String logindevno:logins){
				QueFeeinServReq req2Boss = new QueFeeinServReq();
				req2Boss.setCustid(req.getCustid());
				req2Boss.setIspostpone(req.getIspostpone());
				req2Boss.setLogicdevno(logindevno);
				try{
//					String output = getBossHttpInfOutput(getDAO().getSequence("SEQ_BIZ_CUSTORDER_ID").toString(), 
//							BossInterfaceService.QUE_FEEIN_SERV, req2Boss,loginInfo);
					String output = getBossHttpInfOutput(getBizorderid(), 
							BossInterfaceService.QUE_FEEIN_SERV, req2Boss,loginInfo);
					
					allDatas.addAll(parseData(output));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			resp.setDatas(allDatas);
		}else{
			QueFeeinServReq req2Boss = new QueFeeinServReq();
			BeanUtils.copyPropertiesNotSuperClass(req2Boss, req);

			String output = getBossHttpInfOutput(req.getBizorderid(), BossInterfaceService.QUE_FEEIN_SERV, req2Boss,
					loginInfo);
			resp.setDatas(parseData(output));
		}
		
		return returnInfo;
	}

	private List<QueFeeinServParentBean> parseData(String output) {
		if (StringUtils.isBlank(output))
			return null;
		List<QueFeeinServBean> datas = new Gson().fromJson(output, new TypeToken<List<QueFeeinServBean>>() {
		}.getType());
		if (datas == null || datas.isEmpty()) {
			return new ArrayList<QueFeeinServParentBean>();
		}
		Map<String, List<QueFeeinServBean>> map = new HashMap<String, List<QueFeeinServBean>>();
		for (QueFeeinServBean logicDevno : datas) {
			List<QueFeeinServBean> list = map.get(logicDevno.getLogicdevno());
			if (list == null) {
				list = new ArrayList<QueFeeinServBean>();
				map.put(logicDevno.getLogicdevno(), list);
			}
			list.add(logicDevno);
		}
		List<QueFeeinServParentBean> contents = new ArrayList<QueFeeinServParentBean>();
		Iterator<Entry<String, List<QueFeeinServBean>>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, List<QueFeeinServBean>> entry = iterator.next();
			contents.add(new QueFeeinServParentBean(entry.getKey(), entry.getValue()));
		}
		return contents;
	}

	public ReturnInfo bizFeeinByAddAndOrder(BizFeeinByAddAndOrderReq req, BizFeeinByAddAndOrderResp resp)
			throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkEmpty(req.getCustid(), "客户编号不能为空");
		
		BizFeeinByAddAndOrder2BossReq req2Boss = getReq2Boss(req);
		bizCustOrder(req, loginInfo);
		
		String jsonStr = getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.BIZ_FEEIN_AND_ORDER, req2Boss, loginInfo);
		this.savePortalOrder(req.getBizorderid(), jsonStr);
		copyBossResp2InterResp4doFeein(resp,jsonStr);
		regApplyArrear4doFeein(req, loginInfo,Double.parseDouble(resp.getSums()));	
		resp.setCustorderid(req.getBizorderid());
		return returnInfo;
	}
	
	private void copyBossResp2InterResp4doFeein(BizFeeinByAddAndOrderResp resp, String jsonStr) throws Exception {

		BizFeeinBossResp bossResp = (BizFeeinBossResp) BeanUtil.jsonToObject(jsonStr, BizFeeinBossResp.class);

		// 如果字段是一样的，这里可以偷懒一下，
		// 后面如果接口对外字段和boss返回字段不一致，则自行写使用set方法设置值
		// BeanUtils.copyProperties(resp, bossResp);
//		resp.setBossorderid(bossResp.getOrderid());
		resp.setFeename(bossResp.getFeename());
		resp.setOrdertype(bossResp.getOrdertype());
		resp.setSums(bossResp.getSums());

	}

	private BizFeeinByAddAndOrder2BossReq getReq2Boss(BizFeeinByAddAndOrderReq req){
		BizFeeinByAddAndOrder2BossReq req2Boss = new BizFeeinByAddAndOrder2BossReq();
		req2Boss.setBuff(req.getBuff());
		req2Boss.setChouseid(req.getChouseid());
		req2Boss.setCustid(req.getCustid());
		req2Boss.setGdno(req.getGdno());
		req2Boss.setGdnoid(req.getGdnoid());
		req2Boss.setIscrtorder(req.getIscrtorder());
		req2Boss.setKeyno(req.getKeyno());
		req2Boss.setOrderparams(req.getOrderparams());
		return req2Boss;
	}
	
	private CustOrder bizCustOrder(BizFeeinByAddAndOrderReq req, LoginInfo loginInfo) throws Exception {
		CustOrder custOrder = new CustOrder();
		custOrder.setAddr(req.getWhladdr());
		custOrder.setAreaid(loginInfo.getAreaid());
		custOrder.setCity(loginInfo.getCity());
		custOrder.setCustid(Long.parseLong(req.getCustid()));
		custOrder.setName(req.getName());
		custOrder.setOpcode(BizConstant.BossInterfaceService.BIZ_FEEIN);
		custOrder.setOperator(loginInfo.getOperid());
		custOrder.setOprdep(loginInfo.getDeptid());
		custOrder.setOptime(new Date());
		custOrder.setOrdercode(pubService.getOrderCode().toString());
		custOrder.setId(Long.valueOf(req.getBizorderid()));
		custOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.SYNC);
		custOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
		custOrder.setPortalOrder(null);

		getDAO().save(custOrder);

		return custOrder;
	}
	
	private void regApplyArrear4doFeein(BizFeeinByAddAndOrderReq req, LoginInfo loginInfo,Double fees) throws Exception {
		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getBizorderid(), "缴纳欠费接口：业务订单号不能为空");
		CheckUtils.checkEmpty(req.getCustid(), "缴纳欠费接口客户id不能为空");
		// CheckUtils.checkEmpty(req.getKeyno(), "登记缴纳欠信息：智能卡号[keyno]不能为空");
		CheckUtils.checkEmpty(req.getFees(), "登记缴纳欠信息：缴费金额[fees]不能为空");

		ApplyArrear applyArrear = new ApplyArrear();
		applyArrear.setCity(loginInfo.getCity());
		applyArrear.setCustid(Long.valueOf(req.getCustid()));
		// applyArrear.setEtime(etime);
		applyArrear.setFees(fees);
		applyArrear.setKeyno(req.getKeyno());
		applyArrear.setOrderid(Long.valueOf(req.getBizorderid()));
		applyArrear.setPermark(req.getPermark());
		// applyArrear.setPid(pid);
		// applyArrear.setRecid(recid);
		// applyArrear.setStime(stime);

		getDAO().save(applyArrear);

	}

}
