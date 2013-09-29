package com.htsoft.est.dao.flow;

/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
 */
import java.util.List;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.dao.BaseDao;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.model.flow.ProDefinition;
import com.htsoft.est.model.system.AppUser;

/**
 * 
 * @author
 * 
 */
public interface ProDefinitionDao extends BaseDao<ProDefinition> {
	/**
	 * 按发布ID取得XML
	 * 
	 * @param deployId
	 * @return
	 */
	public ProDefinition getByDeployId(String deployId);

	/**
	 * get by name
	 * 
	 * @param name
	 * @return
	 */
	public ProDefinition getByName(String name);

	public List<ProDefinition> getByRights(AppUser curUser,
			ProDefinition proDefinition, QueryFilter filter);

	public boolean checkNameByVo(ProDefinition proDefinition);

	public boolean checkProcessNameByVo(ProDefinition proDefinition);
	//根据流程实现运行状态来查找流程
	public List<ProDefinition> findRunningPro(ProDefinition proDefinition,Short runstate,PagingBean pb);
}