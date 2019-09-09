package com.maywide.biz.core.servlet;

/**
 * 用于定义全局共用错误代码常量
 */
public interface IErrorDefConstant {
    // 请求报文格式不正确
    public static long ERROR_INVALID_JSON_CODE = -9999;
    public static String ERROR_INVALID_JSON_MSG = "请求报文不是合法的JSON格式";

    // 接口未实现
    public static long ERROR_API_NO_IMPLEMENTED_CODE = -9998;
    public static String ERROR_API_NO_IMPLEMENTED_MSG = "接口[%s]未实现";

    public static long ERROR_INIT_CODE = 0;

    // 业务操作成功
    public static long ERROR_SUCCESS_CODE = 0;
    public static String ERROR_SUCCESS_MSG = "操作成功";
    
    
    // 成功发送随机验证码
    public static String VERIFY_CODE_SEND_SUCCESS_MSG = "随机验证码已成功发送";
    
    // 验证码校验通过
    public static String VERIFY_CODE_CHECK_SUCCESS_MSG = "验证码校验通过";
    
    // JSON报文缺少指定字段参数
    public static long ERROR_PROPERTY_MISSING_CODE = (ERROR_INIT_CODE - 1);
    public static String ERROR_PROPERTY_MISSING_MSG = "缺少参数[%s]";

    // 客户端没有注册
    public static long ERROR_DEVICE_ENROLLMENT_CODE = (ERROR_INIT_CODE - 2);
    public static String ERROR_DEVICE_ENROLLMENT_MSG = "客户端没有注册，当前MAC地址为[%s]";

    // 安全算法错误导致解密失败
    public static long ERROR_INVALID_SECURITY_CODE = (ERROR_INIT_CODE - 3);
    public static String ERROR_INVALID_SECURITY_MSG = "安全算法不相同，加解密失败";

    // 所调用接口不存在
    public static long ERROR_INVALID_API_CODE = (ERROR_INIT_CODE - 4);
    public static String ERROR_INVALID_API_MSG = "接口[%s]不存在，请确认配置是否已经配置或传参数正确";

    // 内部异常转换
    public static long ERROR_ClassNotFoundException = (ERROR_INIT_CODE - 5);
    public static long ERROR_InvocationTargetException = (ERROR_INIT_CODE - 6);
    public static long ERROR_NoSuchMethodException = (ERROR_INIT_CODE - 7);
    public static long ERROR_InstantiationException = (ERROR_INIT_CODE - 8);
    public static long ERROR_IllegalAccessException = (ERROR_INIT_CODE - 9);

    // 操作员没有登录
    public static long ERROR_INVALID_LOGIN_CODE = (ERROR_INIT_CODE - 10);
    public static String ERROR_INVALID_LOGIN_MSG = "找不到登录操作员信息";

    public static long ERROR_RuntimeException_CODE = (ERROR_INIT_CODE - 11);

    // 外部系统接口没有定义在crm4app_remote_call配置表中
    public static long ERROR_RemoteApiNotFoundException_CODE = (ERROR_INIT_CODE - 11);
    public static String ERROR_RemoteApiNotFoundException_MSG = "找不到外部接口[%s]的配置信息";

    // 调用外部系统接口失败
    public static long ERROR_RemoteApiCallFailure_CODE = (ERROR_INIT_CODE - 11);
    public static String ERROR_RemoteApiCallFailure_MSG = "调用外部系统接口%s失败";
    
    //XML数据解析失败
    public static long ERROR_XML_PARSE_FAILED = (ERROR_INIT_CODE - 13);
    public static String ERROR_XML_PARSE_FAILED_MSG = "XML解析失败，请检查XML文件格式";
    
    //用户鉴权失败
    public static long ERROR_USER_AUTH = (ERROR_INIT_CODE - 18);
    public static String ERROR_USER_AUTH_MSG = "密码错误";
    public static String ERROR_USER_AUTH_RULE_MEG = "密码校验通过,但密码规则检验不通过";
    public static String ERROR_USER_AUTH_COUNT = "用户在一天内办理所有业务输错密码次数超过规定次数";
    public static String ERROR_USER_AUTH_WEAK = "密码正确但是密码是弱密码";
    public static String ERROR_USER_AUTH_WRONG = "用户密码验证失败";
    
    //用户证件号鉴权失败
    public static long ERROR_USER_AUTH_CERT = (ERROR_INIT_CODE - 19);
    public static String ERROR_USER_AUTH_CERT_MSG = "证件号校验错误";
    
    //用户证件号鉴权失败
    public static long ERROR_VERIFY_CODE = (ERROR_INIT_CODE - 20);
    public static String ERROR_VERIFY_CODE_MSG = "随机验证码不正确";
    
    //用户证件号鉴权失败
    public static long ERROR_VERIFY_CODE_EXPIRE_CODE = (ERROR_INIT_CODE - 21);
    public static String ERROR_VERIFY_CODE_EXPIRE_MSG = "验证码已经过期";

    //TOKEN失效
    public static long ERROR_TOKEN_FAIL_CODE = (ERROR_INIT_CODE - 22);
    public static String ERROR_TOKEN_FAIL_MSG = "TOKEN已经失效";
}
