package com.maywide.biz.inter.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.BizCustorderOrderstatus;
import com.maywide.biz.cons.BizConstant.BizCustorderSyncmode;
import com.maywide.biz.cons.BizConstant.BossInterfaceService;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.inter.pojo.cryptTicket.BossCryptTicketReq;
import com.maywide.biz.inter.pojo.cryptTicket.CryptTicketReq;
import com.maywide.biz.inter.pojo.cryptTicket.CryptTicketResp;
import com.maywide.biz.inter.pojo.cryptTicketUse.BossCryptTicketUseReq;
import com.maywide.biz.inter.pojo.cryptTicketUse.CryptTicketUseReq;
import com.maywide.biz.inter.pojo.cryptTicketUse.CryptTicketUseResp;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.CheckUtils;
import com.maywide.core.util.DateUtils;

@Service
public class CryptTicketService extends CommonService {

	@Autowired
	PubService pubService;
	
	/**
	 * 卡券激活 BOSS密码鉴权接口
	 * @param req
	 * @return
	 * @throws Exception
	 * 
	 */
	public ReturnInfo cryptTicketVerify(CryptTicketReq req) throws Exception {
		
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkEmpty(req.getPasswd(), "卡券密码不能为空");
		
		BossCryptTicketReq req2Boss = new BossCryptTicketReq(req.getPasswd());
		getBossHttpInfOutput(req.getBizorderid(), BossInterfaceService.CRYPT_TICKET_VERIFY, req2Boss, loginInfo);
		Thread.sleep(5000l);
		return returnInfo;
	}

	/**
	 * 卡券状态查询接口
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo cryptStatusTicket(CryptTicketReq req, CryptTicketResp resp) throws Exception {
		
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkEmpty(req.getPasswd(), "卡券密码不能为空");

		BossCryptTicketReq req2Boss = new BossCryptTicketReq(req.getPasswd());
		String responeStr = getBossHttpInfOutput(req.getBizorderid(), BossInterfaceService.QUE_CRYPT_TICKET, req2Boss, loginInfo);
		
		resp = (CryptTicketResp) BeanUtil.jsonToObject(responeStr, CryptTicketResp.class);
		resp.setKind("1");
		resp.setName("测试商品方案");
		resp.setSnno("459098567898765678");
		resp.setStatus("1");
		return returnInfo;
	}
	
	/*private CryptTicketResp tmpGetTestResp(){
		CryptTicketResp resp = new CryptTicketResp();
		resp
		
		return resp;
	}*/
	
	/**
	 * 使用卡券接口
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo cryptTicketUse(CryptTicketUseReq req,CryptTicketUseResp resp) throws Exception{
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkEmpty(req.getPasswd(), "卡券密码不能为空");
		
		BossCryptTicketUseReq req2Boss = getReq2Boss(req, loginInfo);
		CustOrder custOrder = getCustOrder(req, loginInfo);
		
		getBossHttpInfOutput(req.getBizorderid(), BossInterfaceService.CRYPT_TICKET_USE, req2Boss, loginInfo);
		
		custOrder.setOrderstatus(BizCustorderOrderstatus.SYNC);
		getDAO().update(custOrder);
		resp.setCustOrderid(custOrder.getId());
		return returnInfo;
	}
	
	private BossCryptTicketUseReq getReq2Boss(CryptTicketUseReq req,LoginInfo loginInfo){
		BossCryptTicketUseReq req2Boss = new BossCryptTicketUseReq();
		req2Boss.setCustid(req.getCustid());
		req2Boss.setCustname(req.getCustname());
		req2Boss.setKeyno(req.getKeyno());
		req2Boss.setPasswd(req.getPasswd());
		req2Boss.setSerialno(getSerialno(loginInfo));
		return req2Boss;
	}
	
	public String getSerialno(LoginInfo loginInfo){
		StringBuffer sb = new StringBuffer();
		sb.append(loginInfo.getLoginname());
		sb.append(DateUtils.formatDate(new Date(),"yyyyMMddHHmmss"));
		return sb.toString();
	}
	
	
	private CustOrder getCustOrder(CryptTicketUseReq req,LoginInfo loginInfo) throws Exception{
		CustOrder custOrder = new CustOrder();
		custOrder.setId(Long.parseLong(req.getBizorderid()));
		custOrder.setAddr(req.getAddr());
		custOrder.setAreaid(loginInfo.getAreaid());
		custOrder.setCity(loginInfo.getCity());
		custOrder.setCustid(Long.parseLong(req.getCustid()));
		custOrder.setName(req.getCustname());
		custOrder.setOpcode(BizConstant.BizOpcode.CRYPT_TICKEY_USER);
		custOrder.setOperator(loginInfo.getOperid());
		custOrder.setOprdep(loginInfo.getDeptid());
		custOrder.setOptime(new Date());
		custOrder.setOrdercode(pubService.getOrderCode());
		custOrder.setOrderstatus(BizCustorderOrderstatus.INIT);
		custOrder.setSyncmode(BizCustorderSyncmode.SYNC);
		custOrder.setPortalOrder(null);
		getDAO().save(custOrder);
		return custOrder;
	}

}
