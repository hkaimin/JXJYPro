package com.htsoft.est.service.project.impl;

import java.util.List;

import javax.annotation.Resource;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.core.util.BeanUtil;
import com.htsoft.est.dao.project.CourseDao;
import com.htsoft.est.model.jxjy.JxjyKtgl;
import com.htsoft.est.model.jxjy.JxjyXmgl;
import com.htsoft.est.service.project.CourseService;

public class CourseServiceImpl extends BaseServiceImpl<JxjyKtgl> implements CourseService{

	@Resource
	private CourseDao dao;
	
	public CourseServiceImpl(CourseDao dao) {
		super(dao);
		this.dao = dao;
		// TODO Auto-generated constructor stub
	}

	@Override
	public JxjyKtgl saveCourse(JxjyKtgl course) {
		// TODO Auto-generated method stub
		//新建
		if(course.getKtId() == null) {
			JxjyKtgl temp = this.dao.save(course);
			return temp;
		} else { //修改
			JxjyKtgl temp = this.dao.get(course.getKtId());
			try {
				BeanUtil.copyNotNullProperties(temp, course);
				return dao.save(temp);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				return null;
			}
		}
	}

	@Override
	public List<JxjyKtgl> listCourse(QueryFilter filter) {
		// TODO Auto-generated method stub
		return this.dao.listCourse(filter);
	}

	@Override
	public void mutilDel(String[] ids) {
		// TODO Auto-generated method stub
		for(String id : ids) {
			this.dao.remove(new Long(id));
		}
	}

	@Override
	public List<JxjyKtgl> search(QueryFilter filter, JxjyKtgl kt) {
		// TODO Auto-generated method stub
		return this.dao.search(filter, kt);
	}

}
