package com.htsoft.est.service.flow.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.flow.FormTemplateDao;
import com.htsoft.est.model.flow.FormDefMapping;
import com.htsoft.est.model.flow.FormTemplate;
import com.htsoft.est.service.flow.FormTemplateService;

public class FormTemplateServiceImpl extends BaseServiceImpl<FormTemplate> implements FormTemplateService{
	@SuppressWarnings("unused")
	private FormTemplateDao dao;
	
	public FormTemplateServiceImpl(FormTemplateDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	/**
	 * 按映射取到所有的流程表单定义
	 * @param mappingId
	 * @return
	 */
	public List<FormTemplate> getByMappingId(Long mappingId){
		return dao.getByMappingId(mappingId);
	}

	@Override
	public void batchAddDefault(List<String> nodeNames, FormDefMapping fdm) {
		for(String nodeName:nodeNames){
			FormTemplate formTemplate=new FormTemplate();
			formTemplate.setFormDefMapping(fdm);
			formTemplate.setNodeName(nodeName);
			save(formTemplate);
		}
		
	}
	
	/**
	 * 取得映射
	 * @param mappingId
	 * @param nodeName
	 * @return
	 */
	public FormTemplate getByMappingIdNodeName(Long mappingId,String nodeName){
		return dao.getByMappingIdNodeName(mappingId, nodeName);
	}
}