package com.maywide.biz.core.servlet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maywide.biz.core.entity.AccessLog;
import com.maywide.biz.core.entity.Interface2Service;
import com.maywide.biz.core.pojo.*;
import com.maywide.biz.core.pojo.api.ApiMethodInfo;
import com.maywide.biz.core.pojo.api.BaseApiRequest;
import com.maywide.biz.market.entity.Trace;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.security.encrypt.ISecurityService;
import com.maywide.core.security.encrypt.SecurityFactory;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionSystemException;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Random;
import java.util.zip.GZIPInputStream;

/**
 * 对外接口实现Servlet
 */
public class ApiServlet extends HttpServlet {
	private static final long serialVersionUID = 4293493091077873447L;
	private static Logger log = LoggerFactory.getLogger(ApiServlet.class);

	private ObjectMapper jsonMapper = null;
	private PersistentService baseService = null;
	private ISecurityService securityService = null;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		// 初始化Spring
		SpringBeanUtil.setWebCTX(config.getServletContext());
		baseService = (PersistentService) SpringContextHolder
				.getBean(PersistentService.class);

		if (null == jsonMapper) {
			jsonMapper = new ObjectMapper();
			jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			jsonMapper.setDateFormat(dateFormat);
		}

		// 获取默认配置的安全管理算法
		if (null == securityService) {
			securityService = SecurityFactory.getSecurityService();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String authorization = request.getHeader("Authorization");
		//带token情况请求走另外逻辑
		if(StringUtils.isNotEmpty(authorization)){
			tokenPost(request,response);
			return;
		}
		ReturnInfo returnInfo = new ReturnInfo();
		ResponseWrapper responseWrapper = new ResponseWrapper();
		AccessLog accessLog = new AccessLog();
		ApiMethodInfo apiCallInfo = new ApiMethodInfo();
		GlobalCacheMgr.setSession(request.getSession());

		BaseApiRequest apiReq = new BaseApiRequest();
		Trace bizTrace = null;

		// printConsolelog(request);
		Interface2Service interfaceInfo = null;

		try {

			// 初始化日志记录
			accessLog.setClientIP(request.getRemoteHost());
			accessLog.setCallMethod("N/A");
			accessLog.setCallTime(new Date());
			accessLog.setRequest("");
			accessLog.setResponse("");
			apiCallInfo.setCallTime(accessLog.getCallTime());
			apiCallInfo.setSuccess(false);

			// 获取登录操作员信息
			LoginUser loginUser = GlobalCacheMgr.getLoginUser();
			if (null != loginUser) {
				accessLog.setClientMAC(loginUser.getMAC());
				accessLog.setClientIMEI(loginUser.getIMEI());
				accessLog.setOpCode(loginUser.getOpCode());
				accessLog.setAreaId(loginUser.getAreaId());
			}

			// 读取POST的报文
			StringBuilder postJson = new StringBuilder();
			BufferedReader httpReader = request.getReader();
			String line = null;
			while ((line = httpReader.readLine()) != null) {
				postJson.append(line);
			}

			// 转换请求JSON报文为RequestWrapper对象
			RequestWrapper requestWrapper = jsonMapper.readValue(
					postJson.toString(), RequestWrapper.class);
			if ((null == requestWrapper) || (null == requestWrapper.getApi())) {
				throw new RemoteCallException(
						IErrorDefConstant.ERROR_INVALID_JSON_MSG,
						IErrorDefConstant.ERROR_INVALID_JSON_CODE);
			}
			accessLog.setCallMethod(requestWrapper.getApi());
			apiCallInfo.setApiId(requestWrapper.getApi());

			// RSA算法解密业务报文体
			String cipherBody = requestWrapper.getRequestBody();
			String requestBody = "";
			if ((null != cipherBody) && (!cipherBody.trim().isEmpty())) {
				/* String cryptRequestBody =
				 securityService.encrypt(cipherBody);
				 requestBody = securityService.decrypt(cryptRequestBody);*/
				requestBody = securityService.decrypt(cipherBody);
				if (null == requestBody) {
					throw new RemoteCallException(
							IErrorDefConstant.ERROR_INVALID_SECURITY_MSG,
							IErrorDefConstant.ERROR_INVALID_SECURITY_CODE);
				}
				accessLog.setRequest(requestBody);
			}

			// 设置接口请求信息
			apiReq.setRequestBody(requestBody);
			setBasebizparam(apiReq);

			// 登记业务轨迹表biz_trace，主要是写一条订单id为apiReq.getBizorderid()记录
			// bizorderid相当于业务流水号，业务层通过bizorderid就可以对biz_trace表进行存取(如果非要在业务层操作biz_trace的话)
			bizTrace = regBizTrace(apiReq, accessLog);

			// 根据API接口获取配置对应的实现函数
			interfaceInfo = (Interface2Service) baseService
					.findUniqueByProperty(Interface2Service.class,
							"interfaceName", requestWrapper.getApi());
			baseService.evit(interfaceInfo);
			if (null == interfaceInfo) {
				throw new RemoteCallException(String.format(
						IErrorDefConstant.ERROR_INVALID_API_MSG,
						requestWrapper.getApi()),
						IErrorDefConstant.ERROR_INVALID_API_CODE);
			}

			// 通过反射调用实现函数
			StringBuilder responseBuilder = new StringBuilder();
			returnInfo = callApiImpl(request.getSession(), interfaceInfo,
					apiReq, responseBuilder);
			if (null == returnInfo) {
				throw new RemoteCallException(String.format(
						IErrorDefConstant.ERROR_API_NO_IMPLEMENTED_MSG,
						requestWrapper.getApi()),
						IErrorDefConstant.ERROR_API_NO_IMPLEMENTED_CODE);
			}

			// RSA算法加密JSON明文,并创建ResponseWrapper对象
			accessLog.setResponse(responseBuilder.toString());
//			 responseWrapper.setResponseBody(responseBuilder.toString());
			responseWrapper.setResponseBody(securityService
					.encrypt(responseBuilder.toString()));
			apiCallInfo.setSuccess(true);
		} catch (RemoteCallException Err) {
			log.error("apiServlet::doPost", Err);
			returnInfo.setCode(Err.getErrorCode());
			returnInfo.setMessage(Err.getMessage());
		} catch (ClassNotFoundException Err) {
			log.error("apiServlet::doPost", Err);
			returnInfo.setCode(IErrorDefConstant.ERROR_ClassNotFoundException);
			returnInfo.setMessage(Err.getMessage());
		} catch (InvocationTargetException e) {

			String code = String
					.valueOf(IErrorDefConstant.ERROR_InvocationTargetException);
			String message = e.getMessage();
			
			if (e.getTargetException().getClass()
					.equals(BusinessException.class)) {
				BusinessException be = (BusinessException) e
						.getTargetException();

				if (be.getRetcode() != null) {
					code = be.getRetcode().toString();
				}
				
				message = be.getMessage();
			}else if (e.getTargetException().getClass().equals(TransactionSystemException.class)) {
				TransactionSystemException tranException = (TransactionSystemException) e.getTargetException();
				if (tranException.getOriginalException() instanceof BusinessException) {
					BusinessException be = (BusinessException) tranException.getOriginalException();
					if (be.getRetcode() != null) {
						code = be.getRetcode().toString();
					}
					message = be.getMessage();
				}
			}

			log.error("apiServlet::doPost", e);
			returnInfo.setCode(Long.valueOf(code));
			returnInfo.setMessage(message);
		} catch (NoSuchMethodException Err) {
			log.error("apiServlet::doPost", Err);
			returnInfo.setCode(IErrorDefConstant.ERROR_NoSuchMethodException);
			returnInfo.setMessage(Err.getMessage());
		} catch (InstantiationException Err) {
			log.error("apiServlet::doPost", Err);
			returnInfo.setCode(IErrorDefConstant.ERROR_InstantiationException);
			returnInfo.setMessage(Err.getMessage());
		} catch (IllegalAccessException Err) {
			log.error("apiServlet::doPost", Err);
			returnInfo.setCode(IErrorDefConstant.ERROR_IllegalAccessException);
			returnInfo.setMessage(Err.getMessage());
		} catch (RuntimeException Err) {
			log.error("apiServlet::doPost", Err);
			returnInfo.setCode(IErrorDefConstant.ERROR_RuntimeException_CODE);
			returnInfo.setMessage(Err.getMessage());
		} catch (JsonParseException Err) {
			log.error("initServlet::doPost", Err);
			returnInfo.setCode(IErrorDefConstant.ERROR_INVALID_JSON_CODE);
			returnInfo.setMessage(IErrorDefConstant.ERROR_INVALID_JSON_MSG);
		} catch (Exception Err) {
			log.error("initServlet::doPost", Err);
			returnInfo.setCode(IErrorDefConstant.ERROR_INVALID_JSON_CODE);
			returnInfo.setMessage(Err.getMessage());
		} finally {
			// 返回响应报文
			responseWrapper.setReturnInfo(returnInfo);
			String responseJSON = jsonMapper
					.writeValueAsString(responseWrapper);

			response.setCharacterEncoding("GBK");
			response.setContentType("text/html;charset=GBK");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			PrintWriter writer = response.getWriter();
			writer.print(responseJSON);

			// 记录访问日志
			accessLog.setReturnCode(returnInfo.getCode());
			accessLog.setReturnMsg(returnInfo.getMessage());
			accessLog.setEndTime(new Date());
			accessLog.setOrderid(apiReq.getBizorderid());
			// if(returnInfo.getMessage().length() > 1024){
			// accessLog.setReturnMsg(returnInfo.getMessage().substring(1024));
			// }else{
			// accessLog.setReturnMsg(returnInfo.getMessage());
			// }
			// accessLog.setEndTime(new Date());
			try {
				baseService.save(accessLog);
				updateBizTrace(bizTrace, accessLog);// 更新业务轨迹表
			} catch (Exception e) {
				log.error("", e);
			}

			// 统计API调用
			apiCallInfo.setEchoTime(accessLog.getEndTime());
		}
	}

	private Trace regBizTrace(BaseApiRequest apiReq, AccessLog accessLog)
			throws Exception {
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();

		Trace bizTrace = new Trace();

		if (null != loginInfo) {
			if (StringUtils.isNotBlank(loginInfo.getCity())) {
				bizTrace.setCity(loginInfo.getCity());
			}
			if (null != loginInfo.getOperid()) {
				bizTrace.setOperid(loginInfo.getOperid());
			}
		}
		bizTrace.setMenuid(null);

		bizTrace.setAccesslogid(accessLog.getId());
		// bizTrace.setOpinfo(accessLog.getRequest());
		bizTrace.setOptime(accessLog.getCallTime());
		// bizTrace.setOptype(BizConstant.BizTraceOptype.SUBMIT);
		bizTrace.setOrderid(Long.valueOf(apiReq.getBizorderid()));
		// bizTrace.setResultinfo(accessLog.getResponse());
		// bizTrace.setTraceid(traceid);

		baseService.save(bizTrace);

		return bizTrace;

	}

	private Trace updateBizTrace(Trace bizTrace, AccessLog accessLog)
			throws Exception {
		if (null == bizTrace || null == accessLog) {
			return null;
		}

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();

		if (null != loginInfo) {
			// 如果操作员为空，就再更新一遍
			if (StringUtils.isBlank(bizTrace.getCity())) {
				if (StringUtils.isNotBlank(loginInfo.getCity())) {
					bizTrace.setCity(loginInfo.getCity());
				}
			}

			if (null == bizTrace.getOperid()) {
				if (null != loginInfo.getOperid()) {
					bizTrace.setOperid(loginInfo.getOperid());
				}
			}
		}

		bizTrace.setAccesslogid(accessLog.getId());
		// bizTrace.setResultinfo(accessLog.getResponse());

		baseService.update(bizTrace);

		return bizTrace;
	}

	/**
	 * 调用接口实现函数,支持函数原型 1.没有入出参: public ReturnInfo xxx(HttpSession httpSession,);
	 * 2.只有入参没有出参: public ReturnInfo xxx(HttpSession httpSession, in xxx);
	 * 3.只有出参没有入参: public ReturnInfo xxx(HttpSession httpSession, out yyy);
	 * 4.出入参都有: public ReturnInfo xxx(HttpSession httpSession, in xxx, out yyy);
	 * 出入参支持基本数据类型/自定义类型
	 * 
	 * @param httpSession
	 *            当前session
	 * @param requestBody
	 *            请求入参报文
	 * @param responseBody
	 *            响应报文
	 * @return ReturnInfo操作结果
	 */
	private ReturnInfo callApiImpl(HttpSession httpSession,
			final Interface2Service interfaceInfo, final BaseApiRequest apiReq,
			StringBuilder responseBody) throws ClassNotFoundException,
			IOException, NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, InstantiationException {

		String inType = ((null == interfaceInfo.getInparamType()) ? ""
				: interfaceInfo.getInparamType().trim());
		String outType = ((null == interfaceInfo.getOutparamType()) ? ""
				: interfaceInfo.getOutparamType().trim());
		String methodName = ((null == interfaceInfo.getMethodName()) ? interfaceInfo
				.getInterfaceName() : interfaceInfo.getMethodName());

		interfaceInfo.setInparamType(inType);
		interfaceInfo.setOutparamType(outType);
		interfaceInfo.setMethodName(methodName);

		if (inType.isEmpty() && outType.isEmpty()) {
			return callApiImpl(httpSession, interfaceInfo);
		} else if ((!inType.isEmpty()) && (outType.isEmpty())) {
			return callApiImpl(httpSession, interfaceInfo, apiReq);
		} else if ((inType.isEmpty()) && (!outType.isEmpty())) {
			return callApiImpl(httpSession, interfaceInfo, responseBody);
		}

		Class<?> inParamClass = getClassByName(inType);
		Class<?> outParamClass = getClassByName(outType);
		Constructor<?> outParamClassConstructor = outParamClass
				.getConstructor();

		Object inObject = jsonMapper.readValue(apiReq.getRequestBody(),
                inParamClass);
		copyBaseApiRequest2ReqObject(inObject, apiReq);

		Object outObject = outParamClassConstructor.newInstance();

		Class[] argTypes = new Class[] { inParamClass, outParamClass };
		Object implService = SpringBeanUtil.getBean(interfaceInfo
				.getServiceImpl());
		Method apiImplMethod = implService.getClass().getMethod(
				interfaceInfo.getMethodName(), argTypes);
		ReturnInfo result = (ReturnInfo) apiImplMethod.invoke(implService,
				inObject, outObject);
		responseBody.append(jsonMapper.writeValueAsString(outObject));

		return result;
	}

	/**
	 * 实现没有出入参的接口调用
	 * 
	 * @param interfaceInfo
	 *            接口信息
	 * @return ReturnInfo操作结果
	 */
	private ReturnInfo callApiImpl(HttpSession httpSession,
			final Interface2Service interfaceInfo)
			throws NoSuchMethodException, InvocationTargetException,
			IllegalAccessException, ClassNotFoundException {
		Class[] argTypes = new Class[] {};
		Object implService = SpringBeanUtil.getBean(interfaceInfo
				.getServiceImpl());
		Method apiImplMethod = implService.getClass().getMethod(
				interfaceInfo.getMethodName(), argTypes);

		return (ReturnInfo) apiImplMethod.invoke(implService);
	}

	/**
	 * 实现只有入参的接口调用
	 * 
	 * @param interfaceInfo
	 *            接口信息
	 * @param requestBody
	 *            入参报文
	 * @return ReturnInfo操作结果
	 */
	private ReturnInfo callApiImpl(HttpSession httpSession,
			final Interface2Service interfaceInfo, final BaseApiRequest apiReq)
			throws ClassNotFoundException, IOException, NoSuchMethodException,
			InvocationTargetException, IllegalAccessException {
		Class<?> inParamClass = getClassByName(interfaceInfo.getInparamType());
		Object inObject = jsonMapper.readValue(apiReq.getRequestBody(),
				inParamClass);
		copyBaseApiRequest2ReqObject(inObject, apiReq);

		Class[] argTypes = new Class[] { inParamClass };
		Object implService = SpringBeanUtil.getBean(interfaceInfo
				.getServiceImpl());
		Method apiImplMethod = implService.getClass().getMethod(
				interfaceInfo.getMethodName(), argTypes);

		return (ReturnInfo) apiImplMethod.invoke(implService, inObject);
	}

	/**
	 * 实现只有出参的接口调用
	 * 
	 * @param interfaceInfo
	 *            接口信息
	 * @param responseBody
	 *            出参报文
	 * @return ReturnInfo操作结果
	 */
	private ReturnInfo callApiImpl(HttpSession httpSession,
			final Interface2Service interfaceInfo, StringBuilder responseBody)
			throws ClassNotFoundException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException, JsonProcessingException {
		Class<?> outParamClass = getClassByName(interfaceInfo.getOutparamType());
		Constructor<?> outParamClassConstructor = outParamClass
				.getConstructor();

		Object outObject = outParamClassConstructor.newInstance();
		Class[] argTypes = new Class[] { outParamClass };

		Object implService = SpringBeanUtil.getBean(interfaceInfo
				.getServiceImpl());
		Method apiImplMethod = implService.getClass().getMethod(
				interfaceInfo.getMethodName(), argTypes);
		ReturnInfo result = (ReturnInfo) apiImplMethod.invoke(implService,
				outObject);
		responseBody.append(jsonMapper.writeValueAsString(outObject));

		return result;
	}

	/**
	 * 根据字符串返回反射所使用的类类型
	 * 
	 * @param className
	 *            类名称，可以为原始类型也可以为自定义类型
	 * @return Class<?>
	 */
	private Class<?> getClassByName(final String className)
			throws ClassNotFoundException {
		if (className.equals("byte")) {
			return byte.class;
		} else if (className.equals("short")) {
			return short.class;
		} else if (className.equals("int")) {
			return int.class;
		} else if (className.equals("long")) {
			return long.class;
		} else if (className.equals("float")) {
			return float.class;
		} else if (className.equals("double")) {
			return double.class;
		} else if (className.equals("boolean")) {
			return boolean.class;
		} else if (className.equals("char")) {
			return char.class;
		} else {
			return Class.forName(className);
		}
	}

	private void copyBaseApiRequest2ReqObject(Object inObject,
			BaseApiRequest apiReq) {
		// 将BaseApiRequest中定义的共公参数copy到请求对象中，以便传到业务层
		try {
			BeanUtils.copyProperties(inObject, apiReq);
		} catch (Exception e) {
			// 如果受理参数对象有该成员，才设设置值。所以这里catch了异常不throw
			System.out.println("====>\"procReqBizparam\" method exception....");
		}

	}

//	private void setBasebizparam(BaseApiRequest apiReq) throws Exception {
//
//		String bizorderid = baseService.getSequence("SEQ_BIZ_CUSTORDER_ID")
//				.toString();
//
//		apiReq.setBizorderid(bizorderid);// 业务订单号
//		// 其它公共参数
//	}
	//使用时间戳+当前线程id的后3位 作为业务订单号
	private void setBasebizparam(BaseApiRequest apiReq) throws Exception {
		String idStr;
		try {
			Thread thread = Thread.currentThread();
			long id = thread.getId();
			idStr = String.valueOf(id);
			if (idStr.length() < 3) {
				StringBuffer idSb = new StringBuffer(idStr);

				int length = idSb.toString().length();
				for (int i = 0; i < 3 - length; i++) {
					idSb.append(new Random().nextInt(10));
				}
				idStr = idSb.toString();
			} else {
				idStr = idStr.substring(idStr.length() - 3, idStr.length());
			}
		} catch (Exception e) {
			idStr = "000";
		}
		
		String bizorderid = System.currentTimeMillis()+idStr;
		apiReq.setBizorderid(bizorderid);// 业务订单号
		// 其它公共参数
	}

	// --打印请求信息--不可以用，和request.reader有冲突
	protected void printConsolelog(HttpServletRequest req) {

		System.out.println("====>开始打印请求参数....");
		System.out.println("请求ip=" + req.getRemoteAddr());

		Enumeration emParams = req.getParameterNames();
		// 取得get方法传递的参数
		String queryString = req.getQueryString();

		// 取得post方法传递的参数
		StringBuffer strBuf = new StringBuffer("");

		boolean first = true;
		if (queryString == null) {
			do {
				if (!emParams.hasMoreElements()) {
					break;
				}

				String sParam = (String) emParams.nextElement();
				String[] sValues = req.getParameterValues(sParam);

				String sValue = "";
				for (int i = 0; i < sValues.length; i++) {
					sValue = sValues[i];
					if (sValue != null && sValue.trim().length() != 0
							&& first == true) {
						// 第一个参数
						first = false;
						strBuf.append(sParam).append("=").append(sValue);
					} else if (sValue != null && sValue.trim().length() != 0
							&& first == false) {
						strBuf.append("\n").append(sParam).append("=")
								.append(sValue);
					}
				}
			} while (true);

			System.out.println("接收post发送数据:\n" + strBuf.toString().trim());
		} else {
			System.out.println("接收get发送数据:\n" + queryString.toString().trim());
		}

		System.out.println("====>打印请求参数结束....");
	}

	private TokenReturnInfo tokenCallImpl(Interface2Service interfaceInfo,Object requestBody) throws Exception{
		TokenReturnInfo result = null;
		Object implService = SpringBeanUtil.getBean(interfaceInfo
				.getServiceImpl());
		if(requestBody!=null && requestBody!="" && ((HashMap)requestBody).size()>0){
			Class<?> inParamClass = getClassByName(interfaceInfo.getInparamType());
			Class[] argTypes = new Class[] { inParamClass };
			Method apiImplMethod = implService.getClass().getMethod(
					interfaceInfo.getMethodName(), argTypes);
			String json = jsonMapper.writeValueAsString(requestBody);
			Object inObject = jsonMapper.readValue(json, inParamClass);
			Object obj = apiImplMethod.invoke(implService, inObject);
			if(obj!=null){
				result = (TokenReturnInfo)obj;
			}
		}else{
			Method apiImplMethod = implService.getClass().getMethod(
					interfaceInfo.getMethodName());
			Object obj = apiImplMethod.invoke(implService);
			if(obj!=null){
				result = (TokenReturnInfo)obj;
			}
		}
		return result;
	}

	public void bulidLoginInfo(String authorization,HttpServletRequest request) throws Exception{
		try {
			Jws<Claims> claimsJws = JwtUtil.parseJWT(authorization);
			Claims claims = claimsJws.getBody();
			String subject = claims.getSubject();
			byte[] bytes = new BASE64Decoder().decodeBuffer(subject);
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
			GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);

//			IOUtils.copy(gzipInputStream, out);
			ObjectMapper obj = new ObjectMapper();
			LoginInfo loginInfo = obj.readValue(gzipInputStream,LoginInfo.class);
			AuthContextHolder.setLoginInfo(loginInfo);
//			request.setAttribute(SecurityFilter.TONKE_LOGIN_INFO, loginInfo);
		} catch (Exception e) {
			throw new Exception(IErrorDefConstant.ERROR_TOKEN_FAIL_MSG);
		}
	}

	public void tokenPost(HttpServletRequest request,HttpServletResponse response) throws  IOException {
		TokenReturnInfo returnInfo = new TokenReturnInfo();

		AccessLog accessLog = new AccessLog();
		ApiMethodInfo apiCallInfo = new ApiMethodInfo();
		GlobalCacheMgr.setSession(request.getSession());

		BaseApiRequest apiReq = new BaseApiRequest();
		Trace bizTrace = null;

		// printConsolelog(request);
		Interface2Service interfaceInfo = null;
		String authorization = request.getHeader("Authorization");

		try {

			// 初始化日志记录
			accessLog.setClientIP(request.getRemoteHost());
			accessLog.setCallMethod("N/A");
			accessLog.setCallTime(new Date());
			accessLog.setRequest("");
			accessLog.setResponse("");
			apiCallInfo.setCallTime(accessLog.getCallTime());
			apiCallInfo.setSuccess(false);

			// 获取登录操作员信息
			LoginUser loginUser = GlobalCacheMgr.getLoginUser();
			if (null != loginUser) {
				accessLog.setClientMAC(loginUser.getMAC());
				accessLog.setClientIMEI(loginUser.getIMEI());
				accessLog.setOpCode(loginUser.getOpCode());
				accessLog.setAreaId(loginUser.getAreaId());
			}
			//获取token内用户信息并存到request
			bulidLoginInfo(authorization,request);

			// 读取POST的报文
			StringBuilder postJson = new StringBuilder();
			BufferedReader httpReader = request.getReader();
			String line = null;
			while ((line = httpReader.readLine()) != null) {
				postJson.append(line);
			}

			// 转换请求JSON报文为RequestWrapper对象
			TokenRequestWrapper requestWrapper = jsonMapper.readValue(
					postJson.toString(), TokenRequestWrapper.class);
			if ((null == requestWrapper) || (null == requestWrapper.getApi())) {
				throw new RemoteCallException(
						IErrorDefConstant.ERROR_INVALID_JSON_MSG,
						IErrorDefConstant.ERROR_INVALID_JSON_CODE);
			}
			accessLog.setRequest(BeanUtil.object2String(requestWrapper.getRequestBody()));
			accessLog.setCallMethod(requestWrapper.getApi());
			apiCallInfo.setApiId(requestWrapper.getApi());


			// 设置接口请求信息
			setBasebizparam(apiReq);

			// 登记业务轨迹表biz_trace，主要是写一条订单id为apiReq.getBizorderid()记录
			// bizorderid相当于业务流水号，业务层通过bizorderid就可以对biz_trace表进行存取(如果非要在业务层操作biz_trace的话)
			bizTrace = regBizTrace(apiReq, accessLog);

			// 根据API接口获取配置对应的实现函数
			interfaceInfo = (Interface2Service) baseService
					.findUniqueByProperty(Interface2Service.class,
							"interfaceName", requestWrapper.getApi());
			baseService.evit(interfaceInfo);
			if (null == interfaceInfo) {
				throw new RemoteCallException(String.format(
						IErrorDefConstant.ERROR_INVALID_API_MSG,
						requestWrapper.getApi()),
						IErrorDefConstant.ERROR_INVALID_API_CODE);
			}


			// 通过反射调用实现函数  如果存在token，则走下面逻辑
			returnInfo = tokenCallImpl(interfaceInfo, requestWrapper.getRequestBody());

			if (null == returnInfo) {
				throw new RemoteCallException(String.format(
						IErrorDefConstant.ERROR_API_NO_IMPLEMENTED_MSG,
						requestWrapper.getApi()),
						IErrorDefConstant.ERROR_API_NO_IMPLEMENTED_CODE);
			}

			apiCallInfo.setSuccess(true);
		} catch (RemoteCallException Err) {
			log.error("apiServlet::doPost", Err);
			returnInfo.setCode(Err.getErrorCode());
			returnInfo.setMessage(Err.getMessage());
		} catch (ClassNotFoundException Err) {
			log.error("apiServlet::doPost", Err);
			returnInfo.setCode(IErrorDefConstant.ERROR_ClassNotFoundException);
			returnInfo.setMessage(Err.getMessage());
		} catch (InvocationTargetException e) {

			String code = String
					.valueOf(IErrorDefConstant.ERROR_InvocationTargetException);
			String message = e.getMessage();

			if (e.getTargetException().getClass()
					.equals(BusinessException.class)) {
				BusinessException be = (BusinessException) e
						.getTargetException();

				if (be.getRetcode() != null) {
					code = be.getRetcode().toString();
				}

				message = be.getMessage();
			}else if (e.getTargetException().getClass().equals(TransactionSystemException.class)) {
				TransactionSystemException tranException = (TransactionSystemException) e.getTargetException();
				if (tranException.getOriginalException() instanceof BusinessException) {
					BusinessException be = (BusinessException) tranException.getOriginalException();
					if (be.getRetcode() != null) {
						code = be.getRetcode().toString();
					}
					message = be.getMessage();
				}
			}

			log.error("apiServlet::doPost", e);
			returnInfo.setCode(Long.valueOf(code));
			returnInfo.setMessage(message);
		} catch (NoSuchMethodException Err) {
			log.error("apiServlet::doPost", Err);
			returnInfo.setCode(IErrorDefConstant.ERROR_NoSuchMethodException);
			returnInfo.setMessage(Err.getMessage());
		} catch (InstantiationException Err) {
			log.error("apiServlet::doPost", Err);
			returnInfo.setCode(IErrorDefConstant.ERROR_InstantiationException);
			returnInfo.setMessage(Err.getMessage());
		} catch (IllegalAccessException Err) {
			log.error("apiServlet::doPost", Err);
			returnInfo.setCode(IErrorDefConstant.ERROR_IllegalAccessException);
			returnInfo.setMessage(Err.getMessage());
		} catch (RuntimeException Err) {
			log.error("apiServlet::doPost", Err);
			returnInfo.setCode(IErrorDefConstant.ERROR_RuntimeException_CODE);
			returnInfo.setMessage(Err.getMessage());
		} catch (JsonParseException Err) {
			log.error("initServlet::doPost", Err);
			returnInfo.setCode(IErrorDefConstant.ERROR_INVALID_JSON_CODE);
			returnInfo.setMessage(IErrorDefConstant.ERROR_INVALID_JSON_MSG);
		} catch (Exception Err) {
			log.error("initServlet::doPost", Err);
			returnInfo.setCode(IErrorDefConstant.ERROR_INVALID_JSON_CODE);
			returnInfo.setMessage(Err.getMessage());
			Err.printStackTrace();
		} finally {
			// 返回响应报文
			String responseJSON = null;
			if(returnInfo.getData()!=null &&
					returnInfo.getData() instanceof JSONObject ||
					returnInfo.getData() instanceof JSONArray){
				JSONObject obj = new JSONObject();
				try {
					obj.put("code", returnInfo.getCode());
					obj.put("message", returnInfo.getMessage());
					obj.put("data",returnInfo.getData());
					responseJSON = obj.toString();
				}catch (Exception e){
					e.printStackTrace();
					returnInfo.setCode(-1L);
					returnInfo.setMessage("返回数据格式处理错误!");
					returnInfo.setData(null);
					responseJSON = jsonMapper.writeValueAsString(returnInfo);
				}
			}else {
				responseJSON = jsonMapper.writeValueAsString(returnInfo);
			}
			// 记录访问日志
			accessLog.setReturnCode(returnInfo.getCode());
			accessLog.setReturnMsg(returnInfo.getMessage());
			accessLog.setEndTime(new Date());
			accessLog.setOrderid(apiReq.getBizorderid());
			accessLog.setResponse(responseJSON);
			try {
				baseService.save(accessLog);
				updateBizTrace(bizTrace, accessLog);// 更新业务轨迹表
			} catch (Exception e) {
				log.error("", e);
			}

			// 统计API调用
			apiCallInfo.setEchoTime(accessLog.getEndTime());

			response.setCharacterEncoding("GBK");
			response.setContentType("text/html;charset=GBK");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			PrintWriter writer = response.getWriter();
			writer.print(responseJSON);
		}
	}
}
