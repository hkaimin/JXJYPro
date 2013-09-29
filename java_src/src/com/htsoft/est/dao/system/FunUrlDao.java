package com.htsoft.est.dao.system;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.system.FunUrl;

/**
 * 
 * @author 
 *
 */
public interface FunUrlDao extends BaseDao<FunUrl>{
	/**
	 * 按path及functionId查找
	 * @param path
	 * @param funId
	 * @return
	 */
	public FunUrl getByPathFunId(String path,Long funId);
}