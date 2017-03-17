/*
 * @(#)Entity.java	2011-8-30
 *
 * Copyright (c) 2011. All Rights Reserved.
 *
 */

package com.github.javaclub.jorm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Entity mapped to database
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: Entity.java 2011-8-30 19:43:17 Exp $
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Entity {

	/**
	 * 实体映射的数据库表名
	 *
	 * @return 数据库表名
	 */
	String table();
	
	/**
	 * 被注解的实体对象是否懒加载
	 *
	 * @return true(懒加载); false(不使用懒加载)
	 */
	boolean lazy() default false; 
	
	/**
	 * 是否加载关联对象(一对一，一对多等)，如果有的话
	 *
	 * @return true(加载关联对象); false(不加载关联对象)
	 */
	boolean loadAssociated() default true; 
}
