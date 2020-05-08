package com.genner.formwork.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动注解
 * @Title Autowired.java
 * @Description 
 * @Company: 周大炮工作室
 * @author zg
 * @date 2020年5月8日 下午4:01:23
 * @version V1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {

	public String value() default "";
}
