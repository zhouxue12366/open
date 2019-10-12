package com.m3u8.zg.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64EncoderUtils {

	public static void main(String[] args) throws UnsupportedEncodingException {
		Base64.Encoder encoder = Base64.getEncoder();
		String value="igxefifa123qwe";
		byte[] textByte = value.getBytes("UTF-8");
		String encodedText = encoder.encodeToString(textByte);
		System.out.println(encodedText);
	}
}
