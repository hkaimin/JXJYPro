package com.htsoft.est.dao.project;

import java.util.List;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.jxjy.JxjyDbbz;
import com.htsoft.est.model.jxjy.JxjyRyxfgl;
import com.htsoft.est.model.system.AppUser;

public interface DbbzDao extends BaseDao<JxjyDbbz>{

	/**
	 * 获取列表
	 * @param filter
	 * @return
	 */
	public List<JxjyDbbz> listBz(QueryFilter filter);
	
	/**
	 * 
	 *根据年份，人员，获取标准
	 * @param nf
	 * @return
	 */
	public List<JxjyDbbz> getBzByNf(String nf, Long userId);
	
	/**
	 * 获取人员的学分
	 * @param xflbId
	 * @return
	 */
	public Long getXfByLb(Long xflbId, Long userId, String nf, String isCheck);
	
	/**
	 * 根据单位和职称加载人员
	 * @param orgId
	 * @param zc
	 * @return
	 */
	public List<AppUser> getUserByOrgAndZc(Long orgId, String zc);
}
