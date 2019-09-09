package com.maywide.biz.core.service;

import java.util.List;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.core.entity.RemotecallLog;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.core.cons.SysConstant;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.remote.BossHttpClientImpl;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.CheckUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class BossHttpClientService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private PersistentService dao;

	/* @Transactional(readOnly = true) */
	public String requestPost(String bizorderid, String servicecode,
			String request, LoginInfo loginInfo) throws Exception {
		// bizorderid,biz_trace表中的orderid，实际上对应biz_custorder表的id,在这里相当于业务流水号
		CheckUtils.checkEmpty(bizorderid, "访问远程服务：业务订单号不能为空");

		PostMethod postMethod = this.getPostMethod(servicecode, request,
				loginInfo);

		RemotecallLog retmotecallLog = BossHttpClientImpl
				.requestPost(postMethod);

		// 记录请求日志
		retmotecallLog.setOrderid(Long.valueOf(bizorderid));
		logRemotecall(retmotecallLog);

		return retmotecallLog.getResponse();
	}

	private void logRemotecall(RemotecallLog remotecallLog) throws Exception {
		// 记录请求日志,主要是把网格客户订单、和boss的请求对应该起来，方便后续核对问题
		// bizorderid,servicecode,requestid,request,response,bossorderid等--其实boss订单id属于业务方面的，可以不在这里记的

		try {

			dao.save(remotecallLog);
		} catch (Exception e) {
		    e.getStackTrace();
			//System.out.print(e.getStackTrace());
		}
	}

	/**
	 * 构造post方法实例--使用当前登录工号调boss接口
	 * 
	 * @param servicecode
	 * @param request
	 * @param operatorInfo
	 * @return
	 * @throws Exception
	 */
	public PostMethod getPostMethod(String servicecode, String request,
			LoginInfo loginInfo) throws Exception {
		CheckUtils.checkNull(loginInfo, "构造post方法实例：登录信息不能为空");

		PrvOperator operatorVO = new PrvOperator();
		operatorVO.setId(loginInfo.getOperid());
		operatorVO.setStatus(SysConstant.Operator.PRV_USE_STATUS_ENABLED);
		List<PrvOperator> operatorList = dao.find(operatorVO);
		if (BeanUtil.isListNull(operatorList)) {
			throw new BusinessException("构造post方法实例：获取操作员信息失败");
		}
		PrvOperator operator = operatorList.get(0);

		PostMethod postMethod = new PostMethod(SysConfig.getBossUrl());

		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,
				SysConfig.getHttpTimeout());
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(1, false));

		postMethod.addParameter("version", SysConfig.getParamVersion());
		postMethod.addParameter("clientcode", loginInfo.getLoginname());
		postMethod.addParameter("clientpwd", operator.getPasswd());
		postMethod.addParameter("citycode", loginInfo.getCity());
		postMethod.addParameter("servicecode", servicecode);
		postMethod.addParameter("system", "GRID");
		
		String requestid = BossHttpClientImpl.createRequestid(loginInfo
				.getLoginname());
		postMethod.addParameter("requestid", requestid);

		postMethod.addParameter("requestContent", request);
		postMethod.addParameter("deptid", String.valueOf(loginInfo.getDeptid()));

		return postMethod;
	}

}
