package com.maywide.core.security.remote;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 外部系统接口调用实现
 *
 */
public class RemoteCallUtil {
    private static Logger log = LoggerFactory.getLogger(RemoteCallUtil.class);

    private static final String HTTP_JSON_PROTOCOL = "HTTP";
    private static final String RAW_SOCKET_PROTOCOL = "SOCKET";
    private static final String WSDL_SOAP_PROTOCOL = "SOAP";

    // 接口定义缓存
    private static Map<String, RemoteCall> api2RemoteMap = new HashMap<String, RemoteCall>();

    /**
     * 调用外部系统接口
     * @param apiId 接口名称 映射crm4app_remote_call表中api_id字段
     * @param request 请求入参报文
     * @return 响应报文
     */
    public static String call(final String apiId, final String request) throws Exception {
        RemoteCall remoteCall = api2RemoteMap.get(apiId);
        if (null == remoteCall) {
            
            api2RemoteMap.put(apiId, remoteCall);
        }

        // 根据协议不同进行调用
        if (remoteCall.getProtocol().equalsIgnoreCase(RAW_SOCKET_PROTOCOL)) {
            return callBySocket(remoteCall, request);
        } else if (remoteCall.getProtocol().equalsIgnoreCase(WSDL_SOAP_PROTOCOL)) {
            return callBySoap(remoteCall, request);
        } else if (remoteCall.getProtocol().equalsIgnoreCase(HTTP_JSON_PROTOCOL)) {
            return callByHttp(remoteCall, request);
        } else {
            return callByHttp(remoteCall, request);
        }
    }

    private static String callByHttp(final RemoteCall remoteCall, final String request) throws IOException {
        return HttpClientImpl.call(remoteCall, request);
    }

    private static String callBySocket(final RemoteCall remoteCall, final String request) {
        return null;
    }

    private static String callBySoap(final RemoteCall remoteCall, final String request) {
        return null;
    }
}
