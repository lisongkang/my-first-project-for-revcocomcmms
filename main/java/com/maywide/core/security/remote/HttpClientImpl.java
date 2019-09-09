package com.maywide.core.security.remote;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 实现HTTP+JSON远程接口调用
 */
public class HttpClientImpl {
    private static Logger log = LoggerFactory.getLogger(HttpClientImpl.class);

    private static int httpTimeout = 60000;
    		
    /**
     * 获取连接
     * @return CloseableHttpClient
     */
    private static CloseableHttpClient getHttpClient() {
        PoolingHttpClientConnectionManager clientConnectionMgr = new PoolingHttpClientConnectionManager();
        clientConnectionMgr.setDefaultMaxPerRoute(32);
        clientConnectionMgr.setMaxTotal(64);

        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(httpTimeout * 1000)
            .setConnectionRequestTimeout(httpTimeout * 1000)
            .setSocketTimeout(httpTimeout * 1000)
            .build();

        ConnectionConfig connectionConfig = ConnectionConfig.custom()
            .setCharset(Charset.forName("UTF-8"))
            .build();
        return HttpClients.custom()
            .setConnectionManager(clientConnectionMgr)
            .setDefaultRequestConfig(requestConfig)
            .setDefaultConnectionConfig(connectionConfig)
            .build();
    }

    /**
     * 调用远程HTTP接口
     * @param remoteCall 远程HTTP接口配置信息
     * @param request 请求报文
     * @return String 响应报文
     * @throws IOException
     */
    public static String call(final RemoteCall remoteCall, final String request) throws IOException {

        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse httpResponse = null;
        try {
        	String logContent = String.format("HttpClientImpl::调用外部系统接口%s, 主机%s 端口%s URL%s",
                    remoteCall.getInterfaceName(),
                    remoteCall.getRemoteIP(),
                    remoteCall.getRemotePort(),
                    remoteCall.getCallUrl());
        	
            log.debug(logContent);

            // 创建请求对象
            HttpHost host = new HttpHost(
                remoteCall.getRemoteIP(),
                (0 == remoteCall.getRemotePort()) ? 80 : remoteCall.getRemotePort()
            );
            HttpPost httpPost = new HttpPost(remoteCall.getCallUrl());
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            httpPost.setEntity(new StringEntity(request, "utf-8"));

            // 执行请求
            httpResponse = httpClient.execute(host, httpPost);

            log.debug(
                "HttpClientImpl::调用外部系统接口{} 请求返回代码{}",
                remoteCall.getInterfaceName(),
                httpResponse.getStatusLine().toString()
            );

            // 获取返回结果
            return EntityUtils.toString(httpResponse.getEntity());
        } catch (ClientProtocolException Err) {
            log.debug("HttpClientImpl::call()", Err);
            throw Err;
        } catch (UnsupportedEncodingException Err) {
            log.debug("HttpClientImpl::call()", Err);
            throw Err;
        } catch (IOException Err) {
            log.debug("HttpClientImpl::call()", Err);
            throw Err;
        } finally {
            httpClient.close();
            if (null != httpResponse) {
                httpResponse.close();
            }
        }
    }
}
