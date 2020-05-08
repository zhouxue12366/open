package com.genner.formwork.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RequestMapping注解
 * @Title RequestMapping.java
 * @Description
 * @Company: 周大炮工作室
 * @author zg
 * @date 2020年5月8日 下午3:58:46
 * @version V1.0
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
	public String value() default "";
}
