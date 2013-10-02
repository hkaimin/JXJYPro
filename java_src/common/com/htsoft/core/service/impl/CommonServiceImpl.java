package com.htsoft.core.service.impl;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.htsoft.core.dao.BasicDao;

public class CommonServiceImpl implements CommonService{
	
	private BasicDao basicDao;
	public BasicDao getBasicDao() {
		return basicDao;
	}
	public void setBasicDao(BasicDao basicDao) {
		this.basicDao = basicDao;
	}

	public SimpleJdbcTemplate sjt;
	
	
	public DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		sjt = new SimpleJdbcTemplate(this.getDataSource());
	}
	
	 
	 public SimpleJdbcTemplate getSjt() {
		return sjt;
	}
	 
	 public void saveAndUpdate(Object obj) {
			// TODO Auto-generated method stub
			this.getBasicDao().saveOrUpdate(obj);
			
		}

}
