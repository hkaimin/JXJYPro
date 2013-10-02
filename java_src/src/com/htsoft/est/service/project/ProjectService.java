package com.htsoft.est.service.project;

import java.util.List;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.jxjy.JxjyXmgl;

public interface ProjectService extends BaseService<JxjyXmgl>{
	
	public JxjyXmgl saveProject(JxjyXmgl project);
	
	public List<JxjyXmgl> listProject(QueryFilter filter);
	
	/**
	 * 获取项目根据组织Id
	 * @param orgId
	 * @return
	 */
	public List<JxjyXmgl> getProjectByOrg(Long orgId);
}
