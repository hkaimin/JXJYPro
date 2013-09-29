package com.htsoft.est.dao.flow;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.flow.ProDefRights;

/**
 * 
 * @author 
 *
 */
public interface ProDefRightsDao extends BaseDao<ProDefRights>{

	public ProDefRights findByDefId(Long defId);

	public ProDefRights findByTypeId(Long proTypeId);
	
}