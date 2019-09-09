package com.maywide.core.security.encrypt;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 * RSA�ӽ����㷨ʵ��
 * ֻ�����ڼӽ��ܳ��Ƚ�С�����,����Ч�ʻ�ǳ���
 * initʱ�����KEY��ʽΪ:modulus,privateExponent
 * ���Բ��ó������
 */
public class RSAImpl implements ISecurityService {
    private static Logger log = LoggerFactory.getLogger(RSAImpl.class);
    private static RSAPublicKey publicKey = null;
    private static RSAPrivateKey privateKey = null;
    private static final int RSA_KEY_SIZE = 512;

    private BigInteger publicExponent = new BigInteger("20063", 16);

    /**
     * ��ʼ����Կ
     * @param key ��Կ���� ��ʽΪ:modulus,privateExponent
     */
    @Override
    public void init(String key) {
        try {
/*
            // ���KEY�ķ���
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(RSA_KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            publicKey = (RSAPublicKey) keyPair.getPublic();
            log.debug("publicKey.getModulus({})", publicKey.getModulus().toString(16));
            log.debug("publicKey.getPublicExponent({})", publicKey.getPublicExponent().toString(16));

            privateKey = (RSAPrivateKey) keyPair.getPrivate();
            log.debug("privateKey.getModulus({})", privateKey.getModulus().toString(16));
            log.debug("privateKey.getPrivateExponent({})", privateKey.getPrivateExponent().toString(16));
*/

            String[] keyList = key.split(",");

            BigInteger modulus = new BigInteger(keyList[0], 16);
            BigInteger privateExponent = new BigInteger(keyList[1], 16);

            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(modulus, publicExponent);
            RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(modulus, privateExponent);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
            privateKey = (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
        } catch (NoSuchAlgorithmException Err) {
            log.debug("RSAImpl::init", Err);
        } catch (InvalidKeySpecException Err) {
            log.debug("RSAImpl::init", Err);
        }
    }

    @Override
    public String encrypt(String input) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] plainData = input.getBytes("UTF-8");
            byte[] cipherData = blockCipher(cipher, plainData, Cipher.ENCRYPT_MODE);
            char[] hexData = Hex.encodeHex(cipherData);

            return new String(hexData);
        } catch (NoSuchAlgorithmException Err) {
            log.debug("RSAImpl::encrypt", Err);
        } catch (NoSuchPaddingException Err) {
            log.debug("RSAImpl::encrypt", Err);
        } catch (InvalidKeyException Err) {
            log.debug("RSAImpl::encrypt", Err);
        } catch (BadPaddingException Err) {
            log.debug("RSAImpl::encrypt", Err);
        } catch (IllegalBlockSizeException Err) {
            log.debug("RSAImpl::encrypt", Err);
        } catch (UnsupportedEncodingException Err) {
            log.debug("RSAImpl::encrypt", Err);
        }

        return null;
    }

    @Override
    public String decrypt(String input) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] cipherData = Hex.decodeHex(input.toCharArray());
            byte[] plainData = blockCipher(cipher, cipherData, Cipher.DECRYPT_MODE);

            // ɾ����ַ�
            int nPrefixPos = 0;
            while (plainData[nPrefixPos] == 0) {
                ++nPrefixPos;
            }
            if (nPrefixPos > 0) {
                byte[] realData = new byte[plainData.length - nPrefixPos];
                System.arraycopy(plainData, nPrefixPos, realData, 0, realData.length);
                return new String(realData, "UTF-8");
            }

            return new String(plainData, "UTF-8");
        } catch (NoSuchAlgorithmException Err) {
            log.debug("RSAImpl::decrypt", Err);
        } catch (NoSuchPaddingException Err) {
            log.debug("RSAImpl::decrypt", Err);
        } catch (InvalidKeyException Err) {
            log.debug("RSAImpl::decrypt", Err);
        } catch (BadPaddingException Err) {
            log.debug("RSAImpl::decrypt", Err);
        } catch (IllegalBlockSizeException Err) {
            log.debug("RSAImpl::decrypt", Err);
        } catch (DecoderException Err) {
            log.debug("RSAImpl::decrypt", Err);
        } catch (UnsupportedEncodingException Err) {
            log.debug("RSAImpl::decrypt", Err);
        }

        return null;
    }

    /**
     * RSA���?֧�ֳ����,��ɿ����
     * @param cipher ���μӽ��ܲ���Ķ���
     * @param bytes ��������
     * @param mode �ӽ���ģʽCipher.DECRYPT_MODE/Cipher.ENCRYPT_MODE
     * @return ��ӽ��ܲ�����
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    private byte[] blockCipher(Cipher cipher, byte[] bytes, int mode) throws IllegalBlockSizeException, BadPaddingException{
        // string initialize 2 buffers.
        // scrambled will hold intermediate results
        byte[] scrambled = new byte[0];

        // toReturn will hold the total result
        byte[] toReturn = new byte[0];
        // if we encrypt we use 100 byte long blocks. Decryption requires 128 byte long blocks (because of RSA)
        int length = (mode == Cipher.ENCRYPT_MODE) ? ((RSA_KEY_SIZE / 8) - 11) : (RSA_KEY_SIZE / 8);

        // another buffer. this one will hold the bytes that have to be modified in this step
        byte[] buffer = new byte[bytes.length > length ? length : bytes.length];

        for (int i = 0; i < bytes.length; i++){

            // if we filled our buffer array we have our block ready for de- or encryption
            if ((i > 0) && (i % length == 0)){
                //execute the operation
                scrambled = cipher.doFinal(buffer);
                // add the result to our total result.
                toReturn = append(toReturn, scrambled);
                // here we calculate the length of the next buffer required
                int newlength = length;

                // if newlength would be longer than remaining bytes in the bytes array we shorten it.
                if ((i + length) > bytes.length) {
                    newlength = (bytes.length - i);
                }
                // clean the buffer array
                buffer = new byte[newlength];
            }

            // copy byte into our buffer.
            buffer[i % length] = bytes[i];
        }

        // this step is needed if we had a trailing buffer. should only happen when encrypting.
        // example: we encrypt 110 bytes. 100 bytes per run means we "forgot" the last 10 bytes. they are in the buffer array
        scrambled = cipher.doFinal(buffer);

        // final step before we can return the modified data.
        toReturn = append(toReturn, scrambled);

        return toReturn;
    }

    /**
     * ��ݲ���
     * @param prefix ǰ׺
     * @param suffix ��׺
     * @return byte[]������
     */
    private byte[] append(byte[] prefix, byte[] suffix){
        byte[] toReturn = new byte[prefix.length + suffix.length];
        for (int i = 0; i < prefix.length; i++) {
            toReturn[i] = prefix[i];
        }
        for (int i = 0; i < suffix.length; i++){
            toReturn[i + prefix.length] = suffix[i];
        }
        return toReturn;
    }
}
