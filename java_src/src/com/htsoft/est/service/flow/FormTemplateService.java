package com.htsoft.est.service.flow;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.flow.FormDefMapping;
import com.htsoft.est.model.flow.FormTemplate;

public interface FormTemplateService extends BaseService<FormTemplate>{
	/**
	 * 按映射取到所有的流程表单定义
	 * @param mappingId
	 * @return
	 */
	public List<FormTemplate> getByMappingId(Long mappingId);
	
	/**
	 * 为某个流程定义映射添加缺省的流程表单定义
	 * @param nodeNames
	 * @param fdm
	 */
	public void batchAddDefault(List<String>nodeNames,FormDefMapping fdm);
	
	/**
	 * 取得映射
	 * @param mappingId
	 * @param nodeName
	 * @return
	 */
	public FormTemplate getByMappingIdNodeName(Long mappingId,String nodeName);
}
