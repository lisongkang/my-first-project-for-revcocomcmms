package com.maywide.core.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSON;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maywide.core.mcr.AccessToken;
import com.maywide.core.mcr.AuthInfor;
import com.maywide.core.mcr.BusinessException;
import com.maywide.core.mcr.MCRConstants;
import com.maywide.core.mcr.MicroX509TrustManager;

public class MicroUtil {
	private static Logger log = LoggerFactory.getLogger(MicroUtil.class);
//	public static String APPID = "";
//	public static String APPSECRET = "";
//	public static Map<String, SafetyBo> saftyData = new HashMap<String, SafetyBo>();
	
	//保存获取到的access_tocken 如果过期则重新获取
	private static Map<String,AccessToken> TOKEN_CATCH = new HashMap<String,AccessToken>();

	
	/**
	 * 发起https请求并获取结果
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MicroX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = new JSONObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return jsonObject;
	}
	
	
	/**
	 * 发起https请求并获取结果
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return XML
	 */
	public static Map<String, String> httpRequestXML(String requestUrl, String requestMethod, String outputStr) {
		StringBuffer buffer = new StringBuffer();
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MicroX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			
			// 读取输入流
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			System.out.println("接收的XML数据为:\n"+document.getText());
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List<Element> elementList = root.elements();

			// 遍历所有子节点
			for (Element e : elementList)
				map.put(e.getName(), e.getText());
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return map;
	}
	

	 /**
     * 转换授权访问URL
     * @param _url
     * @param publishid
     * @param params
     * 	      params参数请严格按照，渠道channel、地市city、openid、pagecode顺序传入
     * @return
	 * @throws Exception 
     */
   /* public static String convertAuthorizedAccessUrl(String _url,String publishid, String ...params) throws Exception{
    	if(_url==null||_url.equals(""))
    	{
    		return "";
    	}
    	if(_url.indexOf("?")==-1){
    		_url+="?publishid="+publishid;
    	}else{
    		_url+="&publishid="+publishid;
    	}
    	System.out.println("publishid:"+publishid);
    	String auto_code_url = MCRConstants.AUTH_CODE_URL;
    	SafetyBo safetybo = MicroUtil.saftyData.get(publishid);
    	if(safetybo==null){
    		throw new BusinessException("微信公众账号对应的安全数据未配置！请在参数表中配置！");
    	}
    	
    	McrInfo infobo = MessageUtil.getConfigKeyVlue(publishid,MCRConstants.McrConfigKey.DOMAIN_BASE_URL_KEY);
    	//在原url地址上增加vpm值
//    	String vpm = gainVPM(_url, params);
    	String _cvturl =  infobo.getUrl()+_url;

    	System.out.println("url:"+_cvturl);
    	auto_code_url = MicroUtil.replace("${APPID}", safetybo.getAppid(),auto_code_url);
    	auto_code_url = MicroUtil.replace("${REDIRECT_URI}", URLEncoder.encode(_cvturl, "UTF-8"),auto_code_url);
    	auto_code_url = MicroUtil.replace("${CODE}", "1",auto_code_url);
    	auto_code_url = MicroUtil.replace("${SCOPE}", "snsapi_base",auto_code_url); 
    	
    	return auto_code_url;
    }*/
    
    /**
     * 转换授权访问URL 不带公共号的
     * @param _url
     * @param publishid
     * @param params
     * 	      params参数请严格按照，渠道channel、地市city、openid、pagecode顺序传入
     * @return
	 * @throws Exception 
     */
    /*public static String convertAuthorizedUrl(String _url,String publishid, String ...params) throws Exception{
    	if(_url==null||_url.equals(""))
    	{
    		return "";
    	}
    	String auto_code_url = MCRConstants.AUTH_CODE_URL;
    	SafetyBo safetybo = MicroUtil.saftyData.get(publishid);
    	if(safetybo==null){
    		throw new BusinessException("微信公众账号对应的安全数据未配置！请在参数表中配置！");
    	}
    	
    	McrInfo infobo = MessageUtil.getConfigKeyVlue(publishid,MCRConstants.McrConfigKey.DOMAIN_BASE_URL_KEY);
    	String _cvturl =  infobo.getUrl()+_url;
    	System.out.println("url:"+_cvturl);
    	auto_code_url = MicroUtil.replace("${APPID}", safetybo.getAppid(),auto_code_url);
    	auto_code_url = MicroUtil.replace("${REDIRECT_URI}", URLEncoder.encode(_cvturl, "UTF-8"),auto_code_url);
    	auto_code_url = MicroUtil.replace("${CODE}", "1",auto_code_url);
    	auto_code_url = MicroUtil.replace("${SCOPE}", "snsapi_base",auto_code_url); 
    	
    	return auto_code_url;
    }*/
  
    /**
     * 获取根据code获取用户授权信息的接口
     * @param authInfor
     * @return
     * @throws BusinessException 
     */
    public static String getAuthAcessTokenUrl(AuthInfor authInfor) throws BusinessException{
    	String auth_access_token_url = MCRConstants.AUTH_ACCESS_TOKEN_URL;
    	System.out.println("publishid:"+authInfor.getPublishid());

    	auth_access_token_url = MicroUtil.replace("${APPID}", PropertyUtil.getValueFromProperites("application", "weixin.appid"),auth_access_token_url);
    	auth_access_token_url = MicroUtil.replace("${SECRET}", PropertyUtil.getValueFromProperites("application", "weixin.appsecret"),auth_access_token_url);
    	auth_access_token_url = MicroUtil.replace("${CODE}", authInfor.getCode(),auth_access_token_url);
    	return auth_access_token_url;
    }
    
    /**
     * 获取用户信息URL 
     * @param access_token
     * @param openid
     * @return
     */
    public static String getUserInfoUrl(String access_token,String openid){
    	String access_userinfo_url = MCRConstants.USERINFO_URL;
    	access_userinfo_url = MicroUtil.replace("${ACCESS_TOKEN}",access_token,access_userinfo_url);
    	access_userinfo_url = MicroUtil.replace("${OPENID}",openid,access_userinfo_url);
    	return access_userinfo_url;
    }
    /**
     * 获取主动推送微信客户端信息URL
     * @param access_token
     * @return
     */
    public static String getCustServUrl(String access_token){
    	String url = MCRConstants.CUSTSERV_URL;
    	url = MicroUtil.replace("${ACCESS_TOKEN}",access_token,url);
    	return url;
    }
    /** 
     * 替换字符串 
     * 
     * @param from String 原始字符串 
     * @param to String 目标字符串 
     * @param source String 母字符串 
     * @return String 替换后的字符串 
     */  
  public static String replace(String from, String to, String source) {  
      if (source == null || from == null || to == null)  
        return null;  
      StringBuffer bf = new StringBuffer("");  
      int index = -1;  
      while ((index = source.indexOf(from)) != -1) {  
        bf.append(source.substring(0, index) + to);  
        source = source.substring(index + from.length());  
        index = source.indexOf(from);  
      }  
      bf.append(source);  
      return bf.toString();  
  }  
  
	/**
	 * 获取access_token 这个地方获取的access_token
	 * 
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;
		if(TOKEN_CATCH.get(appid)!=null){
			accessToken = TOKEN_CATCH.get(appid);
			Date crdate = new Date();
			Date lstdate = accessToken.getCrdate();
			long a = crdate.getTime();
			long b = lstdate.getTime();
			int c = (int)((a - b) / 1000);
			if(c>=accessToken.getExpiresIn()){
				accessToken = null;
				TOKEN_CATCH.remove(appid);
			}else{
				return accessToken;
			}
		}
		String requestUrl = MCRConstants.ACCESS_TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
		System.out.println("获取票据的URL为"+requestUrl);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setCrdate(new Date());
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
				TOKEN_CATCH.put(appid, accessToken);
			} catch (JSONException e) {
				TOKEN_CATCH.remove(appid);
				accessToken = null;
				e.printStackTrace();
				// 获取token失败
				//log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}else{
			accessToken = null;
			TOKEN_CATCH.remove(appid);
		}
		return accessToken;
	}
	/**
	 * 获取刷新授权accesstoken
	 * @param request
	 * @param authInfor
	 * @param appid
	 * @param appsecret
	 * @return
	 */
	public static String getAuthAccessToken(HttpServletRequest request,AuthInfor authInfor,String appid, String appsecret) {
			Date crdate = new Date();
			Date lstdate = authInfor.getCrdate();
			long a = crdate.getTime();
			long b = lstdate.getTime();
			int c = (int)((a - b) / 1000);
			if(c>=Integer.parseInt(authInfor.getExpires_in())){//刷新
				//刷新 token
				String refreshUrl =   MCRConstants.ACCESS_REFRESH_TOKEN_URL;
				refreshUrl = MicroUtil.replace("${APPID}",appid,refreshUrl);
				refreshUrl = MicroUtil.replace("${REFRESH_TOKEN}",authInfor.getRefresh_token(),refreshUrl);
				JSONObject jsonObject = httpRequest(refreshUrl, "GET", null);
//				AccessToken refReshAccessToken = parseAccessTokenJson(jsonObject);
				ObjectMapper jsonMapper = new ObjectMapper();
				jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
				try {
					AuthInfor authobj = jsonMapper.readValue(jsonObject.toString(),
							AuthInfor.class);
					if (authobj != null && authobj.getOpenid()!=null && !"".equals(authobj.getOpenid())){
							authInfor.setCrdate(new Date());
							authInfor.setRefresh_token(authobj.getRefresh_token());
							authInfor.setAccess_token(authobj.getAccess_token());
							authInfor.setExpires_in(authobj.getExpires_in());
							authInfor.setOpenid(authobj.getOpenid());
							authInfor.setScope(authobj.getScope());
							request.getSession().setAttribute(
									MCRConstants.SESSION_AUTHINFO, authInfor);
					}
				} catch (Exception e) {
					return "";
				}
				
			}
			return authInfor.getAccess_token();
	}
	
	/**
	 * 创建菜单
	 * 
	 * @param menu 菜单实例
	 * @param accessToken 有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createMenu(String menu, String accessToken) throws Exception {
		int result = 0;

		// 拼装创建菜单的url
		String url = MCRConstants.MENU_CREATE_URL.replace("ACCESS_TOKEN", accessToken);
//		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx9bc05e1ce7a95e43&secret=d6316a72ac74ef6705e86e0f695f17b5&code=026f6ad6a9f68c3e69f075b1926988de&grant_type=authorization_code";
		// 将菜单对象转换成json字符串
		String jsonMenu = menu;
//		String jsonMenu = null;
		// 调用接口创建菜单
		JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);
		System.out.println("====="+jsonObject.toString());
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}

		return result;
	}
	
	/**
	 * 创建二维码
	 * 
	 * @param menu 菜单实例
	 * @param accessToken 有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createQRcode(String qrjson, String accessToken)  throws Exception {
		int result = 0;

		// 拼装创建二维码的url
		String url = MCRConstants.QR_CREATE_URL.replace("ACCESS_TOKEN", accessToken);
		System.out.println("====="+url);
		// 调用接口创建二维码
		JSONObject jsonObject = httpRequest(url, "POST", qrjson);
		System.out.println("====="+jsonObject.toString());
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("创建二维码失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}

		return result;
	}
	
	private static final double EARTH_RADIUS = 6378137.0; //地球半径   
	/**
	 * 返回 两个经纬度点之间的距离
	 * @param lat_a 纬度
	 * @param lng_a 经度
	 * @param lat_b 纬度
	 * @param lng_b 经度
	 * @return 返回单位为公里/千米
	 */
	public static double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {  
	       double radLat1 = (lat_a * Math.PI / 180.0);  
	       double radLat2 = (lat_b * Math.PI / 180.0);  
	       double a = radLat1 - radLat2;  
	       double b = (lng_a - lng_b) * Math.PI / 180.0;  
	       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)  
	              + Math.cos(radLat1) * Math.cos(radLat2)  
	              * Math.pow(Math.sin(b / 2), 2)));  
	       s = s * EARTH_RADIUS;  
	       s = Math.round(s * 10000) / 10000;  
	       s = s/1000;
	       return s;  
	} 
	
	
	public static void main(String args[]){
//		"lat":"23.098069","lng":"113.309401"
//		"lat":"23.100684","lng":"113.312921"
//		System.out.println(MicroUtil.gps2m(23.098069, 113.309401, 23.100684, 113.312921));
//		String _url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx35688a3b9e6ecdd4&redirect_uri=http%3A%2F%2F210.21.65.86%2Fmcrweb%2Fpages%2Fbinding%2Fbinding.shtml&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
//		if(_url.indexOf("?")==-1){
//    		_url+="&publishid="+1;
//    	}else{
//    		_url+="?publishid="+2;
//    	}
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxac51729f5296b836&secret=f31c24dfc1df14ddbde689d5668257da";
//		MicroUtil.httpClientRequest(requestUrl, "POST", "");
//		String url = "/mcrweb/index.shtml?id=1";
//		url = url.replace("/mcrweb", "");
//		if(url.indexOf("?")>-1){
//			url = url.substring(0, url.indexOf("?"));
////					url.split("?")[0];	
//		}
//		try {
////			https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx35688a3b9e6ecdd4&redirect_uri=http%3A%2F%2F210.21.65.86%2Fmcrweb%2Fpages%2Fbinding%2Fbinding.shtml&response_type=code&scope=snsapi_base&state=1#wechat_redirect
//			System.out.println(URLEncoder.encode("http://210.21.65.86/mcrweb/pages/binding/binding.shtml", "UTF-8"));
//			System.out.println(MicroUtil.convertAuthorizedAccessUrl("http://210.21.65.86/mcrweb/pages/binding/binding.shtml"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		List<BusiSiteBo> sitelist = new ArrayList<BusiSiteBo>();
//		List<BusiSiteBo> sitelist1 = new ArrayList<BusiSiteBo>();
//		double j = 20;
//		for(int i=0;i<10;i++){
//			BusiSiteBo bo = new BusiSiteBo();
//			bo.setDistance(j);
//			j--;
//			sitelist.add(bo);
//		}
//		for(int i=0;i<10;i++){
//			BusiSiteBo bo = new BusiSiteBo();
//			bo.setDistance(0d);
//			j--;
//			sitelist1.add(bo);
//		}
//		DistanceComparator c = new DistanceComparator();
//		Collections.sort(sitelist, c);
//		sitelist.addAll(sitelist1);
//		for(int i=0;i<sitelist.size();i++){
//			System.out.println(sitelist.get(i).getDistance());
//		}
//		MicroUtil
//		<BusiSiteBo> sortBusiSite(List<BusiSiteBo> list,McrUserBoundinfo info){
		
	}
	
}

