package com.htsoft.est.service.flow;

/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
 */
import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.flow.FormDefMapping;

public interface FormDefMappingService extends BaseService<FormDefMapping> {
	/**
	 * 按jbpm流程发布id取得表单映射
	 * 
	 * @param deployId
	 * @return
	 */
	public FormDefMapping getByDeployId(String deployId);

	/**
	 * @description 根据defId查询是否已经设置表单数据,存在：FormDefMapping,否则：null
	 * @param defId
	 *            流程定义Id
	 * @return FormDefMapping,null
	 */
	FormDefMapping findByDefId(Long defId);
	/**
	 * 检查表单是否已经被映射
	 * @param formDefId
	 * @return
	 */
	public boolean formDefHadMapping(Long formDefId);
	
	public void copyNewConfig(String oldDeployId,String newDeployId,Integer versionNo);
}
