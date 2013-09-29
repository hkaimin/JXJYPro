package com.htsoft.est.service.system;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;

import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.system.Department;

public interface DepartmentService extends BaseService<Department> {

	public List<Department> findByParentId(Long parentId);
	public List<Department> findByDepName(String depName);
	public List<Department> findByPath(String path);
	
	/**
	 * 删除某个部门及其下属部门
	 * @param depId
	 */
	public void delCascade(Long depId);
}
