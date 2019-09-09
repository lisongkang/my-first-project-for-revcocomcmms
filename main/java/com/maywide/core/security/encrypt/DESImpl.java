package com.maywide.core.security.encrypt;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * DES�ӽ����㷨ʵ��
 */
public class DESImpl implements ISecurityService {
    private static Logger log = LoggerFactory.getLogger(DESImpl.class);
    private static SecretKey desKey = null;

    /**
     * ��ʼ����Կ
     * @param key ��Կ���� ���ȱ���Ϊ8���ֽ�
     */
    @Override
    public void init(String key) {
        try {
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            desKey = keyFactory.generateSecret(desKeySpec);
        } catch (NoSuchAlgorithmException Err) {
            log.debug("DESImpl::init", Err);
        } catch (InvalidKeyException Err) {
            log.debug("DESImpl::init", Err);
        } catch (InvalidKeySpecException Err) {
            log.debug("DESImpl::init", Err);
        }
    }

    @Override
    public String encrypt(String input) {
        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            byte[] cipherData = cipher.doFinal(input.getBytes("UTF-8"));

            char[] hexData = Hex.encodeHex(cipherData);
            return new String(hexData);
        } catch (NoSuchAlgorithmException Err) {
            log.debug("DESImpl::encrypt", Err);
        } catch (NoSuchPaddingException Err) {
            log.debug("DESImpl::encrypt", Err);
        } catch (InvalidKeyException Err) {
            log.debug("DESImpl::encrypt", Err);
        } catch (BadPaddingException Err) {
            log.debug("DESImpl::encrypt", Err);
        } catch (IllegalBlockSizeException Err) {
            log.debug("DESImpl::encrypt", Err);
        } catch (UnsupportedEncodingException Err) {
            log.debug("DESImpl::encrypt", Err);
        }

        return null;
    }

    @Override
    public String decrypt(String input) {
        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, desKey);

            byte[] cipherData = Hex.decodeHex(input.toCharArray());
            byte[] plainData = cipher.doFinal(cipherData);
            return new String(plainData, "UTF-8");
        } catch (NoSuchAlgorithmException Err) {
            log.debug("DESImpl::decrypt", Err);
        } catch (NoSuchPaddingException Err) {
            log.debug("DESImpl::decrypt", Err);
        } catch (InvalidKeyException Err) {
            log.debug("DESImpl::decrypt", Err);
        } catch (BadPaddingException Err) {
            log.debug("DESImpl::decrypt", Err);
        } catch (IllegalBlockSizeException Err) {
            log.debug("DESImpl::decrypt", Err);
        } catch (DecoderException Err) {
            log.debug("DESImpl::decrypt", Err);
        } catch (UnsupportedEncodingException Err) {
            log.debug("DESImpl::decrypt", Err);
        }

        return null;
    }
}
