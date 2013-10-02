package com.htsoft.est.service.ryxf.impl;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.htsoft.core.service.impl.CommonServiceImpl;
import com.htsoft.est.model.jxjy.JxjyXmgl;
import com.htsoft.est.service.ryxf.MyJdbcService;

public class MyJdbcServiceImpl extends CommonServiceImpl implements MyJdbcService{

	protected Log logger=LogFactory.getLog(MyJdbcServiceImpl.class);
	public SimpleJdbcTemplate sjt;
	public DataSource dataSource;
	
	@Override
	public DataSource getDataSource() {
		return dataSource;
	}
	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		sjt = new SimpleJdbcTemplate(this.getDataSource());
	}
	@Override
	public SimpleJdbcTemplate getSjt() {
		return sjt;
	}
	@Override
	public List<JxjyXmgl> getXmgl() {
		String sql ="  ";
		return null;
	}
	
}
