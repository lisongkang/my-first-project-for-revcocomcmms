package com.maywide.biz.core.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.maywide.biz.core.entity.AccessLog;
import com.maywide.biz.core.pojo.RemoteCallException;
import com.maywide.biz.core.pojo.RequestWrapper;
import com.maywide.biz.core.pojo.ResponseWrapper;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.security.encrypt.ISecurityService;
import com.maywide.core.security.encrypt.SecurityFactory;
import com.maywide.core.service.PersistentService;

/**
 * 客户端首次连接做握手操作,完成与客户端约定的业务操作加密算法
 */
public class InitServlet extends HttpServlet {
    private static final long serialVersionUID = 4293493091077873447L;
    private static Logger log = LoggerFactory.getLogger(InitServlet.class);

    private ObjectMapper jsonMapper = null;
    private PersistentService baseService = null;
    private ISecurityService securityService = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // 初始化Spring
        SpringBeanUtil.setWebCTX(config.getServletContext());
        baseService = (PersistentService) SpringContextHolder.getBean(PersistentService.class);
        
        if (null == jsonMapper) {
            jsonMapper = new ObjectMapper();
            jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }

        if (null == securityService) {
//        	securityService = SecurityFactory.getSecurityService();
//        	if (null == securityService) {
        		securityService = SecurityFactory.getSecurityService(SecurityFactory.RSA_IMPL);

//                final String rsaKeyPair =
//                    "a6d9c3726719cee6d53b4a16b431b93e84952b6d6f0525ed03291024a45c1cb5cb904db392c9d2b4880e94e3ce22a52d14f3bc45481a4f956e89dd7e52b6faf1," +
//                    "16ffe9ab397f1b4042a07ba4098ed9243f0e79000acfc0dc9d14e9fec311577b969c5d42d6009c906a27d708bbd194cdf18ffa4baa47b3e06d61b340953a7561";
        		
        		final String rsaKeyPair = "910BB48C1B4DF9561B530E7340F1EEE82AEA9647635DE2985E56C08F0B3BA6FF14F9020B5F4C7A1A4E0CE74FF388CBB9A6A00F152FD3CEDE50093036DE258CF9,"
        				+ "108BB491826D3F228CC15468FAF1F89DE37ABA7BC85B369E983E9432CC943927AE7C5DC23FAFEADCB9BF362B66E22F07EA194BF94176B315E400E494738A926F";
                securityService.init(rsaKeyPair);
//            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ReturnInfo returnInfo = new ReturnInfo();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        AccessLog accessLog = new AccessLog();

        try {
            // 初始化日志记录
            accessLog.setClientIP(request.getRemoteHost());
            accessLog.setCallMethod("/init");
            accessLog.setCallTime(new Date());
            accessLog.setRequest("");
            accessLog.setResponse("");

            // 读取POST的报文
            StringBuilder postJson = new StringBuilder();
            BufferedReader httpReader = request.getReader();
            String line = null;
            while ((line = httpReader.readLine()) != null) {
                postJson.append(line);
            }

            // 转换请求JSON报文为RequestWrapper对象
            RequestWrapper requestWrapper = new RequestWrapper();
            requestWrapper = jsonMapper.readValue(postJson.toString(), RequestWrapper.class);
            if ((null == requestWrapper) || (null == requestWrapper.getRequestBody())) {
                throw new RemoteCallException(
                    IErrorDefConstant.ERROR_INVALID_JSON_MSG,
                    IErrorDefConstant.ERROR_INVALID_JSON_CODE
                );
            }

            // RSA算法解密业务报文体
            String requestBody = securityService.decrypt(requestWrapper.getRequestBody());
            if (null == requestBody) {
                throw new RemoteCallException(
                    IErrorDefConstant.ERROR_INVALID_SECURITY_MSG,
                    IErrorDefConstant.ERROR_INVALID_SECURITY_CODE
                );
            }
            accessLog.setRequest(requestBody);

            // 获取配置参数securityClass/securityKey并生成JSON报文
            ObjectNode resultNode = jsonMapper.createObjectNode();
            resultNode.put("securityClass", SysConfig.getSecurityClass());
            resultNode.put("securityKey", SysConfig.getSecurityKey());

            // RSA算法加密JSON明文,并创建ResponseWrapper对象
            String responseBody = resultNode.toString();
            accessLog.setResponse(responseBody);

            responseWrapper.setResponseBody(securityService.encrypt(responseBody));
            returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
            returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        } catch (RemoteCallException Err) {
            log.debug("initServlet::doPost", Err);
            returnInfo.setCode(Err.getErrorCode());
            returnInfo.setMessage(Err.getMessage());
        } catch (JsonParseException Err) {
            log.debug("initServlet::doPost", Err);
            returnInfo.setCode(IErrorDefConstant.ERROR_INVALID_JSON_CODE);
            returnInfo.setMessage(IErrorDefConstant.ERROR_INVALID_JSON_MSG);
        } finally {
            // 返回响应报文
            responseWrapper.setReturnInfo(returnInfo);
            String responseJSON = jsonMapper.writeValueAsString(responseWrapper);

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
            try {
            	baseService.save(accessLog);
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
    }
}
