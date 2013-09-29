package com.htsoft.est.action.flow;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jbpm.api.ProcessInstance;
import org.jbpm.pvm.internal.model.ExecutionImpl;

import com.htsoft.core.service.DynamicService;
import com.htsoft.core.util.BeanUtil;
import com.htsoft.core.util.StringUtil;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.est.model.flow.ProcessRun;
import com.htsoft.est.service.flow.JbpmService;
import com.htsoft.est.service.flow.ProcessFormService;
import com.htsoft.est.service.flow.ProcessRunService;

/**
 * 显示运行中的流程信息
 * @author csx
 *
 */
public class ProcessRunDetailAction extends BaseAction{
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private ProcessFormService processFormService;
	@Resource
	private JbpmService jbpmService;
	
	private Long runId;

	public Long getRunId() {
		return runId;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	private Long taskId;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	@Override
	public String execute() throws Exception {
		ProcessRun processRun=null;
		if(runId==null){
			ExecutionImpl pis=(ExecutionImpl)jbpmService.getProcessInstanceByTaskId(taskId.toString());
			String piId=pis.getId();
			if(pis.getSuperProcessExecution()!=null){
				piId=pis.getSuperProcessExecution().getId();
			}
			processRun=processRunService.getByPiId(piId);
			getRequest().setAttribute("processRun", processRun);
			runId=processRun.getRunId();
		}else{
			processRun=processRunService.get(runId);
		}
		
		//取到绑定的实体
		Serializable pkValue=(String)processRun.getEntityId(); 
		String entityName=processRun.getEntityName();
		
		if(pkValue!=null&& entityName!=null){
			//检查某个字符串是否number类型
			if(StringUtil.isNumeric(pkValue.toString())){
				pkValue=new Long(pkValue.toString());
			}
			if(entityName!=null){//实体名不为空
				DynamicService dynamicService=BeanUtil.getDynamicServiceBean(entityName);
				if(pkValue!=null){//主键值不为空
					Object entity=dynamicService.get(pkValue);
					//输出实体的描述信息
					if(entity!=null){
						getRequest().setAttribute("entity", entity);
						getRequest().setAttribute("entityHtml",BeanUtil.mapEntity2Html((Map<String,Object>)entity, entityName));
					}
				}
			}
		}
		
		List pfList=processFormService.getByRunId(runId);
		
		getRequest().setAttribute("pfList", pfList);
		
		return SUCCESS;
	}
}
