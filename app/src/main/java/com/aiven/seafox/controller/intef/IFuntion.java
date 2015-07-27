package com.aiven.seafox.controller.intef;

import java.io.Serializable;

/**
 * 
 * @author Aiven
 * @email aiven163@sina.com
 * @date 2015-6-21  下午4:11:23
 * @project  app框架
 * @desc
 */
public interface IFuntion<T> extends Serializable {
	
	public void exucte(T t);

}
