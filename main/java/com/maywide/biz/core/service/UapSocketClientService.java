package com.maywide.biz.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.core.entity.RemotecallLog;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.uapsocketinf.PyUapSocketReqBO;
import com.maywide.biz.core.pojo.uapsocketinf.UapSocketReqBO;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.core.cons.Separator;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.remote.socket.Environment;
import com.maywide.core.security.remote.socket.ServiceOrgRequest;
import com.maywide.core.security.remote.socket.ServiceRequest;
import com.maywide.core.security.remote.socket.ServiceResponse;
import com.maywide.core.security.remote.socket.SocketClientImpl;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.CheckUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class UapSocketClientService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private PersistentService dao;

	/**
	 * 番禺使用的发送短信的接口
	 * @param bizorderid
	 * @param input
	 * @param service
	 * @param login
	 * @param url
	 * @param encoding
	 * @param cliendid
	 * @return
	 * @throws Exception
	 */
	public String pySocketSend(String bizorderid, Object input, String service, LoginInfo login, String url,
			String encoding, String cliendid) throws Exception {
		CheckUtils.checkEmpty(bizorderid, "访问远程服务：业务订单号不能为空");
		PyUapSocketReqBO request = getPyUapSocketReqBO(url, input, service);
		RemotecallLog retmotecallLog = SocketClientImpl.sockeSend(request.getIp(), request.getPort(),
				request.getRequest(), encoding, cliendid);
		// 记录请求日志
		retmotecallLog.setOrderid(Long.valueOf(bizorderid));
		logRemotecall(retmotecallLog);
		return retmotecallLog.getResponse();
	}

	private PyUapSocketReqBO getPyUapSocketReqBO(String url, Object input, String service) throws BusinessException {
		String[] tmpArray = url.split(Separator.COLON);
		if (null == tmpArray || tmpArray.length != 2) {
			throw new BusinessException("远程服务地址配置有误，请联系管理员");
		}
		String ip = tmpArray[0];
		int port = Integer.parseInt(tmpArray[1]);
		PyUapSocketReqBO bo = new PyUapSocketReqBO();
		bo.setIp(ip);
		bo.setPort(port);
		bo.setRequest(getServiceOrgRequest(input, service));
		return bo;
	}

	private ServiceOrgRequest getServiceOrgRequest(Object input, String service) {
		ServiceOrgRequest request = new ServiceOrgRequest();
		request.setInput(input);
		request.setService(service);
		request.setClientid(SysConfig.getUapClientid());
		return request;
	}

	public String socketSend(String bizorderid, Object input, String service, LoginInfo login, String url,
			String encoding) throws Exception {
		CheckUtils.checkEmpty(bizorderid, "访问远程服务：业务订单号不能为空");

		UapSocketReqBO requestBo = getServiceRequest(bizorderid, input, service, login, url);
		RemotecallLog retmotecallLog = SocketClientImpl.sockectSend(requestBo.getIp(), requestBo.getPort(),
				requestBo.getServiceReq(), encoding);

		// 记录请求日志
		retmotecallLog.setOrderid(Long.valueOf(bizorderid));
		logRemotecall(retmotecallLog);
		return retmotecallLog.getResponse();
	}

	private UapSocketReqBO getServiceRequest(String bizorderid, Object input, String servicecode, LoginInfo loginInfo,
			String upaUrl) throws Exception {
		String[] tmpArray = upaUrl.split(Separator.COLON);
		if (null == tmpArray || tmpArray.length != 2) {
			throw new BusinessException("远程服务地址配置有误，请联系管理员");
		}
		String ip = tmpArray[0];
		int port = Integer.parseInt(tmpArray[1]);

		UapSocketReqBO retUapSocketReqBo = new UapSocketReqBO();
		retUapSocketReqBo.setIp(ip);
		retUapSocketReqBo.setPort(port);

		ServiceRequest serviceRequest = new ServiceRequest();
		serviceRequest.setInput(input);
		serviceRequest.setService(servicecode);
		serviceRequest.setClientid(SysConfig.getUapClientid());
		serviceRequest.setClientpwd(SysConfig.getUapClientPwd());

		retUapSocketReqBo.setServiceReq(serviceRequest);

		return retUapSocketReqBo;

	}

	/* @Transactional(readOnly = true) */
	public String sockectSend(String bizorderid, Object input, String servicecode, LoginInfo loginInfo)
			throws Exception {

		// bizorderid,biz_trace表中的orderid，实际上对应biz_custorder表的id,在这里相当于业务流水号
		CheckUtils.checkEmpty(bizorderid, "访问远程服务：业务订单号不能为空");
		UapSocketReqBO requestBo = this.getServiceRequest(bizorderid, input, servicecode, loginInfo);

		// 调socket服务
		RemotecallLog retmotecallLog = SocketClientImpl.sockectSend(requestBo.getIp(), requestBo.getPort(),
				requestBo.getServiceReq(), Environment.UAP_ENCODING);

		// 记录请求日志
		retmotecallLog.setOrderid(Long.valueOf(bizorderid));
		logRemotecall(retmotecallLog);

		return retmotecallLog.getResponse();
	}

	private UapSocketReqBO getServiceRequest(String bizorderid, Object input, String servicecode, LoginInfo loginInfo)
			throws Exception {
		return getServiceRequest(bizorderid, input, servicecode, loginInfo, SysConfig.getUapUrl());

	}

	private void logRemotecall(RemotecallLog remotecallLog) throws Exception {
		// 记录请求日志,主要是把网格客户订单、和Uap的请求对应该起来，方便后续核对问题
		// bizorderid,servicecode,requestid,request,response,bossorderid等--其实boss订单id属于业务方面的，可以不在这里记的

		try {

			dao.save(remotecallLog);
		} catch (Exception e) {
			e.getStackTrace();
			// System.out.print(e.getStackTrace());
		}
	}

	// 解析返回串
	// socketResp2Obj
	public ServiceResponse socketResp2Respobj(String resp, Object output) throws Exception {
		return SocketClientImpl.socketResp2Respobj(resp, output);
	}
}
