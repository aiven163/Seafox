/**
 * Copyright (c) 2013-1015 
 * Creator Aiven
 * 
 */
package com.aiven.seafox.controller.intef;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Aiven
 * @date 2013-5-20
 * @email aiven163@sina.com
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ELayout {
	public int Layout() default -1;
}
