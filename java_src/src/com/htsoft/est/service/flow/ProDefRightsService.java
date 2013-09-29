package com.htsoft.est.service.flow;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.flow.ProDefRights;

public interface ProDefRightsService extends BaseService<ProDefRights>{

	public ProDefRights findByTypeId(Long proTypeId);

	public ProDefRights findByDefId(Long defId);
	
}

