package com.maywide.core.security.encrypt;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * TDES�ӽ����㷨ʵ��
 */
public class TDESImpl implements ISecurityService {
    private static Logger log = LoggerFactory.getLogger(TDESImpl.class);
    private static SecretKey tdesKey = null;

    /**
     * ��ʼ����Կ
     * @param key ��Կ���� ���ȱ���Ϊ24���ֽ�
     */
    @Override
    public void init(String key) {
        try {
            DESedeKeySpec keySpec = new DESedeKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            tdesKey = keyFactory.generateSecret(keySpec);
        } catch (InvalidKeyException Err) {
            log.debug("TDESImpl::init", Err);
        } catch (InvalidKeySpecException Err) {
            log.debug("TDESImpl::init", Err);
        } catch (NoSuchAlgorithmException Err) {
            log.debug("TDESImpl::init", Err);
        }
    }

    @Override
    public String encrypt(String input) {
        try {
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, tdesKey);
            byte[] cipherData = cipher.doFinal(input.getBytes("UTF-8"));

            char[] hexData = Hex.encodeHex(cipherData);
            return new String(hexData);
        } catch (NoSuchAlgorithmException Err) {
            log.debug("TDESImpl::encrypt", Err);
        } catch (NoSuchPaddingException Err) {
            log.debug("TDESImpl::encrypt", Err);
        } catch (InvalidKeyException Err) {
            log.debug("TDESImpl::encrypt", Err);
        } catch (BadPaddingException Err) {
            log.debug("TDESImpl::encrypt", Err);
        } catch (IllegalBlockSizeException Err) {
            log.debug("TDESImpl::encrypt", Err);
        } catch (UnsupportedEncodingException Err) {
            log.debug("TDESImpl::encrypt", Err);
        }

        return null;
    }

    @Override
    public String decrypt(String input) {
        try {
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.DECRYPT_MODE, tdesKey);

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
