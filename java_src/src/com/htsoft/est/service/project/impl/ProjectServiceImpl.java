package com.htsoft.est.service.project.impl;

import java.util.List;

import javax.annotation.Resource;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.core.util.BeanUtil;
import com.htsoft.est.dao.project.ProjectDao;
import com.htsoft.est.model.jxjy.JxjyXmgl;
import com.htsoft.est.service.project.ProjectService;

public class ProjectServiceImpl extends BaseServiceImpl<JxjyXmgl> implements ProjectService{

	@Resource
	private ProjectDao dao;
	
	public ProjectServiceImpl(ProjectDao dao) {
		super(dao);
		this.dao = dao;
		// TODO Auto-generated constructor stub
	}

	@Override
	public JxjyXmgl saveProject(JxjyXmgl project) {
		// TODO Auto-generated method stub
		//新建
		if(project.getXmId() == null) {
			JxjyXmgl temp = this.dao.save(project);
			return temp;
		} else { //修改
			JxjyXmgl temp = this.dao.get(project.getXmId());
			try {
				BeanUtil.copyNotNullProperties(temp, project);
				return dao.save(temp);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				return null;
			}
		}
	}

	@Override
	public List<JxjyXmgl> listProject(QueryFilter filter) {
		// TODO Auto-generated method stub
		return this.dao.listProject(filter);
	}

	@Override
	public List<JxjyXmgl> getProjectByOrg(Long orgId) {
		// TODO Auto-generated method stub
		return this.dao.getProjectByOrg(orgId);
	}

}
