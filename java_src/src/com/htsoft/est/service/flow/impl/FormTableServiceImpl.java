package com.htsoft.est.service.flow.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.dao.flow.FormTableDao;
import com.htsoft.est.model.flow.FormTable;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.service.flow.FormTableService;

public class FormTableServiceImpl extends BaseServiceImpl<FormTable> implements FormTableService{
	@SuppressWarnings("unused")
	private FormTableDao dao;
	
	public FormTableServiceImpl(FormTableDao dao) {
		super(dao);
		this.dao=dao;
	}
	public List<FormTable> getListFromPro(String typeId,String tableName,AppUser curUser,PagingBean pb){
		
		return this.dao.getListFromPro( typeId, tableName, curUser, pb);
	}
	
	/**
	 * 返回所有表定义及其表的字段
	 * @return
	 */
	public List<FormTable> getAllAndFields(){
		return dao.getAllAndFields();
	}
	@Override
	public List<FormTable> findByTableKey(String tableKey) {
		return dao.findByTableKey(tableKey);
	}

}