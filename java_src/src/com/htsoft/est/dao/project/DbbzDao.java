package com.htsoft.est.dao.project;

import java.util.List;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.jxjy.JxjyDbbz;

public interface DbbzDao extends BaseDao<JxjyDbbz>{

	/**
	 * 获取列表
	 * @param filter
	 * @return
	 */
	public List<JxjyDbbz> listBz(QueryFilter filter);
}
