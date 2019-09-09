package com.maywide.core.security.encrypt;

/**
 * 安全实现接口定义，每个安全算法实现类都得实现该接口
 *
 */
public interface ISecurityService {

    /**
     * 设置加解密时所使用的密钥
     * @param key 密钥明文
     */
    public void init(final String key);

    /**
     * 加密数据
     * @param input---用于加密的明文
     * @return String加密结果(用16进制字符表示)
     */
    public String encrypt(final String input);

    /**
     * 解密数据
     * @param input----用于解密的十六进制字符字符串
     * @return String解密后的明文数据
     */
    public String decrypt(final String input);
}
