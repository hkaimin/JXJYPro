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
	
	/**
	 * 上报给医院进行审核
	 * @param xmId
	 */
	public void reportToYy(Long xmId);
	
	/**
	 * 审核项目
	 * @param xmId
	 * @param xmbh
	 */
	public void checkProject(Long xmId, String xmbh, String isCheck);
	
	/**
	 * 医院审核项目
	 * @param xmId
	 * @param isCheck
	 */
	public void checkProjectYy(Long xmId, String isCheck);
	
	/**
	 * 删除项目
	 * @param ids
	 */
	public void mutilDel(String[] ids);
	
	/**
	 * 搜索
	 * @param filter
	 * @param xm
	 * @return
	 */
	public List<JxjyXmgl> search(QueryFilter filter, JxjyXmgl xm);
}
