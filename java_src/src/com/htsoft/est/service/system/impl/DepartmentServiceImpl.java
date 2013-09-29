package com.htsoft.est.service.system.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.dao.system.DepartmentDao;
import com.htsoft.est.model.system.Department;
import com.htsoft.est.service.system.DepartmentService;

public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements
		DepartmentService {

	private DepartmentDao dao;
	public DepartmentServiceImpl(DepartmentDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public List<Department> findByParentId(Long parentId) {
		return dao.findByParentId(parentId);
	}
	@Override
	public List<Department> findByDepName(String depName) {
		return dao.findByDepName(depName);
	}
	@Override
	public List<Department> findByPath(String path) {
		return dao.findByPath(path);
	}

	/**
	 * 删除某个部门及其下属部门
	 * @param depId
	 */
	public void delCascade(Long depId){
		Department dep=get(depId);
		evict(dep);
		List<Department> listDeps=findByPath(dep.getPath());
		for(Department o:listDeps){
			remove(o);
		}
	}
	
}
