package com.htsoft.est.service.flow;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.flow.FieldRights;

public interface FieldRightsService extends BaseService<FieldRights>{
	/**
	 * 根据映射字段和节点来查找权限
	 * @param mappingId
	 * @param fieldId
	 * @param taskName
	 * @return
	 */
	public List<FieldRights> getByMappingFieldTaskName(Long mappingId,Long fieldId,String taskName);
	/**
	 * 根据映射和任务节点来查找表单的权限列表
	 * @param mappingId
	 * @param taskName
	 * @return
	 */
	public List<FieldRights> getByMappingIdAndTaskName(Long mappingId,String taskName);
	/**
	 * 根据映射ID来查找权限
	 * @param mappingId
	 * @return
	 */
	public List<FieldRights> getByMappingId(Long mappingId);
}


