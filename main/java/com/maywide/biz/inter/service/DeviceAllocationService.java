package com.maywide.biz.inter.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.BossInterfaceService;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.inter.pojo.KindNameEnum;
import com.maywide.biz.inter.pojo.ResKindEnum;
import com.maywide.biz.inter.pojo.queDevstore.QueDevstoreReq;
import com.maywide.biz.inter.pojo.queDevstore.QueDevstoreResp;
import com.maywide.biz.inter.pojo.quesalesordercontent.QueSalesorderContentBossReq;
import com.maywide.biz.inter.pojo.quesalesordercontent.QueSalesorderContentBossResp;
import com.maywide.biz.inter.pojo.quesalesordercontent.QueSalesorderContentInterReq;
import com.maywide.biz.inter.pojo.quesalesordercontent.QueSalesorderContentInterResp;
import com.maywide.biz.inter.pojo.wflassignequipinfo.WflAssignEquipinfoBossReq;
import com.maywide.biz.inter.pojo.wflassignequipinfo.WflAssignEquipinfoInterReq;
import com.maywide.biz.inter.pojo.wflassignequipinfo.WflAssignEquipinfoInterResp;
import com.maywide.biz.inter.pojo.wflqueequipinfo.RollDevListBean;
import com.maywide.biz.inter.pojo.wflqueequipinfo.StepinfolistBean;
import com.maywide.biz.inter.pojo.wflqueequipinfo.WflQueEquipinfoBossChildResp;
import com.maywide.biz.inter.pojo.wflqueequipinfo.WflQueEquipinfoBossReq;
import com.maywide.biz.inter.pojo.wflqueequipinfo.WflQueEquipinfoInterReq;
import com.maywide.biz.inter.pojo.wflqueequipinfo.WflQueEquipinfoInterResp;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
@Service
@Transactional(rollbackFor = Exception.class)
public class DeviceAllocationService extends CommonService {
	@Autowired
	private PubService pubService;

	/**
	 * 1.1.	预报装设备分配查询接口
	 * @param req
	 * @param
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo wflQueEquipinfo(WflQueEquipinfoInterReq req,WflQueEquipinfoInterResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
//				LoginInfo loginInfo = InterTest.getGridTestLoginInfo();
		WflQueEquipinfoBossReq bossReq = new WflQueEquipinfoBossReq();
		//把请求转成boss入参
		BeanUtils.copyPropertiesNotSuperClass(bossReq, req);
		String serverCode = BizConstant.BossInterfaceService.WFL_QUE_EQUIPINFO;
		// 调用BOSS接口
		String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(), serverCode, bossReq, loginInfo);
		JSONArray array = new JSONArray(bossRespOutput);
		List<WflQueEquipinfoBossChildResp> bossResp = BeanUtil.jsonToObject(array, WflQueEquipinfoBossChildResp.class);
		fillKind(bossResp);
		resp.setWflQueEquipinfoList(bossResp);
		return returnInfo;
	}
	private void fillKind(List<WflQueEquipinfoBossChildResp> bossResp) {
		for(WflQueEquipinfoBossChildResp childResp:bossResp) {
			if(childResp.getStepinfolist() == null || childResp.getStepinfolist().isEmpty()) {
				continue;
			}
			for(StepinfolistBean bean:childResp.getStepinfolist()) {
				if(StringUtils.isNotBlank(bean.getResoucecode())) {
					bean.setKind(ResKindEnum.getKindByCode(bean.getResoucecode()));
				}
			}
		}
		for(WflQueEquipinfoBossChildResp childResp:bossResp) {
			if(childResp.getRollDevList() == null || childResp.getRollDevList().isEmpty()) {
				continue;
			}
			for(RollDevListBean bean:childResp.getRollDevList()) {
				if(StringUtils.isNotBlank(bean.getKind())) {
					bean.setKindname(KindNameEnum.getKindName(bean.getKind()));
				}
			}
		}
	}
	/**
	 * 1.2.	预报装设备分配接口
	 * @param req
	 * @param
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo wflAssignEquipinfo(WflAssignEquipinfoInterReq req,WflAssignEquipinfoInterResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		//				LoginInfo loginInfo = InterTest.getGridTestLoginInfo();
		WflAssignEquipinfoBossReq bossReq = new WflAssignEquipinfoBossReq();
		//把请求转成boss入参
		BeanUtils.copyPropertiesNotSuperClass(bossReq, req);
		String serverCode = BizConstant.BossInterfaceService.WFL_ASSIGN_EQUIPINFO;

		// 调用BOSS接口
		CustOrder custOrder = insertBizCustor(req,loginInfo);
		String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(), serverCode, bossReq, loginInfo);
		custOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.SYNC);
		DAO.save(custOrder);
		resp.setOrderid(custOrder.getId().toString());
		return returnInfo;
	}
	/**
	 * 1.3 录入销售单无纸化通过这个接口返回无纸化信息
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queSalesorderContent(QueSalesorderContentInterReq req,QueSalesorderContentInterResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		//		LoginInfo loginInfo = InterTest.getGridTestLoginInfo();
		QueSalesorderContentBossReq bossReq = new QueSalesorderContentBossReq();
		//把请求转成boss入参
		BeanUtils.copyPropertiesNotSuperClass(bossReq, req);
		String serverCode = BizConstant.BossInterfaceService.QUE_SALESORDER_CONTENT;
		// 调用BOSS接口
		String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(), serverCode, bossReq, loginInfo);
		//把boss返回的出参转成集客的出参
		QueSalesorderContentBossResp bossResp = (QueSalesorderContentBossResp) this.copyBossResp2InterResp(resp,
				QueSalesorderContentBossResp.class, bossRespOutput);
		return returnInfo;
	}

	

	private CustOrder insertBizCustor(WflAssignEquipinfoInterReq req, LoginInfo loginInfo) throws Exception {
		CustOrder custOrder = new CustOrder();
		custOrder.setId(Long.parseLong(req.getBizorderid()));
		custOrder.setAreaid(loginInfo.getAreaid());
		custOrder.setCity(loginInfo.getCity());
		custOrder.setOprdep(loginInfo.getDeptid());
		custOrder.setName(req.getCustName());
		custOrder.setCustid(Long.parseLong(req.getCustid()));
		custOrder.setOpcode(BizConstant.BossInterfaceService.WFL_ASSIGN_EQUIPINFO);
		custOrder.setAddr(req.getAddress());
		custOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
		custOrder.setOperator(loginInfo.getOperid());
		custOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.INIT);
		custOrder.setOptime(new Date());
		custOrder.setOrdercode(pubService.getOrderCode());
		custOrder.setPortalOrder(null);
		getDAO().save(custOrder);
		getDAO().flushSession();
		return custOrder;
	}
	
	/**
	 * 查询设备库存
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queDevstore(QueDevstoreReq req,QueDevstoreResp resp) throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		QueDevstoreReq req2Boss = getReq2Boss(req,loginInfo);
		String resultStr = getBossHttpInfOutput(req.getBizorderid(), BossInterfaceService.QUE_DEVSTORE, req2Boss, loginInfo);
		handleResult(resultStr,resp);
		return returnInfo;
	}
	
	private void handleResult(String jsonStr,QueDevstoreResp resp) throws Exception {
		QueDevstoreResp bossResp = (QueDevstoreResp) BeanUtil.jsonToObject(jsonStr, QueDevstoreResp.class);
		BeanUtils.copyProperties(resp, bossResp);
	}
	
	private QueDevstoreReq getReq2Boss(QueDevstoreReq req,LoginInfo loginInfo) throws Exception {
		QueDevstoreReq req2Boss = new QueDevstoreReq();
		BeanUtils.copyPropertiesNotSuperClass(req2Boss, req);
		//2019.08.28 boss 去除 tmpPlace字段
		//req2Boss.setTmpPlace(loginInfo.getDeptid().toString());
		return req2Boss;
	}

}
