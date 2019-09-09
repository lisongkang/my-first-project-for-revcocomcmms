package com.maywide.biz.pay.weixin;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.ConnectionPoolTimeoutException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.pay.weixin.entity.PayCallbackNotify;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.mcr.MicroX509TrustManager;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.PersistentService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

@Service
@Transactional
public class WeixinPayService {
	public static final String UNIFY_PAY_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public static final String UNIFY_REFUND_API = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	
	private static HttpClientBuilder httpClientBuilder;
	
	private static final Logger logger = LoggerFactory.getLogger(WeixinPayService.class);
	@Autowired
	private PersistentService persistentService;
	@Autowired
    private ParamService paramService;
	
	public String nativePay(Object data) throws Exception {
		String codeurl = null;
		//接受API返回
        String payServiceResponseString;
        payServiceResponseString = new HttpsRequest().sendPost(UNIFY_PAY_API, data);
        
        //打印回包数据
        logger.info("统一下单服务回包:");
        logger.info(payServiceResponseString);
        
        //将从API返回的XML数据映射到Java对象
        UnifyPayResData unifyPayResData = (UnifyPayResData) Util.getObjectFromXML(payServiceResponseString, UnifyPayResData.class);
        
        String retmsg = "";
        if (unifyPayResData == null || unifyPayResData.getReturn_code() == null) {
        	retmsg = "【支付失败】支付请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问";
            logger.error(retmsg);
            throw new Exception(retmsg);
        }
        
        //验证签名，无论业务返回成功与否
        if (!Signature.checkIsSignValidFromResponseString(payServiceResponseString)) {
           /*retmsg = "【支付失败】支付请求API返回的数据签名验证失败，有可能数据被篡改了";
           logger.error(retmsg);
           throw new Exception(retmsg);*/
       }

        if (unifyPayResData.getReturn_code().equals("FAIL")) {
            //注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
        	retmsg = "【支付失败】支付API系统返回失败，请检测Post给API的数据是否规范合法";
            logger.error("return_msg: "+unifyPayResData.getReturn_msg());
            logger.error(retmsg);
            throw new Exception(retmsg);
        } else {
            logger.info("支付API系统成功返回数据");
            //获取错误码
            String errorCode = unifyPayResData.getErr_code();
            //获取错误描述
            String errorCodeDes = unifyPayResData.getErr_code_des();

            if (unifyPayResData.getResult_code().equals("SUCCESS")) {
                //--------------------------------------------------------------------
                //1)统一下单成功
                //--------------------------------------------------------------------

                logger.info("【统一下单成功】");

                if(!unifyPayResData.getCode_url().equals("")){
                	String code_url = unifyPayResData.getCode_url();
                	logger.info("code_url = " + code_url);
                	codeurl = code_url;
                }
                else { //code_url是空
                	retmsg = "【支付失败】支付API系统返回失败，code_url是空";
                    logger.error(retmsg);
                    throw new Exception(retmsg);
                }
                          	                
            }
            else//统一下单失败
            {
            	logger.error(errorCodeDes);
                throw new Exception(errorCodeDes);
            }
        }
        
        return codeurl;
	}
	
	public void refund(String orderid) throws Exception {
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		if (loginInfo == null) {
        	throw new Exception("用户未登录或已过期");
        }
		
		PrvSysparam sysparam = paramService.getData("SYS_WX_PAYACCT", loginInfo.getCity());
    	if (sysparam == null) throw new Exception("服务端没有配置微信的支付账号信息");
    	String[] certdata = sysparam.getData().split("~");
		
		PayCallbackNotify notify = (PayCallbackNotify) persistentService.
							findUniqueByProperty(PayCallbackNotify.class, "out_trade_no", orderid);
		
		Refund refund = new Refund();
		BeanUtils.copyProperties(refund, notify);
		
		refund.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
		refund.setOp_user_id(AuthContextHolder.getLoginInfo().getLoginname());
		refund.setRefund_fee(notify.getTotal_fee());
		refund.setRefund_fee_type("CNY");
		refund.setOut_refund_no(notify.getOut_trade_no());
		
		Map<String, String> map = BeanUtils.describe(refund);
		map.remove("class");
		map.remove("sign");
		
		String sign = Signature.generateSign(map, certdata[2]);
		refund.setSign(sign);
		
		//String response = new HttpsRequest().sendPost(UNIFY_REFUND_API, refund);
		
		XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
		String xmlContent = xStreamForRequestPostData.toXML(refund);
		
		String response = sendPost(UNIFY_REFUND_API, xmlContent, certdata);
		
		//将从API返回的XML数据映射到Java对象
		RefundRes refundRes = (RefundRes) Util.getObjectFromXML(response, RefundRes.class);
		
		String retmsg = "";
        if (refundRes == null || refundRes.getReturn_code() == null) {
        	retmsg = "【失败】支付请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问";
            logger.error(retmsg);
            throw new Exception(retmsg);
        }
        
        if (refundRes.getResult_code().equals("SUCCESS")) {
        	logger.info("【退款成功】");
        	notify.setStatus(BizConstant.BizWeixinPaystatus.REFUND);
        } else {
        	logger.error("微信退款失败：" + refundRes.getErr_code_des());
        	notify.setStatus(BizConstant.BizWeixinPaystatus.REFUND_FAILED);
        	throw new Exception(refundRes.getErr_code_des());
        }
        persistentService.save(notify);
	}
	
	private String sendPost(String url, String postDataXML, String[] certdata) throws Exception {
		if (httpClientBuilder == null) {
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			Resource resource = new ClassPathResource(certdata[4]);
			InputStream instream = resource.getInputStream();
			//FileInputStream instream = new FileInputStream(new File("F:/tmp/GZ/apiclient_cert.p12"));// 加载本地的证书进行https加密传输
			try {
				keyStore.load(instream, certdata[5].toCharArray());// 设置证书密码
			} catch (CertificateException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} finally {
				instream.close();
			}
	
			// Trust own CA and all self-signed certs
			KeyManagerFactory km = KeyManagerFactory.getInstance("SunX509");
			km.init(keyStore, certdata[5].toCharArray());
			TrustManager[] tm = { new MicroX509TrustManager() };
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(km.getKeyManagers(), tm, new java.security.SecureRandom());
	
			X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
	            public boolean verify(String arg0, SSLSession arg1) {
	                return true;
	            }
	            public void verify(String arg0, SSLSocket arg1) throws IOException {}
	            public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {}
	            public void verify(String arg0, X509Certificate arg1) throws SSLException {}
	        };
			// Allow  TLSv1 protocol only
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslcontext, new String[] { "TLSv1" }, null,
					hostnameVerifier);
			
			httpClientBuilder = HttpClients.custom().setSSLSocketFactory(sslsf);
		}

		// HTTP请求器
		CloseableHttpClient httpClient = httpClientBuilder.build();
		
		// 连接超时时间，默认10秒
		int socketTimeout = 10000;

		// 传输超时时间，默认30秒
		int connectTimeout = 30000;

		// 根据默认超时限制初始化requestConfig 
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeout).build();


		String result = null;

		HttpPost httpPost = new HttpPost(url);

		System.out.println(postDataXML);
		
		// 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
		StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
		httpPost.addHeader("Content-Type", "text/xml");
		httpPost.setEntity(postEntity);

		// 设置请求器的配置 
		httpPost.setConfig(requestConfig);

		// Util.log("executing request" + httpPost.getRequestLine());

		try {
			HttpResponse response = httpClient.execute(httpPost);

			HttpEntity entity = response.getEntity();

			result = EntityUtils.toString(entity, "UTF-8");

		} catch (ConnectionPoolTimeoutException e) {
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpPost.abort();
		}
		System.out.println(result);
		return result;
	}
	
	public static void main(String[] args) {
		try {
			String[] certdatas = "wx78842399017277e4~10074270~EFFC44744815254B887EA42830A2C7E8~http://210.21.65.94:6062/moss/pub/paynotify.servlet~/key/DG/apiclient_cert.p12~10074270".split("~");
			//String[] certdatas = "wxb308ad712f6decea~1220354901~dddc814c3b3b9003f4a5a2df2af16390~http://210.21.65.94:6062/moss/pub/paynotify.servlet~/key/gz/apiclient_cert.p12~1220354901".split("~");
			//String[] certdatas = "wxb308ad712f6decea~1220354901~dddc814c3b3b9003f4a5a2df2af16390~http://210.21.65.94:6062/moss/pub/paynotify.servlet~/key/gz/apiclient_cert.p12~1220354901".split("~");
			WeixinPayService payService = new WeixinPayService();
			
			PayPackage payPackage = new PayPackage();

			payPackage.setAppid(certdatas[0]);
			payPackage.setAttach("test");
			payPackage.setBody("订单支付");
			payPackage.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
			payPackage.setMch_id(certdatas[1]);
			payPackage.setOut_trade_no("10677974");
			//payPackage.setProduct_id("123");
			payPackage.setSpbill_create_ip("127.0.0.1");
			payPackage.setTotal_fee("1");
			payPackage.setTrade_type("NATIVE");
			payPackage.setNotify_url(certdatas[3]);
			
			Map<String, String> map = BeanUtils.describe(payPackage);
			map.remove("class");
			
			String sign = Signature.generateSign(map, certdatas[2]);
			payPackage.setSign(sign);
			
			payService.nativePay(payPackage);
			
			
			//payService.refund("1091013");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
