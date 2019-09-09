package com.maywide.biz.pay.unify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.cons.BizConstant.BossInterfaceService;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.entity.CustBizOrderPool;
import com.maywide.biz.inter.pojo.quecustorder.CustordersBO;
import com.maywide.biz.inter.pojo.quecustorder.QueCustorderInterReq;
import com.maywide.biz.inter.pojo.quecustorder.QueCustorderInterResp;
import com.maywide.biz.inter.service.PubService;
import com.maywide.biz.pay.unify.manager.UnifyPayManager;
import com.maywide.biz.pay.unify.pojo.QueUnifyPayInfoReq;
import com.maywide.biz.pay.unify.pojo.QueUnifyPayInfoResp;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class UnifyPayService extends CommonService {

	@Autowired
	private PubService pubService;

	public ReturnInfo queUnifyPayInfo(QueUnifyPayInfoReq req, QueUnifyPayInfoResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求对象不能为空");
		CheckUtils.checkNull(req.getCustorderid(), "订单号不能为空");
		CheckUtils.checkNull(req.getCondition(), "订单条件不能为空");

		QueCustorderInterResp queOrderResp = new QueCustorderInterResp();
		QueCustorderInterReq queOrderReq = new QueCustorderInterReq();
		queOrderReq.setDetail(false);
		queOrderReq.setCustorderid(req.getCustorderid());
		ReturnInfo queOrderInfo = pubService.queCustorder(queOrderReq, queOrderResp);
		if (queOrderInfo.getCode() != IErrorDefConstant.ERROR_SUCCESS_CODE) {
			throw new BusinessException(queOrderInfo.getMessage(), queOrderInfo.getCode());
		}
		if (queOrderResp.getCustorders() == null || queOrderResp.getCustorders().size() == 0) {
			throw new BusinessException("查询订单失败");
		}
		CustordersBO order = queOrderResp.getCustorders().get(0);
		QueUnifyPayInfoResp quResp = UnifyPayManager.generatePayReq(order, req.getCondition(),req.getUnifyPayWechatBingBean(),req.getMultipayBean());
		
		BeanUtils.copyProperties(resp, quResp);
		return returnInfo;
	}
	
	public QueUnifyPayInfoResp generatePayReq(String fees,String custOdrerid,String opcode,String custName
			,String custid) {
		return UnifyPayManager.generatePayReq(fees, custOdrerid, opcode, custName, custid,"CSJF");
	}
	
	/**
	 * 为当前网格营销的业务工单做额外的支付链接查询
	 * 使用原来的出入参是为了让前端改动最小
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queNoOrderPayInfo(QueUnifyPayInfoReq req, QueUnifyPayInfoResp resp) throws Exception {
		ReturnInfo returnInfo = initReturnInfo();
		LoginInfo loginInfo = getLoginInfo();
		CheckUtils.checkNull(req, "请求对象不能为空");
		CheckUtils.checkNull(req.getCustorderid(), "订单号不能为空");
//		CheckUtils.checkNull(req.getCondition(), "订单条件不能为空");
		CustBizOrderPool custBizOrderPool = new CustBizOrderPool();
		custBizOrderPool.setServorderid(Long.parseLong(req.getCustorderid()));
		List<CustBizOrderPool> list = DAO.find(custBizOrderPool);
		if(null == list || list.isEmpty()) {
			CheckUtils.checkNull(null, "查询支付链接地址错误,根据订单号【"+req.getCustorderid()+"】无法查找到相关订单信息!");
		}
		custBizOrderPool = list.get(0);
		CheckUtils.checkNull(custBizOrderPool, "查询支付链接地址错误,根据订单号【"+req.getCustorderid()+"】无法查找到相关订单信息!");
		if(custBizOrderPool.getFeeStatus().equalsIgnoreCase("Y")) {
			CheckUtils.checkNull(null, "该订单已经为已支付状态,无需再次支付!");
		}
		QueUnifyPayInfoResp payResp = UnifyPayManager.generatePayReq(custBizOrderPool.getFees(), 
				req.getCustorderid(), "业务工单处理", custBizOrderPool.getCustName(), custBizOrderPool.getCustid().toString(),BossInterfaceService.WFL_EQUIPINFO_SUBMIT);
		BeanUtils.copyProperties(resp, payResp);
		return returnInfo;
	}
}
