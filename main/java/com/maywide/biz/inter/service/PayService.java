package com.maywide.biz.inter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.pay.PayPackageReq;
import com.maywide.biz.inter.pojo.pay.PayPackageResp;
import com.maywide.biz.pay.weixin.PayPackage;
import com.maywide.biz.pay.weixin.RandomStringGenerator;
import com.maywide.biz.pay.weixin.Signature;
import com.maywide.biz.pay.weixin.WeixinPayService;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.PersistentService;

@Service
public class PayService {
	@Autowired
    private ParamService paramService;
	@Autowired
    private PersistentService persistentService;
	@Autowired
    private WeixinPayService weixinPayService;
	
	public static Map<String, String> orderidMap = new HashMap<String, String>();
	
	public ReturnInfo weixinPay(PayPackageReq req, PayPackageResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        if (loginInfo == null) {
        	returnInfo.setCode(IErrorDefConstant.ERROR_INVALID_LOGIN_CODE);
        	returnInfo.setMessage(CommonNotice.LOGIN_OUT_NOTICE);
        	
        	return returnInfo;
        }
        
        try {
        	PrvSysparam sysparam = paramService.getData("SYS_WX_PAYACCT", loginInfo.getCity());
        	if (sysparam == null) throw new Exception("服务端没有配置微信的支付账号信息");
        	String[] data = sysparam.getData().split("~");
        	
        	
        	
            PayPackage payPackage = new PayPackage();

    		payPackage.setAppid(data[0]);
    		payPackage.setAttach(req.getOrderid());
    		payPackage.setBody(req.getProductname());
    		payPackage.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
    		payPackage.setMch_id(data[1]);
    		payPackage.setOut_trade_no(req.getOrderid());
    		payPackage.setSpbill_create_ip("127.0.0.1");
    		if (isWeixinPayTest()) {
    			payPackage.setTotal_fee("1");//测试改为1分
    		} else {
    			payPackage.setTotal_fee(Integer.toString((req.getFee()*100)));
    		}
    		
    		payPackage.setTrade_type("NATIVE");
    		payPackage.setNotify_url(data[3]);
    		
    		Map<String, String> map = BeanUtils.describe(payPackage);
    		map.remove("class");
    		
    		String sign = Signature.generateSign(map, data[2]);
    		payPackage.setSign(sign);
    		
    		String codeurl = weixinPayService.nativePay(payPackage);
    		
    		resp.setCodeurl(codeurl);
    		
    		if (orderidMap.size() > 10000) {
    			orderidMap.clear();
    		}
    		orderidMap.put(req.getOrderid(), loginInfo.getLoginname());
		} catch (Exception e) {
			returnInfo.setCode(IErrorDefConstant.ERROR_RuntimeException_CODE);
			returnInfo.setMessage(e.getMessage());
		}
        
        return returnInfo;
	}
	
	public ReturnInfo weixinRefund(PayPackageReq req, PayPackageResp resp) throws Exception {
		Assert.notNull(req.getOrderid(), "参数[orderid]不能为空");
		ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        if (loginInfo == null) {
        	returnInfo.setCode(IErrorDefConstant.ERROR_INVALID_LOGIN_CODE);
        	returnInfo.setMessage(CommonNotice.LOGIN_OUT_NOTICE);
        	
        	return returnInfo;
        }
        
    	weixinPayService.refund(req.getOrderid());
        
        return returnInfo;
	}
	
	private boolean isWeixinPayTest() throws Exception {
		persistentService.clear();
		
		List<PrvSysparam> params = paramService.getData("WEIXIN_PAY_TEST");
		
		if (params.size() == 0) return false;
		
		PrvSysparam sysParam = params.get(0);
		
		return sysParam.getMcode().equals("0");
	}
}
