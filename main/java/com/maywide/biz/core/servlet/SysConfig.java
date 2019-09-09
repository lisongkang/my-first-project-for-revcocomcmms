package com.maywide.biz.core.servlet;

//import static org.hamcrest.CoreMatchers.nullValue;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 全局配置参数
 */
public class SysConfig implements Serializable {
    private static final long serialVersionUID = 6999619696177381206L;
    private static Logger log = LoggerFactory.getLogger(SysConfig.class);

    private static String securityClass = null;         // 实现加解密的类名称
    private static String securityKey = null;           // 实现加解密的密钥明文
    
    private static boolean needEnrollment = false;      // 是否需要先注册客户端设备
    private static String apkReleaseUrl = null;         // apk下载地址
    
    //boss http接口参数
    private static String bossUrl = null;
    private static String paramVersion = null;
    private static String paramClientCode = null;
    private static String cityCode = null;
    private static String clientPasswd = null;
    private static int httpTimeout = 0;
    
    //统一认证平台接口参数
    private static String uapUrl = null;
    private static String uapClientid = null;
    private static String uapClientPwd = null;
    
    private static String bossUapurl = null;
    
    //超级(免验证)验证码
    private static String superRandomCode = null;
    
    private static int commonFlag = 0;
    
    //微厅二维码接口参数
    
    private static String clientcode = null;
    
    private static String clientpwd = null;
    
    private static String grCodeUrl = null;
    
    private static String funkey = null; 
    
    private static String publishnum = null;
    
    
    //大连网格营销区别标识
    private static String serviceCity = null;
    
    
    //阿里大于短信网关参数
    
    private static String ali_url = null;
    
    private static String ali_app_key = null;
    
    private static String ali_app_sercet = null;
    
    //统一支付平台参数
    
    private static String platform_url = null;
    
    private static String platform_code = null;
    
    private static String platform_pwd = null;
    
    private static String platform_version = null;
    
    private static String platform_MD5 = null;
    
    private static String platform_outer_url = null;
    
    private static String platform_redirect_url = null;
    
    private static String platform_notice_url = null;
    
    //cmp
    private static String cmpUrl = null;
    
    //cmp api接口
    private static String cmpApiUrl = null;

    //oss http接口
	private static String osshttp_url = null;
    
    //微厅
    private static String mcrUrl = null;
    
    //oauth
    private static String oAuthUrl = null;
    
    //AM5
    private static String am5RpcUrl = null;
    
    private static String iomporjUrl = null;
    
    private static String dbConnection = null;
    
    private static String authUrl = null;

    private static String billingUrl = null;
    
    private static String fileUploadPath = null;

    /**
     * 加载配置参数表
     * @param configMap key为参数名称　value为参数值
     */
    static {
    	try {
    		Properties prop = new Properties();
    		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    		InputStream inputStream = classLoader.getResourceAsStream("sysconfig.properties");
    		prop.load(inputStream);
    		
    		securityClass = prop.getProperty("securityClass");
    		securityKey = prop.getProperty("securityKey");
    		needEnrollment = new Boolean(prop.getProperty("needEnrollment")).booleanValue();
    		apkReleaseUrl = prop.getProperty("apkReleaseUrl");
    		commonFlag = Integer.valueOf(prop.getProperty("commonFlag"));
    		
    		bossUrl = prop.getProperty("BOSS_URL");
    		paramVersion = prop.getProperty("PARAM_VERSION");
    		paramClientCode = prop.getProperty("PARAM_CLIENTCODE");
    		cityCode = prop.getProperty("CITY_CODE");
    		clientPasswd = prop.getProperty("CLIENT_PWD");
    		httpTimeout = Integer.valueOf(prop.getProperty("HTTP_TIMEOUT"));
    		
    		uapUrl = prop.getProperty("UAP_URL");
    		uapClientid = prop.getProperty("UAP_CLIENTID");
    		uapClientPwd = prop.getProperty("UAP_CLIENTPWD");
    		
    		bossUapurl = prop.getProperty("BOSS_UAP_URL");
    		
    		superRandomCode = prop.getProperty("SUPER_RANDOM_CODE");
    		
    		clientcode = prop.getProperty("CLIENT_CODE");
    		clientpwd = prop.getProperty("CLIENT_PASSWORD");
    		grCodeUrl = prop.getProperty("GR_CODE_URL");
    		funkey = prop.getProperty("FUN_KEY");
    		publishnum  = prop.getProperty("PUBLISHNUM");
    		serviceCity = prop.getProperty("SERVICE_CITY");
    		ali_url = prop.getProperty("ALIBABA_SMS_URL");
    		ali_app_key = prop.getProperty("ALIBABA_APP_KEY");
    		ali_app_sercet = prop.getProperty("ALIBABA_APP_SECRET");
    		
    		platform_url = prop.getProperty("PLATFORM_URL");
    		platform_code = prop.getProperty("PLATFORM_CODE");
    		platform_pwd = prop.getProperty("PLATFORM_PWD");
    		platform_version = prop.getProperty("PLATFORM_VERSION");
    		platform_MD5 = prop.getProperty("PLATFORM_MD5_KEY");
    		platform_outer_url = prop.getProperty("PLATFORM_OUTER_URL");
    		platform_redirect_url = prop.getProperty("PLATFORM_REDIRECT_URL");
    		platform_notice_url = prop.getProperty("PLATFORM_NOTICE_URL");
    	    	 
    		cmpUrl = prop.getProperty("CMP_URL");
    		
    		cmpApiUrl = prop.getProperty("CMP_API_URL");

    		mcrUrl = prop.getProperty("MCR_URL");
    		
    		oAuthUrl = prop.getProperty("OAUTH_URL");
    		
    		am5RpcUrl = prop.getProperty("AM5_RPC_URL");
    		
    		iomporjUrl = prop.getProperty("IOMPORJ_URL");
    		dbConnection = prop.getProperty("ORACLE_CONNECTION","N");
    		
    		
    		authUrl = prop.getProperty("AUTH_URL");
    		billingUrl = prop.getProperty("BILLING_URL");
    		
    		fileUploadPath = prop.getProperty("FILE_UPLOAD_PATH");
    		osshttp_url = prop.getProperty("OSSHTTP_URL");
		} catch (Exception e) {
			log.error("解析sysconfig.properties出错！", e);
			System.exit(1);
		}
    }

    public static String getUapClientPwd() {
		return uapClientPwd;
	}

	public static String getSecurityClass() {
        return securityClass;
    }

    public static String getSecurityKey() {
        return securityKey;
    }

    public static boolean isNeedEnrollment() {
        return needEnrollment;
    }

	public static String getApkReleaseUrl() {
		return apkReleaseUrl;
	}

	public static String getBossUrl() {
		return bossUrl;
	}

	public static String getParamVersion() {
		return paramVersion;
	}

	public static String getParamClientCode() {
		return paramClientCode;
	}

	public static String getCityCode() {
		return cityCode;
	}

	public static String getClientPasswd() {
		return clientPasswd;
	}
	
	public static int getHttpTimeout() {
		return httpTimeout;
	}
	
	public static int getCommonFlag(){
		return commonFlag;
	}

    public static String getUapUrl() {
        return uapUrl;
    }

    public static void setUapUrl(String uapUrl) {
        SysConfig.uapUrl = uapUrl;
    }

    public static void setSecurityKey(String securityKey) {
        SysConfig.securityKey = securityKey;
    }

    public static String getUapClientid() {
        return uapClientid;
    }

    public static void setUapClientid(String uapClientid) {
        SysConfig.uapClientid = uapClientid;
    }

    public static String getSuperRandomCode() {
        return superRandomCode;
    }

    public static void setSuperRandomCode(String superRandomCode) {
        SysConfig.superRandomCode = superRandomCode;
    }

	public static String getClientcode() {
		return clientcode;
	}

	public static String getClientpwd() {
		return clientpwd;
	}

	public static String getGrCodeUrl() {
		return grCodeUrl;
	}

	public static String getFunkey() {
		return funkey;
	}

	public static String getPublishnum() {
		return publishnum;
	}

	public static String getServiceCity() {
		return serviceCity;
	}

	public static String getAli_url() {
		return ali_url;
	}

	public static String getAli_app_key() {
		return ali_app_key;
	}

	public static String getAli_app_sercet() {
		return ali_app_sercet;
	}
	
	public static String getPlatform_url(){
		return platform_url;
	}
	
	public static String getPlatform_code(){
		return platform_code;
	}
	
	public static String getPlatform_pwd(){
		return platform_pwd;
	}
	
	public static String getPlatform_version(){
		return platform_version;
	}
	
	public static String getPlatform_MD5(){
		return platform_MD5;
	}
	
	public static String getPlatform_outer_url(){
		return platform_outer_url;
	}
	
	public static String getPlatform_redirect_url(){
		return platform_redirect_url;
	}
	
	public static String getPlatform_notice_url(){
		return platform_notice_url;
	}

	public static String getCmpUrl(){
		return cmpUrl;
	}
	
	public static String getBossUapurl(){
		return bossUapurl;
	}

	public static String getCmpApiUrl() {
		return cmpApiUrl;
	}

	public static String getMcrUrl() {
		return mcrUrl;
	}

	public static String getOAuthUrl() {
		return oAuthUrl;
	}
	
	public static String getAm5RpcUrl() {
		return am5RpcUrl;
	}

	public static String getIomporjUrl() {
		return iomporjUrl;
	}
	
	public static String getDBconnection(){
		return dbConnection;
	}

	public static String getAuthUrl() {
		return authUrl;
	}

	public static String getBillingUrl() {
		return billingUrl;
	}

	public static String getFileUploadPath() {
		return fileUploadPath;
	}

	public static String getOsshttp_url() {
		return osshttp_url;
	}

	public static void setOsshttp_url(String osshttp_url) {
		SysConfig.osshttp_url = osshttp_url;
	}
}
