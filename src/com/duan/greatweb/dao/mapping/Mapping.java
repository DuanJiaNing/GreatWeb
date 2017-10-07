package com.duan.greatweb.dao.mapping;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 用于数据库字段名到 java 实体类字段的映射
 * @author 2017/09/13 DuanJiaNing
 *
 */
@Target(value={FIELD,TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Mapping {
	String value() default "";
}
