package com.maywide.core.security.encrypt;

/**
 * ���ļӽ����㷨ʵ�֣������κ�ʵ�ʲ���
 */
public class EmptyImpl implements ISecurityService {
    @Override
    public void init(String key) {

    }

    @Override
    public String encrypt(String input) {
        return input;
    }

    @Override
    public String decrypt(String input) {
        return input;
    }
}
