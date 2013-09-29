package com.htsoft.est.action.flow;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.util.BeanUtil;
import com.htsoft.core.util.JsonUtil;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.est.model.flow.ProcessModule;
import com.htsoft.est.service.flow.ProcessModuleService;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class ProcessModuleAction extends BaseAction{
	@Resource
	private ProcessModuleService processModuleService;
	private ProcessModule processModule;
	
	private Long moduleid;

	public Long getModuleid() {
		return moduleid;
	}

	public void setModuleid(Long moduleid) {
		this.moduleid = moduleid;
	}

	public ProcessModule getProcessModule() {
		return processModule;
	}

	public void setProcessModule(ProcessModule processModule) {
		this.processModule = processModule;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<ProcessModule> list= processModuleService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		JSONSerializer json= JsonUtil.getJSONSerializer("createtime");
		buff.append(json.serialize(list));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				processModuleService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		ProcessModule processModule=processModuleService.get(moduleid);
		
		JSONSerializer json= JsonUtil.getJSONSerializer("createtime");
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(json.serialize(processModule));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(processModule.getModuleid()==null){
			processModule.setCreatetime(new Date());
			processModuleService.save(processModule);
		}else{
			ProcessModule orgProcessModule=processModuleService.get(processModule.getModuleid());
			try{
				BeanUtil.copyNotNullProperties(orgProcessModule, processModule);
				processModuleService.save(orgProcessModule);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
