package com.htsoft.est.service.project;

import java.util.List;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.jxjy.JxjyKtgl;

public interface CourseService extends BaseService<JxjyKtgl>{

	public JxjyKtgl saveCourse(JxjyKtgl course);
	
	public List<JxjyKtgl> listCourse(QueryFilter filter);
	
	public void mutilDel(String[] ids);
	
	public List<JxjyKtgl> search(QueryFilter filter, JxjyKtgl kt);
}
