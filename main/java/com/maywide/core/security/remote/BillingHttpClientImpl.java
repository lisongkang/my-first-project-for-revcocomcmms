package com.maywide.core.security.remote;



import com.googlecode.jsonplugin.JSONUtil;
import com.maywide.biz.core.entity.RemotecallLog;
import com.maywide.biz.core.pojo.LoginInfo;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.xmlrpc.util.HttpUtil;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by minch on 2019/3/20 0020 14:53.
 */
public class BillingHttpClientImpl {

    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    public static String getContentPost(String url, Map<String, String> params) throws Exception {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        if (params != null) {
            List<NameValuePair> paramList = new ArrayList<NameValuePair>();

            for (Map.Entry<String, String> entry : params.entrySet()) {
                paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            httpPost.setEntity(new UrlEncodedFormEntity(paramList, Charset.forName("utf-8")));
        }
        CloseableHttpResponse response = client.execute(httpPost);

        HttpEntity entity = response.getEntity();
        InputStream content = entity.getContent();

        StringBuilder result = new StringBuilder();
        String line = null;

        InputStreamReader reader = new InputStreamReader(content, Charset.forName("utf-8"));
        BufferedReader br = new BufferedReader(reader);

        while ((line = br.readLine()) != null) {
            result.append(line);
        }

        return result.toString();
    }


    public static String getContentPost(String url) throws Exception {
        return getContentPost(url, null);
    }

    public static RemotecallLog getContentPostByJson(String url, Map<String,String> params) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        return  getContentByJson(url,params,httpPost);
    }
    public static RemotecallLog getContentPutByJson(String url, Map<String,String> params) throws Exception {
        HttpPut httpPut = new HttpPut(url);
        return getContentByJson(url,params,httpPut);
    }

    public static RemotecallLog getContentByJson(String url, Map<String,String> params, HttpEntityEnclosingRequestBase httpType) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();

        String json = JSONObject.toJSONString(params);
        StringEntity entity2 = new StringEntity(json, "utf-8");
        entity2.setContentEncoding("UTF-8");
        entity2.setContentType("application/json");
        httpType.setEntity(entity2);

        RemotecallLog retRemotecallLog = new RemotecallLog();
        retRemotecallLog.setCalltime(new Date());
        String status = "0";
        String retmsg = "";
        String responseStr = "";
        CloseableHttpResponse response = client.execute(httpType);

        HttpEntity entity = response.getEntity();
        InputStream content = entity.getContent();

        StringBuilder result = new StringBuilder();
        String line = null;

        InputStreamReader reader = new InputStreamReader(content, Charset.forName("utf-8"));
        BufferedReader br = new BufferedReader(reader);

        while ((line = br.readLine()) != null) {
            result.append(line);
        }
        int httpCode = response.getStatusLine().getStatusCode();
        log.info("=========>httpCode=" + httpCode);
        if (httpCode == 200) {
            // 5.读取内容
            responseStr = result.toString();
            log.info("=========>" + params.get("api") + " responseStr=" + responseStr);
        } else if (httpCode == 404) {
            status = "11";
            retmsg = "接口不存在404";
        } else if (httpCode == 500) {
            status = "12";
            retmsg = "接口调用失败500";
        } else {
            status = "13";
            retmsg = "接口调用失败" + httpCode;
        }

        retRemotecallLog.setServiceurl(url);
        retRemotecallLog.setProtocol("http");

        String request = JSONUtil.serialize(params);
        retRemotecallLog.setRequest(request);
        retRemotecallLog.setEndtime(new Date());
        retRemotecallLog.setRetcode(status);
        retRemotecallLog.setRetmsg(retmsg);
        retRemotecallLog.setResponse(responseStr);

        return retRemotecallLog;
    }

    public static String getContentGet(String url, Map<String, String> params) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);
        if(params!=null && !params.isEmpty()){
            for (String s : params.keySet()) {
                uriBuilder.addParameter(s,params.get(s));
            }
        }
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uriBuilder.build());

        CloseableHttpResponse response = client.execute(httpGet);

        HttpEntity entity = response.getEntity();
        InputStream content = entity.getContent();

        StringBuilder result = new StringBuilder();
        String line = null;

        InputStreamReader reader = new InputStreamReader(content, Charset.forName("utf-8"));
        BufferedReader br = new BufferedReader(reader);

        while ((line = br.readLine()) != null) {
            result.append(line);
        }

        return result.toString();
    }

}
