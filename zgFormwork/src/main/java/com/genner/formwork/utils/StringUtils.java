package com.genner.formwork.utils;

/**
 * 
 * @Title StringUtils.java
 * @Description 
 * @Company: 周大炮工作室
 * @author zg
 * @date 2020年5月9日 下午2:41:05
 * @version V1.0
 */
public class StringUtils {

	/**
	 * 首字母转换成小写
	 * 
	 * @Title toLowerFirstCase
	 * @Description
	 * @param value
	 * @return
	 * @since 2020年5月8日 下午4:55:23
	 */
	public static String toLowerFirstCase(String value) {
		char[] chars = value.toCharArray();
		chars[0] += 32;
		return String.valueOf(chars);
	}
}
