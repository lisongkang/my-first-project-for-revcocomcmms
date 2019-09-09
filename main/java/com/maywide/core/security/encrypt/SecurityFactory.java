package com.maywide.core.security.encrypt;

import org.apache.commons.lang3.StringUtils;

import com.maywide.biz.core.servlet.SysConfig;

/**
 * 实现加解密操作
 *
 */
public class SecurityFactory {
    private static ISecurityService securityService = null;

    /**
     * 目前实现的算法列表
     */
    public final static String AES_IMPL = "AES";
    public final static String DES_IMPL = "DES";
    public final static String NVL_IMPL = "TXT";
    public final static String RSA_IMPL = "RSA";
    public final static String TDES_IMPL = "TDES";

    /**
     * 返回配置参数SecurityClass所使用的安全算法实现对象
     * @return ISecurityService实现对象
     */
    public static ISecurityService getSecurityService() {
        if (null == securityService) {
            securityService = getSecurityService(SysConfig.getSecurityClass());
            if (null != securityService) {
                securityService.init(SysConfig.getSecurityKey());
            }
        }

        return securityService;
    }

    /**
     * 根据实现类名称获取加解密实现类实例
     * @param implName 实现类名称(不是指CLASS NAME)
     * @return ISecurityService对象,如果不支持则返回null
     */
    public static ISecurityService getSecurityService(final String implName) {
    	if(StringUtils.isBlank(implName)) return new EmptyImpl();
    	if (implName.equals(AES_IMPL)) {
            return new AESImpl();
        } else if (implName.equals(DES_IMPL)) {
            return new DESImpl();
        } else if (implName.equals(NVL_IMPL)) {
            return new EmptyImpl();
        } else if (implName.equals(RSA_IMPL)) {
            return new RSAImpl();
        } else if (implName.equals(TDES_IMPL)) {
            return new TDESImpl();
        }

    	return new EmptyImpl();
    }
}
