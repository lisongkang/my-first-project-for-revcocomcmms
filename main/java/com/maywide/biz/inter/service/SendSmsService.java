package com.maywide.biz.inter.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.bizSendsms.BizSendMsgReq;
import com.maywide.biz.inter.pojo.bizSendsms.BizSendMsgResp;
import com.maywide.biz.inter.pojo.bizSendsms.SendSmsUapReq;
import com.maywide.biz.inter.pojo.queSmsConfig.QueSmsConfigReq;
import com.maywide.biz.inter.pojo.queSmsConfig.QueSmsConfigResp;
import com.maywide.biz.inter.pojo.queSmsConfig.SmsConfigBean;
import com.maywide.biz.inter.pojo.queSmsCount.QueSmsCountReq;
import com.maywide.biz.inter.pojo.queSmsCount.QueSmsCountResp;
import com.maywide.biz.prd.entity.BizSmsLog;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.security.remote.socket.Environment;
import com.maywide.core.util.CheckUtils;


@Service
@Transactional(rollbackFor = Exception.class)
public class SendSmsService extends CommonService {

	@Autowired
	private RuleService bizRuleService;
	
	/**
	 * 获取短信模版内容
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queSmsConfig(QueSmsConfigReq req,QueSmsConfigResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo,  CommonNotice.LOGIN_OUT_NOTICE);
		
		StringBuffer sb = new StringBuffer();
		sb.append("		SELECT a.TID tid,a.TNAME title,a.TCONTENT tContent ");
		sb.append("		FROM sys_smstemp_config a");
		sb.append("		WHERE   a.CITY=  ?");
		sb.append("		AND a.TSTATUS = ?");
		
		List<SmsConfigBean> datas = getDAO().find(sb.toString(), SmsConfigBean.class, loginInfo.getCity(),"2");
		if(datas != null && !datas.isEmpty()){
			for(SmsConfigBean bean:datas){
				bean.settContent(creatContent(bean.gettContent(), req));
			}
		}
		resp.setDatas(datas);
		
		return returnInfo;
	}
	
	
	
	private String creatContent(String smscontent,QueSmsConfigReq req) throws Exception{
		String regex = "#([^#]*)#";
		Pattern pattern = Pattern.compile (regex);
        Matcher matcher = pattern.matcher (smscontent);
        Field[] fields = req.getClass().getDeclaredFields();
        List<String> replaceList = new ArrayList<String>();
        Map<String, String> values = new HashMap<String, String>();
        while (matcher.find ())
        {
        	replaceList.add(matcher.group (1));
        }
        
        for(String str:replaceList){
        	for(Field field : fields){
        		if(field.getName().equalsIgnoreCase(str)){
        			String value = handlerField(field, req);
        			if(StringUtils.isBlank(value)){
        				CheckUtils.checkNull(null,field.getName()+"未包含其值,请确认数据完整");
        			}
        			values.put(str, value);
        			break;
        		}
        	}
        }
        StringBuffer sb = new StringBuffer();
        matcher.reset();
        matcher = pattern.matcher(smscontent);
        while(matcher.find()) { 
            matcher.appendReplacement(sb, values.get(matcher.group(1)));
        } 
        matcher.appendTail(sb);
        return sb.toString();
	}
	
	private String handlerField(Field field,QueSmsConfigReq req) {
		String value = "";
		try {
			Method method = req.getClass().getMethod("get"+getMethodName(field.getName()));
			value = method.invoke(req).toString();
		} catch (SecurityException e) {
			value = "";
		} catch (NoSuchMethodException e) {
			value = "";
		} catch (IllegalArgumentException e) {
			value = "";
		} catch (IllegalAccessException e) {
			value = "";
		} catch (InvocationTargetException e) {
			value = "";
		}
		return value;
	}
	
	private String getMethodName(String filedName){
		byte[] items = filedName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}
	
	
	public ReturnInfo queSmsCount(QueSmsCountReq req,QueSmsCountResp resp) throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo,  CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkNull(req.getCustid(),"客户信息不能为空");
		String phone = req.getPhonenum();
		if(!phone.substring(0,1).equals("1")){
			throw new BusinessException("手机号码格式不正确");
		}
		if (phone.length() != 11) {
//            System.out.println("手机号应为11位数");
			throw new BusinessException("手机号码格式不正确");
		}

		Rule rule1 = bizRuleService.getRuleByName("PHONE_RULE");
		if(null != rule1){
			boolean flag = CheckUtils.isPhone(req.getPhonenum(), rule1.getValue());
			if(!flag){
				throw new BusinessException("手机号码格式不正确");
			}
		}
		
		int count = getDefaultMsgCount(loginInfo);
		if(count <= 0){
			CheckUtils.checkNull(null, "当前地市未配置短信发送次数,请校对");
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("		select * from biz_sms_log a");
		sb.append("		WHERE a.CUSTID = ?");
		sb.append("		and a.OPTIME >=  NOW() - INTERVAL 1 DAY");
		
		List<BizSmsLog> logs = getDAO().find(sb.toString(), BizSmsLog.class,req.getCustid());
		resp.setMsg(getDefaultMsg(loginInfo, count));
		if(logs != null && !logs.isEmpty()){
			if(count > 0 && count > logs.size()){
				resp.setSendAble(true);
				resp.setMsg(appendReqMsg(req));
			}else{
				resp.setSendAble(false);
				resp.setMsg(getDefaultMsg(loginInfo, count));
			}
		}else{
			resp.setSendAble(true);
			resp.setMsg(appendReqMsg(req));
		}
		return returnInfo;
	}
	
	private String appendReqMsg(QueSmsCountReq req){
		StringBuffer sb = new StringBuffer();
		sb.append("将给客户");
		sb.append(req.getCustname());
		sb.append("的手机号码");
		sb.append(req.getPhonenum());
		sb.append("发送");
		sb.append(req.getTitle());
		sb.append("模版标题的短信");
		return sb.toString();
	}
	
	
	public ReturnInfo bizSendmsg(BizSendMsgReq req,BizSendMsgResp resp) throws Exception{
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo,  CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getPhoneNum(),"手机号码不能为空");
		CheckUtils.checkNull(req.getCustid(),"客户信息不能为空");
		CheckUtils.checkEmpty(req.getMsgContent(),"短信内容不能为空");
		
		Rule rule1 = bizRuleService.getRuleByName("PHONE_RULE");
		if(null != rule1){
			boolean flag = CheckUtils.isPhone(req.getPhoneNum(), rule1.getValue());
			if(!flag){
				throw new BusinessException("手机号码不正确");
			}
		}
		SendSmsUapReq uapReq = getSendSmsUapReq4BizSendMsg(req, loginInfo);
		
		String uapRespOutput = getSocketSend(req.getBizorderid(),
				BizConstant.UAPInterfaceService.UAP_SOS_SMS, uapReq, loginInfo,
				SysConfig.getBossUapurl(),Environment.BOSS_ENCODING);
		saveLog(req,loginInfo);
		resp.setOrderid(req.getBizorderid());
		return returnInfo;
	}
	
	private SendSmsUapReq getSendSmsUapReq4BizSendMsg(BizSendMsgReq req,LoginInfo loginInfo){
		SendSmsUapReq uapReq = new SendSmsUapReq();
		uapReq.setAreaid(loginInfo.getAreaid().toString());
		uapReq.setBusiserial(req.getBizorderid());
		uapReq.setCity(loginInfo.getCity());
		uapReq.setCustid(req.getCustid().toString());
		uapReq.setOpcode("I0");
		uapReq.setPhonenumber(req.getPhoneNum());
		uapReq.setSmstext(req.getMsgContent());
		uapReq.setSmstype("0");//此处也不知道填什么
		return uapReq;
		
	}
	
	private void saveLog(BizSendMsgReq req,LoginInfo loginInfo) throws Exception{
		BizSmsLog log = new BizSmsLog();
		log.setCustid(req.getCustid());
		log.setMobile(req.getPhoneNum());
		log.setMserialno(req.getBizorderid());
		log.setOpdept(loginInfo.getDeptid());
		log.setOptime(new Date());
		log.setSmscont(req.getMsgContent());
		log.setTid(req.getTid().toString());
		log.setOpid(loginInfo.getOperid());
		getDAO().save(log);
	}
	
	private int getDefaultMsgCount(LoginInfo loginInfo) throws Exception{
		int count = 0;
		String rule = "CITY_SMS_COUNT";
		Rule sysparam = bizRuleService.getRule(loginInfo.getCity(), rule);
		if(sysparam != null){
			count = Integer.parseInt(sysparam.getValue());
		}
		return count;
	}
	
	private String getDefaultMsg(LoginInfo loginInfo,int count) throws Exception{
		String msg = "该客户24小时内发送的短信次数已达到"+count+"次，请稍后再发";
		String rule = "CITY_SMS_NOTICE";
		Rule sysparam = bizRuleService.getRule(loginInfo.getCity(), rule);
		if(sysparam != null){
			msg = sysparam.getValue();
		}
		return msg;
	}
	
}
