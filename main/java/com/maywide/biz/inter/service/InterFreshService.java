package com.maywide.biz.inter.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.BizOpcode;
import com.maywide.biz.cons.BizConstant.BossInterfaceService;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.inter.pojo.bizFreshPbn.BizFreshPbnReq;
import com.maywide.biz.inter.pojo.bizFreshPbn.BizFreshPbnResp;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;

@Service
public class InterFreshService extends CommonService {

	
	@Autowired
    private PubService pubService;
	
	/**
	 * 二次刷新授权接口
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo bizFreshPbn(BizFreshPbnReq req,BizFreshPbnResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		
		bizBusiness(req,loginInfo,BossInterfaceService.BIZ_FRESHPBN,BizOpcode.BIZ_FRESHPBN,resp);
		
		return returnInfo;
	}
	
	
	private CustOrder initCustOrder(BizFreshPbnReq req,LoginInfo loginInfo
			,String opcode) throws Exception{
		 	CustOrder bizCustOrder = new CustOrder();
	        bizCustOrder.setAddr(req.getAddress());
	        bizCustOrder.setAreaid(loginInfo.getAreaid());
	        bizCustOrder.setCanceltime(null);
	        bizCustOrder.setCity(loginInfo.getCity());
	        bizCustOrder.setCustid(Long.valueOf(req.getCustid()));
	        bizCustOrder.setLockoper(null);
	        bizCustOrder.setName(req.getCustname());
	        bizCustOrder.setOpcode(opcode);
	        bizCustOrder.setOperator(loginInfo.getOperid());
	        bizCustOrder.setOprdep(loginInfo.getDeptid());
	        bizCustOrder.setOptime(new Date());
	        bizCustOrder.setOrdercode(pubService.getOrderCode().toString());
	        bizCustOrder.setId(Long.valueOf(req.getBizorderid()));
	        bizCustOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.INIT);
	        bizCustOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
	        bizCustOrder.setPortalOrder(null);
	        return bizCustOrder;
	}
	
	
	/**
	 * 重新分配vlan
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo bizFreshVlan(BizFreshPbnReq req,BizFreshPbnResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		
		bizBusiness(req,loginInfo,BossInterfaceService.BIZ_FRESHVLAN,BizOpcode.BIZ_FRESHVLAN,resp);
		
		return returnInfo;
	}
	
	private void bizBusiness(BizFreshPbnReq req,LoginInfo loginInfo,String servicecode,String opcode,BizFreshPbnResp resp) throws Exception{
		CheckUtils.checkEmpty(req.getServid(), "设备servid不能为空");
		
		CustOrder custOrder = initCustOrder(req, loginInfo,opcode);
		BizFreshPbnReq req2Boss = new BizFreshPbnReq();
		BeanUtils.copyPropertiesNotSuperClass(req2Boss, req);
		Exception ex = null;
		try{
			getBossHttpInfOutput(req.getBizorderid(), servicecode ,req2Boss, loginInfo);
			custOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.SYNC);
			resp.setCustorderid(custOrder.getId());
		}catch(Exception ex1){
			ex = ex1;
		}finally{
			getDAO().save(custOrder);
			if(null != ex){
				throw ex;
			}
		}
	}
}
