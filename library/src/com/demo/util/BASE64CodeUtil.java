package com.demo.util;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

//base64加解密
public class BASE64CodeUtil {
	/** 
	 * BASE64解密 
	 *  
	 * @param key 
	 * @return 
	 * @throws Exception 
	 */  
	public static byte[] decryptBASE64(String key) throws Exception {  
	    return (new BASE64Decoder()).decodeBuffer(key);  
	}  
	  
	/** 
	 * BASE64加密 
	 *  
	 * @param key 
	 * @return 
	 * @throws Exception 
	 */  
	public static String encryptBASE64(byte[] key) throws Exception {  
	    return (new BASE64Encoder()).encodeBuffer(key).trim();  
	}  
	
	public static void main(String[]args) throws Exception{
		System.out.println(encryptBASE64("123456".getBytes())+"=====");
		System.out.println(new String(decryptBASE64("MTIzNDU2"),"UTF-8"));
	}
}
