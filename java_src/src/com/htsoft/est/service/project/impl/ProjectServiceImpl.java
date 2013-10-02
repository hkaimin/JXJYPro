package com.htsoft.est.service.project.impl;

import java.util.List;

import javax.annotation.Resource;

import org.jfree.util.Log;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.core.util.BeanUtil;
import com.htsoft.est.dao.project.ProjectDao;
import com.htsoft.est.model.jxjy.JxjyXmgl;
import com.htsoft.est.service.project.ProjectService;
import com.htsoft.est.util.JxjyConstant;

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
			project.setYysh(JxjyConstant.PROJECT_YYSH_DAI_SHEN_HE);
			project.setZt(JxjyConstant.PROJECT_ZT_DAI_SHEN_HE);
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

	@Override
	public void reportToYy(Long xmId) {
		// TODO Auto-generated method stub
		JxjyXmgl project = this.dao.get(xmId);
		if(project != null) {
			project.setYysh(JxjyConstant.PROJECT_YYSH_YI_SHANG_CHUAN);
			this.dao.save(project);
		} else {
			Log.error("获取不到项目");
		}
	}

	@Override
	public void checkProject(Long xmId, String xmbh, String isCheck) {
		// TODO Auto-generated method stub
		if(isCheck.equals(JxjyConstant.PROJECT_ZT_TONG_GUO)) {
			JxjyXmgl project = this.dao.get(xmId);
			project.setXmbh(xmbh);
			project.setZt(JxjyConstant.PROJECT_ZT_TONG_GUO);
			this.dao.save(project);
		} else if(isCheck.equals(JxjyConstant.PROJECT_ZT_BU_TONG_GUO)) {
			JxjyXmgl project = this.dao.get(xmId);
			project.setZt(JxjyConstant.PROJECT_ZT_BU_TONG_GUO);
			this.dao.save(project);
		}
	}

	@Override
	public void checkProjectYy(Long xmId, String isCheck) {
		// TODO Auto-generated method stub
		JxjyXmgl project = this.dao.get(xmId);
		if(isCheck.equals(JxjyConstant.PROJECT_YYSH_TONG_GUO.toString())) {
			project.setYysh(JxjyConstant.PROJECT_YYSH_TONG_GUO);
			this.dao.save(project);
		} else if(isCheck.equals(JxjyConstant.PROJECT_YYSH_BU_TONG_GUO.toString())) {
			project.setYysh(JxjyConstant.PROJECT_YYSH_BU_TONG_GUO);
			this.dao.save(project);
		}
	}

}
