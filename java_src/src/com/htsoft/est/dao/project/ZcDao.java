package com.htsoft.est.dao.project;

import java.util.List;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.jxjy.JxjyZc;

public interface ZcDao extends BaseDao<JxjyZc>{

	/**
	 * 获取职称根据父亲Id
	 * @param pId
	 * @return
	 */
	public List<JxjyZc> getZcByParentId(Long pId);
	
}
