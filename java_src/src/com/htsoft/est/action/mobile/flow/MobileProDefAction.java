package com.htsoft.est.action.mobile.flow;

import java.util.List;

import javax.annotation.Resource;

import com.htsoft.core.web.action.BaseAction;
import com.htsoft.est.model.flow.ProDefinition;
import com.htsoft.est.service.flow.ProDefinitionService;

public class MobileProDefAction extends BaseAction{
	@Resource
	private ProDefinitionService proDefinitionService;
	
	public String list(){
		List<ProDefinition> proDefList=proDefinitionService.getAll(getInitPagingBean());
		getRequest().setAttribute("proDefList", proDefList);
		return SUCCESS;
	}
}
