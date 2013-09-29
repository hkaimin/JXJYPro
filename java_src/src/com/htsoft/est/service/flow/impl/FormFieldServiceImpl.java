package com.htsoft.est.service.flow.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.flow.FormFieldDao;
import com.htsoft.est.model.flow.FormField;
import com.htsoft.est.service.flow.FormFieldService;

public class FormFieldServiceImpl extends BaseServiceImpl<FormField> implements FormFieldService{
	@SuppressWarnings("unused")
	private FormFieldDao dao;
	
	public FormFieldServiceImpl(FormFieldDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	/**
	 * 取某个表的标题字段
	 * @param tableId
	 * @param isFlowTitle
	 * @return
	 */
	public FormField find(Long tableId,Short isFlowTitle){
		return dao.find(tableId, isFlowTitle);
	}

}