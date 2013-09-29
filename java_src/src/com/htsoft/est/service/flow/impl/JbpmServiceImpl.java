package com.htsoft.est.service.flow.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.Session;
import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.model.Activity;
import org.jbpm.api.model.Transition;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.jbpm.jpdl.internal.activity.TaskActivity;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.history.model.HistoryProcessInstanceImpl;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;
import org.jbpm.pvm.internal.repository.RepositoryCache;
import org.jbpm.pvm.internal.svc.TaskServiceImpl;
import org.jbpm.pvm.internal.task.ParticipationImpl;
import org.jbpm.pvm.internal.task.TaskDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.jbpm.pvm.internal.wire.descriptor.ObjectDescriptor;
import org.jbpm.pvm.internal.wire.descriptor.StringDescriptor;
import org.jbpm.pvm.internal.wire.operation.FieldOperation;
import org.jbpm.pvm.internal.wire.operation.Operation;
import org.jbpm.pvm.internal.wire.usercode.UserCodeReference;

import com.htsoft.core.Constants;
import com.htsoft.core.jbpm.jpdl.Node;
import com.htsoft.core.util.ContextUtil;
import com.htsoft.core.util.StringUtil;
import com.htsoft.est.FlowConstants;
import com.htsoft.est.action.flow.FlowRunInfo;
import com.htsoft.est.dao.flow.JbpmDao;
import com.htsoft.est.model.flow.FormDef;
import com.htsoft.est.model.flow.FormDefMapping;
import com.htsoft.est.model.flow.ProDefinition;
import com.htsoft.est.model.flow.ProUserAssign;
import com.htsoft.est.model.flow.ProcessForm;
import com.htsoft.est.model.flow.ProcessRun;
import com.htsoft.est.model.flow.TaskSign;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.model.system.Organization;
import com.htsoft.est.model.system.Position;
import com.htsoft.est.model.system.UserPosition;
import com.htsoft.est.service.flow.FormDefMappingService;
import com.htsoft.est.service.flow.JbpmService;
import com.htsoft.est.service.flow.ProDefinitionService;
import com.htsoft.est.service.flow.ProHandleCompService;
import com.htsoft.est.service.flow.ProUserAssignService;
import com.htsoft.est.service.flow.ProcessFormService;
import com.htsoft.est.service.flow.ProcessRunService;
import com.htsoft.est.service.flow.TaskSignDataService;
import com.htsoft.est.service.flow.TaskSignService;
import com.htsoft.est.service.system.AppUserService;
import com.htsoft.est.service.system.OrganizationService;
import com.htsoft.est.service.system.PositionService;
import com.htsoft.est.service.system.RelativeUserService;
import com.htsoft.est.service.system.UserOrgService;
import com.htsoft.est.service.system.UserPositionService;

public class JbpmServiceImpl implements JbpmService{
	private static final Log logger=LogFactory.getLog(JbpmServiceImpl.class);
	@Resource
	private JbpmDao jbpmDao;
	@Resource
	private ProcessEngine processEngine;
	@Resource
	private RepositoryService repositoryService;
	@Resource
	private ExecutionService executionService; 
	@Resource
	private ProDefinitionService proDefinitionService;
	@Resource 
	private ProHandleCompService proHandleCompService;
	@Resource
	private TaskService taskService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private HistoryService historyService;
	@Resource
	private ProUserAssignService proUserAssignService;
	@Resource
	private RelativeUserService relativeUserService;
	//TODO 需要去掉该注入方式，把该运行服务转至其他
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private ProcessFormService processFormService;
	@Resource
	private UserPositionService userPositionService;
	@Resource
	private TaskSignDataService taskSignDataService;
	@Resource
	private TaskSignService taskSignService;
	@Resource
	FormDefMappingService formDefMappingService;
	
	@Resource(name="flowTaskService")
	private com.htsoft.est.service.flow.TaskService flowTaskService;
	
	@Resource
	private UserOrgService userOrgService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private PositionService positionService;
	
	/*
	 * (non-Javadoc)
	 * @see com.htsoft.est.service.flow.JbpmService#doStartProcess(com.htsoft.est.action.flow.FlowRunInfo)
	 */
	@SuppressWarnings("unchecked")
	public ProcessRun doStartProcess(FlowRunInfo startInfo){
		//初始化流程运行
		ProcessRun processRun=processRunService.getInitFromFlowRunInfo(startInfo);
		//对于在线表单，设置其对应的formDefId,即流程对应的流程表单
		if(!startInfo.isUseTemplate()){
			ProDefinition proDefinition=proDefinitionService.get(new Long(startInfo.getDefId()));
			//缺省使用默认的表单
			Long formDefId=FormDef.DEFAULT_FLOW_FORMID;
			FormDefMapping fdm=formDefMappingService.getByDeployId(proDefinition.getDeployId().toString());
			//若在后台设置了指定的表单
			if(fdm!=null){
				formDefId=fdm.getFormDefId();
			}
			processRun.setFormDefId(formDefId);
		}
		
		//若存在使用实体的方式
		if(startInfo.getEntityPK()!=null){
			//设置他们的实体主键值及实体名
			processRun.setEntityId(startInfo.getEntityPK().toString());
			processRun.setEntityName(startInfo.getEntityName());
		}
		processRunService.save(processRun);
		//1.add the common variable here
		//设置流程启者人ID
		startInfo.getVariables().put(FlowRunInfo.START_USER_ID, ContextUtil.getCurrentUserId());
		//设置流程运行ID
		startInfo.getVariables().put(FlowRunInfo.PROCESS_RUNID, processRun.getRunId());
		//设置业务实体ID
		startInfo.getVariables().put(FlowRunInfo.ENTITY_PK, startInfo.getEntityPK());
		//设置业务实体类
		startInfo.getVariables().put(FlowRunInfo.ENTITY_NAME, startInfo.getEntityName());
		
		//2.启动流程
		ProcessInstance pi=startProcess(processRun.getProDefinition().getDeployId(),startInfo.getDestName(),startInfo.getVariables());
		//3.保存回其状态
		processRun.setPiId(pi.getId());
		processRun.setRunStatus(ProcessRun.RUN_STATUS_RUNNING);
		processRunService.save(processRun);
		//4.保存流程启动的历史信息
		saveInitProcessForm(processRun, startInfo);
		
		
		//5.进行启动后的人员指派	
    	assignTask(pi,startInfo.getVariables());
    	if(pi.getSubProcessInstance()!=null){
	    	 logger.info("debug for subProcessinstance...........");
	    	 assignTask((ProcessInstance)pi.getSubProcessInstance(),startInfo.getVariables());
	    }
    	
		return processRun;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.htsoft.est.service.flow.JbpmService#doNextStep(com.htsoft.est.action.flow.FlowRunInfo)
	 */
	public ProcessRun doNextStep(FlowRunInfo nextInfo){
		
		ProcessInstance pi;
		String nodeType;
		if(StringUtils.isNotEmpty(nextInfo.getTaskId())){//对于任务节点情况
			nodeType="task";
			pi=getProcessInstanceByTaskId(nextInfo.getTaskId());
		}else{//对应非任务节点情况(如State)
			pi=getProcessInstance(nextInfo.getPiId());
			String xml=getDefinitionXmlByPiId(pi.getId());
			nodeType=getNodeType(xml, nextInfo.getActivityName());
		}
		
		ProcessRun processRun=processRunService.getByPiId(pi.getId());
		AppUser curUser=ContextUtil.getCurrentUser();

		//设置当前任务为完成状态，并且为下一任务设置新的执行人或候选人
		if("task".equals(nodeType)){
			TaskImpl curTask=(TaskImpl)taskService.getTask(nextInfo.getTaskId());
			
			String curTaskId=curTask.getId();
			String curActivityName=curTask.getActivityName();
			
			//若为子任务，则取得其任务Id
			String taskId=nextInfo.getTaskId();
			if(curTask.getSuperTask()!=null){
				taskId=curTask.getSuperTask().getId();
			}
			//找到该任务的历史信息，保存其跳转信息
			ProcessForm curTaskForm=processFormService.getByTaskId(taskId);
			//任务驳回，使用办法是不删除原有的任务，只是把任务的执行人员及对应的Execution进行了更改，使其变上一步任务的实例
			if(nextInfo.isBack()&&curTaskForm!=null){
				ProcessForm preTaskForm=null;
				if(curTaskForm.getPreFormId()==null){//当前节点为开始节点，不进行后退操作
					preTaskForm=processFormService.get(curTaskForm.getPreFormId());
				}else{
					preTaskForm=processFormService.get(curTaskForm.getPreFormId());
				}
				
				logger.debug("准备从任务" + curTaskForm.getActivityName()+"========回退");
				//jumpToPreTask(curTask, preTaskForm.getActivityName(), preTaskForm.getCreatorId().toString());
				jumpToPreTask(pi.getId(), preTaskForm.getCreatorId().toString(),preTaskForm.getActivityName());
				
				nextInfo.setDestName(preTaskForm.getActivityName());
				
				logger.debug("成功产生回退任务至＝＝＝＝＝＝＝＝＝＝" + preTaskForm.getActivityName());
			}else{
				//完成此任务，同时为下一任务指定执行人
				completeTask(nextInfo.getTaskId(),nextInfo.getTransitionName(),nextInfo.getDestName(),nextInfo.getVariables());
			}
		 	//更新任务的执行历史信息
			if(curTaskForm!=null){
				//更新执行人
				curTaskForm.setCreatorId(curUser.getUserId());
				curTaskForm.setCreatorName(curUser.getFullname());
				curTaskForm.setEndtime(new Date());
				curTaskForm.setTransTo(nextInfo.getTransitionName());
				if(nextInfo.isBack()){//若为退回
					curTaskForm.setStatus(ProcessForm.STATUS_BACK);
				}else{
					curTaskForm.setStatus(ProcessForm.STATUS_PASS);
				}
				long durTimes=new Date().getTime()-curTaskForm.getCreatetime().getTime();
				curTaskForm.setDurTimes(durTimes);
				//加上审批意见
				curTaskForm.setComments(nextInfo.getComments());
				processFormService.save(curTaskForm);
			}
			if(!ProcessRun.RUN_STATUS_FINISHED.equals(processRun.getRunStatus())){
			//保存下一步流程历史列表
			List<Task> tasks=getTasksByPiId(processRun.getPiId());
			//产生该任务对应的表单及历史信息 TODO 若有并发任务，则以下产生表单历史会有重复
				for(Task task:tasks){
					logger.debug("cur task:==="+task.getActivityName());
					ProcessForm taskForm=new ProcessForm();
					taskForm.setActivityName(task.getActivityName());
					//taskForm.setCreatorName(task.getAssignee());
					taskForm.setCreatetime(new Date());
					taskForm.setTaskId(task.getId());
					if(curTaskForm!=null){
						taskForm.setPreFormId(curTaskForm.getFormId());
					}
					taskForm.setFromTask(curActivityName);
					taskForm.setFromTaskId(curTaskId);
					taskForm.setStatus(ProcessForm.STATUS_INIT);
					taskForm.setProcessRun(processRun);
					processFormService.save(taskForm);
				}
			}
		}else{//普通节点
			signalProcess(pi.getId(), nextInfo.getTransitionName(), nextInfo.getVariables()); 
		}

		return processRun;

	}
	/**
	 * 从当前任务跳回上一任务
	 * @param curTask 当前任务对象
	 * @param preTaskName 前一任务名称 
	 * @param assignee 前一任务执行人ID
	 * @return
	 */
	public Task jumpToPreTask(Task curTask,String preTaskName,String assignee){
		EnvironmentImpl env=null;
		try{
			TaskImpl task=(TaskImpl)curTask;
			env=((EnvironmentFactory) processEngine).openEnvironment();
			ProcessInstance pi=getProcessInstanceByTaskId(curTask.getId());
			
			ProcessDefinitionImpl pd=(ProcessDefinitionImpl)getProcessDefinitionByTaskId(curTask.getId());
			TaskDefinitionImpl taskDef= pd.getTaskDefinition(preTaskName);
			//更换其Execution
			ExecutionImpl exeImpl=(ExecutionImpl)pi;
			//更换其活动的定义
			Activity preActivity=pd.findActivity(preTaskName);
			exeImpl.setActivity(preActivity);
			task.setActivityName(preTaskName);
			task.setName(preTaskName);
			task.setDescription(preTaskName);
			task.setExecution(exeImpl);
			//更换执行人
			task.setAssignee(assignee);
			task.setCreateTime(new Date());
			task.setSignalling(true);
			//task.setNew(true);
			//更换流程任务的定义
			if(taskDef!=null){
				task.setTaskDefinition(taskDef);
			}else{
				//查看其是否为开始任务，这表示开始任务TODO
				//String startName=jbpmService.getStartNodeName();
				TaskDefinitionImpl taskDefinition = new TaskDefinitionImpl();
			    taskDefinition.setName(preTaskName);
			    taskDefinition.setPriority(1);
			    taskDefinition.setProcessDefinition(pd);
			    
			    ActivityImpl startActivityImpl=pd.findActivity(preTaskName);
			    
			    ActivityImpl startTaskImpl=pd.createActivity();
			    startTaskImpl.setName(preTaskName);
			    List outTrans=new ArrayList();
			    outTrans.addAll(startActivityImpl.getOutgoingTransitions());
			    startTaskImpl.setOutgoingTransitions(outTrans);
			
			}
			taskService.saveTask(task);
			return task;
		}finally{
			if(env!=null)env.close();
		}
	}
	/**
	 * 允许任务回退
	 * @param taskId
	 * @return
	 */
	public boolean isAllownBack(String taskId){
		TaskImpl task=(TaskImpl)getTaskById(taskId);
		if(task.getSuperTask()!=null){
			taskId=task.getSuperTask().getId();
		}
		
		ProcessForm taskForm=processFormService.getByTaskId(taskId);
		
		if(taskForm!=null && taskForm.getPreFormId()!=null && taskForm.getFromTaskId()!=null){//即前一任务节点存在
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * 取得前一任务
	 * @param taskId
	 * @return
	 */
	public String getPreTask(String taskId){
		TaskImpl task=(TaskImpl)getTaskById(taskId);
		if(task.getSuperTask()!=null){
			taskId=task.getSuperTask().getId();
		}
		ProcessForm taskForm=processFormService.getByTaskId(taskId);
		if(taskForm!=null && taskForm.getPreFormId()!=null && taskForm.getFromTaskId()!=null){//即前一任务节点存在
			ProcessForm preProcessForm=processFormService.get(taskForm.getPreFormId());
			return preProcessForm.getActivityName();
		}
		return null;
	}
	
	/**
	 * 是否为会签任务 
	 * 说明：通过判断taskId对应的任务是否为子任务来决定是否为会签任务，
	 * 更好的办法应该是去pro_user_assign表中查询isSigned字段来决定
	 * @param taskId
	 * @return
	 */
	public boolean isSignTask(String taskId){
		TaskImpl task=(TaskImpl)getTaskById(taskId);
		if(task.getSuperTask()!=null){
			taskId=task.getSuperTask().getId();
			return true;
		}
		return false;
	}
	
	/**
	 * 保存流程启动时表单历史
	 * @param processRun  流程实例
	 * @param startTrans  开始节点的跳转名称
	 */
	private void saveInitProcessForm(ProcessRun processRun,FlowRunInfo startInfo){
		//保存系统本身的启动流程历史
		String startNode=getStartNodeName(processRun.getProDefinition());
		ProcessForm startForm=processFormService.getInitProcessForm();
		startForm.setActivityName(startNode);
		startForm.setProcessRun(processRun);
		String transName=startInfo.getTransitionName();
		//取得开始跳转路径名
		if(transName==null){
			List<String> trans=getStartNodeTransByDeployId(processRun.getProDefinition().getDeployId());
			if(trans.size()>0){
				transName=trans.get(0);
			}
		}
		startForm.setComments(startInfo.getComments());
		startForm.setStatus(ProcessForm.STATUS_PASS);
		startForm.setTransTo(transName);
		
		processFormService.save(startForm);
		
		//保存下一步流程任务历史
		
		List<Task> tasks=getTasksByPiId(processRun.getPiId());
		//产生该任务对应的表单及历史信息
		for(Task task:tasks){
			ProcessForm taskForm=new ProcessForm();
			taskForm.setActivityName(task.getActivityName());
			taskForm.setCreatetime(new Date());
			taskForm.setTaskId(task.getId());
			taskForm.setFromTask(startNode);
			taskForm.setFromTaskId(null);
			taskForm.setPreFormId(startForm.getFormId());
			
			taskForm.setStatus(ProcessForm.STATUS_INIT);
			taskForm.setProcessRun(processRun);
			processFormService.save(taskForm);
		}
	}
	
	
	@Override
	public Task getTaskById(String taskId) {
		
		Task task=taskService.getTask(taskId);
		
		return task;
	}
	
	/**
	 * 任务指定执行
	 * @param taskId
	 * @param userId
	 */
	public void assignTask(String taskId,String userId){
		taskService.assignTask(taskId, userId);
	}
	
	/**
	 * 删除流程定义，同时也删除该流程的相关数据，包括启动的实例，表单等相关的数据
	 * @param defId
	 */
	public void doUnDeployProDefinition(Long defId){
		
		//删除processRun 相关的数据
		processRunService.removeByDefId(defId);
		
		ProDefinition pd=proDefinitionService.get(defId);
		if(pd!=null&& pd.getDeployId()!=null){
			//删除Jbpm的流程定义
			try{
				repositoryService.deleteDeploymentCascade(pd.getDeployId());
			}catch(Exception ex){
				logger.error(ex);
			}
		}
		//删除流程定义
		proDefinitionService.remove(pd);
	}
	
	/**
	 * 发布或更新流程定义
	 * @param proDefinition
	 * @return
	 */
	public ProDefinition saveOrUpdateDeploy(ProDefinition proDefinition){
		//添加新流程或更新现在的流程定义及发布流程至Jbpm流程表中
		if(logger.isDebugEnabled()){
			logger.debug("deploy jbpm jpdl now===========");
		}
		//旧版本的deployId
		String oldDeployId=proDefinition.getDeployId();
		
		String deployId=repositoryService.createDeployment().addResourceFromString("process.jpdl.xml", proDefinition.getDefXml()).deploy();
		ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().deploymentId(deployId).uniqueResult();
	    proDefinition.setDeployId(deployId);

	    if(oldDeployId!=null){//为更新操作，则需要保留原来的流程设置的内容
	    	formDefMappingService.copyNewConfig(oldDeployId, deployId, processDefinition.getVersion());
	    	proHandleCompService.copyNewConfig(oldDeployId, deployId);
	    	proUserAssignService.copyNewConfig(oldDeployId, deployId);
	    }
	    
	    proDefinition.setProcessName(processDefinition.getName());
	    proDefinition.setNewVersion(processDefinition.getVersion());
	    proDefinitionService.save(proDefinition);
	    
	    
	    
		return proDefinition;
	}
	
	/**
	 * 按流程key取得Jbpm最新的流程定义
	 * @param processKey
	 * @return
	 */
	public ProcessDefinition getProcessDefinitionByKey(String processKey){
		List<ProcessDefinition> list= repositoryService.createProcessDefinitionQuery()
		.processDefinitionKey(processKey).orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION).list();
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 按流程key取得流程定义
	 * @return
	 */
	public ProDefinition getProDefinitionByKey(String processKey){
		ProcessDefinition processDefinition=getProcessDefinitionByKey(processKey);
		if(processDefinition!=null){
			ProDefinition proDef=proDefinitionService.getByDeployId(processDefinition.getDeploymentId());
			return proDef;
		}
		return null;
	}
	/**
	 * 按流程实例取得流程定义
	 * @param piId
	 * @return
	 */
	public ProDefinition getProDefinitionByPiId(String piId){
		ProcessInstance pi=getProcessInstance(piId);
		ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().processDefinitionId(pi.getProcessDefinitionId()).uniqueResult();
		return proDefinitionService.getByDeployId(processDefinition.getDeploymentId());
	}
	/*
	 * (non-Javadoc)
	 * @see com.htsoft.est.service.flow.JbpmService#getProcessDefinitionByDefId(java.lang.Long)
	 */
	public ProcessDefinition getProcessDefinitionByDefId(Long defId){
		ProDefinition proDef=proDefinitionService.get(defId);
		if(proDef!=null){
			ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().deploymentId(proDef.getDeployId()).uniqueResult();
			return processDefinition;
		}
		return null;
	}
	
	public ProcessDefinition getProcessDefinitionByPdId(String pdId){
		ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().processDefinitionId(pdId).uniqueResult();
		return processDefinition;
	}
	
	/**
	 * 按流程定义id取得流程定义的XML
	 * @param defId
	 * @return
	 */
	public String getDefinitionXmlByDefId(Long defId){
		ProDefinition proDefinition=proDefinitionService.get(defId);
		return jbpmDao.getDefXmlByDeployId(proDefinition.getDeployId());
	}
	
	/**
	 * 按发布id取得流程定义的XML
	 */
	public String getDefinitionXmlByDpId(String deployId){
		return jbpmDao.getDefXmlByDeployId(deployId);
	}
	
	/**
	 * 按执行实例取得流程的定义
	 * @param exeId
	 */
	public String getDefinitionXmlByExeId(String exeId){
		String pdId=executionService.findExecutionById(exeId).getProcessDefinitionId();
		String deployId=repositoryService.createProcessDefinitionQuery().processDefinitionId(pdId).uniqueResult().getDeploymentId();
		//return getDefinitionXmlByDpId(deployId);
		return jbpmDao.getDefXmlByDeployId(deployId);
	}

	/**
	 * 按流程例id取得流程定义的XML
	 */
	public String getDefinitionXmlByPiId(String piId){
		ProcessInstance pi=executionService.createProcessInstanceQuery().processInstanceId(piId).uniqueResult();
		ProcessDefinition pd=repositoryService.createProcessDefinitionQuery().processDefinitionId(pi.getProcessDefinitionId()).uniqueResult();
		return jbpmDao.getDefXmlByDeployId(pd.getDeploymentId());
	}
	
	
	
	/**
	 * 通过任务取得流程节义
	 * @param taskId
	 * @return
	 */
	public ProcessDefinition getProcessDefinitionByTaskId(String taskId){
		TaskImpl task=(TaskImpl)taskService.getTask(taskId);
		ProcessInstance pi=null;
		if(task.getSuperTask()!=null){
			pi=task.getSuperTask().getProcessInstance();
		}else{
			pi=task.getProcessInstance();
		}
		ProcessDefinition pd=repositoryService.createProcessDefinitionQuery().processDefinitionId(pi.getProcessDefinitionId()).uniqueResult();
		return pd;
	}
	
	

	/**
	 * 取得流程实例
	 * @param piId
	 * @return
	 */
	public ProcessInstance getProcessInstance(String piId){
		ProcessInstance pi=executionService.createProcessInstanceQuery().processInstanceId(piId).uniqueResult();
		return pi;
	}
	
	/**
	 * 通过流程定义取得所有的任务列表
	 * @param defId
	 * @return
	 */
	public List<Node>getTaskNodesByDefId(Long defId){
		ProDefinition proDefinition=proDefinitionService.get(defId);
		String defXml=jbpmDao.getDefXmlByDeployId(proDefinition.getDeployId());
		return getTaskNodesFromXml(defXml,false,false);
	}
	/**
	 * 通过流程定义取得所有的任务列表,可取开始,结束结点
	 */
	@Override
	public List<Node> getTaskNodesByDefId(Long defId, boolean start, boolean end) {
		ProDefinition proDefinition=proDefinitionService.get(defId);
		String defXml=jbpmDao.getDefXmlByDeployId(proDefinition.getDeployId());
		return getTaskNodesFromXml(defXml,start,end);
	}
	/**
	 * 取得可跳转所有节点（除开始节点以外的所有任务节点与结束节点）
	 * @param defId
	 * @return
	 */
	public List<Node>getJumpNodesByDeployId(String deployId){
		//ProDefinition proDefinition=proDefinitionService.getByDeployId(deployId);
		String defXml=jbpmDao.getDefXmlByDeployId(deployId);
		return getTaskNodesFromXml(defXml,false,true);
	}
	
	/**
	 * 加载需要填写表单的流程节点
	 * @param deployId
	 * @return
	 */
	public List<Node>getFormNodesByDeployId(Long deployId){
		String defXml=jbpmDao.getDefXmlByDeployId(deployId.toString());
		return getTaskNodesFromXml(defXml,true,false);
	}
	/**
	 * 取得开始节点名称
	 * @param proDefinition
	 * @return
	 */
	public String getStartNodeName(ProDefinition proDefinition){
		return getStartNodeNameByDeployId(proDefinition.getDeployId());
	}
	
	private String getStartNodeNameByDeployId(String deployId){
		try{
			String defXml=jbpmDao.getDefXmlByDeployId(deployId);
			Element root = DocumentHelper.parseText(defXml).getRootElement();
			 for (Element elem : (List<Element>) root.elements()) {
				 	String tagName=elem.getName();
				 	if("start".equals(tagName)){
				 		Attribute nameAttr=elem.attribute("name");
						if(nameAttr!=null){
							return nameAttr.getValue();
						}
				 		break;
				 	}
			 }
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
		return null;
	}
	
	/**
	 * 取得流程定义节点的跳转名称
	 * @param proDefinition
	 * @return
	 */
	public List<String> getStartNodeTransByDeployId(String deployId){
			String defXml=jbpmDao.getDefXmlByDeployId(deployId);		
			return getStartNodeTransByXml(defXml);
	}
	
	/**
	 * 取得流程定义节点的跳转名称
	 * @param proDefinition
	 * @return
	 */
	public List<String> getStartNodeTransByXml(String defXml){
		List<String>trans=new ArrayList();
		try{
			
			Element root = DocumentHelper.parseText(defXml).getRootElement();
			 for (Element elem : (List<Element>) root.elements()) {
				 	String tagName=elem.getName();
				 	if("start".equals(tagName)){
				 		Iterator<Element> tranIt=elem.elementIterator();
				 		while(tranIt.hasNext()){
				 			trans.add(tranIt.next().attributeValue("name"));
				 		}
				 		break;
				 	}
			 }
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
		return trans;
	}
	
	 /**
     * 从XML文件中取得任务节点名称列表
     * @param xml
     * @param includeStart  是否包括启动节点
     * @param includeEnd 是否包括结束节点
     * @return
     */
    private List<Node> getTaskNodesFromXml(String xml,boolean includeStart,boolean includeEnd){
		List<Node> nodes=new ArrayList<Node>();
		try{
			Element root = DocumentHelper.parseText(xml).getRootElement();
			   for (Element elem : (List<Element>) root.elements()) {
		            String type = elem.getQName().getName();
		            if("task".equalsIgnoreCase(type)){
			            if (elem.attribute("name") != null) {
			               Node node=new Node(elem.attribute("name").getValue(),"任务节点");
			               nodes.add(node);
			            }
		            }else if(includeStart && "start".equalsIgnoreCase(type)){
		            	if (elem.attribute("name") != null){
		            		Node node=new Node(elem.attribute("name").getValue(),"开始节点");
				            nodes.add(node);
		            	}
		            }else if(includeEnd && type.startsWith("end")){
		            	Node node=new Node(elem.attribute("name").getValue(),"结束节点");
			            nodes.add(node);
		            }
		       }
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
		return nodes;
	}
    
    /**
     * 启动工作流
     * @param deployId
     * @param variables
     */
	public ProcessInstance startProcess(String deployId,String destTaskName, Map variables) {
		ProcessDefinitionImpl pd =(ProcessDefinitionImpl) repositoryService.createProcessDefinitionQuery().deploymentId(deployId).uniqueResult();
		
		//启动工作流
		ProcessInstance pi = executionService.startProcessInstanceById(pd.getId(),variables);
		
		Activity destNodeActivity=pd.findActivity(destTaskName);
		
		//说明：由于JBPM4在启动的时候，会跳至第一个节点，并且产生对应的任务实例，以下的代码就是为了让他们跳回用户在界面指定的节点,若目标的节点是任务
		if(destTaskName!=null && destNodeActivity!=null && "task".equals(destNodeActivity.getType())){//需要跳转
			
			List<Task> tasks=getTasksByPiId(pi.getId());
			
			for(int i=0;i<tasks.size();i++){
				Task task=tasks.get(i);
				if(i==0 && destTaskName.equals(task.getName())){//已经跳到用户指定的节点
					break;
				}
				jumpTaskToAnother(task, destTaskName,variables);
			}
			
		}

		return pi;
	}
	
	
	
	/**
	 * 任务从一节点跳至另一目标
	 * @param task
	 * @param destTaskName //目标任务名称
	 * @return 1=为正常的任务跳转（即流程图上两线是有关联）
	 *         0=自由的任务跳转
	 */
	private Integer jumpTaskToAnother(Task task,String destTaskName,Map variables){
		//正常跳转
		Integer formalJump=1;
		
		ProcessDefinition pd=getProcessDefinitionByTaskId(task.getId());
		String signalName=null;
		List<Transition> trans=getTransitionsByTaskId(task.getId(), false);
		//两节点是否存在连接
		boolean isExistTran=false;
		
		for(Transition tran:trans){
			if(destTaskName.equals(tran.getDestination().getName())){
				signalName=tran.getName();
				isExistTran=true;
				break;
			}
		}
		
		if(!isExistTran){//创建连接
			addOutTransition((ProcessDefinitionImpl)pd, task.getActivityName(), destTaskName);
			signalName="to"+destTaskName;
		}
		taskService.setVariables(task.getId(), variables);
		taskService.completeTask(task.getId(), signalName);
		
		if(!isExistTran){//删除连接
			removeOutTransition((ProcessDefinitionImpl)pd, task.getActivityName(), destTaskName);
			formalJump=0;
		}
		return formalJump;
	}
	
	/**
	 * 通过ExecutionId取得processInstance
	 * @param executionId
	 * @return
	 */
	public ProcessInstance getProcessInstanceByExeId(String executionId){
		Execution execution=executionService.findExecutionById(executionId);
		return(ProcessInstance)execution.getProcessInstance();
	}
	
	public ProcessInstance getProcessInstanceByTaskId(String taskId){
		TaskImpl taskImpl=(TaskImpl)taskService.getTask(taskId.toString());
		if(taskImpl.getSuperTask()!=null){//若当前任务存在父任务，应取得其父任务
			taskImpl=taskImpl.getSuperTask();
		}
		return taskImpl.getProcessInstance();
	}
	/**
	 * 从用户提交的参数中取得用户的Map
	 * @param flowAssignId flowAssignId 指定执行人的ID或ID列表，格式如： 领导审批:财务审核:...|1,2:3,4:...),也只可为1,2,3(当下一步仅有一任务时）
	 * @return
	 */
	private Map<String,String> getUserIdsMap(String flowAssignId){
		HashMap<String, String> taskUserIdsMap=new HashMap();
		//assignId格式如下
		if(StringUtils.isNotEmpty(flowAssignId)){//若在流程执行过程中，用户在表单指定了下一步的执行人员，则流程会自动指派至该人来执行
			if(logger.isDebugEnabled()){
				logger.debug("===>assignId:" + flowAssignId);
			}
			String[]assignIds=flowAssignId.split("[|]");
			if(assignIds!=null && assignIds.length==2){//flowAssignId 格式如：领导审批:财务审核:...|1,2:3,4:...
				String[] destTasks=assignIds[0].split("[:]");
				String[] destUserIds=assignIds[1].split("[:]");
				
				if(destTasks!=null && destUserIds!=null){
					for(int i=0;i<destTasks.length;i++){
						taskUserIdsMap.put(destTasks[i], destUserIds[i]);
					}
				}

			}else if(assignIds.length==1){//flowAssignId 格式如：1,2,3...
				taskUserIdsMap.put("CommonTask", flowAssignId);
			}
		}
		return taskUserIdsMap;
	}
	
	/**
	 * 通过父实例更新子实例
	 * @param parentPiId
	 * @param subPiId
	 * @return
	 */
	public ProcessRun initSuProcessRun(String parentPiId,String subPiId){
		//取得父流程实例
		ProcessRun parentProcessRun=processRunService.getByPiId(parentPiId);
		ProcessRun subProcessRun=new ProcessRun();
		
		subProcessRun.setAppUser(parentProcessRun.getAppUser());
		subProcessRun.setBusDesc(parentProcessRun.getBusDesc());
		subProcessRun.setSubject(parentProcessRun.getSubject());
		subProcessRun.setCreatetime(new Date());
		subProcessRun.setCreator(parentProcessRun.getCreator());
		subProcessRun.setRunStatus(ProcessRun.RUN_STATUS_INIT);
		subProcessRun.setPiId(subPiId);
		subProcessRun.setEntityId(parentProcessRun.getEntityId());
		subProcessRun.setEntityName(parentProcessRun.getEntityName());
		subProcessRun.setFormDefId(parentProcessRun.getFormDefId());
		ProDefinition proDefinition=getProDefinitionByPiId(subPiId);
		subProcessRun.setProDefinition(proDefinition);
		
		processRunService.save(subProcessRun);
		
		return subProcessRun;
		
	}
	
	/**
	 * 任务指派
	 * @param pi
	 * @param parentTaskName 父任务名
	 * @param variables 当前任务执行对应的变量
	 */
	public void assignTask(ProcessInstance pi,Map variables){
		//flowAssignId 指定执行人的ID或ID列表，格式如： 领导审批:财务审核:...|1,2:3,4:...),也只可为1,2,3(当下一步仅有一任务时）
		String flowAssignId=(String)variables.get(Constants.FLOW_ASSIGN_ID);
		//任务的过期时间
		String dueDate=(String)variables.get(FlowConstants.DUE_DATE);
		Date dtDueDate=null;
		if(dueDate!=null){
			try{
				 dtDueDate=DateUtils.parseDate(dueDate, new String[]{"yyyy-MM-dd HH:mm:ss"});
			}catch(Exception e){
				logger.error(e.getMessage());
			}
		}
		
		//取到该流程实例的流程定义
		ProcessDefinition pd=repositoryService.createProcessDefinitionQuery().processDefinitionId(pi.getProcessDefinitionId()).uniqueResult();
		
		//取得当前任务的名称，然后根据该任务名称以及流程定义，查看该任务将由哪一个用户或用户组来处理比较合适
		List<Task> taskList=getTasksByPiId(pi.getId());
		
		//用于下一任务的人员授予 。格式为<任务名,用户ID1,ID2...>
		Map<String, String> taskUserIdsMap=getUserIdsMap(flowAssignId);
		
		ExecutionImpl piExeImpl=(ExecutionImpl)pi;
		String piId=null;
		if(piExeImpl.getSuperProcessExecution()!=null){//是否存在父流程
			piId= piExeImpl.getSuperProcessExecution().getId();
			//若为子流程，则检查其processRun是否已经存在记录，若不存在，则产生新的
			ProcessRun subProcessRun=processRunService.getByPiId(pi.getId());
			if(subProcessRun==null){
				initSuProcessRun(piId,pi.getId());
			}
			
			taskUserIdsMap=null;
		}else{
			piId=pi.getId();
		}
		//取得流程启动者ID
		Long flowStartUserId=(Long)executionService.getVariable(piId,FlowRunInfo.START_USER_ID);
		//取得流程的启动者 
		if(flowStartUserId==null){
			ProcessRun processRun=processRunService.getByPiId(piId);
			flowStartUserId=processRun.getUserId();
		}
		/**
		 * 查找目前该流程实例中的所有任务，为其指定相应的执行人员
		 */
		for(Task task:taskList){
				TaskImpl taskImpl=(TaskImpl)task;
				//1.若原来的任务已有执行人,则保持不变.
				if(task.getAssignee()!=null || taskImpl.getAllParticipants().size()>0) continue;
				
				//若该任务为父任务，则跳过(注：只有子任务才有人员参与)
				if(taskImpl.getSubTasks().size()>0) continue;
				
				//检查任务是否有授予人员
				boolean isAssign=false;
				//单一执行人
				Long exeUserId=null;
				//候选执行人员
				HashSet<Long> candidateUserIds=new HashSet<Long>();
				//候选组
				HashSet<Long> candidateGroupIds=new HashSet<Long>();
				
				//取得该任务的后台人员配置
				ProUserAssign assignSetting=proUserAssignService.getByDeployIdActivityName(pd.getDeploymentId(), task.getActivityName());
				
				if(taskUserIdsMap!=null && taskUserIdsMap.size()>0){//若在流程执行过程中，用户在表单指定了下一步的执行人员，则流程会自动指派至该人来执行
					String userIds="";
					//取得任务的人员
					if(taskUserIdsMap.containsKey("CommonTask")){
						userIds=taskUserIdsMap.get("CommonTask");
					}else{
						userIds=taskUserIdsMap.get(taskImpl.getName());
					}
					String[]assignIds=userIds.split("[,]");
					if(assignIds!=null && assignIds.length>1){
						for(String aId:assignIds){
							candidateUserIds.add(new Long(aId));
						}
					}else{
						exeUserId=new Long(userIds);
					}
				}else if(assignSetting!=null){//3.由后台流程设置中来指定用户
					
					if(StringUtils.isNotEmpty(assignSetting.getUserId())){//设置了由单一用户来处理
						//流程需要重新转回给流程启动者
						if(Constants.FLOW_START_ID.equals(assignSetting.getUserId())){//由启动人来处理
							exeUserId=flowStartUserId;
						}else{//由设置人来处理
							String[] exeUserIds=assignSetting.getUserId().split("[,]");
							if(exeUserIds!=null && exeUserIds.length==1){//单个用户
								exeUserId=new Long(exeUserIds[0]);
							}else{
								String []userIds=assignSetting.getUserId().split("[,]");
								for(String userId:userIds){
									candidateUserIds.add(new Long(userId));
								}
							}
						}
					}
					if(StringUtils.isNotEmpty(assignSetting.getRoleId())){//由角色处理
						//TODO 转成由人员来处理
						candidateGroupIds.add(new Long(assignSetting.getRoleId()));
					}
					if(StringUtils.isNotEmpty(assignSetting.getJobId())){//由岗位的人员来处理
						//取得对应的岗位的所有的人员
						String[]jobIds=assignSetting.getJobId().split("[,]");
						for(String jbId : jobIds){
							List userIds=userPositionService.getUserIdsByPosId(new Long(jbId));
							candidateUserIds.addAll(userIds);
						}
					}
					if(StringUtils.isNotEmpty(assignSetting.getReJobId())){//相对岗位的人员来处理
						//启动人员的相对岗位对应的人员
						String[]reJobIds=assignSetting.getReJobId().split("[,]");
						for(String reJbId:reJobIds){
							List userIds=relativeUserService.getReJobUserIds(flowStartUserId, new Long(reJbId));
							candidateUserIds.addAll(userIds);
						}
					}
				}

				////////////////////为任务进行授权///////////////////////////////////////////////////
				
				//1.是否为会签任务
				if(assignSetting!=null&&ProUserAssign.IS_SIGNED_TASK.equals(assignSetting.getIsSigned())){
					if(candidateUserIds.size()>1){//会签参与人员要多于一人
						Long[] uIds=candidateUserIds.toArray(new Long[]{});
						newSubTask(task.getId(),uIds);
						continue;
					}
				}
				
				if(exeUserId!=null){//为任务直接分配用户
					taskService.assignTask(task.getId(), exeUserId.toString());
					isAssign=true;
				}
				if(candidateUserIds.size()==1){//若候选人员仅有一个，即直接授予该任务
					taskService.assignTask(task.getId(), candidateUserIds.iterator().next().toString());
					isAssign=true;
				}else if(candidateUserIds.size()>1){
					isAssign=true;
					Iterator<Long> its=candidateUserIds.iterator();
					while(its.hasNext()){
						Long userId=its.next();
						taskService.addTaskParticipatingUser(task.getId(),userId.toString(), Participation.CANDIDATE);
					}
				}
				if(candidateGroupIds.size()>0){//把任务分配给角色组
					isAssign=true;
					Iterator<Long> its=candidateGroupIds.iterator();
					while(its.hasNext()){
						Long roleId=its.next();
						taskService.addTaskParticipatingGroup(task.getId(), roleId.toString(), Participation.CANDIDATE);
					}
				}
				
				if(!isAssign){//若没有授予任何人员，则把任务交回到启动者那里(可不要此功能）
					if(logger.isDebugEnabled()){
						logger.debug("------->Task "+task.getActivityName()+" is assign to the flow start there:");
					}
					taskService.assignTask(task.getId(), flowStartUserId.toString());
				}
				//加上日期限制
				if(dtDueDate!=null){
					//jbpmDao.updateDueDate(taskImpl.getDbid(), dtDueDate);
					task.setDuedate(dtDueDate);
				}
				
		}// end of for
		
	}
	/**
	 * 找到某个实例的未授权人员的所有任务，并且进行授权人员
	 * @param piId
	 * @param userId
	 */
	public void assignUnHandlerTask(String piId,String userId){
		
		//找到尚未分配人员的某个流程实例的任务列表
		List<Task> tasks=taskService.createTaskQuery()
		.processInstanceId(piId).unassigned().list();
		
		for(Task task:tasks){
			TaskImpl taskImpl=(TaskImpl)task;
			//若该任务没有对应的人员处理，则直接授权给该启动人
			if(taskImpl.getAssignee()==null || taskImpl.getAllParticipants().size()==0){
				taskService.assignTask(task.getId(), userId);
			}
		}
	}
	
	/**
	 * 显示某个流程当前任务对应的所有出口
	 * @param piId
	 * @return
	 */
	 public List<Transition> getTransitionsForSignalProcess(String piId) {
	        ProcessInstance pi = executionService.findProcessInstanceById(piId);
	        EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
	        EnvironmentImpl env = environmentFactory.openEnvironment();
	        try {
	            ExecutionImpl executionImpl = (ExecutionImpl) pi;
	            ActivityImpl activity = executionImpl.getActivity();
	            List outTrans=activity.getOutgoingTransitions();
	            return outTrans;
	        } finally {
	            env.close();
	        }
	 }
	 
	 
	 /**
	  * 取得开始节点的跳出路线列表
	  * @param deployId
	  * @return
	  */
	 public List<Transition> getStartOutTransByDeployId(String deployId){
		 ProcessDefinitionImpl pd=(ProcessDefinitionImpl)repositoryService.createProcessDefinitionQuery().deploymentId(deployId).uniqueResult();
		 //取得开始节点的名称
		 String startName=getStartNodeNameByDeployId(deployId);
		 EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
			EnvironmentImpl env = environmentFactory.openEnvironment();
			try {
				if(startName!=null){//开始节点有名字
					ActivityImpl activityFind = pd.findActivity(startName);
					if (activityFind != null) {
						List outTrans=activityFind.getOutgoingTransitions();
			            return outTrans;
					}
				}else{//若无名字，则设置
					List activitys=pd.getActivities();
					for(int i=0;i<activitys.size();i++){
						Activity act=(Activity)activitys.get(i);
						if("start".equals(act.getType())){
							List outTrans=act.getOutgoingTransitions();
				            return outTrans;
						}
					}
				}
				
			} finally {
				env.close();
			}
			return new ArrayList();
	 }
	 
	 public List<Transition> getTransitionsByTaskId(String taskId){
		 return getTransitionsByTaskId(taskId,false);
	 }
	 
	 /**
	  * 通过子流程的任务实例id，取得子流程在父流程的跳转分支
	  * @param subFlowTaskId  子流程的任务id
	  * @return
	  */
	 public List<Transition> getTransitionsBySubFlowTaskId(String subFlowTaskId){
		 TaskImpl taskImpl = (TaskImpl) taskService.getTask(subFlowTaskId);
		 if(taskImpl.getExecution().getSuperProcessExecution()!=null){
			 ExecutionImpl parentPi=taskImpl.getExecution().getSuperProcessExecution();
			 EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
				EnvironmentImpl env = environmentFactory.openEnvironment();
				try {
					if (parentPi.getActivity() != null) {
						List outTrans=parentPi.getActivity().getOutgoingTransitions();
						return outTrans;
					}
				} finally {
					env.close();
				}
		 }
		 return new ArrayList();
	 }
	 
	 /**
	  * 取得某个任务节点的所有出口或入口连接
	  * @param taskId
	  * @param isInTransition 是否为入口连接 true为外连接，false为入连接
	  * @return
	  */
	 public List<Transition> getTransitionsByTaskId(String taskId,boolean isInTransition){
		TaskImpl task = (TaskImpl) taskService.getTask(taskId);
		if(task.getSuperTask()!=null){//取得其父任务对应的输出transition
			task=task.getSuperTask();
		}
		EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
		EnvironmentImpl env = environmentFactory.openEnvironment();
		try {
			ProcessDefinitionImpl pd = (ProcessDefinitionImpl) task.getProcessInstance().getProcessDefinition();
			ActivityImpl activityFind = pd.findActivity(task.getActivityName());
			if (activityFind != null) {
				List outTrans=null;
	            if(isInTransition){
	            	outTrans=activityFind.getIncomingTransitions();
	            }else{
	            	outTrans=activityFind.getOutgoingTransitions();
	            }
				return outTrans;
			}
		} finally {
			env.close();
		}
		return new ArrayList();
	 }
	 
	 public List<Transition> getInTransForTask(String taskId){
		 return getTransitionsByTaskId(taskId,true);
	 }
	 
	 /**
	  * 动态创建连接当前任务节点至名称为destName的节点的Transition
	  * @param taskId 任务节点ID
	  * @param sourceName 源节点名称
	  * @param destName  目标节点名称
	  */
	 public void addOutTransition(ProcessDefinitionImpl pd,String sourceName,String destName){
		 
		 EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
		 EnvironmentImpl env=null;
		 try {
			 env = environmentFactory.openEnvironment();
			
			 //取得当前流程的活动定义
			 ActivityImpl sourceActivity = pd.findActivity(sourceName);
			 //取得目标的活动定义
			 ActivityImpl destActivity=pd.findActivity(destName);
			 
			 //为两个节点创建连接
			 TransitionImpl transition = sourceActivity.createOutgoingTransition();
			 transition.setName("to" + destName);
			 transition.setDestination(destActivity);
			 
			 TaskActivity taskAt;
			 TaskDefinitionImpl tdi;
			
			 sourceActivity.addOutgoingTransition(transition);
			 
		 }catch(Exception ex){
			 logger.error(ex.getMessage());
		 }finally{
			 if(env!=null)env.close();
		 }
	 }
	 
	 /**
	  * 动态删除连接sourceName与destName的Transition
	  * @param taskId
	  * @param sourceName
	  * @param destName
	  */
	 @SuppressWarnings("unchecked")
	public void removeOutTransition(ProcessDefinitionImpl pd,String sourceName,String destName){
		 EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
		 EnvironmentImpl env=null;
		 try {
			 env = environmentFactory.openEnvironment();
			 //取得当前流程的活动定义
			 ActivityImpl sourceActivity = pd.findActivity(sourceName);
			 
			 //若存在这个连接，则需要把该连接删除
			 List trans=sourceActivity.getOutgoingTransitions();
			 for(int i=0;i<trans.size();i++){
				Transition tran=(Transition)trans.get(i);
				if(destName.equals(tran.getDestination().getName())){//删除该连接
					trans.remove(tran);
					break;
				}
			 }
		 }catch(Exception ex){
			 logger.error(ex.getMessage());
		 }finally{
			 if(env!=null)env.close();
		 }
	 }
	 /**
	  * 从当前的任务节点，通过动态创建任何跳转的连接，可以跳至流程的任何任务节点 
	  * @param taskId
	  * @return
	  */
	 @SuppressWarnings("unchecked")
	public List<Transition> getFreeTransitionsByTaskId(String taskId){
		 TaskImpl task = (TaskImpl) taskService.getTask(taskId);
		 
		 List outTrans=new ArrayList<Transition>();
		 
		 if(task.getSuperTask()!=null){//取得其父任务对应的输出transition
			task=task.getSuperTask();
		 }
		 EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
		 EnvironmentImpl env=null;
		 try {
			 env = environmentFactory.openEnvironment();
			 ProcessDefinitionImpl pd = (ProcessDefinitionImpl) task.getProcessInstance().getProcessDefinition();
			 ActivityImpl curActivity = pd.findActivity(task.getActivityName());
			 String defXml=jbpmDao.getDefXmlByDeployId(pd.getDeploymentId());
			 //通过DeployId取得可以跳转的节点
			 List<Node> allTaskNodes=getValidNodesFromXml(defXml);
			 
			 for(Node taskNode:allTaskNodes){
				 if(!taskNode.getName().equals(task.getActivityName())){
					 //动态创建连接
					 TransitionImpl transition = curActivity.createOutgoingTransition();
					 //连接的名称加上"to"前缀
					 transition.setName("to" + taskNode.getName());
					 transition.setDestination(pd.findActivity(taskNode.getName()));
					 //同时移除
					 curActivity.getOutgoingTransitions().remove(transition);
					 
					 outTrans.add(transition);
				 }
			 }
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}finally{
			if(env!=null)env.close();
		}
		 
		return outTrans;
	 }
	 
	 /*
	  * (non-Javadoc)
	  * @see com.htsoft.est.service.flow.JbpmService#getProcessDefintionXMLByPiId(java.lang.String)
	  */
	 public String getProcessDefintionXMLByPiId(String piId){
		 ProcessRun processRun=processRunService.getByPiId(piId);
		 String defXml=jbpmDao.getDefXmlByDeployId(processRun.getProDefinition().getDeployId());
		 return defXml;
	 }
	 
	 /**
	  * 取得某流程实例对应的任务列表
	  * @param piId
	  * @return
	  */
	 public List<Task> getTasksByPiId(String piId){
		 List<Task> taskList=taskService.createTaskQuery().processInstanceId(piId).list();
		 return taskList;
	 }
	    
    /**
	 * 取到节点类型
	 * @param xml
	 * @param nodeName
	 * @return
	 */
	public String getNodeType(String xml,String nodeName){
		String type="";
		try{
			Element root = DocumentHelper.parseText(xml).getRootElement();
			for (Element elem : (List<Element>) root.elements()){
				if(elem.attribute("name")!=null){
					String value=elem.attributeValue("name");
					if(value.equals(nodeName)){
						type = elem.getQName().getName();
						return type;
					}
				}
			}
		}catch(Exception ex){
			logger.info(ex.getMessage());
		}
		return type;
	}
	
	protected void clearSession(){
		EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
        EnvironmentImpl env = environmentFactory.openEnvironment();
        try{
	        Session session=env.get(Session.class);
	        session.clear();
        }finally{
        	env.close();
        }
	}
	
	protected void clear(){
		EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
        EnvironmentImpl env = environmentFactory.openEnvironment();
        try{
	        Session session=env.get(Session.class);
	        session.clear();
        }finally{
        	env.close();
        }
	}
	
	protected void flush(){
		EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
        EnvironmentImpl env = environmentFactory.openEnvironment();
        try{
	        Session session=env.get(Session.class);
	        session.flush();
        }finally{
        	env.close();
        }
	}
	
	protected void evict(Object obj){
		EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
        EnvironmentImpl env = environmentFactory.openEnvironment();
        try{
	        Session session=env.get(Session.class);
	        session.evict(obj);
        }finally{
        	env.close();
        }
	}
	
	/**
	 * 完成任务,包括子任务
	 * @param taskId 任务ID
	 * @param signalName 跳转路径名
	 * @param destName 目标节点名称
	 * @param variables
	 */
	public void completeTask(String taskId,String signalName,String destName,Map variables){
		TaskImpl taskImpl=(TaskImpl)taskService.getTask(taskId);
		if(destName==null){//若没有传入destName
			List<Transition> trans=getTransitionsByTaskId(taskId);
			for(Transition tran:trans){
				if(tran.getName().equals(signalName)){
					destName=tran.getDestination().getName();
					break;
				}
			}
		}
		//取得该任务的父任务
		TaskImpl parentTask=taskImpl.getSuperTask();
		ProcessInstance pi=null;
		//父流程实例
		ProcessInstance parentPi=null;
    	//是否会签任务
    	if(parentTask!=null){
    		pi=parentTask.getProcessInstance();
    		ExecutionImpl piExe=(ExecutionImpl)pi;
    		if(piExe.getSuperProcessExecution()!=null){
    			parentPi=piExe.getSuperProcessExecution();
    		}
	    	if(logger.isDebugEnabled()){
	    		logger.debug("Super task is not null, task name is:" + parentTask.getActivityName());
	    	}
	    	completeSignSubTask(parentTask,taskImpl,signalName,variables);
	    }else{//普通任务，直接完成，进入下一步
	    	
	    	pi=taskImpl.getProcessInstance();
	    	ExecutionImpl piExe=(ExecutionImpl)pi;
    		if(piExe.getSuperProcessExecution()!=null){
    			parentPi=piExe.getSuperProcessExecution();
    		}
	    	completeTaskAndJump(taskId, destName, variables);
	    }

    	
    	
    	//流程是否结束了，结束后，需要把状态写回流程实例中去
	    boolean isEndProcess=isProcessInstanceEnd(pi.getId());
    	if (isEndProcess) { // 流程实例已经结束了，保存结束的状态，返回
    		
    		//若为子流程，需要为父流程进行人员的指派
    		if(parentPi!=null){
    			//为下一任务授权
    		    assignTask(parentPi,variables);
    		}
	    	ProcessRun processRun=processRunService.getByPiId(pi.getId());
	    	if(processRun!=null){
	    		processRun.setPiId(null);
	    		processRun.setRunStatus(ProcessRun.RUN_STATUS_FINISHED);
	    		processRunService.save(processRun);
	    	}
	    	return;
	    }

    	
    	//为下一任务授权
	    assignTask(pi,variables);
	    
	    if(pi.getSubProcessInstance()!=null){
	    	 logger.info("debug for subProcessinstance...........");
	    	 assignTask((ProcessInstance)pi.getSubProcessInstance(),variables);
	    }
	    
	}
	
	/**
	 * 完成会签子任务
	 * @param parentTask 父任务
	 * @param subTask 子任务
	 * @param variables 任务中的流程变量
	 */
	private void completeSignSubTask(TaskImpl parentTask,TaskImpl subTask,String signalName,Map variables){
		
		//看目前还有多少子任务
		int subTasksSize=parentTask.getSubTasks().size();
		
		//检查会签的配置情况
		ProcessInstance pi=((TaskImpl)parentTask).getProcessInstance();
		ProcessDefinition pd=repositoryService.createProcessDefinitionQuery().processDefinitionId(pi.getProcessDefinitionId()).uniqueResult();
		//取得该任务的后台人员配置
		ProUserAssign assignSetting=proUserAssignService.getByDeployIdActivityName(pd.getDeploymentId(), parentTask.getActivityName());
		
		evict(subTask);
		evict(parentTask);
		
		if(assignSetting!=null){
			//取得会签配置
			TaskSign taskSign=taskSignService.findByAssignId(assignSetting.getAssignId());
			
			if(taskSign!=null){//按照配置执行任务跳转
				//是否完成父任务
				boolean isFinishSupTask=false;

				//查看用户投的是哪一种票（同意还是不同意还是弃权）
				Short isAgree=(Short)variables.get(FlowConstants.SIGN_VOTE_TYPE);
				
				if(isAgree==null){//若为空，则认为是投通过票
					isAgree=TaskSign.DECIDE_TYPE_PASS;
				}
				
				//1.保存投票信息
				taskSignDataService.addVote(parentTask.getId(),isAgree);
				
				//加上子任务的流程变量
				taskService.setVariables(subTask.getId(),variables);
			    //2.完成子任务
				taskService.completeTask(subTask.getId());
				
				//3.检查其投票数是否已满足后台会签配置条件
				//3.1根据后台配置的投票类型，取得投票的总数
				Long voteCounts=taskSignDataService.getVoteCounts(parentTask.getId(),taskSign.getDecideType());
				
				if(taskSign.getVoteCounts()!=null){//按绝对投票数来进行
					if(voteCounts>=taskSign.getVoteCounts()){
						isFinishSupTask=true;
					}
				}else if(taskSign.getVotePercents()!=null){//按投票百分比来进行
					//取到动态有多少子任务
					Integer taskSignCounts=(Integer)taskService.getVariable(parentTask.getId(), "taskSignCounts");
					if(taskSignCounts==null || taskSignCounts==0){
						taskSignCounts=1;
					}
					BigDecimal totalSubTasks=new BigDecimal(taskSignCounts);
					//当前投票后占的百分比
					BigDecimal tempPercent=new BigDecimal(voteCounts).divide(totalSubTasks);
					Integer curPercent=new Integer(tempPercent.multiply(new BigDecimal(100)).intValue());
					
					if(curPercent>=taskSign.getVotePercents()){
						isFinishSupTask=true;
					}
				}
				//若投票完成后，把投票结果存在decisionType变量里，方便在后台通过脚本根据投票的结果进行跳转
				Map varsMap=new HashMap();
				
				//当前会签子任务完成后，若投票的情况已经满足后台的会签设置条件
				//或没有满足会签设置的情况，并且会签所有子任务均已经完成
				if(isFinishSupTask || (!isFinishSupTask && subTasksSize==1) ){
					String passRefuse=null;
					if(isFinishSupTask){//所有子任务完成，满足会签条件设置
						passRefuse=TaskSign.DECIDE_TYPE_PASS==taskSign.getDecideType()?"pass":"refuse";
					}else{//所有子任务完成，不满足会签条件设置
						passRefuse=taskSign.getDecideType()==TaskSign.DECIDE_TYPE_PASS?"refuse":"pass";
					}
					logger.debug("会签投票结果："+ passRefuse);
					varsMap.put("decisionType",passRefuse);
					taskService.setVariables(parentTask.getId(),varsMap);
					//完成父任务
				    taskService.completeTask(parentTask.getId(),signalName);
				}
				
			}else{//没有设置对应的会签配置，则认为会签是全部完成后才能往下执行
				logger.error("Task "+parentTask.getActivityName()+" is not config right sign config in process admin console.");
				
				if(((TaskImpl)parentTask).getSubTasks().size()==1){//若只有当前子任务，则表示可以结束目前这个任务
					taskService.setVariables(subTask.getId(),variables);
				    //完成子任务
					taskService.completeTask(subTask.getId());
					//完成父任务
				    taskService.completeTask(parentTask.getId(),signalName);
				}else{
					taskService.setVariables(subTask.getId(), variables);
					//完成子任务后，直接返回则可
					taskService.completeTask(subTask.getId());
				    return ;
				}
			}
		}else{
			//TODO
			logger.error("Task "+parentTask.getActivityName()+"is not config the setting in process admin console.");
		}
	}
	
	/**
	 * 查看当前任务是否已经结束
	 * @param piId
	 * @return
	 */
	protected boolean isProcessInstanceEnd(String piId){
		
		 HistoryProcessInstance hpi = historyService.createHistoryProcessInstanceQuery().processInstanceId(piId).uniqueResult(); 
		 if(hpi!=null){//检查当前的流程是否已经结束
			    String endActivityName = ((HistoryProcessInstanceImpl) hpi).getEndActivityName();  
			    if (endActivityName != null) { 
			    	return true;
			    }
		 }
		 return false;
	}
	
	/**
	 * 创建新的任务
	 * @param parentTaskId 父任务 ID
	 * @param assignIds 任务执行人IDs
	 */
	public void newSubTask(String parentTaskId,Long[]userIds){
		
		TaskServiceImpl taskServiceImpl=(TaskServiceImpl) taskService;
		Task parentTask=taskServiceImpl.getTask(parentTaskId);
		
		//为该父任务加上会签的人员数，方便后面对会签的投票进行统计
		Map vars=new HashMap();
		vars.put("taskSignCounts", new Integer(userIds.length));
		taskServiceImpl.setVariables(parentTaskId, vars);
		
		for(int i=0;i<userIds.length;i++){
			String userId=userIds[i].toString();
			TaskImpl task=(TaskImpl)taskServiceImpl.newTask(parentTaskId);
			task.setAssignee(userId);
			task.setName(parentTask.getName() + "-" + (i+1));
			task.setActivityName(parentTask.getName() );
			task.setDescription(parentTask.getDescription());
			//保存
			taskServiceImpl.saveTask(task);
		}
	}
	
	
	/**
	 * 
     * 执行下一步的流程，对于非任务节点
     * @param id processInstanceId
     * @param transitionName
     * @param variables
     */
    public void signalProcess(String executionId, String transitionName,
        Map<String, Object> variables) {
       
        executionService.setVariables(executionId,variables);
        executionService.signalExecutionById(executionId,transitionName);
    }
    
    
    public void endProcessInstance(String piId) {
        ExecutionService executionService = processEngine.getExecutionService();
        executionService.endProcessInstance(piId,Execution.STATE_ENDED);
    }

   
    /**
     * 为流程定义加上任务的指派人员接口
     * @param deployId
     */
    public void addAssignHandler(ProUserAssign proUserAssign){
    	ProcessDefinitionImpl pd=(ProcessDefinitionImpl)repositoryService.createProcessDefinitionQuery().deploymentId(proUserAssign.getDeployId()).uniqueResult();
    	EnvironmentImpl env=null;
		try {
			 env = getEnvImpl();
			 //找到任务的定义
			 TaskDefinitionImpl taskDef=pd.getTaskDefinition(proUserAssign.getActivityName());
			 UserCodeReference userCodeReference = new UserCodeReference();
			 ObjectDescriptor descriptor = new ObjectDescriptor();
			 //加上任务的人员动态指派
			 descriptor.setClassName("com.htsoft.core.jbpm.UserAssignHandler");
			 //动态加参数
			 FieldOperation userIdsFo = new FieldOperation();
			 userIdsFo.setFieldName("userIds");	
			 userIdsFo.setDescriptor(new StringDescriptor(proUserAssign.getUserId()));
			 
			 FieldOperation groupIdsFo=new FieldOperation();
			 groupIdsFo.setFieldName("groupIds");
			 groupIdsFo.setDescriptor(new StringDescriptor(proUserAssign.getRoleId()));
			 
			 List<Operation> listOp=new ArrayList<Operation>();
			 listOp.add(userIdsFo);
			 listOp.add(groupIdsFo);
			 descriptor.setOperations(listOp);
			 
			 userCodeReference.setCached(false);
			 userCodeReference.setDescriptor(descriptor);
			 taskDef.setAssignmentHandlerReference(userCodeReference);
			 
		 }catch(Exception ex){
			 logger.error("ADD AssignHandler Error:" + ex.getMessage());
		 }finally{
			 if(env!=null){
				 env.close();
			 }
		 }
    }
    
	
    private EnvironmentImpl getEnvImpl(){
    	EnvironmentImpl env = ((EnvironmentFactory) processEngine).openEnvironment();
    	return env;
    }
    /*
     * (non-Javadoc)
     * @see com.htsoft.est.service.flow.JbpmService#getNodeHandlerUsers(org.jbpm.api.ProcessDefinition, java.lang.String, java.lang.Long)
     */
    public Set<AppUser> getNodeHandlerUsers(ProcessDefinition pd,String activityName,Long startUserId){
    	
    	Set users=new HashSet<AppUser>();
    	
    	ProUserAssign proUserAssign=proUserAssignService.getByDeployIdActivityName(pd.getDeploymentId(), activityName);
    	if(proUserAssign!=null){
    		if(Constants.FLOW_START_ID.equals(proUserAssign.getUserId())){
				if(startUserId!=null){//流程启动人不为空
					users.add(appUserService.get(startUserId));
				}else{
					users.add(ContextUtil.getCurrentUser());
				}
			}else if(StringUtils.isNotEmpty(proUserAssign.getUserId())){//设置了固定的人员
    			String []userIds=proUserAssign.getUserId().split("[,]");
    			for(int i=0;i<userIds.length;i++){
    				AppUser appUser=appUserService.get(new Long(userIds[i]));
    				users.add(appUser);
    			}
    		}
    		
    		if(StringUtils.isNotEmpty(proUserAssign.getRoleId())){//取得角色列表下所有用户
    			List<AppUser> userList=appUserService.findUsersByRoleIds(proUserAssign.getRoleId());
    			users.addAll(userList);
    		}
    		
    		if(StringUtils.isNotEmpty(proUserAssign.getJobId())){//按绝对岗位取所有的用户
    			String[]jobIds=proUserAssign.getJobId().split("[,]");
    			for(String jobId:jobIds){
    				Position p = positionService.get(new Long(jobId));
    				Set set = p.getUserPositions();
    				Iterator iteUp = set.iterator();
    				while(iteUp.hasNext()){
    					UserPosition userPos = (UserPosition)iteUp.next();
    					if(userPos.getAppUser().getDelFlag()==0)
    						users.add(userPos.getAppUser());
    				}
    			}
    		}
    		
    		 if(proUserAssign.getPosUserFlag()!=null&& proUserAssign.getReJobId()!=null){// 上下级
    			if(proUserAssign.getPosUserFlag()==0){// 按岗位选择
        			List userList = appUserService.getReLevelUser(proUserAssign.getReJobId());
        			users.addAll(userList);
    			}else{// 按人员选择
    				String[]jobIds=proUserAssign.getReJobId().split("[,]");
    				for(String jobId:jobIds){
    					List userList = relativeUserService.findByUserIdReJobId(ContextUtil.getCurrentUserId(), new Long(jobId));
    					users.addAll(userList);
    				}
    			}
    		}
    		
    		if(StringUtils.isNotEmpty(proUserAssign.getDepIds())){//部门负责人
    			String[]depIds=proUserAssign.getDepIds().split("[,]");
    			for(String depId:depIds){
    				Organization org = organizationService.get(new Long(depId));
    				List orgUsers=appUserService.getChargeOrgUsers(org.getUserOrgs());
    				users.addAll(orgUsers);
    			}
    		}
    	}
    	
    	//若流程没有指定人员，直接转至启动人员那里 
    	if(users.size()==0){
    		if(startUserId!=null){
    			users.add(appUserService.get(startUserId));
    		}else{
    			users.add(ContextUtil.getCurrentUser());
    		}
    	}
    	
    	return users;
    }
    
    /**
     * 取得流程定义中的节点处理人
     * @param defId
     * @param activityName
     * @return
     */
    public Set<AppUser> getNodeHandlerUsers(Long defId,String activityName){
    	ProcessDefinition pd=getProcessDefinitionByDefId(defId);
    	return getNodeHandlerUsers(pd, activityName, null);
    }
    
    /**
     * 取得流程某个节点的处理人员列员 TODO
     * @param taskId  当前任务的实例id
     * @param activityName 下一任务活动节点的名称
     * @return
     */
    public Set<AppUser> getNodeHandlerUsers(String taskId,String nextActivityName){
    	
    	TaskImpl task=(TaskImpl)taskService.getTask(taskId);
    	Set<AppUser> users=new HashSet<AppUser>();
    	if(task.getAssignee()!=null){
    		users.add(appUserService.get(new Long(task.getAssignee())));
    	} else if(task.getAllParticipants() != null && task.getAllParticipants().size() > 0){
    		Iterator<ParticipationImpl> partIt = task.getAllParticipants().iterator(); // 执行任务所有的用户信息
			while(partIt.hasNext()){
				ParticipationImpl part = partIt.next();
				if(part.getGroupId()!= null && StringUtil.isNumeric(part.getGroupId())){
					List<AppUser> appUserList = appUserService.findByRoleId(new Long(part.getGroupId()));
					users.addAll(appUserList);
				}else if(part.getUserId() != null && StringUtil.isNumeric(part.getUserId())){
					AppUser appUser = appUserService.get(new Long(part.getUserId()));
					users.add(appUser);
				}
			}
    	}
    	if(users.size()>0){
    		return users;
    	}
    	
    	ProcessInstance pi=getProcessInstanceByTaskId(taskId);
    	
    	ProcessDefinition pd=getProcessDefinitionByTaskId(taskId);	
    	
    	Long startUserId=(Long)executionService.getVariable(pi.getId(), FlowRunInfo.START_USER_ID);
    	
    	return getNodeHandlerUsers(pd, nextActivityName, startUserId);
    }
    
    /**
	 * 取到流程的启动用户
	 * @param taskId
	 * @return
	 */
	public Long getFlowStartUserId(String taskId){
		ProcessInstance pi=getProcessInstanceByTaskId(taskId);
		Long startUserId=(Long)executionService.getVariable(pi.getId(), FlowRunInfo.START_USER_ID);
		if(startUserId==null){
			ProcessRun processRun=processRunService.getByPiId(pi.getId());
			if(processRun!=null){
				return processRun.getUserId();
			}
		}
		return startUserId;
	}
    
    
    
    /**
     * 取得某个任务其对应流程变量值
     * @param taskId 任务ID
     * @param varName 变量名称
     * @return
     */
    public Object getVarByTaskIdVarName(String taskId,String varName){
    	TaskImpl task=(TaskImpl)getTaskById(taskId);
    	if(task.getSuperTask()!=null){
    		taskId=task.getSuperTask().getId();
    	}
    	return taskService.getVariable(taskId, varName);
    }
    
    /**
     * 返回某个任务的所有变量
     * @param taskId
     * @return
     */
    public Map<String,Object> getVarsByTaskId(String taskId){
    	Task task=getParentTask(taskId);
    	Map<String,Object> varMap=new HashMap<String,Object>();
    	Set<String> varNames=taskService.getVariableNames(task.getId());
    	Iterator<String> nameIt=varNames.iterator();
    	while(nameIt.hasNext()){
    		String varName=nameIt.next();
    		Object objVal=taskService.getVariable(task.getId(), varName);
    		varMap.put(varName, objVal);
    	}
    	return varMap;
    }
    
    /**
     * 取得流程定义中的可以跳转的节点
     * @param xml
     * @return
     */
    private List<Node> getValidNodesFromXml(String xml){
		List<Node> nodes=new ArrayList<Node>();
		try{
			Element root = DocumentHelper.parseText(xml).getRootElement();
			   for (Element elem : (List<Element>) root.elements()) {
		            String type = elem.getQName().getName();
		            if("task".equalsIgnoreCase(type)){
			            if (elem.attribute("name") != null) {
			               Node node=new Node(elem.attribute("name").getValue(),"任务节点");
			               nodes.add(node);
			            }
		            }else if("fork".equalsIgnoreCase(type)){
		            	if (elem.attribute("name") != null){
		            		Node node=new Node(elem.attribute("name").getValue(),"同步节点");
				            nodes.add(node);
		            	}
		            }else if("join".equalsIgnoreCase(type)){
		            	if (elem.attribute("name") != null){
		            		Node node=new Node(elem.attribute("name").getValue(),"汇集节点");
				            nodes.add(node);
		            	}
		            }
		            else if(type.startsWith("end")){
		            	if (elem.attribute("name") != null){
		            		Node node=new Node(elem.attribute("name").getValue(),"分支节点");
				            nodes.add(node);
		            	}
		            }else if(type.startsWith("end")){
		            	Node node=new Node(elem.attribute("name").getValue(),"结束节点");
			            nodes.add(node);
		            }
		       }
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
		return nodes;
	}
    
    @Override
    public List<Transition> getNodeOuterTrans(ProcessDefinition definition,
    		String nodeName) {
    	EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
		EnvironmentImpl env = environmentFactory.openEnvironment();
		try {
			ProcessDefinitionImpl pd = (ProcessDefinitionImpl) definition;
			ActivityImpl activityFind = pd.findActivity(nodeName);
			
			if (activityFind != null) {
				return (List<Transition>)activityFind.getOutgoingTransitions();
			}
		} finally {
			env.close();
		}
		return new ArrayList();
    }
    /**
     * 取得某个任务的所有子任务的处理人员
     * @param parentTaskId
     * @return
     */
    public List<String> getAssigneeByTaskId(String parentTaskId){
    	List list=new ArrayList();
    	TaskImpl taskImpl=(TaskImpl)getTaskById(parentTaskId);
    	if(taskImpl.getAssignee()!=null){
    		list.add(taskImpl.getAssignee());
    	}
    	Set<Task> subTasks= taskImpl.getSubTasks();
    	if(subTasks!=null){
    		Iterator<Task> it=subTasks.iterator();
    		while(it.hasNext()){
    			Task subTask=it.next();
    			if(subTask.getAssignee()!=null){
    				list.add(subTask.getAssignee());
    			}
    		
    		}
    	}
    	return list;
    }
    
    /**
     * 取得父任务
     * @param subTaskId 子任务ID
     * @return
     */
    public Task getParentTask(String subTaskId){
    	TaskImpl taskImpl=(TaskImpl)getTaskById(subTaskId);
    	if(taskImpl.getSuperTask()!=null){
    		return taskImpl.getSuperTask();
    	}
    	return taskImpl;
    }

    /**
     * 跳回前一任务
     * @param piId
     * @param assignee
     * @param preTaskName
     */
    public void jumpToPreTask(String piId,String assignee,String preTaskName){
    	//结束当前的所有任务，并且进行跳转,但仅取得其一任务来进行跳转
    	List<Task> tasks=getTasksByPiId(piId);
    	//目前的任务实例
    	Task nowTask=null;
    	/**
    	 * 流程实例主键ID
    	 */
    	Long piDbId=null;
    	
    	for(int i=0;i<tasks.size();i++){
    		TaskImpl task=(TaskImpl)tasks.get(i);
    		flowTaskService.evict(task);
    		if(i==0){
    			nowTask=task;
    		}else{
	    		piDbId=task.getProcessInstance().getDbid();
	    		taskService.completeTask(task.getId());
    		}
    	}
    	//进行任务分配
    	if(nowTask!=null){
    		jumpTaskToAnother(nowTask,preTaskName,null);
    		//进行任务授权
    		List<Task> newTasks=getTasksByPiId(piId);
    		//1.把exectuion中的主execution 的state_字段的值改为active-root;
        	//2.更新子任务中的execution指向(1.execution_id_值需要更新为主execution的id，exeution_,procinst_值均指向同一值）
        	//3.把其下的子记录删除
    		if(piDbId!=null){//若存在有同步流程的跳转，则需要按以下方式更新为正确的任务记录
    			ExecutionImpl execution= (ExecutionImpl)flowTaskService.getExecutionByDbid(piDbId);
    			
    			execution.setState(Execution.STATE_ACTIVE_ROOT);
    			//1.
    			flowTaskService.save(execution);
    			//2.
    			for(Task newTask:newTasks){
    				TaskImpl newTaskImpl=(TaskImpl)newTask;
    				newTaskImpl.setProcessInstance(execution);
    				newTaskImpl.setExecution(execution);
    				newTaskImpl.setAssignee(assignee);
    				newTaskImpl.setActivityName(newTask.getName());
    				ProcessDefinitionImpl pd=(ProcessDefinitionImpl)getProcessDefinitionByTaskId(newTask.getId());
    				
    				Activity activity= pd.findActivity(newTask.getName());
    				if(activity!=null){
    					execution.setActivity(activity);
    				}
    				flowTaskService.save(newTaskImpl);
        		}
    			
    			//3.
    			flowTaskService.removeExeByParentId(piDbId);
    		}else{
	    		for(Task newTask:newTasks){
	    			taskService.assignTask(newTask.getId(), assignee);
	    		}
    		}
    	}
    }
    
    /**
     * 完成当前任务，并且进行跳转
     * @param taskId  当前任务id
     * @param destName 跳于的目录节点名
     * @param varialbe 任务执行进携带的参数
     */
    public void completeTaskAndJump(String taskId,String destName,Map variables){
    	TaskImpl curTaskImpl=(TaskImpl)getTaskById(taskId);
    	String piId=curTaskImpl.getProcessInstance().getId();
    	
    	List<Task> tasks=getTasksByPiId(piId);
    	
    	//当前任务是否为同步任务
    	boolean isConcurrent=Execution.STATE_ACTIVE_CONCURRENT.equals(curTaskImpl.getExecution().getState());
    	
    	//不管两节点是否连着，均可以通过下面方法进行自由跳转，即完成任务
    	Integer formalJump=jumpTaskToAnother(curTaskImpl,destName,variables);
    	
    	if(formalJump==0&&isConcurrent){//若目前为自由跳转并且是从同步任务中的任务跳至非同步的任务，则其相同的子任务需要完成
	    	for(Task task:tasks){
	    		if(!task.getId().equals(curTaskImpl.getId())){
	    			taskService.completeTask(task.getId());
	    		}
	    	}
	    	List<Task> newTasks=getTasksByPiId(piId);
	    	
	    	Long piDbId=curTaskImpl.getProcessInstance().getDbid();
    		ExecutionImpl execution= (ExecutionImpl)flowTaskService.getExecutionByDbid(piDbId);
			
			execution.setState(Execution.STATE_ACTIVE_ROOT);
			//1.
			flowTaskService.save(execution);
			//2.
			for(Task newTask:newTasks){
				TaskImpl newTaskImpl=(TaskImpl)newTask;
				newTaskImpl.setProcessInstance(execution);
				newTaskImpl.setExecution(execution);
				
				newTaskImpl.setActivityName(newTask.getName());
				ProcessDefinitionImpl pd=(ProcessDefinitionImpl)getProcessDefinitionByTaskId(newTask.getId());
				
				Activity activity= pd.findActivity(newTask.getName());
				if(activity!=null){
					execution.setActivity(activity);
				}
				flowTaskService.save(newTaskImpl);
    		}
			//3.
			flowTaskService.removeExeByParentId(piDbId);
    	}
    }
    
    /**
	 * 把修改过的xml更新至回流程定义中
	 * @param deployId
	 * @param defXml
	 */
	public void wirteDefXml(String deployId,String defXml){
		EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
		EnvironmentImpl env = environmentFactory.openEnvironment();
		try {
			//清空发布缓存
			RepositoryCache repositoryCache = org.jbpm.pvm.internal.env.EnvironmentImpl.getFromCurrent(RepositoryCache.class);
		    repositoryCache.set(deployId,null);
		} finally {
			env.close();
		}
	    //写至Xml到数据库
		jbpmDao.wirteDefXml(deployId, defXml);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.htsoft.est.service.flow.JbpmService#getTransByDefIdActivityName(java.lang.Long, java.lang.String)
	 */
	public List<Transition> getTransByDefIdActivityName(Long defId,
			String activityName) {
		ProcessDefinitionImpl pd = (ProcessDefinitionImpl) getProcessDefinitionByDefId(defId);
		Activity act = pd.findActivity(activityName);
		return (List<Transition>) act.getOutgoingTransitions();
	}
	
	
}
