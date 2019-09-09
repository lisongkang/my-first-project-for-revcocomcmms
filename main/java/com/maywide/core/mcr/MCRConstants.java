package com.maywide.core.mcr;

public interface MCRConstants {

	// 网页授权获取用户基本信息涉及到的URL
	/**
	 * 1、用户同意授权，获取code
	 */
	public final static String AUTH_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=${APPID}&redirect_uri=${REDIRECT_URI}&response_type=code&scope=${SCOPE}&state=${CODE}#wechat_redirect";
	/**
	 * 2、通过code换取网页授权access_token
	 */
	public final static String AUTH_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=${APPID}&secret=${SECRET}&code=${CODE}&grant_type=authorization_code";

	/**
	 * 刷新access_token的URL地址（get）  暂定有效期为7天
	 */
	public final static String ACCESS_REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=${APPID}&grant_type=refresh_token&refresh_token=${REFRESH_TOKEN}";
	
	/**
	 * 获取access_token的接口地址（GET） 限2000（次/天）
	 */
	public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	/**
	 * 菜单创建（POST） 限100（次/天）
	 */
	public final static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	/**
	 * 获取用户基本信息
	 */
	public final static String USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=${ACCESS_TOKEN}&openid=${OPENID}&lang=zh_CN";
	/**
	 * 主动发送消息URL
	 */
	public final static String CUSTSERV_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=${ACCESS_TOKEN}";
//	订单预支付URL
	public final static String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
//	安全验证通过之后会将验证信息AuthInfor Bean存入session
	/**
	 * 二维码请求URL
	 */
	public final static String QR_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
	/**
	 * 查询用户订单URL
	 */
	public final static String QUE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	
	/**
	 * 安全验证通过之后会将验证信息AuthInfor Bean存入session
	 */
	public final static String SESSION_AUTHINFO = "authinfo";

	interface WhUsersType {
		public static final Character PERSONAL = '1';// 个人
		public static final Character COMPANY = '2';// 企业
	}

	/** 公众账号安全信息 */
	interface SafetyInfo {
		// public static final String TOKEN = "mayWideMicro";//
		// 与接口配置信息中的Token要一致
		// public static final String APPID = "wx9bc05e1ce7a95e43";
		// public static final String APPSECRET =
		// "d6316a72ac74ef6705e86e0f695f17b5";

		public static final String TOKEN = "TOKEN";// 与接口配置信息中的Token要一致
		public static final String APPID = "APPID";
		public static final String APPSECRET = "APPSECRET";
		public static final String PAYSIGNKEY = "PaySignKey";
		public static final String PARTNERID = "PartnerID";
		public static final String PARTNERKEY = "PartnerKey";
		public static final String NOTIFYURL = "NOTIFYURL";
		public static final String MCHID = "MCHID";
	}

	/** 安全验证类型 */
	interface McrVarify {
		/** 浏览器 */
		public static final int BROWSER = 1;
		/** 微信客户端 */
		public static final int WXCLIENT = 0;
	}

	interface McrCode {
		/** 账号已绑定状态 */
		public static final String BOUND_YES = "1";
		/** 账号未绑定状态 */
		public static final String BOUND_NO = "0";
	}

	interface Distance {
		/** 账号已绑定状态 */
		public static final String KNOWN = "Y";
		/** 账号未绑定状态 */
		public static final String UNKONWN = "N";
	}

	interface McrConfigKey {
		/** 域名key */
		public static final String DOMAIN_BASE_URL_KEY = "baseurl";
		/** 主菜单key */
		public static final String MAIN_MENU_KEY = "mainmenu";
		/** 用户关注key */
		public static final String ATTENTION_KEY = "attention";
		/** 账号已绑定提示key */
		public static final String BOUND_YES_KEY = "accountbounded";
		// /** 首次账号已绑定提示key */
		public static final String FRIST_BOUND_YES_KEY = "accountfirstbounded";
		/** 提示用户绑定账号key */
		public static final String BOUND_ACCOUNT_KEY = "accountbound";
		/** 用户解除绑定成功的提示 key */
		public static final String UNBOUND_ACCOUNT_SUCC_KEY = "accountUnboundSucc";
		/** 个人信息查询key */
		public static final String PERSONALINFO_KEY = "personalinfo";
		/** 产品分类信息key */
		public static final String PRODUCTTYPE_KEY = "producttype";
		/** 我的订单key */
		public static final String MYORDER_KEY = "myorder";
		/** 健康食谱key */
		public static final String HEALTHYREC_KEY = "healthyrec";
		/** 食材密码key */
		public static final String FOODPASS_KEY = "foodpass";

	}
}
