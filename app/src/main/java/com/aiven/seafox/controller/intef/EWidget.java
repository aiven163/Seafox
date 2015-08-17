package com.aiven.seafox.controller.intef;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 *
 * @author Aiven
 * @date 2014-8-14  pm 2:13:44
 * @email aiven163@sina.com
 * @Description TODO
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EWidget {
	public int id() default -1;
	public int parentId() default 0;
}
