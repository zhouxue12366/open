package com.basic.framework.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * json转换工具类
 * @Title JsonParser.java
 * @Description 
 * @Company: 周大炮工作室
 * @author zg
 * @date 2019年10月12日 下午5:25:05
 * @version V1.0
 */
public class JsonParser {
	private static ObjectMapper mapper;

	/**
	 * 获取ObjectMapper实例
	 * 
	 * @param createNew
	 *            方式：true，新实例；false,存在的mapper实例
	 * @return
	 */
	public static synchronized ObjectMapper getMapperInstance(boolean createNew) {
		if (createNew) {
			return new ObjectMapper();
		} else if (mapper == null) {
			mapper = new ObjectMapper();
		}
		return mapper;
	}

	/**
	 * 将java对象转换成json字符串
	 * 
	 * @param obj
	 *            准备转换的对象
	 * @return json字符串
	 * @throws Exception
	 */
	public static String beanToJson(Object obj) {
		String json = null;
		try {
			ObjectMapper objectMapper = getMapperInstance(false);
			json = objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 将java对象转换成json字符串
	 * 
	 * @param obj
	 *            准备转换的对象
	 * @param createNew
	 *            ObjectMapper实例方式:true，新实例;false,存在的mapper实例
	 * @return json字符串
	 * @throws Exception
	 */
	public static String beanToJson(Object obj, Boolean createNew) {
		String json = null;
		try {
			ObjectMapper objectMapper = getMapperInstance(createNew);
			json = objectMapper.writeValueAsString(obj);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 将json字符串转换成java对象
	 * 
	 * @param json
	 *            准备转换的json字符串
	 * @param cls
	 *            准备转换的类
	 * @return
	 * @throws Exception
	 */
	public static <T> T jsonToBean(String json, Class<T> cls) {
		T t = null;
		try {
			t = getMapperInstance(false).readValue(json, cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 将json字符串转成list集合
	 * 
	 * @author ynshun
	 * @param json
	 * @param cls
	 * @return
	 */
	public static <T> List<T> jsonToList(String json, Class<T> cls) {
		List<T> list = null;
		try {
			JavaType javaType = getMapperInstance(false).getTypeFactory().constructParametricType(ArrayList.class, cls);
			list = getMapperInstance(false).readValue(json, javaType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 将json字符串转换成java对象
	 * 
	 * @param json
	 *            准备转换的json字符串
	 * @param cls
	 *            准备转换的类
	 * @param createNew
	 *            ObjectMapper实例方式:true，新实例;false,存在的mapper实例
	 * @return
	 * @throws Exception
	 */
	public static <T> Object jsonToBean(String json, Class<T> cls, Boolean createNew) {
		T t = null;
		try {
			t = getMapperInstance(createNew).readValue(json, cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
}
