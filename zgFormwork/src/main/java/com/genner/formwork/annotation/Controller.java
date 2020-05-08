package com.genner.formwork.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * controller用的注解
 * @Title Controller.java
 * @Description 
 * @Company: 周大炮工作室
 * @author zg
 * @date 2020年5月8日 下午3:57:01
 * @version V1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {

	public String value() default "";
}
