package com.aiven.seafox.controller.intef;

import java.io.Serializable;

/**
 * 
 * @author Aiven
 * @email aiven163@sina.com
 * @date 2015-6-21  pm 4:11:23
 * @desc
 */
public interface IFuntion<T> extends Serializable {
	
	public void exucte(T t);

}
