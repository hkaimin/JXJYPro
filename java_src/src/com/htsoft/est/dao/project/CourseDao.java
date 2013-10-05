package com.htsoft.est.dao.project;

import java.util.List;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.jxjy.JxjyKtgl;

public interface CourseDao extends BaseDao<JxjyKtgl>{

	
	public List<JxjyKtgl> listCourse(QueryFilter filter);
	
	public List<JxjyKtgl> search(QueryFilter filter, JxjyKtgl kt);
}
