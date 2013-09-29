package com.htsoft.core.service;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.io.Serializable;
import java.util.List;

//import javax.jws.WebMethod;
//import javax.jws.WebService;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.web.paging.PagingBean;
/**
 * 
 * @author csx
 *
 * @param <T>
 * @param <PK>
 */
//@WebService
public interface GenericService<T,PK extends Serializable> {
	/**
	 * 
	 * @param entity
	 * @return
	 */
	public T save(T entity);
	/**
	 * merge the object
	 * @param entity
	 * @return
	 */
	public T merge(T entity);
	
	/**
	 * evict the object
	 * @param entity
	 */
	public void evict(T entity);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public T get(PK id);
	
	/**
	 * 
	 * @return
	 */
	//@WebMethod(operationName="getAll")
	public List<T> getAll();
	
	/**
	 * 
	 * @param pb
	 * @return
	 */
	//@WebMethod(operationName="getAllByPb")
	public List<T> getAll(PagingBean pb);
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	//@WebMethod(operationName="getAllByFilter")
	public List<T> getAll(QueryFilter filter);
	
	//@WebMethod(operationName="remove")
	public void remove(PK id);
	
	//@WebMethod(operationName="removeByEntity")
	public void remove(T entity);
	
	/**
	 * flush the session
	 */
	public void flush();

	
}
