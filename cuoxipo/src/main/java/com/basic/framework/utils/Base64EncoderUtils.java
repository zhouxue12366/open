package com.basic.framework.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * 加密测试类
 * 
 * @Title Base64EncoderUtils.java
 * @Description
 * @Company: 周大炮工作室
 * @author zg
 * @date 2019年10月12日 下午5:21:12
 * @version V1.0
 */
public class Base64EncoderUtils {

//	public static void main(String[] args) throws UnsupportedEncodingException {
//		String value = "zhougao";
//		System.out.println(encodeToString(value));
//	}

	public static String encodeToString(String text) {
		try {
			Base64.Encoder encoder = Base64.getEncoder();
			String value = text;
			byte[] textByte = value.getBytes("UTF-8");
			String encodedText = encoder.encodeToString(textByte);
			return encodedText;
		} catch (Exception e) {
			return null;
		}

	}
}
