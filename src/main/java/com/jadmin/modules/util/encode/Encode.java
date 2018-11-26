package com.jadmin.modules.util.encode;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @Title:web框架
 * @Description:加解密<br>
 * 目前存在一个问题，字符串长度小，加密的长度就小
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
public class Encode {

    /** 密钥 */
    private static long key = 1231234234l;

    /** 当前记录日志的logger对象 */
    private static final Logger logger = LoggerFactory.getLogger(Encode.class);
    
    /**
     * 解密
     * @param encodeContent
     * @return
     */
    public static String decode(String encodeContent) {
        String res = "";
        DES des = new DES(getKey());
        byte[] sBytes = encodeContent.getBytes();
        for (int i = 0; i < (sBytes.length / 16); i++) {
            byte[] theBytes = new byte[8];
            for (int j = 0; j <= 7; j++) {
                byte byte1 = (byte) (sBytes[16 * i + 2 * j] - 'a');
                byte byte2 = (byte) (sBytes[16 * i + 2 * j + 1] - 'a');
                theBytes[j] = (byte) (byte1 * 16 + byte2);
            }
            long x = des.bytes2long(theBytes);
            byte[] result = new byte[8];
            des.long2bytes(des.decrypt(x), result);
            res = res + (new String(result));
        }
        return res.trim();
    }

    /**
     * 加密
     * @param content
     * @param isDecode 是否可以解密
     * @return
     */
    public static String encode(String content, boolean isDecode) {
    	if(!isDecode){
    		return getMD5Str(content);
    	}
        String res = "";
        DES des = new DES(getKey());
        byte space = 0x20;
        byte[] sBytes = content.getBytes();
        int length = sBytes.length;
        int newLength = length + (8 - length % 8) % 8;
        byte[] newBytes = new byte[newLength];
        for (int i = 0; i < newLength; i++) {
            if (i <= length - 1) {
                newBytes[i] = sBytes[i];
            } else {
                newBytes[i] = space;
            }
        }
        for (int i = 0; i < (newLength / 8); i++) {
            byte[] theBytes = new byte[8];
            for (int j = 0; j <= 7; j++) {
                theBytes[j] = newBytes[8 * i + j];
            }
            long x = des.bytes2long(theBytes);
            byte[] result = new byte[8];
            des.long2bytes(des.encrypt(x), result);
            byte[] doubleResult = new byte[16];
            for (int j = 0; j < 8; j++) {
                doubleResult[2 * j] = (byte) (((((char) result[j]) & 0xF0) >> 4) + 'a');
                doubleResult[2 * j + 1] = (byte) ((((char) result[j]) & 0x0F) + 'a');
            }
            res = res + new String(doubleResult);
        }
        return res;
    }
    
    /**
     * 不可逆的加密算法
     * @param str
     * @return
     */
    private static String getMD5Str(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (Exception e) {
        	logger.error("【重大问题】密码无法加密：" + str + "……" + e.getMessage(), e);
        	return str;
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }  
        return md5StrBuff.toString();
    }
    
    /**
     * 固定的加密Key
     * @return long
     */
    private static long getKey() {
        return key;
    }
    
    /**
     * 固定的加密Key
     * @return long
     */
    public static void setKey(long enKey) {
        key = enKey;
    }
    
    public static void main(String[] args) {
        /** key */
    	String jm = encode("111111", true);
    	System.out.println(jm);
    	System.out.println(decode(jm));
	}

}