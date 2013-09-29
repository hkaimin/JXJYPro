package com.htsoft.est.service.flow;

/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
 */
import java.util.List;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.service.BaseService;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.model.flow.ProDefinition;
import com.htsoft.est.model.system.AppUser;

public interface ProDefinitionService extends BaseService<ProDefinition> {
	public ProDefinition getByDeployId(String deployId);

	public ProDefinition getByName(String name);

	public List<ProDefinition> getByRights(AppUser curUser,
			ProDefinition proDefinition, QueryFilter filter);

	/**
	 * 返回true则通过,false则重名
	 * 
	 * @param proDefinition
	 * @return
	 */
	public boolean checkNameByVo(ProDefinition proDefinition);

	/**
	 * 返回true则通过,false则重名
	 * 
	 * @param proDefinition
	 * @return
	 */
	public boolean checkProcessNameByVo(ProDefinition proDefinition);

	// 根据流程实现运行状态来查找流程
	public List<ProDefinition> findRunningPro(ProDefinition proDefinition,
			Short runstate, PagingBean pb);
	
	/**
	 * @description 保存流程信息,包含流程发布
	 * @param proDefinition ProDefinition
	 * @param deploy true:保存，并发布;false:只保存
	 * @return 数据操作信息
	 */
	String defSave(ProDefinition proDefinition,Boolean deploy);
}
