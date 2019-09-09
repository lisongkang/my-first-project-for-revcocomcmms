package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.entity.BizCustElecInvinfo;
import com.maywide.biz.inter.pojo.invoice.PrintElecSalesReq;
import com.maywide.biz.inter.pojo.invoice.QueInvoiceListReq;
import com.maywide.biz.inter.pojo.invoice.SaveOrUpdateInvoiceReq;
import com.maywide.biz.inter.pojo.invoice.WaitElecSalesBOSSReq;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;

@Service
public class InvoiceService extends CommonService {

	@Autowired
	private PubService pubService;

	@SuppressWarnings("unchecked")
	public ReturnInfo queInvoiceList(QueInvoiceListReq req, ArrayList<BizCustElecInvinfo> resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getCustid(), "客户编号不能为空");

		BizCustElecInvinfo info = new BizCustElecInvinfo();
		info.setCustid(Long.parseLong(req.getCustid()));
		List<BizCustElecInvinfo> list = getDAO().find(info);
		if (list != null) {
			resp.addAll(list);
		}
		return returnInfo;
	}

	public ReturnInfo saveOrUpdateInvoice(SaveOrUpdateInvoiceReq req) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req, "请求信息不能为空");

		BizCustElecInvinfo info = new BizCustElecInvinfo();
		BeanUtils.copyPropertiesNotSuperClass(info, req);
		getDAO().saveOrUpdate(info);

		return returnInfo;
	}

	public ReturnInfo printElecSales(PrintElecSalesReq req) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getOrderid(), "订单编号不能为空");
		CheckUtils.checkEmpty(req.getBossorderid(), "BOSS订单编号不能为空");
		CheckUtils.checkEmpty(req.getCustid(), "客户编号不能为空");
		CheckUtils.checkNull(req.getInvoiceid(), "发票抬头编码不能为空");

		CustOrder rawOrder = (CustOrder) getDAO().find(CustOrder.class, Long.parseLong(req.getOrderid()));
		if (rawOrder == null) {
			throw new BusinessException("未找到相应订单");
		}
		if ("Y".equals(rawOrder.getPrintedinv())) {
			throw new BusinessException("该订单已打印过发票");
		}

		WaitElecSalesBOSSReq salesReq = getElecSalesBossReq(req);
		getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.WAIT_ELEC_SALES, salesReq, loginInfo);

		saveCustOrder(req, loginInfo, BizConstant.BizOpcode.BIZ_ELEC_SALES);
		rawOrder.setPrintedinv("Y");
		getDAO().update(rawOrder);
		return returnInfo;
	}

	private WaitElecSalesBOSSReq getElecSalesBossReq(PrintElecSalesReq req) throws Exception {
		BizCustElecInvinfo invinfo = (BizCustElecInvinfo) getDAO().find(BizCustElecInvinfo.class, req.getInvoiceid());
		WaitElecSalesBOSSReq salesReq = new WaitElecSalesBOSSReq();
		salesReq.setOrderid(req.getBossorderid());
		salesReq.setCustid(req.getCustid());
		salesReq.setBuyname(invinfo.getBuyname());
		salesReq.setBuyid(invinfo.getBuyid());
		salesReq.setBuyacct(invinfo.getBuyacct());
		salesReq.setBuyaddr(invinfo.getBuyaddr());
		salesReq.setBuyphone(invinfo.getBuyphone());
		salesReq.setBuymail(invinfo.getBuymail());
		salesReq.setBuyacctname(invinfo.getBuyacctname());
		return salesReq;
	}

	public CustOrder saveCustOrder(PrintElecSalesReq req, LoginInfo loginInfo, String opcode) throws Exception {
		CustOrder bizCustOrder = new CustOrder();
		bizCustOrder.setAddr(req.getAddr());
		bizCustOrder.setAreaid(loginInfo.getAreaid());
		bizCustOrder.setCity(loginInfo.getCity());
		bizCustOrder.setBossserialno(null);
		bizCustOrder.setCanceltime(null);
		bizCustOrder.setCustid(Long.valueOf(req.getCustid()));
		bizCustOrder.setDescrip(null);
		bizCustOrder.setLockoper(null);
		bizCustOrder.setName(req.getName());
		bizCustOrder.setOpcode(opcode);
		bizCustOrder.setOperator(loginInfo.getOperid());
		bizCustOrder.setOprdep(loginInfo.getDeptid());
		bizCustOrder.setOptime(new Date());
		bizCustOrder.setOrdercode(pubService.getOrderCode().toString());
		bizCustOrder.setId(Long.valueOf(req.getBizorderid()));
		bizCustOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.SYNC);
		bizCustOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
		bizCustOrder.setPortalOrder(null);
		bizCustOrder.setVerifyphone(null);
		getDAO().save(bizCustOrder);
		return bizCustOrder;
	}

}
