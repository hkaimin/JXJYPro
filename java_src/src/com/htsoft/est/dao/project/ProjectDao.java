package com.htsoft.est.dao.project;

import java.util.List;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.jxjy.JxjyXmgl;

public interface ProjectDao extends BaseDao<JxjyXmgl>{
	
	public List<JxjyXmgl> listProject(QueryFilter filter);
	
	/**
	 * 获取项目根据组织Id
	 * @param orgId
	 * @return
	 */
	public List<JxjyXmgl> getProjectByOrg(Long orgId);
	
	/**
	 * 搜索
	 * @param filter
	 * @param xm
	 * @return
	 */
	public List<JxjyXmgl> search(QueryFilter filter, JxjyXmgl xm);
}
